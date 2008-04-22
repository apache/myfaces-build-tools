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

import javax.faces.component.UIColumn;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlSimpleColumn extends UIColumn implements HtmlColumn
{
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

    private String _width;
    private String _colspan;
    private Boolean _groupBy;
	private String _groupByValue;

    private Boolean _defaultSorted;
    private Boolean _sortable;
    private String _sortPropertyName;

    private String _columnId;

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlColumn";

    /**
     * @param localValue
     * @param valueBindingName
     * @return the value
     */
    private Object getLocalOrValueBindingValue(Object localValue,
                    String valueBindingName)
    {
        if (localValue != null)
            return localValue;
        ValueBinding vb = getValueBinding(valueBindingName);
        return vb != null ? vb.getValue(getFacesContext()) : null;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooterdir()
     */
    public String getFooterdir()
    {
        return (String) getLocalOrValueBindingValue(_footerdir, "footerdir");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooterdir(java.lang.String)
     */
    public void setFooterdir(String footerdir)
    {
        _footerdir = footerdir;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooterlang()
     */
    public String getFooterlang()
    {
        return (String) getLocalOrValueBindingValue(_footerlang, "footerlang");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooterlang(java.lang.String)
     */
    public void setFooterlang(String footerlang)
    {
        _footerlang = footerlang;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronclick()
     */
    public String getFooteronclick()
    {
        return (String) getLocalOrValueBindingValue(_footeronclick,
                        "footeronclick");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronclick(java.lang.String)
     */
    public void setFooteronclick(String footeronclick)
    {
        _footeronclick = footeronclick;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooterondblclick()
     */
    public String getFooterondblclick()
    {
        return (String) getLocalOrValueBindingValue(_footerondblclick,
                        "footerondblclick");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooterondblclick(java.lang.String)
     */
    public void setFooterondblclick(String footerondblclick)
    {
        _footerondblclick = footerondblclick;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronkeydown()
     */
    public String getFooteronkeydown()
    {
        return (String) getLocalOrValueBindingValue(_footeronkeydown,
                        "footeronkeydown");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronkeydown(java.lang.String)
     */
    public void setFooteronkeydown(String footeronkeydown)
    {
        _footeronkeydown = footeronkeydown;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronkeypress()
     */
    public String getFooteronkeypress()
    {
        return (String) getLocalOrValueBindingValue(_footeronkeypress,
                        "footeronkeypress");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronkeypress(java.lang.String)
     */
    public void setFooteronkeypress(String footeronkeypress)
    {
        _footeronkeypress = footeronkeypress;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronkeyup()
     */
    public String getFooteronkeyup()
    {
        return (String) getLocalOrValueBindingValue(_footeronkeyup,
                        "footeronkeyup");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronkeyup(java.lang.String)
     */
    public void setFooteronkeyup(String footeronkeyup)
    {
        _footeronkeyup = footeronkeyup;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronmousedown()
     */
    public String getFooteronmousedown()
    {
        return (String) getLocalOrValueBindingValue(_footeronmousedown,
                        "footeronmousedown");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronmousedown(java.lang.String)
     */
    public void setFooteronmousedown(String footeronmousedown)
    {
        _footeronmousedown = footeronmousedown;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronmousemove()
     */
    public String getFooteronmousemove()
    {
        return (String) getLocalOrValueBindingValue(_footeronmousemove,
                        "footeronmousemove");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronmousemove(java.lang.String)
     */
    public void setFooteronmousemove(String footeronmousemove)
    {
        _footeronmousemove = footeronmousemove;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronmouseout()
     */
    public String getFooteronmouseout()
    {
        return (String) getLocalOrValueBindingValue(_footeronmouseout,
                        "footeronmouseout");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronmouseout(java.lang.String)
     */
    public void setFooteronmouseout(String footeronmouseout)
    {
        _footeronmouseout = footeronmouseout;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronmouseover()
     */
    public String getFooteronmouseover()
    {
        return (String) getLocalOrValueBindingValue(_footeronmouseover,
                        "footeronmouseover");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronmouseover(java.lang.String)
     */
    public void setFooteronmouseover(String footeronmouseover)
    {
        _footeronmouseover = footeronmouseover;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooteronmouseup()
     */
    public String getFooteronmouseup()
    {
        return (String) getLocalOrValueBindingValue(_footeronmouseup,
                        "footeronmouseup");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooteronmouseup(java.lang.String)
     */
    public void setFooteronmouseup(String footeronmouseup)
    {
        _footeronmouseup = footeronmouseup;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooterstyle()
     */
    public String getFooterstyle()
    {
        return (String) getLocalOrValueBindingValue(_footerstyle, "footerstyle");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooterstyle(java.lang.String)
     */
    public void setFooterstyle(String footerstyle)
    {
        _footerstyle = footerstyle;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFooterstyleClass()
     */
    public String getFooterstyleClass()
    {
        return (String) getLocalOrValueBindingValue(_footerstyleClass,
                        "footerstyleClass");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFooterstyleClass(java.lang.String)
     */
    public void setFooterstyleClass(String footerstyleClass)
    {
        _footerstyleClass = footerstyleClass;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFootertitle()
     */
    public String getFootertitle()
    {
        return (String) getLocalOrValueBindingValue(_footertitle, "footertitle");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFootertitle(java.lang.String)
     */
    public void setFootertitle(String footertitle)
    {
        _footertitle = footertitle;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getFootercolspan()
     */
    public String getFootercolspan()
    {
        return (String) getLocalOrValueBindingValue(_footercolspan, "footercolspan");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setFootercolspan(java.lang.String)
     */
    public void setFootercolspan(String footercolspan)
    {
        _footercolspan = footercolspan;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderdir()
     */
    public String getHeaderdir()
    {
        return (String) getLocalOrValueBindingValue(_headerdir, "headerdir");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderdir(java.lang.String)
     */
    public void setHeaderdir(String headerdir)
    {
        _headerdir = headerdir;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderlang()
     */
    public String getHeaderlang()
    {
        return (String) getLocalOrValueBindingValue(_headerlang, "headerlang");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderlang(java.lang.String)
     */
    public void setHeaderlang(String headerlang)
    {
        _headerlang = headerlang;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonclick()
     */
    public String getHeaderonclick()
    {
        return (String) getLocalOrValueBindingValue(_headeronclick,
                        "headeronclick");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonclick(java.lang.String)
     */
    public void setHeaderonclick(String headeronclick)
    {
        _headeronclick = headeronclick;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderondblclick()
     */
    public String getHeaderondblclick()
    {
        return (String) getLocalOrValueBindingValue(_headerondblclick,
                        "headerondblclick");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderondblclick(java.lang.String)
     */
    public void setHeaderondblclick(String headerondblclick)
    {
        _headerondblclick = headerondblclick;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonkeydown()
     */
    public String getHeaderonkeydown()
    {
        return (String) getLocalOrValueBindingValue(_headeronkeydown,
                        "headeronkeydown");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonkeydown(java.lang.String)
     */
    public void setHeaderonkeydown(String headeronkeydown)
    {
        _headeronkeydown = headeronkeydown;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonkeypress()
     */
    public String getHeaderonkeypress()
    {
        return (String) getLocalOrValueBindingValue(_headeronkeypress,
                        "headeronkeypress");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonkeypress(java.lang.String)
     */
    public void setHeaderonkeypress(String headeronkeypress)
    {
        _headeronkeypress = headeronkeypress;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonkeyup()
     */
    public String getHeaderonkeyup()
    {
        return (String) getLocalOrValueBindingValue(_headeronkeyup,
                        "headeronkeyup");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonkeyup(java.lang.String)
     */
    public void setHeaderonkeyup(String headeronkeyup)
    {
        _headeronkeyup = headeronkeyup;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonmousedown()
     */
    public String getHeaderonmousedown()
    {
        return (String) getLocalOrValueBindingValue(_headeronmousedown,
                        "headeronmousedown");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonmousedown(java.lang.String)
     */
    public void setHeaderonmousedown(String headeronmousedown)
    {
        _headeronmousedown = headeronmousedown;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonmousemove()
     */
    public String getHeaderonmousemove()
    {
        return (String) getLocalOrValueBindingValue(_headeronmousemove,
                        "headeronmousemove");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonmousemove(java.lang.String)
     */
    public void setHeaderonmousemove(String headeronmousemove)
    {
        _headeronmousemove = headeronmousemove;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonmouseout()
     */
    public String getHeaderonmouseout()
    {
        return (String) getLocalOrValueBindingValue(_headeronmouseout,
                        "headeronmouseout");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonmouseout(java.lang.String)
     */
    public void setHeaderonmouseout(String headeronmouseout)
    {
        _headeronmouseout = headeronmouseout;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonmouseover()
     */
    public String getHeaderonmouseover()
    {
        return (String) getLocalOrValueBindingValue(_headeronmouseover,
                        "headeronmouseover");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonmouseover(java.lang.String)
     */
    public void setHeaderonmouseover(String headeronmouseover)
    {
        _headeronmouseover = headeronmouseover;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderonmouseup()
     */
    public String getHeaderonmouseup()
    {
        return (String) getLocalOrValueBindingValue(_headeronmouseup,
                        "headeronmouseup");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderonmouseup(java.lang.String)
     */
    public void setHeaderonmouseup(String headeronmouseup)
    {
        _headeronmouseup = headeronmouseup;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderstyle()
     */
    public String getHeaderstyle()
    {
        return (String) getLocalOrValueBindingValue(_headerstyle, "headerstyle");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderstyle(java.lang.String)
     */
    public void setHeaderstyle(String headerstyle)
    {
        _headerstyle = headerstyle;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeaderstyleClass()
     */
    public String getHeaderstyleClass()
    {
        return (String) getLocalOrValueBindingValue(_headerstyleClass,
                        "headerstyleClass");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeaderstyleClass(java.lang.String)
     */
    public void setHeaderstyleClass(String headerstyleClass)
    {
        _headerstyleClass = headerstyleClass;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeadertitle()
     */
    public String getHeadertitle()
    {
        return (String) getLocalOrValueBindingValue(_headertitle, "headertitle");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeadertitle(java.lang.String)
     */
    public void setHeadertitle(String headertitle)
    {
        _headertitle = headertitle;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getHeadercolspan()
     */
    public String getHeadercolspan()
    {
        return (String) getLocalOrValueBindingValue(_headercolspan, "headercolspan");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setHeadercolspan(java.lang.String)
     */
    public void setHeadercolspan(String headercolspan)
    {
        _headercolspan = headercolspan;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getDir()
     */
    public String getDir()
    {
        return (String) getLocalOrValueBindingValue(_dir, "dir");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setDir(java.lang.String)
     */
    public void setDir(String dir)
    {
        _dir = dir;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getLang()
     */
    public String getLang()
    {
        return (String) getLocalOrValueBindingValue(_lang, "lang");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setLang(java.lang.String)
     */
    public void setLang(String lang)
    {
        _lang = lang;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnclick()
     */
    public String getOnclick()
    {
        return (String) getLocalOrValueBindingValue(_onclick, "onclick");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnclick(java.lang.String)
     */
    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOndblclick()
     */
    public String getOndblclick()
    {
        return (String) getLocalOrValueBindingValue(_ondblclick, "ondblclick");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOndblclick(java.lang.String)
     */
    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnkeydown()
     */
    public String getOnkeydown()
    {
        return (String) getLocalOrValueBindingValue(_onkeydown, "onkeydown");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnkeydown(java.lang.String)
     */
    public void setOnkeydown(String onkeydown)
    {
        _onkeydown = onkeydown;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnkeypress()
     */
    public String getOnkeypress()
    {
        return (String) getLocalOrValueBindingValue(_onkeypress, "onkeypress");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnkeypress(java.lang.String)
     */
    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnkeyup()
     */
    public String getOnkeyup()
    {
        return (String) getLocalOrValueBindingValue(_onkeyup, "onkeyup");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnkeyup(java.lang.String)
     */
    public void setOnkeyup(String onkeyup)
    {
        _onkeyup = onkeyup;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnmousedown()
     */
    public String getOnmousedown()
    {
        return (String) getLocalOrValueBindingValue(_onmousedown, "onmousedown");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnmousedown(java.lang.String)
     */
    public void setOnmousedown(String onmousedown)
    {
        _onmousedown = onmousedown;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnmousemove()
     */
    public String getOnmousemove()
    {
        return (String) getLocalOrValueBindingValue(_onmousemove, "onmousemove");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnmousemove(java.lang.String)
     */
    public void setOnmousemove(String onmousemove)
    {
        _onmousemove = onmousemove;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnmouseout()
     */
    public String getOnmouseout()
    {
        return (String) getLocalOrValueBindingValue(_onmouseout, "onmouseout");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnmouseout(java.lang.String)
     */
    public void setOnmouseout(String onmouseout)
    {
        _onmouseout = onmouseout;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnmouseover()
     */
    public String getOnmouseover()
    {
        return (String) getLocalOrValueBindingValue(_onmouseover, "onmouseover");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnmouseover(java.lang.String)
     */
    public void setOnmouseover(String onmouseover)
    {
        _onmouseover = onmouseover;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getOnmouseup()
     */
    public String getOnmouseup()
    {
        return (String) getLocalOrValueBindingValue(_onmouseup, "onmouseup");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setOnmouseup(java.lang.String)
     */
    public void setOnmouseup(String onmouseup)
    {
        _onmouseup = onmouseup;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getStyle()
     */
    public String getStyle()
    {
        return (String) getLocalOrValueBindingValue(_style, "style");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setStyle(java.lang.String)
     */
    public void setStyle(String style)
    {
        _style = style;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getStyleClass()
     */
    public String getStyleClass()
    {
        return (String) getLocalOrValueBindingValue(_styleClass, "styleClass");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setStyleClass(java.lang.String)
     */
    public void setStyleClass(String styleClass)
    {
        _styleClass = styleClass;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getTitle()
     */
    public String getTitle()
    {
        return (String) getLocalOrValueBindingValue(_title, "title");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setTitle(java.lang.String)
     */
    public void setTitle(String title)
    {
        _title = title;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getWidth()
     */
    public String getWidth()
    {
        return (String) getLocalOrValueBindingValue(_width, "width");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setWidth(java.lang.String)
     */
    public void setWidth(String width)
    {
        _width = width;
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#getColspan()
     */
    public String getColspan()
    {
        return (String) getLocalOrValueBindingValue(_colspan, "colspan");
    }

    /**
     * @see org.apache.myfaces.custom.column.HtmlColumn#setColspan(java.lang.String)
     */
    public void setColspan(String colspan)
    {
        _colspan = colspan;
    }

    public boolean isGroupBy()
    {
        if (_groupBy != null) return _groupBy.booleanValue();
        ValueBinding vb = getValueBinding("groupBy");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

	public boolean isGroupByValueSet()
	{
		if (_groupByValue != null) return true;
		ValueBinding vb = getValueBinding("groupByValue");
		return vb != null;
	}

	public Object getGroupByValue()
	{
		if (_groupByValue != null) return _groupByValue;
		ValueBinding vb = getValueBinding("groupByValue");
		return vb != null ? vb.getValue(getFacesContext()) : null;
	}

	public void setGroupBy(boolean groupBy)
    {
        _groupBy = groupBy ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean isDefaultSorted()
    {
        if (_defaultSorted != null) return _defaultSorted.booleanValue();
        ValueBinding vb = getValueBinding("defaultSorted");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public void setDefaultSorted(boolean defaultSorted)
    {
        _defaultSorted = defaultSorted ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean isSortable()
    {
        if (_sortable != null) return _sortable.booleanValue();
        ValueBinding vb = getValueBinding("sortable");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public void setSortable(boolean sortable)
    {
        _sortable = sortable ? Boolean.TRUE : Boolean.FALSE;
    }

    public String getSortPropertyName()
    {
        return (String) getLocalOrValueBindingValue(_sortPropertyName, "sortPropertyName");
    }

    public void setSortPropertyName(String sortPropertyName)
    {
        _sortPropertyName = sortPropertyName;
    }

    public String getColumnId()
    {
        return (String) getLocalOrValueBindingValue(_columnId, "columnId");
    }

    public void setColumnId(String columnId)
    {
        _columnId = columnId;
    }

    /**
     * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
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
			_headercolspan,
			_footercolspan,
			_colspan,
            _columnId
        };

		return values;
    }

    /**
     * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
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
        _headercolspan = (String) values[52];
        _footercolspan = (String) values[53];
        _colspan = (String) values[54];
        _columnId = (String) values[55];
    }
}
