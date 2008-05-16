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
package org.apache.myfaces.custom.suggest;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 *
 * <h4>Events:</h4>
 * <table border="1" width="100%" cellpadding="3" summary="">
 * <tr bgcolor="#CCCCFF" class="TableHeadingColor">
 * <th align="left">Type</th>
 * <th align="left">Phases</th>
 * <th align="left">Description</th>
 * </tr>
 * </table>
 * 
 * @JSFComponent
 *   name = "s:inputSuggest"
 *   tagClass = "org.apache.myfaces.custom.suggest.InputSuggestTag"
 */
public class InputSuggest extends HtmlInputText
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Input";
  static public final String COMPONENT_TYPE =
    "org.apache.myfaces.InputSuggest";

  /**
   * Construct an instance of the InputSuggest.
   */
  public InputSuggest()
  {
    setRendererType("org.apache.myfaces.InputSuggest");
  }

  // Property: forceId
  private Boolean _forceId = Boolean.valueOf(false);

  /**
   * Gets If true, this component will force the use of the specified id when rendering.
   *
   * @return  the new forceId value
   */
  public Boolean getForceId()
  {
    return _forceId;
  }

  /**
   * Sets If true, this component will force the use of the specified id when rendering.
   * 
   * @param forceId  the new forceId value
   */
  public void setForceId(Boolean forceId)
  {
    this._forceId = forceId;
  }

  // Property: forceIdIndex
  private Boolean _forceIdIndex = Boolean.valueOf(true);

  /**
   * Gets If false, this component will not append a '[n]' suffix (where 'n' is the row index) to components
   *                 that are contained within a "list."  This value will be true by default and the value will be ignored if
   *                 the value of forceId is false (or not specified.)
   *
   * @return  the new forceIdIndex value
   */
  public Boolean getForceIdIndex()
  {
    return _forceIdIndex;
  }

  /**
   * Sets If false, this component will not append a '[n]' suffix (where 'n' is the row index) to components
   *                 that are contained within a "list."  This value will be true by default and the value will be ignored if
   *                 the value of forceId is false (or not specified.)
   * 
   * @param forceIdIndex  the new forceIdIndex value
   */
  public void setForceIdIndex(Boolean forceIdIndex)
  {
    this._forceIdIndex = forceIdIndex;
  }

  // Property: javascriptLocation
  private String _javascriptLocation;

  /**
   * Gets An alternate location to find javascript resources. If no values is specified, javascript will be loaded from the resources directory using AddResource and ExtensionsFilter.
   *
   * @return  the new javascriptLocation value
   */
  public String getJavascriptLocation()
  {
    if (_javascriptLocation != null)
    {
      return _javascriptLocation;
    }
    ValueBinding vb = getValueBinding("javascriptLocation");
    if (vb != null)
    {
      return (String)vb.getValue(getFacesContext());
    }
    return null;
  }

  /**
   * Sets An alternate location to find javascript resources. If no values is specified, javascript will be loaded from the resources directory using AddResource and ExtensionsFilter.
   * 
   * @param javascriptLocation  the new javascriptLocation value
   */
  public void setJavascriptLocation(String javascriptLocation)
  {
    this._javascriptLocation = javascriptLocation;
  }

  // Property: imageLocation
  private String _imageLocation;

  /**
   * Gets An alternate location to find image resources. If no values is specified, images will be loaded from the resources directory using AddResource and ExtensionsFilter.
   *
   * @return  the new imageLocation value
   */
  public String getImageLocation()
  {
    if (_imageLocation != null)
    {
      return _imageLocation;
    }
    ValueBinding vb = getValueBinding("imageLocation");
    if (vb != null)
    {
      return (String)vb.getValue(getFacesContext());
    }
    return null;
  }

  /**
   * Sets An alternate location to find image resources. If no values is specified, images will be loaded from the resources directory using AddResource and ExtensionsFilter.
   * 
   * @param imageLocation  the new imageLocation value
   */
  public void setImageLocation(String imageLocation)
  {
    this._imageLocation = imageLocation;
  }

  // Property: styleLocation
  private String _styleLocation;

  /**
   * Gets An alternate location to find stylesheet resources. If no values is specified, stylesheets will be loaded from the resources directory using AddResource and ExtensionsFilter.
   *
   * @return  the new styleLocation value
   */
  public String getStyleLocation()
  {
    if (_styleLocation != null)
    {
      return _styleLocation;
    }
    ValueBinding vb = getValueBinding("styleLocation");
    if (vb != null)
    {
      return (String)vb.getValue(getFacesContext());
    }
    return null;
  }

  /**
   * Sets An alternate location to find stylesheet resources. If no values is specified, stylesheets will be loaded from the resources directory using AddResource and ExtensionsFilter.
   * 
   * @param styleLocation  the new styleLocation value
   */
  public void setStyleLocation(String styleLocation)
  {
    this._styleLocation = styleLocation;
  }

  public Object saveState(FacesContext facesContext)
  {
    Object[] values = new Object[6];
    values[0] = super.saveState(facesContext);
    values[1] = _forceId;
    values[2] = _forceIdIndex;
    values[3] = _javascriptLocation;
    values[4] = _imageLocation;
    values[5] = _styleLocation;

    return values;
  }

  public void restoreState(FacesContext facesContext, Object state)
  {
    Object[] values = (Object[])state;
    super.restoreState(facesContext,values[0]);
    _forceId = (Boolean)values[1];
    _forceIdIndex = (Boolean)values[2];
    _javascriptLocation = (String)values[3];
    _imageLocation = (String)values[4];
    _styleLocation = (String)values[5];
  }

  public String getFamily()
  {
    return COMPONENT_FAMILY;
  }
}
