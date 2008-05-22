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

import javax.faces.component.UIColumn;

/**
 * @JSFComponent
 *   name = "s:sortableColumn"
 *   class = "org.apache.myfaces.custom.table.SortableColumn"
 *   superClass = "org.apache.myfaces.custom.table.AbstractSortableColumn"
 *   tagClass = "org.apache.myfaces.custom.table.SortableColumnTag"
 *   
 * @author Thomas Spiegl
 */
public abstract class AbstractSortableColumn extends UIColumn {
    
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SortableColumn";

    /**
     * see http://dojotoolkit.org org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getField();

    /**
     * default=String {Number,Date,html,String} see http://dojotoolkit.org org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getDataType();

    /**
     * {asc, desc} see http://dojotoolkit.org org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getSort();

    /**
     * {asc, desc} see http://dojotoolkit.org org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getFormat();

    /**
     * {asc, desc} see http://dojotoolkit.org org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getAlign();

    /**
     * {asc, desc} see http://dojotoolkit.org org filterTable
     * 
     * @JSFProperty
     */
    public abstract String getValign();

    /**
     * column header text
     * 
     * @JSFProperty
     */
    public abstract String getText();

    /**
     * default=true escape text
     * 
     * @JSFProperty
     */
    public abstract Boolean getEscape();
}
