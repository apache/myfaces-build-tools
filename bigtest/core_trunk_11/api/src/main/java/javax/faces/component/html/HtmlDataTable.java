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
package javax.faces.component.html;

import java.io.IOException;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * This component renders as an HTML table element.  It has as its
 * children h:column entities, which describe the columns of the table.
 * It can be decorated with facets named "header" and "footer" to
 * specify header and footer rows.
 * 
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions. 
 * 
 * Extend standard UIData component to add support for html-specific features
 * such as CSS style attributes and event handler scripts.
 * <p>
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 * </p>
 * 
 * @JSFComponent
 *   name = "h:dataTable"
 *   type = "javax.faces.HtmlDataTable"
 *   family = "javax.faces.Data"
 *   tagClass = "org.apache.myfaces.taglib.html.HtmlDataTableTag"
 *   tagSuperclass = "javax.faces.webapp.UIComponentBodyTag"
 *   desc = "h:dataTable"
 *
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlDataTable extends UIData
{
    /**
     * @see javax.faces.component.UIData#encodeBegin(javax.faces.context.FacesContext)
     */
    public void encodeBegin(FacesContext context) throws IOException
    {
        // Ensure that the "current row" is set to "no row", so that the
        // correct clientId is set for this component etc. User code may
        // have left this in some other state before rendering began...
        setRowIndex(-1);

        // Now invoke the superclass encodeBegin, which will eventually
        // execute the encodeBegin for the associated renderer.
        super.encodeBegin(context);
    }
    
    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "javax.faces.HtmlDataTable";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Table";
    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    private String _bgcolor = null;
    private Integer _border = null;
    private String _cellpadding = null;
    private String _cellspacing = null;
    private String _columnClasses = null;
    private String _dir = null;
    private String _footerClass = null;
    private String _frame = null;
    private String _headerClass = null;
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
    private String _rowClasses = null;
    private String _rules = null;
    private String _style = null;
    private String _styleClass = null;
    private String _summary = null;
    private String _title = null;
    private String _width = null;

    public HtmlDataTable()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }


    public void setBgcolor(String bgcolor)
    {
        _bgcolor = bgcolor;
    }

    /**
     * HTML: The background color of this element.
     * 
     * @JSFProperty
     */
    public String getBgcolor()
    {
        if (_bgcolor != null) return _bgcolor;
        ValueBinding vb = getValueBinding("bgcolor");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setBorder(int border)
    {
        _border = new Integer(border);
    }

    /**
     * HTML: Specifies the width of the border of this element, in pixels.  Deprecated in HTML 4.01.
     * 
     * @JSFProperty
     */
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

    /**
     * HTML: Specifies the amount of empty space between the cell border and
     * its contents.  It can be either a pixel length or a percentage.
     * 
     * @JSFProperty
     */
    public String getCellpadding()
    {
        if (_cellpadding != null) return _cellpadding;
        ValueBinding vb = getValueBinding("cellpadding");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setCellspacing(String cellspacing)
    {
        _cellspacing = cellspacing;
    }

    /**
     * HTML: Specifies the amount of space between the cells of the table.
     * It can be either a pixel length or a percentage of available 
     * 
     * @JSFProperty
     */
    public String getCellspacing()
    {
        if (_cellspacing != null) return _cellspacing;
        ValueBinding vb = getValueBinding("cellspacing");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setColumnClasses(String columnClasses)
    {
        _columnClasses = columnClasses;
    }

    /**
     * A comma separated list of CSS class names to apply to td elements in
     * each column.
     * 
     * @JSFProperty
     */
    public String getColumnClasses()
    {
        if (_columnClasses != null) return _columnClasses;
        ValueBinding vb = getValueBinding("columnClasses");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setDir(String dir)
    {
        _dir = dir;
    }

    /**
     * HTML: The direction of text display, either 'ltr' (left-to-right) or 'rtl' (right-to-left).
     * 
     * @JSFProperty
     */
    public String getDir()
    {
        if (_dir != null) return _dir;
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setFooterClass(String footerClass)
    {
        _footerClass = footerClass;
    }

    /**
     * The CSS class to be applied to footer cells.
     * 
     * @JSFProperty
     */
    public String getFooterClass()
    {
        if (_footerClass != null) return _footerClass;
        ValueBinding vb = getValueBinding("footerClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setFrame(String frame)
    {
        _frame = frame;
    }


    /**
     * HTML: Controls what part of the frame that surrounds a table is 
     * visible.  Values include:  void, above, below, hsides, lhs, 
     * rhs, vsides, box, and border.
     * 
     * @JSFProperty
     */
    public String getFrame()
    {
        if (_frame != null) return _frame;
        ValueBinding vb = getValueBinding("frame");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setHeaderClass(String headerClass)
    {
        _headerClass = headerClass;
    }

    /**
     * The CSS class to be applied to header cells.
     * 
     * @JSFProperty
     */
    public String getHeaderClass()
    {
        if (_headerClass != null) return _headerClass;
        ValueBinding vb = getValueBinding("headerClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setLang(String lang)
    {
        _lang = lang;
    }

    /**
     * HTML: The base language of this document.
     * 
     * @JSFProperty
     */
    public String getLang()
    {
        if (_lang != null) return _lang;
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    /**
     * HTML: Script to be invoked when the element is clicked.
     * 
     * @JSFProperty
     */
    public String getOnclick()
    {
        if (_onclick != null) return _onclick;
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    /**
     * HTML: Script to be invoked when the element is double-clicked.
     * 
     * @JSFProperty
     */
    public String getOndblclick()
    {
        if (_ondblclick != null) return _ondblclick;
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnkeydown(String onkeydown)
    {
        _onkeydown = onkeydown;
    }

    /**
     * HTML: Script to be invoked when a key is pressed down over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeydown()
    {
        if (_onkeydown != null) return _onkeydown;
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    /**
     * HTML: Script to be invoked when a key is pressed over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeypress()
    {
        if (_onkeypress != null) return _onkeypress;
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnkeyup(String onkeyup)
    {
        _onkeyup = onkeyup;
    }

    /**
     * HTML: Script to be invoked when a key is released over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeyup()
    {
        if (_onkeyup != null) return _onkeyup;
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmousedown(String onmousedown)
    {
        _onmousedown = onmousedown;
    }

    /**
     * HTML: Script to be invoked when the pointing device is pressed over this element.
     * 
     * @JSFProperty
     */
    public String getOnmousedown()
    {
        if (_onmousedown != null) return _onmousedown;
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmousemove(String onmousemove)
    {
        _onmousemove = onmousemove;
    }

    /**
     * HTML: Script to be invoked when the pointing device is moved while it is in this element.
     * 
     * @JSFProperty
     */
    public String getOnmousemove()
    {
        if (_onmousemove != null) return _onmousemove;
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmouseout(String onmouseout)
    {
        _onmouseout = onmouseout;
    }

    /**
     * HTML: Script to be invoked when the pointing device is moves out of this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseout()
    {
        if (_onmouseout != null) return _onmouseout;
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmouseover(String onmouseover)
    {
        _onmouseover = onmouseover;
    }

    /**
     * HTML: Script to be invoked when the pointing device is moved into this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseover()
    {
        if (_onmouseover != null) return _onmouseover;
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmouseup(String onmouseup)
    {
        _onmouseup = onmouseup;
    }

    /**
     * HTML: Script to be invoked when the pointing device is released over this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseup()
    {
        if (_onmouseup != null) return _onmouseup;
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setRowClasses(String rowClasses)
    {
        _rowClasses = rowClasses;
    }

    /**
     * A comma separated list of CSS class names to apply to td elements in each row.

     * @JSFProperty
     */
    public String getRowClasses()
    {
        if (_rowClasses != null) return _rowClasses;
        ValueBinding vb = getValueBinding("rowClasses");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setRules(String rules)
    {
        _rules = rules;
    }

    /**
     * HTML: Controls how rules are rendered between cells.  Values include:
     * none, groups, rows, cols, and all.
     * 
     * @JSFProperty
     */
    public String getRules()
    {
        if (_rules != null) return _rules;
        ValueBinding vb = getValueBinding("rules");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setStyle(String style)
    {
        _style = style;
    }

    /**
     * HTML: CSS styling instructions.
     * 
     * @JSFProperty
     */
    public String getStyle()
    {
        if (_style != null) return _style;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setStyleClass(String styleClass)
    {
        _styleClass = styleClass;
    }

    /**
     * The CSS class for this element.  Corresponds to the HTML 'class' attribute.
     * 
     * @JSFProperty
     */
    public String getStyleClass()
    {
        if (_styleClass != null) return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    /**
     * HTML: Provides a summary of the contents of the table, for
     * accessibility purposes.
     * 
     * @JSFProperty
     */
    public String getSummary()
    {
        if (_summary != null) return _summary;
        ValueBinding vb = getValueBinding("summary");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    /**
     * HTML: An advisory title for this element.  Often used by the user agent as a tooltip.
     * 
     * @JSFProperty
     */
    public String getTitle()
    {
        if (_title != null) return _title;
        ValueBinding vb = getValueBinding("title");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setWidth(String width)
    {
        _width = width;
    }

    /**
     * HTML: Specifies the desired width of the table, as a pixel length or
     * a percentage of available space.
     * @JSFProperty
     */
    public String getWidth()
    {
        if (_width != null) return _width;
        ValueBinding vb = getValueBinding("width");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }


    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[28];
        values[0] = super.saveState(context);
        values[1] = _bgcolor;
        values[2] = _border;
        values[3] = _cellpadding;
        values[4] = _cellspacing;
        values[5] = _columnClasses;
        values[6] = _dir;
        values[7] = _footerClass;
        values[8] = _frame;
        values[9] = _headerClass;
        values[10] = _lang;
        values[11] = _onclick;
        values[12] = _ondblclick;
        values[13] = _onkeydown;
        values[14] = _onkeypress;
        values[15] = _onkeyup;
        values[16] = _onmousedown;
        values[17] = _onmousemove;
        values[18] = _onmouseout;
        values[19] = _onmouseover;
        values[20] = _onmouseup;
        values[21] = _rowClasses;
        values[22] = _rules;
        values[23] = _style;
        values[24] = _styleClass;
        values[25] = _summary;
        values[26] = _title;
        values[27] = _width;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _bgcolor = (String)values[1];
        _border = (Integer)values[2];
        _cellpadding = (String)values[3];
        _cellspacing = (String)values[4];
        _columnClasses = (String)values[5];
        _dir = (String)values[6];
        _footerClass = (String)values[7];
        _frame = (String)values[8];
        _headerClass = (String)values[9];
        _lang = (String)values[10];
        _onclick = (String)values[11];
        _ondblclick = (String)values[12];
        _onkeydown = (String)values[13];
        _onkeypress = (String)values[14];
        _onkeyup = (String)values[15];
        _onmousedown = (String)values[16];
        _onmousemove = (String)values[17];
        _onmouseout = (String)values[18];
        _onmouseover = (String)values[19];
        _onmouseup = (String)values[20];
        _rowClasses = (String)values[21];
        _rules = (String)values[22];
        _style = (String)values[23];
        _styleClass = (String)values[24];
        _summary = (String)values[25];
        _title = (String)values[26];
        _width = (String)values[27];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
