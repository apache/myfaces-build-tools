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
package org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib;

import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.AbstractTagBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.GeneratorHelper;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public abstract class AbstractTagGenerator {
  protected Log _log;
  protected boolean _is12;
  protected String _licenseHeader;
  static final private String _AUTO_GENERATE_WARNING =
"// WARNING: This file was automatically generated. Do not edit it directly,\n"+
"//          or you will lose your changes.\n\n";

  public AbstractTagGenerator(boolean is12, String licenseHeader, Log log) {
    this._log = log;
    this._licenseHeader = licenseHeader;
    this._is12 = is12;
  }

  protected void writePreamble(
    PrettyWriter out)
  {
    out.write(_AUTO_GENERATE_WARNING);
    out.write(_licenseHeader);
  }

  protected Log getLog() {
    return _log;
  }

  protected boolean is12() {
    return _is12;
  }

  protected File createFile(File generatedSourceDirectory, String fullClassName) {
     getLog().debug("Generating " + fullClassName);
     String sourcePath = Util.convertClassToSourcePath(fullClassName, ".java");
     File targetFile = new File(generatedSourceDirectory, sourcePath);

     targetFile.getParentFile().mkdirs();
     return targetFile;
   }

  protected void writeConstructor(
      PrettyWriter  out,
      AbstractTagBean abstractTag) throws IOException
    {
      String fullClassName = abstractTag.getTagClass();
      String className = Util.getClassFromFullClass(fullClassName);
      out.println();
      out.println("/**");
      // TODO: restore this correctly phrased comment (tense vs. command)
      //out.println(" * Constructs an instance of " + className + ".");
      out.println(" * Construct an instance of the " + className + ".");
      out.println(" */");
      out.println("public " + className + "()");
      out.println("{");
      out.println("}");
    }

  protected void writePropertyMethods(
      PrettyWriter  out,
      AbstractTagBean abstractTag) throws IOException
    {
      Iterator properties = abstractTag.properties();
      properties = new FilteredIterator(properties, new TagAttributeFilter());
      while (properties.hasNext())
      {
        PropertyBean property = (PropertyBean)properties.next();
        out.println();
        writePropertyMember(out, property);
        writePropertySet(out, property);
      }
    }
    private void writePropertyMember(
   PrettyWriter  out,
   PropertyBean  property) throws IOException
  {
    String propName = property.getPropertyName();
    String propVar = "_" + Util.getVariableFromName(propName);
    String jspPropType = getJspPropertyType(property);

    out.println("private " + jspPropType + " " + propVar + ";");
  }

  private void writePropertySet(
   PrettyWriter  out,
   PropertyBean  property) throws IOException
  {
    String propName = property.getPropertyName();
    String propVar = Util.getVariableFromName(propName);
    String setMethod = Util.getPrefixedPropertyName("set", propName);
    String jspPropType = getJspPropertyType(property);

    // TODO: restore coding standards, and make final
    out.println("public void " + setMethod + "(" + jspPropType + " " + propVar + ")");
    out.println("{");
    out.indent();
    out.println("_" + propVar + " = " + propVar + ";");
    out.unindent();
    out.println("}");
  }

  protected void writeRelease(
    PrettyWriter  out,
    AbstractTagBean abstractTag) throws IOException
  {
    Iterator properties = abstractTag.properties();
    properties = new FilteredIterator(properties, new TagAttributeFilter());
    if (properties.hasNext())
    {
      out.println();
      if (is12()) {
        out.println("@Override");
      }
      out.println("public void release()");
      out.println("{");
      out.indent();
      out.println("super.release();");
      while (properties.hasNext())
      {
        PropertyBean property = (PropertyBean)properties.next();
        String propName = property.getPropertyName();
        String propVar = "_" + Util.getVariableFromName(propName);
        out.println(propVar + " = null;");
      }
      out.unindent();
      out.println("}");
    }
  }

  private String getJspPropertyType(PropertyBean property)
  {
    if (property.isMethodExpression())
      return "MethodExpression";

    if (is12() && property.isMethodBinding())
      return "MethodExpression";

    if (is12() && !property.isLiteralOnly())
      return "ValueExpression";
    return "String";
  }

  protected void writeEnd(PrettyWriter out) {
      out.unindent();
      out.println("}");
      out.close();
    }

  protected void writeImports(
    PrettyWriter   out,
    AbstractTagBean  abstractTagBean, Set imports)
  {

    // do not import implicit!
    imports.removeAll(Util.PRIMITIVE_TYPES);

    String packageName = Util.getPackageFromFullClass(abstractTagBean.getTagClass());
    GeneratorHelper.writeImports(out, packageName, imports);
  }

  protected final void writeHeader(PrettyWriter out, AbstractTagBean converter, Set imports) {
    String packageName = Util.getPackageFromFullClass(converter.getTagClass());
    // header/copyright
    writePreamble(out);

    // package
    out.println("package " + packageName + ";");

    out.println();
    writeImports(out, converter, imports);

    out.println("/**");
    // TODO: remove this blank line.
    out.println();
    out.println(" * Auto-generated tag class.");
    out.println(" */");
  }

  protected void addImportsFromPropertes(AbstractTagBean abstractTagBean, Set imports) {
    Iterator properties = abstractTagBean.properties();
    properties = new FilteredIterator(properties, new TagAttributeFilter());

    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean)properties.next();

      String propertyClass = property.getPropertyClass();
      if (propertyClass != null)
        imports.add(propertyClass);

      if ("java.lang.String[]".equals(propertyClass))
      {
        imports.add("java.text.ParseException");
      }
    }
  }

  protected String resolveDateType(String className, boolean useMaxTime)
  {
    String type = (String)_RESOLVABLE_TYPES.get(className);
    return useMaxTime ? type + "WithMaxTime" : type;
  }

  protected String resolveType(String className)
  {
    return (String)_RESOLVABLE_TYPES.get(className);
  }

  // TODO: for everything but Locale, String[], Date, and TimeZone,
  // in JSF 1.2 we should already be going through coercion, and
  // not need any of the "TagUtils" functions
  private Map _createResolvableTypes()
  {
    Map resolvableTypes = new HashMap();

    resolvableTypes.put("boolean", "Boolean");
    resolvableTypes.put("char", "Character");
    resolvableTypes.put("java.util.Date", "Date");
    resolvableTypes.put("int", "Integer");
    resolvableTypes.put("float", "Float");
    resolvableTypes.put("double", "Double");
    resolvableTypes.put("java.util.Locale", "Locale");
    resolvableTypes.put("long", "Long");
    resolvableTypes.put("java.lang.String", "String");
    resolvableTypes.put("java.lang.String[]", "StringArray");
    resolvableTypes.put("java.util.TimeZone", "TimeZone");

    return Collections.unmodifiableMap(resolvableTypes);
  }

  final private Map _RESOLVABLE_TYPES = _createResolvableTypes();

}
