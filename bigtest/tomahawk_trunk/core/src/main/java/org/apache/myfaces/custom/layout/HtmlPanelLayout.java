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
package org.apache.myfaces.custom.layout;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @JSFComponent
 *   name = "t:panelLayout"
 *   tagClass = "org.apache.myfaces.custom.layout.HtmlPanelLayoutTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelLayout
        extends HtmlPanelGroup
{
    //private static final Log log = LogFactory.getLog(HtmlPanelLayout.class);

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;
    private static final String DEFAULT_LAYOUT = "classic";
    
    // HTML table attributes
    private String _align;
    private String _bgcolor;
    private Integer _border;
    private String _cellpadding;
    private String _cellspacing;
    private String _frame;
    private String _rules;
    private String _summary;
    private String _width;

    // HTML standard attributes
    private String _dir = null;
    private String _lang = null;
    private String _onclick = null;
    private String _ondblclick = null;
    private String _onkeydown = null;
    private String _onkeypress = null;
    private String _onkeyup = null;
    private String _onmousedown = null;
    private String _onmousemove = null;
    private String _onmouseout = null;
    private String _onmouseover = null;
    private String _onmouseup = null;
    private String _title = null;

    // typesafe facet getters

    public UIComponent getHeader()
    {
        return (UIComponent)getFacet("header");
    }

    public UIComponent getNavigation()
    {
        return (UIComponent)getFacet("navigation");
    }

    public UIComponent getBody()
    {
        return (UIComponent)getFacet("body");
    }

    public UIComponent getFooter()
    {
        return (UIComponent)getFacet("footer");
    }


    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelLayout";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Layout";

    private String _layout = null;
    private String _headerClass = null;
    private String _navigationClass = null;
    private String _bodyClass = null;
    private String _footerClass = null;
    private String _headerStyle = null;
    private String _navigationStyle = null;
    private String _bodyStyle = null;
    private String _footerStyle = null;

    public HtmlPanelLayout()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }

    public String getLayout()
    {
        if (_layout != null) return _layout;
        ValueBinding vb = getValueBinding("layout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : DEFAULT_LAYOUT;
    }

    public void setHeaderClass(String headerClass)
    {
        _headerClass = headerClass;
    }

    public String getHeaderClass()
    {
        if (_headerClass != null) return _headerClass;
        ValueBinding vb = getValueBinding("headerClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setNavigationClass(String navigationClass)
    {
        _navigationClass = navigationClass;
    }

    public String getNavigationClass()
    {
        if (_navigationClass != null) return _navigationClass;
        ValueBinding vb = getValueBinding("navigationClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBodyClass(String bodyClass)
    {
        _bodyClass = bodyClass;
    }

    public String getBodyClass()
    {
        if (_bodyClass != null) return _bodyClass;
        ValueBinding vb = getValueBinding("bodyClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFooterClass(String footerClass)
    {
        _footerClass = footerClass;
    }

    public String getFooterClass()
    {
        if (_footerClass != null) return _footerClass;
        ValueBinding vb = getValueBinding("footerClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setHeaderStyle(String headerStyle)
    {
        _headerStyle = headerStyle;
    }

    public String getHeaderStyle()
    {
        if (_headerStyle != null) return _headerStyle;
        ValueBinding vb = getValueBinding("headerStyle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setNavigationStyle(String navigationStyle)
    {
        _navigationStyle = navigationStyle;
    }

    public String getNavigationStyle()
    {
        if (_navigationStyle != null) return _navigationStyle;
        ValueBinding vb = getValueBinding("navigationStyle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBodyStyle(String bodyStyle)
    {
        _bodyStyle = bodyStyle;
    }

    public String getBodyStyle()
    {
        if (_bodyStyle != null) return _bodyStyle;
        ValueBinding vb = getValueBinding("bodyStyle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFooterStyle(String footerStyle)
    {
        _footerStyle = footerStyle;
    }

    public String getFooterStyle()
    {
        if (_footerStyle != null) return _footerStyle;
        ValueBinding vb = getValueBinding("footerStyle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public String getAlign()
    {
        if (_align != null) return _align;
        ValueBinding vb = getValueBinding("align");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBgcolor(String bgcolor)
    {
        _bgcolor = bgcolor;
    }

    public String getBgcolor()
    {
        if (_bgcolor != null) return _bgcolor;
        ValueBinding vb = getValueBinding("bgcolor");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBorder(int border)
    {
        _border = new Integer(border);
    }

    public int getBorder()
    {
        if (_border != null) return _border.intValue();
        ValueBinding vb = getValueBinding("border");
        Number v = vb != null ? (Number)vb.getValue(getFacesContext()) : null;
        return v != null ? v.intValue() : DEFAULT_BORDER;
    }

    public void setCellpadding(String cellpadding)
    {
        _cellpadding = cellpadding;
    }

    public String getCellpadding()
    {
        if (_cellpadding != null) return _cellpadding;
        ValueBinding vb = getValueBinding("cellpadding");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCellspacing(String cellspacing)
    {
        _cellspacing = cellspacing;
    }

    public String getCellspacing()
    {
        if (_cellspacing != null) return _cellspacing;
        ValueBinding vb = getValueBinding("cellspacing");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDir(String dir)
    {
        _dir = dir;
    }

    public String getDir()
    {
        if (_dir != null) return _dir;
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFrame(String frame)
    {
        _frame = frame;
    }

    public String getFrame()
    {
        if (_frame != null) return _frame;
        ValueBinding vb = getValueBinding("frame");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLang(String lang)
    {
        _lang = lang;
    }

    public String getLang()
    {
        if (_lang != null) return _lang;
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    public String getOnclick()
    {
        if (_onclick != null) return _onclick;
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    public String getOndblclick()
    {
        if (_ondblclick != null) return _ondblclick;
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeydown(String onkeydown)
    {
        _onkeydown = onkeydown;
    }

    public String getOnkeydown()
    {
        if (_onkeydown != null) return _onkeydown;
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    public String getOnkeypress()
    {
        if (_onkeypress != null) return _onkeypress;
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeyup(String onkeyup)
    {
        _onkeyup = onkeyup;
    }

    public String getOnkeyup()
    {
        if (_onkeyup != null) return _onkeyup;
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousedown(String onmousedown)
    {
        _onmousedown = onmousedown;
    }

    public String getOnmousedown()
    {
        if (_onmousedown != null) return _onmousedown;
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousemove(String onmousemove)
    {
        _onmousemove = onmousemove;
    }

    public String getOnmousemove()
    {
        if (_onmousemove != null) return _onmousemove;
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseout(String onmouseout)
    {
        _onmouseout = onmouseout;
    }

    public String getOnmouseout()
    {
        if (_onmouseout != null) return _onmouseout;
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseover(String onmouseover)
    {
        _onmouseover = onmouseover;
    }

    public String getOnmouseover()
    {
        if (_onmouseover != null) return _onmouseover;
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseup(String onmouseup)
    {
        _onmouseup = onmouseup;
    }

    public String getOnmouseup()
    {
        if (_onmouseup != null) return _onmouseup;
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRules(String rules)
    {
        _rules = rules;
    }

    public String getRules()
    {
        if (_rules != null) return _rules;
        ValueBinding vb = getValueBinding("rules");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public String getSummary()
    {
        if (_summary != null) return _summary;
        ValueBinding vb = getValueBinding("summary");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    public String getTitle()
    {
        if (_title != null) return _title;
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWidth(String width)
    {
        _width = width;
    }

    public String getWidth()
    {
        if (_width != null) return _width;
        ValueBinding vb = getValueBinding("width");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[32];
        values[0] = super.saveState(context);
        values[1] = _layout;
        values[2] = _headerClass;
        values[3] = _navigationClass;
        values[4] = _bodyClass;
        values[5] = _footerClass;
        values[6] = _headerStyle;
        values[7] = _navigationStyle;
        values[8] = _bodyStyle;
        values[9] = _footerStyle;
        values[10] = _align;
        values[11] = _bgcolor;
        values[12] = _border;
        values[13] = _cellpadding;
        values[14] = _cellspacing;
        values[15] = _frame;
        values[16] = _rules;
        values[17] = _summary;
        values[18] = _width;
        values[19] = _dir;
        values[20] = _lang;
        values[21] = _onclick;
        values[22] = _ondblclick;
        values[23] = _onkeydown;
        values[24] = _onkeypress;
        values[25] = _onkeyup;
        values[26] = _onmousedown;
        values[27] = _onmousemove;
        values[28] = _onmouseout;
        values[29] = _onmouseover;
        values[30] = _onmouseup;
        values[31] = _title;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _layout = (String)values[1];
        _headerClass = (String)values[2];
        _navigationClass = (String)values[3];
        _bodyClass = (String)values[4];
        _footerClass = (String)values[5];
        _headerStyle = (String)values[6];
        _navigationStyle = (String)values[7];
        _bodyStyle = (String)values[8];
        _footerStyle = (String)values[9];
        _align = (String)values[10];
        _bgcolor = (String)values[11];
        _border = (Integer)values[12];
        _cellpadding = (String)values[13];
        _cellspacing = (String)values[14];
        _frame = (String)values[15];
        _rules = (String)values[16];
        _summary = (String)values[17];
        _width = (String)values[18];
        _dir = (String)values[19];
        _lang = (String)values[20];
        _onclick = (String)values[21];
        _ondblclick = (String)values[22];
        _onkeydown = (String)values[23];
        _onkeypress = (String)values[24];
        _onkeyup = (String)values[25];
        _onmousedown = (String)values[26];
        _onmousemove = (String)values[27];
        _onmouseout = (String)values[28];
        _onmouseover = (String)values[29];
        _onmouseup = (String)values[30];
        _title = (String)values[31];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
