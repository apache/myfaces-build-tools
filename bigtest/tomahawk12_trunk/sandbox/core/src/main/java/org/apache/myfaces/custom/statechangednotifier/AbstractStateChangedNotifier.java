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



/**
 * A component that listens to changes in the components
 * 
 * Shows a confirmation window if some of the input fields of the form have changed its value
 * 
 * @JSFComponent
 *   name = "s:stateChangedNotifier"
 *   class = "org.apache.myfaces.custom.statechangednotifier.StateChangedNotifier"
 *   superClass = "org.apache.myfaces.custom.statechangednotifier.AbstractStateChangedNotifier"
 *   tagClass = "org.apache.myfaces.custom.statechangednotifier.StateChangedNotifierTag"
 *   
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractStateChangedNotifier extends javax.faces.component.html.HtmlInputHidden {
    
    public static final String COMPONENT_TYPE        = "org.apache.myfaces.StateChangedNotifier";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.StateChangedNotifierRenderer";

    private static final String DEFAULT_MESSAGE = "Are you sure?";

    /**
     * enable the state changed notification for this cycle
     *
     */
    public void enableStateChanged() {
        super.setValue("true");
    }

    /**
     * Confirmation message to show if something in the form has changed
     * 
     * @JSFProperty
     *   defaultValue="Are you sure?"
     */
    public abstract String getConfirmationMessage();

    /**
     * If disabled, the confirmation window will not be shown
     * 
     * @JSFProperty
     */
    public abstract Boolean getDisabled();

    /**
     * Comma-separated list of cliend id of the commands which will be excluded 
     * from the confirmation message
     * 
     * @JSFProperty
     */
    public abstract String getExcludedIds();

    /**
     * a helper reset to reset the notifier
     * to a non state changed state
     */
    public void reset() {
        super.setValue("false");
    }

}
