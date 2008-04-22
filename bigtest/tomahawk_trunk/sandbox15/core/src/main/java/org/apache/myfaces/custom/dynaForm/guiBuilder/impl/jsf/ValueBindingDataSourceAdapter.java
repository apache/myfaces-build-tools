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
package org.apache.myfaces.custom.dynaForm.guiBuilder.impl.jsf;

import javax.faces.el.ValueBinding;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.MethodBinding;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import java.util.Collection;

/**
 * wraps a datasource and presents it as selectItem list
 */
public class ValueBindingDataSourceAdapter extends ValueBinding implements StateHolder
{
	private MethodBinding mbValues;
	private MethodBinding mbLabels;

	private boolean _transient = false;

	public ValueBindingDataSourceAdapter()
	{
	}

	public ValueBindingDataSourceAdapter(MethodBinding mbValues, MethodBinding mbLabels)
	{
		this.mbValues = mbValues;
		this.mbLabels = mbLabels;
	}


	@Override
	public Class getType(FacesContext facesContext) throws EvaluationException, PropertyNotFoundException
	{
		return SelectItem[].class;
	}

	@Override
	public Object getValue(FacesContext facesContext) throws EvaluationException, PropertyNotFoundException
	{
		Object valueList = mbValues.invoke(facesContext, new Object[]{null});
		if (valueList == null)
		{
			return null;
		}

		if (valueList instanceof Collection)
		{
			Collection valueCollection = (Collection) valueList;

			SelectItem[] selectItems = new SelectItem[valueCollection.size()];

			int i = -1;
			for (Object value : valueCollection)
			{
				i++;

				String label = (String) mbLabels.invoke(facesContext, new Object[]
					{
						value
					});

				SelectItem si=new SelectItem(value, label);
				selectItems[i] = si;
			}

			return selectItems;
		}

		throw new IllegalArgumentException("don't know how to access " + valueList.getClass().getName());
	}

	@Override
	public boolean isReadOnly(FacesContext facesContext) throws EvaluationException, PropertyNotFoundException
	{
		return true;
	}

	@Override
	public void setValue(FacesContext facesContext, Object object) throws EvaluationException, PropertyNotFoundException
	{
		throw new UnsupportedOperationException();
	}

	public Object saveState(FacesContext facesContext)
	{
		return new Object[]
		{
			UIComponentBase.saveAttachedState(facesContext, mbValues),
			UIComponentBase.saveAttachedState(facesContext, mbLabels),
		};
	}

	public void restoreState(FacesContext facesContext, Object object)
	{
		Object[] states = (Object[]) object;
		mbValues = (MethodBinding) UIComponentBase.restoreAttachedState(facesContext, states[0]);
		mbLabels = (MethodBinding) UIComponentBase.restoreAttachedState(facesContext, states[1]);
	}

	public boolean isTransient()
	{
		return _transient;
	}

	public void setTransient(boolean flag)
	{
		_transient = flag;
	}
}
