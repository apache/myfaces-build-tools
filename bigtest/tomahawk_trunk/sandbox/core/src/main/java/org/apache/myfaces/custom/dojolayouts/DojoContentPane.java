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
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.dojo.DojoWidget;

/**
 * jsfed dojo content pane
 * 
 * @see http://www.dojotoolkit.org for further references to this control and
 *      its parameters
 * 
 * @author werpu
 * 
 */
public class DojoContentPane extends UIOutput implements DojoWidget {

    public static final String DEFAULT_COMPONENT_FAMILY = "javax.faces.Output";

    public static final String DEFAULT_COMPONENT_TYPE   = "org.apache.myfaces.DojoContentPane";

    public static final String DEFAULT_RENDERER_TYPE    = "org.apache.myfaces.DojoContentPaneRenderer";

    private Boolean            _adjustPaths             = null;

    private Boolean            _cacheContent            = null;

    private Boolean            _executeScripts          = null;

    private Boolean            _extractContent          = null;

    private String             _handler                 = null;

    private String             _href                    = null;

    private String             _layoutAlign             = null;

    private Boolean            _parseContent            = null;

    private Boolean            _preload                 = null;

    private Boolean            _refreshOnShow           = null;

    private Integer            _sizeShare               = null;

    private String             _style                   = null;

    private String             _styleClass              = null;

    private java.lang.String   _widgetId                = null;

    private String             _widgetVar               = null;

    public DojoContentPane() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public Boolean getAdjustPaths() {
        if (_adjustPaths != null)
            return _adjustPaths;
        ValueBinding vb = getValueBinding("adjustPaths");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getCacheContent() {
        if (_cacheContent != null)
            return _cacheContent;
        ValueBinding vb = getValueBinding("cacheContent");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getExecuteScripts() {
        if (_executeScripts != null)
            return _executeScripts;
        ValueBinding vb = getValueBinding("executeScripts");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getExtractContent() {
        if (_extractContent != null)
            return _extractContent;
        ValueBinding vb = getValueBinding("extractContent");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public String getFamily() {
        return DEFAULT_COMPONENT_FAMILY;
    }

    public String getHandler() {
        if (_handler != null)
            return _handler;
        ValueBinding vb = getValueBinding("handler");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getHref() {
        if (_href != null)
            return _href;
        ValueBinding vb = getValueBinding("href");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getLayoutAlign() {
        if (_layoutAlign != null)
            return _layoutAlign;
        ValueBinding vb = getValueBinding("layoutAlign");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getParseContent() {
        if (_parseContent != null)
            return _parseContent;
        ValueBinding vb = getValueBinding("parseContent");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getPreload() {
        if (_preload != null)
            return _preload;
        ValueBinding vb = getValueBinding("preload");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Boolean getRefreshOnShow() {
        if (_refreshOnShow != null)
            return _refreshOnShow;
        ValueBinding vb = getValueBinding("refreshOnShow");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Integer getSizeShare() {
        if (_sizeShare != null)
            return _sizeShare;
        ValueBinding vb = getValueBinding("sizeShare");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public String getStyle() {
        if (_style != null)
            return _style;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getStyleClass() {
        if (_style != null)
            return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getWidgetId() {
        if (_widgetId != null)
            return _widgetId;
        ValueBinding vb = getValueBinding("widgetId");
        return vb != null ? (java.lang.String) vb.getValue(getFacesContext()) : null;
    }

    public String getWidgetVar() {
        if (_widgetVar != null)
            return _widgetVar;
        ValueBinding vb = getValueBinding("widgetVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _sizeShare = (Integer) values[1];
        _style = (String) values[2];
        _styleClass = (String) values[3];
        // //restorestate adjustPaths begin
        _adjustPaths = (Boolean) values[4];
        // //restorestate adjustPaths end
        // //restorestate href begin
        _href = (String) values[5];
        // //restorestate href end
        // //restorestate extractContent begin
        _extractContent = (Boolean) values[6];
        // //restorestate extractContent end
        // //restorestate parseContent begin
        _parseContent = (Boolean) values[7];
        // //restorestate parseContent end
        // //restorestate cacheContent begin
        _cacheContent = (Boolean) values[8];
        // //restorestate cacheContent end
        // //restorestate preload begin
        _preload = (Boolean) values[9];
        // //restorestate preload end

        // //restorestate refreshOnShow begin
        _refreshOnShow = (Boolean) values[10];
        // //restorestate refreshOnShow end

        // //restorestate handler begin
        _handler = (String) values[11];
        // //restorestate handler end

        // //restorestate executeScripts begin
        _executeScripts = (Boolean) values[12];
        // //restorestate executeScripts end
        // // restorestate layoutAlign begin
        _layoutAlign = (String) values[13];
        // //restorestate layoutAlign end

        _widgetVar = (String) values[14];

        // //restorestate widgetId begin
        _widgetId = (java.lang.String) values[15];
        // //restorestate widgetId end

    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[16];
        values[0] = super.saveState(context);
        values[1] = _sizeShare;
        values[2] = _style;
        values[3] = _styleClass;

        // //savestate adjustPaths begin
        values[4] = _adjustPaths;
        // //savestate adjustPaths end

        // //savestate href begin
        values[5] = _href;
        // //savestate href end

        // //savestate extractContent begin
        values[6] = _extractContent;
        // //savestate extractContent end

        // //savestate parseContent begin
        values[7] = _parseContent;
        // //savestate parseContent end

        // //savestate cacheContent begin
        values[8] = _cacheContent;
        // //savestate cacheContent end

        // //savestate preload begin
        values[9] = _preload;
        // //savestate preload end

        // //savestate refreshOnShow begin
        values[10] = _refreshOnShow;
        // //savestate refreshOnShow end

        // //savestate handler begin
        values[11] = _handler;
        // //savestate handler end

        // //savestate executeScripts begin
        values[12] = _executeScripts;
        // //savestate executeScripts end

        // //savestate layoutAlign begin
        values[13] = _layoutAlign;
        // //savestate layoutAlign end

        values[14] = _widgetVar;

        // //savestate widgetId begin
        values[15] = _widgetId;
        // //savestate widgetId end

        return ((Object) (values));
    }

    public void setAdjustPaths(Boolean adjustPaths) {
        _adjustPaths = adjustPaths;
    }

    public void setCacheContent(Boolean cacheContent) {
        _cacheContent = cacheContent;
    }

    public void setExecuteScripts(Boolean executeScripts) {
        _executeScripts = executeScripts;
    }

    public void setExtractContent(Boolean extractContent) {
        _extractContent = extractContent;
    }

    public void setHandler(String handler) {
        _handler = handler;
    }

    public void setHref(String href) {
        _href = href;
    }

    public void setLayoutAlign(String layoutAlign) {
        _layoutAlign = layoutAlign;
    }

    public void setParseContent(Boolean parseContent) {
        _parseContent = parseContent;
    }

    public void setPreload(Boolean preload) {
        _preload = preload;
    }

    public void setRefreshOnShow(Boolean refreshOnShow) {
        _refreshOnShow = refreshOnShow;
    }

    public void setSizeShare(Integer sizeShare) {
        _sizeShare = sizeShare;
    }

    public void setStyle(String style) {
        _style = style;
    }

    public void setSTyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    public void setWidgetId(java.lang.String widgetId) {
        _widgetId = widgetId;
    }

    public void setWidgetVar(String widgetVar) {
        _widgetVar = widgetVar;
    }
}
