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


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.inputAjax.Listener;
import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

/**
 * Extending the outputText component in order to introduce the for attribute.
 * 
 * @JSFComponent
 *   name = "s:outputText"
 *   tagClass = "org.apache.myfaces.custom.suggestajax.tablesuggestajax.HtmlOutputTextTag"
 *   class = "org.apache.myfaces.custom.suggestajax.tablesuggestajax.HtmlOutputText"
 *   superClass = "org.apache.myfaces.custom.suggestajax.tablesuggestajax.AbstractHtmlOutputText"
 *   parent = "org.apache.myfaces.component.html.ext.HtmlOutputText"
 *   
 * @author Gerald Muellan
 *         Date: 15.02.2006
 *         Time: 13:30:43
 */
public abstract class AbstractHtmlOutputText extends org.apache.myfaces.component.html.ext.HtmlOutputText
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlOutputTextFor";

    public AbstractHtmlOutputText()
    {
    }

    public void encodeBegin(FacesContext facesContext) throws IOException
    {
        super.encodeBegin(facesContext);
    }

    public void encodeEnd(FacesContext facesContext) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML.SPAN_ELEM, null);
        writer.writeAttribute("id", this.getClientId(facesContext), null);
        super.encodeEnd(facesContext);
        writer.endElement(HTML.SPAN_ELEM);
    }

    public boolean getRendersChildren()
    {
        return true;
    }

    public void encodeChildren(FacesContext facesContext) throws IOException
    {
        checkForListeners(facesContext, this);
        super.encodeChildren(facesContext);
    }

    private void checkForListeners(FacesContext context, UIComponent component)
    {
        // TODO: MOVE THIS UP THE TREE OR TO SOME OTHER LEVEL SO IT CAN WORK ON ANY COMPONENT
        List children = component.getChildren();
        if(children != null){
            for (int i=0; i<children.size(); i++)
            {
                UIComponent child = (UIComponent) children.get(i);
                if(child instanceof Listener){
                    Listener listener = (Listener) child;
                    Map rmap = context.getExternalContext().getRequestMap();
                    List listeners = (List) rmap.get(Listener.LISTENER_MAP_ENTRY);
                    if(listeners == null){
                        listeners = new ArrayList();
                        rmap.put(Listener.LISTENER_MAP_ENTRY, listeners);
                    }
                    // find component
                    UIViewRoot root = context.getViewRoot();
                    UIComponent ajaxComponent; //= component.findComponent(listener.getOn());
                    //System.out.println("FINDING COMPONENT TO LISTEN ON: " + ajaxComponent);
                    ajaxComponent = ComponentUtils.findComponentById(context, root, listener.getOn());
                    if(ajaxComponent != null){
                        //System.out.println("FINDING COMPONENT TO LISTEN ON: " + ajaxComponent);
                        Map listenerItem = new HashMap();
                        listenerItem.put("listenOn", ajaxComponent.getClientId(context));
                        listenerItem.put("listenerId", component.getClientId(context));
                        listenerItem.put("action", listener.getAction());
                        listenerItem.put("eventType", listener.getEventType());
                        listeners.add(listenerItem);
                    }
                }
            }
        }
    }

    /**
     * Specify the id of the dom element where the label should be put in
     * 
     * @JSFProperty
     */
    public abstract String getFor();

    /**
     * Specify the id of the dom element where the value should be put in. 
     * This value is also included in the suggested table, but only in a 
     * hidden span element following the span for the label in one row.
     * 
     * @JSFProperty
     */
    public abstract String getForValue();

    /**
     * To provide a second value in form of a label. Usage like SelectItem. 
     * Label is brought to client in a hidden span element near the value.
     * 
     * @JSFProperty
     */
    public abstract String getLabel();
    
}
