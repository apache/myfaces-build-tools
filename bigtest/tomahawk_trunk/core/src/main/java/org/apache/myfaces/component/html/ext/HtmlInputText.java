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
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlInputText
        extends javax.faces.component.html.HtmlInputText
        implements UserRoleAware, DisplayValueOnlyCapable
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

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlInputText";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Text";

    private static final boolean DEFAULT_DISPLAYVALUEONLY = false;

    private String _autocomplete = null;
    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    
    private Boolean _displayValueOnly = null;
    private String _displayValueOnlyStyle = null;
    private String _displayValueOnlyStyleClass = null;
    
    private Boolean _disabledOnClientSide = null;

    public HtmlInputText()
    {
        super();

        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAutocomplete(String autocomplete)
    {
        _autocomplete = autocomplete;
    }

    public String getAutocomplete()
    {
        if (_autocomplete != null) return _autocomplete;
        ValueBinding vb = getValueBinding("autocomplete");
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

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }
	
    public void setDisplayValueOnly(boolean displayValueOnly)
    {
        _displayValueOnly = Boolean.valueOf(displayValueOnly);
    }

    public boolean isSetDisplayValueOnly() {
        if (_displayValueOnly != null) return true;
        ValueBinding vb = getValueBinding("displayValueOnly");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null;
    }

    public boolean isDisplayValueOnly()
    {
        if (_displayValueOnly != null) return _displayValueOnly.booleanValue();
        ValueBinding vb = getValueBinding("displayValueOnly");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_DISPLAYVALUEONLY;
    }

    public void setDisplayValueOnlyStyle(String displayValueOnlyStyle)
    {
        _displayValueOnlyStyle = displayValueOnlyStyle;
    }

    public String getDisplayValueOnlyStyle()
    {
        if (_displayValueOnlyStyle != null) return _displayValueOnlyStyle;
        ValueBinding vb = getValueBinding("displayValueOnlyStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }
	
    public void setDisplayValueOnlyStyleClass(String displayValueOnlyStyleClass)
    {
        _displayValueOnlyStyleClass = displayValueOnlyStyleClass;
    }

    public String getDisplayValueOnlyStyleClass()
    {
        if (_displayValueOnlyStyleClass != null) return _displayValueOnlyStyleClass;
        ValueBinding vb = getValueBinding("displayValueOnlyStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }
    
    public void setDisabledOnClientSide(boolean disabledOnClientSide)
    {
        _disabledOnClientSide = Boolean.valueOf(disabledOnClientSide);
    }

    public boolean isDisabledOnClientSide()
    {
        if (_disabledOnClientSide != null) return _disabledOnClientSide.booleanValue();
        ValueBinding vb = getValueBinding("disabledOnClientSide");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : false;
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[8];
        values[0] = super.saveState(context);
        values[1] = _enabledOnUserRole;
        values[2] = _visibleOnUserRole;
        values[3] = _displayValueOnly;
        values[4] = _displayValueOnlyStyle;
        values[5] = _displayValueOnlyStyleClass;
        values[6] = _disabledOnClientSide;
        values[7] = _autocomplete;
        return values;
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
        _disabledOnClientSide = (Boolean)values[6];
        _autocomplete = (String)values[7];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
