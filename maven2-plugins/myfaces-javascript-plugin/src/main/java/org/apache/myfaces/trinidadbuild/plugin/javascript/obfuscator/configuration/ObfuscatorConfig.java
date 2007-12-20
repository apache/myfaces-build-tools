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
package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.configuration;

import java.util.Vector;

public class ObfuscatorConfig
{
  private Vector _obfExclusions = new Vector();
  private Vector _commentsExclusions = new Vector();
  private Vector _wsExclusions = new Vector();
  private Vector _nlExclusions = new Vector();
  private Vector _keywordExclusions = new Vector();

  public ObfuscatorConfig() throws ConfigException
  {
  }

  public boolean skipObfuscation(String file)
  {
    return _obfExclusions.contains(file);
  }

  public boolean skipStripComments(String file)
  {
    return _commentsExclusions.contains(file);
  }

  public boolean skipStripWhitespaces(String file)
  {
    return _wsExclusions.contains(file);
  }

  public boolean skipStripNewlines(String file)
  {
    return _nlExclusions.contains(file);
  }

  public boolean skipStripSpecialKeywords(String file)
  {
    return _keywordExclusions.contains(file);
  }

  private void init() throws ConfigException
  {
  }
}
