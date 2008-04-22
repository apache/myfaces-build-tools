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
import org.apache.myfaces.custom.ExtendedComponentBase;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlSelectManyCheckbox
        extends javax.faces.component.html.HtmlSelectManyCheckbox
        implements UserRoleAware, DisplayValueOnlyCapable, ExtendedComponentBase, EscapeCapable
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

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectManyCheckbox";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Checkbox";
    private static final boolean DEFAULT_DISPLAYVALUEONLY = false;

    private String _layoutWidth = null;
    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    private Boolean _displayValueOnly = null;
	private String _displayValueOnlyStyle = null;
	private String _displayValueOnlyStyleClass = null;

    public HtmlSelectManyCheckbox()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setLayoutWidth(String layoutWidth)
    {
        _layoutWidth = layoutWidth;
    }

    public String getLayoutWidth()
    {
        if (_layoutWidth != null) return _layoutWidth;
        ValueBinding vb = getValueBinding("layoutWidth");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
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
        Object values[] = new Object[9];
        values[0] = super.saveState(context);
        values[1] = _layoutWidth;
        values[2] = _enabledOnUserRole;
        values[3] = _visibleOnUserRole;
        values[4] = _displayValueOnly;
        values[5] = _displayValueOnlyStyle;
        values[6] = _displayValueOnlyStyleClass;
        values[7] = _forceId;
        values[8] = _escape;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _layoutWidth = (String)values[1];
        _enabledOnUserRole = (String)values[2];
        _visibleOnUserRole = (String)values[3];
        _displayValueOnly = (Boolean)values[4];
        _displayValueOnlyStyle = (String)values[5];
        _displayValueOnlyStyleClass = (String)values[6];
        _forceId = (Boolean) values[7];
        _escape = (Boolean) values[8];
    }
    //------------------ GENERATED CODE END ---------------------------------------

    private Boolean _forceId = null;
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

    public void setForceId(boolean b)
    {
        _forceId = Boolean.valueOf(b);
    }

    public boolean isForceId()
    {
        if (_forceId != null) return _forceId.booleanValue();
        ValueBinding vb = getValueBinding("forceId");
        return vb != null && booleanFromObject(vb.getValue(getFacesContext()), false);
    }

    private static boolean booleanFromObject(Object obj, boolean defaultValue)
    {
        if(obj instanceof Boolean)
        {
            return ((Boolean) obj).booleanValue();
        }
        else if(obj instanceof String)
        {
            return Boolean.valueOf(((String) obj)).booleanValue();
        }

        return defaultValue;
    }
}
