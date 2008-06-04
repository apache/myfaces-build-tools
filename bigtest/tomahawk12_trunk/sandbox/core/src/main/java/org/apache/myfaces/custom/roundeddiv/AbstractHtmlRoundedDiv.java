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
 * --
 * $Id$
 */
package org.apache.myfaces.custom.roundeddiv;

import org.apache.myfaces.custom.div.Div;

/**
 * Component that allows for a rounded border effect on DIV tags that is supported in CSS2 compatible browsers and IE6.
 * 
 * Component that generates a DIV tag with rounded corners that may
 * be either 3D or 2D in appearence.
 *
 * @JSFComponent
 *   name = "s:roundedDiv"
 *   class = "org.apache.myfaces.custom.roundeddiv.HtmlRoundedDiv"
 *   superClass = "org.apache.myfaces.custom.roundeddiv.AbstractHtmlRoundedDiv"
 *   tagClass = "org.apache.myfaces.custom.roundeddiv.HtmlRoundedDivTag"
 *   
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlRoundedDiv extends Div
{
    public final static String COMPONENT_TYPE = "org.apache.myfaces.HtmlRoundedDiv";
    private final static String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlRoundedDiv";

    /**
     * Default constructor
     */
    public AbstractHtmlRoundedDiv()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * The CSS style to give to the content DIV or TD (based on layout)
     * 
     * @JSFProperty
     * @return the contentStyle
     */
    public abstract String getContentStyle();

    /**
     * The CSS style class to give to the content DIV or TD (based on layout)
     * 
     * @JSFProperty
     * @return the contentStyleClass
     */
    public abstract String getContentStyleClass();

    /**
     * Either "table" or "div". Specifies how the output should be rendered. 
     * Size must be null if using "table" (if it is not, a div will be rendered). 
     * (Default: div)
     * 
     * @JSFProperty
     *   defaultValue = "div"
     * @return
     */
    public abstract String getLayout();

    /**
     * Background color to give the corners. Leave blank (null) to have a transparent 
     * background. If the user is using IE6, this has to be set, or the corners 
     * will not look good due to IE6's lack of PNG support.
     * 
     * @JSFProperty
     * @return the backgroundColor
     */
    public abstract String getBackgroundColor();

    /**
     * The color of the border. If specified, this will cause the DIV to be 2D, if 
     * it isn't the border with have a 3D effect with lighting effects to produce 
     * the border color.
     * 
     * @JSFProperty
     * @return the borderColor
     */
    public abstract String getBorderColor();

    /**
     * Flips the lightening/darkening effect for 3D borders. (Default: false)
     * 
     * @JSFProperty
     *   defaultValue = "false";
     * @return the inverse
     */
    public abstract Boolean getInverse();

    /**
     * The width of the border in pixels. (Default: 8)
     * 
     * @JSFProperty
     *   defaultValue = "Integer.valueOf(8)"
     * @return the borderWidth
     */
    public abstract Integer getBorderWidth();

    /**
     * This allows you to specify a comma-separated list of corners to include. 
     * If not given, all four corners will be rendered. The corners include the 
     * sides they touch. So for example, if used as a tab for a tabbed pane, you 
     * could specify "topleft,topright" to have everything but the bottom corners 
     * and side have the border. Valid values are: topleft, topright, bottomright, 
     * bottomleft
     * 
     * @JSFProperty
     * @return the corners
     */
    public abstract String getCorners();

    /**
     * The foreground color of the DIV
     * 
     * @JSFProperty
     * @return the color
     */
    public abstract String getColor();

    /**
     * The radius of the corners in pixels. (Default: 8)
     * 
     * @JSFProperty
     *   defaultValue = "Integer.valueOf(8)"
     * @return the radius
     */
    public abstract Integer getRadius();

    /**
     * If given, a static size image will be produced. This could be useful for 
     * older browsers. If not given, the DIV that will be created will stretch 
     * to its contents using CSS2 (and CSS expressions in IE6). Value must 
     * contain two numbers, with width first. Example: 640x480
     * 
     * @JSFProperty
     * @return the size
     */
    public abstract String getSize();

    /**
     * @see org.apache.myfaces.custom.htmlTag.HtmlTag#getStyle()
     */
    public String getStyle()
    {
        String style = super.getStyle();
        StringBuffer sb = (style == null) ? new StringBuffer() : new StringBuffer(style).append(';');
        
        if (style == null || style.indexOf("position:") < 0)
        {
            sb.append("position: relative;");
        }
        if ("table".equals(getValue()))
        {
            sb.append("border-collapse: collapse;");
        }
        
        return sb.toString();
    }
    
    /**
     * @JSFProperty
     * @see org.apache.myfaces.custom.div.Div#getValue()
     */
    public Object getValue()
    {
        if ("table".equalsIgnoreCase(getLayout()) && getSize() == null)
        {
            return "table";
        }
        return "div";
    }
    
}
