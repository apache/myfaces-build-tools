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
package org.apache.myfaces.custom.dynaForm.guiBuilder.impl.myfaces;

import org.apache.commons.beanutils.BeanUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.model.SelectItem;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Intercepts the method binding used to build the suggest selection list
 */
public class SuggestSelectListMethodWrapper extends MethodBinding
{
	private final MethodBinding original;
	private final String valueProperty;
	private final String[] descriptionPropertiesArray;

	SuggestSelectListMethodWrapper(MethodBinding original, String valueProperty, String descriptionProperties)
	{
		this.original = original;
		this.valueProperty = valueProperty;
		if (descriptionProperties != null)
		{
			descriptionPropertiesArray = descriptionProperties.split(",\\s*");
		}
		else
		{
			descriptionPropertiesArray = new String[]{};
		}
	}

	@Override
	public Class getType(FacesContext facesContext) throws MethodNotFoundException
	{
		return List.class;
	}

	@Override
	public Object invoke(FacesContext facesContext, Object[] objects) throws EvaluationException
	{
		Object items = original.invoke(facesContext, objects);
		if (items == null)
		{
			return items;
		}

		if (!(items instanceof Collection))
		{
			throw new UnsupportedOperationException("unknown return type " + items.getClass().getName() + " for " + getExpressionString() + " awaited instanceof java.util.Collection");
		}

		Collection coll = (Collection) items;

		List<SelectItem> selectItems = new ArrayList<SelectItem>(coll.size());
		for (Object o : coll)
		{
			SelectItem si;

			if (o instanceof SelectItem)
			{
				si = (SelectItem) o;
			}
			else
			{
				Object value = getValueProperty(o, valueProperty);
				String description = buildDescription(o, descriptionPropertiesArray);

				si = new SelectItem(value, description);
			}

			selectItems.add(si);
		}

		return selectItems;
	}

	protected String buildDescription(Object o, String[] descriptionPropertiesArray)
	{
		StringBuffer sb = new StringBuffer(80);

		for (String descriptionProperty : descriptionPropertiesArray)
		{
			Object descriptionValue = null;
			try
			{
				descriptionValue = BeanUtils.getProperty(o, descriptionProperty);
			}
			catch (IllegalAccessException e)
			{
				throw new EvaluationException(e);
			}
			catch (InvocationTargetException e)
			{
				throw new EvaluationException(e);
			}
			catch (NoSuchMethodException e)
			{
				throw new EvaluationException(e);
			}

			if (descriptionValue != null)
			{
				if (sb.length() > 0)
				{
					sb.append(", ");
				}

				sb.append(descriptionValue);
			}
		}

		return sb.toString();
	}

	protected Object getValueProperty(Object o, String valueProperty)
	{
		if (valueProperty == null || valueProperty.trim().length() < 1)
		{
			return o;
		}

		try
		{
			return BeanUtils.getProperty(o, valueProperty);
		}
		catch (IllegalAccessException e)
		{
			throw new EvaluationException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new EvaluationException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new EvaluationException(e);
		}
	}
}
