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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyModel;
import org.xml.sax.SAXException;

/**
 * Creates taglib (tld) and faces-config files.
 * 
 * @requiresDependencyResolution compile
 * @goal make-config
 * @phase generate-sources
 */
public class MakeConfigMojo extends AbstractMojo {
	final Logger log = Logger.getLogger(MakeConfigMojo.class.getName());

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
	private String metadataFile = "classes/META-INF/myfaces-metadata.xml";

	/**
	 * @parameter
	 */
	private String facesConfigFile = "classes/META-INF/standard-faces-config.xml";

	/**
	 * Execute the Mojo.
	 */
	public void execute() throws MojoExecutionException {
		try {
			Model model = readModel(project);
			generateConfig(model);
			throw new MojoExecutionException("Error during generation");
		} catch (IOException e) {
			throw new MojoExecutionException("Error generating components", e);
		}
	}

	/**
	 * Scan the source tree for annotations. Sets
	 */
	private Model readModel(MavenProject project) throws MojoExecutionException {
		File infile = new File(targetDirectory, metadataFile);
		try {
			FileReader xmlReader = new FileReader(infile);
			BeanReader beanReader = new BeanReader();
			beanReader.getXMLIntrospector().getConfiguration()
					.setAttributesForPrimitives(false);
			beanReader.getBindingConfiguration().setMapIDs(false);

			beanReader.registerBeanClass("model", Model.class);
			Model model = (Model) beanReader.parse(xmlReader);
			xmlReader.close();
			return model;
		} catch (FileNotFoundException e) {
			throw new MojoExecutionException("No metadata file:" + infile);
		} catch (IntrospectionException e) {
			throw new MojoExecutionException("Unable to load metadata", e);
		} catch (IOException e) {
			throw new MojoExecutionException("Unable to load metadata", e);
		} catch (SAXException e) {
			throw new MojoExecutionException("Unable to load metadata", e);
		}
	}

	/**
	 * Generates parsed components.
	 * 
	 * TODO: probably better to use Velocity or similar to generate the output
	 * from the model than direct print statements. Alternately, build a DOM
	 * representation of the model then apply an xslt stylesheet to the dom.
	 */
	private void generateConfig(Model model) throws IOException,
			MojoExecutionException {

		File targetFile = new File(targetDirectory, facesConfigFile);

		try {
			getLog().info("Generating " + targetFile);

			targetFile.delete();
			targetFile.getParentFile().mkdirs();

			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					targetFile));
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = outputFactory.createXMLStreamWriter(out);
			writer.writeStartDocument("1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement("faces-config");
			writer.writeDefaultNamespace("http://java.sun.com/xml/ns/javaee");
			writer.writeCharacters("\n");

			// TODO:
			// * APPLICATION element
			// * write application-listeners
			// * write navigation-handlers
			// * write view-handelrs
			// * write state-managers
			// * write supported-locales
			//
			// * FACTORY element
			// * faces-context-factory
			// * application-factory
			// * lifecycle-factory
			// * render-kit-factory

			writeConverters(model, writer);
			writeValidators(model, writer);
			writeComponents(model, writer);
			writeRenderers(model, writer);
			writeRenderKits(model, writer);

			writer.writeEndDocument();
			writer.close();

			targetFile.setReadOnly();
		} catch (XMLStreamException e) {
			throw new MojoExecutionException("Error during generation", e);
		} catch (IOException e) {
			throw new MojoExecutionException("Error during generation", e);
		} catch (Exception e) {
			throw new MojoExecutionException("Error during generation", e);
		}
	}

	private void writeConverters(Model model, XMLStreamWriter writer)
			throws Exception {

	}

	private void writeValidators(Model model, XMLStreamWriter writer)
			throws Exception {

	}

	private void writeComponents(Model model, XMLStreamWriter writer)
			throws Exception {
		for (Iterator i = model.components(); i.hasNext();) {
			ComponentModel cmp = (ComponentModel) i.next();
			writer.writeStartElement("component");
			writer.writeCharacters("\n");
			writer.writeStartElement("component-type");
			writer.writeCharacters(cmp.getComponentType());
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.writeStartElement("component-class");
			writer.writeCharacters(cmp.getComponentClass());
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.writeEndElement();
			writer.writeCharacters("\n");
		}
	}

	private void writeRenderers(Model model, XMLStreamWriter writer)
			throws Exception {

	}

	private void writeRenderKits(Model model, XMLStreamWriter writer)
			throws Exception {

	}
}
