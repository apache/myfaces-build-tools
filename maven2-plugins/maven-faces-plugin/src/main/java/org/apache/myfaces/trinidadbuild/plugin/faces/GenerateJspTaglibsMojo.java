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
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.AbstractConverterTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.AbstractValidatorTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.ComponentTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.MyFacesComponentTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.MyFacesConverterTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.MyFacesValidatorTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.TagAttributeFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.TrinidadComponentTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.TrinidadConverterTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib.TrinidadValidatorTagGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ComponentBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ConverterBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.FacesConfigBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.MethodSignatureBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ValidatorBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.ComponentFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.ConverterFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.ValidatorFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.XIncludeFilter;
import org.codehaus.plexus.util.FileUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal generate-jsp-taglibs
 * @phase generate-sources
 */
public class GenerateJspTaglibsMojo extends AbstractFacesMojo
{
  /**
   * Execute the Mojo.
   */
  public void execute() throws MojoExecutionException
  {
    try
    {
      processIndex(project, resourcePath);
      _generateTagHandlers();
      _generateTagLibraryDescriptors();
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error generating components", e);
    }
  }


  // hook for custom component tag java content
  protected void writeCustomComponentTagHandlerContent(
      PrettyWriter  out,
      ComponentBean component) throws IOException
  {
  }

  // hook for custom component tag java imports
  protected void addCustomComponentTagHandlerImports(
      Set           imports,
      ComponentBean component)
  {
  }

  // hook for custom component descriptor content
  protected void writeCustomComponentTagDescriptorContent(
      XMLStreamWriter  stream,
      ComponentBean    component)throws XMLStreamException
  {
  }


  /**
   * Generates tag library descriptors for parsed component metadata.
   */
  private void _generateTagLibraryDescriptors() throws MojoExecutionException
  {
    try
    {
      // always add resources directory to project resource root
      addResourceRoot(project, generatedResourcesDirectory.getCanonicalPath());

      // taglibs map syntax requires distinct shortNames,
      // which is a Good Thing!
      for (Iterator i = taglibs.entrySet().iterator(); i.hasNext(); )
      {
        Map.Entry entry = (Map.Entry)i.next();
        String shortName = (String)entry.getKey();
        String namespaceURI = (String)entry.getValue();

        FacesConfigBean facesConfig = getFacesConfig();
        Iterator components = facesConfig.components();
        components = new FilteredIterator(components, new SkipFilter());
        components = new FilteredIterator(components, new ComponentTagLibraryFilter(namespaceURI));

        Iterator validators = facesConfig.validators();
        validators = new FilteredIterator(validators, new ValidatorTagLibraryFilter(namespaceURI));

        Iterator converters = facesConfig.converters();
        converters = new FilteredIterator(converters, new ConverterTagLibraryFilter(namespaceURI));

        String targetPath = "META-INF/" + shortName + ".tld";
        File targetFile = new File(generatedResourcesDirectory, targetPath);

        String configPath = "META-INF/" + shortName + "-base.tld";
        File configFile = new File(configSourceDirectory, configPath);

        targetFile.delete();

        boolean hasGeneratedTags = (components.hasNext() ||
                                    converters.hasNext() ||
                                    validators.hasNext());

        if (hasGeneratedTags && configFile.exists())
        {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
          XMLStreamWriter stream = outputFactory.createXMLStreamWriter(out);

          _writeStartTagLibrary(stream, _XINCLUDE_JSP_TAG_LIBRARY_DTD);
          // base goes first
          stream.writeStartElement("xi", "include",
                                   XIncludeFilter.XINCLUDE_NAMESPACE);
          stream.writeNamespace("xi", XIncludeFilter.XINCLUDE_NAMESPACE);
          stream.writeAttribute("href", configFile.toURL().toExternalForm());
          stream.writeAttribute("xpointer", "/taglib/*");
          stream.writeEndElement();
          while (components.hasNext())
          {
            ComponentBean component = (ComponentBean)components.next();
            _writeTag(stream, component);
          }
          while (converters.hasNext())
          {
            ConverterBean converter = (ConverterBean)converters.next();
            _writeTag(stream, converter);
          }
          while (validators.hasNext())
          {
            ValidatorBean validator = (ValidatorBean)validators.next();
            _writeTag(stream, validator);
          }
          _writeEndTagLibrary(stream);
          stream.close();

          InputStream mergedStream = new ByteArrayInputStream(out.toByteArray());

          // expand all the xi:include elements
          SAXParserFactory saxFactory = SAXParserFactory.newInstance();
          saxFactory.setNamespaceAware(true);
          saxFactory.setValidating(false);
          SAXParser saxParser = saxFactory.newSAXParser();
          XMLReader mergedReader = saxParser.getXMLReader();
          mergedReader = new XIncludeFilter(mergedReader, configFile.toURL());
          // even with validating=false, DTD is still downloaded so that
          // any entities contained in the document can be expanded.
          // the following disables that behavior, also saving the time
          // spent to parse the DTD
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

          targetFile.delete();
          targetFile.getParentFile().mkdirs();
          Result mergedResult = new StreamResult(new FileOutputStream(targetFile));

          TransformerFactory transFactory = TransformerFactory.newInstance();
          Transformer identity = transFactory.newTransformer();
          if (!_is12())
          {
            identity.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
                                       _JSP_TAG_LIBRARY_DOCTYPE_PUBLIC);
            identity.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
                                       _JSP_TAG_LIBRARY_DOCTYPE_SYSTEM);
          }

          identity.transform(mergedSource, mergedResult);

          targetFile.setReadOnly();
        }
        else if (hasGeneratedTags)
        {
          targetFile.getParentFile().mkdirs();
          OutputStream out = new FileOutputStream(targetFile);
          XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
          XMLStreamWriter stream = outputFactory.createXMLStreamWriter(out);

          _writeStartTagLibrary(stream, _is12() ? "2.1" : "1.2", shortName, namespaceURI);
          while (components.hasNext())
          {
            ComponentBean component = (ComponentBean)components.next();
            _writeTag(stream, component);
          }
          while (converters.hasNext())
          {
            ConverterBean converter = (ConverterBean)converters.next();
            _writeTag(stream, converter);
          }
          while (validators.hasNext())
          {
            ValidatorBean validator = (ValidatorBean)validators.next();
            _writeTag(stream, validator);
          }
          _writeEndTagLibrary(stream);
          stream.close();
        }
        else if (configFile.exists())
        {
          // copy if newer
          if (configFile.lastModified() > targetFile.lastModified())
          {
            targetFile.delete();
            targetFile.getParentFile().mkdirs();
            FileUtils.copyFile(configFile, targetFile);
            targetFile.setReadOnly();
          }
        }
      }
    }
    catch (SAXException e)
    {
      throw new MojoExecutionException("Error generating tag library", e);
    }
    catch (ParserConfigurationException e)
    {
      throw new MojoExecutionException("Error generating tag library", e);
    }
    catch (TransformerException e)
    {
      throw new MojoExecutionException("Error generating tag library", e);
    }
    catch (XMLStreamException e)
    {
      throw new MojoExecutionException("Error generating tag library", e);
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error generating tag libraries", e);
    }
  }

  private void _writeStartTagLibrary(
    XMLStreamWriter stream,
    String          dtd) throws XMLStreamException
  {
    stream.writeStartDocument("1.0");
    stream.writeCharacters("\n");
    if (!_is12())
      stream.writeDTD(dtd);

    stream.writeCharacters("\n");
    stream.writeStartElement("taglib");
    if (_is12())
    {
      stream.writeNamespace("", "http://java.sun.com/xml/ns/javaee");
      stream.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
      stream.writeAttribute("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd");
      stream.writeAttribute("version", "2.1");
    }

    stream.writeCharacters("\n  ");
  }

  private void _writeStartTagLibrary(
    XMLStreamWriter stream,
    String          version,
    String          shortName,
    String          namespaceURI) throws XMLStreamException
  {
    _writeStartTagLibrary(stream, _JSP_TAG_LIBRARY_DTD);
    stream.writeStartElement("tlib-version");
    String tlibVersion = project.getVersion();
    // Remove everything but dewey-decimal characters (i.e., numbers and periods)
    tlibVersion = tlibVersion.replaceAll("[^.0-9]", "");
    // Remove leading and/or trailing periods
    while (tlibVersion.startsWith("."))
    {
      tlibVersion = tlibVersion.substring(1);
    }

    while (tlibVersion.endsWith("."))
    {
      tlibVersion = tlibVersion.substring(0, tlibVersion.length() - 1);
    }

    stream.writeCharacters(tlibVersion);
    stream.writeEndElement();

    if (!_is12())
    {
      stream.writeCharacters("\n  ");
      stream.writeStartElement("jsp-version");
      stream.writeCharacters(version);
      stream.writeEndElement();
    }
    stream.writeCharacters("\n  ");
    stream.writeStartElement("short-name");
    stream.writeCharacters(shortName);
    stream.writeEndElement();
    stream.writeCharacters("\n  ");
    stream.writeStartElement("uri");
    stream.writeCharacters(namespaceURI);
    stream.writeEndElement();
  }

  private void _writeEndTagLibrary(
    XMLStreamWriter stream) throws XMLStreamException
  {

    stream.writeCharacters("\n");
    stream.writeEndElement();
    stream.writeEndDocument();
  }

  /**
   * Generates tag library descriptor for parsed component metadata.
   */
  private void _writeTag(
    XMLStreamWriter stream,
    ComponentBean   component) throws XMLStreamException
  {
    stream.writeCharacters("\n  ");
    stream.writeStartElement("tag");
    stream.writeCharacters("\n    ");

    // In JSP 2.1, description goes up top
    if (_is12() && component.getDescription() != null)
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("description");
      stream.writeCData(component.getDescription());
      stream.writeEndElement();
    }

    stream.writeStartElement("name");
    stream.writeCharacters(component.getTagName().getLocalPart());
    stream.writeEndElement();
    stream.writeCharacters("\n    ");
    stream.writeStartElement("tag-class");
    stream.writeCharacters(component.getTagClass());
    stream.writeEndElement();

    // In JSP 2.1, body-content is not optional
    if (_is12())
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("body-content");
      stream.writeCharacters("JSP");
      stream.writeEndElement();
    }

    GenerateJspTaglibsMojo.this.writeCustomComponentTagDescriptorContent(stream, component);

    // In JSP 2.0, description goes just before the attributes
    if (!_is12() && component.getDescription() != null)
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("description");
      stream.writeCData(component.getDescription());
      stream.writeEndElement();
    }

    Iterator properties = component.properties(true);
    properties = new FilteredIterator(properties, new TagAttributeFilter());
    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean)properties.next();
      writeTagAttribute(stream,
                         property.getPropertyName(),
                         property.getDescription(),
                         property.getUnsupportedAgents(),
                         property);
    }

    stream.writeCharacters("\n  ");
    stream.writeEndElement();
  }

  /**
   * Generates tag library descriptor for parsed converter metadata.
   */
  private void _writeTag(
    XMLStreamWriter stream,
    ConverterBean   converter) throws XMLStreamException
  {
    stream.writeCharacters("\n  ");
    stream.writeStartElement("tag");
    stream.writeCharacters("\n    ");
    if (_is12() && converter.getDescription() != null)
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("description");
      stream.writeCData(converter.getDescription());
      stream.writeEndElement();
    }

    stream.writeStartElement("name");
    stream.writeCharacters(converter.getTagName().getLocalPart());
    stream.writeEndElement();
    stream.writeCharacters("\n    ");
    stream.writeStartElement("tag-class");
    stream.writeCharacters(converter.getTagClass());
    stream.writeEndElement();

    // In JSP 2.1, body-content is not optional
    if (_is12())
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("body-content");
      stream.writeCharacters("empty");
      stream.writeEndElement();
    }

    if (!_is12() && converter.getDescription() != null)
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("description");
      stream.writeCData(converter.getDescription());
      stream.writeEndElement();
    }

    // converters need an id attribute
    writeTagAttribute(stream, "id", "the identifier for the converter", null, null);

    Iterator properties = converter.properties();
    properties = new FilteredIterator(properties, new TagAttributeFilter());
    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean)properties.next();
      writeTagAttribute(stream,
                         property.getPropertyName(),
                         property.getDescription(),
                         property.getUnsupportedAgents(),
                         property);
    }

    stream.writeCharacters("\n  ");
    stream.writeEndElement();
  }

  private void _writeTagAttributeDescription(
    XMLStreamWriter stream,
    String          description,
    String[]        unsupportedAgents) throws XMLStreamException
  {

    if (description != null ||
        unsupportedAgents.length > 0)
    {
      stream.writeCharacters("\n      ");
      stream.writeStartElement("description");

      if (unsupportedAgents != null &&
          unsupportedAgents.length > 0)
      {
        if (description == null)
          description = "";

        description += "\n\n    This attribute is not supported on the following agent types:\n";

        for (int i=0; i < unsupportedAgents.length; i++)
        {
          description += " " + unsupportedAgents[i];
          description += (i < unsupportedAgents.length - 1) ? "," : ".";
        }
      }

      stream.writeCData(description);
      stream.writeEndElement();
    }
  }

  protected void writeTagAttribute(
    XMLStreamWriter stream,
    String          propertyName,
    String          description,
    String[]        unsupportedAgents,
    PropertyBean    property) throws XMLStreamException
  {
    stream.writeCharacters("\n    ");
    stream.writeStartElement("attribute");

    // In JSP 2.1, the description goes at the beginning
    if (_is12())
      _writeTagAttributeDescription(stream, description, unsupportedAgents);

    stream.writeCharacters("\n      ");
    stream.writeStartElement("name");
    
    if (property != null)
      stream.writeCharacters(property.getJspPropertyName());
    else
      stream.writeCharacters(propertyName);

    stream.writeEndElement();

    if (!_is12())
    {
      stream.writeCharacters("\n      ");
      stream.writeStartElement("rtexprvalue");
      stream.writeCharacters("false");
      stream.writeEndElement();

      // In JSP 2.0, the tag description goes at the end
      _writeTagAttributeDescription(stream, description, unsupportedAgents);
    }
    else
    {
      if (property != null)
      {
        if (property.isRequired())
        {
          stream.writeCharacters("\n    ");
          stream.writeStartElement("required");
          stream.writeCharacters("true");
          stream.writeEndElement();
        }
        
        if (property.isMethodExpression() || property.isMethodBinding())
        {
          stream.writeCharacters("\n    ");
          stream.writeStartElement("deferred-method");
          stream.writeCharacters("\n      ");
          MethodSignatureBean sig = property.getMethodBindingSignature();
          if (sig != null)
          {
            stream.writeStartElement("method-signature");
            stream.writeCharacters(sig.getReturnType());
            stream.writeCharacters(" myMethod(");
            String[] params = sig.getParameterTypes();
            for (int i = 0; i < params.length; i++)
            {
              if (i > 0)
                stream.writeCharacters(", ");
              stream.writeCharacters(params[i]);
            }

            stream.writeCharacters(")");
            stream.writeEndElement();
          }
          stream.writeEndElement();
        }
        else if (!property.isLiteralOnly() ||
                 // "binding" is always a deferred-value
                 "binding".equals(propertyName))
        {
          stream.writeCharacters("\n      ");
          stream.writeStartElement("deferred-value");
          String propertyClass = property.getPropertyClass();
          // Writing java.lang.String is usually a bad idea - it
          // means that null gets coerced to the empty string.
          if (("java.lang.String".equals(propertyClass) && coerceStrings) ||
              _CAN_COERCE.contains(property.getPropertyClass()))
          {
            stream.writeCharacters("\n        ");
            stream.writeStartElement("type");
            // Trim out any use of generics here - since JSP coercion
            // certainly can't do anything there
            int genericIndex = propertyClass.indexOf('<');
            if (genericIndex > 0)
              propertyClass = propertyClass.substring(0, genericIndex);

            stream.writeCharacters(propertyClass);
            stream.writeEndElement();
            stream.writeCharacters("\n      ");
          }

          stream.writeEndElement();
        }
        else
        {
          stream.writeCharacters("\n      ");
          stream.writeStartElement("rtexprvalue");
          // As of JSF 1.2, "id" can be set via an rtexprvalue (but
          // *not* by a ValueExpression) - it has to be evaluated
          // in the JSP
          if ("id".equals(propertyName) && !disableIdExpressions)
            stream.writeCharacters("true");
          else
            stream.writeCharacters("false");
          stream.writeEndElement();
        }
      }
    }

    stream.writeCharacters("\n    ");
    stream.writeEndElement();
  }

  /**
   * Generates tag library descriptor for parsed validator metadata.
   */
  private void _writeTag(
    XMLStreamWriter stream,
    ValidatorBean   validator) throws XMLStreamException
  {
    stream.writeCharacters("\n  ");
    stream.writeStartElement("tag");

    if (_is12() && validator.getDescription() != null)
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("description");
      stream.writeCData(validator.getDescription());
      stream.writeEndElement();
    }

    stream.writeCharacters("\n    ");
    stream.writeStartElement("name");
    stream.writeCharacters(validator.getTagName().getLocalPart());
    stream.writeEndElement();
    stream.writeCharacters("\n    ");
    stream.writeStartElement("tag-class");
    stream.writeCharacters(validator.getTagClass());
    stream.writeEndElement();

    // In JSP 2.1, body-content is not optional
    if (_is12())
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("body-content");
      stream.writeCharacters("empty");
      stream.writeEndElement();
    }

    if (!_is12() && validator.getDescription() != null)
    {
      stream.writeCharacters("\n    ");
      stream.writeStartElement("description");
      stream.writeCData(validator.getDescription());
      stream.writeEndElement();
    }

    // validators need an id attribute
    writeTagAttribute(stream, "id", "the identifier for the validator", null, null);

    Iterator properties = validator.properties();
    properties = new FilteredIterator(properties, new TagAttributeFilter());
    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean)properties.next();
      writeTagAttribute(stream,
                         property.getPropertyName(),
                         property.getDescription(),
                         property.getUnsupportedAgents(),
                         property);
    }

    stream.writeCharacters("\n  ");
    stream.writeEndElement();
  }

  /**
   * Generates tag handlers for parsed component metadata.
   */
  private void _generateTagHandlers() throws IOException, MojoExecutionException {
    // Make sure generated source directory
    // is added to compilation source path
    project.addCompileSourceRoot(generatedSourceDirectory.getCanonicalPath());

    FacesConfigBean facesConfig = getFacesConfig();
    if (!facesConfig.hasComponents() && !facesConfig.hasConverters() && !facesConfig.hasValidators())
    {
      getLog().info("Nothing to generate - no components found");
    }
    else
    {
      Iterator components = facesConfig.components();
      components = new FilteredIterator(components, new SkipFilter());
      components = new FilteredIterator(components, new ComponentTagFilter());
      components = new FilteredIterator(components, new ComponentTagClassFilter(packageContains));

      Iterator validators = facesConfig.validators();
      validators = new FilteredIterator(validators, new ValidatorTagFilter());
      validators = new FilteredIterator(validators, new ValidatorTagClassFilter(packageContains));

      Iterator converters = facesConfig.converters();
      converters = new FilteredIterator(converters, new ConverterTagFilter());
      converters = new FilteredIterator(converters, new ConverterTagClassFilter(packageContains));

      // incremental unless forced
      if (!force)
      {
        components = new FilteredIterator(components, new IfComponentModifiedFilter());
        converters = new FilteredIterator(converters, new IfConverterModifiedFilter());
        validators = new FilteredIterator(validators, new IfValidatorModifiedFilter());
      }

      if (!components.hasNext() && !converters.hasNext() && !validators.hasNext())
      {
        getLog().info("Nothing to generate - all JSP tags are up to date");
      }
      else
      {
        ComponentTagHandlerGenerator componentGen = new ComponentTagHandlerGenerator();
        AbstractConverterTagGenerator converterGen = null;
        AbstractValidatorTagGenerator validatorGen = null;
        if (type == null || "trinidad".equals(type))
        {
          converterGen = new TrinidadConverterTagGenerator(is12(), getLicenseHeader(), getLog());
          validatorGen = new TrinidadValidatorTagGenerator(is12(), getLicenseHeader(), getLog());
        }
        else
        {
          converterGen = new MyFacesConverterTagGenerator(is12(), getLicenseHeader(), getLog());
          validatorGen = new MyFacesValidatorTagGenerator(is12(), getLicenseHeader(), getLog());    
        }
        int count = 0;
        while (components.hasNext())
        {
          componentGen.generateTagHandler((ComponentBean)components.next());
          count++;
        }
        while (converters.hasNext())
        {
          converterGen.generateTagHandler((ConverterBean)converters.next(), generatedSourceDirectory);
          count++;
        }
        while (validators.hasNext())
        {
          validatorGen.generateTagHandler((ValidatorBean)validators.next(), generatedSourceDirectory);
          count++;
        }
        getLog().info("Generated " + count + " JSP tag(s)");
      }
    }
  }

  class ComponentTagHandlerGenerator
  {
    
    private Set initComponentList(ComponentBean component,
                                  String fullSuperclassName)
    {
      Set componentList = new HashSet();
      componentList.add(component);

      ComponentBean lBean = component;
      while ((lBean = lBean.resolveSupertype()) != null &&
             !fullSuperclassName.equals(lBean.getTagClass()))
      {
        getLog().debug(component.getComponentType()+
                       ": Add additional Tags from: " + lBean.getComponentType());
        componentList.add(lBean);
      }

      return componentList;
    }

    public void generateTagHandler(ComponentBean component)
    {
      ComponentTagGenerator generator;
      Set componentList;
      
      String fullSuperclassName = component.findJspTagSuperclass();
      if (fullSuperclassName == null)
      {
        getLog().warn("Missing JSP Tag superclass for component: " + component.getComponentClass()
                      + ", generation of this Tag is skipped");
        return;
      }
      
      componentList = initComponentList(component, fullSuperclassName);
      
      String fullClassName = component.getTagClass();
      try
      {
        getLog().debug("Generating " + fullClassName);
        
        String sourcePath = Util.convertClassToSourcePath(fullClassName, ".java");
        File targetFile = new File(generatedSourceDirectory, sourcePath);
        
        targetFile.getParentFile().mkdirs();
        StringWriter sw = new StringWriter();
        PrettyWriter out = new PrettyWriter(sw);
        
        if (component.isTrinidadComponent())
        {
          generator = new TrinidadComponentTagGenerator(_is12());
        }
        else
        {
          generator = new MyFacesComponentTagGenerator(_is12());
        }

        getLog().debug("Generating " + fullClassName+", with generator: "+generator.getClass().getName());

        String className = Util.getClassFromFullClass(fullClassName);
        String packageName = Util.getPackageFromFullClass(fullClassName);
        
        // header/copyright
        writePreamble(out);
        
        // package
        out.println("package " + packageName + ";");
        
        out.println();
        
        String superclassName = Util.getClassFromFullClass(fullSuperclassName);
        if (superclassName.equals(className))
        {
          superclassName = fullSuperclassName;
        }
        String componentFullClass = component.getComponentClass();
        String componentClass = Util.getClassFromFullClass(componentFullClass);
        
        generator.writeImports(out, null, packageName, fullSuperclassName, superclassName, componentList);
        
        generator.writeClassBegin(out, className, superclassName, component, null);
        
        int modifiers = component.getTagClassModifiers();
        generator.writeConstructor(out, component, modifiers);
        
        
        if (!Modifier.isAbstract(modifiers))
        {
          generator.writeGetComponentType(out, component);
          generator.writeGetRendererType(out, component);
        }

        GenerateJspTaglibsMojo.this.writeCustomComponentTagHandlerContent(out, component);

        generator.writePropertyMembers(out, componentList);
        generator.writeSetPropertiesMethod(out, componentClass, componentList);
        generator.writeReleaseMethod(out, componentList);
        
        generator.writeClassEnd(out);
        out.close();
        
        // delay write in case of error
        // timestamp should not be updated when an error occurs
        // delete target file first, because it is readonly
        targetFile.delete();
        FileWriter fw = new FileWriter(targetFile);
        StringBuffer buf = sw.getBuffer();
        fw.write(buf.toString());
        fw.close();
        targetFile.setReadOnly();
      }
      catch (Throwable e)
      {
        getLog().error("Error generating " + fullClassName, e);
      }
    }
  }

  protected boolean is12()
  {
    return "1.2".equals(jsfVersion) || "12".equals(jsfVersion);
  }

  private boolean _is12()
  {
    return is12();
  }

  private class IfComponentModifiedFilter extends ComponentFilter
  {
    protected boolean accept(
      ComponentBean component)
    {
      String tagClass = component.getTagClass();
      String sourcePath = Util.convertClassToSourcePath(tagClass, ".java");
      String templatePath = Util.convertClassToSourcePath(tagClass, "Template.java");
      File targetFile = new File(generatedSourceDirectory, sourcePath);
      File templateFile = new File(templateSourceDirectory, templatePath);

      // accept if templateFile is newer or component has been modified
      return (templateFile.lastModified() > targetFile.lastModified() ||
              component.isModifiedSince(targetFile.lastModified()));
    }
  }

  private class IfConverterModifiedFilter extends ConverterFilter
  {
    protected boolean accept(
      ConverterBean converter)
    {
      String tagClass = converter.getTagClass();
      String sourcePath = Util.convertClassToSourcePath(tagClass, ".java");
      String templatePath = Util.convertClassToSourcePath(tagClass, "Template.java");
      File targetFile = new File(generatedSourceDirectory, sourcePath);
      File templateFile = new File(templateSourceDirectory, templatePath);

      // accept if templateFile is newer or component has been modified
      return (templateFile.lastModified() > targetFile.lastModified() ||
              converter.isModifiedSince(targetFile.lastModified()));
    }
  }

  private class IfValidatorModifiedFilter extends ValidatorFilter
  {
    protected boolean accept(
      ValidatorBean validator)
    {
      String tagClass = validator.getTagClass();
      String sourcePath = Util.convertClassToSourcePath(tagClass, ".java");
      String templatePath = Util.convertClassToSourcePath(tagClass, "Template.java");
      File targetFile = new File(generatedSourceDirectory, sourcePath);
      File templateFile = new File(templateSourceDirectory, templatePath);

      // accept if templateFile is newer or component has been modified
      return (templateFile.lastModified() > targetFile.lastModified() ||
              validator.isModifiedSince(targetFile.lastModified()));
    }
  }

  /**
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  protected MavenProject project;

  /**
   * @parameter
   * @required
   */
  protected Map taglibs;

  /**
   * @parameter expression="META-INF/maven-faces-plugin/faces-config.xml"
   * @required
   * @readonly
   */
  protected String resourcePath;

  /**
   * @parameter expression="src/main/conf"
   * @required
   */
  protected File configSourceDirectory;

  /**
   * @parameter expression="src/main/java-templates"
   * @required
   */
  protected File templateSourceDirectory;

  /**
   * @parameter expression="${project.build.directory}/maven-faces-plugin/main/java"
   * @required
   */
  protected File generatedSourceDirectory;

  /**
   * @parameter expression="${project.build.directory}/maven-faces-plugin/main/resources"
   * @required
   */
  protected File generatedResourcesDirectory;

  /**
   * @parameter
   */
  protected String packageContains = "";

  /**
   * @parameter
   */
  protected boolean force;


  /**
   * @parameter
   */
  protected boolean disableIdExpressions;

  /**
   * @parameter
   */
  protected boolean coerceStrings;

  
  /**
   * @parameter
   */
  private String jsfVersion;

  /**
   * @parameter expression="trinidad"
   */
  private String type;



  static final private String _JSP_TAG_LIBRARY_DOCTYPE_PUBLIC =
              "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";

  static final private String _JSP_TAG_LIBRARY_DOCTYPE_SYSTEM =
              "http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd";

  static final private String _JSP_TAG_LIBRARY_DTD =
    "<!DOCTYPE taglib PUBLIC \n" +
    "  \"" + _JSP_TAG_LIBRARY_DOCTYPE_PUBLIC + "\"\n" +
    "  \"" + _JSP_TAG_LIBRARY_DOCTYPE_SYSTEM + "\" >\n";

  static final private String _XINCLUDE_JSP_TAG_LIBRARY_DTD =
    "<!DOCTYPE taglib PUBLIC\n" +
    "  \"" + _JSP_TAG_LIBRARY_DOCTYPE_PUBLIC + "\"\n" +
    "  \"" + _JSP_TAG_LIBRARY_DOCTYPE_SYSTEM + "\" [\n" +
    "      <!ELEMENT xi:include EMPTY>\n" +
    "      <!ATTLIST xi:include\n" +
    "          xmlns:xi CDATA #FIXED  \"" + XIncludeFilter.XINCLUDE_NAMESPACE + "\"\n" +
    "          href     CDATA #IMPLIED\n" +
    "          xpointer CDATA #IMPLIED>\n" +
    "]>\n";

  static final private Set _CAN_COERCE = new HashSet();
  static
  {
    // What?  Can't coerce Strings?  How could that be?  Well, take a look at:
    //   http://issues.apache.org/jira/browse/ADFFACES-377
    // The silly coercion rules in JSP convert null to the
    // empty string.  So it's not that we can't coerce to
    // String, we just really, really don't want to.
    //    _CAN_COERCE.add("java.lang.String");
    // TODO: consider getting rid of coercion rules for
    // all non-primitives
    _CAN_COERCE.add("java.lang.Integer");
    _CAN_COERCE.add("java.lang.Long");
    _CAN_COERCE.add("java.lang.Boolean");
    _CAN_COERCE.add("java.lang.Double");
    _CAN_COERCE.add("java.lang.Float");
    _CAN_COERCE.add("java.lang.Short");
    _CAN_COERCE.add("java.lang.Character");
    _CAN_COERCE.add("java.lang.Byte");
    _CAN_COERCE.add("int");
    _CAN_COERCE.add("long");
    _CAN_COERCE.add("boolean");
    _CAN_COERCE.add("double");
    _CAN_COERCE.add("float");
    _CAN_COERCE.add("short");
    _CAN_COERCE.add("char");
    _CAN_COERCE.add("byte");

    // See http://issues.apache.org/jira/browse/ADFFACES-477:  for
    // "binding" and "converter" properties, we want the deferred-types
    // there.  Hardcoding these here is very ugly, but putting in
    // all coercion rules would break how Trinidad handles Dates,
    // and Lists of Colors, etc. - for those, we want to support
    // raw Strings (which the JSP engine doesn't know how to coerce)
    // and coerce them ourselves in the tag.
    _CAN_COERCE.add("javax.faces.component.UIComponent");
    _CAN_COERCE.add("javax.faces.convert.Converter");
  }
}
