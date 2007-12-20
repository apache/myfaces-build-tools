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
package org.apache.myfaces.trinidadbuild.plugin.faces.parse.converters;

import javax.xml.namespace.QName;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public final class QNameConverter implements Converter
{
  public Object convert(
    Class  type,
    Object value)
  {
    if (value == null)
      throw new ConversionException("Missing value");

    if (value instanceof QName)
      return value;

    try
    {
      return QName.valueOf(value.toString());
    }
    catch (Exception e)
    {
      throw new ConversionException(e);
    }
  }

  static public void register()
  {
    ConvertUtils.register(new QNameConverter(), QName.class);
  }
}
