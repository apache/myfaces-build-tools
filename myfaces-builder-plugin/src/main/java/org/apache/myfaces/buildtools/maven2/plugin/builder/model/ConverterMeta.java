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
 * Store metadata about a class that is either a JSF Converter, or some base
 * class or interface that a Converter can be derived from.
 * <p>
 * A converter can be used in three ways:
 * <ul>
 * <li>instantiated via a tag,
 * <li>referenced via its id,
 * <li>implicitly used via its forClass property
 * </ul>
 */
public class ConverterMeta extends ClassMeta
{
    static private final Logger _LOG = Logger.getLogger(ConverterMeta.class
            .getName());

    private String _description;
    private String _longDescription;

    private String _converterId;
    private int _converterClassModifiers;

    /**
     * Write an instance of this class out as xml.
     */
    public static void writeXml(XmlWriter out, ConverterMeta cm)
    {
        out.beginElement("converter");

        ClassMeta.writeXml(out, cm);

        out.writeElement("converterId", cm._converterId);
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

        ClassMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/converterId");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
    }

    /**
     * Sets the converter identifer for this component.
     */
    public void setConverterId(String converterId)
    {
        _converterId = converterId;
    }

    public String getConverterId()
    {
        return _converterId;
    }

    /**
     * Adds a Java Language class modifier to the converter class.
     * <p>
     * TODO: what is this for????
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
     */
    public void setDescription(String description)
    {
        _description = description;
    }

    public String getDescription()
    {
        return _description;
    }

    /**
     * Sets the long description of this property.
     */
    public void setLongDescription(String longDescription)
    {
        _longDescription = longDescription;
    }

    public String getLongDescription()
    {
        return _longDescription;
    }
}
