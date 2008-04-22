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
package org.apache.myfaces.custom.selectOneRow;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Map;

public class SelectOneRowRenderer extends HtmlRenderer
{

    public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException
    {
        if ((component instanceof SelectOneRow) && component.isRendered())
        {
            SelectOneRow row = (SelectOneRow) component;
            String clientId = row.getClientId(facesContext);

            ResponseWriter writer = facesContext.getResponseWriter();

            writer.startElement(HTML.INPUT_ELEM, row);
            writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_RADIO, null);
            writer.writeAttribute(HTML.NAME_ATTR, row.getGroupName(), null);

            // todo: disabled Attribute
            //writer.writeAttribute(HTML.DISABLED_ATTR, HTML.DISABLED_ATTR, null);

            writer.writeAttribute(HTML.ID_ATTR, clientId, null);

            if (isRowSelected(row))
            {
                writer.writeAttribute(HTML.CHECKED_ATTR, HTML.CHECKED_ATTR, null);
            }

            writer.writeAttribute(HTML.VALUE_ATTR, clientId, null);

            HtmlRendererUtils.renderHTMLAttributes(writer, row, HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);

            HtmlRendererUtils.renderHTMLAttributes(writer, row, new String[]{HTML.ONCLICK_ATTR});

            writer.endElement(HTML.INPUT_ELEM);
        }
    }

    private boolean isRowSelected(UIComponent component)
    {
        UIInput input = (UIInput) component;
        Object value = input.getValue();

        int currentRowIndex = getCurrentRowIndex(component);

        return (value != null)
                && (currentRowIndex == ((Long) value).intValue());

    }

    private int getCurrentRowIndex(UIComponent component)
    {
        UIData uidata = findUIData(component);
        if (uidata == null)
            return -1;
        else
            return uidata.getRowIndex();
    }

    protected UIData findUIData(UIComponent uicomponent)
    {
        if (uicomponent == null)
            return null;
        if (uicomponent instanceof UIData)
            return (UIData) uicomponent;
        else
            return findUIData(uicomponent.getParent());
    }

    public void decode(FacesContext context, UIComponent uiComponent)
    {
        if (! (uiComponent instanceof SelectOneRow))
        {
            return;
        }

        if (!uiComponent.isRendered())
        {
            return;
        }
        SelectOneRow row = (SelectOneRow) uiComponent;

        Map requestMap = context.getExternalContext().getRequestParameterMap();
        String postedValue;

        if (requestMap.containsKey(row.getGroupName()))
        {
            postedValue = (String) requestMap.get(row.getGroupName());
            String clientId = row.getClientId(context);
            if (clientId.equals(postedValue))
            {

                String[] postedValueArray = postedValue.split(":");
                String rowIndex = postedValueArray[postedValueArray.length - 2];

                Long newValue = Long.valueOf(rowIndex);
                //the value to go in conversion&validation
                row.setSubmittedValue(newValue);
                row.setValid(true);
            }
        }
    }
}
