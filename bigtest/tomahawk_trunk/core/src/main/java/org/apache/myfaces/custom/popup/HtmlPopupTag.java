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
package org.apache.myfaces.custom.popup;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPopupTag
        extends HtmlComponentTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlDataScrollerTag.class);

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _displayAtDistanceX = null;
    private String _displayAtDistanceY = null;
    private String _closePopupOnExitingElement = null;
    private String _closePopupOnExitingPopup = null;

    public void release() {
        super.release();

        _enabledOnUserRole = null;
        _visibleOnUserRole = null;
        _displayAtDistanceX = null;
        _displayAtDistanceY = null;
        _closePopupOnExitingElement = null;
        _closePopupOnExitingPopup = null;

    }

    public String getComponentType()
    {
        return HtmlPopup.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlPopupRenderer.RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setIntegerProperty(component,"displayAtDistanceX",_displayAtDistanceX);
        setIntegerProperty(component,"displayAtDistanceY",_displayAtDistanceY);
        setBooleanProperty(component,"closePopupOnExitingElement",_closePopupOnExitingElement);
        setBooleanProperty(component,"closePopupOnExitingPopup",_closePopupOnExitingPopup);
    }

    // userrole attributes
    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setDisplayAtDistanceX(String displayAtDistanceX)
    {
        _displayAtDistanceX = displayAtDistanceX;
    }

    public void setDisplayAtDistanceY(String displayAtDistanceY)
    {
        _displayAtDistanceY = displayAtDistanceY;
    }

    public void setClosePopupOnExitingElement(String closePopupOnExitingElement)
    {
        _closePopupOnExitingElement = closePopupOnExitingElement;
    }

    public void setClosePopupOnExitingPopup(String closePopupOnExitingPopup)
    {
        _closePopupOnExitingPopup = closePopupOnExitingPopup;
    }
}
