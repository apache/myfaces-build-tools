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
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.*;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.PropertyFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.SourceTemplate;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractComponentGenerator implements ComponentGenerator
{

  private Log _log;
  boolean _is12;

  public AbstractComponentGenerator(Log log, boolean is12)
  {
    _log = log;
    _is12 = is12;
  }

  protected Log getLog()
  {
    return _log;
  }

  public void writeClassBegin(
      PrettyWriter out,
      String className,
      String superclassName,
      ComponentBean component,
      SourceTemplate template)
  {
    out.println("/**");

    // TODO: restore description (needs escaping?)
//    String description = component.getDescription();
//    if (description != null)
//    {
//      out.println(" *");
//      out.println(" * " + convertMultilineComment(description));
//    }

    String longDescription = component.getLongDescription();
    if (longDescription != null)
    {
      out.println(" *");
      out.println(" * " + convertMultilineComment(longDescription));
    }

    if (component.hasEvents(true))
    {
      // the events javadoc
      out.println(" *");
      out.println(" * <h4>Events:</h4>");
      out.println(" * <table border=\"1\" width=\"100%\" cellpadding=\"3\" summary=\"\">");
      out.println(" * <tr bgcolor=\"#CCCCFF\" class=\"TableHeadingColor\">");
      out.println(" * <th align=\"left\">Type</th>");
      out.println(" * <th align=\"left\">Phases</th>");
      out.println(" * <th align=\"left\">Description</th>");
      out.println(" * </tr>");
      Iterator events = component.events(true);
      while (events.hasNext())
      {
        EventRefBean eventRef = (EventRefBean) events.next();
        EventBean event = eventRef.resolveEventType();
        if (event != null)
        {
          String eventClass = event.getEventClass();
          String[] eventPhases = eventRef.getEventDeliveryPhases();
          String eventDescription = event.getDescription();
          out.println(" * <tr class=\"TableRowColor\">");
          out.println(" * <td valign=\"top\"><code>" + eventClass + "</code></td>");
          out.print(" * <td valign=\"top\" nowrap>");
          if (eventPhases != null)
          {
            for (int i = 0; i < eventPhases.length; i++)
            {
              if (i > 0)
                out.print("<br>");
              out.print(eventPhases[i]);
            }
          }
          out.println("</td>");
          out.println(" * <td valign=\"top\">" + eventDescription + "</td>");
          out.println(" * </tr>");
        }
      }
      out.println(" * </table>");
    }

    if (!component.hasChildren())
    {
      out.println(" * <p>");
      out.println(" * It does not support any children.");
    }

    out.println(" */");

    // TODO: eliminate <mfp:component-class-modifier> metadata
    int modifiers = component.getComponentClassModifiers();
    String classStart = Modifier.toString(modifiers);
    // TODO: use canonical ordering
    classStart = classStart.replaceAll("public abstract", "abstract public");
    out.println(classStart + " class " + className +
        " extends " + superclassName);

    Set interfaces = new HashSet();
    if (template != null)
      interfaces.addAll(template.getImplements());

    if (component.isNamingContainer())
      interfaces.add("javax.faces.component.NamingContainer");

    Iterator events = component.events();
    while (events.hasNext())
    {
      EventRefBean eventRef = (EventRefBean) events.next();
      EventBean event = eventRef.resolveEventType();
      if (event != null)
      {
        if (!eventRef.isIgnoreSourceInterface())
        {
          String source = event.getEventSourceInterface();
          if (source != null)
            interfaces.add(Util.getClassFromFullClass(source));
        }
      }
    }

    if (!interfaces.isEmpty())
    {
      Set implementsSet = new HashSet();
      for (Iterator iter = interfaces.iterator(); iter.hasNext();)
      {
        String fcqn = (String) iter.next();
        implementsSet.add(Util.getClassFromFullClass(fcqn));
      }

      // implements clause spans multiple lines
      char[] indent = new char[classStart.length() +
          " class ".length() +
          className.length() + 1];
      Arrays.fill(indent, ' ');
      out.print(indent);
      out.print("implements ");
      for (Iterator iter = implementsSet.iterator(); iter.hasNext();)
      {
        out.print((String) iter.next());
        if (iter.hasNext())
        {
          out.println(",");
          out.print(indent);
          out.print("           ");  // same length as "implements "
        }
      }
      out.println();
    }

    out.println("{");
    out.indent();
  }

  public void writeClassEnd(
      PrettyWriter out)
  {
    out.unindent();
    out.println("}");
  }


  public void writeImports(PrettyWriter out, SourceTemplate template, String packageName, String fullSuperclassName,
                           String superclassName, Collection components)
  {
    throw new UnsupportedOperationException("not implemented");
  }

  public void writeImports(
      PrettyWriter out,
      SourceTemplate template,
      String packageName,
      String fullSuperclassName,
      String superclassName,
      ComponentBean component)
  {
    Set imports = new TreeSet();

    // Use the template imports
    if (template != null)
      imports.addAll(template.getImports());

    // Detect NamingContainer
    if (component.isNamingContainer())
      imports.add("javax.faces.component.NamingContainer");

    Iterator properties = component.properties();
    properties = new FilteredIterator(properties, new NonVirtualFilter());
    // PropertyKey only needed if there are properties
    if (properties.hasNext())
    {
      while (properties.hasNext())
      {
        PropertyBean property = (PropertyBean) properties.next();
        String propertyClass = property.getPropertyClass();
        if (propertyClass != null)
        {
          imports.add(propertyClass);
          // Check for generics
          String[] types = property.getAttributeClassParameters();
          if (types != null)
          {
            for (int i = types.length - 1; i >= 0; i--)
            {
              addGenericImports(imports, types[i]);
            }
          }
        }
      }
    }

    Iterator facets = component.facets();
    // UIComponent needed if there are facets
    if (facets.hasNext())
      imports.add("javax.faces.component.UIComponent");

    Iterator events = component.events();
    while (events.hasNext())
    {
      EventRefBean eventRef = (EventRefBean) events.next();
      EventBean event = eventRef.resolveEventType();

      if (event == null)
      {
        getLog().warn("Unknown event type \"" + eventRef.getEventType() + "\"" +
            " in component:" + component.getComponentType());
      }
      else
      {
        String listenerClass = event.getEventListenerClass();
        if (listenerClass != null)
          imports.add(listenerClass);

        if (!eventRef.isIgnoreSourceInterface())
        {
          String sourceInterface = event.getEventSourceInterface();
          if (sourceInterface != null)
            imports.add(sourceInterface);
        }
      }
    }

    // Import causes a collision if className and superclassName are equal
    if (!superclassName.equals(fullSuperclassName))
    {
      String superPackageName = Util.getPackageFromFullClass(fullSuperclassName);
      // component superclass only needed if not in
      // same package as component class
      if (superPackageName != packageName)
        imports.add(fullSuperclassName);
    }

    // add other imports (generator specific)
    addSpecificImports(imports, component);

    // do not import implicit types!
    imports.removeAll(Util.PRIMITIVE_TYPES);

    GeneratorHelper.writeImports(out, packageName, imports);
  }

  protected void addSpecificImports(
      Set imports,
      ComponentBean component)
  {
    // nothing by default
  }

  public void addGenericImports(Set imports, String type)
  {
    Matcher matcher = _GENERIC_TYPE.matcher(type);
    if (matcher.matches())
    {
      // Generic type
      imports.add(matcher.group(1));
      String[] types = matcher.group(2).split(",");
      for (int i = types.length - 1; i >= 0; i--)
      {
        addGenericImports(imports, types[i]);
      }
    }
    else
    {
      // Non-generic type
      imports.add(type);
    }
  }

  public void writeGenericConstants(
      PrettyWriter out,
      String componentFamily,
      String componentType) throws IOException
  {
    out.println();
    out.println("static public final String COMPONENT_FAMILY =");
    out.println("  \"" + componentFamily + "\";");
    out.println("static public final String COMPONENT_TYPE =");
    out.println("  \"" + componentType + "\";");
  }

  public void writePropertyConstants(
      PrettyWriter out,
      String superclassName,
      ComponentBean component) throws IOException
  {
    // nothing
  }

  public void writePropertyValueConstants(
      PrettyWriter out,
      ComponentBean component) throws IOException
  {
    //  component property keys
    Iterator properties = component.properties();
    properties = new FilteredIterator(properties, new NonVirtualFilter());
    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean) properties.next();
      String[] propertyValues = property.getPropertyValues();

      if (propertyValues != null)
      {
        String propName = property.getPropertyName();

        for (int i = 0; i < propertyValues.length; i++)
        {
          String propValue = propertyValues[i];
          String propValueName = propName +
              Character.toUpperCase(propValue.charAt(0)) +
              propValue.substring(1);
          String propValueKey = Util.getConstantNameFromProperty(propValueName);

          out.println("static public final String " + propValueKey + " = \"" + propValue + "\";");
        }

      }
    }
  }

  public void writeFacetConstants(
      PrettyWriter out,
      ComponentBean component) throws IOException
  {
    Iterator facets = component.facets();
    while (facets.hasNext())
    {
      FacetBean facet = (FacetBean) facets.next();
      String facetName = facet.getFacetName();
      String facetKey = Util.getConstantNameFromProperty(facetName, "_FACET");
      out.println("static public final " +
          "String " + facetKey + " = \"" + facetName + "\";");
    }
  }

  protected String convertStringToBoxedLiteral(
      String className,
      String value)
  {
    if (value == null)
    {
      return null;
    }
    else if ("String".equals(className))
    {
      return "\"" + value.replaceAll("\'", "\\'") + "\"";
    }
    else if ("boolean".equals(className))
    {
      return ("true".equals(value)) ? "Boolean.TRUE" : "Boolean.FALSE";
    }
    else if ("char".equals(className))
    {
      return "Character.valueOf('" + value.replaceAll("\'", "\\'") + "')";
    }
    else if ("int".equals(className))
    {
      return "Integer.valueOf(" + value + ")";
    }
    else if ("double".equals(className))
    {
      return "Double.valueOf(" + value + ")";
    }
    else if ("float".equals(className))
    {
      return "Float.valueOf(" + value + ")";
    }
    else if ("Number".equals(className))
    {
      if(value.indexOf(".") == -1)
      {
        return "Integer.valueOf(" + value + ")";
      }
      else
      {
        return "Double.valueOf(" + value + ")";
      }
    }
    else
    {
      throw new IllegalStateException("property-class " + className + " not supported for auto-boxing");
    }
  }


  protected String convertVariableToBoxedForm(
    String  className,
    String  varName)
  {
    if ("boolean".equals(className))
    {
      return varName + " ? Boolean.TRUE : Boolean.FALSE";
    }
    else if ("char".equals(className))
    {
      return "Character.valueOf(" + varName + ")";
    }
    else if ("int".equals(className))
    {
      return "Integer.valueOf(" + varName + ")";
    }
    else if ("short".equals(className))
    {
      return "Short.valueOf(" + varName + ")";
    }
    else if ("long".equals(className))
    {
      return "Long.valueOf(" + varName + ")";
    }
    else if ("double".equals(className))
    {
      return "Double.valueOf(" + varName + ")";
    }
    else if ("float".equals(className))
    {
      return "Float.valueOf(" + varName + ")";
    }
    else
    {
      throw new IllegalStateException("property-class " + className + " not supported for auto-boxing");
    }
  }

  public void writeConstructor(
      PrettyWriter out,
      ComponentBean component,
      int modifiers) throws IOException
  {
    String fullClassName = component.getComponentClass();
    String className = Util.getClassFromFullClass(fullClassName);

    if (Modifier.isPublic(modifiers))
    {
      // TODO: eliminate this inconsistency
      if (!Modifier.isAbstract(component.getComponentClassModifiers()))
      {
        String rendererType = component.getRendererType();

        if (rendererType != null)
          rendererType = convertStringToBoxedLiteral("String", rendererType);

        out.println();
        out.println("/**");
        // TODO: restore this correctly phrased comment (tense vs. command)
        //out.println(" * Constructs an instance of " + className + ".");
        out.println(" * Construct an instance of the " + className + ".");
        out.println(" */");
        out.println("public " + className + "()");
        out.println("{");
        out.indent();

        writeConstructorContent(out, component, modifiers, rendererType);

        out.unindent();
        out.println("}");
      }
    }
    else if (Modifier.isProtected(modifiers))
    {
      out.println();
      out.println("/**");
      // TODO: restore this more descriptive comment with param docs
      //out.println(" * Construct an instance of the " + className);
      //out.println(" * with the specified renderer type.");
      //out.println(" * ");
      //out.println(" * @param rendererType  the renderer type");
      out.println(" * Construct an instance of the " + className + ".");
      out.println(" */");
      out.println("protected " + className + "(");
      out.indent();
      out.println("String rendererType");
      out.println(")");
      out.unindent();
      out.println("{");
      out.indent();

      writeConstructorContent(out, component, modifiers, "rendererType");

      out.unindent();
      out.println("}");

      // TODO: eliminate this inconsistency
      if (Modifier.isAbstract(component.getComponentClassModifiers()))
      {
        out.println();
        out.println("/**");
        // TODO: restore this correctly phrased comment (tense vs. command)
        //out.println(" * Constructs an instance of " + className + ".");
        out.println(" * Construct an instance of the " + className + ".");
        out.println(" */");
        out.println("protected " + className + "()");
        out.println("{");
        out.indent();
        out.println("this(null);");
        out.unindent();
        out.println("}");
      }
    }
  }

  protected abstract void writeConstructorContent(
      PrettyWriter out,
      ComponentBean component,
      int modifiers, String rendererType) throws IOException;

  public void writeGetFamily(
      PrettyWriter out) throws IOException
  {
    out.println();
    out.println("@Override");
    out.println("public String getFamily()");
    out.println("{");
    out.indent();
    out.println("return COMPONENT_FAMILY;");
    out.unindent();
    out.println("}");
  }

  public void writePropertyMethods(
      PrettyWriter out,
      ComponentBean component) throws IOException
  {
    writePropertyMethods(out, component, null);
  }


  public void writePropertyMethods(PrettyWriter out, ComponentBean component, Collection ignoreList)
      throws IOException
  {
    Iterator properties = component.properties();
    properties = new FilteredIterator(properties, new NonVirtualFilter());
    while (properties.hasNext())
    {
      PropertyBean property = (PropertyBean) properties.next();
      if (property.isList())
        writePropertyListMethods(out, property, ignoreList);
      else
      {
        writePropertyDeclaration(out, property);
        writePropertyGet(out, property, ignoreList);
        writePropertySet(out, property, ignoreList);
        if (GeneratorHelper.isValidator(property, _is12))
        {
          writePropertyListMethods(out, property, ignoreList);
        }
      }
    }
  }

  abstract protected void writePropertyListMethods(
      PrettyWriter out,
      PropertyBean property,
      Collection inoreList) throws IOException;

  abstract protected void writePropertyListMethods(
      PrettyWriter out,
      PropertyBean property) throws IOException;

  static protected String getSingular(String plural)
  {
    if (plural.endsWith("s"))
      return plural.substring(0, plural.length() - 1);
    return plural;
  }

  protected abstract void writePropertyDeclaration(
      PrettyWriter out,
      PropertyBean property) throws IOException;

  protected void writePropertySet(
      PrettyWriter out,
      PropertyBean property,
      Collection ignoreList) throws IOException
  {
    String propertyClass = Util.getPropertyClass(property);
    writePropertySet(out, property, propertyClass, ignoreList);

    if (property.getAlternateClass() != null)
    {
      String alternateClass = Util.getAlternatePropertyClass(property);
      writePropertySet(out, property, alternateClass, ignoreList);
    }
  }

  protected void writePropertySet(
      PrettyWriter out,
      PropertyBean property,
      String propertyClass,
      Collection ignoreList) throws IOException
  {
    String propName = property.getPropertyName();
    String propVar = Util.getVariableFromName(propName);
    String description = property.getDescription();
    String setMethod = Util.getPrefixedPropertyName("set", propName);
    if (ignoreList != null && ignoreList.contains(setMethod))
    {
      return;
    }
    out.println();
    out.println("/**");
    if (description != null)
    {
      out.println(" * Sets " + convertMultilineComment(description));
    }

    if (property.isRequired())
    {
      out.println(" * <p>");
      out.println(" * This is a required property on the component.");
    }
    out.println(" * ");
    out.println(" * @param " + Util.getVariableFromName(propName) + "  the new " + propName + " value");
    if (property.isMethodBinding() && _is12)
    {
      out.println(" * @deprecated");
    }
    out.println(" */");

    if (isAccessorMethodFinal())
    {
      out.print("final ");
    }

    out.println("public void " + setMethod + "(" + propertyClass + " " + propVar + ")");
    out.println("{");
    out.indent();
    writePropertySetterMethodBody(out, property, propertyClass);
    out.unindent();
    out.println("}");
  }

  protected abstract void writePropertySetterMethodBody(
      PrettyWriter out,
      PropertyBean property,
      String propertyClass) throws IOException;

  protected void writePropertyGet(
      PrettyWriter out,
      PropertyBean property,
      Collection ignoreList) throws IOException
  {
    String propName = property.getPropertyName();
    String propertyFullClass = property.getPropertyClass();
    String propertyClass = Util.getClassFromFullClass(propertyFullClass);
    String description = property.getDescription();
    String getMethod = Util.getMethodReaderFromProperty(propName, propertyClass);
    if (ignoreList != null && ignoreList.contains(getMethod))
    {
      return;
    }
    boolean isUnchecked = false;
    String[] genericTypes = property.getPropertyClassParameters();
    if (genericTypes != null && genericTypes.length > 0)
    {
      isUnchecked = true;
      propertyClass = Util.getPropertyClass(property);
    }

    out.println();
    out.println("/**");
    if (description != null)
    {
      out.println(" * Gets " + convertMultilineComment(description));
    }
    if (property.isRequired())
    {
      out.println(" * <p>");
      out.println(" * This is a required property on the component.");
      out.println(" * </p>");
    }

    out.println(" *");
    out.println(" * @return  the new " + propName + " value");
    if (property.isMethodBinding() && _is12)
    {
      out.println(" * @deprecated");
    }
    out.println(" */");

    if (isUnchecked)
    {
      out.println("@SuppressWarnings(\"unchecked\")");
    }

    if (isAccessorMethodFinal())
    {
      out.print("final ");
    }

    out.println("public " + propertyClass + " " + getMethod + "()");
    out.println("{");
    out.indent();

    writePropertyGetterMethodBody(out, property);

    out.unindent();
    out.println("}");
  }

  /**
   * Whether the getters/setters have the final modifier
   *
   * @return true if the getters/setters are final
   */
  protected boolean isAccessorMethodFinal()
  {
    return false;
  }

  protected abstract void writePropertyGetterMethodBody(
      PrettyWriter out,
      PropertyBean property) throws IOException;

  public void writeFacetMethods(
      PrettyWriter out,
      ComponentBean component) throws IOException
  {
    Iterator facets = component.facets();
    while (facets.hasNext())
    {
      FacetBean facet = (FacetBean) facets.next();
      writeFacetGet(out, facet);
      writeFacetSet(out, facet);
    }
  }

  public void writeFacetSet(
      PrettyWriter out,
      FacetBean facet) throws IOException
  {
    String facetName = facet.getFacetName();
    // TODO: drop the unnecessary "Facet" suffix
    String facetVar = facetName + "Facet";
    String facetKey = Util.getConstantNameFromProperty(facetName, "_FACET");
    String setMethod = Util.getPrefixedPropertyName("set", facetName);
    String description = facet.getDescription();

    out.println();
    out.println("/**");
    if (description != null)
    {
      out.println(" * " + convertMultilineComment(description));
    }
    if (facet.isRequired())
    {
      out.println(" * <p>");
      out.println(" * This is a required facet on the component.");
    }
    // TODO: put this back in
    //out.println(" * ");
    //out.println(" * @param " + facetVar + "  the new " + facetName + " facet");
    out.println(" */");

    // Remove type safety warning since getFacets is not generics enabled
    // under JSF 1.1 spec
    // TODO: Remove this line when Trinidad switch to JSF 1.2
    out.println("@SuppressWarnings(\"unchecked\")");

    out.println("final public void " + setMethod + "(UIComponent " + facetVar + ")");
    out.println("{");
    out.indent();
    out.println("getFacets().put(" + facetKey + ", " + facetVar + ");");
    out.unindent();
    out.println("}");
  }

  public void writeFacetGet(
      PrettyWriter out,
      FacetBean facet) throws IOException
  {
    String facetName = facet.getFacetName();
    String facetKey = Util.getConstantNameFromProperty(facetName, "_FACET");
    String getMethod = Util.getPrefixedPropertyName("get", facetName);
    String description = facet.getDescription();

    out.println();
    out.println("/**");
    if (description != null)
    {
      out.println(" * " + convertMultilineComment(description));
    }
    if (facet.isRequired())
    {
      out.println(" * <p>");
      out.println(" * This is a required facet on the component.");
    }
    // TODO: put this back in
    //out.println(" * ");
    //out.println(" * @return  the " + facetName + " facet");
    out.println(" */");

    out.println("final public UIComponent " + getMethod + "()");
    out.println("{");
    out.indent();
    out.println("return getFacet(" + facetKey + ");");
    out.unindent();
    out.println("}");
  }

  public void writeListenerMethods(
      PrettyWriter out,
      ComponentBean component) throws IOException
  {
    Iterator events = component.events();
    while (events.hasNext())
    {
      EventRefBean eventRef = (EventRefBean) events.next();
      EventBean event = eventRef.resolveEventType();
      if (event != null)
      {
        writeListenerAdd(out, event);
        writeListenerRemove(out, event);
        writeListenersGet(out, event);
      }
    }
  }

  public void writeListenerAdd(
      PrettyWriter out,
      EventBean event) throws IOException
  {
    String listenerFullClass = event.getEventListenerClass();
    String listenerClass = Util.getClassFromFullClass(listenerFullClass);

    String eventName = event.getEventName();
    String addMethod = Util.getMethodNameFromEvent("add", eventName, "Listener");

    out.println();
    out.println("/**");
    out.println(" * Adds a " + eventName + " listener.");
    out.println(" *");
    out.println(" * @param listener  the " + eventName + " listener to add");
    out.println(" */");

    if (isAccessorMethodFinal())
    {
      out.print("final ");
    }
    out.println("public void " + addMethod + "(");
    out.indent();
    out.println(listenerClass + " listener)");
    out.unindent();
    out.println("{");
    out.indent();
    out.println("addFacesListener(listener);");
    out.unindent();
    out.println("}");
  }

  public void writeListenerRemove(
      PrettyWriter out,
      EventBean event) throws IOException
  {
    String listenerFullClass = event.getEventListenerClass();
    String listenerClass = Util.getClassFromFullClass(listenerFullClass);

    String eventName = event.getEventName();
    String removeMethod = Util.getMethodNameFromEvent("remove", eventName, "Listener");

    out.println();
    out.println("/**");
    out.println(" * Removes a " + eventName + " listener.");
    out.println(" *");
    out.println(" * @param listener  the " + eventName + " listener to remove");
    out.println(" */");

    if (isAccessorMethodFinal())
    {
      out.print("final ");
    }
    out.println("public void " + removeMethod + "(");
    out.indent();
    out.println(listenerClass + " listener)");
    out.unindent();
    out.println("{");
    out.indent();
    out.println("removeFacesListener(listener);");
    out.unindent();
    out.println("}");
  }

  public void writeListenersGet(
      PrettyWriter out,
      EventBean event) throws IOException
  {
    String listenerFullClass = event.getEventListenerClass();
    String listenerClass = Util.getClassFromFullClass(listenerFullClass);

    String eventName = event.getEventName();
    String getMethod = Util.getMethodNameFromEvent("get", eventName, "Listeners");

    out.println();
    out.println("/**");
    out.println(" * Returns an array of attached " + eventName + " listeners.");
    out.println(" *");
    out.println(" * @return  an array of attached " + eventName + " listeners.");
    out.println(" */");

    if (isAccessorMethodFinal())
    {
      out.print("final ");
    }
    out.println("public " + listenerClass + "[] " + getMethod + "()");
    out.println("{");
    out.indent();
    out.println("return (" + listenerClass + "[])" +
        "getFacesListeners(" + listenerClass + ".class);");
    out.unindent();
    out.println("}");
  }


  public abstract void writeStateManagementMethods(PrettyWriter out,
                                                   ComponentBean component) throws IOException;

  public void writeOther(
      PrettyWriter out, ComponentBean component) throws IOException
  {
    // nothing
  }

  protected String convertMultilineComment(
      String commentBody)
  {
    return commentBody.replaceAll("\n", "\n * ");
  }

  protected class ResolvableTypeFilter extends PropertyFilter
  {
    protected boolean accept(
        PropertyBean property)
    {
      String propertyClass = property.getPropertyClass();
      String resolvableType = resolveType(propertyClass);
      return (resolvableType != null);
    }
  }

  protected class NonVirtualFilter extends PropertyFilter
  {
    protected boolean accept(
        PropertyBean property)
    {
      return (!property.isVirtual());
    }
  }

  static protected String resolveType(
      String className)
  {
    return (String) _RESOLVABLE_TYPES.get(className);
  }


  static private Map _createResolvableTypes()
  {
    Map resolvableTypes = new HashMap();

    resolvableTypes.put("boolean", "Boolean");
    resolvableTypes.put("char", "Character");
    // TODO: put this back in
    //resolvableTypes.put("java.util.Date", "Date");
    resolvableTypes.put("int", "Integer");
    resolvableTypes.put("float", "Float");
    resolvableTypes.put("double", "Double");
    resolvableTypes.put("java.util.Locale", "Locale");
    resolvableTypes.put("long", "Long");
    resolvableTypes.put("java.lang.String", "String");
    resolvableTypes.put("java.lang.Number", "Number");
    // TODO: put this back in
    //resolvableTypes.put("java.lang.String[]", "StringArray");
    resolvableTypes.put("java.util.TimeZone", "TimeZone");

    return Collections.unmodifiableMap(resolvableTypes);
  }

  static private final Pattern _GENERIC_TYPE = Pattern.compile("([^<]+)<(.+)>");
  static final private Map _RESOLVABLE_TYPES = _createResolvableTypes();
}
