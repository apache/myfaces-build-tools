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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Stores info about all of the jsf artifacts in the system being processed.
 */
public class Model
{

    private List _components = new ArrayList(100);
    private List _converters = new ArrayList(100);
    private List _behaviors = new ArrayList(100);
    private List _validators = new ArrayList(100);
    private List _renderKits = new ArrayList(100);
    private List _tags = new ArrayList(100);
    private List _faceletTags = new ArrayList(100);
    private List _faceletFunctions = new ArrayList(100);
    private List _webConfigs = new ArrayList(10);

    private Map _componentsByClass = new TreeMap();
    private Map _convertersByClass = new TreeMap();
    private Map _behaviorsByClass = new TreeMap();
    private Map _validatorsByClass = new TreeMap();
    private Map _renderKitsById = new TreeMap();
    private Map _tagsByClass =  new TreeMap();
    private Map _faceletTagsByClass =  new TreeMap();
    private Map _componentsByTagClass = new TreeMap();
    private Map _faceletTagsByName = new TreeMap();
    private Map _faceletFunctionsByName = new TreeMap();
    private Map _webConfigsByModelId = new TreeMap();
    
    private Map _componentsByType = new TreeMap();
    
    private String _modelId;

    /**
     * Write this model out as xml.
     * <p>
     * Having a hand-coded method like this is not very elegant; it would be
     * better to do this via some library like Betwixt. However I'm not very
     * familiar with such libs, so hand-coding is quicker for now.
     */
    public static void writeXml(XmlWriter out, Model model)
    {
        out.beginElement("model");
        out.writeElement("modelId", model._modelId);

        for (Iterator i = model._components.iterator(); i.hasNext();)
        {
            ComponentMeta c = (ComponentMeta) i.next();
            c.writeXml(out);
        }

        for (Iterator i = model._converters.iterator(); i.hasNext();)
        {
            ConverterMeta c = (ConverterMeta) i.next();
            c.writeXml(out);
        }

        for (Iterator i = model._validators.iterator(); i.hasNext();)
        {
            ValidatorMeta c = (ValidatorMeta) i.next();
            c.writeXml(out);
        }
        
        for (Iterator i = model._behaviors.iterator(); i.hasNext();)
        {
            BehaviorMeta c = (BehaviorMeta) i.next();
            c.writeXml(out);
        }
        
        for (Iterator i = model._renderKits.iterator(); i.hasNext();)
        {
            RenderKitMeta c = (RenderKitMeta) i.next();
            RenderKitMeta.writeXml(out, c);
        }
        
        for (Iterator i = model._tags.iterator(); i.hasNext();)
        {
            TagMeta c = (TagMeta) i.next();
            c.writeXml(out);
        }
        
        for (Iterator i = model._faceletTags.iterator(); i.hasNext();)
        {
            FaceletTagMeta c = (FaceletTagMeta) i.next();
            c.writeXml(out);
        }
        
        for (Iterator i = model._webConfigs.iterator(); i.hasNext();)
        {
            WebConfigMeta c = (WebConfigMeta) i.next();
            c.writeXml(out);
        }
        
        for (Iterator i = model._faceletFunctions.iterator(); i.hasNext();)
        {
            FaceletFunctionMeta c = (FaceletFunctionMeta) i.next();
            c.writeXml(out);
        }

        out.endElement("model");
    }

    /**
     * Add digester rules to repopulate a Model instance from an xml file.
     * <p>
     * Having a hand-coded method like this is not very elegant; it would be
     * better to do this via some library like Betwixt. However I'm not very
     * familiar with such libs, so hand-coding is quicker for now.
     */
    public static void addXmlRules(Digester digester)
    {
        String prefix = "model";

        digester.addObjectCreate(prefix, Model.class);
        digester.addBeanPropertySetter(prefix + "/modelId");
        ComponentMeta.addXmlRules(digester, prefix);
        ConverterMeta.addXmlRules(digester, prefix);
        ValidatorMeta.addXmlRules(digester, prefix);
        BehaviorMeta.addXmlRules(digester, prefix);
        ClientBehaviorMeta.addXmlRules(digester, prefix);        
        RenderKitMeta.addXmlRules(digester, prefix);
        ClientBehaviorRendererMeta.addXmlRules(digester, prefix);
        TagMeta.addXmlRules(digester, prefix);
        FaceletTagMeta.addXmlRules(digester, prefix);
        WebConfigMeta.addXmlRules(digester, prefix);
        FaceletFunctionMeta.addXmlRules(digester, prefix);
    }
    
    /**
     * Adds all components from the other model to this model, because
     * only this info is necessary from construct a full model of 
     * components and build correctly faces-config.xml, .tld, and
     * component and tag classes.
     * 
     * @param other
     */
    public void merge(Model other)
    {
        for (Iterator it = other.getComponents().iterator(); it.hasNext();)
        {
            ComponentMeta component = (ComponentMeta) it.next();
            //If the component is present, the actual takes precedence.
            if (this.findComponentByClassName(component.getClassName())== null)
            {
                this.addComponent(component);
            }
        }
        
        for (Iterator it = other.getConverters().iterator(); it.hasNext();)
        {
            ConverterMeta converter = (ConverterMeta) it.next();
            
            if (this.findConverterByClassName(converter.getClassName())== null)
            {
                this.addConverter(converter);
            }
        }
        
        for (Iterator it = other.getValidators().iterator(); it.hasNext();)
        {
            ValidatorMeta validator = (ValidatorMeta) it.next();
            
            if (this.findValidatorByClassName(validator.getClassName())== null)
            {
                this.addValidator(validator);
            }
        }
        
        for (Iterator it = other.getBehaviors().iterator(); it.hasNext();)
        {
            BehaviorMeta behavior = (BehaviorMeta) it.next();
            
            if (this.findBehaviorByClassName(behavior.getClassName())== null)
            {
                this.addBehavior(behavior);
            }
        }
        
        for (Iterator it = other.getTags().iterator(); it.hasNext();)
        {
            TagMeta validator = (TagMeta) it.next();
            
            if (this.findTagByClassName(validator.getClassName())== null)
            {
                this.addTag(validator);
            }
        }
        
        for (Iterator it = other.getFaceletTags().iterator(); it.hasNext();)
        {
            FaceletTagMeta faceletTag = (FaceletTagMeta) it.next();
            
            if (this.findFaceletTagByClassName(faceletTag.getClassName())== null)
            {
                this.addFaceletTag(faceletTag);
            }
        }
        
        for (Iterator it = other.getFaceletFunctions().iterator(); it.hasNext();)
        {
            FaceletFunctionMeta faceletTag = (FaceletFunctionMeta) it.next();
            
            if (this.findFaceletFunctionByName(faceletTag.getName())== null)
            {
                this.addFaceletFunction(faceletTag);
            }
        }        
        
        for (Iterator it = other.getWebConfigs().iterator(); it.hasNext();)
        {
            WebConfigMeta webConfig = (WebConfigMeta) it.next();
            
            if (this.findWebConfigsByModelId(webConfig.getModelId())== null)
            {
                this.addWebConfig(webConfig);
            }
        }
    }

    /**
     * Adds a component to this faces config document.
     * 
     * @param component
     *            the component to add
     */
    public void addComponent(ComponentMeta component)
    {
        _components.add(component);
        _componentsByClass.put(component.getClassName(), component);
        if (null != component.getType())
        {
            _componentsByType.put(component.getType(), component);
        }
        if (null != component.getTagClass())
        {
            _componentsByTagClass.put(component.getTagClass(), component);
        }
    }

    /**
     * Returns all components
     */
    public List getComponents()
    {
        return _components;
    }

    /**
     * Returns an iterator for all components.
     */
    public Iterator components()
    {
        return _components.iterator();
    }

    public ComponentMeta findComponentByClassName(String className)
    {
        return (ComponentMeta) _componentsByClass.get(className);
    }

    public ComponentMeta findComponentByTagClassName(String className)
    {
        return (ComponentMeta) _componentsByTagClass.get(className);
    }
    
    public ComponentMeta findComponentByType(String componentType)
    {
        return (ComponentMeta) _componentsByType.get(componentType);
    }

    /**
     * Holds info about a JSF Converter definition
     */
    public void addConverter(ConverterMeta converter)
    {
        _converters.add(converter);
        _convertersByClass.put(converter.getClassName(), converter);
    }

    /**
     * Returns all converters
     */
    public List getConverters()
    {
        return _converters;
    }

    /**
     * Returns an iterator for all converters
     */
    public Iterator converters()
    {
        return _converters.iterator();
    }

    public ConverterMeta findConverterByClassName(String className)
    {
        return (ConverterMeta) _convertersByClass.get(className);
    }

    /**
     * Holds info about a JSF Behavior definition
     * @since 1.0.6
     */
    public void addBehavior(BehaviorMeta behavior)
    {
        _behaviors.add(behavior);
        _behaviorsByClass.put(behavior.getClassName(), behavior);
    }

    /**
     * Returns all behaviors
     * @since 1.0.6
     */
    public List getBehaviors()
    {
        return _behaviors;
    }

    /**
     * Returns an iterator for all behaviors
     * @since 1.0.6
     */
    public Iterator behaviors()
    {
        return _behaviors.iterator();
    }

    /**
     * 
     * @since 1.0.6
     */
    public BehaviorMeta findBehaviorByClassName(String className)
    {
        return (BehaviorMeta) _behaviorsByClass.get(className);
    }

    
    /**
     * Holds info about a JSF Converter definition
     */
    public void addValidator(ValidatorMeta validator)
    {
        _validators.add(validator);
        _validatorsByClass.put(validator.getClassName(), validator);
    }

    /**
     * Returns all validators
     */
    public List getValidators()
    {
        return _validators;
    }

    /**
     * Returns an iterator for all validators
     */
    public Iterator validators()
    {
        return _validators.iterator();
    }

    public ValidatorMeta findValidatorByClassName(String className)
    {
        return (ValidatorMeta) _validatorsByClass.get(className);
    }

    /**
     * Adds a render kit to this faces config document.
     * 
     * @param renderKit
     *            the render kit to add
     */
    public void addRenderKit(RenderKitMeta renderKit)
    {
        _renderKits.add(renderKit);
        _renderKitsById.put(renderKit.getRenderKitId(), renderKit);
    }

    public List getRenderKits()
    {
        return _renderKits;
    }

    /**
     * Returns an iterator for all render kits in this faces config.
     * 
     * @return the render kit iterator
     */
    public Iterator renderKits()
    {
        return _renderKits.iterator();
    }

    /**
     * Returns the render kit for this render kit id.
     * 
     * @param renderKitId
     *            the render kit id to find
     */
    public RenderKitMeta findRenderKitById(String id)
    {
        return (RenderKitMeta) _renderKitsById.get(id);
    }
    
    /**
     * Adds a tag to this faces config document.
     * 
     * @param tag
     *            the tag to add
     */
    public void addTag(TagMeta tag)
    {
        _tags.add(tag);
        _tagsByClass.put(tag.getClassName(), tag);
    }

    /**
     * Returns all tags
     */
    public List getTags()
    {
        return _tags;
    }

    /**
     * Returns an iterator for all tags.
     */
    public Iterator tags()
    {
        return _tags.iterator();
    }

    public TagMeta findTagByClassName(String className)
    {
        return (TagMeta) _tagsByClass.get(className);
    }
    
    /**
     * Adds a tag to this faces config document.
     * 
     * @since 1.0.4
     * @param tag
     *            the tag to add
     */
    public void addFaceletTag(FaceletTagMeta tag)
    {
        _faceletTags.add(tag);
        _faceletTagsByClass.put(tag.getClassName(), tag);
        if (tag.getName() != null)
        {
            _faceletTagsByName.put(tag.getName(), tag);
        }
    }

    /**
     * Returns all tags
     * @since 1.0.4
     */
    public List getFaceletTags()
    {
        return _faceletTags;
    }

    /**
     * Returns an iterator for all tags.
     * @since 1.0.4
     */
    public Iterator faceletTags()
    {
        return _faceletTags.iterator();
    }

    /**
     * @since 1.0.4
     */
    public FaceletTagMeta findFaceletTagByClassName(String className)
    {
        return (FaceletTagMeta) _faceletTagsByClass.get(className);
    }
    
    /**
     * @since 1.0.4
     */
    public FaceletTagMeta findFaceletTagByName(String name)
    {
        return (FaceletTagMeta) _faceletTagsByName.get(name);
    }
    
    /**
     * Adds a tag to this faces config document.
     * 
     * @since 1.0.10
     * @param tag
     *            the tag to add
     */
    public void addFaceletFunction(FaceletFunctionMeta tag)
    {
        _faceletFunctions.add(tag);
        if (tag.getName() != null)
        {
            _faceletFunctionsByName.put(tag.getName(), tag);
        }
    }

    /**
     * Returns all tags
     * @since 1.0.10
     */
    public List getFaceletFunctions()
    {
        return _faceletFunctions;
    }

    /**
     * Returns an iterator for all tags.
     * @since 1.0.10
     */
    public Iterator faceletFunctions()
    {
        return _faceletFunctions.iterator();
    }

    /**
     * @since 1.0.10
     */
    public FaceletFunctionMeta findFaceletFunctionByName(String name)
    {
        return (FaceletFunctionMeta) _faceletFunctionsByName.get(name);
    }

    
    /**
     * @since 1.0.4
     */
    public List getWebConfigs()
    {
        return _webConfigs;
    }

    /**
     * @since 1.0.4
     */
    public Iterator webConfigs()
    {
        return _webConfigs.iterator();
    }

    /**
     * @since 1.0.4
     */
    public WebConfigMeta findWebConfigsByModelId(String modelId)
    {
        return (WebConfigMeta) _webConfigsByModelId.get(modelId);
    }
    
    /**
     * @since 1.0.4
     */
    public void addWebConfig(WebConfigMeta config)
    {
        _webConfigs.add(config);
        _webConfigsByModelId.put(config.getModelId(), config);
    }

    public void setModelId(String modelId)
    {
        this._modelId = modelId;
    }

    /**
     * Obtain a value that indicate from where this model
     * comes from.
     * 
     * @return
     */
    public String getModelId()
    {
        return _modelId;
    }
    
    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES
    
    public List getWebConfigGroups(List modelIds)
    {
        Set<String> groups = new TreeSet<String>();
        for (Iterator it = _webConfigs.iterator(); it.hasNext();)
        {
            WebConfigMeta wcm = (WebConfigMeta) it.next();
            if (modelIds.contains(wcm.getModelId()))
            {
                for (Iterator it1 = wcm.getWebConfigParametersList().iterator(); it1.hasNext();)
                {
                    WebConfigParamMeta wcpm = (WebConfigParamMeta) it1.next();
                    
                    if (wcpm.getGroup() != null && wcpm.getGroup().length() > 0)
                    {
                        groups.add(wcpm.getGroup());
                    }
                }
            }
        }
        return new ArrayList(groups);
    }
}
