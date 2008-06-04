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
package org.apache.myfaces.renderkit.html.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlMessage;
import org.apache.myfaces.component.html.ext.HtmlMessages;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlMessageRendererBase;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Message"
 *   type = "org.apache.myfaces.Message"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlMessageRenderer
        extends HtmlMessageRendererBase
{
    private static final Log log = LogFactory.getLog(HtmlMessageRenderer.class);

    private static final String OUTPUT_LABEL_MAP = HtmlMessageRenderer.class.getName() + ".OUTPUT_LABEL_MAP";

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        super.encodeEnd(facesContext, component);   //check for NP
        renderMessage(facesContext, component);

        if (component instanceof HtmlMessage
                && ((HtmlMessage)component).getForceSpan())
        {
            String forAttr = getFor(component);
            HtmlMessage htmlMessage = (HtmlMessage) component;

            UIComponent forComponent = component.findComponent(forAttr);

            if (forComponent != null)
            {
                String forCompclientId = forComponent.getClientId(facesContext);

                ResponseWriter writer = facesContext.getResponseWriter();
                writer.startElement(HTML.SPAN_ELEM, null);
                writer.writeAttribute(HTML.ID_ATTR, forCompclientId + "_msgFor", null);
                if(htmlMessage.getStyleClass()!=null)
                writer.writeAttribute(HTML.CLASS_ATTR,htmlMessage.getStyleClass(),null);
                if(htmlMessage.getStyle()!=null)
                writer.writeAttribute(HTML.STYLE_ATTR,htmlMessage.getStyle(),null);
                writer.endElement(HTML.SPAN_ELEM);
            }
        }
    }

    protected String getSummary(FacesContext facesContext,
                                UIComponent message,
                                FacesMessage facesMessage,
                                String msgClientId)
    {
        String msgSummary = facesMessage.getSummary();
        if (msgSummary == null) return null;

        String inputLabel = null;
        if (msgClientId != null) inputLabel = findInputLabel(facesContext, msgClientId);
        if (inputLabel == null) inputLabel = "";

        if(((message instanceof HtmlMessages && ((HtmlMessages) message).isReplaceIdWithLabel()) ||
                (message instanceof HtmlMessage && ((HtmlMessage) message).isReplaceIdWithLabel()))&&
                inputLabel.length()!=0)
            msgSummary = msgSummary.replaceAll(findInputId(facesContext, msgClientId),inputLabel);


        String summaryFormat;
        if (message instanceof HtmlMessage)
        {
            summaryFormat = ((HtmlMessage)message).getSummaryFormat();
        }
        else
        {
            summaryFormat = (String)message.getAttributes().get("summaryFormat");
        }

        if (summaryFormat == null) return msgSummary;

        MessageFormat format = new MessageFormat(summaryFormat, facesContext.getViewRoot().getLocale());

        return format.format(new Object[] {msgSummary, inputLabel});
    }

    protected String getDetail(FacesContext facesContext,
                               UIComponent message,
                               FacesMessage facesMessage,
                               String msgClientId)
    {
        String msgDetail = facesMessage.getDetail();
        if (msgDetail == null) return null;

        String inputLabel = null;
        if (msgClientId != null) inputLabel = findInputLabel(facesContext, msgClientId);
        if (inputLabel == null) inputLabel = "";

        if(((message instanceof HtmlMessages && ((HtmlMessages) message).isReplaceIdWithLabel()) ||
                (message instanceof HtmlMessage && ((HtmlMessage) message).isReplaceIdWithLabel()))&&
                inputLabel.length()!=0)
            msgDetail = msgDetail.replaceAll(findInputId(facesContext, msgClientId),inputLabel);

        String detailFormat;
        if (message instanceof HtmlMessage)
        {
            detailFormat = ((HtmlMessage)message).getDetailFormat();
        }
        else
        {
            detailFormat = (String)message.getAttributes().get("detailFormat");
        }

        if (detailFormat == null) return msgDetail;

        MessageFormat format = new MessageFormat(detailFormat, facesContext.getViewRoot().getLocale());
        return format.format(new Object[] {msgDetail, inputLabel});
    }


    public static String findInputLabel(FacesContext facesContext, String inputClientId)
    {
        Map outputLabelMap = getOutputLabelMap(facesContext);
        MessageLabelInfo info = ((MessageLabelInfo)outputLabelMap.get(inputClientId));

        if(info == null)
        {
            UIComponent comp = facesContext.getViewRoot().findComponent(inputClientId);

            UIComponent parent=comp;

            while(parent != null && !((parent=parent.getParent())instanceof UIColumn));

            if(parent != null)
            {
                UIColumn column = (UIColumn) parent;

                if(column.getHeader()!=null)
                {
                    UIComponent header = column.getHeader();

                    return getComponentText(facesContext, header);
                }
            }
        }

        return info==null?null:info.getText();
    }

    public static String findInputId(FacesContext facesContext, String inputClientId)
    {
        Map outputLabelMap = getOutputLabelMap(facesContext);
        MessageLabelInfo info = ((MessageLabelInfo)outputLabelMap.get(inputClientId));

        if(info == null)
        {
            UIComponent comp = facesContext.getViewRoot().findComponent(inputClientId);

            if(comp!=null)
            {
                return comp.getId();
            }
        }

        return info==null?null:(info.getForComponent()==null?null:info.getForComponent().getId());
    }

    /**
     * @param facesContext
     * @return a Map that reversely maps clientIds of components to their
     *         corresponding OutputLabel component
     */
    private static Map getOutputLabelMap(FacesContext facesContext)
    {
        Map map = (Map)facesContext.getExternalContext().getRequestMap().get(OUTPUT_LABEL_MAP);
        if (map == null)
        {
            map = new HashMap();
            createOutputLabelMap(facesContext, facesContext.getViewRoot(), map);
            facesContext.getExternalContext().getRequestMap().put(OUTPUT_LABEL_MAP, map);
        }
        return map;
    }

    private static void createOutputLabelMap(FacesContext facesContext,
                                             UIComponent root,
                                             Map map)
    {
        for (Iterator it = root.getFacetsAndChildren(); it.hasNext(); )
        {
            UIComponent child = (UIComponent)it.next();
            if (child instanceof HtmlOutputLabel)
            {
                String forAttr = ((HtmlOutputLabel)child).getFor();
                if (forAttr != null)
                {
                    UIComponent input = child.findComponent(forAttr);
                    if (input == null)
                    {
                        log.warn("Unable to find component '" + forAttr + "' (calling findComponent on component '" + child.getClientId(facesContext) + "')");
                    }
                    else
                    {
                        map.put(input.getClientId(facesContext),
                                new MessageLabelInfo(
                                        input,getComponentText(facesContext, (HtmlOutputLabel)child)));
                    }
                }
            }
            else
            {
                createOutputLabelMap(facesContext, child, map);
            }
        }
    }

    private static String getComponentText(FacesContext facesContext, UIComponent component)
    {
        String text = null;

        if(component instanceof ValueHolder)
        {
            text= RendererUtils.getStringValue(facesContext, component);
        }

        if (text == null || text.length() < 1)
        {
            StringBuffer buf = new StringBuffer();
            List li = component.getChildren();

            for (int i = 0; i < li.size(); i++)
            {
                UIComponent child = (UIComponent) li.get(i);

                if(child instanceof HtmlOutputText)
                {
                    String str = RendererUtils.getStringValue(facesContext, child);

                    if(str!=null)
                        buf.append(str);
                }
            }

            text = buf.toString();
        }
        return text;
    }

    public static class MessageLabelInfo
    {
        private UIComponent _forComponent;
        private String _text;

        public MessageLabelInfo(UIComponent forComponent, String text)
        {
            _forComponent = forComponent;
            _text = text;
        }

        public UIComponent getForComponent()
        {
            return _forComponent;
        }

        public void setForComponent(UIComponent forComponent)
        {
            _forComponent = forComponent;
        }

        public String getText()
        {
            return _text;
        }

        public void setText(String text)
        {
            _text = text;
        }
    }
}
