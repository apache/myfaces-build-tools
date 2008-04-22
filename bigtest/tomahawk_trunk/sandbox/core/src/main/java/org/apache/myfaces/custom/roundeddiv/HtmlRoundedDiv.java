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

import java.awt.Color;
import java.awt.Dimension;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.div.Div;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * Component that generates a DIV tag with rounded corners that may
 * be either 3D or 2D in appearence.
 *
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlRoundedDiv extends Div
{
    public final static String COMPONENT_TYPE = "org.apache.myfaces.HtmlRoundedDiv";
    private final static String DEFAULT_RENDERER_TYPE = HtmlRoundedDivRenderer.RENDERER_TYPE;

    private Color color;
    private Color backgroundColor;
    private Color borderColor;
    private Integer borderWidth;
    private Integer radius;
    private Dimension size;
    private String corners;
    private Boolean inverse;
    private String layout;
    private String contentStyle;
    private String contentStyleClass;

    /**
     * Default constructor
     */
    public HtmlRoundedDiv()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * @return the contentStyle
     */
    public String getContentStyle()
    {
        if (this.contentStyle != null)
            return this.contentStyle;
        ValueBinding vb = getValueBinding("contentStyle");
        return vb == null ? null : RendererUtils.getStringValue(
                getFacesContext(), vb);
    }

    /**
     * @param contentStyle the contentStyle to set
     */
    public void setContentStyle(String contentStyle)
    {
        this.contentStyle = contentStyle;
    }

    /**
     * @return the contentStyleClass
     */
    public String getContentStyleClass()
    {
        if (this.contentStyleClass != null)
            return this.contentStyleClass;
        ValueBinding vb = getValueBinding("contentStyleClass");
        return vb == null ? null : RendererUtils.getStringValue(
                getFacesContext(), vb);
    }

    /**
     * @param contentStyleClass the contentStyleClass to set
     */
    public void setContentStyleClass(String contentStyleClass)
    {
        this.contentStyleClass = contentStyleClass;
    }

    public String getLayout()
    {
        if (this.layout != null)
            return this.layout;
        ValueBinding vb = getValueBinding("layout");
        String value = vb == null ? null : RendererUtils.getStringValue(
                getFacesContext(), vb);
        return value == null ? "div" : value;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

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
     * @return the backgroundColor
     */
    public Color getBackgroundColor()
    {
        if (this.backgroundColor != null)
            return this.backgroundColor;
        ValueBinding vb = getValueBinding("backgroundColor");
        return vb == null ? null : asColor(vb.getValue(getFacesContext()));
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the borderColor
     */
    public Color getBorderColor()
    {
        if (this.borderColor != null)
            return this.borderColor;
        ValueBinding vb = getValueBinding("borderColor");
        return vb == null ? null : asColor(vb.getValue(getFacesContext()));
    }

    /**
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }

    /**
     * @return the inverse
     */
    public Boolean getInverse()
    {
        if (this.inverse != null)
            return this.inverse;
        ValueBinding vb = getValueBinding("inverse");
        Boolean value = vb == null ? null : (Boolean) vb
                .getValue(getFacesContext());
        return value == null ? Boolean.FALSE : value;
    }

    /**
     * @param inverse the inverse to set
     */
    public void setInverse(Boolean inverse)
    {
        this.inverse = inverse;
    }

    /**
     * @return the borderWidth
     */
    public Integer getBorderWidth()
    {
        if (this.borderWidth != null)
            return this.borderWidth;
        ValueBinding vb = getValueBinding("borderWidth");
        Object value = vb == null ? null : vb.getValue(getFacesContext());
        if (value == null || "".equals(value))
        {
            value = new Integer(8);
        }
        else if (!(value instanceof Integer))
        {
            value = new Integer(value.toString());
        }
        return (Integer) value;
    }

    /**
     * @param borderWidth the borderWidth to set
     */
    public void setBorderWidth(Integer borderWidth)
    {
        this.borderWidth = borderWidth;
    }

    /**
     * @return the corners
     */
    public String getCorners()
    {
        if (this.corners != null)
            return this.corners;
        ValueBinding vb = getValueBinding("corners");
        String value = vb == null ? null : RendererUtils.getStringValue(
                getFacesContext(), vb);
        return value == null || "".equals(value) ? null : value;
    }

    /**
     * @param corners the corners to set
     */
    public void setCorners(String corners)
    {
        this.corners = corners;
    }

    /**
     * @return the color
     */
    public Color getColor()
    {
        if (this.color != null)
            return this.color;
        ValueBinding vb = getValueBinding("color");
        return vb == null ? null : asColor(vb.getValue(getFacesContext()));
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * @return the radius
     */
    public Integer getRadius()
    {
        if (this.radius != null)
            return this.radius;
        ValueBinding vb = getValueBinding("radius");
        Object value = vb == null ? null : vb.getValue(getFacesContext());
        if (value == null || "".equals(value))
        {
            value = new Integer(8);
        }
        else if (!(value instanceof Integer))
        {
            value = new Integer(value.toString());
        }
        return (Integer) value;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(Integer radius)
    {
        this.radius = radius;
    }

    /**
     * @return the size
     */
    public Dimension getSize()
    {
        if (this.size != null)
            return this.size;
        ValueBinding vb = getValueBinding("size");
        if (vb == null)
            return null;

        Object value = vb.getValue(getFacesContext());

        if (value == null || "".equals(value))
        {
            return null;
        }
        else if (value instanceof Dimension)
        {
            return (Dimension) value;
        }
        else
        {
            Matcher m = Pattern.compile("(\\d+)\\D+(\\d+)").matcher(
                    value.toString());
            if (!m.find())
            {
                throw new IllegalArgumentException("Invalid dimension");
            }
            return new Dimension(Integer.parseInt(m.group(1)), Integer
                    .parseInt(m.group(2)));
        }
    }

    /**
     * @param size the size to set
     */
    public void setSize(Dimension size)
    {
        this.size = size;
    }

    /**
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
    
    /**
     * @see org.apache.myfaces.custom.htmlTag.HtmlTag#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
        return new Object[] { super.saveState(context), color, backgroundColor,
                borderColor, borderWidth, radius, size, corners, inverse,
                layout, contentStyle, contentStyleClass };
    }

    /**
     * @see org.apache.myfaces.custom.htmlTag.HtmlTag#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object[] arr = (Object[]) state;
        int index = -1;
        super.restoreState(context, arr[++index]);
        this.color = (Color) arr[++index];
        this.backgroundColor = (Color) arr[++index];
        this.borderColor = (Color) arr[++index];
        this.borderWidth = (Integer) arr[++index];
        this.radius = (Integer) arr[++index];
        this.size = (Dimension) arr[++index];
        this.corners = (String) arr[++index];
        this.inverse = (Boolean) arr[++index];
        this.layout = (String) arr[++index];
        this.contentStyle = (String) arr[++index];
        this.contentStyleClass = (String) arr[++index];
    }

    private Color asColor(Object value)
    {
        if (value == null || "".equals(value))
        {
            return null;
        }
        else if (value instanceof Color)
        {
            return (Color) value;
        }
        else
        {
            return Color.decode(value.toString());
        }
    }
}
