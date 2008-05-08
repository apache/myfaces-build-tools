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

import org.apache.myfaces.component.html.ext.HtmlCommandLink;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.el.ValueBinding;
import java.util.Iterator;
import java.util.List;

/**
 * Command, that represents a navigation item.
 *
 * @JSFComponent
 *   name = "t:commandNavigation"
 *   tagClass = "org.apache.myfaces.custom.navigation.HtmlCommandNavigationTag"
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCommandNavigation
        extends HtmlCommandLink
{
    private static final Log log = LogFactory.getLog(HtmlCommandNavigation.class);

    private Boolean _open = null;
    private Boolean _active = null;

    public boolean isImmediate()
    {
        //always immediate
        return true;
    }

    public void setImmediate(boolean immediate)
    {
        if (log.isWarnEnabled()) log.warn("Immediate property of HtmlCommandNavigation cannot be set --> ignored.");
    }

    public Boolean getOpenDirectly()
    {
        return _open;
    }

    public boolean isOpen()
    {
        if (_open != null) return _open.booleanValue();
        ValueBinding vb = getValueBinding("open");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public void setOpen(boolean open)
    {
        _open = Boolean.valueOf(open);
    }

    public boolean isActive()
    {
        if (_active != null) return _active.booleanValue();
        ValueBinding vb = getValueBinding("active");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public Boolean getActiveDirectly()
    {
        return _active;
    }

    public void setActive(boolean active)
    {
        _active = Boolean.valueOf(active);
    }

    /**
     * @return false, if this item is child of another HtmlCommandNavigation, which is closed
     */
    public boolean isRendered()
    {
        if (! super.isRendered()) {
            return false;
        }
        UIComponent parent = getParent();
        while (parent != null)
        {
            if (parent instanceof HtmlCommandNavigation)
            {
                if (!((HtmlCommandNavigation)parent).isOpen())
                {
                    return false;
                }
            }

            if (parent instanceof HtmlPanelNavigation)
            {
                break;
            }
            else
            {
                parent = parent.getParent();
            }
        }

        return true;
    }


    public void toggleOpen()
    {
        if (isOpen())
        {
            if (getChildCount() > 0)
            {
                //item is a menu group --> close item
                setOpen(false);
            }
        }
        else
        {
            UIComponent parent = getParent();

            //close all siblings
            closeAllChildren(parent.getChildren().iterator());

            //open all parents (to be sure) and search HtmlPanelNavigation
            UIComponent p = parent;
            while (p != null && !(p instanceof HtmlPanelNavigation))
            {
                if (p instanceof HtmlCommandNavigation)
                {
                    ((HtmlCommandNavigation)p).setOpen(true);
                }
                p = p.getParent();
            }
            // p is now the HtmlPanelNavigation

            if (!hasCommandNavigationChildren())
            {
                //item is an end node --> deactivate all other nodes, and then...
                if (!(p instanceof HtmlPanelNavigation))
                {
                    log.error("HtmlCommandNavigation without parent HtmlPanelNavigation ?!");
                }
                else
                {
                    //deactivate all other items
                    deactivateAllChildren(p.getChildren().iterator());
                }
                //...activate this item
                setActive(true);
            }
            else
            {
                //open item
                setOpen(true);
            }
        }
    }

    private boolean hasCommandNavigationChildren()
    {
        if (getChildCount() == 0)
        {
            return false;
        }
        List list = getChildren();
        for (int i = 0, sizei = list.size(); i < sizei; i++)
        {
            if (list.get(i) instanceof HtmlCommandNavigation)
            {
                return true;
            }
        }
        return false;
    }


    private static void deactivateAllChildren(Iterator children)
    {
        while (children.hasNext())
        {
            UIComponent ni = (UIComponent)children.next();
            if (ni instanceof HtmlCommandNavigation)
            {
                ((HtmlCommandNavigation)ni).setActive(false);
                if (ni.getChildCount() > 0)
                {
                    deactivateAllChildren(ni.getChildren().iterator());
                }
            }
        }
    }

    private static void closeAllChildren(Iterator children)
    {
        while (children.hasNext())
        {
            UIComponent ni = (UIComponent)children.next();
            if (ni instanceof HtmlCommandNavigation)
            {
                ((HtmlCommandNavigation)ni).setOpen(false);
                if (ni.getChildCount() > 0)
                {
                    closeAllChildren(ni.getChildren().iterator());
                }
            }
        }
    }


    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        if (event instanceof ActionEvent)
        {
            ActionEvent actionEvent = (ActionEvent)event;
            if (actionEvent.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES)
            {
                HtmlCommandNavigation navItem = (HtmlCommandNavigation)actionEvent.getComponent();
                navItem.toggleOpen();
                FacesContext.getCurrentInstance().renderResponse();
            }
        }
        super.broadcast(event);
    }


    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = _open;
        values[2] = _active;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _open = (Boolean)values[1];
        _active = (Boolean)values[2];
    }


    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandNavigation";
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Link";


    public HtmlCommandNavigation()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }


    //------------------ GENERATED CODE END ---------------------------------------
    
}
