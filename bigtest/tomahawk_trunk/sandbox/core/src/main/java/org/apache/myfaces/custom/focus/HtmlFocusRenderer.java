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
package org.apache.myfaces.custom.focus;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.custom.date.HtmlDateRenderer;
import org.apache.myfaces.custom.date.HtmlInputDate;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.custom.dojo.DojoConfig;

/**
 * @author Rogerio Pereira Araujo (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlFocusRenderer extends Renderer
{

    public void decode(FacesContext facesContext, UIComponent component)
    {
        RendererUtils.checkParamValidity(facesContext, component, HtmlFocus.class);

        HtmlFocus focus = (HtmlFocus) component;

        if(focus.isRememberClientFocus())
        {
            focus.setSubmittedValue(RendererUtils.getStringValue(facesContext, component));
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent,
                                         HtmlFocus.class);

        HtmlFocus focus = (HtmlFocus) uiComponent;

        UIComponent targetComponent = focus.findUIComponent();

        if(targetComponent != null)
        {
			if (focus.isRememberClientFocus())
			{
				String javascriptLocation = (String)uiComponent.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
				DojoUtils.addMainInclude(facesContext, uiComponent, javascriptLocation, new DojoConfig());
				DojoUtils.addRequire(facesContext, uiComponent, "dojo.event.*");
			}

			String clientId = targetComponent.getClientId(facesContext);
            if(targetComponent instanceof HtmlInputDate)
            {
                clientId = HtmlDateRenderer.getClientIdForDaySubcomponent(clientId);
            }
            ResponseWriter writer = facesContext.getResponseWriter();

            writer.startElement(HTML.SCRIPT_ELEM, uiComponent);
            writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
            writer.writeText("setTimeout(\"document.getElementById('" + clientId + "').focus()\", 100)", null);
            writer.endElement(HTML.SCRIPT_ELEM);

			if (focus.isRememberClientFocus())
			{
				writer.startElement(HTML.INPUT_ELEM, uiComponent);
				writer.writeAttribute(HTML.TYPE_ATTR,HTML.INPUT_TYPE_HIDDEN,null);
				writer.writeAttribute(HTML.ID_ATTR,uiComponent.getClientId(facesContext), JSFAttr.ID_ATTR);
				writer.writeAttribute(HTML.VALUE_ATTR,clientId,JSFAttr.VALUE_ATTR);
				writer.endElement(HTML.INPUT_ELEM);
			}
		}
    }

}
