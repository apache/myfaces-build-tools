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

import org.apache.myfaces.component.html.ext.UIComponentPerspective;
import org.apache.myfaces.shared_tomahawk.component.ExecuteOnCallback;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * UIViewRoot wrapper which will process only those components configured using {@link PPRSubmit}.
 * If this configuration is missing the default process will take place.
 */
public class PPRViewRootWrapper extends UIViewRoot
{
    private final UIViewRoot delegateViewRoot;

    public PPRViewRootWrapper(UIViewRoot delegateViewRoot)
    {
        this.delegateViewRoot = delegateViewRoot;
    }

    public boolean getRendersChildren()
    {
        return true;
    }

    public List getChildren()
    {
        return delegateViewRoot.getChildren();
    }

    public int getChildCount()
    {
        return delegateViewRoot.getChildCount();
    }


    public String getViewId()
    {
        return delegateViewRoot.getViewId();
    }

    public void setViewId(String viewId)
    {
        delegateViewRoot.setViewId(viewId);
    }

    public void queueEvent(FacesEvent event)
    {
        delegateViewRoot.queueEvent(event);
    }

    public void processDecodes(FacesContext context)
    {
        delegateViewRoot.processDecodes(context);
    }

    public void processValidators(FacesContext context)
    {
        Map requestMap = context.getExternalContext().getRequestMap();
        List allProcessComponents = (List) requestMap.get(PPRSupport.PROCESS_COMPONENTS);
        if (allProcessComponents != null)
        {
            invokeOnComponents(context, allProcessComponents, new ContextCallback()
            {
                public void invokeContextCallback(FacesContext context, UIComponent target)
                {
                    target.processValidators(context);
                }
            });
        }
        else
        {
            delegateViewRoot.processValidators(context);
        }
    }

    private void invokeOnComponents(FacesContext context, List componentClientIds, final ContextCallback contextCallback)
    {
        Iterator iterComponents = componentClientIds.iterator();
        while (iterComponents.hasNext())
        {
            String componentId = (String) iterComponents.next();

            UIComponent component = context.getViewRoot().findComponent(componentId);
            if (component instanceof UIComponentPerspective)
            {
                UIComponentPerspective uiComponentPerspective = (UIComponentPerspective) component;
                ExecuteOnCallback getComponentCallback = new ExecuteOnCallback()
                {
                    public Object execute(FacesContext context, UIComponent component)
                    {
                        contextCallback.invokeContextCallback(context, component);
                        return null;
                    }
                };
                uiComponentPerspective.executeOn(context, getComponentCallback);
            }
            else
            {
                contextCallback.invokeContextCallback(context, component);
            }
        }
    }

    public void processUpdates(FacesContext context)
    {
        Map requestMap = context.getExternalContext().getRequestMap();
        List allProcessComponents = (List) requestMap.get(PPRSupport.PROCESS_COMPONENTS);
        if (allProcessComponents != null)
        {
            invokeOnComponents(context, allProcessComponents, new ContextCallback()
            {
                public void invokeContextCallback(FacesContext context, UIComponent target)
                {
                    target.processUpdates(context);
                }
            });
        }
        else
        {
            delegateViewRoot.processUpdates(context);
        }
    }

    public void processApplication(FacesContext context)
    {
        delegateViewRoot.processApplication(context);
    }

    public void encodeBegin(FacesContext context)
        throws java.io.IOException
    {
        delegateViewRoot.encodeBegin(context);
    }

    /* Provides a unique id for this component instance.
    */
    public String createUniqueId()
    {
        return delegateViewRoot.createUniqueId();
    }

    public Locale getLocale()
    {
        return delegateViewRoot.getLocale();
    }


    public void setLocale(Locale locale)
    {
        delegateViewRoot.setLocale(locale);
    }

    public static final String COMPONENT_TYPE = "javax.faces.ViewRoot";
    public static final String COMPONENT_FAMILY = "javax.faces.ViewRoot";

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }


    public void setRenderKitId(String renderKitId)
    {
        delegateViewRoot.setRenderKitId(renderKitId);
    }

    public String getRenderKitId()
    {
        return delegateViewRoot.getRenderKitId();
    }


    public Object saveState(FacesContext context)
    {
        return delegateViewRoot.saveState(context);
    }

    public void restoreState(FacesContext context, Object state)
    {
        delegateViewRoot.restoreState(context, state);
    }

    public Map getAttributes()
    {
        return delegateViewRoot.getAttributes();
    }

    public ValueBinding getValueBinding(String name)
    {
        return delegateViewRoot.getValueBinding(name);
    }

    public void setValueBinding(String name, ValueBinding binding)
    {
        delegateViewRoot.setValueBinding(name, binding);
    }

    public String getClientId(FacesContext context)
    {
        return delegateViewRoot.getClientId(context);
    }

    public String getId()
    {
        return delegateViewRoot.getId();
    }

    public void setId(String id)
    {
        delegateViewRoot.setId(id);
    }

    public UIComponent getParent()
    {
        return delegateViewRoot.getParent();
    }

    public void setParent(UIComponent parent)
    {
        delegateViewRoot.setParent(parent);
    }

    public UIComponent findComponent(String expr)
    {
        return delegateViewRoot.findComponent(expr);
    }

    public Map getFacets()
    {
        return delegateViewRoot.getFacets();
    }

    public UIComponent getFacet(String name)
    {
        return delegateViewRoot.getFacet(name);
    }

    public Iterator getFacetsAndChildren()
    {
        return delegateViewRoot.getFacetsAndChildren();
    }

    public void broadcast(FacesEvent event)
        throws AbortProcessingException
    {
        delegateViewRoot.broadcast(event);
    }

    public void decode(FacesContext context)
    {
        delegateViewRoot.decode(context);
    }

    public void encodeChildren(FacesContext context)
        throws IOException
    {
        delegateViewRoot.encodeChildren(context);
    }

    public void encodeEnd(FacesContext context)
        throws IOException
    {
        delegateViewRoot.encodeEnd(context);
    }

    public Object processSaveState(FacesContext context)
    {
        return delegateViewRoot.processSaveState(context);
    }

    public void processRestoreState(FacesContext context, Object state)
    {
        delegateViewRoot.processRestoreState(context, state);
    }

    public boolean isTransient()
    {
        return delegateViewRoot.isTransient();
    }

    public void setTransient(boolean transientFlag)
    {
        delegateViewRoot.setTransient(transientFlag);
    }

    public void setRendered(boolean rendered)
    {
        delegateViewRoot.setRendered(rendered);
    }

    public boolean isRendered()
    {
        return delegateViewRoot.isRendered();
    }

    public void setRendererType(String rendererType)
    {
        if (delegateViewRoot == null)
        {
            // the MyFaces 1.2 implmemenation of UIViewRoot calls setRendererType(null) from within the
            // constructor. This breaks the decorator pattern as delegateViewRoot has not been called yet.
            // Since every instance of UIViewRoot will issue this call it doesn't matter if we just
            // discard this request.
            return;
        }

        delegateViewRoot.setRendererType(rendererType);
    }

    public String getRendererType()
    {
        return delegateViewRoot.getRendererType();
    }

}
