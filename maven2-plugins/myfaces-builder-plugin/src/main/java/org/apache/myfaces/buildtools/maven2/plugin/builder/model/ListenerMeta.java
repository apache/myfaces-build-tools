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
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 796607 $ $Date: 2009-07-21 22:00:30 -0500 (mar, 21 jul 2009) $
 */
public class ListenerMeta
{
    private String _longDescription;
    private String _description;
    private String _name;
    private Boolean _required;
    
    private Boolean _inherited; //Define if this facet is inherited from parent component
    private Boolean _generated; //Define if this facet should be generated
    
    private String _className;
    private String _eventClassName;
    private String _phases;

    public ListenerMeta()
    {
        
    }
    
    public ListenerMeta(ListenerMeta pm)
    {
        _name = pm._name;
        _className = pm._className;
        _eventClassName = pm._eventClassName;
        _required = pm._required;
        _description = pm._description;
        _longDescription = pm._longDescription;
        _phases = pm._phases;
        _inherited = pm._inherited;
    }
    
    /**
     * Write this model out as xml.
     */
    public static void writeXml(XmlWriter out, ListenerMeta am)
    {
        out.beginElement("listener");
        out.writeElement("name", am._name);
        out.writeElement("className", am._className);
        out.writeElement("eventClassName", am._eventClassName);
        out.writeElement("required", am._required);
        out.writeElement("desc", am._description);
        out.writeElement("longDesc", am._longDescription);
        out.writeElement("phases", am._phases);
        out.writeElement("inherited", am._inherited);
        out.endElement("listener");
    }

    /**
     * Add digester rules to repopulate a Model instance from an XML file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/listener";

        digester.addObjectCreate(newPrefix, ListenerMeta.class);
        digester.addSetNext(newPrefix, "addListener");

        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/className");
        digester.addBeanPropertySetter(newPrefix + "/eventClassName");
        digester.addBeanPropertySetter(newPrefix + "/required");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc",
                "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/phases", "phases");
        digester.addBeanPropertySetter(newPrefix + "/inherited", "inherited");        
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(ListenerMeta other)
    {
        
        _name = ModelUtils.merge(this._name, other._name);
        _required = ModelUtils.merge(this._required, other._required);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription, other._longDescription);
        _phases = ModelUtils.merge(this._phases, other._phases);
    }
    
    
    
    /**
     * Sets the listener name for this listener.
     *
     * @param name  the listener name
     */
    public void setName(String name)
    {
        _name = name;
    }
    
    /**
     * Returns the listener name for this listener.
     *
     * @return  the listener name
     */
    public String getName()
    {
        return _name;
    }
    
    /**
     * The fully-qualified name of the JSF entity class, ie the class that actually
     * implements a Listener.
     * <p>
     * The specified class may be a hand-written one, or one created via code generation. 
     */
    public String getClassName()
    {
        return _className;
    }

    public void setClassName(String className)
    {
        _className = className;
    }
    
    /**
     * Sets the description of this property.
     *
     * @param description  the property description
     */
    public void setDescription(String description)
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
     * Sets the required flag of this facet.
     *
     * @param required  the facet required flag
     */
    public void setRequired(Boolean required)
    {
        _required = required;
    }

    /**
     * Returns required flag of this facet.
     *
     * @return  the facet required flag
     */
    public Boolean isRequired()
    {
        return ModelUtils.defaultOf(_required,false);
    }
    
    public void setLongDescription(String desc)
    {
        _longDescription = desc;
    }

    public String getLongDescription()
    {
        return _longDescription;
    }
    
    public void setInherited(Boolean inherited)
    {
        _inherited = inherited;
    }

    public Boolean isInherited()
    {
        return ModelUtils.defaultOf(_inherited, false);
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

    public void setEventClassName(String eventClassName)
    {
        _eventClassName = eventClassName;
    }

    public String getEventClassName()
    {
        return _eventClassName;
    }

    public void setPhases(String phases)
    {
        _phases = phases;
    }

    public String getPhases()
    {
        return _phases;
    }
    
}
