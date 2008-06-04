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
package org.apache.myfaces.examples.roundeddiv;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

/**
 * Backing bean for the rounded div example.
 *
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RoundedDivBean implements Serializable
{
    private String borderWidth = "8";
    private String radius = "8";
    private String borderColor = null;
    private String backgroundColor = null;
    private String color = "#CCCCCC";
    private String corners = null;
    private String size = null;
    private String layout = "div";
    private String style = null;
    private String styleClass = null;
    private String contentStyle = null;
    private String contentStyleClass = null;

    /**
     * @return the style
     */
    public String getStyle()
    {
        return this.style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style)
    {
        this.style = blankToNull(style);
    }

    /**
     * @return the styleClass
     */
    public String getStyleClass()
    {
        return this.styleClass;
    }

    /**
     * @param styleClass the styleClass to set
     */
    public void setStyleClass(String styleClass)
    {
        this.styleClass = blankToNull(styleClass);
    }

    /**
     * @return the contentStyle
     */
    public String getContentStyle()
    {
        return this.contentStyle;
    }

    /**
     * @param contentStyle the contentStyle to set
     */
    public void setContentStyle(String contentStyle)
    {
        this.contentStyle = blankToNull(contentStyle);
    }

    /**
     * @return the contentStyleClass
     */
    public String getContentStyleClass()
    {
        return this.contentStyleClass;
    }

    /**
     * @param contentStyleClass the contentStyleClass to set
     */
    public void setContentStyleClass(String contentStyleClass)
    {
        this.contentStyleClass = blankToNull(contentStyleClass);
    }

    public String getBackgroundColor()
    {
        return this.backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor)
    {
        this.backgroundColor = blankToNull(backgroundColor);
    }

    public String getBorderColor()
    {
        return this.borderColor;
    }

    public void setBorderColor(String borderColor)
    {
        this.borderColor = blankToNull(borderColor);
    }

    public String getBorderWidth()
    {
        return this.borderWidth;
    }

    public void setBorderWidth(String borderWidth)
    {
        this.borderWidth = blankToNull(borderWidth);
    }

    public String getColor()
    {
        return this.color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getCorners()
    {
        return this.corners;
    }

    public void setCorners(String corners)
    {
        this.corners = blankToNull(corners);
    }

    public String getRadius()
    {
        return this.radius;
    }

    public void setRadius(String radius)
    {
        this.radius = blankToNull(radius);
    }

    public String getSize()
    {
        return this.size;
    }

    public void setSize(String size)
    {
        this.size = blankToNull(size);
    }

    /**
     * @return the layout
     */
    public String getLayout()
    {
        return this.layout;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(String layout)
    {
        layout = blankToNull(layout);
        if (layout != null
                && ("table".equalsIgnoreCase(layout) || "div"
                        .equalsIgnoreCase(layout)))
        {
            this.layout = layout.toLowerCase();
        }
        else if (layout == null)
        {
            this.layout = null;
        }
    }

    public void reset(ActionEvent evt)
    {
        borderWidth = "8";
        radius = "8";
        borderColor = null;
        backgroundColor = null;
        color = "#CCCCCC";
        corners = null;
        size = null;
        layout = "div";
        style = null;
        styleClass = null;
        contentStyle = null;
        contentStyleClass = null;
    }

    private String blankToNull(String value)
    {
        return (value == null || value.length() == 0) ? null : value;
    }
}
