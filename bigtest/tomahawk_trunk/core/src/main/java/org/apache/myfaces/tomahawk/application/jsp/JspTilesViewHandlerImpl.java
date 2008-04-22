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
package org.apache.myfaces.tomahawk.application.jsp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_tomahawk.webapp.webxml.ServletMapping;
import org.apache.myfaces.shared_tomahawk.webapp.webxml.WebXml;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.DefinitionsFactory;
import org.apache.struts.tiles.DefinitionsFactoryConfig;
import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.TilesUtil;

/**
 * @author Thomas Spiegl
 * @version $Revision$ $Date$
 */
public class JspTilesViewHandlerImpl
    extends ViewHandler
{
    private ViewHandler _viewHandler;

    private static final Log log = LogFactory.getLog(JspTilesViewHandlerImpl.class);
    private static final String TILES_DEF_EXT = ".tiles";
    private static final String TILES_DEF_ATTR = "tiles-definitions";
    private static final String TILES_EXT_ATTR = "tiles-extension";

    private String tilesExtension = TILES_DEF_EXT;
    private DefinitionsFactory _definitionsFactory;

    public JspTilesViewHandlerImpl(ViewHandler viewHandler)
    {
        _viewHandler = viewHandler;
    }

    private DefinitionsFactory getDefinitionsFactory()
    {
        if (_definitionsFactory == null)
        {
            if (log.isDebugEnabled()) log.debug("JspTilesViewHandlerImpl init");
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String tilesDefinitions = context.getInitParameter(TILES_DEF_ATTR);
            if (tilesDefinitions == null)
            {
                log.fatal("No Tiles definition found. Specify Definition files by adding "
                          + TILES_DEF_ATTR + " param in your web.xml");
                return null;
            }

            String tilezExtension = context.getInitParameter(TILES_EXT_ATTR);
            if (tilezExtension != null)
            {
                tilesExtension = tilezExtension;
                if (log.isDebugEnabled())
                {
                log.debug("Using non-default tiles extension of: " + tilesExtension);
                }
            } 

            DefinitionsFactoryConfig factoryConfig = new DefinitionsFactoryConfig();
            factoryConfig.setDefinitionConfigFiles(tilesDefinitions);
            try
            {
                if (log.isDebugEnabled()) log.debug("Reading tiles definitions");
                _definitionsFactory = TilesUtil.createDefinitionsFactory((ServletContext)context.getContext(),
                                                                        factoryConfig);
            }
            catch (DefinitionsFactoryException e)
            {
                log.fatal("Error reading tiles definitions", e);
                return null;
            }
        }
        return _definitionsFactory;
    }

    public void renderView(FacesContext facesContext, UIViewRoot viewToRender) throws IOException, FacesException
    {
        if (viewToRender == null)
        {
            log.fatal("viewToRender must not be null");
            throw new NullPointerException("viewToRender must not be null");
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        String viewId = facesContext.getViewRoot().getViewId();
        ServletMapping servletMapping = getServletMapping(externalContext);

        String defaultSuffix = externalContext.getInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);
        String suffix = defaultSuffix != null ? defaultSuffix : ViewHandler.DEFAULT_SUFFIX;
        if (servletMapping.isExtensionMapping())
        {
            if (!viewId.endsWith(suffix))
            {
                int dot = viewId.lastIndexOf('.');
                if (dot == -1)
                {
                    if (log.isTraceEnabled()) log.trace("Current viewId has no extension, appending default suffix " + suffix);
                    viewId = viewId + suffix;
                }
                else
                {
                    if (log.isTraceEnabled()) log.trace("Replacing extension of current viewId by suffix " + suffix);
                    viewId = viewId.substring(0, dot) + suffix;
                }
                facesContext.getViewRoot().setViewId(viewId);
            }
        }
        else if (!viewId.endsWith(suffix))
        {
            // path-mapping used, but suffix is no default-suffix
            dispatch(externalContext, viewToRender, viewId);
            return;
        }

        String tilesId = viewId;
        int idx = tilesId.lastIndexOf('.');
        if (idx > 0)
        {
            tilesId = tilesId.substring(0, idx) + tilesExtension;
        }
        else
        {
            tilesId = tilesId  + tilesExtension;
        }
        ServletRequest request = (ServletRequest)externalContext.getRequest();
        ServletContext servletContext = (ServletContext)externalContext.getContext();

        ComponentDefinition definition = null;
        try
        {
            definition = getDefinitionsFactory().getDefinition(tilesId, request, servletContext);
            
            if (definition == null)
            {
                /**
                 * Check for the definition without the leading '/' character.  Allows user to specify Tiles definitions without a 
                 * leading '/' char.
                 */
                int slashIndex = tilesId.indexOf("/");
                if (slashIndex == 0)
                {
                    tilesId = tilesId.substring(1);
                    definition = getDefinitionsFactory().getDefinition(tilesId, request, servletContext);
                }
            }
            
            if (definition != null)
            {
                // if tiles-definition could be found set ComponentContext & viewId
                ComponentContext tileContext = ComponentContext.getContext(request);
                if (tileContext == null)
                {
                    tileContext = new ComponentContext(definition.getAttributes());
                    ComponentContext.setContext(tileContext, request);
                }
                else
                {
                    tileContext.addMissing(definition.getAttributes());
                }
                viewId = definition.getPage();
                // if a controller is defined for this tile, execute it
                Controller tilesController = definition.getOrCreateController();
                if (tilesController != null) {
                    ServletResponse response = (ServletResponse) externalContext.getResponse();
                    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
                        try {
                            tilesController.execute(tileContext, (HttpServletRequest) request,
                                    (HttpServletResponse) response, servletContext);
                        } catch (Exception e) {
                            throw new FacesException(e);
                        }
                    } // else not executing controller for non-HTTP request/response (is this right??)
                }
            }
        }
        catch (DefinitionsFactoryException e)
        {
            throw new FacesException(e);
        }
        catch (InstantiationException e)
        {
            throw new FacesException(e);
        }

        dispatch(externalContext, viewToRender, viewId);
    }

    private void dispatch(ExternalContext externalContext, UIViewRoot viewToRender, String viewId)
        throws IOException
    {
        if (log.isTraceEnabled()) log.trace("Dispatching to " + viewId);

        // handle character encoding as of section 2.5.2.2 of JSF 1.1
        if (externalContext.getResponse() instanceof ServletResponse) {
            ServletResponse response = (ServletResponse) externalContext.getResponse();
            response.setLocale(viewToRender.getLocale());
        }

        externalContext.dispatch(viewId);

        // handle character encoding as of section 2.5.2.2 of JSF 1.1
        if (externalContext.getRequest() instanceof HttpServletRequest) {
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
            HttpSession session = request.getSession(false);

            if (session != null) {
                session.setAttribute(ViewHandler.CHARACTER_ENCODING_KEY, response.getCharacterEncoding());
            }
        }


    }

    private static ServletMapping getServletMapping(ExternalContext externalContext)
    {
        String servletPath = externalContext.getRequestServletPath();
        String requestPathInfo = externalContext.getRequestPathInfo();

        WebXml webxml = WebXml.getWebXml(externalContext);
        List mappings = webxml.getFacesServletMappings();

        boolean isExtensionMapping = requestPathInfo == null;

        for (int i = 0, size = mappings.size(); i < size; i++)
        {
            ServletMapping servletMapping = (ServletMapping) mappings.get(i);
            if (servletMapping.isExtensionMapping() == isExtensionMapping)
            {
                String urlpattern = servletMapping.getUrlPattern();
                if (isExtensionMapping)
                {
                    String extension = urlpattern.substring(1, urlpattern.length());
                    if (servletPath.endsWith(extension))
                    {
                        return servletMapping;
                    }
                }
                else
                {
                    urlpattern = urlpattern.substring(0, urlpattern.length() - 2);
                    // servletPath starts with "/" except in the case where the
                    // request is matched with the "/*" pattern, in which case
                    // it is the empty string (see Servlet Sepc 2.3 SRV4.4)
                    if (servletPath.equals(urlpattern))
                    {
                        return servletMapping;
                    }
                }
            }
        }
        log.error("could not find pathMapping for servletPath = " + servletPath +
                  " requestPathInfo = " + requestPathInfo);
        throw new IllegalArgumentException("could not find pathMapping for servletPath = " + servletPath +
                  " requestPathInfo = " + requestPathInfo);
    }


    public Locale calculateLocale(FacesContext context)
    {
        return _viewHandler.calculateLocale(context);
    }

    public String calculateRenderKitId(FacesContext context)
    {
        return _viewHandler.calculateRenderKitId(context);
    }

    public UIViewRoot createView(FacesContext context, String viewId)
    {
        return _viewHandler.createView(context, viewId);
    }

    public String getActionURL(FacesContext context, String viewId)
    {
        return _viewHandler.getActionURL(context, viewId);
    }

    public String getResourceURL(FacesContext context, String path)
    {
        return _viewHandler.getResourceURL(context, path);
    }

    public UIViewRoot restoreView(FacesContext context, String viewId)
    {
        return _viewHandler.restoreView(context, viewId);
    }

    public void writeState(FacesContext context) throws IOException
    {
        _viewHandler.writeState(context);
    }

}
