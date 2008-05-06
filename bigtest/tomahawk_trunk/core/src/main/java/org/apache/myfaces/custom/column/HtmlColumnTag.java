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
package org.apache.myfaces.custom.column;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentBodyTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlColumnTag extends HtmlComponentBodyTagBase {
    //HTML universal attributes for header
    private String _headerdir;
    private String _headerlang;
    private String _headerstyle;
    private String _headerstyleClass;
    private String _headertitle;
    private String _headercolspan;

    //HTML event handler attributes for header
    private String _headeronclick;
    private String _headerondblclick;
    private String _headeronkeydown;
    private String _headeronkeypress;
    private String _headeronkeyup;
    private String _headeronmousedown;
    private String _headeronmousemove;
    private String _headeronmouseout;
    private String _headeronmouseover;
    private String _headeronmouseup;

    //HTML universal attributes for footer
    private String _footerdir;
    private String _footerlang;
    private String _footerstyle;
    private String _footerstyleClass;
    private String _footertitle;
    private String _footercolspan;

    //HTML event handler attributes for footer
    private String _footeronclick;
    private String _footerondblclick;
    private String _footeronkeydown;
    private String _footeronkeypress;
    private String _footeronkeyup;
    private String _footeronmousedown;
    private String _footeronmousemove;
    private String _footeronmouseout;
    private String _footeronmouseover;
    private String _footeronmouseup;

    private String _width;
    private String _colspan;
    private String _groupBy;
    private String _groupByValue;

    //sort related attributes
    private String _defaultSorted;
    private String _sortable;
    private String _sortPropertyName;

    private String _columnId;

    public String getComponentType() {
        return HtmlSimpleColumn.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return null;
    }

    public void release() {
        super.release();
        _headerdir = null;
        _headerlang = null;
        _headerstyle = null;
        _headerstyleClass = null;
        _headertitle = null;
        _headercolspan = null;
        _headeronclick = null;
        _headerondblclick = null;
        _headeronkeydown = null;
        _headeronkeypress = null;
        _headeronkeyup = null;
        _headeronmousedown = null;
        _headeronmousemove = null;
        _headeronmouseout = null;
        _headeronmouseover = null;
        _headeronmouseup = null;

        _footerdir = null;
        _footerlang = null;
        _footerstyle = null;
        _footerstyleClass = null;
        _footertitle = null;
        _footercolspan = null;
        _footeronclick = null;
        _footerondblclick = null;
        _footeronkeydown = null;
        _footeronkeypress = null;
        _footeronkeyup = null;
        _footeronmousedown = null;
        _footeronmousemove = null;
        _footeronmouseout = null;
        _footeronmouseover = null;
        _footeronmouseup = null;

        _width = null;
        _colspan = null;
        _groupBy = null;
        _groupByValue = null;

        _defaultSorted = null;
        _sortable = null;
        _sortPropertyName = null;

        _columnId = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setStringProperty(component, "headerdir", _headerdir);
        setStringProperty(component, "headerlang", _headerlang);
        setStringProperty(component, "headerstyle", _headerstyle);
        setStringProperty(component, "headertitle", _headertitle);
        setStringProperty(component, "headercolspan", _headercolspan);
        setStringProperty(component, "headerstyleClass", _headerstyleClass);
        setStringProperty(component, "headeronclick", _headeronclick);
        setStringProperty(component, "headerondblclick", _headerondblclick);
        setStringProperty(component, "headeronmousedown", _headeronmousedown);
        setStringProperty(component, "headeronmouseup", _headeronmouseup);
        setStringProperty(component, "headeronmouseover", _headeronmouseover);
        setStringProperty(component, "headeronmousemove", _headeronmousemove);
        setStringProperty(component, "headeronmouseout", _headeronmouseout);
        setStringProperty(component, "headeronkeypress", _headeronkeypress);
        setStringProperty(component, "headeronkeydown", _headeronkeydown);
        setStringProperty(component, "headeronkeyup", _headeronkeyup);

        setStringProperty(component, "footerdir", _footerdir);
        setStringProperty(component, "footerlang", _footerlang);
        setStringProperty(component, "footerstyle", _footerstyle);
        setStringProperty(component, "footertitle", _footertitle);
        setStringProperty(component, "footercolspan", _footercolspan);
        setStringProperty(component, "footerstyleClass", _footerstyleClass);
        setStringProperty(component, "footeronclick", _footeronclick);
        setStringProperty(component, "footerondblclick", _footerondblclick);
        setStringProperty(component, "footeronmousedown", _footeronmousedown);
        setStringProperty(component, "footeronmouseup", _footeronmouseup);
        setStringProperty(component, "footeronmouseover", _footeronmouseover);
        setStringProperty(component, "footeronmousemove", _footeronmousemove);
        setStringProperty(component, "footeronmouseout", _footeronmouseout);
        setStringProperty(component, "footeronkeypress", _footeronkeypress);
        setStringProperty(component, "footeronkeydown", _footeronkeydown);
        setStringProperty(component, "footeronkeyup", _footeronkeyup);

        setStringProperty(component, "width", _width);
        setStringProperty(component, "colspan", _colspan);
        setBooleanProperty(component, "groupBy", _groupBy);
        setStringProperty(component, "groupByValue", _groupByValue);

        setBooleanProperty(component, "defaultSorted", _defaultSorted);
        setBooleanProperty(component, "sortable", _sortable);
        setStringProperty(component, "sortPropertyName", _sortPropertyName);

        setStringProperty(component, "columnId", _columnId);
    }

    public void setFooterdir(String footerdir) {
        _footerdir = footerdir;
    }

    public void setFooterlang(String footerlang) {
        _footerlang = footerlang;
    }

    public void setFooteronclick(String footeronclick) {
        _footeronclick = footeronclick;
    }

    public void setFooterondblclick(String footerondblclick) {
        _footerondblclick = footerondblclick;
    }

    public void setFooteronkeydown(String footeronkeydown) {
        _footeronkeydown = footeronkeydown;
    }

    public void setFooteronkeypress(String footeronkeypress) {
        _footeronkeypress = footeronkeypress;
    }

    public void setFooteronkeyup(String footeronkeyup) {
        _footeronkeyup = footeronkeyup;
    }

    public void setFooteronmousedown(String footeronmousedown) {
        _footeronmousedown = footeronmousedown;
    }

    public void setFooteronmousemove(String footeronmousemove) {
        _footeronmousemove = footeronmousemove;
    }

    public void setFooteronmouseout(String footeronmouseout) {
        _footeronmouseout = footeronmouseout;
    }

    public void setFooteronmouseover(String footeronmouseover) {
        _footeronmouseover = footeronmouseover;
    }

    public void setFooteronmouseup(String footeronmouseup) {
        _footeronmouseup = footeronmouseup;
    }

    public void setFooterstyle(String footerstyle) {
        _footerstyle = footerstyle;
    }

    public void setFooterstyleClass(String footerstyleClass) {
        _footerstyleClass = footerstyleClass;
    }

    public void setFootercolspan(String footercolspan) {
        _footercolspan = footercolspan;
    }

    public void setFootertitle(String footertitle) {
        _footertitle = footertitle;
    }

    public void setHeaderdir(String headerdir) {
        _headerdir = headerdir;
    }

    public void setHeaderlang(String headerlang) {
        _headerlang = headerlang;
    }

    public void setHeaderonclick(String headeronclick) {
        _headeronclick = headeronclick;
    }

    public void setHeaderondblclick(String headerondblclick) {
        _headerondblclick = headerondblclick;
    }

    public void setHeaderonkeydown(String headeronkeydown) {
        _headeronkeydown = headeronkeydown;
    }

    public void setHeaderonkeypress(String headeronkeypress) {
        _headeronkeypress = headeronkeypress;
    }

    public void setHeaderonkeyup(String headeronkeyup) {
        _headeronkeyup = headeronkeyup;
    }

    public void setHeaderonmousedown(String headeronmousedown) {
        _headeronmousedown = headeronmousedown;
    }

    public void setHeaderonmousemove(String headeronmousemove) {
        _headeronmousemove = headeronmousemove;
    }

    public void setHeaderonmouseout(String headeronmouseout) {
        _headeronmouseout = headeronmouseout;
    }

    public void setHeaderonmouseover(String headeronmouseover) {
        _headeronmouseover = headeronmouseover;
    }

    public void setHeaderonmouseup(String headeronmouseup) {
        _headeronmouseup = headeronmouseup;
    }

    public void setHeaderstyle(String headerstyle) {
        _headerstyle = headerstyle;
    }

    public void setHeaderstyleClass(String headerstyleClass) {
        _headerstyleClass = headerstyleClass;
    }

    public void setHeadertitle(String headertitle) {
        _headertitle = headertitle;
    }

    public void setHeadercolspan(String headercolspan) {
        _headercolspan = headercolspan;
    }

    public void setWidth(String width) {
        _width = width;
    }

    public void setColspan(String colspan) {
        _colspan = colspan;
    }

    public void setGroupBy(String groupBy) {
        _groupBy = groupBy;
    }

    public void setGroupByValue(String groupByValue) {
        this._groupByValue = groupByValue;
    }

    public void setDefaultSorted(String defaultSorted) {
        _defaultSorted = defaultSorted;
    }

    public void setSortable(String sortable) {
        _sortable = sortable;
    }

    public void setSortPropertyName(String sortPropertyName) {
        _sortPropertyName = sortPropertyName;
    }

    public String getColumnId() {
        return _columnId;
    }

    public void setColumnId(String columnId) {
        this._columnId = columnId;
    }
}