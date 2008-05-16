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

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.component.html.util.ParameterResourceHandler;
import org.apache.myfaces.custom.dynamicResources.ResourceContext;
import org.apache.myfaces.custom.dynamicResources.ResourceRenderer;
import org.apache.myfaces.custom.dynamicResources.SimpleResourceContext;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlLinkRenderer;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.renderkit.html.util.ResourceLoader;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.OutputLinkDynamicRenderer"
 *
 * @author Sylvain Vieujot
 * @version $Revision$ $Date$
 */
public class OutputLinkDynamicRenderer extends HtmlLinkRenderer implements ResourceLoader
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.OutputLinkDynamicRenderer";

    private static final class ResourceResponseStream extends ResponseStream
    {
        private final OutputStream _out;

        private ResourceResponseStream(OutputStream out)
        {
            _out = out;
        }

        public void close() throws IOException
        {
            _out.flush();
            _out.close();
        }

        public void flush() throws IOException
        {
            _out.flush();
        }

        public void write(byte[] b, int off, int len) throws IOException
        {
            _out.write(b, off, len);
        }

        public void write(byte[] b) throws IOException
        {
            _out.write(b);
        }

        public void write(int b) throws IOException
        {
            _out.write(b);
        }
    }

    private static final String RENDERER_PARAM = "_renderer";

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(context, component, OutputLinkDynamic.class);

        OutputLinkDynamic outputLinkDynamic = (OutputLinkDynamic) component;
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTML.ANCHOR_ELEM, outputLinkDynamic);
        HtmlRendererUtils.writeIdIfNecessary(writer, outputLinkDynamic, context);
        HtmlRendererUtils.renderHTMLAttributes(writer, outputLinkDynamic,
                                               HTML.ANCHOR_PASSTHROUGH_ATTRIBUTES);

        Map params = getParameterMap(context, component);

        Class resourceRendererClass = outputLinkDynamic.getResourceRendererClass();
        if (resourceRendererClass == null)
        {
            throw new FacesException("No resourceRendererClass defined for component "
                                     + component.getId());
        }
        params.put(RENDERER_PARAM, resourceRendererClass.getName());

        AddResource addResource = AddResourceFactory.getInstance(context);
        String url = addResource.getResourceUri(context, new ParameterResourceHandler(this
                .getClass(), params));
        writer.writeAttribute(HTML.HREF_ATTR, url, null);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
            ResponseWriter writer = facesContext.getResponseWriter();
        // force separate end tag
        writer.writeText("", null);
        writer.endElement(HTML.ANCHOR_ELEM);
    }

    protected Map getParameterMap(FacesContext context, UIComponent component)
    {
        Map result = new HashMap();
        for (Iterator iter = component.getChildren().iterator(); iter.hasNext();)
        {
            UIComponent child = (UIComponent) iter.next();
            if (child instanceof UIParameter)
            {
                UIParameter uiparam = (UIParameter) child;
                Object value = uiparam.getValue();
                if (value != null)
                {
                    result.put(uiparam.getName(), value);
                }
            }
        }
        return result;
    }

    public void decode(FacesContext facesContext, UIComponent component)
    {
        super.decode(facesContext, component);
    }

    /**
     * @throws IOException
     * @see org.apache.myfaces.renderkit.html.util.ResourceLoader#serveResource(javax.servlet.ServletContext, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
     */
    public void serveResource(ServletContext context, HttpServletRequest request,
                              HttpServletResponse response, String resourceUri) throws IOException
    {
        FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(getLifecycleId(context));
        FacesContext facesContext = facesContextFactory.getFacesContext(context, request, response,
                                                                        lifecycle);
        facesContext.setResponseStream(new ResourceResponseStream(response.getOutputStream()));
        try
        {
            Map requestMap = facesContext.getExternalContext().getRequestParameterMap();
            Object rendererValue = requestMap.get(RENDERER_PARAM);
            if (rendererValue == null)
            {
                throw new FacesException("no resource renderer defined.");
            }
            try
            {
                Class rendererClass = ClassUtils.classForName(rendererValue.toString());
                if (!ResourceRenderer.class.isAssignableFrom(rendererClass))
                {
                    throw new FacesException("Resource renderer class [" + rendererValue
                                             + "] does not implement " + ResourceRenderer.class.getName());
                }
                try
                {
                    ResourceRenderer resourceRenderer = (ResourceRenderer) rendererClass.newInstance();
                    renderResource(resourceRenderer, facesContext);
                }
                catch (InstantiationException e)
                {
                    throw new FacesException("could not instantiate resource renderer class "
                                             + rendererValue + " : " + e.getMessage(), e);
                }
                catch (IllegalAccessException e)
                {
                    throw new FacesException("could not instantiate resource renderer class "
                                             + rendererValue + " : " + e.getMessage(), e);
                }
                catch (Exception e)
                {
                    throw new FacesException("could not renderer resource "
                                             + rendererValue + " : " + e.getMessage(), e);
                }
            }
            catch (ClassNotFoundException e)
            {
                throw new FacesException("image renderer class not found: " + e.getMessage(), e);
            }
            facesContext.getResponseStream().close();
        }
        finally
        {
            facesContext.release();
        }
    }

    protected void renderResource(ResourceRenderer resourceRenderer, FacesContext facesContext)
            throws Exception
    {
            ResourceContext resourceContext = createResourceContext(facesContext);

            resourceRenderer.setContext(facesContext, resourceContext);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext()
                .getResponse();


        int contentLength = resourceRenderer.getContentLength();
        if( contentLength >0 )
        {
            response.setContentLength(contentLength);
        }

        String contentType = resourceRenderer.getContentType();
        if (contentType != null && contentType.length() > 0 )
        {
            response.setContentType(contentType);
        }

        ResponseStream out = facesContext.getResponseStream();
        try
        {
                resourceRenderer.renderResource( out );
        }
        finally
        {
            out.close();
            facesContext.responseComplete();
        }
    }

    protected ResourceContext createResourceContext(FacesContext facesContext)
    {
        ExternalContext externalContext = facesContext.getExternalContext();
        final Map requestMap = externalContext.getRequestParameterMap();
        return new SimpleResourceContext(requestMap);
    }

    private String getLifecycleId(ServletContext context)
    {
        String lifecycleId = context.getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);
        return lifecycleId != null ? lifecycleId : LifecycleFactory.DEFAULT_LIFECYCLE;
    }
}
