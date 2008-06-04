// WARNING: This file was automatically generated. Do not edit it directly,
//          or you will lose your changes.

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
package javax.faces.component;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 *
 * UIOutput displays a value to the user
 */
@JSFComponent
(defaultRendererType = "javax.faces.Text"
)
public class UIOutput extends UIComponentBase
                      implements ValueHolder
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Output";
  static public final String COMPONENT_TYPE =
    "javax.faces.Output";

  /**
   * Construct an instance of the UIOutput.
   */
  public UIOutput()
  {
    setRendererType("javax.faces.Text");
  }
      public Object getLocalValue()
    {
        return _value;
    }

  // Property: value
  private Object _value;

  /**
   * Gets The initial value of this component.
   *
   * @return  the new value value
   */
  @JSFProperty
  public Object getValue()
  {
    if (_value != null)
    {
      return _value;
    }
    ValueExpression expression = getValueExpression("value");
    if (expression != null)
    {
      return expression.getValue(getFacesContext().getELContext());
    }
    return null;
  }

  /**
   * Sets The initial value of this component.
   * 
   * @param value  the new value value
   */
  public void setValue(Object value)
  {
    this._value = value;
  }

  // Property: converter
  private Converter _converter;

  /**
   * Gets An expression that specifies the Converter for this component.
   *               The value can either be a static value (ID) or an EL expression.
   *               When a static id is specified, an instance of the converter type
   *               registered with that id is used. When this is an EL expression,
   *               the result of evaluating the expression must be an object that
   *               implements the Converter interface.
   *
   * @return  the new converter value
   */
  @JSFProperty
  public Converter getConverter()
  {
    if (_converter != null)
    {
      return _converter;
    }
    ValueExpression expression = getValueExpression("converter");
    if (expression != null)
    {
      return (Converter)expression.getValue(getFacesContext().getELContext());
    }
    return null;
  }

  /**
   * Sets An expression that specifies the Converter for this component.
   *               The value can either be a static value (ID) or an EL expression.
   *               When a static id is specified, an instance of the converter type
   *               registered with that id is used. When this is an EL expression,
   *               the result of evaluating the expression must be an object that
   *               implements the Converter interface.
   * 
   * @param converter  the new converter value
   */
  public void setConverter(Converter converter)
  {
    this._converter = converter;
  }

  @Override
  public Object saveState(FacesContext facesContext)
  {
    Object[] values = new Object[3];
    values[0] = super.saveState(facesContext);
    values[1] = _value;
    values[2] = saveAttachedState(facesContext, _converter);

    return values;
  }

  @Override
  public void restoreState(FacesContext facesContext, Object state)
  {
    Object[] values = (Object[])state;
    super.restoreState(facesContext,values[0]);
    _value = values[1];
    _converter = (Converter) restoreAttachedState(facesContext, values[2]);
  }

  @Override
  public String getFamily()
  {
    return COMPONENT_FAMILY;
  }
}
