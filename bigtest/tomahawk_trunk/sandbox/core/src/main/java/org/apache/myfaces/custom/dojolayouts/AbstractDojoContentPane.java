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

import javax.faces.component.UIOutput;

import org.apache.myfaces.custom.dojo.DojoWidget;

/**
 * jsfed dojo content pane
 * 
 * @see http://www.dojotoolkit.org for further references to this control and
 *      its parameters
 * 
 * @JSFComponent
 *   name = "s:layoutingContentPane"
 *   class = "org.apache.myfaces.custom.dojolayouts.DojoContentPane"
 *   superClass = "org.apache.myfaces.custom.dojolayouts.AbstractDojoContentPane"
 *   tagClass = "org.apache.myfaces.custom.dojolayouts.DojoContentPaneTag"
 *   
 * @author werpu
 * 
 */
public abstract class AbstractDojoContentPane extends UIOutput implements DojoWidget {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String COMPONENT_TYPE   = "org.apache.myfaces.DojoContentPane";

    public static final String DEFAULT_RENDERER_TYPE    = "org.apache.myfaces.DojoContentPaneRenderer";

    /**
     * @JSFProperty
     */
    public abstract Boolean getAdjustPaths();

    /**
     * @JSFProperty
     */
    public abstract Boolean getCacheContent();

    /**
     * @JSFProperty
     */
    public abstract Boolean getExecuteScripts();

    /**
     * @JSFProperty
     */
    public abstract Boolean getExtractContent();

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    /**
     * @JSFProperty
     */
    public abstract String getHandler();

    /**
     * @JSFProperty
     */
    public abstract String getHref();

    /**
     * @JSFProperty
     */
    public abstract String getLayoutAlign();

    /**
     * @JSFProperty
     */
    public abstract Boolean getParseContent();

    /**
     * @JSFProperty
     */
    public abstract Boolean getPreload();

    /**
     * @JSFProperty
     */
    public abstract Boolean getRefreshOnShow();

    /**
     * @JSFProperty
     */
    public abstract Integer getSizeShare();

    /**
     * @JSFProperty
     */
    public abstract String getStyle();

    /**
     * @JSFProperty
     */
    public abstract String getStyleClass();

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
    public abstract String getScriptScope();
}
