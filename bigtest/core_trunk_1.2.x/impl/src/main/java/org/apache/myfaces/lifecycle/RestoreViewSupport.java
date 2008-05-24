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
package org.apache.myfaces.lifecycle;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Support class for restore view phase
 * 
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface RestoreViewSupport
{
    /**
     * <p>
     * Calculates the view id from the given faces context by the following algorithm
     * </p>
     * <ul>
     * <li>lookup the viewid from the request attribute "javax.servlet.include.path_info"
     * <li>if null lookup the value for viewid by {@link ExternalContext#getRequestPathInfo()}
     * <li>if null lookup the value for viewid from the request attribute "javax.servlet.include.servlet_path"
     * <li>if null lookup the value for viewid by {@link ExternalContext#getRequestServletPath()}
     * <li>if null throw a {@link FacesException}
     * </ul>
     */
    String calculateViewId(FacesContext facesContext);

    /**
     * Processes the component tree. For each component (including the given one) in the tree determine if a value
     * expression for the attribute "binding" is defined. If the expression is not null set the component instance to
     * the value of this expression
     * 
     * @param facesContext
     * @param component
     *            the root component
     */
    void processComponentBinding(FacesContext facesContext, UIComponent component);

    /**
     * <p>
     * Determine if the current request is a post back by the following algorithm.
     * </p>
     * <p>
     * Find the render-kit-id for the current request by calling calculateRenderKitId() on the Application’s
     * ViewHandler. Get that RenderKit’s ResponseStateManager and call its isPostback() method, passing the given
     * FacesContext.
     * </p>
     * 
     * @param facesContext
     * @return
     */
    boolean isPostback(FacesContext facesContext);
}
