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
package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.keywords;

import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.ObfuscatorFilter;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.AnnotatedToken;


public class SpecialKeywordsFilter implements ObfuscatorFilter
{
  public SpecialKeywordsFilter()
  {
  }

  public AnnotatedToken filter(AnnotatedToken startToken)
  {
    AnnotatedToken token = startToken;
    token = removeSpecialTypes(token);

    return token;
  }

  protected void removeSpecialTokens(AnnotatedToken token)
  {
    AnnotatedToken specialToken = token.getSpecialToken();
    AnnotatedToken prevSpecialToken = token;

    while (specialToken != null)
    {
      if (specialToken.isRemovableKeyword())
      {
        prevSpecialToken.specialToken = specialToken.specialToken;
        specialToken = specialToken.getSpecialToken();
      }
      else
      {
        prevSpecialToken = specialToken;
        specialToken = specialToken.getSpecialToken();
      }
    }
  }

  /**
   * The main loop removes special types (like Profiler, and CheckPoint) and
   * all method invocations using variables of these types. The main
   * loop calls removeSpecialTokens which removes special keywords like Assert
   * and Logger, which were tagged by the parser as SpecialTokens
   * @param startToken
   * @return
   */
  protected AnnotatedToken removeSpecialTypes(AnnotatedToken startToken)
  {
    AnnotatedToken token = startToken;
    AnnotatedToken prevToken = null;
    AnnotatedToken newStartToken = null;

    while (token != null)
    {
      if (token.canRemove())
      {
        if (prevToken != null)
        {
          prevToken.next = token.getNext();
        }

        token = token.getNext();
      }
      else
      {
        removeSpecialTokens(token);

        if (newStartToken == null)
        {
          newStartToken = token;
        }

        prevToken = token;
        token = token.getNext();
      }
    }

    return newStartToken;
  }
}
