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
package org.apache.myfaces.custom.table;

import javax.faces.component.UIData;

/**
 * @JSFComponent
 *   name = "s:filterTable"
 *   class = "org.apache.myfaces.custom.table.FilterTable"
 *   superClass = "org.apache.myfaces.custom.table.AbstractFilterTable"
 *   tagClass = "org.apache.myfaces.custom.table.FilterTableTag"
 *   
 * @author Thomas Spiegl
 */
public abstract class AbstractFilterTable extends UIData {
    
    public static final String COMPONENT_TYPE = "org.apache.myfaces.FilterTable";
    public static final String COMPONENT_FAMILY = "javax.faces.Data";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.FilterTable";

    /**
     * CSS style class attribute
     * 
     * @JSFProperty
     */
    public abstract String getStyleClass();

    /**
     * default=fixedHeader see http://dojotoolkit.org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getHeadClass();

    /**
     * default=scrollContent see http://dojotoolkit.org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getTbodyClass();

    /**
     * default=true see http://dojotoolkit.org filterTable
     * 
     * @JSFProperty
     */
    public abstract Boolean getMultiple();

    /**
     * default=true see http://dojotoolkit.org filterTable
     * 
     * @JSFProperty
     */
    public abstract Boolean getAlternateRows();

    /**
     * default=1 see http://dojotoolkit.org filterTable
     * 
     * @JSFProperty
     */
    public abstract Integer getMaxSortable();

    /**
     * default=0
     * 
     * @JSFProperty
     */
    public abstract Integer getCellpadding();

    /**
     * default=0
     * 
     * @JSFProperty
     */
    public abstract Integer getCellspacing();

    /**
     * default=0
     * 
     * @JSFProperty
     */
    public abstract Integer getBorder();
}
