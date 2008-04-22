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
 * --
 * $Id$
 */
package org.apache.myfaces.custom.limitrendered;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;
import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagUtils;

/**
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UILimitRenderedTag extends UIComponentTagBase
{
    private String type;
    private String value;

    /**
     * @see javax.faces.webapp.UIComponentTag#getComponentType()
     */
    public String getComponentType()
    {
        return UILimitRendered.COMPONENT_TYPE;
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#getRendererType()
     */
    public String getRendererType()
    {
        return null;
    }
    
    /**
     * @return the type
     */
    public String getType()
    {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, "type", this.type);
        if (this.value != null) {
            if (UIComponentTagUtils.isValueReference(this.value)) {
                setValueBinding(component, "value", this.value);
            } else {
                component.getAttributes().put("value", this.value);
            }
        }
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#release()
     */
    public void release()
    {
        super.release();
        type = null;
        value = null;
    }
}
