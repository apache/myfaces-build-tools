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

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Stores info about all of the jsf artifacts in the system being processed.
 */
public class Model
{
  static private final Logger _LOG = Logger.getLogger(Model.class.getName());

  private Map _converters = new TreeMap();
  private Map _validators = new TreeMap();
  private Map _components = new TreeMap();
  private Map _renderKits = new TreeMap();

  /**
   * Holds info about a JSF Converter definition
   */
  public void addConverter(ConverterModel converter)
  {
      _converters.put(converter.getConverterId(), converter);
  }

  /**
   * Returns an iterator for all converters
   */
  public Iterator converters()
  {
    return _converters.values().iterator();
  }

  /**
   * Holds info about a JSF Converter definition
   */
  public void addValidator(ValidatorModel validator)
  {
      _validators.put(validator.getValidatorId(), validator);
  }

  /**
   * Returns an iterator for all validators
   */
  public Iterator validators()
  {
    return _validators.values().iterator();
  }

  /**
   * Adds a component to this faces config document.
   *
   * @param component  the component to add
   */
  public void addComponent(ComponentModel component)
  {
    // Generic "includes" will not have a component type
    if (component.getComponentType() != null)
    {
      _components.put(component.getComponentType(), component);
    }
  }

  /**
   * Returns the component for this component type.
   *
   * @param componentType  the component type to find
   */
  public ComponentModel findComponent(
    String componentType)
  {
    return (ComponentModel)_components.get(componentType);
  }

  /**
   * Returns true if this faces config has any components.
   *
   * @return true  if this faces config has any components,
   *         otherwise false
   */
  public boolean hasComponents()
  {
    return !_components.isEmpty();
  }

  /**
   * Returns an iterator for all components in this faces
   * config.
   *
   * @return  the component iterator
   */
  public Iterator components()
  {
    return _components.values().iterator();
  }

  private void _warning(String s)
  {
    _LOG.warning(s);
  }

  /**
   * Adds a render kit to this faces config document.
   *
   * @param renderKit  the render kit to add
   */
  public void addRenderKit(RenderKitModel renderKit)
  {
    String renderKitId = renderKit.getRenderKitId();
    RenderKitModel existing = findRenderKit(renderKitId);

    if (existing == null)
    {
      _renderKits.put(renderKitId, renderKit);
    }
    else
    {
      existing.addAllRenderers(renderKit);
    }
  }

  /**
   * Returns the render kit for this render kit id.
   *
   * @param renderKitId  the render kit id to find
   */
  public RenderKitModel findRenderKit(
    String renderKitId)
  {
    return (RenderKitModel)_renderKits.get(renderKitId);
  }

  /**
   * Returns true if this faces config has any render kits.
   *
   * @return true  if this faces config has any render kits,
   *         otherwise false
   */
  public boolean hasRenderKits()
  {
    return !_renderKits.isEmpty();
  }

  /**
   * Returns an iterator for all render kits in this faces
   * config.
   *
   * @return  the render kit iterator
   */
  public Iterator renderKits()
  {
    return _renderKits.values().iterator();
  }
}
