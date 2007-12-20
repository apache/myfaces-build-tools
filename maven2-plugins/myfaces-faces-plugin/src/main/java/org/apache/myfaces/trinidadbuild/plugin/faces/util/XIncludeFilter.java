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
package org.apache.myfaces.trinidadbuild.plugin.faces.util;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.XMLFilterImpl;

public final class XIncludeFilter extends XMLFilterImpl
{
  public final static String XINCLUDE_NAMESPACE = "http://www.w3.org/2001/XInclude";

  public XIncludeFilter(
    XMLReader parent,
    URL       base)
  {
    super(parent);
    bases.push(base);
  }

  public void setDocumentLocator(
    Locator locator)
  {
    locators.push(locator);
    super.setDocumentLocator(locator);
  }

  public void startPrefixMapping(
    String prefix,
    String namespaceURI) throws SAXException
  {
    if (!namespaceURI.equals(XINCLUDE_NAMESPACE))
    {
      namespaces.declarePrefix(prefix, namespaceURI);
      super.startPrefixMapping(prefix, namespaceURI);
    }
  }

  public void startElement(
    String     namespaceURI,
    String     localName,
    String     qualifiedName,
    Attributes attributes) throws SAXException
  {
    namespaces.pushContext();
    // support both DTD and Schema
    if (namespaceURI.equals(XINCLUDE_NAMESPACE) &&
        localName.equals("include"))
    {
      // href attribute is required
      String href = attributes.getValue("href");
      if (href == null)
        throw new SAXException("Missing href attribute");

      // xpointer attribute is optional
      String xpointer = attributes.getValue("xpointer");

      doXInclude(href, xpointer);
    }
    else
    {
      super.startElement(namespaceURI, localName, qualifiedName, attributes);
    }
  }

  public void endElement(
    String     namespaceURI,
    String     localName,
    String     qualifiedName) throws SAXException
  {
    if (!namespaceURI.equals(XINCLUDE_NAMESPACE))
      super.endElement(namespaceURI, localName, qualifiedName);

    namespaces.popContext();
  }

  public void startDocument() throws SAXException
  {
    if (depth == 0)
      super.startDocument();

    depth++;
  }

  public void endDocument() throws SAXException
  {
    locators.pop();
    depth--;

    if (depth == 0)
      super.endDocument();
  }

  private void doXInclude(
    String href,
    String xpointer) throws SAXException
  {
    URL target = resolveURL(href);

    try
    {
      SAXParserFactory saxFactory = SAXParserFactory.newInstance();
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader reader = saxParser.getXMLReader();
      if (xpointer != null)
      {
        // pass all parsed SAX events through XPointerFilter
        reader = new XPointerFilter(reader, namespaces, xpointer);
      }
      // pass all selected XPointer SAX events to XIncludeFilter
      reader.setContentHandler(this);
      reader.setEntityResolver(this);
      reader.setErrorHandler(this);
      reader.setDTDHandler(this);

      // push on new base URL in case of nested xi:include
      bases.push(target);
      reader.parse(new InputSource(target.openStream()));
      bases.pop();
    }
    catch (ParserConfigurationException e)
    {
      throw new SAXException("Error during xinclude of " +
                             target.toExternalForm(), e);
    }
    catch (IOException e)
    {
      throw new SAXException("Error during xinclude of " +
                             target.toExternalForm(), e);
    }
  }

  private URL resolveURL(
    String href) throws SAXException
  {
    URL base = (URL) bases.peek();

    try
    {
      return new URL(base, href);
    }
    catch (MalformedURLException e)
    {
      throw new SAXException("Error resolving href \"" + href + "\" " +
                             "at base \"" + base + "\"", e);
    }
  }

  private int depth = 0;

  private Stack bases = new Stack();
  private Stack locators = new Stack();
  private NamespaceSupport namespaces = new NamespaceSupport();
}

