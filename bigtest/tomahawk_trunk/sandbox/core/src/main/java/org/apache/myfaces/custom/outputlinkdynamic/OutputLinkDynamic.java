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
package org.apache.myfaces.custom.outputlinkdynamic;

import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.dynamicResources.ResourceRenderer;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * @JSFComponent
 *   name = "s:outputLinkDynamic"
 *   tagClass = "org.apache.myfaces.custom.outputlinkdynamic.OutputLinkDynamicTag"
 *   
 * @author Sylvain Vieujot (latest modification by $Author$)
 *
 * @version $Revision$ $Date$
 * 
 * Warning, this component is far from ready.
 * It's more a proof of concept right now.
 * TODO : Remove the need to include .get for the last part of the method expressions : getBytesMethod="#{graphicImageDynamicBean.upImage.getBytes}"
 * TODO : Make a similar download component to download files 
 * TODO : Use shorter URLs
 */

public class OutputLinkDynamic extends HtmlOutputLink
{
    
    public static final String COMPONENT_TYPE = "org.apache.myfaces.OutputLinkDynamic";
    private static final String DEFAULT_RENDERER_TYPE = OutputLinkDynamicRenderer.RENDERER_TYPE;

    public OutputLinkDynamic()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    private Class _resourceRendererClass;

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = _resourceRendererClass;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _resourceRendererClass = (Class) values[1];
    }

    public void setImageRendererClass(Class resourceRendererClass)
    {
        if (resourceRendererClass != null && !ResourceRenderer.class.isAssignableFrom(resourceRendererClass))
        {
            throw new IllegalArgumentException(
                    "resourceRendererClass must be null or a class which implements "
                            + ResourceRenderer.class.getName());
        }
        _resourceRendererClass = resourceRendererClass;
    }

    public Class getResourceRendererClass()
    {
        if (_resourceRendererClass != null)
        {
            return _resourceRendererClass;
        }
        ValueBinding vb = getValueBinding("resourceRendererClass");
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
                    throw new EvaluationException("Could not load resourceRendererClass for "
                            + vb.getExpressionString(), e);
                }
            }
            if (!ResourceRenderer.class.isAssignableFrom(clazz))
            {
                throw new EvaluationException("Expected value for " + vb.getExpressionString()
                        + " must be one of null, a fully qualified class name or "
                        + "an instance of a class which implements "
                        + ResourceRenderer.class.getName());
            }
            return clazz;
        }
        return null;
    }
}
