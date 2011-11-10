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
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Store metadata about a class that is either a JSF Validator, or some base
 * class or interface that a Validator can be derived from.
 */
public class ValidatorMeta extends ViewEntityMeta implements PropertyHolder
{
    private String _validatorId;
    private int _validatorClassModifiers;
    
    private String _bodyContent;
    private String _tagClass;
    private String _tagSuperclass;
    private String _serialuidtag;
    private String _tagHandler;
    
    private Boolean _generatedComponentClass;
    private Boolean _generatedTagClass;
    private Boolean _configExcluded;
    
    private Boolean _evaluateELOnExecution;

    /**
     * Write an instance of this class out as xml.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        super.writeXmlSimple(out);

        out.writeElement("validatorId", _validatorId);
        out.writeElement("bodyContent", _bodyContent);
        out.writeElement("tagClass", _tagClass);
        out.writeElement("tagSuperclass", _tagSuperclass);
        out.writeElement("tagHandler", _tagHandler);
        out.writeElement("serialuidtag", _serialuidtag);
        out.writeElement("generatedComponentClass", _generatedComponentClass);
        out.writeElement("generatedTagClass", _generatedTagClass);
        out.writeElement("configExcluded", _configExcluded);
        out.writeElement("evaluateELOnExecution", _evaluateELOnExecution);
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

        ViewEntityMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/validatorId");
        digester.addBeanPropertySetter(newPrefix + "/bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/tagClass");
        digester.addBeanPropertySetter(newPrefix + "/tagSuperclass");
        digester.addBeanPropertySetter(newPrefix + "/tagHandler");
        digester.addBeanPropertySetter(newPrefix + "/serialuidtag");
        digester.addBeanPropertySetter(newPrefix + "/generatedComponentClass");
        digester.addBeanPropertySetter(newPrefix + "/generatedTagClass");
        digester.addBeanPropertySetter(newPrefix + "/configExcluded");
        digester.addBeanPropertySetter(newPrefix + "/evaluateELOnExecution");
    }
    
    public ValidatorMeta()
    {
        super("validator");
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
        super.merge(other);

        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);

        // inheritParentTag is true if the tag class to be generated for this
        // artifact extends the tag class generated for the parent artifact.
        // In this case, the tag for this class already inherits setter methods
        // from its parent that handle all the inherited properties, so the
        // tag class for this component just needs to handle its own properties.
        //
        // But when the tag class for this component does not extend the tag class
        // for the parent component (because the parent component does not have
        // a tag class) then we need to 
        boolean inheritParentTag = false;
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

        _tagHandler = ModelUtils.merge(this._tagHandler, other._tagHandler);
        _evaluateELOnExecution = ModelUtils.merge(this._evaluateELOnExecution, other._evaluateELOnExecution);
        
        _validatorId = ModelUtils.merge(this._validatorId, other._validatorId);

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
    
    /**
     * Specifies the class of the Facelets tag handler (component handler) for
     * this component.
     * <p>
     * Note that a Facelets tag handler class is not needed for most components.
     * 
     * @since 1.0.8
     */
    public void setTagHandler(String tagHandler)
    {
        _tagHandler = tagHandler;
    }

    /**
     * 
     * @since 1.0.8
     */
    public String getTagHandler()
    {
        return _tagHandler;
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
     * 
     * @since 1.0.8
     */
    public void setEvaluateELOnExecution(Boolean evaluateELOnExecution)
    {
        this._evaluateELOnExecution = evaluateELOnExecution;
    }

    /**
     * 
     * @since 1.0.8
     */
    public Boolean isEvaluateELOnExecution()
    {
        return ModelUtils.defaultOf(_evaluateELOnExecution,false);
    }

    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES

    // A transient attribute that is computed on-demand from the model data
    // but is not itself stored in the model.
    //
    // It holds a list of all the properties which need to be implemented
    // on the tag class. This is a subset of the properties available on
    // this component itself, and depends upon what the parent class
    // of the generated tag already supports.
    private List _propertyTagList = null; 
    
    public Collection getPropertyTagList()
    {
        if (_propertyTagList == null)
        {
            _propertyTagList = new ArrayList();
            for (Iterator it = getPropertyList().iterator(); it.hasNext();)
            {
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isTagExcluded().booleanValue() &&
                        !prop.isInheritedTag().booleanValue())
                {
                    _propertyTagList.add(prop);
                }
            }
            
        }
        return _propertyTagList;
    }

    private List _propertyValidatorList = null; 

    public Collection getPropertyValidatorList()
    {
        if (_propertyValidatorList == null)
        {
            _propertyValidatorList = new ArrayList();
            for (Iterator it = getPropertyList().iterator(); it.hasNext();)
            {
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isInherited().booleanValue() && prop.isGenerated().booleanValue())
                {
                    _propertyValidatorList.add(prop);
                }
            }
            
        }
        return _propertyValidatorList;
    }

}
