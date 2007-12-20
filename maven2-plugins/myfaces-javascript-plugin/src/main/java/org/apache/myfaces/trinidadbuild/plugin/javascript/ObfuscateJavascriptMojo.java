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
package org.apache.myfaces.trinidadbuild.plugin.javascript;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.Obfuscator;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.configuration.ConfigException;
import org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.configuration.ObfuscatorConfig;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;

import java.util.ArrayList;
import java.util.List;


//~--- classes ----------------------------------------------------------------

/**
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal obfuscate-javascript
 * @phase generate-resources
 * @description Goal which obfuscates the Javascript sources
 */
public class ObfuscateJavascriptMojo
  extends AbstractMojo
{

  //~--- methods ------------------------------------------------------------

  public void execute()
    throws MojoExecutionException
  {
// List compileSourceRoots = new ArrayList();
// compileSourceRoots.add(sourceDirectory);

    try
    {
      _obfuscator = 
          new Obfuscator(obfuscate, stripComments, stripWhitespaces, 
                         stripNewlines, stripSpecialKeywords, 
                         replaceCharLiterals, obfuscatorConfig);

//      File outputDirectory = new File(targetDirectory, targetPath);

      targetDirectory.mkdirs();

      for (File sourceRoot: sourceDirectory)
      {
//        File sourceDirectory = new File(sourceRoot, sourcePath);
        if (sourceRoot.exists())
        {
          _obfuscator.process(sourceRoot, targetDirectory);
        }
      }
    }
    catch (ConfigException e)
    {
      throw new MojoExecutionException("Invalid configuration parameters", 
                                       e);
    }
  }
  private Obfuscator _obfuscator;


  /**
   * @parameter
   * @required
   */
  private boolean obfuscate = false;

  /**
   * @parameter
   * @required
   */
  private boolean replaceCharLiterals = false;

  /**
   * @parameter
   * @required
   */
  private boolean stripComments = false;

  /**
   * @parameter
   * @required
   */
  private boolean stripNewlines = false;

  /**
   * @parameter
   * @required
   */
  private boolean stripSpecialKeywords = false;

  /**
   * @parameter
   * @required
   */
  private boolean stripWhitespaces = false;

  /**
   * @parameter 
   * @required
   */
  private File[] sourceDirectory;


  /**
   * @parameter expression="${project.build.directory}/classes"
   * @required
   */
  private File targetDirectory;

  /**
   * @parameter
   * @required
   */
  private ObfuscatorConfig obfuscatorConfig;
}

