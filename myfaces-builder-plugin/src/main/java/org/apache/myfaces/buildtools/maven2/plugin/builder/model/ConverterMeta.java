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

import java.lang.reflect.Modifier;
import java.util.logging.Logger;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Represent a JSF converter.
 * <p>
 * A converter can be used in two ways: (a) referenced via its id, or (b)
 * instantiated via a tag.
 */
public class ConverterMeta
{
    static private final Logger _LOG = Logger.getLogger(ConverterMeta.class
            .getName());

    private String _className;
    private String _description;
    private String _longDescription;

    private String _converterId;
    private String _converterClass;
    private String _converterSuperClass;
    private int _converterClassModifiers;

    /**
     * Write an instance of this class out as xml.
     */
    public static void writeXml(XmlWriter out, ConverterMeta cm)
    {
        out.beginElement("converter");

        out.writeElement("className", cm._className);
        out.writeElement("converterId", cm._converterId);
        out.writeElement("converterClass", cm._converterClass);
        out.writeElement("converterSuperClass", cm._converterSuperClass);
        out.writeElement("desc", cm._description);
        out.writeElement("longDesc", cm._longDescription);

        out.endElement("converter");
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/converter";

        digester.addObjectCreate(newPrefix, ConverterMeta.class);
        digester.addBeanPropertySetter(newPrefix + "/className");
        digester.addBeanPropertySetter(newPrefix + "/converterId");
        digester.addBeanPropertySetter(newPrefix + "/converterClass");
        digester.addBeanPropertySetter(newPrefix + "/converterSuperClass");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
    }

    /**
     * The name of the class that this metadata applies to.
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
     * Sets the converter identifer for this component.
     * 
     * @param converterId
     *            converter identifer
     */
    public void setConverterId(String converterId)
    {
        _converterId = converterId;
    }

    /**
     * Returns true if the converter identifier is specified, otherwise false.
     * 
     * @return true if the converter identifier is specified, otherwise false.
     */
    public boolean hasConverterId()
    {
        return (_converterId != null);
    }

    /**
     * Returns the converter identifier for this component.
     * 
     * @return the converter identifier
     */
    public String getConverterId()
    {
        return _converterId;
    }

    /**
     * Sets the converter class for this component.
     * 
     * @param converterClass
     *            the converter class
     */
    public void setConverterClass(String converterClass)
    {
        _converterClass = converterClass;
    }

    /**
     * Returns the converter class for this component.
     * 
     * @return the converter class
     */
    public String getConverterClass()
    {
        return _converterClass;
    }

    /**
     * Sets the converter super class for this component.
     * 
     * @param converterSuperClass
     *            the converter super class
     */
    public void setSuperClass(String converterSuperClass)
    {
        _converterSuperClass = converterSuperClass;
    }

    /**
     * Returns the converter super class for this component.
     * 
     * @return the converter super class
     */
    public String getConverterSuperClass()
    {
        return _converterSuperClass;
    }

    /**
     * Adds a Java Language class modifier to the converter class.
     * 
     * @param modifier
     *            the modifier to be added
     */
    public void addConverterClassModifier(int modifier)
    {
        _converterClassModifiers |= modifier;
    }

    /**
     * Returns the Java Language class modifiers for the converter class. By
     * default, these modifiers include Modifier.PUBLIC.
     * 
     * @return the Java Language class modifiers for the converter class
     */
    public int getConverterClassModifiers()
    {
        int modifiers = _converterClassModifiers;

        if (!Modifier.isPrivate(modifiers) && !Modifier.isProtected(modifiers)
                && !Modifier.isPublic(modifiers))
        {
            modifiers |= Modifier.PUBLIC;
        }

        return modifiers;
    }

    /**
     * Sets the brief description of this property.
     * <p>
     * This description is used in tooltips, etc.
     * 
     * @param description
     *            the property description
     */
    public void setDescription(String description)
    {
        _description = description;
    }

    /**
     * Returns the brief description of this property.
     * 
     * @return the property description
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * Sets the long description of this property.
     * 
     * @param longDescription
     *            the long property description
     */
    public void setLongDescription(String longDescription)
    {
        _longDescription = longDescription;
    }

    /**
     * Returns the long description of this property.
     * 
     * @return the long property description
     */
    public String getLongDescription()
    {
        return _longDescription;
    }
}
