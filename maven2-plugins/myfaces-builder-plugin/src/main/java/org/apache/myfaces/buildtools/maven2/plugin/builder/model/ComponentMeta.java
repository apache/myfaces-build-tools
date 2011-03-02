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

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Store metadata about a JSF UIComponent, or some base
 * class or interface that a UIComponent can be derived from.
 */
public class ComponentMeta extends ViewEntityMeta implements 
    PropertyHolder, FacetHolder, ListenerHolder
{
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
    private String _defaultEventName;
    private Boolean _overrideDefaultEventName;
    private Boolean _clientBehaviorHolder;
    private Boolean _composite;
    
    private String _ccLibraryName;
    private String _ccResourceName;
    
    private Boolean _generatedComponentClass;
    private Boolean _generatedTagClass;
    
    private Boolean _template;
    
    protected Map _facets;
    protected Map _listeners;
    protected List _implementedInterfaceClassNames;

    /**
     * Write an instance of this class out as xml.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        super.writeXmlSimple(out);

        out.writeElement("type", _type);
        out.writeElement("bodyContent", _bodyContent);
        out.writeElement("family", _family);
        out.writeElement("tagClass", _tagClass);
        out.writeElement("tagSuperclass", _tagSuperclass);
        out.writeElement("tagHandler", _tagHandler);
        out.writeElement("rendererType", _rendererType);
        out.writeElement("configExcluded", _configExcluded);
        out.writeElement("defaultEventName", _defaultEventName);
        out.writeElement("overrideDefaultEventName", _overrideDefaultEventName);
        out.writeElement("serialuid", _serialuid);
        out.writeElement("implements", _implements);
        out.writeElement("generatedComponentClass", _generatedComponentClass);
        out.writeElement("generatedTagClass", _generatedTagClass);
        out.writeElement("template", _template);
        out.writeElement("clientBehaviorHolder", _clientBehaviorHolder);
        out.writeElement("composite", _composite);
        out.writeElement("ccLibraryName", _ccLibraryName);
        out.writeElement("ccResourceName", _ccResourceName);
        
        for (Iterator i = _facets.values().iterator(); i.hasNext();)
        {
            FacetMeta facet = (FacetMeta) i.next();
            FacetMeta.writeXml(out, facet);
        }
        for (Iterator i = _listeners.values().iterator(); i.hasNext();)
        {
            ListenerMeta listener = (ListenerMeta) i.next();
            ListenerMeta.writeXml(out, listener);
        }
        
        if (!_implementedInterfaceClassNames.isEmpty())
        {
            out.beginElement("implementedInterfaces");
            for (Iterator i = _implementedInterfaceClassNames.iterator(); i.hasNext();)
            {
                String name = (String) i.next();
                out.beginElement("interface");
                out.writeAttr("name", name);
                out.endElement("interface");
            }
            out.endElement("implementedInterfaces");
        }
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

        ViewEntityMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/type");
        digester.addBeanPropertySetter(newPrefix + "/bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/family");
        digester.addBeanPropertySetter(newPrefix + "/tagClass");
        digester.addBeanPropertySetter(newPrefix + "/tagSuperclass");
        digester.addBeanPropertySetter(newPrefix + "/tagHandler");
        digester.addBeanPropertySetter(newPrefix + "/rendererType");
        digester.addBeanPropertySetter(newPrefix + "/defaultEventName");
        digester.addBeanPropertySetter(newPrefix + "/overrideDefaultEventName");
        digester.addBeanPropertySetter(newPrefix + "/faceletRendererType");
        digester.addBeanPropertySetter(newPrefix + "/configExcluded");
        digester.addBeanPropertySetter(newPrefix + "/serialuid");
        digester.addBeanPropertySetter(newPrefix + "/implements");
        digester.addBeanPropertySetter(newPrefix + "/generatedComponentClass");
        digester.addBeanPropertySetter(newPrefix + "/generatedTagClass");
        digester.addBeanPropertySetter(newPrefix + "/template");
        digester.addBeanPropertySetter(newPrefix + "/clientBehaviorHolder");
        digester.addBeanPropertySetter(newPrefix + "/composite");
        digester.addBeanPropertySetter(newPrefix + "/ccLibraryName");
        digester.addBeanPropertySetter(newPrefix + "/ccResourceName");

        FacetMeta.addXmlRules(digester, newPrefix);
        ListenerMeta.addXmlRules(digester, newPrefix);
        
        digester.addCallMethod(newPrefix + "/implementedInterfaces/interface",
                "addImplementedInterfaceClassName", 1);
        digester.addCallParam(newPrefix + "/implementedInterfaces/interface", 0, "name");
    }

    /**
     * Constructor.
     */
    public ComponentMeta()
    {
        super("component");
        _facets = new LinkedHashMap();
        _listeners = new LinkedHashMap();
        _implementedInterfaceClassNames = new ArrayList();
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(ComponentMeta other)
    {
        _bodyContent = ModelUtils.merge(this._bodyContent, other._bodyContent);

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
        _defaultEventName = ModelUtils.merge(this._defaultEventName, other._defaultEventName);
        _clientBehaviorHolder = ModelUtils.merge(this._clientBehaviorHolder, other._clientBehaviorHolder);
        _composite = ModelUtils.merge(this._composite, other._composite);
        _namingContainer = ModelUtils.merge(this._namingContainer,
                other._namingContainer);
        _children = ModelUtils.merge(this._children, other._children);

        ModelUtils.mergeProps(this, other);
        ModelUtils.mergeFacets(this, other);
        ModelUtils.mergeListeners(this, other);
        
        if (!other._implementedInterfaceClassNames.isEmpty())
        {
            this._implementedInterfaceClassNames.addAll(other._implementedInterfaceClassNames);
        }
        
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
    
    /**
     * @since 1.0.4
     */
    public void addListener(ListenerMeta prop)
    {
        _listeners.put(prop.getName(), prop);
    }

    /**
     * @since 1.0.4
     */
    public Iterator listeners()
    {
        return _listeners.values().iterator();
    }

    /**
     * @since 1.0.4
     */
    public ListenerMeta getListener(String name)
    {
        return (ListenerMeta) _listeners.get(name);
    }

    
    /**
     * 
     * @since 1.0.4
     */
    public String getDefaultEventName()
    {
        return _defaultEventName;
    }

    /**
     * 
     * @since 1.0.4
     */
    public void setDefaultEventName(String defaultEventName)
    {
        this._defaultEventName = defaultEventName;
    }

    /**
     * 
     * @since 1.0.5
     */
    public void setOverrideDefaultEventName(Boolean overrideDefaultEventName)
    {
        _overrideDefaultEventName = overrideDefaultEventName;
    }

    /**
     * Indicate if this component must override the method:
     * public String getDefaultEventName()
     * To return the value on defaultEventName 
     * 
     * @since 1.0.5
     */
    public Boolean isOverrideDefaultEventName()
    {
        return ModelUtils.defaultOf(_overrideDefaultEventName,false);
    }

    /**
     * 
     * @since 1.0.5
     */
    public void setClientBehaviorHolder(Boolean clientBehaviorHolder)
    {
        _clientBehaviorHolder = clientBehaviorHolder;
    }

    /**
     * 
     * @since 1.0.5
     */
    public Boolean isClientBehaviorHolder()
    {
        return ModelUtils.defaultOf(_clientBehaviorHolder,false);
    }
    
    /**
     * 
     * @since 1.0.5
     */
    public List getImplementedInterfaceClassNames()
    {
        return _implementedInterfaceClassNames;
    }

    /**
     * 
     * @since 1.0.5
     */
    public void setImplementedInterfaceClassNames(List classNames)
    {
        _implementedInterfaceClassNames = classNames;
    }

    /**
     * 
     * @since 1.0.5
     */
    public void addImplementedInterfaceClassName(String name)
    {
        _implementedInterfaceClassNames.add(name);
    }

    /**
     * 
     * @since 1.0.5
     */
    public Boolean isComposite()
    {
        return ModelUtils.defaultOf(_composite,false);
    }

    /**
     * 
     * @since 1.0.5
     */
    public void setComposite(Boolean composite)
    {
        _composite = composite;
    }

    /**
     * 
     * @since 1.0.9
     */
    public String getCcLibraryName()
    {
        return _ccLibraryName;
    }

    /**
     * 
     * @since 1.0.9
     */
    public void setCcLibraryName(String ccLibraryName)
    {
        this._ccLibraryName = ccLibraryName;
    }
    
    /**
     * 
     * @since 1.0.9
     */
    public String getCcResourceName()
    {
        return _ccResourceName;
    }

    /**
     * 
     * @since 1.0.9
     */
    public void setCcResourceName(String ccResourceName)
    {
        this._ccResourceName = ccResourceName;
    }

    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES
    
    public Collection getFacetList()
    {
        return _facets.values();
    }
    
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
    
    private List _propertyComponentList = null; 
    
    public Collection getPropertyComponentList()
    {
        if (_propertyComponentList == null)
        {
            _propertyComponentList = new ArrayList();
            for (Iterator it = getPropertyList().iterator(); it.hasNext();)
            {
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isInherited().booleanValue() && prop.isGenerated().booleanValue())
                {
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
    public String getTagPackage()
    {
        return StringUtils.substring(getTagClass(), 0, StringUtils.lastIndexOf(getTagClass(), '.'));
    }
    
    private Boolean _overrideEventNames;
    
    /**
     * 
     * @since 1.0.5
     */
    public Boolean isOverrideEventNames()
    {
        if (_overrideEventNames == null)
        {
            for (Iterator it = getPropertyList().iterator(); it.hasNext();)
            {
                PropertyMeta prop = (PropertyMeta) it.next();
                if (!prop.isInherited().booleanValue() && prop.getClientEvent() != null)
                {
                    _overrideEventNames = Boolean.TRUE;
                    break;
                }
            }
        }
        return ModelUtils.defaultOf(_overrideEventNames,false);
    }

}
