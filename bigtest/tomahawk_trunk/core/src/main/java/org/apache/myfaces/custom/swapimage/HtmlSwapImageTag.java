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

import org.apache.myfaces.taglib.html.ext.HtmlGraphicImageTag;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl
 * @version $Revision$ $Date$
 */
public class HtmlSwapImageTag
        extends HtmlGraphicImageTag
{
    private static final String RENDERER_TYPE = "org.apache.myfaces.SwapImage";

    private static final String SWAP_IMG_URL_ATTR = "swapImageUrl";
    private static final String ACTIVE_IMG_URL_ATTR = "activeImageUrl";

    private String _swapImageUrl;
    private String _activeImageUrl;

    public String getComponentType()
    {
        return HtmlSwapImage.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return RENDERER_TYPE;
    }

    public void release() {
        super.release();
        _swapImageUrl=null;
        _activeImageUrl=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, SWAP_IMG_URL_ATTR, _swapImageUrl);
        setStringProperty(component, ACTIVE_IMG_URL_ATTR, _activeImageUrl);
    }

    public void setSwapImageUrl(String swapImageUrl)
    {
        _swapImageUrl = swapImageUrl;
    }

    public void setActiveImageUrl(String activeImageUrl)
    {
        _activeImageUrl = activeImageUrl;
    }

    public void setOnmouseover(String onmouseover)
    {
        throw new UnsupportedOperationException(HtmlSwapImageTag.class.getName() + ".setOnmouseover not supported.");
    }

    public void setOnmousedown(String onmousedown)
    {
        throw new UnsupportedOperationException(HtmlSwapImageTag.class.getName() + ".setOnmousedown not supported.");
    }

    public void setOnmouseup(String onmouseup)
    {
        throw new UnsupportedOperationException(HtmlSwapImageTag.class.getName() + ".setOnmouseup not supported.");
    }

    public void setOnmousemove(String onmousemove)
    {
        throw new UnsupportedOperationException(HtmlSwapImageTag.class.getName() + ".setOnmousemove not supported.");
    }

    public void setOnmouseout(String onmouseout)
    {
        throw new UnsupportedOperationException(HtmlSwapImageTag.class.getName() + ".setOnmouseout not supported.");
    }
}
