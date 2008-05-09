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
package org.apache.myfaces.custom.swapimage;

import javax.faces.component.UIGraphic;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleUtils;

/**
 * @JSFComponent
 *   name = "t:swapImage"
 *   class = "org.apache.myfaces.custom.swapimage.HtmlSwapImage"
 *   superClass = "org.apache.myfaces.custom.swapimage.AbstractHtmlSwapImage"
 *   tagClass = "org.apache.myfaces.custom.swapimage.HtmlSwapImageTag"
 * 
 * @author Thomas Spiegl
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlSwapImage extends UIGraphic
    implements UniversalProperties, AlignProperty
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSwapImage";
    public static final String COMPONENT_FAMILY = "javax.faces.Graphic";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SwapImage";
    private static final boolean DEFAULT_ISMAP = false;

    /**
     * @JSFProperty
     *   defaultValue="Integer.MIN_VALUE"
     */
    public abstract String getBorder();

    /**
     * @JSFProperty
     */
    public abstract String getHspace();
    
    /**
     * @JSFProperty
     */
    public abstract String getVspace();
    
    /**
     * @JSFProperty
     */
    public abstract String getSwapImageUrl();

    /**
     * @JSFProperty
     */
    public abstract String getActiveImageUrl();

    /**
     * @JSFProperty
     */
    public abstract String getAlt();

    /**
     * @JSFProperty
     */
    public abstract String getHeight();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isIsmap();

    /**
     * @JSFProperty
     */
    public abstract String getLongdesc();

    /**
     * @JSFProperty
     */
    public abstract String getOnclick();

    /**
     * @JSFProperty
     */
    public abstract String getOndblclick();

    /**
     * @JSFProperty
     */
    public abstract String getOnkeydown();

    /**
     * @JSFProperty
     */
    public abstract String getOnkeypress();

    /**
     * @JSFProperty
     */
    public abstract String getOnkeyup();

    /**
     * @JSFProperty
     */
    public abstract String getStyle();

    /**
     * @JSFProperty
     */
    public abstract String getStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getUsemap();

    /**
     * @JSFProperty
     */
    public abstract String getWidth();

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

}
