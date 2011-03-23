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

import java.util.List;
import java.util.Map;

import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.FacesConfigBean;

public class ModelParams
{
    private String includes;

    private String excludes;

    private List sources;
    
    private List compositeComponentDirectories;
    
    private Map compositeComponentLibraries;
    
    private FacesConfigBean facesConfigBean;

    public ModelParams()
    {
        super();
    }
    public String getIncludes()
    {
        return includes;
    }
    public void setIncludes(String includes)
    {
        this.includes = includes;
    }
    public String getExcludes()
    {
        return excludes;
    }
    public void setExcludes(String excludes)
    {
        this.excludes = excludes;
    }
    public List getSourceDirs()
    {
        return sources;
    }
    public void setSourceDirs(List sources)
    {
        this.sources = sources;
    }
    public List getCompositeComponentDirectories()
    {
        return compositeComponentDirectories;
    }
    public Map getCompositeComponentLibraries()
    {
        return compositeComponentLibraries;
    }
    public void setCompositeComponentDirectories(List compositeComponentDirectories)
    {
        this.compositeComponentDirectories = compositeComponentDirectories;
    }
    public void setCompositeComponentLibraries(Map compositeComponentLibraries)
    {
        this.compositeComponentLibraries = compositeComponentLibraries;
    }
    public FacesConfigBean getFacesConfigBean()
    {
        return facesConfigBean;
    }
    public void setFacesConfigBean(FacesConfigBean facesConfigBean)
    {
        this.facesConfigBean = facesConfigBean;
    }
}
