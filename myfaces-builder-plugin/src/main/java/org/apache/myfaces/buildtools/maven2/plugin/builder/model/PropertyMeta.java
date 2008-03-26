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

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Store metadata about a bean property of a component, converter or other JSF
 * artifact.
 * <p>
 * This metadata defines the attributes of JSP tags, among other things.
 */
public class PropertyMeta
{
    private String _name;
    private String _className;
    private boolean _required;
    private boolean _literalOnly;
    private boolean _transient;
    private String _description;
    private String _longDescription;

    /**
     * Write this model out as xml.
     */
    public static void writeXml(XmlWriter out, PropertyMeta pm)
    {
        out.beginElement("property");
        out.writeElement("name", pm._name);
        out.writeElement("className", pm._className);
        out.writeElement("required", pm._required);
        out.writeElement("literalOnly", pm._literalOnly);
        out.writeElement("transient", pm._transient);
        out.writeElement("desc", pm._description);
        out.writeElement("longDesc", pm._longDescription);
        out.endElement("property");
    }

    /**
     * Add digester rules to repopulate a Model instance from an xml file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/property";

        digester.addObjectCreate(newPrefix, PropertyMeta.class);
        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/className");
        digester.addBeanPropertySetter(newPrefix + "/required");
        digester.addBeanPropertySetter(newPrefix + "/literalOnly");
        digester.addBeanPropertySetter(newPrefix + "/transient");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
    }

    /**
     * Set the name that users refer to this property by.
     * <p>
     * This sets the name of xml tag attributes, and the base names of generated
     * getter/setter methods.
     */
    public void setName(String name)
    {
        _name = name;
    }

    public String getName()
    {
        return _name;
    }

    /**
     * Set the fully-qualified name of the type of this property.
     */
    public void setClassName(String className)
    {
        this._className = className;
    }

    public String getClassName()
    {
        return _className;
    }

    /**
     * Specify whether this property is transient or not.
     * <p>
     * Transient properties are not saved in the view state and are not restored
     * during the "restore view" phase.
     */
    public void setTransient(boolean transient_)
    {
        _transient = transient_;
    }

    public boolean isTransient()
    {
        return _transient;
    }

    /**
     * Specify whether this property is required, ie whether it is a syntax
     * error for someone to use a tag for a component with this property but not
     * explicitly provide a value for this property.
     */
    public void setRequired(boolean required)
    {
        _required = required;
    }

    public boolean isRequired()
    {
        return _required;
    }

    /**
     * Specify whether this property accepts only literal (constant) values, or
     * whether this property can be mapped to an EL expression.
     */
    public void setLiteralOnly(boolean literalOnly)
    {
        _literalOnly = literalOnly;
    }

    public boolean isLiteralOnly()
    {
        return _literalOnly;
    }

    public void setDescription(String desc)
    {
        _description = desc;
    }

    public String getDescription()
    {
        return _description;
    }

    public void setLongDescription(String desc)
    {
        _longDescription = desc;
    }

    public String getLongDescription()
    {
        return _longDescription;
    }

    /**
     * Returns true if this property is a method binding.
     * <p>
     * TODO: what is this for?
     */
    public boolean isMethodBinding()
    {
        return ("javax.faces.el.MethodBinding".equals(getClassName()));
    }

    /**
     * Returns true if this property is a method binding.
     * <p>
     * TODO: what is this for?
     */
    public boolean isMethodExpression()
    {
        return ("javax.el.MethodExpression".equals(getClassName()));
    }
}
