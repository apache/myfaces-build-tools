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

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentBodyTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl
 */
public class SortableColumnTag extends HtmlComponentBodyTagBase {

    private static final String FIELD_ATTR = "field";
    private static final String DATA_TYPE_ATTR = "dataType";
    private static final String SORT_ATTR = "sort";
    private static final String FORMAT_ATTR = "format";
    private static final String ALIGN_ATTR = "align";
    private static final String VALIGN_ATTR = "valign";
    private static final String TEXT_ATTR = "text";
    private static final String ESCAPE_ATTR = "escape";

    private String _field;
    private String _dataType;
    private String _sort;
    private String _format;
    private String _align;
    private String _valign;
    private String _text;
    private String _escape;

    public String getComponentType() {
        return SortableColumn.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setStringProperty(component, FIELD_ATTR, _field);
        setStringProperty(component, DATA_TYPE_ATTR, _dataType);
        setStringProperty(component, SORT_ATTR, _sort);
        setStringProperty(component, FORMAT_ATTR, _format);
        setStringProperty(component, ALIGN_ATTR, _align);
        setStringProperty(component, VALIGN_ATTR, _valign);
        setStringProperty(component, TEXT_ATTR, _text);
        setBooleanProperty(component, ESCAPE_ATTR, _escape);
    }

    public String getField() {
        return _field;
    }

    public void setField(String field) {
        _field = field;
    }

    public String getDataType() {
        return _dataType;
    }

    public void setDataType(String dataType) {
        _dataType = dataType;
    }

    public String getSort() {
        return _sort;
    }

    public void setSort(String sort) {
        _sort = sort;
    }

    public void setFormat(String format) {
        _format = format;
    }

    public void setAlign(String align) {
        _align = align;
    }

    public void setValign(String valign) {
        _valign = valign;
    }

    public void setText(String text) {
        _text = text;
    }

    public void setEscape(String escape) {
        _escape = escape;
    }

    public void release() {
        super.release();
        _field = null;
        _dataType = null;
        _sort = null;
        _format = null;
        _align = null;
        _valign = null;
        _text = null;
        _escape = null;
    }
}
