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

public class TitlePaneTag extends DojoContentPaneTag {

    public static final String TAG_PARAM_ContainerNodeClass = "containerNodeClass";

    public static final String TAG_PARAM_Label              = "label";

    public static final String TAG_PARAM_LabelNodeClass     = "labelNodeClass";

    private String             _containerNodeClass          = null;

    private String             _label                       = null;

    private String             _labelNodeClass              = null;

    public static final String TAG_PARAM_WidgetVar = "widgetVar";
    
    private String       _widgetVar         = null;
    
    public void setWidgetVar(String widgetVar) {
        _widgetVar = widgetVar;
    }

    
       
    
    public static final String TAG_PARAM_WidgetId = "widgetId";
    
    private String       _widgetId         = null;
    
    public void setWidgetId(String widgetId) {
        _widgetId = widgetId;
    }

 
    
    public String getComponentType() {
        return TitlePane.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return TitlePane.RENDERER_TYPE;
    }


    public void release() {
        super.release();
        // //release containerNodeClass begin
        _containerNodeClass = null;
        // //release containerNodeClass end
        // //release labelNodeClass begin
        _labelNodeClass = null;
        // //release labelNodeClass end

        // //release label begin
        _label = null;
        // //release label end

        ////release widgetVar begin
        _widgetVar = null;
        ////release widgetVar end

        
        ////release widgetId begin
        _widgetId = null;
        ////release widgetId end

    }

    public void setContainerNodeClass(String containerNodeClass) {
        _containerNodeClass = containerNodeClass;
    }

 
    public void setLabel(String label) {
        _label = label;
    }

    public void setLabelNodeClass(String labelNodeClass) {
        _labelNodeClass = labelNodeClass;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        // // setProperties containerNodeClass begin
        super.setStringProperty(component, TAG_PARAM_ContainerNodeClass, _containerNodeClass);
        // //setProperties containerNodeClass end
        // //setProperties labelNodeClass begin
        super.setStringProperty(component, TAG_PARAM_LabelNodeClass, _labelNodeClass);
        // //setProperties labelNodeClass end
        // // setProperties label begin
        super.setStringProperty(component, TAG_PARAM_Label, _label);
        // //setProperties label end
        ////setProperties widgetVar begin
        super.setStringProperty(component, TAG_PARAM_WidgetVar, _widgetVar );
        ////setProperties widgetVar end

      
        
        ////setProperties widgetId begin
        super.setStringProperty(component, TAG_PARAM_WidgetId, _widgetId );
        ////setProperties widgetId end


    }
}
