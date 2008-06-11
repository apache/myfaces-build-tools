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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Store metadata about a class that is either a JSF UIComponent, or some base
 * class or interface that a UIComponent can be derived from.
 */
public class ComponentMeta extends ClassMeta implements 
    PropertyHolder, FacetHolder
{
    static private final Logger _LOG = Logger.getLogger(ComponentMeta.class
            .getName());

    private String _name;
    private String _description;
    private String _longDescription;
    private String _bodyContent;

    private String _type;
    private String _family;
    private String _rendererType;

    private String _tagClass;
    private String _tagHandler;
    private String _tagSuperclass;
    private Boolean _namingContainer;
    private Boolean _children;
    private Boolean _configExcluded;
    private String _serialuid;
    private String _implements;
    
    private Boolean _generatedComponentClass;
    private Boolean _generatedTagClass;
    
    private Boolean _template;
    
    protected Map _properties;
    protected Map _facets;

    /**
     * Write an instance of this class out as xml.
     */
    public static void writeXml(XmlWriter out, ComponentMeta cm)
    {
        out.beginElement("component");

        ClassMeta.writeXml(out, cm);

        out.writeElement("name", cm._name);
        out.writeElement("type", cm._type);
        out.writeElement("bodyContent", cm._bodyContent);
        out.writeElement("family", cm._family);
        out.writeElement("tagClass", cm._tagClass);
        out.writeElement("tagSuperclass", cm._tagSuperclass);
        out.writeElement("tagHandler", cm._tagHandler);
        out.writeElement("rendererType", cm._rendererType);
        out.writeElement("configExcluded", cm._configExcluded);

        out.writeElement("desc", cm._description);
        out.writeElement("longDesc", cm._longDescription);
        out.writeElement("serialuid", cm._serialuid);
        out.writeElement("implements", cm._implements);
        out.writeElement("generatedComponentClass", cm._generatedComponentClass);
        out.writeElement("generatedTagClass", cm._generatedTagClass);
        out.writeElement("template", cm._template);
        
        for (Iterator i = cm._properties.values().iterator(); i.hasNext();)
        {
            PropertyMeta prop = (PropertyMeta) i.next();
            PropertyMeta.writeXml(out, prop);
        }
        
        for (Iterator i = cm._facets.values().iterator(); i.hasNext();)
        {
            FacetMeta facet = (FacetMeta) i.next();
            FacetMeta.writeXml(out, facet);
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
        digester.addSetNext(newPrefix, "addComponent");

        ClassMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/type");
        digester.addBeanPropertySetter(newPrefix + "/bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/family");
        digester.addBeanPropertySetter(newPrefix + "/tagClass");
        digester.addBeanPropertySetter(newPrefix + "/tagSuperclass");
        digester.addBeanPropertySetter(newPrefix + "/tagHandler");
        digester.addBeanPropertySetter(newPrefix + "/rendererType");
        digester.addBeanPropertySetter(newPrefix + "/faceletRendererType");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/configExcluded");
        digester.addBeanPropertySetter(newPrefix + "/serialuid");
        digester.addBeanPropertySetter(newPrefix + "/implements");
        digester.addBeanPropertySetter(newPrefix + "/generatedComponentClass");
        digester.addBeanPropertySetter(newPrefix + "/generatedTagClass");
        digester.addBeanPropertySetter(newPrefix + "/template");
        
        PropertyMeta.addXmlRules(digester, newPrefix);
        FacetMeta.addXmlRules(digester, prefix);
    }

    /**
     * Constructor.
     */
    public ComponentMeta()
    {
        _properties = new LinkedHashMap();
        _facets = new LinkedHashMap();
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(ComponentMeta other)
    {
        //_name = ModelUtils.merge(this._name, other._name);
        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription,
                other._longDescription);

        _type = ModelUtils.merge(this._type, other._type);
        _family = ModelUtils.merge(this._family, other._family);
        _rendererType = ModelUtils.merge(this._rendererType,
                other._rendererType);
        
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
        //_tagClass = ModelUtils.merge(this._tagClass, other._tagClass);
        _tagHandler = ModelUtils.merge(this._tagHandler, other._tagHandler);
        _namingContainer = ModelUtils.merge(this._namingContainer,
                other._namingContainer);
        _children = ModelUtils.merge(this._children, other._children);

        ModelUtils.mergeProps(this, other);
        ModelUtils.mergeFacets(this, other);
        
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
     * Sets the JSF component type for this component.
     */
    public void setType(String componentType)
    {
        _type = componentType;
    }

    public String getType()
    {
        return _type;
    }

    /**
     * Sets the JSF component family for this component.
     */
    public void setFamily(String componentFamily)
    {
        _family = componentFamily;
    }

    public String getFamily()
    {
        return _family;
    }

    /**
     * Sets the renderer type for this component.
     */
    public void setRendererType(String rendererType)
    {
        _rendererType = rendererType;
    }

    public String getRendererType()
    {
        return _rendererType;
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
     * Specifies whether this component is a "naming container", ie whether it
     * adds its own clientId as a prefix onto the clientId of its child
     * components.
     */
    public void setNamingContainer(Boolean namingContainer)
    {
        _namingContainer = namingContainer;
    }

    public Boolean getNamingContainer()
    {
        return ModelUtils.defaultOf(_namingContainer, false);
    }

    public void setConfigExcluded(Boolean configExcluded)
    {
        _configExcluded = configExcluded;
    }

    public Boolean isConfigExcluded()
    {
        return ModelUtils.defaultOf(_configExcluded,false);
    }
    
    public void setSerialuid(String serialuid)
    {
        _serialuid = serialuid;
    }

    public String getSerialuid()
    {
        return _serialuid;
    }
    

    public void setImplements(String implementsValue)
    {
        _implements = implementsValue;
    }

    public String getImplements()
    {
        return _implements;
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
    
    public void setTemplate(Boolean template)
    {
        _template = template;
    }

    public Boolean isTemplate()
    {
        return ModelUtils.defaultOf(_template,false);
    }

    /**
     * Specifies if the component supports child components.
     */
    public void setChildren(Boolean children)
    {
        _children = children;
    }

    public Boolean hasChildren()
    {
        return ModelUtils.defaultOf(_children, true);
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
    
    public void addFacet(FacetMeta prop)
    {
        _facets.put(prop.getName(), prop);
    }

    public Iterator facets()
    {
        return _facets.values().iterator();
    }

    public FacetMeta getFacet(String name)
    {
        return (FacetMeta) _facets.get(name);
    }
            
    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES
    
    public Collection getPropertyList(){
        return _properties.values();
    }
    
    public Collection getFacetList(){
        return _facets.values();
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
    
    private List _propertyComponentList = null; 
    
    public Collection getPropertyComponentList(){
        if (_propertyComponentList == null){
            _propertyComponentList = new ArrayList();
            for (Iterator it = _properties.values().iterator(); it.hasNext();){
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isInherited().booleanValue() && prop.isGenerated().booleanValue()){
                    _propertyComponentList.add(prop);
                }
            }
            
        }
        return _propertyComponentList;
    }
    
    
    /**
     * Returns the package part of the tag class
     * 
     * @return
     */
    public String getTagPackage(){
        return StringUtils.substring(getTagClass(), 0, StringUtils.lastIndexOf(getTagClass(), '.'));
    }

    //END
}
