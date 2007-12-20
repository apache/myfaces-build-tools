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
package org.apache.myfaces.trinidadbuild.plugin.faces;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal generate-master-faces-config
 * @phase generate-resources
 */
public class GenerateMasterFacesConfigMojo extends AbstractFacesMojo
{
  /**
   * Execute the Mojo.
   */
  public void execute() throws MojoExecutionException
  {
    try
    {
      addResourceRoot(project, targetDirectory.getCanonicalPath());

      // Scan for .xrts sources
      DirectoryScanner scanner = new DirectoryScanner();
      scanner.setBasedir(new File(sourceDirectory, sourcePath));
      scanner.addDefaultExcludes();
      scanner.setIncludes(new String[] { "**/*.xml" });
      if (excludes != null)
        scanner.setExcludes(excludes);
      scanner.scan();

      String[] xmlFiles = scanner.getIncludedFiles();

      long lastModified = 0L;
      for (int i=0; i < xmlFiles.length; i++)
      {
        lastModified = Math.max(lastModified,
                                new File(xmlFiles[i]).lastModified());
      }

      File targetFile = new File(targetDirectory, targetPath);

      if (!force && targetFile.lastModified() > lastModified)
      {
        getLog().info("Nothing to generate - " + targetPath + " is up to date");
      }
      else
      {
        getLog().info("Generating " + targetPath);

        targetFile.delete();
        targetFile.getParentFile().mkdirs();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile));
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer =  outputFactory.createXMLStreamWriter(out);
        writer.writeStartDocument("1.0");
        writer.writeCharacters("\n");
        writer.writeStartElement("faces-config");
        writer.writeDefaultNamespace("http://java.sun.com/xml/ns/javaee");
        writer.writeNamespace("xi", "http://www.w3.org/2001/XInclude");
        writer.writeCharacters("\n");
        for (int i=0; i < xmlFiles.length; i++)
        {
          String xmlFile = xmlFiles[i];
          if (File.separatorChar == '\\')
            xmlFile = xmlFile.replaceAll("\\\\", "/");
          writer.writeCharacters("  ");
          writer.writeStartElement("http://www.w3.org/2001/XInclude", "include");
          writer.writeAttribute("href", xmlFile);
          writer.writeAttribute("xpointer", "/faces-config/*");
          writer.writeEndElement();
          writer.writeCharacters("\n");
        }
        writer.writeEndDocument();
        writer.close();

        targetFile.setReadOnly();
      }
    }
    catch (XMLStreamException e)
    {
      throw new MojoExecutionException("Error during generation", e);
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error during generation", e);
    }
  }

  /**
   * @parameter expression="${project}"
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter
   */
  private String[] excludes;

 /**
   * @parameter expression="META-INF/maven-faces-plugin";
   * @required
   */
  private String sourcePath;

  /**
   * @parameter expression="src/main/resources"
   * @required
   */
  private File sourceDirectory;

  /**
   * @parameter
   * @readonly
   */
  private String targetPath = "META-INF/maven-faces-plugin/faces-config.xml";

  /**
   * @parameter expression="${project.build.directory}/maven-faces-plugin/main/resources"
   * @required
   */
  private File targetDirectory;

  /**
   * @parameter
   */
  private boolean force;
}
