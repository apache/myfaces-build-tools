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

import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.component.EscapeCapable;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlSelectOneMenu
        extends javax.faces.component.html.HtmlSelectOneMenu
        implements UserRoleAware, DisplayValueOnlyCapable, EscapeCapable
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

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectOneMenu";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Menu";
    private static final boolean DEFAULT_DISPLAYVALUEONLY = false;

    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    private Boolean _displayValueOnly = null;
	private String _displayValueOnlyStyle = null;
	private String _displayValueOnlyStyleClass = null;

    public HtmlSelectOneMenu()
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

    public boolean isSetDisplayValueOnly() {
        if (_displayValueOnly != null) return true;
        ValueBinding vb = getValueBinding("displayValueOnly");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null;
    }

    public boolean isDisplayValueOnly() {
        if (_displayValueOnly != null) return _displayValueOnly.booleanValue();
        ValueBinding vb = getValueBinding("displayValueOnly");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_DISPLAYVALUEONLY;
    }

    public void setDisplayValueOnly(boolean displayValueOnly) {
        _displayValueOnly = Boolean.valueOf(displayValueOnly);
    }

    public String getDisplayValueOnlyStyle() {
        if (_displayValueOnlyStyle != null) return _displayValueOnlyStyle;
        ValueBinding vb = getValueBinding("displayValueOnlyStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setDisplayValueOnlyStyle(String displayValueOnlyStyle) {
        _displayValueOnlyStyle = displayValueOnlyStyle;
    }

    public String getDisplayValueOnlyStyleClass() {
        if (_displayValueOnlyStyleClass != null) return _displayValueOnlyStyleClass;
        ValueBinding vb = getValueBinding("displayValueOnlyStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setDisplayValueOnlyStyleClass(String displayValueOnlyStyleClass) {
        _displayValueOnlyStyleClass = displayValueOnlyStyleClass;
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
        values[3] = _displayValueOnly;
        values[4] = _displayValueOnlyStyle;
        values[5] = _displayValueOnlyStyleClass;
        values[6] = _escape;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _enabledOnUserRole = (String)values[1];
        _visibleOnUserRole = (String)values[2];
        _displayValueOnly = (Boolean)values[3];
        _displayValueOnlyStyle = (String)values[4];
        _displayValueOnlyStyleClass = (String)values[5];
        _escape = (Boolean)values[6];
    }
    //------------------ GENERATED CODE END ---------------------------------------
    
    private Boolean _escape = null;
    private static final boolean DEFAULT_ESCAPE = true;

    public void setEscape(boolean escape)
    {
        _escape = Boolean.valueOf(escape);
    }

    public boolean isEscape()
    {
        if (_escape != null) return _escape.booleanValue();
        ValueBinding vb = getValueBinding("escape");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_ESCAPE;
    }
}
