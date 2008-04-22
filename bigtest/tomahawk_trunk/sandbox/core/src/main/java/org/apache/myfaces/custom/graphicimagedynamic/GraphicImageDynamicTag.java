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

package org.apache.myfaces.custom.graphicimagedynamic;

import org.apache.myfaces.taglib.html.ext.HtmlGraphicImageTag;

import javax.faces.component.UIComponent;

/**
 * @author Sylvain Vieujot, Mathias Br&ouml;kelmann
 * @version $Revision$ $Date: $
 */

public class GraphicImageDynamicTag extends HtmlGraphicImageTag
{
    private String _imageRendererClass;

    public String getComponentType()
    {
        return GraphicImageDynamic.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return GraphicImageDynamicRenderer.RENDERER_TYPE;
    }

    public void release()
    {
        super.release();
        _imageRendererClass = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "imageRendererClass", _imageRendererClass);
    }

    public void setImageRendererClass(String imageRendererClass)
    {
        _imageRendererClass = imageRendererClass;
    }
}
