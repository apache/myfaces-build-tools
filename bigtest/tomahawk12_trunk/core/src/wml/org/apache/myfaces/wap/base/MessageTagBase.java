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
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Implements attributes:
 * <ol>
 * <li>for
 * <li>showDetail
 * <li>showSummary
 * </ol>
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public abstract class MessageTagBase extends ComponentTagBase {

    /* properties */
    private String forComponent = null;
    private String showDetail = null;
    private String showSummary = null;

    /** Creates a new instance of UIComponentTagBase */
    public MessageTagBase() {
        super();
    }

    public abstract String getRendererType();

    public void release() {
        super.release();
        this.forComponent = null;
        this.showDetail = null;
        this.showSummary= null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if (getRendererType() != null) {
            component.setRendererType(getRendererType());
        }

        UIMessage comp = (UIMessage)component;

        if (forComponent != null) {
            if (isValueReference(forComponent)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(forComponent);
                component.setValueBinding("for", vb);
            } else {
                comp.setFor(forComponent);
            }
        }

        if (showDetail != null) {
            if (isValueReference(showDetail)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(showDetail);
                component.setValueBinding("showDetail", vb);
            } else {
                boolean bool = Boolean.valueOf(showDetail).booleanValue();
                comp.setShowDetail(bool);
            }
        }

        if (showSummary != null) {
            if (isValueReference(showSummary)) {
                ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(showSummary);
                component.setValueBinding("showSummary", vb);
            } else {
                boolean bool = Boolean.valueOf(showSummary).booleanValue();
                comp.setShowSummary(bool);
            }
        }
    }

    // ----------------------------------------------------- Getters and Setters
     public String getFor() {
        return forComponent;
    }

    public void setFor(String forComponent) {
        this.forComponent = forComponent;
    }

    public String getShowDetail() {
        return showDetail;
    }

    public void setShowDetail(String showDetail) {
        this.showDetail = showDetail;
    }

    public String getShowSummary() {
        return showSummary;
    }

    public void setShowSummary(String showSummary) {
        this.showSummary = showSummary;
    }

}
