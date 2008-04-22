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
package org.apache.myfaces.custom.accordion;

import javax.faces.component.UIComponent;

import org.apache.myfaces.taglib.html.ext.HtmlPanelGroupTag;


/**
 * @author Martin Marinschek
 *
 *
 *
 * @version $Revision: $ $Date: $
 *          <p/>
 */
public class HtmlAccordionPanelTag  extends HtmlPanelGroupTag
{
    private String _layout;

    //style properties
    private String _expandedBackColor;
    private String _expandedTextColor;
    private String _expandedFontWeight;
    private String _collapsedBackColor;
    private String _collapsedTextColor;
    private String _collapsedFontWeight;
    private String _hoverBackColor;
    private String _hoverTextColor;
    private String _borderColor;


//    private String _expandedStyleClass; //Background, Textcolor, Fontweight
//    private String _hoverStyleClass; //Background, Textcolor
//    private String _collapsedStyleClass; //Background, Textcolor, Fontweight


    public String getComponentType()
    {
        return HtmlAccordionPanel.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlAccordionPanel.DEFAULT_RENDERER_TYPE;
    }

    public void release()
    {

        super.release();

        _layout = null;
        //style properties
        _expandedBackColor = null;
        _expandedTextColor = null;
        _expandedFontWeight = null;
        _collapsedBackColor = null;
        _collapsedTextColor = null;
        _collapsedFontWeight = null;
        _hoverBackColor = null;
        _hoverTextColor = null;
        _borderColor = null;
    }

    protected void setProperties(UIComponent component)
    {

        super.setProperties(component);

        setStringProperty(component,"layout",_layout);
        //style properties
        setStringProperty(component, "expandedBackColor", _expandedBackColor);
        setStringProperty(component, "expandedTextColor", _expandedTextColor);
        setStringProperty(component, "expandedFontWeight", _expandedFontWeight);
        setStringProperty(component, "collapsedBackColor", _collapsedBackColor);
        setStringProperty(component, "collapsedTextColor", _collapsedTextColor);
        setStringProperty(component, "collapsedFontWeight", _collapsedFontWeight);
        setStringProperty(component, "hoverBackColor", _hoverBackColor);
        setStringProperty(component, "hoverTextColor", _hoverTextColor);
        setStringProperty(component, "borderColor", _borderColor);

    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }


    public void setExpandedBackColor(String expandedBackColor)
    {
        _expandedBackColor = expandedBackColor;
    }


    public void setExpandedTextColor(String expandedTextColor)
    {
        _expandedTextColor = expandedTextColor;
    }


    public void setExpandedFontWeight(String expandedFontWeight)
    {
        _expandedFontWeight = expandedFontWeight;
    }


    public void setCollapsedBackColor(String collapsedBackColor)
    {
        _collapsedBackColor = collapsedBackColor;
    }


    public void setCollapsedTextColor(String collapsedTextColor)
    {
        _collapsedTextColor = collapsedTextColor;
    }


    public void setCollapsedFontWeight(String collapsedFontWeight)
    {
        _collapsedFontWeight = collapsedFontWeight;
    }


    public void setHoverBackColor(String hoverBackColor)
    {
        _hoverBackColor = hoverBackColor;
    }


    public void setHoverTextColor(String hoverTextColor)
    {
        _hoverTextColor = hoverTextColor;
    }


    public void setBorderColor(String borderColor)
    {
        _borderColor = borderColor;
    }
}
