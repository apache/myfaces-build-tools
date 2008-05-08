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
package org.apache.myfaces.custom.tree;

import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * 
 * @JSFComponent
 *   name = "t:treeCheckbox"
 *   tagClass = "org.apache.myfaces.custom.tree.taglib.TreeCheckboxTag"
 *   
 * @author <a href="mailto:dlestrat@yahoo.com">David Le Strat</a>
 */
public class HtmlTreeCheckbox extends UISelectItem
{
    /** The for attribute. */
    private String forAttr = null;
    
    /** The for attribute declaration. */
    public static final String FOR_ATTR = "for".intern();
    
    /** The component type. */
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTreeCheckbox";
    
    /** The component family. */
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.HtmlTreeCheckbox";
    
    /** The default renderer type. */
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTreeCheckbox";
    
    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public HtmlTreeCheckbox()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }
    
    /**
     * @see javax.faces.component.UIComponent#getFamily()
     */
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
    
    /**
     * @return The for attribute.
     */
    public String getFor()
    {
        if (forAttr != null) return forAttr;
        ValueBinding vb = getValueBinding(FOR_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }
    
    /**
     * @param forAttr The for attribute.
     */
    public void setFor(String forAttr)
    {
        this.forAttr = forAttr;
    }
    
    /**
     * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = forAttr;
        return ((Object) (values));
    }

    /**
     * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        forAttr = (String)values[1];
    }

}
