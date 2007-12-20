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
package org.apache.myfaces.trinidadbuild.plugin.i18n;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.myfaces.trinidadbuild.plugin.i18n.uixtools.JSLocaleElementsGenerator;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * @version $Id$
 * @goal generate-javascript-locales
 * @phase generate-sources
 */
public class GenerateJavascriptLocalesMojo extends AbstractMojo
{
  /**
   * Execute the Mojo.
   */
  public void execute() throws MojoExecutionException
  {
    try
    {
      // add generated sources to compilation classpath
      addResourceRoot(targetDirectory.getCanonicalPath());

      File outputDirectory = new File(targetDirectory, targetPath);

      // Incremental check
      boolean uptodate = false;

      // TODO: determine how best to check for incremental
//      if (!force)
//      {
//        String[] localeStrings = getLocaleStrings();
//        String bundlePath = JSLocaleElementsGenerator._DEFAULT_BUNDLE_LOCATION_PATH;
//        for (int i=0; i < localeStrings.length; i++)
//        {
//          String path = bundlePath + "LocaleElements" +
//                        localeStrings[i] + ".java";
//          File targetFile = new File(targetDirectory, path);
//          if (!targetFile.exists())
//          {
//            uptodate = false;
//            break;
//          }
//        }
//      }

      if (force || !uptodate)
      {
        getLog().info("Generating Javascript Locales");

        List argsList = new ArrayList();

        argsList.add("outDir=" + outputDirectory.getCanonicalPath());
        argsList.add("writeJavascript=true");
        argsList.add("writeSource=false");
        argsList.add("verbose=false");
        argsList.add("prettyPrint=true");

        String[] args = (String[])argsList.toArray(new String[0]);
        JSLocaleElementsGenerator.main(args);
      }
      else
      {
        getLog().info("Nothing to generate - Javascript Locales are up to date");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new MojoExecutionException(e.getMessage());
    }
  }

  // TODO: use project.addResourceRoot()
  protected void addResourceRoot(
    String resourceRoot)
  {
    List resources = project.getBuild().getResources();
    Resource resource = new Resource();
    resource.setDirectory(resourceRoot);
    resources.add(resource);
  }

  private String[] getLocaleStrings()
  {
    if ("en_US".equals(locale))
      return new String[] { "" };

    Locale[] locales = Locale.getAvailableLocales();
    String[] localeStrings = new String[locales.length];

    for (int i=0; i < localeStrings.length; i++)
    {
      localeStrings[i] = locales[i].getDisplayName();
    }

    return localeStrings;
  }

  /**
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter expression="${project.build.directory}/maven-i18n-plugin/main/javascript"
   * @required
   */
  private File targetDirectory;

  /**
   * @parameter
   */
  private String targetPath = "";

  /**
   * @parameter
   */
  private String locale;

  /**
   * @parameter
   */
  private boolean force;
}
