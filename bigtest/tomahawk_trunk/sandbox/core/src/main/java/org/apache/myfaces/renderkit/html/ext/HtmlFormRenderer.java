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
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.clientvalidation.common.CVUtils;
import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlFormRendererBase;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlFormRenderer extends HtmlFormRendererBase{

	private static String CLIENT_VALIDATON_SCRIPT = "return tomahawk.executeClientLifeCycle();";
	
	public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
    	if(CVUtils.isCVEnabled()) {
    		ComponentUtils.decorateEventAttribute(component, "onsubmit", CLIENT_VALIDATON_SCRIPT);
    	}
    	
    	super.encodeBegin(facesContext, component);
    }
    
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    	if(CVUtils.isCVEnabled()) {
    		encodeBypassCVField(facesContext, component);
    	}
    	
    	super.encodeEnd(facesContext, component);
    	
    	if(CVUtils.isCVEnabled()) {
    		CVUtils.encodeJavascript(facesContext);
			CVUtils.queueCVCalls(facesContext.getViewRoot());
			CVUtils.encodeValidationScript(facesContext);
    	}
    }

    //Renders a hidden input field that will act as a flag to bypass client validation or not
	private void encodeBypassCVField(FacesContext facesContext, UIComponent component) throws IOException{
		ResponseWriter writer = facesContext.getResponseWriter();
		
		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", null);
		writer.writeAttribute("id", CVUtils.BYPASS_CLIENT_VALIDATION_FIELD, null);
		writer.writeAttribute("name", CVUtils.BYPASS_CLIENT_VALIDATION_FIELD, null);
		writer.writeAttribute("value", "false", null);				//immediate command components make this value true when they're clicked 
		writer.endElement("input");
	}
}