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
package org.apache.myfaces.trinidadbuild.plugin.faces.util;

import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util
{
  static public String convertClassToSourcePath(
    String className,
    String fileExtension)
  {
    return (className.replace('.', File.separatorChar) + fileExtension);
  }

  static public boolean isFullClass(
    String className)
  {
    return (className != null && className.indexOf('.') != -1);
  }

  public static String getGenericsFromProperty(PropertyBean property){
      String gen = "";
      for (int i = 0; i < property.getAttributeClassParameters().length; i++) {
          if (i>0){
              gen += ",";
          }
          gen += getClassFromFullClass(property.getAttributeClassParameters()[i]);          
      }
      return (gen.length() > 0?"<"+gen+">":gen);
  }

  static public String getClassFromFullClass(
    String fullClass)
  {
    if (fullClass == null)
      return null;

    int lastSep = fullClass.lastIndexOf('.');
    // Note: this code also works for the empty package
    return (fullClass.substring(lastSep + 1));
  }

  static public String getPackageFromFullClass(
    String fullClass)
  {
    if (fullClass == null)
      return null;

    int lastSep = fullClass.lastIndexOf('.');

    // detect the empty package
    if (lastSep == -1)
      return "";

    return (fullClass.substring(0, lastSep));
  }

  static public String getConstantNameFromProperty(
    String propertyName)
  {
    return getConstantNameFromProperty(propertyName, null);
  }

  static public String getConstantNameFromProperty(
    String propertyName,
    String constantSuffix)
  {
    StringBuffer constantName = new StringBuffer();

    for (int i=0; i < propertyName.length(); i++)
    {
      char ch = propertyName.charAt(i);
      if (i > 0 &&
          Character.isUpperCase(ch) &&
          Character.isLowerCase(propertyName.charAt(i-1)))
      {
        constantName.append('_');
      }
      ch = Character.toUpperCase(ch);
      constantName.append(ch);
    }

    if (constantSuffix != null)
      constantName.append(constantSuffix);

    return constantName.toString();
  }

  static public String getPrefixedPropertyName(
    String prefix,
    String propertyName)
  {
    return prefix + Character.toUpperCase(propertyName.charAt(0)) +
           propertyName.substring(1);
  }
  
  static public String getPropertyClass(PropertyBean property)
  {
    String propertyFullClass = property.getPropertyClass();
    String propertyClass = Util.getClassFromFullClass(propertyFullClass);
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
    }
    
    return propertyClass;
  }

  static public String getMethodNameFromEvent(
    String methodPrefix,
    String eventName,
    String methodSuffix)
  {
    return methodPrefix +
           Character.toUpperCase(eventName.charAt(0)) +
           eventName.substring(1) +
           methodSuffix;
  }

  static public String getMethodReaderFromProperty(
    String propertyName,
    String propertyClass)
  {
    String methodPrefix = ("boolean".equals(propertyClass) ? "is" : "get");
    return getPrefixedPropertyName(methodPrefix, propertyName);
  }

  static public String getEventNameFromEventType(
    String eventFullClass)
  {
    String eventName = getClassFromFullClass(eventFullClass);
    return Character.toLowerCase(eventName.charAt(0)) +
           eventName.substring(1, eventName.length());
  }

  static public boolean isPrimitiveClass(
    String className)
  {
    return "boolean".equals(className) ||
           "byte".equals(className) ||
           "char".equals(className) ||
           "double".equals(className) ||
           "float".equals(className) ||
           "int".equals(className) ||
           "long".equals(className) ||
           "short".equals(className);
  }
  
  static public String getAlternatePropertyClass(PropertyBean property)
  {
    StringBuffer buffer = new StringBuffer(60);
    _buildPropertyClass(buffer, property.getAlternateClass());
    
    return buffer.toString();
  }

  static public String getBoxedClass(
    String className)
  {
    if ("boolean".equals(className))
      return "Boolean";
    else if ("byte".equals(className))
      return "Byte";
    else if ("char".equals(className))
      return "Character";
    else if ("double".equals(className))
      return "Double";
    else if ("float".equals(className))
      return "Float";
    else if ("int".equals(className))
      return "Integer";
    else if ("long".equals(className))
      return "Long";
    else if ("short".equals(className))
      return "Short";
    else
      return className;
  }

  static public String primitiveDefaultValue(String className)
  {
     if ("boolean".equals(className))
      return "false";
    else if ("byte".equals(className))
      return "0";
    else if ("char".equals(className))
      return "''";
    else if ("double".equals(className))
      return "0.0d";
    else if ("float".equals(className))
      return "0.0f";
    else if ("int".equals(className))
      return "0";
    else if ("long".equals(className))
      return "0L";
    else if ("short".equals(className))
      return "0";
    else
      return className;
  }

  static public String fill(
    String base,
    int    length)
  {
    if (base == null || base.length() > length)
      return base;

    StringBuffer filled = new StringBuffer(base);
    for (int i=base.length(); i < length; i++)
    {
      filled.append(' ');
    }
    return filled.toString();
  }

  static public String getVariableFromClass(
    String className)
  {
    if (className == null)
      return null;

    for (int i=0; i < className.length(); i++)
    {
      char ch = className.charAt(i);
      if (Character.isLowerCase(ch))
      {
        if (i > 0)
        {
          return Character.toLowerCase(className.charAt(i - 1)) +
                 className.substring(i);
        }
        break;
      }
    }

    throw new IllegalStateException("Class name \"" + className +
                                    "\" does not use initcaps");
  }

  static public String getVariableFromName(
    String name)
  {
    if (name == null)
      return null;

    if (RESERVED_WORDS.contains(name))
      name = name + "Param";

    return name;
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
      return value;
    }
  }

    static public String getDefaultValue(PropertyBean property){
        String propertyFullClass = property.getPropertyClass();
        String def = property.getDefaultValue();
        if (def == null || def.length() == 0){
            return null;
        }
        if (isPrimitiveClass(propertyFullClass)){
            return def;
        } else if ("java.lang.String".equals(propertyFullClass)){
            if (def.startsWith("\"") && def.endsWith("\"")){
                return def;
            } else {
                return "\""+def+"\"";
            }
        }
        return null;
    }

    static private void _buildPropertyClass(StringBuffer buffer, String type)
  {
    Matcher matcher = _GENERIC_TYPE.matcher(type);
    if(matcher.matches())
    {
      // Generic type
      buffer.append(Util.getClassFromFullClass(matcher.group(1)));
      buffer.append('<');
      String[] types = matcher.group(2).split(",");
      int max = types.length - 1;
      for(int i = 0; i <= max; i++)
      {
        _buildPropertyClass(buffer, types[i]);
        if(i < max)
        {
          buffer.append(", ");
        }
      }
      buffer.append('>');
    }
    else
    {
      // Non-generic type
      buffer.append(Util.getClassFromFullClass(type));
    }
  }

  static private Set _createPrimitiveTypesSet()
  {
    Set primitives = new TreeSet();
    for (int i=0; i < _PRIMITIVE_TYPES.length; i++)
    {
      String type = _PRIMITIVE_TYPES[i];
      primitives.add(type);
      primitives.add(type + "[]");
    }
    return Collections.unmodifiableSet(primitives);
  }

  static private Set _createReservedWordsSet()
  {
    Set reserved = new TreeSet();
    for (int i=0; i < _RESERVED_WORDS.length; i++)
    {
      String keyword = _RESERVED_WORDS[i];
      reserved.add(keyword);
    }
    return Collections.unmodifiableSet(reserved);
  }

  static private final String[] _PRIMITIVE_TYPES = new String[]
  {// TODO: Shouldn't java.lang.* be specified in that list as well?
    "boolean",
    "byte",
    "char",
    "float",
    "double",
    "int",
    "short",
    "long",
  };

  static private final String[] _RESERVED_WORDS = new String[]
  {
    "abstract",
    "assert",
    "boolean",
    "break",
    "byte",
    "case",
    "catch",
    "char",
    "class",
    "const",
    "continue",
    "default",
    "do",
    "double",
    "else",
    "extends",
    "final",
    "finally",
    "float",
    "for",
    "goto",
    "if",
    "implements",
    "import",
    "instanceof",
    "int",
    "interface",
    "long",
    "native",
    "new",
    "package",
    "private",
    "protected",
    "public",
    "return",
    "short",
    "static",
    "super",
    "switch",
    "synchronized",
    "this",
    "throw",
    "throws",
    "transient",
    "try",
    "void",
    "volatile",
    "while",
  };

  static public final Set RESERVED_WORDS = _createReservedWordsSet();
  static public final Set PRIMITIVE_TYPES = _createPrimitiveTypesSet();

  static private final Pattern _GENERIC_TYPE = Pattern.compile("([^<]+)<(.+)>");

  // no instances
  private Util()
  {
  }
}
