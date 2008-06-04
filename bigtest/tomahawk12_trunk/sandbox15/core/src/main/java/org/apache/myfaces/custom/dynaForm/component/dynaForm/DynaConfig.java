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
package org.apache.myfaces.custom.dynaForm.component.dynaForm;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.dynaForm.metadata.MetaData.FieldImpl;

/**
 * the dynaForm configuration component<br />
 * handles overruled field setup
 */
public class DynaConfig extends UIComponentBase
{
	public static final String COMPONENT_TYPE = "org.apache.myfaces.dynaForm.DynaConfig";
	public static final String COMPONENT_FAMILY = "org.apache.myfaces.dynaForm.DynaConfig";

	private String forProperty;
	private Integer displaySize;
	private Boolean displayOnly;
	private Boolean readOnly;
	private Boolean disabled;

	@Override
	public String getFamily()
	{
		return COMPONENT_FAMILY;
	}

	/**
	 * @see #setDisplaySize(Integer)
	 */
	public Integer getDisplaySize()
	{
		if (displaySize != null)
			return displaySize;
		ValueBinding vb = getValueBinding("displaySize");
		return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
	}

	/**
	 * a hint for the size the ui should use when rendering the field
	 */
	public void setDisplaySize(Integer displaySize)
	{
		this.displaySize = displaySize;
	}

	/**
	 * @see #setDisplayOnly(Boolean)
	 */
	public Boolean getDisplayOnly()
	{
		if (displayOnly != null)
			return displayOnly;
		ValueBinding vb = getValueBinding("displayOnly");
		return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
	}

	/**
	 * set the field to display only
	 */
	public void setDisplayOnly(Boolean displayOnly)
	{
		this.displayOnly = displayOnly;
	}

	/**
	 * @see #setReadOnly(Boolean)
	 */
	public Boolean getReadOnly()
	{
		if (readOnly != null)
			return readOnly;
		ValueBinding vb = getValueBinding("readOnly");
		return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
	}

	/**
	 * configure this field as readOnly - noneditable input field
	 */
	public void setReadOnly(Boolean readOnly)
	{
		this.readOnly = readOnly;
	}

	/**
	 * @see #setDisabled(Boolean)
	 */
	public Boolean getDisabled()
	{
		if (disabled != null)
			return disabled;
		ValueBinding vb = getValueBinding("disabled");
		return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
	}

	/**
	 * configure this field as disabled - like readOnly but grayed too
	 */
	public void setDisabled(Boolean disabled)
	{
		this.disabled = disabled;
	}

	/**
	 * @see #setFor(String)
	 */
	public String getFor()
	{
		if (forProperty != null)
			return forProperty;
		ValueBinding vb = getValueBinding("for");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	/**
	 * the name of the property this configuration is for
	 */
	public void setFor(String forProperty)
	{
		this.forProperty = forProperty;
	}

	@Override
	public void restoreState(FacesContext context, Object stateArray)
	{
		Object[] states = (Object[]) stateArray;
		super.restoreState(context, states[0]);
		displaySize = (Integer) states[1];
		forProperty = (String) states[2];
		displayOnly = (Boolean) states[3];
		readOnly = (Boolean) states[4];
		disabled = (Boolean) states[5];
	}

	@Override
	public Object saveState(FacesContext context)
	{
		return new Object[]
		{
				super.saveState(context),
				displaySize,
				forProperty,
				displayOnly,
				readOnly,
				disabled
		};
	}

	public void configureMetaData(FieldImpl field)
	{
		if (getDisplaySize() != null)
		{
			field.setDisplaySize(getDisplaySize().intValue());
		}
		if (getDisplayOnly() != null)
		{
			field.setDisplayOnly(getDisplayOnly());
		}
		if (getReadOnly() != null)
		{
			field.setCanWrite(getReadOnly().booleanValue());
		}
		if (getDisabled() != null)
		{
			field.setDisabled(getDisabled().booleanValue());
		}

		if (getChildCount() > 0)
		{
			field.setWantedComponent((UIComponent) getChildren().get(0));
		}
	}
}