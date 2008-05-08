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
package org.apache.myfaces.custom.datalist;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Data"
 *   type = "org.apache.myfaces.List"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlListRenderer
        extends HtmlRenderer
{
    //private static final Log log = LogFactory.getLog(HtmlListRenderer.class);

    public static final String LAYOUT_SIMPLE = "simple";
    public static final String LAYOUT_UL = "unorderedList";
    public static final String LAYOUT_OL = "orderedList";
    public static final String LAYOUT_GRID = "grid";

    public boolean getRendersChildren()
    {
        return true;
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIData.class);
        ResponseWriter writer = facesContext.getResponseWriter();
        String layout = getLayout(uiComponent);
        if (layout != null)
        {
            if (! layout.equals(LAYOUT_SIMPLE)) {
                HtmlRendererUtils.writePrettyLineSeparator(facesContext);
            }
            if (layout.equals(LAYOUT_UL))
            {
                writer.startElement(HTML.UL_ELEM, uiComponent);

                writer.writeAttribute(HTML.ID_ATTR, uiComponent.getClientId(facesContext), null);

                HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                        HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            }
            else if (layout.equals(LAYOUT_OL))
            {
                writer.startElement(HTML.OL_ELEM, uiComponent);

                writer.writeAttribute(HTML.ID_ATTR, uiComponent.getClientId(facesContext), null);

                HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                        HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            }
            else if (layout.equals(LAYOUT_GRID))
            {
                writer.startElement(HTML.TABLE_ELEM, uiComponent);

                writer.writeAttribute(HTML.ID_ATTR, uiComponent.getClientId(facesContext), null);

                HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
                        HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            }
            else
            {
                if (uiComponent.getId() != null && !uiComponent.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
                {
                    writer.startElement(HTML.SPAN_ELEM, uiComponent);

                    writer.writeAttribute(HTML.ID_ATTR, uiComponent.getClientId(facesContext), null);

                    HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.COMMON_PASSTROUGH_ATTRIBUTES);

                }
                else
                {
                    HtmlRendererUtils.renderHTMLAttributesWithOptionalStartElement(writer, uiComponent,
                            HTML.SPAN_ELEM, HTML.COMMON_PASSTROUGH_ATTRIBUTES);
                }
            }
        }
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, UIData.class);

        UIData uiData = (UIData) component;
        String layout = getLayout(component);
        //Map requestMap = facesContext.getExternalContext().getRequestMap();

        ResponseWriter writer = facesContext.getResponseWriter();

        int first = uiData.getFirst();
        int rows = uiData.getRows();
        int rowCount = uiData.getRowCount();
        if (rows <= 0)
        {
            rows = rowCount - first;
        }
        int last = first + rows;
        if (last > rowCount) last = rowCount;

        /*
        String rowIndexVar = getRowIndexVar(component);
        String rowCountVar = getRowCountVar(component);

        if (rowCountVar != null)
        {
            requestMap.put(rowCountVar, new Integer(rowCount));
        }
        */

        if (layout != null && layout.equals(LAYOUT_GRID)){
            // output table row
            writer.startElement(HTML.TR_ELEM, null);
        }
        for (int i = first; i < last; i++)
        {
            uiData.setRowIndex(i);
            if (uiData.isRowAvailable())
            {
                /*
                if (rowIndexVar != null)
                {
                    requestMap.put(rowIndexVar, new Integer(i));
                }
                */

                if (layout != null)
                {
                    if (! layout.equals(LAYOUT_SIMPLE)) {
                        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
                    }
                    if (layout.equals(LAYOUT_UL) || (layout.equals(LAYOUT_OL)))
                    {
                        writer.startElement(HTML.LI_ELEM, component);
                        HtmlRendererUtils.renderHTMLAttribute(writer, component, "itemStyleClass", HTML.STYLE_CLASS_ATTR);
                    }
                    else if (layout.equals(LAYOUT_GRID))
                    {
                        // output table cells
                        writer.startElement(HTML.TD_ELEM, null);
                    }
                }

                RendererUtils.renderChildren(facesContext, component);

                if (layout != null)
                {
                    if (layout.equals(LAYOUT_UL) || (layout.equals(LAYOUT_OL)))
                    {
                        writer.endElement(HTML.LI_ELEM);
                    }
                    else if (layout.equals(LAYOUT_GRID))
                    {
                        // output table cells
                        writer.endElement(HTML.TD_ELEM);
                    }
                }

                /*
                if (rowIndexVar != null)
                {
                    requestMap.remove(rowIndexVar);
                }
                */
            }
        }
        if (layout != null && layout.equals(LAYOUT_GRID)){
            writer.endElement(HTML.TR_ELEM);
        }
        /*
        if (rowCountVar != null)
        {
            requestMap.remove(rowCountVar);
        }
        */
    }


    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIData.class);
        ResponseWriter writer = facesContext.getResponseWriter();
        String layout = getLayout(uiComponent);
        if (layout != null)
        {
            if (! layout.equals(LAYOUT_SIMPLE)) {
                HtmlRendererUtils.writePrettyLineSeparator(facesContext);
            }
            if (layout.equals(LAYOUT_UL))
            {
                writer.endElement(HTML.UL_ELEM);
            }
            else if (layout.equals(LAYOUT_OL))
            {
                writer.endElement(HTML.OL_ELEM);
            }
            else if (layout.equals(LAYOUT_GRID))
            {
                writer.endElement(HTML.TABLE_ELEM);
            }
            else
            {
                if (uiComponent.getId() != null && !uiComponent.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
                {
                    writer.endElement(HTML.SPAN_ELEM);
                }
                else
                {
                    HtmlRendererUtils.renderOptionalEndElement(writer,
                            uiComponent,
                            HTML.SPAN_ELEM,
                            HTML.COMMON_PASSTROUGH_ATTRIBUTES);
                }
            }
        }
    }


    private String getLayout(UIComponent component)
    {
        if (component instanceof HtmlDataList)
        {
            return ((HtmlDataList) component).getLayout();
        }
        else
        {
            return (String) component.getAttributes().get(JSFAttr.LAYOUT_ATTR);
        }
    }

    /*
    private String getRowIndexVar(UIComponent component)
    {
        if (component instanceof HtmlDataList)
        {
            return ((HtmlDataList)component).getRowIndexVar();
        }
        else
        {
            return (String)component.getAttributes().get("rowIndexVar");
        }
    }

    private String getRowCountVar(UIComponent component)
    {
        if (component instanceof HtmlDataList)
        {
            return ((HtmlDataList)component).getRowCountVar();
        }
        else
        {
            return (String)component.getAttributes().get("rowCountVar");
        }
    }
    */

}
