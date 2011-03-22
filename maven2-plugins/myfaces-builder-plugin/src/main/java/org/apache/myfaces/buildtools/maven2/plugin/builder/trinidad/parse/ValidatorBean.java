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

import java.lang.reflect.Modifier;
import java.util.logging.Logger;

/**
 * ValidatorBean is a Java representation of the faces-config validator
 * XML element.
 */
public class ValidatorBean extends AbstractTagBean {

  /**
   * Sets the validator identifer for this component.
   *
   * @param validatorId  validator identifer
   */
  public void setValidatorId(
    String validatorId)
  {
    _validatorId = validatorId;
  }
  
  /**
   * Sets the "root" validator Id representing the ID defined in faces-config
   * The root id will be used while the tag definition is written out,
   * while the regular Id is used for the lookup of existing validators.
   * This allows us to define new tags for the existing validator IDs
   * @param id root validator id
   */
  public void setRootValidatorId(String id)
  {
    _rootValidatorId = id;
  }
  
  /**
   * Returns the root validator id
   * @return root validator id
   */
  public String getRootValidatorId()
  {
    return _rootValidatorId;
  }

  /**
   * Returns true if the validator identifier is specified, otherwise false.
   *
   * @return  true if the validator identifier is specified,
   *          otherwise false.
   */
  public boolean hasValidatorId()
  {
    return (_validatorId != null);
  }

  /**
   * Returns the validator identifier for this component.
   *
   * @return  the validator identifier
   */
  public String getValidatorId()
  {
    return _validatorId;
  }

  /**
   * Sets the validator class for this component.
   *
   * @param validatorClass  the validator class
   */
  public void setValidatorClass(
    String validatorClass)
  {
    _validatorClass = validatorClass;
  }

  /**
   * Returns the validator class for this component.
   *
   * @return  the validator class
   */
  public String getValidatorClass()
  {
    return _validatorClass;
  }

  /**
   * Sets the validator super class for this component.
   *
   * @param validatorSuperClass  the validator super class
   */
  public void setValidatorSuperClass(
    String validatorSuperClass)
  {
    _validatorSuperClass = validatorSuperClass;
  }

  /**
   * Returns the validator super class for this component.
   *
   * @return  the validator super class
   */
  public String getValidatorSuperClass()
  {
    return _validatorSuperClass;
  }

  /**
   * Adds a Java Language class modifier to the validator class.
   *
   * @param modifier  the modifier to be added
   */
  public void addValidatorClassModifier(
    int modifier)
  {
    _validatorClassModifiers |= modifier;
  }

  /**
   * Returns the Java Language class modifiers for the validator class.
   * By default, these modifiers include Modifier.PUBLIC.
   *
   * @return  the Java Language class modifiers for the validator class
   */
  public int getValidatorClassModifiers()
  {
    int modifiers = _validatorClassModifiers;

    if (!Modifier.isPrivate(modifiers) &&
        !Modifier.isProtected(modifiers) &&
        !Modifier.isPublic(modifiers))
    {
      modifiers |= Modifier.PUBLIC;
    }

    return modifiers;
  }

  public void parseValidatorClassModifier(
    String modifier)
  {
    addValidatorClassModifier(_parseModifier(modifier));
  }

  private String  _validatorId;
  private String  _rootValidatorId;
  private String  _validatorClass;
  private String  _validatorSuperClass;
  private int     _validatorClassModifiers;


  static private final Logger _LOG = Logger.getLogger(ValidatorBean.class.getName());
}
