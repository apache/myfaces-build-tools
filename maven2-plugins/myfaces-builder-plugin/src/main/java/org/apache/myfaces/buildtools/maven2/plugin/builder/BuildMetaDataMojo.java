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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxModelBuilder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.utils.BuildException;
import org.codehaus.plexus.util.StringUtils;

/**
 * Maven goal which runs one or more ModelBuilder objects to gather metadata
 * about JSF artifacts into a Model object, then save that model object as an
 * xml file for use by other goals of this plugin.
 * <p>
 * By default, the generated file is named "META-INF/myfaces-metadata.xml".
 * This file will be included in the final artifact for this project. Having
 * that metadata file embedded in the generated jarfile is useful for two
 * purposes:
 * <ul>
 * <li>It is needed if other projects then use the myfaces-builder-plugin to
 * create subclasses of the jsf classes in this project.</li>
 * <li>It is good documentation (more precise than the tld and faces-config.xml
 * files).</li>
 * </ul>
 * </p>
 * <p>
 * Note that the generated file contains all the metadata needed by this
 * project, including a copy of all the metadata from other projects that
 * this one depends on. All other goals of this plugin can execute with
 * just the generated metadata as input, without needing to load other
 * projects. Each entry in the metadata is labelled with a "modelId"
 * property that indicates where it originally came from.
 * </p>
 * 
 * @requiresDependencyResolution compile
 * @goal build-metadata
 * @phase generate-sources
 */
public class BuildMetaDataMojo extends AbstractMojo
{
    /**
     * Injected Maven project object.
     * 
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * Build directory for all generated stuff.
     * <p>
     * This mojo registers the specified directory with Maven as a resource dir. The
     * maven-resources-plugin will then read the files from this dir and copy them
     * into the "central" target directory from which the jarfile is built. 
     * </p>
     * 
     * @parameter expression="${project.build.directory}/generated-resources/myfaces-builder-plugin"
     */
    private File targetDirectory;

    /**
     * Name of the metadata file to generate, relative to targetDirectory.
     * 
     * @parameter
     */
    private String outputFile = "META-INF/myfaces-metadata.xml";

    /**
     * The modelId to associate with all model items discovered in the
     * source directories of this project.
     * <p>
     * This value <i>must</i> be unique for each project. If not specified,
     * then it defaults to the artifactId of the current maven project.
     * </p>
     * <p>
     * In later phases, goals are passed the complete metadata model which
     * mixes items discovered here with items imported from metadata in 
     * other projects. The modelId is used to figure out which of the
     * items should be processed (ie which ones are associated with this
     * project) and which should be ignored.
     * </p>
     *
     * @parameter expression="${project.artifactId}"
     */
    private String modelId;
    
    /**
     * Replace the package prefix.
     * <p>
     * This allows a project that inherits metadata from some other project to force
     * copies of the Tag classes to be created in a namespace of its own choosing.
     * </p>
     * <p>
     * This is used in particular to create copies of the Tag classes in myfaces-impl
     * within other projects so that they can be used as base classes for other tags
     * within that project. The original tag present in the myfaces-impl.jar cannot
     * be used as a base because that would prevent the derived project from running
     * with other JSF implementations.
     * </p>
     * <p>
     * The child project first defines this (and replacePackagePrefixTagTo); as the
     * inherited metadata is merged the tagClass attribute is modified. Then during
     * the tag class generation goal, the modelId of the inherited project is included
     * in the list of modelIds to process. That causes the tag classes to be generated
     * again - but this time in a different package.
     * </p>
     * 
     * @parameter
     */
    private String replacePackagePrefixTagFrom;

    /**
     * Replace the package prefix
     * <p>
     * See replacePackagePrefixTagTo.
     * </p>
     * 
     * @parameter
     */
    private String replacePackagePrefixTagTo;
    
    /**
     * Specify the order in which models are to be merged (not usually needed).
     * <p>
     * When two different models define exactly the same item (ie the "class" attribute
     * for two items is the same) then the one from the model that is merged first is
     * used, and the later one is ignored.
     * </p>
     * <p>
     * This property allows the order of merging to be controlled; models are merged in
     * the order specified in this list. Any models whose ids are not in this list are
     * then merged in an undefined order at the end.
     * </p>
     * <p>
     * Setting this property is not normally necessary; typically models inherited from
     * dependencies define different model items (ie have no overlap). However consider
     * the situation where project A defines a model, project B extends A, then project C
     * extends B. In this case, both A and B are in the dependencies, but the metadata in
     * project B contains a complete copy of all the data from A. However B's metadata is
     * from <i>the version of A that it was compiled against</i> which might not be the
     * version of A that is in the dependencies. For safety in this case, it is better to
     * ensure that project B's metadata is loaded first; this can possibly hide any new
     * features (or bugfixes) from the new release of A, but also ensures that classes
     * in C which extend classes in B do not declare features that B does not support.
     * </p>
     * <p>
     * This property is only needed in rare situations; normally it can be omitted.
     * </p>
     * 
     * @parameter
     */
    private List orderModelIds;
    
    /**
     * An optional list of models to import from dependency jars.
     * <p>
     * The default behaviour is to scan all jar dependencies for metadata files, and
     * merge all the data into the model for this project. If this property is set,
     * then metadata found in dependencies is only merged if the modelId matches one
     * in this list.
     * </p>
     * <p>
     * Note that by default, a project's modelId is the same as the artifactId.
     * </p>
     * 
     * @parameter
     */
    private List dependencyModelIds;
    
    /**
     * The name of a metadata file to merge into the model for the current project.
     * <p>
     * This file is always loaded first, before any metadata from dependencies is
     * loaded, and before scanning of the source directories for the current
     * project is done.
     * </p>
     * <p>
     * The specified filename is relative to the current working directory.
     * </p>
     * <p>
     * Normally, this option is not used and any models that this project extends
     * are simply automatically detected via scanning of the maven dependencies and
     * loaded from the dependency jarfile. However there are a couple of situations
     * where it is useful to specify an explicit metadata file to load instead.  
     * </p>
     * <p> 
     * One example is when a project extends components in a project which was not
     * built with the myfaces-builder-plugin (or where myfaces-builder-plugin
     * support is only in a not-yet-released version). In this case, a metadata file
     * can be created by hand (or generated from the unreleased trunk version) and
     * explicitly loaded via this property.
     * </p>
     * <p>
     * A second example is when it is necessary to add some custom model definitions to
     * the model to be built, eg to override buggy or missing metadata in a project
     * that this project extends. Of course this is hopefully not needed; it would be
     * better to get a bugfix release of the parent project out instead!
     * </p>
     * 
     * @parameter
     */
    private File inputFile;
    
    /**
     * A comma separated list of file patterns to include when building the
     * model.  i.e.  **\/*.java
     * 
     * @parameter
     */
    private String includes;
    
    /**
     *  A comma separated list of file patterns to exclude when building the
     *  model.  i.e.  **\/*.java
     * @parameter
     */
    private String excludes;
    
    /**
     * This param is used to search in this folder if some file to
     * be generated exists and avoid generation and duplicate exception.
     * 
     * @since 1.0.4
     * @parameter
     */    
    private List sourceDirectories;

    /**
     * The directory where all generated files are created. This directory is added as a
     * compile source root automatically like src/main/java is. 
     * 
     * @since 1.0.8
     * @parameter expression="${project.build.directory}/generated-sources/myfaces-builder-plugin"
     */
    private File generatedSourceDirectory;
    
    /**
     * Create a metadata file containing information imported from other projects
     * plus data extracted from annotated classes in this project.
     */
    public void execute() throws MojoExecutionException
    {
        try
        {
            // Tell Maven to add this directory to its "resources" path. 
            // The maven-resources-plugin will then copy all the files
            // from this directory to its own target directory, which is
            // where the final jar artifact is built from.
            addResourceRoot(project, targetDirectory.getCanonicalPath());
        }
        catch(IOException e)
        {
            throw new MojoExecutionException("Error during generation", e);
        }
        
        List models = IOUtils.getModelsFromArtifacts(project); 
        models = sortModels(models);

        Model model = new Model();

        if (inputFile != null)
        {
            // An explicitly-specified input model takes precedence
            Model fileModel = IOUtils.loadModel(inputFile);
            model.merge(fileModel);
        }
        
        
        for (Iterator it = models.iterator(); it.hasNext();)
        {
            Model artifactModel = (Model) it.next();
            
            if ((dependencyModelIds == null) || dependencyModelIds.contains(artifactModel.getModelId()))
            {
                model.merge(artifactModel);
            }
        }

        ModelParams parameters = new ModelParams();
        
        List sourceDirs = new ArrayList();
        if (sourceDirectories == null)
        {
            sourceDirs.addAll(project.getCompileSourceRoots());
        }
        else
        {
            sourceDirs.addAll(sourceDirectories);
        }
        
        if (generatedSourceDirectory != null)
        {
            for (Iterator it = sourceDirs.iterator(); it.hasNext();)
            {
                File f = new File((String) it.next());
                if (generatedSourceDirectory.equals(f))
                {
                    it.remove();
                }
            }
        }
        
        parameters.setSourceDirs(sourceDirs);
        
        buildModel(model, project, parameters);
        
        resolveReplacePackage(model);
        
        IOUtils.saveModel(model, new File(targetDirectory, outputFile));
        
        validateComponents(model);
    }
    
    /**
     * Order the models as specified by the modelIdOrder property.
     * <p>
     * Tomahawk sandbox depends from myfaces-api and tomahawk core, so
     * the myfaces-metadata.xml of tomahawk core must be merged first
     * and then myfaces-api.
     * <p>
     * In some cases, the same metadata can be imported multiple times.
     * For example, Project A has metadata. Project B extends A, and
     * Project C extends B. 
     * 
     * @param models
     * @return
     */
    private List sortModels(List models)
    {
        if (orderModelIds == null)
        {
            //No changes
            return models;
        }
        
        Map modelsMap = new HashMap();
        List modelsSorted = new ArrayList();

        // First, put all models into a map keyed by modelId.
        for (Iterator it = models.iterator(); it.hasNext();)
        {
            Model artifactModel = (Model) it.next();
            modelsMap.put(artifactModel.getModelId(), artifactModel);
        }

        // now pull them out of the map in the order specified by orderModelIds.
        for (Iterator it = orderModelIds.iterator(); it.hasNext();)
        {
            String modelId = (String) it.next();
            
            Model artifactModel = (Model) modelsMap.get(modelId);
            if (artifactModel != null)
            {
                modelsMap.remove(modelId);
                modelsSorted.add(artifactModel);
            }
        }

        // and any of the ones that remain in the map (ie had no order specified)
        // now get added to the end of the list.
        modelsSorted.addAll(modelsMap.values());
        
        return modelsSorted;
    }
    
    private void resolveReplacePackage(Model model)
    {
        if (replacePackagePrefixTagFrom == null ||
                replacePackagePrefixTagTo == null)
        {
            return;
        }
        
        List components = model.getComponents();
        for (Iterator i = components.iterator(); i.hasNext();)
        {
            ComponentMeta comp = (ComponentMeta) i.next();
            
            if (comp.getTagClass() == null)
            {
                continue;
            }
            if (comp.getTagClass().startsWith(replacePackagePrefixTagFrom))
            {
                comp.setTagClass(StringUtils.replaceOnce(
                        comp.getTagClass(), replacePackagePrefixTagFrom, replacePackagePrefixTagTo));
            }
        }
    }
    

    /**
     * Execute ModelBuilder classes to create the Model data-structure.
     */
    private Model buildModel(Model model, MavenProject project, ModelParams parameters)
            throws MojoExecutionException
    {
        try
        {
            QdoxModelBuilder builder = new QdoxModelBuilder();
            model.setModelId(modelId);
            if (StringUtils.isNotEmpty(includes)) 
            {
                parameters.setIncludes(includes);
            }
            if (StringUtils.isNotEmpty(excludes))
            {
                parameters.setExcludes(excludes);
            }
            builder.buildModel(model, parameters);            
            return model;
        }
        catch (BuildException e)
        {
            throw new MojoExecutionException("Unable to build metadata", e);
        }
    }
    
    protected void addResourceRoot(MavenProject project, String resourceRoot)
    {
        List resources = project.getBuild().getResources();
        Resource resource = new Resource();
        resource.setDirectory(resourceRoot);
        resources.add(resource);
    }
    
    /**
     * Check that each component is valid (has all mandatory properties etc).
     * <p>
     * Most sanity checks are best done after the myfaces-metadata.xml file
     * is created, so that if an error occurs the file is available for the
     * user to inspect. In particular, problems due to missing properties
     * which are permitted to be inherited can be tricky to track down if
     * the metadata file is not available.
     * <p>
     * TODO: make this gather up all the errors, then report them at once
     * rather than stopping on the first error found.
     */
    private void validateComponents(Model model) throws MojoExecutionException
    {
        for(Iterator i = model.components(); i.hasNext(); )
        {
            ComponentMeta component = (ComponentMeta) i.next();
            validateComponent(model, component);
        }
    }
    
    private void validateComponent(Model model, ComponentMeta component)
    throws MojoExecutionException
    {
        if (component.getName() != null)
        {
            if (component.getDescription() == null)
            {
                throw new MojoExecutionException(
                        "Missing mandatory property on component " + component.getClassName()
                        + " [sourceClass=" + component.getSourceClassName() + "]: description");
            }

            if (component.getType() == null)
            {
                throw new MojoExecutionException(
                        "Missing mandatory property on component " + component.getClassName()
                        + " [sourceClass=" + component.getSourceClassName() + "]: type");
            }
            
            // this is a concrete component, so it must have a family property
            validateComponentFamily(model, component);
        }
    }
    
    private void validateComponentFamily(Model model, ComponentMeta component)
    throws MojoExecutionException
    {
        // TODO: clean this code up, it is pretty ugly
        boolean familyDefined = false;
        ComponentMeta curr = component;
        while ((curr != null) && !familyDefined)
        {
            if (curr.getFamily() != null)
            {
                familyDefined = true;
            }
            else
            {
                String parentName = curr.getParentClassName();
                if (parentName == null)
                {
                    curr = null;
                }
                else
                {
                    curr = model.findComponentByClassName(parentName);
                    if (curr == null)
                    {
                        throw new MojoExecutionException(
                                "Parent class not found for component " + component.getClassName()
                                + " [sourceClass=" + component.getSourceClassName() + "]");
                    }
                }
            }
        }
        if (!familyDefined)
        {
            throw new MojoExecutionException(
                "Missing mandatory property on component " + component.getClassName()
                + " [sourceClass=" + component.getSourceClassName() + "]: family");
        }
    }
}
