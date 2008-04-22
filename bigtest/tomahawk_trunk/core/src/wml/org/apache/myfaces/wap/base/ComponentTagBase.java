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
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * Implements attributes:
 * <ol>
 * <li>id
 * <li>renderer
 * <li>binding
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public abstract class ComponentTagBase extends UIComponentTag {

    /* properties */
    private String id = null;
    private String rendered = null;
    private String binding = null;

    /** Creates a new instance of UIComponentTagBase */
    public ComponentTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.id = null;
        this.rendered = null;
        this.binding = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if (getRendererType() != null) {
            component.setRendererType(getRendererType());
        }

        if (id != null) {
            if (isValueReference(id)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(id);
                component.setValueBinding("id", vb);
            } else {
                component.setId(id);
            }
        }

        if (rendered != null) {
            if (isValueReference(rendered)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(rendered);
                component.setValueBinding("rendered", vb);
            } else {
                boolean bool = Boolean.valueOf(rendered).booleanValue();
                component.setRendered(bool);
            }
        }

        if (binding != null) {
            if (isValueReference(binding)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(binding);
                component.setValueBinding("binding", vb);
            } else {
                throw new IllegalArgumentException("Not a valid binding: " + binding);
            }
        }
    }
    // ----------------------------------------------------- Getters and Setters

    /**
     * Getter for property id.
     * @return value of property id.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for property id.
     * @param id new value of property id.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Getter for property rendered.
     * @return value of property rendered.
     */
    public String getRendered() {
        return rendered;
    }

    /**
     * Setter for property rendered.
     * @param rendered new value of property rendered.
     */
    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    /**
     * Setter for property binding.
     * @param binding new value of property binding.
     */
    public void setBinding(String binding) {
        if (!isValueReference(binding)) {
            throw new IllegalArgumentException("Not a valid binding: " + binding);
        }
        this.binding = binding;
    }
}
