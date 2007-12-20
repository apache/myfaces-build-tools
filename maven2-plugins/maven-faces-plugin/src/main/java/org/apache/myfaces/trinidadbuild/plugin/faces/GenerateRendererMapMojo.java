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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.FacesConfigBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.RenderKitBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.RendererBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

/**
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal generate-renderer-map
 * @phase generate-sources
 */
public class GenerateRendererMapMojo extends AbstractFacesMojo
{
  /**
   * Execute the Mojo.
   */
  public void execute() throws MojoExecutionException
  {
    try
    {
      processIndex(project, resourcePath);
      _generateRendererMap();
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error generating components", e);
    }
  }

  /**
   * Generates parsed components.
   */
  private void _generateRendererMap() throws IOException
  {
    // Make sure generated source directory
    // is added to compilation source path
    project.addCompileSourceRoot(generatedSourceDirectory.getCanonicalPath());

    FacesConfigBean facesConfig = getFacesConfig();
    if (!facesConfig.hasRenderKits())
    {
      getLog().info("Nothing to generate - no renderkits found");
    }
    else
    {
      Iterator renderkits = facesConfig.renderKits();
      while (renderkits.hasNext())
      {
        RenderKitBean renderkit = (RenderKitBean) renderkits.next();
        String id = renderkit.getRenderKitId();
        if (!id.startsWith(renderKitPrefix))
          continue;

        _writeRenderKitMap(renderkit);
      }
    }
  }

  private void _writeRenderKitMap(RenderKitBean rk) throws IOException
  {
    File outFile = new File(generatedSourceDirectory,
                            "/META-INF/" + rk.getRenderKitId() + ".renderkit");
    FileWriter fw = new FileWriter(outFile);
    PrintWriter pw = new PrintWriter(fw);
    try
    {
      Iterator renderers = rk.renderers();
      while (renderers.hasNext())
      {
        RendererBean r = (RendererBean) renderers.next();
        pw.print(r.getComponentFamily());
        pw.print('|');
        pw.print(r.getRendererType());
        pw.print('=');
        pw.println(r.getRendererClass());
      }
    }
    finally
    {
      pw.close();
    }
  }

  /**
   * @parameter expression="${project}"
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter
   * @readonly
   */
  private String resourcePath = "META-INF/maven-faces-plugin/index.lst";

  /**
   * @parameter expression="${project.build.directory}/maven-faces-plugin/main/resources"
   * @required
   */
  private File generatedSourceDirectory;

  /**
   * @parameter
   * @required
   */
  private String renderKitPrefix;
}
