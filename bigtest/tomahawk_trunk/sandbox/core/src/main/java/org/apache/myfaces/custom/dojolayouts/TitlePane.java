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

/**
 * TitlePane Component class
 */

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

public class TitlePane extends DojoContentPane implements DojoWidget {
    // ------------------ GENERATED CODE BEGIN (do not modify!)
    // --------------------

    public static final String COMPONENT_FAMILY      = "javax.faces.Output";

    public static final String COMPONENT_TYPE        = "org.apache.myfaces.TitlePane";

    public static final String RENDERER_TYPE = "org.apache.myfaces.TitlePaneRenderer";

    private String             _containerNodeClass   = null;

    private String             _label                = null;

    private String             _labelNodeClass       = null;

     private String _widgetVar = null;
 
      private String _widgetId = null;
 
     public void setWidgetId(String widgetId)
    {
        _widgetId = widgetId;
    }

    public String getWidgetId()
    {
        if (_widgetId != null) return _widgetId;
        ValueBinding vb = getValueBinding("widgetId");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }
 
     
     public void setWidgetVar(String widgetVar)
    {
        _widgetVar = widgetVar;
    }

    public String getWidgetVar()
    {
        if (_widgetVar != null) return _widgetVar;
        ValueBinding vb = getValueBinding("widgetVar");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }
    
    public TitlePane() {
        super();
        setRendererType(RENDERER_TYPE);
    }

    public String getContainerNodeClass() {
        if (_containerNodeClass != null)
            return _containerNodeClass;
        ValueBinding vb = getValueBinding("containerNodeClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getLabel() {
        if (_label != null)
            return _label;
        ValueBinding vb = getValueBinding("label");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getLabelNodeClass() {
        if (_labelNodeClass != null)
            return _labelNodeClass;
        ValueBinding vb = getValueBinding("labelNodeClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        // //restorestate labelNodeClass begin
        _labelNodeClass = (String) values[1];
        // //restorestate labelNodeClass end
        // // restorestate containerNodeClass begin
        _containerNodeClass = (String) values[2];
        // //restorestate containerNodeClass end

        // //restorestate label begin
        _label = (String) values[3];
        // //restorestate label end

        ////restorestate widgetVar begin
        _widgetVar = (String)values[4];
        ////restorestate widgetVar end
           ////restorestate widgetId begin
           _widgetId = (String)values[5];
           ////restorestate widgetId end

        

    }
    // ------------------ GENERATED CODE END
    // ---------------------------------------

    public Object saveState(FacesContext context) {
        Object values[] = new Object[6];
        values[0] = super.saveState(context);
        ;
        // //savestate labelNodeClass begin
        values[1] = _labelNodeClass;
        // //savestate labelNodeClass end

        // //savestate containerNodeClass begin
        values[2] = _containerNodeClass;
        // //savestate containerNodeClass end

        // //savestate label begin
        values[3] = _label;
        // //savestate label end

        ////savestate widgetVar begin
        values[4] = _widgetVar;
        ////savestate widgetVar end
        
        ////savestate widgetId begin
        values[5] = _widgetId;
        ////savestate widgetId end
        
        return ((Object) (values));
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
}
