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
package org.apache.myfaces.plugins.jsdoc;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.apache.myfaces.plugins.jsdoc.util.HTMLFileContentFilter;
import org.apache.myfaces.plugins.jsdoc.util.JSDocPackMaven;
import org.apache.myfaces.plugins.jsdoc.util.JSFileNameFilter;
import org.apache.myfaces.plugins.jsdoc.util.XMLConfig;

public abstract class AbstractJSDocMojo extends AbstractMojo
{
    
    // ----------------------------------------------------------------------
    // Mojo components
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // Mojo parameters
    // ----------------------------------------------------------------------
    
    /**
     * The Maven Project Object
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    protected MavenProject project;
    
    /**
     * the root project build dir (target directory)
     *
     * @parameter expression="${project.build.directory}"
     */
    protected String projectBuildDir;
    
    // ----------------------------------------------------------------------
    // Standard Options
    // ----------------------------------------------------------------------
    
    /**
     * The project source JavaScript directory, which are the source
     * files unprocessed by myfaces JavaScript plugin
     *
     * @parameter expression="${basedir}/src/main/javascript/"
     */
    protected String sourceDirectory;
    
    /**
     * The relative dir name sourceDirectory is
     *
     * @parameter expression="src/main/javascript/"
     */
    protected String relativeSourceDirectory;
    
    /**
     * The project resource directory, to take all files ending 
     * with .js 
     *
     * @parameter expression="${basedir}/src/main/resources/"
     */
    protected File resourceDirectory;
    
    /**
     * The relative dir name resourceDirectory is
     *
     * @parameter expression="src/main/resources/"
     */
    protected String relativeResourceDirectory;
    
    
    /**
     * The project webapp directory, to take all files ending
     * with .js
     *
     * @parameter expression="${basedir}/src/main/webapp/"
     */
    protected String webappDirectory;
    
    /**
     * The relative dir name webappDirectory is
     *
     * @parameter expression="src/main/webapp/"
     */
    protected String relativeWebappDirectory;
    
    /**
     * Specifies the destination directory where JSDoc saves the generated HTML files.
     *
     * @parameter expression="${destDir}" alias="destDir" default-value="${project.build.directory}/jsdoc"
     * @required
     */
    protected File outputDirectory;
    
    /**
     * Path to the assembly file containing the file paths to our source JavaScript files
     *
     * @parameter expression="${basedir}/src/assembler/jsdoc-compiler.xml"
     */
    protected String assemblyFile;
    
    /**
     * Specifies whether the Javadoc generation should be skipped.
     *
     * @since 2.5
     * @parameter expression="${myfaces.jsdoc.skip}" default-value="false"
     */
    protected boolean skip;
    
    // ----------------------------------------------------------------------
    // Standard JSDoc Options
    // ----------------------------------------------------------------------
    
    //various JSDoc params, copied over as well as the corresponding snippets from
    /**
     * Whether to include symbols tagged as private. Default is <code>false</code>.
     *
     * @parameter expression="false"
     */
    protected boolean includePrivate;

    /**
     * Include all functions, even undocumented ones. Default is <code>false</code>.
     *
     * @parameter expression="false"
     */
    protected boolean includeUndocumented;

    /**
     * Include all functions, even undocumented, underscored ones. Default is <code>false</code>.
     *
     * @parameter expression="false"
     */
    protected boolean includeUndocumentedUnderscored;

    /**
     * template directory used by JSDoc the default is <code>templates/jsdoc</code> under the JSDoc root
     *
     * @parameter expression="templates/jsdoc"
     */
    protected String templates;

    // ----------------------------------------------------------------------
    // protected methods
    // ----------------------------------------------------------------------

    protected void executeReport( Locale unusedLocale )
        throws MavenReportException
    {
        JSDocHelper helper = _setup();
        try
        {
            _execute(helper);
        }
        catch (IOException e)
        {
            throw new MavenReportException(e.toString());
        }
        finally
        {
            _tearDown(helper);
        }  
    }

    /**
     * @return the output directory
     */
    protected String getOutputDirectory()
    {
        return outputDirectory.getAbsoluteFile().toString();
    }
    
    protected MavenProject getProject()
    {
        return project;
    }
    
    // ----------------------------------------------------------------------
    // private methods
    // ----------------------------------------------------------------------
    
    protected JSDocHelper _setup() throws MavenReportException
    {
        JSDocHelper helper = new JSDocHelper();
        try
        {
            if (new File(assemblyFile).exists())
            {
                helper.setFileMap(new XMLConfig(assemblyFile));
            }
        }
        catch (XMLStreamException e)
        {
            getLog().error(e);
            throw new MavenReportException(e.toString());
        }
        catch (FileNotFoundException e)
        {
            getLog().error(e);
            throw new MavenReportException(e.toString());
        }
        helper.setUnpacker(new JSDocPackMaven());
        //unpacker = new JSDocPackResources();

        helper.setJsdocRunPath(projectBuildDir + File.separator + JSDocMojoConst.JSDOC);
        helper.setJsdocEngineUnpacked(projectBuildDir + File.separator + JSDocMojoConst.TEMP
                + File.separator + JSDocMojoConst.JSDOC);

        helper.setJavascriptTargetPath(helper.getJsdocRunPath() + File.separator + JSDocMojoConst.JAVASCRIPT);

        File pathCreator = new File(helper.getJsdocEngineUnpacked());
        File jsdocPathCreator = new File(helper.getJavascriptTargetPath());
        pathCreator.mkdirs();
        jsdocPathCreator.mkdirs();
        return helper;
    }

    public void _tearDown(JSDocHelper helper) throws MavenReportException
    {
        try
        {
            FileUtils.deleteDirectory(new File(helper.getJsdocEngineUnpacked()));
        }
        catch (IOException e)
        {
            throw new MavenReportException(e.toString());
        }
    }

    protected void _execute(JSDocHelper helper) throws MavenReportException, IOException
    {

        copyJavascripts(helper);

        //fetchJavascriptSources(helper);
        //now we have all files we now can now work on our plugin call
        unpackJSDoc(helper);

        String systemJsdocDir = setenvJSDocDir(helper);
        String userDir = setenvUserDir(helper);
        try
        {
            executeJSDoc(helper);
        }
        finally
        {
            resetSysenvVars(systemJsdocDir, userDir);
        }
    }

    private void resetSysenvVars(String systemJsdocDir, String userDir)
    {
        if (systemJsdocDir != null)
        {
            System.setProperty(JSDocMojoConst.JSDOC_DIR, systemJsdocDir);
        }
        if (userDir != null)
        {
            System.setProperty("user.dir", userDir);
        }
    }

    private void executeJSDoc(JSDocHelper helper)
    {
        List args = _initArguments(helper);

        getLog().info("[JSDOC] Executing within maven: '" + args.toString().replaceAll(",", "") + "'");

        // tell Rhino to run JSDoc with the provided params
        // without calling System.exit

        org.mozilla.javascript.tools.shell.Main.main((String[]) args.toArray(new String[0]));

        this.fixHTML(helper);
    }

    private String setenvUserDir(JSDocHelper helper)
    {
        String userDir = System.getProperty("user.dir");
        System.setProperty("user.dir", helper.getJsdocEngineUnpacked() + File.separator);
        return userDir;
    }

    private String setenvJSDocDir(JSDocHelper helper)
    {
        String systemJsdocDir = System.getProperty(JSDocMojoConst.JSDOC_DIR);
        System.setProperty(JSDocMojoConst.JSDOC_DIR, helper.getJsdocEngineUnpacked() + File.separator);
        return systemJsdocDir;
    }

    private void unpackJSDoc(JSDocHelper helper) throws IOException
    {
        getLog().info("[JSDOC] Unpacking JSDoc toolkit for further processing");
        helper.getUnpacker().unpack(helper.getJsdocEngineUnpacked(), getLog());
        getLog().info("[JSDOC] Unpacking JSDoc toolkit for further processing done");
    }

    /**
     * initially copies all source files from the given source dir to the target
     * dir so that the files can be referenced later on by the HTML files
     */
    private void copyJavascripts(JSDocHelper helper) throws IOException
    {
        getLog().info("[JSDOC] Copying all JavaScript sources to the target dir for later reference");
        
        if (!StringUtils.isEmpty(sourceDirectory))
        {
            File buildSourceDirFile = new File(sourceDirectory);
            if (buildSourceDirFile.exists())
            {
                FileUtils.copyDirectory(
                        buildSourceDirFile, 
                        new File(helper.getJavascriptTargetPath()+'/'+relativeSourceDirectory), 
                        new FileFilter()
                {
                    
                    public boolean accept(File pathname)
                    {
                        if (pathname.getName().endsWith(".svn"))
                        {
                            return false;
                        }
                        return true;
                    }
                });
            }
        }

        if (resourceDirectory != null)
        {
            if (resourceDirectory.exists())
            {
                FileUtils.copyDirectory(
                        resourceDirectory, 
                        new File(helper.getJavascriptTargetPath()+'/'+relativeResourceDirectory), 
                        new FileFilter()
                {
                    
                    public boolean accept(File pathname)
                    {
                        if (pathname.getName().endsWith(".svn"))
                        {
                            return false;
                        }
                        if (pathname.isDirectory())
                        {
                            return true;
                        }
                        if (pathname.getName().endsWith(".js"))
                        {
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
        
        if (!StringUtils.isEmpty(webappDirectory))
        {
            File buildWebappSourceDirFile = new File(webappDirectory);
            if (buildWebappSourceDirFile.exists())
            {
                FileUtils.copyDirectory(
                        buildWebappSourceDirFile, 
                        new File(helper.getJavascriptTargetPath()+'/'+relativeWebappDirectory), 
                        new FileFilter()
                {
                    
                    public boolean accept(File pathname)
                    {
                        if (pathname.getName().endsWith(".svn"))
                        {
                            return false;
                        }
                        if (pathname.isDirectory())
                        {
                            return true;
                        }
                        if (pathname.getName().endsWith(".js"))
                        {
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
        
        getLog().info("[JSDOC] Copying done without any errors");
    }

    private final List _initArguments(JSDocHelper helper)
    {
        List args = new ArrayList();
        String runJsPath = helper.getJsdocEngineUnpacked() + File.separator + JSDocMojoConst.APP
                + File.separator + JSDocMojoConst.RUN_JS;
        args.add(runJsPath);

        if (this.includeUndocumented)
        {
            args.add(JSDocMojoConst.PARAM_UNDOCUMENTED);
        }
        if (this.includeUndocumentedUnderscored)
        {
            args.add(JSDocMojoConst.PARAM_UNDOCUMENTED_UNDERSCORED);
        }
        if (this.includePrivate)
        {
            args.add(JSDocMojoConst.PARAM_PRIVATE);
        }
        args.add(JSDocMojoConst.PARAM_OUTPUT + JSDocMojoConst.EQUALS + this.getOutputDirectory());
        args.add(JSDocMojoConst.PARAM_TEMPLATE + JSDocMojoConst.EQUALS + getTemplateDirectory(helper));

        args.addAll(fetchJavascriptSources(helper));
        //according to the run.js source the last argument
        //must be a -j param pointing to the JSDoc javascripts
        args.add(JSDocMojoConst.PARAM_JS_FLAG + JSDocMojoConst.EQUALS + runJsPath);
        return args;
    }

    /**
     * @return the directory as absolute path holding the JSDoc toolkit templates
     */
    private final String getTemplateDirectory(JSDocHelper helper)
    {
        return (JSDocMojoConst.TEMPLATES_JSDOC.equals(this.templates)) ?
                helper.getJsdocEngineUnpacked() + File.separator + this.templates :
                this.templates;
    }

    /**
     * @return the target directory for the JSDoc files
     */
    /*
    private final String getOutputDirectory()
    {
        return (this.outputDirectory == null || this.outputDirectory.equals("")) ?
                projectBuildDir + File.separator + JSDocMojoConst.JSDOC :
                this.outputDirectory;

    }*/

    /**
     * @return fetches the sources for the javascripts in the order given by the xml
     */
    private List<String> fetchJavascriptSources(JSDocHelper helper)
    {
        List<String> sources = null;
        getLog().info("[JSDOC] Fetch JavaScript sources for further processing");
        if (helper.getFileMap() == null)
        {
            sources = new ArrayList<String>();
            File javascriptTargetFileBase = new File(helper.getJavascriptTargetPath());
            addSources(sources, javascriptTargetFileBase);
            return sources;
        }
        else
        {
            JSFileNameFilter fileNameFilter = new JSFileNameFilter(helper.getFileMap());
            //FileUtils.iterateFiles(new File(getOutputDirectory()), fileNameFilter, TrueFileFilter.INSTANCE);
            FileUtils.iterateFiles(new File(helper.getJavascriptTargetPath()), fileNameFilter, TrueFileFilter.INSTANCE);
    
            Map sortedResult = fileNameFilter.getSortedResults();
            sources = new ArrayList(sortedResult.size());
            Iterator it = sortedResult.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry singleItem = (Map.Entry) it.next();
                String finalFileName = (String) singleItem.getValue();
                sources.add(finalFileName);
            }
        }
        getLog().info("[JSDOC] All JavaScript sources are prepared for processing");
        return sources;
    }
    
    private void addSources(List<String> sources, File file)
    {
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            if (files != null)
            {
                for (int i = 0; i < files.length; i++)
                {
                    addSources(sources, files[i]);
                }
            }
        }
        else
        {
            sources.add(file.getAbsolutePath());
        }
    }

    private void fixHTML(JSDocHelper helper)
    {
        FileUtils.iterateFiles(new File(getOutputDirectory()), 
                new HTMLFileContentFilter(helper.getJavascriptTargetPath()),
                TrueFileFilter.INSTANCE);
    }

}
