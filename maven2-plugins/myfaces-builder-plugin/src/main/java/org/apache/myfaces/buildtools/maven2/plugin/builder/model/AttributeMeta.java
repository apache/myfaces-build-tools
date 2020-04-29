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
 * Store metadata about a attribute of a jsp tag. 
 * <p>
 * This metadata defines the attributes of JSP tags, among other things.
 * 
 * Since this is a different concept from PropertyMeta, is is left as
 * another class (Maybe PropertyMeta must AttributeMeta but I'm not sure, since
 * there are different concepts (a property is for jsf, an attribute is for jsp)).
 */
public class AttributeMeta
{
    private String _name;
    private String _className;
    private Boolean _required;
    private String _description;
    private String _longDescription;
    private Boolean _rtexprvalue;
    private String _deferredValueType;
    private String _deferredMethodSignature;
    private Boolean _exclude;
    
    //Set on facelet merge
    private transient Boolean _faceletsOnly;

    public AttributeMeta()
    {
        
    }
    
    /**
      * @since 1.0.4
      **/
    public AttributeMeta(AttributeMeta am)
    {
        _name = am._name;
        _className = am._className;
        _required = am._required;
        _description = am._description;
        _longDescription = am._longDescription;
        _rtexprvalue = am._rtexprvalue;
        _deferredValueType = am._deferredValueType;
        _deferredMethodSignature = am._deferredMethodSignature;
        _exclude = am._exclude;
        _faceletsOnly = am._faceletsOnly;
    }
    
    /**
     * Write this model out as xml.
     */
    public static void writeXml(XmlWriter out, AttributeMeta am)
    {
        out.beginElement("attribute");
        out.writeElement("name", am._name);
        out.writeElement("className", am._className);
        out.writeElement("required", am._required);
        out.writeElement("rtexprvalue", am._rtexprvalue);
        out.writeElement("desc", am._description);
        out.writeElement("longDesc", am._longDescription);
        out.writeElement("deferredValueType", am._deferredValueType);
        out.writeElement("deferredMethodSignature", am._deferredMethodSignature);
        out.writeElement("exclude", am._exclude);
        out.writeElement("faceletsOnly", am._faceletsOnly);
        out.endElement("attribute");
    }

    /**
     * Add digester rules to repopulate a Model instance from an xml file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/attribute";

        digester.addObjectCreate(newPrefix, AttributeMeta.class);
        digester.addSetNext(newPrefix, "addAttribute");

        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/className");
        digester.addBeanPropertySetter(newPrefix + "/required");
        digester.addBeanPropertySetter(newPrefix + "/rtexprvalue");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/deferredValueType");
        digester.addBeanPropertySetter(newPrefix + "/deferredMethodSignature");
        digester.addBeanPropertySetter(newPrefix + "/exclude");
        digester.addBeanPropertySetter(newPrefix + "/faceletsOnly");
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(AttributeMeta other)
    {
        // don't merge className
        
        _name = ModelUtils.merge(this._name, other._name);
        _required = ModelUtils.merge(this._required, other._required);
        _rtexprvalue = ModelUtils.merge(this._rtexprvalue, other._rtexprvalue);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription, other._longDescription);
        _deferredValueType = ModelUtils.merge(this._deferredValueType, other._deferredValueType);
        _deferredMethodSignature = ModelUtils.merge(this._deferredMethodSignature, other._deferredMethodSignature);
        _exclude = ModelUtils.merge(this._exclude, other._exclude);
        _faceletsOnly = ModelUtils.merge(this._faceletsOnly, other._faceletsOnly);
    }
    
    /**
     * Copy all attributes in other to this instance.
     * 
     * @since 1.0.3
     * @param other
     */
    public void copy(AttributeMeta other)
    {
        _name = other._name;
        _required = other._required;
        _rtexprvalue = other._rtexprvalue;
        _description = other._description;
        _longDescription = other._longDescription;
        _deferredValueType = other._deferredValueType;
        _deferredMethodSignature = other._deferredMethodSignature;
        _exclude = other._exclude;
        _faceletsOnly = other._faceletsOnly;
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
     * Specify whether this property is required, ie whether it is a syntax
     * error for someone to use a tag for a component with this property but not
     * explicitly provide a value for this property.
     */
    public void setRequired(Boolean required)
    {
        _required = required;
    }

    public Boolean isRequired()
    {
        return ModelUtils.defaultOf(_required, false);
    }
    
    public void setRtexprvalue(Boolean rtexprvalue)
    {
        _rtexprvalue = rtexprvalue;
    }

    public Boolean isRtexprvalue()
    {
        return ModelUtils.defaultOf(_rtexprvalue, false);
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
     * @since 1.0.3
     */
    public void setDeferredValueType(String deferredValueType)
    {
        _deferredValueType = deferredValueType;
    }
    
    /**
     * Indicate the type that values should be
     * cast on tld. It is supposed that the className is
     * jakarta.el.ValueExpression to apply it. 
     *
     * @since 1.0.3
     */
    public String getDeferredValueType()
    {
        return _deferredValueType;
    }

    /**
     * @since 1.0.3
     */
    public void setDeferredMethodSignature(String deferredMethodSignature)
    {
        _deferredMethodSignature = deferredMethodSignature;
    }

    /**
     * Indicate the method signature that values should be
     * cast on tld. It is supposed that the className is
     * jakarta.el.MethodExpression to apply it. 
     *
     * @since 1.0.3
     */
    public String getDeferredMethodSignature()
    {
        return _deferredMethodSignature;
    }
    
    /**
     * @since 1.0.3
     */
    public Boolean isExclude()
    {
        return ModelUtils.defaultOf(_exclude, false);
    }
    
    /**
     * @since 1.0.3
     */
    public void setExclude(Boolean exclude)
    {
        _exclude = exclude;
    }
    
    /**
     * 
     * @since 1.0.6
     */
    public Boolean isFaceletsOnly()
    {
        return ModelUtils.defaultOf(_faceletsOnly, false);
    }

    /**
     * 
     * @since 1.0.6
     */
    public void setFaceletsOnly(Boolean faceletsOnly)
    {
        this._faceletsOnly = faceletsOnly;
    }
}
