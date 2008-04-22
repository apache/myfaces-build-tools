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

import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.imageloop.HtmlImageLoop;
import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

/**
 * HTML image loop tag.
 * @author Felix Röthenbacher (latest modification by $Author:$)
 * @version $Revision:$ $Date:$
 */
public class HtmlImageLoopTag extends UIComponentTagBase {
    
    private String _delay;
    private String _minDelay;
    private String _maxDelay;
    private String _transitionTime;
    private String _width;
    private String _height;

    public String getComponentType() {
        return HtmlImageLoop.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return HtmlImageLoop.RENDERER_TYPE;
    }

    protected void setProperties(UIComponent uiComponent) {
        super.setProperties(uiComponent);
        setIntegerProperty(uiComponent, HtmlImageLoop.VB_DELAY, _delay);
        setIntegerProperty(uiComponent, HtmlImageLoop.VB_MIN_DELAY, _minDelay);
        setIntegerProperty(uiComponent, HtmlImageLoop.VB_MAX_DELAY, _maxDelay);
        setIntegerProperty(uiComponent, HtmlImageLoop.VB_TRANSITION_TIME, _transitionTime);
        setIntegerProperty(uiComponent, HtmlImageLoop.VB_WIDTH, _width);
        setIntegerProperty(uiComponent, HtmlImageLoop.VB_HEIGHT, _height);
    }
    
    public void release() {
        super.release();
        _delay = null;
        _minDelay = null;
        _maxDelay = null;
        _transitionTime = null;
        _width = null;
        _height = null;
    }
    
    public void setDelay(String delay) {
        _delay = delay;
    }
    
    public void setMinDelay(String minDelay) {
        _minDelay = minDelay;
    }
    
    public void setMaxDelay(String maxDelay) {
        _maxDelay = maxDelay;
    }
    
    public void setTransitionTime(String transitionTime) {
        _transitionTime = transitionTime;
    }
    
    public void setWidth(String width) {
        _width = width;
    }
    
    public void setHeight(String height) {
        _height = height;
    }
}
