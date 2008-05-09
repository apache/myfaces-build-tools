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
package org.apache.myfaces.custom.navmenu.htmlnavmenu;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.PanelProperties;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Many thanks to the guys from Swiss Federal Institute of Intellectual Property & Marc Bouquet
 * for helping to develop this component.
 * 
 * @JSFComponent
 *   name = "t:panelNavigation2"
 *   class = "org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlPanelNavigationMenu"
 *   superClass = "org.apache.myfaces.custom.navmenu.htmlnavmenu.AbstractHtmlPanelNavigationMenu"
 *   tagClass = "org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlPanelNavigationMenuTag"
 * 
 * @author Manfred Geiler
 * @author Thomas Spiegl
 */
public abstract class AbstractHtmlPanelNavigationMenu extends HtmlPanelGroup 
    implements NamingContainer, UniversalProperties, EventAware, 
    PanelProperties, AlignProperty, DataProperties
{
    private static final Log log = LogFactory.getLog(AbstractHtmlPanelNavigationMenu.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelNavigationMenu";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.NavigationMenu";
    
    static final String PREVIOUS_VIEW_ROOT = AbstractHtmlPanelNavigationMenu.class.getName() + ".PREVIOUS_VIEW_ROOT";
    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

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
                if(previousRoot.findComponent(getClientId(context)) instanceof AbstractHtmlPanelNavigationMenu)
                {
                    restoreOpenActiveStates(context, previousRoot, getChildren());
                }
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
            UIComponent prevItem = (UIComponent)previousRoot.findComponent(child.getClientId(facesContext));
            
            if (prevItem instanceof HtmlCommandNavigationItem &&
            		child instanceof HtmlCommandNavigationItem)
            {
                HtmlCommandNavigationItem previousItem = (HtmlCommandNavigationItem)prevItem;
                if (previousItem != null)
                {
                    ((HtmlCommandNavigationItem)child).setOpen(previousItem.isOpen());
                    ((HtmlCommandNavigationItem)child).setActive(previousItem.isActive());
                }
                else
                {
                    log.debug("Navigation item " + child.getClientId(facesContext) + " not found in previous view.");
                }
                if (child.getChildCount() > 0)
                {
                    restoreOpenActiveStates(facesContext, previousRoot, child.getChildren());
                }
            }
        }
    }
    
    /**
     * @JSFProperty
     */
    public abstract String getItemClass();

    /**
     * @JSFProperty
     */
    public abstract String getOpenItemClass();

    /**
     * @JSFProperty
     */
    public abstract String getActiveItemClass();

    /**
     * @JSFProperty
     */
    public abstract String getSeparatorClass();

    /**
     * @JSFProperty
     */
    public abstract String getItemStyle();

    /**
     * @JSFProperty
     */
    public abstract String getOpenItemStyle();

    /**
     * @JSFProperty
     */
    public abstract String getActiveItemStyle();

    /**
     * @JSFProperty
     */
    public abstract String getSeparatorStyle();
    
    /**
     * @JSFProperty
     */
    public abstract String getLayout();

    /**
     * @JSFProperty
     *   tagExcluded="true"
     *   literalOnly = "true"
     *   defaultValue = "false"
     */
    public abstract Boolean getPreprocessed();

    /**
     * @JSFProperty
     *   defaultValue="false"
     */
    public abstract boolean isExpandAll();
    
    /**
     * @JSFProperty
     *   defaultValue="false"
     */
    public abstract boolean isRenderAll();

    /**
     * @JSFProperty
     *   defaultValue="false"
     */
    public abstract boolean isDisabled();

    /**
     * @JSFProperty
     */
    public abstract String getDisabledStyle();

    /**
     * @JSFProperty
     */
    public abstract String getDisabledStyleClass();

}
