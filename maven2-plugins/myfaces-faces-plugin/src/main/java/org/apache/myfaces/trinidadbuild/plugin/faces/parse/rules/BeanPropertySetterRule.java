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
package org.apache.myfaces.trinidadbuild.plugin.faces.parse.rules;

import java.beans.PropertyDescriptor;

import javax.xml.namespace.QName;

import org.apache.myfaces.trinidadbuild.plugin.faces.parse.converters.QNameConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;


/**
 * Extends Apache Commons Digester BeanPropertySetterRule to add QName support.
 *
 * A QName conveter is not sufficient because it requires contextual
 * knowledge of the current prefix-to-namespace mappings.
 */
public class BeanPropertySetterRule
       extends org.apache.commons.digester.BeanPropertySetterRule
{
  public BeanPropertySetterRule(
    String propertyName)
  {
    super(propertyName);
  }

  public void end(
    String namespace,
    String name) throws Exception
  {
    Object top = digester.peek();

    PropertyDescriptor descriptor =
          PropertyUtils.getPropertyDescriptor(top, propertyName);

    if (descriptor == null)
    {
      throw new NoSuchMethodException("Missing bean property \"" +
                                      propertyName + "\"");
    }

    Class propertyType = descriptor.getPropertyType();

    if (QName.class.equals(propertyType))
    {
      int colon = bodyText.indexOf(':');
      if (colon != -1)
      {
        String namespaceURI = digester.findNamespaceURI(bodyText.substring(0, colon));
        bodyText = "{" + namespaceURI + "}" + bodyText.substring(colon + 1);
      }
      else if (bodyText.indexOf('{') == -1)
      {
        String namespaceURI = digester.findNamespaceURI("");
        bodyText = "{" + namespaceURI + "}" + bodyText.substring(colon + 1);
      }
      BeanUtils.setProperty(top, propertyName, bodyText);
    }
    else
    {
      super.end(namespace, name);
    }
  }

  static
  {
    QNameConverter.register();
  }
}
