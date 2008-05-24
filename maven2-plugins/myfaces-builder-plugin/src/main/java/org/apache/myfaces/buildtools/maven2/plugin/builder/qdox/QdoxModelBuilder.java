package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelBuilder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.AttributeMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ClassMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.MethodSignatureMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RenderKitMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RendererMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.TagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;

import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;

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

    private static final String DOC_PROPERTY = "JSFProperty";
    private static final String DOC_FACET = "JSFFacet";   
    
    //This property is used in special cases where properties 
    //does not have methods defined on component class, like binding
    //in jsf 1.1 (in 1.2 has component counterpart). In fact, all
    //properties must be defined with JSFProperty
    private static final String DOC_JSP_PROPERTY = "JSFJspProperty";
    
    private static final String DOC_TAG = "JSFJspTag";
    private static final String DOC_JSP_ATTRIBUTE = "JSFJspAttribute";

    private static final String ANNOTATION_BASE = "org.apache.myfaces.buildtools.annotation";

    private static class JavaClassComparator implements Comparator
    {
        public int compare(Object arg0, Object arg1)
        {
            JavaClass c0 = (JavaClass) arg0;
            JavaClass c1 = (JavaClass) arg1;

            return (c0.getName().compareTo(c1.getName()));
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
     * Scan the source tree for doc-annotations, and build Model objects
     * containing info extracted from the doc-annotation attributes and
     * introspected info about the item the annotation is attached to.
     */
    public void buildModel(Model model, List sourceDirs)
            throws MojoExecutionException
    {
        JavaDocBuilder builder = new JavaDocBuilder();

        // need a File object representing the original source tree
        for (Iterator i = sourceDirs.iterator(); i.hasNext();)
        {
            String srcDir = (String) i.next();
            builder.addSourceTree(new File(srcDir));
        }

        JavaClass[] classes = builder.getClasses();

        // Sort the class array so that they are processed in a
        // predictable order, regardless of how the source scanning
        // returned them.
        Arrays.sort(classes, new JavaClassComparator());
        Set processedClasses = new HashSet();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass clazz = classes[i];
            processClass(processedClasses, clazz, model);
        }
        
        for (Iterator it = model.getComponents().iterator(); it.hasNext();)
        {
            ComponentMeta component = (ComponentMeta) it.next();
            component.setModelId(model.getModelId());
            //Check if the component class file exists
            if (!IOUtils.existsSourceFile(StringUtils.replace(
                    component.getClassName(),".","/")+".java", sourceDirs)){
                component.setGeneratedComponentClass(Boolean.TRUE);
            }
            //Check if the component tag class file exists
            if (component.getTagClass() != null && 
                    !IOUtils.existsSourceFile(StringUtils.replace(
                    component.getTagClass(),".","/")+".java", sourceDirs)){
                component.setGeneratedTagClass(Boolean.TRUE);
            }            
        }
        for (Iterator it = model.getConverters().iterator(); it.hasNext();)
        {
            ConverterMeta converter = (ConverterMeta) it.next();
            converter.setModelId(model.getModelId());
        }
        for (Iterator it = model.getValidators().iterator(); it.hasNext();)
        {
            ValidatorMeta validator = (ValidatorMeta) it.next();
            validator.setModelId(model.getModelId());
        }
        for (Iterator it = model.getTags().iterator(); it.hasNext();)
        {
            TagMeta tag = (TagMeta) it.next();
            tag.setModelId(model.getModelId());
        }       
    }

    /**
     * Set the parentClassName and interfaceClassNames properties of the
     * provided modelItem object.
     */
    private void processClass(Set processedClasses, JavaClass clazz, Model model)
            throws MojoExecutionException
    {
        if (processedClasses.contains(clazz))
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
        processedClasses.add(clazz);

        log.info("processed class:" + clazz.getName());

        DocletTag tag;
        Annotation anno;

        // converters
        tag = clazz.getTagByName(DOC_CONVERTER, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processConverter(props, tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_CONVERTER);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processConverter(props, anno.getContext(), clazz, model);
        }

        // validators
        tag = clazz.getTagByName(DOC_VALIDATOR, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processValidator(props, tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_VALIDATOR);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processValidator(props, anno.getContext(), clazz, model);
        }

        // components
        tag = clazz.getTagByName(DOC_COMPONENT, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processComponent(props, tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_COMPONENT);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processComponent(props, anno.getContext(), clazz, model);
        }
        
        //tag
        tag = clazz.getTagByName(DOC_TAG, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processTag(props, tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_TAG);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processTag(props, anno.getContext(), clazz, model);
        }
        
        // renderKit
        tag = clazz.getTagByName(DOC_RENDERKIT, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processRenderKit(props, tag.getContext(), clazz, model);
        }
        anno = getAnnotation(clazz, DOC_RENDERKIT);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processRenderKit(props, anno.getContext(), clazz, model);
        }
                
        // renderer
        
        DocletTag [] tags = clazz.getTagsByName(DOC_RENDERER, false);
        
        for (int i = 0; i < tags.length; i++)
        {
            tag = tags[i];
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processRenderer(props, tag.getContext(), clazz, model);
            }
            anno = getAnnotation(clazz, DOC_RENDERER);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processRenderer(props, anno.getContext(), clazz, model);
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
            return src;
        
        if (src.equals("\"\""))
            return "\"\"";

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
     * Set the parentClassName and interfaceClassNames properties of the
     * provided modelItem object.
     */
    private void initAncestry(Model model, JavaClass clazz, ClassMeta modelItem)
    {
        // self
        modelItem.setClassName(clazz.getName());

        // parent
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            ComponentMeta parentComponent = model
                    .findComponentByClassName(parentClazz.getName());
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }

        // interfaces
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

    private void processConverter(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = getFirstSentence(longDescription);
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
        String converterClass = getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        
        String componentName = getString(clazz, "name", props, null);
        String bodyContent = getString(clazz, "bodyContent", props, null);
        String tagClass = getString(clazz, "tagClass", props, null);

        ConverterMeta converter = new ConverterMeta();
        converter.setName(componentName);
        converter.setBodyContent(bodyContent);
        converter.setTagClass(tagClass);
        converter.setClassSource(clazz.getFullyQualifiedName());
        converter.setClassName(converterClass);
        converter.setConverterId(converterId);
        converter.setDescription(shortDescription);
        converter.setLongDescription(longDescription);
        
        // Now here walk the component looking for property annotations.
        processComponentProperties(clazz, converter);
        
        model.addConverter(converter);
    }
        
    private void processValidator(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = getFirstSentence(longDescription);
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
        String validatorClass = getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        
        String componentName = getString(clazz, "name", props, null);
        String bodyContent = getString(clazz, "bodyContent", props, null);
        String tagClass = getString(clazz, "tagClass", props, null);        

        ValidatorMeta validator = new ValidatorMeta();
        validator.setName(componentName);
        validator.setBodyContent(bodyContent);
        validator.setTagClass(tagClass);
        validator.setClassSource(clazz.getFullyQualifiedName());
        validator.setClassName(validatorClass);
        validator.setValidatorId(validatorId);
        validator.setDescription(shortDescription);
        validator.setLongDescription(longDescription);
        
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
        String descDflt = getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
   
        String renderKitId = getString(clazz, "renderKitId", props, null);
        String rendererClass = getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
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
        String descDflt = getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);

        String tagName = getString(clazz, "name", props, null);
        String tagClass = getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        
        String bodyContent = getString(clazz, "bodyContent", props, "JSP");

        TagMeta tag = new TagMeta();
        tag.setName(tagName);
        tag.setClassName(tagClass);
        tag.setBodyContent(bodyContent);
        tag.setDescription(shortDescription);
        tag.setLongDescription(longDescription);
        
        processTagAttributes(clazz, tag);
        model.addTag(tag);
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
        String componentClass = getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        
        String componentParentClass = getString(clazz, "parent", props, 
                clazz.getSuperJavaClass()!= null?
                        clazz.getSuperJavaClass().getFullyQualifiedName():null);
        
        String superClassName = getString(clazz,"superClass",props,null);
        
        
        
        if (componentParentClass != null && componentParentClass.startsWith("java.lang"))
        {
            componentParentClass = null;
        }
        
        if (componentParentClass != null)
        {
            if (componentParentClass.equals(""))
            {
                componentParentClass = null;
            }
        }
                
        String longDescription = clazz.getComment();
        String descDflt = getFirstSentence(longDescription);
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
        String serialuid = getString(clazz, "serialuid", props, null);
        String implementsValue = getString(clazz, "implements", props, null);

        ComponentMeta component = new ComponentMeta();
        initAncestry(model, clazz, component);
        component.setName(componentName);
        component.setBodyContent(bodyContent);
        component.setClassName(componentClass);
        component.setClassSource(clazz.getFullyQualifiedName());
        component.setParentClassName(componentParentClass);
        component.setSuperClassName(superClassName);
        component.setDescription(shortDescription);
        component.setLongDescription(longDescription);
        component.setConfigExcluded(configExcluded);
        component.setType(componentType);
        component.setFamily(componentFamily);
        component.setRendererType(rendererType);
        component.setChildren(canHaveChildren);
        component.setSerialuid(serialuid);
        component.setImplements(implementsValue);
        
        JavaClass[] interfaces = clazz.getImplementedInterfaces();
        for (int i = 0; i < interfaces.length; ++i)
        {
            JavaClass iface = interfaces[i];
            if (iface.getName().equals("javax.faces.component.NamingContainer"))
            {
                component.setNamingContainer(Boolean.TRUE);
                break;
            }
        }

        component.setTagClass(tagClass);
        component.setTagSuperclass(tagSuperclass);
        component.setTagHandler(tagHandler);

        // Now here walk the component looking for property annotations.
        processComponentProperties(clazz, component);
        processComponentFacets(clazz, component);

        validateComponent(component);
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
                processTagAttribute(props, tag.getContext(), clazz,
                        method, ctag);
            }

            Annotation anno = getAnnotation(method, DOC_JSP_ATTRIBUTE);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processTagAttribute(props, anno.getContext(), clazz,
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
            processTagAttribute(props, tag.getContext(), clazz,
                    ctag);
            
        }                
    }
    
    private void processTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, TagMeta tag)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = ctx.getComment();
        String descDflt = getFirstSentence(longDescription);
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
        
        AttributeMeta a = new AttributeMeta();
        a.setName(methodToPropName(method.getName()));
        a.setClassName(returnType.toString());
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        
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
                
        String name = getString(clazz, "name", props, descDflt);
        String className = getString(clazz, "className", props, descDflt);
                
        AttributeMeta a = new AttributeMeta();
        a.setName(name);
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        
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
                processComponentProperty(props, tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = getAnnotation(method, DOC_PROPERTY);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentProperty(props, anno.getContext(), clazz,
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
                        processInterfaceComponentProperty(props, tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = getAnnotation(intfmethod, DOC_PROPERTY);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentProperty(props, anno.getContext(),
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
            processComponentJspProperty(props, tag.getContext(), clazz,
                    component);
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
                processComponentFacet(props, tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = getAnnotation(method, DOC_FACET);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentFacet(props, anno.getContext(), clazz,
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
                        processInterfaceComponentFacet(props, tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = getAnnotation(intfmethod, DOC_FACET);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentFacet(props, anno.getContext(),
                                clazz, intfmethod, component);
                    }
                }
            }
        }
    }
        
    private void processInterfaceComponentProperty(Map props, AbstractJavaEntity ctx,
    JavaClass clazz, JavaMethod method, PropertyHolder component){
        this.processComponentProperty(props, ctx, clazz, method, component);
        
        PropertyMeta property = component.getProperty(methodToPropName(method.getName()));
        
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
            JavaClass clazz, JavaMethod method, FacetHolder component){
                this.processComponentFacet(props, ctx, clazz, method, component);
                
                FacetMeta facet = component.getFacet(methodToPropName(method.getName()));
                
                //Try to get the method from the component clazz to see if this
                //has an implementation
                JavaMethod clazzMethod = clazz.getMethodBySignature(method.getName(), null , false);
                
                if (clazzMethod == null)
                {
                    //The method should be generated!
                    facet.setGenerated(Boolean.TRUE);
                }            
            }
    
    private void processComponentProperty(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, PropertyHolder component)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean transientProp = getBoolean(clazz, "transient", props, null);
        Boolean stateHolder = getBoolean(clazz, "stateHolder", props, null);
        Boolean literalOnly = getBoolean(clazz, "literalOnly", props, null);
        Boolean tagExcluded = getBoolean(clazz, "tagExcluded", props, null);
        Boolean localMethod = getBoolean(clazz, "localMethod",props,null);
        Boolean setMethod = getBoolean(clazz, "setMethod",props,null);
        String localMethodScope = getString(clazz, "localMethodScope",props,null);
        String setMethodScope = getString(clazz, "setMethodScope",props,null);
        Boolean inheritedTag = getBoolean(clazz, "inheritedTag",props,null);

        String longDescription = ctx.getComment();
        String descDflt = getFirstSentence(longDescription);
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

        Type returnType = null;
        
        if (method.getName().startsWith("set"))
        {
            returnType = method.getParameters()[0].getType();
        }
        else
        {
            returnType = method.getReturns();
        }
        
        
        PropertyMeta p = new PropertyMeta();
        p.setName(methodToPropName(method.getName()));
        p.setClassName(returnType.toString());
        p.setRequired(required);
        p.setTransient(transientProp);
        p.setStateHolder(stateHolder);
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
        if (method.isAbstract()){
            p.setGenerated(Boolean.TRUE);
        }

        component.addProperty(p);
    }
    
    private void processComponentFacet(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, FacetHolder component)
    {
        Boolean required = getBoolean(clazz, "required", props, null);

        String longDescription = ctx.getComment();
        String descDflt = getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = getString(clazz, "desc", props, descDflt);
        
        FacetMeta p = new FacetMeta();
        p.setName(methodToPropName(method.getName()));
        p.setRequired(required);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        
        //If the method is abstract this should be generated
        if (method.isAbstract()){
            p.setGenerated(Boolean.TRUE);
        }

        component.addFacet(p);
    }
    
    
    private void processComponentJspProperty(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, PropertyHolder component)
    {
        Boolean required = getBoolean(clazz, "required", props, null);
        Boolean transientProp = getBoolean(clazz, "transient", props, null);
        Boolean stateHolder = getBoolean(clazz, "stateHolder", props, null);
        Boolean literalOnly = getBoolean(clazz, "literalOnly", props, null);
        Boolean tagExcluded = getBoolean(clazz, "tagExcluded", props, null);

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
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        
        p.setGenerated(Boolean.FALSE);

        component.addProperty(p);
    }
    

    /**
     * Convert a method name to a property name.
     */
    static String methodToPropName(String methodName)
    {
        StringBuffer name = new StringBuffer();
        if (methodName.startsWith("get") || methodName.startsWith("set"))
        {
            name.append(methodName.substring(3));
        }
        else if (methodName.startsWith("is"))
        {
            name.append(methodName.substring(2));
        }
        else
        {
            throw new IllegalArgumentException("Invalid annotated method name "
                    + methodName);
        }

        // Handle following styles of property name
        // getfooBar --> fooBar
        // getFooBar --> fooBar
        // getURL --> url
        // getURLLocation --> urlLocation
        for (int i = 0; i < name.length(); ++i)
        {
            char c = name.charAt(i);
            if (Character.isUpperCase(c))
            {
                name.setCharAt(i, Character.toLowerCase(c));
            }
            else
            {
                if (i > 1)
                {
                    // reset the previous char to uppercase
                    c = name.charAt(i - 1);
                    name.setCharAt(i - 1, Character.toUpperCase(c));
                }
                break;
            }
        }
        return name.toString();
    }

    /**
     * Given the full javadoc for a component, extract just the "first
     * sentence".
     * <p>
     * Initially, just find the first dot, and strip out any linefeeds. Later,
     * try to handle "e.g." and similar (see javadoc algorithm for sentence
     * detection).
     */
    private String getFirstSentence(String doc)
    {
        if (doc == null)
        {
            return null;
        }

        int index = doc.indexOf('.');
        if (index == -1)
        {
            return doc;
        }
        // abc.
        return doc.substring(0, index);
    }

    private void validateComponent(ComponentMeta component)
            throws MojoExecutionException
    {
        // when name is set, this is a real component, so must have
        // desc, type, family, rendererType,
        // tagClass, tagSuperclass

        if (component.getName() == null)
        {
            return;
        }

        List badprops = new ArrayList();
        if (component.getDescription() == null)
        {
            badprops.add("description");
        }
        if (component.getType() == null)
        {
            badprops.add("type");
        }
        
        //Family is optional because this can be inherited
        //if (component.getFamily() == null)
        //{
        //    badprops.add("family");
        //}
        
        //Renderer is optional
        //if (component.getRendererType() == null)
        //{
        //    badprops.add("rendererType");
        //}

        if (badprops.size() > 0)
        {
            StringBuffer buf = new StringBuffer();
            for (Iterator i = badprops.iterator(); i.hasNext();)
            {
                if (buf.length() > 0)
                {
                    buf.append(",");
                }
                buf.append((String) i.next());
            }
            throw new MojoExecutionException(
                    "Missing properties on component class "
                            + component.getClassName() + ":" + buf.toString());

        }
    }
}