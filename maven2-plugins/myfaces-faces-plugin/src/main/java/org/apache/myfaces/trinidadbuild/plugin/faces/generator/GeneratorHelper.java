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
package org.apache.myfaces.trinidadbuild.plugin.faces.generator;

import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Filter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

import java.util.Iterator;
import java.util.Set;

/**
 * TODO: comment this!
 *
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GeneratorHelper
{
  private GeneratorHelper()
  {
  }

  public static boolean isConverter(
      String propClass)
  {
    return ("javax.faces.convert.Converter".equals(propClass));
  }

  public static boolean isKeyStroke(
      String propClass)
  {
    return ("javax.swing.KeyStroke".equals(propClass));
  }

  public static boolean isAWTKeyStroke(
      String propClass)
  {
    return ("java.awt.AWTKeyStroke".equals(propClass));
  }

  public static boolean isNumber(
      String propClass)
  {
    return ("java.lang.Number".equals(propClass));
  }

  public static boolean isColorList(
      String propClass,
      String[] propClassParams)
  {
    return ("java.util.List".equals(propClass) &&
        propClassParams.length == 1 &&
        "java.awt.Color".equals(propClassParams[0]));
  }

  public static boolean isColor(
      String propClass)
  {
    return ("java.awt.Color".equals(propClass));
  }  

  public static boolean isAction(PropertyBean property)
  {
    return (property.getClass().equals("javax.el.MethodExpression")
        && property.getJspPropertyName().equals("action"));
  }

  public static boolean isActionListener(PropertyBean property, boolean is12)
  {
    return (property.getJspPropertyName().equals("actionListener") &&
        ((property.isMethodExpression()) || (is12 && property.isMethodBinding())));
  }

  public static boolean isValueChangeListener(PropertyBean property, boolean is12)
  {
    return (property.getJspPropertyName().equals("valueChangeListener") &&
        ((property.isMethodExpression()) || (is12 && property.isMethodBinding())));
  }

  public static boolean isValidator(PropertyBean property, boolean is12)
  {
    return (property.getJspPropertyName().equals("validator")) &&
        ((property.isMethodExpression()) || (is12 && property.isMethodBinding()));
  }

  static public void writeImports(
      PrettyWriter out,
      String packageName,
      Set imports)
  {
    Iterator iterator = imports.iterator();
    iterator = new FilteredIterator(iterator,
        new PackageImportsFilter(packageName));
    while (iterator.hasNext())
    {
      String className = (String) iterator.next();
      out.println("import " + className + ";");
    }

    out.println();
  }

  public static class PackageImportsFilter implements Filter
  {
    public PackageImportsFilter(
        String packageName)
    {
      _packageName = packageName;
    }

    public boolean accept(
        Object object)
    {
      String className = (String) object;
      String packageName = Util.getPackageFromFullClass(className);
      return (!packageName.equals(_packageName) &&
          !packageName.equals("java.lang"));
    }

    private final String _packageName;
  }

  public static String getJspPropertyType(PropertyBean property, boolean is12)
  {
    if (property.isMethodExpression())
      return "MethodExpression";

    if (is12 && property.isMethodBinding())
      return "MethodExpression";

    if (is12)
    {
      if (!property.isLiteralOnly())
        return "ValueExpression";
      else
        return property.getPropertyClass();
    }

    return "String";
  }
}
