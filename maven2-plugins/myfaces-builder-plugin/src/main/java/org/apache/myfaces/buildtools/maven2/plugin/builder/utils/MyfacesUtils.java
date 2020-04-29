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
package org.apache.myfaces.buildtools.maven2.plugin.builder.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.MethodSignatureMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyHolder;

/**
 * Collection of useful utility methods.
 * <p>
 * An instance of this type is made available to the templates used to generate
 * output files, so that they can call these useful methods.
 */
public class MyfacesUtils
{
    public MyfacesUtils()
    {
    }

    public static String getPrefixedPropertyName(String prefix,
            String propertyName)
    {
        return prefix + Character.toUpperCase(propertyName.charAt(0))
                + propertyName.substring(1);
    }
    
    /**
     * Convert h:commandButton to commandButton
     * 
     * @param prefixedName
     * @return
     */
    public static String getTagName(String prefixedName)
    {
        return prefixedName.substring(prefixedName.indexOf(':')+1); 
    }
    
    public static String getTagPrefix(String prefixedName)
    {
        return prefixedName.substring(0,prefixedName.indexOf(':'));
    }

    public static String getJspPropertyType11(PropertyMeta property)
    {
//        if (property.isMethodExpression())
//            return "MethodBinding";

//        if (property.isMethodBinding())
//            return "MethodBinding";
        //On 1.1 all properties is mapped as String
        return "String";
    }
    
    public static String getFullJspPropertyType11(PropertyMeta property)
    {
        if (property.isMethodExpression())
        {
            return "jakarta.faces.el.MethodBinding";
        }

        if (property.isMethodBinding())
        {
            return "jakarta.faces.el.MethodBinding";
        }

        return "java.lang.String";
    }
    

    public static String getJspPropertyType12(PropertyMeta property)
    {
        if (property.getJspName().equals("actionListener"))
        {
            return "jakarta.el.MethodExpression";
        }
        if (property.getJspName().equals("validator"))
        {
            return "jakarta.el.MethodExpression";
        }
        if (property.getJspName().equals("valueChangeListener"))
        {
            return "jakarta.el.MethodExpression";
        }
        
        if (property.isMethodExpression())
        {
            return "MethodExpression";
        }

        if (property.isMethodBinding())
        {
            return "MethodBinding";
        }

        if (!property.isLiteralOnly().booleanValue())
        {
            return "ValueExpression";
        }
        else
        {
            return property.getClassName();
        }
    }

    public static String getVariableFromName(String name)
    {
        if (name == null)
        {
            return null;
        }

        if (RESERVED_WORDS.contains(name))
        {
            name = name + "Param";
        }

        return name;
    }

    // TODO: perhaps this could just return a list of class names for
    // templates to iterate over, rather than building the text itself?
    //
    // TODO: why are we importing tag classes into components anyway?
    public static String importTagClasses(PropertyHolder component)
    {
        Set imports = new HashSet();
        for (Iterator it = component.properties(); it.hasNext();)
        {
            PropertyMeta property = (PropertyMeta) it.next();
            if (property.getClassName() != null && 
                    !PRIMITIVE_TYPES.contains(property.getClassName()))
            {
                if (!property.getClassName().startsWith("java.lang"))
                {
                    imports.add(property.getClassName());
                }
            }
        }

        StringBuffer value = new StringBuffer();

        for (Iterator importIterator = imports.iterator(); importIterator
                .hasNext();)
        {
            value.append("import ");
            value.append((String) importIterator.next());
            value.append(';');
            value.append('\n');
        }

        return value.toString();
    }
    
    public static String importTagClasses12(ComponentMeta component)
    {
        Set imports = new HashSet();
        for (Iterator it = component.properties(); it.hasNext();)
        {
            PropertyMeta property = (PropertyMeta) it.next();
            if (property.getClassName() != null && 
                    !PRIMITIVE_TYPES.contains(property.getClassName()))
            {
                if (!property.getClassName().startsWith("java.lang"))
                {
                    imports.add(property.getClassName());
                }
            }
            if (property.getJspName().equals("actionListener"))
            {
                imports.add("jakarta.faces.event.MethodExpressionActionListener");
            }
            if (property.getJspName().equals("valueChangeListener"))
            {
                imports.add("jakarta.faces.event.MethodExpressionValueChangeListener");
            }
            if (property.getJspName().equals("validator"))
            {
                imports.add("jakarta.faces.validator.MethodExpressionValidator");
            }
        }

        StringBuffer value = new StringBuffer();

        for (Iterator importIterator = imports.iterator(); importIterator
                .hasNext();)
        {
            value.append("import ");
            value.append((String) importIterator.next());
            value.append(';');
            value.append('\n');
        }

        return value.toString();
    }
    

    public static boolean isConverter(String propClass)
    {
        return ("jakarta.faces.convert.Converter".equals(propClass));
    }
    
    public static boolean isList(String propClass)
    {
        if (propClass == null)
        {
            return false;
        }
        else if(StringUtils.contains(propClass, "List"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static String castIfNecessary(String propClass)
    {
      if (propClass.equals("Object") || propClass.equals("java.lang.Object"))
      {
        return "";
      }

      if (isPrimitiveClass(propClass))
      {
        propClass = getBoxedClass(propClass);
      }

      return "(" + propClass + ")";
    }    
        
    public static String getBoxedClass(String className)
    {
        if ("boolean".equals(className))
        {
            return "Boolean";
        }
        else if ("byte".equals(className))
        {
            return "Byte";
        }
        else if ("char".equals(className))
        {
            return "Character";
        }
        else if ("double".equals(className))
        {
            return "Double";
        }
        else if ("float".equals(className))
        {
            return "Float";
        }
        else if ("int".equals(className))
        {
            return "Integer";
        }
        else if ("long".equals(className))
        {
            return "Long";
        }
        else if ("short".equals(className))
        {
            return "Short";
        }
        else
        {
            return className;
        }
    }
    
    public static String getSignatureParams(MethodSignatureMeta signature)
    {
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
            {
              sb.append(',');
            }
            sb.append(paramTypes[i]);
            sb.append(".class");
          }

          // TODO: remove trailing comma
          sb.append(',');

          sb.append('}');
          classArray = sb.toString();
        }
        return classArray;
    }
    
    public static boolean isStringMethodBindingReturnType(MethodSignatureMeta sig)
    {
        return (sig != null && "java.lang.String".equals(sig.getReturnType()));
    }
    
    static public String getMethodReaderFromProperty(String propertyName,
            String propertyClass)
    {
        String methodPrefix = ("boolean".equals(propertyClass) ? "is" : "get");
        return getPrefixedPropertyName(methodPrefix, propertyName);
    }
    
    public static String getPrimitiveType(String className)
    {
        if (className.startsWith("java.lang."))
        {
            className = StringUtils.replace(className, "java.lang.", "");
        }

        if (className.endsWith("eger"))
        {
            className = StringUtils.replace(className, "eger", "");
        }

        if (MyfacesUtils.isPrimitiveClass(className.toLowerCase()))
        {
            return className.toLowerCase();
        }
        else
        {
            return className;
        }
    }    
    
    public static boolean isPrimitiveClass(String className)
    {
        return "boolean".equals(className) || "byte".equals(className)
                || "char".equals(className) || "double".equals(className)
                || "float".equals(className) || "int".equals(className)
                || "long".equals(className) || "short".equals(className);
    }

    private static Set _createPrimitiveTypesSet()
    {
        Set primitives = new TreeSet();
        for (int i = 0; i < _PRIMITIVE_TYPES.length; i++)
        {
            String type = _PRIMITIVE_TYPES[i];
            primitives.add(type);
            primitives.add(type + "[]");
        }
        return Collections.unmodifiableSet(primitives);
    }

    private static Set _createReservedWordsSet()
    {
        Set reserved = new TreeSet();
        for (int i = 0; i < _RESERVED_WORDS.length; i++)
        {
            String keyword = _RESERVED_WORDS[i];
            reserved.add(keyword);
        }
        return Collections.unmodifiableSet(reserved);
    }

    static private final String[] _PRIMITIVE_TYPES = new String[] {
        // TODO: Shouldn't java.lang.* be specified in that list as well?
    "boolean", "byte", "char", "float", "double", "int", "short", "long", };

    static private final String[] _RESERVED_WORDS = new String[] { "abstract",
            "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else",
            "extends", "final", "finally", "float", "for", "goto", "if",
            "implements", "import", "instanceof", "int", "interface", "long",
            "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "super", "switch", "synchronized",
            "this", "throw", "throws", "transient", "try", "void", "volatile",
            "while", };

    static public final Set RESERVED_WORDS = _createReservedWordsSet();
    static public final Set PRIMITIVE_TYPES = _createPrimitiveTypesSet();

    static private final Pattern _GENERIC_TYPE = Pattern
            .compile("([^<]+)<(.+)>");
    
    //UNUSED METHODS


    static public String convertClassToSourcePath(String className,
            String fileExtension)
    {
        return (className.replace('.', File.separatorChar) + fileExtension);
    }

    static public boolean isFullClass(String className)
    {
        return (className != null && className.indexOf('.') != -1);
    }

    /*
    public static String getGenericsFromProperty(PropertyMeta property){
        String gen = "";
        for (int i = 0; i < property.getAttributeClassParameters().length; i++) {
            if (i>0){
                gen += ",";
            }
            gen += getClassFromFullClass(property.getAttributeClassParameters()[i]);          
        }
        return (gen.length() > 0?"<"+gen+">":gen);
    }
    */

    /**
     * Extract the simple class name from a fully-qualified classname.
     * <p>
     * Given a string like "org.apache.Foo", this method returns "Foo".
     */
    static public String getClassFromFullClass(String fullClass)
    {
        if (fullClass == null)
        {
            return null;
        }

        int lastSep = fullClass.lastIndexOf('.');
        // Note: this code also works for the empty package
        return (fullClass.substring(lastSep + 1));
    }

    static public String getPackageFromFullClass(String fullClass)
    {
        if (fullClass == null)
        {
            return null;
        }

        int lastSep = fullClass.lastIndexOf('.');

        // detect the empty package
        if (lastSep == -1)
        {
            return "";
        }

        return (fullClass.substring(0, lastSep));
    }

    static public String getConstantNameFromProperty(String propertyName)
    {
        return getConstantNameFromProperty(propertyName, null);
    }

    static public String getConstantNameFromProperty(String propertyName,
            String constantSuffix)
    {
        StringBuffer constantName = new StringBuffer();

        for (int i = 0; i < propertyName.length(); i++)
        {
            char ch = propertyName.charAt(i);
            if (i > 0 && Character.isUpperCase(ch)
                    && Character.isLowerCase(propertyName.charAt(i - 1)))
            {
                constantName.append('_');
            }
            ch = Character.toUpperCase(ch);
            constantName.append(ch);
        }

        if (constantSuffix != null)
        {
            constantName.append(constantSuffix);
        }

        return constantName.toString();
    }

    static public String getPropertyClass(PropertyMeta property)
    {
        String propertyFullClass = property.getClassName();
        String propertyClass = MyfacesUtils
                .getClassFromFullClass(propertyFullClass);
        /*
        String[] genericTypes = property.getPropertyClassParameters();
        if(genericTypes != null && genericTypes.length > 0)
        {
          StringBuffer buffer = new StringBuffer(60);
          buffer.append(propertyClass);
          buffer.append('<');
          int max = genericTypes.length - 1;
          for(int i = 0; i <= max; i++)
          {
            _buildPropertyClass(buffer, genericTypes[i]);
            if(i < max)
            {
              buffer.append(", ");
            }
          }
          buffer.append('>');
          
          propertyClass = buffer.toString();
        }*/

        return propertyClass;
    }

    static public String getMethodNameFromEvent(String methodPrefix,
            String eventName, String methodSuffix)
    {
        return methodPrefix + Character.toUpperCase(eventName.charAt(0))
                + eventName.substring(1) + methodSuffix;
    }

    static public String getEventNameFromEventType(String eventFullClass)
    {
        String eventName = getClassFromFullClass(eventFullClass);
        return Character.toLowerCase(eventName.charAt(0))
                + eventName.substring(1, eventName.length());
    }
    
    /*
    static public String getAlternatePropertyClass(PropertyMeta property)
    {
      StringBuffer buffer = new StringBuffer(60);
      _buildPropertyClass(buffer, property.getAlternateClass());
      
      return buffer.toString();
    }
    */

    static public String primitiveDefaultValue(String className)
    {
        if ("boolean".equals(className))
        {
            return "false";
        }
        else if ("byte".equals(className))
        {
            return "0";
        }
        else if ("char".equals(className))
        {
            return "''";
        }
        else if ("double".equals(className))
        {
            return "0.0d";
        }
        else if ("float".equals(className))
        {
            return "0.0f";
        }
        else if ("int".equals(className))
        {
            return "0";
        }
        else if ("long".equals(className))
        {
            return "0L";
        }
        else if ("short".equals(className))
        {
            return "0";
        }
        else
        {
            return className;
        }
    }

    static public String fill(String base, int length)
    {
        if (base == null || base.length() > length)
        {
            return base;
        }

        StringBuffer filled = new StringBuffer(base);
        for (int i = base.length(); i < length; i++)
        {
            filled.append(' ');
        }
        return filled.toString();
    }

    static public String getVariableFromClass(String className)
    {
        if (className == null)
        {
            return null;
        }

        for (int i = 0; i < className.length(); i++)
        {
            char ch = className.charAt(i);
            if (Character.isLowerCase(ch))
            {
                if (i > 0)
                {
                    return Character.toLowerCase(className.charAt(i - 1))
                            + className.substring(i);
                }
                break;
            }
        }

        throw new IllegalStateException("Class name \"" + className
                + "\" does not use initcaps");
    }

    static public String convertStringToLiteral(String value)
    {
        return convertStringToLiteral("String", value);
    }

    static public String convertStringToLiteral(String className, String value)
    {
        if (value == null)
        {
            return null;
        }
        else if ("String".equals(className))
        {
            return "\"" + value.replaceAll("\'", "\\'") + "\"";
        }
        else if ("Number".equals(className))
        {
            // Double vs. Integer.
            if (value.indexOf(".") == -1)
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
            return value;
        }
    }

    static public String getDefaultValue(PropertyMeta property)
    {
        String propertyFullClass = property.getClassName();
        String def = property.getDefaultValue();
        if (def == null || def.length() == 0)
        {
            return null;
        }
        if (isPrimitiveClass(propertyFullClass))
        {
            return def;
        }
        else if ("java.lang.String".equals(propertyFullClass))
        {
            if (def.startsWith("\"") && def.endsWith("\""))
            {
                return def;
            }
            else
            {
                return "\"" + def + "\"";
            }
        }
        else
        {
            //Any other class, so we return the default value
            return def;
        }
    }
    
    public static String getDefaultValueField(PropertyMeta property)
    {
        String def =  getDefaultValue(property);

        if (property.getClassName().endsWith("Boolean") && def != null)
        {
                def = "Boolean.valueOf("+def+")";
        }
        return def;
    }

    static private void _buildPropertyClass(StringBuffer buffer, String type)
    {
        Matcher matcher = _GENERIC_TYPE.matcher(type);
        if (matcher.matches())
        {
            // Generic type
            buffer.append(MyfacesUtils.getClassFromFullClass(matcher.group(1)));
            buffer.append('<');
            String[] types = matcher.group(2).split(",");
            int max = types.length - 1;
            for (int i = 0; i <= max; i++)
            {
                _buildPropertyClass(buffer, types[i]);
                if (i < max)
                {
                    buffer.append(", ");
                }
            }
            buffer.append('>');
        }
        else
        {
            // Non-generic type
            buffer.append(MyfacesUtils.getClassFromFullClass(type));
        }
    }

}
