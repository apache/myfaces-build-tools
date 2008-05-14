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
package org.apache.myfaces.custom.media.util;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.media.MediaComponent;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

/**
 * This class acts as a helper for the Media component. 
 */
public class MediaUtil {
    
    final static String[] IMAGES_EXTENSIONS = new String[] {
	"jpg",
	"jpeg",
	"png",
	"mng",
	"bmp",
	"gif",
	"dxf"
    };
    
    /**
     * This method checks whether the passed uri contains
     * image.
     * @param uri
     * @return boolean.
     */
    public static boolean isImage(String uri) {
	
	for(int i = 0; i < IMAGES_EXTENSIONS.length; ++i) 
	{
	    if(uri.endsWith(IMAGES_EXTENSIONS[i])) 
	    {
		return true;
	    }
	}
	
	return false;
    }
    
    /**
     * This method is used for generating the EMBED Tag 
     * from the mediaComponent attributes.
     * @param context
     * @param mediaComponent
     * @throws IOException
     */
    public static void generateEmbedTag(FacesContext context,
	    MediaComponent mediaComponent) throws IOException {

	ResponseWriter writer = context.getResponseWriter();
	String source = mediaComponent.getSource();
	String contentType = mediaComponent.getContentType();
	String width = mediaComponent.getWidth();
	String height = mediaComponent.getHeight();

	/* start writing the media component */
	writer.startElement(MediaConstants.EMBED_ELEM, mediaComponent);

	writer.writeAttribute(HTML.ID_ATTR,
		mediaComponent.getClientId(context), null);
	writer.writeAttribute(HTML.NAME_ATTR, mediaComponent.getId(),
		JSFAttr.ID_ATTR);

	writer.writeAttribute(HTML.SRC_ATTR, source, null);

	writer.writeAttribute(HTML.TYPE_ATTR, contentType, null);

	/* write the rest of attributes */

	if (width != null && !"".equals(width)) {
	    writer.writeAttribute(HTML.WIDTH_ATTR, width, null);
	}

	if (height != null && !"".equals(height)) {
	    writer.writeAttribute(HTML.HEIGHT_ATTR, height, null);
	}

	writer.writeAttribute(MediaConstants.PLUGINSPAGE_ATTR,
		MediaConstants.DEFAULT_MEDIA_PLUGIN_PAGE, null);
	writer.writeAttribute(MediaConstants.SHOW_GOTO_BAR_ATTR, "true", null);
	writer.writeAttribute(MediaConstants.SHOW_DISPLAY_ATTR, "true", null);
	writer
		.writeAttribute(MediaConstants.SHOW_STATUS_BAR_ATTR, "true",
			null);

	/* if no EMBED not supported */
	writer.startElement(MediaConstants.NO_EMBED_ELEM, mediaComponent);
	writer.write("<a href=\"" + 
			source + 
			"\">EMBED is not supported, Click here to see the resource</a>");
	writer.endElement(MediaConstants.NO_EMBED_ELEM);
    }
    
    /**
     * This method is used for generating the IMG tag 
     * from the mediaComponent attributes. 
     * @param context
     * @param mediaComponent
     * @throws IOException
     */
    public static void generateImageTag(FacesContext context,
	    MediaComponent mediaComponent) throws IOException {

	ResponseWriter writer = context.getResponseWriter();
	String source = mediaComponent.getSource();
	String width = mediaComponent.getWidth();
	String height = mediaComponent.getHeight();

	/* start writing the media component */
	writer.startElement(HTML.IMG_ELEM, mediaComponent);

	writer.writeAttribute(HTML.ID_ATTR, mediaComponent.getClientId(context), null);
	writer.writeAttribute(HTML.NAME_ATTR, mediaComponent.getId(), JSFAttr.ID_ATTR);

	writer.writeAttribute(HTML.SRC_ATTR, source, null);

	/* write the rest of attributes */

	if (width != null && !"".equals(width)) 
	{
	    writer.writeAttribute(HTML.WIDTH_ATTR, width, null);
	}

	if (height != null && !"".equals(height)) 
	{
	    writer.writeAttribute(HTML.HEIGHT_ATTR, height, null);
	}

	writer.endElement(HTML.IMG_ELEM);
    }    
    
}
