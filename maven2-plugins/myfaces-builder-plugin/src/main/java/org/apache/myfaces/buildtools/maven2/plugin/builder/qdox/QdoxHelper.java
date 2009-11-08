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
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.util.Map;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ClassMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FaceletTagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;

import com.thoughtworks.qdox.model.JavaClass;

/**
 * Helper class used by QdoxModelBuilder and provide utility methods. 
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class QdoxHelper
{

    /**
     * Convert a method name to a property name.
     */
    public static String methodToPropName(String methodName)
    {
        StringBuffer name = new StringBuffer();
        if (methodName.startsWith("get") || methodName.startsWith("set"))
        {
            name.append(methodName.substring(3));
        }
        else if (methodName.startsWith("is"))
        {
            name.append(methodName.substring(2));
        }
        else
        {
            throw new IllegalArgumentException("Invalid annotated method name "
                    + methodName);
        }
    
        // Handle following styles of property name
        // getfooBar --> fooBar
        // getFooBar --> fooBar
        // getURL --> url
        // getURLLocation --> urlLocation
        for (int i = 0; i < name.length(); ++i)
        {
            char c = name.charAt(i);
            if (Character.isUpperCase(c))
            {
                name.setCharAt(i, Character.toLowerCase(c));
            }
            else
            {
                if (i > 1)
                {
                    // reset the previous char to uppercase
                    c = name.charAt(i - 1);
                    name.setCharAt(i - 1, Character.toUpperCase(c));
                }
                break;
            }
        }
        return name.toString();
    }

    /**
     * Given the full javadoc for a component, extract just the "first
     * sentence".
     * <p>
     * Initially, just find the first dot, and strip out any linefeeds. Later,
     * try to handle "e.g." and similar (see javadoc algorithm for sentence
     * detection).
     */
    public static String getFirstSentence(String doc)
    {
        if (doc == null)
        {
            return null;
        }
    
        int index = doc.indexOf('.');
        if (index == -1)
        {
            return doc;
        }
        // abc.
        return doc.substring(0, index);
    }
    
    
    public static String getFullyQualifiedClassName(JavaClass clazz, String fqn)
    {
        //QDox 1.9 bug. getFullyQualifiedName does not resolve 
        //correctly classes like javax.servlet.jsp.tagext.TagSupport as parent
        //of a class with @JSFJspTag. The temporal solution is scan
        //the imports, looking for this type and if it is found replace it.
        //Fixed on 1.9.1, but better let the code as is 
        /*
        if (fqn.indexOf('.') == -1)
        {
            String [] imports = clazz.getSource().getImports();
            for (int i = 0; i < imports.length; i++)
            {
                if (imports[i].endsWith(fqn))
                {
                    fqn = imports[i];
                }
            }
        }*/
        return fqn;
    }
    
    /**
     * For each component, try to find its "logical" parent component,
     * ie the nearest superclass that is also annotated as a component
     * and therefore has an entry in the model.
     * <p>
     * In most cases this could be done at the time the component is
     * processed. The processClass() method does try to process the
     * classes that qdox discovers in ancestor->descendant order.
     * <p>
     * However there is one case where this just doesn't work. Therefore
     * a two-pass approach is used: first create a ComponentMeta for
     * each component, and then on a second pass find the matching
     * parent for each one.
     * <p>
     * The problem case is where an annotated java class extends a
     * generated one. In this case when walking up the ancestry tree of
     * the hand-written class we find an entry for which there is no
     * ComponentMeta entry. We do not know whether this is because the
     * parent exists but is not annotated, or whether a ComponentMeta
     * for that parent will be generated once we have processed some
     * other class that happens to have the matching annotation.
     */
    public static void initComponentAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);
            
            ComponentMeta parentComponent = model
                    .findComponentByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }

    /**
     * Same as initComponentAncestry but for validators.
     */
    public static void initValidatorAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);

            ValidatorMeta parentComponent = model
                    .findValidatorByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }
    
    /**
     * Same as initComponentAncestry but for converters
     */
    public static void initConverterAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);
            
            ConverterMeta parentComponent = model
                    .findConverterByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }
    
    public static void initFaceletTagHandlerAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);

            FaceletTagMeta parentComponent = model
                    .findFaceletTagByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }
    
    public static String evaluateParameterInitializationExpression(String value)
    {
        if (value.charAt(0) == '"' && value.charAt(value.length()-1) == '"' && value.indexOf('+') == -1)
        {
            return value.substring(1,value.length()-1);
        }            
        return value;
    }
}
