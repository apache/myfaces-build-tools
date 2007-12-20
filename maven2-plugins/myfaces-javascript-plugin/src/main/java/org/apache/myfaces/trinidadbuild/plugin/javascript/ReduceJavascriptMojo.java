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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.myfaces.trinidadbuild.plugin.javascript.uixtools.Reducer;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * @version $Id$
 * @goal reduce-javascript
 * @phase generate-sources
 * @description Goal which compiles the Javascript sources
 */
public class ReduceJavascriptMojo extends AbstractMojo
{
  public void execute() throws MojoExecutionException
  {
    List compileSourceRoots = new ArrayList();
    compileSourceRoots.add(sourceDirectory);
    // TODO: register these paths from inside relevant plugins
    compileSourceRoots.add(new File(project.getBuild().getDirectory(),
                                    "maven-i18n-plugin/main/javascript"));
    compileSourceRoots.add(new File(project.getBuild().getDirectory(),
                                    "maven-faces-plugin/main/javascript"));

    try
    {
      Resource resource = new Resource();
      resource.setDirectory(targetDirectory.getCanonicalPath());
      project.addResource(resource);

      // TODO: switch Optimized, not Debug, to be the special-case
      //       so that Debug can be processed as ordinary resource copy
      //       without having to explicitly code it here
      File outputDirectory = new File(targetDirectory, targetPath);
      outputDirectory.mkdirs();
      for (Iterator i = compileSourceRoots.iterator(); i.hasNext(); )
      {
        File sourceRoot = (File)i.next();
        File sourceDirectory = new File(sourceRoot, sourcePath);
        if (sourceDirectory.exists())
        {
          _copyJavascript(sourceDirectory, outputDirectory);
        }
      }
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error copying Javascript files", e);
    }


    try
    {
      if (optimizeTargetPath != null)
      {
        File outputDirectory = new File(targetDirectory, optimizeTargetPath);
        outputDirectory.mkdirs();
        for (Iterator i = compileSourceRoots.iterator(); i.hasNext(); )
        {
          File sourceRoot = (File)i.next();
          File sourceDirectory = new File(sourceRoot, sourcePath);
          if (sourceDirectory.exists())
          {
            String[] args = { sourceDirectory.getCanonicalPath(),
                              outputDirectory.getCanonicalPath() };
            // TODO: incremental check
            Reducer.main(args);
          }
        }
      }
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error compiling Javascript files", e);
    }
  }

  static private void _copyJavascript(File sourceDir, File targetDir)
    throws IOException
  {
    File[] sourceFiles = sourceDir.listFiles();
    if (sourceFiles != null)
    {
      for (File sourceFile : sourceFiles)
      {
        if (sourceFile.isDirectory())
        {
          File targetSubdir = new File(targetDir, sourceFile.getName());
          if (!targetSubdir.exists())
            targetSubdir.mkdir();
          _copyJavascript(sourceFile, targetSubdir);
        }
        else if (sourceFile.getName().endsWith(".js"))
        {
          FileUtils.copyFileToDirectory(sourceFile, targetDir);
        }
      }
    }
  }

  /**
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter expression="src/main/javascript"
   * @required
   */
  private File sourceDirectory;

  /**
   * @parameter
   * @required
   */
  private String sourcePath;

  /**
   * @parameter expression="${project.build.directory}/maven-javascript-plugin/main/resources"
   * @required
   */
  private File targetDirectory;

  /**
   * @parameter
   */
  private String targetPath;

  /**
   * @parameter
   */
  private String optimizeTargetPath;

}
