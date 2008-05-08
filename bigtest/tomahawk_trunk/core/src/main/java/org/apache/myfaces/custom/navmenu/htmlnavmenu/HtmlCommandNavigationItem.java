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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.el.ValueBinding;
import java.util.*;

/**
 * Many thanks to the guys from Swiss Federal Institute of Intellectual Property & Marc Bouquet
 * for helping to develop this component.
 *
 * @JSFComponent
 *   name = "t:commandNavigation2"
 *   tagClass = "org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlCommandNavigationItemTag"
 *    
 * @author Manfred Geiler
 * @author Thomas Spiegl
 */
public class HtmlCommandNavigationItem extends HtmlCommandLink {
    private static final Log log = LogFactory.getLog(HtmlCommandNavigationItem.class);

    private Boolean _open = null;
    private Boolean _active = null;
    private String _activeOnViewIds = null;
    private String _externalLink = null;

    public boolean isImmediate() {
        //always immediate
        return true;
    }

    public void setImmediate(Boolean immediate) {
        if (log.isWarnEnabled()) log.warn("Immediate property of HtmlCommandNavigation cannot be set --> ignored.");
    }

    public boolean isOpen() {
        if (_open != null) return _open.booleanValue();
        ValueBinding vb = getValueBinding("open");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public Boolean getOpenDirectly() {
        return _open;
    }

    public void setOpen(boolean open) {
        _open = open ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean isActive() {
        if (_active != null) return _active.booleanValue();
        ValueBinding vb = getValueBinding("active");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null && v.booleanValue();
    }

    public Boolean getActiveDirectly() {
        return _active;
    }

    public void setActive(boolean active) {
        _active = active ? Boolean.TRUE : Boolean.FALSE;
    }

    public String getActiveOnViewIds() {
        if (_activeOnViewIds != null) return _activeOnViewIds;
        ValueBinding vb = getValueBinding("activeOnViewIds");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setActiveOnViewIds(String activeOnViewIds) {
        this._activeOnViewIds = activeOnViewIds;
    }

    public String getExternalLink() {
        if (_externalLink != null) return _externalLink;
        ValueBinding vb = getValueBinding("externalLink");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setExternalLink(String externalLink) {
        this._externalLink = externalLink;
    }

    /**
     * @return false, if this item is child of another HtmlCommandNavigation, which is closed
     */
    public boolean isRendered() {
        if (! super.isRendered()) {
            return false;
        }

        HtmlPanelNavigationMenu parentPanelNavMenu = getParentPanelNavigation();
        if (parentPanelNavMenu != null && parentPanelNavMenu.isRenderAll())
            return true;

        UIComponent parent = getParent();
        while (parent != null) {
            if (parent instanceof HtmlCommandNavigationItem) {
                if (!((HtmlCommandNavigationItem) parent).isOpen()) {
                    return false;
                }
            }

            if (parent instanceof HtmlPanelNavigationMenu) {
                break;
            }
            else {
                parent = parent.getParent();
            }
        }

        return true;
    }

    private HtmlPanelNavigationMenu getParentPanelNavigation() {
        UIComponent parent = getParent();

        // search HtmlPanelNavigation
        UIComponent p = parent;
        while (p != null && !(p instanceof HtmlPanelNavigationMenu)) {
            p = p.getParent();
        }
        // p is now the HtmlPanelNavigation
        if (!(p instanceof HtmlPanelNavigationMenu)) {
            log.error("HtmlCommandNavigation without parent HtmlPanelNavigation ?!");
            return null;
        }

        return (HtmlPanelNavigationMenu) p;
    }

    public void toggleOpen() {
        HtmlPanelNavigationMenu menu = getParentPanelNavigation();
        if (isOpen() && menu != null && !menu.isExpandAll()) {
            if (getChildCount() > 0) {
                //item is a menu group --> close item
                setOpen(false);
            }
        }
        else {
            UIComponent parent = getParent();

            //close all siblings
            closeAllChildren(parent.getChildren().iterator(), this, true);

            //open all parents (to be sure) and search HtmlPanelNavigation
            UIComponent p = parent;
            HtmlCommandNavigationItem rootItem = null;
            while (p != null && !(p instanceof HtmlPanelNavigationMenu)) {
                if (p instanceof HtmlCommandNavigationItem) {
                    rootItem = (HtmlCommandNavigationItem) p;
                    rootItem.setOpen(true);
                }
                p = p.getParent();
            }
            if (rootItem != null) {
                List children = menu.getChildren();
                for (int i = 0, sizei = children.size(); i < sizei; i++) {
                    Object obj = children.get(i);
                    if (obj != rootItem && obj instanceof HtmlCommandNavigationItem) {
                        ((HtmlCommandNavigationItem)obj).setOpen(false);
                    }
                }
            }

            // p is now the HtmlPanelNavigation
            if (!(p instanceof HtmlPanelNavigationMenu)) {
                log.error("HtmlCommandNavigation without parent HtmlPanelNavigation ?!");
            }
            else {
                if (!hasCommandNavigationChildren() || ((HtmlPanelNavigationMenu) p).isExpandAll()) {
                    //item is an end node or Menu always expanded --> deactivate all other nodes, and then...

                    //deactivate all other items
                    deactivateAllChildren(p.getChildren().iterator());
                    //...activate this item
                    setActive(true);
                }
                else {
                    //open item
                    setOpen(true);
                }
            }
        }
    }

    private boolean hasCommandNavigationChildren() {
        if (getChildCount() == 0) {
            return false;
        }
        List list = getChildren();
        for (int i = 0, sizei = list.size(); i < sizei; i++) {
            if (list.get(i) instanceof HtmlCommandNavigationItem) {
                return true;
            }
        }
        return false;
    }


    private static void deactivateAllChildren(Iterator children) {
        while (children.hasNext()) {
            UIComponent ni = (UIComponent) children.next();
            if (ni instanceof HtmlCommandNavigationItem) {
                ((HtmlCommandNavigationItem) ni).setActive(false);
                if (ni.getChildCount() > 0) {
                    deactivateAllChildren(ni.getChildren().iterator());
                }
            }
        }
    }

    private static void closeAllChildren(Iterator children, HtmlCommandNavigationItem current, boolean resetActive) {
        while (children.hasNext()) {
            UIComponent ni = (UIComponent) children.next();
            if (ni instanceof HtmlCommandNavigationItem) {
                ((HtmlCommandNavigationItem) ni).setOpen(false);
                if (resetActive)
                    ((HtmlCommandNavigationItem) ni).setActive(false);
                if (ni.getChildCount() > 0) {
                    closeAllChildren(ni.getChildren().iterator(), current, current != ni);
                }
            }
        }
    }

    public String[] getActiveOnVieIds() {
        String value = getActiveOnViewIds();
        if (value == null)
            return new String[]{};
        return value.split(",");
    }

    public void deactivateAll() {
        UIComponent parent = this.getParent();
        while (!(parent instanceof HtmlPanelNavigationMenu) && parent != null) {
            parent = parent.getParent();
        }
        if (parent == null) {
            throw new IllegalStateException("no PanelNavigationMenu!");
        }

        HtmlPanelNavigationMenu root = (HtmlPanelNavigationMenu) parent;
        for (Iterator it = root.getChildren().iterator(); it.hasNext();) {
            Object o = it.next();
            if (o instanceof HtmlCommandNavigationItem) {
                HtmlCommandNavigationItem navItem = (HtmlCommandNavigationItem) o;
                navItem.setActive(false);
                if (navItem.getChildCount() > 0) {
                    navItem.deactivateChildren();
                }
            }
        }
    }

    public void deactivateChildren() {
        for (Iterator it = this.getChildren().iterator(); it.hasNext();) {
            Object o = it.next();
            if (o instanceof HtmlCommandNavigationItem) {
                HtmlCommandNavigationItem current = (HtmlCommandNavigationItem) o;
                current.setActive(false);
                if (current.getChildCount() > 0) {
                    current.deactivateChildren();
                }
            }
        }
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        if (event instanceof ActionEvent) {
            ActionEvent actionEvent = (ActionEvent) event;
            if (actionEvent.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES) {
                HtmlCommandNavigationItem navItem = (HtmlCommandNavigationItem) actionEvent.getComponent();
                navItem.toggleOpen();
                FacesContext.getCurrentInstance().renderResponse();
            }
        }
        super.broadcast(event);
    }


    public Object saveState(FacesContext context) {
        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = _open;
        values[2] = _active;
        values[3] = _activeOnViewIds;
        values[4] = _externalLink;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _open = ((Boolean) values[1]);
        _active = ((Boolean) values[2]);
        _activeOnViewIds = ((String) values[3]);
        _externalLink = ((String) values[4]);
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandNavigationItem";
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Link";


    public HtmlCommandNavigationItem() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    //------------------ GENERATED CODE END ---------------------------------------
}
