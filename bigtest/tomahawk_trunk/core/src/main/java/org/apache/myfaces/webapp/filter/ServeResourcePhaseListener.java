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

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.tomahawk.util.ExternalContextUtils;

/**
 * @author Martin Marinschek
 */
public class ServeResourcePhaseListener implements PhaseListener {

    private Log log = LogFactory.getLog(ServeResourcePhaseListener.class);

    public static final String DOFILTER_CALLED = "org.apache.myfaces.component.html.util.ExtensionFilter.doFilterCalled";

    public void afterPhase(PhaseEvent event) {
    }

    public void beforePhase(PhaseEvent event) {
        if(event.getPhaseId()==PhaseId.RESTORE_VIEW || event.getPhaseId()==PhaseId.RENDER_RESPONSE) {

            FacesContext fc = event.getFacesContext();
            ExternalContext externalContext = event.getFacesContext().getExternalContext();

            if(externalContext.getRequestMap().get(DOFILTER_CALLED)!=null)
            {
                //we have already been called (before-restore-view, and we are now in render-response),
                // no need to do everything again...
                return;
            }

            externalContext.getRequestMap().put(DOFILTER_CALLED,"true");


            //Use ExternalContextUtils to find if this is a portled request
            //if(externalContext.getRequest() instanceof PortletRequest) {            
            if(ExternalContextUtils.getRequestType(externalContext).isPortlet()) {
                //we are in portlet-world! in portlet 1.0 (JSR-168), we cannot do anything here, but
                //TODO in portlet 2.0 (JSR-286), we will write the resource to the stream here if we
                //get a resource-request (resource-requests are only available in 286)
                if(log.isDebugEnabled()) {
                    log.debug("We are in portlet-space, but we cannot do anything here in JSR-168 - " +
                            "for resource-serving, our resource-servlet has to be registered.");
                }
            }
            else if(externalContext.getResponse() instanceof HttpServletResponse) {

                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
                ServletContext context = (ServletContext) fc.getExternalContext().getContext();

                // Serve resources
                AddResource addResource;

                try
                {
                    addResource= AddResourceFactory.getInstance(request);
                    if( addResource.isResourceUri(context, request ) ){
                        addResource.serveResource(context, request, response);
                        event.getFacesContext().responseComplete();
                        return;
                    }
                }
                catch(Throwable th)
                {
                    log.error("Exception wile retrieving addResource",th);
                    throw new FacesException(th);
                }
            }
            else {
                if(log.isDebugEnabled()) {
                    log.debug("Response of type : "+(
                            externalContext.getResponse()==null?"null":externalContext.getResponse().getClass().getName())+" not handled so far.");
                }
            }
        }
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
