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

import javax.faces.component.UIComponent;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputTextTagBase;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlInputDateTag extends HtmlInputTextTagBase {
    public String getComponentType() {
        return HtmlInputDate.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "org.apache.myfaces.Date";
    }

    // HtmlInputDate attributes
    private String type;
    private String ampm;
    private String popupCalendar;
    private String timeZone;
    private String emptyMonthSelection;
    private String emptyAmpmSelection;


    // UIComponent attributes --> already implemented in UIComponentTagBase
    // HTML universal attributes --> already implemented in HtmlComponentTagBase
    // HTML event handler attributes --> already implemented in MyFacesTag

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // UIInput attributes --> already implemented in HtmlInputTagBase
    // UIHTML Input attributes --> already implemented in HtmlInputTextTagBase

    // User Role support
    private String enabledOnUserRole;
    private String visibleOnUserRole;

    public void release() {
        super.release();
        enabledOnUserRole=null;
        visibleOnUserRole=null;
        type=null;
        ampm=null;
        popupCalendar=null;
        timeZone = null;
        emptyMonthSelection=null;
        emptyAmpmSelection=null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setStringProperty(component, "type", type);
        setBooleanProperty(component, "popupCalendar", popupCalendar);
        setBooleanProperty(component, "ampm", ampm);
        setStringProperty(component, "timeZone", timeZone);
        setStringProperty(component, "emptyMonthSelection", emptyMonthSelection);
        setStringProperty(component, "emptyAmpmSelection", emptyAmpmSelection);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, visibleOnUserRole);
    }

    public void setType(String type){
        this.type = type;
    }

    public void setPopupCalendar(String popupCalendar){
        this.popupCalendar = popupCalendar;
    }

    public void setAmpm(String ampm){
        this.ampm = ampm;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole){
        this.enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole){
        this.visibleOnUserRole = visibleOnUserRole;
    }

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }

    public String getEmptyMonthSelection() {
    	return emptyMonthSelection;
    }
    
    public void setEmptyMonthSelection(String emptyMonthSelection) {
    	this.emptyMonthSelection = emptyMonthSelection;
    }
    
	public String getEmptyAmpmSelection() {
		return emptyAmpmSelection;
	}

	public void setEmptyAmpmSelection(String emptyAmpmSelection) {
		this.emptyAmpmSelection = emptyAmpmSelection;
	}

}
