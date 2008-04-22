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
package org.apache.myfaces.renderkit.html.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlResponseWriterImpl;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;
import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.net.URLEncoder;

/**
 * This is a utility class to render link to resources used by custom components.
 * Mostly used to avoid having to include [script src="..."][/script]
 * in the head of the pages before using a component.
 * <p/>
 * When used together with the ExtensionsFilter, this class can allow components
 * in the body of a page to emit script and stylesheet references into the page
 * head section. The relevant methods on this object simply queue the changes,
 * and when the page is complete the ExtensionsFilter calls back into this
 * class to allow it to insert the commands into the buffered response.
 * <p/>
 * This class also works with the ExtensionsFilter to allow components to
 * emit references to javascript/css/etc which are bundled in the component's
 * jar file. Special URLs are generated which the ExtensionsFilter will later
 * handle by retrieving the specified resource from the classpath.
 * <p/>
 * The special URL format is:
 * <pre>
 * {contextPath}/faces/myFacesExtensionResource/
 *    {resourceLoaderName}/{cacheKey}/{resourceURI}
 * </pre>
 * Where:
 * <ul>
 * <li> {contextPath} is the context path of the current webapp
 * <li> {resourceLoaderName} is the fully-qualified name of a class which
 * implements the ResourceLoader interface. When a browser app sends a request
 * for the specified resource, an instance of the specified ResourceLoader class
 * will be created and passed the resourceURI part of the URL for resolving to the
 * actual resource to be served back. The standard MyFaces ResourceLoader
 * implementation only serves resources for files stored beneath path
 * org/apache/myfaces/custom in the classpath but non-myfaces code can provide their
 * own ResourceLoader implementations.
 * </ul>
 *
 * @author Sylvain Vieujot (latest modification by $Author: mmarinschek $)
 * @version $Revision: 371739 373827 $ $Date: 2006-01-31 14:50:35 +0000 (Tue, 31 Jan 2006) $
 */
public class DefaultAddResource implements AddResource
{
    private static final String PATH_SEPARATOR = "/";

    protected static final Log log = LogFactory.getLog(DefaultAddResource.class);

    private static final String RESOURCES_CACHE_KEY = AddResource.class.getName() + ".CACHE_KEY";

    protected String _contextPath;

    protected StringBuffer originalResponse;

    private Set headerBeginInfo;
    private Set bodyEndInfo;
    private Set bodyOnloadInfo;


    protected boolean parserCalled = false;
    protected int headerInsertPosition = -1;
    protected int bodyInsertPosition = -1;
    protected int beforeBodyPosition = -1;
    protected int afterBodyContentInsertPosition = -1;
    protected int beforeBodyEndPosition = -1;

    private String resourceVirtualPath;

    protected DefaultAddResource()
    {
    }

    /**
     * the context path for the web-app.<br />
     * You can set the context path only once, every subsequent set will throw an SecurityException
     */
    public void setContextPath(String contextPath)
    {
        if (_contextPath != null)
        {
            throw new SecurityException("context path already set");
        }

        _contextPath = contextPath;
    }

    // Methods to add resources

    /**
     * Insert a [script src="url"] entry at the current location in the response.
     * The resource is expected to be in the classpath, at the same location as the
     * specified component + "/resource".
     * <p/>
     * Example: when customComponent is class example.Widget, and
     * resourceName is script.js, the resource will be retrieved from
     * "example/Widget/resource/script.js" in the classpath.
     */
    public void addJavaScriptHere(FacesContext context, Class myfacesCustomComponent,
                                  String resourceName) throws IOException
    {
        addJavaScriptHere(context, new MyFacesResourceHandler(myfacesCustomComponent, resourceName));
    }

    /**
     * Insert a [script src="url"] entry at the current location in the response.
     *
     * @param uri is the location of the desired resource, relative to the base
     *            directory of the webapp (ie its contextPath).
     */
    public void addJavaScriptHere(FacesContext context, String uri) throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_ELEM, null);
        writer.writeAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_TYPE_ATTR, org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        String src = context.getExternalContext().encodeResourceURL(getResourceUri(context, uri));
        writer.writeURIAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SRC_ATTR, src, null);
        writer.endElement(HTML.SCRIPT_ELEM);
    }

    public void addJavaScriptHerePlain(FacesContext context, String uri) throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_ELEM, null);
        writer.writeAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_TYPE_ATTR, org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        String src = getResourceUri(context, uri);
        writer.writeURIAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SRC_ATTR, src, null);
        writer.endElement(HTML.SCRIPT_ELEM);
    }

    /**
     * Insert a [script src="url"] entry at the current location in the response.
     *
     * @param context The current faces-context
     * @param resourceHandler is an object which specifies exactly how to build the url
     *                        that is emitted into the script tag. Code which needs to generate URLs in ways
     *                        that this class does not support by default can implement a custom ResourceHandler.
     * @throws IOException
     */
    public void addJavaScriptHere(FacesContext context, ResourceHandler resourceHandler)
            throws IOException
    {
        validateResourceHandler(resourceHandler);

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTML.SCRIPT_ELEM, null);
        writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        String src = context.getExternalContext().encodeResourceURL(
                getResourceUri(context, resourceHandler));
        writer.writeURIAttribute(HTML.SRC_ATTR, src, null);
        writer.endElement(HTML.SCRIPT_ELEM);
    }

    public void addResourceHere(FacesContext context, ResourceHandler resourceHandler)
            throws IOException
    {
        validateResourceHandler(resourceHandler);

        String path = getResourceUri(context, resourceHandler);
        ResponseWriter writer = context.getResponseWriter();
        writer.write(context.getExternalContext().encodeResourceURL(path));
    }

    /**
     * Verify that the resource handler is acceptable. Null is not
     * valid, and the getResourceLoaderClass method must return a
     * Class object whose instances implements the ResourceLoader
     * interface.
     *
     * @param resourceHandler handler to check
     */
    protected void validateResourceHandler(ResourceHandler resourceHandler)
    {
        if (resourceHandler == null)
        {
            throw new IllegalArgumentException("ResourceHandler is null");
        }
        validateResourceLoader(resourceHandler.getResourceLoaderClass());
    }

    /**
     * Given a Class object, verify that the instances of that class
     * implement the ResourceLoader interface.
     *
     * @param resourceloader loader to check
     */
    protected void validateResourceLoader(Class resourceloader)
    {
        if (!ResourceLoader.class.isAssignableFrom(resourceloader))
        {
            throw new FacesException("Class " + resourceloader.getName() + " must implement "
                    + ResourceLoader.class.getName());
        }
    }

    /**
     * Adds the given Javascript resource to the document header at the specified
     * document positioy by supplying a resourcehandler instance.
     * <p/>
     * Use this method to have full control about building the reference url
     * to identify the resource and to customize how the resource is
     * written to the response. In most cases, however, one of the convenience
     * methods on this class can be used without requiring a custom ResourceHandler
     * to be provided.
     * <p/>
     * If the script has already been referenced, it's added only once.
     * <p/>
     * Note that this method <i>queues</i> the javascript for insertion, and that
     * the script is inserted into the buffered response by the ExtensionsFilter
     * after the page is complete.
     */
    public void addJavaScriptAtPosition(FacesContext context, ResourcePosition position,
                                        ResourceHandler resourceHandler)
    {
        addJavaScriptAtPosition(context, position, resourceHandler, false);
    }

    /**
     * Insert a [script src="url"] entry into the document header at the
     * specified document position. If the script has already been
     * referenced, it's added only once.
     * <p/>
     * The resource is expected to be in the classpath, at the same location as the
     * specified component + "/resource".
     * <p/>
     * Example: when customComponent is class example.Widget, and
     * resourceName is script.js, the resource will be retrieved from
     * "example/Widget/resource/script.js" in the classpath.
     */
    public void addJavaScriptAtPosition(FacesContext context, ResourcePosition position,
                                        Class myfacesCustomComponent, String resourceName)
    {
        addJavaScriptAtPosition(context, position, new MyFacesResourceHandler(
                myfacesCustomComponent, resourceName));
    }

    public void addJavaScriptAtPositionPlain(FacesContext context, ResourcePosition position, Class myfacesCustomComponent, String resourceName)
    {
        addJavaScriptAtPosition(context, position,
                new MyFacesResourceHandler(myfacesCustomComponent, resourceName),
                false, false);
    }


    /**
     * Insert a [script src="url"] entry into the document header at the
     * specified document position. If the script has already been
     * referenced, it's added only once.
     *
     * @param defer specifies whether the html attribute "defer" is set on the
     *              generated script tag. If this is true then the browser will continue
     *              processing the html page without waiting for the specified script to
     *              load and be run.
     */
    public void addJavaScriptAtPosition(FacesContext context, ResourcePosition position,
                                        Class myfacesCustomComponent, String resourceName, boolean defer)
    {
        addJavaScriptAtPosition(context, position, new MyFacesResourceHandler(
                myfacesCustomComponent, resourceName), defer);
    }

    /**
     * Insert a [script src="url"] entry into the document header at the
     * specified document position. If the script has already been
     * referenced, it's added only once.
     *
     * @param uri is the location of the desired resource, relative to the base
     *            directory of the webapp (ie its contextPath).
     */
    public void addJavaScriptAtPosition(FacesContext context, ResourcePosition position, String uri)
    {
        addJavaScriptAtPosition(context, position, uri, false);
    }

    /**
     * Adds the given Javascript resource at the specified document position.
     * If the script has already been referenced, it's added only once.
     */
    public void addJavaScriptAtPosition(FacesContext context, ResourcePosition position, String uri,
                                        boolean defer)
    {
        addPositionedInfo(position, getScriptInstance(context, uri, defer));
    }

    public void addJavaScriptToBodyTag(FacesContext context, String javascriptEventName,
                                       String addedJavaScript)
    {
        AttributeInfo info = new AttributeInfo();
        info.setAttributeName(javascriptEventName);
        info.setAttributeValue(addedJavaScript);

        addPositionedInfo(BODY_ONLOAD, info);
    }

    /**
     * Adds the given Javascript resource at the specified document position.
     * If the script has already been referenced, it's added only once.
     */
    public void addJavaScriptAtPosition(FacesContext context, ResourcePosition position,
                                        ResourceHandler resourceHandler, boolean defer)
    {
        validateResourceHandler(resourceHandler);
        addPositionedInfo(position, getScriptInstance(context, resourceHandler, defer));
    }

    private void addJavaScriptAtPosition(FacesContext context, ResourcePosition position,
                                         ResourceHandler resourceHandler, boolean defer, boolean encodeUrl)
    {
        validateResourceHandler(resourceHandler);
        addPositionedInfo(position, getScriptInstance(context, resourceHandler, defer, encodeUrl));
    }

    /**
     * Adds the given Style Sheet at the specified document position.
     * If the style sheet has already been referenced, it's added only once.
     */
    public void addStyleSheet(FacesContext context, ResourcePosition position,
                              Class myfacesCustomComponent, String resourceName)
    {
        addStyleSheet(context, position, new MyFacesResourceHandler(myfacesCustomComponent,
                resourceName));
    }

    /**
     * Adds the given Style Sheet at the specified document position.
     * If the style sheet has already been referenced, it's added only once.
     */
    public void addStyleSheet(FacesContext context, ResourcePosition position, String uri)
    {
        addPositionedInfo(position, getStyleInstance(context, uri));
    }

    /**
     * Adds the given Style Sheet at the specified document position.
     * If the style sheet has already been referenced, it's added only once.
     */
    public void addStyleSheet(FacesContext context, ResourcePosition position,
                              ResourceHandler resourceHandler)
    {
        validateResourceHandler(resourceHandler);
        addPositionedInfo(position, getStyleInstance(context, resourceHandler));
    }

    /**
     * Adds the given Inline Style at the specified document position.
     */
    public void addInlineStyleAtPosition(FacesContext context, ResourcePosition position, String inlineStyle)
    {
        addPositionedInfo(position, getInlineStyleInstance(inlineStyle));
    }

    /**
     * Adds the given Inline Script at the specified document position.
     */
    public void addInlineScriptAtPosition(FacesContext context, ResourcePosition position,
                                          String inlineScript)
    {
        addPositionedInfo(position, getInlineScriptInstance(inlineScript));
    }

    public String getResourceUri(FacesContext context, Class myfacesCustomComponent,
                                 String resource, boolean withContextPath)
    {
        return getResourceUri(context,
                new MyFacesResourceHandler(myfacesCustomComponent, resource), withContextPath);
    }

    public String getResourceUri(FacesContext context, Class myfacesCustomComponent, String resource)
    {
        return getResourceUri(context, new MyFacesResourceHandler(myfacesCustomComponent, resource));
    }

    /**
     * Get the Path used to retrieve an resource.
     */
    public String getResourceUri(FacesContext context, ResourceHandler resourceHandler)
    {
        String uri = resourceHandler.getResourceUri(context);
        if (uri == null)
        {
            return getResourceUri(context, resourceHandler.getResourceLoaderClass(), true);
        }
        return getResourceUri(context, resourceHandler.getResourceLoaderClass(), true) + uri;
    }

    /**
     * Get the Path used to retrieve an resource.
     */
    public String getResourceUri(FacesContext context, ResourceHandler resourceHandler,
                                 boolean withContextPath)
    {
        String uri = resourceHandler.getResourceUri(context);
        if (uri == null)
        {
            return getResourceUri(context, resourceHandler.getResourceLoaderClass(),
                    withContextPath);
        }
        return getResourceUri(context, resourceHandler.getResourceLoaderClass(), withContextPath)
                + uri;
    }

    /**
     * Get the Path used to retrieve an resource.
     */
    public String getResourceUri(FacesContext context, String uri)
    {
        return getResourceUri(context, uri, true);
    }

    /**
     * Get the Path used to retrieve an resource.
     */
    public String getResourceUri(FacesContext context, String uri, boolean withContextPath)
    {
        if (withContextPath)
        {
            return context.getApplication().getViewHandler().getResourceURL(context, uri);
        }
        return uri;
    }

    /**
     * Get the Path used to retrieve an resource.
     * @param context current faces-context
     * @param resourceLoader resourceLoader
     * @param withContextPath use the context-path of the web-app when accessing the resources
     *
     * @return the URI of the resource
     */
    protected String getResourceUri(FacesContext context, Class resourceLoader,
                                    boolean withContextPath)
    {
        StringBuffer sb = new StringBuffer(200);
        sb.append(MyfacesConfig.getCurrentInstance(context.getExternalContext()).getResourceVirtualPath());
        sb.append(PATH_SEPARATOR);
        sb.append(resourceLoader.getName());
        sb.append(PATH_SEPARATOR);
        sb.append(getCacheKey(context));
        sb.append(PATH_SEPARATOR);
        return getResourceUri(context, sb.toString(), withContextPath);
    }

    /**
     * Return a value used in the {cacheKey} part of a generated URL for a
     * resource reference.
     * <p/>
     * Caching in browsers normally works by having files served to them
     * include last-modified and expiry-time http headers. Until the expiry
     * time is reached, a browser will silently use its cached version. After
     * the expiry time, it will send a "get if modified since {time}" message,
     * where {time} is the last-modified header from the version it has cached.
     * <p/>
     * Unfortunately this scheme only works well for resources represented as
     * plain files on disk, where the webserver can easily and efficiently see
     * the last-modified time of the resource file. When that query has to be
     * processed by a servlet that doesn't scale well, even when it is possible
     * to determine the resource's last-modified date from servlet code.
     * <p/>
     * Fortunately, for the AddResource class a static resource is only ever
     * accessed because a URL was embedded by this class in a dynamic page.
     * This makes it possible to implement caching by instead marking every
     * resource served with a very long expiry time, but forcing the URL that
     * points to the resource to change whenever the old cached version becomes
     * invalid; the browser effectively thinks it is fetching a different
     * resource that it hasn't seen before. This is implemented by embedding
     * a "cache key" in the generated URL.
     * <p/>
     * Rather than using the actual modification date of a resource as the
     * cache key, we simply use the webapp deployment time. This means that all
     * data cached by browsers will become invalid after a webapp deploy (all
     * the urls to the resources change). It also means that changes that occur
     * to a resource <i>without</i> a webapp redeploy will not be seen by browsers.
     *
     * @param context the current faces-context
     *
     * @return the key for caching
     */
    protected long getCacheKey(FacesContext context)
    {
		// cache key is hold in application scope so it is recreated on redeploying the webapp.
        Map applicationMap = context.getExternalContext().getApplicationMap();
        Long cacheKey = (Long) applicationMap.get(RESOURCES_CACHE_KEY);
        if (cacheKey == null)
        {
            cacheKey = new Long(System.currentTimeMillis() / 100000);
            applicationMap.put(RESOURCES_CACHE_KEY, cacheKey);
        }
        return cacheKey.longValue();
    }

    public boolean isResourceUri(ServletContext servletContext, HttpServletRequest request)
    {

        String path;
        if (_contextPath != null)
        {
            path = _contextPath + getResourceVirtualPath(servletContext);
        }
        else
        {
            path = getResourceVirtualPath(servletContext);
        }

        //fix for TOMAHAWK-660; to be sure this fix is backwards compatible, the
        //encoded context-path is only used as a first option to check for the prefix
        //if we're sure this works for all cases, we can directly return the first value
        //and not double-check.
        try
        {
            if(request.getRequestURI().startsWith(URLEncoder.encode(path,"UTF-8")))
                return true;
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("Unsupported encoding UTF-8 used",e);

        }

        return request.getRequestURI().startsWith(path);
    }

    private String getResourceVirtualPath(ServletContext servletContext)
    {
        if(resourceVirtualPath == null)
        {
            resourceVirtualPath = servletContext.getInitParameter(MyfacesConfig.INIT_PARAM_RESOURCE_VIRTUAL_PATH);

            if(resourceVirtualPath == null)
            {
                resourceVirtualPath = MyfacesConfig.INIT_PARAM_RESOURCE_VIRTUAL_PATH_DEFAULT;
            }
        }

        return resourceVirtualPath;
    }

    private Class getClass(String className) throws ClassNotFoundException
    {
        Class clazz = ClassUtils.classForName(className);
        validateResourceLoader(clazz);
        return clazz;
    }

    public void serveResource(ServletContext context, HttpServletRequest request,
                              HttpServletResponse response) throws IOException
    {
        String pathInfo = request.getPathInfo();
        String uri = request.getContextPath() + request.getServletPath()
                + (pathInfo == null ? "" : pathInfo);
        String classNameStartsAfter = getResourceVirtualPath(context) + '/';

        int posStartClassName = uri.indexOf(classNameStartsAfter) + classNameStartsAfter.length();
        int posEndClassName = uri.indexOf(PATH_SEPARATOR, posStartClassName);
        String className = uri.substring(posStartClassName, posEndClassName);
        int posEndCacheKey = uri.indexOf(PATH_SEPARATOR, posEndClassName + 1);
        String resourceUri = null;
        if (posEndCacheKey + 1 < uri.length())
        {
            resourceUri = uri.substring(posEndCacheKey + 1);
        }
        try
        {
            Class resourceLoader = getClass(className);
            validateResourceLoader(resourceLoader);
            ((ResourceLoader) resourceLoader.newInstance()).serveResource(context, request,
                    response, resourceUri);

            // Do not call response.flushBuffer buffer here. There is no point, as if there
            // ever were header data to write, this would fail as we have already written
            // the response body. The only point would be to flush the output stream, but
            // that will happen anyway when the servlet container closes the socket.
            //
            // In addition, flushing could fail here; it appears that Microsoft IE
            // hasthe habit of hard-closing its socket as soon as it has received a complete
            // gif file, rather than letting the server close it. The container will hopefully
            // silently ignore exceptions on close.
        }
        catch (ResourceLoader.ClosedSocketException e)
        {
        	// The ResourceLoader was unable to send the data because the client closed
        	// the socket on us; just ignore.
        }
        catch (ClassNotFoundException e)
        {
            log.error("Could not find class for name: " + className, e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Could not find resourceloader class for name: " + className);
        }
        catch (InstantiationException e)
        {
            log.error("Could not instantiate class for name: " + className, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Could not instantiate resourceloader class for name: " + className);
        }
        catch (IllegalAccessException e)
        {
            log.error("Could not access class for name: " + className, e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Could not access resourceloader class for name: " + className);
        }
        catch (Throwable e)
        {
            log.error("Error while serving resource: " + resourceUri + ", message : " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // Positioned stuffs

    protected Set getHeaderBeginInfos()
    {
        if (headerBeginInfo == null)
        {
            headerBeginInfo = new LinkedHashSet();
        }
        return headerBeginInfo;
    }

    protected Set getBodyEndInfos()
    {
        if (bodyEndInfo == null)
        {
            bodyEndInfo = new LinkedHashSet();
        }
        return bodyEndInfo;
    }

    protected Set getBodyOnloadInfos()
    {
        if (bodyOnloadInfo == null)
        {
            bodyOnloadInfo = new LinkedHashSet();
        }
        return bodyOnloadInfo;
    }

    private void addPositionedInfo(ResourcePosition position, PositionedInfo info)
    {
        if (HEADER_BEGIN.equals(position))
        {
            Set set = getHeaderBeginInfos();
            set.add(info);
        }
        else if (BODY_END.equals(position))
        {
            Set set = getBodyEndInfos();
            set.add(info);

        }
        else if (BODY_ONLOAD.equals(position))
        {
            Set set = getBodyOnloadInfos();
            set.add(info);
        }
    }

    public boolean hasHeaderBeginInfos()
    {
        return headerBeginInfo != null;
    }

    /**
     * Parses the response to mark the positions where code will be inserted
     */
    public void parseResponse(HttpServletRequest request, String bufferedResponse,
                              HttpServletResponse response)
    {

        originalResponse = new StringBuffer(bufferedResponse);

        ParseCallbackListener l = new ParseCallbackListener();
        ReducedHTMLParser.parse(originalResponse, l);

        headerInsertPosition = l.getHeaderInsertPosition();
        bodyInsertPosition = l.getBodyInsertPosition();
        beforeBodyPosition = l.getBeforeBodyPosition();
        afterBodyContentInsertPosition = l.getAfterBodyContentInsertPosition();
        beforeBodyEndPosition = l.getAfterBodyEndPosition() - 7;  // 7, which is the length of </body>

        parserCalled = true;
    }

    /**
     * Writes the javascript code necessary for myfaces in every page, just befode the closing &lt;/body&gt; tag
     */
    public void writeMyFacesJavascriptBeforeBodyEnd(HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException
    {
        if (!parserCalled)
        {
            throw new IOException("Method parseResponse has to be called first");
        }

        if (beforeBodyEndPosition >= 0)
        {
            String myFacesJavascript = (String) request.getAttribute("org.apache.myfaces.myFacesJavascript");
            if (myFacesJavascript != null)
            {
                originalResponse.insert(beforeBodyEndPosition, myFacesJavascript);
            }
            else
            {
                log.warn("MyFaces special javascript could not be retrieved from request-map.");
            }
        }
    }

    /**
     * Add the resources to the &lt;head&gt; of the page.
     * If the head tag is missing, but the &lt;body&gt; tag is present, the head tag is added.
     * If both are missing, no resource is added.
     * <p/>
     * The ordering is such that the user header CSS & JS override the MyFaces' ones.
     */
    public void writeWithFullHeader(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException
    {
        if (!parserCalled)
        {
            throw new IOException("Method parseResponse has to be called first");
        }

        boolean addHeaderTags = false;

        if (headerInsertPosition == -1)
        {
            if (beforeBodyPosition != -1)
            {
                // The input html has a body start tag, but no head tags. We therefore
                // need to insert head start/end tags for our content to live in.
                addHeaderTags = true;
                headerInsertPosition = beforeBodyPosition;
            }
            else
            {
                // neither head nor body tags in the input
                log.warn("Response has no <head> or <body> tag:\n" + originalResponse);
            }
        }

        ResponseWriter writer = new HtmlResponseWriterImpl(response.getWriter(),
                HtmlRendererUtils.selectContentType(request.getHeader("accept")),
                response.getCharacterEncoding());

        if (afterBodyContentInsertPosition >= 0)
        {
            // insert all the items that want to go immediately after the <body> tag.
            HtmlBufferResponseWriterWrapper writerWrapper = HtmlBufferResponseWriterWrapper
                    .getInstance(writer);

            for (Iterator i = getBodyEndInfos().iterator(); i.hasNext();)
            {
                writerWrapper.write("\n");

                PositionedInfo positionedInfo = (PositionedInfo) i.next();

                if (!(positionedInfo instanceof WritablePositionedInfo))
                    throw new IllegalStateException("positionedInfo of type : "
                            + positionedInfo.getClass().getName());
                ((WritablePositionedInfo) positionedInfo).writePositionedInfo(response,
                        writerWrapper);
            }

            originalResponse.insert(headerInsertPosition, writerWrapper.toString());
        }

        if (bodyInsertPosition > 0)
        {
            StringBuffer buf = new StringBuffer();
            Set bodyInfos = getBodyOnloadInfos();
            if (bodyInfos.size() > 0)
            {
                int i = 0;
                for (Iterator it = getBodyOnloadInfos().iterator(); it.hasNext();)
                {
                    AttributeInfo positionedInfo = (AttributeInfo) it.next();
                    if (i == 0)
                    {
                        buf.append(positionedInfo.getAttributeName());
                        buf.append("=\"");
                    }
                    buf.append(positionedInfo.getAttributeValue());

                    i++;
                }

                buf.append("\"");
                originalResponse.insert(bodyInsertPosition - 1, " " + buf.toString());
            }
        }

        if (headerInsertPosition >= 0)
        {
            HtmlBufferResponseWriterWrapper writerWrapper = HtmlBufferResponseWriterWrapper
                    .getInstance(writer);

            if (addHeaderTags)
                writerWrapper.write("<head>");

            for (Iterator i = getHeaderBeginInfos().iterator(); i.hasNext();)
            {
                writerWrapper.write("\n");

                PositionedInfo positionedInfo = (PositionedInfo) i.next();

                if (!(positionedInfo instanceof WritablePositionedInfo))
                    throw new IllegalStateException("positionedInfo of type : "
                            + positionedInfo.getClass().getName());
                ((WritablePositionedInfo) positionedInfo).writePositionedInfo(response,
                        writerWrapper);
            }

            if (addHeaderTags)
                writerWrapper.write("</head>");

            originalResponse.insert(headerInsertPosition, writerWrapper.toString());

        }

    }

    /**
     * Writes the response
     */
    public void writeResponse(HttpServletRequest request,
                              HttpServletResponse response) throws IOException
    {
        ResponseWriter writer = new HtmlResponseWriterImpl(response.getWriter(), HtmlRendererUtils
                .selectContentType(request.getHeader("accept")),
                response.getCharacterEncoding());
        writer.write(originalResponse.toString());
    }

    private PositionedInfo getStyleInstance(FacesContext context, ResourceHandler resourceHandler)
    {
        return new StylePositionedInfo(getResourceUri(context, resourceHandler));
    }

    private PositionedInfo getScriptInstance(FacesContext context, ResourceHandler resourceHandler,
                                             boolean defer)
    {
        return new ScriptPositionedInfo(getResourceUri(context, resourceHandler), defer);
    }

    private PositionedInfo getScriptInstance(FacesContext context, ResourceHandler resourceHandler,
                                             boolean defer, boolean encodeURL)
    {
           return new ScriptPositionedInfo(getResourceUri(context, resourceHandler), defer, encodeURL);
    }

    private PositionedInfo getStyleInstance(FacesContext context, String uri)
    {
        return new StylePositionedInfo(getResourceUri(context, uri));
    }

    protected PositionedInfo getScriptInstance(FacesContext context, String uri, boolean defer)
    {
        return new ScriptPositionedInfo(getResourceUri(context, uri), defer);
    }

    private PositionedInfo getInlineScriptInstance(String inlineScript)
    {
        return new InlineScriptPositionedInfo(inlineScript);
    }

    private PositionedInfo getInlineStyleInstance(String inlineStyle)
    {
        return new InlineStylePositionedInfo(inlineStyle);
    }

    protected interface PositionedInfo
    {
    }

    protected static class AttributeInfo implements PositionedInfo
    {
        private String _attributeName;
        private String _attributeValue;

        public String getAttributeName()
        {
            return _attributeName;
        }

        public void setAttributeName(String attributeName)
        {
            _attributeName = attributeName;
        }

        public String getAttributeValue()
        {
            return _attributeValue;
        }

        public void setAttributeValue(String attributeValue)
        {
            _attributeValue = attributeValue;
        }
    }

    protected interface WritablePositionedInfo extends PositionedInfo
    {
        public abstract void writePositionedInfo(HttpServletResponse response, ResponseWriter writer)
                throws IOException;
    }

    private abstract class AbstractResourceUri
    {
        protected final String _resourceUri;

        protected AbstractResourceUri(String resourceUri)
        {
            _resourceUri = resourceUri;
        }

        public int hashCode()
        {
            return _resourceUri.hashCode();
        }

        public boolean equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (obj == this)
            {
                return true;
            }
            if (obj instanceof AbstractResourceUri)
            {
                AbstractResourceUri other = (AbstractResourceUri) obj;
                return _resourceUri.equals(other._resourceUri);
            }
            return false;
        }

        protected String getResourceUri()
        {
            return _resourceUri;
        }
    }

    private class StylePositionedInfo extends AbstractResourceUri implements WritablePositionedInfo
    {
        protected StylePositionedInfo(String resourceUri)
        {
            super(resourceUri);
        }

        public void writePositionedInfo(HttpServletResponse response, ResponseWriter writer)
                throws IOException
        {
            writer.startElement(HTML.LINK_ELEM, null);
            writer.writeAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.REL_ATTR, org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.STYLESHEET_VALUE, null);
            writer.writeAttribute(HTML.HREF_ATTR, response.encodeURL(this.getResourceUri()), null);
            writer.writeAttribute(HTML.TYPE_ATTR, HTML.STYLE_TYPE_TEXT_CSS, null);
            writer.endElement(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.LINK_ELEM);
        }
    }

    private class ScriptPositionedInfo extends AbstractResourceUri implements
            WritablePositionedInfo
    {
        protected final boolean _defer;
        protected final boolean _encode;

        public ScriptPositionedInfo(String resourceUri, boolean defer)
        {
            this(resourceUri, defer, true);
        }

        public ScriptPositionedInfo(String resourceUri, boolean defer, boolean encodeUrl)
        {
            super(resourceUri);
            _defer = defer;
            _encode = encodeUrl;
        }

        public int hashCode()
        {
            return new HashCodeBuilder()
                .append(this.getResourceUri())
                .append(_defer)
                .append(_encode)
                .toHashCode();
        }

        public boolean equals(Object obj)
        {
            if (super.equals(obj))
            {
                if (obj instanceof ScriptPositionedInfo)
                {
                    ScriptPositionedInfo other = (ScriptPositionedInfo) obj;
                    return new EqualsBuilder()
                        .append(_defer, other._defer)
                        .append(_encode, other._encode)
                        .isEquals();
                }
            }
            return false;
        }

        public void writePositionedInfo(HttpServletResponse response, ResponseWriter writer)
                throws IOException
        {
            writer.startElement(HTML.SCRIPT_ELEM, null);
            writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
            if (_encode)
            {
                writer.writeAttribute(HTML.SRC_ATTR, response.encodeURL(this.getResourceUri()), null);
            }
            else
            {
                writer.writeAttribute(HTML.SRC_ATTR, this.getResourceUri(), null);
            }

            if (_defer)
            {
                writer.writeAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_ELEM_DEFER_ATTR, "true", null);
            }
            writer.endElement(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_ELEM);
        }
    }

    private abstract class InlinePositionedInfo implements WritablePositionedInfo
    {
        private final String _inlineValue;

        protected InlinePositionedInfo(String inlineValue)
        {
            _inlineValue = inlineValue;
        }

        public String getInlineValue()
        {
            return _inlineValue;
        }

        public int hashCode()
        {
            return new HashCodeBuilder().append(_inlineValue).toHashCode();
        }

        public boolean equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (obj == this)
            {
                return true;
            }
            if (obj instanceof InlinePositionedInfo)
            {
                InlinePositionedInfo other = (InlinePositionedInfo) obj;
                return new EqualsBuilder().append(_inlineValue, other._inlineValue).isEquals();
            }
            return false;
        }
    }

    private class InlineScriptPositionedInfo extends InlinePositionedInfo
    {
        protected InlineScriptPositionedInfo(String inlineScript)
        {
            super(inlineScript);
        }

        public void writePositionedInfo(HttpServletResponse response, ResponseWriter writer)
                throws IOException
        {
            writer.startElement(HTML.SCRIPT_ELEM, null);
            writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
            writer.writeText(getInlineValue(), null);
            writer.endElement(HTML.SCRIPT_ELEM);
        }
    }

    private class InlineStylePositionedInfo extends InlinePositionedInfo
    {
        protected InlineStylePositionedInfo(String inlineStyle)
        {
            super(inlineStyle);
        }

        public void writePositionedInfo(HttpServletResponse response, ResponseWriter writer)
                throws IOException
        {
            writer.startElement(HTML.STYLE_ELEM, null);
            writer.writeAttribute(HTML.REL_ATTR, HTML.STYLESHEET_VALUE, null);
            writer.writeAttribute(HTML.TYPE_ATTR, HTML.STYLE_TYPE_TEXT_CSS, null);
            writer.writeText(getInlineValue(), null);
            writer.endElement(HTML.STYLE_ELEM);
        }
    }

    protected static class ParseCallbackListener implements CallbackListener
    {
        private int headerInsertPosition = -1;
        private int bodyInsertPosition = -1;
        private int beforeBodyPosition = -1;
        private int afterBodyContentInsertPosition = -1;
        private int afterBodyEndPosition = -1;

        public ParseCallbackListener()
        {
        }

        public void openedStartTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                beforeBodyPosition = charIndex;
            }
        }

        public void closedStartTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.HEAD_TAG)
            {
                headerInsertPosition = charIndex;
            }
            else if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                bodyInsertPosition = charIndex;
            }
        }

        public void openedEndTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                afterBodyContentInsertPosition = charIndex;
            }
        }

        public void closedEndTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                afterBodyEndPosition = charIndex;
            }
        }

        public void attribute(int charIndex, int tagIdentifier, String key, String value)
        {
        }

        public int getHeaderInsertPosition()
        {
            return headerInsertPosition;
        }

        public int getBodyInsertPosition()
        {
            return bodyInsertPosition;
        }

        public int getBeforeBodyPosition()
        {
            return beforeBodyPosition;
        }

        public int getAfterBodyContentInsertPosition()
        {
            return afterBodyContentInsertPosition;
        }

        public int getAfterBodyEndPosition()
        {
            return afterBodyEndPosition;
        }
    }

    public boolean requiresBuffer()
    {
        return true;
    }

    public void responseStarted()
    {
    }

    public void responseFinished()
    {
    }
}
