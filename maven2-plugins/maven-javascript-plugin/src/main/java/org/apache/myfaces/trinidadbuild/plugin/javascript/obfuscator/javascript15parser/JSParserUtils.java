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
package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser;

import java.util.Enumeration;
import java.util.Vector;


public class JSParserUtils
{
  public JSParserUtils()
  {
  }

  /**
   *
   * @param funcToken
   * @param bodyStart
   * @param bodyEnd
   */
  public static void tagEvalCalls(Token funcToken, Token bodyStart,
                                  Token bodyEnd)
  {
    boolean hasEval = false;
    Token token = bodyStart;

    while (token != bodyEnd)
    {
      if ((token.kind == JSParser15Constants.IDENTIFIER) &&
          token.image.equals("eval") && (token.next != null) &&
          (token.next.kind == JSParser15Constants.LPAREN))
      {
        hasEval = true;

        break;
      }

      token = token.next;
    }

    ((AnnotatedToken) funcToken).setFunctionUsesEval(hasEval);
  }

  /**
   *
   * @param contextStack
   * @param token
   */
  public static void pushToken(ProgramContextStack contextStack,
                               AnnotatedToken token)
  {
    ProgramContext context = (ProgramContext) contextStack.peek();
    context.addToken(token.image, token);
  }

  /**
   *
   * @param contextStack
   * @param v
   */
  public static void pushTokens(ProgramContextStack contextStack, Vector v)
  {
    for (Enumeration item = v.elements(); item.hasMoreElements(); )
    {
      AnnotatedToken t = (AnnotatedToken) item.nextElement();
      pushToken(contextStack, t);
    }
  }

  /**
   *
   * @param objToken
   * @param end
   */
  public static void tagObjectIdentifier(Token objToken, Token end)
  {
    Token t = objToken.next;
    Token suffixToken = null;

    while (t != end)
    {
      if (t.kind == JSParser15Constants.DOT)
      {
        annotateToken(objToken, AnnotationConstants.OBJECT_IDENTIFIER,
                      null, -1);
        suffixToken = t.next;

        if ((suffixToken != null) &&
            (suffixToken.kind == JSParser15Constants.IDENTIFIER))
        {
          annotateToken(suffixToken,
                        AnnotationConstants.OBJECT_FIELD_IDENTIFIER, null,
                        -1);
        }
      }

      if ((t.kind == JSParser15Constants.LPAREN) && (suffixToken != null))
      {
        annotateToken(suffixToken,
                      AnnotationConstants.OBJECT_METHOD_IDENTIFIER, null,
                      -1);
      }

      t = t.next;
    }
  }

  /**
   *
   * @param contextStack
   * @param start
   * @param end
   */
  public static void tagMethodInvocation(ProgramContextStack contextStack,
                                         AnnotatedToken start,
                                         AnnotatedToken end)
  {
    if (tagAssertProfilerCalls(contextStack, start, end))
    {
      return;
    }

    tagLoggerCalls(contextStack, start, end);
  }

  /**
   *
   * @param contextStack
   * @param start
   * @param end
   * @return
   */
  private static boolean tagAssertProfilerCalls(ProgramContextStack contextStack,
                                                AnnotatedToken start,
                                                AnnotatedToken end)
  {
    AnnotatedToken token = start;
    boolean isSpecialType = false;
    boolean isRemovable = false;

    while (token != end)
    {
      // This checks for:
      // "Profiler.xxx=...;"
      // or "foo.<profiler method>;" where foo is a var of type Profiler
      AnnotatedToken objToken = contextStack.getToken(token.image);

      if ((token.isSpecialClassType() ||
           ((objToken != null) && objToken.isSpecialObjectType())) &&
          (token.getKind() == AnnotationConstants.OBJECT_IDENTIFIER))
      {
        isSpecialType = true;
      }

      if (isSpecialType &&
          (token.getKind() == AnnotationConstants.OBJECT_METHOD_IDENTIFIER))
      {
        isRemovable = true;

        break;
      }

      token = token.getNext();
    }

    if (isRemovable)
    {
      token = start;

      while (token != end)
      {
        token.setRemovable(true);
        token = token.getNext();
      }

      if (token.kind == JSParser15Constants.SEMICOLON)
      {
        token.setRemovable(true);
      }
    }

    return isRemovable;
  }

  /**
   *
   * @param contextStack
   * @param start
   * @param end
   * @return
   */
  private static boolean tagLoggerCalls(ProgramContextStack contextStack,
                                        AnnotatedToken start,
                                        AnnotatedToken end)
  {
    AnnotatedToken token = start;
    boolean isSpecialType = false;
    boolean isLOGGER = false;
    boolean isRemovable = false;

    while (token != end)
    {
      // This checks for:
      // "Logger.LOGGER.<static method>"
      if ((token.isSpecialClassType()) &&
          (token.getKind() == AnnotationConstants.OBJECT_IDENTIFIER))
      {
        isSpecialType = true;
      }

      if (isSpecialType &&
          (token.getKind() == AnnotationConstants.OBJECT_FIELD_IDENTIFIER) &&
          token.image.equals("LOGGER"))
      {
        isLOGGER = true;
      }

      if (isLOGGER &&
          (token.getKind() == AnnotationConstants.OBJECT_METHOD_IDENTIFIER))
      {
        isRemovable = true;

        break;
      }

      token = token.getNext();
    }

    if (isRemovable)
    {
      token = start;

      while (token != end)
      {
        token.setRemovable(true);
        token = token.getNext();
      }

      if (token.kind == JSParser15Constants.SEMICOLON)
      {
        token.setRemovable(true);
      }
    }

    return isRemovable;
  }

  /**
   *
   * @param contextStack
   * @param lhs
   * @param op
   * @param rhs
   * @param end
   */
  public static void tagAssignmentExpression(ProgramContextStack contextStack,
                                             AnnotatedToken varToken,
                                             AnnotatedToken lhs,
                                             AnnotatedToken op,
                                             AnnotatedToken rhs,
                                             AnnotatedToken end)
  {
    AnnotatedToken token = (varToken != null)? varToken: lhs;
    boolean isSpecialType = false;
    boolean isRemovable = false;
    ProgramContext context = (ProgramContext) contextStack.peek();

    if (rhs.kind != JSParser15Constants.IDENTIFIER)
    {
      return;
    }

    while (token != end)
    {
      // This checks for:
      // "var foo = Profiler.xxx=...;"
      // or "var bar = foo.<profiler method>;"
      AnnotatedToken objToken = contextStack.getToken(token.image);

      if ((token.isSpecialClassType() ||
           ((objToken != null) && objToken.isSpecialObjectType())) &&
          (token.getKind() == AnnotationConstants.OBJECT_IDENTIFIER))
      {
        isSpecialType = true;
      }

      if (isSpecialType &&
          (token.getKind() == AnnotationConstants.OBJECT_METHOD_IDENTIFIER))
      {
        isRemovable = true;

        break;
      }

      token = token.getNext();
    }

    if (isRemovable)
    {
      token = (varToken != null)? varToken: lhs;

      AnnotatedToken prevToken = lhs;

      while (token != end)
      {
        token.setRemovable(true);

        if (token.getNext().isSpecialClassType() &&
            (token.kind == JSParser15Constants.ASSIGN))
        {
          // This saves the LHS var name "foo" to be removed later. It also assigns the LHS type
          // "var foo = Profiler.xxx=...;"
          prevToken.setType(token.next.image);
          context.addToken(prevToken.image, prevToken);
        }

        prevToken = token;
        token = token.getNext();
      }
    }
  }

  /**
   *
   * @param t
   * @param annotationId
   * @param annotationObject
   * @param wsSensitive
   */
  public static void annotateToken(Token t, int annotationId,
                                   Object annotationObject,
                                   int wsSensitive)
  {
    AnnotatedToken annToken = (AnnotatedToken) t;
    annToken.setAnnotationKind(annotationId);

    if (annotationObject != null)
    {
      annToken.setAnnotationObject(annotationObject);
    }

    if (wsSensitive >= 0)
    {
      annToken.setWSSensitive(wsSensitive);
    }
  }

  /**
   *
   * @param v
   * @param annotationId
   * @param annotationObject
   * @param wsSensitive
   */
  public static void annotateTokens(Vector v, int annotationId,
                                    Object annotationObject,
                                    int wsSensitive)
  {
    for (Enumeration item = v.elements(); item.hasMoreElements(); )
    {
      Token t = (Token) item.nextElement();
      annotateToken(t, annotationId, annotationObject, wsSensitive);
    }
  }
}