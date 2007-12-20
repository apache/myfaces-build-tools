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

  private String  _description;
  private String  _facetName;
  private boolean _required;
}
