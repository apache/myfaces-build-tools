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
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlGroupRendererBase;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;


/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.Group"
 * 
 * @author Martin Marinschek (latest modification by $Author: mmarinschek $)
 * @version $Revision: 167446 $ $Date: 2004-12-23 13:03:09Z $
 */
public class HtmlGroupRenderer
    extends HtmlGroupRendererBase
{
	
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean span = false;
        String element = getHtmlElement(component);
        
        if(component.getId()!=null && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
        {
            span = true;

            writer.startElement(element, component);

            HtmlRendererUtils.writeIdIfNecessary(writer, component, context);

            HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.COMMON_PASSTROUGH_ATTRIBUTES);
        }
        else
        {
            span=HtmlRendererUtils.renderHTMLAttributesWithOptionalStartElement(writer,
                                                                             component,
                                                                             element,
                                                                             HTML.COMMON_PASSTROUGH_ATTRIBUTES);
        }

        RendererUtils.renderChildren(context, component);
        if (span)
        {
            writer.endElement(element);
        }
    }
	
	private String getHtmlElement(UIComponent component) {
		if (component instanceof HtmlPanelGroup) {
			HtmlPanelGroup group = (HtmlPanelGroup) component;
			if (HtmlPanelGroup.BLOCK_LAYOUT.equals(group.getLayout())) {
				return HTML.DIV_ELEM;
			}
		}
		return HTML.SPAN_ELEM;
	}
}
