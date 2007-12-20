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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.component.ComponentGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.component.MyFacesComponentGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.component.TrinidadComponentGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ComponentBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.FacesConfigBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.ComponentFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.SourceTemplate;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.util.Iterator;

/**
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal generate-components
 * @phase generate-sources
 */
public class GenerateComponentsMojo extends AbstractFacesMojo
{
  /**
   * Execute the Mojo.
   */
  public void execute() throws MojoExecutionException
  {
    try
    {
      processIndex(project, resourcePath);
      _generateComponents();
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error generating components", e);
    }
  }

  /**
   * Generates parsed components.
   */
  private void _generateComponents() throws IOException, MojoExecutionException
  {
    // Make sure generated source directory
    // is added to compilation source path
    project.addCompileSourceRoot(generatedSourceDirectory.getCanonicalPath());

    FacesConfigBean facesConfig = getFacesConfig();
    if (!facesConfig.hasComponents())
    {
      getLog().info("Nothing to generate - no components found");
    }
    else
    {
      if (suppressListenerMethods)
        getLog().warn("Event listener methods will not be generated");

      Iterator components = facesConfig.components();
      components = new FilteredIterator(components, new SkipFilter());
      components = new FilteredIterator(components,
                                        new ComponentTypeFilter(typePrefix));

      // incremental unless forced
      if (!force)
        components = new FilteredIterator(components, new IfModifiedFilter());

      if (!components.hasNext())
      {
        getLog().info("Nothing to generate - all components are up to date");
      }
      else
      {
        int count = 0;
        while (components.hasNext())
        {
          _generateComponent((ComponentBean)components.next());
          count++;
        }
        getLog().info("Generated " + count + " component(s)");
      }
    }
  }

  /**
   * Generates a parsed component.
   *
   * @param component  the parsed component metadata
   */
  private void _generateComponent(
    ComponentBean component) throws MojoExecutionException
  {
    ComponentGenerator generator;

    String fullClassName = component.getComponentClass();

    if (component.isTrinidadComponent())
    {
      generator = new TrinidadComponentGenerator(getLog(), _is12());
    }
    else 
    {
      generator = new MyFacesComponentGenerator(getLog(),_is12() );
    }

    try
    {
      getLog().debug("Generating " + fullClassName+", with generator: "+generator.getClass().getName());

      String sourcePath = Util.convertClassToSourcePath(fullClassName, ".java");
      File targetFile = new File(generatedSourceDirectory, sourcePath);

      StringWriter sw = new StringWriter();
      PrettyWriter out = new PrettyWriter(sw);

      String className = Util.getClassFromFullClass(fullClassName);
      String componentFamily = component.findComponentFamily();

      if (componentFamily == null)
      {
        getLog().warn("Missing <component-family> for \"" +
                       fullClassName + "\", generation of this Component is skipped");
      }
      else
      {
        String packageName = Util.getPackageFromFullClass(fullClassName);
        String fullSuperclassName = component.findComponentSuperclass();
        String superclassName = Util.getClassFromFullClass(fullSuperclassName);

        // make class name fully qualified in case of collision
        if (superclassName.equals(className))
          superclassName = fullSuperclassName;

        // TODO: remove this bogosity
        if (superclassName.equals("UIXMenuHierarchy") ||
            superclassName.equals("UIXTable") ||
            superclassName.equals("UIXHierarchy") ||
            superclassName.equals("UIXMenuTree") ||
            className.equals("CoreTree"))
        {
          superclassName = fullSuperclassName;
        }


        String componentType = component.getComponentType();

        // Use template file if it exists
        String templatePath = Util.convertClassToSourcePath(fullClassName, "Template.java");
        File templateFile = new File(templateSourceDirectory, templatePath);

        SourceTemplate template = null;
        if (templateFile.exists())
        {
          getLog().debug("Using template " + templatePath);
          template = new SourceTemplate(templateFile);
          template.substitute(className + "Template", className);
          template.readPreface();
        }

        // header/copyright
        writePreamble(out);

        // package
        out.println("package " + packageName + ";");
        out.println();

        // imports
        generator.writeImports(out, template, packageName,
                      fullSuperclassName, superclassName,
                      component);

        // class
        generator.writeClassBegin(out, className, superclassName, component, template);

        // static final constants
        generator.writePropertyValueConstants(out, component);
        generator.writePropertyConstants(out, superclassName, component);
        generator.writeFacetConstants(out, component);
        generator.writeGenericConstants(out, componentFamily, componentType);

        // public constructors and methods
        generator.writeConstructor(out, component, Modifier.PUBLIC);

        // insert template code
        if (template != null)
        {
          template.writeContent(out);
          template.close();
        }

        generator.writeFacetMethods(out, component);

        if (template == null)
        {
          generator.writePropertyMethods(out, component);
        }
        else
        {
          generator.writePropertyMethods(out, component, template.getIgnoreMethods());
        }
        
        if (!suppressListenerMethods)
          generator.writeListenerMethods(out, component);

        generator.writeStateManagementMethods(out, component);

        generator.writeGetFamily(out);

        // protected constructors and methods
        // TODO: reverse this order, to make protected constructor go first
        //       for now we want consistency with previous code generation
        generator.writeOther(out, component);

        generator.writeClassEnd(out);

        out.close();

        // delay write in case of error
        // timestamp should not be updated when an error occurs
        // delete target file first, because it is readonly
        targetFile.getParentFile().mkdirs();
        targetFile.delete();
        FileWriter fw = new FileWriter(targetFile);
        StringBuffer buf = sw.getBuffer();
        fw.write(buf.toString());
        fw.close();
        targetFile.setReadOnly();
      }
    }
    catch (IOException e)
    {
      getLog().error("Error generating " + fullClassName, e);
    }
  }

  private class IfModifiedFilter extends ComponentFilter
  {
    protected boolean accept(
      ComponentBean component)
    {
      String componentClass = component.getComponentClass();
      String sourcePath = Util.convertClassToSourcePath(componentClass, ".java");
      String templatePath = Util.convertClassToSourcePath(componentClass, "Template.java");
      File targetFile = new File(generatedSourceDirectory, sourcePath);
      File templateFile = new File(templateSourceDirectory, templatePath);

      // accept if templateFile is newer or component has been modified
      return (templateFile.lastModified() > targetFile.lastModified() ||
              component.isModifiedSince(targetFile.lastModified()));
    }
  }

  private boolean _is12()
  {
    return "1.2".equals(jsfVersion) || "12".equals(jsfVersion);
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
   * @parameter expression="src/main/java-templates"
   * @required
   */
  private File templateSourceDirectory;

  /**
   * @parameter expression="${project.build.directory}/maven-faces-plugin/main/java"
   * @required
   */
  private File generatedSourceDirectory;

  /**
   * @parameter
   * @required
   */
  private String packageContains;

  /**
   * @parameter
   * @required
   */
    private String typePrefix;

  /**
   * @parameter
   */
  private boolean force;

  /**
   * @parameter
   */
  private boolean suppressListenerMethods;

  /**
   * @parameter
   */
  private String jsfVersion;
}
