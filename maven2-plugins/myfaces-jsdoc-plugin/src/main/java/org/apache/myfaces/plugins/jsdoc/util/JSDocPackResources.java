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
package org.apache.myfaces.plugins.jsdoc.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          unpacks a locally hosted jsdoc (which is placed in our resources folder
 */

public class JSDocPackResources extends JSDocPack
{
    /**
     * constructor
     */
    public JSDocPackResources()
    {
        super();
    }

    public void unpack(String targetDir, org.apache.maven.plugin.logging.Log log) throws IOException
    {
        JarFile jarFile = null;
        try
        {
            jarFile = new JarFile(fetchJarLocation());
            _expandJarFile(targetDir, log, jarFile);
        }
        catch (URISyntaxException e)
        {
            throw new IOException(e.getMessage());
        }

    }

    protected File fetchJarLocation() throws IOException, URISyntaxException
    {
        URL markerResourceLocation = this.getClass().getClassLoader().getResource("jsdoc-toolkit-2.4.0.jar");
        //we cannot simply use the uri we have to copy the file over to a temp file first
        File tempFile = File.createTempFile("jsdoc-toolkit-2.4.0", "jar");
        tempFile.deleteOnExit();
        FileUtils.copyURLToFile(markerResourceLocation, tempFile);

        return tempFile;

    }
}
