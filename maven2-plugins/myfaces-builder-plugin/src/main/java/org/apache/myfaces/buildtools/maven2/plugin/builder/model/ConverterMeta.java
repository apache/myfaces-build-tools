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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class ConverterMeta extends ClassMeta implements PropertyHolder
{
    static private final Logger _LOG = Logger.getLogger(ConverterMeta.class
            .getName());

    private String _description;
    private String _longDescription;

    private String _converterId;
    private int _converterClassModifiers;
    
    //Some converters has its own tag class, so it's necessary to
    //add some properties for this cases (f:convertNumber or f:convertDateTime)
    private String _name;
    private String _bodyContent;
    private String _tagClass;
    private String _tagSuperclass;
    private String _serialuidtag;
    
    private Boolean _generatedTagClass;

    protected Map _properties;
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
        out.writeElement("name", cm._name);        
        out.writeElement("bodyContent", cm._bodyContent);
        out.writeElement("tagClass", cm._tagClass);
        out.writeElement("tagSuperclass", cm._tagSuperclass);
        out.writeElement("serialuidtag", cm._serialuidtag);
        out.writeElement("generatedTagClass", cm._generatedTagClass);

        for (Iterator i = cm._properties.values().iterator(); i.hasNext();)
        {
            PropertyMeta prop = (PropertyMeta) i.next();
            PropertyMeta.writeXml(out, prop);
        }
        
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
        digester.addSetNext(newPrefix, "addConverter");

        ClassMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/converterId");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/name");        
        digester.addBeanPropertySetter(newPrefix + "/bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/tagClass");
        digester.addBeanPropertySetter(newPrefix + "/tagSuperclass");
        digester.addBeanPropertySetter(newPrefix + "/serialuidtag");
        digester.addBeanPropertySetter(newPrefix + "/generatedTagClass");

        PropertyMeta.addXmlRules(digester, newPrefix);
    }
    
    public ConverterMeta()
    {
        _properties = new LinkedHashMap();        
    }
    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     * 
     * Not used right now since theorically there is very few inheritance
     * on converters
     * 
     */
    public void merge(ConverterMeta other)
    {
        _name = ModelUtils.merge(this._name, other._name);
        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);
        
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription,
                other._longDescription);

        _converterId = ModelUtils.merge(this._converterId, other._converterId);
        
        ModelUtils.mergeProps(this, other);
        // TODO: _converterClassMOdifiers
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
    
    public void setBodyContent(String bodyContent)
    {
        this._bodyContent = bodyContent;
    }

    public String getBodyContent()
    {
        return _bodyContent;
    }

    /**
     * Sets the JSP tag handler class for this component.
     */
    public void setTagClass(String tagClass)
    {
        _tagClass = tagClass;
    }

    public String getTagClass()
    {
        return _tagClass;
    }
    
    /**
     * Sets the JSP tag handler superclass for this component.
     */
    public void setTagSuperclass(String tagSuperclass)
    {
        _tagSuperclass = tagSuperclass;
    }

    public String getTagSuperclass()
    {
        return _tagSuperclass;
    }
    
    public void setSerialuidtag(String serialuidtag)
    {
        _serialuidtag = serialuidtag;
    }

    public String getSerialuidtag()
    {
        return _serialuidtag;
    }
    
    public void setGeneratedTagClass(Boolean generatedTagClass)
    {
        _generatedTagClass = generatedTagClass;
    }

    public Boolean isGeneratedTagClass()
    {
        return ModelUtils.defaultOf(_generatedTagClass,false);
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
    
    private List _propertyTagList = null; 
    
    public Collection getPropertyTagList(){
        if (_propertyTagList == null){
            _propertyTagList = new ArrayList();
            for (Iterator it = _properties.values().iterator(); it.hasNext();){
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isTagExcluded().booleanValue() &&
                        !prop.isInheritedTag().booleanValue()){
                    _propertyTagList.add(prop);
                }
            }
            
        }
        return _propertyTagList;
    }

}
