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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxModelBuilder;

/**
 * Creates myfaces-metadata.xml file.
 * 
 * @requiresDependencyResolution compile
 * @goal build-metadata
 * @phase generate-sources
 */
public class BuildMetaDataMojo extends AbstractMojo
{
    final Log log = LogFactory.getLog(BuildMetaDataMojo.class.getName());

    /**
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter expression="${project.build.directory}"
     */
    private File targetDirectory;

    /**
     * @parameter
     */
    private String outputFile = "classes/META-INF/myfaces-metadata.xml";

    /**
     * Execute the Mojo.
     */
    public void execute() throws MojoExecutionException
    {
        Model model = buildModel(project);
        saveModel(model);
    }

    /**
     * Execute ModelBuilder classes to create the Model data-structure.
     */
    private Model buildModel(MavenProject project)
            throws MojoExecutionException
    {
        Model model = new Model();
        QdoxModelBuilder builder = new QdoxModelBuilder();
        builder.buildModel(model, project);
        return model;
    }

    /**
     * Write the contents of the model to an xml file.
     */
    void saveModel(Model model) throws MojoExecutionException
    {
        File outfile = new File(targetDirectory, outputFile);
        FileWriter outputWriter = null;

        try
        {
            log.info("Writing file " + outfile);
            outfile.getParentFile().mkdirs();

            outputWriter = new FileWriter(outfile);
            writeModel(outputWriter, model);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Unable to save data", e);
        }
        finally
        {
            try
            {
                if (outputWriter != null)
                {
                    outputWriter.close();
                }
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }
    
    void writeModel(Writer outputWriter, Model model)  throws MojoExecutionException
    {
        try
        {
            outputWriter.write("<?xml version='1.0' ?>\n");
            PrintWriter pw = new PrintWriter(outputWriter);
            XmlWriter xmlWriter = new XmlWriter(pw);
            Model.writeXml(xmlWriter, model);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Unable to save data", e);
        }
        finally
        {
            try
            {
                if (outputWriter != null)
                {
                    outputWriter.close();
                }
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }
}
