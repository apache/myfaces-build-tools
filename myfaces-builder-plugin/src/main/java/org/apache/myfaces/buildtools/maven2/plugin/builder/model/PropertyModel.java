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

/**
 */
public class PropertyModel
{
  private String name;
  private String propertyClass;
  private boolean _required;
  private boolean _literalOnly;
  private boolean _transient;
  private String description;
	
  /**
   * Sets the name of this property.
   *
   * @param propertyName  the property name
   */
  public void setPropertyName(
    String propertyName)
  {
    name = propertyName;
  }

  /**
   * Returns the name of this property.
   *
   * @return  the property name
   */
  public String getPropertyName()
  {
    return name;
  }

  /**
   * Sets the property class for this property.
   *
   * @param propertyClass  the property class
   */
  public void setPropertyClass(
    String propertyClass)
  {
    this.propertyClass = propertyClass;
  }

  /**
   * Returns the property class for this property.
   *
   * @return  the property class
   */
  public String getPropertyClass()
  {
    return propertyClass;
  }

  /**
   * Sets the transient flag of this property.
   *
   * @param transient  the property transient flag
   */
  public void setTransient(
    boolean transient_)
  {
    _transient = transient_;
  }

  /**
   * Returns transient flag of this property.
   *
   * @return  the property transient flag
   */
  public boolean isTransient()
  {
    return _transient;
  }

  /**
   * Sets the required flag of this property.
   *
   * @param required  the property required flag
   */
  public void setRequired(
    boolean required)
  {
    _required = required;
  }

  /**
   * Returns required flag of this property.
   *
   * @return  the property required flag
   */
  public boolean isRequired()
  {
    return _required;
  }

  /**
   * Sets the literalOnly flag of this property.
   *
   * @param literalOnly  the property literalOnly flag
   */
  public void setLiteralOnly(
    boolean literalOnly)
  {
    _literalOnly = literalOnly;
  }

  /**
   * Returns literalOnly flag of this property.
   *
   * @return  the property literalOnly flag
   */
  public boolean isLiteralOnly()
  {
    return _literalOnly;
  }

  public void setDescription(String desc)
  {
	  description = desc;
  }
  
  public String getDescription()
  {
	return description;  
  }

  /**
   * Returns true if this property is a method binding.
   *
   * @return true  if this property is a method binding,
   *         otherwise false
   */
  public boolean isMethodBinding()
  {
    return ("javax.faces.el.MethodBinding".equals(getPropertyClass()));
  }


  /**
   * Returns true if this property is a method binding.
   *
   * @return true  if this property is a method binding,
   *         otherwise false
   */
  public boolean isMethodExpression()
  {
    return ("javax.el.MethodExpression".equals(getPropertyClass()));
  }
}
