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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * Allows a component to listen for events on another component with AJAX input elements.
 * 
 * On is the id of the component you want to listen on.
 * eventType is what happened to the component, for instance "onChange"
 * action is what to do if the event occurs, default is "update".  Will be able to call arbitrary javascript functions too.
 *
 * NOTE: eventType and action are NOT implemented yet
 *
 * @JSFComponent
 *   name = "s:listener"
 *   class = "org.apache.myfaces.custom.inputAjax.Listener"
 *   superClass = "org.apache.myfaces.custom.inputAjax.AbstractListener"
 *   tagClass = "org.apache.myfaces.custom.inputAjax.ListenerTag"
 *   
 * User: Travis Reeder
 * Date: Apr 5, 2006
 * Time: 4:33:10 PM
 */
public abstract class AbstractListener extends UIComponentBase
{
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.Listener";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Listener";
    public static final String LISTENER_MAP_ENTRY = "org.apache.myfaces.Listener";

    public AbstractListener()
    {
    }

    public boolean isRendered()
    {
        return super.isRendered();
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    /**
     * Id of another component.
     * 
     * @JSFProperty
     */
    public abstract String getOn();

    /**
     * NOT IMPLEMENTED - Type of event (ie: onchange, onclick)
     * 
     * @JSFProperty
     *   defaultValue = "onChange"
     */
    public abstract String getEventType();

    /**
     * NOT IMPLEMENTED - Action to take (ie: update, submit, call (call a javascript function))
     * 
     * @JSFProperty
     *   defaultValue = "update"
     */
    public abstract String getAction();

}
