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
package org.apache.myfaces.custom.crosstable;

import org.apache.myfaces.custom.column.HtmlColumn;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlColumns extends UIColumns implements HtmlColumn {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlColumns";

    private String _width;

    //HTML universal attributes for header
    private String _headerdir;
    private String _headerlang;
    private String _headerstyle;
    private String _headerstyleClass;
    private String _headertitle;

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

    //HTML universal attributes for row
    private String _dir;
    private String _lang;
    private String _style;
    private String _styleClass;
    private String _title;

    //HTML event handler attributes for
    private String _onclick;
    private String _ondblclick;
    private String _onkeydown;
    private String _onkeypress;
    private String _onkeyup;
    private String _onmousedown;
    private String _onmousemove;
    private String _onmouseout;
    private String _onmouseover;
    private String _onmouseup;

    private Boolean _groupBy;
	private String _groupByValue;
    private Boolean _defaultSorted;
    private Boolean _sortable;
    private String _sortPropertyName;


    /**
     *
     */
    public HtmlColumns() {
        setRendererType(null);
    }

    public String getWidth() {
    	return (String) getLocalOrValueBindingValue(_width, "width");
    }

    public void setWidth(String width) {
        _width = width;
    }

    /**
     * @param localValue
     * @param valueBindingName
     */
    private Object getLocalOrValueBindingValue(Object localValue,
                                               String valueBindingName) {
        if (localValue != null)
            return localValue;
        ValueBinding vb = getValueBinding(valueBindingName);
        return vb != null ? vb.getValue(getFacesContext()) : null;
    }

    public String getFooterdir() {
        return (String) getLocalOrValueBindingValue(_footerdir, "footerdir");
    }

    public void setFooterdir(String footerdir) {
        _footerdir = footerdir;
    }

    public String getFooterlang() {
        return (String) getLocalOrValueBindingValue(_footerlang, "footerlang");
    }

    public void setFooterlang(String footerlang) {
        _footerlang = footerlang;
    }

    public String getFooteronclick() {
        return (String) getLocalOrValueBindingValue(_footeronclick,
                                                    "footeronclick");
    }

    public void setFooteronclick(String footeronclick) {
        _footeronclick = footeronclick;
    }

    public String getFooterondblclick() {
        return (String) getLocalOrValueBindingValue(_footerondblclick,
                                                    "footerondblclick");
    }

    public void setFooterondblclick(String footerondblclick) {
        _footerondblclick = footerondblclick;
    }

    public String getFooteronkeydown() {
        return (String) getLocalOrValueBindingValue(_footeronkeydown,
                                                    "footeronkeydown");
    }

    public void setFooteronkeydown(String footeronkeydown) {
        _footeronkeydown = footeronkeydown;
    }

    public String getFooteronkeypress() {
        return (String) getLocalOrValueBindingValue(_footeronkeypress,
                                                    "footeronkeypress");
    }

    public void setFooteronkeypress(String footeronkeypress) {
        _footeronkeypress = footeronkeypress;
    }

    public String getFooteronkeyup() {
        return (String) getLocalOrValueBindingValue(_footeronkeyup,
                                                    "footeronkeyup");
    }

    public void setFooteronkeyup(String footeronkeyup) {
        _footeronkeyup = footeronkeyup;
    }

    public String getFooteronmousedown() {
        return (String) getLocalOrValueBindingValue(_footeronmousedown,
                                                    "footeronmousedown");
    }

    public void setFooteronmousedown(String footeronmousedown) {
        _footeronmousedown = footeronmousedown;
    }

    public String getFooteronmousemove() {
        return (String) getLocalOrValueBindingValue(_footeronmousemove,
                                                    "footeronmousemove");
    }

    public void setFooteronmousemove(String footeronmousemove) {
        _footeronmousemove = footeronmousemove;
    }

    public String getFooteronmouseout() {
        return (String) getLocalOrValueBindingValue(_footeronmouseout,
                                                    "footeronmouseout");
    }

    public void setFooteronmouseout(String footeronmouseout) {
        _footeronmouseout = footeronmouseout;
    }

    public String getFooteronmouseover() {
        return (String) getLocalOrValueBindingValue(_footeronmouseover,
                                                    "footeronmouseover");
    }

    public void setFooteronmouseover(String footeronmouseover) {
        _footeronmouseover = footeronmouseover;
    }

    public String getFooteronmouseup() {
        return (String) getLocalOrValueBindingValue(_footeronmouseup,
                                                    "footeronmouseup");
    }

    public void setFooteronmouseup(String footeronmouseup) {
        _footeronmouseup = footeronmouseup;
    }

    public String getFooterstyle() {
        return (String) getLocalOrValueBindingValue(_footerstyle, "footerstyle");
    }

    public void setFooterstyle(String footerstyle) {
        _footerstyle = footerstyle;
    }

    public String getFooterstyleClass() {
        return (String) getLocalOrValueBindingValue(_footerstyleClass,
                                                    "footerstyleClass");
    }

    public void setFooterstyleClass(String footerstyleClass) {
        _footerstyleClass = footerstyleClass;
    }

    public String getFootertitle() {
        return (String) getLocalOrValueBindingValue(_footertitle, "footertitle");
    }

    public void setFootertitle(String footertitle) {
        _footertitle = footertitle;
    }

    public String getHeaderdir() {
        return (String) getLocalOrValueBindingValue(_headerdir, "headerdir");
    }

    public void setHeaderdir(String headerdir) {
        _headerdir = headerdir;
    }

    public String getHeaderlang() {
        return (String) getLocalOrValueBindingValue(_headerlang, "headerlang");
    }

    public void setHeaderlang(String headerlang) {
        _headerlang = headerlang;
    }

    public String getHeaderonclick() {
        return (String) getLocalOrValueBindingValue(_headeronclick,
                                                    "headeronclick");
    }

    public void setHeaderonclick(String headeronclick) {
        _headeronclick = headeronclick;
    }

    public String getHeaderondblclick() {
        return (String) getLocalOrValueBindingValue(_headerondblclick,
                                                    "headerondblclick");
    }

    public void setHeaderondblclick(String headerondblclick) {
        _headerondblclick = headerondblclick;
    }

    public String getHeaderonkeydown() {
        return (String) getLocalOrValueBindingValue(_headeronkeydown,
                                                    "headeronkeydown");
    }

    public void setHeaderonkeydown(String headeronkeydown) {
        _headeronkeydown = headeronkeydown;
    }

    public String getHeaderonkeypress() {
        return (String) getLocalOrValueBindingValue(_headeronkeypress,
                                                    "headeronkeypress");
    }

    public void setHeaderonkeypress(String headeronkeypress) {
        _headeronkeypress = headeronkeypress;
    }

    public String getHeaderonkeyup() {
        return (String) getLocalOrValueBindingValue(_headeronkeyup,
                                                    "headeronkeyup");
    }

    public void setHeaderonkeyup(String headeronkeyup) {
        _headeronkeyup = headeronkeyup;
    }

    public String getHeaderonmousedown() {
        return (String) getLocalOrValueBindingValue(_headeronmousedown,
                                                    "headeronmousedown");
    }

    public void setHeaderonmousedown(String headeronmousedown) {
        _headeronmousedown = headeronmousedown;
    }

    public String getHeaderonmousemove() {
        return (String) getLocalOrValueBindingValue(_headeronmousemove,
                                                    "headeronmousemove");
    }

    public void setHeaderonmousemove(String headeronmousemove) {
        _headeronmousemove = headeronmousemove;
    }

    public String getHeaderonmouseout() {
        return (String) getLocalOrValueBindingValue(_headeronmouseout,
                                                    "headeronmouseout");
    }

    public void setHeaderonmouseout(String headeronmouseout) {
        _headeronmouseout = headeronmouseout;
    }

    public String getHeaderonmouseover() {
        return (String) getLocalOrValueBindingValue(_headeronmouseover,
                                                    "headeronmouseover");
    }

    public void setHeaderonmouseover(String headeronmouseover) {
        _headeronmouseover = headeronmouseover;
    }

    public String getHeaderonmouseup() {
        return (String) getLocalOrValueBindingValue(_headeronmouseup,
                                                    "headeronmouseup");
    }

    public void setHeaderonmouseup(String headeronmouseup) {
        _headeronmouseup = headeronmouseup;
    }

    public String getHeaderstyle() {
        return (String) getLocalOrValueBindingValue(_headerstyle, "headerstyle");
    }

    public void setHeaderstyle(String headerstyle) {
        _headerstyle = headerstyle;
    }

    public String getHeaderstyleClass() {
        return (String) getLocalOrValueBindingValue(_headerstyleClass,
                                                    "headerstyleClass");
    }

    public void setHeaderstyleClass(String headerstyleClass) {
        _headerstyleClass = headerstyleClass;
    }

    public String getHeadertitle() {
        return (String) getLocalOrValueBindingValue(_headertitle, "headertitle");
    }

    public void setHeadertitle(String headertitle) {
        _headertitle = headertitle;
    }

    public String getDir() {
        return (String) getLocalOrValueBindingValue(_dir, "dir");
    }

    public void setDir(String dir) {
        _dir = dir;
    }

    public String getLang() {
        return (String) getLocalOrValueBindingValue(_lang, "lang");
    }

    public void setLang(String lang) {
        _lang = lang;
    }

    public String getOnclick() {
        return (String) getLocalOrValueBindingValue(_onclick, "onclick");
    }

    public void setOnclick(String onclick) {
        _onclick = onclick;
    }

    public String getOndblclick() {
        return (String) getLocalOrValueBindingValue(_ondblclick, "ondblclick");
    }

    public void setOndblclick(String ondblclick) {
        _ondblclick = ondblclick;
    }

    public String getOnkeydown() {
        return (String) getLocalOrValueBindingValue(_onkeydown, "onkeydown");
    }

    public void setOnkeydown(String onkeydown) {
        _onkeydown = onkeydown;
    }

    public String getOnkeypress() {
        return (String) getLocalOrValueBindingValue(_onkeypress, "onkeypress");
    }

    public void setOnkeypress(String onkeypress) {
        _onkeypress = onkeypress;
    }

    public String getOnkeyup() {
        return (String) getLocalOrValueBindingValue(_onkeyup, "onkeyup");
    }

    public void setOnkeyup(String onkeyup) {
        _onkeyup = onkeyup;
    }

    public String getOnmousedown() {
        return (String) getLocalOrValueBindingValue(_onmousedown, "onmousedown");
    }

    public void setOnmousedown(String onmousedown) {
        _onmousedown = onmousedown;
    }

    public String getOnmousemove() {
        return (String) getLocalOrValueBindingValue(_onmousemove, "onmousemove");
    }

    public void setOnmousemove(String onmousemove) {
        _onmousemove = onmousemove;
    }

    public String getOnmouseout() {
        return (String) getLocalOrValueBindingValue(_onmouseout, "onmouseout");
    }

    public void setOnmouseout(String onmouseout) {
        _onmouseout = onmouseout;
    }

    public String getOnmouseover() {
        return (String) getLocalOrValueBindingValue(_onmouseover, "onmouseover");
    }

    public void setOnmouseover(String onmouseover) {
        _onmouseover = onmouseover;
    }

    public String getOnmouseup() {
        return (String) getLocalOrValueBindingValue(_onmouseup, "onmouseup");
    }

    public void setOnmouseup(String onmouseup) {
        _onmouseup = onmouseup;
    }

    public String getStyle() {
        return (String) getLocalOrValueBindingValue(_style, "style");
    }

    public void setStyle(String style) {
        _style = style;
    }

    public String getStyleClass() {
        return (String) getLocalOrValueBindingValue(_styleClass, "styleClass");
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    public String getTitle() {
        return (String) getLocalOrValueBindingValue(_title, "title");
    }

    public void setTitle(String title) {
        _title = title;
    }

    public boolean isGroupBy() {
        if (_groupBy != null) return _groupBy.booleanValue();
        ValueBinding vb = getValueBinding("groupBy");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public void setGroupBy(Boolean groupBy) {
        _groupBy = groupBy;
    }

	public Object getGroupByValue() {
		if (_groupByValue != null) return _groupByValue;
		ValueBinding vb = getValueBinding("groupByValue");
		return vb != null ? vb.getValue(getFacesContext()) : null;
	}

	public void setGroupByValue(String groupByValue) {
		_groupByValue = groupByValue;
	}

	public boolean isDefaultSorted() {
        if (_defaultSorted != null) return _defaultSorted.booleanValue();
        ValueBinding vb = getValueBinding("defaultSorted");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public void setDefaultSorted(Boolean defaultSorted) {
        _defaultSorted = defaultSorted;
    }

    public boolean isSortable() {
        if (_sortable != null) return _sortable.booleanValue();
        ValueBinding vb = getValueBinding("defaultSorted");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public void setSortable(Boolean sortable) {
        _sortable = sortable;
    }

    public String getSortPropertyName() {
        return (String) getLocalOrValueBindingValue(_sortPropertyName, "sortPropertyName");
    }

    public void setSortPropertyName(String sortPropertyName) {
        _sortPropertyName = sortPropertyName;
    }

    // the following are not implemented, but are in the HtmlColumn interface
    public String getColspan() {return null;}
    public void setColspan(String colspan) {}
    public String getHeadercolspan() {return null;}
    public void setHeadercolspan(String headercolspan) {}
    public String getFootercolspan() {return null;}
    public void setFootercolspan(String footercolspan) {}

    public String getColumnId() {
        return null;
    }

    public void setColumnId(String columnId) {
    }

    /**
     * @see javax.faces.component.UIData#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context) {
        Object[] values = new Object[]
		{
			super.saveState(context),

			_headerdir,
			_headerlang,
			_headerstyle,
			_headerstyleClass,
			_headertitle,
			_headeronclick,
			_headerondblclick,
			_headeronkeydown,
			_headeronkeypress,
			_headeronkeyup,
			_headeronmousedown,
			_headeronmousemove,
			_headeronmouseout,
			_headeronmouseover,
			_headeronmouseup,

			_footerdir,
			_footerlang,
			_footerstyle,
			_footerstyleClass,
			_footertitle,
			_footeronclick,
			_footerondblclick,
			_footeronkeydown,
			_footeronkeypress,
			_footeronkeyup,
			_footeronmousedown,
			_footeronmousemove,
			_footeronmouseout,
			_footeronmouseover,
			_footeronmouseup,

			_dir,
			_lang,
			_style,
			_styleClass,
			_title,
			_onclick,
			_ondblclick,
			_onkeydown,
			_onkeypress,
			_onkeyup,
			_onmousedown,
			_onmousemove,
			_onmouseout,
			_onmouseover,
			_onmouseup,

			_width,
			_groupBy,
			_groupByValue,
			_defaultSorted,
			_sortable,
			_sortPropertyName,
		};

		return values;
    }

    /**
     * @see javax.faces.component.UIData#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);

        _headerdir = (String) values[1];
        _headerlang = (String) values[2];
        _headerstyle = (String) values[3];
        _headerstyleClass = (String) values[4];
        _headertitle = (String) values[5];
        _headeronclick = (String) values[6];
        _headerondblclick = (String) values[7];
        _headeronkeydown = (String) values[8];
        _headeronkeypress = (String) values[9];
        _headeronkeyup = (String) values[10];
        _headeronmousedown = (String) values[11];
        _headeronmousemove = (String) values[12];
        _headeronmouseout = (String) values[13];
        _headeronmouseover = (String) values[14];
        _headeronmouseup = (String) values[15];

        _footerdir = (String) values[16];
        _footerlang = (String) values[17];
        _footerstyle = (String) values[18];
        _footerstyleClass = (String) values[19];
        _footertitle = (String) values[20];
        _footeronclick = (String) values[21];
        _footerondblclick = (String) values[22];
        _footeronkeydown = (String) values[23];
        _footeronkeypress = (String) values[24];
        _footeronkeyup = (String) values[25];
        _footeronmousedown = (String) values[26];
        _footeronmousemove = (String) values[27];
        _footeronmouseout = (String) values[28];
        _footeronmouseover = (String) values[29];
        _footeronmouseup = (String) values[30];

        _dir = (String) values[31];
        _lang = (String) values[32];
        _style = (String) values[33];
        _styleClass = (String) values[34];
        _title = (String) values[35];
        _onclick = (String) values[36];
        _ondblclick = (String) values[37];
        _onkeydown = (String) values[38];
        _onkeypress = (String) values[39];
        _onkeyup = (String) values[40];
        _onmousedown = (String) values[41];
        _onmousemove = (String) values[42];
        _onmouseout = (String) values[43];
        _onmouseover = (String) values[44];
        _onmouseup = (String) values[45];

        _width = (String) values[46];
        _groupBy = (Boolean) values[47];
		_groupByValue = (String) values[48];
        _defaultSorted = (Boolean) values[49];
        _sortable = (Boolean) values[50];
        _sortPropertyName = (String) values[51];
    }
}
