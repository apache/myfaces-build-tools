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
package org.apache.myfaces.custom.form;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlFormTagBase;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 *
 */
public class HtmlFormTag extends HtmlFormTagBase
{
    private String _scheme;
    private String _serverName;
    private String _port;
    private String _method;
    private String _action;

    public String getComponentType()
    {
        return HtmlForm.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.Form";
    }

    /**
     * @see org.apache.myfaces.shared_tomahawk.taglib.html.HtmlFormTagBase#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, "scheme", _scheme);
        setStringProperty(component, "serverName", _serverName);
        setIntegerProperty(component, "port", _port);
        setStringProperty(component,"action",_action);
        setStringProperty(component,"method",_method);
    }

    /**
     * @see org.apache.myfaces.shared_tomahawk.taglib.html.HtmlFormTagBase#release()
     */
    public void release()
    {
        super.release();
        _scheme = null;
        _serverName = null;
        _port = null;
        _action = null;
        _method = null;
    }

    public void setPort(String port)
    {
        _port = port;
    }

    public void setScheme(String scheme)
    {
        _scheme = scheme;
    }

    public void setServerName(String serverName)
    {
        _serverName = serverName;
    }

    public void setMethod(String method)
    {
        _method = method;
    }

    public void setAction(String action)
    {
        _action = action;
    }

}
