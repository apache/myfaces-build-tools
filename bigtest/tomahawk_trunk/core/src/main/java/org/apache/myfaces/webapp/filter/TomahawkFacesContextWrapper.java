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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.tomahawk.util.ExternalContextUtils;

/**
 * @author Martin Marinschek
 */
public class TomahawkFacesContextWrapper extends FacesContext {

    private FacesContext delegate=null;
    private ExternalContext externalContextDelegate=null;
    private ExtensionsResponseWrapper extensionsResponseWrapper = null;

    public TomahawkFacesContextWrapper(FacesContext delegate) {

        this.delegate = delegate;
        
        //if(delegate.getExternalContext().getResponse() instanceof PortletResponse) {
        if(ExternalContextUtils.getRequestType(delegate.getExternalContext()).isPortlet()) {
            //todo do something here - with the multipart-wrapper. rest should be fine
            AddResource addResource= AddResourceFactory.getInstance(this);
            addResource.responseStarted();

	        if (addResource.requiresBuffer())
	        {
                throw new IllegalStateException("buffering not supported in the portal environment. "+
                        " Use for org.apache.myfaces.ADD_RESOURCE_CLASS the value"+
                        " org.apache.myfaces.renderkit.html.util.NonBufferingAddResource.");
            }
        }
        else {
            HttpServletResponse httpResponse = (HttpServletResponse) delegate.getExternalContext().getResponse();
            HttpServletRequest httpRequest = (HttpServletRequest) delegate.getExternalContext().getRequest();

            HttpServletRequest extendedRequest = httpRequest;
            HttpServletResponse extendedResponse = httpResponse;

            // For multipart/form-data requests
            if (FileUpload.isMultipartContent(httpRequest)) {
                extendedRequest = new MultipartRequestWrapper(httpRequest, /*todo _uploadMaxFileSize*/-1,
                        /*todo _uploadThresholdSize*/-1, /*todo _uploadRepositoryPath*/null);
            }

            AddResource addResource= AddResourceFactory.getInstance(this);
            addResource.responseStarted();

	        if (addResource.requiresBuffer())
	        {
                extensionsResponseWrapper = new ExtensionsResponseWrapper(httpResponse);
		        extendedResponse = extensionsResponseWrapper;
            }

            externalContextDelegate = new ExternalContextWrapper(
                    delegate.getExternalContext(), extendedRequest, extendedResponse);
        }
    }

    /**
     * This method uses reflection to call the method of the delegated
     * FacesContext getELContext, present on 1.2. This should be done
     * since we need compatibility between 1.1 and 1.2 for tomahawk
     * 
     * @return
     */
    public javax.el.ELContext getELContext() {
        ;
        try
        {
            return (javax.el.ELContext) delegate.getClass().getMethod("getELContext", null).invoke(delegate, null);
        }
        catch (IllegalArgumentException e)
        {
            //If the method is called with jsf 1.2, this should not 
            //be thrown
            throw new RuntimeException(e);
        }
        catch (SecurityException e)
        {
            //If the method is called with jsf 1.2, this should not 
            //be thrown
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (IllegalAccessException e)
        {
            //If the method is called with jsf 1.2, this should not 
            //be thrown
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (InvocationTargetException e)
        {
            //If the method is called with jsf 1.2, this should not 
            //be thrown
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (NoSuchMethodException e)
        {
            //If the method is called with jsf 1.2, this should not 
            //be thrown
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());            
        }
    }

    public Application getApplication() {
        return delegate.getApplication();
    }

    public Iterator getClientIdsWithMessages() {
        return delegate.getClientIdsWithMessages();
    }

    public ExternalContext getExternalContext() {
        return externalContextDelegate==null?delegate.getExternalContext():externalContextDelegate;
    }

    public FacesMessage.Severity getMaximumSeverity() {
        return delegate.getMaximumSeverity();
    }

    public Iterator getMessages() {
        return delegate.getMessages();
    }

    public Iterator getMessages(String clientId) {
        return delegate.getMessages(clientId);
    }

    public RenderKit getRenderKit() {
        return delegate.getRenderKit();
    }

    public boolean getRenderResponse() {
        return delegate.getRenderResponse();
    }

    public boolean getResponseComplete() {
        return delegate.getResponseComplete();
    }

    public ResponseStream getResponseStream() {
        return delegate.getResponseStream();
    }

    public void setResponseStream(ResponseStream responseStream) {
        delegate.setResponseStream(responseStream);
    }

    public ResponseWriter getResponseWriter() {
        return delegate.getResponseWriter();
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        delegate.setResponseWriter(responseWriter);
    }

    public UIViewRoot getViewRoot() {
        return delegate.getViewRoot();
    }

    public void setViewRoot(UIViewRoot root) {
        delegate.setViewRoot(root);
    }

    public void addMessage(String clientId, FacesMessage message) {
        delegate.addMessage(clientId, message);
    }

    public void release() {

        AddResource addResource=null;

        try
        {
            addResource= AddResourceFactory.getInstance(this);
            if (addResource.requiresBuffer())
	        {
                if(extensionsResponseWrapper == null) {
                    throw new NullPointerException("When wrapping the faces-context, add-resource told us that no buffer is required, " +
                            "now it is required, and we have a null-extensionsResponseWrapper. Please fix add-resource to be consistent over a single request.");
                }
                extensionsResponseWrapper.finishResponse();

		        // write the javascript stuff for myfaces and headerInfo, if needed
		        HttpServletResponse servletResponse = extensionsResponseWrapper.getDelegate();
                HttpServletRequest servletRequest = (HttpServletRequest) getExternalContext().getRequest();

                // only parse HTML responses
		        if (extensionsResponseWrapper.getContentType() != null && isValidContentType(extensionsResponseWrapper.getContentType()))
		        {
		            addResource.parseResponse(servletRequest, extensionsResponseWrapper.toString(),
		                    servletResponse);

		            addResource.writeMyFacesJavascriptBeforeBodyEnd(servletRequest,
		                    servletResponse);

		            if( ! addResource.hasHeaderBeginInfos() ){
		                // writes the response if no header info is needed
		                addResource.writeResponse(servletRequest, servletResponse);
		                return;
		            }

		            // Some headerInfo has to be added
		            addResource.writeWithFullHeader(servletRequest, servletResponse);

		            // writes the response
		            addResource.writeResponse(servletRequest, servletResponse);
		        }
		        else
		        {

		        	byte[] responseArray = extensionsResponseWrapper.getBytes();

                    if(responseArray.length > 0)
                    {
 			        	// When not filtering due to not valid content-type, deliver the byte-array instead of a charset-converted string.
 			        	// Otherwise a binary stream gets corrupted.
 			            servletResponse.getOutputStream().write(responseArray);
 		        	}
                }
	        }
        }
        catch(Throwable th) {
            throw new FacesException(th);
        }
        finally
        {
            try
            {
                if(addResource!=null)
                {
                    addResource.responseFinished();
                }
            }
            finally
            {
                delegate.release();
            }
        }

    }

    public boolean isValidContentType(String contentType)
    {
        return contentType.startsWith("text/html") ||
                contentType.startsWith("text/xml") ||
                contentType.startsWith("application/xhtml+xml") ||
                contentType.startsWith("application/xml");
    }

    public void renderResponse() {
        delegate.renderResponse();
    }

    public void responseComplete() {
        delegate.responseComplete();
    }
    
    /*This method is for do not break MyfacesGenericPortlet*/
    public void setExternalContext(ExternalContext extContext){
        try
        {
            Method method = delegate.getClass().getMethod("setExternalContext", new Class[]{Class.forName("org.apache.myfaces.context.ReleaseableExternalContext")});
            method.invoke(delegate, new Object[]{extContext});
            FacesContext.setCurrentInstance(this);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException("JSF 1.2 method not implemented: "+e.getMessage());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error calling JSF 1.2 method: "+e.getMessage());
        }
    }
    
}
