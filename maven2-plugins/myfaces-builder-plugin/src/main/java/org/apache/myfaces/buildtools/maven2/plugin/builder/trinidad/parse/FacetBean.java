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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * FacetBean is a Java representation of the faces-config component or
 * renderer facet XML element.
 */
public class FacetBean extends ObjectBean
{
  /**
   * Sets the facet name for this facet.
   *
   * @param facetName  the facet name
   */
  public void setFacetName(
    String facetName)
  {
    _facetName = facetName;
  }

  /**
   * Returns the facet name for this facet.
   *
   * @return  the facet name
   */
  public String getFacetName()
  {
    return _facetName;
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
   * Sets the required flag of this facet.
   *
   * @param required  the facet required flag
   */
  public void setRequired(
    boolean required)
  {
    _required = required;
  }

  /**
   * Returns required flag of this facet.
   *
   * @return  the facet required flag
   */
  public boolean isRequired()
  {
    return _required;
  }

  /**
   * If the facet should be hidden from documentation
   * @return If the facet should be hidden
   */
  public boolean isHidden()
  {
    return _hidden;
  }  

  /**
   * Set if this facet should be hidden from documentation
   * @param hidden If the facet should be hidden
   */
  public void setHidden(boolean hidden)
  {
    this._hidden = hidden;
  }
 
  /**
   * Adds an Accessibility (e.g. section 508 compliance) Guideline to this facet. The
   * accessibility guidelines are used during tag doc generation to give the application
   * developer hints on how to configure the facet to be accessibility-compliant.
   *
   * @param accGuidelines  the accessibility guideline to add
   */
  public void addAccessibilityGuideline(
    String accessibilityGuideline)
  {
    _accessibilityGuidelines.add(accessibilityGuideline);       
  }

  /**
   * Returns true if this component has any accessibility guidelines.
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
   * Parses the string of allowed child class names into a String array
   * using space as the separator between values.
   * In the component metadata file, the allowed child components
   * are denoted with allowed-child-components markup.  As an example,
   * oracle.adf.RichMenu is an allowed child component of 
   * af:panelCollection's (oracle.adf.view.rich.component.rich.output.RichPanelCollection) 
   * menu facet. The allowed child components of one component are matched with the
   * component type attribute of other components to determine which child tags
   * are allowed in the component hierarchy.
   *
   * @param allowedChildComponents  a space delimited string of allowed child component class names
   */
  public void parseAllowedChildComponents(
    String allowedChildComponents)
  {
    setAllowedChildComponents(allowedChildComponents.split(" "));
  }

  /**
   * Sets the possible values for this property.
   * In the component metadata file, the allowed child components
   * are denoted with allowed-child-components markup.  As an example,
   * oracle.adf.RichMenu is an allowed child component of 
   * af:panelCollection's (oracle.adf.view.rich.component.rich.output.RichPanelCollection)
   * menu facet. The allowed child components of one component are matched with the
   * component type attribute of other components to determine which child tags
   * are allowed in the component hierarchy.
   *
   * @param allowedChildComponents  a string array of the allowed child component class names
   */
  public void setAllowedChildComponents(
    String[] allowedChildComponents)
  {
    _allowedChildComponents = Arrays.asList(allowedChildComponents);
  }

  /**
   * Returns the allowed child components for this facet.
   * In the component metadata file, the allowed child components
   * are denoted with allowed-child-components markup.  As an example,
   * oracle.adf.RichMenu is an allowed child component of 
   * af:panelCollection's (oracle.adf.view.rich.component.rich.output.RichPanelCollection)
   * menu facet. The allowed child components of one component are matched with the
   * component type attribute of other components to determine which child tags
   * are allowed in the component hierarchy.
   * 
   * @return  an iterator of allowed child component class name strings
   */
  public Iterator<String> allowedChildComponents()
  {
    return (_allowedChildComponents.iterator());
  }  

  /**
   * Returns true if this component has any allowed child components.
   *
   * @return  true   if this component has any allowed child components,
   *          false  otherwise
   */
  public boolean hasAllowedChildComponents()
  {
    return (!_allowedChildComponents.isEmpty());
  }

  private String       _description;
  private String       _facetName;
  private boolean      _required;
  private boolean      _hidden;
  private List<String> _accessibilityGuidelines = new ArrayList<String>();
  private List<String> _allowedChildComponents = new ArrayList<String>();
}
