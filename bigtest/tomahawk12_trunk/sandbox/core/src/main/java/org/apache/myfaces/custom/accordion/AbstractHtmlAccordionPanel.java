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

import org.apache.myfaces.component.html.ext.HtmlPanelGroup;


/**
 * A group of panels, which can be opened and closed. See attribute layout for 
 * further description on how opening and closing works. 
 * 
 * Extends standard panelGroup by user role support.
 * 
 * @JSFComponent
 *   name = "s:accordionPanel"
 *   class = "org.apache.myfaces.custom.accordion.HtmlAccordionPanel"
 *   superClass = "org.apache.myfaces.custom.accordion.AbstractHtmlAccordionPanel"
 *   tagClass = "org.apache.myfaces.custom.accordion.HtmlAccordionPanelTag"
 *   
 * @author Martin Marinschek
 * @version $Revision$ $Date$
 *          <p/>
 */
public abstract class AbstractHtmlAccordionPanel extends HtmlPanelGroup
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

    //expansionstate of children
    private List _childExpanded;

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = _childExpanded;

        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _childExpanded = (List) values[1];
    }

    /**
     * Defines the layout of this accordionPanel. If you set this to 
     * 'accordion', opening a panel will close all other panels. 
     * If you set this to 'toggling', opening a panel doesn't 
     * affect the state of the other panels. You can close 
     * a panel by clicking on the header of this panel a second time.
     * 
     * @JSFProperty
     *   defaultValue="accordion"
     */
    public abstract String getLayout();

    /**
     * Defines the background color for expanded state.
     * 
     * @JSFProperty
     */
    public abstract String getExpandedBackColor();

    /**
     * Defines the text color for expanded state.
     * 
     * @JSFProperty
     */
    public abstract String getExpandedTextColor();

    /**
     * Defines the font weight for expanded state.
     * 
     * @JSFProperty
     */
    public abstract String getExpandedFontWeight();

    /**
     * Defines the background color for collapsed state.
     * 
     * @JSFProperty
     */
    public abstract String getCollapsedBackColor();

    /**
     * Defines the text color for collapsed state.
     * 
     * @JSFProperty
     */
    public abstract String getCollapsedTextColor();

    /**
     * Defines the font weight for collapsed state.
     * 
     * @JSFProperty
     */
    public abstract String getCollapsedFontWeight();

    /**
     * Defines the background color on hover.
     * 
     * @JSFProperty
     */
    public abstract String getHoverBackColor();

    /**
     * Defines the text color on hover.
     * 
     * @JSFProperty
     */
    public abstract String getHoverTextColor();

    /**
     * Defines the color of the border.
     * 
     * @JSFProperty
     */
    public abstract String getBorderColor();

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
