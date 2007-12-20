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

import java.util.LinkedList;
import java.util.List;

/**
 * MethodSignatureBean is a Java representation of the faces-config component
 * property-extension method-signature XML element.
 */
public class MethodSignatureBean extends ObjectBean
{
  /**
   * Creates a new MethodSignatureBean.
   */
  public MethodSignatureBean()
  {
    _parameterTypes = new LinkedList();
  }

  /**
   * Adds a new parameter type to this method signature.
   *
   * @param parameterType  the parameter type
   */
  public void addParameterType(
    String parameterType)
  {
    _parameterTypes.add(parameterType);
  }

  /**
   * Returns the list of parameter types as an array.
   *
   * @return  the parameter type list
   */
  public String[] getParameterTypes()
  {
    return (String[])_parameterTypes.toArray(new String[0]);
  }

  /**
   * Sets the return type of this method signature.
   *
   * @param returnType  the method signature return type
   */
  public void setReturnType(
    String returnType)
  {
    _returnType = returnType;
  }

  /**
   * Returns the return type of this method signature.
   *
   * @return  the method signature return type
   */
  public String getReturnType()
  {
    return _returnType;
  }

  private String _returnType;
  private List   _parameterTypes;
}
