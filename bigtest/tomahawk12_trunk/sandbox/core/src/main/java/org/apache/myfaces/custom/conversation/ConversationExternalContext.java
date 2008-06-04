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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

/**
 * we need some information we normally get from the FacesContext.getExternalContext BEFORE the FacesContext is active.
 * So we require our own ExternalContext.
 */
public class ConversationExternalContext
{
	private final Map requestMap;
	private final Map requestParameterMap;
	private final Map initParameterMap;

	protected ConversationExternalContext(Map initParameterMap, Map requestMap, Map requestParameterMap)
	{
		this.requestMap = requestMap;
		this.requestParameterMap = requestParameterMap;
		this.initParameterMap = initParameterMap;
	}

	public static ConversationExternalContext create(final ServletContext servletContext, final HttpServletRequest httpRequest)
	{
		Map initParameterMap = new FakeMap()
		{
			public boolean containsKey(Object key)
			{
				return servletContext.getInitParameter((String) key) != null;
			}

			public Object get(Object key)
			{
				return servletContext.getInitParameter((String) key);
			}
		};

		Map requestParameterMap = new FakeMap()
		{
			public boolean containsKey(Object key)
			{
				return httpRequest.getParameter((String) key) != null;
			}

			public Object get(Object key)
			{
				return httpRequest.getParameter((String) key);
			}
		};

		Map requestMap = new FakeMap()
		{
			public boolean containsKey(Object key)
			{
				return httpRequest.getAttribute((String) key) != null;
			}

			public Object get(Object key)
			{
				return httpRequest.getAttribute((String) key);
			}

			public Object put(Object key, Object value)
			{
				Object prev = httpRequest.getAttribute((String) key);
				httpRequest.setAttribute((String) key, value);
				return prev;
			}
		};

		return new ConversationExternalContext(initParameterMap, requestMap, requestParameterMap);
	}

	public Map getRequestMap()
	{
		return requestMap;
	}

	public Map getRequestParameterMap()
	{
		return requestParameterMap;
	}

	public Map getInitParameterMap()
	{
		return initParameterMap;
	}
}
