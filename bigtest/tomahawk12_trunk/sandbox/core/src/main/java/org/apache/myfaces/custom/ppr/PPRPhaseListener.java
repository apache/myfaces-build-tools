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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlMessages;
import org.apache.myfaces.component.html.ext.UIComponentPerspective;
import org.apache.myfaces.shared_tomahawk.component.ExecuteOnCallback;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlResponseWriterImpl;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.StateManager;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Before RenderResponse PhaseListener for processing Ajax requests from
 * {@link PPRPanelGroup}. It also participates in handling transient components
 * in PPR Requests
 *
 * @author Ernst Fastl
 */
public class PPRPhaseListener implements PhaseListener
{
    private static Log log = LogFactory.getLog(PPRPhaseListener.class);

    /**
     * Request parameter which marks a request as PPR request
     */
    private static final String PPR_PARAMETER = "org.apache.myfaces.PPRCtrl.ajaxRequest";

    /**
     * Request parameter containing a comma separated list of component IDs
     * of the to be updated components
     */
    private static final String TRIGGERED_COMPONENTS_PARAMETER = "org.apache.myfaces.PPRCtrl.triggeredComponents";

    private static final String XML_HEADER = "<?xml version=\"1.0\"?>\n";

    public void afterPhase(PhaseEvent phaseEvent)
    {
    }

    /**
     * Determines wether the currently processed request is a PPR request
     * (by searching for PPR_PARAMETER in the request parameter map) or an
     * ordinary HTTP request. If the request is a PPR request the triggered
     * components are encoded. Otherwise transient components which have
     * previously been marked not transient by the
     * {@link PPRPanelGroupRenderer} are set to transient again
     */
    public void beforePhase(PhaseEvent event)
    {
        if (log.isDebugEnabled()) {
            log.debug("In PPRPhaseListener beforePhase");
        }

        final FacesContext context = event.getFacesContext();
        final ExternalContext externalContext = context.getExternalContext();

        Map requestMap = externalContext.getRequestMap();

        if (isPartialRequest(context)) {
            processPartialPageRequest(context, externalContext, requestMap);
        }
        else {
            // Iterate over the component tree and set all previously
            // transient components to transient again
            resetTransientComponents(context.getViewRoot());
        }
    }

    /**
     * if the provided component was marked transient in the last request
     * set it to transient. Recursively do the same for all children
     *
     * @param comp the component to reset
     */
    private void resetTransientComponents(UIComponent comp)
    {
        if (comp.getAttributes().containsKey(PPRPanelGroupRenderer.TRANSIENT_MARKER_ATTRIBUTE)) {
            comp.setTransient(true);
        }
        for (Iterator iter = comp.getChildren().iterator(); iter.hasNext();) {
            UIComponent child = (UIComponent) iter.next();
            resetTransientComponents(child);
        }
    }

    /**
     * Checks if the currently processed Request is an AJAX request from a
     * PPRPanelGroup
     *
     * @param context the current {@link FacesContext}
     * @return true if a PPR request is being processed , false otherwise
     */
    public static boolean isPartialRequest(FacesContext context)
    {
        return context.getExternalContext().getRequestParameterMap().containsKey(PPR_PARAMETER);
    }

    /**
     * Respond to an AJAX request from a {@link PPRPanelGroup}. The
     * triggered components are determined by reading the
     * TRIGGERED_COMPONENTS_PARAMETER from either the RequestParameterMap or
     * the Request Map. Those componenets are encoded into an XML response.
     * The lifecycle is quit afterwards.
     *
     * @param context         the current {@link FacesContext}
     * @param externalContext the current {@link ExternalContext}
     * @param requestMap      Map containing the request attributes
     */
    private void processPartialPageRequest(FacesContext context, final ExternalContext externalContext, Map requestMap)
    {

        ServletResponse response = (ServletResponse) externalContext.getResponse();
        ServletRequest request = (ServletRequest) externalContext.getRequest();

        UIViewRoot viewRoot = context.getViewRoot();

        // Set Character encoding, contentType and locale for the response
        final String characterEncoding = request.getCharacterEncoding();
        String contentType = getContentType("text/xml", characterEncoding);
        response.setContentType(contentType);
        response.setLocale(viewRoot.getLocale());

        // Fetch the comma-separated list of triggered components
        String triggeredComponents = getTriggeredComponents(context);

        try {
            PrintWriter out = response.getWriter();
            context.setResponseWriter(new HtmlResponseWriterImpl(out, contentType, characterEncoding));
            out.print(XML_HEADER);
            out.print("<response>\n");
            encodeTriggeredComponents(out, triggeredComponents, viewRoot, context);
            out.print("</response>");
            out.flush();
        }
        catch (IOException e) {
            throw new FacesException(e);
        }

        context.responseComplete();
    }

    /**
     * Fetch the comma-separated list of triggered components. They are
     * either obtained from the Request Parameter Map where they had
     * previously been set using
     * {@link PPRPhaseListener#addTriggeredComponent(FacesContext, String))
     * or from the request parameter map.
     *
     * @param fc the current {@link FacesContext}
     * @return a comma separated list of component IDs of the components
     *         which are to be updated
     */
    private static String getTriggeredComponents(FacesContext fc)
    {
        String triggeredComponents = (String) fc.getExternalContext().getRequestMap().get(TRIGGERED_COMPONENTS_PARAMETER);

        if (triggeredComponents == null) {
            triggeredComponents = (String) fc.getExternalContext().getRequestParameterMap().get(TRIGGERED_COMPONENTS_PARAMETER);
        }

        return triggeredComponents;
    }

    /**
     * API method for adding triggeredComponents programmatically.
     *
     * @param fc                         the current {@link FacesContext}
     * @param triggeredComponentClientId client ID of the component which is to be updated in
     *                                   case of a PPR Response
     */
    public static void addTriggeredComponent(FacesContext fc, String triggeredComponentClientId)
    {
        String triggeredComponents = getTriggeredComponents(fc);

        if (triggeredComponents == null || triggeredComponents.trim().length() == 0) {
            triggeredComponents = new String();
        }
        else {
            triggeredComponents = triggeredComponents + ",";
        }

        triggeredComponents = triggeredComponents + triggeredComponentClientId;

        fc.getExternalContext().getRequestMap().put(TRIGGERED_COMPONENTS_PARAMETER, triggeredComponents);
    }

    /**
     * Generate content-type String either containing only the mime-type or
     * mime-type and character enconding.
     *
     * @param contentType the contentType/mimeType
     * @param charset     the character set
     * @return the content-type String to be used in an HTTP response
     */
    private String getContentType(String contentType, String charset)
    {
        if (charset == null || charset.trim().length() == 0) {
            return contentType;
        }
        else {
            return contentType + ";charset=" + charset;
        }
    }

    /**
     * Writes the XML elements for the triggered components to the provided
     * {@link PrintWriter}. Also encode the current state in a separate XML
     * element.
     *
     * @param out                 the output Writer
     * @param triggeredComponents comma-separated list of component IDs
     * @param viewRoot            the current ViewRoot
     * @param context             the current {@link FacesContext}
     */
    private void encodeTriggeredComponents(PrintWriter out, String triggeredComponents, UIViewRoot viewRoot, FacesContext context)
    {
        StringTokenizer st = new StringTokenizer(triggeredComponents, ",", false);
        String clientId;
        UIComponent component;
        boolean handleState = true;


        Set toAppendMessagesComponents = new HashSet();
        Set toReplaceMessagesComponents = new HashSet();

        // Iterate over the individual client IDs
        while (st.hasMoreTokens()) {
            clientId = st.nextToken();
            component = viewRoot.findComponent(clientId);
            if (component != null) {
                //get info about state writing/rendering
                //if at least one ppr does not update the state
                //the response will not include state information
                PPRPanelGroup ppr = null;
                int oldIndex = 0;
                if (component instanceof UIComponentPerspective) {
                    UIComponentPerspective uiComponentPerspective = (UIComponentPerspective) component;
                    ExecuteOnCallback getComponentCallback = new ExecuteOnCallback()
                    {
                        public Object execute(FacesContext context, UIComponent component)
                        {
                            return component;
                        }
                    };
                    Object retval = uiComponentPerspective.executeOn(context, getComponentCallback);
                    if (retval instanceof PPRPanelGroup) {
                        ppr = (PPRPanelGroup) retval;
                    }
                    else {
                        throw new IllegalArgumentException("Expect PPRPanelGroup or UiComponentPerspective");
                    }
                    //setup perspective
                    oldIndex = uiComponentPerspective.getUiData().getRowIndex();
                    uiComponentPerspective.getUiData().setRowIndex(uiComponentPerspective.getRowIndex());
                }
                else if (component instanceof PPRPanelGroup) {
                    ppr = (PPRPanelGroup) component;
                }
                else {
                    throw new IllegalArgumentException("Expect PPRPanelGroup or UiComponentPerspective");
                }
                if (ppr.getStateUpdate().booleanValue() == false) {
                    handleState = false;
                }
                //Check which messages components this group wants to append to
                if (ppr.getAppendMessages() != null) {
                    List appendMessagesForThisGroup = PPRSupport.getComponentsByCommaSeparatedIdList(context, ppr, ppr.getAppendMessages(), HtmlMessages.class);
                    toAppendMessagesComponents.addAll(appendMessagesForThisGroup);
                }

                //Check which messages components this group should refresh
                if (ppr.getReplaceMessages() != null) {
                    List replaceMessagesForThisGroup = PPRSupport.getComponentsByCommaSeparatedIdList(context, ppr, ppr.getReplaceMessages(), HtmlMessages.class);
                    toReplaceMessagesComponents.addAll(replaceMessagesForThisGroup);
                }

                // Write a component tag which contains a CDATA section whith
                // the rendered HTML
                // of the component children
                out.print("<component id=\"" + component.getClientId(context) + "\"><![CDATA[");
                boolean oldValue = HtmlRendererUtils.isAllowedCdataSection(context);
                HtmlRendererUtils.allowCdataSection(context, false);
                try {
                    component.encodeChildren(context);
                }
                catch (IOException e) {
                    throw new FacesException(e);
                }
                HtmlRendererUtils.allowCdataSection(context, oldValue);
                out.print("]]></component>");

                //tear down perspective
                if (component instanceof UIComponentPerspective) {
                    UIComponentPerspective uiComponentPerspective = (UIComponentPerspective) component;
                    uiComponentPerspective.getUiData().setRowIndex(oldIndex);
                }
            }
            else {
                log.debug("PPRPhaseListener component with id" + clientId + "not found!");
            }
        }

        boolean handleFacesMessages = !toAppendMessagesComponents.isEmpty() || !toReplaceMessagesComponents.isEmpty();

        if (handleFacesMessages) {   //encode all facesMessages into  xml-elements
            //for starter just return all messages (bother with client IDs later on)
            Iterator messagesIterator = context.getMessages();

            //only write messages-elements if messages are present
            while (messagesIterator.hasNext()) {
                FacesMessage msg = (FacesMessage) messagesIterator.next();
                String messageText = msg.getSummary() + " " + msg.getDetail();
                out.print("<message><![CDATA[");
                out.print(messageText);
                out.print("]]></message>");
            }

            //Replace has precedence over append
            Collection intersection = CollectionUtils.intersection(toAppendMessagesComponents, toReplaceMessagesComponents);
            for (Iterator iterator = intersection.iterator(); iterator.hasNext();) {
                UIComponent uiComponent = (UIComponent) iterator.next();
                log.warn("Component with id " + uiComponent.getClientId(context) +
                    " is marked for replace and append messages -> replace has precedence");
                toAppendMessagesComponents.remove(uiComponent);
            }

            for (Iterator iterator = toAppendMessagesComponents.iterator(); iterator.hasNext();) {
                UIComponent uiComponent = (UIComponent) iterator.next();
                out.print("<append id=\"");
                out.print(uiComponent.getClientId(context));
                out.print("\"/>");
            }

            for (Iterator iterator = toReplaceMessagesComponents.iterator(); iterator.hasNext();) {
                UIComponent uiComponent = (UIComponent) iterator.next();
                out.print("<replace id=\"");
                out.print(uiComponent.getClientId(context));
                out.print("\"/>");
            }
        }

        if (handleState) {
            // Write the serialized state into a separate XML element
            out.print("<state>");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            StateManager stateManager = facesContext.getApplication().getStateManager();
            StateManager.SerializedView serializedView = stateManager.saveSerializedView(facesContext);
            try {
                stateManager.writeState(facesContext, serializedView);
            }
            catch (IOException e) {
                throw new FacesException(e);
            }
            out.print("</state>");
        }

    }


    private static List getComponentsByCommaSeparatedList(FacesContext context, UIComponent comp, String commaSeparatedIdList, Class componentType)
    {
        List retval = new ArrayList();
        UIComponent currentComponent = null;
        String[] componentIds = StringUtils.split(commaSeparatedIdList, ',');
        for (int i = 0; i < componentIds.length; i++) {
            String componentId = componentIds[i];

        }
        return retval;
    }

    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }
}
