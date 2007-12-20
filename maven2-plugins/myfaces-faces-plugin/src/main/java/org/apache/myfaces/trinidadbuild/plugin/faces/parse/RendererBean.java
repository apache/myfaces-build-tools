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

import org.apache.myfaces.trinidadbuild.plugin.faces.util.CompoundIterator;

/**
 * RenderKitBean is a Java representation of the faces-config render-kit
 * XML element.
 */
public class RendererBean extends ObjectBean
{
  /**
   * Creates a new RendererBean.
   */
  public RendererBean()
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
    ComponentBean component = resolveComponentType();
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
    ComponentBean component = resolveComponentType();
    ComponentBean behavioral = (component != null)
                                  ? component.findBehavioralComponent()
                                  : null;
    return (behavioral != null) ? behavioral.getComponentClass()
                                : "org.apache.myfaces.trinidad.component.UIXComponent";
  }

  /**
   * Adds a attribute to this component.
   *
   * @param attribute  the attribute to add
   */
  public void addAttribute(
    AttributeBean attribute)
  {
    _attributes.put(attribute.getAttributeName(), attribute);
  }

  /**
   * Returns the attribute for this attribute name.
   *
   * @param attributeName  the attribute name to find
   */
  public AttributeBean findAttribute(
    String attributeName)
  {
    AttributeBean attribute = (AttributeBean)_attributes.get(attributeName);

    if (attribute == null)
    {
      ComponentBean component = resolveComponentType();
      if (component != null)
        attribute = component.findProperty(attributeName);
    }

    return attribute;
  }

  /**
   * Returns true if this component has any attributes.
   *
   * @return  true   if this component has any attributes,
   *          false  otherwise
   */
  public boolean hasAttributes()
  {
    boolean hasAttributes = !_attributes.isEmpty();

    if (!hasAttributes)
    {
      ComponentBean component = resolveComponentType();
      if (component != null)
        hasAttributes |= component.hasProperties();
    }

    return hasAttributes;
  }

  /**
   * Returns true if this component or any component supertype
   * has any attributes.
   *
   * @return  true   if this component or any supertype has any attributes,
   *          false  otherwise
   */
  public boolean hasAttributes(
    boolean flatten)
  {
    boolean hasAttributes = hasAttributes();

    if (!hasAttributes && flatten)
    {
      ComponentBean component = resolveComponentType();
      if (component != null)
      {
        ComponentBean parent = component.resolveSupertype();
        if (parent != null)
          hasAttributes |= parent.hasProperties(true);
      }
    }

    return hasAttributes;
  }

  /**
   * Returns an iterator for all attributes on this component only.
   *
   * @return  the attribute iterator
   */
  public Iterator attributes()
  {
    Iterator attributes = _attributes.values().iterator();

    ComponentBean component = resolveComponentType();
    if (component != null)
        attributes = new CompoundIterator(attributes, component.properties());

    return attributes;
  }

  /**
   * Returns an iterator for attributes on this component.
   *
   * @param flatten  true   if the iterator should be a combined list of
   *                        attributes of this component and its supertype,
   *                 false  otherwise
   *
   * @return  the attribute iterator
   */
  public Iterator attributes(
    boolean flatten)
  {
    Iterator attributes = attributes();
    if (flatten)
    {
      ComponentBean component = resolveComponentType();
      if (component != null)
      {
        ComponentBean parent = component.resolveSupertype();
        if (parent != null)
          attributes = new CompoundIterator(attributes, parent.properties(true));
      }
    }
    return attributes;
  }

  /**
   * Adds a facet to this component.
   *
   * @param facet  the facet to add
   */
  public void addFacet(
    FacetBean facet)
  {
    _facets.put(facet.getFacetName(), facet);
  }

  /**
   * Returns the facet for this facet name.
   *
   * @param facetName  the facet name to find
   */
  public FacetBean findFacet(
    String facetName)
  {
    return (FacetBean)_facets.get(facetName);
  }

  /**
   * Returns true if this component has any facets.
   *
   * @return  true   if this component has any facets,
   *          false  otherwise
   */
  public boolean hasFacets()
  {
    boolean hasFacets = !_facets.isEmpty();

    ComponentBean component = resolveComponentType();
    if (component != null)
      hasFacets |= component.hasFacets();

    return hasFacets;
  }

  /**
   * Returns true if this component or any component supertype
   * has any facets.
   *
   * @return  true   if this component or any supertype has any facets,
   *          false  otherwise
   */
  public boolean hasFacets(
    boolean flatten)
  {
    boolean hasFacets = hasFacets();

    if (!hasFacets && flatten)
    {
      ComponentBean component = resolveComponentType();
      if (component != null)
      {
        ComponentBean parent = component.resolveSupertype();
        if (parent != null)
          hasFacets |= parent.hasFacets(true);
      }
    }

    return hasFacets;
  }

  /**
   * Returns an iterator for all facets on this component only.
   *
   * @return  the facet iterator
   */
  public Iterator facets()
  {
    Iterator facets = _facets.values().iterator();

    ComponentBean component = resolveComponentType();
    if (component != null)
        facets = new CompoundIterator(facets, component.facets());

    return facets;
  }

  /**
   * Returns an iterator for facets on this component.
   *
  * @param flatten  true   if the iterator should be a combined list of
  *                        facets of this component and its supertype,
  *                 false  otherwise
  *
  * @return  the facet iterator
  */
  public Iterator facets(
    boolean flatten)
  {
    Iterator facets = facets();
    if (flatten)
    {
      ComponentBean component = resolveComponentType();
      if (component != null)
      {
        ComponentBean parent = component.resolveSupertype();
        if (parent != null)
          facets = new CompoundIterator(facets, parent.facets(true));
      }
    }
    return facets;
  }

  /**
   * Returns the component type instance.
   *
   * @return  the component type instance
   */
  public ComponentBean resolveComponentType()
  {
    if (_componentType == null)
      return null;

    FacesConfigBean owner = getOwner();
    return (owner != null) ? owner.findComponent(_componentType) : null;
  }

  private String  _description;
  private String  _componentFamily;
  private String  _rendererType;
  private String  _rendererClass;
  private String  _rendererSuperclass;
  private String  _componentType;
  private Map     _attributes;
  private Map     _facets;
}
