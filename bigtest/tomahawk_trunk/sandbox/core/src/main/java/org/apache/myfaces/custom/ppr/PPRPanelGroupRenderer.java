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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.ext.HtmlGroupRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Ernst Fastl
 */
public class PPRPanelGroupRenderer extends HtmlGroupRenderer
{
    private static Log log = LogFactory.getLog(PPRPanelGroupRenderer.class);

    private static final String DISABLE_RENDER_CHILDREN = "org.apache.myfaces.PPRPanelGroup.disableRenderChildren";

    public static final String TRANSIENT_MARKER_ATTRIBUTE = "org.apache.myfaces.PPRPanelGroup.transientComponent";

    /**
     * Renders the start of a span element. Iterates over all child
     * components and sets transient components to transient=false. Those
     * components are marked with the TRANSIENT_MARKER_ATTRIBUTE so the
     * {@link PPRPhaseListener} can reset them to transient in the next
     * non-PPR Request
     *
     * @param facesContext the current {@link FacesContext}
     * @param uiComponent  the {@link PPRPanelGroup} to render
     */
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        if (uiComponent.getId() == null || uiComponent.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            throw new IllegalArgumentException("'id' is a required attribute for the PPRPanelGroup");
        }

        // todo: in 1.2, better use a combo of
        // invokeComponent/RendererUtils.renderChildren() instead
        uiComponent.getAttributes().put(DISABLE_RENDER_CHILDREN, Boolean.TRUE);

        // Iterate over the transient child components and set transient to
        // false
        // This is necessary to have those components available for PPR
        // responses later on
        for (Iterator iter = uiComponent.getChildren().iterator(); iter.hasNext();) {
            UIComponent child = (UIComponent) iter.next();
            if (child.isTransient()) {
                child.setTransient(false);
                child.getAttributes().put(TRANSIENT_MARKER_ATTRIBUTE, Boolean.TRUE);
            }
        }

        super.encodeBegin(facesContext, uiComponent);
    }

    /**
     * todo: in 1.2, better use a combo of
     * invokeComponent/RendererUtils.renderChildren() instead
     *
     * @param context
     * @param component
     * @throws IOException
     */
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException
    {
        Boolean disableRenderChildren = (Boolean) component.getAttributes().get(DISABLE_RENDER_CHILDREN);

        if (disableRenderChildren == null || disableRenderChildren.booleanValue() == false) {
            RendererUtils.renderChildren(context, component);
        }
    }

    /**
     * Encodes the end of the span-element and afterwards the inline
     * JavaScript for the client side initialization of the
     * {@link PPRPanelGroup}.
     *
     * @param facesContext the current {@link FacesContext}
     * @param uiComponent  the {@link PPRPanelGroup} to render
     */
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        // Render the span end element
        super.encodeEnd(facesContext, uiComponent);
        if (uiComponent instanceof PPRPanelGroup) {
            PPRPanelGroup pprGroup = (PPRPanelGroup) uiComponent;

            final String triggers = pprGroup.getPartialTriggers();
            final String triggerPattern = pprGroup.getPartialTriggerPattern();

            // Check if triggers, a pattern or a periodical update is
            // defined
            if ((triggers != null && triggers.length() > 0) || (triggerPattern != null && triggerPattern.length() > 0) || pprGroup.getPeriodicalUpdate() != null) {
                if (PPRSupport.isPartialRequest(facesContext)) {
                    return;
                }
                // encode the initialization inline JavaScript
                PPRSupport.initPPR(facesContext, pprGroup);
                PPRSupport.encodeJavaScript(facesContext, pprGroup);
            }
        }

        // todo: in 1.2, better use a combo of
        // invokeComponent/RendererUtils.renderChildren() instead
        uiComponent.getAttributes().put(DISABLE_RENDER_CHILDREN, Boolean.FALSE);
    }

}
