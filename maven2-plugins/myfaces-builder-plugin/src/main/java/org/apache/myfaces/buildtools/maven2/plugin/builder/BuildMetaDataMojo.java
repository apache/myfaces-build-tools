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
     * Execute the Mojo.
     */
    public void execute() throws MojoExecutionException
    {
        try {
            addResourceRoot(project, targetDirectory.getCanonicalPath());
        }catch(IOException e){
            throw new MojoExecutionException("Error during generation", e);
        }
        
        Model model = buildModel(project);
        List models = null;
        if (dependencyModelIds != null)
        {
            models = IOUtils.getModelsFromArtifacts(project,dependencyModelIds);
        }
        else
        {
            models = IOUtils.getModelsFromArtifacts(project); 
        }
        
        models = sortModels(models);
        
        for (Iterator it = models.iterator(); it.hasNext();){
            Model artifactModel = (Model) it.next();
            model.merge(artifactModel);
        }

        resolveReplacePackage(model);
        
        IOUtils.saveModel(model, new File(targetDirectory, outputFile));
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
    private Model buildModel(MavenProject project)
            throws MojoExecutionException
    {
        try
        {
            Model model = new Model();
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
}
