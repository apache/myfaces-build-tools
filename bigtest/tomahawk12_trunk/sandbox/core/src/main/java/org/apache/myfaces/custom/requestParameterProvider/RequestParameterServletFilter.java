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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Thomas Obereder
 * @version 30.04.2006 14:38:23
 * 
 * Once moved to tomahawk, we can get rid of this filter and add its functionality to the default ExtensionsFilter.
 */
public class RequestParameterServletFilter implements Filter
{
	public final static String REQUEST_PARAM_FILTER_CALLED = RequestParameterServletFilter.class.getName() + ".CALLED";
	
    public RequestParameterServletFilter()
    {
    }

    public void init(FilterConfig filterConfig)
    {
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException
    {
        if(servletResponse instanceof HttpServletResponse)
        {
        	HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    		if (!Boolean.TRUE.equals(httpServletRequest.getAttribute(REQUEST_PARAM_FILTER_CALLED)))
    		{
    			httpServletRequest.setAttribute(REQUEST_PARAM_FILTER_CALLED, Boolean.TRUE);
    			servletResponse = new RequestParameterResponseWrapper((HttpServletResponse) servletResponse);
    		}
        }
        
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy()
    {
    }
}
