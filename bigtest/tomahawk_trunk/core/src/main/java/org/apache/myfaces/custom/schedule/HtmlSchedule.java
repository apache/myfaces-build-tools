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

package org.apache.myfaces.custom.schedule;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.custom.schedule.util.ScheduleUtil;

/**
 * This class holds all properties specific to the HTML version of the Schedule component.
 *
 * @JSFComponent
 *   name = "t:schedule"
 *   tagClass = "org.apache.myfaces.custom.schedule.ScheduleTag"
 *
 * @author Bruno Aranda (latest modification by $Author: jlust $)
 * @author Jurgen Lust
 * 
 * @version $Revision: 392301 $
 */
public class HtmlSchedule extends UISchedule implements UserRoleAware,
        Serializable
{

    private static final long serialVersionUID = 5859593107442371656L;
    
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Schedule";
    public static final String RENDERER_TYPE = "org.apache.myfaces.Schedule";

    //UserRoleAware properties
    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    //CSS style classes
    private String _backgroundClass;
    private String _columnClass;
    private String _contentClass;
    private String _dateClass;
    private String _dayClass;
    private String _entryClass;
    private Object _entryRenderer;
    private String _evenClass;
    private String _foregroundClass;
    private String _freeClass;
    private String _gutterClass;
    private String _headerClass;
    private String _holidayClass;
    private String _hoursClass;
    private String _inactiveDayClass;
    private String _minutesClass;
    private String _monthClass;
    private String _selectedClass;
    private String _selectedEntryClass;
    private String _subtitleClass;
    private String _textClass;
    private String _titleClass;
    private String _unevenClass;
    private String _weekClass;

    /**
     * @return the _enabledOnUserRole
     */
    public String getEnabledOnUserRole()
    {
        return ScheduleUtil.getStringProperty(this, _enabledOnUserRole, "enabledOnUserRole", DEFAULT_ENABLED_ON_USER_ROLE);
    }

    /**
     * @return the _visibleOnUserRole
     */
    public String getVisibleOnUserRole()
    {
        return ScheduleUtil.getStringProperty(this, _visibleOnUserRole, "visibleOnUserRole", DEFAULT_VISIBLE_ON_USER_ROLE);
    }

    /**
     * @see org.apache.myfaces.custom.schedule.UISchedule#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        _enabledOnUserRole = (String)values[1];
        _visibleOnUserRole = (String)values[2];
        _backgroundClass = (String)values[3];
        _columnClass = (String)values[4];
        _contentClass = (String)values[5];
        _dateClass = (String)values[6];
        _dayClass = (String)values[7];
        _entryClass = (String)values[8];
        _entryRenderer = (Object)values[9];
        _evenClass = (String)values[10];
        _foregroundClass = (String)values[11];
        _freeClass = (String)values[12];
        _gutterClass = (String)values[13];
        _headerClass = (String)values[14];
        _holidayClass = (String)values[15];
        _hoursClass = (String)values[16];
        _inactiveDayClass = (String)values[17];
        _minutesClass = (String)values[18];
        _monthClass = (String)values[19];
        _selectedClass = (String)values[20];
        _selectedEntryClass = (String)values[21];
        _subtitleClass = (String)values[22];
        _textClass = (String)values[23];
        _titleClass = (String)values[24];
        _unevenClass = (String)values[25];
        _weekClass = (String)values[26];
    }

    /**
     * @see org.apache.myfaces.custom.schedule.UISchedule#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[27];
        values[0] = super.saveState(context);
        values[1] = _enabledOnUserRole;
        values[2] = _visibleOnUserRole;
        values[3] = _backgroundClass;
        values[4] = _columnClass;
        values[5] = _contentClass;
        values[6] = _dateClass;
        values[7] = _dayClass;
        values[8] = _entryClass;
        values[9] = _entryRenderer;
        values[10] = _evenClass;
        values[11] = _foregroundClass;
        values[12] = _freeClass;
        values[13] = _gutterClass;
        values[14] = _headerClass;
        values[15] = _holidayClass;
        values[16] = _hoursClass;
        values[17] = _inactiveDayClass;
        values[18] = _minutesClass;
        values[19] = _monthClass;
        values[20] = _selectedClass;
        values[21] = _selectedEntryClass;
        values[22] = _subtitleClass;
        values[23] = _textClass;
        values[24] = _titleClass;
        values[25] = _unevenClass;
        values[26] = _weekClass;
        
        return values;
    }

    /**
     * @param enabledOnUserRole the _enabledOnUserRole to set
     */
    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    /**
     * @param onUserRole the _visibleOnUserRole to set
     */
    public void setVisibleOnUserRole(String onUserRole)
    {
        _visibleOnUserRole = onUserRole;
    }

    /**
     * @return the backgroundClass
     */
    public String getBackgroundClass()
    {
        return ScheduleUtil.getStringProperty(this, _backgroundClass, "backgroundClass", null);
    }

    /**
     * @return the columnClass
     */
    public String getColumnClass()
    {
        return ScheduleUtil.getStringProperty(this, _columnClass, "columnClass", null);
    }

    /**
     * @return the contentClass
     */
    public String getContentClass()
    {
        return ScheduleUtil.getStringProperty(this, _contentClass, "contentClass", null);
    }

    /**
     * @return the dateClass
     */
    public String getDateClass()
    {
        return ScheduleUtil.getStringProperty(this, _dateClass, "dateClass", null);
    }

    /**
     * @return the dayClass
     */
    public String getDayClass()
    {
        return ScheduleUtil.getStringProperty(this, _dayClass, "dayClass", null);
    }

    /**
     * @return the entryClass
     */
    public String getEntryClass()
    {
        return ScheduleUtil.getStringProperty(this, _entryClass, "entryClass", null);
    }

    /**
     * @return the entryRenderer
     */
    public Object getEntryRenderer()
    {
        return ScheduleUtil.getObjectProperty(this, _entryRenderer, "entryRenderer", null);
    }

    /**
     * @return the evenClass
     */
    public String getEvenClass()
    {
        return ScheduleUtil.getStringProperty(this, _evenClass, "evenClass", null);
    }

    /**
     * @return the foregroundClass
     */
    public String getForegroundClass()
    {
        return ScheduleUtil.getStringProperty(this, _foregroundClass, "foregroundClass", null);
    }

    /**
     * @return the freeClass
     */
    public String getFreeClass()
    {
        return ScheduleUtil.getStringProperty(this, _freeClass, "freeClass", null);
    }

    /**
     * @return the gutterClass
     */
    public String getGutterClass()
    {
        return ScheduleUtil.getStringProperty(this, _gutterClass, "gutterClass", null);
    }

    /**
     * @return the headerClass
     */
    public String getHeaderClass()
    {
        return ScheduleUtil.getStringProperty(this, _headerClass, "headerClass", null);
    }

    /**
     * @return the holidayClass
     */
    public String getHolidayClass()
    {
        return ScheduleUtil.getStringProperty(this, _holidayClass, "holidayClass", null);
    }

    /**
     * @return the hoursClass
     */
    public String getHoursClass()
    {
        return ScheduleUtil.getStringProperty(this, _hoursClass, "hoursClass", null);
    }

    /**
     * @return the inactiveDayClass
     */
    public String getInactiveDayClass()
    {
        return ScheduleUtil.getStringProperty(this, _inactiveDayClass, "inactiveDayClass", null);
    }

    /**
     * @return the minutesClass
     */
    public String getMinutesClass()
    {
        return ScheduleUtil.getStringProperty(this, _minutesClass, "minutesClass", null);
    }

    /**
     * @return the monthClass
     */
    public String getMonthClass()
    {
        return ScheduleUtil.getStringProperty(this, _monthClass, "monthClass", null);
    }

    /**
     * @return the selectedClass
     */
    public String getSelectedClass()
    {
        return ScheduleUtil.getStringProperty(this, _selectedClass, "selectedClass", null);
    }

    /**
     * @return the selectedEntryClass
     */
    public String getSelectedEntryClass()
    {
        return ScheduleUtil.getStringProperty(this, _selectedEntryClass, "selectedEntryClass", null);
    }

    /**
     * @return the subtitleClass
     */
    public String getSubtitleClass()
    {
        return ScheduleUtil.getStringProperty(this, _subtitleClass, "subtitleClass", null);
    }

    /**
     * @return the textClass
     */
    public String getTextClass()
    {
        return ScheduleUtil.getStringProperty(this, _textClass, "textClass", null);
    }

    /**
     * @return the titleClass
     */
    public String getTitleClass()
    {
        return ScheduleUtil.getStringProperty(this, _titleClass, "titleClass", null);
    }

    /**
     * @return the unevenClass
     */
    public String getUnevenClass()
    {
        return ScheduleUtil.getStringProperty(this, _unevenClass, "unevenClass", null);
    }

    /**
     * @return the weekClass
     */
    public String getWeekClass()
    {
        return ScheduleUtil.getStringProperty(this, _weekClass, "weekClass", null);
    }

    /**
     * @param backgroundClass the backgroundClass to set
     */
    public void setBackgroundClass(String backgroundClass)
    {
        this._backgroundClass = backgroundClass;
    }

    /**
     * @param columnClass the columnClass to set
     */
    public void setColumnClass(String columnClass)
    {
        this._columnClass = columnClass;
    }

    /**
     * @param contentClass the contentClass to set
     */
    public void setContentClass(String contentClass)
    {
        this._contentClass = contentClass;
    }

    /**
     * @param dateClass the dateClass to set
     */
    public void setDateClass(String dateClass)
    {
        this._dateClass = dateClass;
    }

    /**
     * @param dayClass the dayClass to set
     */
    public void setDayClass(String dayClass)
    {
        this._dayClass = dayClass;
    }

    /**
     * @param entryClass the entryClass to set
     */
    public void setEntryClass(String entryClass)
    {
        this._entryClass = entryClass;
    }

    /**
     * @param entryRenderer the entryRenderer to set
     */
    public void setEntryRenderer(Object entryRenderer)
    {
        this._entryRenderer = entryRenderer;
    }

    /**
     * @param evenClass the evenClass to set
     */
    public void setEvenClass(String evenClass)
    {
        this._evenClass = evenClass;
    }

    /**
     * @param foregroundClass the foregroundClass to set
     */
    public void setForegroundClass(String foregroundClass)
    {
        this._foregroundClass = foregroundClass;
    }

    /**
     * @param freeClass the freeClass to set
     */
    public void setFreeClass(String freeClass)
    {
        this._freeClass = freeClass;
    }

    /**
     * @param gutterClass the gutterClass to set
     */
    public void setGutterClass(String gutterClass)
    {
        this._gutterClass = gutterClass;
    }

    /**
     * @param headerClass the headerClass to set
     */
    public void setHeaderClass(String headerClass)
    {
        this._headerClass = headerClass;
    }

    /**
     * @param holidayClass the holidayClass to set
     */
    public void setHolidayClass(String holidayClass)
    {
        this._holidayClass = holidayClass;
    }

    /**
     * @param hoursClass the hoursClass to set
     */
    public void setHoursClass(String hoursClass)
    {
        this._hoursClass = hoursClass;
    }

    /**
     * @param inactiveDayClass the inactiveDayClass to set
     */
    public void setInactiveDayClass(String inactiveDayClass)
    {
        this._inactiveDayClass = inactiveDayClass;
    }

    /**
     * @param minutesClass the minutesClass to set
     */
    public void setMinutesClass(String minutesClass)
    {
        this._minutesClass = minutesClass;
    }

    /**
     * @param monthClass the monthClass to set
     */
    public void setMonthClass(String monthClass)
    {
        this._monthClass = monthClass;
    }

    /**
     * @param selectedClass the selectedClass to set
     */
    public void setSelectedClass(String selectedClass)
    {
        this._selectedClass = selectedClass;
    }

    /**
     * @param selectedEntryClass the selectedEntryClass to set
     */
    public void setSelectedEntryClass(String selectedEntryClass)
    {
        this._selectedEntryClass = selectedEntryClass;
    }

    /**
     * @param subtitleClass the subtitleClass to set
     */
    public void setSubtitleClass(String subtitleClass)
    {
        this._subtitleClass = subtitleClass;
    }

    /**
     * @param textClass the textClass to set
     */
    public void setTextClass(String textClass)
    {
        this._textClass = textClass;
    }

    /**
     * @param titleClass the titleClass to set
     */
    public void setTitleClass(String titleClass)
    {
        this._titleClass = titleClass;
    }

    /**
     * @param unevenClass the unevenClass to set
     */
    public void setUnevenClass(String unevenClass)
    {
        this._unevenClass = unevenClass;
    }

    /**
     * @param weekClass the weekClass to set
     */
    public void setWeekClass(String weekClass)
    {
        this._weekClass = weekClass;
    }

}
//The End
