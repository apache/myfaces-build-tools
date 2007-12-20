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

/**
 * Tokens for JavaScript source code
 * @version $Name:  $ ($Revision$) $Date$
 */
public class Token
{

  public Token(int code, int lineNumber)
  {
    this(code, lineNumber, (char) 0, (String) null);
  }

  public Token(int code, int lineNumber, char ch)
  {
    this(code, lineNumber, ch, (String) null);
  }

  public Token(int code, int lineNumber, String s)
  {
    this(code, lineNumber, (char) 0, s);
  }

  public Token(int code, int lineNumber, char ch, String s)
  {
    this.code=code;
    this.lineNumber = lineNumber;
    this.ch=ch;
    string = s;
  }

  public String toString()
  {
    return "Token code:"+code+" line:"+lineNumber+
      " char:"+ch+" string:"+string;
  }

  public final int code, lineNumber;
  public final char ch;
  public final String string;

  public static final int EOF =                 0;
  public static final int NEWLINE =             10;
  public static final int WHITESPACE =          11;
  public static final int PERIOD =              12;
  public static final int SEMICOLON =           13;
  public static final int QUOTED =              20;
  public static final int NAME =                30;
  public static final int NUMBER =              40;
  public static final int COMMENT =             50;
  public static final int REGULAR_EXP =         60;
  public static final int REGULAR_EXP_MODIFIER =65;
  public static final int CONTROL =             100;
  public static final int LEFT_BRACE =          110;
  public static final int RIGHT_BRACE =         120;
  public static final int RESERVED =            200; //reserved words

}