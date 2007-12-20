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
package org.apache.myfaces.trinidadbuild.plugin.javascript.uixtools;

import java.io.IOException;
import java.io.Writer;
import java.io.PrintWriter;

/**
 * Converts Token Objects into a JavaScript file
 * @version $Name:  $ ($Revision$) $Date$
 */
public class Detokenizer
{
  private final PrintWriter _out;

  /**
   * @param out the file to write the output to.
   */
  public Detokenizer(PrintWriter out)
  {
    this._out = out;
  }

  /**
   * Creates a new PrintWriter and calls the other constructor.
   */
  public Detokenizer(Writer out)
  {
    this(new PrintWriter(out));
  }

  /**
   * decodes a token and writes the result
   * @param tok the token to decode.
   */
  public void write(Token tok) throws IOException
  {
    int com = tok.code;
    switch(com)
      {
      case Token.NEWLINE :
        _out.println();
        break;
      case Token.WHITESPACE :
        _out.print(' ');
        break;
      case Token.PERIOD :
        _out.print('.');
        break;
      case Token.SEMICOLON :
        _out.print(';');
        break;
      case Token.QUOTED :
        {
          char ch = tok.ch;
          _out.print(ch+tok.string+ch);
        }
        break;
      case Token.CONTROL :
      case Token.LEFT_BRACE :
      case Token.RIGHT_BRACE :
        _out.print(tok.ch);
        break;
      case Token.NUMBER :
      case Token.RESERVED :
      case Token.NAME :
        _out.print(tok.string);
        break;
      case Token.COMMENT :
        _out.print("/*"+tok.string+"*/");
        break;
      case Token.REGULAR_EXP :
        _out.print("/"+tok.string+"/");
        break;
      case Token.REGULAR_EXP_MODIFIER :
        _out.print(tok.ch);
        break;
      case Token.EOF :
        _out.close();
        break;
      default :
        throw new RuntimeException("Unknown Token:"+tok);
      }
  }
}