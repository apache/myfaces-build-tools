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
package org.apache.myfaces.wap.renderkit.wml;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.PanelGrid;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;


/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GridRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(GridRenderer.class);

    /** Creates a new instance of TextRenderer */
    public GridRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }

    public boolean getRendersChildren(){
        return(true);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeBegin(" + component.getId() + ")");

        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;
        // renderen in method encodeEnd
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeChildren(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;
        // renderen in method encodeEnd
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeEnd(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        ResponseWriter writer = context.getResponseWriter();
        PanelGrid comp = (PanelGrid)component;
        int columns = getColumnsNumber(comp.getColumns());

        // If no body, header or footer is found, then the table element will be not rendered.
        if (!hasTableAnyContext(context, component)) return;

        writer.startElement(Attributes.TABLE, component);
        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        RendererUtils.writeAttribute(Attributes.CLASS, comp.getStyleClass(), writer);
        RendererUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);

        RendererUtils.writeAttribute(Attributes.ALIGN, comp.getAlign(), writer);
        RendererUtils.writeAttribute(Attributes.COLUMNS, comp.getColumns(), writer);
        RendererUtils.writeAttribute(Attributes.TITLE, comp.getTitle(), writer);

        renderHeaderOrFooter(context, component, Attributes.HEADER, columns);

        renderChildern(context, component, columns);

        renderHeaderOrFooter(context, component, Attributes.FOOTER, columns);

        writer.endElement(Attributes.TABLE);
    }

    public void decode(FacesContext context, UIComponent component) {
        if (component == null ) throw new NullPointerException();
    }

    private void renderChildern(FacesContext context, UIComponent component, int maxColumn) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        int column = 0, row = 0;
        boolean rowStarted = false;

        Iterator childn = component.getChildren().iterator();
        while (childn.hasNext()){
            if (!rowStarted) {
                writer.startElement(Attributes.TR, component);
                rowStarted = true; column = 0;  row = 1;
            }

            // next cell
            writer.startElement(Attributes.TD, component);
            RendererUtils.renderChild(context, (UIComponent)childn.next());
            writer.endElement(Attributes.TD);
            column++;

            // end row
            if (column >= maxColumn) {
                writer.endElement(Attributes.TR);
                rowStarted = false;
            }
        }

        if (rowStarted) writeRowEnd(component, writer, column, maxColumn);
    }

    /**
     * renders header or footer of the table
     * @param context
     * @param component
     * @param str
     * @param maxColumn
     * @throws java.io.IOException
     */
    private void renderHeaderOrFooter(FacesContext context, UIComponent component, String str, int maxColumn) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        UIComponent facet = component.getFacet(str);

        int column = 0;
        boolean rowStarted = false;

        if (facet == null) return;

        log.debug("Facet " + str + " is not null. Child numb:" + facet.getChildCount());
        Iterator childn =  facet.getChildren().iterator();

        // starts header row
        if (childn.hasNext()){
            writer.startElement(Attributes.TR, component);
            rowStarted = true;
        }

        // render all children
        while (childn.hasNext()){
            writer.startElement(Attributes.TD, component);
            // render facet
            RendererUtils.renderChild(context, (UIComponent)childn.next());
            writer.endElement(Attributes.TD);
            column++;
        }

        if (rowStarted) writeRowEnd(component, writer, column, maxColumn);
    }

    /**
     * appends free cells to the number of columns
     * @param component
     * @param writer
     * @param column
     * @param maxColumn
     * @throws java.io.IOException
     */
    private void writeRowEnd(UIComponent component, ResponseWriter writer, int column, int maxColumn) throws IOException {
        while (column < maxColumn) {
            writer.startElement(Attributes.TD, component);
            writer.endElement(Attributes.TD);
            column++;
        }
        writer.endElement(Attributes.TR);
    }

    /** Checks if the table has any header, body or footer. */
    private boolean hasTableAnyContext(FacesContext context, UIComponent component){
        boolean hasHeader = component.getFacet(Attributes.HEADER) != null;
        boolean hasBody = component.getChildCount() > 0;
        boolean hasFooter = component.getFacet(Attributes.FOOTER) != null;

        return (hasHeader || hasBody || hasFooter);
    }

    /** Returns number of columns in table. Converts String parameter to int number, if parameter is not correct int number, return zero.   */
    private int getColumnsNumber(String columns){
        int number;
        try {
            number = Integer.parseInt(columns);
        } catch (NumberFormatException ex){
            return (0);
        }
        return (number);
    }
}

