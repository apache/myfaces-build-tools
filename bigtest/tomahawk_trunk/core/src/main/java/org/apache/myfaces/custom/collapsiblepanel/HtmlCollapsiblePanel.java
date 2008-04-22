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

import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Kalle Korhonen (latest modification by $Author$)
 * 
 * @version $Revision$ $Date$
 *
 */
public class HtmlCollapsiblePanel extends UIInput
{
    private boolean _currentlyCollapsed;
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

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCollapsiblePanel";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.CollapsiblePanel";

    private String _var = null;
    private String _title = null;
    private String _titleVar = null;
    private String _style = null;
    private String _styleClass = null;

    public HtmlCollapsiblePanel()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getTitle()
    {
        if (_title != null) return _title;
        ValueBinding vb = getValueBinding("title");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    public String getStyle()
    {
        if (_style != null) return _style;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setStyle(String style)
    {
        _style = style;
    }

    public String getStyleClass()
    {
        if (_styleClass != null) return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setStyleClass(String styleClass)
    {
        _styleClass = styleClass;
    }

    public void setVar(String var)
    {
        _var = var;
    }

    public String getVar()
    {
        if (_var != null) return _var;
        ValueBinding vb = getValueBinding("var");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setTitleVar(String titleVar)
    {
        _titleVar = titleVar;
    }

    public String getTitleVar()
    {
        if (_titleVar != null) return _titleVar;
        ValueBinding vb = getValueBinding("titleVar");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    //------------------ GENERATED CODE END ---------------------------------------

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[6];
        values[0] = super.saveState(context);
        values[1] = _title;
        values[2] = _var;
        values[3] = _titleVar;
        values[4] = _style;
        values[5] = _styleClass;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _title = (String)values[1];
        _var = (String)values[2];
        _titleVar = (String) values[3];
        _style = (String)values[4];
        _styleClass = (String)values[5];
    }

    public void setCurrentlyCollapsed(boolean collapsed)
    {
        _currentlyCollapsed = collapsed;
    }

    public boolean isCurrentlyCollapsed()
    {
        return _currentlyCollapsed;
    }
}
