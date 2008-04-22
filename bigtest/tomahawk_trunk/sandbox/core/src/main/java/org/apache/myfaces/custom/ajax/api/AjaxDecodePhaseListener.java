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

package org.apache.myfaces.custom.ajax.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.UIComponentPerspective;
import org.apache.myfaces.custom.ajax.util.AjaxRendererUtils;
import org.apache.myfaces.custom.inputAjax.HtmlCommandButtonAjax;
import org.apache.myfaces.custom.util.URIComponentUtils;
import org.apache.myfaces.shared_tomahawk.component.ExecuteOnCallback;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlResponseWriterImpl;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.FormInfo;

import javax.faces.application.StateManager;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This short circuits the life cycle and applies updates to affected components only
 * <p/>
 * User: treeder
 * Date: Oct 26, 2005
 * Time: 6:03:21 PM
 */
public class AjaxDecodePhaseListener
        implements PhaseListener
{
    private static Log log = LogFactory.getLog(AjaxDecodePhaseListener.class);


    /**
     * Some Ajax components might do special decoding at apply request values phase
     *
     * @return PhaseId The AJAX decode phase listener will be invoked before apply request values phase.
     */
    public PhaseId getPhaseId()
    {
        return PhaseId.APPLY_REQUEST_VALUES;
    }

    public void beforePhase(PhaseEvent event)
    {
        log.debug("In AjaxDecodePhaseListener beforePhase");
        FacesContext context = event.getFacesContext();
        Map externalRequestMap = context.getExternalContext().getRequestParameterMap();

        if (externalRequestMap.containsKey("affectedAjaxComponent"))
        {
            UIViewRoot root = context.getViewRoot();
            String affectedAjaxComponent = (String) context.getExternalContext()
                                                        .getRequestParameterMap().get("affectedAjaxComponent");
            UIComponent ajaxComponent = root.findComponent(affectedAjaxComponent);
            //checking if ajaxComp is inside a dataTable - necessary for non JSF 1.2 MyFaces implementation
            if (ajaxComponent instanceof UIComponentPerspective)
            {
                UIComponentPerspective componentPerspective = (UIComponentPerspective) ajaxComponent;
                ajaxComponent = (UIComponent) componentPerspective.executeOn(context, new ExecuteOnCallback()
                {
                    public Object execute(FacesContext facesContext, UIComponent ajaxComponent)
                    {
                        handleAjaxRequest(ajaxComponent, facesContext);

                        return ajaxComponent;
                    }
                });
            }
            else
            {
                handleAjaxRequest(ajaxComponent, context);
            }

            StateManager stateManager = context.getApplication().getStateManager();
            if (!stateManager.isSavingStateInClient(context))
            {
                stateManager.saveSerializedView(context);
            }
            context.responseComplete();
        }
    }


    private void handleAjaxRequest(UIComponent ajaxComponent, FacesContext facesContext)
    {
        String updateOnly = (String) facesContext.getExternalContext().getRequestParameterMap().get("updateOnly");
        if(updateOnly == null) {
            // then decode
            decodeAjax(ajaxComponent, facesContext);
            facesContext.getViewRoot().processApplication(facesContext);
        }
        // else only update
        encodeAjax(ajaxComponent, facesContext);
    }

    private void decodeAjax(UIComponent ajaxComponent, FacesContext context)
    {

        String affectedAjaxComponent = (String) context.getExternalContext()
                                                    .getRequestParameterMap().get("affectedAjaxComponent");
        if (ajaxComponent == null)
        {
            String msg = "Component with id [" + affectedAjaxComponent + "] not found in view tree.";
            log.error(msg);
            throw new ComponentNotFoundException(msg);
        }
        log.debug("affectedAjaxComponent: " + ajaxComponent + " - " + ajaxComponent.getId());
        //todo: refactor this completely - should be somewhere else, but definitely not
        //in the general phase listener
        if (ajaxComponent instanceof HtmlCommandButtonAjax)
        {
            // special treatment for this one, it will try to update the entire form
            // 1. get surrounding form
            //String elname = (String) requestMap.get("elname");
            FormInfo fi = RendererUtils.findNestingForm(ajaxComponent, context);
            UIComponent form = fi.getForm();
            //System.out.println("FOUND FORM: " + form);
            if (form != null)
            {
                form.processDecodes(context);
                //if(context.getRenderResponse())
                    form.processValidators(context);
                //else log.debug("Decode failed on Ajax Decode");
                //if(context.getRenderResponse())
                    form.processUpdates(context);
                //else log.debug("Validation failed on Ajax Decode");
                //System.out.println("DONE!");
            }
        }
        else if (ajaxComponent instanceof AjaxComponent)
        {
            try
            {
                // Now let the component decode this request
                ((AjaxComponent) ajaxComponent).decodeAjax(context);
            }
            catch (Exception e)
            {
                log.error("Exception while decoding ajax-request", e);
            }
        }
        else
        {
            log.error("Found component is no ajaxComponent : " + RendererUtils.getPathToComponent(ajaxComponent));
        }
    }


    private void encodeAjax(UIComponent component, FacesContext context)
    {
        ServletResponse response = 
            (ServletResponse) context.getExternalContext().getResponse();
        ServletRequest request = 
            (ServletRequest) context.getExternalContext().getRequest();
        UIViewRoot viewRoot = context.getViewRoot();
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        
        String charset = (String) requestMap.get("charset");

       /* Handle character encoding as of section 2.5.2.2 of JSF 1.1:
        * At the beginning of the render-response phase, the ViewHandler must ensure
        * that the response Locale is set to that of the UIViewRoot, for exampe by
        * calling ServletResponse.setLocale() when running in the servlet environment.
        * Setting the response Locale may affect the response character encoding.
        *
        * Since there is no 'Render Response' phase for AJAX requests, we have to handle
        * this manually.
        */
        response.setLocale(viewRoot.getLocale());


        if(component instanceof DeprecatedAjaxComponent)
        {
            try
            {
                String contentType = getContentType("text/xml", charset);
                response.setContentType(contentType);

                StringBuffer buff = new StringBuffer();
                buff.append("<?xml version=\"1.0\"?>\n");
                buff.append("<response>\n");

                PrintWriter out = response.getWriter();
                out.print(buff);

                // imario@apache.org: setup response writer, otherwise the component will fail with an NPE. I dont know why this worked before.
                context.setResponseWriter(new HtmlResponseWriterImpl(out,
                                          contentType,
                                          request.getCharacterEncoding()));

                if (component instanceof HtmlCommandButtonAjax)
                {
                    buff = new StringBuffer();
                    buff.append("<triggerComponent id=\"");
                    buff.append(component.getClientId(context));
                    buff.append("\" />\n");
                    out.print(buff);                    

                    // special treatment for this one, it will try to update the entire form
                    // 1. get surrounding form
                    //String elname = (String) requestMap.get("elname");
                    FormInfo fi = RendererUtils.findNestingForm(component, context);
                    UIComponent form = fi.getForm();
                    //System.out.println("FOUND FORM: " + form);
                    if (form != null)
                    {
                        // special case, add responses from all components in form
                        encodeChildren(form, context, requestMap);
                    }
                }
                else if (component instanceof AjaxComponent)
                {
                    // let component render xml response
                    // NOTE: probably don't need an encodeAjax in each component, but leaving it in until that's for sure
                    ((AjaxComponent) component).encodeAjax(context);
                } else {
                    // just get latest value
                    AjaxRendererUtils.encodeAjax(context, component);
                }
                // end response
                out.print("</response>");
                out.flush();
            }
            catch (IOException e)
            {
                log.error("Exception while rendering ajax-response", e);
            }
        }
        else if (component instanceof AjaxComponent)
        {
            try
            {
                if (context.getResponseWriter() == null)
                {
                    String contentType = getContentType("text/html", charset);
                    response.setContentType(contentType);
                    PrintWriter writer = response.getWriter();
                    context.setResponseWriter(new HtmlResponseWriterImpl(writer,
                                              contentType, response.getCharacterEncoding()));
                }

                ((AjaxComponent) component).encodeAjax(context);
            }
            catch (IOException e)
            {
                log.error("Exception while rendering ajax-response", e);
            }
        }

    }

    private String getContentType(String contentType, String charset)
    {
        if (charset == null || charset.trim().length() == 0)
            return contentType;
        else
            return contentType + ";charset=" + charset;
    }

    private void encodeChildren(UIComponent form, FacesContext context, Map requestMap)
            throws IOException
    {                                     
        List formChildren = form.getChildren();
        for (int i = 0; i < formChildren.size(); i++)
        {
            UIComponent uiComponent = (UIComponent) formChildren.get(i);
            //System.out.println("component id: " + uiComponent.getClientId(context));
            // only if it has a matching id in the request list
            if (requestMap.containsKey(uiComponent.getClientId(context)))
            {
                //System.out.println("FOUND COMPONENT SO ENCODING AJAX");
                AjaxRendererUtils.encodeAjax(context, uiComponent);
            }
            // recurse
            encodeChildren(uiComponent, context, requestMap);
        }
    }

    /**
     * spit out each name/value pair
     * THIS IS IN HASHMAPUTILS, BUT FOR SOME REASON, ISN'T GETTING INTO THE JARS
     */
    public static String mapToString(Map map)
    {
        Set entries = map.entrySet();
        Iterator iter = entries.iterator();
        StringBuffer buff = new StringBuffer();
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry) iter.next();
            buff.append("[" + entry.getKey() + "," + entry.getValue() + "]\n");
        }
        return buff.toString();
    }

    public static Object getValueForComponent(FacesContext context, UIComponent component)
    {
        String possibleClientId = component.getClientId(context);

        if (!context.getExternalContext().getRequestParameterMap().containsKey(possibleClientId))
        {
            possibleClientId = (String) context.getExternalContext()
                                            .getRequestParameterMap().get("affectedAjaxComponent");

            log.debug("affectedAjaxComponent: " + possibleClientId);
            UIViewRoot root = context.getViewRoot();
            UIComponent ajaxComponent = root.findComponent(possibleClientId);
            if (ajaxComponent instanceof UIComponentPerspective)
            {
                UIComponentPerspective componentPerspective = (UIComponentPerspective) ajaxComponent;

                ajaxComponent = (UIComponent)componentPerspective.executeOn(context, new ExecuteOnCallback()
                {
                    public Object execute(FacesContext facesContext, UIComponent uiComponent)
                    {
                        return uiComponent;
                    }
                });
            }

            if (ajaxComponent != component)
            {
                log.error("No value found for this component : " + possibleClientId);
                return null;
            }
        }

        Object encodedValue = context.getExternalContext().getRequestParameterMap().get(possibleClientId);

        if (encodedValue instanceof String)
        {
            return URIComponentUtils.decodeURIComponent((String) encodedValue);
        }
        else return encodedValue;
    }

    public void afterPhase(PhaseEvent event)
    {

    }


}
