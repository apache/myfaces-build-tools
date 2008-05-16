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

/**
 * @JSFComponent
 *   name = "s:captcha"
 *   tagClass = "org.apache.myfaces.custom.captcha.CAPTCHATag"
 * 
 * @author Hazem Saleh
 *
 */
public class CAPTCHAComponent extends UIComponentBase {

    public static String COMPONENT_TYPE = "org.apache.myfaces.CAPTCHA";
    public static String RENDERER_TYPE = "org.apache.myfaces.CAPTCHA";
    
    // Value binding constants
    public static final String ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME = "captchaSessionKeyName";
    public static final String ATTRIBUTE_IMAGE_WIDTH = "imageWidth";
    public static final String ATTRIBUTE_IMAGE_HEIGHT = "imageHeight";
    
    private String _captchaSessionKeyName;
    private String _imageWidth;
    private String _imageHeight;    

    public String getFamily() {
	return "org.apache.myfaces.CAPTCHA";
    }

    public String getCaptchaSessionKeyName() {
	if (_captchaSessionKeyName != null)
	{
	    return _captchaSessionKeyName;
	}

	ValueBinding vb = getValueBinding(ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME);
	return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
		: null;
    }
    
    public String getImageWidth() {
	if (_imageWidth != null) 
	{
	    return _imageWidth;
	}

	ValueBinding vb = getValueBinding(ATTRIBUTE_IMAGE_WIDTH);
	return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
		: null;
    }

    public String getImageHeight() {
	if (_imageHeight != null) 
	{
	    return _imageHeight;
	}

	ValueBinding vb = getValueBinding(ATTRIBUTE_IMAGE_HEIGHT);
	return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
		: null;
    }      

    public void setImageWidth(String width) {
	this._imageWidth = width;
    }       
    
    public void setImageHeight(String height) {
	this._imageHeight = height;
    }    
   

    public Object saveState(FacesContext context) {
	Object values[] = new Object[4];
	values[0] = super.saveState(context);
	values[1] = _captchaSessionKeyName;
	values[2] = _imageWidth;
	values[3] = _imageHeight;	
	return ((values));
    }

    public void restoreState(FacesContext context, Object state) {
	Object values[] = (Object[]) state;
	super.restoreState(context, values[0]);
	_captchaSessionKeyName = (String) values[1];
	_imageWidth = (String) values[2];
	_imageHeight = (String) values[3];	
    }
}
