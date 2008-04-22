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

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.DataTable;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;


/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TableRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(TableRenderer.class);

    /** Creates a new instance of TableRenderer */
    public TableRenderer() {
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
        // rendered in method encodeEnd
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeChildren(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        // rendered in method encodeEnd
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeEnd(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        ResponseWriter writer = context.getResponseWriter();
        DataTable comp = (DataTable)component;

        // number of columns equals count of childern (UIColumn elements)
        int columns = component.getChildCount();

        // If no columns was found, the table element will not be rendered.
        if (columns == 0) return;

        writer.startElement(Attributes.TABLE, component);
        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        RendererUtils.writeAttribute(Attributes.CLASS, comp.getStyleClass(), writer);
        RendererUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);

        RendererUtils.writeAttribute(Attributes.ALIGN, comp.getAlign(), writer);

        RendererUtils.writeAttribute(Attributes.COLUMNS, new Integer(columns), writer);
        RendererUtils.writeAttribute(Attributes.TITLE, comp.getTitle(), writer);

        if (hasHeaderOrFooter(context, component, true))
            renderHeaderOrFooter(context, component, true);

        renderChildren(context, component);

        if (hasHeaderOrFooter(context, component, false))
            renderHeaderOrFooter(context, component, false);

        writer.endElement(Attributes.TABLE);
    }

    public void decode(FacesContext context, UIComponent component) {
        if (component == null ) throw new NullPointerException();
    }

    private void renderChildren(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();


        log.debug("Childern: " + component.getChildCount() + " of Component " + component.getFamily());

        UIData comp = (UIData)component;

        int first = comp.getFirst();
        int rows = comp.getRows();
        int rowCount = comp.getRowCount();
        if (rows <= 0) {
            rows = rowCount - first;
        }
        int last = first + rows;
        if (last > rowCount) last = rowCount;

        for (int i = first; i < last; i++){
            writer.startElement(Attributes.TR, component);
            comp.setRowIndex(i);

            if (!comp.isRowAvailable()){
                log.error("Row: " + i + " is not available.");
                continue;
            }

            Iterator columns = component.getChildren().iterator();
            while (columns.hasNext()){
                UIComponent column = (UIComponent)columns.next();
                if (!(column instanceof UIColumn)) {
                    log.error("Only column elements can be nested in dataTable.");
                    break;
                }

                writer.startElement(Attributes.TD, component);
                RendererUtils.renderChild(context, column);
                writer.endElement(Attributes.TD);
            }
            writer.endElement(Attributes.TR);
        }
    }

    /** renders header or footer of the table */
    private void renderHeaderOrFooter(FacesContext context, UIComponent component, boolean header) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(Attributes.TR, component);

        Iterator columns = component.getChildren().iterator();
        while (columns.hasNext()){
            UIComponent column = (UIComponent)columns.next();
            if (!(column instanceof UIColumn)) {
                log.error("Only column elements can be nested in dataTable.");
                break;
            }

            UIComponent facet = header ? ((UIColumn)column).getHeader() : ((UIColumn)column).getFooter();

            writer.startElement(Attributes.TD, component);
            RendererUtils.renderChild(context, facet);
            writer.endElement(Attributes.TD);
        }
        writer.endElement(Attributes.TR);
    }

    /** Checks if the table has any header or footer. */
    private boolean hasHeaderOrFooter(FacesContext context, UIComponent component, boolean header){
        Iterator childn = component.getChildren().iterator();
        while (childn.hasNext()){
            UIComponent child = (UIComponent)childn.next();
            if (child instanceof UIColumn && child.isRendered()){
                if (header) {
                    if (((UIColumn)child).getHeader() != null) return(true);
                }
                else{
                    if (((UIColumn)child).getFooter() != null) return(true);
                }
            }
        }
        return (false);
    }
}

