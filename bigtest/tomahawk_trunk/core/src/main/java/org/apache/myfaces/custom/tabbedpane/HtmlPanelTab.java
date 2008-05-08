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
package org.apache.myfaces.custom.tabbedpane;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.NamingContainer;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @JSFComponent
 *   name = "t:panelTab"
 *   tagClass = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTabTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelTab
        extends HtmlPanelGroup  implements NamingContainer
{
    //private static final Log log = LogFactory.getLog(HtmlPanelTab.class);


    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelTab";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Group";
    private static final boolean DEFAULT_DISABLED = false;

    private String _label = null;
    private Boolean _disabled = null;

    public HtmlPanelTab()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setLabel(String label)
    {
        _label = label;
    }

    public String getLabel()
    {
        if (_label != null) return _label;
        ValueBinding vb = getValueBinding("label");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }
    
    public void setDisabled(boolean disabled)
    {
        _disabled = Boolean.valueOf(disabled);
    }

    public boolean isDisabled()
    {
        if (_disabled != null) return _disabled.booleanValue();
        ValueBinding vb = getValueBinding("disabled");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_DISABLED;
    }


    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = _label;
        values[2] = _disabled;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _label = (String)values[1];
        _disabled =  (Boolean)values[2];
    }
    //------------------ GENERATED CODE END ---------------------------------------


    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) {
            return false;
        }
        return super.isRendered();
    }
}
