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

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements attributes:
 * <ol>
 * <li>converter
 * <li>value
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class ValueHolderTagBase extends ComponentTagBase {
    private static Log log = LogFactory.getLog(ValueHolderTagBase.class);

    /* properties */
    private String converter = null;
    private String value = null;

    /** Creates a new instance of ValueTag */
    public ValueHolderTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.converter = null;
        this.value = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if (converter != null) {
            if (component instanceof ValueHolder) {
                if (isValueReference(converter)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(converter);
                    component.setValueBinding("converter", vb);
                }
                else {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    Converter conv = facesContext.getApplication().createConverter(converter);
                    ((ValueHolder)component).setConverter(conv);
                }
            }
            else {
                log.error("Component " + component.getClass().getName() + " is no ValueHolder, cannot set 'converter' attribute.");
            }
        }


        if (value != null) {
            if (component instanceof ValueHolder) {
                if (isValueReference(value)) {
                    ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(value);
                    component.setValueBinding("value", vb);
                } else
                    ((ValueHolder)component).setValue(value);

            }
            else log.error("Component " + component.getClass().getName() + " is no ValueHolder, cannot set 'value' attribute.");
        }

    }
    // ----------------------------------------------------- Getters and Setters

    /**
     * Getter for property converter.
     * @return value of property converter.
     */
    public String getConverter() {
        return converter;
    }

    /**
     * Setter for property converter.
     * @param converter new value of property converter.
     */
    public void setConverter(String converter) {
        this.converter = converter;
    }

    /**
     * Getter for property value.
     * @return value of property value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter for property value.
     * @param value new value of property value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
