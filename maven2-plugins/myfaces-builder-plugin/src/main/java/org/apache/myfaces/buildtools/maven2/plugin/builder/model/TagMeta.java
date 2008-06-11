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

public class TagMeta extends ClassMeta implements AttributeHolder
{
    private String _name;
    private String _bodyContent;
    private String _description;
    private String _longDescription;
    private String _tagHandler;

    protected Map _attributes;
    
    /**
     * Write an instance of this class out as xml.
     */
    public static void writeXml(XmlWriter out, TagMeta tm)
    {
        out.beginElement("tag");

        ClassMeta.writeXml(out, tm);
        out.writeElement("name", tm._name);
        out.writeElement("bodyContent", tm._bodyContent);
        out.writeElement("desc", tm._description);
        out.writeElement("longDesc", tm._longDescription);
        out.writeElement("tagHandler", tm._tagHandler);

        for (Iterator i = tm._attributes.values().iterator(); i.hasNext();)
        {
            AttributeMeta prop = (AttributeMeta) i.next();
            AttributeMeta.writeXml(out, prop);
        }

        out.endElement("tag");
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/tag";

        digester.addObjectCreate(newPrefix, TagMeta.class);
        digester.addSetNext(newPrefix, "addTag");

        ClassMeta.addXmlRules(digester, newPrefix);
        digester.addBeanPropertySetter(newPrefix + "/name", "name");
        digester.addBeanPropertySetter(newPrefix + "/bodyContent", "bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/tagHandler");
        
        AttributeMeta.addXmlRules(digester, newPrefix);
    }

    /**
     * Constructor.
     */
    public TagMeta()
    {
        _attributes = new LinkedHashMap();
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(TagMeta other)
    {
        _name = ModelUtils.merge(this._name, other._name);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription,
                other._longDescription);
        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);
        _tagHandler = ModelUtils.merge(this._tagHandler, other._tagHandler);
        //TODO: Merge attributes
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
        
    public void setBodyContent(String bodyContent)
    {
        this._bodyContent = bodyContent;
    }

    public String getBodyContent()
    {
        return _bodyContent;
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
     * Specifies the class of the Facelets tag handler (component handler) for
     * this component.
     * <p>
     * Note that a Facelets tag handler class is not needed for most components.
     */
    public void setTagHandler(String tagHandler)
    {
        _tagHandler = tagHandler;
    }

    public String getTagHandler()
    {
        return _tagHandler;
    }
    
    /**
     * Adds a property to this component.
     */
    public void addAttribute(AttributeMeta attribute)
    {
        _attributes.put(attribute.getName(), attribute);
    }

    public AttributeMeta getAttribute(String attributeName)
    {
        return (AttributeMeta) _attributes.get(attributeName);
    }

    /**
     * Number of properties for this component
     */
    public int attributesSize()
    {
        return _attributes.size();
    }

    /**
     * Returns true if this component has any properties.
     */
    public boolean hasAttributes()
    {
        return _attributes.size() > 0;
    }

    /**
     * Returns an iterator for all properties
     */
    public Iterator attributes()
    {
        return _attributes.values().iterator();
    }
    
    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES
    
    public Collection getAttributeList(){
        return _attributes.values();
    }

}
