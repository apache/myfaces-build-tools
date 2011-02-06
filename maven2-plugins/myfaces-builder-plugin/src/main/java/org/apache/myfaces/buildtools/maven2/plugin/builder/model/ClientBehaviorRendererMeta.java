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
 * Store metadata about a class that is either a JSF Renderer, or some base
 * class or interface that a Renderer can be derived from.
 */
public class ClientBehaviorRendererMeta extends ClassMeta
{
    private String _description;
    private String _rendererType;

    /**
     * Write an instance of this class out as xml.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        super.writeXmlSimple(out);
        
        out.writeElement("rendererType", _rendererType);
        out.writeElement("description", _description);
    }
    
    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/clientBehaviorRenderer";

        digester.addObjectCreate(newPrefix, ClientBehaviorRendererMeta.class);
        digester.addSetNext(newPrefix, "addClientBehaviorRenderer");

        ClassMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/componentFamily");
        digester.addBeanPropertySetter(newPrefix + "/rendererType");
        digester.addBeanPropertySetter(newPrefix + "/description");
    }
    
    /**
     * Creates a new RendererBean.
     */
    public ClientBehaviorRendererMeta()
    {
        super("clientBehaviorRenderer");
    }    

    /**
     * Sets the renderer type for this component.
     * 
     * @param rendererType
     *            the renderer type
     */
    public void setRendererType(String rendererType)
    {
        _rendererType = rendererType;
    }

    /**
     * Returns the renderer type for this component.
     * 
     * @return the renderer type
     */
    public String getRendererType()
    {
        return _rendererType;
    }

    /**
     * Sets the description of this attribute.
     * 
     * @param description
     *            the attribute description
     */
    public void setDescription(String description)
    {
        _description = description;
    }

    /**
     * Returns the description of this attribute.
     * 
     * @return the attribute description
     */
    public String getDescription()
    {
        return _description;
    }

}
