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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * <p>
 * Note that the generated file contains all the metadata needed by this
 * project, including a copy of all the metadata from other projects that
 * this one depends on. All other goals of this plugin can execute with
 * just the generated metadata as input, without needing to load other
 * projects. Each entry in the metadata is labelled with a "modelId"
 * property that indicates where it originally came from.
 * 
 * @requiresDependencyResolution compile
 * @goal build-metadata
 * @phase generate-sources
 */
public class BuildMetaDataMojo extends AbstractMojo
{
    final Log log = LogFactory.getLog(BuildMetaDataMojo.class.getName());

    /**
     * Injected Maven project object.
     * 
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * Injected build directory for all generated stuff.
     * 
     * @parameter expression="${project.build.directory}/maven-faces-plugin/main/resources"
     */
    private File targetDirectory;

    /**
     * Injected name of file to generate, relative to targetDirectory.
     * 
     * @parameter
     */
    private String outputFile = "META-INF/myfaces-metadata.xml";

    /**
     * 
     * @parameter expression="${project.artifactId}"
     */
    private String modelId;
    
    /**
     * Replace the package prefix
     * 
     * @parameter
     */
    private String replacePackagePrefixTagFrom;

    /**
     * Replace the package prefix
     * 
     * @parameter
     */
    private String replacePackagePrefixTagTo;
    
    /**
     * 
     * @parameter
     */
    private List orderModelIds;
    
    /**
     * @parameter
     */
    private List dependencyModelIds;
    
    /**
     * Alternate file to read model definition and merge it to the
     * current model, usually used when it is necessary to avoid dependencies.
     * 
     * One example is when it is necessary a release of the library (tomahawk for 
     * example), but the previous version of the scanned dependency library
     * (myfaces-api) does not have META-INF/myfaces-metadata.xml in his jar,
     * so when the metadata is generated the result is a lot of errors due to 
     * the model is incomplete.
     * 
     * Other use is when it is necessary to add some custom model definitions to
     * the model to be built, but this should be avoided. Instead, this goal should
     * be used on the dependency library, release the dependency library and
     * then the library that consume the metadata.  
     * 
     * @parameter
     */
    private File inputFile;
    
    /**
     * Execute the Mojo.
     */
    public void execute() throws MojoExecutionException
    {
        try {
            addResourceRoot(project, targetDirectory.getCanonicalPath());
        }catch(IOException e){
            throw new MojoExecutionException("Error during generation", e);
        }
        
        Model model = new Model();

        List models = null;
        if (dependencyModelIds != null)
        {
            models = IOUtils.getModelsFromArtifacts(project,dependencyModelIds);
        }
        else
        {
            models = IOUtils.getModelsFromArtifacts(project); 
        }
        
        if (inputFile != null)
        {
            Model fileModel = IOUtils.loadModel(inputFile);
            //The input model takes precedence
            model.merge(fileModel);
        }
        
        models = sortModels(models);
        
        for (Iterator it = models.iterator(); it.hasNext();){
            Model artifactModel = (Model) it.next();
            model.merge(artifactModel);
        }

        buildModel(model, project);
        resolveReplacePackage(model);
        
        IOUtils.saveModel(model, new File(targetDirectory, outputFile));
        
        validateComponents(model);
    }
    
    /**
     * This function order the models as suggested by the modelIdOrder.
     * Tomahawk sandbox depends from myfaces-api and tomahawk core, so
     * the myfaces-metadata.xml of tomahawk core must be merged first
     * and then myfaces-api.
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
        
        for (Iterator it = models.iterator(); it.hasNext();){
            Model artifactModel = (Model) it.next();
            modelsMap.put(artifactModel.getModelId(), artifactModel);
        }
        
        for (Iterator it = orderModelIds.iterator(); it.hasNext();){
            String modelId = (String) it.next();
            
            Model artifactModel = null;
            if ( (artifactModel = (Model) modelsMap.get(modelId)) != null)
            {
                modelsMap.remove(modelId);
                modelsSorted.add(artifactModel);
            }
        }
        
        modelsSorted.addAll(modelsMap.values());
        
        return modelsSorted;
    }
    
    private void resolveReplacePackage(Model model)
    {
        if (replacePackagePrefixTagFrom == null ||
                replacePackagePrefixTagTo == null)
            return;
        
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
                log.info("Tag class changed to:"+comp.getTagClass());
            }
        }
    }
    

    /**
     * Execute ModelBuilder classes to create the Model data-structure.
     */
    private Model buildModel(Model model, MavenProject project)
            throws MojoExecutionException
    {
        try
        {
            QdoxModelBuilder builder = new QdoxModelBuilder();
            model.setModelId(modelId);
            builder.buildModel(model, project);            
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
	        		curr = null;
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
