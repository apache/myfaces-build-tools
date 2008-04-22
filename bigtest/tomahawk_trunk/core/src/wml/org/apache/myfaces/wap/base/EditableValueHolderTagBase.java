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

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements attributes:
 * <ol>
 * <li>immediate
 * <li>required
 * <li>validator
 * <li>valueChangeListener
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class EditableValueHolderTagBase extends ValueHolderTagBase {
    private static Log log = LogFactory.getLog(EditableValueHolderTagBase.class);

    /* properties */
    private String immediate = null;
    private String required = null;
    private String validator = null;
    private String valueChangeListener = null;

    /** Creates a new instance of UIComponentTagBase */
    public EditableValueHolderTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.immediate = null;
        this.required = null;
        this.validator = null;
        this.valueChangeListener = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        /* method binding parameters */
        Class[] mbParams = {FacesContext.class, UIComponent.class, Object.class};

        if (immediate != null) {
            if (component instanceof EditableValueHolder) {
                if (isValueReference(immediate)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(immediate);
                    component.setValueBinding("immediate", vb);
                }
                else {
                    boolean imm = stringToBoolean(immediate);
                    ((EditableValueHolder)component).setImmediate(imm);
                }

            }
            else log.error("Component " + component.getClass().getName() + " is no ValueHolder, cannot set 'immediate' attribute.");
        }

        if (required != null) {
            if (component instanceof EditableValueHolder) {
                if (isValueReference(required)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(required);
                    component.setValueBinding("required", vb);
                } else {
                    boolean req = stringToBoolean(required);
                    ((EditableValueHolder)component).setRequired(req);
                }
            }
            else {
                log.error("Component " + component.getClass().getName() + " is no EditableValueHolder, cannot set 'required'.");
            }
        }

        if (validator != null) {
            if (component instanceof EditableValueHolder) {
                if (isValueReference(validator)) {
                    Class[] params = {FacesContext.class, UIComponent.class, Object.class};

                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    MethodBinding mb = facesContext.getApplication().createMethodBinding(validator, mbParams);
                    ((EditableValueHolder)component).setValidator(mb);
                }
                else {
                    log.error("Invalid expression " + validator);
                }
            }
            else {
                log.error("Component " + component.getClass().getName() + " is no EditableValueHolder, cannot set 'validator'.");
            }
        }

        if (valueChangeListener != null) {
            if (component instanceof EditableValueHolder) {
                if (isValueReference(valueChangeListener)) {

                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    MethodBinding mb = facesContext.getApplication().createMethodBinding(valueChangeListener, mbParams);
                    ((EditableValueHolder)component).setValueChangeListener(mb);
                }
                else {
                    log.error("Invalid expression " + valueChangeListener);
                }
            }
            else {
                log.error("Component " + component.getClass().getName() + " is no EditableValueHolder, cannot set 'valueChangedListener'.");
            }
        }
    }

    private boolean stringToBoolean(String str){
        Boolean bool = Boolean.valueOf(str);
        return(bool.booleanValue());
    }

    // ----------------------------------------------------- Getters and Setters
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

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required=required;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator=validator;
    }

    public String getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(String valueChangeListener) {
        this.valueChangeListener=valueChangeListener;
    }


}
