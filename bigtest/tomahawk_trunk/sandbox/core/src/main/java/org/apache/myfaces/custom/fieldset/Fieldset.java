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

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.htmlTag.HtmlTag;

/**
 * @JSFComponent
 *   name = "s:fieldset"
 *   tagClass = "org.apache.myfaces.custom.fieldset.FieldsetTag"
 *   
 * @author svieujot (latest modification by $Author$)
 * @version $Revision$ $Date: 2005-06-09 02:27:56 -0400 (Thu, 09 Jun 2005) $
 */
public class Fieldset extends HtmlTag {
  public static final String COMPONENT_TYPE = "org.apache.myfaces.Fieldset";
  public static final String COMPONENT_FAMILY = "javax.faces.Output";
  private static final String DEFAULT_RENDERER_TYPE = FieldsetRenderer.RENDERER_TYPE;

  private String legend = null;
  
  public Fieldset(){
      setRendererType(DEFAULT_RENDERER_TYPE);
  }
  
  public Object getValue() {
	return "fieldset";
  }
  
  /**
   * @JSFProperty
   */
  public String getLegend(){
      if (legend != null)
          return legend;
      ValueBinding vb = getValueBinding("legend");
      return vb != null ? (String) vb.getValue(getFacesContext()) : null;
  }

  public void setLegend(String legend){
      this.legend = legend;
  }

  public void restoreState(FacesContext context, Object state){
      Object values[] = (Object[]) state;
      super.restoreState(context, values[0]);
      legend = (String) values[1];
  }

  public Object saveState(FacesContext context){
      Object values[] = new Object[2];
      values[0] = super.saveState(context);
      values[1] = legend;
      return values;
  }
}