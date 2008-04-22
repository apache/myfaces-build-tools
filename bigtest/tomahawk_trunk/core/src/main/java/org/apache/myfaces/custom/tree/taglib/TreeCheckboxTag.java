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
package org.apache.myfaces.custom.tree.taglib;

import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.tree.HtmlTreeCheckbox;
import org.apache.myfaces.shared_tomahawk.taglib.core.SelectItemTagBase;

/**
 * @author <a href="dlestrat@apache.org">David Le Strat</a>
 */
public class TreeCheckboxTag extends SelectItemTagBase
{
    /** The for attribute. */
    private String forAttr;
    
    /**
     * @see javax.faces.webapp.UIComponentTag#getComponentType()
     */
    public String getComponentType()
    {
        return HtmlTreeCheckbox.COMPONENT_TYPE;
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#getRendererType()
     */
    public String getRendererType()
    {
        return null;
    }
    
    /**
     * @return Returns the forAttr.
     */
    public String getFor()
    {
        return this.forAttr;
    }
    /**
     * @param forAttr The forAttr to set.
     */
    public void setFor(String forAttr)
    {
        this.forAttr = forAttr;
    }
    
    public void release() {
        super.release();
        this.forAttr = null;
    }
    
    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HtmlTreeCheckbox.FOR_ATTR, this.forAttr);
    }
}
