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

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

public class CAPTCHATag extends UIComponentTagBase {

    /**
     * return the component class ...
     */
    public String getComponentType() {
	return CAPTCHAComponent.COMPONENT_TYPE;
    }

    /**
     * Returns the component renderer ...
     */
    public String getRendererType() {
	return CAPTCHAComponent.RENDERER_TYPE;
    }

    /**
     * release the used resources ...
     */
    public void release() {
	super.release();
	_captchaSessionKeyName = null;
	_imageHeight = null;
	_imageWidth = null;
    }

    protected void setProperties(UIComponent component) {
	super.setProperties(component);
	setStringProperty(component, CAPTCHAComponent.ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME,
		_captchaSessionKeyName);
	setStringProperty(component, CAPTCHAComponent.ATTRIBUTE_IMAGE_WIDTH, _imageWidth);
	setStringProperty(component, CAPTCHAComponent.ATTRIBUTE_IMAGE_HEIGHT, _imageHeight);	
    }

    public String getCaptchaSessionKeyName() {
	return _captchaSessionKeyName;
    }

    public void setCaptchaSessionKeyName(String captchaSessionKeyName) {
	this._captchaSessionKeyName = captchaSessionKeyName;
    }
    
    public String getImageWidth() {
	return _imageWidth;
    }

    public void setImageWidth(String width) {
	this._imageWidth = width;
    }   
    
    public String getImageHeight() {
	return _imageHeight;
    }

    public void setImageHeight(String height) {
	this._imageHeight = height;
    }      

    // Attributes ...
    private String _captchaSessionKeyName;
    private String _imageWidth;
    private String _imageHeight;    
}
