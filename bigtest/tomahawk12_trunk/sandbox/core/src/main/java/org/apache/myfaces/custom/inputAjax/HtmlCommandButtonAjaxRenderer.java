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

import org.apache.myfaces.renderkit.html.ext.HtmlButtonRenderer;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.custom.ajax.util.AjaxRendererUtils;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import java.io.IOException;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Command"
 *   type = "org.apache.myfaces.CommandButtonAjax"
 *
 * User: Travis Reeder
 * Date: Mar 22, 2006
 * Time: 4:38:13 PM
 */
public class HtmlCommandButtonAjaxRenderer extends HtmlButtonRenderer implements AjaxRenderer
{
    private static final Log log = LogFactory.getLog(HtmlCommandButtonAjaxRenderer.class);
    private static final String JAVASCRIPT_ENCODED = "org.apache.myfaces.custom.inputAjax.HtmlCommandButtonAjax.JAVASCRIPT_ENCODED";


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
        HtmlCommandButtonAjax comp = (HtmlCommandButtonAjax) component;

        AddResource addResource = AddResourceFactory.getInstance(context);

        AjaxRendererUtils.addPrototypeScript(context, component, addResource);

        ResponseWriter out = context.getResponseWriter();
        AjaxRendererUtils.writeAjaxScript(context, out, comp);

        context.getExternalContext().getRequestMap().put(JAVASCRIPT_ENCODED, Boolean.TRUE);
    }




    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        log.debug("encodeEnd in HtmlCommandButtonAjaxRenderer");
        RendererUtils.checkParamValidity(context, component, HtmlCommandButtonAjax.class);

        if (HtmlRendererUtils.isDisplayValueOnly(component) || isDisabled(context, component))
        {
            super.encodeEnd(context, component);
            return;
        }

        this.encodeJavascript(context, component);
        super.encodeEnd(context, component);
        // now write loading image
        AjaxRendererUtils.writeLoadingImage(context, component);
    }


    protected StringBuffer buildOnClick(UIComponent uiComponent, FacesContext facesContext, ResponseWriter writer) throws IOException {
        String clientId = uiComponent.getClientId(facesContext);
        String submitFunctionStart = AjaxRendererUtils.JS_MYFACES_NAMESPACE + "ajaxSubmit3('" + clientId + "');";

        StringBuffer buf = super.buildOnClick(uiComponent, facesContext, writer);

        if(buf.length()!=0 && !(buf.charAt(buf.length()-1)==';'))
        {
            buf.append(";");
        }
        buf.append(submitFunctionStart);

        return buf;
    }

    public void encodeAjax(FacesContext context, UIComponent component) throws IOException
    {
        log.debug("encodeAjax in HtmlCommandButtonAjaxRenderer");
        AjaxRendererUtils.encodeAjax(context, component);
    }

}
