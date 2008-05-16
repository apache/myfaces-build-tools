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
package org.apache.myfaces.custom.imageloop;

import org.apache.myfaces.custom.dojo.DojoConfig;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.HtmlImageLoop"
 *
 * HTML image loop renderer. 
 * @author Felix Röthenbacher (latest modification by $Author:$)
 * @version $Revision:$ $Date:$
 */
public class HtmlImageLoopRenderer extends HtmlRenderer {
    
    private static final Integer DEFAULT_DELAY = new Integer(1000);
    private static final Integer DEFAULT_TRANSITION_TIME = new Integer(1000);

    /**
     * Add the javascript files needed by Dojo and the custom javascript for
     * this component
     */
    private void encodeJavascript(FacesContext context, UIComponent uiComponent)
    throws IOException
    {
        String javascriptLocation = (String) uiComponent.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        DojoUtils.addMainInclude(context, uiComponent, javascriptLocation, new DojoConfig());
        DojoUtils.addRequire(context, uiComponent, "dojo.event.*");
        DojoUtils.addRequire(context, uiComponent, "dojo.lfx.*");
        DojoUtils.addRequire(context, uiComponent, "dojo.html.*");

        AddResource addResource = AddResourceFactory.getInstance(context);

        addResource.addJavaScriptAtPosition(context,
            AddResource.HEADER_BEGIN, HtmlImageLoop.class, "javascript/imageloop.js");
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        // Component is rendered in encodeEnd().
    }

    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        // Children are rendered in encodeEnd().
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent,
                HtmlImageLoop.class);
        
        encodeJavascript(facesContext, uiComponent);

        ResponseWriter writer = facesContext.getResponseWriter();
        
        String clientId = uiComponent.getClientId(facesContext);
        String clientIdImage1 = clientId + ":IMG1";
        String clientIdImage2 = clientId + ":IMG2";
        
        HtmlImageLoop imageLoop = (HtmlImageLoop)uiComponent;
        
        Integer delay = imageLoop.getDelay() != null ? imageLoop.getDelay() : DEFAULT_DELAY;
        Integer minDelay = imageLoop.getMinDelay() != null ? imageLoop.getMinDelay() : DEFAULT_DELAY;
        Integer maxDelay = imageLoop.getMaxDelay() != null ? imageLoop.getMaxDelay() : DEFAULT_DELAY;
        Integer transitionTime =
            imageLoop.getTransitionTime() != null ? imageLoop.getTransitionTime() : DEFAULT_TRANSITION_TIME;
        Integer width = imageLoop.getWidth();
        Integer height = imageLoop.getHeight();
        
        writer.startElement(HTML.DIV_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML.STYLE_ATTR,
                "position:relative;" +
                "width:" + width + ";" +
                "height:" + height, null);

        writer.startElement(HTML.IMG_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, clientIdImage1, null);
        writer.writeAttribute(HTML.SRC_ATTR, "", null);
        writer.writeAttribute(HTML.STYLE_ATTR,
                "position:absolute;" +
                "top:0px;" +
                "left:0px;" +
                "border-style:none", null);
        writer.endElement(HTML.IMG_ELEM);
        
        writer.startElement(HTML.IMG_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, clientIdImage2, null);
        writer.writeAttribute(HTML.SRC_ATTR, "", null);
        writer.writeAttribute(HTML.STYLE_ATTR,
                "position:absolute;" +
                "top:0px;" +
                "left:0px;" +
                "border-style:none", null);
        writer.endElement(HTML.IMG_ELEM);
        
        writer.startElement(HTML.SCRIPT_ELEM, uiComponent);
        writer.writeAttribute(HTML.SCRIPT_LANGUAGE_ATTR, HTML.SCRIPT_LANGUAGE_JAVASCRIPT, null);
        writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        String jsImageArray = getJavascriptImageArray(facesContext, uiComponent);
        writer.writeText("new ImageLoop('" + clientId + "', " +
                jsImageArray + ", " + delay + ", " + minDelay + ", " + maxDelay + ", "+ transitionTime + ")", null);
        writer.endElement(HTML.SCRIPT_ELEM);
        
        writer.endElement(HTML.DIV_ELEM);
    }
    
    private String getJavascriptImageArray(FacesContext facesContext, UIComponent uiComponent) {
        String jsArray = new String();

        ImageLoopItemsIterator itemIter = new ImageLoopItemsIterator(uiComponent);

        while(itemIter.hasNext()) {
            GraphicItem graphicItem = (GraphicItem)itemIter.next();
            String url = graphicItem.getUrl();
            String src = facesContext.getApplication().getViewHandler().getResourceURL(facesContext, url);
                
            if (jsArray.length() == 0)
                jsArray += "'" + facesContext.getExternalContext().encodeResourceURL(src) + "'";
            else
                jsArray += ",'" + facesContext.getExternalContext().encodeResourceURL(src) + "'";
        }
        return "[" + jsArray + "]";
    }
    
    public boolean getRendersChildren() {
        return true;
    }
}
