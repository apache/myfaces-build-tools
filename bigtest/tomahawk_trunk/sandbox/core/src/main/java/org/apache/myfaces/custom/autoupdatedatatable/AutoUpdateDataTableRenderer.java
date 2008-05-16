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
package org.apache.myfaces.custom.autoupdatedatatable;

import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.prototype.PrototypeResourceLoader;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Data"
 *   type = "org.apache.myfaces.AutoUpdateDataTable"
 *
 * @author J&ouml;rg Artaker
 * @author Thomas Huber
 * @version $Revision: $ $Date: $
 *          <p/>
 *          $Log: $
 */
public class AutoUpdateDataTableRenderer extends HtmlTableRenderer implements AjaxRenderer{

    /**
     * @see org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlTableRendererBase#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    public void encodeBegin(FacesContext context, UIComponent uiComponent) throws IOException
    {
        // output div around here so it can work in IE, IE will not allow updating of innerHTML within a table
        ResponseWriter out = context.getResponseWriter();
        out.write("\n");
        out.startElement(HTML.DIV_ELEM, uiComponent);
        out.writeAttribute(HTML.ID_ATTR, "div" + uiComponent.getClientId(context),null);
        // todo: output some table attributes to the div such as width and maybe alignment

        encodeBeginOnly(context, uiComponent);
    }

    /**
     * Encodes any stand-alone javascript functions that are needed.  Uses either the extension filter, or a
     * user-supplied location for the javascript files.
     *
     * @param context FacesContext
     * @param component UIComponent
     */
    private void encodeJavascript(FacesContext context, UIComponent component)
    {
        // AddResource takes care to add only one reference to the same script
        
        // render javascript function for client-side toggle (it won't be used if user has opted for server-side toggle)
        String javascriptLocation = (String) component.getAttributes().get(
                JSFAttr.JAVASCRIPT_LOCATION);

        AddResource addResource = AddResourceFactory.getInstance(context);
        if(javascriptLocation != null)
        {
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, javascriptLocation + "/prototype.js");
        }
        else
        {
            addResource.addJavaScriptAtPosition(context, AddResource.HEADER_BEGIN, 
                    PrototypeResourceLoader.class, "prototype.js");
        }
    }

    /**
     * @param context FacesContext
     * @param component UIComponent
     * @throws java.io.IOException
     */
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(context, component, AutoUpdateDataTable.class);
        AutoUpdateDataTable autoUpdateDataTable = (AutoUpdateDataTable) component;

        encodeEndOnly(context, component);
        ResponseWriter out = context.getResponseWriter();
        out.endElement(HTML.DIV_ELEM);
        out.write("\n");

        this.encodeJavascript(context,component);




        String viewId = context.getViewRoot().getViewId();
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        String actionURL = viewHandler.getActionURL(context, viewId);
        String clientId = component.getClientId(context);

        out.startElement(HTML.SCRIPT_ELEM, component);
        out.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);

        StringBuffer script = new StringBuffer();
        script.append("function initAutoUpdateDataTable_");
        script.append(component.getId());
        script.append("()\n{");
        script.append("\n");
        script.append(component.getId()).append("_updater").append(" = new Ajax.PeriodicalUpdater('");
        script.append("div").append(component.getClientId(context)); //.append(":tbody_element");
        script.append("','");
        script.append(context.getExternalContext().encodeActionURL(actionURL+"?affectedAjaxComponent="+clientId));
        script.append("', {\n frequency: ").append(autoUpdateDataTable.getFrequency());
        if (context.getApplication().getStateManager().isSavingStateInClient(context)){
            script.append(" , parameters: '&jsf_tree_64='+encodeURIComponent(document.getElementById('jsf_tree_64').value)+'&jsf_state_64='+encodeURIComponent(document.getElementById('jsf_state_64').value)+'&jsf_viewid='+encodeURIComponent(document.getElementById('jsf_viewid').value)");
        }
        String onSuccess = autoUpdateDataTable.getOnSuccess();
        if(onSuccess != null){
            script.append(" , onSuccess: ").append(onSuccess);
        }
        script.append("    });");
        script.append("\n}\n");
        script.append("setTimeout(\"initAutoUpdateDataTable_");
        script.append(component.getId());
        script.append("()\", 0);\n");

        out.writeText(script.toString(),null);

        out.endElement(HTML.SCRIPT_ELEM);
    }

    /**
     * Pulled this out here so when getting a table update, it won't return the script
     * @param context
     * @param component
     * @throws IOException
     */
    private void encodeEndOnly(FacesContext context, UIComponent component)
            throws IOException
    {
        super.encodeEnd(context, component);
    }

    /**
     * Pulled this out here so when getting a table update, it won't do the containing div again
     * @param context
     * @param component
     */
    private void encodeBeginOnly(FacesContext context, UIComponent component) throws IOException
    {
        super.encodeBegin(context, component);
    }

    /**
     * @param facesContext FacesContext
     * @param component UIComponent
     */
    public void decode(FacesContext facesContext, UIComponent component)
    {
        super.decode(facesContext, component);
    }

    /**
     * @param context FacesContext
     * @param component UIComponent
     * @throws java.io.IOException
     */
    public void encodeAjax(FacesContext context, UIComponent component) throws IOException
    {
        encodeBeginOnly(context, component);
        encodeChildren(context, component);
        encodeEndOnly(context, component);
        /*if (context.getApplication().getStateManager().isSavingStateInClient(context)){
            StateManager stateManager = context.getApplication().getStateManager();
            StateManager.SerializedView serializedView = stateManager.saveSerializedView(context);
            Object compStates =  serializedView.getState();

            StringBuffer buf = new StringBuffer();

            buf.append("jsf_state=");
            buf.append(StateUtils.encode64(compStates));
            buf.append("jsf_state_end");

            context.getResponseWriter().write(buf.toString());
        }*/
    }


}
