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
package org.apache.myfaces.webapp.filter;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter is mandatory for the use of many tomahawk components.
 *
 * <h2>Response Buffering</h2>
 * 
 * When the request is for a JSF page, and the configured "resource manager"
 * requires response buffering then a response wrapper is created which
 * buffers the entire output from the current request in memory.
 * <p>
 * Tomahawk provides at least two "resource managers":
 * <ul>
 * <li> DefaultAddResources (the default) does require response buffering
 * <li> StreamingAddResources does not, but only provides a subset of the
 * features of DefaultAddResources and therefore does not work with all
 * Tomahawk components.
 * </ul>
 * <p>
 * Only one "resource manager" may be configured for a webapp. See class
 * AddResourceFactory for further details on configuring this.
 * <p>
 * When DefaultAddResources is enabled (default behaviour), the resulting
 * response buffering does cause some unnecessary memory and performance
 * impact for pages where no component in the page actually registers a
 * resource that needs to be inserted into the page - but whether a page
 * does that or not cannot be known until after the page has been rendered.
 * In the rare case where a request to a JSF page generates non-html
 * output (eg a PDF is generated as a response to a submit of a jsf page),
 * the data is unfortunately buffered. However it is not post-processed,
 * because its http content-type header will not be "text/html" (see other
 * documentation for this class).
 *
 * <h2>Inserting Resources into HTML responses (DefaultAddResources)</h2>
 * 
 * After the response has been completely generated (and cached in memory),
 * this filter checks the http content-type header. If it is not html or xhtml,
 * then the data is simply send to the response stream without further processing.
 * 
 * For html or xhtml responses, this filter canses the data to be post-processed
 * to insert any "resources" registered via the AddResources framework. This
 * allows jsf components (and other code if it wants) to register data that
 * should be output into an HTML page, particularly into places like an html
 * "head" section, even when the component occurs after that part of the
 * response has been generated.
 * <p>
 * The default "resource manager" (DefaultAddResources) supports inserting
 * resources into any part of the generated page. The alternate class
 * StreamingAddResources does not; it does not buffer output and therefore
 * can only insert resources for a jsf component  into the page after the
 * point at which that component is rendered. In particular, this means that
 * components that use external javascript files will not work with that
 * "resource manager" as [script href=...] only works in the head section
 * of an html page.
 *
 * <h2>Serving Resources from the Classpath</h2>
 * 
 * Exactly what text gets inserted into an HTML page via a "resource"
 * (see above) is managed by the AddResources class, not this one. However
 * often it is useful for jsf components to be able to refer to resources
 * that are on the classpath, and in particular when the resource is in the
 * same jar as the component code. Servlet engines do not support serving
 * resources from the classpath. However the AddResources framework can be
 * used to generate a special url prefix that this filter can be mapped to,
 * allowing this to be done. In particular, many Tomahawk components use
 * this to bundle their necessary resources within the tomahawk jarfile.
 * <p>
 * When a request to such a url is found by this filter, the actual resource
 * is located and streamed back to the user (no buffering required). See the
 * AddResource class documentation for further details.
 *
 * <h2>Handling File Uploads</h2>
 * 
 * Sometimes an application needs to allow a user to send a file of data
 * to the web application. The html page needs only to include an input
 * tag with type=file; clicking on this will prompt the user for a file
 * to send. The browser will then send an http request to the server
 * which is marked as a "mime multipart request" with normal http post
 * parameters in one mime part, and the file contents in another mime part.
 * <p>
 * This filter also handles such requests, using the Apache HttpClient
 * library to save the file into a configurable local directory before
 * allowing the normal processing for the url that the post request
 * refers to. A number of configuration properties on this filter control
 * maximum file upload sizes and various other useful settings. 
 * 
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExtensionsFilter implements Filter {

    private Log log = LogFactory.getLog(ExtensionsFilter.class);

    private int _uploadMaxFileSize = 100 * 1024 * 1024; // 10 MB

    private int _uploadThresholdSize = 1 * 1024 * 1024; // 1 MB

    private String _uploadRepositoryPath = null; //standard temp directory

    private ServletContext _servletContext;

    public static final String DOFILTER_CALLED = "org.apache.myfaces.component.html.util.ExtensionFilter.doFilterCalled";

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {

        String param = filterConfig.getInitParameter("uploadMaxFileSize");

        _uploadMaxFileSize = resolveSize(param, _uploadMaxFileSize);

        param = filterConfig.getInitParameter("uploadThresholdSize");

        _uploadThresholdSize = resolveSize(param, _uploadThresholdSize);

        _uploadRepositoryPath = filterConfig.getInitParameter("uploadRepositoryPath");

        _servletContext = filterConfig.getServletContext();
    }

    private int resolveSize(String param, int defaultValue) {
        int numberParam = defaultValue;

        if (param != null) {
            param = param.toLowerCase();
            int factor = 1;
            String number = param;

            if (param.endsWith("g")) {
                factor = 1024 * 1024 * 1024;
                number = param.substring(0, param.length() - 1);
            } else if (param.endsWith("m")) {
                factor = 1024 * 1024;
                number = param.substring(0, param.length() - 1);
            } else if (param.endsWith("k")) {
                factor = 1024;
                number = param.substring(0, param.length() - 1);
            }

            numberParam = Integer.parseInt(number) * factor;
        }
        return numberParam;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if(request.getAttribute(DOFILTER_CALLED)!=null)
        {
            chain.doFilter(request, response);
            return;
        }

        request.setAttribute(DOFILTER_CALLED,"true");

        if (!(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletRequest extendedRequest = httpRequest;

        // For multipart/form-data requests
        if (FileUpload.isMultipartContent(httpRequest)) {
            extendedRequest = new MultipartRequestWrapper(httpRequest, _uploadMaxFileSize, _uploadThresholdSize, _uploadRepositoryPath);
        }

        // Serve resources
        AddResource addResource;

        try
        {
            addResource=AddResourceFactory.getInstance(httpRequest);
            if( addResource.isResourceUri(_servletContext, httpRequest ) ){
                addResource.serveResource(_servletContext, httpRequest, httpResponse);
                return;
            }
        }
        catch(Throwable th)
        {
            log.error("Exception wile retrieving addResource",th);
            throw new ServletException(th);
        }

        try
        {
        	addResource.responseStarted();
        	
	        if (addResource.requiresBuffer())
	        {
		        ExtensionsResponseWrapper extendedResponse = new ExtensionsResponseWrapper((HttpServletResponse) response);
		
		        // Standard request
		        chain.doFilter(extendedRequest, extendedResponse);
		
		        extendedResponse.finishResponse();
		
		        // write the javascript stuff for myfaces and headerInfo, if needed
		        HttpServletResponse servletResponse = (HttpServletResponse)response;
		
		        // only parse HTML responses
		        if (extendedResponse.getContentType() != null && isValidContentType(extendedResponse.getContentType()))
		        {
		            addResource.parseResponse(extendedRequest, extendedResponse.toString(),
		                    servletResponse);
		
		            addResource.writeMyFacesJavascriptBeforeBodyEnd(extendedRequest,
		                    servletResponse);
		
		            if( ! addResource.hasHeaderBeginInfos() ){
		                // writes the response if no header info is needed
		                addResource.writeResponse(extendedRequest, servletResponse);
		                return;
		            }
		
		            // Some headerInfo has to be added
		            addResource.writeWithFullHeader(extendedRequest, servletResponse);
		
		            // writes the response
		            addResource.writeResponse(extendedRequest, servletResponse);
		        }
		        else
		        {

		        	byte[] responseArray = extendedResponse.getBytes();

                    if(responseArray.length > 0)
                    {
 			        	// When not filtering due to not valid content-type, deliver the byte-array instead of a charset-converted string.
 			        	// Otherwise a binary stream gets corrupted.
 			            servletResponse.getOutputStream().write(responseArray);
 		        	}
                }
	        }
	        else
	        {
		        chain.doFilter(extendedRequest, response);
	        }
        }
        finally
        {
        	addResource.responseFinished();        	
        }
    }

    public boolean isValidContentType(String contentType)
    {
        return contentType.startsWith("text/html") ||
                contentType.startsWith("text/xml") ||
                contentType.startsWith("application/xhtml+xml") ||
                contentType.startsWith("application/xml");
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
        // NoOp
    }


}
