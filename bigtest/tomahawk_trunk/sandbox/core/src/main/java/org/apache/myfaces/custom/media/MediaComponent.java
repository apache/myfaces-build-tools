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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;


public class MediaComponent extends UIComponentBase {

    public static String COMPONENT_TYPE = "org.apache.myfaces.media";
    public static String RENDERER_TYPE = "org.apache.myfaces.media";
    
    // Value binding constants
    public static final String ATTRIBUTE_WIDTH = "width";
    public static final String ATTRIBUTE_HEIGHT = "height";
    public static final String ATTRIBUTE_SOURCE = "source";
    public static final String ATTRIBUTE_CONTENT_TYPE = "contentType";    
    
    private String _source;
    private String _contentType;      
    private String _width;
    private String _height;        

    public String getFamily() {
	return "org.apache.myfaces.media";
    }

    public String getWidth() {
	if (_width != null) 
	{
	    return _width;
	}

	ValueBinding vb = getValueBinding(ATTRIBUTE_WIDTH);
	return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
		: null;
    }

    public String getHeight() {
	if (_height != null) 
	{
	    return _height;
	}

	ValueBinding vb = getValueBinding(ATTRIBUTE_HEIGHT);
	return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
		: null;
    }
    
    public String getContentType() {
	if (_contentType != null) 
	{
	    return _contentType;
	}

	ValueBinding vb = getValueBinding(ATTRIBUTE_CONTENT_TYPE);
	return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
		: null;
    }
    
    public String getSource() {
	if (_source != null) 
	{
	    return _source;
	}

	ValueBinding vb = getValueBinding(ATTRIBUTE_SOURCE);
	return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb)
		: null;
    }
    
    public void setWidth(String width) {
	this._width = width;
    }       
    
    public void setHeight(String height) {
	this._height = height;
    }    
  
    public void setContentType(String contentType) {
        _contentType = contentType;
    }

    public void setSource(String _source) {
        this._source = _source;
    }

    public Object saveState(FacesContext context) {
	Object values[] = new Object[5];
	values[0] = super.saveState(context);
	values[1] = _source;
	values[2] = _contentType;
	values[3] = _width;	
	values[4] = _height;	
	return ((values));
    }

    public void restoreState(FacesContext context, Object state) {
	Object values[] = (Object[]) state;
	super.restoreState(context, values[0]);
	_source = (String) values[1];
	_contentType = (String) values[2];
	_width = (String) values[3];
	_height = (String) values[4];
    }
}
