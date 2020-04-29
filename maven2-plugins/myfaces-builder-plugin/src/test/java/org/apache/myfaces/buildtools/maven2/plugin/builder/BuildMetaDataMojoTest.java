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
package org.apache.myfaces.buildtools.maven2.plugin.builder;

import junit.framework.TestCase;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;

/**
 * Tests the faces-config.xml generation mojo.
 */
public class BuildMetaDataMojoTest extends TestCase
{
    public void testWrite() throws Exception
    {
        Model model = new Model();

        ComponentMeta comp = new ComponentMeta();
        comp.setName("mockComponent");
        comp.setClassName("example.MockComponent");
        comp.setDescription("dummy desc");
        comp.setLongDescription("dummy long desc");
        comp.setFamily("mockFamily");
        comp.setParentClassName("jakarta.faces.UIComponent");
        comp.setType("mockType");

        PropertyMeta prop = new PropertyMeta();
        prop.setName("mockProp");
        prop.setDescription("propdesc");
        prop.setLiteralOnly(Boolean.TRUE);
        prop.setRequired(Boolean.TRUE);
        comp.addProperty(prop);

        model.addComponent(comp);

    }
}
