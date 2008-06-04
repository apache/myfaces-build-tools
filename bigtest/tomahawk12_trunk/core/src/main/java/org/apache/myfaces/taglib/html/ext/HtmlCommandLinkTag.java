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
import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlCommandLinkTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCommandLinkTag
        extends HtmlCommandLinkTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlCommandLinkTag.class);

    public String getComponentType()
    {
        return HtmlCommandLink.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlCommandLink.DEFAULT_RENDERER_TYPE;
    }


    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    private String _actionFor;
    private String _disabled;
    private String _disabledStyle;
    private String _disabledStyleClass;

    public void release() {
        super.release();

        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _actionFor = null;
        _disabled = null;
        _disabledStyle = null;
        _disabledStyleClass = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setStringProperty(component, "actionFor", _actionFor);
        setBooleanProperty(component, "disabled", _disabled);
        setStringProperty(component, "disabledStyle",_disabledStyle);
        setStringProperty(component, "disabledStyleClass",_disabledStyleClass);
    }


    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setActionFor(String actionFor)
    {
        _actionFor = actionFor;
    }

    public void setDisabled(String disabled)
    {
        _disabled = disabled;
    }

    public void setDisabledStyle(String disabledStyle)
    {
        _disabledStyle = disabledStyle;
    }

    public void setDisabledStyleClass(String disabledStyleClass)
    {
        _disabledStyleClass = disabledStyleClass;
    }
}
