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
package org.apache.myfaces.custom.subform;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Gerald Muellan
 *         Date: 19.01.2006
 *         Time: 14:01:07
 */
public class SubFormTag extends UIComponentTagBase
{
	private String preserveSubmittedValues;

	public String getComponentType()
    {
        return SubForm.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return SubForm.DEFAULT_RENDERER_TYPE;
    }

	public void release()
	{
		super.release();

		preserveSubmittedValues = null;
	}

	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);

		if (preserveSubmittedValues != null)
		{
			setBooleanProperty(component, "preserveSubmittedValues", preserveSubmittedValues);
		}
	}

	public String getPreserveSubmittedValues()
	{
		return preserveSubmittedValues;
	}

	public void setPreserveSubmittedValues(String preserveSubmittedValues)
	{
		this.preserveSubmittedValues = preserveSubmittedValues;
	}
}
