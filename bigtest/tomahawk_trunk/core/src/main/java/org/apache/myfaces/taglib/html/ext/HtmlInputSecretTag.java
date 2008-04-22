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

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlInputSecret;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputSecretTagBase;

/**
 * @author Bruno Aranda 
 * @version $Revision$ $Date: 2005-06-05 13:08:33 +0200 (Sun, 5 Jun 2005) $
 */
public class HtmlInputSecretTag
        extends HtmlInputSecretTagBase
{
    public String getComponentType()
    {
        return HtmlInputSecret.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlInputSecret.DEFAULT_RENDERER_TYPE;
    }

    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;

    private String _displayValueOnly;
    private String _displayValueOnlyStyle;
    private String _displayValueOnlyStyleClass;

    public void release() {
        super.release();

        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _displayValueOnly=null;
        _displayValueOnlyStyle=null;
        _displayValueOnlyStyleClass=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setStringProperty(component, HTML.DATAFLD_ATTR, _datafld);
        setStringProperty(component, HTML.DATASRC_ATTR, _datasrc);
        setStringProperty(component, HTML.DATAFORMATAS_ATTR, _dataformatas);
        setBooleanProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_ATTR, _displayValueOnly);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_ATTR, _displayValueOnlyStyle);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_CLASS_ATTR, _displayValueOnlyStyleClass);
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setDisplayValueOnly(String displayValueOnly)
    {
        _displayValueOnly = displayValueOnly;
    }

    public void setDisplayValueOnlyStyle(String displayValueOnlyStyle)
    {
        _displayValueOnlyStyle = displayValueOnlyStyle;
    }

    public void setDisplayValueOnlyStyleClass(String displayValueOnlyStyleClass)
    {
        _displayValueOnlyStyleClass = displayValueOnlyStyleClass;
    }

    public void setDatafld(String datafld)
    {
        _datafld = datafld;
    }

    public void setDatasrc(String datasrc)
    {
        _datasrc = datasrc;
    }

    public void setDataformatas(String dataformatas)
    {
        _dataformatas = dataformatas;
    }
}
