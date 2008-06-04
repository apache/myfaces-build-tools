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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;

/**
 * Manage valueChange events to be fired after the UPDATE_MODEL phase.
 * 
 * @author Mario Ivankovits <imario - at - apache.org>  (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ValueChangeManager
{
	public final static Class[] SIGNATURE = new Class[]
                                                    	{ ValueChangeEvent.class };
	private final static String VCL_MANAGER = "_VCL_MANAGER_"
			+ ValueChangeNotifierTag.class.getName();

	private List events = new ArrayList(10);

	private static class Entry
	{
		private final String method;
		private final ValueChangeEvent event;
		private final List restoreStateCommands;

		public Entry(String method,
				ValueChangeEvent event,
				List restoreStateCommands)
		{
			this.method = method;
			this.event = event;
			this.restoreStateCommands = restoreStateCommands;
		}
	}

	private ValueChangeManager()
	{
	}

	/**
	 * add a new event 
	 */
	public void addEvent(String method,
			ValueChangeEvent event,
			List restoreStateCommands)
	{
		events.add(new Entry(method, event, restoreStateCommands));
	}

	/**
	 * walk through list and fire collected events 
	 */
	public void fireEvents(FacesContext context)
	{
		try
		{
			Iterator iterEvents = events.iterator();
			while (iterEvents.hasNext())
			{
				Entry entry = (Entry) iterEvents.next();

				saveCurrentStates(entry.restoreStateCommands);
				
				try
				{
					restoreEventStates(entry.restoreStateCommands);
					
					MethodBinding mb = context.getApplication().createMethodBinding(entry.method, SIGNATURE);
					mb.invoke(context, new Object[] { entry.event });
				}
				catch (MethodNotFoundException e)
				{
					throw new FacesException(e);
				}
				catch (AbortProcessingException e)
				{
					// ignore any other value change event
					return;
				}
				finally
				{
					restoreCurrentStates(entry.restoreStateCommands);
				}
			}
		}
		finally
		{
			events.clear();			
		}
	}

	protected void saveCurrentStates(List restoreStateCommands)
	{
		for (int i = 0; i<restoreStateCommands.size(); i++)
		{
			RestoreStateCommand cmd = (RestoreStateCommand) restoreStateCommands.get(i);
			cmd.saveCurrentState();
		}
	}
	
	protected void restoreCurrentStates(List restoreStateCommands)
	{
		for (int i = restoreStateCommands.size()-1; i>=0; i--)
		{
			RestoreStateCommand cmd = (RestoreStateCommand) restoreStateCommands.get(i);
			cmd.restoreCurrentState();
		}
	}

	protected void restoreEventStates(List restoreStateCommands)
	{
		for (int i = restoreStateCommands.size()-1; i>=0; i--)
		{
			RestoreStateCommand cmd = (RestoreStateCommand) restoreStateCommands.get(i);
			cmd.restoreEventState();
		}
	}

	/**
	 * check if the current request has a manager.<br />
	 * The current request has a manager if, and only if it
	 * collected valueChange events 
	 */
	public static boolean hasManager(FacesContext context)
	{
		Map requestMap = context.getExternalContext().getRequestMap();
		return requestMap.get(VCL_MANAGER) != null;
	}

	/**
	 * get the manager for this request. Create one if needed. 
	 */
	public static ValueChangeManager getManager(FacesContext context)
	{
		Map requestMap = context.getExternalContext().getRequestMap();
		ValueChangeManager manager = (ValueChangeManager) requestMap
				.get(VCL_MANAGER);
		if (manager == null)
		{
			manager = new ValueChangeManager();
			requestMap.put(VCL_MANAGER, manager);
		}

		return manager;
	}
}