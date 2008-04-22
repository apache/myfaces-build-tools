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
 * @author Thomas Spiegl
 */
public class PPRSubmit extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.PPRSubmit";

    public static final String COMPONENT_FAMILY = "org.apache.myfaces.PPRSubmit";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.PPRSubmit";

    private String processComponentIds;

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getProcessComponentIds()
    {
        if (processComponentIds != null) {
            return processComponentIds;
        }
        ValueBinding vb = getValueBinding("processComponentIds");
        return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setProcessComponentIds(String processComponentIds)
    {
        this.processComponentIds = processComponentIds;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object[] states = (Object[]) state;
        super.restoreState(context, states[0]);
        processComponentIds = (String) states[1];
    }

    public Object saveState(FacesContext context)
    {
        return new Object[]
            {
                super.saveState(context),
                processComponentIds
            };
    }

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