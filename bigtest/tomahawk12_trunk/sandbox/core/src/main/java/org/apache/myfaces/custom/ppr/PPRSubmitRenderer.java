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

import javax.faces.FacesException;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * If container for a commond component this component allows you to configure which components
 * to process (validate/update-model) during a ppr request.
 *
 * TODO: document why this component helps with UIData too ... why does it?
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "org.apache.myfaces.PPRSubmit"
 *   type = "org.apache.myfaces.PPRSubmit"
 * 
 */
public class PPRSubmitRenderer extends Renderer
{
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
        super.encodeBegin(context, component);

        final PPRSubmit pprSubmit = (PPRSubmit) component;
        UICommand command = findCommandComponent(false, component);
        if (!StringUtils.isEmpty(pprSubmit.getProcessComponentIds()) &&
            (command == null || command.isImmediate())) {
            throw new FacesException("PPRSubmit must embed a command component with immedate='false'.");
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        UICommand command = findCommandComponent(true, component);

        if (command != null) {
            List panelGroups = new ArrayList(5);
            String id = command.getId();
            addPPRPanelGroupComponents(context.getViewRoot(), panelGroups);
            for (int i = 0; i < panelGroups.size(); i++) {
                PPRPanelGroup pprGroup = (PPRPanelGroup) panelGroups.get(i);

                if (!PPRSupport.isPartialRequest(context)) {
                    PPRSupport.initPPR(context, pprGroup);
                }

                List triggers = pprGroup.parsePartialTriggers();
                for (int j = 0; j < triggers.size(); j++) {
                    PartialTriggerParser.PartialTrigger trigger = (PartialTriggerParser.PartialTrigger) triggers.get(j);

                    // TODO: what about trigger patterns?
                    if (trigger.getPartialTriggerId().equals(id)) {
                        PPRSupport.encodeJavaScriptTriggerOnly(context, command, pprGroup, trigger);
                    }
                }
            }
        }
        else {
            throw new FacesException("PPRSubmitRenderer must be embedded in or embed a command component.");
        }
    }

    /**
     * This component can be child of a command component or embed one as child.
     * Try to find the command component that way.
     */
    private UICommand findCommandComponent(boolean checkParent, UIComponent component)
    {
        if (checkParent) {
            UIComponent parent = component.getParent();
            if (parent instanceof UICommand) {
                return (UICommand) parent;
            }
        }

        if (component.getChildCount() > 0) {
            UIComponent child = (UIComponent) component.getChildren().get(0);
            if (child instanceof UICommand) {
                return (UICommand) child;
            }
        }

        return null;
    }

    public void addPPRPanelGroupComponents(UIComponent component, List list)
    {
        // TODO: what about facets?
        for (Iterator it = component.getChildren().iterator(); it.hasNext();) {
            UIComponent c = (UIComponent) it.next();
            if (c instanceof PPRPanelGroup) {
                list.add(c);
            }
            if (c.getChildCount() > 0) {
                addPPRPanelGroupComponents(c, list);
            }
        }
    }
}