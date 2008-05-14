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
package org.apache.myfaces.custom.media;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

public class MediaTag extends UIComponentTagBase {

    /**
     * return the component class ...
     */
    public String getComponentType() {
	return MediaComponent.COMPONENT_TYPE;
    }

    /**
     * Returns the component renderer ...
     */
    public String getRendererType() {
	return MediaComponent.RENDERER_TYPE;
    }

    /**
     * release the used resources ...
     */
    public void release() {
	super.release();
	_source = null;
	_contentType = null;
	_height = null;
	_width = null;
    }

    protected void setProperties(UIComponent component) {
	super.setProperties(component);
	setStringProperty(component, MediaComponent.ATTRIBUTE_SOURCE, _source);
	setStringProperty(component, MediaComponent.ATTRIBUTE_CONTENT_TYPE, _contentType);	
	setStringProperty(component, MediaComponent.ATTRIBUTE_WIDTH, _width);
	setStringProperty(component, MediaComponent.ATTRIBUTE_HEIGHT, _height);	
    }

    public String getSource() {
	return _source;
    }
    
    public String getContentType() {
	return _contentType;
    }    
    
    public String getWidth() {
	return _width;
    }
    
    public String getHeight() {
	return _height;
    }
    
    public void setSource(String source) {
	this._source = source;
    }  
    
    public void setContentType(String contentType) {
	this._contentType = contentType;
    }        

    public void setWidth(String width) {
	this._width = width;
    }
        
    public void setHeight(String height) {
	this._height = height;
    }      

    // Attributes ...
    private String _source;
    private String _contentType;    
    private String _width;
    private String _height;    
}
