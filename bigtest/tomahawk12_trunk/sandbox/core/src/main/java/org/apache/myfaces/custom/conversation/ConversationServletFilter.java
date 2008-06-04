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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ConversationServletFilter implements Filter
{
	public final static String CONVERSATION_FILTER_CALLED = "org.apache.myfaces.conversation.ConversationServletFilter.CALLED";

	private final static ThreadLocal externalContextTL = new ThreadLocal();
	private final static ThreadLocal conversationManagerTL = new ThreadLocal();

	private ServletContext servletContext;

	public void init(FilterConfig arg0) throws ServletException
	{
		servletContext = arg0.getServletContext();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		ConversationManager conversationManager = null;

		if (!Boolean.TRUE.equals(request.getAttribute(CONVERSATION_FILTER_CALLED)))
		{
			if (request instanceof HttpServletRequest)
			{
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				externalContextTL.set(ConversationExternalContext.create(servletContext, httpRequest));

				HttpSession httpSession = httpRequest.getSession(false);
				if (httpSession != null)
				{
					conversationManager = ConversationManager.getInstance(httpSession);
					if (conversationManager != null)
					{
						conversationManagerTL.set(conversationManager);

						conversationManager.attachPersistence();
					}
				}
			}

			request.setAttribute(CONVERSATION_FILTER_CALLED, Boolean.TRUE);
		}

		boolean ok = false;
		try
		{
			chain.doFilter(request, response);
			ok = true;
		}
		finally
		{
			if (conversationManager != null)
			{
				try
				{
					if (!ok)
					{
						conversationManager.purgePersistence();
					}
					else
					{
						conversationManager.detachPersistence();
					}
				}
				finally
				{
					externalContextTL.set(null);
					conversationManagerTL.set(null);
				}
			}
		}
	}

	public void destroy()
	{
	}

	static ConversationExternalContext getConversationExternalContext()
	{
		return (ConversationExternalContext) externalContextTL.get();
	}

	static ConversationManager getConversationManager()
	{
		return (ConversationManager) conversationManagerTL.get();
	}
}
