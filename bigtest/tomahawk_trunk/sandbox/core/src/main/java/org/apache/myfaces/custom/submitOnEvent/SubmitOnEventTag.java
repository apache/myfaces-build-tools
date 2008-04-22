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
package org.apache.myfaces.custom.submitOnEvent;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * Attach an event handler to an input element or use a global event handler to
 * submit a form by "clicking" on a link or button
 * 
 * @author Mario Ivankovits (imario -at - apache.org)
 */
public class SubmitOnEventTag extends UIComponentTagBase
{
    private String forComponent;
    private String event;
    private String callback;

    public String getComponentType()
    {
        return SubmitOnEvent.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return SubmitOnEvent.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "for", forComponent);
        setStringProperty(component, "event", event);
        setStringProperty(component, "callback", callback);
    }

    public String getFor()
    {
        return forComponent;
    }

    public void setFor(String forComponent)
    {
        this.forComponent = forComponent;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public String getCallback()
    {
        return callback;
    }

    public void setCallback(String callback)
    {
        this.callback = callback;
    }
}