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

package org.apache.myfaces.buildtools.maven2.plugin.builder.unpack;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.dependency.utils.filters.ArtifactItemFilter;
import org.apache.maven.plugin.dependency.utils.filters.MarkerFileFilter;
import org.apache.maven.plugin.dependency.utils.markers.MarkerHandler;
import org.apache.maven.plugin.dependency.utils.markers.UnpackFileMarkerHandler;
import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.BehaviorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.codehaus.plexus.util.StringUtils;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;

/**
 * Goal that retrieves a list of artifacts from the repository and unpacks them
 * in a defined location.
 * <p>
 * This mojo reutilize org.apache.maven.plugin.dependency.fromConfiguration.UnpackMojo
 * from maven-dependency-plugin.
 * </p>
 * <p>
 * The idea of this plugin, instead just unpack a list of artifacts is add some new 
 * features necessary to make easier maintain 1.1 and 1.2 code on myfaces projects like
 * tomahawk.
 * </p>
 * <p>
 * This plugin works as unpack goal of maven-dependency-plugin with 2 additional 
 * enhancements:
 * </p>
 * <ol>
 *  <li>If some file exists on the base directories, it is added as excluded, so it is
 * not copied to the output directory. This makes easier to manage the code and maintain it.</li>
 *  <li>If a file is generated from the model (reading the myfaces-metadata.xml) it is not
 * copied, since this should be generated again.</li>
 * </ol>
 *  
 * @since 1.0
 * @goal unpack
 * @phase process-sources
 * @author Leonardo Uribe
 * @version $Id$
 */
public class UnpackMojo extends AbstractFromConfigurationMojo
{
    /**
     * Directory where he should check if a file exists or not. If 
     * exists, he does not copy to , if not he copy it to the output
     * directory.
     * 
     * This is possible because all files on this directory are added
     * as excluded param.
     * 
     * @parameter expression="src/main/java"
     */
    private File baseDirectory1;
    
    /**
     * Directory where he should check if a file exists or not. If 
     * exists, he does not copy to , if not he copy it to the output
     * directory.
     * 
     * This is possible because all files on this directory are added
     * as excluded param.
     * 
     * @parameter 
     */    
    private File baseDirectory2;
        
    /**
     * If the plugin should scan the model
     * 
     * @optional
     * @parameter expression="${scanModel}"
     *            default-value="false"
     */    
    private boolean scanModel;
    
    /**
     * Directory to store flag files after unpack
     * 
     * @parameter expression="${project.build.directory}/dependency-maven-plugin-markers"
     */
    private File markersDirectory;

    /**
     * A comma separated list of file patterns to include when unpacking the artifact. i.e. **\/*.xml,**\/*.properties
     * NOTE: Excludes patterns override the includes. (component code = return isIncluded( name ) AND !isExcluded( name
     * );)
     * 
     * @since 2.0-alpha-5
     * @parameter expression="${mdep.unpack.includes}"
     */
    private String includes;

    /**
     * A comma separated list of file patterns to exclude when unpacking the artifact. i.e. **\/*.xml,**\/*.properties
     * NOTE: Excludes patterns override the includes. (component code = return isIncluded( name ) AND !isExcluded( name
     * );)
     * 
     * @since 2.0-alpha-5
     * @parameter expression="${mdep.unpack.excludes}"
     */
    private String excludes;

    /**
     * Main entry into mojo. This method gets the ArtifactItems and iterates through each one passing it to
     * unpackArtifact.
     * 
     * @throws MojoExecutionException with a message if an error occurs.
     * @see ArtifactItem
     * @see #getArtifactItems
     * @see #unpackArtifact(ArtifactItem)
     */
    public void execute()
        throws MojoExecutionException
    {
        if ( isSkip() )
        {
            return;
        }

        String existingFiles = scanAndAddExistingFilesAsExcluded(
                baseDirectory1, baseDirectory2);
        
        String excludedFiles = null;
        
        List<ArtifactItem> processedItems = getProcessedArtifactItems( false );
        for ( ArtifactItem artifactItem : processedItems )
        {
            if ( artifactItem.isNeedsProcessing() )
            {
                if (scanModel)
                {
                    String generatedFiles = this.scanModelAndAddGeneratedFiles(artifactItem);                    
                    excludedFiles = existingFiles + ','+ generatedFiles;
                }
                else
                {
                    excludedFiles = existingFiles;
                }
                
                //Exclude existing files on baseDirectory1 and baseDirectory2
                if (artifactItem.getExcludes() != null)
                {
                    artifactItem.setExcludes(artifactItem.getExcludes()+','+excludedFiles);
                }
                else
                {
                    artifactItem.setExcludes(excludedFiles);
                }
                //Unpack
                unpackArtifact( artifactItem );
            }
            else
            {
                this.getLog().info( artifactItem.getArtifact().getFile().getName() + " already unpacked." );
            }
        }
    }

    /**
     * This method gets the Artifact object and calls DependencyUtil.unpackFile.
     * 
     * @param artifactItem containing the information about the Artifact to unpack.
     * @throws MojoExecutionException with a message if an error occurs.
     * @see #getArtifact
     * @see DependencyUtil#unpackFile(Artifact, File, File, ArchiverManager, Log)
     */
    private void unpackArtifact( ArtifactItem artifactItem )
        throws MojoExecutionException
    {
        MarkerHandler handler = new UnpackFileMarkerHandler( artifactItem, this.markersDirectory );

        unpack( artifactItem.getArtifact().getFile(), artifactItem.getOutputDirectory(), artifactItem.getIncludes(),
                artifactItem.getExcludes() );
        handler.setMarker();
    }

    ArtifactItemFilter getMarkedArtifactFilter( ArtifactItem item )
    {
        MarkerHandler handler = new UnpackFileMarkerHandler( item, this.markersDirectory );

        return new MarkerFileFilter( this.isOverWriteReleases(), this.isOverWriteSnapshots(),
                                     this.isOverWriteIfNewer(), handler );
    }

    protected List<ArtifactItem> getProcessedArtifactItems( boolean removeVersion )
        throws MojoExecutionException
    {
        List<ArtifactItem> items = super.getProcessedArtifactItems( removeVersion );
        for ( ArtifactItem artifactItem : items )
        {
            if ( StringUtils.isEmpty( artifactItem.getIncludes() ) )
            {
                artifactItem.setIncludes( getIncludes() );
            }
            if ( StringUtils.isEmpty( artifactItem.getExcludes() ) )
            {
                artifactItem.setExcludes( getExcludes() );
            }
        }
        return items;
    }
    
    protected String scanModelAndAddGeneratedFiles(ArtifactItem artifactItem)
        throws MojoExecutionException
    {
        
        ArrayList exclusions = new ArrayList();
        
        Model model = IOUtils.getModelFromArtifact(artifactItem.getArtifact());
        
        if (model != null)
        {
            for (Iterator it = model.components(); it.hasNext();)
            {
                ComponentMeta component = (ComponentMeta) it.next();

                if (component.getModelId().equals(model.getModelId()))
                {
                    if (component.isGeneratedComponentClass().booleanValue())
                    {
                        getLog().info("Adding Generated: "+ component.getClassName());
                        exclusions.add(StringUtils.replace(
                                component.getClassName(), ".", "/")
                                + ".java");
                    }
                    if (component.isGeneratedTagClass().booleanValue())
                    {
                        getLog().info("Adding Generated: "+ component.getTagClass());
                        exclusions.add(StringUtils.replace(component.getTagClass(),
                                ".", "/")
                                + ".java");
                    }                
                }
            }
            for (Iterator it = model.converters(); it.hasNext();)
            {
                ConverterMeta converter = (ConverterMeta) it.next();

                if (converter.getModelId().equals(model.getModelId()))
                {
                    if (converter.isGeneratedComponentClass().booleanValue())
                    {
                        getLog().info("Adding Generated: "+ converter.getClassName());
                        exclusions.add(StringUtils.replace(
                                converter.getClassName(), ".", "/")
                                + ".java");
                    }
                    if (converter.isGeneratedTagClass().booleanValue())
                    {
                        getLog().info("Adding Generated: "+ converter.getTagClass());
                        exclusions.add(StringUtils.replace(converter.getTagClass(),
                                ".", "/")
                                + ".java");
                    }                
                }
            }
            for (Iterator it = model.validators(); it.hasNext();)
            {
                ValidatorMeta validator = (ValidatorMeta) it.next();

                if (validator.getModelId().equals(model.getModelId()))
                {
                    if (validator.isGeneratedComponentClass().booleanValue())
                    {
                        getLog().info("Adding Generated: "+ validator.getClassName());
                        exclusions.add(StringUtils.replace(
                                validator.getClassName(), ".", "/")
                                + ".java");
                    }
                    if (validator.isGeneratedTagClass().booleanValue())
                    {
                        getLog().info("Adding Generated: "+ validator.getTagClass());
                        exclusions.add(StringUtils.replace(validator.getTagClass(),
                                ".", "/")
                                + ".java");
                    }                
                }
            }
            for (Iterator it = model.behaviors(); it.hasNext();)
            {
                BehaviorMeta behavior = (BehaviorMeta) it.next();

                if (behavior.getModelId().equals(model.getModelId()))
                {
                    if (behavior.isGeneratedComponentClass().booleanValue())
                    {
                        getLog().info("Adding Generated: "+ behavior.getClassName());
                        exclusions.add(StringUtils.replace(
                                behavior.getClassName(), ".", "/")
                                + ".java");
                    }
                }
            }
            
        }
        else
        {
            getLog().info("No myfaces-metadata.xml found on artifact.");
        }

        StringBuffer existingFiles = new StringBuffer();
        for (int i = 0; i < exclusions.size(); i++)
        {
            existingFiles.append(exclusions.get(i));
            if (i != exclusions.size() - 1)
            {
                existingFiles.append(',');
            }
        }
        
        return existingFiles.toString();
    }
    
    /**
     * This method scan on both directories and add all files as excluded files to be 
     * unpacked. Return the resulting filter.
     * 
     * @param exclusions
     * @param dir1
     * @param dir2
     * @return
     */
    protected String scanAndAddExistingFilesAsExcluded(File dir1, File dir2)
    {
        ArrayList exclusions = new ArrayList();
        
        if (dir1 != null)
        {
            addExcludes(dir1.getPath(), dir1, exclusions);
        }
        
        if (dir2 != null)
        {
            addExcludes(dir2.getPath(), dir2, exclusions);
        }
        
        StringBuffer existingFiles = new StringBuffer();
        for (int i = 0; i < exclusions.size(); i++)
        {
            existingFiles.append(exclusions.get(i));
            if (i != exclusions.size() - 1)
            {
                existingFiles.append(',');
            }
        }
        
        return existingFiles.toString();
    }
    
    protected void addExcludes(String basePath, File f, List exclusions)
    {
        
        if ( f.isDirectory() )
        {
            File [] fileList = f.listFiles();

            for (int i = 0; i < fileList.length; ++i)
            {
                if (!fileList[i].getName().equals(".svn"))
                {
                    addExcludes(basePath,fileList[i], exclusions);
                }
            }
        }
        else
        {
            String path = f.getPath();
            
            //path = path.replace(basePath,"");
            path = org.apache.commons.lang.StringUtils.replace(path, basePath, "");
            while (path.startsWith("/"))
            {
                path = path.substring(1);
            }
            while (path.startsWith("\\"))
            {
                path = path.substring(1);
            }
            path = StringUtils.replace( path, "\\", "/" );
            exclusions.add(path);
            getLog().info("Adding: "+path);
        }        
    }

    /**
     * @return Returns the markersDirectory.
     */
    public File getMarkersDirectory()
    {
        return this.markersDirectory;
    }

    /**
     * @param theMarkersDirectory The markersDirectory to set.
     */
    public void setMarkersDirectory( File theMarkersDirectory )
    {
        this.markersDirectory = theMarkersDirectory;
    }

    /**
     * @return Returns a comma separated list of excluded items
     */
    public String getExcludes()
    {
        return this.excludes;
    }

    /**
     * @param excludes A comma separated list of items to exclude i.e. **\/*.xml, **\/*.properties
     */
    public void setExcludes( String excludes )
    {
        this.excludes = excludes;
    }

    /**
     * @return Returns a comma separated list of included items
     */
    public String getIncludes()
    {
        return this.includes;
    }

    /**
     * @param includes A comma separated list of items to include i.e. **\/*.xml, **\/*.properties
     */
    public void setIncludes( String includes )
    {
        this.includes = includes;
    }
}
