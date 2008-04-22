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
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlMessage;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlMessageTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlMessageTag
        extends HtmlMessageTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlOutputFormatTag.class);

    public String getComponentType()
    {
        return HtmlMessage.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlMessage.DEFAULT_RENDERER_TYPE;
    }

    private String _summaryFormat;
    private String _detailFormat;
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _replaceIdWithLabel;
    private String _forceSpan;

    public void release() {
        super.release();

        _summaryFormat = null;
        _detailFormat = null;
        _enabledOnUserRole = null;
        _visibleOnUserRole = null;
        _replaceIdWithLabel = null;
        _forceSpan = null;
    }
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "summaryFormat", _summaryFormat);
        setStringProperty(component, "detailFormat", _detailFormat);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setBooleanProperty(component, "replaceIdWithLabel",_replaceIdWithLabel==null?Boolean.TRUE.toString():_replaceIdWithLabel);
        setBooleanProperty(component, "forceSpan",_forceSpan ==null?Boolean.FALSE.toString():_forceSpan);
    }

    public void setSummaryFormat(String summaryFormat)
    {
        _summaryFormat = summaryFormat;
    }

    public void setDetailFormat(String detailFormat)
    {
        _detailFormat = detailFormat;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setReplaceIdWithLabel(String replaceIdWithLabel)
    {
        _replaceIdWithLabel = replaceIdWithLabel;
    }

    public void setForceSpan(String forceSpan)
    {
        _forceSpan = forceSpan;
    }
}
