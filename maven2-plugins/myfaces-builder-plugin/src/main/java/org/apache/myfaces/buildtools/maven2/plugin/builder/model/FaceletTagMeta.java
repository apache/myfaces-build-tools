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
 * Store metadata about a JSF Facelet Tag Handler, or some base
 * class or interface that a Tag Handler can be derived from.
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FaceletTagMeta extends ClassMeta implements AttributeHolder
{
    private String _name;
    private String _bodyContent;
    private String _description;
    private String _longDescription;
    private String _componentClass;
    private String _converterClass;
    private String _behaviorClass;
    private String _validatorClass;
    private String _tagClass;
    private Boolean _configExcluded;

    protected Map _attributes;
    
    /**
     * Write an instance of this class out as xml.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        super.writeXmlSimple(out);

        out.writeElement("name", _name);
        out.writeElement("bodyContent", _bodyContent);
        out.writeElement("desc", _description);
        out.writeElement("longDesc", _longDescription);
        out.writeElement("componentClass", _componentClass);
        out.writeElement("tagClass", _tagClass);
        out.writeElement("converterClass", _converterClass);
        out.writeElement("behaviorClass", _behaviorClass);
        out.writeElement("validatorClass", _validatorClass);
        out.writeElement("configExcluded", _configExcluded);

        for (Iterator i = _attributes.values().iterator(); i.hasNext();)
        {
            AttributeMeta prop = (AttributeMeta) i.next();
            AttributeMeta.writeXml(out, prop);
        }
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/faceletTag";

        digester.addObjectCreate(newPrefix, FaceletTagMeta.class);
        digester.addSetNext(newPrefix, "addFaceletTag");

        ClassMeta.addXmlRules(digester, newPrefix);
        digester.addBeanPropertySetter(newPrefix + "/name", "name");
        digester.addBeanPropertySetter(newPrefix + "/bodyContent", "bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/componentClass", "componentClass");
        digester.addBeanPropertySetter(newPrefix + "/tagClass", "tagClass");
        digester.addBeanPropertySetter(newPrefix + "/converterClass", "converterClass");
        digester.addBeanPropertySetter(newPrefix + "/behaviorClass", "behaviorClass");
        digester.addBeanPropertySetter(newPrefix + "/validatorClass", "validatorClass");
        digester.addBeanPropertySetter(newPrefix + "/configExcluded");
        
        AttributeMeta.addXmlRules(digester, newPrefix);
    }

    /**
     * Constructor.
     */
    public FaceletTagMeta()
    {
        super("faceletTag");
        _attributes = new LinkedHashMap();
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(FaceletTagMeta other)
    {
        _name = ModelUtils.merge(this._name, other._name);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription,
                other._longDescription);
        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);
        ModelUtils.mergeAttributes(this, other);
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
        
    public String getComponentClass()
    {
        return _componentClass;
    }

    public void setComponentClass(String componentClass)
    {
        this._componentClass = componentClass;
    }

    public String getTagClass()
    {
        return _tagClass;
    }

    public void setTagClass(String tagClass)
    {
        this._tagClass = tagClass;
    }
    
    public String getConverterClass()
    {
        return _converterClass;
    }

    public void setConverterClass(String converterClass)
    {
        this._converterClass = converterClass;
    }

    public String getValidatorClass()
    {
        return _validatorClass;
    }

    public void setValidatorClass(String validatorClass)
    {
        this._validatorClass = validatorClass;
    }
    
    /**
     * 
     * @since 1.0.6
     */
    public String getBehaviorClass()
    {
        return _behaviorClass;
    }

    /**
     * 
     * @since 1.0.6
     */
    public void setBehaviorClass(String behaviorClass)
    {
        this._behaviorClass = behaviorClass;
    }
    
    public void setConfigExcluded(Boolean configExcluded)
    {
        _configExcluded = configExcluded;
    }

    public Boolean isConfigExcluded()
    {
        return ModelUtils.defaultOf(_configExcluded,false);
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
    
    public Collection getAttributeList()
    {
        return _attributes.values();
    }
}
