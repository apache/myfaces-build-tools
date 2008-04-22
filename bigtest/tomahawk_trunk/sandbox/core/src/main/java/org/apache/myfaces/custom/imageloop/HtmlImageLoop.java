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
package org.apache.myfaces.custom.imageloop;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * HTML image loop component.
 * @author Felix Röthenbacher (latest modification by $Author:$)
 * @version $Revision:$ $Date:$
 */
public class HtmlImageLoop extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlImageLoop";
    public static final String RENDERER_TYPE = "org.apache.myfaces.HtmlImageLoop";
    
    // Value binding constants
    public static final String VB_DELAY = "delay";
    public static final String VB_MIN_DELAY = "minDelay";
    public static final String VB_MAX_DELAY = "maxDelay";
    public static final String VB_TRANSITION_TIME = "transitionTime";
    public static final String VB_WIDTH = "width";
    public static final String VB_HEIGHT = "height";
    
    private Integer _delay = null;
    private Integer _minDelay = null;
    private Integer _maxDelay = null;
    private Integer _transitionTime = null;
    private Integer _width = null;
    private Integer _height = null;
    
    public HtmlImageLoop() {
        setRendererType(RENDERER_TYPE);
    }
    
    public String getClientId(FacesContext context) {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }
        return clientId;
    }

    public Object saveState(FacesContext facesContext) {
        Object values[] = new Object[7];
        values[0] = super.saveState(facesContext);
        values[1] = _delay;
        values[2] = _minDelay;
        values[3] = _maxDelay;
        values[4] = _transitionTime;
        values[5] = _width;
        values[6] = _height;
        return values;
    }

    public void restoreState(FacesContext facesContext, Object state) {
        Object values[] = (Object[])state;
        super.restoreState(facesContext, values[0]);
        _delay = (Integer)values[1];
        _minDelay = (Integer)values[2];
        _maxDelay = (Integer)values[3];
        _transitionTime = (Integer)values[4];
        _width = (Integer)values[5];
        _height = (Integer)values[6];
    }
    
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
    
    public Integer getDelay() {
        if (_delay != null) return _delay;
        ValueBinding vb = getValueBinding(VB_DELAY);
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }
    
    public void setDelay(Integer delay) {
        _delay = delay;
    }
    
    public Integer getMinDelay() {
        if (_minDelay != null) return _minDelay;
        ValueBinding vb = getValueBinding(VB_MIN_DELAY);
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null; 
    }
    
    public void setMinDelay(Integer minDelay) {
        _minDelay = minDelay;
    }

    public Integer getMaxDelay() {
        if (_maxDelay != null) return _maxDelay;
        ValueBinding vb = getValueBinding(VB_MAX_DELAY);
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null; 
    }

    public void setMaxDelay(Integer maxDelay) {
        _maxDelay = maxDelay;
    }

    public Integer getTransitionTime() {
        if (_transitionTime != null) return _transitionTime;
        ValueBinding vb = getValueBinding(VB_TRANSITION_TIME);
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null; 
    }
    
    public void setTransitionTime(Integer transitionTime) {
        _transitionTime = transitionTime;
    }
    
    public Integer getWidth() {
        if (_width != null) return _width;
        ValueBinding vb = getValueBinding(VB_WIDTH);
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null; 
    }
    
    public void setWidth(Integer width) {
        _width = width;
    }

    public Integer getHeight() {
        if (_height != null) return _height;
        ValueBinding vb = getValueBinding(VB_HEIGHT);
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null; 
    }
    
    public void setHeight(Integer height) {
        _height = height;
    }
}
