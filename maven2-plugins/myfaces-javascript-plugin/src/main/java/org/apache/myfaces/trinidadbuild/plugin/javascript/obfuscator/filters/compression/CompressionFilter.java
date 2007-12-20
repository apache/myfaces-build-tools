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
package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.compression;

import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.ObfuscatorFilter;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.AnnotatedToken;

import java.util.Iterator;
import java.util.Vector;


public class CompressionFilter implements ObfuscatorFilter
{
  private boolean _stripComments;
  private boolean _stripWhitespaces;
  private boolean _stripNewlines;
  private Vector _handlers = new Vector();

  public CompressionFilter(boolean stripComments, boolean stripWhitespaces,
    boolean stripNewlines)
  {
    _stripComments = stripComments;
    _stripWhitespaces = stripWhitespaces;
    _stripNewlines = stripNewlines;

    // create a set of handlers based on supplied options
    init();
  }

  public AnnotatedToken filter(AnnotatedToken startToken)
  {
    AnnotatedToken token = startToken;
    AnnotatedToken prevToken = null;

    while (token != null)
    {
      // hand off to handlers
      // todo: inline these if this is too expensive
      for (Iterator itr = _handlers.iterator(); itr.hasNext();)
      {
        CompressionHandler handler = (CompressionHandler) itr.next();
        handler.handle(token, prevToken);
      }

      prevToken = token;
      token = token.getNext();
    }

    return startToken;
  }

  private void init()
  {
    if (_stripComments)
    {
      _handlers.add(new CommentsHandler());
    }

    if (_stripWhitespaces)
    {
      _handlers.add(new WhitespaceHandler());
    }

    if (_stripNewlines)
    {
      _handlers.add(new NewlineHandler());
    }
  }
}
