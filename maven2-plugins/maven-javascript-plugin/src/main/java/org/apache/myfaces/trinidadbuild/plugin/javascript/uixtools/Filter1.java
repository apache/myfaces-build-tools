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

/**
 * Removes all comments and redundant whitespace
 * @version $Name:  $ ($Revision$) $Date$
 */
public class Filter1 implements TokenReader
{

  /**
   * blocks until is has read atleast one token from the input reader
   */
  public Filter1(TokenReader in) throws IOException, InterruptedException
  {
    _in = in;
    _next = _in.read();
  }

  /**
   * @see TokenReader
   */
  public Token read() throws IOException, InterruptedException
  {
    Token cur = _next;
    Token res = cur;
    for(;cur!=null;)
      {
        _next = _in.read();
        res = _filter(_prev, cur, _next) ;
        if (res!=null)
          {
            _prev = res;
            break;
          }
        else cur = _next;
      }
    return res;
  }

  /**
   * Removes extra spaces/newlines and all comments
   */
  private Token _filter(Token prev, Token cur, Token next)
  {
    //ystem.out.println("filtering token:"+cur);
    int ccode = cur.code;
    if ((ccode==Token.COMMENT) ||   //skip comments
        ((ccode==Token.WHITESPACE) &&
         !(_isSpaceSensitive(prev) && _isSpaceSensitive(next))) ||
        ((ccode==Token.NEWLINE) &&
         ((prev==null) || (prev.code==Token.NEWLINE))))
      return null;
    else return cur;
  }

  /**
   * @return true if the given token is affected by whitespace preceding or
   *  following it.
   */
  private boolean _isSpaceSensitive(Token tok)
  {
    if (tok==null) return false;
    int i = tok.code;
    if ((i == Token.NAME) || (i == Token.NUMBER) || (i == Token.RESERVED) ||
        (i == Token.PERIOD))
      return true;
    else return false;
  }

  private final TokenReader _in;
  private Token _prev = null;
  private Token _next;
}