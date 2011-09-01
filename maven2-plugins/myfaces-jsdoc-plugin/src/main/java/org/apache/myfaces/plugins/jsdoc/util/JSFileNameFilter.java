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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          A javascript filename filter which can be used within the context of commons-io
 */
public class JSFileNameFilter implements IOFileFilter
{

    XMLConfig _fileMap = null;

    Map _sortedResults = new TreeMap();

    public JSFileNameFilter(XMLConfig fileMap)
    {
        this._fileMap = fileMap;
    }

    private boolean matchNames(String fileName)
    {
        Iterator it = _fileMap.getFileNames().iterator();
        if (fileName != null)
        {
            fileName = fileName.replace('\\', '/');
        }
        while (it.hasNext())
        {
            String matchPattern = (String) it.next();
            boolean matches = FilenameUtils.wildcardMatch(fileName, matchPattern);
            if (matches)
            {
                _sortedResults.put(_fileMap.getFileNameIdx().get(matchPattern), fileName);
                return matches;
            }
        }
        return false;
    }

    public boolean accept(File file)
    {
        //no js file no match
        return file.getName().endsWith(".js") && matchNames(file.getAbsolutePath());
    }

    public boolean accept(File file, String s)
    {
        return s.endsWith(".js") && matchNames(file.getAbsolutePath() + "/" + s);
    }

    public XMLConfig getFileMap()
    {
        return _fileMap;
    }

    public void setFileMap(XMLConfig fileMap)
    {
        _fileMap = fileMap;
    }

    public Map getSortedResults()
    {
        return _sortedResults;
    }

    public void setSortedResults(Map sortedResults)
    {
        _sortedResults = sortedResults;
    }
}


