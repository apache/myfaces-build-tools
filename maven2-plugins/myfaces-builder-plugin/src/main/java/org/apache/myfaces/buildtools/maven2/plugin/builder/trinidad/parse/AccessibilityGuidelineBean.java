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

/**
 * AccessibilityGuidelineBean is a Java representation of the faces-config mfp:accessibilityGuidelines
 * XML element.
 * An example of how the AccessibilityGuidelineBean would be represented as an XML element is:
 * <mfp:accessibilityGuideline>
 *   <mfp:type>required</mfp:type>
 *   <mfp:description>
 *     Title attribute must be set in order for a screenreader user to identify the purpose of the popup dialog.
 *   </mfp:description>
 * </mfp:accessibilityGuideline>
 */
public class AccessibilityGuidelineBean extends ObjectBean
{
  /**
   * Get Description Text. The description text for the accessibility guideline.
   * 
   * @return Accessibility Guideline Description Text
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set accessibility guideline Description Text.  
   *
   * @param description   Accessibility Guideline Description Text.
   */
  public void setDescription( String description )
  {
    _description = description;
  }

  /**
   * Returns the type of the Accessibility Guideline (e.g. required, suggested, discouraged).
   *
   * @return type of Accessibility Guideline
   */
  public String getType()
  {
    return _type;
  }

  /**
   * Set if element is required to meet Accessibility Guidelines
   *
   * @param required  whether or not the element is required to meet accessibility guidelines
   */
  public void setType( String type )
  {
    _type = type;
  }

  /**
   * Returns AccessibilityGuideline hashmap key.
   *
   * @return AccessibilityGuideline hashmap key
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set hashmap key for this accessibility guideline.
   *
   * @param key Set key for this accessibility guideline put in 
   *        ComponentBean _accessibilityGuidelines hashmap.
   */
  protected void setKey( String key )
  {
    _key = key;
  }

  private String _description  = null;
  private String _type         = null;
  private String _key          = null;
}
