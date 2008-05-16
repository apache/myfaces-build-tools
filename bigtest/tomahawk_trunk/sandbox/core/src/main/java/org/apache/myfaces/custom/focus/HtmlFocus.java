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
package org.apache.myfaces.custom.focus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @JSFComponent
 *   name = "s:focus"
 *   tagClass = "org.apache.myfaces.custom.focus.HtmlFocusTag"
 *   
 * @author Rogerio Pereira Araujo (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlFocus extends UIInput
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Focus";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Focus";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";

	private String _for = null;
    private Boolean _rememberClientFocus=null;
    private static final boolean DEFAULT_REMEMBER_CLIENT_FOCUS = false;

    private static Log log = LogFactory.getLog(HtmlFocus.class);

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

	public void setFor(String forValue)
	{
		_for = forValue;
	}

	public String getFor()
	{
		if (_for != null)
			return _for;
		ValueBinding vb = getValueBinding("for");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

    public void setRememberClientFocus(boolean rememberClientFocus)
    {
        _rememberClientFocus = Boolean.valueOf(rememberClientFocus);
    }

    public boolean isRememberClientFocus()
    {
        if (_rememberClientFocus != null) return _rememberClientFocus.booleanValue();
        ValueBinding vb = getValueBinding("rememberClientFocus");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_REMEMBER_CLIENT_FOCUS;
    }


    protected UIComponent findUIComponent()
	{
		String forStr = getFor();

		if (forStr == null)
		{
			throw new IllegalArgumentException("focus@for must be specified");
		}

		UIComponent forComp = findComponent(forStr);
		if (forComp == null)
		{
			log.warn("could not find UIComponent referenced by attribute focus@for = '"
					+ forStr + "'");
		}
		return forComp;
	}

	public Object saveState(FacesContext context)
	{
		Object values[] = new Object[3];
		values[0] = super.saveState(context);
		values[1] = _for;
        values[2] = _rememberClientFocus;
        return values;
	}

	public void restoreState(FacesContext context, Object state)
	{
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		_for = (String) values[1];
        _rememberClientFocus = (Boolean) values[2];
    }

}
