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
public class HtmlPanelGroup
        extends javax.faces.component.html.HtmlPanelGroup
        implements UserRoleAware, DisplayValueOnlyCapable
{
    public static final int DEFAULT_COLSPAN = Integer.MIN_VALUE;

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }

    //private static final Log log = LogFactory.getLog(HtmlPanelGroup.class);

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelGroup";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Group";
    private static final boolean DEFAULT_DISPLAYVALUEONLY = false;
    
    public static final String BLOCK_LAYOUT = "block";

    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    private Boolean _displayValueOnly = null;
	private String _displayValueOnlyStyle = null;
	private String _displayValueOnlyStyleClass = null;
    private Integer _colspan = null;
	private String _layout;

    public HtmlPanelGroup()
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
    
    public String getLayout() {
        if (_layout != null) return _layout;
        ValueBinding vb = getValueBinding("layout");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setColspan(int colspan)
    {
        _colspan = new Integer(colspan);
    }

    public void setLayout(String layout) {
        _layout = layout;
    }

    public int getColspan()
    {
        if (_colspan != null) return _colspan.intValue();
        ValueBinding vb = getValueBinding("colspan");
        Number v = vb != null ? (Number)vb.getValue(getFacesContext()) : null;
        return v != null ? v.intValue() : DEFAULT_COLSPAN;
    }

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
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
        values[6] = _colspan;
        values[7] = _layout;
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
        _colspan = (Integer)values[6];
        _layout = (String) values[7];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
