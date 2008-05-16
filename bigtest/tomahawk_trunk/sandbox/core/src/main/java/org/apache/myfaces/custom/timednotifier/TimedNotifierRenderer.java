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
package org.apache.myfaces.custom.timednotifier;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

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

/**
 * 
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.TimedNotifierRenderer"
 * 
 * @author werpu
 * The html renderer for the timed notifier
 *
 */
public class TimedNotifierRenderer extends HtmlRenderer {

    public void decode(FacesContext facesContext, UIComponent component) {
        RendererUtils.checkParamValidity(facesContext, component, null);

        if (component instanceof UIInput) {
            HtmlRendererUtils.decodeUIInput(facesContext, component);
        } else {
            throw new IllegalArgumentException("Unsupported component class " + component.getClass().getName());
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, TimedNotifier.class);

        TimedNotifier notifier = (TimedNotifier) uiComponent;

        if (notifier.getDisabled() != null) {

            if (notifier.getDisabled().booleanValue())
                return;
        }

        renderInitialization(facesContext, uiComponent, notifier);

        encodeJavascript(facesContext, notifier);

   
    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIOutput.class);

        return RendererUtils.getConvertedUIOutputValue(facesContext, (UIOutput) uiComponent, submittedValue);
    }

    private void encodeJavascript(FacesContext facesContext, TimedNotifier notifier) throws IOException {
        String notifierClientId = notifier.getClientId(facesContext);

        String replacedClientId = notifierClientId.replaceAll(":", "_");
        String dialogVar        = replacedClientId + "Notifier_Dialog";
        String confirmVar 		= dialogVar + "_Yes";
        
        Integer timeShow			= notifier.getShowDelay();
        Integer timeHide			= notifier.getHideDelay();
        
        UIComponent confirmComp = notifier.getConfirm();
       
        	
       
       

        String notifierVar = replacedClientId + "Notifier";

        StringBuffer sb = new StringBuffer();
        sb.append("function "+replacedClientId+"() {\n");
        if(confirmComp == null)
        	sb.append("var "+ notifierVar + " = new myfaces_TimedNotifier('" + 
        		dialogVar + "','" + confirmVar + "','" + notifierClientId + "',"+
        		timeShow.toString()+","+timeHide.toString()+");\n");
        else
          	sb.append("var "+ notifierVar + " = new myfaces_TimedNotifier('" + 
            		dialogVar + "',null,'" + notifierClientId + "',"+
            		timeShow.toString()+","+timeHide.toString()+");\n");
            	
        sb.append( notifierVar + ".showDialog();\n");
        sb.append("};\n");
        sb.append("dojo.lang.setTimeout("+replacedClientId+","+timeShow.toString()+");");

        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML.SCRIPT_ELEM, notifier);
        writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        writer.write(sb.toString());
        writer.endElement(HTML.SCRIPT_ELEM);

    }

   

    private void renderInitialization(FacesContext facesContext, UIComponent uiComponent, TimedNotifier notifier) throws IOException {
        String javascriptLocation = (String) notifier.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        DojoUtils.addMainInclude(facesContext, uiComponent, javascriptLocation, new DojoConfig());
        DojoUtils.addRequire(facesContext, uiComponent, "dojo.widget.Dialog");
        DojoUtils.addRequire(facesContext, uiComponent, "dojo.event.*");

        // DojoUtils.addRequire(facesContext, "dojo.xml.Parse");

        writeDialog(facesContext, notifier);

        AddResource addResource = AddResourceFactory.getInstance(facesContext);

        if (!DojoUtils.isInlineScriptSet(facesContext, "stateChangedNotifier.js"))
            addResource.addJavaScriptHere(facesContext, TimedNotifierRenderer.class, "timednotifier.js");

        String styleLocation = (String) uiComponent.getAttributes().get(JSFAttr.STYLE_LOCATION);

        // we need a style def for the dialog system
        if (StringUtils.isNotBlank(styleLocation)) {
            addResource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN, styleLocation + "/default.css");
        } else {
            addResource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN, TimedNotifierRenderer.class, "css/default.css");
        }
    }

   

    /**
     * dialog definition this one is needed for the dojoized dialog
     *
     * @param facesContext
     * @param uiComponent
     * @throws IOException
     */
    private void writeDialog(FacesContext facesContext, TimedNotifier uiComponent) throws IOException {
        /*
         * <div id="form1__idJsp1Notifier_Dialog" style="position:absolute;
         * visibility: hidden;"> <div id="form1__idJsp1Notifier_Dialog_Content">
         * values have changed do you really want to <br> submit the values
         * </div> <input type="button" id="form1__idJsp1Notifier_Dialog_Yes"
         * value="yes" /> <input type="button"
         * id="form1__idJsp1Notifier_Dialog_No" value="no" /> </div>
         */
    	TimedNotifier notifier = (TimedNotifier) uiComponent;
    	
        String notifierClientId = uiComponent.getClientId(facesContext);
        String replacedClientId = notifierClientId.replaceAll(":", "_");
        String dialogVar        = replacedClientId + "Notifier_Dialog";
        String yesText          = (String) uiComponent.getAttributes().get("yesText");
   
        yesText = (yesText == null) ? "Yes" : yesText;
   
        String content = uiComponent.getConfirmationMessage();
        
        String styleClass   = uiComponent.getStyleClass();
        
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML.DIV_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, dialogVar, null);
        writer.writeAttribute(HTML.STYLE_ATTR, "position:absolute; visibility: hidden;", null);
        writer.writeAttribute(HTML.CLASS_ATTR, styleClass,null);
        writer.startElement(HTML.DIV_ELEM, uiComponent);
        writer.writeAttribute(HTML.CLASS_ATTR, styleClass,null);
           
        writer.startElement(HTML.DIV_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, dialogVar + "_Content", null);
        
        UIComponent contentComp = notifier.getContent();
        if(contentComp == null)
        	writer.write(content);
        else
        	RendererUtils.renderChild(facesContext, contentComp);
        writer.endElement(HTML.DIV_ELEM);
        writer.write(HTML.NBSP_ENTITY);
        UIComponent confirmComp = notifier.getConfirm();
        if(confirmComp != null)
        	RendererUtils.renderChild(facesContext, confirmComp);
        else {
	        writer.startElement(HTML.INPUT_ELEM, uiComponent);
	        writer.writeAttribute(HTML.TYPE_ATTR, HTML.BUTTON_ELEM, null);
	        writer.writeAttribute(HTML.ID_ATTR, dialogVar + "_Yes", null);
	        writer.writeAttribute(HTML.VALUE_ATTR, yesText, null);
	        writer.endElement(HTML.INPUT_ELEM);
        }
        writer.write(HTML.NBSP_ENTITY);
        writer.endElement(HTML.DIV_ELEM);
        writer.endElement(HTML.DIV_ELEM);
    }
}
