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
 */
public class PropertyModel
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
    public static void writeXml(XmlWriter out, PropertyModel pm)
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

        digester.addObjectCreate(newPrefix, PropertyModel.class);
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
     * Sets the name of this property.
     */
    public void setName(String name)
    {
        _name = name;
    }

    /**
     * Returns the name of this property.
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Sets the property class for this property.
     */
    public void setClassName(String className)
    {
        this._className = className;
    }

    /**
     * Returns the property class for this property.
     * 
     * @return the property class
     */
    public String getClassName()
    {
        return _className;
    }

    /**
     * Sets the transient flag of this property.
     * 
     * @param transient
     *            the property transient flag
     */
    public void setTransient(boolean transient_)
    {
        _transient = transient_;
    }

    /**
     * Returns transient flag of this property.
     * 
     * @return the property transient flag
     */
    public boolean isTransient()
    {
        return _transient;
    }

    /**
     * Sets the required flag of this property.
     * 
     * @param required
     *            the property required flag
     */
    public void setRequired(boolean required)
    {
        _required = required;
    }

    /**
     * Returns required flag of this property.
     * 
     * @return the property required flag
     */
    public boolean isRequired()
    {
        return _required;
    }

    /**
     * Sets the literalOnly flag of this property.
     * 
     * @param literalOnly
     *            the property literalOnly flag
     */
    public void setLiteralOnly(boolean literalOnly)
    {
        _literalOnly = literalOnly;
    }

    /**
     * Returns literalOnly flag of this property.
     * 
     * @return the property literalOnly flag
     */
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
     * 
     * @return true if this property is a method binding, otherwise false
     */
    public boolean isMethodBinding()
    {
        return ("javax.faces.el.MethodBinding".equals(getClassName()));
    }

    /**
     * Returns true if this property is a method binding.
     * 
     * @return true if this property is a method binding, otherwise false
     */
    public boolean isMethodExpression()
    {
        return ("javax.el.MethodExpression".equals(getClassName()));
    }
}
