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

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.component.html.util.ParameterResourceHandler;
import org.apache.myfaces.custom.captcha.util.CAPTCHAImageGenerator;
import org.apache.myfaces.custom.captcha.util.CAPTCHAResponseStream;
import org.apache.myfaces.custom.captcha.util.CAPTCHATextGenerator;
import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.renderkit.html.util.ResourceLoader;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

public class CAPTCHARenderer extends Renderer implements ResourceLoader {

	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {

		CAPTCHAComponent captchaComponent = (CAPTCHAComponent) component;
		
		generateImageTag(context, captchaComponent);
	}
	
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		super.encodeEnd(context, component);
	}

	/*
	 * This helper method is used for generating the img tag that will
	 * use the (AddResource) to generate the url of the generated image.
	 */
	private void generateImageTag(FacesContext context, CAPTCHAComponent component)
			throws IOException {
		
        AddResource addResource;
        String url;
        ResponseWriter writer = context.getResponseWriter();
        Map params = ComponentUtils.getParameterMap(component);
        String captchaSessionKeyName = component.getCaptchaSessionKeyName();
        
        writer.startElement(HTML.IMG_ELEM, component);

        if (captchaSessionKeyName != null) {
			params.put(CAPTCHAComponent.ATTRIBUTE_CAPTCHASESSIONKEYNAME,
					captchaSessionKeyName);
		}

		addResource = AddResourceFactory.getInstance(context);
        
		url = context.getExternalContext().encodeResourceURL(
				addResource.getResourceUri(context,
						new ParameterResourceHandler(this.getClass(), params)));
        
        writer.writeAttribute(HTML.SRC_ATTR, url, null);

        writer.endElement(HTML.IMG_ELEM);		
	}

	
    /*
     * This method is implemented to be called from the (AddResource).
     * It wraps the CAPTCHA image generation.
     */
	public void serveResource(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			String resourceUri) throws IOException {
		
		// get the FacesContext from the ServletContext.
		FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
				.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
				.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle = lifecycleFactory.getLifecycle(ComponentUtils
				.getLifecycleId(servletContext));
		FacesContext facesContext = facesContextFactory.getFacesContext(
				servletContext, request, response, lifecycle);
		facesContext.setResponseStream(new CAPTCHAResponseStream(response
				.getOutputStream()));
		
		// render the CAPTCHA.
		try {
			try {
				renderCAPTCHA(facesContext);
			} catch (IOException e) {
				throw new FacesException("Could not render the CAPTCHA : "
						+ e.getMessage(), e);
			}
			facesContext.getResponseStream().close();
		} finally {
			facesContext.release();
		}
	}
	    
	/*
	 * This method is used for rendering the CAPTCHA component.
	 */
	protected void renderCAPTCHA(FacesContext facesContext) throws IOException{
		
		// Initialize the CAPTCHA world.
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		ResponseStream out = facesContext.getResponseStream();
		final Map requestMap = facesContext.getExternalContext()
				.getRequestParameterMap();
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		String captchaText;
		CAPTCHAImageGenerator captchaImageGenerator = new CAPTCHAImageGenerator();
		String captchaSessionKeyName = requestMap.get(
				CAPTCHAComponent.ATTRIBUTE_CAPTCHASESSIONKEYNAME).toString();
		
		try {			
			
			// Generate random CAPTCHA text.
			captchaText = CAPTCHATextGenerator.generateRandomText();

			// Generate the image.
			captchaImageGenerator.generateImage(response, captchaText);

			// Set the generated text in the user session.
			request.getSession().setAttribute(captchaSessionKeyName, captchaText);						
			
		} finally {
			out.close();
			facesContext.responseComplete();
		}
	}      
}
