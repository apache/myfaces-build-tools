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
package org.apache.myfaces.custom.navmenu;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.taglib.core.SelectItemTagBase;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlNavigationMenuItemTag extends SelectItemTagBase
{

    private static final String ICON_ATTR   = "icon";
    private static final String SPLIT_ATTR  = "split";
    private static final String TARGET_ATTR = "target";
    private static final String DISABLED_ATTR = "disabled";
    private static final String DISABLED_STYLE_ATTR = "disabledStyle";
    private static final String DISABLED_STYLE_CLASS_ATTR = "disabledStyleClass";
    private static final String ACTIVE_ON_VIEW_IDS_ATTR = "activeOnViewIds";

    private String _icon;
    private String _action;
    private String _actionListener;
    private String _immediate;
    private String _split;
    private String _target;
    private String _disabled;
    private String _disabledStyle;
    private String _disabledStyleClass;
    private String _activeOnViewIds;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    public void release() {
        super.release();

        _icon = null;
        _action = null;
        _split= null;
        _enabledOnUserRole= null;
        _visibleOnUserRole= null;
        _activeOnViewIds = null;
        _actionListener = null;
        _immediate = null;
        _target = null;
        _disabled = null;
        _disabledStyle = null;
        _disabledStyleClass = null;
    }

    public String getComponentType()
    {
        return UINavigationMenuItem.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return null;
    }

    protected void setProperties(UIComponent component)
    {
        if (getItemValue() == null)
            setItemValue("0"); // itemValue not used
        super.setProperties(component);
        setStringProperty(component, ICON_ATTR, _icon);

        setBooleanProperty(component, SPLIT_ATTR, _split);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setActionProperty(component, _action);
        setActionListenerProperty(component, _actionListener);
        setBooleanProperty(component, JSFAttr.IMMEDIATE_ATTR, _immediate);
        setStringProperty(component,TARGET_ATTR,_target);
        setBooleanProperty(component,DISABLED_ATTR,_disabled);
        setStringProperty(component,DISABLED_STYLE_ATTR,_disabledStyle);
        setStringProperty(component,DISABLED_STYLE_CLASS_ATTR,_disabledStyleClass);
        setStringProperty(component,ACTIVE_ON_VIEW_IDS_ATTR,_activeOnViewIds);
    }

    public void setAction(String action)
    {
        _action = action;
    }

    public void setActionListener(String actionListener)
    {
        _actionListener = actionListener;
    }

    public void setIcon(String icon)
    {
        _icon = icon;
    }

    public void setSplit(String split)
    {
        _split = split;
    }

    public void setImmediate(String immediate)
    {
        _immediate = immediate;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setTarget(String target)
    {
        _target = target;
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

    public void setActiveOnViewIds(String activeOnViewIds)
    {
        _activeOnViewIds = activeOnViewIds;
    }
}
