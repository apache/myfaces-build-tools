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

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface HtmlColumn
{
    /**
     * @JSFProperty
     */
    String getFooterdir();

    void setFooterdir(String footerdir);

    /**
     * @JSFProperty
     */
    String getFooterlang();

    void setFooterlang(String footerlang);

    /**
     * @JSFProperty
     */
    String getFooteronclick();

    void setFooteronclick(String footeronclick);

    /**
     * @JSFProperty
     */
    String getFooterondblclick();

    void setFooterondblclick(String footerondblclick);

    /**
     * @JSFProperty
     */
    String getFooteronkeydown();

    void setFooteronkeydown(String footeronkeydown);

    /**
     * @JSFProperty
     */
    String getFooteronkeypress();

    void setFooteronkeypress(String footeronkeypress);

    /**
     * @JSFProperty
     */
    String getFooteronkeyup();

    void setFooteronkeyup(String footeronkeyup);

    /**
     * @JSFProperty
     */
    String getFooteronmousedown();

    void setFooteronmousedown(String footeronmousedown);

    /**
     * @JSFProperty
     */
    String getFooteronmousemove();

    void setFooteronmousemove(String footeronmousemove);

    /**
     * @JSFProperty
     */
    String getFooteronmouseout();

    void setFooteronmouseout(String footeronmouseout);

    /**
     * @JSFProperty
     */
    String getFooteronmouseover();

    void setFooteronmouseover(String footeronmouseover);

    /**
     * @JSFProperty
     */
    String getFooteronmouseup();

    void setFooteronmouseup(String footeronmouseup);

    /**
     * @JSFProperty
     */
    String getFooterstyle();

    void setFooterstyle(String footerstyle);

    /**
     * Corresponds to the HTML class attribute.
     * 
     * @JSFProperty
     */
    String getFooterstyleClass();

    void setFooterstyleClass(String footerstyleClass);

    /**
     * @JSFProperty
     */
    String getFootertitle();

    void setFootertitle(String footertitle);

    /**
     * @JSFProperty
     */
    String getHeaderdir();

    void setHeaderdir(String headerdir);

    /**
     * @JSFProperty
     */
    String getHeaderlang();

    void setHeaderlang(String headerlang);

    /**
     * @JSFProperty
     */
    String getHeaderonclick();

    void setHeaderonclick(String headeronclick);

    /**
     * @JSFProperty
     */
    String getHeaderondblclick();

    void setHeaderondblclick(String headerondblclick);

    /**
     * @JSFProperty
     */
    String getHeaderonkeydown();

    void setHeaderonkeydown(String headeronkeydown);

    /**
     * @JSFProperty
     */
    String getHeaderonkeypress();

    void setHeaderonkeypress(String headeronkeypress);

    /**
     * @JSFProperty
     */
    String getHeaderonkeyup();

    void setHeaderonkeyup(String headeronkeyup);

    /**
     * @JSFProperty
     */
    String getHeaderonmousedown();

    void setHeaderonmousedown(String headeronmousedown);

    /**
     * @JSFProperty
     */
    String getHeaderonmousemove();

    void setHeaderonmousemove(String headeronmousemove);

    /**
     * @JSFProperty
     */
    String getHeaderonmouseout();

    void setHeaderonmouseout(String headeronmouseout);

    /**
     * @JSFProperty
     */
    String getHeaderonmouseover();

    void setHeaderonmouseover(String headeronmouseover);

    /**
     * @JSFProperty
     */
    String getHeaderonmouseup();

    void setHeaderonmouseup(String headeronmouseup);

    /**
     * @JSFProperty
     */
    String getHeaderstyle();

    void setHeaderstyle(String headerstyle);

    /**
     * Corresponds to the HTML class attribute.
     * 
     * @JSFProperty
     */
    String getHeaderstyleClass();

    void setHeaderstyleClass(String headerstyleClass);

    /**
     * @JSFProperty
     */
    String getHeadertitle();

    void setHeadertitle(String headertitle);

    /**
     * HTML: The direction of text display, either 'ltr' 
     * (left-to-right) or 'rtl' (right-to-left).
     * 
     * @JSFProperty
     */
    String getDir();

    void setDir(String dir);

    /**
     * HTML: The base language of this document.
     * 
     * @JSFProperty
     */
    String getLang();

    void setLang(String lang);

    /**
     * HTML: Script to be invoked when the element is clicked.
     * 
     * @JSFProperty
     */
    public String getOnclick();

    void setOnclick(String onclick);
    
    /**
     * HTML: Script to be invoked when the element is double-clicked.
     * 
     * @JSFProperty
     */
    public String getOndblclick();

    void setOndblclick(String ondblclick);

    /**
     * HTML: Script to be invoked when a key is pressed down over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeydown();

    void setOnkeydown(String onkeydown);

    /**
     * HTML: Script to be invoked when a key is pressed over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeypress();

    void setOnkeypress(String onkeypress);

    /**
     * HTML: Script to be invoked when a key is released over this element.
     * 
     * @JSFProperty
     */
    public String getOnkeyup();

    void setOnkeyup(String onkeyup);

    /**
     * HTML: Script to be invoked when the pointing device is pressed over this element.
     * 
     * @JSFProperty
     */
    public String getOnmousedown();

    void setOnmousedown(String onmousedown);

    /**
     * HTML: Script to be invoked when the pointing device is moved while it is in this element.
     * 
     * @JSFProperty
     */
    public String getOnmousemove();

    void setOnmousemove(String onmousemove);

    /**
     * HTML: Script to be invoked when the pointing device is moves out of this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseout();

    void setOnmouseout(String onmouseout);

    /**
     * HTML: Script to be invoked when the pointing device is moved into this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseover();

    void setOnmouseover(String onmouseover);

    /**
     * HTML: Script to be invoked when the pointing device is released over this element.
     * 
     * @JSFProperty
     */
    public String getOnmouseup();
    
    void setOnmouseup(String onmouseup);

    /**
     * HTML: CSS styling instructions.
     * 
     * @JSFProperty
     */
    String getStyle();

    void setStyle(String style);

    /**
     * The CSS class for this element. Corresponds to the HTML 'class' attribute.
     * 
     * @JSFProperty
     */
    String getStyleClass();

    void setStyleClass(String styleClass);

    /**
     *  HTML: An advisory title for this element. Often used by 
     *  the user agent as a tooltip.
     * 
     * @JSFProperty
     */
    String getTitle();

    void setTitle(String title);

    /**
     * This attribute can be used to set the width of the elements.
     * 
     * @JSFProperty
     */
    String getWidth();

    void setWidth(String width);

    /**
     * This attribute specifies the colspan attribute for the cell
     * 
     * @JSFProperty
     */
    String getColspan();

    void setColspan(String colspan);

    /**
     * @JSFProperty
     */
    String getHeadercolspan();

    void setHeadercolspan(String headercolspan);

    /**
     * @JSFProperty
     */
    String getFootercolspan();

    void setFootercolspan(String footercolspan);

    /**
     * The columnId which will be used as id for the column header. 
     * Notice: As the rowId on t:datatable this will not add 
     * any namespace to the id. The id will be rendered exactly 
     * as you provide it.
     * 
     * @JSFProperty
     */
    String getColumnId();

    void setColumnId(String columnId);
}