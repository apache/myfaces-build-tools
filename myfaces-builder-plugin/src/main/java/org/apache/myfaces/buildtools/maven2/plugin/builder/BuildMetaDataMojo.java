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

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxModelBuilder;
import org.xml.sax.SAXException;

/**
 * Creates myfaces-metadata.xml file.
 * 
 * @requiresDependencyResolution compile
 * @goal build-metadata
 * @phase generate-sources
 */
public class BuildMetaDataMojo extends AbstractMojo {
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
	public void execute() throws MojoExecutionException {
		Model model = buildModel(project);
		saveModel(model);
	}

	/**
	 * Execute ModelBuilder classes to create the Model data-structure.
	 */
	private Model buildModel(MavenProject project)
			throws MojoExecutionException {
		Model model = new Model();
		QdoxModelBuilder builder = new QdoxModelBuilder();
		builder.buildModel(model, project);
		return model;
	}

	/**
	 * Write the contents of the model to an xml file.
	 */
	private void saveModel(Model model) throws MojoExecutionException {
		File outfile = new File(targetDirectory, outputFile);
		FileWriter outputWriter = null;

		try {
			log.info("Writing file " + outfile);
			outfile.getParentFile().mkdirs();

			outputWriter = new FileWriter(outfile);
			outputWriter.write("<?xml version='1.0' ?>\n");
			BeanWriter beanWriter = new BeanWriter(outputWriter);

			beanWriter.getXMLIntrospector().getConfiguration()
					.setAttributesForPrimitives(false);
			beanWriter.getBindingConfiguration().setMapIDs(false);
			beanWriter.enablePrettyPrint();

			beanWriter.write("model", model);
		} catch (IntrospectionException e) {
			throw new MojoExecutionException("Unable to save data", e);
		} catch (IOException e) {
			throw new MojoExecutionException("Unable to save data", e);
		} catch (SAXException e) {
			throw new MojoExecutionException("Unable to save data", e);
		} finally {
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
