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
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.doxia.module.xhtml.decoration.render.RenderingContext;
import org.apache.maven.doxia.siterenderer.sink.SiteRendererSink;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.reporting.MavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.sink.Sink;

/**
 * <p/>
 * A simple jsdoc plugin which should cover our documentation needs
 * Note this plugin is a simplified tailored derivate from
 * <p/>
 * http://www.abiss.gr some code stems from there.
 * <p/>
 * since we use jsdoc for now and are not in the reporting part
 * a simple plugin suffices.
 *          
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 * @goal jsdocreport
 * @execute phase="generate-resources"
 */
public class JSDocReportMojo extends AbstractJSDocMojo implements MavenReport
{
    // ----------------------------------------------------------------------
    // Report Mojo Parameters
    // ----------------------------------------------------------------------

    /**
     * Specifies the destination directory where jsdoc saves the generated HTML files.
     *
     * @parameter expression="${reportOutputDirectory}" default-value="${project.reporting.outputDirectory}/jsdoc"
     * @required
     */
    private File reportOutputDirectory;

    /**
     * The name of the destination directory.
     * <br/>
     *
     * @since 2.1
     * @parameter expression="${destDir}" default-value="jsdoc"
     */
    private String destDir;

    /**
     * The name of the Javadoc report to be displayed in the Maven Generated Reports page
     * (i.e. <code>project-reports.html</code>).
     *
     * @since 2.1
     * @parameter expression="${name}"
     */
    private String name;

    /**
     * The description of the Javadoc report to be displayed in the Maven Generated Reports page
     * (i.e. <code>project-reports.html</code>).
     *
     * @since 2.1
     * @parameter expression="${description}"
     */
    private String description;

    // ----------------------------------------------------------------------
    // Report public methods
    // ----------------------------------------------------------------------

    /** {@inheritDoc} */
    public String getName( Locale locale )
    {
        if ( StringUtils.isEmpty( name ) )
        {
            //return getBundle( locale ).getString( "report.jsdoc.name" );
            return "JSDoc Report";
        }

        return name;
    }

    /** {@inheritDoc} */
    public String getDescription( Locale locale )
    {
        if ( StringUtils.isEmpty( description ) )
        {
            //return getBundle( locale ).getString( "report.jsdoc.description" );
            return "Javascript report using JSDoc";
        }

        return description;
    }

    /** {@inheritDoc} */
    public void generate( Sink sink, Locale locale )
        throws MavenReportException
    {
        outputDirectory = getReportOutputDirectory();

        try
        {
            executeReport( locale );
        }
        catch ( MavenReportException e )
        {
            getLog().error( "Error while creating jsdoc report: " + e.getMessage(), e );
        }
        catch ( RuntimeException e )
        {
            getLog().error( "Error while creating jsdoc report: " + e.getMessage(), e );
        }
    }

    /** {@inheritDoc} */
    public String getOutputName()
    {
        return destDir + "/index";
    }

    /** {@inheritDoc} */
    public boolean isExternalReport()
    {
        return true;
    }

    public boolean canGenerateReport()
    {
        return true;
    }

    /** {@inheritDoc} */
    public String getCategoryName()
    {
        return CATEGORY_PROJECT_REPORTS;
    }

    /** {@inheritDoc} */
    public File getReportOutputDirectory()
    {
        if ( reportOutputDirectory == null )
        {
            return outputDirectory;
        }

        return reportOutputDirectory;
    }

    /**
     * Method to set the directory where the generated reports will be put
     *
     * @param reportOutputDirectory the directory file to be set
     */
    public void setReportOutputDirectory( File reportOutputDirectory )
    {
        updateReportOutputDirectory( reportOutputDirectory, destDir );
    }

    public void setDestDir( String destDir )
    {
        this.destDir = destDir;
        updateReportOutputDirectory( reportOutputDirectory, destDir );
    }

    private void updateReportOutputDirectory( File reportOutputDirectory, String destDir )
    {
        if ( reportOutputDirectory != null && destDir != null
             && !reportOutputDirectory.getAbsolutePath().endsWith( destDir ) )
        {
            this.reportOutputDirectory = new File( reportOutputDirectory, destDir );
        }
        else
        {
            this.reportOutputDirectory = reportOutputDirectory;
        }
    }

    /**
     * Gets the resource bundle for the specified locale.
     *
     * @param locale The locale of the currently generated report.
     * @return The resource bundle for the requested locale.
     */
    //private ResourceBundle getBundle( Locale locale )
    //{
    //    return ResourceBundle.getBundle( "jsdoc-report", locale, getClass().getClassLoader() );
    //}
    
    public void execute() throws MojoExecutionException
    {
        if ( skip )
        {
            getLog().info( "Skipping jsdoc generation" );
            return;
        }
        
        try
        {
            executeReport(Locale.US);
        }
        catch ( MavenReportException e )
        {
            getLog().error("MavenReportException: Error while creating archive", e );
        }
        catch ( RuntimeException e )
        {
            getLog().error("RuntimeException: Error while creating archive", e );
        }
        
        try
        {
            RenderingContext context = new RenderingContext( outputDirectory, getOutputName() + ".html" );
            SiteRendererSink sink = new SiteRendererSink( context );
            Locale locale = Locale.getDefault();
            generate( sink, locale );
        }
        catch ( MavenReportException e )
        {
            getLog().error("An error has occurred in " + getName( Locale.ENGLISH ) + " report generation", e );
        }
        catch ( RuntimeException e )
        {
            getLog().error("An error has occurred in " + getName( Locale.ENGLISH ) + " report generation", e );
        }

    }

}
