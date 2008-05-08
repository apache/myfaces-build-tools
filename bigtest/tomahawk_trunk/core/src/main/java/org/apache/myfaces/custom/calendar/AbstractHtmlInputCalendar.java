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

import org.apache.myfaces.component.LocationAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.ext.HtmlInputText;

/**
 * @JSFComponent
 *   name = "t:inputCalendar"
 *   class = "org.apache.myfaces.custom.calendar.HtmlInputCalendar"
 *   superClass = "org.apache.myfaces.custom.calendar.AbstractHtmlInputCalendar"
 *   tagClass = "org.apache.myfaces.custom.calendar.HtmlInputCalendarTag"
 * 
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class AbstractHtmlInputCalendar
        extends HtmlInputText implements UserRoleAware, LocationAware
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlInputCalendar";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Calendar";
    
    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }
    
    /**
     * @JSFProperty
     */
    public abstract String getAlign();
    
    /**
     * @JSFProperty 
     */
    public abstract String getMonthYearRowClass();

    /**
     * @JSFProperty 
     */
    public abstract String getWeekRowClass();

    /**
     * @JSFProperty 
     */
    public abstract String getDayCellClass();

    /**
     * @JSFProperty 
     */
    public abstract String getCurrentDayCellClass();

    /**
     * @JSFProperty
     *   defaultValue = "false" 
     */
    public abstract boolean isPopupLeft();

    /**
     * @JSFProperty
     *   defaultValue = false; 
     */
    public abstract boolean isRenderAsPopup();

    /**
     * @JSFProperty
     *   defaultValue = "true" 
     */
    public abstract boolean isAddResources();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupButtonString();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupButtonStyle();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupButtonStyleClass();

    /**
     * @JSFProperty
     *   defaultValue = "false" 
     */
    public abstract boolean isRenderPopupButtonAsImage();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupDateFormat();
    
    /**
     * @JSFProperty 
     */
    public abstract String getPopupGotoString();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupTodayString();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupTodayDateFormat();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupWeekString();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupScrollLeftMessage();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupScrollRightMessage();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupSelectMonthMessage();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupSelectYearMessage();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupSelectDateMessage();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupTheme();

    /**
     * @JSFProperty 
     */
    public abstract String getPopupButtonImageUrl();

    /**
     * @JSFProperty 
     */
    public abstract String getHelpText();

    
    /**
     * @JSFProperty
     *   defaultValue = "day" 
     */
    public abstract String getPopupSelectMode();
}