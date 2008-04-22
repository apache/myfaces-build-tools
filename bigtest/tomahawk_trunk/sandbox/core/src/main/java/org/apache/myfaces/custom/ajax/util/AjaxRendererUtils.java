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

package org.apache.myfaces.custom.ajax.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlMessages;
import org.apache.myfaces.custom.ajax.AjaxCallbacks;
import org.apache.myfaces.custom.inputAjax.HtmlInputTextAjax;
import org.apache.myfaces.custom.prototype.PrototypeResourceLoader;
import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlMessageRendererBase;

/**
 * @author Travis Reeder (latest modification by $Author: mmarinschek $)
 * @version $Revision: 290397 $ $Date: 2005-09-20 10:35:09 +0200 (Di, 20 Sep 2005) $
 */
public final class AjaxRendererUtils
{
    public static final String STYLECLASS_LOADER = "myFacesInputSuggestAjax";

    /**
     * util class.
     */
    private AjaxRendererUtils()
    {
        //util clazz
    }

    private static final Log log = LogFactory.getLog(AjaxRendererUtils.class);
    public static final String JAVASCRIPT_ENCODED = "org.apache.myfaces.custom.inputAjax.JAVASCRIPT_ENCODED";
    public static final String JS_MYFACES_NAMESPACE = "_MyFaces_inputAjax_";

    public static void addPrototypeScript(FacesContext context, UIComponent component, AddResource addResource)
    {
        // todo: replace with DOJO
        // todo: this doesn't need to be called from every component, move it into encodeAjax or something
        String javascriptLocation = (String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        if (javascriptLocation != null)
        {
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/prototype.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/effects.js");
        }
        else
        {
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, PrototypeResourceLoader.class, "prototype.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, PrototypeResourceLoader.class, "effects.js");
        }
        addResource.addStyleSheet(context, AddResource.HEADER_BEGIN, AjaxRendererUtils.class, "myFaces_Ajax.css");
    }

    public static void writeAjaxScript(FacesContext context, ResponseWriter out, AjaxCallbacks component)
            throws IOException
    {
        writeAjaxScript(context, out, component, null);
    }

    /**
     * Not really liking having the extraParams thing, seems to inflexible for altering other things
     *
     * @param context
     * @param out
     * @param component
     * @param extraParams
     * @throws IOException
     */
    public static void writeAjaxScript(FacesContext context, ResponseWriter out, AjaxCallbacks component, String extraParams) throws IOException
    {
        UIComponent uiComponent = (UIComponent) component;
        String clientId = uiComponent.getClientId(context);
        String viewId = context.getViewRoot().getViewId();
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        String ajaxURL = viewHandler.getActionURL(context, viewId);

        String ajaxMessagesId = null;

        if (uiComponent instanceof HtmlInputTextAjax)
        {
            //finding the corresponding messages component to display an ajaxMessage
            UIComponent ajaxMessages = (UIComponent) ComponentUtils
                    .findFirstMessagesComponent(context, context.getViewRoot());

            if (ajaxMessages != null && ((HtmlMessages) ajaxMessages).getForceSpan())
            {
                ajaxMessagesId = ajaxMessages.getClientId(context);
            }
        }

        String jsNameSpace = //uiComponent.getId() +
                JS_MYFACES_NAMESPACE;
        String AJAX_RESPONSE_MAP = JS_MYFACES_NAMESPACE + "ajaxResponseMap";

        // todo: only namespace the things that are specific to the component and only output those a second time, use comment below to limit
        // // check to see if javascript has already been written

        if (!context.getExternalContext().getRequestMap().containsKey(JAVASCRIPT_ENCODED))
        {
            out.startElement(HTML.SCRIPT_ELEM, null);
            out.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);

            out.writeText("var " + jsNameSpace + "ajaxUrl = '" + ajaxURL + "';\n", null);
            out.writeText("var " + jsNameSpace + "globalErrorsId = '" + ajaxMessagesId + "';\n", null);
            // for component specific mappings
            out.writeText("var " + AJAX_RESPONSE_MAP + " = new Object();\n", null);

            out.endElement(HTML.SCRIPT_ELEM);

            AddResource addResource = AddResourceFactory.getInstance(context);
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN,
                    AjaxRendererUtils.class, "inputAjax.js");
                    //"/js/inputAjax.js");

            context.getExternalContext().getRequestMap().put(JAVASCRIPT_ENCODED, Boolean.TRUE);
        }

        // component specific mappings, one per component
        out.startElement(HTML.SCRIPT_ELEM, null);
        out.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);
        out.writeText(AJAX_RESPONSE_MAP + "['" + clientId + "'] = new Object();\n", null);
        if (component.getOnSuccess() != null)
            out.writeText(AJAX_RESPONSE_MAP + "['" + clientId + "']['onSuccessFunction'] = " + component.getOnSuccess() + ";\n", null);
        if (component.getOnFailure() != null)
            out.writeText(AJAX_RESPONSE_MAP + "['" + clientId + "']['onFailureFunction'] = " + component.getOnFailure() + ";\n", null);
        if (component.getOnStart() != null)
            out.writeText(AJAX_RESPONSE_MAP + "['" + clientId + "']['onStartFunction'] = " + component.getOnStart() + ";\n", null);
        out.endElement(HTML.SCRIPT_ELEM);

    }


    public static void encodeAjax(FacesContext context, UIComponent component)
            throws IOException
    {
        encodeAjax(context, component, null);
    }

    /**
     * Outputs elementUpdate elements with the client id and value.
     * Also outputs error elements.
     *
     * @param context
     * @param component
     * @param extraReturnAttributes
     * @throws IOException
     */
    public static void encodeAjax(FacesContext context, UIComponent component, Map extraReturnAttributes) throws IOException
    {
        String clientId = component.getClientId(context);
        Object responseOb = context.getExternalContext().getResponse();
        if (responseOb instanceof HttpServletResponse)
        {
            HttpServletResponse response = (HttpServletResponse) responseOb;
            //HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            StringBuffer buff = new StringBuffer();

            // send error messages
            boolean hasErrorMessages = false;
            Iterator iter = context.getMessages(clientId);
            while (iter.hasNext())
            {
                FacesMessage msg = (FacesMessage) iter.next();
                String style = "";
                String styleClass = "";

                String msgForId = clientId + "_msgFor";
                //System.out.println("Looking for component: " + msgForId);
                UIComponent msgComponent = context.getViewRoot().findComponent(msgForId);
                String msgId = null;
                if (msgComponent != null)
                {
                    //System.out.println("Component found");
                    // then send to update single component
                    // get styleclass
                    String[] styleAndClass = HtmlMessageRendererBase.getStyleAndStyleClass(msgComponent, msg.getSeverity());
                    style = styleAndClass[0];
                    styleClass = styleAndClass[1];
                    msgId = msgComponent.getClientId(context);
                    //System.out.println("style: " + style);
                }
                else
                {
                    // send to update global messages, maybe this could happen on the client side though ?
                }
                buff.append("<error elname=\"").append(clientId)
                        .append("\" severity=\"").append(msg.getSeverity().toString());
                if (styleClass != null) buff.append("\" styleClass=\"").append(styleClass);
                if (style != null) buff.append("\" style=\"").append(style);
                buff.append("\" summary=\"").append(msg.getSummary())
                        .append("\" ");
                        if(msgId != null) buff.append(" msgId=\"").append(msgId).append("\"");
                        buff.append(">\n");
                String detail = msg.getDetail();
                if (detail != null)
                {
                    buff.append("<detail>");
                    buff.append(msg.getDetail());
                    buff.append("</detail>\n");
                }
                buff.append("</error>\n");
                hasErrorMessages = true;
            }
            if (!hasErrorMessages)
            {
                // send elementUpdated messages
                buff.append("<elementUpdated elname=\"").append(clientId).append("\"");
                if(component instanceof UIOutput){
                    UIOutput uiOutput = (UIOutput) component;
                    // todo: might have to make sure this value can be serialized like this
                    // todo: and should probably be in text block, rather than an attribute
                    buff.append(" elvalue=\"").append(uiOutput.getValue()).append("\"");
                    // this is needed to know how to update the html element, javascript doesn't give the correct info!
                    //buff.append(" eltype=\"output\"");
                }

                if (extraReturnAttributes != null)
                {
                    Iterator iter2 = extraReturnAttributes.keySet().iterator();
                    while (iter2.hasNext())
                    {
                        String key = (String) iter2.next();
                        buff.append(" ").append(key).append("=\"").append(extraReturnAttributes.get(key).toString()).append("\"");
                    }
                }
                buff.append(" />");
                buff.append("\n");
            }

            String output = buff.toString();
            log.debug(output);
            PrintWriter out = response.getWriter();
            out.print(output);

        }
    }

    /**
     * Writes an animated image to show that something is happening
     * @param context
     * @param comp
     */
    public static void writeLoadingImage(FacesContext context, UIComponent comp) throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTML.SPAN_ELEM, null);
        writer.writeAttribute(HTML.ID_ATTR, comp.getClientId(context) + "_loaderImg", null);
        writer.writeAttribute(HTML.CLASS_ATTR, AjaxRendererUtils.STYLECLASS_LOADER, null);
        // could alternatively use a
        // spacer.gif to stretch the span, but didn't want to have another resource dependency.  It would be nice to
        // have a "common" resource repository with spacer.gif and other common things
        writer.write("<spacer type=\"block\" width=\"15\" height=\"15\"/>");
        writer.endElement(HTML.SPAN_ELEM);

    }
}
