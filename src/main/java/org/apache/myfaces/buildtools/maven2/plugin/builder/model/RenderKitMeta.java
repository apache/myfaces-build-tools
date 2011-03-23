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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Store metadata about a JSF RenderKit.
 */
public class RenderKitMeta
{
    private String _className;

    private String _renderKitId = "";
    private Map _renderers;
    private Map _clientBehaviorRenderers;

    /**
     * Write an instance of this class out as xml.
     */
    public static void writeXml(XmlWriter out, RenderKitMeta rkm)
    {
        out.beginElement("renderKit");
                
        out.writeElement("renderKitId", rkm._renderKitId);
        out.writeElement("className", rkm._className);
        
        for (Iterator it = rkm._renderers.values().iterator();it.hasNext();)
        {
            RendererMeta rm = (RendererMeta) it.next();
            rm.writeXml(out);  
        }
        
        for (Iterator it = rkm._clientBehaviorRenderers.values().iterator();it.hasNext();)
        {
            ClientBehaviorRendererMeta rm = (ClientBehaviorRendererMeta) it.next();
            rm.writeXml(out);  
        }
        
        out.endElement("renderKit");        
    }
    
    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/renderKit";

        digester.addObjectCreate(newPrefix, RenderKitMeta.class);
        digester.addSetNext(newPrefix, "addRenderKit");

        ClassMeta.addXmlRules(digester, newPrefix);

        digester.addBeanPropertySetter(newPrefix + "/renderKitId");
        digester.addBeanPropertySetter(newPrefix + "/className");
        
        RendererMeta.addXmlRules(digester, newPrefix);
        ClientBehaviorRendererMeta.addXmlRules(digester, newPrefix);
    }


    /**
     * Creates a new RenderKitBean.
     */
    public RenderKitMeta()
    {
        _renderers = new TreeMap();
        _clientBehaviorRenderers = new TreeMap();
    }

    /**
     * The name of the class that this metadata applies to.
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
     * Sets the render kit id for this component.
     * 
     * @param renderKitId
     *            the render kit id
     */
    public void setRenderKitId(String renderKitId)
    {
        _renderKitId = renderKitId;
    }

    /**
     * Returns the render kit id type for this component.
     * 
     * @return the render kit id
     */
    public String getRenderKitId()
    {
        return _renderKitId;
    }

    /**
     * Adds a renderer to this render kit.
     * 
     * @param renderer
     *            the renderer to add
     */
    public void addRenderer(RendererMeta renderer)
    {
        String componentFamily = renderer.getComponentFamily();
        String rendererType = renderer.getRendererType();
        String compositeKey = componentFamily + "|" + rendererType;
        _renderers.put(compositeKey, renderer);
    }

    /**
     * Returns the renderer for this component family and renderer type.
     * 
     * @param componentFamily
     *            the component family
     * @param rendererType
     *            the renderer type
     */
    public RendererMeta findRenderer(String componentFamily,
            String rendererType)
    {
        String compositeKey = componentFamily + "|" + rendererType;
        return (RendererMeta) _renderers.get(compositeKey);
    }

    /**
     * Returns true if this render kit has any renderers.
     * 
     * @return true if this render kit has any renderers, false otherwise
     */
    public boolean hasRenderers()
    {
        return !_renderers.isEmpty();
    }

    /**
     * Returns an iterator for all renderers in this render kit.
     * 
     * @return the renderer iterator
     */
    public Iterator renderers()
    {
        return _renderers.values().iterator();
    }
    
    public Collection getRenderers()
    {
        return _renderers.values();
    }

    void addAllRenderers(RenderKitMeta renderKit)
    {
        for (Iterator i = renderKit._renderers.values().iterator(); i.hasNext();)
        {
            // use addRenderer to establish owner
            addRenderer((RendererMeta) i.next());
        }
    }
    
    /**
     * Adds a renderer to this render kit.
     * 
     * @param renderer
     *            the renderer to add
     */
    public void addClientBehaviorRenderer(ClientBehaviorRendererMeta renderer)
    {
        String rendererType = renderer.getRendererType();
        _clientBehaviorRenderers.put(rendererType, renderer);
    }

    /**
     * Returns the renderer for this component family and renderer type.
     * 
     * @param componentFamily
     *            the component family
     * @param rendererType
     *            the renderer type
     */
    public RendererMeta findClientBehaviorRenderer(String rendererType)
    {
        return (RendererMeta) _renderers.get(rendererType);
    }

    /**
     * Returns true if this render kit has any renderers.
     * 
     * @return true if this render kit has any renderers, false otherwise
     */
    public boolean hasClientBehaviorRenderers()
    {
        return !_clientBehaviorRenderers.isEmpty();
    }

    /**
     * Returns an iterator for all renderers in this render kit.
     * 
     * @return the renderer iterator
     */
    public Iterator clientBehaviorRenderers()
    {
        return _clientBehaviorRenderers.values().iterator();
    }
    
    public Collection getClientBehaviorRenderers()
    {
        return _clientBehaviorRenderers.values();
    }

    void addAllClientBehaviorRenderers(RenderKitMeta renderKit)
    {
        for (Iterator i = renderKit._clientBehaviorRenderers.values().iterator(); i.hasNext();)
        {
            // use addRenderer to establish owner
            addClientBehaviorRenderer((ClientBehaviorRendererMeta) i.next());
        }
    }    
}
