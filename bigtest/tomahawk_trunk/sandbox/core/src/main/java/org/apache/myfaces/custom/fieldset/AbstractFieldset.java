/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.custom.fieldset;

import org.apache.myfaces.custom.htmlTag.HtmlTag;

/**
 * Renders an HTML Fieldset
 * 
 * @JSFComponent
 *   name = "s:fieldset"
 *   class = "org.apache.myfaces.custom.fieldset.Fieldset"
 *   superClass = "org.apache.myfaces.custom.fieldset.AbstractFieldset"
 *   tagClass = "org.apache.myfaces.custom.fieldset.FieldsetTag"
 *   
 * @JSFJspProperty name="value" tagExcluded = "true"
 * @JSFJspProperty name="converter" tagExcluded = "true"
 * 
 * @author svieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFieldset extends HtmlTag {
  public static final String COMPONENT_TYPE = "org.apache.myfaces.Fieldset";
  public static final String COMPONENT_FAMILY = "javax.faces.Output";
  private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.FieldsetRenderer";

  /**
   * @JSFProperty
   *   tagExcluded = "true"
   */
  public Object getValue() {
	return "fieldset";
  }
  
  /**
   * The fieldset's legend.
   * 
   * @JSFProperty
   */
  public abstract String getLegend();
  
}