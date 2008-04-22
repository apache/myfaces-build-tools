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
package org.apache.myfaces.examples.graphicImageDynamic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;

import org.apache.myfaces.custom.dynamicResources.ResourceContext;
import org.apache.myfaces.custom.graphicimagedynamic.util.ImageContext;
import org.apache.myfaces.custom.graphicimagedynamic.util.ImageRenderer;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Mathias Broekelmann
 *
 */
public class GraphicImageDynamicTextBean implements ImageRenderer
{
	private String _text;
	
    private byte[] bytes = null;
    
    public String getText() {
		return _text;
	}

	public void setText(String text) {
		this._text = text;
	}

	public void setContext(FacesContext facesContext, ResourceContext resourceContext) throws IOException
    {
		ImageContext imageContext = (ImageContext) resourceContext;
	    Object text = imageContext.getParamters().get("text");
	    if (text == null)
	    {
	        text = "";
	    }
	    int width = 300;
	    int height = 30;
	    Integer widthObj = imageContext.getWidth();
	    if (widthObj != null)
	    {
	        width = widthObj.intValue();
	    }
	    Integer heightObj = imageContext.getHeight();
	    if (heightObj != null)
	    {
	        height = heightObj.intValue();
	    }
	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics graphics = img.getGraphics();
	    try
	    {
	        graphics.setColor(Color.WHITE);
	        graphics.fillRect(0, 0, width, height);
	        graphics.setColor(Color.BLUE);
	        graphics.drawString(text.toString(), 10, 20);
	        
	        ByteArrayOutputStream baout = new ByteArrayOutputStream();
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baout);
	        encoder.encode(img);
	        baout.flush();
	        bytes = baout.toByteArray();
	    }
	    finally
	    {
	        graphics.dispose();
	    }	
    }

    public Class getImageRenderer()
    {
        return this.getClass();
    }

    /**
     * @see org.apache.myfaces.custom.graphicimagedynamic.ImageRenderer#getContentType()
     */
    public String getContentType()
    {
        return "image/jpeg";
    }
    
    /**
     * @see org.apache.myfaces.custom.graphicimagedynamic.ImageRenderer#getContentLength()
     */
    public int getContentLength()
    {
        return -1;
    }

    /**
     * @see org.apache.myfaces.custom.graphicimagedynamic.ImageRenderer#renderResource(javax.faces.context.ResponseStream) 
     */
    public void renderResource(ResponseStream out)
            throws IOException
    {
        out.write( bytes );
    }

}
