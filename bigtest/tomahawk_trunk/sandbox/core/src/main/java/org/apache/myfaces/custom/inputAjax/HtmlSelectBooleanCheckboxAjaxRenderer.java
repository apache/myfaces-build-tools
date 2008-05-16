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

package org.apache.myfaces.custom.inputAjax;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.ajax.util.AjaxRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlCheckboxRenderer;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.SelectBoolean"
 *   type = "org.apache.myfaces.CheckboxAjax"
 *
 * User: treeder
 * Date: Nov 21, 2005
 * Time: 9:09:20 AM
 */
public class HtmlSelectBooleanCheckboxAjaxRenderer extends HtmlCheckboxRenderer implements AjaxRenderer
{
    private static final Log log = LogFactory.getLog(HtmlSelectBooleanCheckboxAjaxRenderer.class);
    private static final String JAVASCRIPT_ENCODED = "org.apache.myfaces.custom.inputAjax.HtmlSelectBooleanCheckboxAjax.JAVASCRIPT_ENCODED";


    /**
     * Encodes any stand-alone javascript functions that are needed.
     * Uses either the extension filter, or a
     * user-supplied location for the javascript files.
     *
     * @param context   FacesContext
     * @param component UIComponent
     * @throws java.io.IOException
     */
    private void encodeJavascript(FacesContext context, UIComponent component) throws IOException
    {

        HtmlSelectBooleanCheckboxAjax selectBooleanCheckbox = (HtmlSelectBooleanCheckboxAjax) component;

        // Add prototype script to header
        AddResource addResource = AddResourceFactory.getInstance(context);
        AjaxRendererUtils.addPrototypeScript(context, component, addResource);

        // write required javascript
        ResponseWriter out = context.getResponseWriter();
        String extraParams = null; //("&checked=\" + el.checked + \"");
        AjaxRendererUtils.writeAjaxScript(context, out, selectBooleanCheckbox, extraParams);

        if (!context.getExternalContext().getRequestMap().containsKey(JAVASCRIPT_ENCODED))
        {
            // write a special script to swap out images onclick.
            out.startElement(HTML.SCRIPT_ELEM, null);
            out.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);

            StringBuffer buff = new StringBuffer();
            // todo: what if an error occurs? we should swap back to actual value back in server side model in an onComplete function
            buff.append("function ").append(AjaxRendererUtils.JS_MYFACES_NAMESPACE).append("swapImages(imgEl, hiddenElId, onImg, offImg){\n")
                    .append("    var hiddenEl = document.getElementById(hiddenElId);\n")
                    .append("    var currValue = hiddenEl.value;\n")
                    .append("    if(currValue == 'true') {\n")
                    .append("        hiddenEl.value = 'false';\n")
                    .append("        imgEl.src = offImg;\n")
                    .append("    } else {\n")
                    .append("        hiddenEl.value = 'true';\n")
                    .append("        imgEl.src = onImg;\n")
                    .append("    }\n")
                    //.append("    if(imgEl.src == offImg) imgEl.src = onImg;\n")
                    //.append("    if(imgEl.src == onImg) imgEl.src = offImg;\n")
                    .append("}\n");
            out.writeText(buff.toString(), null);

            out.endElement(HTML.SCRIPT_ELEM);
        }

        // set request var to make sure this doesn't get written again
        context.getExternalContext().getRequestMap().put(JAVASCRIPT_ENCODED, Boolean.TRUE);
    }


    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(context, component, HtmlSelectBooleanCheckboxAjax.class);
        if (HtmlRendererUtils.isDisplayValueOnly(component) || isDisabled(context, component))
        {
            super.encodeEnd(context, component);
            return;
        }

        String clientId = component.getClientId(context);
        HtmlSelectBooleanCheckboxAjax selectBooleanCheckbox = (HtmlSelectBooleanCheckboxAjax) component;

        // allow for user defined onclick's as well
        String onClick = selectBooleanCheckbox.getOnclick();
        if (onClick == null)
        {
            onClick = "";
        }
        onClick = AjaxRendererUtils.JS_MYFACES_NAMESPACE + "ajaxSubmit1('" + clientId + "'); " + onClick;
        selectBooleanCheckbox.setOnclick(onClick);

        this.encodeJavascript(context, component);

        String onImgUrl = selectBooleanCheckbox.getOnImage();
        String offImgUrl = selectBooleanCheckbox.getOffImage();
        ResponseWriter out = context.getResponseWriter();
        if (selectBooleanCheckbox.getOnImage() != null)
        {

            Object valOb = selectBooleanCheckbox.getValue();
            if (valOb != null)
            {
                if (valOb instanceof Boolean)
                {
                    Boolean val = (Boolean) valOb;

                    // then render an image instead
                    // and a hidden input to store value
                    out.startElement(HTML.INPUT_ELEM, null);
                    out.writeAttribute(HTML.TYPE_ATTR, "hidden", null);
                    out.writeAttribute(HTML.NAME_ATTR, clientId, null);
                    out.writeAttribute(HTML.ID_ATTR, clientId, null);
                    out.writeAttribute(HTML.VALUE_ATTR, val, null);
                    out.endElement(HTML.INPUT_ELEM);

                    String imgUrl = val.booleanValue() ? onImgUrl : offImgUrl;
                    out.startElement(HTML.IMG_ELEM, null);
                    out.writeAttribute(HTML.SRC_ATTR, imgUrl, null);
                    // add the swap image function to onClick as well
                    onClick = AjaxRendererUtils.JS_MYFACES_NAMESPACE + "swapImages(this, '" + clientId + "', '" + onImgUrl + "', '" + offImgUrl + "'); " + onClick;
                    out.writeAttribute(HTML.ONCLICK_ATTR, onClick, null);
                    out.endElement(HTML.IMG_ELEM);
                }
                else
                {
                    log.error("HtmlSelectBooleanAjax.value is not an instance of Boolean: " + valOb);
                }
            }
        }
        else
        {
            super.encodeEnd(context, component);
        }
        AjaxRendererUtils.writeLoadingImage(context, component);
    }

    public void encodeAjax(FacesContext context, UIComponent component) throws IOException
    {
        log.debug("entering HtmlSelectBooleanCheckboxAjaxRenderer.encodeAjax");
// check for request type (portlet support)
        /*HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        */
        Map extraReturnAttributes = new HashMap();
        //extraReturnAttributes.put("checked", request.getParameter("checked"));
        extraReturnAttributes.put("eltype", "checkbox");
        AjaxRendererUtils.encodeAjax(context, component, null);
    }
}
