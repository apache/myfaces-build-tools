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
package org.apache.myfaces.custom.datalist;

import javax.faces.component.UIComponent;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentBodyTagBase;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlDataListTag
        extends HtmlComponentBodyTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlDataListTag.class);

    public String getComponentType()
    {
        return HtmlDataList.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.List";
    }

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // UIData attributes
    private String _rows;
    private String _var;
    private String _first;

    // HtmlDataList attributes
    private String _layout;
    private String _rowIndexVar;
    private String _rowCountVar;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    //CSS class for List Items
    private String _itemStyleClass;

    public void release() {
        super.release();
        _rows=null;
        _var=null;
        _first=null;
        _layout=null;
        _rowIndexVar=null;
        _rowCountVar=null;
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _itemStyleClass=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setIntegerProperty(component, JSFAttr.ROWS_ATTR, _rows);
        setStringProperty(component, JSFAttr.VAR_ATTR, _var);
        setIntegerProperty(component, JSFAttr.FIRST_ATTR, _first);

        setStringProperty(component, JSFAttr.LAYOUT_ATTR, _layout);
        setStringProperty(component, "rowIndexVar", _rowIndexVar);
        setStringProperty(component, "rowCountVar", _rowCountVar);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);

        setStringProperty(component, "itemStyleClass", _itemStyleClass);
    }

    public void setRows(String rows)
    {
        _rows = rows;
    }

    public void setVar(String var)
    {
        _var = var;
    }

    public void setFirst(String first)
    {
        _first = first;
    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }

    public void setRowIndexVar(String rowIndexVar)
    {
        _rowIndexVar = rowIndexVar;
    }

    public void setRowCountVar(String rowCountVar)
    {
        _rowCountVar = rowCountVar;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setItemStyleClass(String itemStyleClass) {
        _itemStyleClass = itemStyleClass;
    }

    public String getItemStyleClass() {
        return _itemStyleClass;
    }

}
