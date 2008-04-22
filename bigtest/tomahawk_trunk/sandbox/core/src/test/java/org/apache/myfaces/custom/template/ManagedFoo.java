/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.custom.template;

import java.io.InputStream;

/**
 * Simple class to be used as a ManagedBean in testing.
 *
 * @author Sean Schofield
 */
public class ManagedFoo
{
    private String content;
    private String stylesheet;
    private String contentLocation;
    private String stylesheetLocation;
    private InputStream contentStream;
    private InputStream styleStream;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getStylesheet()
    {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet)
    {
        this.stylesheet = stylesheet;
    }

    public String getContentLocation()
    {
        return contentLocation;
    }

    public void setContentLocation(String contentLocation)
    {
        this.contentLocation = contentLocation;
    }

    public String getStylesheetLocation()
    {
        return stylesheetLocation;
    }

    public void setStylesheetLocation(String stylesheetLocation)
    {
        this.stylesheetLocation = stylesheetLocation;
    }

    public InputStream getContentStream()
    {
        return contentStream;
    }

    public void setContentStream(InputStream contentStream)
    {
        this.contentStream = contentStream;
    }

    public InputStream getStyleStream()
    {
        return styleStream;
    }

    public void setStyleStream(InputStream styleStream)
    {
        this.styleStream = styleStream;
    }
}
