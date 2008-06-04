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
package org.apache.myfaces.custom.popup;

import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.List;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.Popup"
 * 
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPopupRenderer
    extends HtmlRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.Popup";
    //private static final Log log = LogFactory.getLog(HtmlListRenderer.class);

    public boolean getRendersChildren()
    {
        return true;
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException
    {

    }


    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlPopup.class);

        HtmlPopup popup = (HtmlPopup) uiComponent;

        UIComponent popupFacet = popup.getPopup();

        String popupId = writePopupScript(
                facesContext, popup.getClientId(facesContext),
                popup.getDisplayAtDistanceX(),popup.getDisplayAtDistanceY(), uiComponent);

        //writeMouseOverAndOutAttribs(popupId, popupFacet.getChildren());

        writeMouseOverAttribs(popupId, uiComponent.getChildren(),
            popup.getClosePopupOnExitingElement()==null ||
                    popup.getClosePopupOnExitingElement().booleanValue());

        RendererUtils.renderChildren(facesContext, uiComponent);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.DIV_ELEM, popup);
        writer.writeAttribute(HTML.STYLE_ATTR,(popup.getStyle()!=null?(popup.getStyle()+
                (popup.getStyle().trim().endsWith(";")?"":";")):"")+
                "position:absolute;display:none;",null);
        if(popup.getStyleClass()!=null)
        {
            writer.writeAttribute(HTML.CLASS_ATTR,popup.getStyleClass(),null);
        }
        writer.writeAttribute(HTML.ID_ATTR, popup.getClientId(facesContext),null);
        writer.writeAttribute(HTML.ONMOUSEOVER_ATTR, new String(popupId+".redisplay();"),null);

        Boolean closeExitPopup = popup.getClosePopupOnExitingPopup();

        if(closeExitPopup==null || closeExitPopup.booleanValue())
            writer.writeAttribute(HTML.ONMOUSEOUT_ATTR, popupId + ".hide();",null);

        RendererUtils.renderChild(facesContext, popupFacet);
        writer.endElement(HTML.DIV_ELEM);
    }

    private void writeMouseOverAttribs(String popupId, List children, boolean renderMouseOut)
    {
        for (int i = 0; i < children.size(); i++)
        {
            UIComponent uiComponent = (UIComponent) children.get(i);

            callMethod(uiComponent,"onmouseover", popupId + ".display(event);");

            if(renderMouseOut)
                callMethod(uiComponent,"onmouseout", popupId + ".hide(event);");

            writeMouseOverAttribs(popupId, uiComponent.getChildren(),renderMouseOut);
        }
    }

    private String writePopupScript(FacesContext context, String clientId,
                                    Integer displayAtDistanceX, Integer displayAtDistanceY, UIComponent uiComponent)
        throws IOException
    {
        AddResourceFactory.getInstance(context).addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, HtmlPopupRenderer.class, "JSPopup.js");

        String popupId = JavascriptUtils.getValidJavascriptName(clientId+"Popup",false);

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(HTML.SCRIPT_ELEM, uiComponent);
        writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR,HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT,null);
        writer.writeText("var "+popupId+"=new orgApacheMyfacesPopup('"+clientId+"',"+
                (displayAtDistanceX==null?-5:displayAtDistanceX.intValue())+","+
                (displayAtDistanceY==null?-5:displayAtDistanceY.intValue())+");",null);
        writer.endElement(HTML.SCRIPT_ELEM);

        return popupId;
    }


    private void callMethod(UIComponent uiComponent, String propName, String value)
    {
        Object oldValue = uiComponent.getAttributes().get(propName);

        String oldValueStr = "";

        String genCommentary = "/* generated code */";

        if(oldValue != null)
        {
            oldValueStr = oldValue.toString().trim();

            int genCommentaryIndex;

            //check if generated code has already been added...
            if((genCommentaryIndex=oldValueStr.indexOf(genCommentary))!=-1)
            {
                oldValueStr = oldValueStr.substring(0,genCommentaryIndex);
            }

            if(oldValueStr.length()>0 && !oldValueStr.endsWith(";"))
                oldValueStr +=";";
        }

        value = oldValueStr+genCommentary+value;

        uiComponent.getAttributes().put(propName, value);
    }
}
