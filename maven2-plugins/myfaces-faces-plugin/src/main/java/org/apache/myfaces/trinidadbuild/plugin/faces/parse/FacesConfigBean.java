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

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * FacesConfigBean is a Java representation of the faces-config XML element.
 */
public class FacesConfigBean extends ObjectBean
{
  /**
   * Adds a converter to this faces config document.
   *
   * @param converter  the converter to add
   */
  public void addConverter(
    ConverterBean converter)
  {
    converter.attach(this);
    if (converter.hasConverterId())
      _converters.put(converter.getConverterId(), converter);
  }

  /**
   * Returns the converter for this converter type.
   *
   * @param converterId  the converter type to find
   */
  public ConverterBean findConverter(
    String converterId)
  {
    return (ConverterBean)_converters.get(converterId);
  }

  /**
   * Returns true if this faces config has any converters.
   *
   * @return true  if this faces config has any converters,
   *         otherwise false
   */
  public boolean hasConverters()
  {
    return !_converters.isEmpty();
  }

  /**
   * Returns an iterator for all converters in this faces
   * config.
   *
   * @return  the converter iterator
   */
  public Iterator converters()
  {
    return _converters.values().iterator();
  }

  /**
   * Adds a validator to this faces config document.
   *
   * @param validator  the validator to add
   */
  public void addValidator(
    ValidatorBean validator)
  {
    validator.attach(this);
    if (validator.hasValidatorId())
      _validators.put(validator.getValidatorId(), validator);
  }

  /**
   * Returns the validator for this validator type.
   *
   * @param validatorId  the validator type to find
   */
  public ValidatorBean findValidator(
    String validatorId)
  {
    return (ValidatorBean)_validators.get(validatorId);
  }

  /**
   * Returns true if this faces config has any validators.
   *
   * @return true  if this faces config has any validators,
   *         otherwise false
   */
  public boolean hasValidators()
  {
    return !_validators.isEmpty();
  }

  /**
   * Returns an iterator for all validators in this faces
   * config.
   *
   * @return  the validator iterator
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
  public void addComponent(
    ComponentBean component)
  {
    // Generic "includes" will not have a component type
    if (component.getComponentType() != null)
    {
      component.attach(this);
      _components.put(component.getComponentType(), component);
    }
  }

  /**
   * Returns the component for this component type.
   *
   * @param componentType  the component type to find
   */
  public ComponentBean findComponent(
    String componentType)
  {
    return (ComponentBean)_components.get(componentType);
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

  /**
   * Adds an event to this faces config document.
   *
   * @param event  the event to add
   */
  public void addEvent(
    EventBean event)
  {
    if (event.getEventType() == null)
    {
      _warning("Missing event type");
    }
    else if (event.getEventListenerClass() == null)
    {
      _warning("Event \"" + event.getEventType() + "\" " +
                   "has no listener class");
    }
    else if (event.getEventClass() == null)
    {
      _warning("Event \"" + event.getEventType() + "\" " +
                   "has no event class");
    }
    else
    {
      event.attach(this);
      _events.put(event.getEventType(), event);
    }
  }

  private void _warning(String s)
  {
    _LOG.warning(s+"\n  parsing resource:"+getCurrentResource());
  }

  /**
   * Returns the event for this event type.
   *
   * @param eventType  the event type to find
   */
  public EventBean findEvent(
    String eventType)
  {
    return (EventBean)_events.get(eventType);
  }

  /**
   * Returns true if this faces config has any events.
   *
   * @return true  if this faces config has any events,
   *         otherwise false
   */
  public boolean hasEvents()
  {
    return !_events.isEmpty();
  }

  /**
   * Returns an iterator for all events in this faces
   * config.
   *
   * @return  the event iterator
   */
  public Iterator events()
  {
    return _events.values().iterator();
  }

  /**
   * Adds a render kit to this faces config document.
   *
   * @param renderKit  the render kit to add
   */
  public void addRenderKit(
    RenderKitBean renderKit)
  {
    String renderKitId = renderKit.getRenderKitId();
    RenderKitBean existing = findRenderKit(renderKitId);

    if (existing == null)
    {
      renderKit.attach(this);
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
  public RenderKitBean findRenderKit(
    String renderKitId)
  {
    return (RenderKitBean)_renderKits.get(renderKitId);
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

  public URL getCurrentResource()
  {
    return _currentResource;
  }
  
  public URL setCurrentResource(URL resource)
  {
    URL _cur = _currentResource;
    _currentResource = resource;
    return _cur;
  }

  private Map _converters = new TreeMap();
  private Map _validators = new TreeMap();
  private Map _components = new TreeMap();
  private Map _events = new TreeMap();
  private Map _renderKits = new TreeMap();
  private URL _currentResource = null;

  static private final Logger _LOG = Logger.getLogger(FacesConfigBean.class.getName());
}
