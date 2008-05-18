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

package org.apache.myfaces.custom.toggle;

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

/**
 * Container class allows user to toggle between view/edit mode.
 * 
 * @JSFComponent
 *   name = "s:togglePanel"
 *   class = "org.apache.myfaces.custom.toggle.TogglePanel"
 *   superClass = "org.apache.myfaces.custom.toggle.AbstractTogglePanel"
 *   tagClass = "org.apache.myfaces.custom.toggle.TogglePanelTag"
 *   
 * @author Sharath
 * 
 */
public abstract class AbstractTogglePanel extends HtmlPanelGroup
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.TogglePanel";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TogglePanel";

    public static final boolean DEFAULT_TOGGLED = false;

    /**
     * @JSFProperty
     *   defaultValue="false"
     * @return
     */
    public abstract boolean isToggled();
    
    public abstract void setToggled(boolean toggleMode);
    
    public void processDecodes(FacesContext context)
    {
        super.processDecodes(context);

        String hiddenFieldId = this.getClientId(context) + "_hidden";
        String toggleMode = (String) context.getExternalContext().getRequestParameterMap().get(hiddenFieldId);

        if (toggleMode != null && toggleMode.trim().equals("1")) {
            this.setToggled(true);
        }
    }

    public void processUpdates(FacesContext context)
    {
        super.processUpdates(context);
        this.setToggled(false);
    }
        
    /**
     * HTML: The direction of text display, either 'ltr' (left-to-right) or 'rtl' (right-to-left).
     * 
     * @JSFProperty
     */
    public abstract String getDir();

    /**
     * HTML: The base language of this document.
     * 
     * @JSFProperty
     */
    public abstract String getLang();

    /**
     * HTML: An advisory title for this element.  Often used by the user agent as a tooltip.
     * 
     * @JSFProperty
     */
    public abstract String getTitle();

    /**
     * HTML: Script to be invoked when the element is clicked.
     * 
     * @JSFProperty
     */
    public abstract String getOnclick();

    /**
     * HTML: Script to be invoked when the element is double-clicked.
     * 
     * @JSFProperty
     */
    public abstract String getOndblclick();

    /**
     * HTML: Script to be invoked when a key is pressed down over this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnkeydown();

    /**
     * HTML: Script to be invoked when a key is pressed over this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnkeypress();

    /**
     * HTML: Script to be invoked when a key is released over this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnkeyup();

    /**
     * HTML: Script to be invoked when the pointing device is pressed over this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnmousedown();

    /**
     * HTML: Script to be invoked when the pointing device is moved while it is in this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnmousemove();

    /**
     * HTML: Script to be invoked when the pointing device is moves out of this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnmouseout();

    /**
     * HTML: Script to be invoked when the pointing device is moved into this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnmouseover();

    /**
     * HTML: Script to be invoked when the pointing device is released over this element.
     * 
     * @JSFProperty
     */
    public abstract String getOnmouseup();
    
}
