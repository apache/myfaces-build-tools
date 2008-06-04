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
package org.apache.myfaces.webapp.filter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Locale;
import java.util.Iterator;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.MalformedURLException;
import java.security.Principal;

/**
 * @author Martin Marinschek
 */
public class ExternalContextWrapper extends ExternalContext {
    private ExternalContext delegate;
    private Object request;
    private Object response;

    public ExternalContextWrapper(ExternalContext delegate, Object request, Object response) {
        this.delegate = delegate;
        this.request = request;
        this.response = response;
    }

    public void dispatch(String path) throws IOException {
        delegate.dispatch(path);
    }

    public String encodeActionURL(String url) {
        return delegate.encodeActionURL(url);
    }

    public String encodeNamespace(String name) {
        return delegate.encodeNamespace(name);
    }

    public String encodeResourceURL(String url) {
        return delegate.encodeResourceURL(url);
    }

    public Map getApplicationMap() {
        return delegate.getApplicationMap();
    }

    public String getAuthType() {
        return delegate.getAuthType();
    }

    public Object getContext() {
        return delegate.getContext();
    }

    public String getInitParameter(String name) {
        return delegate.getInitParameter(name);
    }

    public Map getInitParameterMap() {
        return delegate.getInitParameterMap();
    }

    public String getRemoteUser() {
        return delegate.getRemoteUser();
    }

    public Object getRequest() {
        return request==null?delegate.getRequest():request;
    }

    public String getRequestContextPath() {
        return delegate.getRequestContextPath();
    }

    public Map getRequestCookieMap() {
        return delegate.getRequestCookieMap();
    }

    public Map getRequestHeaderMap() {
        return delegate.getRequestHeaderMap();
    }

    public Map getRequestHeaderValuesMap() {
        return delegate.getRequestHeaderValuesMap();
    }

    public Locale getRequestLocale() {
        return delegate.getRequestLocale();
    }

    public Iterator getRequestLocales() {
        return delegate.getRequestLocales();
    }

    public Map getRequestMap() {
        return delegate.getRequestMap();
    }

    public Map getRequestParameterMap() {
        return delegate.getRequestParameterMap();
    }

    public Iterator getRequestParameterNames() {
        return delegate.getRequestParameterNames();
    }

    public Map getRequestParameterValuesMap() {
        return delegate.getRequestParameterValuesMap();
    }

    public String getRequestPathInfo() {
        return delegate.getRequestPathInfo();
    }

    public String getRequestServletPath() {
        return delegate.getRequestServletPath();
    }

    public URL getResource(String path) throws MalformedURLException {
        return delegate.getResource(path);
    }

    public InputStream getResourceAsStream(String path) {
        return delegate.getResourceAsStream(path);
    }

    public Set getResourcePaths(String path) {
        return delegate.getResourcePaths(path);
    }

    public Object getResponse() {
        return response==null?delegate.getResponse():response;
    }

    public Object getSession(boolean create) {
        return delegate.getSession(create);
    }

    public Map getSessionMap() {
        return delegate.getSessionMap();
    }

    public Principal getUserPrincipal() {
        return delegate.getUserPrincipal();
    }

    public boolean isUserInRole(String role) {
        return delegate.isUserInRole(role);
    }

    public void log(String message) {
        delegate.log(message);
    }

    public void log(String message, Throwable exception) {
        delegate.log(message, exception);
    }

    public void redirect(String url) throws IOException {
        delegate.redirect(url);
    }
    
    //Methods since 1.2
    
    public String getResponseContentType()
    {
        try
        {
            Method method = delegate.getClass().getMethod(
                    "getResponseContentType", 
                    null);
            return (String) method.invoke(delegate, null);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error calling JSF 1.2 method: "+e.getMessage());
        }
    }

    public void setRequest(java.lang.Object request)
    {
        try
        {
            Method method = delegate.getClass().getMethod(
                    "setRequest", 
                    new Class[]{java.lang.Object.class});
            method.invoke(delegate, new Object[]{request});
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error calling JSF 1.2 method: "+e.getMessage());
        }
    }

    public void setRequestCharacterEncoding(java.lang.String encoding)
        throws java.io.UnsupportedEncodingException{

        try
        {
            Method method = delegate.getClass().getMethod(
                    "setRequestCharacterEncoding", 
                    new Class[]{java.lang.String.class});
            method.invoke(delegate, new Object[]{encoding});
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error calling JSF 1.2 method: "+e.getMessage());
        }
    }
    
    public void setResponse(java.lang.Object response)
    {
        try
        {
            Method method = delegate.getClass().getMethod(
                    "setResponse", 
                    new Class[]{java.lang.Object.class});
            method.invoke(delegate, new Object[]{response});
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error calling JSF 1.2 method: "+e.getMessage());
        }
    }
    
    public void setResponseCharacterEncoding(java.lang.String encoding)
    {
        try
        {
            Method method = delegate.getClass().getMethod(
                    "setResponseCharacterEncoding", 
                    new Class[]{java.lang.String.class});
            method.invoke(delegate, new Object[]{encoding});
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error calling JSF 1.2 method: "+e.getMessage());
        }
    }

    public String getResponseCharacterEncoding()
    {
        try
        {
            Method method = delegate.getClass().getMethod(
                    "getResponseCharacterEncoding", 
                    null);
            return (String) method.invoke(delegate, null);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error calling JSF 1.2 method: "+e.getMessage());
        }
    }
        
}
