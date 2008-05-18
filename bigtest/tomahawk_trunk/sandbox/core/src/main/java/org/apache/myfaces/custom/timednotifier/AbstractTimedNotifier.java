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
package org.apache.myfaces.custom.timednotifier;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;


/**
 * 
 * @JSFComponent
 *   name = "s:timedNotifier"
 *   class = "org.apache.myfaces.custom.timednotifier.TimedNotifier"
 *   superClass = "org.apache.myfaces.custom.timednotifier.AbstractTimedNotifier"
 *   tagClass = "org.apache.myfaces.custom.timednotifier.TimedNotifierTag"
 *   
 * @author werpu
 * Shows a Timed notifier
 * 
 */
public abstract class AbstractTimedNotifier extends UIOutput {
    public static final String  COMPONENT_TYPE        = "org.apache.myfaces.TimedNotifier";
    public static final String  DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TimedNotifierRenderer";
    private static final String DEFAULT_MESSAGE       = "Ok";
    private static final String CONTENT_FACET_NAME    = "content";
    private static final String CONFIRM_FACET_NAME    = "confirm";

    /**
     * @JSFFacet
     */
    public UIComponent getConfirm() {
        return (UIComponent) getFacets().get(CONFIRM_FACET_NAME);
    }

    /**
     * @JSFProperty
     *   defaultValue = "Ok"
     */
    public abstract String getConfirmationMessage();

    /**
     * @JSFFacet
     */
    public UIComponent getContent() {
        return (UIComponent) getFacets().get(CONTENT_FACET_NAME);
    }

    /**
     * @JSFProperty
     */
    public abstract Boolean getDisabled();

    /**
     * @JSFProperty
     */
    public abstract String getHeight();

    /**
     * @JSFProperty
     *   defaultValue="Integer.valueOf(-1)"
     */
    public abstract Integer getHideDelay();

    /**
     * @JSFProperty
     *   defaultValue="Ok"
     */
    public abstract String getOkText();

    /**
     * @JSFProperty
     *   defaultValue="Integer.valueOf(0)"
     */
    public abstract Integer getShowDelay();

    /**
     * @JSFProperty
     *   defaultValue="dojoTimedNotifierDialog"
     */
    public abstract String getStyleClass();

    /**
     * @JSFProperty
     *   defaultValue="\"\""
     */
    public abstract String getWidth();


    public void setConfirm(UIComponent confirm) {
        getFacets().put(CONFIRM_FACET_NAME, confirm);
    }

    public void setContent(UIComponent content) {
        getFacets().put(CONTENT_FACET_NAME, content);
    }

}
