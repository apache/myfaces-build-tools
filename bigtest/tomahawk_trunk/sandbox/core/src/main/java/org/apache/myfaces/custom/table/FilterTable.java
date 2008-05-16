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

import javax.faces.component.UIData;
import javax.faces.el.ValueBinding;
import javax.faces.context.FacesContext;

/**
 * @JSFComponent
 *   name = "s:filterTable"
 *   tagClass = "org.apache.myfaces.custom.table.FilterTableTag"
 *   
 * @author Thomas Spiegl
 */
public class FilterTable extends UIData {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.FilterTable";

    private String _styleClass = null;
    private String _headClass;
    private String _tbodyClass;
    private Boolean _multiple;
    private Boolean _alternateRows;
    private Integer _maxSortable;
    private Integer _cellpadding;
    private Integer _cellspacing;
    private Integer _border;

    public String getStyleClass() {
        if (_styleClass != null) return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    public String getHeadClass() {
        if (_headClass != null) return _headClass;
        ValueBinding vb = getValueBinding("headClass");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setHeadClass(String headClass) {
        _headClass = headClass;
    }

    public String getTbodyClass() {
        if (_tbodyClass != null) return _tbodyClass;
        ValueBinding vb = getValueBinding("tbodyClass");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setTbodyClass(String tbodyClass) {
        _tbodyClass = tbodyClass;
    }

    public Boolean getMultiple() {
        if (_multiple != null) return _multiple;
        ValueBinding vb = getValueBinding("enableMultipleSelect");
        return vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
    }

    public void setMultiple(Boolean multiple) {
        _multiple = multiple;
    }

    public Boolean getAlternateRows() {
        if (_alternateRows != null) return _alternateRows;
        ValueBinding vb = getValueBinding("enableAlternateRows");
        return vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
    }

    public void setAlternateRows(Boolean alternateRows) {
        _alternateRows = alternateRows;
    }

    public Integer getMaxSortable() {
        if (_maxSortable != null) return _maxSortable;
        ValueBinding vb = getValueBinding("maxSortable");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }

    public void setMaxSortable(Integer maxSortable) {
        _maxSortable = maxSortable;
    }

    public Integer getCellpadding() {
        if (_cellpadding != null) return _cellpadding;
        ValueBinding vb = getValueBinding("cellpadding");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }

    public void setCellpadding(Integer cellpadding) {
        _cellpadding = cellpadding;
    }

    public Integer getCellspacing() {
        if (_cellspacing != null) return _cellspacing;
        ValueBinding vb = getValueBinding("cellspacing");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }

    public void setCellspacing(Integer cellspacing) {
        _cellspacing = cellspacing;
    }

    public Integer getBorder() {
        if (_border != null) return _border;
        ValueBinding vb = getValueBinding("border");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }

    public void setBorder(Integer border) {
        _border = border;
    }

    public Object saveState(FacesContext context) {
        Object[] obj = new Object[11];
        obj[0] = super.saveState(context);
        obj[2] = _styleClass;
        obj[3] = _headClass;
        obj[4] = _tbodyClass;
        obj[5] = _multiple;
        obj[6] = _alternateRows;
        obj[7] = _maxSortable;
        obj[8] = _cellpadding;
        obj[9] = _cellspacing;
        obj[10] = _border;
        return obj;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] obj = (Object[]) state;
        super.restoreState(context, obj[0]);
        _styleClass = (String) obj[2];
        _headClass = (String) obj[3];
        _tbodyClass = (String) obj[4];
        _multiple = (Boolean) obj[5];
        _alternateRows = (Boolean) obj[6];
        _maxSortable = (Integer) obj[7];
        _cellpadding = (Integer) obj[8];
        _cellspacing = (Integer) obj[9];
        _border = (Integer) obj[10];
    }
}
