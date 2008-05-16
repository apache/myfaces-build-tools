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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.myfaces.custom.media.util.MediaConstants;
import org.apache.myfaces.custom.media.util.MediaUtil;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "org.apache.myfaces.media"
 *   type = "org.apache.myfaces.media"
 *
 * @author Hazem Saleh
 *
 */
public class MediaRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component)
	    throws IOException {

	MediaComponent mediaComponent = (MediaComponent) component;

	RendererUtils.checkParamValidity(context, component,
		MediaComponent.class);

	generateMediaTag(context, mediaComponent);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
	    throws IOException {
	super.encodeEnd(context, component);
    }

    /*
     * This helper method is used for generating the suitable media tag.
     */
    private void generateMediaTag(FacesContext context,
	    MediaComponent mediaComponent) throws IOException {
	
	String source = mediaComponent.getSource();	
	
	/* if the src uri is an image then use the <img> tag */
	if(MediaUtil.isImage(source)) 
	{
	    MediaUtil.generateImageTag(context, mediaComponent);
	} 
	else /* generate EMBED Tag */
	{
	    MediaUtil.generateEmbedTag(context, mediaComponent);
	}
	
    }    
}
