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

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.component.html.ext.HtmlPanelGroup;


/**
 * @author Martin Marinschek
 * @version $Revision: $ $Date: $
 *          <p/>
 */
public class HtmlAccordionPanel extends HtmlPanelGroup
{
    public static String ACCORDION_LAYOUT = "accordion";
    public static String TOGGLING_LAYOUT = "toggling";

    public static String EXPANDED_BACK_COLOR = "expandedBg";
    public static String EXPANDED_TEXT_COLOR = "expandedTextColor";
    public static String EXPANDED_FONT_WEIGHT = "expandedFontWeight";
    public static String COLLAPSED_BACK_COLOR = "collapsedBg";
    public static String COLLAPSED_TEXT_COLOR = "collapsedTextColor";
    public static String COLLAPSED_FONT_WEIGHT = "collapsedFontWeight";
    public static String HOVER_BACK_COLOR = "hoverBg";
    public static String HOVER_TEXT_COLOR = "hoverTextColor";
    public static String BORDER_COLOR = "borderColor";

    public static String EXPAND_STATEHOLDER_ID = "_STATEHOLDER";

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlAccordionPanel";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.AccordionPanel";

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

    //expansionstate of children
    private List _childExpanded;


    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[12];
        values[0] = super.saveState(context);
        values[1] = _layout;

        values[2] = _expandedBackColor;
        values[3] = _expandedTextColor;
        values[4] = _expandedFontWeight;
        values[5] = _collapsedBackColor;
        values[6] = _collapsedTextColor;
        values[7] = _collapsedFontWeight;
        values[8] = _hoverBackColor;
        values[9] = _hoverTextColor;
        values[10] = _borderColor;

        values[11] = _childExpanded;

        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _layout = (String) values[1];

        _expandedBackColor = (String) values[2];
        _expandedTextColor = (String) values[3];
        _expandedFontWeight = (String) values[4];
        _collapsedBackColor = (String) values[5];
        _collapsedTextColor = (String) values[6];
        _collapsedFontWeight = (String) values[7];
        _hoverBackColor = (String) values[8];
        _hoverTextColor = (String) values[9];
        _borderColor = (String) values[10];

        _childExpanded = (List) values[11];
    }

    public HtmlAccordionPanel()
    {
        super();

        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getLayout()
    {
        if(_layout != null)
        {
            return _layout;
        }
        ValueBinding vb = getValueBinding("layout");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               ACCORDION_LAYOUT;
    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }


    public String getExpandedBackColor()
    {
        if(_expandedBackColor != null)
        {
            return _expandedBackColor;
        }
        ValueBinding vb = getValueBinding("expandedBackColor");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "#63699c";
    }


    public void setExpandedBackColor(String expandedBackColor)
    {
        _expandedBackColor = expandedBackColor;
    }


    public String getExpandedTextColor()
    {
        if(_expandedTextColor != null)
        {
            return _expandedTextColor;
        }
        ValueBinding vb = getValueBinding("expandedTextColor");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "#ffffff";
    }


    public void setExpandedTextColor(String expandedTextColor)
    {
        _expandedTextColor = expandedTextColor;
    }


    public String getExpandedFontWeight()
    {
        if(_expandedFontWeight != null)
        {
            return _expandedFontWeight;
        }
        ValueBinding vb = getValueBinding("expandedFontWeight");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "bold";
    }


    public void setExpandedFontWeight(String expandedFontWeight)
    {
        _expandedFontWeight = expandedFontWeight;
    }


    public String getCollapsedBackColor()
    {
        if(_collapsedBackColor != null)
        {
            return _collapsedBackColor;
        }
        ValueBinding vb = getValueBinding("collapsedBackColor");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "#6b79a5";
    }


    public void setCollapsedBackColor(String collapsedBackColor)
    {
        _collapsedBackColor = collapsedBackColor;
    }


    public String getCollapsedTextColor()
    {
        if(_collapsedTextColor != null)
        {
            return _collapsedTextColor;
        }
        ValueBinding vb = getValueBinding("collapsedTextColor");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "#ced7ef";
    }


    public void setCollapsedTextColor(String collapsedTextColor)
    {
        _collapsedTextColor = collapsedTextColor;
    }


    public String getCollapsedFontWeight()
    {
        if(_collapsedFontWeight != null)
        {
            return _collapsedFontWeight;
        }
        ValueBinding vb = getValueBinding("collapsedFontWeight");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "normal";
    }


    public void setCollapsedFontWeight(String collapsedFontWeight)
    {
        _collapsedFontWeight = collapsedFontWeight;
    }


    public String getHoverBackColor()
    {
        if(_hoverBackColor != null)
        {
            return _hoverBackColor;
        }
        ValueBinding vb = getValueBinding("hoverBackColor");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "#63699c";
    }


    public void setHoverBackColor(String hoverBackColor)
    {
        _hoverBackColor = hoverBackColor;
    }


    public String getHoverTextColor()
    {
        if(_hoverTextColor != null)
        {
            return _hoverTextColor;
        }
        ValueBinding vb = getValueBinding("hoverTextColor");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "#ffffff";
    }


    public void setHoverTextColor(String hoverTextColor)
    {
        _hoverTextColor = hoverTextColor;
    }


    public String getBorderColor()
    {
        if(_borderColor != null)
        {
            return _borderColor;
        }
        ValueBinding vb = getValueBinding("borderColor");
        return vb != null ?
               vb.getValue(getFacesContext()).toString() :
               "#1f669b";
    }


    public void setBorderColor(String borderColor)
    {
        _borderColor = borderColor;
    }

    //TODO
    public List getChildExpanded()
    {
        if(_childExpanded == null)
        {
            _childExpanded = new ArrayList(getChildCount());
            for(int i = 0; i < getChildCount(); i++)
            {
                Integer curState = new Integer(i == 0 ? 1 : 0);
                _childExpanded.add(curState);
            }
        }
        return _childExpanded;
    }


    public void setChildExpanded(List childExpanded)
    {
        _childExpanded = childExpanded;
    }
}
