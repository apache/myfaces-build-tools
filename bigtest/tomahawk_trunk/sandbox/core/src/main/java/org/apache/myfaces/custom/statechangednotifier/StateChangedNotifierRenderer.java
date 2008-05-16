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
package org.apache.myfaces.custom.statechangednotifier;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.FacesException;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.dojo.DojoConfig;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.FormInfo;


/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Input"
 *   type = "org.apache.myfaces.StateChangedNotifierRenderer"
 * 
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date: 2006-04-02 22:03:33 +0200 (So, 02 Apr
 *          2006) $
 */
public class StateChangedNotifierRenderer extends HtmlRenderer {

    public void decode(FacesContext facesContext, UIComponent component) {
        RendererUtils.checkParamValidity(facesContext, component, null);

        if (component instanceof UIInput) {
            HtmlRendererUtils.decodeUIInput(facesContext, component);
        } else {
            throw new IllegalArgumentException("Unsupported component class " + component.getClass().getName());
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, StateChangedNotifier.class);

        StateChangedNotifier notifier = (StateChangedNotifier) uiComponent;

        if (notifier.getDisabled() != null) {

            if (notifier.getDisabled().booleanValue())
                return;
        }

        renderInitialization(facesContext, uiComponent, notifier);

        encodeJavascript(facesContext, notifier);
        //We have to render on our own to avoid dependencies into myfaces-impl
        renderInputHidden(facesContext, uiComponent);

    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIOutput.class);

        return RendererUtils.getConvertedUIOutputValue(facesContext, (UIOutput) uiComponent, submittedValue);
    }

    private void encodeJavascript(FacesContext facesContext, StateChangedNotifier notifier) {
        String notifierClientId = notifier.getClientId(facesContext);

        String replacedClientId = notifierClientId.replaceAll(":", "_");
        String initFunctionName = "init_" + replacedClientId;

        FormInfo formInfo   = RendererUtils.findNestingForm(notifier,facesContext);
        if(formInfo == null)
            throw new FacesException("StateChangedNotifier must be embedded in a form.");

        String formId = formInfo.getFormName();

        String notifierVar = replacedClientId + "Notifier";

        StringBuffer sb = new StringBuffer();
        sb.append("dojo.addOnLoad(window, '" + initFunctionName + "');\n");
        sb.append("function " + initFunctionName + "() {\n");
        sb.append(notifierVar + " = new org_apache_myfaces_StateChangedNotifier('" + notifierVar + "','" + formId + "','" + notifierClientId + "','" + notifier.getConfirmationMessage() + "',");

        String excludedCommandIds = notifier.getExcludedIds();

        if (excludedCommandIds != null) {
            sb.append("'" + excludedCommandIds + "');\n");
        } else {
            sb.append("'');\n");
        }

        sb.append("setTimeout('" + replacedClientId + "Notifier.prepareNotifier()',500);\n");

        sb.append("}\n");

        AddResource addResource = AddResourceFactory.getInstance(facesContext);
        addResource.addInlineScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, sb.toString());
    }

    private void renderInitialization(FacesContext facesContext, UIComponent uiComponent, StateChangedNotifier notifier)
        throws IOException {
        String javascriptLocation = (String) notifier.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        DojoUtils.addMainInclude(facesContext, uiComponent, javascriptLocation, new DojoConfig());
        DojoUtils.addRequire(facesContext, uiComponent, "dojo.widget.Dialog");
        DojoUtils.addRequire(facesContext, uiComponent, "dojo.event.*");

        // DojoUtils.addRequire(facesContext, "dojo.xml.Parse");

        writeDialog(facesContext, uiComponent);

        AddResource addResource = AddResourceFactory.getInstance(facesContext);

        if (!DojoUtils.isInlineScriptSet(facesContext, "stateChangedNotifier.js"))
            addResource.addJavaScriptHere(facesContext, StateChangedNotifierRenderer.class, "stateChangedNotifier.js");

        String styleLocation = (String) uiComponent.getAttributes().get(JSFAttr.STYLE_LOCATION);

        // we need a style def for the dialog system
        if (StringUtils.isNotBlank(styleLocation)) {
            addResource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN, styleLocation + "/default.css");
        } else {
            addResource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN, StateChangedNotifierRenderer.class, "css/default.css");
        }
    }

    private void renderInputHidden(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIInput.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.INPUT_ELEM, uiComponent);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);

        String clientId = uiComponent.getClientId(facesContext);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);

        String value = RendererUtils.getStringValue(facesContext, uiComponent);

        if (value != null) {
            writer.writeAttribute(HTML.VALUE_ATTR, value, JSFAttr.VALUE_ATTR);
        }

        writer.endElement(HTML.INPUT_ELEM);
    }

    /**
     * dialog definition this one is needed for the dojoized dialog
     *
     * @param facesContext
     * @param uiComponent
     * @throws IOException
     */
    private void writeDialog(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        /*
         * <div id="form1__idJsp1Notifier_Dialog" style="position:absolute;
         * visibility: hidden;"> <div id="form1__idJsp1Notifier_Dialog_Content">
         * values have changed do you really want to <br> submit the values
         * </div> <input type="button" id="form1__idJsp1Notifier_Dialog_Yes"
         * value="yes" /> <input type="button"
         * id="form1__idJsp1Notifier_Dialog_No" value="no" /> </div>
         */

        String notifierClientId = uiComponent.getClientId(facesContext);
        String replacedClientId = notifierClientId.replaceAll(":", "_");
        String dialogVar        = replacedClientId + "Notifier_Dialog";
        String yesText          = (String) uiComponent.getAttributes().get("yesText");
        String noText           = (String) uiComponent.getAttributes().get("noText");

        yesText = (yesText == null) ? "Yes" : yesText;
        noText  = (noText == null) ? "No" : noText;

        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML.DIV_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, dialogVar, null);
        writer.writeAttribute(HTML.STYLE_ATTR, "position:absolute; visibility: hidden;", null);
        writer.startElement(HTML.DIV_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, dialogVar + "_Content", null);
        writer.endElement(HTML.DIV_ELEM);
        writer.write(HTML.NBSP_ENTITY);
        writer.startElement(HTML.INPUT_ELEM, uiComponent);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.BUTTON_ELEM, null);
        writer.writeAttribute(HTML.ID_ATTR, dialogVar + "_Yes", null);
        writer.writeAttribute(HTML.VALUE_ATTR, yesText, null);
        writer.endElement(HTML.INPUT_ELEM);
        writer.write(HTML.NBSP_ENTITY);
        writer.startElement(HTML.INPUT_ELEM, uiComponent);
        writer.writeAttribute(HTML.TYPE_ATTR, "button", null);
        writer.writeAttribute(HTML.ID_ATTR, dialogVar + "_No", null);
        writer.writeAttribute(HTML.VALUE_ATTR, noText, null);
        writer.endElement(HTML.INPUT_ELEM);
        writer.endElement(HTML.DIV_ELEM);
        writer.write(HTML.NBSP_ENTITY);
    }
}
