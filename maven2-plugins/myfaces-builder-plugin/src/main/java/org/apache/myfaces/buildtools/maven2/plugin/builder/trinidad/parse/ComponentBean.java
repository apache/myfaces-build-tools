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
package org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse;

import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.util.CompoundIterator;


/**
 * ComponentBean is a Java representation of the faces-config component
 * XML element.
 */
public class ComponentBean extends AbstractTagBean
{
  /**
   * Creates a new ComponentBean.
   */
  public ComponentBean()
  {
    super(true);
    _properties = new LinkedHashMap<String, PropertyBean>();
    _facets = new LinkedHashMap<String, FacetBean>();
    _events = new LinkedHashMap<String, EventRefBean>();
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
   * Sets the Java Script component class for this component.
   *
   * @param jsComponentClass  the JavaScript component class
   */
  public void setJsComponentClass(
    String jsComponentClass)
  {
    _jsComponentClass = jsComponentClass;
  }

  /**
   * Returns the JavaScript component class for this component.
   *
   * @return  the Java Script component class
   */
  public String getJsComponentClass()
  {
    return _jsComponentClass;
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

  public boolean isClientBehaviorHolder()
  {
    return _eventNames != null && _eventNames.length > 0;
  }

  public void setDefaultEventName(String defaultEventName)
  {
    this._defaultEventName = defaultEventName;
  }

  public String getDefaultEventName()
  {
    return _defaultEventName;
  }

  public void setEventNames(String[] eventNames)
  {
    this._eventNames = eventNames;
  }

  public String[] getEventNames()
  {
    return _eventNames;
  }

  public void parseEventNames(String value)
  {
    if (value != null)
    {
      String[] names = value.trim().split("\\s+");
      // combine event names if given more than once (to make importing from another XML file more easy)
      if (_eventNames == null)
      {
        _eventNames = names;
      }
      else
      {
        String[] currEventNames = _eventNames;
        _eventNames = new String[_eventNames.length + names.length];
        System.arraycopy(currEventNames, 0, _eventNames, 0, currEventNames.length);
        System.arraycopy(names, 0, _eventNames, currEventNames.length, names.length);
      }
    }
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
    return _properties.get(propertyName);
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
  public Iterator<PropertyBean> properties()
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
  public Iterator<PropertyBean> properties(
    boolean flatten)
  {
    if (flatten)
    {
      return getFlattenedProperties().values().iterator();
    }
    else
    {
      return properties();
    }
  }

  protected Map getFlattenedProperties()
  {
    Map props = new HashMap(_properties);
    ComponentBean parent = resolveSupertype();
    if (parent != null)
    {
      Map superProps = parent.getFlattenedProperties();
      for (Iterator<Map.Entry> iter = superProps.entrySet().iterator(); iter.hasNext(); )
      {
        Map.Entry entry = iter.next();
        if (!props.containsKey(entry.getKey()))
        {
          props.put(entry.getKey(), entry.getValue());
        }
      }
    }
    return props;
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
    return _facets.get(facetName);
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
  public Iterator<FacetBean> facets()
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
  public Iterator<FacetBean> facets(
   boolean flatten)
  {
    if (flatten)
    {
      return getFlattenedFacets().values().iterator();
    }
    else
    {
      return facets();
    }
  }

  protected Map getFlattenedFacets()
  {
    Map facets = new HashMap(_facets);
    ComponentBean parent = resolveSupertype();
    if (parent != null)
    {
      Map superFacets = parent.getFlattenedFacets();
      for (Iterator<Map.Entry> iter = superFacets.entrySet().iterator(); iter.hasNext(); )
      {
        Map.Entry entry = iter.next();
        if (!facets.containsKey(entry.getKey()))
        {
          facets.put(entry.getKey(), entry.getValue());
        }
      }
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
   * Parses the string of satisfied contracts into a String array
   * using space as the separator between values.
   * In the component metadata file, the satisfied contracts are noted
   * with satisfied-contracts markup.  As an example, af:popup
   * (oracle.adf.view.rich.component.rich.RichPopup) supports
   * oracle-adf-richmenu-holder, oracle-adf-richdialog-holder,
   * oracle-adf-richnotewindow-holder, and oracle-adf-richpanelwindow-holder
   * contracts.  The satisfied contracts of a given component are matched
   * with the required ancestor contracts of other components to determine
   * if a component hierarchy is legally assembled.
   *
   * @param satisfiedContracts  a space delimited string of satisifed contracts
   */
  public void parseSatisfiedContracts(
    String satisfiedContracts)
  {
    setSatisfiedContracts(satisfiedContracts.split(" "));
  }

  /**
   * Sets the possible values for this property.
   * In the component metadata file, the satisfied contracts are noted
   * with satisfied-contracts markup.  As an example, af:popup
   * (oracle.adf.view.rich.component.rich.RichPopup) supports
   * oracle-adf-richmenu-holder, oracle-adf-richdialog-holder,
   * oracle-adf-richnotewindow-holder, and oracle-adf-richpanelwindow-holder
   * contracts.  The satisfied contracts of a given component are matched
   * with the required ancestor contracts of other components to determine
   * if a component hierarchy is legally assembled.
   *
   * @param satisfiedContracts  a string array of the satisfied contracts
   */
  public void setSatisfiedContracts(
    String[] satisfiedContracts)
  {
    _satisfiedContracts = Arrays.asList(satisfiedContracts);
  }

  /**
   * Returns an iterator of the satisfied contracts for this component.
   * In the component metadata file, the satisfied contracts are noted
   * with satisfied-contracts markup.  As an example, af:popup
   * (oracle.adf.view.rich.component.rich.RichPopup) supports
   * oracle-adf-richmenu-holder, oracle-adf-richdialog-holder,
   * oracle-adf-richnotewindow-holder, and oracle-adf-richpanelwindow-holder
   * contracts.  The satisfied contracts of a given component are matched
   * with the required ancestor contracts of other components to determine
   * if a component hierarchy is legally assembled.
   *
   * @return  a java.util.Iterator of Strings, where each string is the name of a
   *          satisfied contract
   */
  public Iterator<String> satisfiedContracts()
  {
    return _satisfiedContracts.iterator();
  }

  /**
   * Returns true if this component has any satisfied contracts.
   *
   * @return  true   if this component has any satisfied contracts,
   *          false  otherwise
   */
  public boolean hasSatisfiedContracts()
  {
    return (!_satisfiedContracts.isEmpty());
  }

  /**
   * Parses the string of required ancestor contracts into a String array
   * using space as the separator between values.
   * In the component metadata file, the required ancestors are noted
   * with required-ancestor-contracts markup. This indicates that an
   * ancestor (e.g. parent or grandparent) tag must be have satisfied-contracts
   * metadata matching the required-ancestor-contracts metadata of this tag.
   * As an example, af:dialog
   * (oracle.adf.view.rich.component.rich.RichDialog) lists
   * oracle-adf-richdialog-holder as a required ancestor contract, and
   * af:popup (oracle.adf.view.rich.component.rich.RichPopup) lists
   * oracle-adf-richdialog-holder as a satisified contract.
   *
   * @param requiredAncestorContracts  a space delimited string of required ancestor contracts
   */
  public void parseRequiredAncestorContracts(
    String requiredAncestorContracts)
  {
    setRequiredAncestorContracts(requiredAncestorContracts.split(" "));
  }

  /**
   * Sets the possible values for this property.
   * In the component metadata file, the required ancestors are noted
   * with required-ancestor-contracts markup. This indicates that an
   * ancestor (e.g. parent or grandparent) tag must be have satisfied-contracts
   * metadata matching the required-ancestor-contracts metadata of this tag.
   * As an example, af:dialog
   * (oracle.adf.view.rich.component.rich.RichDialog) lists
   * oracle-adf-richdialog-holder as a required ancestor contract, and
   * af:popup (oracle.adf.view.rich.component.rich.RichPopup) lists
   * oracle-adf-richdialog-holder as a satisified contract.
   *
   * @param requiredAncestorContracts  a string array of the required ancestor contracts
   */
  public void setRequiredAncestorContracts(
    String[] requiredAncestorContracts)
  {
    _requiredAncestorContracts = Arrays.asList(requiredAncestorContracts);
  }

  /**
   * Returns the required ancestor contracts for this component.
   * In the component metadata file, the required ancestors are noted
   * with required-ancestor-contracts markup. This indicates that an
   * ancestor (e.g. parent or grandparent) tag must be have satisfied-contracts
   * metadata matching the required-ancestor-contracts metadata of this tag.
   * As an example, af:dialog
   * (oracle.adf.view.rich.component.rich.RichDialog) lists
   * oracle-adf-richdialog-holder as a required ancestor contract, and
   * af:popup (oracle.adf.view.rich.component.rich.RichPopup) lists
   * oracle-adf-richdialog-holder as a satisified contract.
   *
   * @return  a java.util.Iterator of strings, where each string is the name
   *          of a required ancestor contract
   */
  public Iterator<String> requiredAncestorContracts()
  {
    return _requiredAncestorContracts.iterator();
  }

  /**
   * Returns true if this component has any required ancestor contracts.
   *
   * @return  true   if this component has any required ancestor contracts,
   *          false  otherwise
   */
  public boolean hasRequiredAncestorContracts()
  {
    return (!_requiredAncestorContracts.isEmpty());
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
  public EventRefBean findEvent(
    String eventName)
  {
    return _events.get(eventName);
  }

  public EventRefBean findEvent(
    String eventName,
    boolean flatten)
  {
    EventRefBean event = findEvent(eventName);
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
  public Iterator<EventRefBean> events()
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
  public Iterator<EventRefBean> events(
   boolean flatten)
  {
    Iterator<EventRefBean> events = events();
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
    String tagClass = getTagClass();
    if (tagClass != null)
      return tagClass;

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
    Iterator<EventRefBean> events = events(false);
    while (events.hasNext())
    {
      EventRefBean eventRef = events.next();
      eventRef.attach(owner);
    }
  }

  private String  _description;
  private String  _longDescription;
  private String  _componentType;
  private String  _componentFamily;
  private String  _componentClass;
  private String  _jsComponentClass;
  private String  _componentSupertype;
  private String  _componentSuperclass;
  private String  _rendererType;
  private String  _implementationType;
  private String  _tagHandler;
  private String  _tagSuperclass;
  private String  _localName;
  private String  _nodeClass;
  private String  _defaultEventName;
  private boolean _namingContainer;
  private boolean _children = true;
  private Map<String, PropertyBean> _properties;
  private Map<String, FacetBean>    _facets;
  private Map<String, EventRefBean>    _events;
  private int     _componentClassModifiers;
  private int     _tagClassModifiers;
  private String[] _unsupportedAgents = new String[0];
  private List<String> _requiredAncestorContracts = new ArrayList<String>();
  private List<String> _satisfiedContracts = new ArrayList<String>();

  private String[] _eventNames;

  static private final String _TRINIDAD_COMPONENT_BASE =
                         "org.apache.myfaces.trinidad.component.UIXComponentBase";

  static private final String _TRINIDAD_COMPONENT_TAG =
                         "org.apache.myfaces.trinidad.webapp.UIXComponentTag";

  static private final Logger _LOG = Logger.getLogger(ComponentBean.class.getName());
}
