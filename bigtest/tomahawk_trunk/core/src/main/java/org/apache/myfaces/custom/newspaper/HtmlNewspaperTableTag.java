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

import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlDataTableTagBase;

/**
 * Tag for a table in multiple balanced columns.
 *
 * @author <a href="mailto:jesse@odel.on.ca">Jesse Wilson</a>
 */
public class HtmlNewspaperTableTag
        extends HtmlDataTableTagBase
{
    /** the number of newspaper columns */
    private String newspaperColumns = null;
	
	private String newspaperOrientation = null;
    
    public void release() {
        super.release();
        newspaperColumns = null;
        newspaperOrientation = null;
    }
    
    public String getComponentType() {
        return HtmlNewspaperTable.COMPONENT_TYPE;
    }
    
    public String getRendererType() {
        return HtmlNewspaperTable.RENDERER_TYPE;
    }
    
    public void setNewspaperColumns(String newspaperColumns) {
        this.newspaperColumns = newspaperColumns;
    }
	
    public void setNewspaperOrientation(String newspaperOrientation) {
		this.newspaperOrientation = newspaperOrientation;
	}

	/**
     * Apply properties from this tag to the specified component.
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        setIntegerProperty(component, HtmlNewspaperTable.NEWSPAPER_COLUMNS_PROPERTY, newspaperColumns);
		setStringProperty(component, HtmlDataTable.NEWSPAPER_ORIENTATION_PROPERTY, newspaperOrientation);
    }
}
