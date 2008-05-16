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

import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponentBase;
import javax.faces.el.ValueBinding;
import javax.faces.context.FacesContext;

/**
 * Attach an event handler to an input element or use a global event handler to
 * submit a form by "clicking" on a link or button
 *
 * @JSFComponent
 *   name = "s:submitOnEvent"
 *   tagClass = "org.apache.myfaces.custom.submitOnEvent.SubmitOnEventTag"
 *   
 * @author Mario Ivankovits (imario -at - apache.org)
 */
public class SubmitOnEvent extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SubmitOnEvent";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SubmitOnEvent";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.SubmitOnEvent";

    private String forComponent;
    private String event;
    private String callback;

    public SubmitOnEvent()
    {
        super.setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }


    public String getFor()
    {
        if (forComponent != null)
        {
            return forComponent;
        }

        ValueBinding vb = getValueBinding("for");

        return (vb != null) ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setFor(String forComponent)
    {
        this.forComponent = forComponent;
    }

    public String getEvent()
    {
        if (event != null)
        {
            return event;
        }

        ValueBinding vb = getValueBinding("event");

        return (vb != null) ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public String getCallback()
    {
        if (callback != null)
        {
            return callback;
        }

        ValueBinding vb = getValueBinding("callback");

        return (vb != null) ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setCallback(String callback)
    {
        this.callback = callback;
    }

    public Object saveState(FacesContext context)
    {
        return new Object[]
            {
                super.saveState(context),
                forComponent,
                event,
                callback
            };
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object[] states = (Object[]) state;
        super.restoreState(context, states[0]);
        forComponent = (String) states[1];
        event = (String) states[2];
        callback = (String) states[3];
    }
}
