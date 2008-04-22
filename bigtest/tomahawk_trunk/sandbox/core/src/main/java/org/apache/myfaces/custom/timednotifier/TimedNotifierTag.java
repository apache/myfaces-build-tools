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

import org.apache.myfaces.custom.htmlTag.HtmlTagTag;

import javax.faces.component.UIComponent;



/**
 * 
 * @author werpu
 * The tag descriptor class for the timed notifier
 * 
 */
public class TimedNotifierTag extends HtmlTagTag {
    private String _confirmationMessage;

    private String _disabled;

    private String _height;

    private String _hideDelay;

    private String _okText;

    private String _showDelay;

    private String _width;

    public String getComponentType() {
        return TimedNotifier.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return TimedNotifier.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        _confirmationMessage = null;
        _disabled            = null;
        _hideDelay           = null;
        _showDelay           = null;
        _okText              = null;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this._confirmationMessage = confirmationMessage;
    }

    public void setDisabled(String disabled) {
        this._disabled = disabled;
    }

    public void setHeight(String height) {
        this._height = height;
    }

    public void setHideDelay(String hideDelay) {
        this._hideDelay = hideDelay;
    }

    public void setOkText(String okText) {
        this._okText = okText;
    }

    public void setShowDelay(String showDelay) {
        this._showDelay = showDelay;
    }

    public void setWidth(String width) {
        this._width = width;
    }

    protected void setProperties(UIComponent component) {

        super.setProperties(component);

        setStringProperty(component, "confirmationMessage", _confirmationMessage);
        setBooleanProperty(component, "disabled", _disabled);
        setIntegerProperty(component, "showDelay", _showDelay);
        setIntegerProperty(component, "hideDelay", _hideDelay);
        setStringProperty(component, "okText", _okText);
        setStringProperty(component, "width", _width);
        setStringProperty(component, "height", _height);
    }

}
