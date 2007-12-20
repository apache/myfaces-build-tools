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
package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.obfuscation.state;

import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.AnnotatedToken;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.NameGen;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.ProgramContext;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.ProgramContextStack;


public class RootState extends ObfuscationState
{
  static private RootState _instance;

  // private construnctor for singleton
  private RootState()
  {
  }

  static public RootState getInstance()
  {
    if (_instance == null)
    {
      synchronized (RootState.class)
      {
        if (_instance == null)
        {
          _instance = new RootState();
        }
      }
    }

    return _instance;
  }

  public AnnotatedToken enterState(ProgramContextStack contextStack,
    AnnotatedToken startToken, NameGen nameGen)
  {
    contextStack.pushContext(new ProgramContext("root"));

    return processCodeBlock(contextStack, startToken);
  }

  public void exitState(ProgramContextStack contextStack)
  {
    contextStack.popContext();
  }
}
