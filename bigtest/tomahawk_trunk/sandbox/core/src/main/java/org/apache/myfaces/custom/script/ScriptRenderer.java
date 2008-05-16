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
package org.apache.myfaces.custom.script;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.Script"
 * 
 * @author Matthias Wessendorf (changed by $Author$)
 * @version $Revision$ $Date$
 */
public class ScriptRenderer extends HtmlRenderer {

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if ((context == null) || (component == null)) {
			throw new NullPointerException();
		}
		Script script = (Script) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.startElement(HTML.SCRIPT_ELEM, component);
		if (script.getSrc() != null)
		{
			writer.writeAttribute(HTML.SRC_ATTR, context.getApplication().getViewHandler().getResourceURL(context, script.getSrc()), null);
		}
		if (script.getType() != null)
		{
			writer.writeAttribute(HTML.TYPE_ATTR, script.getType(), null);
		}
		if (script.getLanguage() != null)
		{
			writer.writeAttribute(HTML.SCRIPT_LANGUAGE_ATTR, script.getLanguage(), null);
		}
		writer.writeText("",null);
	}
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		if ((context == null) || (component == null)) {
			throw new NullPointerException();
		}

		ResponseWriter writer = context.getResponseWriter();
        writer.endElement(HTML.SCRIPT_ELEM);
    }
}
