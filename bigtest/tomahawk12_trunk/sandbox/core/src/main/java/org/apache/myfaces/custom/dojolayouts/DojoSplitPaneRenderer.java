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

package org.apache.myfaces.custom.dojolayouts;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.DojoSplitPaneRenderer"
 *
 * @author werpu
 *
 */
public class DojoSplitPaneRenderer extends DojoContentPaneRenderer {
    protected void encodeJavascriptBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeJavascriptBegin(context, component);
        ResponseWriter writer = context.getResponseWriter();
        //writer.startElement(HTML.SCRIPT_ELEM, component);
        //writer.writeAttribute(HTML.TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        //writer.write("dojo.hostenv.setModulePrefix('org.apache.myfaces.dojolayouts', '../dojolayouts.ResourceLoader');\n");
        //writer.endElement(HTML.SCRIPT_ELEM);
        DojoUtils.addRequire(context, component, "dojo.widget.SplitContainer");

        //DojoUtils.addRequire(context, component, "org.apache.myfaces.dojolayouts.MyfacesSplitContainer");

    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        Boolean rendered = (Boolean) component.getAttributes().get("rendered");

        if ((rendered != null) && (!rendered.booleanValue()))
            return;

        super.encodeBegin(context, component);
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.renderChildren(context, component);
        HtmlRendererUtils.writePrettyLineSeparator(context);
        Stack stack = getChildsStack(context, component);
        List children = component.getChildren();

        for (Iterator cit = children.iterator(); cit.hasNext();) {
            UIComponent child = (UIComponent) cit.next();
            if (!child.isRendered())
                continue;
            if (child instanceof DojoContentPane) {
                stack.push(DojoUtils.calculateWidgetVarName(context, child));
            }
        }
    }

    private Stack getChildsStack(FacesContext context, UIComponent component) {
        Stack menuStack = (Stack) ((HttpServletRequest) context.getExternalContext().getRequest()).getAttribute(component.getClientId(context)
                + DojoSplitPaneRenderer.class.toString());
        if (menuStack != null)
            return menuStack;

        menuStack = new Stack();
        ((HttpServletRequest) context.getExternalContext().getRequest()).setAttribute(component.getClientId(context) + DojoSplitPaneRenderer.class.toString(),
                menuStack);
        return menuStack;
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
    }

    protected void encodeJavascriptEnd(FacesContext context, UIComponent component) throws IOException {
        // called by super.encodeEnd
        //"sizeShare",
        String [] attributeNames = {"activeSizing", "orientation", 
                "sizerWidth", "persist", "startPoint", "lastPoint",  "widgetVar", "widgetId"};
        
        
        String panelComponentVar = DojoUtils.calculateWidgetVarName(component.getClientId(context));
        
        DojoUtils.renderWidgetInitializationCode(context, component, "SplitContainer", attributeNames);
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(HTML.SCRIPT_ELEM, component);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);

        Stack stack = this.getChildsStack(context, component);
        while (!stack.isEmpty()) {
            String javascriptVar = (String) stack.pop();
            writer.write(panelComponentVar + ".addChild(" + javascriptVar + ");\n");
        }
        writer.write(panelComponentVar+".postCreate();\n");
        
        writer.endElement(HTML.SCRIPT_ELEM);
    }

    public boolean getRendersChildren() {
        return true;
    }

}
