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
 * Store metadata about a class that is either a JSF Behavior, or some base
 * class or interface that a Behavior can be derived from.
 * <p>
 * A behavior can be used in two ways:
 * <ul>
 * <li>instantiated via a facelet tag,
 * <li>referenced via its id
 * </ul>
 * @since 1.0.6
 */
public class BehaviorMeta extends ViewEntityMeta implements PropertyHolder
{
    private String _behaviorId;
    private int _behaviorClassModifiers;
    
    //Some behaviors has its own tag class, so it's necessary to
    //add some properties for this cases (f:convertNumber or f:convertDateTime)
    private String _bodyContent;
    private Boolean _configExcluded;
    
    private String _tagHandler;
    private Boolean _generatedComponentClass;
    private Boolean _evaluateELOnExecution;


    /**
     * Write an instance of this class out as xml.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        super.writeXmlSimple(out);
        out.writeElement("behaviorId", _behaviorId);
        out.writeElement("bodyContent", _bodyContent);
        out.writeElement("configExcluded", _configExcluded);
        out.writeElement("tagHandler", _tagHandler);
        out.writeElement("generatedComponentClass", _generatedComponentClass);
        out.writeElement("evaluateELOnExecution", _evaluateELOnExecution);
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/behavior";

        digester.addObjectCreate(newPrefix, BehaviorMeta.class);
        digester.addSetNext(newPrefix, "addBehavior");

        ViewEntityMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/behaviorId");
        digester.addBeanPropertySetter(newPrefix + "/bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/configExcluded");
        digester.addBeanPropertySetter(newPrefix + "/tagHandler");
        digester.addBeanPropertySetter(newPrefix + "/generatedComponentClass");
        digester.addBeanPropertySetter(newPrefix + "/evaluateELOnExecution");
    }
    
    public BehaviorMeta()
    {
        super("behavior");
    }
    
    public BehaviorMeta(String name)
    {
        super(name);
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     * 
     * Not used right now since theoretically there is very few inheritance
     * on behaviors
     * 
     */
    public void merge(BehaviorMeta other)
    {
        super.merge(other);
        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);

        boolean inheritParentTag = false;
        //check if the parent set a tag class

        _tagHandler = ModelUtils.merge(this._tagHandler, other._tagHandler);
        _evaluateELOnExecution = ModelUtils.merge(this._evaluateELOnExecution, other._evaluateELOnExecution);

        _behaviorId = ModelUtils.merge(this._behaviorId, other._behaviorId);
        
        // TODO: _behaviorClassMOdifiers
        
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
     * Sets the behavior identifier for this component.
     */
    public void setBehaviorId(String behaviorId)
    {
        _behaviorId = behaviorId;
    }

    public String getBehaviorId()
    {
        return _behaviorId;
    }

    /**
     * Adds a Java Language class modifier to the behavior class.
     * <p>
     * TODO: what is this for????
     */
    public void addBehaviorClassModifier(int modifier)
    {
        _behaviorClassModifiers |= modifier;
    }

    /**
     * Returns the Java Language class modifiers for the behavior class. By
     * default, these modifiers include Modifier.PUBLIC.
     * 
     * @return the Java Language class modifiers for the behavior class
     */
    public int getBehaviorClassModifiers()
    {
        int modifiers = _behaviorClassModifiers;

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

    public void setConfigExcluded(Boolean configExcluded)
    {
        _configExcluded = configExcluded;
    }

    public Boolean isConfigExcluded()
    {
        return ModelUtils.defaultOf(_configExcluded,false);
    }
    
    public String getBehaviorType()
    {
        return null;
    }
    
    /**
     * Specifies the class of the Facelets tag handler (component handler) for
     * this component.
     * <p>
     * Note that a Facelets tag handler class is not needed for most components.
     * 
     * @since 1.0.9
     */
    public void setTagHandler(String tagHandler)
    {
        _tagHandler = tagHandler;
    }

    /**
     * 
     * @since 1.0.9
     */
    public String getTagHandler()
    {
        return _tagHandler;
    }
    
    /**
     * 
     * @since 1.0.9
     */
    public void setEvaluateELOnExecution(Boolean evaluateELOnExecution)
    {
        this._evaluateELOnExecution = evaluateELOnExecution;
    }

    /**
     * 
     * @since 1.0.9
     */
    public Boolean isEvaluateELOnExecution()
    {
        return ModelUtils.defaultOf(_evaluateELOnExecution,false);
    }
    
    /**
     * 
     * @since 1.0.9
     */
    public void setGeneratedComponentClass(Boolean generatedComponentClass)
    {
        _generatedComponentClass = generatedComponentClass;
    }

    /**
     * 
     * @since 1.0.9
     */
    public Boolean isGeneratedComponentClass()
    {
        return ModelUtils.defaultOf(_generatedComponentClass,false);
    }

    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES
    
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

    private List _propertyBehaviorList = null; 

    public Collection getPropertyBehaviorList()
    {
        if (_propertyBehaviorList == null)
        {
            _propertyBehaviorList = new ArrayList();
            for (Iterator it = getPropertyList().iterator(); it.hasNext();)
            {
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isInherited().booleanValue() && prop.isGenerated().booleanValue())
                {
                    _propertyBehaviorList.add(prop);
                }
            }
            
        }
        return _propertyBehaviorList;
    }
}
