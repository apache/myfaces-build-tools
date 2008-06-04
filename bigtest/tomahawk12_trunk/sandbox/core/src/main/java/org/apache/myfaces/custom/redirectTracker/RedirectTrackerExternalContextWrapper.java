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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Locale;
import java.util.Iterator;
import java.util.Set;
import java.net.URL;
import java.net.MalformedURLException;
import java.security.Principal;

/**
 * wraps a external context
 */
public class RedirectTrackerExternalContextWrapper extends ExternalContext
{
	private final ExternalContext original;

	public RedirectTrackerExternalContextWrapper(ExternalContext original)
	{
		this.original = original;
	}

	public void dispatch(String s)
		throws IOException
	{
		original.dispatch(s);
	}

	public String encodeActionURL(String s)
	{
		return original.encodeActionURL(s);
	}

	public String encodeNamespace(String s)
	{
		return original.encodeNamespace(s);
	}

	public String encodeResourceURL(String s)
	{
		return original.encodeResourceURL(s);
	}

	public Map getApplicationMap()
	{
		return original.getApplicationMap();
	}

	public String getAuthType()
	{
		return original.getAuthType();
	}

	public Object getContext()
	{
		return original.getContext();
	}

	public String getInitParameter(String s)
	{
		return original.getInitParameter(s);
	}

	public Map getInitParameterMap()
	{
		return original.getInitParameterMap();
	}

	public String getRemoteUser()
	{
		return original.getRemoteUser();
	}

	public Object getRequest()
	{
		return original.getRequest();
	}

	public String getRequestContextPath()
	{
		return original.getRequestContextPath();
	}

	public Map getRequestCookieMap()
	{
		return original.getRequestCookieMap();
	}

	public Map getRequestHeaderMap()
	{
		return original.getRequestHeaderMap();
	}

	public Map getRequestHeaderValuesMap()
	{
		return original.getRequestHeaderValuesMap();
	}

	public Locale getRequestLocale()
	{
		return original.getRequestLocale();
	}

	public Iterator getRequestLocales()
	{
		return original.getRequestLocales();
	}

	public Map getRequestMap()
	{
		return original.getRequestMap();
	}

	public Map getRequestParameterMap()
	{
		return original.getRequestParameterMap();
	}

	public Iterator getRequestParameterNames()
	{
		return original.getRequestParameterNames();
	}

	public Map getRequestParameterValuesMap()
	{
		return original.getRequestParameterValuesMap();
	}

	public String getRequestPathInfo()
	{
		return original.getRequestPathInfo();
	}

	public String getRequestServletPath()
	{
		return original.getRequestServletPath();
	}

	public URL getResource(String s)
		throws MalformedURLException
	{
		return original.getResource(s);
	}

	public InputStream getResourceAsStream(String s)
	{
		return original.getResourceAsStream(s);
	}

	public Set getResourcePaths(String s)
	{
		return original.getResourcePaths(s);
	}

	public Object getResponse()
	{
		return original.getResponse();
	}

	public Object getSession(boolean b)
	{
		return original.getSession(b);
	}

	public Map getSessionMap()
	{
		return original.getSessionMap();
	}

	public Principal getUserPrincipal()
	{
		return original.getUserPrincipal();
	}

	public boolean isUserInRole(String s)
	{
		return original.isUserInRole(s);
	}

	public void log(String s)
	{
		original.log(s);
	}

	public void log(String s, Throwable throwable)
	{
		original.log(s, throwable);
	}

	public void redirect(String url) throws IOException
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();

		RedirectTrackerManager manager = RedirectTrackerManager.getInstance(facesContext);
		if (manager != null)
		{
			url = manager.trackRedirect(facesContext, url);
		}

		original.redirect(url);
	}

	public ExternalContext getWrappedExternalContext()
	{
		return original;
	}
}
