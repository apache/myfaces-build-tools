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
    private String _jspName;
    private String _fieldName;    
    private Boolean _required;
    private Boolean _literalOnly;
    private Boolean _transient;
    private Boolean _stateHolder;
    private Boolean _partialStateHolder;
    private String _description;
    private String _longDescription;
    private String   _defaultValue;
    private MethodSignatureMeta _signature;
    
    private Boolean _inherited; //Define if this property is inherited from parent component
    private Boolean _inheritedTag; //Define if this property is inherited from tag component
    private Boolean _tagExcluded; //Define if this property is excluded from tag and tld
    
    private Boolean _generated;
    
    private String _localMethodScope;
    private Boolean _localMethod; //generate method that returns local value without evaluate EL
    private String _setMethodScope;
    private Boolean _setMethod; //Generate method to define if is set a value or not
    
    private Boolean _rtexprvalue;
    private String _clientEvent;
    
    private String _deferredValueType;
    private Boolean _faceletsOnly;

    public PropertyMeta()
    {
        
    }
    
    public PropertyMeta(PropertyMeta pm)
    {
        _name = pm._name;
        _className = pm._className;
        _jspName = pm._jspName;
        _fieldName = pm._fieldName;    
        _required = pm._required;
        _literalOnly = pm._literalOnly;
        _transient = pm._transient;
        _stateHolder = pm._stateHolder;
        _description = pm._description;
        _longDescription = pm._longDescription;
        _defaultValue = pm._defaultValue;
        _signature = pm._signature;
        
        _inherited = pm._inherited;
        _inheritedTag = pm._inheritedTag;
        _tagExcluded = pm._tagExcluded;
        
        _generated = pm._generated;
        _localMethodScope = pm._localMethodScope;
        _localMethod = pm._localMethod;
        _setMethodScope = pm._setMethodScope;
        _setMethod = pm._setMethod;
        _rtexprvalue = pm._rtexprvalue;
        _deferredValueType = pm._deferredValueType;
        _clientEvent = pm._clientEvent;
        _partialStateHolder = pm._partialStateHolder;
        _faceletsOnly = pm._faceletsOnly;
    }
    
    /**
     * Write this model out as xml.
     */
    public static void writeXml(XmlWriter out, PropertyMeta pm)
    {
        out.beginElement("property");
        out.writeElement("name", pm._name);
        if (pm._jspName != null)
        {
            out.writeElement("jspName", pm._jspName);
        }
        if (pm._fieldName != null)
        {
            out.writeElement("fieldName", pm._fieldName);
        }
        out.writeElement("className", pm._className);
        out.writeElement("required", pm._required);
        out.writeElement("literalOnly", pm._literalOnly);
        out.writeElement("transient", pm._transient);
        out.writeElement("stateHolder", pm._stateHolder);
        out.writeElement("partialStateHolder", pm._partialStateHolder);
        out.writeElement("desc", pm._description);
        out.writeElement("longDesc", pm._longDescription);
        out.writeElement("defaultValue", pm._defaultValue);
        if (pm._signature != null)
        {
            MethodSignatureMeta.writeXml(out, pm._signature);
        }
        out.writeElement("inherited", pm._inherited);
        out.writeElement("inheritedTag", pm._inheritedTag);
        out.writeElement("tagExcluded", pm._tagExcluded);
        out.writeElement("generated",pm._generated);
        out.writeElement("localMethodScope", pm._localMethodScope);
        out.writeElement("localMethod", pm._localMethod);
        out.writeElement("setMethodScope", pm._setMethodScope);
        out.writeElement("setMethod", pm._setMethod);
        out.writeElement("rtexprvalue", pm._rtexprvalue);
        out.writeElement("deferredValueType", pm._deferredValueType);
        out.writeElement("clientEvent", pm._clientEvent);
        out.writeElement("faceletsOnly", pm._faceletsOnly);
        out.endElement("property");
    }

    /**
     * Add digester rules to repopulate a Model instance from an xml file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/property";

        digester.addObjectCreate(newPrefix, PropertyMeta.class);
        digester.addSetNext(newPrefix, "addProperty");

        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/jspName");
        digester.addBeanPropertySetter(newPrefix + "/fieldName");
        digester.addBeanPropertySetter(newPrefix + "/className");
        digester.addBeanPropertySetter(newPrefix + "/required");
        digester.addBeanPropertySetter(newPrefix + "/literalOnly");
        digester.addBeanPropertySetter(newPrefix + "/transient");
        digester.addBeanPropertySetter(newPrefix + "/stateHolder");
        digester.addBeanPropertySetter(newPrefix + "/partialStateHolder");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/defaultValue", "defaultValue");
        digester.addBeanPropertySetter(newPrefix + "/inherited", "inherited");
        digester.addBeanPropertySetter(newPrefix + "/inheritedTag", "inheritedTag");
        digester.addBeanPropertySetter(newPrefix + "/tagExcluded", "tagExcluded");
        digester.addBeanPropertySetter(newPrefix + "/generated", "generated");
        digester.addBeanPropertySetter(newPrefix + "/localMethodScope", "localMethodScope");
        digester.addBeanPropertySetter(newPrefix + "/localMethod", "localMethod");
        digester.addBeanPropertySetter(newPrefix + "/setMethodScope", "setMethodScope");
        digester.addBeanPropertySetter(newPrefix + "/setMethod", "setMethod");
        digester.addBeanPropertySetter(newPrefix + "/rtexprvalue", "rtexprvalue");
        digester.addBeanPropertySetter(newPrefix + "/deferredValueType", "deferredValueType");
        digester.addBeanPropertySetter(newPrefix + "/clientEvent", "clientEvent");
        digester.addBeanPropertySetter(newPrefix + "/faceletsOnly", "faceletsOnly");
        MethodSignatureMeta.addXmlRules(digester, newPrefix);
        
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(PropertyMeta other)
    {
        // Merge className does not harm, since you cannot
        //use polymorphism on a jsf component.  
        _className = ModelUtils.merge(this._className, other._className);
        
        _name = ModelUtils.merge(this._name, other._name);
        _jspName = ModelUtils.merge(this._jspName, other._jspName);
        _fieldName = ModelUtils.merge(this._fieldName, other._fieldName);
        _required = ModelUtils.merge(this._required, other._required);
        _literalOnly = ModelUtils.merge(this._literalOnly, other._literalOnly);
        _transient = ModelUtils.merge(this._transient, other._transient);
        _stateHolder = ModelUtils.merge(this._stateHolder, other._stateHolder);
        _partialStateHolder = ModelUtils.merge(this._partialStateHolder, other._partialStateHolder);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription, other._longDescription);
        _defaultValue = ModelUtils.merge(this._defaultValue, other._defaultValue);
        _signature = (MethodSignatureMeta) ModelUtils.merge(this._signature, other._signature);
        _generated = ModelUtils.merge(this._generated, other._generated);
        _localMethod = ModelUtils.merge(this._localMethod, other._localMethod);
        _localMethodScope = ModelUtils.merge(this._localMethodScope, other._localMethodScope);
        _setMethodScope = ModelUtils.merge(this._setMethodScope, other._setMethodScope);
        _setMethod = ModelUtils.merge(this._setMethod, other._setMethod);
        _tagExcluded = ModelUtils.merge(this._tagExcluded, other._tagExcluded);
        _rtexprvalue = ModelUtils.merge(this._rtexprvalue, other._rtexprvalue);
        _deferredValueType = ModelUtils.merge(this._deferredValueType, other._deferredValueType);
        _clientEvent = ModelUtils.merge(this._clientEvent, other._clientEvent);
        _faceletsOnly = ModelUtils.merge(this._faceletsOnly, other._faceletsOnly);
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
    public void setTransient(Boolean state)
    {
        _transient = state;
    }

    public Boolean isTransient()
    {
        return ModelUtils.defaultOf(_transient, false);
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

    /**
     * Specify whether this property accepts only literal (constant) values, or
     * whether this property can be mapped to an EL expression.
     */
    public void setLiteralOnly(Boolean literalOnly)
    {
        _literalOnly = literalOnly;
    }

    public Boolean isLiteralOnly()
    {
        return ModelUtils.defaultOf(_literalOnly, false);
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
     * Sets the default value of this attribute.
     *
     * @param defaultValue  the attribute default value
     */
    public void setDefaultValue(String defaultValue)
    {
      _defaultValue = defaultValue;
    }

    /**
     * Returns the default value of this attribute.
     *
     * @return  the attribute default value
     */
    public String getDefaultValue()
    {
      return _defaultValue;
    }
    
    /**
     * Sets the JSP name of this property.
     *
     * @param jspName  the JSP property name
     */
    public void setJspName(String jspName)
    {
      _jspName = jspName;
    }

    /**
     * Returns the JSP name of this property.
     *
     * @return  the JSP property name
     */
    public String getJspName()
    {
      if (_jspName == null)
      {
        return getName();
      }

      return _jspName;
    }

    /**
     * Sets the field name of this property, when not generating Trinidad components
     *
     * @param fieldName  the field property name
     */
    public void setFieldName(String fieldName)
    {
      _fieldName = fieldName;
    }

    /**
     * Returns the field name of this property, when not generating Trinidad components
     *
     * @return  the field property name
     */
    public String getFieldName()
    {
      if (_fieldName == null)
      {
        return "_"+getName();
      }

      return _fieldName;
    }
            
    /**
     * Sets the method binding signature of this property.
     *
     * @param signature  the method binding signature of this property
     */
    public void setMethodBindingSignature(
      MethodSignatureMeta signature)
    {
      _signature = signature;
    }

    /**
     * Returns the method binding signature of this property.
     *
     * @return the method binding signature of this property
     */
    public MethodSignatureMeta getMethodBindingSignature()
    {
      return _signature;
    }
    
    public void setInherited(Boolean inherited)
    {
        _inherited = inherited;
    }

    public Boolean isInherited()
    {
        return ModelUtils.defaultOf(_inherited, false);
    }

    public void setInheritedTag(Boolean inheritedTag)
    {
        _inheritedTag = inheritedTag;
    }

    /**
     * Returns true if this property was inherited from an ancestor component
     * which has no associated tag class.
     * <p>
     * When a tag class is generated for <i>this</i> component, then the tag
     * class will need to define a setter method to handle this property.
     * <p>
     * However properties which are inherited from an ancestor component that
     * does have a tag class will not need 
     * @param inheritedTag
     */
    public Boolean isInheritedTag()
    {
        return ModelUtils.defaultOf(_inheritedTag, false);
    }
    
    /**
     * 
     */
    public Boolean isLocalInheritedTag()
    {
        return _inheritedTag;
    }
    
    public void setTagExcluded(Boolean tagExcluded)
    {
        _tagExcluded = tagExcluded;
    }

    public Boolean isTagExcluded()
    {
        return ModelUtils.defaultOf(_tagExcluded, false);
    }
    
    public void setGenerated(Boolean generated)
    {
        _generated = generated;
    }

    /**
     * Indicates if the property should be generated
     * or not.
     * 
     * @return
     */
    public Boolean isGenerated()
    {
        return ModelUtils.defaultOf(_generated, false);
    }

    public void setStateHolder(Boolean stateHolder)
    {
        _stateHolder = stateHolder;
    }

    public Boolean isStateHolder()
    {
        return ModelUtils.defaultOf(_stateHolder, false);
    }

    public void setLocalMethodScope(String localMethodScope)
    {
        _localMethodScope = localMethodScope;
    }

    public String getLocalMethodScope()
    {
        return _localMethodScope == null ? "protected" : _localMethodScope;
    }

    public void setLocalMethod(Boolean localMethod)
    {
        _localMethod = localMethod;
    }

    public Boolean isLocalMethod()
    {
        return ModelUtils.defaultOf(_localMethod,false);
    }

    public void setSetMethodScope(String setMethodScope)
    {
        _setMethodScope = setMethodScope;
    }

    public String getSetMethodScope()
    {
        return _setMethodScope == null ? "protected" : _setMethodScope;
    }

    public void setSetMethod(Boolean setMethod)
    {
        _setMethod = setMethod;
    }
    
    public void setRtexprvalue(Boolean rtexprvalue)
    {
        _rtexprvalue = rtexprvalue;
    }

    public Boolean isRtexprvalue()
    {
        return _rtexprvalue;
    }
    

    public Boolean isSetMethod()
    {
        return ModelUtils.defaultOf(_setMethod,false);
    }

    /**
     * Returns true if this property is a method binding.
     * <p>
     * TODO: what is this for?
     */
    public boolean isMethodBinding()
    {
        return ("jakarta.faces.el.MethodBinding".equals(getClassName()));
    }

    /**
     * Returns true if this property is a method binding.
     * <p>
     * TODO: what is this for?
     */
    public boolean isMethodExpression()
    {
        return ("jakarta.el.MethodExpression".equals(getClassName()));
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
     * 
     * @since 1.0.4
     */
    public String getClientEvent()
    {
        return _clientEvent;
    }

    /**
     * 
     * @since 1.0.4
     */
    public void setClientEvent(String clientEvent)
    {
        this._clientEvent = clientEvent;
    }

    /**
     * 
     * @since 1.0.6
     */
    public void setPartialStateHolder(Boolean partialStateHolder)
    {
        _partialStateHolder = partialStateHolder;
    }

    /**
     * 
     * @since 1.0.6
     */
    public Boolean isPartialStateHolder()
    {
        return ModelUtils.defaultOf(_partialStateHolder, false);
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
        _faceletsOnly = faceletsOnly;
    }
}