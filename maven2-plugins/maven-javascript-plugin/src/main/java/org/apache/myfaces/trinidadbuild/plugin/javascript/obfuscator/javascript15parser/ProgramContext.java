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

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

//~--- classes ----------------------------------------------------------------

public class ProgramContext
{

  // Table of symbol name, AnnotatedToken
  protected HashMap _symbolTable = new HashMap();
  protected String _name;
  protected NameGen _nameGen;

  //~--- constructors -------------------------------------------------------

  public ProgramContext(String name)
  {
    this(name, new NameGen());
  }

  public ProgramContext(String name, NameGen nameGen)
  {
    _name = name;
    _nameGen = nameGen;
  }

  //~--- methods ------------------------------------------------------------

  public void addToken(String name, AnnotatedToken token)
  {
    _symbolTable.put(name, token);
  }

  public String generateSymbolName()
  {
    return _nameGen.getName();
  }

  //~--- get methods --------------------------------------------------------

  public String getName()
  {
    return _name;
  }

  public NameGen getNameGen()
  {
    return _nameGen;
  }

  public AnnotatedToken getToken(String key)
  {
    return (AnnotatedToken) _symbolTable.get(key);
  }

  //~--- set methods --------------------------------------------------------

  public void setName(String name)
  {
    _name = name;
  }

  public void setNameGen(NameGen nameGen)
  {
    _nameGen = nameGen;
  }
}


//~ Formatted by Jindent --- http://www.jindent.com