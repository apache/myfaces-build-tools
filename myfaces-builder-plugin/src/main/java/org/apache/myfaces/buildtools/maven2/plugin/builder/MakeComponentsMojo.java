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
package org.apache.myfaces.buildtools.maven2.plugin.builder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxModelBuilder;

/**
 * @version $Id$
 * @requiresDependencyResolution compile
 * @goal make-components
 * @phase generate-sources
 */
public class MakeComponentsMojo extends AbstractMojo
{
    final Logger log = Logger.getLogger(MakeComponentsMojo.class.getName());

    /**
     * Execute the Mojo.
     */
    public void execute() throws MojoExecutionException
    {
        // This command makes Maven compile the generated source:
        // getProject().addCompileSourceRoot( absoluteGeneratedPath.getPath() );
        try
        {

            Model artifacts = scanSource(project);
            dumpModel(artifacts);
            generateComponents(artifacts);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Error generating components", e);
        }
    }

    /**
     * Scan the source tree for annotations. Sets
     */
    private Model scanSource(MavenProject project)
            throws MojoExecutionException
    {
        Model model = new Model();
        QdoxModelBuilder builder = new QdoxModelBuilder();
        builder.buildModel(model, project);
        return model;
    }

    private void dumpModel(Model artifacts)
    {
        System.out.println("--dumping artifacts--");
        Iterator components = artifacts.components();
        while (components.hasNext())
        {
            dumpComponent((ComponentModel) components.next());
        }
        System.out.println("--dumped artifacts--");
    }

    private void dumpComponent(ComponentModel c)
    {
        PrintStream out = System.out;
        out.println("==Component");
        out.println("class:" + c.getClassName());
        out.println("type:" + c.getType());
        Iterator p = c.properties();
        while (p.hasNext())
        {
            PropertyModel prop = (PropertyModel) p.next();
            out.println("prop:" + prop.getName());
            out.println("  class:" + prop.getClassName());
            out.println("  isLiteral:" + prop.isLiteralOnly());
            out.println("  desc:" + prop.getDescription());
        }
    }

    /**
     * Generates parsed components.
     */
    private void generateComponents(Model artifacts) throws IOException,
            MojoExecutionException
    {
        throw new MojoExecutionException("stopping..");

        /*
         * // Make sure generated source directory // is added to compilation
         * source path project.addCompileSourceRoot(generatedSourceDirectory
         * .getCanonicalPath());
         * 
         * Iterator components = artifacts.components(); if
         * (!components.hasNext()) { log.info("Nothing to generate - no
         * components found"); return; }
         * 
         * if (suppressListenerMethods) log.severe("Event listener methods will
         * not be generated");
         */
        /*
         * Iterator components = facesConfig.components(); components = new
         * FilteredIterator(components, new SkipFilter()); components = new
         * FilteredIterator(components, new ComponentTypeFilter( typePrefix)); //
         * incremental unless forced if (!force) components = new
         * FilteredIterator(components, new IfModifiedFilter());
         * 
         * if (!components.hasNext()) { getLog() .info("Nothing to generate -
         * all components are up to date"); } else { int count = 0; while
         * (components.hasNext()) { ComponentBean component = (ComponentBean)
         * components.next(); if (!component.isComponentClassExcluded()) {
         * _generateComponent(component); count++; } } getLog().info("Generated " +
         * count + " component(s)"); }
         */
    }

    /**
     * Generates a parsed component.
     * 
     * @param component
     *            the parsed component metadata
     */
    private void _generateComponent(ComponentModel component)
            throws MojoExecutionException
    {
        /*
         * ComponentGenerator generator;
         * 
         * String fullClassName = component.getComponentClass();
         * 
         * if (component.isTrinidadComponent()) { generator = new
         * TrinidadComponentGenerator(getLog(), _is12()); } else { generator =
         * new MyFacesComponentGenerator(getLog(), _is12()); }
         * 
         * try { getLog().debug( "Generating " + fullClassName + ", with
         * generator: " + generator.getClass().getName());
         * 
         * String sourcePath = Util.convertClassToSourcePath(fullClassName,
         * ".java"); File targetFile = new File(generatedSourceDirectory,
         * sourcePath);
         * 
         * StringWriter sw = new StringWriter(); PrettyWriter out = new
         * PrettyWriter(sw);
         * 
         * String className = Util.getClassFromFullClass(fullClassName); String
         * componentFamily = component.findComponentFamily();
         * 
         * if (componentFamily == null) { getLog() .warn( "Missing
         * <component-family> for \"" + fullClassName + "\", generation of this
         * Component is skipped"); } else { String packageName = Util
         * .getPackageFromFullClass(fullClassName); String fullSuperclassName =
         * component.findComponentSuperclass(); String superclassName = Util
         * .getClassFromFullClass(fullSuperclassName); // make class name fully
         * qualified in case of collision if (superclassName.equals(className))
         * superclassName = fullSuperclassName; // TODO: remove this bogosity if
         * (superclassName.equals("UIXMenuHierarchy") ||
         * superclassName.equals("UIXTable") ||
         * superclassName.equals("UIXHierarchy") ||
         * superclassName.equals("UIXMenuTree") || className.equals("CoreTree")) {
         * superclassName = fullSuperclassName; }
         * 
         * String componentType = component.getComponentType(); // Use template
         * file if it exists String templatePath =
         * Util.convertClassToSourcePath( fullClassName, "Template.java"); File
         * templateFile = new File(templateSourceDirectory, templatePath);
         * 
         * SourceTemplate template = null; if (templateFile.exists()) {
         * getLog().debug("Using template " + templatePath); template = new
         * SourceTemplate(templateFile); template.substitute(className +
         * "Template", className); template.readPreface(); } // header/copyright
         * writePreamble(out); // package out.println("package " + packageName +
         * ";"); out.println(); // imports generator.writeImports(out, template,
         * packageName, fullSuperclassName, superclassName, component); // class
         * generator.writeClassBegin(out, className, superclassName, component,
         * template); // static final constants
         * generator.writePropertyValueConstants(out, component); generator
         * .writePropertyConstants(out, superclassName, component);
         * generator.writeFacetConstants(out, component);
         * generator.writeGenericConstants(out, componentFamily, componentType); //
         * public constructors and methods generator.writeConstructor(out,
         * component, Modifier.PUBLIC); // insert template code if (template !=
         * null) { template.writeContent(out); template.close(); }
         * 
         * generator.writeFacetMethods(out, component);
         * 
         * if (template == null) { generator.writePropertyMethods(out,
         * component); } else { generator.writePropertyMethods(out, component,
         * template .getIgnoreMethods()); }
         * 
         * if (!suppressListenerMethods) generator.writeListenerMethods(out,
         * component);
         * 
         * generator.writeStateManagementMethods(out, component);
         * 
         * generator.writeGetFamily(out); // protected constructors and methods //
         * TODO: reverse this order, to make protected constructor go // first //
         * for now we want consistency with previous code generation
         * generator.writeOther(out, component);
         * 
         * generator.writeClassEnd(out);
         * 
         * out.close(); // delay write in case of error // timestamp should not
         * be updated when an error occurs // delete target file first, because
         * it is readonly targetFile.getParentFile().mkdirs();
         * targetFile.delete(); FileWriter fw = new FileWriter(targetFile);
         * StringBuffer buf = sw.getBuffer(); fw.write(buf.toString());
         * fw.close(); targetFile.setReadOnly(); } } catch (IOException e) {
         * getLog().error("Error generating " + fullClassName, e); }
         */
    }

    /*
     * private class IfModifiedFilter extends ComponentFilter { protected
     * boolean accept(Component component) { String componentClass =
     * component.getComponentClass(); String sourcePath =
     * Util.convertClassToSourcePath(componentClass, ".java"); String
     * templatePath = Util.convertClassToSourcePath(componentClass,
     * "Template.java"); File targetFile = new File(generatedSourceDirectory,
     * sourcePath); File templateFile = new File(templateSourceDirectory,
     * templatePath); // accept if templateFile is newer or component has been
     * modified return (templateFile.lastModified() > targetFile.lastModified() ||
     * component .isModifiedSince(targetFile.lastModified())); return true; } }
     */

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
