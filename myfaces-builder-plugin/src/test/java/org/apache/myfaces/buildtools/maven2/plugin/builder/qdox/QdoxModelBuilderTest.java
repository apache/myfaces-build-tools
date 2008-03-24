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
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyModel;

/**
 * Tests the QDoxModelBuilderTest generation mojo.
 */
public class QdoxModelBuilderTest extends TestCase
{
    public void testScan() throws Exception
    {
        QdoxModelBuilder builder = new QdoxModelBuilder();
        Model model = new Model();

        URL sourceUrl = this.getClass().getClassLoader().getResource("builder/Foo.java");
        String parentDirName = new File(sourceUrl.getFile()).getParent(); 
        File parentDir = new File(parentDirName);
        List sourceDirs = new ArrayList();
        sourceDirs.add(parentDir.getAbsolutePath());
        builder.buildModel(model, sourceDirs);
        
        assertEquals(1, model.getComponents().size());
        ComponentModel cm =  (ComponentModel) model.getComponents().get(0);
        assertEquals(1, cm.propertiesSize());
        Iterator props = cm.properties();
        PropertyModel prop1 = (PropertyModel) props.next();
        assertEquals("prop1", prop1.getPropertyName());
        assertEquals("java.lang.String", prop1.getPropertyClass());
    }
}
