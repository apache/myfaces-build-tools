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
package org.apache.myfaces.trinidadbuild.plugin.faces;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.myfaces.trinidadbuild.plugin.faces.GenerateComponentsMojo;
import org.apache.myfaces.trinidadbuild.plugin.faces.AbstractMojoTestCase;

/**
 * Tests the component generation mojo.
 */
public class GenerateComponentsMojoTest extends AbstractMojoTestCase
{
  /**
   * Creates a new GenerateComponentsMojoTest.
   *
   * @param testName  the test to execute
   */
  public GenerateComponentsMojoTest(
    String testName)
  {
    super(testName);
  }

  public void setUp() throws MojoExecutionException
  {
    Mojo mojo = new GenerateComponentsMojo();
    File genSrcDir = new File("target/mojo-test-output/java");

    setMojoProject(mojo, "project");
    setMojoField(mojo, "packageContains", "org");
    setMojoField(mojo, "typePrefix", "org.apache");
    setMojoField(mojo, "templateSourceDirectory",
                           new File("src/test/java-templates"));
    setMojoField(mojo, "generatedSourceDirectory", genSrcDir);
    setMojoField(mojo, "force", Boolean.TRUE);

    _mojo = mojo;
    _genSrcDir = genSrcDir;
  }

  public void tearDown()
  {
    _mojo = null;
    _genSrcDir = null;
  }

    public void testMissingDefaultIndex() throws MojoExecutionException, MojoFailureException
  {
    _mojo.execute();
  }
/*
  public void testSkipAPI() throws MojoExecutionException
  {
    setMojoField(_mojo, "resourcePath",
                           "META-INF/maven-faces-plugin/testSkipAPI.lst");
    _mojo.execute();

    File targetFile = new File(_genSrcDir,
                               "javax/faces/component/UINamingContainer.java");
    if (targetFile.exists())
      fail("JavaServer Faces API component generated");
  }
*/
  public void testGenerate() throws MojoExecutionException, MojoFailureException
  {
    setMojoField(_mojo, "resourcePath",
                        "META-INF/maven-faces-plugin/testGenerate.lst");
    _mojo.execute();

    File targetFile = new File(_genSrcDir,
                               "org/apache/myfaces/trinidad/component/UIXCommand.java");
    if (!targetFile.exists())
      fail("Component not generated");

    // TODO: verify identity content with trinidad Faces example
  }

  public void testGenerateWithTemplate() throws MojoExecutionException, MojoFailureException
  {
    setMojoField(_mojo, "resourcePath",
                           "META-INF/maven-faces-plugin/testGenerate.lst");
    _mojo.execute();

    File targetFile = new File(_genSrcDir,
                               "org/apache/myfaces/trinidad/component/UIXCommand.java");
    if (!targetFile.exists())
      fail("Component not generated");

    // TODO: verify identity content with trinidad Faces example
  }

  private Mojo _mojo;
  private File _genSrcDir;
}
