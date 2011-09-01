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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.myfaces.plugins.jsdoc.util.HTMLFileContentFilter;
import org.apache.myfaces.plugins.jsdoc.util.XMLConfig;
import org.apache.myfaces.plugins.jsdoc.util.JSDocPack;
import org.apache.myfaces.plugins.jsdoc.util.JSDocPackMaven;
import org.apache.myfaces.plugins.jsdoc.util.JSFileNameFilter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          A simple jsdoc plugin which should cover our documentation needs
 *          Note this plugin is a simplified tailored derivate from
 *          <p/>
 *          http://www.abiss.gr some code stems from there.
 *          <p/>
 *          since we use jsdoc for now and are not in the reporting part
 *          a simple plugin suffices.
 * @goal jsdoc
 */
public class JSDocMojo extends AbstractMojo
{

    /**
     * the root project build dir (target directory)
     *
     * @parameter expression="${project.build.directory}"
     */
    String projectBuildDir;

    /**
     * The project source directory
     *
     * @parameter expression="${project.build.directory}/../src/main/javascript/META-INF/"
     */
    String buildSourceDirectory;

    /**
     * Path to the assembly file containing the file paths to our source javascript files
     *
     * @parameter expression="${project.build.directory}/../src/assembler/jsdoc-compiler.xml"
     */
    String assemblyFile;

    //various jsdoc params, copied over as well as the corresponding snippets from
    /**
     * Whether to include symbols tagged as private. Default is <code>false</code>.
     *
     * @parameter expression="false"
     */
    boolean includePrivate;

    /**
     * Include all functions, even undocumented ones. Default is <code>false</code>.
     *
     * @parameter expression="false"
     */
    boolean includeUndocumented;

    /**
     * Include all functions, even undocumented, underscored ones. Default is <code>false</code>.
     *
     * @parameter expression="false"
     */
    boolean includeUndocumentedUnderscored;

    /**
     * template directory used by jsdoc the default is <code>templates/jsdoc</code> under the jsdoc root
     *
     * @parameter expression="templates/jsdoc"
     */
    String templates;

    /**
     * output dir override
     *
     * @parameter expression=""
     */
    String outputDirectory;

    /**
     * the parsed xml filemap containing the single source files
     */
    XMLConfig fileMap;

    JSDocPack unpacker;

    /**
     * target path for the unpacked jsdoc engine
     */
    String jsdocEngineUnpacked = null;

    /**
     * target patchs for the javascript
     */
    String javascriptTargetPath = null;

    /**
     * run path for the jsdoc engine
     */
    String jsdocRunPath = null;

    protected void _setup() throws MojoExecutionException
    {
        try
        {
            fileMap = new XMLConfig(assemblyFile);
        }
        catch (XMLStreamException e)
        {
            getLog().error(e);
            throw new MojoExecutionException(e.toString());
        }
        catch (FileNotFoundException e)
        {
            getLog().error(e);
            throw new MojoExecutionException(e.toString());
        }
        unpacker = new JSDocPackMaven();
        //unpacker = new JSDocPackResources();

        jsdocRunPath = projectBuildDir + File.separator + JSDocMojoConst.JSDOC;
        jsdocEngineUnpacked = projectBuildDir + File.separator + JSDocMojoConst.TEMP
                + File.separator + JSDocMojoConst.JSDOC;

        javascriptTargetPath = jsdocRunPath + File.separator + JSDocMojoConst.JAVASCRIPT;

        File pathCreator = new File(jsdocEngineUnpacked);
        File jsdocPathCreator = new File(javascriptTargetPath);
        pathCreator.mkdirs();
        jsdocPathCreator.mkdirs();
    }

    public void _tearDown() throws MojoExecutionException
    {
        try
        {
            FileUtils.deleteDirectory(new File(jsdocEngineUnpacked));
        }
        catch (IOException e)
        {
            throw new MojoExecutionException(e.toString());
        }
    }

    protected void _execute() throws MojoExecutionException, IOException
    {

        copyJavascripts();

        fetchJavascriptSources();
        //now we have all files we now can now work on our plugin call
        unpackJSDoc();

        String systemJsdocDir = setenvJSDocDir();
        String userDir = setenvUserDir();
        try
        {
            executeJSDoc();
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

    private void executeJSDoc()
    {
        List args = _initArguments();

        getLog().info("[JSDOC] Executing within maven: '" + args.toString().replaceAll(",", "") + "'");

        // tell Rhino to run JSDoc with the provided params
        // without calling System.exit

        org.mozilla.javascript.tools.shell.Main.main((String[]) args.toArray(new String[0]));

        this.fixHTML();
    }

    private String setenvUserDir()
    {
        String userDir = System.getProperty("user.dir");
        System.setProperty("user.dir", jsdocEngineUnpacked + File.separator);
        return userDir;
    }

    private String setenvJSDocDir()
    {
        String systemJsdocDir = System.getProperty(JSDocMojoConst.JSDOC_DIR);
        System.setProperty(JSDocMojoConst.JSDOC_DIR, jsdocEngineUnpacked + File.separator);
        return systemJsdocDir;
    }

    private void unpackJSDoc() throws IOException
    {
        getLog().info("[JSDOC] Unpacking jsdoc toolkit for further processing");
        unpacker.unpack(jsdocEngineUnpacked, getLog());
        getLog().info("[JSDOC] Unpacking jsdoc toolkit for further processing done");
    }

    /**
     * initially copies all source files from the given source dir to the target
     * dir so that the files can be referenced later on by the html files
     */
    private void copyJavascripts() throws IOException
    {
        getLog().info("[JSDOC] Copying all javascript sources to the target dir for later reference");
        FileUtils.copyDirectory(new File(buildSourceDirectory), new File(javascriptTargetPath), new FileFilter()
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
        getLog().info("[JSDOC] Copying done without any errors");
    }

    private final List _initArguments()
    {
        List args = new ArrayList();
        String runJsPath = jsdocEngineUnpacked + File.separator + JSDocMojoConst.APP
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
        args.add(JSDocMojoConst.PARAM_TEMPLATE + JSDocMojoConst.EQUALS + getTemplateDirectory());

        args.addAll(fetchJavascriptSources());
        //according to the run.js source the last argument
        //must be a -j param pointing to the jsdoc javascripts
        args.add(JSDocMojoConst.PARAM_JS_FLAG + JSDocMojoConst.EQUALS + runJsPath);
        return args;
    }

    /**
     * @return the directory as absolute path holding the jsdoc toolkit templates
     */
    private final String getTemplateDirectory()
    {
        return (JSDocMojoConst.TEMPLATES_JSDOC.equals(this.templates)) ?
                this.jsdocEngineUnpacked + File.separator + this.templates :
                this.templates;
    }

    /**
     * @return the target directory for the jsdoc files
     */
    private final String getOutputDirectory()
    {
        return (this.outputDirectory == null || this.outputDirectory.equals("")) ?
                projectBuildDir + File.separator + JSDocMojoConst.JSDOC :
                this.outputDirectory;

    }

    /**
     * @return fetches the sources for the javascripts in the order given by the xml
     */
    private List fetchJavascriptSources()
    {
        getLog().info("[JSDOC] Fetch Javascript sources for further processing");
        JSFileNameFilter fileNameFilter = new JSFileNameFilter(fileMap);
        FileUtils.iterateFiles(new File(getOutputDirectory()), fileNameFilter, TrueFileFilter.INSTANCE);

        Map sortedResult = fileNameFilter.getSortedResults();
        List sources = new ArrayList(sortedResult.size());
        Iterator it = sortedResult.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry singleItem = (Map.Entry) it.next();
            String finalFileName = (String) singleItem.getValue();
            sources.add(finalFileName);
        }
        getLog().info("[JSDOC] All Javascript sources are prepared for processing");
        return sources;
    }

    private void fixHTML()
    {
        FileUtils.iterateFiles(new File(getOutputDirectory()), new HTMLFileContentFilter(getOutputDirectory()),
                TrueFileFilter.INSTANCE);
    }

    public void execute() throws MojoExecutionException
    {
        _setup();
        try
        {
            _execute();
        }
        catch (IOException e)
        {
            throw new MojoExecutionException(e.toString());
        }
        finally
        {
            _tearDown();
        }

    }

}
