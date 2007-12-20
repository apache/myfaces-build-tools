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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Reduces JavaScript files by stripping comments and redundant whitespace
 * and renaming local variable names to shorter ones.
 * @version $Name:  $ ($Revision$) $Date$
 */
public class Reducer extends FileProcessor
{
  /**
   * creates a new Reducer.
   * @param whitespaceComments if true removes comments and extra whitespace
   * @param localVars if true renames local variable names to shorter ones.
   */
  public Reducer(boolean whitespaceComments, boolean localVars)
  {
    super(".js", false);
    _STRIP_WHITESPACE_COMMENTS = whitespaceComments;
    _RENAME_LOCAL_VARIABLES = localVars;
  }

  public Reducer()
  {
    this(true, true);
  }

  /**
   * reduces a JavaScript file.
   * @param in the source to read from
   * @param out the source to write the reduced form to
   */
  public void process(BufferedReader in, PrintWriter out)
    throws IOException, InterruptedException
  {
    TokenReader tr = new Tokenizer(in);
    if (_STRIP_WHITESPACE_COMMENTS) tr = new Filter1(tr);
    if (_RENAME_LOCAL_VARIABLES) tr = new Filter2(tr);
    Detokenizer detok = new Detokenizer(out);
    for(;;)
    {
      Token tok = tr.read();
      if (tok==null) break;
      else detok.write(tok);
    }
  }

  /**
   * @see FileProcessor#processFile(File, File)
   */
  protected void processFile(File in, File out)
    throws IOException, InterruptedException
  {
    BufferedReader reader = new BufferedReader(new FileReader(in));
    PrintWriter writer =  new PrintWriter(new FileWriter(out));
    process(reader, writer);
    writer.close();
    reader.close();
  }

  private static void _help()
  {
    String s;
    s = "Reduces JavaScript source code\n" +
      "Usage:\n" +
      "java oracle.uix.tools.uix22.javascript.Reducer" +
      " [-norename] [-whitespace] [-help] input output \n" +
      " input/output can be either files or directories.\n" +
      " Directories will be processed recursively.\n" +
      " Only files with names that end with .js will be processed.\n" +
      " -norename prevents renaming local variables to short ones\n" +
      " -whitespace prevents removing comments and extra whitespace\n" +
      " -help prints this message.";
    System.out.println(s);
  }

  public static void main(String[] args)
  {
    boolean rename = true;
    boolean space = true;

    final int sz = args.length-2;

    if (sz<0)
    {
      _help();
      return;
    }

    for(int i=0; i<sz; i++)
    {
      String s = args[i];
      if (s.equals("-help")) _help();
      else if (s.equals("-norename")) rename = false;
      else if (s.equals("-whitespace")) space = false;
      else
      {
        System.out.println("Unknown option:"+s);
        _help();
        return;
      }
    }

    File in = new File(args[sz]);
    File out = new File(args[sz+1]);
    Reducer reducer = new Reducer(space, rename);
    reducer.process(in, out);
  }

  private final boolean _STRIP_WHITESPACE_COMMENTS;
  private final boolean _RENAME_LOCAL_VARIABLES;
}