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
package org.apache.myfaces.custom.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Data"
 *   type = "org.apache.myfaces.FilterTable"
 * 
 * @author Thomas Spiegl
 */
public class FilterTableRenderer extends HtmlRenderer {

    public static final String RENDERER_TYPE = "org.apache.myfaces.FilterTable";

    private Log log = LogFactory.getLog(FilterTableRenderer.class);

    public boolean getRendersChildren() {
        return true;
    }

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.checkParamValidity(context, component, FilterTable.class);
        ResponseWriter writer = context.getResponseWriter();
        
        FilterTable table = (FilterTable) component;

        String javascriptLocation = (String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        DojoUtils.addMainInclude(context, component, javascriptLocation, DojoUtils.getDjConfigInstance(context));
        DojoUtils.addRequire(context, component, "dojo.widget.FilteringTable");

        writer.write("<table dojoType=\"filteringTable\" id=\"" + component.getClientId(context) +  "\" ");
        if (table.getStyleClass() != null) {
             writer.write("class=\"" + table.getStyleClass() + "\" " );
        }
        if (table.getHeadClass() != null) {
            writer.write("headClass=\"" + table.getHeadClass() + "\" " );
        } else {
            writer.write("headClass=\"fixedHeader\" " );
        }
        if (table.getTbodyClass() != null) {
            writer.write("tbodyClass=\"" + table.getTbodyClass() + "\" ");
        } else {
            writer.write("tbodyClass=\"scrollContent\" ");
        }
        if (table.getMultiple() != null) {
            String value = table.getMultiple().booleanValue() ? "true" : "false";
            writer.write("multiple=\"" + value + "\" ");
        } else {
            writer.write("multiple=\"true\" ");
        }
        if (table.getAlternateRows() != null) {
            String value = table.getAlternateRows().booleanValue() ? "true" : "false";
            writer.write("alternateRows=\""+ value + "\" ");
        } else {
            writer.write("alternateRows=\"true\" ");
        }
        if (table.getMaxSortable() != null) {
            writer.write("maxSortable=\"" + table.getMaxSortable() + "\" ");
        } else {
            writer.write("maxSortable=\"1\" ");
        }
        if (table.getCellpadding() != null) {
            writer.write("cellpadding=\"" + table.getCellpadding() + "\" ");
        } else {
            writer.write("cellpadding=\"0\" ");
        }
        if (table.getCellspacing() != null) {
            writer.write("cellspacing=\"" + table.getCellspacing() + "\" ");
        } else {
            writer.write("cellspacing=\"0\" ");
        }
        if (table.getBorder() != null) {
            writer.write("border=\"" + table.getBorder() + "\"");
        } else {
            writer.write("border=\"0\"");
        }
        writer.write(">");
        encodeHeader(context, component);
    }

    public void encodeHeader(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(HTML.THEAD_ELEM, component);
        writer.startElement(HTML.TR_ELEM, component);
        for (Iterator it = component.getChildren().iterator(); it.hasNext(); ) {
            UIComponent child = (UIComponent) it.next();
            if (child.isRendered() && child instanceof SortableColumn) {
                SortableColumn col = (SortableColumn) child;
                writer.startElement(HTML.TH_ELEM, col);
                if (col.getField() != null) {
                    writer.writeAttribute("field", col.getField(), null);
                }
                if (col.getDataType() != null) {
                    writer.writeAttribute("dataType", col.getDataType(), null);
                } else {
                    writer.writeAttribute("dataType", "String", null);
                }
                if (col.getSort() != null) {
                     writer.writeAttribute("sort", col.getSort(), null);
                 }
                 if (col.getAlign() != null) {
                     writer.writeAttribute("align", col.getAlign(), null);
                 }
                 if (col.getValign() != null) {
                     writer.writeAttribute("valign", col.getValign(), null);
                 }
                 if (col.getFormat() != null) {
                     writer.writeAttribute("format", col.getFormat(), null);
                 }
                Boolean escape = col.getEscape();
                if (escape == null || escape.booleanValue())  {
                    writer.writeText(col.getText(), JSFAttr.VALUE_ATTR);
                } else {
                    writer.writeText(col.getText(), null);
                }
                writer.endElement(HTML.TH_ELEM);
            }
        }
        writer.endElement(HTML.TR_ELEM);
        writer.endElement(HTML.THEAD_ELEM);
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException
    {
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.TBODY_ELEM, component);
        encodeInnerHtml(facesContext, component);
        writer.endElement(HTML.TBODY_ELEM);
    }

    protected void encodeInnerHtml(FacesContext facesContext, UIComponent component)throws IOException{
        UIData uiData = (UIData) component;
        ResponseWriter writer = facesContext.getResponseWriter();

        int first = uiData.getFirst();
        int rows = uiData.getRows();
        int last;
        if (rows <= 0) {
            last = uiData.getRowCount();
        }
        else {
            last = first + rows;
            if (last > uiData.getRowCount()) {
                last=uiData.getRowCount();
            }
        }
        for(int idx = first; idx < last; idx++) {
            // bail if any row does not exist
            uiData.setRowIndex(idx);
            if(!uiData.isRowAvailable()) {
                log.error("Row is not available. Rowindex = " + idx);
                break;
            }

            writer.startElement(HTML.TR_ELEM, component);
            writer.writeAttribute("value", Integer.toString(idx + 1), null);
            List children = getChildren(component);
            for (int j = 0, size = getChildCount(component); j < size; j++) {
                UIComponent child = (UIComponent) children.get(j);
                if (child.isRendered()) {
                    boolean columnRendering = child instanceof UIColumn;
                    if (columnRendering)
                        encodeColumnChild(facesContext, writer, uiData, child);
                }
            }
            writer.endElement(HTML.TR_ELEM);
        }
    }

    protected void encodeColumnChild(FacesContext facesContext,  ResponseWriter writer, UIData uiData,
                                     UIComponent component) throws IOException {
        writer.startElement(HTML.TD_ELEM, uiData);
        RendererUtils.renderChild(facesContext, component);
        writer.endElement(HTML.TD_ELEM);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.checkParamValidity(context, component, UIData.class);
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(HTML.TABLE_ELEM);
        HtmlRendererUtils.writePrettyLineSeparator(context);
    }
}
