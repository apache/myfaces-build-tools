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
package org.apache.myfaces.trinidadbuild.plugin.faces.parse;

import org.apache.myfaces.trinidadbuild.plugin.faces.util.CompoundIterator;

import javax.xml.namespace.QName;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * ComponentBean is a Java representation of the faces-config component
 * XML element.
 */
public class ComponentBean extends ObjectBean
{
  /**
   * Creates a new ComponentBean.
   */
  public ComponentBean()
  {
    _properties = new LinkedHashMap();
    _facets = new LinkedHashMap();
    _events = new LinkedHashMap();
  }

  /**
   * Sets the UIX2 local name for this component.
   *
   * @param localName  the local name
   *
   * @deprecated remove when "ui" package is gone
   */
  public void setLocalName(
    String localName)
  {
    _localName = localName;
  }

  /**
   * Returns the UIX2 local name for this component.
   *
   * @return  the local name
   */
  public String getLocalName()
  {
    return _localName;
  }

  /**
   * Sets the UIX2 node class for this component.
   *
   * @param nodeClass  the node class
   *
   * @deprecated remove when "ui" package is gone
   */
  public void setNodeClass(
    String nodeClass)
  {
    _nodeClass = nodeClass;
  }

  /**
   * Returns the UIX2 node class for this component.
   *
   * @return  the node class
   */
  public String getNodeClass()
  {
    return _nodeClass;
  }

  /**
   * Sets the component type for this component.
   *
   * @param componentType  the component type
   */
  public void setComponentType(
    String componentType)
  {
    _componentType = componentType;
  }

  /**
   * Returns the component type for this component.
   *
   * @return  the component type
   */
  public String getComponentType()
  {
    return _componentType;
  }

  /**
   * Sets the component family for this component.
   *
   * @param componentFamily  the component family
   */
  public void setComponentFamily(
    String componentFamily)
  {
    _componentFamily = componentFamily;
  }

  /**
   * Returns the component family for this component.
   *
   * @return  the component family
   */
  public String getComponentFamily()
  {
    return _componentFamily;
  }

  /**
   * Returns true if the component family exists for this component.
   *
   * @return  true  if the component family exists,
   *          false otherwise
   */
  public boolean hasComponentFamily()
  {
    return (_componentFamily != null);
  }

  /**
   * Sets the component class for this component.
   *
   * @param componentClass  the component class
   */
  public void setComponentClass(
    String componentClass)
  {
    _componentClass = componentClass;
  }

  /**
   * Returns the component class for this component.
   *
   * @return  the component class
   */
  public String getComponentClass()
  {
    return _componentClass;
  }

  /**
   * Sets the description of this property.
   *
   * @param description  the property description
   */
  public void setDescription(
    String description)
  {
    _description = description;
  }

  /**
   * Returns the description of this property.
   *
   * @return  the property description
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Sets the long description of this property.
   *
   * @param longDescription  the long property description
   */
  public void setLongDescription(
    String longDescription)
  {
    _longDescription = longDescription;
  }

  /**
   * Returns the long description of this property.
   *
   * @return  the long property description
   */
  public String getLongDescription()
  {
    return _longDescription;
  }

  /**
   * Sets the JSP tag handler class for this component.
   *
   * @param tagClass  the JSP tag handler class
   */
  public void setTagClass(
    String tagClass)
  {
    _tagClass = tagClass;
  }

  /**
   * Sets the unsupported agents for this component.
   *
   * @param unsupportedAgents  the unsupported agents
   */
  public void setUnsupportedAgents(
    String[] unsupportedAgents)
  {
    if (unsupportedAgents == null)
      throw new NullPointerException("unsupportedAgents");

    _unsupportedAgents = unsupportedAgents;
  }

  /**
   * Returns unsupported agents for this component.
   *
   * @return  the unsupported agents
   */
  public String[] getUnsupportedAgents()
  {
    return _unsupportedAgents;
  }


  /**
   * Returns the JSP tag handler class for this component.
   *
   * @return  the JSP tag handler class
   */
  public String getTagClass()
  {
    return _tagClass;
  }

   /**
   * Sets the JSP tag handler superclass for this component.
   *
   * @param tagSuperclass  the JSP tag handler superclass
   */
  public void setTagSuperclass(
    String tagSuperclass)
  {
    _tagSuperclass = tagSuperclass;
  }

  /**
   * Returns the JSP tag handler superclass for this component.
   *
   * @return  the JSP tag handler superclass
   */
  public String getTagSuperclass()
  {
    return _tagSuperclass;
  }

  /**
  * Sets the Facelets tag handler (component handler) this component.
  *
  * @param tagHandler the Facelets tag handler class
  */
 public void setTagHandler(
   String tagHandler)
 {
   _tagHandler = tagHandler;
 }

 /**
  * Returns the Facelets tag handler for this component
  *
  * @return  the Facelets tag handler
  */
 public String getTagHandler()
 {
   return _tagHandler;
 }

  /**
   * Returns the JSP tag name for this component.
   *
   * @return  the JSP tag name
   */
  public QName getTagName()
  {
    return _tagName;
  }

  /**
   * Sets the JSP tag name for this component.
   *
   * @param tagName  the JSP tag name
   */
  public void setTagName(
    QName tagName)
  {
    _tagName = tagName;
  }

  /**
   * Sets the namingContainer flag of this property.
   *
   * @param namingContainer  the component namingContainer flag
   */
  public void setNamingContainer(
    boolean namingContainer)
  {
    _namingContainer = namingContainer;
  }

  /**
   * Returns namingContainer flag of this component.
   *
   * @return  the component namingContainer flag
   */
  public boolean isNamingContainer()
  {
    return _namingContainer;
  }

  /**
   * Sets the component supertype for this component.
   *
   * @param componentSupertype  the component super type
   */
  public void setComponentSupertype(
    String componentSupertype)
  {
    _componentSupertype = componentSupertype;
  }

  /**
   * Returns the component supertype for this component.
   *
   * @return  the component supertype
   */
  public String getComponentSupertype()
  {
    return _componentSupertype;
  }

  /**
   * Sets the component super class for this component.
   *
   * @param componentSuperclass  the component super class
   */
  public void setComponentSuperclass(
    String componentSuperclass)
  {
    _componentSuperclass = componentSuperclass;
  }

  /**
   * Returns the component super class for this component.
   *
   * @return  the component super class
   */
  public String getComponentSuperclass()
  {
    return _componentSuperclass;
  }

  /**
   * Sets the renderer type for this component.
   *
   * @param rendererType  the renderer type
   */
  public void setRendererType(
    String rendererType)
  {
    _rendererType = rendererType;
  }

  /**
   * Returns the renderer type for this component.
   *
   * @return  the renderer type
   */
  public String getRendererType()
  {
    return _rendererType;
  }

  /**
   * Returns the default renderer type for this component.
   *
   * @return  the default renderer type
   */
  public String getDefaultRendererType()
  {
    ComponentBean parent = resolveSupertype();
    return (parent != null) ? parent.findRendererType() : null;
  }

  public String getImplementationType()
  {
    return _implementationType;
  }

  public void setImplementationType(String implementationType)
  {
    _implementationType = implementationType;
  }


  /**
   * Adds a property to this component.
   *
   * @param property  the property to add
   */
  public void addProperty(
    PropertyBean property)
  {
    _properties.put(property.getPropertyName(), property);
  }

  /**
   * Returns the property for this property name.
   *
   * @param propertyName  the property name to find
   */
  public PropertyBean findProperty(
    String propertyName)
  {
    return (PropertyBean)_properties.get(propertyName);
  }

  /**
   * Returns the property for this property name.
   *
   * @param propertyName  the property name to find
   */
  public PropertyBean findProperty(
    String propertyName,
    boolean flatten)
  {
    PropertyBean prop = findProperty(propertyName);
    if (prop == null && flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        prop = parent.findProperty(propertyName, flatten);
    }

    return prop;
  }


  /**
   * Returns true if this component has any properties.
   *
   * @return  true   if this component has any properties,
   *          false  otherwise
   */
  public boolean hasProperties()
  {
    return hasProperties(false);
  }

  /**
   * Returns true if this component or any component supertype
   * has any properties.
   *
   * @return  true   if this component or any supertype has any properties,
   *          false  otherwise
   */
  public boolean hasProperties(
    boolean flatten)
  {
    boolean hasProperties = !_properties.isEmpty();

    if (!hasProperties && flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        hasProperties |= parent.hasProperties(flatten);
    }

    return hasProperties;
  }

  /**
   * Returns an iterator for all properties on this component only.
   *
   * @return  the property iterator
   */
  public Iterator properties()
  {
    return _properties.values().iterator();
  }

  /**
   * Returns an iterator for properties on this component.
   *
   * @param flatten  true   if the iterator should be a combined list of
   *                        properties of this component and its supertype,
   *                 false  otherwise
   *
   * @return  the property iterator
   */
  public Iterator properties(
    boolean flatten)
  {
    Iterator properties = properties();
    if (flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        properties = new CompoundIterator(parent.properties(true), properties);
    }
    return properties;
  }

 /**
  * Number of properties for this component
  * @return num of properties
  */
  public int propertiesSize()
  {
    return _properties.size();
  }

  /**
   * Adds a facet to this component.
   *
   * @param facet  the facet to add
   */
  public void addFacet(
    FacetBean facet)
  {
    _facets.put(facet.getFacetName(), facet);
  }

  /**
   * Returns the facet for this facet name.
   *
   * @param facetName  the facet name to find
   */
  public FacetBean findFacet(
    String facetName)
  {
    return (FacetBean)_facets.get(facetName);
  }


  public FacetBean findFacet(
    String facetName,
    boolean flatten)
  {
    FacetBean facet = findFacet(facetName);
    if (facet == null && flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        facet = parent.findFacet(facetName, flatten);
    }

    return facet;
  }

  /**
   * Returns true if this component has any facets.
   *
   * @return  true   if this component has any facets,
   *          false  otherwise
   */
  public boolean hasFacets()
  {
    return hasFacets(false);
  }

  /**
   * Returns true if this component or any component supertype
   * has any facets.
   *
   * @return  true   if this component or any supertype has any facets,
   *          false  otherwise
   */
  public boolean hasFacets(
    boolean flatten)
  {
    boolean hasFacets = !_facets.isEmpty();

    if (!hasFacets && flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        hasFacets |= parent.hasFacets(flatten);
    }

    return hasFacets;
  }

  /**
   * Returns an iterator for all facets on this component only.
   *
   * @return  the facet iterator
   */
  public Iterator facets()
  {
    return _facets.values().iterator();
  }

  /**
   * Returns an iterator for facets on this component.
   *
  * @param flatten  true   if the iterator should be a combined list of
  *                        facets of this component and its supertype,
  *                 false  otherwise
  *
  * @return  the facet iterator
  */
  public Iterator facets(
   boolean flatten)
  {
    Iterator facets = facets();
    if (flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        facets = new CompoundIterator(facets, parent.facets(true));
    }
    return facets;
  }

  /**
   * Specifies if the component can have children.
   *
   * @param children  true  if the component can have children.
   *                  false otherwise
   */
  public void setChildren(
    boolean children)
  {
    _children = children;
  }


  /**
   * Returns true if the component can have children.
   *
   * @return  true  if the component can have children.
   *          false otherwise
   */
  public boolean hasChildren()
  {
    return _children;
  }

  /**
   * Adds a Java Language class modifier to the component class.
   *
   * @param modifier  the modifier to be added
   */
  public void addComponentClassModifier(
    int modifier)
  {
    _componentClassModifiers |= modifier;
  }

  /**
   * Returns the Java Language class modifiers for the component class.
   * By default, these modifiers include Modifier.PUBLIC.
   *
   * @return  the Java Language class modifiers for the component class
   */
  public int getComponentClassModifiers()
  {
    int modifiers = _componentClassModifiers;

    if (!Modifier.isPrivate(modifiers) &&
        !Modifier.isProtected(modifiers) &&
        !Modifier.isPublic(modifiers))
    {
      modifiers |= Modifier.PUBLIC;
    }

    return modifiers;
  }

  public void parseComponentClassModifier(
    String modifier)
  {
    addComponentClassModifier(_parseModifier(modifier));
  }

  public void parseTagClassModifier(
    String modifier)
  {
    addTagClassModifier(_parseModifier(modifier));
  }

  private int _parseModifier(
    String text)
  {
    if ("public".equals(text))
      return Modifier.PUBLIC;
    else if ("protected".equals(text))
      return Modifier.PROTECTED;
    else if ("private".equals(text))
      return Modifier.PRIVATE;
    else if ("abstract".equals(text))
      return Modifier.ABSTRACT;
    else if ("final".equals(text))
      return Modifier.FINAL;

    throw new IllegalArgumentException("Unrecognized modifier: " + text);
  }

  /**
   * Parses the unsupported agents for this component into a String array
   * using space as the separator between values.
   *
   * @param unsupportedAgents  the unsupported agents
   */
  public void parseUnsupportedAgents(
    String unsupportedAgents)
  {
    setUnsupportedAgents(unsupportedAgents.split(" "));
  }
  
  /**
   * Adds a Java Language class modifier to the tag class.
   *
   * @param modifier  the modifier to be added
   */
  public void addTagClassModifier(
    int modifier)
  {
    _tagClassModifiers |= modifier;
  }

  /**
   * Returns the Java Language class modifiers for the tag class.
   * By default, these modifiers include Modifier.PUBLIC.
   *
   * @return  the Java Language class modifiers for the tag class
   */
  public int getTagClassModifiers()
  {
    int modifiers = _tagClassModifiers;

    if (!Modifier.isPrivate(modifiers) &&
        !Modifier.isProtected(modifiers) &&
        !Modifier.isPublic(modifiers))
    {
      modifiers |= Modifier.PUBLIC;
    }

    return modifiers;
  }

  /**
   * Adds an event to this component.
   *
   * @param eventRef  the event to add
   */
  public void addEvent(
    EventRefBean eventRef)
  {
    if (eventRef.getEventType() == null)
    {
      _LOG.warning("Missing event type in component \"" +
                   _componentType + "\"");
    }
    else
    {
      _events.put(eventRef.getEventType(), eventRef);
    }
  }

  /**
   * Returns true if this component has any events.
   *
   * @return  true   if this component has any events,
   *          false  otherwise
   */
  public boolean hasEvents()
  {
    return hasEvents(false);
  }

  /**
   * Returns true if this component or any component supertype
   * has any events.
   *
   * @return  true   if this component or any supertype has any events,
   *          false  otherwise
   */
  public boolean hasEvents(
    boolean flatten)
  {
    boolean hasEvents = !_events.isEmpty();

    if (!hasEvents && flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        hasEvents |= parent.hasEvents(flatten);
    }

    return hasEvents;
  }

  /**
   * Returns the event for this event name.
   *
   * @param eventName  the event name to find
   */
  public EventBean findEvent(
    String eventName)
  {
    return (EventBean)_events.get(eventName);
  }

  public EventBean findEvent(
    String eventName,
    boolean flatten)
  {
    EventBean event = findEvent(eventName);
    if (event == null && flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        event = parent.findEvent(eventName, flatten);
    }

    return event;
  }


  /**
   * Returns an iterator for all events on this component only.
   *
   * @return  the event iterator
   */
  public Iterator events()
  {
    return _events.values().iterator();
  }

  /**
   * Returns an iterator for events on this component.
   *
  * @param flatten  true   if the iterator should be a combined list of
  *                        events of this component and its supertype,
  *                 false  otherwise
  *
  * @return  the event iterator
  */
  public Iterator events(
   boolean flatten)
  {
    Iterator events = events();
    if (flatten)
    {
      ComponentBean parent = resolveSupertype();
      if (parent != null)
        events = new CompoundIterator(events, parent.events(true));
    }
    return events;
  }

  /**
   * Finds the component family in the component inheritance
   * hierarchy.
   *
   * @return  the component family
   */
  public String findComponentFamily()
  {
    if (_componentFamily != null)
      return _componentFamily;

    ComponentBean parent = resolveSupertype();
    return (parent != null) ? parent.findComponentFamily() : null;
  }

  /**
   * Finds the behavioral component component inheritance
   * hierarchy.
   *
   * @return  the behavioral component
   */
  public ComponentBean findBehavioralComponent()
  {
    if (_componentFamily != null)
      return this;

    ComponentBean component = resolveSupertype();
    return (component != null) ? component.findBehavioralComponent() : this;
  }

  /**
   * Finds the renderer type in the component inheritance
   * hierarchy.
   *
   * @return  the renderer type
   */
  public String findRendererType()
  {
    if (_rendererType != null)
      return _rendererType;

    ComponentBean parent = resolveSupertype();
    return (parent != null) ? parent.findRendererType() : null;
  }


  /**
   * Finds the component superclass in the component inheritance
   * hierarchy.
   *
   * @return  the component superclass
   */
  public String findComponentSuperclass()
  {
    if (_componentSuperclass != null)
      return _componentSuperclass;

    ComponentBean parent = resolveSupertype();
    return (parent != null) ? parent.findComponentClass()
                            : _TRINIDAD_COMPONENT_BASE;
  }

  /**
   * Finds the tag superclass in the component inheritance
   * hierarchy.
   *
   * @return  the tag superclass
   */
  public String findJspTagSuperclass()
  {
    if (_tagSuperclass != null)
      return _tagSuperclass;

    ComponentBean parent = resolveSupertype();
    return (parent != null) ? parent.findJspTagClass()
                            : _TRINIDAD_COMPONENT_TAG;
  }

  /**
   * Returns the component supertype instance.
   *
   * @return  the component supertype instance
   */
  public ComponentBean resolveSupertype()
  {
    if (_componentSupertype == null)
      return null;

    FacesConfigBean owner = getOwner();
    return (owner != null) ? owner.findComponent(_componentSupertype) : null;
  }

  /**
   * Checks if any of the component superclasses is UIXComponentBase
   */
  public boolean isTrinidadComponent()
  {
    String implementationType = getImplementationType();
    if (implementationType != null)
      return "trinidad".equals(implementationType);

    ComponentBean componentSupertype = resolveSupertype();
    if (componentSupertype != null)
    {
      if (_TRINIDAD_COMPONENT_BASE.equals(componentSupertype.getComponentClass()))
      {
        return true;
      }
      else
      {
        return componentSupertype.isTrinidadComponent();
      }
    }
    
    return false;
  }

  /**
   * Finds the component class in the component inheritance
   * hierarchy.
   *
   * @return  the component class
   */
  protected String findComponentClass()
  {
    if (_componentClass != null)
      return _componentClass;

    ComponentBean parent = resolveSupertype();
    return (parent != null) ? parent.findComponentClass() : null;
  }

  /**
   * Finds the tag class in the component inheritance
   * hierarchy.
   *
   * @return  the tag class
   */
  protected String findJspTagClass()
  {
    if (_tagClass != null)
      return _tagClass;

    ComponentBean parent = resolveSupertype();
    return (parent != null) ? parent.findJspTagClass() : null;
  }

  /**
   * Attaches the component and all event references.
   *
   * @param owner  the faces config owner
   */
  protected void attach(
    FacesConfigBean owner)
  {
    super.attach(owner);
    Iterator events = events(false);
    while (events.hasNext())
    {
      EventRefBean eventRef = (EventRefBean)events.next();
      eventRef.attach(owner);
    }
  }

  private String  _description;
  private String  _longDescription;
  private String  _componentType;
  private String  _componentFamily;
  private String  _componentClass;
  private String  _componentSupertype;
  private String  _componentSuperclass;
  private String  _rendererType;
  private String  _implementationType;
  private QName   _tagName;
  private String  _tagClass;
  private String  _tagHandler;
  private String  _tagSuperclass;
  private String  _localName;
  private String  _nodeClass;
  private boolean _namingContainer;
  private boolean _children = true;
  private Map     _properties;
  private Map     _facets;
  private Map     _events;
  private int     _componentClassModifiers;
  private int     _tagClassModifiers;
  private String[] _unsupportedAgents = new String[0];

  static private final String _TRINIDAD_COMPONENT_BASE =
                         "org.apache.myfaces.trinidad.component.UIXComponentBase";

  static private final String _TRINIDAD_COMPONENT_TAG =
                         "org.apache.myfaces.trinidad.webapp.UIXComponentTag";

  static private final Logger _LOG = Logger.getLogger(ComponentBean.class.getName());
}
