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

import org.apache.myfaces.trinidadbuild.plugin.faces.generator.GeneratorHelper;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ComponentBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.MethodSignatureBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Component tag generator for myfaces
 *
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MyFacesComponentTagGenerator extends AbstractComponentTagGenerator
{
  private boolean _is12;

  public MyFacesComponentTagGenerator(boolean is12)
  {
    _is12 = is12;
  }
  
  protected boolean is12()
  {
    return _is12;
  }

  protected void addSpecificImports(Set imports,
                                    ComponentBean component)
  {
    imports.add("javax.faces.component.UIComponent");
    imports.add(component.getComponentClass());

    if (_is12)
    {
      imports.add("javax.el.ValueExpression");
    }

    Iterator properties = component.properties();
    properties = new FilteredIterator(properties, new TagAttributeFilter());

    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean) properties.next();
      String propertyClass = property.getPropertyClass();

      if (GeneratorHelper.isConverter(propertyClass))
      {
        if (!_is12)
        {
          imports.add("javax.faces.el.ValueBinding");
        }
        imports.add("javax.faces.convert.Converter");
      }
      else if (property.isMethodBinding())
      {
        if (_is12)
        {
          imports.add("javax.el.MethodExpression");
          if (GeneratorHelper.isActionListener(property, _is12))
          {
            imports.add("javax.faces.event.MethodExpressionActionListener");
          }
          if (GeneratorHelper.isValueChangeListener(property, _is12))
          {
            imports.add("javax.faces.event.MethodExpressionValueChangeListener");
          }
          if (GeneratorHelper.isValidator(property, _is12))
          {
            imports.add("javax.faces.validator.MethodExpressionValidator");
          }
        }
        else
        {
          imports.add("javax.faces.el.MethodBinding");
        }
      }
      else if (property.isMethodExpression())
      {
        imports.add("javax.el.MethodExpression");
      }
      else if (GeneratorHelper.isActionListener(property, _is12))
      {
        imports.add("javax.faces.event.MethodExpressionActionListener");
      }
      else if (GeneratorHelper.isValueChangeListener(property, _is12))
      {
        imports.add("javax.faces.event.MethodExpressionValueChangeListener");
      }
      else if (GeneratorHelper.isValidator(property, _is12))
      {
        imports.add("javax.faces.validator.MethodExpressionValidator");
      }
    }
  }


  protected void writePropertyDeclaration(PrettyWriter out,
                                          PropertyBean property) throws IOException
  {
    String fieldPropName = property.getFieldPropertyName();
    String jspPropType = GeneratorHelper.getJspPropertyType(property, _is12);

    out.println();
    out.println("private " + jspPropType + " " + fieldPropName + ";");
  }

  protected void writePropertySetter(PrettyWriter out,
                                     PropertyBean property) throws IOException
  {
    String propName = property.getPropertyName();
    String fieldPropName = property.getFieldPropertyName();
    String jspPropName = property.getJspPropertyName();
    String propVar = Util.getVariableFromName(propName);
    String setMethod = Util.getPrefixedPropertyName("set", jspPropName);
    String jspPropType = GeneratorHelper.getJspPropertyType(property, _is12);

    out.println("public void " + setMethod + "(" + jspPropType + " " + propVar + ")");
    out.println("{");
    out.indent();
    out.println(fieldPropName + " = " + propVar + ";");
    out.unindent();
    out.println("}");
  }

  public void writeGetComponentType(PrettyWriter out,
                                    ComponentBean component) throws IOException
  {
    String componentType = component.getComponentType();
    if("javax.faces.HtmlColumn".equals(componentType))
    {
      componentType = "javax.faces.Column";
    }
    out.println();

    // The superclass does not necessarily need to have this method
    out.println("@Override");
    out.println("public String getComponentType()");
    out.println("{");
    out.indent();
    out.println("return \"" + componentType + "\";");
    out.unindent();
    out.println("}");
  }

  public void writeSetPropertiesMethod(PrettyWriter out,
                                       String componentClass,
                                       ComponentBean component) throws IOException
  {
    Collection components = new HashSet();
    components.add(component);
    writeSetPropertiesMethod(out, componentClass, components);
  }


  public void writeSetPropertiesMethod(PrettyWriter out, String componentClass, Collection components)
      throws IOException
  {

    Collection all = new HashSet();
    for (Iterator lIterator = components.iterator(); lIterator.hasNext();)
    {
      ComponentBean component = (ComponentBean) lIterator.next();
      Iterator prop = component.properties();
      while (prop.hasNext())
      {
        all.add(prop.next());
      }
    }

    Iterator properties = all.iterator();
    properties = new FilteredIterator(properties, new TagAttributeFilter());

    out.println();

    if (_is12)
    {
      out.println("@Override");
    }
    out.println("protected void setProperties(UIComponent component)");
    out.unindent();
    out.println("{");
    out.indent();

    writeSetPropertyMethodBody(out, componentClass, properties);
    out.unindent();
    out.println("}");
  }

  protected void writeSetPropertyMethodBody(PrettyWriter out,
                                            String componentClass,
                                            Iterator properties) throws IOException
  {

    out.println("if (!(component instanceof " + componentClass + "))");
    out.println("{");
    out.indent();
    out.print("throw new IllegalArgumentException(");
    out.print("\"Component \" + component.getClass().getName() + \" is no " + componentClass + "\"");
    out.println(");");
    out.unindent();
    out.println("}");

    out.println(componentClass + " comp = (" + componentClass + ")component;");

    out.println();
    out.println("super.setProperties(component);");
    out.println();

    if (!_is12)
    {
      out.println("FacesContext context = FacesContext.getCurrentInstance();");
    }

    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean) properties.next();
      _writeSetPropertiesCase(out, componentClass, property);
    }
  }

  private void _writeSetPropertiesCase(
      PrettyWriter out,
      String componentClass,
      PropertyBean property) throws IOException
  {

    if (property.isMethodBinding())
    {
      _writeSetMethodBinding(out, componentClass, property);
    }
    else if (property.isMethodExpression())
    {
      _writeSetMethodExpression(out, componentClass, property);
    }
    else if (GeneratorHelper.isConverter(property.getPropertyClass()))
    {
      _writeSetConverter(out, componentClass, property.getPropertyName());
    }
    else
    {
      _writeSetProperty(out, property);
    }
  }

  private void _writeSetProperty(
      PrettyWriter out,
      PropertyBean property)
  {
    String propName = property.getPropertyName();
    String propVar = "_" + propName;
    
    out.println("if (" + propVar + " != null) ");
    out.println("{");
    out.indent();

    if (property.isLiteralOnly())
    {
      out.println("comp.getAttributes().put(\"" + propName + "\", " + propVar + ");");
    }
    else if (_is12)
    {
      out.println("comp.setValueExpression(\"" + propName + "\", " + propVar + ");");
    }
    else
    {
      _writeSetValueBinding(out, propName, propVar);
    }
    out.unindent();
    out.println("}");
  }

  private void _writeSetValueBinding(
      PrettyWriter out,
      String propName,
      String propVar)
  {
    out.println("if (isValueReference(" + propVar + ")");
    out.println("{");
    out.indent();
    out.println("ValueBinding vb = context.getApplication().createValueBinding(" + propVar + ");");
    out.println("comp.setValueBinding(\"" + propName + "\", " + propVar + ");");
    out.unindent();
    out.println("}");
    out.println("else");
    out.println("{");
    out.indent();
    out.println("comp.getAttributes().put(\"" + propName + "\", " + propVar + ");");
    out.unindent();
    out.println("}");
  }


  private void _writeSetMethodBinding(
      PrettyWriter out,
      String componentClass,
      PropertyBean property) throws IOException
  {
    String propName = property.getPropertyName();
    String propKey = Util.getConstantNameFromProperty(propName, "_KEY");
    String propVar = "_" + propName;

    if (_is12)
    {
      out.println("if (" + propVar + " != null)");
      out.println("{");
      out.indent();
      if (GeneratorHelper.isActionListener(property, _is12))
      {
        out.println("comp.addActionListener(new MethodExpressionActionListener(" + propVar + "));");
      }
      else if (GeneratorHelper.isValueChangeListener(property, _is12))
      {
        out.println("comp.addValueChangeListener(new MethodExpressionValueChangeListener(" + propVar + "));");
      }
      else if (GeneratorHelper.isValidator(property, _is12))
      {
        out.println("comp.addValidator(new MethodExpressionValidator(" + propVar + "));");
      }
      else
      {
        out.println("bean.setProperty(" + componentClass + "." + propKey + ", " +
            "new MethodExpressionMethodBinding(" + propVar + "));");
      }
      out.unindent();
      out.println("}");
    }
    else
    {
      MethodSignatureBean signature = property.getMethodBindingSignature();
      String[] paramTypes = (signature != null) ? signature.getParameterTypes() : null;

      String classArray;

      if (paramTypes == null || paramTypes.length == 0)
      {
        classArray = "new Class[0]";
      }
      else
      {
        StringBuffer sb = new StringBuffer();
        sb.append("new Class[]{");
        for (int i = 0; i < paramTypes.length; i++)
        {
          if (i > 0)
            sb.append(',');
          sb.append(paramTypes[i]);
          sb.append(".class");
        }

        // TODO: remove trailing comma
        sb.append(',');

        sb.append('}');
        classArray = sb.toString();
      }

      out.println("if (" + propVar + " != null)");
      out.println("{");
      out.indent();

      if (isStringMethodBindingReturnType(signature))
      {
        out.println("MethodBinding mb;");
        out.println("if (isValueReference(" + propVar + "))");
        out.indent();
        out.println("mb = createMethodBinding(" + propVar + ", " + classArray + ");");
        out.unindent();
        out.println("else");
        out.indent();
        out.println("mb = new org.apache.myfaces.trinidadinternal.taglib.ConstantMethodBinding(" + propVar + ");");
        out.unindent();
      }
      else
      {
        // never a literal, no need for ConstantMethodBinding
        out.println("MethodBinding mb = createMethodBinding(" + propVar + ", " +
            classArray + ");");
      }

      out.println("bean.setProperty(" + componentClass + "." + propKey + ", mb);");
      out.unindent();
      out.println("}");
    }
  }

  private void _writeSetMethodExpression(
      PrettyWriter out,
      String componentClass,
      PropertyBean property) throws IOException
  {
    String propName = property.getPropertyName();
    String propVar = property.getFieldPropertyName();

    out.println("if (" + propVar + " != null) ");
    out.println("{");
    out.indent();

    if (GeneratorHelper.isAction(property))
    {
      out.println("comp.setActionExpression(" + propVar + ");");
    }
    else if (GeneratorHelper.isActionListener(property, _is12))
    {
      out.println("comp.addActionListener(new MethodExpressionActionListener(" + propVar + "));");
    }
    else
    {
      out.println("comp." + Util.getPrefixedPropertyName("set", propName) + "(" + propVar + ");");
    }
    out.unindent();
    out.println("}");
  }

  private void _writeSetKeyStroke(
      PrettyWriter out,
      String componentClass,
      String propName) throws IOException
  {
    String propKey = Util.getConstantNameFromProperty(propName, "_KEY");
    String propVar = "_" + propName;


    if (_is12)
    {
      out.println("if (" + propVar + " != null)");
      out.println("{");
      out.indent();
      out.println("if (!" + propVar + ".isLiteralText())");
      out.println("{");
      out.indent();
      out.println("bean.setValueExpression(" + componentClass + "." + propKey + ", " + propVar + ");");
      out.unindent();
      out.println("}");
      out.println("else");
      out.println("{");
      out.indent();
      out.println("Object val = " + propVar + ".getValue(null);");
      out.println("if (val != null)");
      out.indent();
      out.println("bean.setProperty(" + componentClass + "." + propKey + ",");
      out.println("\tKeyStroke.getKeyStroke(val.toString()));");
      out.unindent();
      out.unindent();
      out.println("}");
      out.unindent();
      out.println("}");
    }
    else
    {
      out.println("if (" + propVar + " != null)");
      out.println("{");
      out.indent();
      out.println("if (isValueReference(" + propVar + "))");
      out.println("{");
      out.indent();
      out.println("ValueBinding vb = createValueBinding(" + propVar + ");");
      out.println("bean.setValueBinding(" + componentClass + "." + propKey + ", vb);");
      out.unindent();
      out.println("}");
      out.println("else");
      out.println("{");
      out.indent();
      out.println("bean.setProperty(" + componentClass + "." + propKey + ",");
      out.println("\tKeyStroke.getKeyStroke(" + propVar + "));");
      out.unindent();
      out.println("}");
      out.unindent();
      out.println("}");
    }
  }

  private void _writeSetConverter(
      PrettyWriter out,
      String componentClass,
      String propName) throws IOException
  {
    String propKey = Util.getConstantNameFromProperty(propName, "_KEY");
    String propVar = "_" + propName;

    out.println("if (" + propVar + " != null)");
    out.println("{");
    out.indent();

    if (_is12)
    {
      out.println("if (!" + propVar + ".isLiteralText())");
      out.println("{");
      out.indent();
      out.println("comp.setValueExpression(\"" + propName + "\", " + propVar + ");");
      out.unindent();
      out.println("}");
      out.println("else");
      out.println("{");
      out.indent();
      out.println("String s = " + propVar + ".getExpressionString();");
      out.println("if (s != null)");
      out.println("{");

      out.indent();
      out.println("Converter converter = getFacesContext().getApplication().");
      out.indent();
      out.println("createConverter(s);");
      out.unindent();
      out.println("comp.setConverter(converter);");

      out.unindent();
      out.println("}");

      out.unindent();
      out.println("}");
    }
    else
    {
      out.println("if (isValueReference(" + propVar + "))");
      out.println("{");
      out.indent();
      out.println("ValueBinding vb = createValueBinding(" + propVar + ");");
      out.println("bean.setValueBinding(" + componentClass + "." + propKey + ", vb);");
      out.unindent();
      out.println("}");
      out.println("else");
      out.println("{");
      out.indent();
      out.println("Converter converter = getFacesContext().getApplication().");
      out.indent();
      out.println("createConverter(" + propVar + ");");
      out.unindent();
      out.println("bean.setProperty(" + componentClass + "." + propKey + ", converter);");
      out.unindent();
      out.println("}");
    }
    out.unindent();
    out.println("}");
  }

  private boolean isStringMethodBindingReturnType(
      MethodSignatureBean sig)
  {
    return (sig != null && "java.lang.String".equals(sig.getReturnType()));
  }

}
