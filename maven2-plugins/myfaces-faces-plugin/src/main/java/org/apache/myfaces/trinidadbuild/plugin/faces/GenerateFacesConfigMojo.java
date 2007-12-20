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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.XIncludeFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal generate-faces-config
 * @phase generate-resources
 */
public class GenerateFacesConfigMojo extends AbstractFacesMojo
{
  /**
   * Execute the Mojo.
   */
  public void execute() throws MojoExecutionException
  {
    try
    {
      // always add resources directory to project resource root
      addResourceRoot(project, targetDirectory.getCanonicalPath());

      File targetFile = new File(targetDirectory, targetPath);

      URL[] index = readIndex(project);

      if (!force && !isModifiedSince(index, targetFile.lastModified()))
      {
        getLog().info("Nothing to generate - " + targetPath + " is up to date");
      }
      else
      {
        List configURLs = getMasterConfigs(project);

        File configFile = new File(configDirectory, configPath);
        if (configFile.exists())
        {
          configURLs = new LinkedList(configURLs);
          // base config goes first
          configURLs.add(0, configFile.toURL());
        }

        if (!configURLs.isEmpty())
        {
          URL baseURL = GenerateFacesConfigMojo.class.getResource("resources/faces-config.xml");
          DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
          Document document = docBuilder.parse(baseURL.openStream());
          Element root = document.getDocumentElement();

          // embed each <xi:include> element
          for (Iterator i = configURLs.iterator(); i.hasNext(); )
          {
            URL url = (URL)i.next();
            Element include = document.createElementNS("http://www.w3.org/2001/XInclude",
                                                       "xi:include");
            include.setAttribute("href", url.toExternalForm());
            include.setAttribute("xpointer", "/faces-config/*");
            root.insertBefore(include, root.getFirstChild());
          }

          // Note: use a direct DOM->SAX reader
          //       rather than transforming to string
          TransformerFactory transFactory = TransformerFactory.newInstance();
          Transformer identity = transFactory.newTransformer();
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          identity.transform(new DOMSource(document), new StreamResult(out));

          InputStream mergedStream = new ByteArrayInputStream(out.toByteArray());
          // expand all the xi:include elements
          SAXParserFactory saxFactory = SAXParserFactory.newInstance();
          saxFactory.setNamespaceAware(true);
          saxFactory.setValidating(false);
          SAXParser saxParser = saxFactory.newSAXParser();
          XMLReader mergedReader = saxParser.getXMLReader();
          mergedReader = new XIncludeFilter(mergedReader, baseURL);
          mergedReader.setEntityResolver(new EntityResolver()
            {
              public InputSource resolveEntity(
                String publicId,
                String systemId)
              {
                return new InputSource(new ByteArrayInputStream(new byte[0]));
              }
            });
          InputSource mergedInput = new InputSource(mergedStream);
          Source mergedSource = new SAXSource(mergedReader, mergedInput);

          // Transform the combined faces-config.xml file to resolve
          // component-supertype, inject the DTD and ensure that
          // metadata is represented compatibly with JSF 1.1 syntax.
          targetFile.delete();
          targetFile.getParentFile().mkdirs();

          File tmpFile = null;
          OutputStream resultStream;
          if (transformStylesheet != null)
          {
            tmpFile = File.createTempFile("generate-faces-config", null);
            resultStream = new FileOutputStream(tmpFile);
          }
          else
          {
            resultStream = new FileOutputStream(targetFile);
          }

          Result mergedResult = new StreamResult(resultStream);

          URL xslURL;
          if (_is12())
            xslURL = getClass().getResource("resources/transform12.xsl");
          else
            xslURL = getClass().getResource("resources/transform.xsl");

          InputStream xsl = xslURL.openStream();
          StreamSource xslSource = new StreamSource(xsl);
          Transformer transformer = transFactory.newTransformer(xslSource);
          transformer.setParameter("packageContains", packageContains);
          transformer.setParameter("typePrefix", typePrefix);
          transformer.setParameter("removeRenderers",
                                   removeRenderers ? "true" : "false");
          transformer.setParameter("converterPackageContains", getParameter(converterPackageContains, packageContains));
          transformer.setParameter("validatorPackageContains", getParameter(validatorPackageContains, packageContains));

          transformer.transform(mergedSource, mergedResult);
          resultStream.close();

          // OK, if there's a transformSylesheet, we've written
          // the output to a temporary file, now transform it again.
          // =-=FIXME AdamWiner:  no, this is not the smartest and
          // fastest way to do it.  But after finding out
          // that XMLFilter apparently doesn't support XSL parameters,
          // and running into a bizarre "illegal processing instruction:
          // saxon:warning" error message when using chained handlers, I got
          // tired of trying to be smart.  This is good enough.
          if (transformStylesheet != null)
          {
            StreamSource tmpSource = new StreamSource(tmpFile);
            StreamSource transformSource = new StreamSource(
                new File(configDirectory, transformStylesheet));
            Transformer finalTransform =
              transFactory.newTransformer(transformSource);
            FileOutputStream finalOut = new FileOutputStream(targetFile);
            Result finalResult =
              new StreamResult(finalOut);

            finalTransform.transform(tmpSource, finalResult);
            finalOut.close();
            tmpFile.delete();
          }

          
          targetFile.setReadOnly();

          getLog().info("Generated " + targetPath);
        }
      }
    }
    catch (SAXException e)
    {
      throw new MojoExecutionException("Error during generation", e);
    }
    catch (TransformerException e)
    {
      throw new MojoExecutionException("Error during generation", e);
    }
    catch (ParserConfigurationException e)
    {
      throw new MojoExecutionException("Error during generation", e);
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error during generation", e);
    }
  }

  private boolean _is12()
  {
    return "1.2".equals(jsfVersion) || "12".equals(jsfVersion);
  }

  private String getParameter(String paramName, String defaultValue)
  {
    String param;

    if (paramName.length() > 0)
    {
       param = paramName;
    }
    else
    {
        param = defaultValue;
    }
    return param;
  }

  /**
   * @parameter expression="${project}"
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter expression="src/main/conf"
   * @required
   */
  private File configDirectory;

  /**
   * @parameter
   */
  private String configPath = "META-INF/faces-config-base.xml";

  /**
   * @parameter
   */
  private String packageContains = "";

  /**
   * @parameter
   */
  private String converterPackageContains = "";

  /**
   * @parameter
   */
  private String validatorPackageContains = "";


  /**
   * @parameter expression="${project.build.directory}/maven-faces-plugin/main/resources"
   * @required
   */
  private File targetDirectory;

  /**
   * @parameter
   */
  private String targetPath = "META-INF/faces-config.xml";

  /**
   * @parameter
   */
  private boolean force;

  /**
   * @parameter
   */
  private String typePrefix = "";

  /**
   * Name of an XSLT stylesheet in src/main/conf that will be applied
   * as the final step.  This can be used to implement any final fixup
   * of the output.
   * @parameter
   */
  private String transformStylesheet;

  /**
   * @parameter
   */
  private boolean removeRenderers;

  /**
   * @parameter
   */
  private String jsfVersion;
}
