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

import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          An unpacker for the jsdoc toolkit
 *          Unfortunately we have to unpack the jsdoc toolkit during
 *          the build process because we have a set of js files
 *          running via rhino which will not work unless we have them
 *          on the filesystem instead of the jar.
 *          <p/>
 *          The jsdoc toolkit itself can be streamed in via maven.
 */

public class JSDocPackMaven extends JSDocPack
{

    /**
     * constructor
     */
    public JSDocPackMaven()
    {
        super();
        _jarPath = fetchJarLocation();

    }

    /**
     * public method which is called from the outside
     * unpacks the current jsdoc package into our target dir
     *
     * @param targetDir the target dir to unpack to
     * @param log       the target log to log into
     * @throws java.io.IOException in case of an error
     */
    public void unpack(String targetDir, org.apache.maven.plugin.logging.Log log) throws IOException
    {
        JarFile jarFile = new JarFile(_jarPath);
        _expandJarFile(targetDir, log, jarFile);
    }

    /**
     * fetches the location of the jsdoc hosting jar,
     * the location already should be in maven and our main
     * constant is the <code>app/main.js</code> file which should be present
     * everywhere
     *
     * @return a string to the location
     */
    protected String fetchJarLocation()
    {
        URL markerResourceLocation = this.getClass().getClassLoader().getResource("app/main.js");
        String markerResource = markerResourceLocation.getFile();
        markerResource = markerResource.substring(5);
        markerResource = markerResource.substring(0, markerResource.length() - "!/app/main.js".length());
        return markerResource;
    }

}
