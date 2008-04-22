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
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.clientvalidation.common.CVUtils;
import org.apache.myfaces.custom.util.ComponentUtils;

/**
 * Sandbox version of extended tomahawk button renderer, this must be merge in case client validation goes in tomahawk
 * 
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlButtonExRenderer extends HtmlButtonRenderer{
	
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		HtmlCommandButton button = (HtmlCommandButton) component;
		
		if(CVUtils.isCVEnabled() && button.isImmediate()) {
			String bypassCVScript = "document.getElementById('" + CVUtils.BYPASS_CLIENT_VALIDATION_FIELD + "').value = true;";
			ComponentUtils.decorateEventAttribute(component, "onclick", bypassCVScript);
		}
		
		super.encodeBegin(context, component);
	}

	
}
