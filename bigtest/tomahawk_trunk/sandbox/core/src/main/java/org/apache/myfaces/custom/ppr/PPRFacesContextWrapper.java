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
package org.apache.myfaces.custom.ppr;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Convenient class to wrap the current FacesContext.
 * <p>
 * A class of this name is provided in JSF1.2, but not in JSF1.1.
 * <p>
 * Any methods that do not actually need to be overridden are declared final
 * in order to improve performance (helps the JVM to optimise away the call).
 */
class PPRFacesContextWrapper extends FacesContext
{
    //~ Instance fields -------------------------------------------------------

    private final FacesContext _facesContext;
    private Method methodGetELContext = null;

    public PPRFacesContextWrapper(FacesContext facesContext)
    {
        _facesContext = facesContext;
    }

    //~ Non-Final Methods -----------------------------------------------------

    public void release()
    {
        _facesContext.release();
    }

    //~ Final Methods ---------------------------------------------------------

    public final Application getApplication()
    {
        return _facesContext.getApplication();
    }

    public final Iterator getClientIdsWithMessages()
    {
        return _facesContext.getClientIdsWithMessages();
    }

    public final ExternalContext getExternalContext()
    {
        return _facesContext.getExternalContext();
    }

    public final FacesMessage.Severity getMaximumSeverity()
    {
        return _facesContext.getMaximumSeverity();
    }

    public final Iterator getMessages()
    {
        return _facesContext.getMessages();
    }

    public final Iterator getMessages(String clientId)
    {
        return _facesContext.getMessages(clientId);
    }

    public final RenderKit getRenderKit()
    {
        return _facesContext.getRenderKit();
    }

    public final boolean getRenderResponse()
    {
        return _facesContext.getRenderResponse();
    }

    public final boolean getResponseComplete()
    {
        return _facesContext.getResponseComplete();
    }

    public final void setResponseStream(ResponseStream responsestream)
    {
        _facesContext.setResponseStream(responsestream);
    }

    public final ResponseStream getResponseStream()
    {
        return _facesContext.getResponseStream();
    }

    public final void setResponseWriter(ResponseWriter responsewriter)
    {
        _facesContext.setResponseWriter(responsewriter);
    }

    public final ResponseWriter getResponseWriter()
    {
        return _facesContext.getResponseWriter();
    }

    public final void setViewRoot(UIViewRoot viewRoot)
    {
        _facesContext.setViewRoot(viewRoot);
    }

    public UIViewRoot getViewRoot()
    {
        return _facesContext.getViewRoot();
    }

    public final void addMessage(String clientId, FacesMessage message)
    {
        _facesContext.addMessage(clientId, message);
    }

    public final void renderResponse()
    {
        _facesContext.renderResponse();
    }

    public final void responseComplete()
    {
        _facesContext.responseComplete();
    }

    /**
     * Implement getELContext by delegating call to another instance.
     * <p>
     * Note that this method was added in JSF1.2. In order for a JSF1.2
     * implementation to be backwards-compatible with JSF1.1, the base
     * class FacesContext therefore has to automatically do the delegation.
     * Without automatic delegation, any JSF1.1 class that applies the decorator
     * pattern to a FacesContext will just break in JSF1.2; the getELContext
     * method is there (inherited from the base class) but does not correctly
     * delegate.
     * <p>
     * Unfortunately, due to a design flaw in JSF1.2 it is simply not possible
     * for the base class to delegate; the object to delegate to is not known
     * to the base class! A partial solution that works in most cases is for
     * the base class to delegate to the "core" instance of FacesContext for
     * methods that are not overridden; Sun's RI does this correctly but
     * unfortunately MyFaces 1.2.0-1.2.2 do not. See MYFACES-1820 for details.
     * <p>
     * The solution *here* is to require that a javax.el implementation is in
     * the classpath even when running JSF1.1. It is then possible for this
     * wrapper to override the method defined in JSF1.2 even when being
     * compiled against the JSF1.1 implementation. It is mildly annoying to
     * have to include javax.el in a JSF environment (ie when it will never
     * be used) but better than the alternatives. Actually, for at least some
     * JVMs, classes needed by a method are not loaded unless that method is
     * actually referenced, so in some cases (including Sun Java 1.4-1.6) the
     * el library *can* be omitted from the classpath with JSF1.1.
     */
    public final ELContext getELContext()
    {
    	// Here, we cannot call getELContext on FacesContext as it does not
    	// exist for JSF1.1; the solution is to use reflection instead. This
    	// method will never be called unless we are in a JSF1.2 environment
    	// so the target method will always exist when this is called.
    	try
    	{
    		if (methodGetELContext == null)
    		{
    			// Performance optimisation: find method, and cache it for later.
    			methodGetELContext = FacesContext.class.getDeclaredMethod("getELContext", (Class[]) null);
    		}
    		return (ELContext) methodGetELContext.invoke(_facesContext, (Object[]) null);
    	}
    	catch(NoSuchMethodException e)
    	{
    		// should never happen
    		throw (IllegalStateException) new IllegalStateException("JSF1.2 method invoked in non-JSF-1.2 environment").initCause(e);
    	}
    	catch(InvocationTargetException e)
    	{
    		// should never happen
    		throw (IllegalStateException) new IllegalStateException("Method getELContext on wrapped instance threw exception").initCause(e);
    	}
    	catch(IllegalAccessException e)
    	{
    		// should never happen
    		throw (IllegalStateException) new IllegalStateException("Method getElContext on wrapped instance is not accessable").initCause(e);
    	}
    }
}
