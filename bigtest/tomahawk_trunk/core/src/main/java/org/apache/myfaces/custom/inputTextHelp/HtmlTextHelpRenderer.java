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
package org.apache.myfaces.custom.inputTextHelp;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlTextRenderer;

/**
 * @author Thomas Obereder
 * @version Date: 09.06.2005, 22:50:48
 */
public class HtmlTextHelpRenderer extends HtmlTextRenderer
{
    private static final String JAVASCRIPT_ENCODED = "org.apache.myfaces.inputTextHelp.JAVASCRIPT_ENCODED";

    protected void renderNormal(FacesContext facesContext, UIComponent component) throws IOException
    {
        if(component instanceof HtmlInputTextHelp)
        {
            HtmlInputTextHelp helpTextComp = (HtmlInputTextHelp) component;
            addJavaScriptResources(facesContext);
            renderInputTextHelp(facesContext, helpTextComp);
        }
        else
        {
            super.renderNormal(facesContext, component);
        }
    }

    public static boolean isSelectText(UIComponent component)
    {
        if(component instanceof HtmlInputTextHelp)
        {
            HtmlInputTextHelp helpTextComp = (HtmlInputTextHelp) component;
            return helpTextComp.isSelectText();
        }
        return false;
    }

    public static String getHelpText(UIComponent component)
    {
        if(component instanceof HtmlInputTextHelp)
        {
            HtmlInputTextHelp helpTextComp = (HtmlInputTextHelp) component;
            if(helpTextComp.getHelpText() != null)
                return helpTextComp.getHelpText();
        }
        return null;
    }

    public void renderInputTextHelp(FacesContext facesContext, UIInput input)
            throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.INPUT_ELEM, input);

        writer.writeAttribute(HTML.ID_ATTR, input.getClientId(facesContext), null);
        writer.writeAttribute(HTML.NAME_ATTR, input.getClientId(facesContext), null);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_TEXT, null);

        renderHelpTextAttributes(input, writer, facesContext);

        String value = RendererUtils.getStringValue(facesContext, input);
        value = (value==null || value.length()==0) ? getHelpText(input) : value;

        if (value != null)
        {
            writer.writeAttribute(HTML.VALUE_ATTR, value, JSFAttr.VALUE_ATTR);
        }

        writer.endElement(HTML.INPUT_ELEM);
    }

    private void renderHelpTextAttributes(UIComponent component,
                                          ResponseWriter writer,
                                          FacesContext facesContext)
            throws IOException
    {
        if(!(component instanceof HtmlInputTextHelp))
        {
            HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        }
        else
        {
            String id = component.getClientId(facesContext);
            if(isSelectText(component))
            {
                HtmlRendererUtils.renderHTMLAttributes(writer, component,
                                                       HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_ONFOCUS_AND_ONCLICK);
                writer.writeAttribute(HTML.ONFOCUS_ATTR,
                                      HtmlInputTextHelp.JS_FUNCTION_SELECT_TEXT + "('" +
                                      getHelpText(component) + "', '" + id +"')", null);
                writer.writeAttribute(HTML.ONCLICK_ATTR,
                                      HtmlInputTextHelp.JS_FUNCTION_SELECT_TEXT + "('" +
                                      getHelpText(component) + "', '" + id +"')", null);

            }
            else
            {
                if(getHelpText(component) != null)
                {
                    HtmlRendererUtils.renderHTMLAttributes(writer, component,
                                                           HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_ONFOCUS_AND_ONCLICK);
                    writer.writeAttribute(HTML.ONFOCUS_ATTR,
                                          HtmlInputTextHelp.JS_FUNCTION_RESET_HELP + "('" +
                                          getHelpText(component) + "', '" + id +"')", null);
                writer.writeAttribute(HTML.ONCLICK_ATTR,
                                      HtmlInputTextHelp.JS_FUNCTION_RESET_HELP + "('" +
                                      getHelpText(component) + "', '" + id +"')", null);
                }
                else
                {
                    HtmlRendererUtils.renderHTMLAttributes(writer,
                                                           component, HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
                }
            }
        }

         if (isDisabled(facesContext, component))
        {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }
    }

    public void decode(FacesContext facesContext, UIComponent component)
    {
        super.decode(facesContext, component);
    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent component, Object submittedValue) throws ConverterException
    {
        if(submittedValue!=null && component instanceof HtmlInputTextHelp &&
           ((HtmlInputTextHelp) component).getHelpText()!=null &&
           submittedValue.equals(((HtmlInputTextHelp) component).getHelpText()))
        {
            submittedValue = "";
        }

        return super.getConvertedValue(facesContext, component, submittedValue);
    }

    public static void addJavaScriptResources(FacesContext facesContext)
    {
        // check to see if javascript has already been written (which could happen if more than one calendar on the same page)
        if (facesContext.getExternalContext().getRequestMap().containsKey(JAVASCRIPT_ENCODED))
        {
            return;
        }

        AddResourceFactory.getInstance(facesContext).addJavaScriptAtPosition(
                facesContext, AddResource.HEADER_BEGIN, HtmlTextHelpRenderer.class, "inputTextHelp.js");

        facesContext.getExternalContext().getRequestMap().put(JAVASCRIPT_ENCODED, Boolean.TRUE);
    }
}
