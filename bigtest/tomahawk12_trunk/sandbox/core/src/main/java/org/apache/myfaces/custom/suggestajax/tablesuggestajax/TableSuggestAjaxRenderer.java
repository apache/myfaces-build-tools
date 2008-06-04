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
package org.apache.myfaces.custom.suggestajax.tablesuggestajax;

import org.apache.commons.collections.map.HashedMap;
import org.apache.myfaces.component.html.ext.UIComponentPerspective;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.dojo.DojoConfig;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.custom.suggestajax.SuggestAjaxRenderer;
import org.apache.myfaces.shared_tomahawk.component.ExecuteOnCallback;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Input"
 *   type = "org.apache.myfaces.TableSuggestAjax"
 * 
 * @author Gerald Muellan
 *         Date: 25.03.2006
 *         Time: 17:05:38
 */
public class TableSuggestAjaxRenderer extends SuggestAjaxRenderer implements AjaxRenderer
{
    public static final int DEFAULT_START_REQUEST = 0;
    public static final int DEFAULT_BETWEEN_KEY_UP = 1000;
    public static final String DEFAULT_AUTO_COMPLETE = "true";
    
   /**
     * Encodes any stand-alone javascript functions that are needed.
     * Uses either the extension filter, or a
     * user-supplied location for the javascript files.
     *
     * @param context FacesContext
     * @param component UIComponent
     * @throws java.io.IOException
     */
    private void encodeJavascript(FacesContext context, UIComponent component) throws IOException
    {

        String javascriptLocation = (String)component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);

        DojoConfig dojoConfig = new DojoConfig();
        DojoUtils.addMainInclude(context, component, javascriptLocation, dojoConfig);
        DojoUtils.addRequire(context, component, "extensions.FacesIO");
        DojoUtils.addRequire(context, component, "extensions.widget.TableSuggest");
        DojoUtils.addRequire(context, component, "dojo.event.*");
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(context, component, TableSuggestAjax.class);

        TableSuggestAjax tableSuggestAjax = (TableSuggestAjax) component;

        encodeJavascript(context,component);

        //Disables browser auto completion
        tableSuggestAjax.getAttributes().put("autocomplete","off");

        String clientId = component.getClientId(context);
        String actionURL = getActionUrl(context);

        String charset = (tableSuggestAjax.getCharset() != null ? tableSuggestAjax.getCharset() : "");
        String ajaxUrl = context.getExternalContext().encodeActionURL(addQueryString(actionURL, "affectedAjaxComponent=" + clientId +
                "&charset=" + charset + "&" + clientId + "=%{searchString}"));

        ResponseWriter out = context.getResponseWriter();

        String valueToRender = RendererUtils.getStringValue(context,tableSuggestAjax);
        valueToRender = escapeQuotes(valueToRender);

        if (getChildren(tableSuggestAjax) != null
                && !getChildren(tableSuggestAjax).isEmpty())
        {
            StringBuffer divId = new StringBuffer();
            divId.append(clientId).append(":::div");

            out.startElement(HTML.DIV_ELEM, component);
            out.writeAttribute(HTML.ID_ATTR, divId , null);
            out.endElement(HTML.DIV_ELEM);
            super.encodeEnd(context, tableSuggestAjax);

            String tableSuggestComponentVar = DojoUtils.calculateWidgetVarName(divId.toString());

            Map attributes = new HashedMap();
            String betweenKeyUp = "";
            String startRequest = "";
            String autoComplete = "";

            //General attributes
            attributes.put("dataUrl", ajaxUrl);
            attributes.put("mode", "remote");

            //BetweenKeyUp
            if (tableSuggestAjax.getBetweenKeyUp() != null) {
                betweenKeyUp = tableSuggestAjax.getBetweenKeyUp().toString();
            }
            else {
                betweenKeyUp = Integer.toString(DEFAULT_BETWEEN_KEY_UP);
            }
            attributes.put("searchDelay", betweenKeyUp);

            //StartRequest
            if (tableSuggestAjax.getStartRequest() != null) {
                startRequest = tableSuggestAjax.getStartRequest().toString();
            }
            else {
                startRequest = Integer.toString(DEFAULT_START_REQUEST);
            }
            attributes.put("startRequest", startRequest);

            //Autocomplete
            if (tableSuggestAjax.getAutocomplete()!=null) {
                autoComplete = tableSuggestAjax.getAutocomplete();
            }
            else {
                autoComplete = DEFAULT_AUTO_COMPLETE;
            }
            attributes.put("autoComplete", autoComplete);

            //PopupId
            if (tableSuggestAjax.getPopupId() != null) {
                attributes.put("popupId", tableSuggestAjax.getPopupId());
            }

            //PopupStyleClass
            if (tableSuggestAjax.getPopupStyleClass() != null) {
                attributes.put("popupStyleClass", tableSuggestAjax.getPopupStyleClass());
            }

            //TableStyleClass
            if (tableSuggestAjax.getTableStyleClass() != null) {
                attributes.put("tableStyleClass", tableSuggestAjax.getTableStyleClass());
            }

            //ComboBoxStyleClass
            if (tableSuggestAjax.getComboBoxStyleClass() != null) {
                attributes.put("comboBoxStyleClass", tableSuggestAjax.getComboBoxStyleClass());
            }

            //RowStyleClass
            if (tableSuggestAjax.getRowStyleClass() != null) {
                attributes.put("rowStyleClass", tableSuggestAjax.getRowStyleClass());
            }

            //EvenRowStyleClass
            if (tableSuggestAjax.getEvenRowStyleClass() != null) {
                attributes.put("evenRowStyleClass", tableSuggestAjax.getEvenRowStyleClass());
            }

            //OddRowStyleClass
            if (tableSuggestAjax.getOddRowStyleClass() != null) {
                attributes.put("oddRowStyleClass", tableSuggestAjax.getOddRowStyleClass());
            }

            //HoverRowStyleClass
            if (tableSuggestAjax.getHoverRowStyleClass() != null) {
                attributes.put("hoverRowStyleClass", tableSuggestAjax.getHoverRowStyleClass());
            }

            //textInputId
            attributes.put("textInputId", clientId);

            DojoUtils.renderWidgetInitializationCode(context.getResponseWriter(), component, "extensions:TableSuggest", attributes, divId.toString(), true);

            out.startElement(HTML.SCRIPT_ELEM, null);
            out.writeAttribute(HTML.TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);

            StringBuffer buffer = new StringBuffer();

            buffer.append("dojo.addOnLoad(function() {\n")
                  .append(tableSuggestComponentVar).append(".comboBoxValue.value = \"").append(valueToRender).append("\";\n")
                  .append(tableSuggestComponentVar).append(".onResize();\n")
                  .append("});\n");

            out.write(buffer.toString());

            out.endElement(HTML.SCRIPT_ELEM);
        }
    }

    public void decode(FacesContext facesContext, UIComponent component)
    {
        super.decode(facesContext, component);
    }

    public void encodeAjax(FacesContext context, UIComponent uiComponent)
                                                                    throws IOException
    {
        String clientId = (String)context.getExternalContext()
                                    .getRequestParameterMap().get("affectedAjaxComponent");

        UIViewRoot root = context.getViewRoot();

        UIComponent ajaxComp = root.findComponent(clientId);
        
        
        //checking if ajaxComp is inside a dataTable; here needed for getting the correct ids
        if (ajaxComp instanceof UIComponentPerspective)
        {
            UIComponentPerspective componentPerspective = (UIComponentPerspective) ajaxComp;

            componentPerspective.executeOn(context, new ExecuteOnCallback()
            {
                public Object execute(FacesContext facesContext, UIComponent uiComponent)
                {
                    try
                    {
                        renderResponse ( (TableSuggestAjax) uiComponent, facesContext);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    return uiComponent;
                }
            });
        }
        else
        {
            renderResponse ( (TableSuggestAjax) uiComponent, context);
        }
    }
    
    //renders the response data in an XML format
    private void renderResponse(TableSuggestAjax tableSuggestAjax,
            FacesContext context) throws IOException
    {

        context.getResponseWriter().write("[");
        renderColumnHeaders(tableSuggestAjax, context);
        
        StringBuffer response = new StringBuffer();
        response.append("[");

        Collection suggestedItems =
            getSuggestedItems(context, tableSuggestAjax);

        if (getChildren(tableSuggestAjax) == null
                || getChildren(tableSuggestAjax).isEmpty())
        {
            return;
        }
            
        for (Iterator it = suggestedItems.iterator(); it.hasNext();) 
        {
            
            Object addressEntryObject = it.next();
            
            context.getExternalContext().getRequestMap().put(
                    tableSuggestAjax.getVar(), addressEntryObject);

            response.append("[");

            Iterator columns = tableSuggestAjax.getChildren().iterator();
            while (columns.hasNext()) 
            {
                UIComponent column = (UIComponent) columns.next();

                Iterator columnChildren = column.getChildren().iterator(); 
                while (columnChildren.hasNext())
                {
                    Object component = columnChildren.next();
                    
                    if (!(component instanceof HtmlOutputText)) 
                    {
                        continue;
                    }
                    
                    HtmlOutputText htmlOutputText = 
                        (HtmlOutputText) component;

                    response.append("{");
                    
                    //foreign-key field is a simple text field
                    if (htmlOutputText.getFor() != null)
                    {
                        response.append("\"forText\": ");
                        String forText = RendererUtils.getClientId(context,
                                tableSuggestAjax,
                                htmlOutputText.getFor());
                        response.append("\"").append(forText).append("\",");
                        response.append("\"label\": ");
                        response.append("\"").append(htmlOutputText.getLabel()).append("\"");
                    }
                    //foreign-key field is a combo-box field 
                    else if (htmlOutputText.getForValue() != null)
                    {
                        response.append("\"forValue\": ");
                        String forValue = RendererUtils.getClientId(context,
                                tableSuggestAjax,
                                htmlOutputText.getForValue());
                        response.append("\"").append(forValue).append("\",");
                        response.append("\"label\": ");
                        response.append("\"").append(htmlOutputText.getLabel()).append("\",");
                        response.append("\"value\": ");
                        response.append("\"").append(htmlOutputText.getValue()).append("\"");
                    } else {
                        response.append("\"label\": ");
                        response.append("\"").append(htmlOutputText.getValue()).append("\"");
                    }
                    response.append("}");
                    if (columnChildren.hasNext() || columns.hasNext()) {
                        response.append(",");
                    }
                }
            }
            response.append("]");
            if (it.hasNext()) {
                response.append(",");
            }
        }
        
        context.getExternalContext().getRequestMap().remove(tableSuggestAjax.getVar());
        response.append("]");
        context.getResponseWriter().write(response.toString());
        context.getResponseWriter().write("]");
        
    }

    //renders column names in the XML response
    private void renderColumnHeaders(TableSuggestAjax tableSuggestAjax,
            FacesContext context) throws IOException
    {
        StringBuffer columnHeaders = new StringBuffer();
        columnHeaders.append("[");

        Iterator it = tableSuggestAjax.getChildren().iterator();
        
        while (it.hasNext())
        {
            UIComponent column = (UIComponent) it.next();

            if (column instanceof UIColumn)
            {
                UIComponent tableHeader = (UIComponent) column.getFacet("header");

                if (tableHeader != null && tableHeader instanceof HtmlOutputText)
                {
                    HtmlOutputText htmlOutputText = (HtmlOutputText) tableHeader;
                    columnHeaders.append("\"").append(htmlOutputText.getValue()).append("\"");
                    if (it.hasNext()) {
                        columnHeaders.append(",");
                    }
                }
            }
        }
        columnHeaders.append("],");
        context.getResponseWriter().write(columnHeaders.toString());
    }

    private String escapeQuotes(String input)
    {
   	    return input != null ? input.replaceAll("\"", "\\\\\"") : "";
    }

}


