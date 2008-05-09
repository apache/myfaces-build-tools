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
package org.apache.myfaces.custom.collapsiblepanel;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.StyleAware;
import org.apache.myfaces.component.UniversalProperties;
import org.apache.myfaces.component.UserRoleAware;

/**
 * @JSFComponent
 *   name = "t:collapsiblePanel"
 *   class = "org.apache.myfaces.custom.collapsiblepanel.HtmlCollapsiblePanel"
 *   superClass = "org.apache.myfaces.custom.collapsiblepanel.AbstractHtmlCollapsiblePanel"    
 *   tagClass = "org.apache.myfaces.custom.collapsiblepanel.HtmlCollapsiblePanelTag"
 * 
 * @author Kalle Korhonen (latest modification by $Author$)
 * @version $Revision$ $Date$
 *
 */
public abstract class AbstractHtmlCollapsiblePanel extends UIInput
     implements StyleAware, UniversalProperties, EventAware,
     UserRoleAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCollapsiblePanel";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.CollapsiblePanel";
        
    private boolean _currentlyCollapsed;
    
    public void setCurrentlyCollapsed(boolean collapsed)
    {
        _currentlyCollapsed = collapsed;
    }

    public boolean isCurrentlyCollapsed()
    {
        return _currentlyCollapsed;
    }
    
    //private static final Log log = LogFactory.getLog(HtmlCollapsiblePanel.class);

    public void processDecodes(FacesContext context)
    {
        if (context == null) throw new NullPointerException("context");

        initialiseVars(context);

        if (!isRendered()) return;

        try
        {
            decode(context);
        }
        catch (RuntimeException e)
        {
            context.renderResponse();
            throw e;
        }

        UIComponent headerComponent = getFacet("header");

        if(headerComponent != null)
        {
            for (Iterator it = headerComponent.getChildren().iterator(); it.hasNext(); )
            {
                UIComponent child = (UIComponent)it.next();

                if(!(child instanceof HtmlHeaderLink))
                {
                    child.processDecodes(context);
                }
            }
        }

        if(isCurrentlyCollapsed())
        {
            UIComponent component = getFacet("closedContent");

            if(component != null)
            {
                component.processDecodes(context);
            }
        }
        else
        {
            for (Iterator it = getChildren().iterator(); it.hasNext(); )
            {
                UIComponent child = (UIComponent)it.next();
                child.processDecodes(context);
            }
        }
        
        removeVars(context);
    }

    public String getClientId(FacesContext context)
    {
        return super.getClientId(context);
    }

    public void processUpdates(FacesContext context)
    {
        initialiseVars(context);

        super.processUpdates(context);

        removeVars(context);
    }

    private void initialiseVars(FacesContext context)
    {
        if(getVar()!=null)
        {
            context.getExternalContext().getRequestMap().put(getVar(),
                            Boolean.valueOf(isCollapsed()));
        }

        if(getTitleVar()!=null)
        {
            context.getExternalContext().getRequestMap().put(getTitleVar(),
                            getTitle());
        }
    }

    private void removeVars(FacesContext context)
    {
        if(getVar()!=null)
        {
            context.getExternalContext().getRequestMap().remove(getVar());
        }

        if(getTitleVar()!=null)
        {
            context.getExternalContext().getRequestMap().remove(getTitleVar());
        }
    }

    public void processValidators(FacesContext context)
    {
        initialiseVars(context);

        super.processValidators(context);

        removeVars(context);
    }

    public void encodeChildren(FacesContext context) throws IOException
    {
        initialiseVars(context);

        super.encodeChildren(context);

        removeVars(context);
    }

    public void updateModel(FacesContext context)
    {
        super.updateModel(context);
    }

    public boolean isCollapsed()
    {
        return isCollapsed(getValue());
    }

    public static boolean isCollapsed(Object collapsedValue)
    {
        Object value = collapsedValue;

        if(value instanceof Boolean)
        {
            return ((Boolean) value).booleanValue();
        }
        else if (value instanceof String)
        {
            return Boolean.valueOf((String) value).booleanValue();
        }

        return true;
    }

    /**
     * @JSFProperty
     */
    public abstract String getVar();

    /**
     * @JSFProperty
     */    
    public abstract String getTitleVar();
    
    
}
