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
package org.apache.myfaces.buildtools.maven2.plugin.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.utils.BuildException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.utils.MavenPluginConsoleLogSystem;
import org.apache.myfaces.buildtools.maven2.plugin.builder.utils.MyfacesUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;

/**
 * Maven goal to generate java source code for Component classes.
 * 
 * <p>It uses velocity to generate templates, and has the option to define custom templates.</p>
 * <p>The executed template has the following variables available to it:</p>
 * <ul>
 *  <li>utils : Returns an instance of 
 *  org.apache.myfaces.buildtools.maven2.plugin.builder.utils.MyfacesUtils, 
 *  it contains some useful methods.</li>
 *  <li>component : Returns the current instance of
 *   org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta</li>
 *  <li>innersource : code to be injected from the template class when template
 *  mode is used</li>  
 * </ul>
 * 
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal make-components
 * @phase generate-sources
 */
public class MakeComponentsMojo extends AbstractBuilderMojo
{
    /**
     * Injected Maven project.
     * 
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * Defines the directory where the metadata file (META-INF/myfaces-metadata.xml) is loaded.
     * 
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
     * The directory used to load templates into velocity environment.
     * 
     * @parameter expression="src/main/resources/META-INF"
     */
    private File templateSourceDirectory;

    /**
     * The directory where all generated files are created. This directory is added as a
     * compile source root automatically like src/main/java is. 
     * 
     * @parameter expression="${project.build.directory}/generated-sources/myfaces-builder-plugin"
     */
    private File generatedSourceDirectory;

    /**
     * Generate only component classes that starts with the specified package prefix. 
     * 
     * @parameter
     */
    private String packageContains;

    /**
     * Generate only component classes that starts with the specified component type prefix. 
     * 
     * @parameter
     */
    private String typePrefix;

    /**
     *  Log and continue execution when generating component classes.
     *  <p>
     *  If this property is set to false (default), errors when a component class is generated stops
     *  execution immediately.
     *  </p>
     * 
     * @parameter
     */
    private boolean force;

    /**
     * Defines the jsf version (1.1 or 1.2), used to take the default templates for each version.
     * <p> 
     * If version is 1.1, the default templateComponentName is 'componentClass11.vm', if version
     * is 1.2 the default templateComponentName is 'componentClass12.vm', if version is 2.0 the
     * default templateComponentName is 'componentClass20.vm'
     * </p>
     * 
     * @parameter
     */
    private String jsfVersion;
    
    /**
     * Define the models that should be included when generate component classes. If not set, the
     * current model identified by the artifactId is used.
     * <p>
     * Each model built by build-metadata goal has a modelId, that by default is the artifactId of
     * the project. Setting this property defines which objects tied in a specified modelId should
     * be taken into account.  
     * </p>
     * <p>In this case, limit component generation only to the components defined in the models 
     * identified by the modelId defined. </p>
     * <p>This is useful when you need to generate files that take information defined on other
     * projects</p>
     * <p>Example:</p>
     * <pre>
     *    &lt;modelIds&gt;
     *        &lt;modelId>model1&lt;/modelId&gt;
     *        &lt;modelId>model2&lt;/modelId&gt;
     *    &lt;/modelIds&gt;
     * </pre>
     * 
     * @parameter
     */
    private List modelIds;

    /**
     * The name of the template used to generate component classes. According to the value on 
     * jsfVersion property the default if this property is not set could be componentClass11.vm (1.1) or
     * componentClass12.vm (1.2)
     * 
     * @parameter 
     */
    private String templateComponentName;
    
    /**
     * This param is used to search in this folder if some file to
     * be generated exists and avoid generation and duplicate exception.
     * 
     * @parameter expression="src/main/java"
     */    
    private File mainSourceDirectory;
    
    /**
     * This param is used to search in this folder if some file to
     * be generated exists and avoid generation and duplicate exception.
     * 
     * @parameter
     */        
    private File mainSourceDirectory2;

    /**
     * Excludes files from the component generation. Must be comma separated.
     * 
     * @parameter
     */
    private String[] excludes;

    /**
     * Execute the Mojo.
     */
    public void execute() throws MojoExecutionException
    {
        // This command makes Maven compile the generated source:
        // getProject().addCompileSourceRoot( absoluteGeneratedPath.getPath() );
        
        try
        {
            project.addCompileSourceRoot( generatedSourceDirectory.getCanonicalPath() );
            
            if (modelIds == null)
            {
                modelIds = new ArrayList();
                modelIds.add(project.getArtifactId());
            }
            File mdFile = new File(buildDirectory, metadataFile);
            Model model = IOUtils.loadModel(mdFile);
            new Flattener(model).flatten();

            for(int i = 0; i < excludes.length; i++){
                String str =  excludes[i];
                getLog().info("Files to be excluded: "+ str);
                if(excludes != null){
                    if(str.contains("**"))
                    {
                        // must be reglar expression 
                        str = str.replace("**", ".*");
                    }
                    excludes[i] = str;
                }
            }

            Properties cacheInfo = new Properties();
            loadCache(cacheInfo);
            generateComponents(model, cacheInfo, mdFile.lastModified() );
            storeCache(cacheInfo);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Error generating components", e);
        }
        catch (BuildException e)
        {
            throw new MojoExecutionException("Error generating components", e);
        }
    }
    
    private VelocityEngine initVelocity() throws MojoExecutionException
    {
        File template = new File(templateSourceDirectory, _getTemplateName());
        
        if (template.exists())
        {
            getLog().info("Using template from file loader: "+template.getPath());
        }
        else
        {
            getLog().info("Using template from class loader: META-INF/"+_getTemplateName());
        }
                
        VelocityEngine velocityEngine = null;
        try
        {
            velocityEngine = new VelocityEngine();
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
            //    "org.apache.myfaces.buildtools.maven2.plugin.builder.utils.ConsoleLogSystem" );
            
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
    

    /**
     * Generates parsed components.
     */
    private void generateComponents(Model model, Properties cachedInfo, long lastModifiedMetadata) throws IOException,
            MojoExecutionException
    {
        // Make sure generated source directory 
        // is added to compilation source path 
        //project.addCompileSourceRoot(generatedSourceDirectory.getCanonicalPath());
        File tf = new File(templateSourceDirectory, _getTemplateName());
        
        if (isCachingEnabled())
        {
            boolean upToDate = true;
            for (Iterator it = model.getComponents().iterator(); it.hasNext();)
            {
                ComponentMeta component = (ComponentMeta) it.next();
                
                if (component.getClassName() != null)
                {
                    File f = new File(mainSourceDirectory, StringUtils.replace(
                            component.getClassName(), ".", "/")+".java");
                                        
                    if (!f.exists() && canGenerateComponent(component))
                    {
                        if (mainSourceDirectory2 != null)
                        {
                            File f2 = new File(mainSourceDirectory2, StringUtils.replace(
                                    component.getClassName(), ".", "/")+".java");
                            if (f2.exists())
                            {
                                //Skip
                                continue;
                            }
                        }
    
                        File outFile = new File(generatedSourceDirectory, StringUtils.replace(
                                component.getClassName(), ".", "/")+".java");
    
                        String lastModifiedString = cachedInfo.getProperty(outFile.getAbsolutePath());
                        if (lastModifiedString == null)
                        {
                            upToDate = false;
                            break;
                        }
                        else if (!outFile.exists())
                        {
                            upToDate = false;
                            break;
                        }
                        else
                        {
                            Long lastModified = Long.valueOf(lastModifiedString);
                            if (lastModified != null && lastModifiedMetadata > lastModified.longValue())
                            {
                                upToDate = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (upToDate && tf != null && tf.exists())
            {
                upToDate = isFileUpToDate(cachedInfo, tf);
            }
            if (upToDate)
            {
                getLog().info("generated component files are up to date");
                return;
            }
        }

        
        //Init Qdox for extract code 
        JavaDocBuilder builder = new JavaDocBuilder();
        
        List sourceDirs = project.getCompileSourceRoots();
        
        // need a File object representing the original source tree
        for (Iterator i = sourceDirs.iterator(); i.hasNext();)
        {
            String srcDir = (String) i.next();
            File dir = new File(srcDir);
            //recursively add files to the builder
            // allows control to exclude files 
            addFilesToBuilder(builder,dir);
        }        
        
        //Init velocity
        VelocityEngine velocityEngine = initVelocity();

        VelocityContext baseContext = new VelocityContext();
        baseContext.put("utils", new MyfacesUtils());
        
        for (Iterator it = model.getComponents().iterator(); it.hasNext();)
        {
            ComponentMeta component = (ComponentMeta) it.next();
            
            if (component.getClassName() != null)
            {
                File f = new File(mainSourceDirectory, StringUtils.replace(
                    component.getClassName(), ".", "/")+".java");
                                
                if (!f.exists() && canGenerateComponent(component))
                {
                    if (mainSourceDirectory2 != null)
                    {
                        File f2 = new File(mainSourceDirectory2, StringUtils.replace(
                                component.getClassName(), ".", "/")+".java");
                        if (f2.exists())
                        {
                            //Skip
                            continue;
                        }
                    }
                    getLog().info("Generating component class:"+component.getClassName());
                    
                    try 
                    {
                        _generateComponent(velocityEngine, builder,component,baseContext,
                                cachedInfo, lastModifiedMetadata);
                    }
                    catch(MojoExecutionException e)
                    {
                        if (force)
                        {
                            getLog().error(e.getMessage());
                        }
                        else
                        {
                            //Stop execution throwing exception
                            throw e;
                        }
                    }
                }
            }
        }
        if (isCachingEnabled())
        {
            if (tf != null && tf.exists())
            {
                cachedInfo.put(tf.getAbsolutePath(), Long.toString(tf.lastModified()));
            }
        }
    }

    public void addFilesToBuilder(JavaDocBuilder builder, File dir) throws IOException
    {
        File[] directoryListing = dir.listFiles();
        if(directoryListing == null)
        {
            return;
        }
        for(int j = 0; j < directoryListing.length; j++)
        {
            File f =  directoryListing[j];
            if(f.isDirectory()){
                addFilesToBuilder(builder, f);
            } 
            else 
            {
                boolean skip = false;
                for(int i = 0; i < excludes.length; i++) {
                    String currentExclude= excludes[i];
                    String filepath = f.toString().replace("\\", "/");
                    if(currentExclude != null && filepath.matches(currentExclude))
                    {
                        getLog().info("Excluding source from builder: " + f);
                        skip = true;
                        break;
                    }
                }
                if(!skip){
                    builder.addSource(f);
                }
            }
        }
    }
    
    public boolean canGenerateComponent(ComponentMeta component)
    {
        if ( modelIds.contains(component.getModelId())
                && includePackage(component)
                && includeType(component))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean includePackage(ComponentMeta component)
    {
        if (packageContains != null)
        {
            if (MyfacesUtils.getPackageFromFullClass(component.getClassName()).startsWith(packageContains))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }        
    }

    public boolean includeType(ComponentMeta component)
    {
        if (typePrefix != null)
        {
            if (component.getType().startsWith(typePrefix))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }        
    }
    
    
    /**
     * Generates a parsed component.
     * 
     * @param component
     *            the parsed component metadata
     */
    private void _generateComponent(VelocityEngine velocityEngine,
            JavaDocBuilder builder,
            ComponentMeta component, VelocityContext baseContext,
            Properties cachedInfo, long lastModifiedMetadata)
            throws MojoExecutionException
    {
        Context context = new VelocityContext(baseContext);
        context.put("component", component);

        if (Boolean.TRUE.equals(component.isTemplate()))
        {
            String source = this.getInnerSourceCode(builder, component);
            
            if (source != null && !"".equals(source))
            {
                context.put("innersource", source);
            }
        }        
        
        Writer writer = null;
        File outFile = null;

        try
        {
            outFile = new File(generatedSourceDirectory, StringUtils.replace(
                    component.getClassName(), ".", "/")+".java");

            if ( !outFile.getParentFile().exists() )
            {
                outFile.getParentFile().mkdirs();
            }

            writer = new OutputStreamWriter(new FileOutputStream(outFile));

            Template template = velocityEngine.getTemplate(_getTemplateName());
                        
            template.merge(context, writer);

            writer.flush();
            
            if (isCachingEnabled())
            {
                cachedInfo.put(outFile.getAbsolutePath(), Long.toString(lastModifiedMetadata));
            }
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(
                    "Error merging velocity templates: " + e.getMessage(), e);
        }
        finally
        {
            IOUtil.close(writer);
            writer = null;
        }
    }
        
    /**
     * This method extract from component scanned sourceClass, the parts
     * which we need to move to generated class as a single String using Qdox.
     * 
     * Ignore code that has a \@JSFExclude or \@JSFProperty Doclet or Annotation
     * 
     * @param component
     * @return
     */
    private String getInnerSourceCode(JavaDocBuilder builder, ComponentMeta component)
    {   
        StringWriter writer = new StringWriter();
        
        JavaClass sourceClass = builder.getClassByName(component.getSourceClassName());
        
        JavaField [] fields = sourceClass.getFields();

        //Include the fields defined
        for (int i = 0; i < fields.length; i++)
        {            
            JavaField field = fields[i];
            
            DocletTag tag = field.getTagByName("JSFExclude");
            Annotation anno = getAnnotation(field, "JSFExclude");
            
            if (!(tag == null && anno == null))
            {
                continue;
            }
                        
            if (!isExcludedField(field.getName()))
            {
                writer.write("    ");
                writer.write(field.getDeclarationSignature(true));
                String initExpr = field.getInitializationExpression();
                initExpr = cleanInitializationExpression(initExpr);
                if (initExpr != null)
                {
                    writer.write(" = ");
                    writer.write(initExpr);
                }                
                writer.write(';');
                writer.write('\n');
            }
        }
        
        JavaMethod [] methods = sourceClass.getMethods();
        for (int i = 0; i < methods.length; i++)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName("JSFExclude");
            Annotation anno = getAnnotation(method, "JSFExclude");
            
            if (!(tag == null && anno == null))
            {
                continue;
            }
            
            tag = method.getTagByName("JSFProperty", false);
            anno = getAnnotation(method, "JSFProperty");
            
            if ( (tag == null && anno == null) || !method.isAbstract())
            {              
                //Get declaration signature in a way that we don't need
                //to declare imports.
                String declaration = method.getDeclarationSignature(true);
                                
                writer.write("    ");
                writer.write(declaration);
                String sourceCode = method.getSourceCode();
                if(sourceCode != null && sourceCode.length() > 0)
                {
                    writer.write('\n');
                    writer.write("    ");
                    writer.write('{');
                    writer.write(method.getSourceCode());
                    writer.write('}');
                    writer.write('\n');
                    writer.write('\n');
                }
                else
                {
                    writer.write(';');
                    writer.write('\n');
                }
            }
            
        }
                
        return writer.toString();
    }
    
    private boolean isExcludedField(String name)
    {
        return (name.equals("COMPONENT_TYPE") || 
                name.equals("DEFAULT_RENDERER_TYPE") ||
                name.equals("COMPONENT_FAMILY") );
    }

    /**
     * Clean up a field initialisation expression returned by qdox.
     * <p>
     * When a class has "int foo = 1;" then the initialisation expression
     * is the bit after the equals sign. Unfortunately qdox tends to return
     * a lot of garbage stuff in there. In particular, if there is no
     * initialisation expression, then qdox can return a string with
     * whitespace (not a null or empty string). 
     * <p>
     * In addition, if there are comments *before* a field declaration,
     * they appear inside the initialisation text (this is just broken
     * behaviour in qdox). Ideally we would move them to above the field
     * declaration where they belong. However that is complicated and
     * fragile, so we just discard them here.
     */
    static String cleanInitializationExpression(String expr)
    {
        expr = StringUtils.trim(expr);
        if (StringUtils.isEmpty(expr))
        {
            return null;
        }
        
        // split on linefeeds
        // trim each separately
        // remove any comment chars
        
        if (expr.indexOf("\n") > -1)
        {
            StringBuffer buf = new StringBuffer(100);
            String[] lines = StringUtils.split(expr, "\n");
            for(int i=0; i<lines.length; ++i)
            {
                String line = lines[i];
                line = StringUtils.trim(line);
                if (!line.startsWith("//") && !StringUtils.isEmpty(line))
                {
                    if (buf.length()> 0)
                    {
                        buf.append(" ");
                    }
                    buf.append(line);
                }
            }
            
            expr = buf.toString();
        }
        
        if (expr.startsWith("//"))
        {
            return null;
        }

        if (StringUtils.isEmpty(expr))
        {
            return null;
        }

        return expr;
    }

    /**
     * TODO: Copied from QdoxModelBuilder!
     * 
     * @param entity
     * @param annoName
     * @return
     */
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

    private String _getTemplateName()
    {
        if (templateComponentName == null)
        {
            if (_is20())
            {
                return "componentClass20.vm";
            }
            else if (_is12())
            {
                return "componentClass12.vm";
            }
            else
            {
                return "componentClass11.vm";
            }
        }
        else
        {
            return templateComponentName;
        }
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
