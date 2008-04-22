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

package org.apache.myfaces.custom.graphicimagedynamic.util;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.myfaces.custom.graphicimagedynamic.GraphicImageDynamic;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * This class is a utility class used by the 
 * GraphicsImageDynamic component.
 */
public class GraphicsImageDynamicHelper {
	
	/**
	 * This method is used for getting the ImageRenderer
	 * from the image render class.
	 * @param imageRendererClassName
	 * @return the image renderer
	 */
	public static ImageRenderer getImageRendererFromClassName(
			String imageRendererClassName) {
		ImageRenderer imageRenderer;
		try {
			Class rendererClass = ClassUtils
					.classForName(imageRendererClassName);
			if (!ImageRenderer.class.isAssignableFrom(rendererClass)) {
				throw new FacesException("Image renderer class ["
						+ imageRendererClassName + "] does not implement "
						+ ImageRenderer.class.getName());
			}
			try {
				imageRenderer = (ImageRenderer) rendererClass.newInstance();
			} catch (InstantiationException e) {
				throw new FacesException(
						"could not instantiate image renderer class "
								+ imageRendererClassName + " : "
								+ e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new FacesException(
						"could not instantiate image renderer class "
								+ imageRendererClassName + " : "
								+ e.getMessage(), e);
			}
		} catch (ClassNotFoundException e) {
			throw new FacesException("image renderer class not found: "
					+ e.getMessage(), e);
		}
		return imageRenderer;
	}

	/**
	 * This method gets the image renderer from the ValueBinding.
	 * @param facesContext
	 * @param rendererValueBinding
	 * @return the ImageRenderer.
	 */
	public static ImageRenderer getImageRendererFromValueBinding(
			FacesContext facesContext, String rendererValueBinding) {
		return (ImageRenderer) facesContext.getApplication()
				.createValueBinding(rendererValueBinding).getValue(
						facesContext);
	}	
	
	
	/**
	 * This method is used for creating the image context.
	 * @param facesContext
	 * @return the imageContext.
	 */
    public static ImageContext createImageContext(FacesContext facesContext,
			Log log) {

		ExternalContext externalContext = facesContext.getExternalContext();
		final Map requestMap = externalContext.getRequestParameterMap();
		Object value = requestMap.get(GraphicImageDynamic.WIDTH_PARAM);
		Integer height = null;
		Integer width = null;

		if (value != null) {
			try {
				width = Integer.valueOf(value.toString());
			} catch (NumberFormatException e) {
				log.error("Invalid value for image width : " + value + ", "
						+ e.getMessage(), e);
			}
		}

		value = requestMap.get(GraphicImageDynamic.HEIGHT_PARAM);
		if (value != null) {
			try {
				height = Integer.valueOf(value.toString());
			} catch (NumberFormatException e) {
				log.error("Invalid value for image height : " + value + ", "
						+ e.getMessage(), e);
			}
		}

		return new SimpleImageContext(requestMap, width, height);
	}
}
