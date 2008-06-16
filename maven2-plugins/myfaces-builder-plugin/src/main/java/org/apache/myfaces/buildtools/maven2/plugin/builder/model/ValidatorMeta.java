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
    
    private String _name;
    private String _bodyContent;
    private String _tagClass;
    private String _tagSuperclass;
    private String _serialuidtag;
    
    private Boolean _generatedComponentClass;
    private Boolean _generatedTagClass;
    private Boolean _configExcluded;
    
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
        out.writeElement("name", vm._name);        
        out.writeElement("bodyContent", vm._bodyContent);
        out.writeElement("tagClass", vm._tagClass);
        out.writeElement("tagSuperclass", vm._tagSuperclass);
        out.writeElement("serialuidtag", vm._serialuidtag);
        out.writeElement("generatedComponentClass", vm._generatedComponentClass);
        out.writeElement("generatedTagClass", vm._generatedTagClass);
        out.writeElement("configExcluded", vm._configExcluded);

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
        digester.addBeanPropertySetter(newPrefix + "/name");        
        digester.addBeanPropertySetter(newPrefix + "/bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/tagClass");
        digester.addBeanPropertySetter(newPrefix + "/tagSuperclass");
        digester.addBeanPropertySetter(newPrefix + "/serialuidtag");
        digester.addBeanPropertySetter(newPrefix + "/generatedComponentClass");
        digester.addBeanPropertySetter(newPrefix + "/generatedTagClass");
        digester.addBeanPropertySetter(newPrefix + "/configExcluded");
        
        PropertyMeta.addXmlRules(digester, newPrefix);
    }
    
    public ValidatorMeta()
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
    public void merge(ValidatorMeta other)
    {
        _name = ModelUtils.merge(this._name, other._name);
        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription,
                other._longDescription);

        boolean inheritParentTag = false;
        //check if the parent set a tag class
        if (other._tagClass != null)
        {
            //The tagSuperclass is the tagClass of the parent
            _tagSuperclass = ModelUtils.merge(this._tagSuperclass,
                    other._tagClass);
            inheritParentTag = true;
        }
        else
        {
            //The tagSuperclass is the tagSuperclass of the parent
            _tagSuperclass = ModelUtils.merge(this._tagSuperclass,
                    other._tagSuperclass);            
        }

        
        _validatorId = ModelUtils.merge(this._validatorId, other._validatorId);

        ModelUtils.mergeProps(this, other);
        // TODO: _validatorClassMOdifiers
        
        if (inheritParentTag)
        {
            for (Iterator i = this.properties(); i.hasNext();)
            {
                PropertyMeta srcProp = (PropertyMeta) i.next();
                PropertyMeta parentProp = other.getProperty(srcProp.getName());
                if (parentProp != null)
                {
                    //There are three possible behaviors:
                    //1. The property is defined on the child again and
                    //   the property was already on the tag hierarchy, so
                    //   inheritedTag must be set to TRUE.
                    //2. The property is defined on the child again and
                    //   it is necessary to write again on the generated
                    //   tag, so the annotation looks like
                    //   "@JSFProperty inheritedTag=false"
                    //   This condition must remain as FALSE
                    //3. The property is set by the user as true, but there
                    //   was not defined previously on the hierarchy, so
                    //   this condition must be as is (TRUE) 
                    //   (skipped on parentProp != null).
                    if (srcProp.isLocalInheritedTag() == null ||
                            srcProp.isInheritedTag().booleanValue())
                    {
                        srcProp.setInheritedTag(Boolean.TRUE);
                    }
                }
            }
            _propertyTagList = null;
        }        
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
    
    public void setGeneratedComponentClass(Boolean generatedComponentClass)
    {
        _generatedComponentClass = generatedComponentClass;
    }

    public Boolean isGeneratedComponentClass()
    {
        return ModelUtils.defaultOf(_generatedComponentClass,false);
    }

    public void setGeneratedTagClass(Boolean generatedTagClass)
    {
        _generatedTagClass = generatedTagClass;
    }

    public Boolean isGeneratedTagClass()
    {
        return ModelUtils.defaultOf(_generatedTagClass,false);
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

    private List _propertyValidatorList = null; 

    public Collection getPropertyValidatorList(){
        if (_propertyValidatorList == null){
            _propertyValidatorList = new ArrayList();
            for (Iterator it = _properties.values().iterator(); it.hasNext();){
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isInherited().booleanValue() && prop.isGenerated().booleanValue()){
                    _propertyValidatorList.add(prop);
                }
            }
            
        }
        return _propertyValidatorList;
    }

}
