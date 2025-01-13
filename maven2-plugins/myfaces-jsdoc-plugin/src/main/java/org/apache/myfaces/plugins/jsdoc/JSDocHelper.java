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
package org.apache.myfaces.plugins.jsdoc;

import org.apache.myfaces.plugins.jsdoc.util.JSDocPack;
import org.apache.myfaces.plugins.jsdoc.util.XMLConfig;

public class JSDocHelper
{
    /**
     * the parsed xml filemap containing the single source files
     */
    private XMLConfig fileMap;

    private JSDocPack unpacker;

    /**
     * target path for the unpacked jsdoc engine
     */
    private String jsdocEngineUnpacked = null;

    /**
     * target paths for the javascript
     */
    private String javascriptTargetPath = null;

    /**
     * run path for the jsdoc engine
     */
    private String jsdocRunPath = null;

    public JSDocHelper()
    {
    }

    public XMLConfig getFileMap()
    {
        return fileMap;
    }

    public void setFileMap(XMLConfig fileMap)
    {
        this.fileMap = fileMap;
    }

    public JSDocPack getUnpacker()
    {
        return unpacker;
    }

    public void setUnpacker(JSDocPack unpacker)
    {
        this.unpacker = unpacker;
    }

    public String getJsdocEngineUnpacked()
    {
        return jsdocEngineUnpacked;
    }

    public void setJsdocEngineUnpacked(String jsdocEngineUnpacked)
    {
        this.jsdocEngineUnpacked = jsdocEngineUnpacked;
    }

    public String getJavascriptTargetPath()
    {
        return javascriptTargetPath;
    }

    public void setJavascriptTargetPath(String javascriptTargetPath)
    {
        this.javascriptTargetPath = javascriptTargetPath;
    }

    public String getJsdocRunPath()
    {
        return jsdocRunPath;
    }

    public void setJsdocRunPath(String jsdocRunPath)
    {
        this.jsdocRunPath = jsdocRunPath;
    }
    
    
}
