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
package org.apache.myfaces.custom.layout;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentBodyTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelLayoutTag
        extends HtmlComponentBodyTagBase
{
    public String getComponentType()
    {
        return HtmlPanelLayout.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.Layout";
    }

    // UIComponent attributes --> already implemented in UIComponentBodyTagBase

    // HTML universal attributes --> already implemented in MyFacesTag

    // HTML event handler attributes --> already implemented in MyFacesTag

    // UIPanel attributes --> value attribute already implemented in UIComponentBodyTagBase

    // HtmlPanelLayout attributes
    private String _layout;
    private String _headerClass;
    private String _navigationClass;
    private String _bodyClass;
    private String _footerClass;
    private String _headerStyle;
    private String _navigationStyle;
    private String _bodyStyle;
    private String _footerStyle;

    // HTML table attributes
    private String _align;
    private String _bgcolor;
    private String _border;
    private String _cellpadding;
    private String _cellspacing;
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;
    private String _frame;
    private String _rules;
    private String _summary;
    private String _width;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;


    public void release() {
        super.release();
        _layout=null;
        _headerClass=null;
        _navigationClass=null;
        _bodyClass=null;
        _footerClass=null;
        _headerStyle=null;
        _navigationStyle=null;
        _bodyStyle=null;
        _footerStyle=null;
        _align=null;
        _bgcolor=null;
        _border=null;
        _cellpadding=null;
        _cellspacing=null;
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _frame=null;
        _rules=null;
        _summary=null;
        _width=null;
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
   }
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "layout", _layout);
        setStringProperty(component, "headerClass", _headerClass);
        setStringProperty(component, "navigationClass", _navigationClass);
        setStringProperty(component, "bodyClass", _bodyClass);
        setStringProperty(component, "footerClass", _footerClass);
        setStringProperty(component, "headerStyle", _headerStyle);
        setStringProperty(component, "navigationStyle", _navigationStyle);
        setStringProperty(component, "bodyStyle", _bodyStyle);
        setStringProperty(component, "footerStyle", _footerStyle);

        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.BGCOLOR_ATTR, _bgcolor);
        setIntegerProperty(component, HTML.BORDER_ATTR, _border);
        setStringProperty(component, HTML.CELLPADDING_ATTR, _cellpadding);
        setStringProperty(component, HTML.CELLSPACING_ATTR, _cellspacing);
        setStringProperty(component, HTML.DATAFLD_ATTR, _datafld);
        setStringProperty(component, HTML.DATASRC_ATTR, _datasrc);
        setStringProperty(component, HTML.DATAFORMATAS_ATTR, _dataformatas);
        setStringProperty(component, HTML.FRAME_ATTR, _frame);
        setStringProperty(component, HTML.RULES_ATTR, _rules);
        setStringProperty(component, HTML.SUMMARY_ATTR, _summary);
        setStringProperty(component, HTML.WIDTH_ATTR, _width);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }

    public void setHeaderClass(String headerClass)
    {
        _headerClass = headerClass;
    }

    public void setNavigationClass(String navigationClass)
    {
        _navigationClass = navigationClass;
    }

    public void setBodyClass(String bodyClass)
    {
        _bodyClass = bodyClass;
    }

    public void setFooterClass(String footerClass)
    {
        _footerClass = footerClass;
    }

    public void setHeaderStyle(String headerStyle)
    {
        _headerStyle = headerStyle;
    }

    public void setNavigationStyle(String navigationStyle)
    {
        _navigationStyle = navigationStyle;
    }

    public void setBodyStyle(String bodyStyle)
    {
        _bodyStyle = bodyStyle;
    }

    public void setFooterStyle(String footerStyle)
    {
        _footerStyle = footerStyle;
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setBgcolor(String bgcolor)
    {
        _bgcolor = bgcolor;
    }

    public void setBorder(String border)
    {
        _border = border;
    }

    public void setCellpadding(String cellpadding)
    {
        _cellpadding = cellpadding;
    }

    public void setCellspacing(String cellspacing)
    {
        _cellspacing = cellspacing;
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

    public void setFrame(String frame)
    {
        _frame = frame;
    }

    public void setRules(String rules)
    {
        _rules = rules;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public void setWidth(String width)
    {
        _width = width;
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
