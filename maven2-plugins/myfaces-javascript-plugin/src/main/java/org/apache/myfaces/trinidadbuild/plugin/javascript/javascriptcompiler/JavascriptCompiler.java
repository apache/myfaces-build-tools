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
package org.apache.myfaces.trinidadbuild.plugin.javascript.javascriptcompiler;

import java.io.BufferedOutputStream;

import org.apache.myfaces.trinidadbuild.plugin.javascript.uixtools.FileProcessor;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.JSParser20;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.Token;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintStream;

import java.util.HashMap;

public class JavascriptCompiler extends FileProcessor
{
  private JSParser20        _jsParser;
  public  HashMap<String, String>         fileTable;

  public JavascriptCompiler()
  {
    super("js", false);
    fileTable = new HashMap<String,String>();
  }

  private void init(InputStream in)
  {
    // apply overrides
  }

  protected void processFile(File in, File out)
    throws Exception
  {
    String className, newClassName;
    FileInputStream inStream = new FileInputStream(in);
    //
    //  Get the name of the file
    //
    className = in.getName();
    className = className.substring(0, className.indexOf('.'));
    //
    //  Check to see if the name exists as we do not want to over write the four
    //  special names we are using.
    //
    newClassName = fileTable.get(className);
    if ( newClassName == null) 
    {
//       newClassName = "Adf" + className;
//      fileTable.put(className, newClassName);
      newClassName = className;
    }
    out = new File(out.getParentFile(), newClassName + ".js");
    PrintStream outStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(out)));
    process(inStream, outStream);
    inStream.close();
    outStream.flush();
    outStream.close();
  }
  private void initParser(InputStream in)
  {

    if (_jsParser == null)
    {
      _jsParser = new JSParser20(in);
    }
    else
    {
      _jsParser.ReInit(new InputStreamReader(in));
    }
  }
  public void process(InputStream in, PrintStream o) throws ParseException
  {
    Token token = null;
    DepthFirstTraversal vstr;
    init(in);
    token = tokenize(in);
    //
    //   Create an instance of DepthFirstTraversal
    //
    vstr = new DepthFirstTraversal(o, fileTable); 
    _jsParser.getRootNode().jjtAccept(vstr, null);
  }
  private Token tokenize(InputStream in) throws ParseException
  {
    initParser(in);
    return (Token) _jsParser.Program();
  }

}
