/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.component.html.util;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * <p>Utility class for providing basic functionality to the HTML faces 
 * extended components.<p>
 * 
 * @author Sean Schofield
 * @version $Revision$ $Date$
 */
public final class HtmlComponentUtils
{
    /**
     * Constructor (Private)
     */
    private HtmlComponentUtils()
    {}

    /**
     * Gets the client id associated with the component.  Checks the forceId 
     * attribute of the component (if present) and uses the orginally supplied 
     * id value if that attribute is true.  Also performs the required call 
     * to <code>convertClientId</code> on the {@link Renderer} argument.
     * 
     * @param component The component for which the client id is needed.
     * @param renderer The renderer associated with the component.
     * @param context Additional context information to help in the request.
     * @return The clientId to use with the specified component.
     */
    public static String getClientId(UIComponent component,
                                     Renderer renderer,
                                     FacesContext context)
    {

        //forceId enabled?
        boolean forceId = RendererUtils.getBooleanValue(JSFAttr.FORCE_ID_ATTR,
                component.getAttributes().get(JSFAttr.FORCE_ID_ATTR),false);

        if (forceId && component.getId() != null)
        {
            String clientId = component.getId();

            /**
             * See if there is a parent naming container.  If there is ...
             */
            UIComponent parentContainer = HtmlComponentUtils.findParentNamingContainer(component, false);
            if (parentContainer != null)
            {
                if (parentContainer instanceof UIData)
                {
                    // see if the originally supplied id should be used
                    boolean forceIdIndex = RendererUtils.getBooleanValue(JSFAttr.FORCE_ID_ATTR,
                            component.getAttributes().get(JSFAttr.FORCE_ID_INDEX_ATTR),true);

                    // note: user may have specifically requested that we do not add the special forceId [index] suffix
                    if (forceIdIndex)
                    {
                        int rowIndex = ( (UIData) parentContainer).getRowIndex();
                        if (rowIndex != -1) {
                            clientId = clientId + "[" + rowIndex + "]";
                        }
                    }
                }
            }

            // JSF spec requires that renderer get a chance to convert the id
            if (renderer != null)
            {
                clientId = renderer.convertClientId(context, clientId);
            }

            return clientId;
        }
        else
        {
            return null;
        }
    }

    /**
     * Locates the {@link NamingContainer} associated with the givem 
     * {@link UIComponent}.
     * 
     * @param component The component whose naming locator needs to be found.
     * @param returnRootIfNotFound Whether or not the root should be returned 
     *    if no naming container is found.
     * @return The parent naming container (or root if applicable).
     */
    public static UIComponent findParentNamingContainer(UIComponent component,
                                                        boolean returnRootIfNotFound)
    {
        UIComponent parent = component.getParent();
        if (returnRootIfNotFound && parent == null)
        {
            return component;
        }
        while (parent != null)
        {
            if (parent instanceof NamingContainer) return parent;
            if (returnRootIfNotFound)
            {
                UIComponent nextParent = parent.getParent();
                if (nextParent == null)
                {
                    return parent;  //Root
                }
                parent = nextParent;
            }
            else
            {
                parent = parent.getParent();
            }
        }
        return null;
    }
    

}
