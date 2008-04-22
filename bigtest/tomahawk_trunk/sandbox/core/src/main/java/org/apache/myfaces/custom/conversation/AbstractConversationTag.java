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
package org.apache.myfaces.custom.conversation;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

/**
 * base class for all conversation tags
 * @author im
 */
public abstract class AbstractConversationTag extends UIComponentTagBase
{
	private String name;
	
	public String getRendererType()
	{
		return null;
	}

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, "name", name);
    }

    /**
     * the conversation name
     */
	public String getName()
	{
		return name;
	}

	/**
	 * the conversation name
	 */
	public void setName(String alias)
	{
		this.name = alias;
	}
}
