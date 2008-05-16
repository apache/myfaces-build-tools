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

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlOutputTextTagBase;

/**
 * dojo content pane tag jsfied tagdescriptor for the dojo content pane
 * 
 * @see http://www.dojotoolkit.org for further references to this class
 * 
 * @author werpu
 * 
 */
public class DojoContentPaneTag extends HtmlOutputTextTagBase {

    public static final String  TAG_PARAM_AdjustPaths    = "adjustPaths";

    public static final String  TAG_PARAM_ExecuteScripts = "executeScripts";

    public static final String  TAG_PARAM_ExtractContent = "extractContent";

    public static final String  TAG_PARAM_Handler        = "handler";

    public static final String  TAG_PARAM_Href           = "href";

    public static final String  TAG_PARAM_LayoutAlign    = "layoutAlign";

    public static final String  TAG_PARAM_ParseContent   = "parseContent";

    public static final String  TAG_PARAM_Preload        = "preload";

    public static final String  TAG_PARAM_RefreshOnShow  = "refreshOnShow";

    public static final String  TAG_PARAM_ScriptScope    = "scriptScope";

    public static final String  TAG_PARAM_SIZESHARE      = "sizeShare";

    private static final String TAG_PARAM_STYLE          = "style";

    private static final String TAG_PARAM_STYLE_CLASS    = "styleClass";

    private String              _adjustPaths             = null;

    private String              _executeScripts          = null;

    private String              _extractContent          = null;

    private String              _handler                 = null;

    private String              _href                    = null;

    private String              _layoutAlign             = null;

    private String              _parseContent            = null;

    private String              _preload                 = null;

    private String              _refreshOnShow           = null;

    private String              _scriptScope             = null;

    private String              _sizeShare               = null;

    private String              _style                   = null;

    private String              _styleClass              = null;

    public static final String  TAG_PARAM_WidgetVar      = "widgetVar";

    private String              _widgetVar               = null;

    public void setWidgetVar(String widgetVar) {
        _widgetVar = widgetVar;
    }

    public static final String TAG_PARAM_WidgetId = "widgetId";

    private String             _widgetId          = null;

    public void setWidgetId(String widgetId) {
        _widgetId = widgetId;
    }

    public String getComponentType() {
        return DojoContentPane.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return DojoContentPane.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        _style = null;
        _styleClass = null;
        _sizeShare = null;

        // //release handler begin
        _handler = null;
        // //release handler end

        // //release parseContent begin
        _parseContent = null;
        // //release parseContent end
        // // release href begin
        _href = null;
        // //release href end

        // //release adjustPaths begin
        _adjustPaths = null;
        // //release adjustPaths end
        // // release extractContent begin
        _extractContent = null;
        // //release extractContent end
        // // release refreshOnShow begin
        _refreshOnShow = null;
        // //release refreshOnShow end

        // //release executeScripts begin
        _executeScripts = null;
        // //release executeScripts end

        // //release preload begin
        _preload = null;
        // //release preload end

        // //release scriptScope begin
        _scriptScope = null;
        // //release scriptScope end

        // //release layoutAlign begin
        _layoutAlign = null;
        // //release layoutAlign end

        // //release widgetVar begin
        _widgetVar = null;
        // //release widgetVar end

        // //release widgetId begin
        _widgetId = null;
        // //release widgetId end

    }

    public void setAdjustPaths(String adjustPaths) {
        _adjustPaths = adjustPaths;
    }

    public void setExecuteScripts(String executeScripts) {
        _executeScripts = executeScripts;
    }

    public void setExtractContent(String extractContent) {
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

    public void setParseContent(String parseContent) {
        _parseContent = parseContent;
    }

    public void setPreload(String preload) {
        _preload = preload;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        super.setIntegerProperty(component, TAG_PARAM_SIZESHARE, _sizeShare);
        super.setStringProperty(component, TAG_PARAM_STYLE, _style);
        super.setStringProperty(component, TAG_PARAM_STYLE_CLASS, _styleClass);

        // //setProperties refreshOnShow begin
        super.setBooleanProperty(component, TAG_PARAM_RefreshOnShow, _refreshOnShow);
        // //setProperties refreshOnShow end

        // //setProperties scriptScope begin
        super.setStringProperty(component, TAG_PARAM_ScriptScope, _scriptScope);
        // //setProperties scriptScope end

        // //setProperties preload begin
        super.setBooleanProperty(component, TAG_PARAM_Preload, _preload);
        // //setProperties preload end
        // //setProperties parseContent begin
        super.setBooleanProperty(component, TAG_PARAM_ParseContent, _parseContent);
        // //setProperties parseContent end
        // //setProperties handler begin
        super.setStringProperty(component, TAG_PARAM_Handler, _handler);
        // //setProperties handler end

        // //setProperties href begin
        super.setStringProperty(component, TAG_PARAM_Href, _href);
        // //setProperties href end

        // //setProperties extractContent begin
        super.setBooleanProperty(component, TAG_PARAM_ExtractContent, _extractContent);
        // //setProperties extractContent end

        // //setProperties adjustPaths begin
        super.setBooleanProperty(component, TAG_PARAM_AdjustPaths, _adjustPaths);
        // //setProperties adjustPaths end

        // //setProperties executeScripts begin
        super.setBooleanProperty(component, TAG_PARAM_ExecuteScripts, _executeScripts);
        // //setProperties executeScripts end
        // // setProperties layoutAlign begin
        super.setStringProperty(component, TAG_PARAM_LayoutAlign, _layoutAlign);
        // //setProperties layoutAlign end

        // //setProperties widgetId begin
        super.setStringProperty(component, TAG_PARAM_WidgetId, _widgetId);
        // //setProperties widgetId end

        // //setProperties widgetVar begin
        super.setStringProperty(component, TAG_PARAM_WidgetVar, _widgetVar);
        // //setProperties widgetVar end

    }

    public void setRefreshOnShow(String refreshOnShow) {
        _refreshOnShow = refreshOnShow;
    }

    public void setScriptScope(String scriptScope) {
        _scriptScope = scriptScope;
    }

    public void setSizeShare(String sizeShare) {
        this._sizeShare = sizeShare;
    }

    public void setStyle(String style) {
        this._style = style;
    }

    public void setStyleClass(String styleClass) {
        this._styleClass = styleClass;
    }

}
