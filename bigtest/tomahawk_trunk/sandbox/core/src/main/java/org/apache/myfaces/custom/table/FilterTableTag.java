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
package org.apache.myfaces.custom.table;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl
 */
public class FilterTableTag extends HtmlComponentTagBase {

    public static final String HEAD_CLASS_ATTR = "headClass";
    public static final String TBODY_CLASS_ATTR = "tbodyClass";
    public static final String MULTIPLE_SELECT_ATTR = "multiple";
    public static final String ALETERNATE_ROWS_ATTR = "alternateRows";
    public static final String MAX_SELECTABLE_ATTR = "maxSelectable";
    public static final String CELLPADING_ATTR = "cellpadding";
    public static final String CELLSPACING_ATTR = "cellspacing";
    public static final String BORDER_ATTR = "border";

    private String _var;
    private String _styleClass;
    private String _headClass;
    private String _tbodyClass;
    private String _multiple;
    private String _alternateRows;
    private String _maxSortable;
    private String _cellpadding;
    private String _cellspacing;
    private String _border;

    public String getComponentType() {
        return FilterTable.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return FilterTableRenderer.RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setStringProperty(component, JSFAttr.VAR_ATTR, _var);
        setStringProperty(component, JSFAttr.STYLE_CLASS_ATTR, _styleClass);
        setStringProperty(component, HEAD_CLASS_ATTR, _headClass);
        setStringProperty(component, TBODY_CLASS_ATTR, _tbodyClass);
        setBooleanProperty(component, MULTIPLE_SELECT_ATTR, _multiple);
        setBooleanProperty(component, ALETERNATE_ROWS_ATTR, _alternateRows);
        setIntegerProperty(component, MAX_SELECTABLE_ATTR, _maxSortable);
        setIntegerProperty(component, CELLPADING_ATTR, _cellpadding);
        setIntegerProperty(component, CELLSPACING_ATTR, _cellspacing);
        setIntegerProperty(component, BORDER_ATTR, _border);
    }

    public void setVar(String var) {
        _var = var;
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    public void setHeadClass(String headClass) {
        _headClass = headClass;
    }

    public void setTbodyClass(String tbodyClass) {
        _tbodyClass = tbodyClass;
    }

    public void setMultiple(String multiple) {
        _multiple = multiple;
    }

    public void setAlternateRows(String alternateRows) {
        _alternateRows = alternateRows;
    }

    public void setMaxSortable(String maxSortable) {
        _maxSortable = maxSortable;
    }

    public void setCellpadding(String cellpadding) {
        _cellpadding = cellpadding;
    }

    public void setCellspacing(String cellspacing) {
        _cellspacing = cellspacing;
    }

    public void setBorder(String border) {
        _border = border;
    }

    public void release() {
        super.release();
        _var = null;
        _styleClass = null;
        _headClass = null;
        _tbodyClass = null;
        _multiple = null;
        _alternateRows = null;
        _maxSortable = null;
        _cellpadding = null;
        _cellspacing = null;
        _border = null;
    }
}
