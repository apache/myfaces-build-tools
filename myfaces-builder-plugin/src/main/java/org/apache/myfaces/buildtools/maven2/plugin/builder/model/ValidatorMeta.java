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
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Store metadata about a class that is either a JSF Validator, or some base
 * class or interface that a Validator can be derived from.
 */
public class ValidatorMeta extends ClassMeta implements PropertyHolder
{
    static private final Logger _LOG = Logger.getLogger(ValidatorMeta.class
            .getName());

    private String _description;
    private String _longDescription;

    private String _validatorId;
    private int _validatorClassModifiers;
    
    protected Map _properties;

    /**
     * Write an instance of this class out as xml.
     */
    public static void writeXml(XmlWriter out, ValidatorMeta vm)
    {
        out.beginElement("validator");

        ClassMeta.writeXml(out, vm);

        out.writeElement("validatorId", vm._validatorId);
        out.writeElement("desc", vm._description);
        out.writeElement("longDesc", vm._longDescription);

        for (Iterator i = vm._properties.values().iterator(); i.hasNext();)
        {
            PropertyMeta prop = (PropertyMeta) i.next();
            PropertyMeta.writeXml(out, prop);
        }

        
        out.endElement("validator");
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/validator";

        digester.addObjectCreate(newPrefix, ValidatorMeta.class);
        digester.addSetNext(newPrefix, "addValidator");

        ClassMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/validatorId");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        PropertyMeta.addXmlRules(digester, newPrefix);
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(ValidatorMeta other)
    {
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription,
                other._longDescription);

        _validatorId = ModelUtils.merge(this._validatorId, other._validatorId);

        ModelUtils.mergeProps(this, other);
        // TODO: _validatorClassMOdifiers
    }

    /**
     * Sets the validator identifer for this component.
     */
    public void setValidatorId(String validatorId)
    {
        _validatorId = validatorId;
    }

    public String getValidatorId()
    {
        return _validatorId;
    }

    /**
     * Adds a Java Language class modifier to the validator class.
     * <p>
     * TODO: what is this for?
     */
    public void addValidatorClassModifier(int modifier)
    {
        _validatorClassModifiers |= modifier;
    }

    /**
     * Returns the Java Language class modifiers for the validator class. By
     * default, these modifiers include Modifier.PUBLIC.
     * 
     * @return the Java Language class modifiers for the validator class
     */
    public int getValidatorClassModifiers()
    {
        int modifiers = _validatorClassModifiers;

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
    
    /**
     * Adds a property to this component.
     */
    public void addProperty(PropertyMeta property)
    {
        _properties.put(property.getName(), property);
    }

    public PropertyMeta getProperty(String propertyName)
    {
        return (PropertyMeta) _properties.get(propertyName);
    }

    /**
     * Number of properties for this component
     */
    public int propertiesSize()
    {
        return _properties.size();
    }

    /**
     * Returns true if this component has any properties.
     */
    public boolean hasProperties()
    {
        return _properties.size() > 0;
    }

    /**
     * Returns an iterator for all properties
     */
    public Iterator properties()
    {
        return _properties.values().iterator();
    }
    
    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES
    
    public Collection getPropertyList(){
        return _properties.values();
    }
    
}
