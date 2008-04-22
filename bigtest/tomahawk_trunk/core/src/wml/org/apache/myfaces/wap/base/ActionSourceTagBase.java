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
package org.apache.myfaces.wap.base;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements attributes:
 * <ol>
 * <li>action
 * <li>actionListener
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class ActionSourceTagBase extends ComponentTagBase {
    private static Log log = LogFactory.getLog(ActionSourceTagBase.class);

    /* properties */
    private String action = null;
    private String actionListener = null;
    private String immediate = null;

    /** Creates a new instance of CommandTag */
    public ActionSourceTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.action = null;
        this.actionListener = null;
        this.immediate = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        Class[] mbParams = {ActionEvent.class};

        if (action != null) {
            if (!(component instanceof ActionSource)) {
                throw new IllegalArgumentException("Component " + component.getId() + " is no ActionSource, cannot set 'action' attribute.");
            }
            MethodBinding mb;
            if (isValueReference(action))
                mb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(action, null);
            else
                mb = new ConstantMethodBinding(action);

            ((ActionSource)component).setAction(mb);
        }

        if (actionListener != null) {
            if (!(component instanceof ActionSource)) {
                throw new IllegalArgumentException("Component " + component.getId() + " is no ActionSource, cannot set 'actionListener' attribute.");
            }
            if (isValueReference(actionListener)) {
                MethodBinding mb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(actionListener, mbParams);
                ((ActionSource)component).setActionListener(mb);
            }
            else {
                log.error("Invalid expression " + actionListener);
            }
        }

        if (immediate != null) {
            if (component instanceof ActionSource) {
                if (isValueReference(immediate)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(immediate);
                    component.setValueBinding("immediate", vb);
                }
                else {
                    Boolean imm = Boolean.valueOf(immediate);
                    ((ActionSource)component).setImmediate(imm.booleanValue());
                }

            }
            else log.error("Component " + component.getClass().getName() + " is no ActionSource, cannot set 'immediate' attribute.");
        }
    }

    // ----------------------------------------------------- Getters and Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionListener() {
        return actionListener;
    }

    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * Getter for property immediate.
     * @return value of property immediate.
     */
    public String getImmediate() {
        return immediate;
    }

    /**
     * Setter for property immediate.
     * @param converter new value of property immediate.
     */
    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }
}
