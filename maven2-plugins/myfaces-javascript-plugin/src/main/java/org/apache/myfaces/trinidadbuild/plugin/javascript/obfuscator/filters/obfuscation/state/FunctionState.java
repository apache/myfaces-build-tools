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
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.AnnotationConstants;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.JSParser15Constants;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.NameGen;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.ProgramContext;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.ProgramContextStack;

import java.util.Iterator;
import java.util.Vector;


public class FunctionState extends ObfuscationState
{
  static private FunctionState _instance;

  // private construnctor for singleton
  private FunctionState()
  {
  }

  static public FunctionState getInstance()
  {
    if (_instance == null)
    {
      synchronized (FunctionState.class)
      {
        if (_instance == null)
        {
          _instance = new FunctionState();
        }
      }
    }

    return _instance;
  }

  public AnnotatedToken enterState(ProgramContextStack contextStack,
    AnnotatedToken startToken, NameGen nameGen)
  {
    FunctionContext functionContext;
    AnnotatedToken token = startToken;
    String contextName = "AnonymousFunction";
    Vector params = (Vector) token.getAnnotationObject();
    boolean obfuscateLocalVars = !token.functionUsesEval();

    if (token.getKind() == AnnotationConstants.NAMED_FUNCTION)
    {
      // grab the function name
      token = token.getNext();
      contextName = token.image;
      functionContext = new FunctionContext(contextName, obfuscateLocalVars);
    }
    else
    {
      // reuse the symbol name generator for anonymous functions
      functionContext = new FunctionContext(contextName, nameGen,
          obfuscateLocalVars);
    }

    if (obfuscateLocalVars)
    {
      obfuscateFunctionParams(params, functionContext);
    }

    while (token.kind != JSParser15Constants.LBRACE)
    {
      token = token.getNext();
    }

    token = token.getNext();
    contextStack.pushContext(functionContext);

    return processCodeBlock(contextStack, token);
  }

  private void obfuscateFunctionParams(Vector params,
    ProgramContext functionContext)
  {
    for (Iterator itr = params.iterator(); itr.hasNext();)
    {
      AnnotatedToken paramToken = (AnnotatedToken) itr.next();
      functionContext.addToken(paramToken.image, paramToken);
      paramToken.image = functionContext.generateSymbolName();
    }
  }

  public void exitState(ProgramContextStack contextStack)
  {
    contextStack.popContext();
  }

  protected void processVarIdentifier(ProgramContextStack contextStack,
    AnnotatedToken token)
  {
    FunctionContext functionContext = (FunctionContext) contextStack.peek();

    // normal processing for functions that do not use eval() calls
    if (functionContext.canObfuscateLocalVars())
    {
      super.processVarIdentifier(contextStack, token);
    }
  }
}
