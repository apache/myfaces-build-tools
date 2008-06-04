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
package org.apache.myfaces.custom.valueChangeNotifier;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * receives valueChange events and pass them to the manager
 * 
 * @author Mario Ivankovits <imario - at - apache.org> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValueChangeCollector implements ValueChangeListener,
		StateHolder
{
	private String method = null;
	private boolean isTransient = false;

	public ValueChangeCollector()
	{
	}

	protected ValueChangeCollector(String method)
	{
		this.method = method;
	}

	/**
	 * This it the valueChange sink<br />
	 * The received event will be cloned and collected by the manager.  
	 */
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException
	{
		UIComponent valueChangeComponent = event.getComponent();
		List restoreStateCommands = new ArrayList(); 
		collectStates(restoreStateCommands, valueChangeComponent); 
		
		ValueChangeEvent clonedEvent = new ValueChangeEvent(
			event.getComponent(),
			event.getOldValue(),
			event.getNewValue());

		ValueChangeManager manager = ValueChangeManager.getManager(FacesContext
				.getCurrentInstance());
		manager.addEvent(method, clonedEvent, restoreStateCommands);
	}

	protected void collectStates(List restoreStateCommands, UIComponent component)
	{
		while (component != null)
		{
			if (component instanceof UIData)
			{
				final UIData data = (UIData) component;
				final int rowIndex = data.getRowIndex();
				
				restoreStateCommands.add(new RestoreStateCommand()
				{
					int currentRowIndex;
					
					public void saveCurrentState()
					{
						currentRowIndex = data.getRowIndex();
					}
					
					public void restoreCurrentState()
					{
						data.setRowIndex(currentRowIndex);
					}

					public void restoreEventState()
					{
						data.setRowIndex(rowIndex);
					}
				});
			}
			
			component = component.getParent(); 
		}
	}

	public Object saveState(FacesContext context)
	{
		return new Object[]
		{ this.method};
	}

	public void restoreState(FacesContext context, Object state)
	{
		Object[] o = (Object[]) state;
		
		this.method = (String) o[0];
	}

	public boolean isTransient()
	{
		return isTransient;
	}

	public void setTransient(boolean isTransient)
	{
		this.isTransient = isTransient;
	}
}