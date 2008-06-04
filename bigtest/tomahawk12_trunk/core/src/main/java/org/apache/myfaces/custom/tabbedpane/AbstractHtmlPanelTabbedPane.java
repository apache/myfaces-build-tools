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

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UINamingContainer;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.PanelProperties;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleAware;

/**
 * TODO: Document this component.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:panelTabbedPane"
 *   class = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTabbedPane"
 *   superClass = "org.apache.myfaces.custom.tabbedpane.AbstractHtmlPanelTabbedPane"
 *   tagClass = "org.apache.myfaces.custom.tabbedpane.HtmlPanelTabbedPaneTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlPanelTabbedPane
        extends HtmlPanelGroup
        implements UniversalProperties, EventAware, PanelProperties,
        AlignProperty, DataProperties, UserRoleAware
        
{
    //private static final Log log = LogFactory.getLog(HtmlPanelTabbedPane.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelTabbedPane";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TabbedPane";
    private static final int DEFAULT_SELECTEDINDEX = 0;
    private static final boolean DEFAULT_SERVER_SIDE_TAB_SWITCH = false;

    private MethodBinding _tabChangeListener = null;
    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

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
        return !isServerSideTabSwitch(); 
    }

    /**
     * @JSFProperty
     *   tagExcluded = "true"
     */
    public abstract String getActiveTabVar();
    
    /**
     * Boolean Variable that is set in request scope when 
     * rendering a panelTab. True means that the currently 
     * rendered panelTab is active.
     * 
     * @JSFProperty
     */
    public abstract Boolean getActivePanelTabVar();

    /**
     * Index of tab that is selected by default.
     * 
     * @JSFProperty
     *   defaultValue = "0"
     */
    public abstract int getSelectedIndex();
    
    public abstract void setSelectedIndex(int selectedIndex);

    /**
     * Style class of the active tab cell.
     * 
     * @JSFProperty
     */
    public abstract String getActiveTabStyleClass();

    /**
     * Style class of the inactive tab cells.
     * 
     * @JSFProperty
     */
    public abstract String getInactiveTabStyleClass();

    /**
     * Style class of the active tab sub cell.
     * 
     * @JSFProperty
     */
    public abstract String getActiveSubStyleClass();

    /**
     * Style class of the inactive tab sub cells.
     * 
     * @JSFProperty
     */
    public abstract String getInactiveSubStyleClass();

    /**
     * Style class of the active tab content cell.
     * 
     * @JSFProperty
     */
    public abstract String getTabContentStyleClass();

    /**
     * Style class of the disabled tab cells.
     * 
     * @JSFProperty
     */
    public abstract String getDisabledTabStyleClass();

    /**
     * Toggle client-side/server-side tab switches.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isServerSideTabSwitch();

}
