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
package org.apache.myfaces.custom.redirectTracker;

import javax.faces.context.FacesContext;

/**
 * Provides a context for the RedirectTrackerPolicy
 */
public class RedirectTrackerContext
{
	private final RedirectTrackerManager manager;
	private final RedirectTrackerManager.Entry entry;
	private final FacesContext context;

	RedirectTrackerContext(RedirectTrackerManager manager, RedirectTrackerManager.Entry entry, FacesContext context)
	{
		this.entry = entry;
		this.context = context;
		this.manager = manager;
	}

	/**
	 * saves all request beans
	 */
	public void saveBeans()
	{
		manager.saveBeans(entry);
	}

	/**
	 * saves the given object with the given name, after the redirect this value will be reinjected into the request.
	 */
	public void saveBean(String name, Object value)
	{
		manager.saveBean(entry, name, value);
	}

	/**
	 * saves all messages
	 */
	public void saveMessages()
	{
		manager.saveMessages(context, entry);
	}

	/**
	 * saves the locale information
	 */
	public void saveLocale()
	{
		manager.saveLocale(context, entry);
	}
}
