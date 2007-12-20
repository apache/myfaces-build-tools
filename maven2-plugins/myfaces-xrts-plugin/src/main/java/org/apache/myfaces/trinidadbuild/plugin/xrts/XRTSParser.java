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
package org.apache.myfaces.trinidadbuild.plugin.xrts;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * */
final class XRTSParser extends HandlerBase
{

  /**
   * Empty constructor for the XRTSParser
   *
   */
  public XRTSParser()
  {
  }

  public XRTSParser(RTSWriter bw, Map parms)
  {
    _bundleWriter = bw;
    _parms = parms;
  }

  /**
   * Receive a Locator object for document events.
   * Setting Locator ensures the SAX parser can find a given XML-based RTS
   * document.  This method is closely related to the _fileToURL in
   * <code>XRTSMakeBundle</code>.
   *
   * @param locator A locator for all SAX document events.
   */
  public void setDocumentLocator (Locator locator)
  {
    _locator = locator;
  }

  /**
   * Receive notification of the beginning of the document.
   * SAX Parser event for finding the beginning of an XML-based RTS document
   */
  public void startDocument() throws SAXException
  {
    _metaHt.put("fileType", "xrts");
    _startDoc = true;

  }

  /**
   * Receive notification of the end of the document.
   * SAX Parser event for finding the end of an XML-based RTS document
   */
  public void endDocument() throws SAXException
  {
//    System.out.println("EndDocument");
    try
    {
      _bundleWriter.endBundle(_parms, _metaHt);
    }
    catch (Throwable th)
    {
      throw new SAXException("endBundle Exception: " + th.getMessage());
    }
  }

  /**
   * Receive notification of the start of an element.
   *
   * @param name the element type name.
   * @param attributes the specified or defaulted attributes.
   */
  public void startElement(String name, AttributeList atts) throws SAXException
  {
    _nestingLevel++;

    // startBundle doesn't go in the startDocument method because it
    // requires metadata to be passed and in particular, requires
    // at least the package for any implementation of startBundle to work
    if ((_nestingLevel == 2) && name.equals("resource"))
    {
      if (_startDoc)
      {
        try
        {
          _bundleWriter.startBundle(_parms, _metaHt);
          _startDoc = false;
        }
        catch (Throwable th)
        {
          throw new SAXException("startBundle Exception: " + th.getMessage());
        }
      }

      _currentResourceKey = atts.getValue("key");
      if (_uniqKeys.contains(_currentResourceKey))
      {
        System.err.println("Duplicate id \"" + _currentResourceKey + "\"");
      }

      _uniqKeys.add(_currentResourceKey);
      

      _currentResourceValue = new StringBuilder();
    }
    else if ((_nestingLevel == 1) && name.equals("resources"))
    {
      _metaHt.put("package", atts.getValue("package"));
      if (atts.getValue("version") != null)
        _metaHt.put("version", atts.getValue("version"));
      if (atts.getValue("baseversion") != null)
        _metaHt.put("baseVersion", atts.getValue("baseversion"));
    }
  }

  /**
   * Receive notification of the end of an element.
   * @param name the element type name.
   */
  public void endElement(String name) throws SAXException
  {
    if ((_nestingLevel == 2) && name.equals("resource"))
    {
      String value = _currentResourceValue.toString().trim();
      try
      {
        _bundleWriter.writeString(_parms, _metaHt, _currentResourceKey, value);
      }
      catch (Throwable t)
      {
        throw new SAXException(t.getMessage());
      }
    }

    _nestingLevel--;
  }

  /**
   * Receive notification of ignorable whitespace in element content.
   *
   * @param ch the whitespace characters.
   * @param start the start position in the character array.
   * @param length the number of characters to use from the character array.
   */
  public void ignorableWhitespace(char[] cbuf, int start, int len)
    throws SAXException
  {
//    System.out.println("IgnorableWhiteSpace" + len);
  }

  /**
   * Receive notification of character data inside an element.
   *
   * @param ch the characters.
   * @param start the start position in the character array.
   * @param length the number of characters to use from the character array.
   */
  public void characters(char[] cbuf, int start, int len) throws SAXException
  {
    if (_nestingLevel == 2)
    {
      _currentResourceValue.append(cbuf, start, len);
    }
  }


  // Store the locator
  private Locator _locator;

  private String _currentResourceKey;
  private StringBuilder _currentResourceValue;
  private Map<String, Object> _metaHt = new HashMap<String, Object>();
  private Set<String> _uniqKeys = new HashSet<String>();

  private RTSWriter _bundleWriter;
  private Map _parms;

  private int _nestingLevel = 0;
  private boolean _startDoc = false;
}
