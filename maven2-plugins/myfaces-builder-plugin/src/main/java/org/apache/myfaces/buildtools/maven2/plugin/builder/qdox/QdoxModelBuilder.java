/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelBuilder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.AttributeMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ClassMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FaceletTagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ListenerHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ListenerMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.MethodSignatureMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RenderKitMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RendererMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.TagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.WebConfigMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.WebConfigParamMeta;
import org.codehaus.plexus.components.io.fileselectors.IncludeExcludeFileSelector;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.Type;

/**
 * An implementation of the ModelBuilder interface that uses the Qdox java
 * source-parsing library to scan a list of specified source directories for
 * java files.
 * <p>
 * The java source files found can use either java15 annotations or doclet
 * annotations to indicate what data should be added to the Model.
 */
public class QdoxModelBuilder implements ModelBuilder
{
    private final Log log = LogFactory.getLog(QdoxModelBuilder.class);

    private static final String DOC_CONVERTER = "JSFConverter";
    private static final String DOC_VALIDATOR = "JSFValidator";
    private static final String DOC_COMPONENT = "JSFComponent";
    private static final String DOC_RENDERER = "JSFRenderer";
    private static final String DOC_RENDERKIT = "JSFRenderKit";
    private static final String DOC_RENDERERS = "JSFRenderers";

    private static final String DOC_PROPERTY = "JSFProperty";
    private static final String DOC_FACET = "JSFFacet";
    private static final String DOC_LISTENER = "JSFListener";
    
    //This property is used in special cases where properties 
    //does not have methods defined on component class, like binding
    //in jsf 1.1 (in 1.2 has component counterpart). In fact, all
    //properties must be defined with JSFProperty
    private static final String DOC_JSP_PROPERTY = "JSFJspProperty";
    private static final String DOC_JSP_PROPERTIES = "JSFJspProperties";
    
    private static final String DOC_TAG = "JSFJspTag";
    private static final String DOC_JSP_ATTRIBUTE = "JSFJspAttribute";
    private static final String DOC_FACELET_TAG = "JSFFaceletTag";
    private static final String DOC_FACELET_TAGS = "JSFFaceletTags";
    private static final String DOC_FACELET_TAG_ATTRIBUTE = "JSFFaceletAttribute";
    private static final String DOC_FACELET_TAG_ATTRIBUTES = "JSFFaceletAttributes";
    
    private static final String DOC_WEB_CONFIG_PARAM = "JSFWebConfigParam";
    
    private static class JavaClassComparator implements Comparator
    {
        public int compare(Object arg0, Object arg1)
        {
            JavaClass c0 = (JavaClass) arg0;
            JavaClass c1 = (JavaClass) arg1;

            return (c0.getFullyQualifiedName().compareTo(c1.getFullyQualifiedName()));
        }
    }

    /**
     * Scan the source tree for doc-annotations, and build Model objects
     * containing info extracted from the doc-annotation attributes and
     * introspected info about the item the annotation is attached to.
     */
    public void buildModel(Model model, MavenProject project)
            throws MojoExecutionException
    {
        buildModel(model, project.getCompileSourceRoots());
    }
    
    /**
     * @since 1.0.2
     */
    public void buildModel(Model model, MavenProject project, String includes, String excludes)
        throws MojoExecutionException
    {
        if (StringUtils.isNotEmpty(includes) || 
                StringUtils.isNotEmpty(excludes))
        {
            buildModel(model, project.getCompileSourceRoots(),includes, excludes);            
        }
        else
        {
            buildModel(model, project.getCompileSourceRoots());
        }
    }
    
    /**
     * @since 1.0.2
     */
    public void buildModel(Model model, List sourceDirs, String includes, String excludes)
        throws MojoExecutionException
    {
        String currModelId = model.getModelId();
        if (currModelId == null)
        {
            throw new MojoExecutionException("Model must have id set");
        }
        //System.out.println("includes:"+includes+" excludes:"+excludes);

        JavaDocBuilder builder = new JavaDocBuilder();

        IncludeExcludeFileSelector selector = 
            new IncludeExcludeFileSelector(); 
    
        if (StringUtils.isNotEmpty(excludes))
        {
            selector.setExcludes(excludes.split(","));
        }
        if (StringUtils.isNotEmpty(includes))
        {
            selector.setIncludes(includes.split(","));            
        }
        
        for (Iterator i = sourceDirs.iterator(); i.hasNext();)
        {
            Object dir = i.next();
            File srcDir = null;
            if (dir instanceof File)
            {
                srcDir = (File) dir;
            }
            else         
            {
                new File((String) i.next());
            }
            
            //Scan all files on directory and add to builder
            QdoxHelper.addFileToJavaDocBuilder(builder, selector, srcDir);
        }        
        
        JavaClass[] classes = builder.getClasses();
        
        buildModel(model, sourceDirs, classes);
    }
    
    /**
     * Scan the source tree for doc-annotations, and build Model objects
     * containing info extracted from the doc-annotation attributes and
     * introspected info about the item the annotation is attached to.
     */
    public void buildModel(Model model, List sourceDirs)
            throws MojoExecutionException
    {
        String currModelId = model.getModelId();
        if (currModelId == null)
        {
            throw new MojoExecutionException("Model must have id set");
        }

        JavaDocBuilder builder = new JavaDocBuilder();

        // need a File object representing the original source tree
        //
        // TODO: make this more flexible, so specific classes can
        // be included and excluded.
        for (Iterator i = sourceDirs.iterator(); i.hasNext();)
        {
            String srcDir = (String) i.next();
            builder.addSourceTree(new File(srcDir));
        }

        JavaClass[] classes = builder.getClasses();

        buildModel(model, sourceDirs, classes);
    }
    
    protected void buildModel(Model model, List sourceDirs, JavaClass[] classes)
        throws MojoExecutionException
    {
        String currModelId = model.getModelId();

        // Sort the class array so that they are processed in a
        // predictable order, regardless of how the source scanning
        // returned them.
        Arrays.sort(classes, new JavaClassComparator());
        Map processedClasses = new HashMap();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass clazz = classes[i];
            processClass(processedClasses, clazz, model);
        }

        // Post-process the list of components which we added in this run.
        // Note that model has all the inherited components in it too, so
        // we need to skip them.
        //
        // Hmm..as noted elsewhere, JavaClass objects representing parent
        // classes are accessable via getParentClazz(). Presumably they are
        // not in the array returned by builder.getClasses() though..
        for (Iterator it = model.getComponents().iterator(); it.hasNext();)
        {
            ComponentMeta component = (ComponentMeta) it.next();
            if (!component.getModelId().equals(currModelId))
            {
                continue;
            }
            QdoxHelper.initComponentAncestry(processedClasses, model, component);

            //Check if the component class java file exists in the source dirs
            String classname = component.getClassName();
            String classfile = StringUtils.replace(classname,".","/")+".java";
            if (!IOUtils.existsSourceFile(classfile, sourceDirs))
            {
                component.setGeneratedComponentClass(Boolean.TRUE);
            }

            // Check if the tag class java file exists in the source dirs
            if (QdoxHelper.isTagClassMissing(component.getTagClass(), sourceDirs))
            {
                component.setGeneratedTagClass(Boolean.TRUE);
            }
        }

        // post-process the list of converters
        for (Iterator it = model.getConverters().iterator(); it.hasNext();)
        {
            ConverterMeta converter = (ConverterMeta) it.next();
            if (!converter.getModelId().equals(currModelId))
            {
                continue;
            }
            QdoxHelper.initConverterAncestry(processedClasses, model, converter);
            // TODO: why is there no check for Converter class existence here??
            // ANS: there is no automatic generation of converter class.
            
            // Check if the tag class java file exists in the source dirs
            if (QdoxHelper.isTagClassMissing(converter.getTagClass(), sourceDirs))
            {
                converter.setGeneratedTagClass(Boolean.TRUE);
            }
        }

        // post-process the list of validators
        for (Iterator it = model.getValidators().iterator(); it.hasNext();)
        {
            ValidatorMeta validator = (ValidatorMeta) it.next();
            if (!validator.getModelId().equals(currModelId))
            {
                continue;
            }
            QdoxHelper.initValidatorAncestry(processedClasses, model, validator);
            
            //Check if the validator class file exists
            if (!IOUtils.existsSourceFile(StringUtils.replace(
                    validator.getClassName(),".","/")+".java", sourceDirs))
            {
                validator.setGeneratedComponentClass(Boolean.TRUE);
            }

            // Check if the tag class java file exists in the source dirs
            if (QdoxHelper.isTagClassMissing(validator.getTagClass(), sourceDirs))
            {
                validator.setGeneratedTagClass(Boolean.TRUE);
            }
        }

        // post-process the list of tags
        for (Iterator it = model.getTags().iterator(); it.hasNext();)
        {
            TagMeta tag = (TagMeta) it.next();
            // nothing to do at the moment 
        }
        
        for (Iterator it = model.getFaceletTags().iterator(); it.hasNext();)
        {
            FaceletTagMeta tag = (FaceletTagMeta) it.next();
            if (!tag.getModelId().equals(currModelId))
            {
                continue;
            }
            QdoxHelper.initFaceletTagHandlerAncestry(processedClasses, model, tag);            
        }

    }

    /**
     * Set the parentClassName and interfaceClassNames properties of the
     * provided modelItem object.
     */
    private void processClass(Map processedClasses, JavaClass clazz, Model model) throws MojoExecutionException
    {
        if (processedClasses.containsKey(clazz.getFullyQualifiedName()))
        {
            return;
        }

        // first, check that the parent type and all interfaces have been
        // processed.
        JavaClass parentClazz = clazz.getSuperJavaClass();
        if (parentClazz != null)
        {
            processClass(processedClasses, parentClazz, model);
        }

        JavaClass[] classes = clazz.getImplementedInterfaces();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass iclazz = classes[i];
            processClass(processedClasses, iclazz, model);
        }

        // ok, now we can mark this class as processed.
        processedClasses.put(clazz.getFullyQualifiedName(), clazz);

        log.info("processed class:" + clazz.getFullyQualifiedName());
        DocletTag tag;
        Annotation anno;
        WebConfigMeta webConfig = model.findWebConfigsByModelId(model.getModelId());
        boolean createWebConfig = false;
        if (webConfig == null)
        {
            createWebConfig = true;
            webConfig = new WebConfigMeta();
            webConfig.setModelId(model.getModelId());
        }
        //Web Config Params
        JavaField[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; ++i)
        {
            JavaField field = fields[i];
            tag = field.getTagByName(DOC_WEB_CONFIG_PARAM);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processWebConfigParam(props, (AbstractJavaEntity)tag.getContext(), 
                        clazz, field, webConfig);
            }
            anno = getAnnotation(field, DOC_WEB_CONFIG_PARAM);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processWebConfigParam(props, (AbstractJavaEntity)anno.getContext(),
                        clazz, field, webConfig);
            }
        }
        if (webConfig.webConfigParametersSize() > 0 && createWebConfig)
        {
            model.addWebConfig(webConfig);
        }
        // converters
        tag = clazz.getTagByName(DOC_CONVERTER, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processConverter(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_CONVERTER);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processConverter(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
        // validators
        tag = clazz.getTagByName(DOC_VALIDATOR, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processValidator(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_VALIDATOR);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processValidator(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
        // components
        tag = clazz.getTagByName(DOC_COMPONENT, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processComponent(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_COMPONENT);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processComponent(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
        //tag
        tag = clazz.getTagByName(DOC_TAG, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processTag(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_TAG);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processTag(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
        //facelet tagHandler
        tag = clazz.getTagByName(DOC_FACELET_TAG, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processFaceletTag(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_FACELET_TAG);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processFaceletTag(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }        
        anno = getAnnotation(clazz, DOC_FACELET_TAGS);
        if (anno != null)
        {
            Object jspProps = anno.getNamedParameter("tags");            
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processFaceletTag(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation ranno = (Annotation) jspPropsList.get(i);
                    Map props = ranno.getNamedParameterMap();
                    processFaceletTag(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
                }
            }
        }        
        // renderKit
        tag = clazz.getTagByName(DOC_RENDERKIT, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processRenderKit(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_RENDERKIT);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processRenderKit(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
        // renderer
        DocletTag [] tags = clazz.getTagsByName(DOC_RENDERER, false);
        for (int i = 0; i < tags.length; i++)
        {
            tag = tags[i];
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processRenderer(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
            }
        }
        anno = getAnnotation(clazz, DOC_RENDERER);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processRenderer(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_RENDERERS);
        if (anno != null)
        {
            Object jspProps = anno.getNamedParameter("renderers");
            
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processRenderer(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation ranno = (Annotation) jspPropsList.get(i);
                    
                    Map props = ranno.getNamedParameterMap();
                    processRenderer(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
                }
            }
        }
    }

    private Annotation getAnnotation(AbstractJavaEntity entity, String annoName)
    {
        Annotation[] annos = entity.getAnnotations();
        if (annos == null)
        {
            return null;
        }
        // String wanted = ANNOTATION_BASE + "." + annoName;
        for (int i = 0; i < annos.length; ++i)
        {
            Annotation thisAnno = annos[i];
            // Ideally, here we would check whether the fully-qualified name of
            // the annotation
            // class matches ANNOTATION_BASE + "." + annoName. However it
            // appears that qdox 1.6.3
            // does not correctly expand @Foo using the class import statements;
            // method
            // Annotation.getType.getJavaClass.getFullyQualifiedName still just
            // returns the short
            // class name. So for now, just check for the short name.
            String thisAnnoName = thisAnno.getType().getJavaClass().getName();
            
            //Make short name for recognizing, if returns long
            int containsPoint = thisAnnoName.lastIndexOf('.');
            if (containsPoint != -1)
            {
                thisAnnoName = thisAnnoName.substring(containsPoint+1);
            }
            if (thisAnnoName.equals(annoName))
            {
                return thisAnno;
            }
        }
        return null;
    }
    


    /**
     * Remove all leading whitespace and a quotemark if it exists.
     * <p>
     * Qdox comments like <code>foo val= "bar"</code> return a value with
     * leading whitespace and quotes, so remove them.
     */
    private String clean(Object srcObj)
    {
        if (srcObj == null)
        {
            return null;
        }

        String src = srcObj.toString();
        int start = 0;
        int end = src.length();
        
        if (end == 0)
        {
            return src;
        }
        
        if (src.equals("\"\""))
        {
            return "\"\"";
        }

        while (start <= end)
        {
            char c = src.charAt(start);
            if (!Character.isWhitespace(c) && (c != '"'))
            {
                break;
            }
            ++start;
        }
        while (end >= start)
        {
            char c = src.charAt(end - 1);
            if (!Character.isWhitespace(c) && (c != '"'))
            {
                break;
            }
            --end;
        }
        return src.substring(start, end);
    }

    /**
     * Get the named attribute from a doc-annotation.
     * 
     * Param clazz is the class the annotation is attached to; only used when
     * reporting errors.
     */
    private String getString(JavaClass clazz, String key, Map map, String dflt)
    {
        String val = clean(map.get(key));
        if (val != null)
        {
            return val;
        }
        else
        {
            return dflt;
        }
    }

    /**
     * Get the named attribute from a doc-annotation and convert to a boolean.
     * 
     * Param clazz is the class the annotation is attached to; only used when
     * reporting errors.
     */
    private Boolean getBoolean(JavaClass clazz, String key, Map map,
            Boolean dflt)
    {
        String val = clean(map.get(key));
        if (val == null)
        {
            return dflt;
        }
        // TODO: report problem if the value does not look like "true" or
        // "false",
        // rather than silently converting it to false.
        return Boolean.valueOf(val);
    }

    /**
     * Set the basic data on a ClassMeta.
     * <p>
     * There is one property not set here: the parentClassName. See method
     * initComponentAncestry for further details.
     */
    private void initClassMeta(Model model, JavaClass clazz,
            ClassMeta modelItem, String classNameOverride)
    {
        modelItem.setModelId(model.getModelId());
        modelItem.setSourceClassName(clazz.getFullyQualifiedName());
        JavaClass realParentClass = clazz.getSuperJavaClass();
        if (realParentClass != null)
        {
            String fqn = realParentClass.getFullyQualifiedName();
            
            if ((fqn != null) && !fqn.startsWith("java.lang"))
            {
                fqn = QdoxHelper.getFullyQualifiedClassName(clazz,fqn);
                modelItem.setSourceClassParentClassName(fqn);
            }
        }

        // JSF Entity class.
        if (StringUtils.isEmpty(classNameOverride))
        {
            modelItem.setClassName(clazz.getFullyQualifiedName());
        }
        else
        {
            modelItem.setClassName(classNameOverride);
        }

        // interfaces metadata is inherited from
        JavaClass[] classes = clazz.getImplementedInterfaces();
        List ifaceNames = new ArrayList();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass iclazz = classes[i];

            ComponentMeta ifaceComponent = model
                    .findComponentByClassName(iclazz.getFullyQualifiedName());
            if (ifaceComponent != null)
            {
                ifaceNames.add(ifaceComponent.getClassName());
            }
        }
        modelItem.setInterfaceClassNames(ifaceNames);
    }
    
    private void processWebConfigParam(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaField field, WebConfigMeta webConfig)
    {
        String longDescription = field.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
        
        String name = getString(clazz, "name", props, 
                QdoxHelper.evaluateParameterInitializationExpression(
                        field.getInitializationExpression()));
        String defaultValue = getString(clazz,"defaultValue",props,null);
        String expectedValues = getString(clazz,"expectedValues",props,null);
        String since = getString(clazz,"since",props,null);
        
        WebConfigParamMeta wcp = new WebConfigParamMeta();
        wcp.setName(name);
        wcp.setFieldName(field.getName());
        wcp.setSourceClassName(clazz.getFullyQualifiedName());
        wcp.setDefaultValue(defaultValue);
        wcp.setExpectedValues(expectedValues);
        wcp.setLongDescription(longDescription);
        wcp.setDescription(shortDescription);
        wcp.setSince(since);
        webConfig.addWebConfigParam(wcp);
    }
    
    private void processConverter(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);

        String converterIdDflt = null;
        JavaField fieldConverterId = clazz
                .getFieldByName("CONVERTER_ID");
        if (fieldConverterId != null)
        {
            String value = fieldConverterId.getInitializationExpression();
            converterIdDflt = clean(value.substring(value.indexOf('"')));
        }        
        String converterId = getString(clazz, "id", props, converterIdDflt);

        // Check for both "class" and "clazz" in order to support
        // doclet and real annotations.
        String classNameOverride = getString(clazz, "class", props, null);
        classNameOverride = getString(clazz,"clazz",props,classNameOverride);
        
        String componentName = getString(clazz, "name", props, null);
        String bodyContent = getString(clazz, "bodyContent", props, null);
        String tagClass = getString(clazz, "tagClass", props, null);
        String tagSuperclass = getString(clazz, "tagSuperclass", props, null);
        String serialuidtag = getString(clazz, "serialuidtag", props, null);
        Boolean configExcluded = getBoolean(clazz,"configExcluded",props,null);   

        ConverterMeta converter = new ConverterMeta();
        initClassMeta(model, clazz, converter, classNameOverride);
        converter.setName(componentName);
        converter.setBodyContent(bodyContent);
        converter.setTagClass(tagClass);
        converter.setTagSuperclass(tagSuperclass);
        converter.setConverterId(converterId);
        converter.setDescription(shortDescription);
        converter.setLongDescription(longDescription);
        converter.setSerialuidtag(serialuidtag);
        converter.setConfigExcluded(configExcluded);
        
        // Now here walk the component looking for property annotations.
        processComponentProperties(clazz, converter);
        
        model.addConverter(converter);
    }
        
    private void processValidator(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);

        String validatorIdDflt = null;
        JavaField fieldConverterId = clazz
                .getFieldByName("VALIDATOR_ID");
        if (fieldConverterId != null)
        {
            String value = fieldConverterId.getInitializationExpression();
            validatorIdDflt = clean(value.substring(value.indexOf('"')));
        }        
        String validatorId = getString(clazz, "id", props, validatorIdDflt);

        // Check for both "class" and "clazz" in order to support
        // doclet and real annotations.
        String classNameOverride = getString(clazz, "class", props, null);
        classNameOverride = getString(clazz,"clazz",props,classNameOverride);
        
        String componentName = getString(clazz, "name", props, null);
        String bodyContent = getString(clazz, "bodyContent", props, null);
        String tagClass = getString(clazz, "tagClass", props, null);        
        String tagSuperclass = getString(clazz, "tagSuperclass", props, null);
        String serialuidtag = getString(clazz, "serialuidtag", props, null);
        Boolean configExcluded = getBoolean(clazz,"configExcluded",props,null);   
        
        ValidatorMeta validator = new ValidatorMeta();
        initClassMeta(model, clazz, validator, classNameOverride);
        validator.setName(componentName);
        validator.setBodyContent(bodyContent);
        validator.setTagClass(tagClass);
        validator.setTagSuperclass(tagSuperclass);
        validator.setValidatorId(validatorId);
        validator.setDescription(shortDescription);
        validator.setLongDescription(longDescription);
        validator.setSerialuidtag(serialuidtag);
        validator.setConfigExcluded(configExcluded);
        
        // Now here walk the component looking for property annotations.
        processComponentProperties(clazz, validator);
        
        model.addValidator(validator);
    }
    
    private void processRenderKit(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {

        String renderKitId = getString(clazz, "renderKitId", props, null);
        String renderKitClass = getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        renderKitClass = getString(clazz,"clazz",props,renderKitClass);

        RenderKitMeta renderKit = model.findRenderKitById(renderKitId);
        
        if (renderKit == null)
        {
            renderKit = new RenderKitMeta();
            renderKit.setClassName(renderKitClass);
            renderKit.setRenderKitId(renderKitId);
            model.addRenderKit(renderKit);        
        }
        else
        {
            renderKit.setClassName(renderKitClass);
            renderKit.setRenderKitId(renderKitId);            
        }
    }
    
    private void processRenderer(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
   
        String renderKitId = getString(clazz, "renderKitId", props, null);
        String rendererClass = getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        rendererClass = getString(clazz,"clazz",props,rendererClass);
        
        String componentFamily = getString(clazz,"family", props,null);
        String rendererType = getString(clazz,"type", props,null);
        
        RenderKitMeta renderKit = model.findRenderKitById(renderKitId);
        
        if (renderKit == null)
        {
            renderKit = new RenderKitMeta();
            renderKit.setRenderKitId(renderKitId);
            model.addRenderKit(renderKit);            
        }

        RendererMeta renderer = new RendererMeta();
        renderer.setClassName(rendererClass);
        renderer.setDescription(shortDescription);
        renderer.setComponentFamily(componentFamily);
        renderer.setRendererType(rendererType);
        renderKit.addRenderer(renderer);
    }
    
    private void processTag(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model) throws MojoExecutionException
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);

        String tagName = getString(clazz, "name", props, null);
        String classNameOverride = getString(clazz, "class", props, null);
        classNameOverride = getString(clazz,"clazz",props,classNameOverride);
        
        String bodyContent = getString(clazz, "bodyContent", props, "JSP");
        String tagHandler = getString(clazz, "tagHandler", props, null);

        TagMeta tag = new TagMeta();
        initClassMeta(model, clazz, tag, classNameOverride);
        tag.setName(tagName);
        tag.setBodyContent(bodyContent);
        tag.setDescription(shortDescription);
        tag.setLongDescription(longDescription);
        tag.setTagHandler(tagHandler);
        
        processTagAttributes(clazz, tag);
        model.addTag(tag);
    }
    
    /**
     * @since 1.0.4
     */
    private void processFaceletTag(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model) throws MojoExecutionException
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
        
        longDescription = getString(clazz,"longDescription",props, longDescription);

        String tagName = getString(clazz, "name", props, null);
        String classNameOverride = getString(clazz, "class", props, null);
        classNameOverride = getString(clazz,"clazz",props,classNameOverride);
        
        String bodyContent = getString(clazz, "bodyContent", props, "JSP");
        String componentClass = getString(clazz, "componentClass", props, null);
        String tagClass = getString(clazz, "tagClass", props, null);
        String converterClass = getString(clazz, "converterClass", props, null);
        String validatorClass = getString(clazz, "validatorClass", props, null);

        FaceletTagMeta tag = new FaceletTagMeta();
        initClassMeta(model, clazz, tag, classNameOverride);
        tag.setName(tagName);
        tag.setBodyContent(bodyContent);
        tag.setDescription(shortDescription);
        tag.setLongDescription(longDescription);
        tag.setComponentClass(componentClass);
        tag.setTagClass(tagClass);
        tag.setConverterClass(converterClass);
        tag.setValidatorClass(validatorClass);
        
        processFaceletTagAttributes(clazz, tag);
        model.addFaceletTag(tag);
    }
    
    private void processComponent(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model) throws MojoExecutionException
    {
        String componentTypeDflt = null;
        JavaField fieldComponentType = clazz.getFieldByName("COMPONENT_TYPE");
        if (fieldComponentType != null)
        {
            componentTypeDflt = clean(fieldComponentType
                    .getInitializationExpression());
        }

        String componentTypeFamily = null;
        JavaField fieldComponentFamily = clazz
                .getFieldByName("COMPONENT_FAMILY");
        if (fieldComponentFamily != null)
        {
            componentTypeFamily = clean(fieldComponentFamily
                    .getInitializationExpression());
        }

        String rendererTypeDflt = null;
        JavaField fieldRendererType = clazz
                .getFieldByName("DEFAULT_RENDERER_TYPE");
        if (fieldRendererType != null)
        {
            rendererTypeDflt = clean(fieldRendererType
                    .getInitializationExpression());
        }

        String componentName = getString(clazz, "name", props, null);

        // Check for both "class" and "clazz" in order to support
        // doclet and real annotations.
        String classNameOverride = getString(clazz, "class", props, null);
        classNameOverride = getString(clazz,"clazz",props,classNameOverride);
                
        Boolean template = getBoolean(clazz,"template",props, null);
                
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);

        String bodyContent = getString(clazz, "bodyContent", props, null);
        
        String componentFamily = getString(clazz, "family", props,
                componentTypeFamily);
        String componentType = getString(clazz, "type", props,
                componentTypeDflt);
        String rendererType = getString(clazz, "defaultRendererType", props,
                rendererTypeDflt);
        Boolean canHaveChildren = getBoolean(clazz, "canHaveChildren", props, null);
        Boolean configExcluded = getBoolean(clazz,"configExcluded",props,null);        

        String tagClass = getString(clazz, "tagClass", props, null);
        String tagSuperclass = getString(clazz, "tagSuperclass", props, null);
        String tagHandler = getString(clazz, "tagHandler", props, null);
        String defaultEventName = getString(clazz, "defaultEventName", props, null);
        String serialuid = getString(clazz, "serialuid", props, null);
        String implementsValue = getString(clazz, "implements", props, null);
        implementsValue = getString(clazz, "implementz", props, implementsValue);
        
        Boolean composite = getBoolean(clazz, "composite", props, null);

        ComponentMeta component = new ComponentMeta();
        initClassMeta(model, clazz, component, classNameOverride);
        component.setName(componentName);
        component.setBodyContent(bodyContent);
        component.setDescription(shortDescription);
        component.setLongDescription(longDescription);
        component.setConfigExcluded(configExcluded);
        component.setType(componentType);
        component.setFamily(componentFamily);
        component.setRendererType(rendererType);
        component.setChildren(canHaveChildren);
        component.setSerialuid(serialuid);
        component.setImplements(implementsValue);
        component.setTemplate(template);
        component.setDefaultEventName(defaultEventName);
        if (defaultEventName != null)
        {
            component.setOverrideDefaultEventName(Boolean.TRUE);
        }
        JavaClass[] interfaces = clazz.getImplementedInterfaces();
        for (int i = 0; i < interfaces.length; ++i)
        {
            JavaClass iface = interfaces[i];
            if (iface.getFullyQualifiedName().equals(
                    "javax.faces.component.NamingContainer"))
            {
                component.setNamingContainer(Boolean.TRUE);
                break;
            }
            if (iface.getFullyQualifiedName().equals(
                    "javax.faces.component.behavior.ClientBehaviorHolder"))
            {
                component.setClientBehaviorHolder(Boolean.TRUE);
                break;
            }
            if (!(template != null && template.booleanValue()))
            {
                component.addImplementedInterfaceClassName(
                        QdoxHelper.getFullyQualifiedClassName(iface, iface.getFullyQualifiedName()));
            }
        }
        if (implementsValue != null)
        {
            if (StringUtils.contains(implementsValue, "javax.faces.component.behavior.ClientBehaviorHolder"))
            {
                component.setClientBehaviorHolder(Boolean.TRUE);
            }
            StringTokenizer st = new StringTokenizer(implementsValue,",");
            while (st.hasMoreTokens())
            {
                component.addImplementedInterfaceClassName(st.nextToken());
            }
        }
        component.setTagClass(tagClass);
        component.setTagSuperclass(tagSuperclass);
        component.setTagHandler(tagHandler);
        component.setComposite(composite);

        // Now here walk the component looking for property annotations.
        processComponentProperties(clazz, component);
        processComponentFacets(clazz, component);
        processComponentListeners(clazz, component);

        model.addComponent(component);
    }

    private void processTagAttributes(JavaClass clazz,
            TagMeta ctag)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_JSP_ATTRIBUTE);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, ctag);
            }

            Annotation anno = getAnnotation(method, DOC_JSP_ATTRIBUTE);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processTagAttribute(props, (AbstractJavaEntity)anno.getContext(), clazz,
                        method, ctag);
            }
        }
        
        DocletTag[] jspProperties = clazz.getTagsByName(DOC_JSP_ATTRIBUTE);
        for (int i = 0; i < jspProperties.length; ++i)
        {
            //We have here only doclets, because this part is only for
            //solve problems with binding property on 1.1
            DocletTag tag = jspProperties[i];
            
            Map props = tag.getNamedParameterMap();
            processTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz,
                    ctag);
            
        }                
    }
    
    /**
     * @since 1.0.4
     */
    private void processFaceletTagAttributes(JavaClass clazz,
            FaceletTagMeta ctag)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_FACELET_TAG_ATTRIBUTE);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, ctag);
            }

            Annotation anno = getAnnotation(method, DOC_FACELET_TAG_ATTRIBUTE);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)anno.getContext(), clazz,
                        method, ctag);
            }
        }
        
        JavaField[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; ++i)
        {
            JavaField field = fields[i];
            DocletTag tag = field.getTagByName(DOC_FACELET_TAG_ATTRIBUTE);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz, field, ctag);
            }

            Annotation anno = getAnnotation(field, DOC_FACELET_TAG_ATTRIBUTE);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)anno.getContext(), clazz, field, ctag);
            }
        }
        
        DocletTag[] jspProperties = clazz.getTagsByName(DOC_FACELET_TAG_ATTRIBUTE);
        for (int i = 0; i < jspProperties.length; ++i)
        {
            //We have here only doclets, because this part is only for
            //solve problems with binding property on 1.1
            DocletTag tag = jspProperties[i];
            
            Map props = tag.getNamedParameterMap();
            processFaceletTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz,
                    ctag);
            
        }
        
        Annotation jspPropertyAnno = getAnnotation(clazz, DOC_FACELET_TAG_ATTRIBUTE);
        if (jspPropertyAnno != null)
        {
            Map props = jspPropertyAnno.getNamedParameterMap();
            processFaceletTagAttribute(props, (AbstractJavaEntity)jspPropertyAnno.getContext(),
                    clazz, ctag);
        }
        
        Annotation jspAnno = getAnnotation(clazz, DOC_FACELET_TAG_ATTRIBUTES);        
        if (jspAnno != null)
        {
            Object jspProps = jspAnno.getNamedParameter("attributes");
            
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                        ctag);
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation anno = (Annotation) jspPropsList.get(i);

                    Map props = anno.getNamedParameterMap();
                    processFaceletTagAttribute(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                            ctag);                    
                }
            }
        }
    }
    
    private void processTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, TagMeta tag)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
                
        Type returnType = null;
        
        if (method.getName().startsWith("set"))
        {
            returnType = method.getParameters()[0].getType();
        }
        else
        {
            returnType = method.getReturns();
        }

        String fullyQualifiedReturnType = returnType.getJavaClass().getFullyQualifiedName();
        
        fullyQualifiedReturnType = QdoxHelper.getFullyQualifiedClassName(clazz,fullyQualifiedReturnType);
        
        if (returnType.isArray() && (fullyQualifiedReturnType.indexOf('[') == -1))
        {
            for (int i = 0; i < returnType.getDimensions();i++)
            {
                fullyQualifiedReturnType = fullyQualifiedReturnType + "[]";
            }
        }
                
        String className = getString(clazz,"className",props, fullyQualifiedReturnType);
        String deferredValueType = getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = getBoolean(clazz, "exclude", props, null);
        
        AttributeMeta a = new AttributeMeta();
        a.setName(QdoxHelper.methodToPropName(method.getName()));
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }
    
    private void processTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, TagMeta tag)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = getString(clazz, "longDescription", props, null);
        String descDflt = longDescription;
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
                
        String name = getString(clazz, "name", props, null);
        String className = getString(clazz, "className", props, null);
        String deferredValueType = getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = getBoolean(clazz, "exclude", props, null);
                
        AttributeMeta a = new AttributeMeta();
        a.setName(name);
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }

    /**
     * @since 1.0.4
     */
    private void processFaceletTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, FaceletTagMeta tag)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
                
        Type returnType = null;
        
        if (method.getName().startsWith("set"))
        {
            returnType = method.getParameters()[0].getType();
        }
        else
        {
            returnType = method.getReturns();
        }

        String fullyQualifiedReturnType = returnType.getJavaClass().getFullyQualifiedName();
        
        fullyQualifiedReturnType = QdoxHelper.getFullyQualifiedClassName(clazz,fullyQualifiedReturnType);
        
        if (returnType.isArray() && (fullyQualifiedReturnType.indexOf('[') == -1))
        {
            for (int i = 0; i < returnType.getDimensions();i++)
            {
                fullyQualifiedReturnType = fullyQualifiedReturnType + "[]";
            }
        }
                
        String className = getString(clazz,"className",props, fullyQualifiedReturnType);
        String deferredValueType = getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = getBoolean(clazz, "exclude", props, null);
        
        AttributeMeta a = new AttributeMeta();
        a.setName(QdoxHelper.methodToPropName(method.getName()));
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }

    /**
     * @since 1.0.4
     */
    private void processFaceletTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, FaceletTagMeta tag)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = getString(clazz, "longDescription", props, null);
        String descDflt = longDescription;
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
                
        String name = getString(clazz, "name", props, null);
        String className = getString(clazz, "className", props, null);
        String deferredValueType = getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = getBoolean(clazz, "exclude", props, null);
                
        AttributeMeta a = new AttributeMeta();
        a.setName(name);
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }

    /**
     * @since 1.0.4
     */
    private void processFaceletTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaField field, FaceletTagMeta tag)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
                
        String name = getString(clazz, "name", props, field.getName());
        String className = getString(clazz, "className", props, null);
        String deferredValueType = getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = getBoolean(clazz, "exclude", props, null);
                
        AttributeMeta a = new AttributeMeta();
        a.setName(name);
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }
        
    /**
     * Look for any methods on the specified class that are annotated as being
     * component properties, and add metadata about them to the model.
     */
    private void processComponentProperties(JavaClass clazz,
            PropertyHolder component)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_PROPERTY);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processComponentProperty(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = getAnnotation(method, DOC_PROPERTY);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentProperty(props, (AbstractJavaEntity)anno.getContext(), clazz,
                        method, component);
            }
        }
        
        Type [] interfaces = clazz.getImplements();
        
        //Scan interfaces for properties to be added to this component
        //This feature allow us to have groups of functions.
        for (int i = 0; i < interfaces.length;++i)
        {
            JavaClass intf = interfaces[i].getJavaClass();

            //If the interfaces has a JSFComponent Doclet,
            //this is managed in other way
            if (intf.getTagByName(DOC_COMPONENT, false) == null)
            {
                JavaMethod[] intfmethods = intf.getMethods();
                for (int j = 0; j < intfmethods.length; ++j)
                {
                    JavaMethod intfmethod = intfmethods[j];

                    DocletTag tag = intfmethod.getTagByName(DOC_PROPERTY);
                    if (tag != null)
                    {
                        Map props = tag.getNamedParameterMap();
                        processInterfaceComponentProperty(props, (AbstractJavaEntity)tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = getAnnotation(intfmethod, DOC_PROPERTY);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentProperty(props, (AbstractJavaEntity)anno.getContext(),
                                clazz, intfmethod, component);
                    }
                }
            }
        }

        //Scan for properties defined only on jsp (special case on myfaces 1.1,
        //this feature should not be used on typical situations)
        DocletTag[] jspProperties = clazz.getTagsByName(DOC_JSP_PROPERTY);
        for (int i = 0; i < jspProperties.length; ++i)
        {
            //We have here only doclets, because this part is only for
            //solve problems with binding property on 1.1
            DocletTag tag = jspProperties[i];
            
            Map props = tag.getNamedParameterMap();
            processComponentJspProperty(props, (AbstractJavaEntity)tag.getContext(), clazz,
                    component);
        }
        
        Annotation jspPropertyAnno = getAnnotation(clazz, DOC_JSP_PROPERTY);
        if (jspPropertyAnno != null)
        {
            Map props = jspPropertyAnno.getNamedParameterMap();
            processComponentJspProperty(props, (AbstractJavaEntity)jspPropertyAnno.getContext(),
                    clazz, component);
        }
        
        
        Annotation jspAnno = getAnnotation(clazz, DOC_JSP_PROPERTIES);        
        if (jspAnno != null)
        {
            Object jspProps = jspAnno.getNamedParameter("properties");
            
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processComponentJspProperty(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                        component);               
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation anno = (Annotation) jspPropsList.get(i);

                    Map props = anno.getNamedParameterMap();
                    processComponentJspProperty(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                            component);                    
                }
            }
            
        }
    }
        
    private void processComponentFacets(JavaClass clazz,
            FacetHolder component)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_FACET);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processComponentFacet(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = getAnnotation(method, DOC_FACET);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentFacet(props, (AbstractJavaEntity)anno.getContext(), clazz,
                        method, component);
            }
        }
        
        Type [] interfaces = clazz.getImplements();
        
        //Scan interfaces for properties to be added to this component
        //This feature allow us to have groups of functions.
        for (int i = 0; i < interfaces.length;++i)
        {
            JavaClass intf = interfaces[i].getJavaClass();

            //If the interfaces has a JSFComponent Doclet,
            //this is managed in other way
            if (intf.getTagByName(DOC_COMPONENT, false) == null)
            {
                JavaMethod[] intfmethods = intf.getMethods();
                for (int j = 0; j < intfmethods.length; ++j)
                {
                    JavaMethod intfmethod = intfmethods[j];

                    DocletTag tag = intfmethod.getTagByName(DOC_FACET);
                    if (tag != null)
                    {
                        Map props = tag.getNamedParameterMap();
                        processInterfaceComponentFacet(props, (AbstractJavaEntity)tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = getAnnotation(intfmethod, DOC_FACET);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentFacet(props, (AbstractJavaEntity)anno.getContext(),
                                clazz, intfmethod, component);
                    }
                }
            }
        }
    }

    /**
     * @since 1.0.4
     */
    private void processComponentListeners(JavaClass clazz,
            ListenerHolder component)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_LISTENER);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processComponentListener(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = getAnnotation(method, DOC_LISTENER);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentListener(props, (AbstractJavaEntity)anno.getContext(), clazz,
                        method, component);
            }
        }
        
        Type [] interfaces = clazz.getImplements();
        
        //Scan interfaces for properties to be added to this component
        //This feature allow us to have groups of functions.
        for (int i = 0; i < interfaces.length;++i)
        {
            JavaClass intf = interfaces[i].getJavaClass();

            //If the interfaces has a JSFComponent Doclet,
            //this is managed in other way
            if (intf.getTagByName(DOC_COMPONENT, false) == null)
            {
                JavaMethod[] intfmethods = intf.getMethods();
                for (int j = 0; j < intfmethods.length; ++j)
                {
                    JavaMethod intfmethod = intfmethods[j];

                    DocletTag tag = intfmethod.getTagByName(DOC_LISTENER);
                    if (tag != null)
                    {
                        Map props = tag.getNamedParameterMap();
                        processInterfaceComponentListener(props, (AbstractJavaEntity)tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = getAnnotation(intfmethod, DOC_LISTENER);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentListener(props, (AbstractJavaEntity)anno.getContext(),
                                clazz, intfmethod, component);
                    }
                }
            }
        }
    }
        
    private void processInterfaceComponentProperty(Map props, AbstractJavaEntity ctx,
    JavaClass clazz, JavaMethod method, PropertyHolder component)
    {
        this.processComponentProperty(props, ctx, clazz, method, component);
        
        PropertyMeta property = component.getProperty(QdoxHelper.methodToPropName(method.getName()));
        
        //Try to get the method from the component clazz to see if this
        //has an implementation
        JavaMethod clazzMethod = clazz.getMethodBySignature(method.getName(), null , false);
        
        if (clazzMethod == null)
        {
            //The method should be generated!
            property.setGenerated(Boolean.TRUE);
        }            
    }

    private void processInterfaceComponentFacet(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, FacetHolder component)
    {
        this.processComponentFacet(props, ctx, clazz, method, component);
                
        FacetMeta facet = component.getFacet(QdoxHelper.methodToPropName(method.getName()));
                
        //Try to get the method from the component clazz to see if this
        //has an implementation
        JavaMethod clazzMethod = clazz.getMethodBySignature(method.getName(), null , false);
                
        if (clazzMethod == null)
        {
            //The method should be generated!
            facet.setGenerated(Boolean.TRUE);
        }            
    }
    
    /**
     * @since 1.0.4
     */
    private void processInterfaceComponentListener(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, ListenerHolder component)
    {
        this.processComponentListener(props, ctx, clazz, method, component);
                
        ListenerMeta listener = component.getListener(QdoxHelper.methodToPropName(method.getName()));
                
        //Try to get the method from the component clazz to see if this
        //has an implementation
        JavaMethod clazzMethod = clazz.getMethodBySignature(method.getName(), null , false);
                
        if (clazzMethod == null)
        {
            //The method should be generated!
            listener.setGenerated(Boolean.TRUE);
        }            
    }
    
    private void processComponentProperty(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, PropertyHolder component)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean transientProp = getBoolean(clazz, "transient", props, null);
        transientProp = getBoolean(clazz, "istransient", props, transientProp);
        Boolean stateHolder = getBoolean(clazz, "stateHolder", props, null);
        Boolean partialStateHolder = getBoolean(clazz, "partialStateHolder", props, null);
        Boolean literalOnly = getBoolean(clazz, "literalOnly", props, null);
        Boolean tagExcluded = getBoolean(clazz, "tagExcluded", props, null);
        Boolean localMethod = getBoolean(clazz, "localMethod",props,null);
        Boolean setMethod = getBoolean(clazz, "setMethod",props,null);
        String localMethodScope = getString(clazz, "localMethodScope",props,null);
        String setMethodScope = getString(clazz, "setMethodScope",props,null);
        Boolean inheritedTag = getBoolean(clazz, "inheritedTag",props,null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
        String returnSignature = getString(clazz, "returnSignature", props, null);
        String methodSignature = getString(clazz, "methodSignature", props, null);
        String defaultValue = getString(clazz,"defaultValue",props,null);
        String jspName = getString(clazz,"jspName",props,null);
        Boolean rtexprvalue = getBoolean(clazz, "rtexprvalue",props,null);
        String clientEvent = getString(clazz, "clientEvent",props,null);
        String deferredValueType = getString(clazz, "deferredValueType", props, null);

        Type returnType = null;
        
        if (method.getName().startsWith("set"))
        {
            returnType = method.getParameters()[0].getType();
        }
        else
        {
            returnType = method.getReturns();
        }
        
        String fullyQualifiedReturnType = returnType.getJavaClass().getFullyQualifiedName();
        
        fullyQualifiedReturnType = QdoxHelper.getFullyQualifiedClassName(clazz, fullyQualifiedReturnType);
        
        if (returnType.isArray() && (fullyQualifiedReturnType.indexOf('[') == -1))
        {
            for (int i = 0; i < returnType.getDimensions();i++)
            {
                fullyQualifiedReturnType = fullyQualifiedReturnType + "[]";
            }
        }
        
        PropertyMeta p = new PropertyMeta();
        p.setName(QdoxHelper.methodToPropName(method.getName()));
        p.setClassName(fullyQualifiedReturnType);
        p.setRequired(required);
        p.setTransient(transientProp);
        p.setStateHolder(stateHolder);
        p.setPartialStateHolder(partialStateHolder);
        p.setLiteralOnly(literalOnly);
        p.setTagExcluded(tagExcluded);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        p.setDefaultValue(defaultValue);
        p.setLocalMethod(localMethod);
        p.setLocalMethodScope(localMethodScope);
        p.setSetMethod(setMethod);
        p.setSetMethodScope(setMethodScope);
        p.setJspName(jspName);
        p.setRtexprvalue(rtexprvalue);
        p.setDeferredValueType(deferredValueType);
        p.setClientEvent(clientEvent);
        p.setInheritedTag(inheritedTag);
        
        if (returnSignature != null)
        {
            MethodSignatureMeta signature = new MethodSignatureMeta();
            signature.setReturnType(returnSignature);
            
            if (methodSignature != null)
            {
                String[] params = StringUtils.split(methodSignature,',');
                
                if (params != null)
                {
                    for (int i = 0; i < params.length; i++)
                    {
                        signature.addParameterType(params[i].trim());
                    }
                }
            }
            p.setMethodBindingSignature(signature);
        }
        
        //If the method is abstract this should be generated
        if (method.isAbstract())
        {
            p.setGenerated(Boolean.TRUE);
        }

        component.addProperty(p);
    }
    
    private void processComponentFacet(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, FacetHolder component)
    {
        Boolean required = getBoolean(clazz, "required", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
        
        FacetMeta p = new FacetMeta();
        p.setName(QdoxHelper.methodToPropName(method.getName()));
        p.setRequired(required);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        
        //If the method is abstract this should be generated
        if (method.isAbstract())
        {
            p.setGenerated(Boolean.TRUE);
        }

        component.addFacet(p);
    }
    
    /**
     * @since 1.0.4
     */
    private void processComponentListener(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, ListenerHolder component)
    {
        Boolean required = getBoolean(clazz, "required", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
        
        Type returnType = null;
        
        if (method.getName().startsWith("set"))
        {
            returnType = method.getParameters()[0].getType();
        }
        else
        {
            returnType = method.getReturns();
        }
        
        String fullyQualifiedReturnType = returnType.getJavaClass().getFullyQualifiedName();
        fullyQualifiedReturnType = QdoxHelper.getFullyQualifiedClassName(clazz, fullyQualifiedReturnType);
        fullyQualifiedReturnType = getString(clazz, "clazz", props, fullyQualifiedReturnType);
        
        String phases = getString(clazz, "phases", props, null);
        String eventClassName = getString(clazz, "event", props, null);
        String name = getString(clazz, "name", props, QdoxHelper.methodToPropName(method.getName()));
        
        ListenerMeta p = new ListenerMeta();
        p.setName(name);
        p.setClassName(fullyQualifiedReturnType);
        p.setEventClassName(eventClassName);
        p.setRequired(required);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        p.setPhases(phases);
        
        //If the method is abstract this should be generated
        if (method.isAbstract())
        {
            p.setGenerated(Boolean.TRUE);
        }

        component.addListener(p);
    }
    
    private void processComponentJspProperty(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, PropertyHolder component)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean transientProp = getBoolean(clazz, "transient", props, null);
        Boolean stateHolder = getBoolean(clazz, "stateHolder", props, null);
        Boolean literalOnly = getBoolean(clazz, "literalOnly", props, null);
        Boolean tagExcluded = getBoolean(clazz, "tagExcluded", props, null);
        Boolean inheritedTag = getBoolean(clazz, "inheritedTag", props, null);

        String longDescription = getString(clazz, "longDesc", props, null);
        
        String descDflt = longDescription;
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
        String returnType = getString(clazz, "returnType", props, null);
        String name = getString(clazz, "name", props, null);
        
        PropertyMeta p = new PropertyMeta();
        p.setName(name);
        p.setClassName(returnType);
        p.setRequired(required);
        p.setTransient(transientProp);
        p.setStateHolder(stateHolder);
        p.setLiteralOnly(literalOnly);
        p.setTagExcluded(tagExcluded);
        p.setInheritedTag(inheritedTag);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        p.setGenerated(Boolean.FALSE);
        component.addProperty(p);
    }
}