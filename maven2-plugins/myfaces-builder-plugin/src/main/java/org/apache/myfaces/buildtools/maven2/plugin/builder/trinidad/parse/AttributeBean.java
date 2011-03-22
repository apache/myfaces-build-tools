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
package org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AttributeBean is a Java representation of the faces-config component or
 * renderer attribute XML element.
 */
public class AttributeBean extends ObjectBean implements Comparable
{
  /**
   * Sets the name of this attribute.
   *
   * @param attributeName  the attribute name
   */
  public void setAttributeName(
    String attributeName)
  {
    _attributeName = attributeName;
  }

  /**
   * Returns the name of this attribute.
   *
   * @return  the attribute name
   */
  public String getAttributeName()
  {
    return _attributeName;
  }

  /**
   * Sets the attribute class for this attribute.
   *
   * @param attributeClass  the attribute class
   */
  public void setAttributeClass(
    String attributeClass)
  {
    Matcher matcher = _GENERIC_TYPE.matcher(attributeClass);

    if (matcher.matches())
    {
      _attributeClass = matcher.group(1);
      _attributeClassParameters = matcher.group(2).split(",");
    }
    else
    {
      _attributeClass = attributeClass;
      _attributeClassParameters = _EMPTY_ARRAY;
    }
  }

  /**
   * Returns the array of parameterized types for this attribute
   * if it uses generics.
   *
   * @return the array of parameterized types for this attribute
   */
  public String[] getAttributeClassParameters()
  {
    return _attributeClassParameters;
  }

  /**
   * Returns the attribute class for this attribute.
   *
   * @return  the attribute class
   */
  public String getAttributeClass()
  {
    return _attributeClass;
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
   * Sets the default value of this attribute.
   *
   * @param defaultValue  the attribute default value
   */
  public void setDefaultValue(
    String defaultValue)
  {
    _defaultValue = defaultValue;
  }

  /**
   * Returns the default value of this attribute.
   *
   * @return  the attribute default value
   */
  public String getDefaultValue()
  {
    return _defaultValue;
  }

  /**
   * Sets the alternative class for this property.
   *
   * @param alternateClass  the alternative class
   */
  public void setAlternateClass(
    String alternateClass)
  {
    _alternateClass = alternateClass;
  }

  /**
   * Returns the alternative class for this property.
   *
   * @return  the alternative class
   */
  public String getAlternateClass()
  {
    return _alternateClass;
  }

  /**
   * Sets the method binding signature of this property.
   *
   * @param signature  the method binding signature of this property
   */
  public void setMethodBindingSignature(
    MethodSignatureBean signature)
  {
    _signature = signature;
  }

  /**
   * Returns the method binding signature of this property.
   *
   * @return the method binding signature of this property
   */
  public MethodSignatureBean getMethodBindingSignature()
  {
    return _signature;
  }

  /**
   * Adds an Accessibility (e.g. section 508 compliance) Guideline to this attribute. The
   * accessibility guidelines are used during tag doc generation to give the application
   * developer hints on how to configure the attribute to be accessibility-compliant.
   *
   * @param accessibilityGuideline  the accessibility guideline to add
   */
  public void addAccessibilityGuideline(
    String accessibilityGuideline)
  {
    _accessibilityGuidelines.add(accessibilityGuideline);      
  }

  /**
   * Returns true if this attribute has any accessibility guidelines.
   *
   * @return  true   if this component has any accessibility guidelines,
   *          false  otherwise
   */
  public boolean hasAccessibilityGuidelines()
  {
    return !_accessibilityGuidelines.isEmpty();
  }

  /**
   * Returns an iterator for all accessibility guidelines on this component only.
   *
   * @return  the accessibility guidelines iterator
   */
  public Iterator<String> accessibilityGuidelines()
  {
    return _accessibilityGuidelines.iterator();
  }

  /**
   * Sets the virtual flag of this property.
   *
   * @param virtual  the property required flag
   */
  public void setVirtual(
    boolean virtual)
  {
    _virtual = virtual;
  }

  /**
   * Returns virtual flag of this property.
   *
   * @return  the property virtual flag
   */
  public boolean isVirtual()
  {
    return _virtual;
  }

  public int compareTo(Object o)
  {
    if(o instanceof AttributeBean)
    {
      AttributeBean attr = (AttributeBean)o;
      return _attributeName.compareTo(attr._attributeName);
    }
    else
    {
      return 1;
    }
  }

  private String   _attributeName;
  private String   _attributeClass;
  private String[] _attributeClassParameters;
  private String   _description;
  private String   _defaultValue;
  // FIXME: Alternate type does not seem to support generic types
  private String   _alternateClass;
  private boolean  _virtual;
  private MethodSignatureBean _signature;
  private List<String> _accessibilityGuidelines = new ArrayList<String>();

  static private final Pattern _GENERIC_TYPE = Pattern.compile("([^<]+)<(.+)>");
  static private final String[] _EMPTY_ARRAY = new String[0];
}
