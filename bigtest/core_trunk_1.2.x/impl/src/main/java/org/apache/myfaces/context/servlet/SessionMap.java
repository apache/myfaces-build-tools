/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.myfaces.shared_impl.util.NullEnumeration;
import org.apache.myfaces.util.AbstractAttributeMap;


/**
 * HttpSession attibutes as Map.
 *
 * @author Anton Koinov (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SessionMap extends AbstractAttributeMap<Object>
{
    private final HttpServletRequest _httpRequest;

    SessionMap(HttpServletRequest httpRequest)
    {
        _httpRequest = httpRequest;
    }

    @Override
    protected Object getAttribute(String key)
    {
        HttpSession httpSession = getSession();
        return (httpSession == null) ? null : httpSession.getAttribute(key.toString());
    }

    @Override
    protected void setAttribute(String key, Object value)
    {
        _httpRequest.getSession(true).setAttribute(key, value);
    }

    @Override
    protected void removeAttribute(String key)
    {
        HttpSession httpSession = getSession();
        if (httpSession != null)
        {
            httpSession.removeAttribute(key);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Enumeration<String> getAttributeNames()
    {
        HttpSession httpSession = getSession();
        return (httpSession == null) ? NullEnumeration.instance() : httpSession.getAttributeNames();
    }

    private HttpSession getSession()
    {
        return _httpRequest.getSession(false);
    }

    @Override
    public void putAll(Map t)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * This will clear the session without invalidation. If no session has been created, it will simply return.
     */
    @Override
    public void clear()
    {
        HttpSession session = getSession();
        if (session == null)
            return;
        for (Enumeration attributeNames = session.getAttributeNames(); attributeNames.hasMoreElements();)
        {
            String attributeName = (String) attributeNames.nextElement();
            session.removeAttribute(attributeName);
        }
    }
}