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
package org.apache.myfaces.custom.dynaForm.guiBuilder;

import java.util.Date;
import java.util.Map;

import org.apache.myfaces.custom.dynaForm.metadata.FieldInterface;
import org.apache.myfaces.custom.dynaForm.metadata.RelationType;
import org.apache.myfaces.custom.dynaForm.metadata.utils.TypeInfos;

/**
 * The interface to the gui builder which creates all needed gui elements.<br />
 * You have implement each of them.
 */
public abstract class GuiBuilder
{
	private boolean displayOnly;
	private Map labelBundle;

	/**
	 * @see #setDisplayOnly(boolean)
	 */
	public boolean isDisplayOnly()
	{
		return displayOnly;
	}

	/**
	 * set if the guiBuilder should build a read only form only
	 */
	public void setDisplayOnly(boolean displayOnly)
	{
		this.displayOnly = displayOnly;
	}

	/**
	 * @see #setLabelBundle(Map)
	 */
	public Map getLabelBundle()
	{
		return labelBundle;
	}

	/**
	 * the bundle to use with labels
	 */
	public void setLabelBundle(Map labelBundle)
	{
		this.labelBundle = labelBundle;
	}

	/**
	 * output text using the field as value provider
	 */
	public abstract void createOutputText(FieldInterface field);

	/**
	 * input field
	 */
	public abstract void createInputText(FieldInterface field);

	/**
	 * input date
	 */
	public abstract void createInputDate(FieldInterface field);

	/**
	 * select one menu
	 */
	public abstract void createSelectOneMenu(FieldInterface field);

	/**
	 * enter a number
	 */
	public abstract void createInputNumber(FieldInterface field);

	/**
	 * select a boolean
	 */
	public abstract void createInputBoolean(FieldInterface field);

	/**
	 * search for component
	 */
	public abstract void createSearchFor(FieldInterface field);

	/**
	 * search for component
	 */
	public abstract void createSearchForSelectMenu(FieldInterface field);

	// public abstract void createSearchForSelectMenu(FieldInterface field);

	/**
	 * this is when the user passed in a component to use for the this field
	 */
	public abstract void createNative(FieldInterface field);

	/**
	 * actuallly build the fields gui component
	 */
	protected boolean buildField(FieldInterface field)
	{
		Class fieldType = field.getType();

		if (field.getWantedComponent() != null)
		{
			createNative(field);
			return true;
		}

		if (!field.getWantedComponentType().equals(ComponentEnum.Automatic))
		{
			switch(field.getWantedComponentType())
			{
				case OutputText:
					createOutputText(field);
					return true;

				case InputText:
					createInputText(field);
					return true;

				case InputDate:
					createInputDate(field);
					return true;

				case SelectOneMenu:
					if (RelationType.MANY_TO_ONE.equals(field.getRelationType()))
					{
						createSearchForSelectMenu(field);
					}
					else
					{
						// TODO: will not work now
						createSelectOneMenu(field);
					}
					return true;

				/*
				case SelectSearchMenu:
					createSearchForSelectMenu(field);
					return true;
				*/

				case InputNumber:
					createInputNumber(field);
					return true;

				case InputBoolean:
					createInputBoolean(field);
					return true;
			}
		}

		if (Boolean.TRUE.equals(field.getDisplayOnly()))
		{
			createOutputText(field);
			return true;
		}

		if (field.getAllowedSelections() != null)
		{
			createSelectOneMenu(field);
			return true;
		}

		if (RelationType.MANY_TO_ONE.equals(field.getRelationType()))
		{
			createSearchFor(field);
			return true;
		}

		if (fieldType == null)
		{
			throw new IllegalArgumentException("No type for field '" + field.getName() + "' detected.");
		}

		if (Date.class.isAssignableFrom(fieldType))
		{
			createInputDate(field);
			return true;
		}

		TypeInfos.Info typeInfo = TypeInfos.getInfo(fieldType);
		if (typeInfo != null && typeInfo.isNumber())
		{
			createInputNumber(field);
			return true;
		}

		if (String.class.isAssignableFrom(fieldType))
		{
			createInputText(field);
			return true;
		}

		if (Boolean.class.isAssignableFrom(fieldType) || boolean.class.equals(fieldType))
		{
			createInputBoolean(field);
			return true;
		}

		return false;
	}
}
