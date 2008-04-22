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
package org.apache.myfaces.component.html.ext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @author Manfred Geiler
 * @version $Revision$ $Date$
 */
public class HtmlCommandLink
        extends javax.faces.component.html.HtmlCommandLink
        implements UserRoleAware
{
        
    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }        
    
    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandLink";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Link";

    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    private String _actionFor = null;
    private Boolean _disabled;
    private String _disabledStyle;
    private String _disabledStyleClass;

    public HtmlCommandLink()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }


    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public String getEnabledOnUserRole()
    {
        if (_enabledOnUserRole != null) return _enabledOnUserRole;
        ValueBinding vb = getValueBinding("enabledOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public String getVisibleOnUserRole()
    {
        if (_visibleOnUserRole != null) return _visibleOnUserRole;
        ValueBinding vb = getValueBinding("visibleOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setActionFor(String actionFor)
    {
        _actionFor = actionFor;
    }

    public String getActionFor()
    {
        if (_actionFor != null) return _actionFor;
        ValueBinding vb = getValueBinding("actionFor");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public boolean isDisabled()
    {
        if (_disabled != null) return _disabled.booleanValue();
        ValueBinding vb = getValueBinding("disabled");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public void setDisabled(boolean disabled)
    {
        _disabled = disabled ? Boolean.TRUE : Boolean.FALSE;
    }

    public String getDisabledStyle()
    {
        if (_disabledStyle != null) return _disabledStyle;
        ValueBinding vb = getValueBinding("disabledStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setDisabledStyle(String disabledStyle)
    {
        _disabledStyle = disabledStyle;
    }

    public String getDisabledStyleClass()
    {
        if (_disabledStyleClass != null) return _disabledStyleClass;
        ValueBinding vb = getValueBinding("disabledStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setDisabledStyleClass(String disabledStyleClass)
    {
        _disabledStyleClass = disabledStyleClass;
    }

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[7];
        values[0] = super.saveState(context);
        values[1] = _enabledOnUserRole;
        values[2] = _visibleOnUserRole;
        values[3] = _actionFor;
        values[4] = _disabled;
        values[5] = _disabledStyle;
        values[6] = _disabledStyleClass;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _enabledOnUserRole = (String)values[1];
        _visibleOnUserRole = (String)values[2];
        _actionFor = (String)values[3];
        _disabled = (Boolean) values[4];
        _disabledStyle = (String) values[5];
        _disabledStyleClass = (String) values[6];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
