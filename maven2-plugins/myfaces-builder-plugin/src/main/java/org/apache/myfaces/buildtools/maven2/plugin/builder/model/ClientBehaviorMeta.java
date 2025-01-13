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
 * @since 1.0.9
 */
public class ClientBehaviorMeta extends BehaviorMeta
{
    private String _rendererType;


    /**
     * Write an instance of this class out as xml.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        super.writeXmlSimple(out);
        out.writeElement("rendererType", _rendererType);
    }

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/clientBehavior";

        digester.addObjectCreate(newPrefix, ClientBehaviorMeta.class);
        digester.addSetNext(newPrefix, "addBehavior");

        ViewEntityMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/behaviorId");
        digester.addBeanPropertySetter(newPrefix + "/bodyContent");
        digester.addBeanPropertySetter(newPrefix + "/configExcluded");
        digester.addBeanPropertySetter(newPrefix + "/tagHandler");
        digester.addBeanPropertySetter(newPrefix + "/generatedComponentClass");
        digester.addBeanPropertySetter(newPrefix + "/evaluateELOnExecution");
        digester.addBeanPropertySetter(newPrefix + "/rendererType");
    }
    
    public ClientBehaviorMeta()
    {
        super("clientBehavior");
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     * 
     * Not used right now since theoretically there is very few inheritance
     * on behaviors
     * 
     */
    public void merge(ClientBehaviorMeta other)
    {
        super.merge(other);
        _rendererType = ModelUtils.merge(this._rendererType, other._rendererType);
    }
    
    public String getBehaviorType()
    {
        return "clientBehavior";
    }
    
    public void setRendererType(String rendererType)
    {
        _rendererType = rendererType;
    }

    public String getRendererType()
    {
        return _rendererType;
    }
}
