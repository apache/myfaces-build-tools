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

/*
 * //faces config definition, please cut/paste it into your faces-config
 * <component> <component-type>org.apache.myfaces.TitlePane</component-type>
 * <component-class>org.apache.myfaces.custom.dojolayout.TitlePane</component-class>
 * </component> <renderer> <component-family>javax.faces.Output</component-family>
 * <renderer-type>javax.faces.Panel</renderer-type>
 * <renderer-class>org.apache.myfaces.custom.dojolayout.TitlePaneRenderer</renderer-class>
 * </renderer>
 * 
 */

/**
 * TitlePane Component class
 *  
 * @JSFComponent
 *   name = "s:layoutingTitlePane"
 *   class = "org.apache.myfaces.custom.dojolayouts.TitlePane"
 *   superClass = "org.apache.myfaces.custom.dojolayouts.AbstractTitlePane"
 *   parent = "javax.faces.component.UIOutput"
 *   tagClass = "org.apache.myfaces.custom.dojolayouts.TitlePaneTag"
 *   
 */
public abstract class AbstractTitlePane extends DojoContentPane implements DojoWidget {
    // ------------------ GENERATED CODE BEGIN (do not modify!)
    // --------------------

    public static final String COMPONENT_FAMILY      = "javax.faces.Output";

    public static final String COMPONENT_TYPE        = "org.apache.myfaces.TitlePane";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TitlePaneRenderer";

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
    public abstract String getContainerNodeClass();

    /**
     * @JSFProperty
     */
    public abstract String getLabel();

    /**
     * @JSFProperty
     */
    public abstract String getLabelNodeClass();

}
