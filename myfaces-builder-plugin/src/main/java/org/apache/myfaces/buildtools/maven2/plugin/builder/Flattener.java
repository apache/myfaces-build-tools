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

    public Flattener(Model model)
    {
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

    private void flattenComponent(ComponentMeta component)
    {
        if (flattened.contains(component))
        {
            // already done
            return;
        }
        String parentClassName = component.getParentClassName();
        if (parentClassName != null)
        {
            ComponentMeta parent = model
                    .findComponentByClassName(parentClassName);
            if (parent != null)
            {
                flattenComponent(parent);
                component.merge(parent);
            }
            else
            {
                //How to manage a component that its
                //parent class is not a real component?
                //Use UIComponent instead and log a warn
                //so if needed we can fix this.
                log.warn("Component:"+component.getClassName()+
                        " without a parent defined as component, using " +
                        "UIComponent");
                parent = model
                    .findComponentByClassName("javax.faces.component.UIComponent");
                flattenComponent(parent);
                component.merge(parent);                
            }
        }

        List interfaceClassNames = component.getInterfaceClassNames();
        for (Iterator i = interfaceClassNames.iterator(); i.hasNext();)
        {
            String ifaceClassName = (String) i.next();
            ComponentMeta iface = model
                    .findComponentByClassName(ifaceClassName);
            flattenComponent(iface);
            component.merge(iface);
        }

        flattened.add(component);
    }
}