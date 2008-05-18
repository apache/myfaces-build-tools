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
package org.apache.myfaces.custom.ppr;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.FacesEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @JSFComponent
 *   name = "s:pprSubmit"
 *   class = "org.apache.myfaces.custom.ppr.PPRSubmit"
 *   superClass = "org.apache.myfaces.custom.ppr.AbstractPPRSubmit"
 *   tagClass = "org.apache.myfaces.custom.ppr.PPRSubmitTag"
 *   
 * @author Thomas Spiegl
 */
public abstract class AbstractPPRSubmit extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.PPRSubmit";

    public static final String COMPONENT_FAMILY = "org.apache.myfaces.PPRSubmit";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.PPRSubmit";

    /**
     * @JSFProperty
     */
    public abstract String getProcessComponentIds();

    /**
     * intercept the event placed by any child component
     * <br />
     * if such an event happens PPRSubmit will gather all components and store their client-ids in
     * a request scoped list for further processing by the {@link PPRViewRootWrapper} 
     */
    public void queueEvent(FacesEvent event)
    {
        super.queueEvent(event);

        FacesContext context = FacesContext.getCurrentInstance();

        String processComponentIdsString = getProcessComponentIds();
        if (!StringUtils.isEmpty(processComponentIdsString))
        {
            Map requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
            List allProcessComponents = (List) requestMap.get(PPRSupport.PROCESS_COMPONENTS);

            List processComponents = PPRSupport.getComponentsByCommaSeparatedIdList(
                context,
                this,
                processComponentIdsString,
                null
            );

            Iterator iterComponents = processComponents.iterator();
            while (iterComponents.hasNext())
            {
                UIComponent component = (UIComponent) iterComponents.next();
                String clientId = component.getClientId(context);

                if (allProcessComponents == null)
                {
                    allProcessComponents = new ArrayList();
                    requestMap.put(PPRSupport.PROCESS_COMPONENTS, allProcessComponents);
                }

                allProcessComponents.add(clientId);
            }
        }
    }
}