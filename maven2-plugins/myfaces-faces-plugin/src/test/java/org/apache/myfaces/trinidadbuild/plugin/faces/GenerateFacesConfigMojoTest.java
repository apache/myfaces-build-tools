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
import org.apache.myfaces.trinidadbuild.plugin.faces.GenerateFacesConfigMojo;

/**
 * Tests the faces-config.xml generation mojo.
 */
public class GenerateFacesConfigMojoTest extends AbstractMojoTestCase
{
  /**
   * Creates a new GenerateFacesConfigMojoTest.
   *
   * @param testName  the test to execute
   */
  public GenerateFacesConfigMojoTest(
    String testName)
  {
    super(testName);
  }

  public void setUp() throws MojoExecutionException
  {
    Mojo mojo = new GenerateFacesConfigMojo();
    File targetDir = new File("target/mojo-test-output/java");

    setMojoProject(mojo, "project");
    setMojoField(mojo, "configDirectory",
                           new File("src/test/resources"));
    setMojoField(mojo, "configPath", "META-INF/faces-config.xml");
    setMojoField(mojo, "targetDirectory", targetDir);
    setMojoField(mojo, "force", Boolean.TRUE);

    _mojo = mojo;
    _targetDir = targetDir;
  }

  public void tearDown()
  {
    _mojo = null;
    _targetDir = null;
  }

    public void testGenerate() throws MojoExecutionException, MojoFailureException
  {
    _mojo.execute();

    // TODO: verify content is correct! 
  }

  private Mojo _mojo;
  private File _targetDir;
}
