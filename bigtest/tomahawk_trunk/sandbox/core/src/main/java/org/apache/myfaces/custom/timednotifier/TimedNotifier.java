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
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * 
 * @author werpu
 * Shows a Timed notifier
 * 
 */
public class TimedNotifier extends UIOutput {
    public static final String  COMPONENT_TYPE        = "org.apache.myfaces.TimedNotifier";
    public static final String  DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TimedNotifierRenderer";
    private static final String DEFAULT_MESSAGE       = "Ok";
    private static final String CONTENT_FACET_NAME    = "content";
    private static final String CONFIRM_FACET_NAME    = "confirm";


    private String  confirmationMessage = DEFAULT_MESSAGE;
    private Boolean disabled            = null;
    private String  height              = null;
    private Integer hideDelay           = null;
    private String  okText              = null;
    private Integer showDelay           = null;
    private String  styleClass          = null;
    private String  width               = null;


    public TimedNotifier() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public UIComponent getConfirm() {
        return (UIComponent) getFacets().get(CONFIRM_FACET_NAME);
    }

    public String getConfirmationMessage() {

        if (confirmationMessage != null)
            return confirmationMessage;

        ValueBinding vb = getValueBinding("confirmationMessage");

        return (vb != null) ? (String) vb.getValue(getFacesContext()) : null;
    }

    public UIComponent getContent() {
        return (UIComponent) getFacets().get(CONTENT_FACET_NAME);
    }

    public Boolean getDisabled() {

        if (disabled != null)
            return disabled;

        ValueBinding vb = getValueBinding("disabled");

        return (vb != null) ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public String getHeight() {

        if (height != null)
            return height;

        ValueBinding vb = getValueBinding("height");

        return (vb != null) ? (String) vb.getValue(getFacesContext()) : "";

    }

    public Integer getHideDelay() {

        if (hideDelay != null)
            return hideDelay;

        ValueBinding vb = getValueBinding("hideDelay");

        return (vb != null) ? (Integer) vb.getValue(getFacesContext()) : new Integer(-1);
    }

    public String getOkText() {

        if (okText != null)
            return okText;

        ValueBinding vb = getValueBinding("okText");

        return (vb != null) ? (String) vb.getValue(getFacesContext()) : "Ok";
    }

    public Integer getShowDelay() {

        if (showDelay != null)
            return showDelay;

        ValueBinding vb = getValueBinding("showDelay");

        return (vb != null) ? (Integer) vb.getValue(getFacesContext()) : new Integer(0);
    }

    public String getStyleClass() {

        if (styleClass != null)
            return styleClass;

        ValueBinding vb = getValueBinding("styleClass");

        return (vb != null) ? (String) vb.getValue(getFacesContext()) : "dojoTimedNotifierDialog";
    }

    public String getWidth() {

        if (width != null)
            return width;

        ValueBinding vb = getValueBinding("width");

        return (vb != null) ? (String) vb.getValue(getFacesContext()) : "";

    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        this.confirmationMessage = (String) values[1];
        this.disabled            = (Boolean) values[2];
        this.showDelay           = (Integer) values[3];
        this.hideDelay           = (Integer) values[4];
        this.styleClass          = (String) values[5];
        this.width               = (String) values[6];
        this.height              = (String) values[7];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[8];
        values[0] = super.saveState(context);
        values[1] = confirmationMessage;
        values[2] = disabled;
        values[3] = showDelay;
        values[4] = hideDelay;
        values[5] = styleClass;
        values[6] = width;
        values[7] = height;

        return values;
    }

    public void setConfirm(UIComponent confirm) {
        getFacets().put(CONFIRM_FACET_NAME, confirm);
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }


    public void setContent(UIComponent content) {
        getFacets().put(CONTENT_FACET_NAME, content);
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setHideDelay(Integer hideDelay) {
        this.hideDelay = hideDelay;
    }

    public void setOkText(String okText) {
        this.okText = okText;
    }

    public void setShowDelay(Integer showDelay) {
        this.showDelay = showDelay;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
