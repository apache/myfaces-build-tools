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
package org.apache.myfaces.custom.navmenu.jscookmenu;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;
import org.apache.myfaces.component.UserRoleAware;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlJSCookMenuTag
        extends UIComponentTagBase
{
    private static final String COMPONENT_TYPE = "org.apache.myfaces.JSCookMenu".intern();
    private static final String RENDERER_TYPE = "org.apache.myfaces.JSCookMenu".intern();

    private static final String LAYOUT_ATTR = "layout";
    private static final String THEME_ATTR  = "theme";

    private String _layout;
    private String _theme;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _immediate;

    public void release() {
        super.release();
        _layout=null;
        _theme=null;
      	_enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _immediate = null;
    }
    
    public String getComponentType()
    {
        return COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, LAYOUT_ATTR, _layout);
        setStringProperty(component, THEME_ATTR, _theme);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        
        setBooleanProperty(component, JSFAttr.IMMEDIATE_ATTR, _immediate);
    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }

    public void setTheme(String theme)
    {
        _theme = theme;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }
    
    public void setImmediate(String immediate)
    {
        _immediate = immediate;
    }
}
