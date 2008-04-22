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
package org.apache.myfaces.custom.tabbedpane;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.taglib.html.ext.HtmlPanelGroupTag;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelTabbedPaneTag
        extends HtmlPanelGroupTag
{
    //private static final Log log = LogFactory.getLog(HtmlPanelTabbedPaneTag.class);

    public String getComponentType()
    {
        return HtmlPanelTabbedPane.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.TabbedPane";
    }


    // HtmlPanelTabbedPane attributes
    private String _selectedIndex;

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
    private String _activeTabStyleClass;
    private String _inactiveTabStyleClass;
    private String _disabledTabStyleClass;
    private String _activeSubStyleClass;
    private String _inactiveSubStyleClass;
    private String _tabContentStyleClass;
    private String _serverSideTabSwitch;
    private String _activePanelTabVar;
    // User Role support --> already handled by HtmlPanelGroupTag


    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setIntegerProperty(component, "selectedIndex", _selectedIndex);

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
        setStringProperty(component, "activeTabStyleClass", _activeTabStyleClass);
        setStringProperty(component, "inactiveTabStyleClass", _inactiveTabStyleClass);
        setStringProperty(component, "disabledTabStyleClass", _disabledTabStyleClass);
        setStringProperty(component, "activeSubStyleClass", _activeSubStyleClass);
        setStringProperty(component, "inactiveSubStyleClass", _inactiveSubStyleClass);
        setStringProperty(component, "tabContentStyleClass", _tabContentStyleClass);
        setBooleanProperty(component, "serverSideTabSwitch", _serverSideTabSwitch);
        setStringProperty(component, "activePanelTabVar", _activePanelTabVar);
    }


    public void release()
    {
        super.release();
        _selectedIndex=null;
        _align = null;
        _border = null;
        _cellpadding = null;
        _cellspacing = null;
        _datafld = null;
        _datasrc = null;
        _dataformatas = null;
        _frame = null;
        _rules = null;
        _summary = null;
        _width = null;
        _activeTabStyleClass = null;
        _inactiveTabStyleClass = null;
        _disabledTabStyleClass = null;
        _activeSubStyleClass = null;
        _inactiveSubStyleClass = null;
        _tabContentStyleClass = null;
        _serverSideTabSwitch = null;
        _activePanelTabVar = null;
    }

    public void setServerSideTabSwitch(String serverSideTabSwitch)
    {
        _serverSideTabSwitch = serverSideTabSwitch;
    }

    public void setSelectedIndex(String selectedIndex)
    {
        _selectedIndex = selectedIndex;
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


    public void setActiveTabStyleClass(String activeTabStyleClass)
    {
        _activeTabStyleClass = activeTabStyleClass;
    }


    public void setInactiveTabStyleClass(String inactiveTabStyleClass)
    {
        _inactiveTabStyleClass = inactiveTabStyleClass;
    }


    public void setActiveSubStyleClass(String activeSubStyleClass)
    {
        _activeSubStyleClass = activeSubStyleClass;
    }


    public void setInactiveSubStyleClass(String inactiveSubStyleClass)
    {
        _inactiveSubStyleClass = inactiveSubStyleClass;
    }

    public void setTabContentStyleClass(String tabContentStyleClass)
    {
        _tabContentStyleClass = tabContentStyleClass;
    }

    public void setDisabledTabStyleClass(String disabledTabStyleClass)
    {
        _disabledTabStyleClass = disabledTabStyleClass;
    }

    public void setActivePanelTabVar(String activePanelTabVar) {
        _activePanelTabVar = activePanelTabVar;
    }
}
