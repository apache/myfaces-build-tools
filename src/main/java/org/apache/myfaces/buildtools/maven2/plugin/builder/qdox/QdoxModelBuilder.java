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
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelBuilder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelParams;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.BehaviorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FaceletTagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.TagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.BehaviorParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.ClientBehaviorParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.ClientBehaviorRendererParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.ComponentParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.ConverterParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.FaceletFunctionParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.FaceletTagParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.JspTagParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.RenderKitParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.RendererParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.ValidatorParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse.WebConfigParamParsingStrategy;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.TrinidadMavenFacesPluginModelBuilder;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;

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
    
    /**
     * Scan the source tree for doc-annotations, and build Model objects
     * containing info extracted from the doc-annotation attributes and
     * introspected info about the item the annotation is attached to.
     */
    public void buildModel(Model model, ModelParams parameters)
            throws MojoExecutionException
    {
        String currModelId = model.getModelId();
        if (currModelId == null)
        {
            throw new MojoExecutionException("Model must have id set");
        }
        
        final JavaDocBuilder builder = new JavaDocBuilder();
        IOUtils.visitSources(parameters, new IOUtils.SourceVisitor()
        {
            public void processSource(File file) throws IOException
            {
                builder.addSource(file);
            }
            
        });
        JavaClass[] classes = builder.getClasses();

        buildModel(model, parameters.getSourceDirs(), classes);
        CompositeComponentModelBuilder qccmb = new CompositeComponentModelBuilder();
        qccmb.buildModel(model, parameters);
        TrinidadMavenFacesPluginModelBuilder tmfpmb = new TrinidadMavenFacesPluginModelBuilder();
        tmfpmb.buildModel(model, parameters);
    }

    protected void buildModel(Model model, List sourceDirs, JavaClass[] classes)
        throws MojoExecutionException
    {
        String currModelId = model.getModelId();
        // Sort the class array so that they are processed in a
        // predictable order, regardless of how the source scanning
        // returned them.
        Arrays.sort(classes, new QdoxHelper.JavaClassComparator());
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

            //Check if the converter class file exists
            if (!IOUtils.existsSourceFile(StringUtils.replace(
                    converter.getClassName(),".","/")+".java", sourceDirs))
            {
                converter.setGeneratedComponentClass(Boolean.TRUE);
            }

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

        // post-process the list of behaviors
        for (Iterator it = model.getBehaviors().iterator(); it.hasNext();)
        {
            BehaviorMeta behavior = (BehaviorMeta) it.next();
            if (!behavior.getModelId().equals(currModelId))
            {
                continue;
            }
            QdoxHelper.initBehaviorAncestry(processedClasses, model, behavior);
            
            //Check if the behavior class file exists
            if (!IOUtils.existsSourceFile(StringUtils.replace(
                    behavior.getClassName(),".","/")+".java", sourceDirs))
            {
                behavior.setGeneratedComponentClass(Boolean.TRUE);
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
        
        ParsingContext context = new ParsingContext();
        
        context.addStrategy(new BehaviorParsingStrategy());
        context.addStrategy(new ClientBehaviorParsingStrategy());
        context.addStrategy(new ClientBehaviorRendererParsingStrategy());
        context.addStrategy(new ComponentParsingStrategy());
        context.addStrategy(new ConverterParsingStrategy());
        context.addStrategy(new FaceletTagParsingStrategy());
        context.addStrategy(new JspTagParsingStrategy());
        context.addStrategy(new RendererParsingStrategy());
        context.addStrategy(new RenderKitParsingStrategy());
        context.addStrategy(new ValidatorParsingStrategy());
        context.addStrategy(new WebConfigParamParsingStrategy());
        context.addStrategy(new FaceletFunctionParsingStrategy());
        
        context.parseClass(clazz, model);
    }
}
