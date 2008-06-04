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

package org.apache.myfaces.custom.requestParameterProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Thomas Obereder
 * @version 27.04.2006 22:42:32
 */
public class RequestParameterProviderManager implements Serializable
{
    private static final Log LOG = LogFactory.getLog(RequestParameterProviderManager.class);

    private static final String PAGE_PARAMETER_SEP = "?";
    private static final String PARAMETER_SEP = "&";
    private static final String PARAMETER_PROVIDER_MANAGER_KEY = "org.apache.myfaces.RequestParameterProviderManager";

    private List providers;


    private RequestParameterProviderManager()
    {
        providers = new ArrayList();
    }

    public static RequestParameterProviderManager getInstance()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null)
        {
            throw new IllegalStateException("no faces context available!");
        }
        return getInstance(context);
    }

    public static RequestParameterProviderManager getInstance(FacesContext context)
    {
        RequestParameterProviderManager manager =
                (RequestParameterProviderManager) context.getExternalContext().getSessionMap().get(PARAMETER_PROVIDER_MANAGER_KEY);

        if (manager == null)
        {
            manager = new RequestParameterProviderManager();
            context.getExternalContext().getSessionMap().put(PARAMETER_PROVIDER_MANAGER_KEY, manager);
        }

        return manager;
    }


    /**
     * Register the given provider.
     * @param provider the provider to register.
     */

    public void register(RequestParameterProvider provider)
    {
        if(provider == null)
            LOG.warn("RequestParameterProvider is null -> no registration!");
        else
            this.providers.add(provider);
    }


    /**
     * Encode all fields of all providers, and attach the name-value pairs to url.
     * @param url the URL to which the fields should be attached.
     * @return the url after attaching all fields.
     */

    public String encodeAndAttachParameters(String url)
    {
    		if (!isFilterCalled())
    		{
    			throw new IllegalStateException("RequestParameterServletFilter not called. Please configure the filter " + RequestParameterServletFilter.class.getName() + " in your web.xml to cover your faces requests.");
    		}

        StringBuffer sb = new StringBuffer();
        if(url == null)
        {
            LOG.warn("URL is null -> empty string is returned.");
            return sb.toString();
        }

        int nuofParams = -1;
        String firstSeparator = url.indexOf(PAGE_PARAMETER_SEP) == -1 ? PAGE_PARAMETER_SEP : PARAMETER_SEP;
        sb.append(url);
        for (Iterator it = providers.iterator(); it.hasNext();)
        {
            RequestParameterProvider provider = (RequestParameterProvider) it.next();
            String[] fields = provider.getFields();
            if (fields == null)
            {
            	continue;
            }
            for (int i = 0; i < fields.length; i++)
            {
            	nuofParams++;

                sb.append(nuofParams == 0 ? firstSeparator : PARAMETER_SEP);
                sb.append(fields[i]);
                sb.append("=");
                sb.append(provider.getFieldValue(fields[i]));
            }
        }
        return sb.toString();
    }


    /**
     * Check if there are any providers registered.
     * @return true, if the list is not null and not empty.
     */
    public boolean hasProviders()
    {
        return this.providers != null && !this.providers.isEmpty();
    }

    public boolean isFilterCalled()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null)
        {
            throw new IllegalStateException("no faces context available!");
        }

        return Boolean.TRUE.equals(context.getExternalContext().getRequestMap().get(RequestParameterServletFilter.REQUEST_PARAM_FILTER_CALLED));
    }

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		// the conversation manager is not (yet) serializable, we just implement it
		// to make it work with distributed sessions
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		// nothing written, so nothing to read
	}

	private Object readResolve() throws ObjectStreamException
	{
		// do not return a real object, that way on first request a new manager will be created
		return null;
	}
}
