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
package org.apache.myfaces.buildtools.maven2.plugin.tagdoc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.AttributeHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.AttributeMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ListenerHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ListenerMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RenderKitMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RendererMeta;

public class TagdocUtils
{
    
    static public final String platformAgnosticPath(String path)
    {
        return path.replace('/', File.separatorChar);
    }
    
    static public String getDisplayType(String className, String name,
            String type)
    {
        if (type != null && type.startsWith("java.lang."))
        {
            return type.substring("java.lang.".length());
        }
        else if ("binding".equals(name))
        {
            StringTokenizer tokens = new StringTokenizer(className, ".", true);
            String out = "";
            while (tokens.hasMoreTokens())
            {
                String token = tokens.nextToken();
                out = out + token;
                // Give ourselves an opportunity for a line break after "component.";
                if (out.endsWith("component."))
                {
                    out = out + "<wbr/>";
                }
            }

            return out;
        }

        return type;
    }
    
    static public List getSortedPropertyList(PropertyHolder component)
    {
        // Sort the names
        TreeSet attributes = new TreeSet();
        Iterator attrs = component.properties();
        while (attrs.hasNext())
        {
            PropertyMeta property = (PropertyMeta) attrs.next();
            if (!property.isTagExcluded().booleanValue())
            {
                attributes.add(property.getName());
            }
        }

        // Now get a list of PropertyMetas
        List list = new ArrayList();
        Iterator iter = attributes.iterator();
        while (iter.hasNext())
        {
            String attrName = (String) iter.next();
            list.add(component.getProperty(attrName));
        }
        return list;        
    }
    
    static public List getSortedFacetList(FacetHolder component)
    {
        TreeSet facetNames = new TreeSet();
        Iterator iter = component.facets();
        while (iter.hasNext())
        {
            facetNames.add(((FacetMeta) iter.next()).getName());
        }
        
        // Now get a list of PropertyMetas
        List list = new ArrayList();
        Iterator nameIter = facetNames.iterator();
        while (nameIter.hasNext())
        {
            String name = (String) nameIter.next();
            list.add(component.getFacet(name));
        }
        return list;
    }
    
    /**
     * @since 1.0.4
     * @param component
     * @return
     */
    static public List getSortedListenerList(ListenerHolder component)
    {
        TreeSet facetNames = new TreeSet();
        Iterator iter = component.listeners();
        while (iter.hasNext())
        {
            facetNames.add(((ListenerMeta) iter.next()).getClassName());
        }
        
        // Now get a list of PropertyMetas
        List list = new ArrayList();
        Iterator nameIter = facetNames.iterator();
        while (nameIter.hasNext())
        {
            String name = (String) nameIter.next();
            list.add(component.getListener(name));
        }
        return list;
    }
    
    static public List getSortedAttributeList(AttributeHolder component)
    {
        // Sort the names
        TreeSet attributes = new TreeSet();
        Iterator attrs = component.attributes();
        while (attrs.hasNext())
        {
            AttributeMeta attribute = (AttributeMeta) attrs.next();
            attributes.add(attribute.getName());
        }

        // Now get a list of PropertyMetas
        List list = new ArrayList();
        Iterator iter = attributes.iterator();
        while (iter.hasNext())
        {
            String attrName = (String) iter.next();
            list.add(component.getAttribute(attrName));
        }
        return list;        
    }
        
    static public Map getRendererClasses(ComponentMeta component, Model model)
    {
        Map componentRenderers = new HashMap(); 
        List renderKits = model.getRenderKits();
        if (renderKits != null)
        {
            for (Iterator it = renderKits.iterator();it.hasNext();)
            {
                RenderKitMeta renderkit = (RenderKitMeta) it.next();
                RendererMeta renderer = renderkit.findRenderer(component.getFamily(), component.getRendererType());
                if (renderer != null)
                {
                    componentRenderers.put(renderkit.getRenderKitId(), renderer.getClassName());
                }
            }
        }
        return componentRenderers;
    }
}
