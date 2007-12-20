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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class JSParserTest15
{
  protected InputStream _in;

  public JSParserTest15(InputStream in)
  {
    _in = in;
  }

  public void start()
  {
    // create an instance of the parser
    JSParser15 jsParser = new JSParser15(_in);

    Token t;

    try
    {
      for (t = jsParser.Program(); t != null; t = t.next)
      {
        System.out
        .println(t.kind + "\t" + JSParser15.tokenImage[t.kind] + "\t" +
                           t.image + "\t" +
                           ((AnnotatedToken) t).getAnnotationKind() +
                           "\t" + ((AnnotatedToken) t).canRemove());
        printSpecialTokens((AnnotatedToken) t);
      }

      // jsParser.getRootNode().dump("p");
    }
    catch (Throwable e)
    {
      e.printStackTrace();
    }
  }

  public void printSpecialTokens(AnnotatedToken token)
  {
    AnnotatedToken specialToken = token.getSpecialToken();

    while (specialToken != null)
    {
      System.out.println(specialToken.image);
      specialToken = specialToken.getSpecialToken();
    }
  }

  public static void main(String[] args)
  {
    if (args.length == 0)
    {
      System.out.println("Usage: JSParserTest15 <JS File>");

      return;
    }

    File jsFile = new File(args[0]);
    FileInputStream inFileStream;

    try
    {
      inFileStream = new FileInputStream(jsFile);

      JSParserTest15 jsParserTest = new JSParserTest15(inFileStream);
      jsParserTest.start();
      inFileStream.close();
    }
    catch (FileNotFoundException e)
    {
      System.out.println("FileNotFound");
    }
    catch (IOException e)
    {
      System.out.println("FileIO");
    }
  }
}