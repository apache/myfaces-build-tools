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
package org.apache.myfaces.custom.navmenu;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlCommandNavigationItem;
import org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlPanelNavigationMenu;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UINavigationMenuItem extends UISelectItem implements
    UserRoleAware, ActionSource {
    private static final boolean DEFAULT_IMMEDIATE = true;

    public static final String COMPONENT_TYPE = "org.apache.myfaces.NavigationMenuItem";
    public static final String COMPONENT_FAMILY = "javax.faces.SelectItem";

    private String _icon = null;
    private Boolean _split = null;
    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    private Boolean _open = null;
    private Boolean _active = null;
    private MethodBinding _action = null;
    private MethodBinding _actionListener = null;
    private Boolean _immediate = null;
    private String _target = null;
    private Boolean _disabled = null;
    private String _disabledStyle = null;
    private String _disabledStyleClass = null;
    private String _activeOnViewIds = null;
    private String _externalLink = null;

    public UINavigationMenuItem() {
        super();
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void setIcon(String icon) {
        _icon = icon;
    }

    public String getIcon() {
        if (_icon != null)
            return _icon;
        ValueBinding vb = getValueBinding("icon");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(),
                                                           vb) : null;
    }

    public void setSplit(boolean split) {
        _split = Boolean.valueOf(split);
    }

    public boolean isSplit() {
        if (_split != null)
            return _split.booleanValue();
        ValueBinding vb = getValueBinding("split");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
            : null;
        return v != null && v.booleanValue();
    }

    public void setOpen(boolean open) {
        _open = Boolean.valueOf(open);
    }

    public boolean isOpen() {
        if (_open != null)
            return _open.booleanValue();
        ValueBinding vb = getValueBinding("open");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
            : null;
        return v != null && v.booleanValue();
    }

    public void setActive(boolean active) {
        _active = Boolean.valueOf(active);
    }

    public boolean isActive() {
        if (_active != null)
            return _active.booleanValue();
        ValueBinding vb = getValueBinding("active");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
            : null;
        return v != null && v.booleanValue();
    }

    public void setImmediate(boolean immediate) {
        _immediate = Boolean.valueOf(immediate);
    }

    public boolean isImmediate() {
        if (_immediate != null)
            return _immediate.booleanValue();
        ValueBinding vb = getValueBinding("immediate");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
            : null;
        return v != null ? v.booleanValue() : DEFAULT_IMMEDIATE;
    }

    public String getExternalLink() {
        if (_externalLink != null)
            return _externalLink;
        ValueBinding vb = getValueBinding("externalLink");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(),
                                                           vb) : null;
    }

    public void setExternalLink(String externalLink) {
        _externalLink = externalLink;
    }

    // Action Source

    public void setAction(MethodBinding action) {
        _action = action;
    }

    public MethodBinding getAction() {
        return _action;
    }

    public void setActionListener(MethodBinding actionListener) {
        _actionListener = actionListener;
    }

    public MethodBinding getActionListener() {
        return _actionListener;
    }

    public void addActionListener(ActionListener listener) {
        addFacesListener(listener);
    }

    public ActionListener[] getActionListeners() {
        return (ActionListener[]) getFacesListeners(ActionListener.class);
    }

    public void removeActionListener(ActionListener listener) {
        removeFacesListener(listener);
    }

    // Action Source

    public void setEnabledOnUserRole(String enabledOnUserRole) {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public String getEnabledOnUserRole() {
        if (_enabledOnUserRole != null)
            return _enabledOnUserRole;
        ValueBinding vb = getValueBinding("enabledOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(),
                                                           vb) : null;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole) {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public String getVisibleOnUserRole() {
        if (_visibleOnUserRole != null)
            return _visibleOnUserRole;
        ValueBinding vb = getValueBinding("visibleOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(),
                                                           vb) : null;
    }

    public void setTarget(String target) {
        _target = target;
    }

    public String getTarget() {
        if (_target != null)
            return _target;
        ValueBinding vb = getValueBinding("target");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(),
                                                           vb) : null;
    }

    public void setDisabled(boolean disabled) {
        _disabled = Boolean.valueOf(disabled);
    }

    public boolean isDisabled() {
        if (_disabled != null)
            return _disabled.booleanValue();
        ValueBinding vb = getValueBinding("disabled");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
            : null;
        return v != null && v.booleanValue();
    }

    public String getDisabledStyle() {
        if (_disabledStyle != null)
            return _disabledStyle;
        ValueBinding vb = getValueBinding("disabledStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(),
                                                           vb) : null;
    }

    public void setDisabledStyle(String disabledStyle) {
        _disabledStyle = disabledStyle;
    }

    /**
     * @see javax.faces.component.UIComponent#broadcast(javax.faces.event.FacesEvent)
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        super.broadcast(event);

        if (event instanceof ActionEvent) {
            FacesContext context = getFacesContext();

            MethodBinding actionListenerBinding = getActionListener();
            if (actionListenerBinding != null) {
                try {
                    actionListenerBinding.invoke(context,
                                                 new Object[]{event});
                }
                catch (EvaluationException e) {
                    Throwable cause = e.getCause();
                    if (cause != null
                        && cause instanceof AbortProcessingException) {
                        throw (AbortProcessingException) cause;
                    }
                    else {
                        throw e;
                    }
                }
            }

            ActionListener defaultActionListener = context.getApplication()
                .getActionListener();
            if (defaultActionListener != null) {
                defaultActionListener.processAction((ActionEvent) event);
            }
        }
    }

    public String getDisabledStyleClass() {
        if (_disabledStyleClass != null)
            return _disabledStyleClass;
        ValueBinding vb = getValueBinding("disabledStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(),
                                                           vb) : null;
    }

    public void setDisabledStyleClass(String disabledStyleClass) {
        _disabledStyleClass = disabledStyleClass;
    }

    public String getActiveOnViewIds() {
        if (_activeOnViewIds != null) return _activeOnViewIds;
        ValueBinding vb = getValueBinding("activeOnViewIds");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public String getActiveOnViewIdsDirectly() {
        return _activeOnViewIds;
    }

    public void setActiveOnViewIds(String activeOnViewIds) {
        this._activeOnViewIds = activeOnViewIds;
    }

    public boolean isRendered() {
        if (!UserRoleUtils.isVisibleOnUserRole(this))
            return false;
        return super.isRendered();
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[16];
        values[0] = super.saveState(context);
        values[1] = _icon;
        values[2] = _split;
        values[3] = saveAttachedState(context, _action);
        values[4] = _enabledOnUserRole;
        values[5] = _visibleOnUserRole;
        values[6] = _open;
        values[7] = _active;
        values[8] = saveAttachedState(context, _actionListener);
        values[9] = _immediate;
        values[10] = _target;
        values[11] = _disabled;
        values[12] = _disabledStyle;
        values[13] = _disabledStyleClass;
        values[14] = _activeOnViewIds;
        values[15] = _externalLink;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _icon = (String) values[1];
        _split = (Boolean) values[2];
        _action = (MethodBinding) restoreAttachedState(context, values[3]);
        _enabledOnUserRole = (String) values[4];
        _visibleOnUserRole = (String) values[5];
        _open = (Boolean) values[6];
        _active = (Boolean) values[7];
        _actionListener = (MethodBinding) restoreAttachedState(context,
                                                               values[8]);
        _immediate = (Boolean) values[9];
        _target = (String) values[10];
        _disabled = (Boolean) values[11];
        _disabledStyle = (String) values[12];
        _disabledStyleClass = (String) values[13];
        _activeOnViewIds = (String) values[14];
        _externalLink = (String) values[15];
    }

    public void toggleActive(FacesContext context) {
        StringTokenizer tokenizer = new StringTokenizer(this.getActiveOnViewIdsDirectly(), ";");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.trim().equals(context.getViewRoot().getViewId())) {
                this.deactivateAll();
                this.setActive(true);
                openParents();
            }
            else {
                this.setActive(false);
            }
        }
    }

    private void openParents() {
        UIComponent comp = this;

        while ((comp = comp.getParent()) instanceof UINavigationMenuItem) {
            UINavigationMenuItem parent = (UINavigationMenuItem) comp;
            if (!parent.isOpen())
                parent.setOpen(true);
            else
                return;
        }
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
            if (o instanceof UINavigationMenuItem) {
                UINavigationMenuItem navItem = (UINavigationMenuItem) o;
                navItem.setActive(false);
                if (navItem.getChildCount() > 0) {
                    navItem.deactivateChildren();
                }
            }
            if (o instanceof HtmlCommandNavigationItem) {
                HtmlCommandNavigationItem current = (HtmlCommandNavigationItem) o;
                current.setActive(false);
                if (current.getChildCount() > 0) {
                    current.deactivateChildren();
                }
            }
        }
    }

    public void deactivateChildren() {
        for (Iterator it = this.getChildren().iterator(); it.hasNext();) {
            Object o = it.next();
            if (o instanceof UINavigationMenuItem) {
                UINavigationMenuItem current = (UINavigationMenuItem) o;
                current.setActive(false);
                if (current.getChildCount() > 0) {
                    current.deactivateChildren();
                }
            }
        }
    }

    public Boolean getActiveDirectly() {
        return _active;
    }
}
