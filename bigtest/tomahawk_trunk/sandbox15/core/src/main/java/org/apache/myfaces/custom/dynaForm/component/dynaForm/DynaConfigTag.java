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

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentBodyTagBase;

public class DynaConfigTag extends UIComponentBodyTagBase
{
	private String displaySize;
	private String forProperty;
	private String displayOnly;
	private String readOnly;
	private String disabled;

	public void release()
	{
		super.release();

		displaySize = null;
		forProperty = null;
	}

	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);
		setIntegerProperty(component, "displaySize", displaySize);
		setStringProperty(component, "for", forProperty);
		setBooleanProperty(component, "displayOnly", displayOnly);
		setBooleanProperty(component, "readOnly", readOnly);
		setBooleanProperty(component, "disabled", disabled);
	}

	/**
	 * @see DynaConfig#setDisplaySize(Integer)
	 */
	public String getDisplaySize()
	{
		return displaySize;
	}

	/**
	 * @see DynaConfig#setDisplaySize(Integer)
	 */
	public void setDisplaySize(String size)
	{
		this.displaySize = size;
	}

	/**
	 * @see DynaConfig#setFor(String)
	 */
	public String getFor()
	{
		return forProperty;
	}

	/**
	 * @see DynaConfig#setFor(String)
	 */
	public void setFor(String forProperty)
	{
		this.forProperty = forProperty;
	}

	/**
	 * @see DynaConfig#setDisplayOnly(Boolean)
	 */
	public String getDisplayOnly()
	{
		return displayOnly;
	}

	/**
	 * @see DynaConfig#setDisplayOnly(Boolean)
	 */
	public void setDisplayOnly(String displayOnly)
	{
		this.displayOnly = displayOnly;
	}

	/**
	 * @see DynaConfig#setDisabled(Boolean)
	 */
	public String getDisabled()
	{
		return disabled;
	}

	/**
	 * @see DynaConfig#setDisabled(Boolean)
	 */
	public void setDisabled(String disabled)
	{
		this.disabled = disabled;
	}

	/**
	 * @see DynaConfig#setReadOnly(Boolean)
	 */
	public String getReadOnly()
	{
		return readOnly;
	}

	/**
	 * @see DynaConfig#setReadOnly(Boolean)
	 */
	public void setReadOnly(String readOnly)
	{
		this.readOnly = readOnly;
	}

	public String getComponentType()
	{
		return DynaConfig.COMPONENT_TYPE;
	}

	public String getRendererType()
	{
		return null;
	}
}
