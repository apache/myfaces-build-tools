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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 */
public class ComponentMeta extends ModelItem
{
    static private final Logger _LOG = Logger.getLogger(ComponentMeta.class
            .getName());

    private String _name;
    private String _description;
    private String _longDescription;

    private String _type;
    private String _family;
    private String _rendererType;

    private String _tagClass;
    private String _tagHandler;
    private String _tagSuperclass;
    private boolean _namingContainer;
    private boolean _children = true;

    protected Map _properties;

    /**
     * Write an instance of this class out as xml.
     */
    public static void writeXml(XmlWriter out, ComponentMeta cm)
    {
        out.beginElement("component");

        ModelItem.writeXml(out, cm);

        out.writeElement("name", cm._name);
        out.writeElement("type", cm._type);
        out.writeElement("family", cm._family);
        out.writeElement("rendererType", cm._rendererType);

        out.writeElement("desc", cm._description);
        out.writeElement("longDesc", cm._longDescription);

        for (Iterator i = cm._properties.values().iterator(); i.hasNext();)
        {
            PropertyMeta prop = (PropertyMeta) i.next();
            PropertyMeta.writeXml(out, prop);
        }

        out.endElement("component");
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/component";

        digester.addObjectCreate(newPrefix, ComponentMeta.class);

        ModelItem.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/type");
        digester.addBeanPropertySetter(newPrefix + "/family");
        digester.addBeanPropertySetter(newPrefix + "/rendererType");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        PropertyMeta.addXmlRules(digester, newPrefix);
    }

    /**
     * Constructor.
     */
    public ComponentMeta()
    {
        _properties = new LinkedHashMap();
    }

    /**
     * Sets the name that the user will refer to instances of this component by.
     */
    public void setName(String name)
    {
        _name = name;
    }

    /**
     * Returns the name that the user will refer to instances of this component
     * by.
     * <p>
     * In JSP tags, this value will be used as the JSP tag name.
     * <p>
     * This property is optional; if not set then this Model instance represents
     * a base class that components can be derived from, but which cannot itself
     * be instantiated as a component.
     */
    public String getName()
    {
        return _name;
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

    /**
     * Sets the JSF component type for this component.
     * 
     * @param componentType
     *            the component type
     */
    public void setType(String componentType)
    {
        _type = componentType;
    }

    /**
     * Returns the JSF component type for this component.
     * 
     * @return the component type
     */
    public String getType()
    {
        return _type;
    }

    /**
     * Sets the component family for this component.
     * 
     * @param componentFamily
     *            the component family
     */
    public void setFamily(String componentFamily)
    {
        _family = componentFamily;
    }

    /**
     * Returns the component family for this component.
     * 
     * @return the component family
     */
    public String getFamily()
    {
        return _family;
    }

    /**
     * Sets the renderer type for this component.
     * 
     * @param rendererType
     *            the renderer type
     */
    public void setRendererType(String rendererType)
    {
        _rendererType = rendererType;
    }

    /**
     * Returns the renderer type for this component.
     * 
     * @return the renderer type
     */
    public String getRendererType()
    {
        return _rendererType;
    }

    /**
     * Sets the JSP tag handler class for this component.
     * 
     * @param tagClass
     *            the JSP tag handler class
     */
    public void setTagClass(String tagClass)
    {
        _tagClass = tagClass;
    }

    /**
     * Returns the JSP tag handler class for this component.
     * 
     * @return the JSP tag handler class
     */
    public String getTagClass()
    {
        return _tagClass;
    }

    /**
     * Sets the JSP tag handler superclass for this component.
     * 
     * @param tagSuperclass
     *            the JSP tag handler superclass
     */
    public void setTagSuperclass(String tagSuperclass)
    {
        _tagSuperclass = tagSuperclass;
    }

    /**
     * Returns the JSP tag handler superclass for this component.
     * 
     * @return the JSP tag handler superclass
     */
    public String getTagSuperclass()
    {
        return _tagSuperclass;
    }

    /**
     * Sets the Facelets tag handler (component handler) this component.
     * 
     * @param tagHandler
     *            the Facelets tag handler class
     */
    public void setTagHandler(String tagHandler)
    {
        _tagHandler = tagHandler;
    }

    /**
     * Returns the Facelets tag handler for this component
     * 
     * @return the Facelets tag handler
     */
    public String getTagHandler()
    {
        return _tagHandler;
    }

    /**
     * Sets the namingContainer flag of this property.
     * 
     * @param namingContainer
     *            the component namingContainer flag
     */
    public void setNamingContainer(boolean namingContainer)
    {
        _namingContainer = namingContainer;
    }

    /**
     * Returns namingContainer flag of this component.
     * 
     * @return the component namingContainer flag
     */
    public boolean isNamingContainer()
    {
        return _namingContainer;
    }

    /**
     * Specifies if the component can have children.
     * 
     * @param children
     *            true if the component can have children. false otherwise
     */
    public void setChildren(boolean children)
    {
        _children = children;
    }

    /**
     * Returns true if the component can have children.
     * 
     * @return true if the component can have children. false otherwise
     */
    public boolean hasChildren()
    {
        return _children;
    }

    /**
     * Adds a property to this component.
     * 
     * @param property
     *            the property to add
     */
    public void addProperty(PropertyMeta property)
    {
        _properties.put(property.getName(), property);
    }

    /**
     * Returns the property for this property name.
     * 
     * @param propertyName
     *            the property name to find
     */
    public PropertyMeta getProperty(String propertyName)
    {
        return (PropertyMeta) _properties.get(propertyName);
    }

    /**
     * Number of properties for this component
     * 
     * @return num of properties
     */
    public int propertiesSize()
    {
        return _properties.size();
    }

    /**
     * Returns true if this component has any properties.
     * 
     * @return true if this component has any properties, false otherwise
     */
    public boolean hasProperties()
    {
        return _properties.size() > 0;
    }

    /**
     * Returns an iterator for all properties
     * 
     * @return the property iterator
     */
    public Iterator properties()
    {
        return _properties.values().iterator();
    }
}
