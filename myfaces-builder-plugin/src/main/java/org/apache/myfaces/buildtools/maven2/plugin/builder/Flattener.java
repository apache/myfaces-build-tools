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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;

/**
 */
public class Flattener
{
    private final Log log = LogFactory.getLog(Flattener.class);
    
    private Model model;
    private Set flattened = new HashSet();

    public Flattener(Model model) {
        this.model = model;
    }

    /**
     * Flatten the specified model.
     * <p>
     * In the flattened representation, each model object directly contains the
     * data that it inherits from its parents, so that the getter methods return
     * all available metadata, not just the data that was defined directly on
     * that item.
     */
    public void flatten()
            throws MojoExecutionException
    {
        flattenComponentProperties();
    }

    private void flattenComponentProperties()
    {
        List components = model.getComponents();
        for (Iterator i = components.iterator(); i.hasNext();)
        {
            ComponentMeta comp = (ComponentMeta) i.next();
            flattenComponent(comp);
        }
    }

    private void flattenComponent(ComponentMeta component) {
        if (flattened.contains(component)) {
            // already done
            return;
        }
        
        // for each component
        //   flatten its parent
        //   merge parent props into curr class
        //   for each interface
        //     flatten the interface
        //     merge interface props into curr class
        
        String parentClassName = component.getParentClassName();
        if (parentClassName != null) {
            ComponentMeta parent = model.findComponentByClassName(parentClassName);
            flattenComponent(parent);
            copyProps(parent, component);
        }

        List interfaceClassNames = component.getInterfaceClassNames();
        for(Iterator i = interfaceClassNames.iterator(); i.hasNext(); ) {
            String ifaceClassName = (String) i.next();
            ComponentMeta iface = model.findComponentByClassName(ifaceClassName);
            flattenComponent(iface);
            copyProps(iface, component);
        }
        
        flattened.add(component);
    }
        
    private void copyProps(ComponentMeta src, ComponentMeta dst)
    {
        for (Iterator i = src.properties(); i.hasNext();)
        {
            PropertyMeta prop = (PropertyMeta) i.next();
            if (dst.getProperty(prop.getName()) == null)
            {
                dst.addProperty(prop);
            }
            else
            {
                // TODO: consider checking that the redefinition of the
                // property is "compatible".
                log.info("Duplicate prop def for class " + dst.getClassName()
                        + " prop " + prop.getName());
            }

        }
    }
}