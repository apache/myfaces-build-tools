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
package org.apache.myfaces.trinidadbuild.plugin.faces.parse;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * RenderKitBean is a Java representation of the faces-config render-kit
 * XML element.
 */
public class RenderKitBean extends ObjectBean
{
  /**
   * Creates a new RenderKitBean.
   */
  public RenderKitBean()
  {
    _renderers = new TreeMap();
  }

  /**
   * Sets the render kit id for this component.
   *
   * @param renderKitId  the render kit id
   */
  public void setRenderKitId(
    String renderKitId)
  {
    _renderKitId = renderKitId;
  }

  /**
   * Returns the render kit id type for this component.
   *
   * @return  the render kit id
   */
  public String getRenderKitId()
  {
    return _renderKitId;
  }

  /**
   * Adds a renderer to this render kit.
   *
   * @param renderer  the renderer to add
   */
  public void addRenderer(
    RendererBean renderer)
  {
    String componentFamily = renderer.getComponentFamily();
    String rendererType = renderer.getRendererType();
    String compositeKey = componentFamily + "|" + rendererType;
    _renderers.put(compositeKey, renderer);

    FacesConfigBean owner = getOwner();
    if (owner != null)
      renderer.attach(owner);
  }

  /**
   * Returns the renderer for this component family and renderer type.
   *
   * @param componentFamily  the component family
   * @param rendererType     the renderer type
   */
  public RendererBean findRenderer(
    String componentFamily,
    String rendererType)
  {
    String compositeKey = componentFamily + "|" + rendererType;
    return (RendererBean) _renderers.get(compositeKey);
  }

  /**
   * Returns true if this render kit has any renderers.
   *
   * @return  true   if this render kit has any renderers,
   *          false  otherwise
   */
  public boolean hasRenderers()
  {
    return !_renderers.isEmpty();
  }

  /**
   * Returns an iterator for all renderers in this render kit.
   *
   * @return  the renderer iterator
   */
  public Iterator renderers()
  {
    return _renderers.values().iterator();
  }

  /**
   * Attaches the component and all event references.
   *
   * @param owner  the faces config owner
   */
  protected void attach(
    FacesConfigBean owner)
  {
    super.attach(owner);
    Iterator renderers = renderers();
    while (renderers.hasNext())
    {
      RendererBean renderer = (RendererBean)renderers.next();
      renderer.attach(owner);
    }
  }

  void addAllRenderers(
    RenderKitBean renderKit)
  {
    for (Iterator i = renderKit._renderers.values().iterator(); i.hasNext();)
    {
      // use addRenderer to establish owner
      addRenderer((RendererBean)i.next());
    }
  }

  private String  _renderKitId = "";
  private Map     _renderers;
}
