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
package org.apache.myfaces.custom.statechangednotifier;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputHiddenTagBase;




/**
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StateChangedNotifierTag extends HtmlInputHiddenTagBase {
    private String confirmationMessage;
    private String disabled;
    private String excludedIds;

    public String getComponentType() {
        return StateChangedNotifier.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return StateChangedNotifier.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        confirmationMessage = null;
        disabled            = null;
        excludedIds         = null;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setExcludedIds(String excludedIds) {
        this.excludedIds = excludedIds;
    }

    protected void setProperties(UIComponent component) {

        super.setProperties(component);

        setStringProperty(component, "confirmationMessage", confirmationMessage);
        setBooleanProperty(component, "disabled", disabled);
        setStringProperty(component, "excludedIds", excludedIds);

    }
}
