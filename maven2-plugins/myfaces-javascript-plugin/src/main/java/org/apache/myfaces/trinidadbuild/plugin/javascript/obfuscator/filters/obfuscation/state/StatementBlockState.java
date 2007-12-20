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


public class StatementBlockState extends ObfuscationState
{
  static private StatementBlockState _instance;

  private StatementBlockState()
  {
  }

  static public StatementBlockState getInstance()
  {
    if (_instance == null)
    {
      synchronized (StatementBlockState.class)
      {
        if (_instance == null)
        {
          _instance = new StatementBlockState();
        }
      }
    }

    return _instance;
  }

  public AnnotatedToken enterState(ProgramContextStack contextStack,
    AnnotatedToken startToken, NameGen nameGen)
  {
    // do not create a new context in a statement block. JavaScript does not use a new scope in a
    // statement block
    AnnotatedToken token = startToken.getNext();

    return processCodeBlock(contextStack, token);
  }

  public void exitState(ProgramContextStack contextStack)
  {
    // do not pop context
  }

  protected void processVarIdentifier(ProgramContextStack contextStack,
    AnnotatedToken token)
  {
    ProgramContext blockContext = (ProgramContext) contextStack.peek();

    // normal processing for functions that do not use eval() calls
    if (!(blockContext instanceof FunctionContext) ||
        (blockContext instanceof FunctionContext &&
        ((FunctionContext) blockContext).canObfuscateLocalVars()))
    {
      super.processVarIdentifier(contextStack, token);
    }
  }
}
