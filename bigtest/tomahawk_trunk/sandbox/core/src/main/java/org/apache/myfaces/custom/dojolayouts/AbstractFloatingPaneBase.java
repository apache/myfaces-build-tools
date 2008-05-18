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

import org.apache.myfaces.custom.dojo.DojoWidget;

/**
 * 
 * @JSFComponent
 *   name = "s:floatingPane"
 *   class = "org.apache.myfaces.custom.dojolayouts.FloatingPaneBase"
 *   superClass = "org.apache.myfaces.custom.dojolayouts.AbstractFloatingPaneBase"
 *   parent = "javax.faces.component.UIOutput"
 *   tagClass = "org.apache.myfaces.custom.dojolayouts.FloatingPaneTag"
 *   
 *
 */
public abstract class AbstractFloatingPaneBase extends DojoContentPane implements DojoWidget {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String COMPONENT_TYPE   = "org.apache.myfaces.FloatingPaneBase";

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

    /**
     * @JSFProperty
     */
    public abstract Boolean getConstrainToContainer();

    /**
     * @JSFProperty
     */
    public abstract Boolean getDisplayCloseAction();

    /**
     * @JSFProperty
     */
    public abstract Boolean getDisplayMinimizeAction();

    /**
     * @JSFProperty
     */
    public abstract Boolean getHasShadow();

    /**
     * @JSFProperty
     */
    public abstract String getIconSrc();

    /**
     * @JSFProperty
     */
    public abstract Boolean getModal();

    /**
     * @JSFProperty
     */
    public abstract Boolean getResizable();

    /**
     * @JSFProperty
     */
    public abstract String getTaskBarId();

    /**
     * @JSFProperty
     */
    public abstract String getTitle();

    /**
     * @JSFProperty
     */
    public abstract Boolean getTitleBarDisplay();

    /**
     * @JSFProperty
     */
    public abstract String getWidgetId();

    /**
     * @JSFProperty
     */
    public abstract String getWidgetVar();

    /**
     * @JSFProperty
     */
    public abstract String getWindowState();

}
