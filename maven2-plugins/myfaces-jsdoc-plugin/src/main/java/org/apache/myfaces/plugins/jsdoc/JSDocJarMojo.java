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
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProjectHelper;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.jar.ManifestException;

/**
 * @author  Werner Punz
 *          Leonardo Uribe
 * @version 1.0.0
 *          <p/>
 *          A simple jsdoc plugin which should cover our documentation needs
 *          Note this plugin is a simplified tailored derivative from
 *          <p/>
 *          http://www.abiss.gr some code stems from there.
 *          <p/>
 *          since we use jsdoc for now and are not in the reporting part
 *          a simple plugin suffices.
 * @goal jar
 * @phase package
 */
public class JSDocJarMojo extends AbstractJSDocMojo
{

    
    
    /** Includes all generated Javascript doc files */
    private static final String[] DEFAULT_INCLUDES = new String[] { "**/**" };

    /**
     * Excludes none
     */
    private static final String[] DEFAULT_EXCLUDES = new String[] {};

    
    // ----------------------------------------------------------------------
    // Mojo components
    // ----------------------------------------------------------------------

    /**
     * Used for attaching the artifact in the project.
     *
     * @component
     */
    private MavenProjectHelper projectHelper;

    /**
     * The Jar archiver.
     *
     * @component role="org.codehaus.plexus.archiver.Archiver" roleHint="jar"
     * @since 2.5
     */
    private JarArchiver jarArchiver;
    
    /**
     * The archive configuration to use.
     * See <a href="http://maven.apache.org/shared/maven-archiver/index.html">Maven Archiver Reference</a>.
     *
     * @parameter
     * @since 2.5
     */
    private MavenArchiveConfiguration archive = new MavenArchiveConfiguration();
    
    // ----------------------------------------------------------------------
    // Parameters
    // ----------------------------------------------------------------------

    /**
     * Specifies the directory where the generated jar file will be put.
     *
     * @parameter expression="${project.build.directory}"
     */
    private String jarOutputDirectory;
    
    /**
     * Specifies the filename that will be used for the generated jar file. Please note that <code>-jsdoc</code>
     * or <code>-test-jsdoc</code> will be appended to the file name.
     *
     * @parameter expression="${project.build.finalName}"
     */
    private String finalName;
    
    /**
     * Specifies whether to attach the generated artifact to the project helper.
     * <br/>
     *
     * @parameter expression="${attach}" default-value="true"
     */
    private boolean attach;
    
    /**
     * Path to the default MANIFEST file to use. It will be used if
     * <code>useDefaultManifestFile</code> is set to <code>true</code>.
     *
     * @parameter expression="${project.build.outputDirectory}/META-INF/MANIFEST.MF"
     * @required
     * @readonly
     * @since 1.0
     */
    private File defaultManifestFile;

    /**
     * Set this to <code>true</code> to enable the use of the <code>defaultManifestFile</code>.
     * <br/>
     *
     * @parameter default-value="false"
     * @since 1.0
     */
    private boolean useDefaultManifestFile;

    public void execute() throws MojoExecutionException
    {
        if ( skip )
        {
            getLog().info( "Skipping jsdoc generation" );
            return;
        }
    
        File innerDestDir = new File( getOutputDirectory() );
   
        try
        {
            executeReport(Locale.US);
            if ( innerDestDir.exists() )
            {
                File outputFile = generateArchive( innerDestDir, finalName + "-" + getClassifier() + ".jar" );
    
                if ( !attach )
                {
                    getLog().info( "NOT adding jsdoc to attached artifacts list." );
                }
                else
                {
                    // TODO: these introduced dependencies on the project are going to
                    // become problematic - can we export it
                    //  through metadata instead?
                    projectHelper.attachArtifact( project, "jar", getClassifier(), outputFile );
                }
            }
        }
        catch ( ArchiverException e )
        {
            getLog().error("ArchiverException: Error while creating archive", e );
        }
        catch ( IOException e )
        {
            getLog().error("IOException: Error while creating archive", e );
        }
        catch ( MavenReportException e )
        {
            getLog().error("MavenReportException: Error while creating archive", e );
        }
        catch ( RuntimeException e )
        {
            getLog().error("RuntimeException: Error while creating archive", e );
        }
    }
    
    // ----------------------------------------------------------------------
    // Protected methods
    // ----------------------------------------------------------------------

    /**
     * @return the wanted classifier, i.e. <code>jsdoc</code> or <code>test-jsdoc</code>
     */
    protected String getClassifier()
    {
        return "jsdoc";
    }
    
    // ----------------------------------------------------------------------
    // private methods
    // ----------------------------------------------------------------------

    /**
     * Method that creates the jar file
     *
     * @param jsdocFiles the directory where the generated jar file will be put
     * @param jarFileName the filename of the generated jar file
     * @return a File object that contains the generated jar file
     * @throws ArchiverException if any
     * @throws IOException if any
     */
    private File generateArchive( File jsdocFiles, String jarFileName )
        throws ArchiverException, IOException
    {
        File jsdocJar = new File( jarOutputDirectory, jarFileName );

        if ( jsdocJar.exists() )
        {
            jsdocJar.delete();
        }

        MavenArchiver archiver = new MavenArchiver();
        archiver.setArchiver( jarArchiver );
        archiver.setOutputFile( jsdocJar );

        File contentDirectory = jsdocFiles;
        if ( !contentDirectory.exists() )
        {
            getLog().warn( "JAR will be empty - no content was marked for inclusion!" );
        }
        else
        {
            archiver.getArchiver().addDirectory( contentDirectory, DEFAULT_INCLUDES, DEFAULT_EXCLUDES );
        }

        List<Resource> resources = project.getBuild().getResources();

        for ( Resource r : resources )
        {
            if ( r.getDirectory().endsWith( "maven-shared-archive-resources" ) )
            {
                archiver.getArchiver().addDirectory( new File( r.getDirectory() ) );
            }
        }

        if ( useDefaultManifestFile && defaultManifestFile.exists() && archive.getManifestFile() == null )
        {
            getLog().info( "Adding existing MANIFEST to archive. Found under: " + defaultManifestFile.getPath() );
            archive.setManifestFile( defaultManifestFile );
        }

        try
        {
            // we don't want Maven stuff
            archive.setAddMavenDescriptor( false );
            archiver.createArchive( project, archive );
        }
        catch ( ManifestException e )
        {
            throw new ArchiverException( "ManifestException: " + e.getMessage(), e );
        }
        catch ( DependencyResolutionRequiredException e )
        {
            throw new ArchiverException( "DependencyResolutionRequiredException: " + e.getMessage(), e );
        }

        return jsdocJar;
    }

}