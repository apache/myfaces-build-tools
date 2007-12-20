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


public abstract class ObfuscationState
{
  public abstract AnnotatedToken enterState(ProgramContextStack contextStack,
    AnnotatedToken startToken, NameGen nameGen);

  public abstract void exitState(ProgramContextStack contextStack);

  /**
  * Obfuscates variable declarations
  * @param contextStack
  * @param token
  */
  protected void processVarIdentifier(ProgramContextStack contextStack,
    AnnotatedToken token)
  {
    ProgramContext blockContext = (ProgramContext) contextStack.peek();

    // skip global vars
    if (contextStack.size() > 1)
    {
      // add only if we have not seen this variable before in this context.
      AnnotatedToken savedToken = blockContext.getToken(token.image);
      String tokenImage;

      if (savedToken == null)
      {
        tokenImage = blockContext.generateSymbolName();
        blockContext.addToken(token.image, token);
      }
      else
      {
        tokenImage = savedToken.image;
      }

      token.image = tokenImage;
    }
  }

  /**
  *
  * @param contextStack
  * @param startToken
  * @return
  */
  public AnnotatedToken processCodeBlock(ProgramContextStack contextStack,
    AnnotatedToken startToken)
  {
    // grab the context on top of the stack
    ProgramContext blockContext = (ProgramContext) contextStack.peek();
    AnnotatedToken token = startToken;

    while (token != null)
    {
      switch (token.getKind())
      {
      case AnnotationConstants.NAMED_FUNCTION:
      case AnnotationConstants.ANONYMOUS_FUNCTION:
        token = FunctionState.getInstance().enterState(contextStack, token,
            blockContext.getNameGen());

        break;

      case AnnotationConstants.VAR_IDENTIFIER:
      {
        processVarIdentifier(contextStack, token);

        break;
      }

      case JSParser15Constants.IDENTIFIER:
      case AnnotationConstants.OBJECT_IDENTIFIER:

        AnnotatedToken obfuscatedToken = contextStack.getToken(token.image);

        if (obfuscatedToken != null)
        {
          token.image = obfuscatedToken.image;
        }

        break;

      case JSParser15Constants.LBRACE:
        token = StatementBlockState.getInstance().enterState(contextStack,
            token, blockContext.getNameGen());

        break;

      case JSParser15Constants.RBRACE:
        exitState(contextStack);

        return token;
      }

      token = token.getNext();
    }

    return token;
  }
}
