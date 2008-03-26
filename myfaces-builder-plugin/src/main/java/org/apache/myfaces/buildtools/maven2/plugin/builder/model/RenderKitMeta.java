/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.buildtools.maven2.plugin.builder.model;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Store metadata about a JSF RenderKit.
 */
public class RenderKitMeta
{
    private String _className;

    private String _renderKitId = "";
    private Map _renderers;

    /**
     * The name of the class that this metadata applies to.
     */
    public String getClassName()
    {
        return _className;
    }

    public void setClassName(String className)
    {
        _className = className;
    }

    /**
     * Creates a new RenderKitBean.
     */
    public RenderKitMeta()
    {
        _renderers = new TreeMap();
    }

    /**
     * Sets the render kit id for this component.
     * 
     * @param renderKitId
     *            the render kit id
     */
    public void setRenderKitId(String renderKitId)
    {
        _renderKitId = renderKitId;
    }

    /**
     * Returns the render kit id type for this component.
     * 
     * @return the render kit id
     */
    public String getRenderKitId()
    {
        return _renderKitId;
    }

    /**
     * Adds a renderer to this render kit.
     * 
     * @param renderer
     *            the renderer to add
     */
    public void addRenderer(RendererMeta renderer)
    {
        String componentFamily = renderer.getComponentFamily();
        String rendererType = renderer.getRendererType();
        String compositeKey = componentFamily + "|" + rendererType;
        _renderers.put(compositeKey, renderer);
    }

    /**
     * Returns the renderer for this component family and renderer type.
     * 
     * @param componentFamily
     *            the component family
     * @param rendererType
     *            the renderer type
     */
    public RendererMeta findRenderer(String componentFamily,
            String rendererType)
    {
        String compositeKey = componentFamily + "|" + rendererType;
        return (RendererMeta) _renderers.get(compositeKey);
    }

    /**
     * Returns true if this render kit has any renderers.
     * 
     * @return true if this render kit has any renderers, false otherwise
     */
    public boolean hasRenderers()
    {
        return !_renderers.isEmpty();
    }

    /**
     * Returns an iterator for all renderers in this render kit.
     * 
     * @return the renderer iterator
     */
    public Iterator renderers()
    {
        return _renderers.values().iterator();
    }

    void addAllRenderers(RenderKitMeta renderKit)
    {
        for (Iterator i = renderKit._renderers.values().iterator(); i.hasNext();)
        {
            // use addRenderer to establish owner
            addRenderer((RendererMeta) i.next());
        }
    }
}
