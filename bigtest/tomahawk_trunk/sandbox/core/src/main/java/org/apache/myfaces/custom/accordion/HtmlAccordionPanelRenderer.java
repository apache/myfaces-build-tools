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
package org.apache.myfaces.custom.accordion;

import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.custom.tabbedpane.HtmlPanelTab;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlGroupRenderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author Martin Marinschek
 *
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.AccordionPanel"
 *
 * @version $Revision: $ $Date: $
 *          <p/>
 */
public class HtmlAccordionPanelRenderer extends HtmlGroupRenderer
{
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
        encodeJavascript(context, component);

        super.encodeBegin(context, component);
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException
    {
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(context, component, HtmlAccordionPanel.class);

        HtmlAccordionPanel panel = (HtmlAccordionPanel) component;

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTML.DIV_ELEM, component);
        writer.writeAttribute(HTML.ID_ATTR,component.getClientId(context), JSFAttr.ID_ATTR);
        HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES);

        List childExpanded = panel.getChildExpanded();

        if (component.getChildCount() > 0)
        {
            int i = 0;
            for (Iterator it = component.getChildren().iterator(); it.hasNext(); i++)
            {
                UIComponent child = (UIComponent)it.next();

                if(child instanceof HtmlPanelTab)
                {
                    HtmlPanelTab pane = (HtmlPanelTab) child;
                    writer.startElement(HTML.DIV_ELEM, child);
                    writer.writeAttribute(HTML.ID_ATTR,
                                          child.getClientId(context) + "_MAIN_DIV",
                                          JSFAttr.ID_ATTR);

                    writer.startElement(HTML.DIV_ELEM, child);
                    writer.writeAttribute(HTML.ID_ATTR,
                                          child.getClientId(context) + "_HEADER_DIV",
                                          JSFAttr.ID_ATTR);
                    if(pane.getLabel() != null)
                    {
                        writer.writeText(pane.getLabel(), JSFAttr.LABEL_ATTR);
                    }
                    else
                    {
                        UIComponent header = pane.getFacet("header");

                        if(header == null)
                        {
                            throw new IllegalStateException("You need to set a label on the tab or include a facet with name 'header' into it.");
                        }

                        RendererUtils.renderChild(context, header);
                    }
                    writer.endElement(HTML.DIV_ELEM);

                    UIComponent closedContent = pane.getFacet("closedContent");

                    if(closedContent != null)
                    {
                        writer.startElement(HTML.DIV_ELEM, child);
                        writer.writeAttribute(HTML.ID_ATTR,
                                              child.getClientId(context) + "_CLOSED_CONTENT_DIV",
                                              JSFAttr.ID_ATTR);
                        RendererUtils.renderChild(context, closedContent);
                        writer.endElement(HTML.DIV_ELEM);
                    }

                    writer.startElement(HTML.DIV_ELEM, child);
                    writer.writeAttribute(HTML.ID_ATTR,
                                          child.getClientId(context) + "_CONTENT_DIV",
                                          JSFAttr.ID_ATTR);
                    RendererUtils.renderChildren(context, child);
                    writer.endElement(HTML.DIV_ELEM);

                    //stateholder hidden input
                    String stateID = child.getClientId(context) + HtmlAccordionPanel.EXPAND_STATEHOLDER_ID;
                    writer.startElement(HTML.INPUT_ELEM, child);
                    writer.writeAttribute(HTML.ID_ATTR,
                                          stateID,
                                          JSFAttr.ID_ATTR);
                    //must be setted, else not included in requestmap
                    writer.writeAttribute(HTML.NAME_ATTR,
                                          stateID,
                                          null);
                    writer.writeAttribute(HTML.TYPE_ATTR,
                                          HTML.INPUT_TYPE_HIDDEN,
                                          JSFAttr.TYPE_ATTR);
                    Object o = childExpanded.get(i);
                    if(o instanceof Integer)
                    {
                        writer.writeAttribute(HTML.VALUE_ATTR,
                                              o,
                                              JSFAttr.VALUE_ATTR);
                    }
                    writer.endElement(HTML.INPUT_ELEM);

                    writer.endElement(HTML.DIV_ELEM);

                }
                else
                {
                    throw new IllegalStateException("no other children accepted");
                }
            }
        }

        writer.endElement(HTML.DIV_ELEM);

        writer.startElement(HTML.SCRIPT_ELEM, component);
        writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR,
                              HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT,
                              null);

        String jsObjectName = "options";
        encodeOptions(writer, panel, jsObjectName);

        String id = component.getClientId(context);

        if(panel.getLayout().equals(HtmlAccordionPanel.ACCORDION_LAYOUT))
        {
            writer.writeText("new Rico.Accordion.Custom('"+ id +"', " + jsObjectName + ");",
                             null);
        }
        else if(panel.getLayout().equals(HtmlAccordionPanel.TOGGLING_LAYOUT))
        {
            writer.writeText("new Rico.Toggler.Custom('" + id + "', " + jsObjectName + ");",
                             null);
        }
        else
        {
            throw new IllegalStateException("nothing known about layout : " +
                                            panel.getLayout());
        }

        writer.endElement(HTML.SCRIPT_ELEM);
    }


    public void decode(FacesContext context, UIComponent component)
    {
        super.decode(context, component);

        RendererUtils.checkParamValidity(context, component, HtmlAccordionPanel.class);

        HtmlAccordionPanel panel = (HtmlAccordionPanel) component;

        Map requestParams = context.getExternalContext().getRequestParameterMap();

        if(panel.getChildCount() > 0)
        {
            int i = 0;
            List list = panel.getChildExpanded();

            for (Iterator it = component.getChildren().iterator(); it.hasNext(); i++)
            {
                UIComponent child = (UIComponent)it.next();

                if(child instanceof HtmlPanelTab)
                {
                    String stateID = child.getClientId(context) + HtmlAccordionPanel.EXPAND_STATEHOLDER_ID;
                    String stateValue = (String)requestParams.get(stateID);

                    try
                    {
                        Integer cur = Integer.valueOf(stateValue);
                        list.set(i, cur);
                    }
                    catch(NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            panel.setChildExpanded(list);
        }
    }


    /**
     * Encodes any stand-alone javascript functions that are needed.
     * Uses either the extension filter, or a
     * user-supplied location for the javascript files.
     *
     * @param context FacesContext
     * @param component UIComponent
     */
    private void encodeJavascript(FacesContext context, UIComponent component)
    {
        // AddResource takes care to add only one reference to the same script
        String javascriptLocation = (String)component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        AddResource addResource = AddResourceFactory.getInstance(context);
        if(javascriptLocation != null)
        {
            // add user defined javascripts
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/dpdump.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/prototype.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/rico.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/toggler.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/customRico.js");
        }
        else
        {
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, HtmlAccordionPanelRenderer.class, "dpdump.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, HtmlAccordionPanelRenderer.class, "prototype.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, HtmlAccordionPanelRenderer.class, "rico.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, HtmlAccordionPanelRenderer.class, "toggler.js");
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, HtmlAccordionPanelRenderer.class, "customRico.js");
        }
    }


    /**
     * Writes the style options for the given {@link HtmlAccordionPanel}.
     *
     * @param writer the {@link ResponseWriter} of the current {@link FacesContext}.
     * @param panel the {@link HtmlAccordionPanel} to encode.
     *
     * @throws IOException
     */

    private void encodeOptions(ResponseWriter writer,
                               HtmlAccordionPanel panel,
                               String jsObjectName)
                                throws IOException
    {
        writer.writeText("var " + jsObjectName + " = new Object();", null);

        if(panel.getExpandedBackColor() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.EXPANDED_BACK_COLOR,
                                               panel.getExpandedBackColor());

            writer.writeText(statement, null);
        }
        if(panel.getExpandedTextColor() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.EXPANDED_TEXT_COLOR,
                                               panel.getExpandedTextColor());

            writer.writeText(statement, null);
        }
        if(panel.getExpandedFontWeight() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.EXPANDED_FONT_WEIGHT,
                                               panel.getExpandedFontWeight());

            writer.writeText(statement, null);
        }

        if(panel.getCollapsedBackColor() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.COLLAPSED_BACK_COLOR,
                                               panel.getCollapsedBackColor());

            writer.writeText(statement, null);
        }
        if(panel.getCollapsedTextColor() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.COLLAPSED_TEXT_COLOR,
                                               panel.getCollapsedTextColor());

            writer.writeText(statement, null);
        }
        if(panel.getCollapsedFontWeight() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.COLLAPSED_FONT_WEIGHT,
                                               panel.getCollapsedFontWeight());

            writer.writeText(statement, null);
        }

        if(panel.getHoverBackColor() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.HOVER_BACK_COLOR,
                                               panel.getHoverBackColor());

            writer.writeText(statement, null);
        }
        if(panel.getHoverTextColor() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.HOVER_TEXT_COLOR,
                                               panel.getHoverTextColor());

            writer.writeText(statement, null);
        }

        if(panel.getBorderColor() != null)
        {
            String statement;
            statement = getJSPropertySetString(jsObjectName,
                                               HtmlAccordionPanel.BORDER_COLOR,
                                               panel.getBorderColor());

            writer.writeText(statement, null);
        }
    }


    /**
     * Builds a set statement for a property of an Javascript Object.
     *
     * @param object the name of the Javascript object.
     * @param property the name of the object's property.
     * @param value the new value of the property.
     *
     * @return a set statement for a Javascript object.
     */

    private String getJSPropertySetString(String object,
                                          String property,
                                          String value)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(object);
        sb.append(".");
        sb.append(property);
        sb.append("='");
        sb.append(value);
        sb.append("';");
        return sb.toString();
    }
}
