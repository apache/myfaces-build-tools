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
package org.apache.myfaces.buildtools.maven2.plugin.builder.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Base class for various metadata (model) classes.
 * <p>
 * This holds metadata common to all model classes that represent a jsf class,
 * eg a component, converter, validator. In all these cases, the class has a
 * type, a parent and optionally a list of implemented interfaces.
 */
public class ClassMeta
{
    private String _className;
    private String _parentClassName;
    private String _superClassName;
    
    private List _interfaceClassNames = new ArrayList();
    private String _modelId;
    private String _classSource;

    /**
     * Write this model out as xml.
     */
    public static void writeXml(XmlWriter out, ClassMeta mi)
    {
        out.writeElement("className", mi._className);
        out.writeElement("parentClassName", mi._parentClassName);
        out.writeElement("superClassName", mi._superClassName);
        out.writeElement("modelId", mi._modelId);
        out.writeElement("classSource", mi._classSource);

        if (!mi._interfaceClassNames.isEmpty())
        {
            out.beginElement("interfaces");
            for (Iterator i = mi._interfaceClassNames.iterator(); i.hasNext();)
            {
                String name = (String) i.next();
                out.beginElement("interface");
                out.writeAttr("name", name);
                out.endElement("interface");
            }
            out.endElement("interfaces");
        }
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        digester.addBeanPropertySetter(prefix + "/className");
        digester.addBeanPropertySetter(prefix + "/parentClassName");
        digester.addBeanPropertySetter(prefix + "/superClassName");
        digester.addBeanPropertySetter(prefix + "/classSource");
        digester.addBeanPropertySetter(prefix + "/modelId");
        digester.addCallMethod(prefix + "/interfaces/interface",
                "addInterfaceClassName", 1);
        digester.addCallParam(prefix + "/interfaces/interface", 0, "name");
    }

    /**
     * The fully-qualified name of the class that this metadata was extracted
     * from.
     */
    public String getClassName()
    {
        return _className;
    }

    public void setClassName(String className)
    {
        _className = className;
    }

    /**
     * The nearest relevant ancestor class of the class that this metadata was
     * extracted from.
     * <p>
     * For example, when a class is marked as a Component class, then this will
     * refer to the nearest ancestor class that is also marked as a Component
     * class.
     */
    public String getParentClassName()
    {
        return _parentClassName;
    }

    public void setParentClassName(String className)
    {
        _parentClassName = className;
    }

    /**
     * The superClassName is the fully qualified name of
     * the class that should be parent of the class representing
     * this component. The difference between this and 
     * the parentClassName is that the parent reference
     * the class that is a full component which is inside the
     * hierarchy and extends the properties. One useful example 
     * about why this semantic is important is use the abstract
     * pattern to generate classes. The abstract class is not
     * a full component (never instantiated, just a holder of
     * code) but usually parent class of the abstract
     * is a full component.
     * 
     */
    public void setSuperClassName(String superClassName)
    {
        this._superClassName = superClassName;
    }

    public String getSuperClassName()
    {
        if (_superClassName == null){
            //return the parent class name instead.
            return getParentClassName();
        }else{
            return _superClassName;            
        }        
    }

    /**
     * The list of relevant interface classes.
     * <p>
     * For example, when a class is marked as a Component class, then this will
     * refer to the list of interfaces which that class implements that are also
     * marked as a component.
     */
    public List getInterfaceClassNames()
    {
        return _interfaceClassNames;
    }

    public void setInterfaceClassNames(List classNames)
    {
        _interfaceClassNames = classNames;
    }

    public void addInterfaceClassName(String name)
    {
        _interfaceClassNames.add(name);
    }

    public void setModelId(String _modelId)
    {
        this._modelId = _modelId;
    }

    public String getModelId()
    {
        return _modelId;
    }

    public String getPackageName()
    {
        return StringUtils.substring(getClassName(), 0, StringUtils.lastIndexOf(getClassName(), '.'));
    }

    public void setClassSource(String classSource)
    {
        this._classSource = classSource;
    }

    public String getClassSource()
    {
        return _classSource;
    }
}
