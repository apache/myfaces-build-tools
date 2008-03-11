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
 */
public class RendererModel
{

	  private String  _description;
	  private String  _componentFamily;
	  private String  _rendererType;
	  private String  _rendererClass;
	  private String  _rendererSuperclass;
	  private String  _componentType;
	  private Map     _attributes;
	  private Map     _facets;

	  /**
   * Creates a new RendererBean.
   */
  public RendererModel()
  {
    _attributes = new TreeMap();
    _facets = new TreeMap();
  }

  /**
   * Sets the component family for this component.
   *
   * @param componentFamily  the component family
   */
  public void setComponentFamily(
    String componentFamily)
  {
    _componentFamily = componentFamily;
  }

  /**
   * Returns the component family for this component.
   *
   * @return  the component family
   */
  public String getComponentFamily()
  {
    return _componentFamily;
  }

  /**
   * Sets the renderer type for this component.
   *
   * @param rendererType  the renderer type
   */
  public void setRendererType(
    String rendererType)
  {
    _rendererType = rendererType;
  }

  /**
   * Returns the renderer type for this component.
   *
   * @return  the renderer type
   */
  public String getRendererType()
  {
    return _rendererType;
  }

  /**
   * Sets the renderer class for this renderer.
   *
   * @param rendererClass  the renderer class
   */
  public void setRendererClass(
    String rendererClass)
  {
    _rendererClass = rendererClass;
  }

  /**
   * Returns the renderer class for this renderer.
   *
   * @return  the renderer class
   */
  public String getRendererClass()
  {
    return _rendererClass;
  }

  /**
   * Sets the description of this attribute.
   *
   * @param description  the attribute description
   */
  public void setDescription(
    String description)
  {
    _description = description;
  }

  /**
   * Returns the description of this attribute.
   *
   * @return  the attribute description
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Sets the component type for this component.
   *
   * @param componentType  the component type
   */
  public void setComponentType(
    String componentType)
  {
    _componentType = componentType;
  }

  /**
   * Returns the component type for this component.
   *
   * @return  the component type
   */
  public String getComponentType()
  {
    return _componentType;
  }

  /**
   * Sets the renderer superclass for this renderer.
   *
   * @param rendererSuperclass  the renderer superclass
   */
  public void setRendererSuperclass(
    String rendererSuperclass)
  {
    _rendererSuperclass = rendererSuperclass;
  }

  /**
   * Returns the renderer superclass for this component.
   *
   * @return  the renderer superclass
   */
  public String getRendererSuperclass()
  {
    return _rendererSuperclass;
  }

  /**
   * Finds the renderer-specific component class for this renderer.
   *
   * @return  the renderer-specifc component class
   */
  public String findComponentClass()
  {
    ComponentModel component = resolveComponentType();
    return (component != null) ? component.getComponentClass()
                               : "org.apache.myfaces.trinidad.component.UIXComponent";
  }

  /**
   * Finds the behavioral component class for this renderer.
   *
   * @return  the behavioral component class
   */
  public String findComponentFamilyClass()
  {
	  return null;
  }

  /**
   * Returns the component type instance.
   *
   * @return  the component type instance
   */
  public ComponentModel resolveComponentType()
  {
    if (_componentType == null)
      return null;
    return null;
  }
}
