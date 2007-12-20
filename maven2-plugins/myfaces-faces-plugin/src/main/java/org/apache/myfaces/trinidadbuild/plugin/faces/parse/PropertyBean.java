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

/**
 * PropertyBean is a Java representation of the faces-config component property
 * XML element.
 */
public class PropertyBean extends AttributeBean
{
    public void setUseMaxTime(boolean _useMaxTime) 
    {
      this._useMaxTime = _useMaxTime;
    }
    
    public boolean getUseMaxTime() 
    {
      return _useMaxTime;
    }

  /**
   * Sets the name of this property.
   *
   * @param propertyName  the property name
   */
  public void setPropertyName(
    String propertyName)
  {
    setAttributeName(propertyName);
  }

  /**
   * Returns the name of this property.
   *
   * @return  the property name
   */
  public String getPropertyName()
  {
    return getAttributeName();
  }

  /**
   * Sets the property class for this property.
   *
   * @param propertyClass  the property class
   */
  public void setPropertyClass(
    String propertyClass)
  {
    setAttributeClass(propertyClass);
  }

  /**
   * Returns the property class for this property.
   *
   * @return  the property class
   */
  public String getPropertyClass()
  {
    return getAttributeClass();
  }

  /**
   * Returns the array of parameterized types for this property
   * if it uses generics.
   *
   * @return the array of parameterized types for this property
   */
  public String[] getPropertyClassParameters()
  {
    return getAttributeClassParameters();
  }

  /**
   * Sets the possible values for this property.
   *
   * @param propertyValues  the property values
   */
  public void setPropertyValues(
    String[] propertyValues)
  {
    _propertyValues = propertyValues;
  }

  /**
   * Returns possible values for this property.
   *
   * @return  the property values
   */
  public String[] getPropertyValues()
  {
    return _propertyValues;
  }

  /**
   * Sets the stateHolder flag of this property.
   *
   * @param stateHolder  the property stateHolder flag
   */
  public void setStateHolder(
    boolean stateHolder)
  {
    _stateHolder = stateHolder;
  }

  /**
   * Returns stateHolder flag of this property.
   *
   * @return  the property stateHolder flag
   */
  public boolean isStateHolder()
  {
    return _stateHolder;
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
   * Sets the list flag of this property.
   *
   * @param list  the property list flag
   */
  public void setList(
    boolean list_)
  {
    _list = list_;
  }

  /**
   * Returns list flag of this property.
   *
   * @return  the property list flag
   */
  public boolean isList()
  {
    return _list;
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

  /**
   * Sets the alias of this property.
   *
   * @param aliasOf  the property alias
   */
  public void setAliasOf(
    String aliasOf)
  {
    _aliasOf = aliasOf;
  }

  /**
   * Returns the alias of this property.
   *
   * @return  the property alias
   */
  public String getAliasOf()
  {
    return _aliasOf;
  }

  /**
   * Sets the unsupported agents for this property.
   *
   * @param unsupportedAgents  the unsupported agents
   */
  public void setUnsupportedAgents(
    String[] unsupportedAgents)
  {
    if (unsupportedAgents == null)
      throw new NullPointerException("unsupportedAgents");

    _unsupportedAgents = unsupportedAgents;
  }

  /**
   * Returns unsupported agents for this property.
   *
   * @return  the unsupported agents
   */
  public String[] getUnsupportedAgents()
  {
    return _unsupportedAgents;
  }

  /**
   * Sets the unsupported RenderKits for this property.
   *
   * @param unsupportedRenderKits  the unsupported RenderKits
   */
  public void setUnsupportedRenderKits(
    String[] unsupportedRenderKits)
  {
    if (unsupportedRenderKits == null)
      throw new NullPointerException("unsupportedRenderKits");

    _unsupportedRenderKits = unsupportedRenderKits;
  }

  /**
   * Returns unsupported RenderKits for this property.
   *
   * @return  the unsupported RenderKits
   */
  public String[] getUnsupportedRenderKits()
  {
    return _unsupportedRenderKits;
  }

  /**
   * Sets the tag attribute excluded flag for this property.
   *
   * @param excluded  true,  if the tag attribute should be excluded;
   *                  false, otherwise
   */
  public void setTagAttributeExcluded(
    boolean excluded)
  {
    _tagAttributeExcluded = excluded;
  }

  /**
   * Returns the tag attribute excluded flag for this property.
   *
   * @return true,  if the tag attribute should be excluded;
   *         false, otherwise
   */
  public boolean isTagAttributeExcluded()
  {
    return _tagAttributeExcluded;
  }

  /**
   * Returns true if the property is an enumerated Java type.
   */
  public boolean isEnum()
  {
    return _enum;
  }

  /**
   * Returns true if the property is an enumerated Java type.
   */
  public void setEnum(boolean isEnum)
  {
    _enum = isEnum;
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

  /**
   * Parses the possible values for this property into a String array
   * using space as the separator between values.
   *
   * @param propertyValues  the property values
   */
  public void parsePropertyValues(
    String propertyValues)
  {
    setPropertyValues(propertyValues.split(" "));
  }

  /**
   * Parses the unsupported agents for this property into a String array
   * using space as the separator between values.
   *
   * @param unsupportedAgents  the unsupported agents
   */
  public void parseUnsupportedAgents(
    String unsupportedAgents)
  {
    setUnsupportedAgents(unsupportedAgents.split(" "));
  }

  /**
   * Parses the unsupported RenderKits for this property into a String array
   * using space as the separator between values.
   *
   * @param unsupportedRenderKits  the unsupported RenderKits
   */
  public void parseUnsupportedRenderKits(
    String unsupportedRenderKits)
  {
    setUnsupportedRenderKits(unsupportedRenderKits.split(" "));
  }

  /**
   * Sets the JSP name of this property.
   *
   * @param jspPropertyName  the JSP property name
   */
  public void setJspPropertyName(
    String jspPropertyName)
  {
    _jspPropertyName = jspPropertyName;
  }

  /**
   * Returns the JSP name of this property.
   *
   * @return  the JSP property name
   */
  public String getJspPropertyName()
  {
    if (_jspPropertyName == null)
      return getPropertyName();

    return _jspPropertyName;
  }

  /**
   * Sets the field name of this property, when not generating Trinidad components
   *
   * @param fieldPropertyName  the field property name
   */
  public void setFieldPropertyName(
    String fieldPropertyName)
  {
    _fieldPropertyName = fieldPropertyName;
  }

  /**
   * Returns the field name of this property, when not generating Trinidad components
   *
   * @return  the field property name
   */
  public String getFieldPropertyName()
  {
    if (_fieldPropertyName == null)
      return "_"+getPropertyName();

    return _fieldPropertyName;
  }

  private String  _aliasOf;
  private String  _jspPropertyName;
  private String  _fieldPropertyName;
  private boolean _required;
  private boolean _literalOnly;
  private boolean _stateHolder;
  private boolean _transient;
  private boolean _list;
  private boolean _tagAttributeExcluded;
  private boolean _enum;
  private boolean _useMaxTime;
  private String[] _propertyValues;
  private String[] _unsupportedAgents = _EMPTY_ARRAY;
  private String[] _unsupportedRenderKits = _EMPTY_ARRAY;

  static private String[] _EMPTY_ARRAY = new String[0];

}
