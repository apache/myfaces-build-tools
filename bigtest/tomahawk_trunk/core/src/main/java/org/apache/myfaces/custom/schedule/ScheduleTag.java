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

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentTagBase;

/**
 * @author Jurgen Lust
 * @version $Revision$
 */
public class ScheduleTag extends HtmlComponentTagBase
{
    //UIScheduleBase properties
    private String _compactMonthRowHeight;
    private String _compactWeekRowHeight;
    private String _detailedRowHeight;
    private String _expandToFitEntries;
    private String _headerDateFormat;
    private String _immediate;
    private String _readonly;
    private String _renderZeroLengthEntries;
    private String _theme;
    private String _tooltip;
    private String _visibleEndHour;
    private String _visibleStartHour;
    private String _workingEndHour;
    private String _workingStartHour;
    private String _submitOnClick;
    private String _hourNotation;
    private String _compactMonthDayOfWeekDateFormat;
    //UISchedule properties
    private String _action;
    private String _actionListener;
    private String _mouseListener;
    //HtmlSchedule properties
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _backgroundClass;
    private String _columnClass;
    private String _contentClass;
    private String _dateClass;
    private String _dayClass;
    private String _entryClass;
    private String _entryRenderer;
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
	private String _splitWeekend;
    
    public String getSplitWeekend()
	{
		return _splitWeekend;
	}

    /**
     * @return the action
     */
    public String getAction()
    {
        return _action;
    }

    /**
     * @return the actionListener
     */
    public String getActionListener()
    {
        return _actionListener;
    }

    /**
     * @return the compactMonthRowHeight
     */
    public String getCompactMonthRowHeight()
    {
        return _compactMonthRowHeight;
    }

    /**
     * @return the compactWeekRowHeight
     */
    public String getCompactWeekRowHeight()
    {
        return _compactWeekRowHeight;
    }

    public String getComponentType()
    {
        return HtmlSchedule.COMPONENT_TYPE;
    }

    /**
     * @return the detailedRowHeight
     */
    public String getDetailedRowHeight()
    {
        return _detailedRowHeight;
    }

    /**
     * @return the enabledOnUserRole
     */
    public String getEnabledOnUserRole()
    {
        return _enabledOnUserRole;
    }

    /**
     * @return the expandToFitEntries
     */
    public String getExpandToFitEntries()
    {
        return _expandToFitEntries;
    }

    /**
     * @return the headerDateFormat
     */
    public String getHeaderDateFormat()
    {
        return _headerDateFormat;
    }

    /**
     * @return the immediate
     */
    public String getImmediate()
    {
        return _immediate;
    }

    /**
     * @return the mouseListener
     */
    public String getMouseListener()
    {
        return _mouseListener;
    }

    /**
     * @return the readonly
     */
    public String getReadonly()
    {
        return _readonly;
    }

    public String getRendererType()
    {
        return HtmlSchedule.RENDERER_TYPE;
    }

    /**
     * @return the renderZeroLengthEntries
     */
    public String getRenderZeroLengthEntries()
    {
        return _renderZeroLengthEntries;
    }

    /**
     * @return the submitOnClick
     */
    public String getSubmitOnClick()
    {
        return _submitOnClick;
    }

    /**
     * @return the theme
     */
    public String getTheme()
    {
        return _theme;
    }

    /**
     * @return the tooltip
     */
    public String getTooltip()
    {
        return _tooltip;
    }

    /**
     * @return the visibleEndHour
     */
    public String getVisibleEndHour()
    {
        return _visibleEndHour;
    }

    /**
     * @return the visibleOnUserRole
     */
    public String getVisibleOnUserRole()
    {
        return _visibleOnUserRole;
    }

    /**
     * @return the visibleStartHour
     */
    public String getVisibleStartHour()
    {
        return _visibleStartHour;
    }

    /**
     * @return the workingEndHour
     */
    public String getWorkingEndHour()
    {
        return _workingEndHour;
    }

    /**
     * @return the workingStartHour
     */
    public String getWorkingStartHour()
    {
        return _workingStartHour;
    }

    /**
     * @return the hourNotation
     */
    public String getHourNotation()
    {
		return _hourNotation;
	}
    
    /**
     * @return the compactMonthDayOfWeekDateFormat
     */
    public String getcompactMonthDayOfWeekDateFormat()
    {
		return _compactMonthDayOfWeekDateFormat;
	}
    
    public void release()
    {
        super.release();
        //UIScheduleBase properties
        _compactMonthRowHeight = null;
        _compactWeekRowHeight = null;
        _detailedRowHeight = null;
        _expandToFitEntries = null;
        _headerDateFormat = null;
        _immediate = null;
        _readonly = null;
        _renderZeroLengthEntries = null;
        _theme = null;
        _tooltip = null;
        _visibleEndHour = null;
        _visibleStartHour = null;
        _workingEndHour = null;
        _workingStartHour = null;
        _submitOnClick = null;
        _hourNotation = null;
        _compactMonthDayOfWeekDateFormat = null;
        //UISchedule properties
        _action = null;
        _actionListener = null;
        _mouseListener = null;
        //HtmlSchedule properties
        _enabledOnUserRole = null;
        _visibleOnUserRole = null;
        _backgroundClass = null;
        _columnClass = null;
        _contentClass = null;
        _dateClass = null;
        _dayClass = null;
        _entryClass = null;
        _entryRenderer = null;
        _evenClass = null;
        _foregroundClass = null;
        _freeClass = null;
        _gutterClass = null;
        _headerClass = null;
        _holidayClass = null;
        _hoursClass = null;
        _inactiveDayClass = null;
        _minutesClass = null;
        _monthClass = null;
        _selectedClass = null;
        _selectedEntryClass = null;
        _subtitleClass = null;
        _textClass = null;
        _titleClass = null;
        _unevenClass = null;
        _weekClass = null;
		_splitWeekend = null;
        
    }

	public void setSplitWeekend(String splitWeekend)
	{
		this._splitWeekend = splitWeekend;
	}
	
    /**
     * @param action the action to set
     */
    public void setAction(String action)
    {
        this._action = action;
    }

    /**
     * @param actionListener the actionListener to set
     */
    public void setActionListener(String actionListener)
    {
        this._actionListener = actionListener;
    }

    /**
     * @param compactMonthRowHeight the compactMonthRowHeight to set
     */
    public void setCompactMonthRowHeight(String compactMonthRowHeight)
    {
        this._compactMonthRowHeight = compactMonthRowHeight;
    }

    /**
     * @param compactWeekRowHeight the compactWeekRowHeight to set
     */
    public void setCompactWeekRowHeight(String compactWeekRowHeight)
    {
        this._compactWeekRowHeight = compactWeekRowHeight;
    }

    /**
     * @param detailedRowHeight the detailedRowHeight to set
     */
    public void setDetailedRowHeight(String detailedRowHeight)
    {
        this._detailedRowHeight = detailedRowHeight;
    }

    /**
     * @param enabledOnUserRole the enabledOnUserRole to set
     */
    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        this._enabledOnUserRole = enabledOnUserRole;
    }

    /**
     * @param expandToFitEntries the expandToFitEntries to set
     */
    public void setExpandToFitEntries(String expandToFitEntries)
    {
        this._expandToFitEntries = expandToFitEntries;
    }

    /**
     * @param headerDateFormat the headerDateFormat to set
     */
    public void setHeaderDateFormat(String headerDateFormat)
    {
        this._headerDateFormat = headerDateFormat;
    }

    /**
     * @param immediate the immediate to set
     */
    public void setImmediate(String immediate)
    {
        this._immediate = immediate;
    }

    /**
     * @param mouseListener the mouseListener to set
     */
    public void setMouseListener(String mouseListener)
    {
        this._mouseListener = mouseListener;
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties
     */
     protected void setProperties(UIComponent component) {
          super.setProperties(component);
          HtmlSchedule schedule = (HtmlSchedule) component;
          FacesContext context = FacesContext.getCurrentInstance();
          Application app = context.getApplication();
          //UIScheduleBase properties
		  setBooleanProperty(component, "splitWeekend", _splitWeekend);
          setIntegerProperty(component, "compactMonthRowHeight", _compactMonthRowHeight);
          setIntegerProperty(component, "compactWeekRowHeight", _compactWeekRowHeight);
          setIntegerProperty(component, "detailedRowHeight", _detailedRowHeight);
          setBooleanProperty(component, "expandToFitEntries", _expandToFitEntries);
          setStringProperty(component, "headerDateFormat", _headerDateFormat);
          setBooleanProperty(component, "immediate", _immediate);
          setBooleanProperty(component, "readonly", _readonly);
          setBooleanProperty(component, "renderZeroLengthEntries", _renderZeroLengthEntries);
          setStringProperty(component, "theme", _theme);
          setBooleanProperty(component, "tooltip", _tooltip);
          setIntegerProperty(component, "visibleEndHour", _visibleEndHour);
          setIntegerProperty(component, "visibleStartHour", _visibleStartHour);
          setIntegerProperty(component, "workingEndHour", _workingEndHour);
          setIntegerProperty(component, "workingStartHour", _workingStartHour);
          setBooleanProperty(component, "submitOnClick", _submitOnClick);
          setStringProperty(component, "hourNotation", _hourNotation);
          setStringProperty(component, "compactMonthDayOfWeekDateFormat", _compactMonthDayOfWeekDateFormat);
          //UISchedule properties
          setActionProperty(component, _action);
          setActionListenerProperty(component, _actionListener);
          if (_mouseListener != null)
          {
              if (isValueReference(_mouseListener))
              {
                  MethodBinding mouseListenerBinding = app
                          .createMethodBinding(_mouseListener,
                                  new Class[] { ScheduleMouseEvent.class });
                  schedule.setMouseListener(mouseListenerBinding);
              }
              else
              {
                  throw new IllegalArgumentException(
                          "mouseListener property must be a method-binding expression.");
              }
          }
          //HtmlSchedule properties
          setStringProperty(component, "enabledOnUserRole", _enabledOnUserRole);
          setStringProperty(component, "visibleOnUserRole", _visibleOnUserRole);
          setStringProperty(component, "backgroundClass", _backgroundClass);
          setStringProperty(component, "columnClass", _columnClass);
          setStringProperty(component, "contentClass", _contentClass);
          setStringProperty(component, "dateClass", _dateClass);
          setStringProperty(component, "dayClass", _dayClass);
          setStringProperty(component, "entryClass", _entryClass);
          if (_entryRenderer != null)
          {
              if (isValueReference(_entryRenderer))
              {
                  setValueBinding(component, "entryRenderer", _entryRenderer);
              }
          }
          setStringProperty(component, "evenClass", _evenClass);
          setStringProperty(component, "foregroundClass", _foregroundClass);
          setStringProperty(component, "freeClass", _freeClass);
          setStringProperty(component, "gutterClass", _gutterClass);
          setStringProperty(component, "headerClass", _headerClass);
          setStringProperty(component, "holidayClass", _holidayClass);
          setStringProperty(component, "hoursClass", _hoursClass);
          setStringProperty(component, "inactiveDayClass", _inactiveDayClass);
          setStringProperty(component, "minutesClass", _minutesClass);
          setStringProperty(component, "monthClass", _monthClass);
          setStringProperty(component, "selectedClass", _selectedClass);
          setStringProperty(component, "selectedEntryClass", _selectedEntryClass);
          setStringProperty(component, "subtitleClass", _subtitleClass);
          setStringProperty(component, "textClass", _textClass);
          setStringProperty(component, "titleClass", _titleClass);
          setStringProperty(component, "unevenClass", _unevenClass);
          setStringProperty(component, "weekClass", _weekClass);
     }

    /**
     * @param readonly the readonly to set
     */
    public void setReadonly(String readonly)
    {
        this._readonly = readonly;
    }

    /**
     * @param renderZeroLengthEntries the renderZeroLengthEntries to set
     */
    public void setRenderZeroLengthEntries(String renderZeroLengthEntries)
    {
        this._renderZeroLengthEntries = renderZeroLengthEntries;
    }

    /**
     * @param submitOnClick the submitOnClick to set
     */
    public void setSubmitOnClick(String submitOnClick)
    {
        this._submitOnClick = submitOnClick;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme)
    {
        this._theme = theme;
    }

    /**
     * @param tooltip the tooltip to set
     */
    public void setTooltip(String tooltip)
    {
        this._tooltip = tooltip;
    }

    /**
     * @param visibleEndHour the visibleEndHour to set
     */
    public void setVisibleEndHour(String visibleEndHour)
    {
        this._visibleEndHour = visibleEndHour;
    }

    /**
     * @param visibleOnUserRole the visibleOnUserRole to set
     */
    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        this._visibleOnUserRole = visibleOnUserRole;
    }

    /**
     * @param visibleStartHour the visibleStartHour to set
     */
    public void setVisibleStartHour(String visibleStartHour)
    {
        this._visibleStartHour = visibleStartHour;
    }

    /**
     * @param workingEndHour the workingEndHour to set
     */
    public void setWorkingEndHour(String workingEndHour)
    {
        this._workingEndHour = workingEndHour;
    }

    /**
     * @param workingStartHour the workingStartHour to set
     */
    public void setWorkingStartHour(String workingStartHour)
    {
        this._workingStartHour = workingStartHour;
    }

    /**
     * @param hourNotation the hourNotation to set
     */
    public void setHourNotation(String hourNotation)
    {
        this._hourNotation = hourNotation;
    }

    /**
     * @param hourNotation the monthDayOfWeekDateFormat to set
     */
    public void setCompactMonthDayOfWeekDateFormat(String monthDayOfWeekDateFormat)
    {
		_compactMonthDayOfWeekDateFormat = monthDayOfWeekDateFormat;
	}
    
    /**
     * @return the backgroundClass
     */
    public String getBackgroundClass()
    {
        return _backgroundClass;
    }

    /**
     * @return the columnClass
     */
    public String getColumnClass()
    {
        return _columnClass;
    }

    /**
     * @return the contentClass
     */
    public String getContentClass()
    {
        return _contentClass;
    }

    /**
     * @return the dateClass
     */
    public String getDateClass()
    {
        return _dateClass;
    }

    /**
     * @return the dayClass
     */
    public String getDayClass()
    {
        return _dayClass;
    }

    /**
     * @return the entryClass
     */
    public String getEntryClass()
    {
        return _entryClass;
    }

    /**
     * @return the entryRenderer
     */
    public String getEntryRenderer()
    {
        return _entryRenderer;
    }

    /**
     * @return the evenClass
     */
    public String getEvenClass()
    {
        return _evenClass;
    }

    /**
     * @return the foregroundClass
     */
    public String getForegroundClass()
    {
        return _foregroundClass;
    }

    /**
     * @return the freeClass
     */
    public String getFreeClass()
    {
        return _freeClass;
    }

    /**
     * @return the gutterClass
     */
    public String getGutterClass()
    {
        return _gutterClass;
    }

    /**
     * @return the headerClass
     */
    public String getHeaderClass()
    {
        return _headerClass;
    }

    /**
     * @return the holidayClass
     */
    public String getHolidayClass()
    {
        return _holidayClass;
    }

    /**
     * @return the hoursClass
     */
    public String getHoursClass()
    {
        return _hoursClass;
    }

    /**
     * @return the inactiveDayClass
     */
    public String getInactiveDayClass()
    {
        return _inactiveDayClass;
    }

    /**
     * @return the minutesClass
     */
    public String getMinutesClass()
    {
        return _minutesClass;
    }

    /**
     * @return the monthClass
     */
    public String getMonthClass()
    {
        return _monthClass;
    }

    /**
     * @return the selectedClass
     */
    public String getSelectedClass()
    {
        return _selectedClass;
    }

    /**
     * @return the selectedEntryClass
     */
    public String getSelectedEntryClass()
    {
        return _selectedEntryClass;
    }

    /**
     * @return the subtitleClass
     */
    public String getSubtitleClass()
    {
        return _subtitleClass;
    }

    /**
     * @return the textClass
     */
    public String getTextClass()
    {
        return _textClass;
    }

    /**
     * @return the titleClass
     */
    public String getTitleClass()
    {
        return _titleClass;
    }

    /**
     * @return the unevenClass
     */
    public String getUnevenClass()
    {
        return _unevenClass;
    }

    /**
     * @return the weekClass
     */
    public String getWeekClass()
    {
        return _weekClass;
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
    public void setEntryRenderer(String entryRenderer)
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
