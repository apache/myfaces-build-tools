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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * For now, just skip the root element since that's our usecase.
 */
public final class XPointerFilter extends XMLFilterImpl
{
  public XPointerFilter(
    XMLReader        parent,
    NamespaceSupport namespaces,
    String           xpointer)
  {
    super(parent);

    // We support a very rudimentary - but totally sufficient - XPointer syntax,
    // which is just /element/otherelement/otherotherelement/*
    if (!xpointer.startsWith("/") || !xpointer.endsWith("/*"))
      throw new IllegalArgumentException("Unsupported xpointer syntax: " +
                                         xpointer);

    xpointer = xpointer.substring(0, xpointer.length() - 2).substring(1);
    String[] elements = xpointer.split("/");
    int count = elements.length;

    _rootNamespaceURI = new String[count];
    _rootLocalName = new String[count];
    _acceptChildNodes = new boolean[count];

    for (int i = 0; i < count; i++)
    {
      String[] parts = elements[i].split(":");
      String prefix = (parts.length == 1) ? "" : parts[0];
      String namespaceURI = namespaces.getURI(prefix);
      _rootNamespaceURI[i] = (namespaceURI != null) ? namespaceURI : "";
      _rootLocalName[i] = (parts.length == 1) ? parts[0] : parts[1];
      _acceptChildNodes[i] = false;
    }
  }

  public void startElement(
    String     namespaceURI,
    String     localName,
    String     qualifiedName,
    Attributes attributes) throws SAXException
  {
    if (_depth < _acceptChildNodes.length)
    {
      _acceptChildNodes[_depth] =
           (_rootNamespaceURI[_depth].equals(namespaceURI) &&
            _rootLocalName[_depth].equals(localName));
    }
    else if (_acceptingChildNodes())
    {
      super.startElement(namespaceURI, localName, qualifiedName, attributes);
    }

    _depth++;
  }

  public void endElement(
    String namespaceURI,
    String localName,
    String qualifiedName) throws SAXException
  {
    _depth--;

    if (_depth >= _acceptChildNodes.length && _acceptingChildNodes())
      super.endElement(namespaceURI, localName, qualifiedName);
  }

  
  private boolean _acceptingChildNodes()
  {
    for (int i = _acceptChildNodes.length - 1; i >= 0; i--)
    {
      if (!_acceptChildNodes[i])
        return false;
    }

    return true;
  }

  private int     _depth;
  private boolean[] _acceptChildNodes;
  private String[]  _rootNamespaceURI;
  private String[]  _rootLocalName;
}
