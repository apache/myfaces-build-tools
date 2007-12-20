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

package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.configuration.ConfigException;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.configuration.ObfuscatorConfig;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.ObfuscatorFilter;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.compression.CompressionFilter;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.keywords.SpecialKeywordsFilter;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.obfuscation.ObfuscationFilter;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.filters.output.OutputGenerator;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.AnnotatedToken;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.JSParser15;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser.ParseException;
import org.apache.myfaces.trinidadbuild.plugin.javascript.uixtools.FileProcessor;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

//~--- classes ----------------------------------------------------------------

public class Obfuscator
  extends FileProcessor
{
  private Vector _filters = new Vector();

  private ObfuscatorFilter _keywordsFilter = new SpecialKeywordsFilter();

  private ObfuscationFilter _obfuscationFilter = new ObfuscationFilter();

  private OutputGenerator _outputGenerator = new OutputGenerator();

  private ObfuscatorConfig _config;

  private JSParser15 _jsParser;

  private boolean _obfuscate;

  private boolean _replaceCharLiterals;

  private boolean _stripComments;

  private boolean _stripNewlines;

  private boolean _stripSpecialKeywords;

  private boolean _stripWhitespaces;

  //~--- constructors -------------------------------------------------------

  public Obfuscator(boolean obfuscate, boolean stripComments,
                    boolean stripWhitespaces, boolean stripNewlines,
                    boolean stripSpecialKeywords,
                    boolean replaceCharLiterals, ObfuscatorConfig config)
    throws ConfigException
  {
    super("js", false);
    _obfuscate = obfuscate;
    _stripComments = stripComments;
    _stripWhitespaces = stripWhitespaces;
    _stripNewlines = stripNewlines;
    _stripSpecialKeywords = stripSpecialKeywords;
    _replaceCharLiterals = replaceCharLiterals;
    _config = config;
  }

  //~--- methods ------------------------------------------------------------

  private void init(InputSource in)
  {

    // apply overrides
    boolean obfuscate = in.skipObfuscation()? false: _obfuscate;
    boolean stripComments = in.skipStripComments()? false: _stripComments;
    boolean stripWhitespaces =
      in.skipStripWhitespaces()? false: _stripWhitespaces;
    boolean stripNewLines = in.skipStripNewlines()? false: _stripNewlines;
    boolean stripSpecialKeywords =
      in.skipStripSpecialKeywords()? false: _stripSpecialKeywords;

    // setup filters
    _filters.clear();

    if (stripSpecialKeywords)
    {
      _filters.add(_keywordsFilter);
    }

    if (stripWhitespaces || stripNewLines || stripComments)
    {
      _filters
      .add(new CompressionFilter(stripComments, stripWhitespaces, stripNewLines));
    }

    if (obfuscate)
    {
      _filters.add(_obfuscationFilter);
    }

    // make output generator the last filter
    _filters.add(_outputGenerator);
  }

  private void initParser(InputStream in)
  {
    if (_jsParser == null)
    {
      _jsParser = new JSParser15(in);
    }
    else
    {
      _jsParser.ReInit(in);
    }
  }

  public void process(InputSource in, OutputStream out)
    throws ParseException
  {
    init(in);
    _outputGenerator.setOutputStream(out);

    AnnotatedToken token = tokenize(in.getInputStream());

    for (Iterator itr = _filters.iterator(); itr.hasNext(); )
    {
      ObfuscatorFilter filter = (ObfuscatorFilter) itr.next();

      token = filter.filter(token);
    }
  }

  protected void processFile(File in, File out)
    throws Exception
  {
    if (_obfuscate == true)
    {
      FileInputStream inStream = new FileInputStream(in);
      FileOutputStream outStream = new FileOutputStream(out);
      String fileName = in.getName();
      InputSource inpSource =
        new InputSource(inStream,
                          _config.skipObfuscation(fileName),
                          _config.skipStripComments(fileName),
                          _config.skipStripWhitespaces(fileName),
                          _config.skipStripNewlines(fileName),
                          _config.skipStripSpecialKeywords(fileName));

      process(inpSource, outStream);
      inStream.close();
      outStream.close();
    }
    else
    {
      // Just copy the files over.
      FileUtils.touch(out);
      FileUtils.copyFile(in,out);
    }
  }

  private AnnotatedToken tokenize(InputStream in)
    throws ParseException
  {
    initParser(in);

    return (AnnotatedToken) _jsParser.Program();
  }
}

