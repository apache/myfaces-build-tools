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
package org.apache.myfaces.trinidadinternal.view.faces.maven.plugin.javacc;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import org.codehaus.plexus.compiler.util.scan.InclusionScanException;
import org.codehaus.plexus.compiler.util.scan.SourceInclusionScanner;
import org.codehaus.plexus.compiler.util.scan.StaleSourceScanner;
import org.codehaus.plexus.compiler.util.scan.mapping.SuffixMapping;
import org.codehaus.plexus.util.FileUtils;


/**
 * @goal generate
 * @phase generate-sources
 * @description Goal which parse a JJ file and transform it to Java Source Files.
 * @version $Id$
 */
public class JavaCCMojo
  extends AbstractMojo
{
  /**
   * @parameter expression=1
   * @required
   */
  private int lookAhead;

  /**
   * @parameter expression=2
   * @required
   */
  private int choiceAmbiguityCheck;

  /**
   * @parameter expression=1
   * @required
   */
  private int otherAmbiguityCheck;

  /**
   * @parameter expression="true"
   * @required
   */
  private boolean isStatic;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean debugParser;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean debugLookAhead;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean debugTokenManager;

  /**
   * @parameter expression="true"
   * @required
   */
  private boolean optimizeTokenManager;

  /**
   * @parameter expression="true"
   * @required
   */
  private boolean errorReporting;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean javaUnicodeEscape;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean unicodeInput;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean ignoreCase;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean commonTokenAction;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean userTokenManager;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean userCharStream;

  /**
   * @parameter expression="true"
   * @required
   */
  private boolean buildParser;

  /**
   * @parameter expression="true"
   * @required
   */
  private boolean buildTokenManager;

  /**
   * @parameter expression="true"
   * @required
   */
  private boolean sanityCheck;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean forceLaCheck;

  /**
   * @parameter expression="false"
   * @required
   */
  private boolean cacheTokens;

  /**
   * @parameter expression="true"
   * @required
   */
  private boolean keepLineColumn;

  /**
   * Directory where the JJ file(s) are located.
   * @parameter expression="src/main/javacc"
   */
  private File sourceDirectory;

  /**
   * Path under source directory where the JJ file(s) are located.
   * @parameter expression=""
   */
  private String sourcePath;

  /**
   * Directory where the output Java Files will be located.
   * @parameter expression="${project.build.directory}/maven-javacc-plugin/src/main/java"
   */
  private File outputDirectory;

  /**
   * Path under output directory where the output Java Files will be located.
   * @parameter expression=""
   */
  private String outputPath;

  /**
   * the directory to store the processed .jj files
   *
   * @parameter expression="${project.build.directory}/maven-javacc-plugin/src/main/conf"
   */
  private File timestampDirectory;

  /**
   * The granularity in milliseconds of the last modification
   * date for testing whether a source needs recompilation
   *
   * @parameter expression="${lastModGranularityMs}" default-value="0"
   */
  private int staleMillis;

  /**
   * @parameter expression="${project}"
   * @required
   */
  private MavenProject project;

  /**
   * @parameter expression=false
   * @required
   */
  private boolean doNotGenerateTokenFile;

  public void execute()
    throws MojoExecutionException
  {
    File outDir = new File(outputDirectory, outputPath);

    if (!outDir.exists())
    {
      outDir.mkdirs();
    }

    if (!timestampDirectory.exists())
    {
      timestampDirectory.mkdirs();
    }

    Set staleGrammars = computeStaleGrammars();

    if (staleGrammars.isEmpty())
    {
      getLog().info("Nothing to process - all grammars are up to date");
      if (project != null)
      {
        project.addCompileSourceRoot(outDir.getPath());
      }
      return;
    }

    for (Iterator i = staleGrammars.iterator(); i.hasNext(); )
    {
      File javaccFile = (File) i.next();
      try
      {
        org.javacc.parser.Main.mainProgram(generateJavaCCArgumentList(javaccFile.getAbsolutePath()));

        FileUtils.copyFileToDirectory(javaccFile, timestampDirectory);
        if (doNotGenerateTokenFile == true) {
          File tokenFile  = new File(outDir, "Token.java");
          FileUtils.forceDelete(tokenFile);
        }
      }
      catch (Exception e)
      {
        throw new MojoExecutionException("JavaCC execution failed", e);
      }
    }

    if (project != null)
    {
      project.addCompileSourceRoot(outputDirectory.getPath());
    }
  }


  private String[] generateJavaCCArgumentList(String javaccInput)
  {

    File outDir = new File(outputDirectory, outputPath);

    ArrayList argsList = new ArrayList();

    argsList.add("-LOOKAHEAD=" + lookAhead);
    argsList.add("-CHOICE_AMBIGUITY_CHECK=" + choiceAmbiguityCheck);
    argsList.add("-OTHER_AMBIGUITY_CHECK=" + otherAmbiguityCheck);
    argsList.add("-STATIC=" + isStatic);
    argsList.add("-DEBUG_PARSER=" + debugParser);
    argsList.add("-DEBUG_LOOKAHEAD=" + debugLookAhead);
    argsList.add("-DEBUG_TOKEN_MANAGER=" + debugTokenManager);
    argsList.add("-OPTIMIZE_TOKEN_MANAGER=" + optimizeTokenManager);
    argsList.add("-ERROR_REPORTING=" + errorReporting);
    argsList.add("-JAVA_UNICODE_ESCAPE=" + javaUnicodeEscape);
    argsList.add("-UNICODE_INPUT=" + unicodeInput);
    argsList.add("-IGNORE_CASE=" + ignoreCase);
    argsList.add("-COMMON_TOKEN_ACTION=" + commonTokenAction);
    argsList.add("-USER_TOKEN_MANAGER=" + userTokenManager);
    argsList.add("-USER_CHAR_STREAM=" + userCharStream);
    argsList.add("-BUILD_PARSER=" + buildParser);
    argsList.add("-BUILD_TOKEN_MANAGER=" + buildTokenManager);
    argsList.add("-SANITY_CHECK=" + sanityCheck);
    argsList.add("-FORCE_LA_CHECK=" + forceLaCheck);
    argsList.add("-CACHE_TOKENS=" + cacheTokens);
    argsList.add("-KEEP_LINE_COLUMN=" + keepLineColumn);
    argsList.add("-OUTPUT_DIRECTORY:" + outDir.getPath());
    argsList.add(javaccInput);

    getLog().debug("argslist: " + argsList.toString());

    return (String[]) argsList.toArray(new String[argsList.size()]);
  }

  private Set computeStaleGrammars()
    throws MojoExecutionException
  {
    SuffixMapping mapping = new SuffixMapping(".jj", ".jj");
    SuffixMapping mappingCAP = new SuffixMapping(".JJ", ".JJ");

    SourceInclusionScanner scanner = new StaleSourceScanner(staleMillis);

    scanner.addSourceMapping(mapping);
    scanner.addSourceMapping(mappingCAP);

    Set staleSources = new HashSet();

    File sourceDir = new File(sourceDirectory, sourcePath);

    try
    {
      staleSources.addAll(scanner.getIncludedSources(sourceDir, 
                                                     timestampDirectory));
    }
    catch (InclusionScanException e)
    {
      throw new MojoExecutionException("Error scanning source root: \'" + 
                                       sourceDir + 
                                       "\' for stale grammars to reprocess.", 
                                       e);
    }

    return staleSources;
  }


}

