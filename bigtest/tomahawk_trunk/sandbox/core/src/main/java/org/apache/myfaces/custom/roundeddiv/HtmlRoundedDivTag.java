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

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlOutputTextTagBase;

/**
 * The Tag implementation for the {@link HtmlRoundedDiv} component and those sorry
 * souls using JSP instead of facelets. 
 *
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlRoundedDivTag extends HtmlOutputTextTagBase
{
    //DivTag
    private String color;
    private String backgroundColor;
    private String borderColor;
    private String borderWidth;
    private String radius;
    private String size;
    private String corners;
    private String inverse;
    private String layout;
    private String contentStyle;
    private String contentStyleClass;

    /**
     * @see org.apache.myfaces.custom.htmlTag.HtmlTagTag#getRendererType()
     */
    public String getRendererType()
    {
        return HtmlRoundedDivRenderer.RENDERER_TYPE;
    }

    /**
     * @see org.apache.myfaces.custom.div.DivTag#getComponentType()
     */
    public String getComponentType()
    {
        return HtmlRoundedDiv.COMPONENT_TYPE;
    }

    private String _enabledOnUserRole;
    
    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }
 
    private String _visibleOnUserRole;
    
    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }
    
    /**
     * @param inverse the inverse to set
     */
    public void setInverse(String inverse)
    {
        this.inverse = inverse;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(String backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(String borderColor)
    {
        this.borderColor = borderColor;
    }

    /**
     * @param borderWidth the borderWidth to set
     */
    public void setBorderWidth(String borderWidth)
    {
        this.borderWidth = borderWidth;
    }

    /**
     * @param corners the corners to set
     */
    public void setCorners(String corners)
    {
        this.corners = corners;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color)
    {
        this.color = color;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(String radius)
    {
        this.radius = radius;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size)
    {
        this.size = size;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(String layout)
    {
        this.layout = layout;
    }

    /**
     * @param contentStyle the contentStyle to set
     */
    public void setContentStyle(String contentStyle)
    {
        this.contentStyle = contentStyle;
    }

    /**
     * @param contentStyleClass the contentStyleClass to set
     */
    public void setContentStyleClass(String contentStyleClass)
    {
        this.contentStyleClass = contentStyleClass;
    }

    /**
     * @see org.apache.myfaces.custom.div.DivTag#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setColorProperty(component, "color", this.color);
        setColorProperty(component, "backgroundColor", this.backgroundColor);
        setColorProperty(component, "borderColor", this.borderColor);
        setIntegerProperty(component, "borderWidth", this.borderWidth);
        setIntegerProperty(component, "radius", this.radius);
        setStringProperty(component, "size", this.size);
        setStringProperty(component, "corners", this.corners);
        setStringProperty(component, "contentStyle", this.contentStyle);
        setStringProperty(component, "contentStyleClass", this.contentStyleClass);
        setStringProperty(component, "layout", this.layout);
        setStringProperty(component, "enabledOnUserRole", this._enabledOnUserRole);
        setStringProperty(component, "visibleOnUserRole", this._visibleOnUserRole);
        setBooleanProperty(component, "inverse", this.inverse);
    }

    protected void setColorProperty(UIComponent component, String propName,
            String value)
    {
        if (value != null)
        {
            if (isValueReference(value))
            {
                ValueBinding vb = getFacesContext().getApplication()
                        .createValueBinding(value);
                component.setValueBinding(propName, vb);
            }
            else
            {
                component.getAttributes().put(propName, Color.decode(value));
            }
        }
    }

    /**
     * @see org.apache.myfaces.custom.htmlTag.HtmlTagTag#release()
     */
    public void release()
    {
        super.release();
        backgroundColor = null;
        borderColor = null;
        borderWidth = null;
        radius = null;
        size = null;
        corners = null;
        inverse = null;
        layout = null;
        contentStyle = null;
        contentStyleClass = null;
    }
}
