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
package org.apache.myfaces.trinidadbuild.plugin.faces.generator.component;

import org.apache.maven.plugin.logging.Log;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.GeneratorHelper;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ComponentBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Component generator for MyFaces
 *
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MyFacesComponentGenerator extends AbstractComponentGenerator
{

  public MyFacesComponentGenerator(Log log, boolean is12)
  {
    super(log, is12);
  }


  protected void addSpecificImports(Set imports, ComponentBean component)
  {
    imports.add("javax.faces.context.FacesContext");
    imports.add("javax.el.ValueExpression");
    for (Iterator lIterator = component.properties(); lIterator.hasNext();)
    {
      PropertyBean lPropertyBean = (PropertyBean) lIterator.next();
      if (GeneratorHelper.isValidator(lPropertyBean, _is12))
      {
        imports.add("javax.faces.validator.Validator");
        imports.add("java.util.ArrayList");
        imports.add("java.util.List");
        break;
      }
    }
  }

  public void writePropertyDeclaration(PrettyWriter out,
                                       PropertyBean property) throws IOException
  {
    String propName = property.getPropertyName();
    String fieldPropName = property.getFieldPropertyName();
    String propertyFullClass = property.getPropertyClass();
    String propertyClass = Util.getClassFromFullClass(propertyFullClass);
    String propertyGenerics = Util.getGenericsFromProperty(property);
    String def = Util.getDefaultValue(property);

    out.println();
    out.println("// Property: " + propName);
    out.println("private " + propertyClass + propertyGenerics + " " +
        fieldPropName + (def == null ? ";" : " = " + def + ";"));

    if (Util.isPrimitiveClass(propertyFullClass) && !property.isTagAttributeExcluded())
    {
      out.println("private boolean " + fieldPropName + "Set;");
    }
  }

  public void writeStateManagementMethods(PrettyWriter out,
                                          ComponentBean component) throws IOException
  {
    if (!component.hasProperties())
    {
      return;
    }

    writeSaveState(out, component);
    writeRestoreState(out, component);
  }

  public void writePropertyListMethods(
      PrettyWriter out,
      PropertyBean property) throws IOException
  {
    // nothing
  }

  protected void writeConstructorContent(PrettyWriter out, ComponentBean component, int modifiers, String rendererType) throws IOException
  {
    out.println("setRendererType(" + rendererType + ");");
  }

  protected void writePropertyListMethods(PrettyWriter out, PropertyBean property, Collection ignoreList)
  {
    String propName = property.getPropertyName();
    String generic;
    String type;
    boolean isValidator = GeneratorHelper.isValidator(property, _is12);
    if (isValidator)
    {
      generic = "<Validator>";
      type = "Validator";
    }
    else if (property.getAttributeClassParameters().length == 1)
    {
      generic = Util.getGenericsFromProperty(property);
      type = Util.getClassFromFullClass(property.getAttributeClassParameters()[0]);
    }
    else
    {
      throw new IllegalArgumentException("only one Generic Parameter is allowed for Lists");
    }

    String singularName = getSingular(propName);
    String propVar = Util.getVariableFromName(singularName);
    String description = property.getDescription();
    String addMethod = Util.getPrefixedPropertyName("add", singularName);
    String removeMethod = Util.getPrefixedPropertyName("remove", singularName);
    String getMethod = Util.getPrefixedPropertyName("get", singularName + "s");
    String fieldPropName = property.getFieldPropertyName() + (isValidator ? "List" : "");

    out.println();
    out.println("// Property: " + propName);
    out.println("private List" + generic + " " + fieldPropName + ";");
    if (ignoreList == null || !ignoreList.contains(addMethod))
    {
      out.println();
      out.println("/**");
      if (description != null)
      {
        out.println(" * Adds a " + convertMultilineComment(description));
      }
      out.println(" */");
      out.println("public void " + addMethod + "( " + type + " " +
          propVar + ")");
      out.println("{");
      out.indent();
      out.println("if (" + propVar + " == null) throw new NullPointerException(\"" + propVar + "\");");
      out.println("if (" + fieldPropName + " == null)");
      out.println("  " + fieldPropName + " = new ArrayList" + generic + "();");
      out.println();
      out.println(fieldPropName + ".add(" + propVar + ");");
      out.unindent();
      out.println("}");
    }

    if (ignoreList == null || !ignoreList.contains(removeMethod))
    {
      out.println();
      out.println("/**");
      if (description != null)
      {
        out.println(" * Removes a " + convertMultilineComment(description));
      }
      out.println(" */");
      out.println("public void " + removeMethod + "( " + type + " " + propVar + ")");
      out.println("{");
      out.indent();
      out.println("if (" + propVar + " == null || " + fieldPropName + " == null)");
      out.println("  return;");
      out.println();
      out.println(fieldPropName + ".remove(" + propVar + ");");
      out.unindent();
      out.println("}");
    }

    if (ignoreList == null || !ignoreList.contains(getMethod))
    {
      if (isValidator)
      {
        out.println("private static final " + type + "[] EMPTY_" + type.toUpperCase() + "_ARRAY = new " + type + "[0];");
      }
      out.println();
      out.println("/**");
      if (description != null)
      {
        out.println(" * Gets all " + convertMultilineComment(description));
      }
      out.println(" */");
      if (isValidator)
      {
        out.println("public " + type + "[] " + getMethod + "()");
      }
      else
      {
        out.println("public List" + generic + " " + getMethod + "()");
      }
      out.println("{");
      out.indent();
      if (isValidator)
      {
        out.println("return " + fieldPropName + "== null? EMPTY_" + type.toUpperCase() + "_ARRAY : " + fieldPropName + ".toArray(new " + type + "[" + fieldPropName + ".size()]);");
      }
      else
      {
        out.println("if (" + fieldPropName + " == null)");
        out.indent();
        out.println(fieldPropName + " = new ArrayList<" + type + ">();");
        out.unindent();
        out.println("return " + fieldPropName + ";");
      }
      out.unindent();
      out.println("}");
    }
  }

  protected void writePropertySetterMethodBody(PrettyWriter out,
                                               PropertyBean property,
                                               String propertyClass) throws IOException
  {
    String varName = property.getFieldPropertyName();
    String propVar = Util.getVariableFromName(property.getPropertyName());

    out.println("this." + varName + " = " + propVar + ";");

    if (Util.isPrimitiveClass(propertyClass) && !property.isTagAttributeExcluded())
    {
      out.println("this." + _primitiveSetVarName(varName) + " = true;");
    }
  }

  protected void writePropertyGetterMethodBody(PrettyWriter out,
                                               PropertyBean property) throws IOException
  {
    String varName = property.getFieldPropertyName();
    String propFullClass = property.getPropertyClass();
    String propClass = Util.getClassFromFullClass(propFullClass);
    if (property.isTagAttributeExcluded())
    {
      out.println("return " + property.getFieldPropertyName() + ";");
    }
    else if(property.isLiteralOnly())
    {
      out.println("return " + varName + ";");
    }
    else
    {
      if (Util.isPrimitiveClass(propFullClass))
      {
        out.println("if (" + _primitiveSetVarName(varName) + ")");
      }
      else
      {
        out.println("if (" + varName + " != null)");
      }
      out.println("{");
      out.indent();
      out.println("return " + property.getFieldPropertyName() + ";");
      out.unindent();
      out.println("}");
      out.println("ValueExpression expression = getValueExpression(\"" + property.getPropertyName() + "\");");
      out.println("if (expression != null)");
      out.println("{");
      out.indent();
      out.println("return " + _castIfNecessary(propClass) + "expression.getValue(getFacesContext().getELContext());");
      out.unindent();
      out.println("}");

      String ret = Util.getDefaultValue(property);
      if (null != ret)
      {
        out.println("return " + ret + ";");
      }
      else if (Util.isPrimitiveClass(propFullClass))
      {
        out.println("return " + Util.primitiveDefaultValue(propFullClass) + ";");
      }
      else
      {
        out.println("return null;");
      }
    }
  }

  protected void writeSaveState(PrettyWriter out,
                                ComponentBean component) throws IOException
  {
    String arrayName = "values";

    // first we count the primitive properties, because for each primitive property
    // we save a new property that says if the property has been set or not
    int primitivePropertiesCount = 0;
    for (Iterator iterator = component.properties(); iterator.hasNext();)
    {
      PropertyBean property = (PropertyBean) iterator.next();
      if (Util.isPrimitiveClass(property.getPropertyClass()))
      {
        primitivePropertiesCount++;
      }
    }

    // the total array size is the number of properties, plus the number of primitives,
    // plus 1 (the super call)
    int arraySize = component.propertiesSize() + primitivePropertiesCount + 1;

    out.println();
    out.println("@Override");
    out.println("public Object saveState(FacesContext facesContext)");
    out.println("{");
    out.indent();
    out.println("Object[] " + arrayName + " = new Object[" + arraySize + "];");

    out.println(arrayName + "[" + 0 + "] = super.saveState(facesContext);");

    int propIndex = 1;

    for (Iterator iterator = component.properties(); iterator.hasNext();)
    {
      PropertyBean property = (PropertyBean) iterator.next();
      String varName = property.getFieldPropertyName();

      if (property.isStateHolder())
      {
        if (GeneratorHelper.isValidator(property, _is12))
        {
          out.println(arrayName + "[" + ++propIndex + "] = saveAttachedState(facesContext, " + varName + "List);");
        }
        else if (property.isList())
        {
          out.println(arrayName + "[" + ++propIndex + "] = saveAttachedState(facesContext, " + varName + ");");
        }
        else
        {
          out.println(arrayName + "[" + propIndex + "] = saveAttachedState(facesContext, " + varName + ");");
        }
      }
      else if (GeneratorHelper.isConverter(property.getPropertyClass()))
      {
        out.println(arrayName + "[" + propIndex + "] = saveAttachedState(facesContext, " + varName + ");");
      }
      else
      {
        out.println(arrayName + "[" + propIndex + "] = " + varName + ";");
      }

      propIndex++;

      if (Util.isPrimitiveClass(property.getPropertyClass()) && !property.isTagAttributeExcluded())
      {
        out.println(arrayName + "[" + propIndex + "] = " + _primitiveSetVarName(varName) + ";");
        propIndex++;
      }
    }

    out.println();
    out.println("return " + arrayName + ";");
    out.unindent();
    out.println("}");
  }

  protected void writeRestoreState(PrettyWriter out,
                                   ComponentBean component) throws IOException
  {
    String arrayName = "values";

    out.println();
    out.println("@Override");
    out.println("public void restoreState(FacesContext facesContext, Object state)");
    out.println("{");
    out.indent();

    out.println("Object[] " + arrayName + " = (Object[])state;");
    out.println("super.restoreState(facesContext," + arrayName + "[0]);");

    int propIndex = 1;

    for (Iterator iterator = component.properties(); iterator.hasNext();)
    {
      PropertyBean property = (PropertyBean) iterator.next();

      String varName = property.getFieldPropertyName();
      String propFullClass = property.getPropertyClass();
      String propClass = Util.getClassFromFullClass(propFullClass);

      if (property.isStateHolder())
      {
        if (GeneratorHelper.isValidator(property, _is12))
        {
          out.println(varName + "List = (List<Validator>) restoreAttachedState(facesContext, "
              + arrayName + "[" + ++propIndex + "]);");
        }
        else if (property.isList())
        {
          out.println(varName + " = (List) restoreAttachedState(facesContext, "
              + arrayName + "[" + ++propIndex + "]);");
        }
        else
        {
          out.println(varName + " = " + _castIfNecessary(propClass) + "restoreAttachedState(facesContext, "
              + arrayName + "[" + propIndex + "]);");
        }

      }
      else if (GeneratorHelper.isConverter(property.getPropertyClass()))
      {
        out.println(varName + " = (Converter) restoreAttachedState(facesContext, "
            + arrayName + "[" + propIndex + "]);");
      }
      else
      {
        out.println(varName + " = " + _castIfNecessary(propClass)
            + arrayName + "[" + propIndex + "];");
      }

      propIndex++;

      if (Util.isPrimitiveClass(property.getPropertyClass()) && !property.isTagAttributeExcluded())
      {
        out.println(_primitiveSetVarName(varName) + " = (Boolean)"
            + arrayName + "[" + propIndex + "];");
        propIndex++;
      }
    }

    out.unindent();
    out.println("}");
  }

  private static String _castIfNecessary(String propClass)
  {
    if (propClass.equals("Object") || propClass.equals("java.lang.Object"))
    {
      return "";
    }

    if (Util.isPrimitiveClass(propClass))
    {
      propClass = Util.getBoxedClass(propClass);
    }

    return "(" + propClass + ")";
  }

  private static String _primitiveSetVarName(String varName)
  {
    return varName + "Set";
  }
}
