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

import org.apache.myfaces.component.html.ext.HtmlGraphicImage;
import org.apache.myfaces.custom.graphicimagedynamic.util.ImageRenderer;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.ValueBinding;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 *
 * @version $Revision$ $Date: 2005-05-11 19:57:24 +0200 (Wed, 11 May 2005) $
 * 
 * Warning, this component is far from ready.
 * It's more a proof of concept right now.
 * TODO : Remove the need to include .get for the last part of the method expressions : getBytesMethod="#{graphicImageDynamicBean.upImage.getBytes}"
 * TODO : Make a similar download component to download files 
 * TODO : Use shorter URLs
 */

public class GraphicImageDynamic extends HtmlGraphicImage
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.GraphicImageDynamic";
    public static final String COMPONENT_FAMILY = "javax.faces.Graphic";
    public static final String RENDERER_PARAM = "_renderer";
	public static final String VALUE_PARAM = "_value";
	public static final String WIDTH_PARAM = "_width";
	public static final String HEIGHT_PARAM = "_height";    
    private static final String DEFAULT_RENDERER_TYPE = GraphicImageDynamicRenderer.RENDERER_TYPE;	
    

    public GraphicImageDynamic()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    private Class _imageRendererClass;

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = _imageRendererClass;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _imageRendererClass = (Class) values[1];
    }

    public void setImageRendererClass(Class imageRendererClass)
    {
        if (imageRendererClass != null && !ImageRenderer.class.isAssignableFrom(imageRendererClass))
        {
            throw new IllegalArgumentException(
                    "imageRendererClass must be null or a class which implements "
                            + ImageRenderer.class.getName());
        }
        _imageRendererClass = imageRendererClass;
    }

    public Class getImageRendererClass()
    {
        if (_imageRendererClass != null)
        {
            return _imageRendererClass;
        }
        ValueBinding vb = getValueBinding("imageRendererClass");
        if (vb != null)
        {
            Object value = vb.getValue(getFacesContext());
            if (value == null)
            {
                return null;
            }
            Class clazz;
            if (value instanceof Class)
            {
                clazz = (Class) value;
            }
            else
            {
                try
                {
                    clazz = ClassUtils.classForName(value.toString());
                }
                catch (ClassNotFoundException e)
                {
                    throw new EvaluationException("Could not load imageRendererClass for "
                            + vb.getExpressionString(), e);
                }
            }
            if (!ImageRenderer.class.isAssignableFrom(clazz))
            {
                throw new EvaluationException("Expected value for " + vb.getExpressionString()
                        + " must be one of null, a fully qualified class name or "
                        + "an instance of a class which implements "
                        + ImageRenderer.class.getName());
            }
            return clazz;
        }
        return null;
    }
}
