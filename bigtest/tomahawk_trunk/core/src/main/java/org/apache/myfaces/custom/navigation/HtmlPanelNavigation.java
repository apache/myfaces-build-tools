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
package org.apache.myfaces.custom.navigation;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Panel, that includes navigation items ({@link HtmlCommandNavigation}) and other
 * components (separators).
 * 
 * @JSFComponent
 *   name = "t:panelNavigation"
 *   tagClass = "org.apache.myfaces.custom.navigation.HtmlPanelNavigationTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelNavigation
        extends HtmlPanelGroup
{
    private static final Log log = LogFactory.getLog(HtmlPanelNavigation.class);

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    private static final String PREVIOUS_VIEW_ROOT = HtmlPanelNavigation.class.getName() + ".PREVIOUS_VIEW_ROOT";
    private boolean _itemOpenActiveStatesRestored = false;

    public void decode(FacesContext context)
    {
        super.decode(context);    //To change body of overridden methods use File | Settings | File Templates.
        
        //Save the current view root for later reference...
        context.getExternalContext().getRequestMap().put(PREVIOUS_VIEW_ROOT, context.getViewRoot());
        //...and remember that this instance needs NO special treatment on rendering:
        _itemOpenActiveStatesRestored = true;
    }

    public void encodeBegin(FacesContext context) throws IOException
    {
        if (!_itemOpenActiveStatesRestored && getChildCount() > 0)
        {
            UIViewRoot previousRoot = (UIViewRoot)context.getExternalContext().getRequestMap().get(PREVIOUS_VIEW_ROOT);
            if (previousRoot != null)
            {
                restoreOpenActiveStates(context, previousRoot, getChildren());
            }
            else
            {
                //no previous root, means no decode was done
                //--> a new request
            }
        }
        
        super.encodeBegin(context);    //To change body of overridden methods use File | Settings | File Templates.
    }
    
    public void restoreOpenActiveStates(FacesContext facesContext,
                                        UIViewRoot previousRoot,
                                        List children)
    {
        for (Iterator it = children.iterator(); it.hasNext(); )
        {
            UIComponent child = (UIComponent)it.next();
            if (child instanceof HtmlCommandNavigation)
            {
                HtmlCommandNavigation previousItem = (HtmlCommandNavigation)previousRoot.findComponent(child.getClientId(facesContext));
                if (previousItem != null)
                {

                    HtmlCommandNavigation childItem = (HtmlCommandNavigation)child;
                    if(previousItem.getOpenDirectly()!=null)
                    {
                        childItem.setOpen(previousItem.isOpen());
                    }
                    else if(previousItem.getValueBinding("open")!=null)
                    {
                        childItem.setValueBinding("open",previousItem.getValueBinding("open"));
                    }

                    if(previousItem.getActiveDirectly()!=null)
                    {
                        childItem.setActive(previousItem.isActive());
                    }
                    else if(previousItem.getValueBinding("active")!=null)
                    {
                        childItem.setValueBinding("active",previousItem.getValueBinding("active"));
                    }
                }
                else
                {
                    log.error("Navigation item " + child.getClientId(facesContext) + " not found in previous view.");
                }
                if (child.getChildCount() > 0)
                {
                    restoreOpenActiveStates(facesContext, previousRoot, child.getChildren());
                }
            }
        }
    }

    // HTML table attributes
    private String _align;
    private String _bgcolor;
    private Integer _border;
    private String _cellpadding;
    private String _cellspacing;
    private String _frame;
    private String _rules;
    private String _summary;
    private String _width;

    // HTML standard attributes
    private String _dir = null;
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
    private String _title = null;

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelNavigation";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Navigation";
    
    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    private String _itemClass = null;
    private String _openItemClass = null;
    private String _activeItemClass = null;
    private String _separatorClass = null;
    private String _itemStyle = null;
    private String _openItemStyle = null;
    private String _activeItemStyle = null;
    private String _separatorStyle = null;

    public HtmlPanelNavigation()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setItemClass(String itemClass)
    {
        _itemClass = itemClass;
    }

    public String getItemClass()
    {
        if (_itemClass != null) return _itemClass;
        ValueBinding vb = getValueBinding("itemClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOpenItemClass(String openItemClass)
    {
        _openItemClass = openItemClass;
    }

    public String getOpenItemClass()
    {
        if (_openItemClass != null) return _openItemClass;
        ValueBinding vb = getValueBinding("openItemClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setActiveItemClass(String activeItemClass)
    {
        _activeItemClass = activeItemClass;
    }

    public String getActiveItemClass()
    {
        if (_activeItemClass != null) return _activeItemClass;
        ValueBinding vb = getValueBinding("activeItemClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setSeparatorClass(String separatorClass)
    {
        _separatorClass = separatorClass;
    }

    public String getSeparatorClass()
    {
        if (_separatorClass != null) return _separatorClass;
        ValueBinding vb = getValueBinding("separatorClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setItemStyle(String itemStyle)
    {
        _itemStyle = itemStyle;
    }

    public String getItemStyle()
    {
        if (_itemStyle != null) return _itemStyle;
        ValueBinding vb = getValueBinding("itemStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOpenItemStyle(String openItemStyle)
    {
        _openItemStyle = openItemStyle;
    }

    public String getOpenItemStyle()
    {
        if (_openItemStyle != null) return _openItemStyle;
        ValueBinding vb = getValueBinding("openItemStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setActiveItemStyle(String activeItemStyle)
    {
        _activeItemStyle = activeItemStyle;
    }

    public String getActiveItemStyle()
    {
        if (_activeItemStyle != null) return _activeItemStyle;
        ValueBinding vb = getValueBinding("activeItemStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setSeparatorStyle(String separatorStyle)
    {
        _separatorStyle = separatorStyle;
    }

    public String getSeparatorStyle()
    {
        if (_separatorStyle != null) return _separatorStyle;
        ValueBinding vb = getValueBinding("separatorStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }


    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[31];
        values[0] = super.saveState(context);
        values[1] = _itemClass;
        values[2] = _openItemClass;
        values[3] = _activeItemClass;
        values[4] = _separatorClass;
        values[5] = _itemStyle;
        values[6] = _openItemStyle;
        values[7] = _activeItemStyle;
        values[8] = _separatorStyle;
        values[9] = _title;
        values[10] = _align;
        values[11] = _bgcolor;
        values[12] = _border;
        values[13] = _cellpadding;
        values[14] = _cellspacing;
        values[15] = _frame;
        values[16] = _rules;
        values[17] = _summary;
        values[18] = _width;
        values[19] = _dir;
        values[20] = _lang;
        values[21] = _onclick;
        values[22] = _ondblclick;
        values[23] = _onkeydown;
        values[24] = _onkeypress;
        values[25] = _onkeyup;
        values[26] = _onmousedown;
        values[27] = _onmousemove;
        values[28] = _onmouseout;
        values[29] = _onmouseover;
        values[30] = _onmouseup;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _itemClass = (String)values[1];
        _openItemClass = (String)values[2];
        _activeItemClass = (String)values[3];
        _separatorClass = (String)values[4];
        _itemStyle = (String)values[5];
        _openItemStyle = (String)values[6];
        _activeItemStyle = (String)values[7];
        _separatorStyle = (String)values[8];
        _title = (String)values[9];
        _align = (String)values[10];
        _bgcolor = (String)values[11];
        _border = (Integer)values[12];
        _cellpadding = (String)values[13];
        _cellspacing = (String)values[14];
        _frame = (String)values[15];
        _rules = (String)values[16];
        _summary = (String)values[17];
        _width = (String)values[18];
        _dir = (String)values[19];
        _lang = (String)values[20];
        _onclick = (String)values[21];
        _ondblclick = (String)values[22];
        _onkeydown = (String)values[23];
        _onkeypress = (String)values[24];
        _onkeyup = (String)values[25];
        _onmousedown = (String)values[26];
        _onmousemove = (String)values[27];
        _onmouseout = (String)values[28];
        _onmouseover = (String)values[29];
        _onmouseup = (String)values[30];
    }
    //------------------ GENERATED CODE END ---------------------------------------
    public void setAlign(String align)
    {
        _align = align;
    }

    public String getAlign()
    {
        if (_align != null) return _align;
        ValueBinding vb = getValueBinding("align");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBgcolor(String bgcolor)
    {
        _bgcolor = bgcolor;
    }

    public String getBgcolor()
    {
        if (_bgcolor != null) return _bgcolor;
        ValueBinding vb = getValueBinding("bgcolor");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBorder(int border)
    {
        _border = new Integer(border);
    }

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

    public String getCellpadding()
    {
        if (_cellpadding != null) return _cellpadding;
        ValueBinding vb = getValueBinding("cellpadding");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCellspacing(String cellspacing)
    {
        _cellspacing = cellspacing;
    }

    public String getCellspacing()
    {
        if (_cellspacing != null) return _cellspacing;
        ValueBinding vb = getValueBinding("cellspacing");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDir(String dir)
    {
        _dir = dir;
    }

    public String getDir()
    {
        if (_dir != null) return _dir;
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFrame(String frame)
    {
        _frame = frame;
    }

    public String getFrame()
    {
        if (_frame != null) return _frame;
        ValueBinding vb = getValueBinding("frame");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLang(String lang)
    {
        _lang = lang;
    }

    public String getLang()
    {
        if (_lang != null) return _lang;
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    public String getOnclick()
    {
        if (_onclick != null) return _onclick;
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    public String getOndblclick()
    {
        if (_ondblclick != null) return _ondblclick;
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeydown(String onkeydown)
    {
        _onkeydown = onkeydown;
    }

    public String getOnkeydown()
    {
        if (_onkeydown != null) return _onkeydown;
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    public String getOnkeypress()
    {
        if (_onkeypress != null) return _onkeypress;
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeyup(String onkeyup)
    {
        _onkeyup = onkeyup;
    }

    public String getOnkeyup()
    {
        if (_onkeyup != null) return _onkeyup;
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousedown(String onmousedown)
    {
        _onmousedown = onmousedown;
    }

    public String getOnmousedown()
    {
        if (_onmousedown != null) return _onmousedown;
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousemove(String onmousemove)
    {
        _onmousemove = onmousemove;
    }

    public String getOnmousemove()
    {
        if (_onmousemove != null) return _onmousemove;
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseout(String onmouseout)
    {
        _onmouseout = onmouseout;
    }

    public String getOnmouseout()
    {
        if (_onmouseout != null) return _onmouseout;
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseover(String onmouseover)
    {
        _onmouseover = onmouseover;
    }

    public String getOnmouseover()
    {
        if (_onmouseover != null) return _onmouseover;
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseup(String onmouseup)
    {
        _onmouseup = onmouseup;
    }

    public String getOnmouseup()
    {
        if (_onmouseup != null) return _onmouseup;
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRules(String rules)
    {
        _rules = rules;
    }

    public String getRules()
    {
        if (_rules != null) return _rules;
        ValueBinding vb = getValueBinding("rules");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public String getSummary()
    {
        if (_summary != null) return _summary;
        ValueBinding vb = getValueBinding("summary");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    public String getTitle()
    {
        if (_title != null) return _title;
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWidth(String width)
    {
        _width = width;
    }

    public String getWidth()
    {
        if (_width != null) return _width;
        ValueBinding vb = getValueBinding("width");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

}
