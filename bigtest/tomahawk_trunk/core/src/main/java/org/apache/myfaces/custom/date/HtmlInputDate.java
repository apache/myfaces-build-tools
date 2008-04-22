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
package org.apache.myfaces.custom.date;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlInputDate extends UIInput implements UserRoleAware {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlInputDate";
    public static final String COMPONENT_FAMILY = "javax.faces.Input";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Date";
    private static final boolean DEFAULT_DISABLED = false;

	private Boolean _readonly = null;
    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;

    /**
     * Same as for f:convertDateTime
     * Specifies what contents the string value will be formatted to include, or parsed expecting.
     * Valid values are "date", "time", and "both". Default value is "date".
     */
    private String _type = null;
    private Boolean _popupCalendar = null;
    private String _timeZone = null;
    private Boolean _ampm = null;
    private String _emptyMonthSelection = null;
    private String _emptyAmpmSelection = null;


    private Boolean _disabled = null;

    public HtmlInputDate() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public UserData getUserData(Locale currentLocale){
        return new UserData((Date) getValue(), currentLocale, getTimeZone(), isAmpm(), getType());
    }
    
    /**
     * Overriden to support the force id, since the parent is not an extended component 
     */
    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }

	public String getType() {
		if (_type != null) return _type;
		ValueBinding vb = getValueBinding("type");
		return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : "date";
	}
	public void setType(String string) {
		_type = string;
	}

    public boolean isPopupCalendar(){
   		if (_popupCalendar != null)
   		    return _popupCalendar.booleanValue();
   		ValueBinding vb = getValueBinding("popupCalendar");
   		return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    public void setPopupCalendar(boolean popupCalendar){
        this._popupCalendar = Boolean.valueOf(popupCalendar);
    }
    
    public boolean isAmpm(){
   		if (_ampm != null)
   		    return _ampm.booleanValue();
   		ValueBinding vb = getValueBinding("ampm");
   		return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    public void setAmpm(boolean ampm){
        this._ampm = Boolean.valueOf(ampm);
    }

    public String getTimeZone(){
        if(_timeZone != null) return _timeZone;
        ValueBinding vb = getValueBinding("timeZone");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }
    
    public void setTimeZone(String timeZone){
        _timeZone = timeZone;
    }
    
	public String getEmptyMonthSelection() {
		if (_emptyMonthSelection != null) return _emptyMonthSelection;
		ValueBinding vb = getValueBinding("emptyMonthSelection");
		return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : "";
	}
	
	public void setEmptyMonthSelection(String string) {
		_emptyMonthSelection = string;
	}
	
	public String getEmptyAmpmSelection() {
		if (_emptyAmpmSelection != null) return _emptyAmpmSelection;
		ValueBinding vb = getValueBinding("emptyAmpmSelection");
		return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : "";
	}
	
	public void setEmptyAmpmSelection(String string) {
		_emptyAmpmSelection = string;
	}

    public boolean isReadonly(){
        if (_readonly != null) return _readonly.booleanValue();
        ValueBinding vb = getValueBinding("readonly");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : false;
    }
    public void setReadonly(boolean readonly){
        _readonly = Boolean.valueOf(readonly);
    }

    public void setEnabledOnUserRole(String enabledOnUserRole){
        _enabledOnUserRole = enabledOnUserRole;
    }
    public String getEnabledOnUserRole(){
        if (_enabledOnUserRole != null) return _enabledOnUserRole;
        ValueBinding vb = getValueBinding("enabledOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole){
        _visibleOnUserRole = visibleOnUserRole;
    }
    public String getVisibleOnUserRole(){
        if (_visibleOnUserRole != null) return _visibleOnUserRole;
        ValueBinding vb = getValueBinding("visibleOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public boolean isRendered(){
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

    public boolean isDisabled(){
        if (_disabled != null) return _disabled.booleanValue();
        ValueBinding vb = getValueBinding("disabled");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_DISABLED;
    }
    public void setDisabled(boolean disabled) {
        _disabled = Boolean.valueOf(disabled);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[11];
        values[0] = super.saveState(context);
        values[1] = _type;
        values[2] = _popupCalendar;
        values[3] = _disabled;
		values[4] = _readonly;
        values[5] = _enabledOnUserRole;
        values[6] = _visibleOnUserRole;
        values[7] = _timeZone;
        values[8] = _ampm;
        values[9] = _emptyMonthSelection;
        values[10] = _emptyAmpmSelection;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _type = (String)values[1];
        _popupCalendar = (Boolean)values[2];
        _disabled = (Boolean)values[3];
		_readonly = (Boolean)values[4];
        _enabledOnUserRole = (String)values[5];
        _visibleOnUserRole = (String)values[6];
        _timeZone = (String)values[7];
        _ampm = (Boolean)values[8];
        _emptyMonthSelection = (String)values[9];
        _emptyAmpmSelection = (String)values[10];
    }

    public static class UserData implements Serializable {
        private static final long serialVersionUID = -6507279524833267707L;
        private String day;
        private String month;
        private String year;
        private String hours;
        private String minutes;
        private String seconds;
        private TimeZone timeZone = null;
        private String ampm;
        private boolean uses_ampm;
        private String type;

        public UserData(Date date, Locale currentLocale, String _timeZone, boolean uses_ampm, String type){
        	this.uses_ampm = uses_ampm;
        	this.type = type;

            Calendar calendar = Calendar.getInstance(currentLocale);
            if (_timeZone != null) {
				timeZone = TimeZone.getTimeZone(_timeZone);
                calendar.setTimeZone(timeZone);
			}
            
            if(date == null)
            	return;
          
            calendar.setTime( date );
            day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            month = Integer.toString(calendar.get(Calendar.MONTH)+1);
            year = Integer.toString(calendar.get(Calendar.YEAR));
            if (uses_ampm) {
            	int int_hours = calendar.get(Calendar.HOUR);
            	// ampm hours must be in range 0-11 to be handled right; we have to handle "12" specially
            	if (int_hours == 0) {
            		int_hours = 12;
            	}
            	hours = Integer.toString(int_hours);
                ampm = Integer.toString(calendar.get(Calendar.AM_PM));
            } else {
            	hours = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
            }
            minutes = Integer.toString(calendar.get(Calendar.MINUTE));
            seconds = Integer.toString(calendar.get(Calendar.SECOND));
        }

        public Date parse() throws ParseException{
        	Date retDate = null;
            Calendar tempCalendar=Calendar.getInstance();
            tempCalendar.setLenient(Boolean.FALSE.booleanValue());
            if (timeZone != null)
                   tempCalendar.setTimeZone(timeZone);
            try{
        		if(!isSubmitValid(uses_ampm, type)) {
        			return null;
        		}
        		
            	if(! (type.equals( "time" ) || type.equals( "short_time" )) ) {
            		tempCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
            		tempCalendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
            		tempCalendar.set(Calendar.YEAR,Integer.parseInt(year));
            	}

            	if(! type.equals( "date" )) {
            		
            		if (uses_ampm) {
	            		int int_hours = Integer.parseInt(hours);
	            		// ampm hours must be in range 0-11 to be handled right; we have to handle "12" specially
	            		if (int_hours == 12) {
	            			int_hours = 0;
	            		}
	            		tempCalendar.set(Calendar.HOUR,int_hours);
	            		tempCalendar.set(Calendar.AM_PM,Integer.parseInt(ampm));
	            	} else {
	            		tempCalendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hours));
	            	}
            		tempCalendar.set(Calendar.MINUTE,Integer.parseInt(minutes));
            		
            		if (seconds != null & (type.equals("full") || type.equals("time") || type.equals("short_time"))) {
	            		tempCalendar.set(Calendar.SECOND,Integer.parseInt(seconds));
            		}
            	}
            	tempCalendar.set(Calendar.MILLISECOND, 0);
	            retDate = tempCalendar.getTime();
            } catch (NumberFormatException e) {
            	throw new ParseException(e.getMessage(),0);
            } catch (IllegalArgumentException e) {
            	throw new ParseException(e.getMessage(),0);
            } 
            return retDate;
        }

        private String formatedInt(String toFormat){
            if( toFormat == null )
                return null;

            int i = -1;
            try{
                i = Integer.parseInt( toFormat );
            }catch(NumberFormatException nfe){
                return toFormat;
            }
            if( i >= 0 && i < 10 )
                return "0"+i;
            return Integer.toString(i);
        }
        
        private boolean isDateSubmitted(boolean usesAmpm, String type) {
        	boolean isDateSubmitted = ! (StringUtils.isEmpty(getDay()) && ((getMonth() == null) || getMonth().equals("-1")) && StringUtils.isEmpty(getYear()));
        	if(usesAmpm)
        		isDateSubmitted = isDateSubmitted || isAmpmSubmitted();
        	return isDateSubmitted;
        }
        
        private boolean isTimeSubmitted(boolean usesAmpm, String type) {
        	boolean isTimeSubmitted = ! (StringUtils.isEmpty(getHours()) && StringUtils.isEmpty(getMinutes()));
        	if(type.equals("time") || type.equals("full"))
        		isTimeSubmitted = isTimeSubmitted || ! StringUtils.isEmpty(getSeconds());
        	if(usesAmpm)
        		isTimeSubmitted = isTimeSubmitted || isAmpmSubmitted();
        	return isTimeSubmitted;
        }
        
        private boolean isSubmitValid(boolean usesAmpm, String type) {
        	if(type.equals("date"))
        		return isDateSubmitted(usesAmpm, type);
        	else if(type.equals("time") || (type.equals("short_time")))
        		return isTimeSubmitted(usesAmpm, type);
        	else if(type.equals("full") || type.equals("both"))
        		return isDateSubmitted(usesAmpm, type) || isTimeSubmitted(usesAmpm, type);
        	else
        		return false;
        }
        
        private boolean isAmpmSubmitted() {
        	if(getAmpm() == null)
        		return false;
        	else
        		return ! getAmpm().equals("-1");
        }

        public String getDay() {
            return formatedInt( day );
        }
        public void setDay(String day) {
            this.day = day;
        }

        public String getMonth() {
            return month;
        }
        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }
        public void setYear(String year) {
            this.year = year;
        }

        public String getHours() {
            return formatedInt( hours );
        }
        public void setHours(String hours) {
            this.hours = hours;
        }
        public String getMinutes() {
            return formatedInt( minutes );
        }
        public void setMinutes(String minutes) {
            this.minutes = minutes;
        }

        public String getSeconds() {
            return formatedInt( seconds );
        }
        public void setSeconds(String seconds) {
            this.seconds = seconds;
        }
        
        public String getAmpm() {
            return ampm;
        }
        public void setAmpm(String ampm) {
            this.ampm = ampm;
        }
        
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
    }
}
