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
 *   name = "s:layoutingSplitPane"
 *   class = "org.apache.myfaces.custom.dojolayouts.DojoSplitPane"
 *   superClass = "org.apache.myfaces.custom.dojolayouts.AbstractDojoSplitPane"
 *   parent = "javax.faces.component.UIOutput"
 *   tagClass = "org.apache.myfaces.custom.dojolayouts.DojoSplitPaneTag"
 *   
 *
 */
public abstract class AbstractDojoSplitPane extends DojoContentPane implements DojoWidget {

    public static final String COMPONENT_TYPE        = "org.apache.myfaces.DojoSplitPane";

    public static final String COMPONENT_FAMILY      = "javax.faces.Output";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DojoSplitPaneRenderer";

     /**
      * @JSFProperty
      */
    public abstract String getWidgetId();

    /**
     * @JSFProperty
     */
    public abstract java.lang.String getWidgetVar();

    /**
     * @JSFProperty
     */
    public abstract String getOrientation();

    /**
     * @JSFProperty
     */
    public abstract Integer getSizerWidth();

    /**
     * @JSFProperty
     */
    public abstract Integer getActiveSizing();

    /**
     * @JSFProperty
     */
    public abstract Boolean getPersist();

    /**
     * @JSFProperty
     */
    public abstract Integer getLastPoint();

    /**
     * @JSFProperty
     */
    public abstract Integer getStartPoint();
    
    /**
     * @JSFProperty
     */
    public String getStyle(){
        return super.getStyle();
    }

    /**
     * @JSFProperty
     */
    public String getStyleClass(){
        return super.getStyleClass();
    }
    
    /**
     * @JSFProperty
     */
    public Integer getSizeShare(){
        return super.getSizeShare();
    }
    
}
