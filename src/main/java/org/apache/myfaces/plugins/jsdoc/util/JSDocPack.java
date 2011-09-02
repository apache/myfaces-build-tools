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

import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          base class for all our jsdoc unpackers
 */

public abstract class JSDocPack
{
    String _jarPath;
    File _jsdocContainer;

    protected JSDocPack()
    {

    }

    public abstract void unpack(String targetDir, Log log) throws IOException;

    protected void _expandJarFile(String targetDir, Log log, JarFile jarFile) throws IOException
    {
        Enumeration entries = jarFile.entries();
        while (entries.hasMoreElements())
        {
            JarEntry entry = (JarEntry) entries.nextElement();

            File targetFile = new File(targetDir + File.separator + entry.getName());
            if (entry.isDirectory())
            {
                targetFile.mkdir();
                continue;
            }
            InputStream istr = new BufferedInputStream(jarFile.getInputStream(entry));
            OutputStream ostr = new BufferedOutputStream(new FileOutputStream(targetFile));
            log.debug("[JSDOC] Unpacking jar: Writing" + targetFile);
            while (istr.available() > 0)
            {
                ostr.write(istr.read());
            }
            ostr.close();
            istr.close();
        }
    }

}
