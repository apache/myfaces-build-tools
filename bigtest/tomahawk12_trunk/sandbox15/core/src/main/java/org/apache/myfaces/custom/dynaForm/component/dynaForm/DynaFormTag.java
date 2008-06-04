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

public class DynaFormTag extends UIComponentBodyTagBase
{
	private String uri;
	private String var;
    private String valueBindingPrefix;
	private String displayOnly;
	private String bundle;
	private String exclusiveFields;

    public void release()
	{
		super.release();

		uri = null;
		var = null;
		displayOnly = null;
		bundle = null;
		exclusiveFields = null;
        valueBindingPrefix = null;
    }

	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);
		setStringProperty(component, "uri", uri);
		setStringProperty(component, "var", var);
        setStringProperty(component, "valueBindingPrefix", valueBindingPrefix);
        setBooleanProperty(component, "displayOnly", displayOnly);
		setStringProperty(component, "bundle", bundle);
		setBooleanProperty(component, "exclusiveFields", exclusiveFields);
	}

    /**
	 * @see DynaForm#setUri(String)
	 */
	public String getUri()
	{
		return uri;
	}

	/**
	 * @see DynaForm#setUri(String)
	 */
	public void setUri(String uri)
	{
		this.uri = uri;
	}
	
	/**
	 * @see DynaForm#setVar(String)
	 */
	public String getVar()
	{
		return var;
	}

	/**
	 * @see DynaForm#setVar(String)
	 */
	public void setVar(String var)
	{
		this.var = var;
	}
	
	/**
	 * @see DynaForm#setDisplayOnly(boolean)
	 */
	public String getDisplayOnly()
	{
		return displayOnly;
	}

	/**
	 * @see DynaForm#setDisplayOnly(boolean)
	 */
	public void setDisplayOnly(String displayOnly)
	{
		this.displayOnly = displayOnly;
	}

	/**
	 * @see DynaForm#setBundle(String)
	 */
	public String getBundle()
	{
		return bundle;
	}

	/**
	 * @see DynaForm#setBundle(String)
	 */
	public void setBundle(String bundle)
	{
		this.bundle = bundle;
	}

	/**
	 * @see DynaForm#setExclusiveFields(boolean)
	 */
	public String getExclusiveFields()
	{
		return exclusiveFields;
	}

	/**
	 * @see DynaForm#setExclusiveFields(boolean)
	 */
	public void setExclusiveFields(String exclusiveFields)
	{
		this.exclusiveFields = exclusiveFields;
	}

    /**
     * @see DynaForm#setValueBindingPrefix(String)
     */
    public String getValueBindingPrefix()
    {
        return valueBindingPrefix;
    }

    /**
     * @see DynaForm#setValueBindingPrefix(String)
     */
    public void setValueBindingPrefix(String valueBindingPrefix)
    {
        this.valueBindingPrefix = valueBindingPrefix;
    }

    public String getComponentType()
	{
		return DynaForm.COMPONENT_TYPE;
	}

	public String getRendererType()
	{
		return DynaForm.DEFAULT_RENDERER_TYPE;
	}
}
