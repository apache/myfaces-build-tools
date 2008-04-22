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

import javax.faces.component.UIComponent;

/**
 * FloatingPane Tag class
 */

/**
 * defs as defined:
 * 
 * private String _title = null; private String _iconSrc = null; private Boolean
 * _hasShadow = null; private Boolean _constrainToContainer = null; private
 * String _taskBarId = null; private Boolean _resizable = null; private Boolean
 * _titleBarDisplay = null; private String _windowState = null; private Boolean
 * _displayCloseAction = null; private Boolean _displayMinimizeAction = null;
 * private Boolean _modal = null;
 * 
 */
public class FloatingPaneTag extends DojoContentPaneTag {

    public static final String TAG_PARAM_ConstrainToContainer  = "constrainToContainer";

    public static final String TAG_PARAM_DisplayCloseAction    = "displayCloseAction";

    public static final String TAG_PARAM_DisplayMinimizeAction = "displayMinimizeAction";

    public static final String TAG_PARAM_HasShadow             = "hasShadow";

    public static final String TAG_PARAM_IconSrc               = "iconSrc";

    public static final String TAG_PARAM_Modal                 = "modal";

    public static final String TAG_PARAM_Resizable             = "resizable";

    public static final String TAG_PARAM_Style                 = "style";

    public static final String TAG_PARAM_StyleClass            = "styleClass";

    public static final String TAG_PARAM_TaskBarId             = "taskBarId";

    public static final String TAG_PARAM_Title                 = "title";

    public static final String TAG_PARAM_TitleBarDisplay       = "titleBarDisplay";

    public static final String TAG_PARAM_WidgetId              = "widgetId";

    public static final String TAG_PARAM_WidgetVar             = "widgetVar";

    public static final String TAG_PARAM_WindowState           = "windowState";

    public static final String TAG_PARAM_Visible               = "visible";
    
    private String             _constrainToContainer           = null;

    private String             _displayCloseAction             = null;

    private String             _displayMinimizeAction          = null;

    private String             _hasShadow                      = null;

    private String             _iconSrc                        = null;

    private String             _modal                          = null;

    private String             _resizable                      = null;

    private String             _style                          = null;

    private String             _styleClass                     = null;

    private String             _taskBarId                      = null;

    private String             _title                          = null;

    private String             _titleBarDisplay                = null;

    private String             _widgetId                       = null;

    private String             _widgetVar                      = null;

    private String             _windowState                    = null;

    //private String              _visible                        = null;
    
    public String getComponentType() {
        return FloatingPaneBase.DEFAULT_COMPONENT_TYPE;
    }

    public String getRendererType() {
        return FloatingPaneBase.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        // //release modal begin
        _modal = null;
        // //release modal end
        // //release displayCloseAction begin
        _displayCloseAction = null;
        // //release displayCloseAction end
        // //release displayMinimizeAction begin
        _displayMinimizeAction = null;
        // //release displayMinimizeAction end
        // //release title begin
        _title = null;
        // //release title end
        // //release IconSrc begin
        _iconSrc = null;
        // //release IconSrc end
        // //release hasShadow begin
        _hasShadow = null;
        // //release hasShadow end
        // //release constrainToContainer begin
        _constrainToContainer = null;
        // //release constrainToContainer end
        // //release taskBarId begin
        _taskBarId = null;
        // //release taskBarId end
        // //release resizable begin
        _resizable = null;
        // //release resizable end
        // //release titleBarDisplay begin
        _titleBarDisplay = null;
        // //release titleBarDisplay end
        // //release windowState begin
        _windowState = null;
        // //release windowState end

        // //release styleClass begin
        _styleClass = null;
        // //release styleClass end

        // //release style begin
        _style = null;
        // //release style end

        // //release widgetVar begin
        _widgetVar = null;
        // //release widgetVar end

        // //release widgetId begin
        _widgetId = null;
        // //release widgetId end

        //_visible = null;
    }

    public void setConstrainToContainer(String constrainToContainer) {
        _constrainToContainer = constrainToContainer;
    }

    public void setDisplayCloseAction(String displayCloseAction) {
        _displayCloseAction = displayCloseAction;
    }

    public void setDisplayMinimizeAction(String displayMinimizeAction) {
        _displayMinimizeAction = displayMinimizeAction;
    }

    public void setHasShadow(String hasShadow) {
        _hasShadow = hasShadow;
    }

    public void setIconSrc(String iconSrc) {
        _iconSrc = iconSrc;
     }

    public void setModal(String modal) {
        _modal = modal;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        // //setProperties modal begin
        super.setBooleanProperty(component, TAG_PARAM_Modal, _modal);
        // //setProperties modal end
        // //setProperties displayCloseAction begin
        super.setBooleanProperty(component, TAG_PARAM_DisplayCloseAction, _displayCloseAction);
        // //setProperties displayCloseAction end
        // //setProperties displayMinimizeAction begin
        super.setBooleanProperty(component, TAG_PARAM_DisplayMinimizeAction, _displayMinimizeAction);
        // //setProperties displayMinimizeAction end
        // //setProperties title begin
        super.setStringProperty(component, TAG_PARAM_Title, _title);
        // //setProperties title end
        // //setProperties IconSrc begin
        super.setStringProperty(component, TAG_PARAM_IconSrc, _iconSrc);
        // //setProperties IconSrc end
        // //setProperties hasShadow begin
        super.setBooleanProperty(component, TAG_PARAM_HasShadow, _hasShadow);
        // //setProperties hasShadow end
        // //setProperties constrainToContainer begin
        super.setBooleanProperty(component, TAG_PARAM_ConstrainToContainer, _constrainToContainer);
        // //setProperties constrainToContainer end
        // //setProperties taskBarId begin
        super.setStringProperty(component, TAG_PARAM_TaskBarId, _taskBarId);
        // //setProperties taskBarId end
        // //setProperties resizable begin
        super.setBooleanProperty(component, TAG_PARAM_Resizable, _resizable);
        // //setProperties resizable end
        // //setProperties titleBarDisplay begin
        super.setBooleanProperty(component, TAG_PARAM_TitleBarDisplay, _titleBarDisplay);
        // //setProperties titleBarDisplay end
        // //setProperties windowState begin
        super.setStringProperty(component, TAG_PARAM_WindowState, _windowState);
        // //setProperties windowState end

        // // setProperties styleClass begin
        super.setStringProperty(component, TAG_PARAM_StyleClass, _styleClass);
        // //setProperties styleClass end//// setProperties style begin
        super.setStringProperty(component, TAG_PARAM_Style, _style);
        // //setProperties style end

        // //setProperties widgetVar begin
        super.setStringProperty(component, TAG_PARAM_WidgetVar, _widgetVar);
        // //setProperties widgetVar end

        // //setProperties widgetId begin
        super.setStringProperty(component, TAG_PARAM_WidgetId, _widgetId);
        // //setProperties widgetId end

        //super.setBooleanProperty(component, TAG_PARAM_Visible, _visible);
    }

    public void setResizable(String resizable) {
        _resizable = resizable;
    }

    public void setStyle(String style) {
        _style = style;
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    public void setTaskBarId(String taskBarId) {
        _taskBarId = taskBarId;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public void setTitleBarDisplay(String titleBarDisplay) {
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

/*    public String getVisible() {
        return _visible;
    }

    public void setVisible(String visible) {
        this._visible = visible;
    }*/
}
