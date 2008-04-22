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

package org.apache.myfaces.custom.dojolayouts;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.dojo.DojoWidget;

public class FloatingPaneBase extends DojoContentPane implements DojoWidget {

    public static final String DEFAULT_COMPONENT_FAMILY = "javax.faces.Output";

    public static final String DEFAULT_COMPONENT_TYPE   = "org.apache.myfaces.FloatingPaneBase";

    public static final String DEFAULT_RENDERER_TYPE    = "org.apache.myfaces.FloatingPaneBaseRenderer";

    private Boolean            _constrainToContainer    = null;

    private Boolean            _displayCloseAction      = null;

    private Boolean            _displayMinimizeAction   = null;

    private Boolean            _hasShadow               = null;

    private String             _iconSrc                 = null;

    private Boolean            _modal                   = null;

    private Boolean            _resizable               = null;

    private String             _taskBarId               = null;

    private String             _title                   = null;

    private Boolean            _titleBarDisplay         = null;

    private String             _widgetId                = null;

    private String             _widgetVar               = null;

    private String             _windowState             = null;

    
    
    
    public FloatingPaneBase() {
        super();
        setRendererType(FloatingPaneBase.DEFAULT_RENDERER_TYPE);
    }

    public String getComponentType() {
        return FloatingPaneBase.DEFAULT_COMPONENT_TYPE;
    }

    public Boolean getConstrainToContainer() {
        if (_constrainToContainer != null)
            return _constrainToContainer;
        ValueBinding vb = getValueBinding("constrainToContainer");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getDisplayCloseAction() {
        if (_displayCloseAction != null)
            return _displayCloseAction;
        ValueBinding vb = getValueBinding("displayCloseAction");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getDisplayMinimizeAction() {
        if (_displayMinimizeAction != null)
            return _displayMinimizeAction;
        ValueBinding vb = getValueBinding("displayMinimizeAction");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public String getFamily() {
        return FloatingPaneBase.DEFAULT_COMPONENT_FAMILY;
    }

    public Boolean getHasShadow() {
        if (_hasShadow != null)
            return _hasShadow;
        ValueBinding vb = getValueBinding("hasShadow");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public String getIconSrc() {
        if (_iconSrc != null)
            return _iconSrc;
        ValueBinding vb = getValueBinding("iconSrc");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getModal() {
        if (_modal != null)
            return _modal;
        ValueBinding vb = getValueBinding("modal");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public String getRendererType() {
        return FloatingPaneBase.DEFAULT_RENDERER_TYPE;
    }

    public Boolean getResizable() {
        if (_resizable != null)
            return _resizable;
        ValueBinding vb = getValueBinding("resizable");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public String getTaskBarId() {
        if (_taskBarId != null)
            return _taskBarId;
        ValueBinding vb = getValueBinding("taskBarId");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getTitle() {
        if (_title != null)
            return _title;
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getTitleBarDisplay() {
        if (_titleBarDisplay != null)
            return _titleBarDisplay;
        ValueBinding vb = getValueBinding("titleBarDisplay");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public String getWidgetId() {
        if (_widgetId != null)
            return _widgetId;
        ValueBinding vb = getValueBinding("widgetId");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getWidgetVar() {
        if (_widgetVar != null)
            return _widgetVar;
        ValueBinding vb = getValueBinding("widgetVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getWindowState() {
        if (_windowState != null)
            return _windowState;
        ValueBinding vb = getValueBinding("windowState");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);

        // //restorestate modal begin
        _modal = (Boolean) values[11];
        // //restorestate modal end

        // //restorestate displayMinimizeAction begin
        _displayMinimizeAction = (Boolean) values[10];
        // //restorestate displayMinimizeAction end

        // //restorestate displayCloseAction begin
        _displayCloseAction = (Boolean) values[9];
        // //restorestate displayCloseAction end

        // //restorestate windowState begin
        _windowState = (String) values[8];
        // //restorestate windowState end

        // //restorestate titleBarDisplay begin
        _titleBarDisplay = (Boolean) values[7];
        // //restorestate titleBarDisplay end

        // //restorestate resizable begin
        _resizable = (Boolean) values[6];
        // //restorestate resizable end

        // //restorestate taskBarId begin
        _taskBarId = (String) values[5];
        // //restorestate taskBarId end

        // //restorestate constrainToContainer begin
        _constrainToContainer = (Boolean) values[4];
        // //restorestate constrainToContainer end

        // //restorestate hasShadow begin
        _hasShadow = (Boolean) values[3];
        // //restorestate hasShadow end

        // //restorestate iconSrc begin
        _iconSrc = (String) values[2];
        // //restorestate iconSrc end

        // //restorestate title begin
        _title = (String) values[1];
        // //restorestate title end
        // //restorestate widgetId begin
        _widgetId = (String) values[12];
        // //restorestate widgetId end
        // // restorestate widgetVar begin
        _widgetVar = (String) values[13];
        // //restorestate widgetVar end

    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[14];
        values[0] = super.saveState(context);

        // //savestate title begin
        values[1] = _title;
        // //savestate title end
        // //savestate iconSrc begin
        values[2] = _iconSrc;
        // //savestate iconSrc end
        // //savestate hasShadow begin
        values[3] = _hasShadow;
        // //savestate hasShadow end
        // //savestate constrainToContainer begin
        values[4] = _constrainToContainer;
        // //savestate constrainToContainer end
        // //savestate taskBarId begin
        values[5] = _taskBarId;
        // //savestate taskBarId end
        // //savestate resizable begin
        values[6] = _resizable;
        // //savestate resizable end
        // //savestate titleBarDisplay begin
        values[7] = _titleBarDisplay;
        // //savestate titleBarDisplay end
        // //savestate windowState begin
        values[8] = _windowState;
        // //savestate windowState end
        // //savestate displayCloseAction begin
        values[9] = _displayCloseAction;
        // //savestate displayCloseAction end
        // //savestate displayMinimizeAction begin
        values[10] = _displayMinimizeAction;
        // //savestate displayMinimizeAction end

        // //savestate modal begin
        values[11] = _modal;
        // //savestate modal end

        // //savestate widgetId begin
        values[12] = _widgetId;
        // //savestate widgetId end

        // //savestate widgetVar begin
        values[13] = _widgetVar;
        // //savestate widgetVar end

        return values;
    }

    public void setConstrainToContainer(Boolean constrainToContainer) {
        _constrainToContainer = constrainToContainer;
    }

    public void setDisplayCloseAction(Boolean displayCloseAction) {
        _displayCloseAction = displayCloseAction;
    }

    public void setDisplayMinimizeAction(Boolean displayMinimizeAction) {
        _displayMinimizeAction = displayMinimizeAction;
    }

    public void setHasShadow(Boolean hasShadow) {
        _hasShadow = hasShadow;
    }

    public void setIconSrc(String iconSrc) {
        _iconSrc = iconSrc;
    }

    public void setModal(Boolean modal) {
        _modal = modal;
    }

    public void setResizable(Boolean resizable) {
        _resizable = resizable;
    }

    public void setTaskBarId(String taskBarId) {
        _taskBarId = taskBarId;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public void setTitleBarDisplay(Boolean titleBarDisplay) {
        _titleBarDisplay = titleBarDisplay;
    }

    public void setWidgetId(String widgetId) {
        _widgetId = widgetId;
    }

    public void setWidgetVar(String widgetVar) {
        _widgetVar = widgetVar;
    }

    public void setWindowState(String windowState) {
        _windowState = windowState;
    }

}
