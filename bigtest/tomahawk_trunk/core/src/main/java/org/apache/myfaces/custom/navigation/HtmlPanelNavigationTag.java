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
package org.apache.myfaces.custom.navigation;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.taglib.html.ext.HtmlPanelGroupTag;

import javax.faces.component.UIComponent;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelNavigationTag
    extends HtmlPanelGroupTag
{
    public String getComponentType()
    {
        return HtmlPanelNavigation.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.Navigation";
    }

    // UIComponent attributes --> already implemented in UIComponentBodyTagBase

    // HTML universal attributes --> already implemented in MyFacesTag

    // HTML event handler attributes --> already implemented in MyFacesTag

    // UIPanel attributes --> value attribute already implemented in UIComponentBodyTagBase

    // HtmlPanelNavigation attributes
    private String _itemClass;
    private String _openItemClass;
    private String _activeItemClass;
    private String _separatorClass;
    private String _itemStyle;
    private String _openItemStyle;
    private String _activeItemStyle;
    private String _separatorStyle;

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
    
    public void release() {
        super.release();

        _itemClass=null;
        _openItemClass=null;
        _activeItemClass=null;
        _separatorClass=null;
        _itemStyle=null;
        _openItemStyle=null;
        _activeItemStyle=null;
        _separatorStyle=null;
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

    }

    // User Role support --> already handled by HtmlPanelGroupTag

    
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "itemClass", _itemClass);
        setStringProperty(component, "openItemClass", _openItemClass);
        setStringProperty(component, "activeItemClass", _activeItemClass);
        setStringProperty(component, "separatorClass", _separatorClass);
        setStringProperty(component, "itemStyle", _itemStyle);
        setStringProperty(component, "openItemStyle", _openItemStyle);
        setStringProperty(component, "activeItemStyle", _activeItemStyle);
        setStringProperty(component, "separatorStyle", _separatorStyle);

        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.BGCOLOR_ATTR, _bgcolor);
        setStringProperty(component, HTML.BORDER_ATTR, _border);
        setStringProperty(component, HTML.CELLPADDING_ATTR, _cellpadding);
        setStringProperty(component, HTML.CELLSPACING_ATTR, _cellspacing);
        setStringProperty(component, HTML.DATAFLD_ATTR, _datafld);
        setStringProperty(component, HTML.DATASRC_ATTR, _datasrc);
        setStringProperty(component, HTML.DATAFORMATAS_ATTR, _dataformatas);
        setStringProperty(component, HTML.FRAME_ATTR, _frame);
        setStringProperty(component, HTML.RULES_ATTR, _rules);
        setStringProperty(component, HTML.SUMMARY_ATTR, _summary);
        setStringProperty(component, HTML.WIDTH_ATTR, _width);
    }

    public void setItemClass(String itemClass)
    {
        _itemClass = itemClass;
    }

    public void setOpenItemClass(String openItemClass)
    {
        _openItemClass = openItemClass;
    }

    public void setActiveItemClass(String activeItemClass)
    {
        _activeItemClass = activeItemClass;
    }

    public void setSeparatorClass(String separatorClass)
    {
        _separatorClass = separatorClass;
    }

    public void setItemStyle(String itemStyle)
    {
        _itemStyle = itemStyle;
    }

    public void setOpenItemStyle(String openItemStyle)
    {
        _openItemStyle = openItemStyle;
    }

    public void setActiveItemStyle(String activeItemStyle)
    {
        _activeItemStyle = activeItemStyle;
    }

    public void setSeparatorStyle(String separatorStyle)
    {
        _separatorStyle = separatorStyle;
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
}
