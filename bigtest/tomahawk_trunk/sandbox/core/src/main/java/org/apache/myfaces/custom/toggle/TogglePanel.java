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

package org.apache.myfaces.custom.toggle;

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Container class allows user to toggle between view/edit mode.
 * 
 * @JSFComponent
 *   name = "s:togglePanel"
 *   tagClass = "org.apache.myfaces.custom.toggle.TogglePanelTag"
 *   
 * @author Sharath
 * 
 */
public class TogglePanel extends HtmlPanelGroup
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.TogglePanel";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TogglePanel";

    public static final boolean DEFAULT_TOGGLED = false;

    private Boolean _toggled = null;

    public TogglePanel()
    {
        super();
        setRendererType(TogglePanel.DEFAULT_RENDERER_TYPE);
    }

    public boolean isToggled()
    {
        if (_toggled != null) return _toggled.booleanValue();
        ValueBinding vb = getValueBinding("toggled");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_TOGGLED;
    }
    public void setToggled(boolean toggleMode)
    {
    	_toggled = Boolean.valueOf( toggleMode );  
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = _toggled;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _toggled =  (Boolean)values[1];
    }
    
    public void processDecodes(FacesContext context)
    {
        super.processDecodes(context);

        String hiddenFieldId = this.getClientId(context) + "_hidden";
        String toggleMode = (String) context.getExternalContext().getRequestParameterMap().get(hiddenFieldId);

        if (toggleMode != null && toggleMode.trim().equals("1")) {
            this.setToggled(true);
        }
    }

    public void processUpdates(FacesContext context)
    {
        super.processUpdates(context);
        this.setToggled(false);
    }
}
