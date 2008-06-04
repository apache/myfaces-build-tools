package org.apache.myfaces.tomahawk.application.jsp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.factory.TilesContainerFactory;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.myfaces.shared_tomahawk.webapp.webxml.ServletMapping;
import org.apache.myfaces.shared_tomahawk.webapp.webxml.WebXml;

import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Martin Marinschek
 * @version $Revision: 472792 $ $Date: 2006-11-09 07:34:47 +0100 (Do, 09 Nov 2006) $
 */
public class JspTilesTwoViewHandlerImpl
        extends ViewHandler {
    private ViewHandler _viewHandler;

    private static final Log log = LogFactory.getLog(JspTilesTwoViewHandlerImpl.class);
    private static final String TILES_DEF_EXT = ".tiles";

    private String tilesExtension = TILES_DEF_EXT;

    public JspTilesTwoViewHandlerImpl(ViewHandler viewHandler)
    {
        _viewHandler = viewHandler;
    }

    private void initContainer(ExternalContext context) {

        if(TilesAccess.getContainer(context.getContext())==null) {
            try
            {
                TilesContainerFactory factory = TilesContainerFactory.getFactory(context.getContext());
                TilesContainer container = factory.createTilesContainer(context.getContext());
                TilesAccess.setContainer(context.getContext(),container);
            }
            catch (Exception e)
            {
                throw new FacesException("Error reading tiles definitions : " + e.getMessage(), e);
            }
        }
    }

    public void renderView(FacesContext facesContext, UIViewRoot viewToRender) throws IOException, FacesException
    {
        if (viewToRender == null)
        {
            log.fatal("viewToRender must not be null");
            throw new NullPointerException("viewToRender must not be null");
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        String viewId = deriveViewId(externalContext, viewToRender.getViewId());

        if(viewId==null) {
            //deriving view-id made clear we are not responsible for this view-id - call the delegate
            _viewHandler.renderView(facesContext, viewToRender);
            return;
        }


        initContainer(externalContext);

        String tilesId = deriveTileFromViewId(viewId);

        TilesContainer container = TilesAccess.getContainer(externalContext.getContext());

        Object[] requestObjects = {externalContext.getRequest(), externalContext.getResponse()};

        if(container.isValidDefinition(tilesId, requestObjects)) {

            //propagate our new view-id to wherever it makes sense
            setViewId(viewToRender, viewId, facesContext);

            renderTilesView(externalContext, requestObjects, container, viewToRender, viewId, tilesId);
        }
        else {
            //we're not using tiles as no valid definition has been found
            //just call the delegate view-handler to let it do its thing
            _viewHandler.renderView(facesContext, viewToRender);
        }
    }

    private void renderTilesView(ExternalContext externalContext, Object[] requestObjects, TilesContainer container, UIViewRoot viewToRender, String viewId, String tilesId) {
        handleCharacterEncoding(viewId, externalContext, viewToRender);

        container.startContext(requestObjects);
        try {
            container.render(tilesId,requestObjects);
        } catch (TilesException e) {
            throw new FacesException(e);
        }
        finally {
            container.endContext(requestObjects);
        }

        handleCharacterEncodingPostDispatch(externalContext);
    }

    private void setViewId(UIViewRoot viewToRender, String viewId, FacesContext facesContext) {
        viewToRender.setViewId(viewId);
        if(facesContext.getViewRoot()!=null) {
            facesContext.getViewRoot().setViewId(viewId);
        }
    }

    private String deriveTileFromViewId(String viewId) {
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
        return tilesId;
    }

    private String deriveViewId(ExternalContext externalContext, String viewId) {
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
                    return viewId + suffix;
                }
                else
                {
                    if (log.isTraceEnabled()) log.trace("Replacing extension of current viewId by suffix " + suffix);
                    return viewId.substring(0, dot) + suffix;
                }
            }

            //extension-mapped page ends with proper suffix - all ok
            return viewId;
        }
        else if (!viewId.endsWith(suffix))
        {
            // path-mapping used, but suffix is no default-suffix
            return null;
        }
        else {
            //path-mapping used, suffix is default-suffix - all ok
            return viewId;
        }
    }

    private void handleCharacterEncodingPostDispatch(ExternalContext externalContext) {
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

    private void handleCharacterEncoding(String viewId, ExternalContext externalContext, UIViewRoot viewToRender) {
        if (log.isTraceEnabled()) log.trace("Dispatching to " + viewId);

        // handle character encoding as of section 2.5.2.2 of JSF 1.1
        if (externalContext.getResponse() instanceof ServletResponse) {
            ServletResponse response = (ServletResponse) externalContext.getResponse();
            response.setLocale(viewToRender.getLocale());
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
