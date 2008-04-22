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

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlMessage
        extends javax.faces.component.html.HtmlMessage
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

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlMessage";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Message";

    private String _summaryFormat = null;
    private String _detailFormat = null;
    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    private Boolean _replaceIdWithLabel = null;
    private Boolean _forceSpan = null;

    public HtmlMessage()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }


    public void setSummaryFormat(String summaryFormat)
    {
        _summaryFormat = summaryFormat;
    }

    public String getSummaryFormat()
    {
        if (_summaryFormat != null) return _summaryFormat;
        ValueBinding vb = getValueBinding("summaryFormat");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDetailFormat(String detailFormat)
    {
        _detailFormat = detailFormat;
    }

    public String getDetailFormat()
    {
        if (_detailFormat != null) return _detailFormat;
        ValueBinding vb = getValueBinding("detailFormat");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public String getEnabledOnUserRole()
    {
        if (_enabledOnUserRole != null) return _enabledOnUserRole;
        ValueBinding vb = getValueBinding("enabledOnUserRole");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public String getVisibleOnUserRole()
    {
        if (_visibleOnUserRole != null) return _visibleOnUserRole;
        ValueBinding vb = getValueBinding("visibleOnUserRole");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setReplaceIdWithLabel(boolean replaceIdWithLabel)
    {
        _replaceIdWithLabel = Boolean.valueOf(replaceIdWithLabel);
    }

    public boolean isReplaceIdWithLabel()
    {
        if (_replaceIdWithLabel != null) return _replaceIdWithLabel.booleanValue();
        ValueBinding vb = getValueBinding("replaceIdWithLabel");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : false;
    }

    public void setForceSpan(boolean forceSpan)
    {
        _forceSpan = Boolean.valueOf(forceSpan);
    }

    public boolean getForceSpan()
    {
        if (_forceSpan != null) return _forceSpan.booleanValue();
        ValueBinding vb = getValueBinding("forceRenderSpan");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : false;
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
        values[1] = _summaryFormat;
        values[2] = _detailFormat;
        values[3] = _enabledOnUserRole;
        values[4] = _visibleOnUserRole;
        values[5] = _replaceIdWithLabel;
        values[6] = _forceSpan;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _summaryFormat = (String) values[1];
        _detailFormat = (String) values[2];
        _enabledOnUserRole = (String) values[3];
        _visibleOnUserRole = (String) values[4];
        _replaceIdWithLabel = (Boolean) values[5];
        _forceSpan = (Boolean) values[6];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
