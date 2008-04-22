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
package org.apache.myfaces.custom.checkbox;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCheckboxTag
        extends UIComponentTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlInputFileUploadTag.class);

    public String getComponentType()
    {
        return HtmlCheckbox.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return null;
    }

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HtmlCheckbox attributes
    private String _for;
    private String _index;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    public void release() {
        super.release();
        _for=null;
        _index=null;
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HtmlCheckbox.FOR_ATTR, _for);
        setIntegerProperty(component, HtmlCheckbox.INDEX_ATTR, _index);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }

    public String getFor()
    {
        return _for;
    }

    public void setFor(String aFor)
    {
        _for = aFor;
    }

    public String getIndex()
    {
        return _index;
    }

    public void setIndex(String index)
    {
        _index = index;
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
