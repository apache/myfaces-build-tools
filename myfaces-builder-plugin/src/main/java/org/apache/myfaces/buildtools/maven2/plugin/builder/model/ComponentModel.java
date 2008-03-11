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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 */
public class ComponentModel
{
  static private final Logger _LOG = Logger.getLogger(ComponentModel.class.getName());

  /** Brief description of this component, for tool-tips etc */
  private String  _description;
  
  /** Longer description of this component. */
  private String  _longDescription;
  
  private String  _componentClass;
  private String  _componentType;
  private String  _componentFamily;
  private String  _rendererType;

  private String  _tagName;
  private String  _tagClass;
  private String  _tagHandler;
  private String  _tagSuperclass;
  private boolean _namingContainer;
  private boolean _children = true;

  protected Map   _properties;

  /**
   * Creates a new ComponentBean.
   */
  public ComponentModel()
  {
    _properties = new LinkedHashMap();
  }

  /**
   * Sets the component type for this component.
   *
   * @param componentType  the component type
   */
  public void setComponentType(String componentType)
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
   * Returns true if the component family exists for this component.
   *
   * @return  true  if the component family exists,
   *          false otherwise
   */
  public boolean hasComponentFamily()
  {
    return (_componentFamily != null);
  }

  /**
   * Sets the component class for this component.
   *
   * @param componentClass  the component class
   */
  public void setComponentClass(
    String componentClass)
  {
    _componentClass = componentClass;
  }

  /**
   * Returns the component class for this component.
   *
   * @return  the component class
   */
  public String getComponentClass()
  {
    return _componentClass;
  }

  /**
   * Sets the description of this property.
   *
   * @param description  the property description
   */
  public void setDescription(
    String description)
  {
    _description = description;
  }

  /**
   * Returns the description of this property.
   *
   * @return  the property description
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Sets the long description of this property.
   *
   * @param longDescription  the long property description
   */
  public void setLongDescription(
    String longDescription)
  {
    _longDescription = longDescription;
  }

  /**
   * Returns the long description of this property.
   *
   * @return  the long property description
   */
  public String getLongDescription()
  {
    return _longDescription;
  }

  /**
   * Sets the JSP tag handler class for this component.
   *
   * @param tagClass  the JSP tag handler class
   */
  public void setTagClass(
    String tagClass)
  {
    _tagClass = tagClass;
  }

  /**
   * Returns the JSP tag handler class for this component.
   *
   * @return  the JSP tag handler class
   */
  public String getTagClass()
  {
    return _tagClass;
  }

  /**
   * Sets the JSP tag handler superclass for this component.
   *
   * @param tagSuperclass  the JSP tag handler superclass
   */
  public void setTagSuperclass(
    String tagSuperclass)
  {
    _tagSuperclass = tagSuperclass;
  }

  /**
   * Returns the JSP tag handler superclass for this component.
   *
   * @return  the JSP tag handler superclass
   */
  public String getTagSuperclass()
  {
    return _tagSuperclass;
  }

  /**
  * Sets the Facelets tag handler (component handler) this component.
  *
  * @param tagHandler the Facelets tag handler class
  */
 public void setTagHandler(
   String tagHandler)
 {
   _tagHandler = tagHandler;
 }

 /**
  * Returns the Facelets tag handler for this component
  *
  * @return  the Facelets tag handler
  */
 public String getTagHandler()
 {
   return _tagHandler;
 }

  /**
   * Returns the JSP tag name for this component.
   *
   * @return  the JSP tag name
   */
  public String getTagName()
  {
    return _tagName;
  }

  /**
   * Sets the JSP tag name for this component.
   *
   * @param tagName  the JSP tag name
   */
  public void setTagName(String tagName)
  {
    _tagName = tagName;
  }

  /**
   * Sets the namingContainer flag of this property.
   *
   * @param namingContainer  the component namingContainer flag
   */
  public void setNamingContainer(
    boolean namingContainer)
  {
    _namingContainer = namingContainer;
  }

  /**
   * Returns namingContainer flag of this component.
   *
   * @return  the component namingContainer flag
   */
  public boolean isNamingContainer()
  {
    return _namingContainer;
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
   * Returns the default renderer type for this component.
   *
   * @return  the default renderer type
   */
  public String getDefaultRendererType()
  {
	  return null;
  }

  /**
   * Adds a property to this component.
   *
   * @param property  the property to add
   */
  public void addProperty(PropertyModel property)
  {
    _properties.put(property.getPropertyName(), property);
  }

  /**
   * Returns the property for this property name.
   *
   * @param propertyName  the property name to find
   */
  public PropertyModel findProperty(String propertyName)
  {
    return (PropertyModel)_properties.get(propertyName);
  }

  /**
   * Returns true if this component has any properties.
   *
   * @return  true   if this component has any properties,
   *          false  otherwise
   */
  public boolean hasProperties()
  {
    return _properties.size() > 0;
  }

  /**
   * Returns an iterator for all properties
   *
   * @return  the property iterator
   */
  public Iterator properties()
  {
    return _properties.values().iterator();
  }

 /**
  * Number of properties for this component
  * @return num of properties
  */
  public int propertiesSize()
  {
    return _properties.size();
  }

  /**
   * Specifies if the component can have children.
   *
   * @param children  true  if the component can have children.
   *                  false otherwise
   */
  public void setChildren(
    boolean children)
  {
    _children = children;
  }

  /**
   * Returns true if the component can have children.
   *
   * @return  true  if the component can have children.
   *          false otherwise
   */
  public boolean hasChildren()
  {
    return _children;
  }
}
