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
package org.apache.myfaces.buildtools.maven2.plugin.tagdoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.Flattener;
import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.BehaviorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ClassMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FaceletFunctionMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FaceletTagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.TagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.utils.MavenPluginConsoleLogSystem;
import org.apache.myfaces.buildtools.maven2.plugin.builder.utils.MyfacesUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.Xpp3DomWriter;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.xml.sax.Attributes;

/**
 * 
 * Generate the tag doc content pages using velocity. This is done before
 * site, because maven-site-plugin use velocity and if we do this inside 
 * report generation, we cause a ClassLoader problem.
 * 
 * @author Leonardo Uribe
 * @goal tagdoc-content
 * @phase generate-resources
 */
public class TagdocContentMojo extends AbstractMojo
{
    private Model _model;

    /**
     * Specifies the directory where the report will be generated
     *
     * @parameter default-value="${project.reporting.outputDirectory}"
     * @required
     */
    private File outputDirectory;

    /**
     * Directory where the original site is present.
     * (TRIED using ${baseDir}/src/site;  that inserted a 'null' into
     * the string for some reason.  TRIED using ${siteDirectory},
     * which was undefined.  TRIED ${project.directory}src/site; which also
     * inserted a null.  ${project.build.directory}/../src/site seems to work,
     * though it assumes that ${project.build.directory} is 
     * ${project.directory}/target.
     * 
     * @parameter default-value="${project.build.directory}/../src/site/"
     * @required
     */
    private File siteDirectory;

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter
     */
    private Map taglibs;
        
    /**
     * @parameter expression="${project.build.directory}/generated-resources/myfaces-builder-plugin"
     */
    private File buildDirectory;

    /**
     * Injected name of file generated by earlier run of BuildMetaDataMojo goal.
     * 
     * @parameter
     */
    private String metadataFile = "META-INF/myfaces-metadata.xml";
    
    /**
     * @parameter expression="src/main/resources/META-INF"
     */
    private File templateSourceDirectory;
    
    /**
     * @parameter expression="src/main/tagdoc"
     */    
    private File baseFilesSourceDirectory;
    
    /**
     * @parameter
     */
    private List modelIds;
    
    /**
     * @parameter expression="xdoc-component.vm"
     */
    private String templateComponent;
    
    /**
     * @parameter expression="xdoc-converter.vm"
     */
    private String templateConverter;
    
    /**
     * @parameter expression="xdoc-validator.vm"
     */
    private String templateValidator;

    /**
     * @since 1.0.6
     * @parameter expression="xdoc-behavior.vm"
     */
    private String templateBehavior;
    
    /**
     * @parameter expression="xdoc-tag.vm"
     */
    private String templateTag;
    
    /**
     * @since 1.0.4
     * @parameter expression="xdoc-facelet-tag.vm"
     */
    private String templateFaceletTag;
    
    /**
     * 
     * @since 1.0.10
     * @parameter expression="xdoc-facelet-functions.vm"
     */
    private String templateFaceletFunctions;
    
    /**
     * Defines the jsf version (1.1, 1.2 or 2.0), used to pass it and add default 
     * properties for converters or validators in jsf 2.0.
     * 
     * @parameter
     */
    private String jsfVersion;

    static private final String _DOC_SUBDIRECTORY = "tagdoc";

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        if (modelIds == null)
        {
            modelIds = new ArrayList();
            modelIds.add(project.getArtifactId());
        }
        
        if (taglibs == null)
        {
            taglibs = new HashMap();
            taglibs.put("t", "http://myfaces.apache.org/tomahawk");
        }
        
        try
        {
            _model = IOUtils.loadModel(new File(buildDirectory,
                    metadataFile));
            new Flattener(_model).flatten();            
            _generateTagDocs();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Couldn't generate tagdoc", e);
        }        
    }
    
    public boolean canGenerate(ClassMeta component)
    {
        if ( modelIds.contains(component.getModelId()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean canGenerate(FaceletFunctionMeta component)
    {
        if (modelIds.contains(component.getModelId()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public class CustomResourceManagerImpl extends ResourceManagerImpl
    {
        public CustomResourceManagerImpl()
        {
            super();
        }
    }
    
    private VelocityEngine initVelocity() throws MojoExecutionException
    {        
        VelocityEngine velocityEngine = new VelocityEngine();
                
        try
        {
            velocityEngine.setProperty( "resource.loader", "file, class" );
            velocityEngine.setProperty( "file.resource.loader.class",
                    "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            velocityEngine.setProperty( "file.resource.loader.path", templateSourceDirectory.getPath());
            velocityEngine.setProperty( "class.resource.loader.class",
                    "org.apache.myfaces.buildtools.maven2.plugin.builder.utils.RelativeClasspathResourceLoader" );
            velocityEngine.setProperty( "class.resource.loader.path", "META-INF");                    
            velocityEngine.setProperty( "velocimacro.library", "componentClassMacros11.vm");
            velocityEngine.setProperty( "velocimacro.permissions.allow.inline","true");
            velocityEngine.setProperty( "velocimacro.permissions.allow.inline.local.scope", "true");
            velocityEngine.setProperty( "directive.foreach.counter.initial.value","0");
            //velocityEngine.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
            //"org.apache.myfaces.buildtools.maven2.plugin.builder.utils.ConsoleLogSystem" );

            velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM,
                    new MavenPluginConsoleLogSystem(this.getLog()));
            velocityEngine.init();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Error creating VelocityEngine", e);
        }
        
        return velocityEngine;
                
    }
            
    private void _generateTagDocs() throws Exception
    {
        Model model = getModel();
        if (model.getComponents().size() == 0)
        {
            getLog().info("Nothing to generate - no components found");
            return;
        }
        
        VelocityEngine velocityEngine = initVelocity();
        
        VelocityContext baseContext = new VelocityContext();
        baseContext.put("utils", new MyfacesUtils());
        baseContext.put("tagdocUtils", new TagdocUtils());
        baseContext.put("model",getModel());
        baseContext.put("jsf20", new Boolean(_is20()));
        
        Iterator components = model.components();

        Iterator validators = model.validators();

        Iterator converters = model.converters();
        
        Iterator behaviors = model.behaviors();
        
        Iterator tags = model.tags();
        
        Iterator faceletTags = model.faceletTags();
        
        Iterator faceletFunctions = model.faceletFunctions();
        
        Map<String, List<FaceletFunctionMeta>> faceletFunctionByModelId = 
            new HashMap<String, List<FaceletFunctionMeta>>();
        
        Set componentPages = new TreeSet();
        Set converterPages = new TreeSet();
        Set validatorPages = new TreeSet();
        Set behaviorPages = new TreeSet();
        Set tagsPages = new TreeSet();
        Set faceletTagsPages = new TreeSet();
        Set faceletFunctionPages = new TreeSet();

        int count = 0;
        while (components.hasNext())
        {
            ComponentMeta component = (ComponentMeta) components.next();
            if (canGenerate(component))
            {
                String pageName = _generateComponentDoc(velocityEngine,baseContext,model,component);
                if (pageName != null)
                {
                    componentPages.add(pageName);
                    count++;
                }
            }
        }
        while (converters.hasNext())
        {
            ConverterMeta converter = (ConverterMeta) converters.next();
            if (canGenerate(converter))
            {
                String pageName = _generateConverterDoc(velocityEngine,baseContext,model,converter);
                if (pageName != null)
                {
                    converterPages.add(pageName);
                    count++;
                }
            }
        }
        while (validators.hasNext())
        {
            ValidatorMeta validator = (ValidatorMeta) validators.next();

            if (canGenerate(validator))
            {
                String pageName = _generateValidatorDoc(velocityEngine,baseContext,model,validator);
                if (pageName != null)
                {
                    validatorPages.add(pageName);
                    count++;
                }
            }
        }
        while (behaviors.hasNext())
        {
            BehaviorMeta behavior = (BehaviorMeta) behaviors.next();
            if (canGenerate(behavior))
            {
                String pageName = _generateBehaviorDoc(velocityEngine,baseContext,model,behavior);
                if (pageName != null)
                {
                    behaviorPages.add(pageName);
                    count++;
                }
            }
        }
        while (tags.hasNext())
        {
            TagMeta tag = (TagMeta) tags.next();
            
            if (canGenerate(tag))
            {
                String pageName = _generateTagDoc(velocityEngine,baseContext,model,tag);
                if (pageName != null)
                {
                    tagsPages.add(pageName);
                    count++;
                }                
            }
        }
        while (faceletTags.hasNext())
        {
            FaceletTagMeta faceletTag = (FaceletTagMeta) faceletTags.next();
            
            if (canGenerate(faceletTag))
            {
                String pageName = _generateFaceletTagDoc(velocityEngine,baseContext,model,faceletTag);
                if (pageName != null)
                {
                    faceletTagsPages.add(pageName);
                    count++;
                }
            }
        }
        
        for (int i = 0; i < modelIds.size(); i++)
        {
            String modelId = (String) modelIds.get(i);
            
            faceletFunctionByModelId.put(modelId, new ArrayList<FaceletFunctionMeta>());
        }

        while (faceletFunctions.hasNext())
        {
            FaceletFunctionMeta faceletFunction = (FaceletFunctionMeta) faceletFunctions.next();
            
            if (canGenerate(faceletFunction))
            {
                String pageName = _generateFaceletFunctionDoc(velocityEngine, baseContext, model, faceletFunction);
                if (pageName != null)
                {
                    faceletFunctionPages.add(pageName);
                    count++;
                }
            }
        }

        Set otherPages = _gatherOtherTags();

        getLog().info("Generated " + count + " page(s)");
    }

    private Set _gatherOtherTags()
    {
        TreeSet set = new TreeSet();
        String subDir = _platformAgnosticPath(_platformAgnosticPath("xdoc/"
                + _DOC_SUBDIRECTORY));
        File siteSubDir = new File(siteDirectory, subDir);
        if (siteSubDir.exists())
        {
            String[] files = siteSubDir.list();
            for (int i = 0; i < files.length; i++)
            {
                String file = files[i];
                if (file.endsWith(".xml"))
                {
                    set.add(file.substring(0, file.length() - 4));
                }
            }
        }

        return set;
    }

    public boolean usePageLinkBar()
    {
        return false;
    }

    private String _toPageName(String qName)
    {
        return MyfacesUtils.getTagPrefix(qName) + "_" + MyfacesUtils.getTagName(qName);
    }

    private String _generateComponentDoc(VelocityEngine velocityEngine, 
            VelocityContext baseContext, Model model, ComponentMeta component)
            throws Exception
    {
        if (component.getName() == null)
        {
            return null;
        }
        String pageName = _toPageName(component.getName());
        
        Context context = new VelocityContext(baseContext);
        context.put("component", component);
        context.put("jsf20", new Boolean(_is20()));
        FaceletTagMeta faceletTag = model.findFaceletTagByName(component.getName());
        if (faceletTag == null && component.getTagHandler() != null)
        {
            faceletTag = model.findFaceletTagByClassName(component.getTagHandler());
        }
        if (faceletTag != null)
        {
            context.put("faceletTag", faceletTag);
        }
        
        String baseContent = "";
        
        File xmlBaseFile = new File(baseFilesSourceDirectory, 
                _platformAgnosticPath(pageName + "-base.xml"));
        
        if (xmlBaseFile != null && xmlBaseFile.exists())
        {
            if (getLog().isDebugEnabled())
            {
                getLog().debug("using base content file: "+xmlBaseFile.getPath());
            }
            
            Reader reader = null;
            try
            {
                reader = new FileReader(xmlBaseFile);
                Xpp3Dom root = Xpp3DomBuilder.build(reader);
                
                StringWriter writer = new StringWriter();
                
                Xpp3Dom [] children = root.getChild("body").getChildren();
                
                for (int i = 0; i< children.length; i++)
                {
                    Xpp3Dom dom = children[i];
                    Xpp3DomWriter.write(writer, dom);
                }
                baseContent = writer.toString();
                writer.close();
            }
            catch (XmlPullParserException e)
            {
                throw new MojoExecutionException(
                        "Error parsing base file: " + e.getMessage(), e);
            }
            finally
            {
                reader.close();
            }
        }
        
        baseContext.put("baseContent", baseContent);        
        
        Writer out = null;
        
        try
        {        
            File targetDir = new File(outputDirectory.getParentFile(),
                    _platformAgnosticPath("generated-site/xdoc/"
                            + _DOC_SUBDIRECTORY));
            
            if ( !targetDir.exists() )
            {
                targetDir.mkdirs();
            }
            File targetFile = new File(targetDir, pageName + ".xml");
    
            out = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");
            
            Template template = velocityEngine.getTemplate(getTemplateComponent());
            
            template.merge(context, out);
            
            out.flush();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(out);
            out = null;
        }

        return pageName;
    }

    private String _generateConverterDoc(VelocityEngine velocityEngine, 
            VelocityContext baseContext, Model model, ConverterMeta converter)
            throws Exception
    {
        if (converter.getName() == null)
        {
            return null;
        }

        String pageName = _toPageName(converter.getName());
        
        Context context = new VelocityContext(baseContext);
        context.put("converter", converter);
        context.put("jsf20", new Boolean(_is20()));
        FaceletTagMeta faceletTag = model.findFaceletTagByName(converter.getName());
        if (faceletTag == null && converter.getTagHandler() != null)
        {
            faceletTag = model.findFaceletTagByClassName(converter.getTagHandler());
        }
        if (faceletTag != null)
        {
            context.put("faceletTag", faceletTag);
        }
        
        String baseContent = "";
        
        File xmlBaseFile = new File(baseFilesSourceDirectory, 
                _platformAgnosticPath(pageName + "-base.xml"));
        
        if (xmlBaseFile != null && xmlBaseFile.exists())
        {
            if (getLog().isDebugEnabled())
            {
                getLog().debug("using base content file: "+xmlBaseFile.getPath());
            }
            
            Reader reader = null;
            try
            {
                reader = new FileReader(xmlBaseFile);
                Xpp3Dom root = Xpp3DomBuilder.build(reader);
                
                StringWriter writer = new StringWriter();
                
                Xpp3Dom [] children = root.getChild("body").getChildren();
                
                for (int i = 0; i< children.length; i++)
                {
                    Xpp3Dom dom = children[i];
                    Xpp3DomWriter.write(writer, dom);
                }
                baseContent = writer.toString();
                writer.close();
            }
            catch (XmlPullParserException e)
            {
                throw new MojoExecutionException(
                        "Error parsing base file: " + e.getMessage(), e);
            }
            finally
            {
                reader.close();
            }
        }
        
        baseContext.put("baseContent", baseContent);        
        
        Writer out = null;
        
        try
        {        
            File targetDir = new File(outputDirectory.getParentFile(),
                    _platformAgnosticPath("generated-site/xdoc/"
                            + _DOC_SUBDIRECTORY));
            
            if ( !targetDir.exists() )
            {
                targetDir.mkdirs();
            }
            File targetFile = new File(targetDir, pageName + ".xml");
    
            out = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");
            
            Template template = velocityEngine.getTemplate(getTemplateConverter());
            
            template.merge(context, out);
            
            out.flush();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(out);
            out = null;
        }

        return pageName;
    }

    private String _generateValidatorDoc(VelocityEngine velocityEngine, 
            VelocityContext baseContext, Model model, ValidatorMeta validator)
            throws Exception
    {
        if (validator.getName() == null)
        {
            return null;
        }

        String pageName = _toPageName(validator.getName());
        
        Context context = new VelocityContext(baseContext);
        context.put("validator", validator);
        context.put("jsf20", new Boolean(_is20()));
        FaceletTagMeta faceletTag = model.findFaceletTagByName(validator.getName());
        if (faceletTag == null && validator.getTagHandler() != null)
        {
            faceletTag = model.findFaceletTagByClassName(validator.getTagHandler());
        }
        if (faceletTag != null)
        {
            context.put("faceletTag", faceletTag);
        }
        
        String baseContent = "";
        
        File xmlBaseFile = new File(baseFilesSourceDirectory, 
                _platformAgnosticPath(pageName + "-base.xml"));
        
        if (xmlBaseFile != null && xmlBaseFile.exists())
        {
            if (getLog().isDebugEnabled())
            {
                getLog().debug("using base content file: "+xmlBaseFile.getPath());
            }
            
            Reader reader = null;
            try
            {
                reader = new FileReader(xmlBaseFile);
                Xpp3Dom root = Xpp3DomBuilder.build(reader);
                
                StringWriter writer = new StringWriter();
                
                Xpp3Dom [] children = root.getChild("body").getChildren();
                
                for (int i = 0; i< children.length; i++)
                {
                    Xpp3Dom dom = children[i];
                    Xpp3DomWriter.write(writer, dom);
                }
                baseContent = writer.toString();
                writer.close();
            }
            catch (XmlPullParserException e)
            {
                throw new MojoExecutionException(
                        "Error parsing base file: " + e.getMessage(), e);
            }
            finally
            {
                reader.close();
            }
        }
        
        baseContext.put("baseContent", baseContent);        
        
        Writer out = null;
        
        try
        {        
            File targetDir = new File(outputDirectory.getParentFile(),
                    _platformAgnosticPath("generated-site/xdoc/"
                            + _DOC_SUBDIRECTORY));
            
            if ( !targetDir.exists() )
            {
                targetDir.mkdirs();
            }
            File targetFile = new File(targetDir, pageName + ".xml");
    
            out = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");
            
            Template template = velocityEngine.getTemplate(getTemplateValidator());
            
            template.merge(context, out);
            
            out.flush();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(out);
            out = null;
        }

        return pageName;
    }
    
    private String _generateBehaviorDoc(VelocityEngine velocityEngine, 
            VelocityContext baseContext, Model model, BehaviorMeta behavior)
            throws Exception
    {
        if (behavior.getName() == null)
        {
            return null;
        }

        String pageName = _toPageName(behavior.getName());
        
        Context context = new VelocityContext(baseContext);
        context.put("behavior", behavior);
        context.put("jsf20", new Boolean(_is20()));
        FaceletTagMeta faceletTag = model.findFaceletTagByName(behavior.getName());
        if (faceletTag != null)
        {
            context.put("faceletTag", faceletTag);
        }
        
        String baseContent = "";
        
        File xmlBaseFile = new File(baseFilesSourceDirectory, 
                _platformAgnosticPath(pageName + "-base.xml"));
        
        if (xmlBaseFile != null && xmlBaseFile.exists())
        {
            if (getLog().isDebugEnabled())
            {
                getLog().debug("using base content file: "+xmlBaseFile.getPath());
            }
            
            Reader reader = null;
            try
            {
                reader = new FileReader(xmlBaseFile);
                Xpp3Dom root = Xpp3DomBuilder.build(reader);
                
                StringWriter writer = new StringWriter();
                
                Xpp3Dom [] children = root.getChild("body").getChildren();
                
                for (int i = 0; i< children.length; i++)
                {
                    Xpp3Dom dom = children[i];
                    Xpp3DomWriter.write(writer, dom);
                }
                baseContent = writer.toString();
                writer.close();
            }
            catch (XmlPullParserException e)
            {
                throw new MojoExecutionException(
                        "Error parsing base file: " + e.getMessage(), e);
            }
            finally
            {
                reader.close();
            }
        }
        
        baseContext.put("baseContent", baseContent);        
        
        Writer out = null;
        
        try
        {        
            File targetDir = new File(outputDirectory.getParentFile(),
                    _platformAgnosticPath("generated-site/xdoc/"
                            + _DOC_SUBDIRECTORY));
            
            if ( !targetDir.exists() )
            {
                targetDir.mkdirs();
            }
            File targetFile = new File(targetDir, pageName + ".xml");
    
            out = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");
            
            Template template = velocityEngine.getTemplate(getTemplateBehavior());
            
            template.merge(context, out);
            
            out.flush();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(out);
            out = null;
        }

        return pageName;
    }
    
    private String _generateTagDoc(VelocityEngine velocityEngine, 
            VelocityContext baseContext, Model model, TagMeta tag)
            throws Exception
    {
        if (tag.getName() == null)
        {
            return null;
        }

        String pageName = _toPageName(tag.getName());
        
        Context context = new VelocityContext(baseContext);
        context.put("tag", tag);
        context.put("jsf20", new Boolean(_is20()));
        FaceletTagMeta faceletTag = model.findFaceletTagByName(tag.getName());
        if (faceletTag == null && tag.getTagHandler() != null)
        {
            faceletTag = model.findFaceletTagByClassName(tag.getTagHandler());
        }
        if (faceletTag != null)
        {
            context.put("faceletTag", faceletTag);
        }
        
        String baseContent = "";
        
        File xmlBaseFile = new File(baseFilesSourceDirectory, 
                _platformAgnosticPath(pageName + "-base.xml"));
        
        if (xmlBaseFile != null && xmlBaseFile.exists())
        {
            if (getLog().isDebugEnabled())
            {
                getLog().debug("using base content file: "+xmlBaseFile.getPath());
            }
            
            Reader reader = null;
            try
            {
                reader = new FileReader(xmlBaseFile);
                Xpp3Dom root = Xpp3DomBuilder.build(reader);
                
                StringWriter writer = new StringWriter();
                
                Xpp3Dom [] children = root.getChild("body").getChildren();
                
                for (int i = 0; i< children.length; i++)
                {
                    Xpp3Dom dom = children[i];
                    Xpp3DomWriter.write(writer, dom);
                }
                baseContent = writer.toString();
                writer.close();
            }
            catch (XmlPullParserException e)
            {
                throw new MojoExecutionException(
                        "Error parsing base file: " + e.getMessage(), e);
            }
            finally
            {
                reader.close();
            }
        }
        
        baseContext.put("baseContent", baseContent);        
        
        Writer out = null;
        
        try
        {        
            File targetDir = new File(outputDirectory.getParentFile(),
                    _platformAgnosticPath("generated-site/xdoc/"
                            + _DOC_SUBDIRECTORY));
            
            if ( !targetDir.exists() )
            {
                targetDir.mkdirs();
            }
            File targetFile = new File(targetDir, pageName + ".xml");
    
            out = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");
            
            Template template = velocityEngine.getTemplate(getTemplateTag());
            
            template.merge(context, out);
            
            out.flush();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(out);
            out = null;
        }

        return pageName;
    }
    
    private String _generateFaceletFunctionDoc(
            VelocityEngine velocityEngine, 
            VelocityContext baseContext, 
            Model model, FaceletFunctionMeta faceletFunction)
    throws Exception
    {
        String pageName = _toPageName(faceletFunction.getName());
        
        Context context = new VelocityContext(baseContext);
        context.put("faceletFunction", faceletFunction);
        context.put("jsf20", new Boolean(_is20()));
        
        String baseContent = "";
        
        File xmlBaseFile = new File(baseFilesSourceDirectory, 
                _platformAgnosticPath(pageName + "-base.xml"));
        
        if (xmlBaseFile != null && xmlBaseFile.exists())
        {
            if (getLog().isDebugEnabled())
            {
                getLog().debug("using base content file: "+xmlBaseFile.getPath());
            }
            
            Reader reader = null;
            try
            {
                reader = new FileReader(xmlBaseFile);
                Xpp3Dom root = Xpp3DomBuilder.build(reader);
                
                StringWriter writer = new StringWriter();
                
                Xpp3Dom [] children = root.getChild("body").getChildren();
                
                for (int i = 0; i< children.length; i++)
                {
                    Xpp3Dom dom = children[i];
                    Xpp3DomWriter.write(writer, dom);
                }
                baseContent = writer.toString();
                writer.close();
            }
            catch (XmlPullParserException e)
            {
                throw new MojoExecutionException(
                        "Error parsing base file: " + e.getMessage(), e);
            }
            finally
            {
                reader.close();
            }
        }
        
        baseContext.put("baseContent", baseContent);        
        
        Writer out = null;
        
        try
        {        
            File targetDir = new File(outputDirectory.getParentFile(),
                    _platformAgnosticPath("generated-site/xdoc/"
                            + _DOC_SUBDIRECTORY));
            
            if ( !targetDir.exists() )
            {
                targetDir.mkdirs();
            }
            File targetFile = new File(targetDir, pageName + ".xml");
    
            out = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");
            
            Template template = velocityEngine.getTemplate(getTemplateFaceletFunctions());
            
            template.merge(context, out);
            
            out.flush();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(out);
            out = null;
        }

        return pageName;
    }
    
    private String _generateFaceletTagDoc(VelocityEngine velocityEngine, 
            VelocityContext baseContext, Model model, FaceletTagMeta faceletTag)
            throws Exception
    {
        String name = faceletTag.getName(); 
        if (name == null)
        {
            return null;
        }
        
        if (faceletTag.getComponentClass() != null)
        {
            ComponentMeta comp = model.findComponentByClassName(faceletTag.getComponentClass());
            if (name.equals(comp.getName()))
            {
                //Exists in jsp and in facelets, but has specific facelets properties
                return null;
            }
        }
        if (faceletTag.getConverterClass() != null)
        {
            ConverterMeta comp = model.findConverterByClassName(faceletTag.getConverterClass());
            if (name.equals(comp.getName()))
            {
                //Exists in jsp and in facelets, but has specific facelets properties
                return null;
            }            
        }
        if (faceletTag.getValidatorClass() != null)
        {
            ValidatorMeta comp = model.findValidatorByClassName(faceletTag.getValidatorClass());
            if (name.equals(comp.getName()))
            {
                //Exists in jsp and in facelets, but has specific facelets properties
                return null;
            }            
        }
        if (faceletTag.getBehaviorClass() != null)
        {
            BehaviorMeta comp = model.findBehaviorByClassName(faceletTag.getBehaviorClass());
            if (name.equals(comp.getName()))
            {
                //Exists in jsp and in facelets, but has specific facelets properties
                return null;
            }
        }
        if (faceletTag.getTagClass() != null)
        {
            TagMeta comp = model.findTagByClassName(faceletTag.getTagClass());
            if (name.equals(comp.getName()))
            {
                //Exists in jsp and in facelets, but has specific facelets properties
                return null;
            }
        }

        String pageName = _toPageName(faceletTag.getName());
        
        Context context = new VelocityContext(baseContext);
        context.put("faceletTag", faceletTag);
        context.put("jsf20", new Boolean(_is20()));
        
        String baseContent = "";
        
        File xmlBaseFile = new File(baseFilesSourceDirectory, 
                _platformAgnosticPath(pageName + "-base.xml"));
        
        if (xmlBaseFile != null && xmlBaseFile.exists())
        {
            if (getLog().isDebugEnabled())
            {
                getLog().debug("using base content file: "+xmlBaseFile.getPath());
            }
            
            Reader reader = null;
            try
            {
                reader = new FileReader(xmlBaseFile);
                Xpp3Dom root = Xpp3DomBuilder.build(reader);
                
                StringWriter writer = new StringWriter();
                
                Xpp3Dom [] children = root.getChild("body").getChildren();
                
                for (int i = 0; i< children.length; i++)
                {
                    Xpp3Dom dom = children[i];
                    Xpp3DomWriter.write(writer, dom);
                }
                baseContent = writer.toString();
                writer.close();
            }
            catch (XmlPullParserException e)
            {
                throw new MojoExecutionException(
                        "Error parsing base file: " + e.getMessage(), e);
            }
            finally
            {
                reader.close();
            }
        }
        
        baseContext.put("baseContent", baseContent);        
        
        Writer out = null;
        
        try
        {        
            File targetDir = new File(outputDirectory.getParentFile(),
                    _platformAgnosticPath("generated-site/xdoc/"
                            + _DOC_SUBDIRECTORY));
            
            if ( !targetDir.exists() )
            {
                targetDir.mkdirs();
            }
            File targetFile = new File(targetDir, pageName + ".xml");
    
            out = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");
            
            Template template = velocityEngine.getTemplate(getTemplateFaceletTag());
            
            template.merge(context, out);
            
            out.flush();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(out);
            out = null;
        }

        return pageName;
    }    

    static private final String _platformAgnosticPath(String path)
    {
        return path.replace('/', File.separatorChar);
    }

    protected MavenProject getProject()
    {
        return project;
    }

    protected String getOutputDirectory()
    {
        return outputDirectory.getAbsolutePath();
    }

    public String getName(Locale locale)
    {
        return "JSF Tag Documentation";
    }

    public String getDescription(Locale locale)
    {
        return "Documentation for JSF Tags";
    }

    public String getOutputName()
    {
        return "tagdoc";
    }

    protected Model getModel()
    {
        return _model;
    }

    protected List getMasterConfigs(MavenProject project)
            throws MavenReportException
    {
        String resourcePath = "META-INF/maven-faces-plugin/faces-config.xml";
        return getCompileDependencyResources(project, resourcePath);
    }

    protected List getCompileDependencyResources(MavenProject project,
            String resourcePath) throws MavenReportException
    {
        try
        {
            ClassLoader cl = createCompileClassLoader(project);
            Enumeration e = cl.getResources(resourcePath);
            List urls = new ArrayList();
            while (e.hasMoreElements())
            {
                URL url = (URL) e.nextElement();
                urls.add(url);
            }
            return Collections.unmodifiableList(urls);
        }
        catch (IOException e)
        {
            throw new MavenReportException("Unable to get resources for path "
                    + "\"" + resourcePath + "\"", e);
        }

    }

    private ClassLoader createCompileClassLoader(MavenProject project)
            throws MavenReportException
    {
        Thread current = Thread.currentThread();
        ClassLoader cl = current.getContextClassLoader();

        try
        {
            List classpathElements = project.getCompileClasspathElements();
            if (!classpathElements.isEmpty())
            {
                String[] entries = (String[]) classpathElements
                        .toArray(new String[0]);
                URL[] urls = new URL[entries.length];
                for (int i = 0; i < urls.length; i++)
                {
                    urls[i] = new File(entries[i]).toURL();
                }
                cl = new URLClassLoader(urls, cl);
            }
        }
        catch (DependencyResolutionRequiredException e)
        {
            throw new MavenReportException("Error calculating scope classpath",
                    e);
        }
        catch (MalformedURLException e)
        {
            throw new MavenReportException("Error calculating scope classpath",
                    e);
        }

        return cl;
    }

    static public class URLCreationFactory extends
            AbstractObjectCreationFactory
    {
        public Object createObject(Attributes attributes)
                throws MalformedURLException
        {
            String href = attributes.getValue("href");
            if (href == null)
            {
                throw new IllegalStateException("Missing href attribute");
            }

            URL master = (URL) digester.getRoot();
            return new URL(master, href);
        }
    }

    public String getTemplateComponent()
    {
        return templateComponent;
    }

    public String getTemplateConverter()
    {
        return templateConverter;
    }

    public String getTemplateBehavior()
    {
        return templateBehavior;
    }

    public String getTemplateValidator()
    {
        return templateValidator;
    }

    public String getTemplateTag()
    {
        return templateTag;
    }

    public String getTemplateFaceletTag()
    {
        return templateFaceletTag;
    }
    
    public String getTemplateFaceletFunctions()
    {
        return templateFaceletFunctions;
    }
    
    private boolean _is12()
    {
        return "1.2".equals(jsfVersion) || "12".equals(jsfVersion);
    }
    
    private boolean _is20()
    {
        return "2.0".equals(jsfVersion) || "20".equals(jsfVersion);
    }
}
