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
package org.apache.myfaces.component.html.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.net.URLEncoder;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.MyFacesResourceHandler;
import org.apache.myfaces.renderkit.html.util.ResourceHandler;
import org.apache.myfaces.renderkit.html.util.ResourceLoader;
import org.apache.myfaces.renderkit.html.util.ResourcePosition;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;
import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;

/**
 * This is a utility class to render link to resources used by custom components.
 * Mostly used to avoid having to include [script src="..."][/script]
 * in the head of the pages before using a component.
 * <p>
 * The special URL format is:
 * <pre>
 * {contextPath}/faces/myFacesExtensionResource/
 *    {resourceLoaderName}/{cacheKey}/{resourceURI}
 * </pre>
 * Where:
 * <ul>
 * <li> {contextPath} is the context path of the current webapp
 * <li> {resourceLoaderName} is the fully-qualified name of a class which
 *  implements the ResourceLoader interface. When a browser app sends a request
 *  for the specified resource, an instance of the specified ResourceLoader class
 *  will be created and passed the resourceURI part of the URL for resolving to the
 *  actual resource to be served back. The standard MyFaces ResourceLoader
 *  implementation only serves resources for files stored beneath path
 *  org/apache/myfaces/custom in the classpath but non-myfaces code can provide their
 *  own ResourceLoader implementations.
 * </ul>
 *
 * @author Mario Ivankovits (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StreamingAddResource implements AddResource
{
    /**
     * central place where all request store their "to be added" stylesheets
     */
    private final static Map headerInfos = new HashMap();

    /**
     * request counter
     */
    private static long REQUEST_ID_COUNTER = 0;

    /**
     * own request
     */
    private Long requestId;

    /**
     * own header infos - e.g holds the "to be added" stylesheets and a destroy time
     */
    private HeaderInfoEntry headerInfoEntry;

    /**
     * helper to determines if the resource has already been added
     */
    private Set alreadySeenResources = new TreeSet();

    private static final String PATH_SEPARATOR = "/";

    protected static final Log log = LogFactory.getLog(StreamingAddResource.class);
	protected static final Log logSend = LogFactory.getLog(StreamingAddResource.class.getName() + ".SEND");

    private static final String RESOURCE_VIRTUAL_PATH = "/faces/myFacesExtensionResource";

    private static final String RESOURCES_CACHE_KEY = AddResource.class.getName() + ".CACHE_KEY";

    protected String _contextPath;
    private String resourceVirtualPath;

    public static class HeaderInfoEntry
    {
        private final long destroyTime = System.currentTimeMillis() + (1000 * 60); // one minute;
        private final List addedInfos = new ArrayList(10);
        private volatile boolean requestDone = false;

        protected HeaderInfoEntry()
        {
        }

        protected boolean isDestroyable(long now)
        {
            return destroyTime < now;
        }

        protected void addInfo(StreamablePositionedInfo positionedInfo)
        {
            synchronized (addedInfos)
            {
                addedInfos.add(positionedInfo);
                addedInfos.notifyAll();
            }
        }

        protected StreamablePositionedInfo fetchInfo() throws InterruptedException
        {
            synchronized (addedInfos)
            {
                while (addedInfos.size() < 1 && !requestDone)
                {
                    addedInfos.wait(100);
                }
                if (addedInfos.size() < 1)
                {
                    // request done
                    return null;
                }

                return (StreamablePositionedInfo) addedInfos.remove(0);
            }
        }

        protected void setRequestDone()
        {
            requestDone = true;
        }
    }

    private static class CleanupThread implements Runnable
    {
        // how many entries should be removed per run
        private final static int CHECKS_PER_RUN = 10;

        // but never reach this maximum
        private final static int CACHE_LIMIT = 1000;

        public void run()
        {
            while (!Thread.interrupted())
            {
                checkMap();

                try
                {
                    Thread.sleep(1000 * 30); // check every 30 sek
                }
                catch (InterruptedException e)
                {
                    // ignore
                }
            }
        }

        private void checkMap()
        {
            synchronized (headerInfos)
            {
                long now = System.currentTimeMillis();

                int checkNo = 0;
                Iterator iterEntries = headerInfos.entrySet().iterator();
                while (iterEntries.hasNext() && !Thread.currentThread().isInterrupted())
                {
                    checkNo++;
                    if (headerInfos.size() < CACHE_LIMIT && checkNo > CHECKS_PER_RUN)
                    {
                        return;
                    }
                    Map.Entry entry = (Map.Entry) iterEntries.next();
                    HeaderInfoEntry headerInfoEntry = (HeaderInfoEntry) entry.getValue();
                    if (headerInfoEntry.isDestroyable(now))
                    {
                        iterEntries.remove();
                    }
                }
            }
        }
    }

    static
    {
        Thread cleanupThread = new Thread(new CleanupThread(), "StreamingAddResource.CleanupThread");
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    public StreamingAddResource()
    {
    }

    public static HeaderInfoEntry getHeaderInfo(Long requestId)
    {
        synchronized (headerInfos)
        {
            return (HeaderInfoEntry) headerInfos.get(requestId);
        }
    }

    public static void removeHeaderInfo(Long requestId)
    {
        synchronized (headerInfos)
        {
            headerInfos.remove(requestId);
        }
    }

    // Methods to add resources

    public void setContextPath(String contextPath)
    {
        _contextPath = contextPath;
    }

    /**
     * Insert a [script src="url"] entry at the current location in the response.
     * The resource is expected to be in the classpath, at the same location as the
     * specified component + "/resource".
     * <p>
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
     * directory of the webapp (ie its contextPath).
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
     * @param context
     *
     * @param resourceHandler is an object which specifies exactly how to build the url
     * that is emitted into the script tag. Code which needs to generate URLs in ways
     * that this class does not support by default can implement a custom ResourceHandler.
     *
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
     * @param resourceHandler
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
     * @param resourceloader
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
     * <p>
     * Use this method to have full control about building the reference url
     * to identify the resource and to customize how the resource is
     * written to the response. In most cases, however, one of the convenience
     * methods on this class can be used without requiring a custom ResourceHandler
     * to be provided.
     * <p>
     * If the script has already been referenced, it's added only once.
     * <p>
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
     * <p>
     * The resource is expected to be in the classpath, at the same location as the
     * specified component + "/resource".
     * <p>
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
     * generated script tag. If this is true then the browser will continue
     * processing the html page without waiting for the specified script to
     * load and be run.
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
     * directory of the webapp (ie its contextPath).
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
        WritablePositionedInfo info = (WritablePositionedInfo) getScriptInstance(context, uri, defer);
        if (checkAlreadyAdded(info))
        {
            return;
        }
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try
        {
            info.writePositionedInfo(response, context.getResponseWriter());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addJavaScriptToBodyTag(FacesContext context, String javascriptEventName,
                                       String addedJavaScript)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the given Javascript resource at the specified document position.
     * If the script has already been referenced, it's added only once.
     */
    public void addJavaScriptAtPosition(FacesContext context, ResourcePosition position, ResourceHandler resourceHandler, boolean defer)
    {
        addJavaScriptAtPosition(context, position, resourceHandler, defer, false);
    }

    private void addJavaScriptAtPosition(FacesContext context, ResourcePosition position,
                                         ResourceHandler resourceHandler, boolean defer, boolean encodeURL)
    {
        validateResourceHandler(resourceHandler);
        WritablePositionedInfo info = (WritablePositionedInfo) getScriptInstance(context, resourceHandler, defer, encodeURL);
        if (checkAlreadyAdded(info))
        {
            return;
        }
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try
        {
            info.writePositionedInfo(response, context.getResponseWriter());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private boolean checkAlreadyAdded(PositionedInfo info)
    {
        Long key = new Long(info.hashCode());
        if (alreadySeenResources.contains(key))
        {
            return true;
        }

        alreadySeenResources.add(key);
        return false;
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
        uri = getAbsoluteUri(context, uri);

        addStyleSheet(getStyleInstance(context, uri));
    }

    protected String getAbsoluteUri(FacesContext context, String uri)
    {
        if (uri.startsWith("/"))
        {
            return uri;
        }

        StringBuffer sb = new StringBuffer(80);
        if (context.getExternalContext().getRequestPathInfo() != null)
        {
            sb.append(context.getExternalContext().getRequestPathInfo());
        }
        sb.append("/");
        sb.append(uri);

        return sb.toString();
    }

    private void addStyleSheet(StreamablePositionedInfo styleInstance)
    {
        if (checkAlreadyAdded(styleInstance))
        {
            return;
        }
        getHeaderInfoEntry().addInfo(styleInstance);
    }

    /**
     * Adds the given Style Sheet at the specified document position.
     * If the style sheet has already been referenced, it's added only once.
     */
    public void addStyleSheet(FacesContext context, ResourcePosition position,
                              ResourceHandler resourceHandler)
    {
        validateResourceHandler(resourceHandler);
        addStyleSheet(getStyleInstance(context, resourceHandler));
    }

    /**
     * Adds the given Inline Style at the specified document position.
     */
    public void addInlineStyleAtPosition(FacesContext context, ResourcePosition position, String inlineStyle)
    {
        addStyleSheet(getInlineStyleInstance(inlineStyle));
    }

    /**
     * Adds the given Inline Script at the specified document position.
     */
    public void addInlineScriptAtPosition(FacesContext context, ResourcePosition position,
                                          String inlineScript)
    {
        WritablePositionedInfo info = (WritablePositionedInfo) getInlineScriptInstance(inlineScript);
        if (checkAlreadyAdded(info))
        {
            return;
        }
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try
        {
            info.writePositionedInfo(response, context.getResponseWriter());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
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
     */
    protected String getResourceUri(FacesContext context, Class resourceLoader,
                                    boolean withContextPath)
    {
        StringBuffer sb = new StringBuffer(200);
        sb.append(RESOURCE_VIRTUAL_PATH);
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
     * <p>
     * Caching in browsers normally works by having files served to them
     * include last-modified and expiry-time http headers. Until the expiry
     * time is reached, a browser will silently use its cached version. After
     * the expiry time, it will send a "get if modified since {time}" message,
     * where {time} is the last-modified header from the version it has cached.
     * <p>
     * Unfortunately this scheme only works well for resources represented as
     * plain files on disk, where the webserver can easily and efficiently see
     * the last-modified time of the resource file. When that query has to be
     * processed by a servlet that doesn't scale well, even when it is possible
     * to determine the resource's last-modified date from servlet code.
     * <p>
     * Fortunately, for the AddResource class a static resource is only ever
     * accessed because a URL was embedded by this class in a dynamic page.
     * This makes it possible to implement caching by instead marking every
     * resource served with a very long expiry time, but forcing the URL that
     * points to the resource to change whenever the old cached version becomes
     * invalid; the browser effectively thinks it is fetching a different
     * resource that it hasn't seen before. This is implemented by embedding
     * a "cache key" in the generated URL.
     * <p>
     * Rather than using the actual modification date of a resource as the
     * cache key, we simply use the webapp deployment time. This means that all
     * data cached by browsers will become invalid after a webapp deploy (all
     * the urls to the resources change). It also means that changes that occur
     * to a resource <i>without</i> a webapp redeploy will not be seen by browsers.
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
        String classNameStartsAfter = RESOURCE_VIRTUAL_PATH + '/';

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
            response.flushBuffer();
        }
        catch (ClassNotFoundException e)
        {
            log.error("Could not find class for name: " + className, e);
			sendError(response, HttpServletResponse.SC_NOT_FOUND,
                    "Could not find resourceloader class for name: " + className);
		}
        catch (InstantiationException e)
        {
            log.error("Could not instantiate class for name: " + className, e);
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Could not instantiate resourceloader class for name: " + className);
        }
        catch (IllegalAccessException e)
        {
            log.error("Could not access class for name: " + className, e);
            sendError(response, HttpServletResponse.SC_FORBIDDEN,
                    "Could not access resourceloader class for name: " + className);
        }
		catch (IOException e)
		{
			logSend.error("Error while serving resource: " +resourceUri+", message : "+ e.getMessage(), e);
			sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
        catch (Throwable e)
        {
            log.error("Unknown error while serving resource: " +resourceUri+", message : "+ e.getMessage(), e);
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

	protected void sendError(HttpServletResponse response, int errorCode, String errorText)
		throws IOException
	{
		try
		{
			response.sendError(errorCode, errorText);
		}
		catch (IllegalStateException e)
		{
			logSend.error("Could not send error, maybe some data has already been sent.", e);
		}
	}

	public boolean hasHeaderBeginInfos(HttpServletRequest request)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Parses the response to mark the positions where code will be inserted
     */
    public void parseResponse(HttpServletRequest request, String bufferedResponse,
                              HttpServletResponse response)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Writes the javascript code necessary for myfaces in every page, just befode the closing &lt;/body&gt; tag
     */
    public void writeMyFacesJavascriptBeforeBodyEnd(HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException
    {
        throw new UnsupportedOperationException();
        /*
        if (beforeBodyEndPosition >= 0)
        {
            String myFacesJavascript = (String) request.getAttribute("org.apache.myfaces.myFacesJavascript");
            if(myFacesJavascript != null)
            {
            	originalResponse.insert(beforeBodyEndPosition, myFacesJavascript);
            }
            else
            {
                log.warn("MyFaces special javascript could not be retrieved from request-map.");
            }
        }
        */
    }

    /**
     * Add the resources to the &lt;head&gt; of the page.
     * If the head tag is missing, but the &lt;body&gt; tag is present, the head tag is added.
     * If both are missing, no resource is added.
     *
     * The ordering is such that the user header CSS & JS override the MyFaces' ones.
     */
    public void writeWithFullHeader(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Writes the response
     */
    public void writeResponse(HttpServletRequest request,
                              HttpServletResponse response) throws IOException
    {
        throw new UnsupportedOperationException();
    }

    private StylePositionedInfo getStyleInstance(FacesContext context, ResourceHandler resourceHandler)
    {
        return new StylePositionedInfo(getResourceUri(context, resourceHandler));
    }

    private PositionedInfo getScriptInstance(FacesContext context, ResourceHandler resourceHandler,
                                             boolean defer, boolean encodeUrl)
    {
        return new ScriptPositionedInfo(getResourceUri(context, resourceHandler), defer, encodeUrl);
    }

    private StylePositionedInfo getStyleInstance(FacesContext context, String uri)
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

    private InlineStylePositionedInfo getInlineStyleInstance(String inlineStyle)
    {
        return new InlineStylePositionedInfo(inlineStyle);
    }

    protected interface PositionedInfo
    {
    }

    protected interface WritablePositionedInfo extends PositionedInfo
    {
        public abstract void writePositionedInfo(HttpServletResponse response, ResponseWriter writer)
                throws IOException;
    }

    protected interface StreamablePositionedInfo extends PositionedInfo
    {
        public abstract void writePositionedInfo(HttpServletResponse response, PrintWriter writer)
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

    private class StylePositionedInfo extends AbstractResourceUri implements WritablePositionedInfo, StreamablePositionedInfo
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

        public void writePositionedInfo(HttpServletResponse response, PrintWriter writer) throws IOException
        {
            writer.println();
            writer.write("@import url(\"");
            writer.write(response.encodeURL(this.getResourceUri()));
            writer.write("\");");
            writer.println();
        }
    }

    private class ScriptPositionedInfo extends AbstractResourceUri implements
            WritablePositionedInfo
    {
        protected final boolean _defer;
        protected final boolean _encodeUrl;

        public ScriptPositionedInfo(String resourceUri, boolean defer)
        {
            this(resourceUri, defer, true);
        }

        public ScriptPositionedInfo(String resourceUri, boolean defer, boolean encodeUrl)
        {
            super(resourceUri);
            _defer = defer;
            _encodeUrl = encodeUrl;
        }

        public int hashCode()
        {
            return new HashCodeBuilder()
                .append(this.getResourceUri())
                .append(_defer)
                .append(_encodeUrl)
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
                        .append(_encodeUrl, other._encodeUrl)
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
            if (_encodeUrl)
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

    private class InlineStylePositionedInfo extends InlinePositionedInfo implements StreamablePositionedInfo
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

        public void writePositionedInfo(HttpServletResponse response, PrintWriter writer) throws IOException
        {
            writer.println();
            writer.write(getInlineValue());
            writer.println();
        }
    }

    public boolean requiresBuffer()
    {
        return false;
    }

    protected HeaderInfoEntry getHeaderInfoEntry()
    {
        if (headerInfoEntry == null)
        {
            throw new IllegalStateException("responseStarted() needs to be called first");
        }

        return headerInfoEntry;
    }

    public void responseStarted()
    {
        synchronized(StreamingAddResource.class)
        {
            REQUEST_ID_COUNTER++;
            requestId = new Long(REQUEST_ID_COUNTER);
        }
        headerInfoEntry = new HeaderInfoEntry();
        synchronized (headerInfos)
        {
            headerInfos.put(requestId, headerInfoEntry);
        }
    }

    public void responseFinished()
    {
        getHeaderInfoEntry().setRequestDone();
    }

    public boolean hasHeaderBeginInfos()
    {
        return false;
    }

    public void addStyleLoaderHere(FacesContext context, Class myfacesCustomComponent) throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTML.LINK_ELEM, null);
        writer.writeAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.REL_ATTR, org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.STYLESHEET_VALUE, null);
        writer.writeAttribute(HTML.HREF_ATTR,
                getResourceUri(context,
                        new StreamingResourceHandler(requestId + "/header.css"),
                        true), null);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.STYLE_TYPE_TEXT_CSS, null);
        writer.endElement(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.LINK_ELEM);
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
}
