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
package org.apache.myfaces.custom.calendar;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.taglib.html.ext.HtmlInputTextTag;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.component.UIComponent;

/**
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlInputCalendarTag
        extends HtmlInputTextTag
{
    private String _accesskey;
    private String _align;
    private String _alt;
    private String _maxlength;
    private String _size;
    //private static final Log log = LogFactory.getLog(HtmlInputCalendarTag.class);

    public String getComponentType()
    {
        return HtmlInputCalendar.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.Calendar";
    }

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in MyFacesTag

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // UIInput attributes
    // --> already implemented in HtmlInputTagBase

    // HtmlCalendar attributes
    private String _monthYearRowClass;
    private String _weekRowClass;
    private String _dayCellClass;
    private String _currentDayCellClass;
    private String _renderAsPopup;
    private String _popupLeft;
    private String _addResources;
    private String _popupDateFormat;
    private String _popupButtonString;
    private String _popupButtonStyle;
    private String _popupButtonStyleClass;
    private String _renderPopupButtonAsImage;
    private String _popupGotoString = null;
    private String _popupTodayString = null;
    private String _popupTodayDateFormat = null;
    private String _popupWeekString = null;
    private String _popupScrollLeftMessage = null;
    private String _popupScrollRightMessage = null;
    private String _popupSelectMonthMessage = null;
    private String _popupSelectYearMessage = null;
    private String _popupSelectDateMessage = null;
    private String _popupTheme = null;
    private String _popupButtonImageUrl = null;
    private String _helpText;
    private String _popupSelectMode;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    public void release() {
        super.release();

        _monthYearRowClass = null;
        _weekRowClass = null;
        _dayCellClass = null;
        _currentDayCellClass = null;
        _renderAsPopup = null;
        _popupLeft = null;
        _addResources = null;
        _popupDateFormat = null;
        _popupButtonString = null;
        _popupButtonStyle = null;
        _popupButtonStyleClass = null;
        _renderPopupButtonAsImage = null;
        _popupGotoString = null;
        _popupTodayString = null;
        _popupTodayDateFormat = null;
        _popupWeekString = null;
        _popupScrollLeftMessage = null;
        _popupScrollRightMessage = null;
        _popupSelectMonthMessage = null;
        _popupSelectYearMessage = null;
        _popupSelectDateMessage = null;
        _enabledOnUserRole = null;
        _visibleOnUserRole = null;
        _popupTheme=null;
        _popupButtonImageUrl=null;
        _helpText = null;
        _popupSelectMode = null;
    }


    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.ALT_ATTR, _alt);
        setIntegerProperty(component, HTML.MAXLENGTH_ATTR, _maxlength);
        setIntegerProperty(component, HTML.SIZE_ATTR, _size);


        setStringProperty(component, "monthYearRowClass", _monthYearRowClass);
        setStringProperty(component, "weekRowClass", _weekRowClass);
        setStringProperty(component, "dayCellClass", _dayCellClass);
        setStringProperty(component, "currentDayCellClass", _currentDayCellClass);
        setBooleanProperty(component,"renderAsPopup",_renderAsPopup);
        setBooleanProperty(component,"popupLeft",_popupLeft);
        setBooleanProperty(component,"addResources",_addResources);
        setStringProperty(component,"popupDateFormat",_popupDateFormat);
        setStringProperty(component,"popupButtonString",_popupButtonString);
        setStringProperty(component,"popupButtonStyle",_popupButtonStyle);
        setStringProperty(component,"popupButtonStyleClass",_popupButtonStyleClass);
        setBooleanProperty(component,"renderPopupButtonAsImage",_renderPopupButtonAsImage);
        setStringProperty(component,"popupGotoString",_popupGotoString);
        setStringProperty(component,"popupTodayString",_popupTodayString);
        setStringProperty(component,"popupTodayDateFormat",_popupTodayDateFormat);
        setStringProperty(component,"popupWeekString",_popupWeekString);
        setStringProperty(component,"popupScrollLeftMessage",_popupScrollLeftMessage);
        setStringProperty(component,"popupScrollRightMessage",_popupScrollRightMessage);
        setStringProperty(component,"popupSelectMonthMessage",_popupSelectMonthMessage);
        setStringProperty(component,"popupSelectYearMessage",_popupSelectYearMessage);
        setStringProperty(component,"popupSelectDateMessage",_popupSelectDateMessage);
        setStringProperty(component,"popupTheme",_popupTheme);
        setStringProperty(component,"popupButtonImageUrl",_popupButtonImageUrl);
        setStringProperty(component,"helpText",_helpText);
        setStringProperty(component,"popupSelectMode",_popupSelectMode);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }

    public void setMonthYearRowClass(String monthYearRowClass)
    {
        _monthYearRowClass = monthYearRowClass;
    }

    public void setWeekRowClass(String weekRowClass)
    {
        _weekRowClass = weekRowClass;
    }

    public void setDayCellClass(String dayCellClass)
    {
        _dayCellClass = dayCellClass;
    }

    public void setCurrentDayCellClass(String currentDayCellClass)
    {
        _currentDayCellClass = currentDayCellClass;
    }

    public void setRenderAsPopup(String renderAsPopup)
    {
        _renderAsPopup = renderAsPopup;
    }

    public void setPopupLeft(String popupLeft)
    {
        _popupLeft = popupLeft;
    }

    public void setAddResources(String addResources)
    {
        _addResources = addResources;
    }

    public void setPopupDateFormat(String popupDateFormat)
    {
        _popupDateFormat = popupDateFormat;
    }

    public void setPopupButtonString(String popupButtonString)
    {
        _popupButtonString = popupButtonString;
    }
    
    public void setPopupButtonStyle(String popupButtonStyle)
    {
        _popupButtonStyle = popupButtonStyle;
    }

    public void setPopupButtonStyleClass(String popupButtonStyleClass)
    {
        _popupButtonStyleClass = popupButtonStyleClass;
    }

    public void setRenderPopupButtonAsImage(String renderPopupButtonAsImage)
    {
        _renderPopupButtonAsImage = renderPopupButtonAsImage;
    }


    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setPopupGotoString(String popupGotoString)
    {
        _popupGotoString = popupGotoString;
    }

    public void setPopupScrollLeftMessage(String popupScrollLeftMessage)
    {
        _popupScrollLeftMessage = popupScrollLeftMessage;
    }

    public void setPopupScrollRightMessage(String popupScrollRightMessage)
    {
        _popupScrollRightMessage = popupScrollRightMessage;
    }

    public void setPopupSelectDateMessage(String popupSelectDateMessage)
    {
        _popupSelectDateMessage = popupSelectDateMessage;
    }

    public void setPopupSelectMonthMessage(String popupSelectMonthMessage)
    {
        _popupSelectMonthMessage = popupSelectMonthMessage;
    }

    public void setPopupSelectYearMessage(String popupSelectYearMessage)
    {
        _popupSelectYearMessage = popupSelectYearMessage;
    }

    public void setPopupTodayString(String popupTodayString)
    {
        _popupTodayString = popupTodayString;
    }

    public void setPopupTodayDateFormat(String popupTodayDateFormat)
    {
        _popupTodayDateFormat = popupTodayDateFormat;
    }

    public void setPopupWeekString(String popupWeekString)
    {
        _popupWeekString = popupWeekString;
    }

    public void setPopupTheme(String popupTheme)
    {
        _popupTheme = popupTheme;
    }

    public void setPopupButtonImageUrl(String popupButtonImageUrl)
    {
        _popupButtonImageUrl = popupButtonImageUrl;
    }

    public void setHelpText(String helpText)
    {
        _helpText = helpText;
    }

    public void setPopupSelectMode(String popupSelectMode)
    {
    	_popupSelectMode = popupSelectMode;
    }

    public void setAccesskey(String accesskey)
    {
        _accesskey = accesskey;
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setAlt(String alt)
    {
        _alt = alt;
    }

    public void setMaxlength(String maxlength)
    {
        _maxlength = maxlength;
    }

    public void setSize(String size)
    {
        _size = size;
    }

}
