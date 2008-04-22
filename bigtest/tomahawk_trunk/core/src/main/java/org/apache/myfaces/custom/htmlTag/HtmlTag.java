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
package org.apache.myfaces.custom.htmlTag;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author bdudney (latest modification by $Author$)
 * @version $Revision$ $Date: 2005-05-11 11:47:12 -0400 (Wed, 11 May 2005) $
 */
public class HtmlTag extends UIOutput implements UserRoleAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTag";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    private static final String DEFAULT_RENDERER_TYPE = HtmlTagRenderer.RENDERER_TYPE;

    private String _style = null;
    private String _styleClass = null;
    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;

    public HtmlTag()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this,
                getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }

    public String getStyle()
    {
        if (_style != null)
            return _style;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyle(String style)
    {
        this._style = style;
    }

    public String getStyleClass()
    {
        if (_styleClass != null)
            return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass)
    {
        this._styleClass = styleClass;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _style = (String) values[1];
        _styleClass = (String) values[2];
        _enabledOnUserRole = (String) values[3];
        _visibleOnUserRole = (String) values[4];
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = _style;
        values[2] = _styleClass;
        values[3] = _enabledOnUserRole;
        values[4] = _visibleOnUserRole;
        return values;
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

}