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

import javax.faces.component.UIComponentBase;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.myfaces.custom.schedule.model.SimpleScheduleModel;
import org.apache.myfaces.custom.schedule.util.ScheduleUtil;

/**
 * Base class for the Schedule component. This class contains all the properties
 * for the schedule, but not the ActionSource stuff. Keeping these things separate
 * should make the code a little easier to digest.
 * 
 * @author Jurgen Lust
 * @version $Revision$
 */
public class UIScheduleBase extends UIComponentBase implements ValueHolder,
        Serializable
{
    private static final long serialVersionUID = 5702081384947086911L;

    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Schedule";
    public static final String RENDERER_TYPE = "org.apache.myfaces.Schedule";

    protected static final String HOUR_NOTATION_24 = "24";
    protected static final String HOUR_NOTATION_12 = "12";

    protected static final int DEFAULT_COMPACT_MONTH_ROWHEIGHT = 120;
    protected static final int DEFAULT_COMPACT_WEEK_ROWHEIGHT = 200;
    protected static final int DEFAULT_DETAILED_ROWHEIGHT = 22;
    protected static final boolean DEFAULT_EXPAND_TO_FIT = false;
    protected static final String DEFAULT_HEADER_DATE_FORMAT = null;
    protected static final boolean DEFAULT_IMMEDIATE = false;
    protected static final boolean DEFAULT_READONLY = false;
    protected static final boolean DEFAULT_RENDER_ZEROLENGTH = false;
    protected static final String DEFAULT_THEME = "default";
    protected static final boolean DEFAULT_TOOLTIP = false;
    protected static final int DEFAULT_VISIBLE_END_HOUR = 20;
    protected static final int DEFAULT_VISIBLE_START_HOUR = 8;
    protected static final int DEFAULT_WORKING_END_HOUR = 17;
    protected static final int DEFAULT_WORKING_START_HOUR = 9;
    protected static final String DEFAULT_ENABLED_ON_USER_ROLE = null;
    protected static final boolean DEFAULT_SUBMIT_ON_CLICK = false;
    protected static final String DEFAULT_VISIBLE_ON_USER_ROLE = null;
	protected static final boolean DEFAULT_SPLIT_WEEKEND = true;
    
    private Integer _compactMonthRowHeight;
    private Integer _compactWeekRowHeight;
    private Converter _converter;
    private Integer _detailedRowHeight;
    private Boolean _expandToFitEntries;
    private String _headerDateFormat;
    private Boolean _immediate;
    private Boolean _readonly;
    private Boolean _renderZeroLengthEntries;
    private String _theme;
    private Boolean _tooltip;
    private Object _value;
    private Integer _visibleEndHour;
    private Integer _visibleStartHour;
    private Integer _workingEndHour;
    private Integer _workingStartHour;
    private Boolean _submitOnClick = null;
    private String _hourNotation;
    private String _compactMonthDayOfWeekDateFormat;
	private Boolean _splitWeekend;

	public boolean isSplitWeekend()
	{
		return ScheduleUtil.getBooleanProperty(this, _splitWeekend, "splitWeekend", DEFAULT_SPLIT_WEEKEND);
	}
	
	public void setSplitWeekend(boolean splitWeekend)
	{
		_splitWeekend = Boolean.valueOf(splitWeekend);
	}
	
    /**
     * Should the parent form of this schedule be submitted when the user
     * clicks on a day? Note that this will only work when the readonly
     * property is set to false.
     *
     * @return the _submitOnClick
     */
    public boolean isSubmitOnClick()
    {
        return ScheduleUtil.getBooleanProperty(this, _submitOnClick, "submitOnClick", DEFAULT_SUBMIT_ON_CLICK);
    }

    /**
     * @param submitOnClick the _submitOnClick to set
     */
    public void setSubmitOnClick(boolean submitOnClick)
    {
        _submitOnClick = Boolean.valueOf(submitOnClick);
    }

    public UIScheduleBase()
    {
        super();
        setRendererType(RENDERER_TYPE);
    }

    /**
     * @return the compactMonthRowHeight
     */
    public int getCompactMonthRowHeight()
    {
        return ScheduleUtil.getIntegerProperty(this, _compactMonthRowHeight,
                "compactMonthRowHeight", DEFAULT_COMPACT_MONTH_ROWHEIGHT);
    }

    /**
     * @return the compactWeekRowHeight
     */
    public int getCompactWeekRowHeight()
    {
        return ScheduleUtil.getIntegerProperty(this, _compactWeekRowHeight,
                "compactWeekRowHeight", DEFAULT_COMPACT_WEEK_ROWHEIGHT);
    }

    /**
     * @see javax.faces.component.ValueHolder#getConverter()
     */
    public Converter getConverter()
    {
        return _converter;
    }

    /**
     * @return the detailedRowHeight
     */
    public int getDetailedRowHeight()
    {
        return ScheduleUtil.getIntegerProperty(this, _detailedRowHeight,
                "detailedRowHeight", DEFAULT_DETAILED_ROWHEIGHT);
    }

    /**
     * @return the expandToFitEntries
     */
    public boolean isExpandToFitEntries()
    {
        return ScheduleUtil.getBooleanProperty(this, _expandToFitEntries,
                "expandToFitEntries", DEFAULT_EXPAND_TO_FIT);
    }

    /**
     * @see javax.faces.component.UIComponent#getFamily()
     */
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    /**
     * @return the headerDateFormat
     */
    public String getHeaderDateFormat()
    {
        return ScheduleUtil.getStringProperty(this, _headerDateFormat,
                "headerDateFormat", DEFAULT_HEADER_DATE_FORMAT);
    }

    /**
     * @return the immediate
     */
    public boolean isImmediate()
    {
        return ScheduleUtil.getBooleanProperty(this, _immediate, "immediate",
                DEFAULT_IMMEDIATE);
    }

    /**
     * @see javax.faces.component.ValueHolder#getLocalValue()
     */
    public Object getLocalValue()
    {
        return _value;
    }

    /**
     * The underlying model
     *
     * @return Returns the model.
     */
    public ScheduleModel getModel()
    {
        if (getValue() instanceof ScheduleModel)
        {
            return (ScheduleModel) getValue();
        }
        else
        {
            return new SimpleScheduleModel();
        }
    }

    /**
     * @return the readonly
     */
    public boolean isReadonly()
    {
        return ScheduleUtil.getBooleanProperty(this, _readonly, "readonly",
                DEFAULT_READONLY);
    }

    /**
     * @see javax.faces.component.UIComponentBase#getRendersChildren()
     */
    public boolean isRendersChildren()
    {
        return true;
    }

    /**
     * @return the renderZeroLengthEntries
     */
    public boolean isRenderZeroLengthEntries()
    {
        return ScheduleUtil.getBooleanProperty(this, _renderZeroLengthEntries,
                "renderZeroLengthEntries", DEFAULT_RENDER_ZEROLENGTH);
    }

    /**
     * @return the theme
     */
    public String getTheme()
    {
        return ScheduleUtil.getStringProperty(this, _theme, "theme",
                DEFAULT_THEME);
    }

    /**
     * @return the tooltip
     */
    public boolean isTooltip()
    {
        return ScheduleUtil.getBooleanProperty(this, _tooltip, "tooltip",
                DEFAULT_TOOLTIP);
    }

    /**
     * @see javax.faces.component.ValueHolder#getValue()
     */
    public Object getValue()
    {
        return ScheduleUtil.getObjectProperty(this, _value, "value", _value);
    }

    /**
     * @return the visibleEndHour
     */
    public int getVisibleEndHour()
    {
        return ScheduleUtil.getIntegerProperty(this, _visibleEndHour,
                "visibleEndHour", DEFAULT_VISIBLE_END_HOUR);
    }

    /**
     * @return the visibleStartHour
     */
    public int getVisibleStartHour()
    {
        return ScheduleUtil.getIntegerProperty(this, _visibleStartHour,
                "visibleStartHour", DEFAULT_VISIBLE_START_HOUR);
    }

    /**
     * @return the workingEndHour
     */
    public int getWorkingEndHour()
    {
        return ScheduleUtil.getIntegerProperty(this, _workingEndHour,
                "workingEndHour", DEFAULT_WORKING_END_HOUR);
    }

    /**
     * @return the workingStartHour
     */
    public int getWorkingStartHour()
    {
        return ScheduleUtil.getIntegerProperty(this, _workingStartHour,
                "workingStartHour", DEFAULT_WORKING_START_HOUR);
    }

    /**
     * <p>
     * Show dates in 24 hour notation or 12 hour notation.
     * </p>
     * 
     * @return "12", "24" or null for the renderer default
     */
    public String getHourNotation()
    {
    	return ScheduleUtil.getStringProperty(this, _hourNotation, "hourNotation", null);
    }

    /**
     * @return the headerDateFormat
     */
    public String getCompactMonthDayOfWeekDateFormat()
    {
        return ScheduleUtil.getStringProperty(this, _compactMonthDayOfWeekDateFormat,
                "compactMonthDayOfWeekDateFormat", null);
    }
    
    /**
     * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        _compactMonthRowHeight = (Integer)values[1];
        _compactWeekRowHeight = (Integer)values[2];
        _detailedRowHeight = (Integer)values[3];
        _expandToFitEntries = (Boolean)values[4];
        _headerDateFormat = (String)values[5];
        _immediate = (Boolean)values[6];
        _readonly = (Boolean)values[7];
        _renderZeroLengthEntries = (Boolean)values[8];
        _theme = (String)values[9];
        _tooltip = (Boolean)values[10];
        _value = values[11];
        _visibleEndHour = (Integer)values[12];
        _visibleStartHour = (Integer)values[13];
        _workingEndHour = (Integer)values[14];
        _workingStartHour = (Integer)values[15];
        _submitOnClick = (Boolean)values[16];
        _hourNotation = (String) values[17];
		_splitWeekend = (Boolean)values[18];
		_compactMonthDayOfWeekDateFormat = (String)values[19];
    }

    /**
     * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[20];
        values[0] = super.saveState(context);
        values[1] = _compactMonthRowHeight;
        values[2] = _compactWeekRowHeight;
        values[3] = _detailedRowHeight;
        values[4] = _expandToFitEntries;
        values[5] = _headerDateFormat;
        values[6] = _immediate;
        values[7] = _readonly;
        values[8] = _renderZeroLengthEntries;
        values[9] = _theme;
        values[10] = _tooltip;
        values[11] = _value;
        values[12] = _visibleEndHour;
        values[13] = _visibleStartHour;
        values[14] = _workingEndHour;
        values[15] = _workingStartHour;
        values[16] = _submitOnClick;
        values[17] = _hourNotation;
		values[18] = _splitWeekend;
		values[19] = _compactMonthDayOfWeekDateFormat;
        
        return values;
    }

    /**
     * @param compactMonthRowHeight the compactMonthRowHeight to set
     */
    public void setCompactMonthRowHeight(int compactMonthRowHeight)
    {
        this._compactMonthRowHeight = new Integer(compactMonthRowHeight);
    }

    /**
     * @param compactWeekRowHeight the compactWeekRowHeight to set
     */
    public void setCompactWeekRowHeight(int compactWeekRowHeight)
    {
        this._compactWeekRowHeight = new Integer(compactWeekRowHeight);
    }

    /**
     * @see javax.faces.component.ValueHolder#setConverter(javax.faces.convert.Converter)
     */
    public void setConverter(Converter converter)
    {
        this._converter = converter;
    }

    /**
     * @param detailedRowHeight the detailedRowHeight to set
     */
    public void setDetailedRowHeight(int detailedRowHeight)
    {
        this._detailedRowHeight = new Integer(detailedRowHeight);
    }

    /**
     * @param expandToFitEntries the expandToFitEntries to set
     */
    public void setExpandToFitEntries(boolean expandToFitEntries)
    {
        this._expandToFitEntries = Boolean.valueOf(expandToFitEntries);
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
    public void setImmediate(boolean immediate)
    {
        this._immediate = Boolean.valueOf(immediate);
    }

    /**
     * The underlying model
     *
     * @param model The model to set.
     */
    public void setModel(ScheduleModel model)
    {
        setValue(model);
    }

    /**
     * @param readonly the readonly to set
     */
    public void setReadonly(boolean readonly)
    {
        this._readonly = Boolean.valueOf(readonly);
    }

    /**
     * @param renderZeroLengthEntries the renderZeroLengthEntries to set
     */
    public void setRenderZeroLengthEntries(boolean renderZeroLengthEntries)
    {
        this._renderZeroLengthEntries = Boolean.valueOf(renderZeroLengthEntries);
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
    public void setTooltip(boolean tooltip)
    {
        this._tooltip = Boolean.valueOf(tooltip);
    }

    /**
     * @see javax.faces.component.ValueHolder#setValue(java.lang.Object)
     */
    public void setValue(Object value)
    {
        this._value = value;
    }

    /**
     * @param visibleEndHour the visibleEndHour to set
     */
    public void setVisibleEndHour(int visibleEndHour)
    {
        this._visibleEndHour = new Integer(visibleEndHour);
    }

    /**
     * @param visibleStartHour the visibleStartHour to set
     */
    public void setVisibleStartHour(int visibleStartHour)
    {
        this._visibleStartHour = new Integer(visibleStartHour);
    }
    
    /**
     * @param workingEndHour the workingEndHour to set
     */
    public void setWorkingEndHour(int workingEndHour)
    {
        this._workingEndHour = new Integer(workingEndHour);
    }

    /**
     * @param workingStartHour the workingStartHour to set
     */
    public void setWorkingStartHour(int workingStartHour)
    {
        this._workingStartHour = new Integer(workingStartHour);
    }
    
    /**
     * <p>
     * Show dates in 24 hour notation or 12 hour notation.
     * </p>
     * 
     * @param hourNotation 12 for 12 hour notation and 24 for 24 hour notation
     */
    public void setHourNotation(String hourNotation)
    {
    	this._hourNotation = hourNotation;
    }
    
    /**
     * <p>
     * Date format to use for days of the week headers in the month view.
     * e.g. EEEE = Monday, Tuesday,  etc.
     * </p>
     * 
     * @param compactMonthDayOfWeekDateFormat Date format for day of the week header, or null for no header
     */
    public void setCompactMonthDayOfWeekDateFormat(String compactMonthDayOfWeekDateFormat) {
		_compactMonthDayOfWeekDateFormat = compactMonthDayOfWeekDateFormat;
	}
}
