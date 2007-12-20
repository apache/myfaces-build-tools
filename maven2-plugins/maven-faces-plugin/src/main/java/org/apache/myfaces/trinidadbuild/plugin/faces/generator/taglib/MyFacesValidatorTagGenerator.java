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

import org.apache.maven.plugin.logging.Log;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ValidatorBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

import java.util.Set;
import java.util.TreeSet;

public class MyFacesValidatorTagGenerator extends AbstractValidatorTagGenerator {

  public MyFacesValidatorTagGenerator(boolean is12, String licenseHeader, Log log) {
    super(is12, licenseHeader, log);
  }

  protected Set createImports(ValidatorBean validator) {
    Set imports = new TreeSet();

    if (is12())
    {
      imports.add("javax.faces.webapp.ValidatorELTag");
      imports.add("javax.faces.context.FacesContext");
      imports.add("javax.faces.application.Application");
    }
    else
    {
      imports.add("javax.faces.webapp.ValidatorTag");
    }
    imports.add("javax.servlet.jsp.JspException");
    imports.add(validator.getValidatorClass());

    imports.add("javax.faces.validator.Validator");

    if (is12())
    {
      imports.add("javax.el.ValueExpression");
    }
    else
    {
      imports.add("javax.faces.el.ValueBinding");
    }
    imports.add("org.apache.myfaces.commons.util.TagUtils");


    addImportsFromPropertes(validator, imports);
    return imports;
  }

  protected void writeSetProperty(
    PrettyWriter out,
    PropertyBean property)
  {
    String propName = property.getPropertyName();
    String propFullClass = property.getPropertyClass();
    String propClass = Util.getClassFromFullClass(propFullClass);
    String propVar = "_" + Util.getVariableFromName(propName);
    out.println("if (" + propVar + " != null)");
    out.println("{");
    out.indent();
    if (is12())
    {
      out.println("if (!" + propVar + ".isLiteralText())");
      out.println("{");
      out.indent();
      out.println("validator.setValueExpression(\"" + propName + "\", " +
                  propVar + ");");
      out.unindent();
      out.println("}");
      String propType = resolveType(propFullClass);
      if (propType != null)
      {
        out.println("else");
        out.println("{");
        out.indent();
        if ("StringArray".equals(propType))
        {
          out.println("try");
          out.println("{");
        }

        out.println(propClass + " value = TagUtils.get" + propType + "(" + propVar + ".getValue(null));");
        String setMethod = Util.getPrefixedPropertyName("set", propName);
        out.println("validator." + setMethod + "(value);");
        if ("StringArray".equals(propType))
        {
          out.println("}");
          out.println("catch (ParseException pe)");
          out.println("{");
          out.indent();
          out.println("throw new JspException(");
          out.println("  pe.getMessage() + \": \" + \"Position \" + pe.getErrorOffset());");
          out.unindent();
          out.println("}");
        }
        out.unindent();
        out.println("}");
      }
    }
    else
    {
      out.println("if (TagUtils.isValueReference(" + propVar + "))");
      out.println("{");
      out.indent();
      out.println("ValueBinding vb = TagUtils.getValueBinding(" + propVar + ");");
      out.println("validator.setValueBinding(\"" + propName + "\", vb);");
      out.unindent();
      out.println("}");
      String propType = resolveType(propFullClass);
      if (propType != null)
      {
        out.println("else");
        out.println("{");
        out.indent();
        if ("StringArray".equals(propType))
        {
          out.println("try");
          out.println("{");
        }
        out.println(propClass + " value = TagUtils.get" + propType + "(" + propVar + ");");
        String setMethod = Util.getPrefixedPropertyName("set", propName);
        out.println("validator." + setMethod + "(value);");
        if ("StringArray".equals(propType))
        {
          out.println("}");
          out.println("catch (ParseException pe)");
          out.println("{");
          out.indent();
          out.println("throw new JspException(");
          out.println("  pe.getMessage() + \": \" + \"Position \" + pe.getErrorOffset());");
          out.unindent();
          out.println("}");
        }
        out.unindent();
        out.println("}");
      }
    }

    out.unindent();
    out.println("}");
  }
}
