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
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.ajax.util.AjaxRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlCheckboxRenderer;

/**
 * For onSuccess, onFailure, onStart functions, they should accept (elname, elvalue) attributes for the specific item
 * <p/>
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.SelectMany"
 *   type = "org.apache.myfaces.CheckboxAjax"
 *
 * User: treeder
 * Date: Oct 20, 2005
 * Time: 3:28:04 PM
 */
public class HtmlSelectManyCheckboxAjaxRenderer extends HtmlCheckboxRenderer implements AjaxRenderer
{
    private static final Log log = LogFactory.getLog(HtmlSelectManyCheckboxAjaxRenderer.class);
    private static final String JAVASCRIPT_ENCODED = "org.apache.myfaces.custom.inputAjax.HtmlSelectManyCheckbox.JAVASCRIPT_ENCODED";


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

        HtmlSelectManyCheckboxAjax selectManyCheckbox = (HtmlSelectManyCheckboxAjax) component;

        AddResource addResource = AddResourceFactory.getInstance(context);

        AjaxRendererUtils.addPrototypeScript(context, component, addResource);

        ResponseWriter out = context.getResponseWriter();

        String extraParams =("&checked=\" + el.checked + \"");
        AjaxRendererUtils.writeAjaxScript(context, out, selectManyCheckbox, extraParams);

        context.getExternalContext().getRequestMap().put(JAVASCRIPT_ENCODED, Boolean.TRUE);
    }


    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(context, component, HtmlSelectManyCheckboxAjax.class);

        if (HtmlRendererUtils.isDisplayValueOnly(component) || isDisabled(context, component))
        {
            super.encodeEnd(context, component);
            return;
        }

        String clientId = component.getClientId(context);

        HtmlSelectManyCheckboxAjax selectManyCheckbox = (HtmlSelectManyCheckboxAjax) component;

        // allow for user defined onclick's as well
        String onClick = selectManyCheckbox.getOnclick();
        if(onClick == null){
            onClick = "";
        }
        // todo: this was doublling up, so took out the + onclick below, need a way for user onclicks to work still
        onClick = AjaxRendererUtils.JS_MYFACES_NAMESPACE + "ajaxSubmit2(this, '" + clientId + "'); "; // + onClick;
        selectManyCheckbox.setOnclick(onClick);

        this.encodeJavascript(context, component);

        super.encodeEnd(context, component);
        AjaxRendererUtils.writeLoadingImage(context, component);

    }

    public void encodeAjax(FacesContext context, UIComponent component) throws IOException
    {
        log.debug("encodeAjax in HtmlSelectManyCheckboxAjaxRenderer");
        // check for request type (portlet support)
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Map extraReturnAttributes = new HashMap();
        extraReturnAttributes.put("checked", request.getParameter("checked"));
        extraReturnAttributes.put("eltype", "checkbox");
        AjaxRendererUtils.encodeAjax(context, component, extraReturnAttributes);


    }



}
