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
 * 
 * @JSFComponent
 *   name = "s:layoutingSplitPane"
 *   tagClass = "org.apache.myfaces.custom.dojolayouts.DojoSplitPaneTag"
 *   
 *
 */
public class DojoSplitPane extends DojoContentPane implements DojoWidget {

    public static final String COMPONENT_TYPE        = "org.apache.myfaces.DojoSplitPane";

    public static final String COMPONENT_FAMILY      = "javax.faces.Output";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DojoSplitPaneRenderer";

    private String             _orientation          = null;

    private Integer            _sizerWidth           = null;

    private Integer            _activeSizing         = null;

    private Boolean            _persist              = null;

    private Integer            _startPoint           = null;

    private Integer            _lastPoint            = null;

    private String   _widgetVar            = null;

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
 
    
    public void setWidgetVar(java.lang.String widgetVar) {
        _widgetVar = widgetVar;
    }

    public java.lang.String getWidgetVar() {
        if (_widgetVar != null)
            return _widgetVar;
        ValueBinding vb = getValueBinding("widgetVar");
        return vb != null ? (java.lang.String) vb.getValue(getFacesContext()) : null;
    }

    public DojoSplitPane() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void setOrientation(String orientation) {
        _orientation = orientation;
    }

    public String getOrientation() {
        if (_orientation != null)
            return _orientation;
        ValueBinding vb = getValueBinding("splitOrientation");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setSizerWidth(Integer sizerWidth) {
        _sizerWidth = sizerWidth;
    }

    public Integer getSizerWidth() {
        if (_sizerWidth != null)
            return _sizerWidth;
        ValueBinding vb = getValueBinding("sizerWidth");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setActiveSizing(Integer activeSizing) {
        _activeSizing = activeSizing;
    }

    public Integer getActiveSizing() {
        if (_activeSizing != null)
            return _activeSizing;
        ValueBinding vb = getValueBinding("activeSizing");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setPersist(Boolean persist) {
        _persist = persist;
    }

    public Boolean getPersist() {
        if (_persist != null)
            return _persist;
        ValueBinding vb = getValueBinding("persist");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public void setLastPoint(Integer lastPoint) {
        _lastPoint = lastPoint;
    }

    public Integer getLastPoint() {
        if (_lastPoint != null)
            return _lastPoint;
        ValueBinding vb = getValueBinding("lastPoint");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setStartPoint(Integer startPoint) {
        _startPoint = startPoint;
    }

    public Integer getStartPoint() {
        if (_startPoint != null)
            return _startPoint;
        ValueBinding vb = getValueBinding("startPoint");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[9];
        values[0] = super.saveState(context);
        values[1] = _orientation;
        values[2] = _sizerWidth;
        values[3] = _activeSizing;
        values[4] = _persist;

        // //savestate startPoint begin
        values[5] = _startPoint;
        // //savestate startPoint end

        // //savestate lastPoint begin
        values[6] = _lastPoint;
        // //savestate lastPoint end

        // //savestate widgetVar begin
        values[7] = _widgetVar;
        // //savestate widgetVar end

        ////savestate widgetId begin
        values[8] = _widgetId;
        ////savestate widgetId end

        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _orientation = (String) values[1];
        _sizerWidth = (Integer) values[2];
        _activeSizing = (Integer) values[3];
        _persist = (Boolean) values[4];

        // //restorestate startPoint begin
        _startPoint = (Integer) values[5];
        // //restorestate startPoint end

        // //restorestate lastPoint begin
        _lastPoint = (Integer) values[6];
        // //restorestate lastPoint end

        // //restorestate widgetVar begin
        _widgetVar = (java.lang.String) values[7];
        // //restorestate widgetVar end

        ////restorestate widgetId begin
        _widgetId = (String)values[8];
        ////restorestate widgetId end


    }
}
