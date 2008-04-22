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
package org.apache.myfaces.custom.tabbedpane;

import java.util.Iterator;
import java.util.List;

import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UINamingContainer;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelTabbedPane
        extends HtmlPanelGroup
{
    //private static final Log log = LogFactory.getLog(HtmlPanelTabbedPane.class);

    private MethodBinding _tabChangeListener = null;
    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    //TODO: additional HTML Table attributes (see HtmlPanelTabbedPaneTag)
    // HTML table attributes
    private String _align;
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

    public void decode(FacesContext context)
    {
        super.decode(context);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void processDecodes(javax.faces.context.FacesContext context)
   {
       if (context == null) throw new NullPointerException("context");
       decode(context);

       int tabIdx = 0;
       int selectedIndex = getSelectedIndex();

       Iterator it = getFacetsAndChildren();

       while (it.hasNext())
       {
           UIComponent childOrFacet = getUIComponent((UIComponent) it.next());
           if (childOrFacet instanceof HtmlPanelTab) {
               if (isClientSide() || selectedIndex == tabIdx) {
                   childOrFacet.processDecodes(context);
               }
               tabIdx++;
           } else {
               childOrFacet.processDecodes(context);
           }
       }
   }

    public void processValidators(FacesContext context)
    {
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;

        int tabIdx = 0;
        int selectedIndex = getSelectedIndex();

        Iterator it = getFacetsAndChildren();

        while (it.hasNext())
        {
            UIComponent childOrFacet = getUIComponent((UIComponent) it.next());
            if (childOrFacet instanceof HtmlPanelTab) {
                if (isClientSide() || selectedIndex == tabIdx) {
                    childOrFacet.processValidators(context);
                }
                tabIdx++;
            } else {
                childOrFacet.processValidators(context);
            }
        }
    }

    public void processUpdates(FacesContext context)
    {
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;

        int tabIdx = 0;
        int selectedIndex = getSelectedIndex();

        Iterator it = getFacetsAndChildren();

        while (it.hasNext())
        {
            UIComponent childOrFacet = getUIComponent((UIComponent) it.next());
            if (childOrFacet instanceof HtmlPanelTab) {
                if (isClientSide() || selectedIndex == tabIdx) {
                    childOrFacet.processUpdates(context);
                }
                tabIdx++;
            } else {
                childOrFacet.processUpdates(context);
            }
        }
    }

    private UIComponent getUIComponent(UIComponent uiComponent)
    {
        /*todo: checking for UIForm is not enough - Trinidad form, etc.*/
        if (uiComponent instanceof UINamingContainer || uiComponent instanceof UIForm)
        {
            List children = uiComponent.getChildren();
            for (int i = 0, len = children.size(); i < len; i++)
            {
                uiComponent = getUIComponent((UIComponent)children.get(i));
            }
        }
        return uiComponent;
    }

    public void addTabChangeListener(TabChangeListener listener)
    {
        addFacesListener(listener);
    }

    public void removeTabChangeListener(TabChangeListener listener)
    {
        removeFacesListener(listener);
    }

    public MethodBinding getTabChangeListener()
    {
        return _tabChangeListener;
    }

    public void setTabChangeListener(MethodBinding tabChangeListener)
    {
        _tabChangeListener = tabChangeListener;
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        if (event instanceof TabChangeEvent)
        {
            TabChangeEvent tabChangeEvent = (TabChangeEvent)event;
            if (tabChangeEvent.getComponent() == this)
            {
                setSelectedIndex(tabChangeEvent.getNewTabIndex());
                getFacesContext().renderResponse();
            }
        }
        super.broadcast(event);

        MethodBinding tabChangeListenerBinding = getTabChangeListener();
        if (tabChangeListenerBinding != null)
        {
            try
            {
                tabChangeListenerBinding.invoke(getFacesContext(), new Object[]{event});
            }
            catch (EvaluationException e)
            {
                Throwable cause = e.getCause();
                if (cause != null && cause instanceof AbortProcessingException)
                {
                    throw (AbortProcessingException)cause;
                }
                else
                {
                    throw e;
                }
            }
        }
    }
    
    /**
     * Write out information about the toggling mode - the component might
     * be toggled server side or client side.
     */
    public boolean isClientSide()
    {
        return !getServerSideTabSwitch(); 
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelTabbedPane";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TabbedPane";
    private static final int DEFAULT_SELECTEDINDEX = 0;
    private static final boolean DEFAULT_SERVER_SIDE_TAB_SWITCH = false;

    private Integer _selectedIndex = null;
    private String _bgcolor = null;
    private String _activeTabStyleClass = null;
    private String _inactiveTabStyleClass = null;
    private String _disabledTabStyleClass = null;
    private String _activeSubStyleClass = null;
    private String _inactiveSubStyleClass = null;
    private String _tabContentStyleClass = null;
    private Boolean _serverSideTabSwitch = null;
    private String _activePanelTabVar;

    public HtmlPanelTabbedPane()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getActiveTabVar() {
        if (_activePanelTabVar != null) return _activePanelTabVar;
        ValueBinding vb = getValueBinding("activeTabVar");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setActiveTabVar(String activeTabVar) {
        _activePanelTabVar = activeTabVar;
    }


    public void setSelectedIndex(int selectedIndex)
    {
        _selectedIndex = new Integer(selectedIndex);
    }

    public int getSelectedIndex()
    {
        if (_selectedIndex != null) return _selectedIndex.intValue();
        ValueBinding vb = getValueBinding("selectedIndex");
        Number v = vb != null ? (Number)vb.getValue(getFacesContext()) : null;
        return v != null ? v.intValue() : DEFAULT_SELECTEDINDEX;
    }

    public void setBgcolor(String bgcolor)
    {
        _bgcolor = bgcolor;
    }

    public String getBgcolor()
    {
        if (_bgcolor != null) return _bgcolor;
        ValueBinding vb = getValueBinding("bgcolor");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setActiveTabStyleClass(String activeTabStyleClass)
    {
        _activeTabStyleClass = activeTabStyleClass;
    }

    public String getActiveTabStyleClass()
    {
        if (_activeTabStyleClass != null) return _activeTabStyleClass;
        ValueBinding vb = getValueBinding("activeTabStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setInactiveTabStyleClass(String inactiveTabStyleClass)
    {
        _inactiveTabStyleClass = inactiveTabStyleClass;
    }

    public String getInactiveTabStyleClass()
    {
        if (_inactiveTabStyleClass != null) return _inactiveTabStyleClass;
        ValueBinding vb = getValueBinding("inactiveTabStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setActiveSubStyleClass(String activeSubStyleClass)
    {
        _activeSubStyleClass = activeSubStyleClass;
    }

    public String getActiveSubStyleClass()
    {
        if (_activeSubStyleClass != null) return _activeSubStyleClass;
        ValueBinding vb = getValueBinding("activeSubStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setInactiveSubStyleClass(String inactiveSubStyleClass)
    {
        _inactiveSubStyleClass = inactiveSubStyleClass;
    }

    public String getInactiveSubStyleClass()
    {
        if (_inactiveSubStyleClass != null) return _inactiveSubStyleClass;
        ValueBinding vb = getValueBinding("inactiveSubStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setTabContentStyleClass(String tabContentStyleClass)
    {
        _tabContentStyleClass = tabContentStyleClass;
    }

    public String getTabContentStyleClass()
    {
        if (_tabContentStyleClass != null) return _tabContentStyleClass;
        ValueBinding vb = getValueBinding("tabContentStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }


    public String getDisabledTabStyleClass()
    {
        if (_disabledTabStyleClass != null) return _disabledTabStyleClass;
        ValueBinding vb = getValueBinding("disabledTabStyleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }


    public void setDisabledTabStyleClass(String disabledTabStyleClass)
    {
        this._disabledTabStyleClass = disabledTabStyleClass;
    }

    public boolean getServerSideTabSwitch() 
    { 
        if (_serverSideTabSwitch != null) return _serverSideTabSwitch.booleanValue(); 
        ValueBinding vb = getValueBinding("serverSideTabSwitch"); 
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null; 
        return v != null ? v.booleanValue() : DEFAULT_SERVER_SIDE_TAB_SWITCH; 
    } 

    public void setServerSideTabSwitch( boolean serverSideTabSwitch )
    {
        _serverSideTabSwitch = new Boolean( serverSideTabSwitch );
    }

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

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[33];
        values[0] = super.saveState(context);
        values[1] = _selectedIndex;
        values[2] = _bgcolor;
        values[3] = saveAttachedState(context, _tabChangeListener);
        values[4] = _activeTabStyleClass;
        values[5] = _inactiveTabStyleClass;
        values[6] = _activeSubStyleClass;
        values[7] = _inactiveSubStyleClass;
        values[8] = _tabContentStyleClass;
        values[9] = _disabledTabStyleClass;
        values[10] = _serverSideTabSwitch;
        values[11] = _activePanelTabVar;
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
        values[31] = _title;
        values[32] = _align;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _selectedIndex = (Integer)values[1];
        _bgcolor = (String)values[2];
        _tabChangeListener = (MethodBinding)restoreAttachedState(context, values[3]);
        _activeTabStyleClass = (String)values[4];
        _inactiveTabStyleClass = (String)values[5];
        _activeSubStyleClass = (String)values[6];
        _inactiveSubStyleClass = (String)values[7];
        _tabContentStyleClass = (String)values[8];
        _disabledTabStyleClass = (String)values[9];
        _serverSideTabSwitch = (Boolean) values[10];
        _activePanelTabVar = (String) values[11];
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
        _title = (String)values[31];
        _align = (String)values[32];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
