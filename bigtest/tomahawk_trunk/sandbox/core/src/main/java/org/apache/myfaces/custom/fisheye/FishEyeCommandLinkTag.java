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

package org.apache.myfaces.custom.fisheye;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * JSP Tag for the FishEyeList component
 *
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FishEyeCommandLinkTag extends UIComponentTagBase
{
    private static final String CAPTION_ATTR = "caption";
    private static final String ICONSRC_ATTR = "iconSrc";
    private static final String TARGET_ATTR = "target";

    private String _caption;
    private String _iconSrc;
    private String _target;
    private String _action;
    private String _actionListener;
    private String _immediate;

    public String getComponentType() {
        return FishEyeCommandLink.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return FishEyeCommandLink.RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setStringProperty(component, CAPTION_ATTR, _caption);
        setStringProperty(component, ICONSRC_ATTR, _iconSrc);
        setStringProperty(component, TARGET_ATTR, _target);
        setActionProperty(component, _action);
        setActionListenerProperty(component, _actionListener);
        setBooleanProperty(component, org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr.IMMEDIATE_ATTR, _immediate);
    }

    public void release() {
        super.release();
        _caption = null;
        _iconSrc = null;
        _target = null;
        _action = null;
        _actionListener = null;
        _immediate = null;
    }

    public void setCaption(String caption) {
        _caption = caption;
    }

    public void setIconSrc(String iconSrc) {
        _iconSrc = iconSrc;
    }

    public void setTarget(String target) {
        _target = target;
    }

    public void setAction(String action) {
        _action = action;
    }

    public void setActionListener(String actionListener) {
        _actionListener = actionListener;
    }

    public void setImmediate(String immediate) {
        _immediate = immediate;
    }
}
