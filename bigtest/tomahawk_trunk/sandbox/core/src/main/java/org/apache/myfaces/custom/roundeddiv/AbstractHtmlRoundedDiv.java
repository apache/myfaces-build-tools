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
     * @JSFProperty
     * @return the contentStyle
     */
    public abstract String getContentStyle();

    /**
     * @JSFProperty
     * @return the contentStyleClass
     */
    public abstract String getContentStyleClass();

    /**
     * @JSFProperty
     *   defaultValue = "div"
     * @return
     */
    public abstract String getLayout();

    /**
     * @JSFProperty
     * @see org.apache.myfaces.custom.htmlTag.HtmlTag#getStyle()
     */
    public abstract String getStyle();

    /**
     * @JSFProperty
     * @return the backgroundColor
     */
    public abstract String getBackgroundColor();

    /**
     * @JSFProperty
     * @return the borderColor
     */
    public abstract String getBorderColor();

    /**
     * @JSFProperty
     *   defaultValue = "false";
     * @return the inverse
     */
    public abstract Boolean getInverse();

    /**
     * @JSFProperty
     *   defaultValue = "Integer.valueOf(8)"
     * @return the borderWidth
     */
    public abstract Integer getBorderWidth();

    /**
     * @JSFProperty
     * @return the corners
     */
    public abstract String getCorners();

    /**
     * @JSFProperty
     * @return the color
     */
    public abstract String getColor();

    /**
     * @JSFProperty
     *   defaultValue = "Integer.valueOf(8)"
     * @return the radius
     */
    public abstract Integer getRadius();

    /**
     * @JSFProperty
     * @return the size
     */
    public abstract String getSize();

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
