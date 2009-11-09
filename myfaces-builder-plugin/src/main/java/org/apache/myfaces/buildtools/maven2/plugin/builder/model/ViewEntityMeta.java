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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;


/**
 * Base class for metadata about any class whose instances can be used in a
 * JSF view.
 * <p>
 * This means Components, Converters, Validators.
 */
public abstract class ViewEntityMeta extends ClassMeta implements PropertyHolder
{
    private String _name;
    private String _description;
    private String _longDescription;
    private Map _properties = new LinkedHashMap();

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        ClassMeta.addXmlRules(digester, prefix);

        digester.addBeanPropertySetter(prefix + "/name");
        digester.addBeanPropertySetter(prefix + "/desc", "description");
        digester.addBeanPropertySetter(prefix + "/longDesc",
                "longDescription");
        PropertyMeta.addXmlRules(digester, prefix);
    }

    /**
     * Constructor.
     */
    public ViewEntityMeta(String xmlElementName)
    {
        super(xmlElementName);
    }

    /**
     * Implement callback method to write out the "simple" properties of
     * this class as xml.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        super.writeXmlSimple(out);

        out.writeElement("name", _name);
    }

    /**
     * Implement callback method to write out the "complex" properties of
     * this class as xml.
     */
    protected void writeXmlComplex(XmlWriter out)
    {
        super.writeXmlComplex(out);
        out.writeElement("desc", _description);
        out.writeElement("longDesc", _longDescription);
        for (Iterator i = _properties.values().iterator(); i.hasNext();)
        {
            PropertyMeta prop = (PropertyMeta) i.next();
            PropertyMeta.writeXml(out, prop);
        }
    }

    protected void merge(ViewEntityMeta other)
    {
        super.merge(other);
        // name cannot be merged
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription,
                other._longDescription);
        ModelUtils.mergeProps(this, other);
    }


    /**
     * Sets the name that the user will refer to instances of this component by.
     * <p>
     * In JSP tags, this value will be used as the JSP tag name.
     * <p>
     * This property is optional; if not set then this Model instance represents
     * a base class that components can be derived from, but which cannot itself
     * be instantiated as a component.
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

    public Map getProperties()
    {
        return _properties;
    }

    public Collection getPropertyList()
    {
        return _properties.values();
    }

    /**
     * Returns an iterator for all properties
     */
    public Iterator properties()
    {
        return _properties.values().iterator();
    }
}