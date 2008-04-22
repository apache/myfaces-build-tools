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
package org.apache.myfaces.custom.inputAjax;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagUtils;

/**
 * User: Travis Reeder
 * Date: Apr 5, 2006
 * Time: 4:14:07 PM
 */
public class ListenerTag extends UIComponentTag
{
    private String _on;
    private String _eventType;
    private String _action;

    public void release()
    {
        super.release();
        _on = null;
        _eventType = null;
        _action = null;
    }

    public String getComponentType()
    {
        return Listener.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return null;
    }

    protected void setProperties(UIComponent uiComponent)
    {
        super.setProperties(uiComponent);
        UIComponentTagUtils.setStringProperty(getFacesContext(), uiComponent, "on", _on);
        UIComponentTagUtils.setStringProperty(getFacesContext(), uiComponent, "eventType", _eventType);
        UIComponentTagUtils.setStringProperty(getFacesContext(), uiComponent, "action", _action);
    }

    public String getOn()
    {
        return _on;
    }

    public void setOn(String on)
    {
        _on = on;
    }

    public String getEventType()
    {
        return _eventType;
    }

    public void setEventType(String eventType)
    {
        _eventType = eventType;
    }

    public String getAction()
    {
        return _action;
    }

    public void setAction(String action)
    {
        _action = action;
    }
}
