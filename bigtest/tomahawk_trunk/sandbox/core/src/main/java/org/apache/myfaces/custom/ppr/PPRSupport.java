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
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.dojo.DojoConfig;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.custom.subform.SubForm;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.FormInfo;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * @author Ernst Fastl
 * @author Thomas Spiegl
 */
public class PPRSupport
{
    private static Log log = LogFactory.getLog(PPRSupport.class);

    private static final String PPR_INITIALIZED = "org.apache.myfaces.ppr.INITIALIZED";

    private static final String PPR_JS_FILE = "ppr.js";

    private static final String ADD_PARTIAL_TRIGGER_FUNCTION = "addPartialTrigger";

    private static final String ADD_PERIODICAL_TRIGGER_FUNCTION = "addPeriodicalTrigger";

    private static final String ADD_PARTIAL_TRIGGER_PATTERN_FUNCTION = "addPartialTriggerPattern";

    private static final String SET_SUBFORM_ID_FUNCTION = "setSubFormId";

    private static final String SET_COMPONENT_UPDATE_FUNCTION = "setComponentUpdateFunction";

    private static final String ADD_INLINE_LOADING_MESSAGE_FUNCTION = "addInlineLoadingMessage";

    private static final String MY_FACES_PPR_INIT_CODE = "new org.apache.myfaces.PPRCtrl";


    public final static String COMMAND_CONFIGURED_MARK = PPRSupport.class.getName() + "_CONFIGURED";

    public final static String PROCESS_COMPONENTS = PPRSupport.class.getName() + "PROCESS_COMPONENTS";


    public static boolean isPartialRequest(FacesContext facesContext)
    {
        return PPRPhaseListener.isPartialRequest(facesContext);
    }


    /**
     * Renders inline JavaScript registering an onLoad function for:
     * <ul>
     * <li>Initializing the PPRCtrl for the current Form</li>
     * </ul>
     *
     * @param facesContext the current {@link javax.faces.context.FacesContext}
     * @param uiComponent  the currently rendered {@link PPRPanelGroup}
     * @throws java.io.IOException if the underlying Layer throws an {@link java.io.IOException}
     *                             it is passed through
     */
    public static void initPPR(FacesContext facesContext, PPRPanelGroup uiComponent) throws IOException
    {
        //if(isPartialRequest(facesContext)) {
        //    return;
        //}
        final ExternalContext externalContext = facesContext.getExternalContext();
        final Map requestMap = externalContext.getRequestMap();

        // Do not render the JavaScript if answering to a PPR response

        //Initialize the client side PPR engine
        if (!requestMap.containsKey(PPR_INITIALIZED)) {
            requestMap.put(PPR_INITIALIZED, Boolean.TRUE);

            String encoding = "UTF-8"; // Hardcoded default
            if (facesContext.getResponseWriter().getCharacterEncoding() != null) {
                encoding = facesContext.getResponseWriter().getCharacterEncoding();
            }

            DojoConfig currentConfig = DojoUtils.getDjConfigInstance(facesContext);
            currentConfig.setBindEncoding(encoding);

            String javascriptLocation = (String) uiComponent.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
            AddResource addResource = AddResourceFactory.getInstance(facesContext);
            DojoUtils.addMainInclude(facesContext, uiComponent, javascriptLocation, currentConfig);
            DojoUtils.addRequire(facesContext, uiComponent, "dojo.io.*");
            DojoUtils.addRequire(facesContext, uiComponent, "dojo.event.*");
            DojoUtils.addRequire(facesContext, uiComponent, "dojo.xml.*");
            addResource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, PPRPanelGroup.class, PPR_JS_FILE);
        }
    }

    /**
     * Renders inline JavaScript registering an onLoad function for:
     * <ul>
     * <li>Initializing the PPRCtrl for the current Form</li>
     * <li>Registering partialTriggers</li>
     * <li>Registering partialTriggerPatterns</li>
     * <li>Starting periodical updates</li>
     * <li>Registering inline Loading messages</li>
     * </ul>
     *
     * @param facesContext the current {@link FacesContext}
     * @param pprGroup     the currently rendered {@link PPRPanelGroup}
     * @throws IOException if the underlying Layer throws an {@link IOException}
     *                     it is passed through
     */
    public static void encodeJavaScript(FacesContext facesContext, PPRPanelGroup pprGroup) throws IOException
    {
        StringBuffer script = new StringBuffer();

        // all JS is put inside a function passed to dojoOnLoad
        // this is necessary in order to be able to replace all button onClick
        // handlers

        script.append("dojo.addOnLoad( function(){ ");

        String pprCtrlReference = initPPRFormControl(facesContext, pprGroup, script);

        encodePeriodicalUpdates(facesContext, pprGroup, script, pprCtrlReference);

        encodePartialTriggerPattern(facesContext, pprGroup, script, pprCtrlReference);

        encodeSubFormFunction(facesContext, pprGroup, script, pprCtrlReference);

        encodeDomUpdateConfig(facesContext, pprGroup, script, pprCtrlReference);

        encodeInlineLoadMsg(facesContext, pprGroup, script, pprCtrlReference);

        encodePartialTriggers(facesContext, pprGroup, script, pprCtrlReference);

        // closing the dojo.addOnLoad call
        script.append("});");

        //Really render the script
        renderInlineScript(facesContext, pprGroup, script.toString());
    }

    public static void encodeJavaScriptTriggerOnly(FacesContext context, UIComponent uiComponent, PPRPanelGroup pprGroup,
                                        PartialTriggerParser.PartialTrigger trigger) throws IOException
    {
        StringBuffer script = new StringBuffer();
        script.append("dojo.addOnLoad( function(){ ");
        String pprCtrlReference = initPPRFormControl(context, pprGroup, script);
        String clientId = pprGroup.getClientId(context);
        encodePartialTrigger(context, script, pprCtrlReference, clientId, uiComponent, trigger);
        // closing the dojo.addOnLoad call
        script.append("});");

        //Really render the script
        renderInlineScript(context, pprGroup, script.toString());
    }

    private static void encodePartialTrigger(FacesContext facesContext,
                                             StringBuffer script,
                                             String pprCtrlReference,
                                             String clientId,
                                             UIComponent partialTriggerComponent,
                                             PartialTriggerParser.PartialTrigger trigger)
    {
        String partialTriggerClientId;
        String partialTriggerId = trigger.getPartialTriggerId();
        if (partialTriggerComponent == null) {
            partialTriggerComponent = facesContext.getViewRoot().findComponent(partialTriggerId);
        }
        if (partialTriggerComponent != null) {
            partialTriggerClientId = partialTriggerComponent.getClientId(facesContext);
            script.append(pprCtrlReference + "." + ADD_PARTIAL_TRIGGER_FUNCTION + "('" + partialTriggerClientId + "'," + encodeArray(trigger.getEventHooks()) + ",'" + clientId + "');");
        }
        else {
            if (log.isDebugEnabled()) {
                log.debug("PPRPanelGroupRenderer Component with id " + partialTriggerId + " not found!");
            }
        }
    }

    private static void encodePartialTriggers(FacesContext context, PPRPanelGroup pprGroup, StringBuffer script, String pprCtrlReference)
    {
        String clientId = pprGroup.getClientId(context);
        UIComponent partialTriggerComponent;

        List partialTriggerIds = pprGroup.parsePartialTriggers();
        for (int i = 0; i < partialTriggerIds.size(); i++) {
            PartialTriggerParser.PartialTrigger trigger = (PartialTriggerParser.PartialTrigger) partialTriggerIds
                .get(i);
            partialTriggerComponent = pprGroup.findComponent(trigger.getPartialTriggerId());
            encodePartialTrigger(context, script, pprCtrlReference, clientId, partialTriggerComponent, trigger);
        }
    }

    private static void encodePartialTriggerPattern(FacesContext context, PPRPanelGroup pprGroup, StringBuffer script, String pprCtrlReference)
    {
        String clientId = pprGroup.getClientId(context);

        String partialTriggerPattern = pprGroup.getPartialTriggerPattern();

        //handle partial trigger patterns
        if (partialTriggerPattern != null && partialTriggerPattern.trim().length() > 0) {
            script.append(pprCtrlReference + "." + ADD_PARTIAL_TRIGGER_PATTERN_FUNCTION + "('" + partialTriggerPattern + "','" + clientId + "');");
        }

    }

    private static void encodeInlineLoadMsg(FacesContext context, PPRPanelGroup pprGroup, StringBuffer script, String pprCtrlReference)
    {
        String clientId = pprGroup.getClientId(context);
        String inlineLoadingMessage = pprGroup.getInlineLoadingMessage();

        //handle inline loading messages
        if (inlineLoadingMessage != null && inlineLoadingMessage.trim().length() > 0) {
            script.append(pprCtrlReference + "." + ADD_INLINE_LOADING_MESSAGE_FUNCTION + "('" + inlineLoadingMessage + "','" + clientId + "');");
        }
    }

    private static void encodeSubFormFunction(FacesContext context, PPRPanelGroup pprGroup, StringBuffer script, String pprCtrlReference)
    {
        String clientId = pprGroup.getClientId(context);
        SubForm subFormParent = findParentSubForm(pprGroup);
        if (subFormParent != null) {
            script.append(pprCtrlReference + "." + SET_SUBFORM_ID_FUNCTION + "('" + subFormParent.getId() + "','" + clientId + "');");
        }
    }

    private static void encodeDomUpdateConfig(FacesContext context, PPRPanelGroup pprGroup, StringBuffer script, String pprCtrlReference)
    {
        String componentUpdateFunction = pprGroup.getComponentUpdateFunction();
        if (!StringUtils.isEmpty(componentUpdateFunction)) {
            script.append(pprCtrlReference + "." + SET_COMPONENT_UPDATE_FUNCTION+ "('" + componentUpdateFunction + "');");
        }
    }

    private static void encodePeriodicalUpdates(FacesContext facesContext, PPRPanelGroup pprGroup, StringBuffer script, String pprCtrlReference)
    {
        String clientId = pprGroup.getClientId(facesContext);

        //Handle periodical updates
        if (pprGroup.getPeriodicalUpdate() != null) {
            List partialTriggers = pprGroup.parsePeriodicalTriggers();
            if (partialTriggers.size() == 0) {
                Integer wait = null;
                if (pprGroup.getExcludeFromStoppingPeriodicalUpdate() != null) {
                    wait = pprGroup.getWaitBeforePeriodicalUpdate();
                }
                script.append(pprCtrlReference + ".startPeriodicalUpdate(" + pprGroup.getPeriodicalUpdate() + ",'" + clientId + "', " + wait + ");");
            }
            else {
                String periodicalTriggerId;
                String periodicalTriggerClientId;
                UIComponent periodicalTriggerComponent;
                for (int i = 0; i < partialTriggers.size(); i++) {
                    PartialTriggerParser.PartialTrigger trigger = (PartialTriggerParser.PartialTrigger) partialTriggers
                        .get(i);
                    periodicalTriggerId = trigger.getPartialTriggerId();
                    periodicalTriggerComponent = pprGroup.findComponent(periodicalTriggerId);
                    if (periodicalTriggerComponent == null) {
                        periodicalTriggerComponent = facesContext.getViewRoot().findComponent(periodicalTriggerId);
                    }

                    // Component found
                    if (periodicalTriggerComponent != null) {
                        periodicalTriggerClientId = periodicalTriggerComponent.getClientId(facesContext);
                        script.append(pprCtrlReference + "." + ADD_PERIODICAL_TRIGGER_FUNCTION +
                            "('" + periodicalTriggerClientId + "'," +
                            encodeArray(trigger.getEventHooks()) + ",'" + clientId + "', " +
                            pprGroup.getPeriodicalUpdate() + ");");

                        // Component missing
                    }
                    else {
                        if (log.isDebugEnabled()) {
                            log.debug("PPRPanelGroupRenderer Component with id " + periodicalTriggerId + " not found!");
                        }
                    }
                }
            }

            String idRegex = pprGroup.getExcludeFromStoppingPeriodicalUpdate();

            if (idRegex != null) {
                script.append(pprCtrlReference + ".excludeFromStoppingPeriodicalUpdate('" + idRegex + "');");
            }
        }
    }

    private static String initPPRFormControl(FacesContext facesContext, PPRPanelGroup pprGroup, StringBuffer script)
    {
        FormInfo fi = RendererUtils.findNestingForm(pprGroup, facesContext);
        if (fi == null) {
            throw new FacesException("PPRPanelGroup must be embedded in a form.");
        }
        final String formName = fi.getFormName();
        Map requestMap = facesContext.getExternalContext().getRequestMap();

        String pprCtrlReference = "dojo.byId('" + formName + "').myFacesPPRCtrl";

        //Each form containing PPRPanelGroups has its own PPRCtrl

        // the following complicated stuff should deal with the following use-cases:
        // 1) normal create ppr on non-ppr-response
        // 2) create ppr on ppr-response
        // 3) add triggers to ppr on ppr-response (e.g to commands within uidata)
        //
        // get state of the ppr component ...
        boolean pprInited = pprGroup.getInitializationSent();
        if (!PPRSupport.isPartialRequest(facesContext))
        {
            // ... but override with current request state if we are not within an
            // ppr request.
            pprInited = Boolean.TRUE.equals(requestMap.get(PPR_INITIALIZED + "." + formName));
        }

        if (!pprInited) {
            pprGroup.setInitializationSent(true);
            requestMap.put(PPR_INITIALIZED + "." + formName, Boolean.TRUE);

            script.append(pprCtrlReference + "=" + MY_FACES_PPR_INIT_CODE + "('" + formName + "'," + pprGroup.getShowDebugMessages().booleanValue() + "," + pprGroup.getStateUpdate().booleanValue() + ");\n");

            if (pprGroup.getPeriodicalUpdate() != null) {
                script.append(pprCtrlReference + ".registerOnSubmitInterceptor();");
            }
        }
        return pprCtrlReference;
    }

    public static SubForm findParentSubForm(UIComponent base)
    {
        if (base == null) {
            return null;
        }
        if (base instanceof SubForm) {
            return (SubForm) base;
        }
        return findParentSubForm(base.getParent());
    }

    private static String encodeArray(List eventHooks)
    {
        if (eventHooks == null || eventHooks.size() == 0) {
            return "null";
        }
        else {
            StringBuffer buf = new StringBuffer();
            buf.append("[");

            for (int i = 0; i < eventHooks.size(); i++) {
                if (i > 0) {
                    buf.append(",");
                }
                String eventHook = (String) eventHooks.get(i);
                buf.append("'");
                buf.append(eventHook);
                buf.append("'");
            }
            buf.append("]");

            return buf.toString();
        }
    }

    /**
     * Helper to write an inline javascript at the exact resource location
     * of the call.
     *
     * @param facesContext The current faces-context.
     * @param component    The component for which the script is written.
     * @param script       The script to be written.
     * @throws IOException A forwarded exception from the underlying renderer.
     */
    private static void renderInlineScript(FacesContext facesContext, UIComponent component, String script) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML.SCRIPT_ELEM, component);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        writer.write(script);
        writer.endElement(HTML.SCRIPT_ELEM);
    }

    /**
     * get all components by given id-string-list ("id1,id2,id3") and appropriate type
     *
     * @param context
     * @param comp
     * @param idList
     * @param desiredType
     * @return
     */
    public static List getComponentsByCommaSeparatedIdList(FacesContext context, UIComponent comp, String idList, Class desiredType)
    {
        List retval = new ArrayList();
        UIComponent currentComponent = null;
        String[] ids = StringUtils.split(idList, ',');
        for (int i = 0; i < ids.length; i++) {
            String id = StringUtils.trim(ids[i]);
            currentComponent = comp.findComponent(id);
            if (nullSafeCheckComponentType(desiredType, currentComponent)) {
                retval.add(currentComponent);
            }
            else {
                currentComponent = context.getViewRoot().findComponent(id);
                if (nullSafeCheckComponentType(desiredType, currentComponent)) {
                    retval.add(currentComponent);
                }
            }
        }
        return retval;
    }

    private static boolean nullSafeCheckComponentType(Class desiredType, UIComponent currentComponent)
    {
        return currentComponent != null && (desiredType == null || desiredType.isAssignableFrom(currentComponent.getClass()));
    }
}
