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

import java.util.Iterator;

/**
 * Simple static helper methods.
 */
public class ModelUtils
{
    /**
     * Inherit setting only if current one is empty.
     */
    public static String merge(String curr, String inherited)
    {
        if (curr != null)
        {
            return curr;
        }
        return inherited;
    }
    
    /**
     * Inherit setting only if current one is empty.
     */
    public static Object merge(Object curr, Object inherited)
    {
        if (curr != null)
        {
            return curr;
        }
        return inherited;
    }    
    
    /**
     * Inherit setting only if current one is empty.
     */
    public static Boolean merge(Boolean curr, Boolean inherited)
    {
        if (curr != null)
        {
            return curr;
        }
        return inherited;
    }

    /**
     * Return a default value if a boolean property is null.
     */
    public static Boolean defaultOf(Boolean val, boolean dflt)
    {
        if (val != null)
        {
            return val;
        }
        else
        {
            return Boolean.valueOf(dflt);
        }
    }

    /**
     * Given two JSF artifacts that can have associated user-settable properties,
     * merge all the properties one one into the other.
     * <p>
     * The dst object is expected to be a "child" of the src object. Any data on dst
     * therefore overrides stuff on src, but otherwise everything on src gets copied
     * to dst.
     */
    public static void mergeProps(PropertyHolder dst, PropertyHolder src)
    {
        for (Iterator i = src.properties(); i.hasNext();)
        {
            PropertyMeta srcProp = (PropertyMeta) i.next();
            PropertyMeta dstProp = dst.getProperty(srcProp.getName());
            if (dstProp == null)
            {
                // Just copy the whole property unaltered
                dstProp = new PropertyMeta(srcProp);
                dstProp.setInherited(Boolean.TRUE);
                dst.addProperty(dstProp);
            }
            else
            {
                // merge the two property objects together
                dstProp.merge(srcProp);
            }
        }
    }
    
    public static void mergeFacets(FacetHolder dst, FacetHolder src)
    {
        for (Iterator i = src.facets(); i.hasNext();)
        {
            FacetMeta srcProp = (FacetMeta) i.next();
            FacetMeta dstProp = dst.getFacet(srcProp.getName());
            if (dstProp == null)
            {
                // Just copy the whole property unaltered
                dstProp = new FacetMeta(srcProp);
                dstProp.setInherited(Boolean.TRUE);
                dst.addFacet(dstProp);
            }
            else
            {
                // merge the two property objects together
                dstProp.merge(srcProp);
            }
        }
    }
    
    /**
     * @since 1.0.4
     * @param dst
     * @param src
     */
    public static void mergeListeners(ListenerHolder dst, ListenerHolder src)
    {
        for (Iterator i = src.listeners(); i.hasNext();)
        {
            ListenerMeta srcProp = (ListenerMeta) i.next();
            ListenerMeta dstProp = dst.getListener(srcProp.getName());
            if (dstProp == null)
            {
                // Just copy the whole property unaltered
                dstProp = new ListenerMeta(srcProp);
                dstProp.setInherited(Boolean.TRUE);
                dst.addListener(dstProp);
            }
            else
            {
                // merge the two property objects together
                dstProp.merge(srcProp);
            }
        }
    }
    
    /**
     * @since 1.0.4
     */
    public static void mergeAttributes(AttributeHolder dst, AttributeHolder src)
    {
        for (Iterator i = src.attributes(); i.hasNext();)
        {
            AttributeMeta srcAttr = (AttributeMeta) i.next();
            AttributeMeta dstAttr = dst.getAttribute(srcAttr.getName());
            if (dstAttr == null)
            {
                // Just copy the whole property unaltered
                dstAttr = new AttributeMeta(srcAttr);
                //dstProp.setInherited(Boolean.TRUE);
                dst.addAttribute(dstAttr);
            }
            else
            {
                // merge the two property objects together
                dstAttr.merge(srcAttr);
            }
        }
    }
}
