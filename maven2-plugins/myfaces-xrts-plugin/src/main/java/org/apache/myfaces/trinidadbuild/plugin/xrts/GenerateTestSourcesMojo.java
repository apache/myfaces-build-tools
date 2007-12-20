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
package org.apache.myfaces.trinidadbuild.plugin.xrts;

import java.io.File;
import java.io.IOException;

import org.apache.maven.project.MavenProject;

/**
 * @version $Id$
 * @goal generate-test-sources
 * @phase generate-test-sources
 * @description Goal which generates the XRTS Bundles
 */
public class GenerateTestSourcesMojo extends AbstractGenerateSourcesMojo
{
  /**
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter expression="list"
   * @required
   */
  private String targetType;

  /**
   * @parameter
   */
  private String[] defaultLocales;

  /**
   * @parameter
   */
  private String[] testExcludes;

  /**
   * @parameter expression="src/test/xrts"
   * @required
   */
  private File testSourceDirectory;

  /**
   * @parameter expression="${project.build.directory}/maven-xrts-plugin/test/java"
   * @required
   */
  private File targetDirectory;

  protected String[] getDefaultLocales()
  {
    return defaultLocales;
  }

  protected String[] getExcludes()
  {
    return testExcludes;
  }

  protected void addCompileSourceRoot() throws IOException
  {
    project.addTestCompileSourceRoot(targetDirectory.getCanonicalPath());
  }

  protected String getTargetType()
  {
    return targetType;
  }

  protected File getSourceDirectory()
  {
    return testSourceDirectory;
  }

  protected File getTargetDirectory()
  {
    return targetDirectory;
  }
}
