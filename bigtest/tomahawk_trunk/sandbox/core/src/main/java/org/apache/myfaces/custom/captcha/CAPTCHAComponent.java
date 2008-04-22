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
package org.apache.myfaces.custom.captcha;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;


public class CAPTCHAComponent extends UIComponentBase { 

    public static final String ATTRIBUTE_CAPTCHASESSIONKEYNAME = "captchaSessionKeyName";
	public static String COMPONENT_TYPE = "org.apache.myfaces.CAPTCHA";
	public static String RENDERER_TYPE = "org.apache.myfaces.CAPTCHA";
	private String _captchaSessionKeyName;
	
	public String getFamily() {
		return "org.apache.myfaces.CAPTCHA";
	}
	
	public String getCaptchaSessionKeyName() {
		if (_captchaSessionKeyName != null)
			return _captchaSessionKeyName;

		ValueBinding vb = getValueBinding("captchaSessionKeyName");
		return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
				: null;
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[2];
		values[0] = super.saveState(context);
		values[1] = _captchaSessionKeyName;	
		return ((values));
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		_captchaSessionKeyName = (String) values[1];
	}	
}
