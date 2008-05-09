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
package org.apache.myfaces.custom.newspaper;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.DataProperties;

/**
 * Model for a table in multiple balanced columns.
 *
 * @JSFComponent
 *   name = "t:newspaperTable"
 *   class = "org.apache.myfaces.custom.newspaper.HtmlNewspaperTable"
 *   superClass = "org.apache.myfaces.custom.newspaper.AbstractHtmlNewspaperTable"
 *   tagClass = "org.apache.myfaces.custom.newspaper.HtmlNewspaperTableTag"
 *
 * @author <a href="mailto:jesse@odel.on.ca">Jesse Wilson</a>
 */
public abstract class AbstractHtmlNewspaperTable
        extends HtmlDataTable implements AlignProperty, DataProperties
{
    /** the component's renderers and type */
    public static final String RENDERER_TYPE = "org.apache.myfaces.HtmlNewspaperTable";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlNewspaperTable";

    /** the property names */
    public static final String NEWSPAPER_COLUMNS_PROPERTY = "newspaperColumns";
    public static final String SPACER_FACET_NAME = "spacer";
    
    private static final Integer DEFAULT_NEWSPAPER_COLUMNS = new Integer(1);

    /**
     * Set the number of columns the table will be divided over.
     * 
     * @JSFProperty
     *   defaultValue = "1"
     */
    public abstract int getNewspaperColumns();
    
    /**
     * @JSFProperty
     */
    public abstract String getNewspaperOrientation();    
    
    /**
     * Gets the spacer facet, between each pair of newspaper columns.
     */
    public UIComponent getSpacer() {
        return (UIComponent)getFacets().get(SPACER_FACET_NAME);
    }
    public void setSpacer(UIComponent spacer) {
        getFacets().put(SPACER_FACET_NAME, spacer);
    }

}