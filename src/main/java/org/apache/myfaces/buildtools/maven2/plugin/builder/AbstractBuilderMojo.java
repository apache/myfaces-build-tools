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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * 
 * @author Leonardo Uribe
 *
 */
public abstract class AbstractBuilderMojo extends AbstractMojo
{
    /**
     * 
     * @parameter expression="${project.build.directory}/myfaces-builder-plugin-cachefile"
     */
    private File cacheFile;

    /**
     * Does not check if the model is up to date and always build the model when it is executed
     * 
     * @parameter
     */
    private String noCache;

    protected boolean isCachingEnabled()
    {
        return (!Boolean.valueOf(noCache)) && cacheFile != null;
    }
    
    protected void loadCache(Properties cacheInfo) throws MojoExecutionException
    {
        try
        {
            if (isCachingEnabled() && cacheFile != null && cacheFile.exists())
            {
                cacheInfo.load(new BufferedInputStream(new FileInputStream(cacheFile)));
            }
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Error during saving cache information", e);
        }
    }
    
    protected void storeCache(Properties cacheInfo) throws MojoExecutionException
    {
        if (isCachingEnabled())
        {
            //save info
            try
            {
                cacheInfo.store(new BufferedOutputStream(new FileOutputStream(cacheFile)), 
                        "Created: "+ Long.toString(System.currentTimeMillis()));
            }
            catch (IOException e)
            {
                throw new MojoExecutionException("Error during saving cache information", e);
            }
        }
    }
    
    protected boolean isFileUpToDate(Properties cachedInfo, File outFile)
    {
        return isFileUpToDate(cachedInfo, outFile.lastModified(), outFile);
    }
    
    protected boolean isFileUpToDate(Properties cachedInfo, long lastModifiedMetadata, File outFile)
    {
        boolean upToDate = true;
        if (!outFile.exists())
        {
            return false;
        }
        String lastModifiedString = cachedInfo.getProperty(outFile.getAbsolutePath());
        if (lastModifiedString == null)
        {
            upToDate = false;
        }
        else if (!outFile.exists())
        {
            upToDate = false;
        }
        else
        {
            Long lastModified = Long.valueOf(lastModifiedString);
            if (lastModified != null && lastModifiedMetadata > lastModified.longValue())
            {
                upToDate = false;
            }
        }
        return upToDate;
    }
}
