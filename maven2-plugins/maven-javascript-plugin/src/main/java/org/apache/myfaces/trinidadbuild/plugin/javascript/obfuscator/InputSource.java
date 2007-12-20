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
package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator;

import java.io.InputStream;


public class InputSource
{
  private boolean _skipObfuscation;
  private boolean _skipStripComments;
  private boolean _skipStripWhitespaces;
  private boolean _skipStripNewlines;
  private boolean _skipStripSpecialKeywords;
  private InputStream _in;

  public InputSource(InputStream in, boolean skipObfuscation,
    boolean skipStripComments, boolean skipStripWhitespaces,
    boolean skipStripNewlines, boolean skipStripSpecialKeywords)
  {
    _in = in;
    _skipObfuscation = skipObfuscation;
    _skipStripComments = skipStripComments;
    _skipStripWhitespaces = skipStripWhitespaces;
    _skipStripNewlines = skipStripNewlines;
    _skipStripSpecialKeywords = skipStripSpecialKeywords;
  }

  public InputStream getInputStream()
  {
    return _in;
  }

  public boolean skipObfuscation()
  {
    return _skipObfuscation;
  }

  public boolean skipStripComments()
  {
    return _skipStripComments;
  }

  public boolean skipStripWhitespaces()
  {
    return _skipStripWhitespaces;
  }

  public boolean skipStripNewlines()
  {
    return _skipStripNewlines;
  }

  public boolean skipStripSpecialKeywords()
  {
    return _skipStripSpecialKeywords;
  }
}
