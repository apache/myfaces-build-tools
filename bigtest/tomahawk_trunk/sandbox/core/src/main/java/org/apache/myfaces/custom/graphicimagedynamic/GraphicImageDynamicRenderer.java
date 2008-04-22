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

package org.apache.myfaces.custom.graphicimagedynamic;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.util.ParameterResourceHandler;
import org.apache.myfaces.custom.graphicimagedynamic.util.GraphicImageDynamicConstants;
import org.apache.myfaces.custom.graphicimagedynamic.util.GraphicsImageDynamicHelper;
import org.apache.myfaces.custom.graphicimagedynamic.util.ImageContext;
import org.apache.myfaces.custom.graphicimagedynamic.util.ImageRenderer;
import org.apache.myfaces.custom.graphicimagedynamic.util.ImageResponseStream;
import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlImageRenderer;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.renderkit.html.util.ResourceLoader;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * @author Sylvain Vieujot
 */
public class GraphicImageDynamicRenderer extends HtmlImageRenderer implements
		ResourceLoader {
	
	private static final Log log = LogFactory
			.getLog(GraphicImageDynamicRenderer.class);
    public static final String RENDERER_TYPE = "org.apache.myfaces.GraphicImageDynamicRenderer";

    public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {

		GraphicImageDynamic graphicImageDynamic = (GraphicImageDynamic) component;
		String width = graphicImageDynamic.getWidth();
		String height = graphicImageDynamic.getHeight();
		ResponseWriter writer = context.getResponseWriter();
		Map params = ComponentUtils.getParameterMap(component);
		Class imageRendererClass = graphicImageDynamic.getImageRendererClass();
		ValueBinding imageRendererValueBinding = graphicImageDynamic
				.getValueBinding("value");
		AddResource addResource;
		String url;

		// render the img HTML element.
		RendererUtils.checkParamValidity(context, component,
				GraphicImageDynamic.class);

		writer.startElement(HTML.IMG_ELEM, graphicImageDynamic);

		HtmlRendererUtils.writeIdIfNecessary(writer, graphicImageDynamic,
				context);
		HtmlRendererUtils.renderHTMLAttributes(writer, graphicImageDynamic,
				HTML.IMG_PASSTHROUGH_ATTRIBUTES);

		if (width != null) {
			params.put(GraphicImageDynamic.WIDTH_PARAM, width);
		}

		if (height != null) {
			params.put(GraphicImageDynamic.HEIGHT_PARAM, height);
		}

		if (imageRendererClass != null) {
			params.put(GraphicImageDynamic.RENDERER_PARAM, imageRendererClass
					.getName());
		}

		if (imageRendererValueBinding != null) {
			params.put(GraphicImageDynamic.VALUE_PARAM,
					imageRendererValueBinding.getExpressionString());
		}

		addResource = AddResourceFactory.getInstance(context);
		url = context.getExternalContext().encodeResourceURL(
				addResource.getResourceUri(context,
						new ParameterResourceHandler(this.getClass(), params)));
		writer.writeAttribute(HTML.SRC_ATTR, url, null);

		writer.endElement(HTML.IMG_ELEM);
	}

    public void decode(FacesContext facesContext, UIComponent component) {
		super.decode(facesContext, component);
	}

    public void serveResource(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			String resourceUri) throws IOException {

		// get the facesContext from the servletContext.
		FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
				.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
				.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle = lifecycleFactory.getLifecycle(ComponentUtils
				.getLifecycleId(servletContext));
		FacesContext facesContext = facesContextFactory.getFacesContext(
				servletContext, request, response, lifecycle);

		facesContext.setResponseStream(new ImageResponseStream(response
				.getOutputStream()));

		// render the image.
		try {

			ImageRenderer imageRenderer = null;
			Map requestMap = facesContext.getExternalContext()
					.getRequestParameterMap();
			Object rendererValue = requestMap
					.get(GraphicImageDynamic.RENDERER_PARAM);

			if (rendererValue != null) {
				imageRenderer = GraphicsImageDynamicHelper
						.getImageRendererFromClassName(rendererValue.toString());
			} else {
				Object rendererValueBinding = requestMap
						.get(GraphicImageDynamic.VALUE_PARAM);
				
				if (rendererValueBinding != null) {
					imageRenderer = GraphicsImageDynamicHelper
							.getImageRendererFromValueBinding(facesContext,
									rendererValueBinding.toString());
				}
			}

			if (imageRenderer == null) {
				throw new FacesException(
						GraphicImageDynamicConstants.NO_IMAGE_RENDERER_DEFINED);
			}

			try {
				renderImage(imageRenderer, facesContext);
			} catch (Exception e) {
				throw new FacesException(
						GraphicImageDynamicConstants.MSG_COULDNOT_RENDER_IMAGE
								+ rendererValue + " : " + e.getMessage(), e);
			}

			facesContext.getResponseStream().close();
		} finally {
			facesContext.release();
		}
	}

    /**
     * This method is used for rendering the image.
     * @param imageRenderer
     * @param facesContext
     * @throws Exception
     */
	protected void renderImage(ImageRenderer imageRenderer,
			FacesContext facesContext) throws Exception {

		ImageContext imageContext = GraphicsImageDynamicHelper
				.createImageContext(facesContext, log);
		imageRenderer.setContext(facesContext, imageContext);
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		int contentLength = imageRenderer.getContentLength();
		String contentType = imageRenderer.getContentType();
		ResponseStream out = facesContext.getResponseStream();

		if (contentLength > 0) {
			response.setContentLength(contentLength);
		}

		if (contentType != null && contentType.length() > 0) {
			response.setContentType(contentType);
		}

		try {
			imageRenderer.renderResource(out);
		} finally {
			out.close();
			facesContext.responseComplete();
		}
	}
}
