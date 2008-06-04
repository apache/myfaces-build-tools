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

import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * HTML image loop component.
 * 
 * Image loop/slide show component. 
 * 
 * Provides Javascript methods to control image loop behaviour. 
 * Methods: getImageLoop(id) - get image loop object with id, 
 * play() - play loop, 
 * stop() - stop loop, 
 * accelerate() - accelerate loop until minDelay reached, 
 * decelerate() - decelerate loop until maxDelay reached, 
 * setImageIndex(index) - show image with index, 
 * reset() - reset settings to origin values, 
 * getImageCount() - get number of images loaded
 * 
 * @JSFComponent
 *   name = "s:imageLoop"
 *   class = "org.apache.myfaces.custom.imageloop.HtmlImageLoop"
 *   superClass = "org.apache.myfaces.custom.imageloop.AbstractHtmlImageLoop"
 *   tagClass = "org.apache.myfaces.custom.imageloop.HtmlImageLoopTag"
 *   
 * @author Felix Röthenbacher (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlImageLoop extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlImageLoop";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlImageLoop";
    
    // Value binding constants
    public static final String VB_DELAY = "delay";
    public static final String VB_MIN_DELAY = "minDelay";
    public static final String VB_MAX_DELAY = "maxDelay";
    public static final String VB_TRANSITION_TIME = "transitionTime";
    public static final String VB_WIDTH = "width";
    public static final String VB_HEIGHT = "height";
            
    public String getClientId(FacesContext context) {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }
        return clientId;
    }
    
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
    
    /**
     * The delay between transitions
     * 
     * @JSFProperty
     */
    public abstract Integer getDelay();
    
    /**
     * The minimum delay allowed when decreasing delay time
     * 
     * @JSFProperty
     */
    public abstract Integer getMinDelay();

    /**
     * The maximum delay allowed when increasing delay time
     * 
     * @JSFProperty
     */
    public abstract Integer getMaxDelay();

    /**
     * Transition time in milliseconds. Set to -1 for immediate image switch.
     * 
     * @JSFProperty
     */
    public abstract Integer getTransitionTime();
    
    /**
     * Width
     * 
     * @JSFProperty
     */
    public abstract Integer getWidth();

    /**
     * Height
     * 
     * @JSFProperty
     */
    public abstract Integer getHeight();
    
    /**
     * If true, this component will force the use of the specified id when rendering.
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "false"
     *   
     * @return
     */
    public abstract Boolean getForceId();
        
    /**
     *  If false, this component will not append a '[n]' suffix 
     *  (where 'n' is the row index) to components that are 
     *  contained within a "list." This value will be true by 
     *  default and the value will be ignored if the value of 
     *  forceId is false (or not specified.)
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "true"
     *   
     * @return
     */
    public abstract Boolean getForceIdIndex();
        
}
