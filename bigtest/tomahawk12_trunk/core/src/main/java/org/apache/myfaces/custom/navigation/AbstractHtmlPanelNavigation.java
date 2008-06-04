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

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.PanelProperties;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;

/**
 * 
 * Renders a vertical menu structure with support for nested menu 
 * items. Unless otherwise specified, all attributes accept 
 * static values or EL expressions.
 * 
 * Panel, that includes navigation items ({@link HtmlCommandNavigation}) and other
 * components (separators).
 * 
 * @JSFComponent
 *   name = "t:panelNavigation"
 *   class = "org.apache.myfaces.custom.navigation.HtmlPanelNavigation"
 *   superClass = "org.apache.myfaces.custom.navigation.AbstractHtmlPanelNavigation"
 *   tagClass = "org.apache.myfaces.custom.navigation.HtmlPanelNavigationTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlPanelNavigation
        extends HtmlPanelGroup implements AlignProperty,
        UniversalProperties, EventAware, DataProperties, PanelProperties
{
    private static final Log log = LogFactory.getLog(AbstractHtmlPanelNavigation.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelNavigation";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Navigation";

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    private static final String PREVIOUS_VIEW_ROOT = AbstractHtmlPanelNavigation.class.getName() + ".PREVIOUS_VIEW_ROOT";
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
            
    /**
     * The CSS class of closed navigation items.
     * 
     * @JSFProperty
     */
    public abstract String getItemClass();

    /**
     * The CSS class of open navigation items.
     * 
     * @JSFProperty
     */
    public abstract String getOpenItemClass();

    /**
     * The CSS class of the active navigation item.
     * 
     * @JSFProperty
     */
    public abstract String getActiveItemClass();

    /**
     * The CSS class for the td element of a separator.
     * 
     * @JSFProperty
     */
    public abstract String getSeparatorClass();

    /**
     * The CSS Style of closed navigation items.
     * 
     * @JSFProperty
     */
    public abstract String getItemStyle();

    /**
     * The CSS Style of open navigation items.
     * 
     * @JSFProperty
     */
    public abstract String getOpenItemStyle();

    /**
     * The CSS Style of the active navigation item.
     * 
     * @JSFProperty
     */
    public abstract String getActiveItemStyle();

    /**
     * The CSS Style for the td element of a separator.
     * 
     * @JSFProperty
     */
    public abstract String getSeparatorStyle();

}
