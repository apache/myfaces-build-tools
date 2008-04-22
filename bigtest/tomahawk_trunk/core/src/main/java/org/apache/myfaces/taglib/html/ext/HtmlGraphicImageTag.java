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

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlGraphicImageTagBase;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlGraphicImage;

/**
 * @author Bruno Aranda
 * @version $Revision$ $Date: 2005-05-11 18:45:06 +0200 (Wed, 11 May 2005) $
 */
public class HtmlGraphicImageTag
        extends HtmlGraphicImageTagBase
{
    public String getComponentType()
    {
        return HtmlGraphicImage.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlGraphicImage.DEFAULT_RENDERER_TYPE;
    }

    private String _align;
    private String _border;
    private String _enabledOnUserRole;
    private String _hspace;
    private String _visibleOnUserRole;
    private String _vspace;

    public void release() {
        super.release();

        _align=null;
        _border=null;
        _enabledOnUserRole=null;
        _hspace=null;
        _visibleOnUserRole=null;
        _vspace=null;
   }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.BORDER_ATTR, _border);
        setStringProperty(component, HTML.HSPACE_ATTR, _hspace);
        setStringProperty(component, HTML.VSPACE_ATTR, _vspace);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setBorder(String border)
    {
        _border = border;
    }

    public void setHspace(String hspace)
    {
        _hspace = hspace;
    }

    public void setVspace(String vspace)
    {
        _vspace = vspace;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

}
