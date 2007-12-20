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

import java.lang.reflect.Modifier;
import java.util.logging.Logger;

/**
 * ConverterBean is a Java representation of the faces-config converter
 * XML element.
 */
public class ConverterBean extends AbstractTagBean {

  /**
   * Sets the converter identifer for this component.
   *
   * @param converterId  converter identifer
   */
  public void setConverterId(
    String converterId)
  {
    _converterId = converterId;
  }

  /**
   * Returns true if the converter identifier is specified, otherwise false.
   *
   * @return  true if the converter identifier is specified,
   *          otherwise false.
   */
  public boolean hasConverterId()
  {
    return (_converterId != null);
  }

  /**
   * Returns the converter identifier for this component.
   *
   * @return  the converter identifier
   */
  public String getConverterId()
  {
    return _converterId;
  }

  /**
   * Sets the converter class for this component.
   *
   * @param converterClass  the converter class
   */
  public void setConverterClass(
    String converterClass)
  {
    _converterClass = converterClass;
  }

  /**
   * Returns the converter class for this component.
   *
   * @return  the converter class
   */
  public String getConverterClass()
  {
    return _converterClass;
  }

  /**
   * Sets the converter super class for this component.
   *
   * @param converterSuperClass  the converter super class
   */
  public void setConverterSuperClass(
    String converterSuperClass)
  {
    _converterSuperClass = converterSuperClass;
  }

  /**
   * Returns the converter super class for this component.
   *
   * @return  the converter super class
   */
  public String getConverterSuperClass()
  {
    return _converterSuperClass;
  }

  /**
   * Adds a Java Language class modifier to the converter class.
   *
   * @param modifier  the modifier to be added
   */
  public void addConverterClassModifier(
    int modifier)
  {
    _converterClassModifiers |= modifier;
  }

  /**
   * Returns the Java Language class modifiers for the converter class.
   * By default, these modifiers include Modifier.PUBLIC.
   *
   * @return  the Java Language class modifiers for the converter class
   */
  public int getConverterClassModifiers()
  {
    int modifiers = _converterClassModifiers;

    if (!Modifier.isPrivate(modifiers) &&
        !Modifier.isProtected(modifiers) &&
        !Modifier.isPublic(modifiers))
    {
      modifiers |= Modifier.PUBLIC;
    }

    return modifiers;
  }

  public void parseConverterClassModifier(
    String modifier)
  {
    addConverterClassModifier(_parseModifier(modifier));
  }



  private String  _converterId;
  private String  _converterClass;
  private String  _converterSuperClass;
  private int     _converterClassModifiers;


  static private final Logger _LOG = Logger.getLogger(ConverterBean.class.getName());
}
