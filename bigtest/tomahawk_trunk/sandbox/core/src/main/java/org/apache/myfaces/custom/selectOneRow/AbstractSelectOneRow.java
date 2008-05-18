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
package org.apache.myfaces.custom.selectOneRow;

import javax.faces.component.UIInput;

import org.apache.myfaces.component.AlignProperty;

/**
 * 
 * @JSFComponent
 *   name = "s:selectOneRow"
 *   class = "org.apache.myfaces.custom.selectOneRow.SelectOneRow"
 *   superClass = "org.apache.myfaces.custom.selectOneRow.AbstractSelectOneRow"
 *   tagClass = "org.apache.myfaces.custom.selectOneRow.SelectOneRowTag"
 *   
 *
 */
public abstract class AbstractSelectOneRow extends UIInput 
    implements AlignProperty
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.SelectOneRow";

    public static final String COMPONENT_FAMILY = "org.apache.myfaces.SelectOneRow";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SelectOneRow";

    /**
     * @JSFProperty
     *   literalOnly = "true"
     */
    public abstract String getGroupName();
    
    /**
     * HTML: Specifies the horizontal alignment of this element. Deprecated in HTML 4.01.
     * 
     * @JSFProperty 
     */
    public abstract String getAlign();  
    
    /**
     * HTML: When true, this element cannot receive focus.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDisabled();   
    
    /**
     * HTML: When true, indicates that this component cannot be modified by the user.
     * The element may receive focus unless it has also been disabled.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isReadonly();    
    
    /**
     * HTML: Specifies a script to be invoked when the element loses focus.
     * 
     * @JSFProperty
     */
    public abstract String getOnblur();
    
    /**
     * HTML: Specifies a script to be invoked when the element receives focus.
     * 
     * @JSFProperty
     */
    public abstract String getOnfocus();

    /**
     * HTML: Specifies a script to be invoked when the element is modified.
     * 
     * @JSFProperty
     */
    public abstract String getOnchange();


    /**
     * HTML: Specifies a script to be invoked when the element is selected.
     * 
     * @JSFProperty
     */
    public abstract String getOnselect();
    
    /**
     * HTML: Script to be invoked when the element is clicked.
     * 
     * @JSFProperty
     */
    public abstract String getOnclick();    
    
}
