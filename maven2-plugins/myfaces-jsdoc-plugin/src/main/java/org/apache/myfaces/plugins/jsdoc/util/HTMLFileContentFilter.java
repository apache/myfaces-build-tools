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
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          Since jsdoc adds the full canonical path to our filenames we have to fix it within the generated html
 */

public class HTMLFileContentFilter implements IOFileFilter
{

    String substitutionDir;

    public HTMLFileContentFilter(String substitutionDir)
    {
        this.substitutionDir = substitutionDir;
    }

    private boolean fixFile(String fileName) throws IOException
    {
        if (!fileName.toLowerCase().endsWith(".html"))
        {
            return false;
        }
        String fileContents = FileUtils.readFileToString(new File(fileName));
        List currFile = FileUtils.readLines(new File(fileName));
        List targetLines = new ArrayList(currFile.size());
        Iterator it = currFile.iterator();
        while (it.hasNext())
        {
            Object line = it.next();
            String sLine = (String) line;
            targetLines.add(sLine.replaceAll(substitutionDir, ""));
        }
        FileUtils.writeLines(new File(fileName), targetLines);
        return true;
    }

    public boolean accept(File file)
    {
        if (file.isDirectory())
        {
            return false;
        }
        try
        {
            fixFile(file.getAbsolutePath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public boolean accept(File file, String s)
    {
        if (file.isDirectory())
        {
            return false;
        }
        try
        {
            fixFile(file.getAbsolutePath() + File.separator + s);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
