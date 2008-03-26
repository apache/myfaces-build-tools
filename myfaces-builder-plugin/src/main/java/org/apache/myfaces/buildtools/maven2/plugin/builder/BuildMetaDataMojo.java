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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxModelBuilder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.utils.BuildException;

/**
 * Maven goal which runs one or more ModelBuilder objects to gather metadata
 * about JSF artifacts into a Model object, then save that model object as an
 * xml file for use by other goals of this plugin.
 * <p>
 * By default, the generated file is named "META-INF/myfaces-metadata.xml".
 * 
 * @requiresDependencyResolution compile
 * @goal build-metadata
 * @phase generate-sources
 */
public class BuildMetaDataMojo extends AbstractMojo
{
    final Log log = LogFactory.getLog(BuildMetaDataMojo.class.getName());

    /**
     * Injected Maven project object.
     * 
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * Injected build directory for all generated stuff.
     * 
     * @parameter expression="${project.build.directory}"
     */
    private File targetDirectory;

    /**
     * Injected name of file to generate, relative to targetDirectory.
     * 
     * @parameter
     */
    private String outputFile = "classes/META-INF/myfaces-metadata.xml";

    /**
     * Execute the Mojo.
     */
    public void execute() throws MojoExecutionException
    {
        Model model = buildModel(project);
        IOUtils.saveModel(model, new File(targetDirectory, outputFile));
    }

    /**
     * Execute ModelBuilder classes to create the Model data-structure.
     */
    private Model buildModel(MavenProject project)
            throws MojoExecutionException
    {
        try
        {
            Model model = new Model();
            QdoxModelBuilder builder = new QdoxModelBuilder();
            builder.buildModel(model, project);
            return model;
        }
        catch (BuildException e)
        {
            throw new MojoExecutionException("Unable to build metadata", e);
        }
    }
}
