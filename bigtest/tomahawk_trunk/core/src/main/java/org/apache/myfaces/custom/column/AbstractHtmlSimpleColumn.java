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
package org.apache.myfaces.custom.column;

import javax.faces.component.UIColumn;

/**
 * @JSFComponent
 *   name = "t:column"
 *   class = "org.apache.myfaces.custom.column.HtmlSimpleColumn"
 *   superClass = "org.apache.myfaces.custom.column.AbstractHtmlSimpleColumn"
 *   tagClass = "org.apache.myfaces.custom.column.HtmlColumnTag"
 * 
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class AbstractHtmlSimpleColumn extends UIColumn implements HtmlColumn
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlColumn";

    
    public boolean isGroupByValueSet()
    {
        return getGroupByValue() != null;
    }
    
    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isGroupBy();


    /**
     * @JSFProperty
     */
	public abstract Object getGroupByValue();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDefaultSorted();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isSortable();

    /**
     * @JSFProperty
     */    
    public abstract String getSortPropertyName();

}
