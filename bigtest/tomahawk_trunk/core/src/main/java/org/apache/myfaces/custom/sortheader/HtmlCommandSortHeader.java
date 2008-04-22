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
package org.apache.myfaces.custom.sortheader;

import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.component.html.ext.HtmlDataTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCommandSortHeader
        extends HtmlCommandLink
{
    private static final Log log = LogFactory.getLog(HtmlCommandSortHeader.class);

    /*
    public boolean isImmediate()
    {
        return true;
    }
    */

    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        if (event instanceof ActionEvent)
        {
            HtmlDataTable dataTable = findParentDataTable();
            if (dataTable == null)
            {
                log.error("CommandSortHeader has no MyFacesHtmlDataTable parent");
            }
            else
            {
                String colName = getColumnName();                                
                String currentSortColumn = dataTable.getSortColumn();                                
                
                boolean currentAscending = dataTable.isSortAscending();
                
                if (colName.equals(currentSortColumn))
                {
                    String propName = getPropertyName();                       
                    if (propName != null)
                        dataTable.setSortProperty(getPropertyName());                        
                    
                    dataTable.setSortColumn(getColumnName()); 
                    dataTable.setSortAscending(!currentAscending);
                }
                else
                {
                    dataTable.setSortProperty(getPropertyName());
                    dataTable.setSortColumn(getColumnName());
                    dataTable.setSortAscending(true);
                }
            }
        }
        super.broadcast(event);
    }       

    public HtmlDataTable findParentDataTable()
    {
        UIComponent parent = getParent();
        while (parent != null)
        {
            if (parent instanceof HtmlDataTable)
            {
                return (HtmlDataTable)parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);
        values[1] = _columnName;
        values[2] = _arrow;
        values[3] = _propertyName;
        
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _columnName = (String)values[1];
        _arrow      = (Boolean)values[2];
        _propertyName = (String)values[3];
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandSortHeader";
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SortHeader";

    private String _columnName = null;
    private String _propertyName = null;
    private Boolean _arrow = null;

    public HtmlCommandSortHeader()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setColumnName(String columnName)
    {
        _columnName = columnName;
    }

    public String getColumnName()
    {
        if (_columnName != null) return _columnName;
        ValueBinding vb = getValueBinding("columnName");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }
    
    public void setPropertyName(String propertyName)
    {
        _propertyName = propertyName;
    }

    public String getPropertyName()
    {
        if (_propertyName != null) return _propertyName;
        ValueBinding vb = getValueBinding("propertyName");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setArrow(boolean arrow)
    {
        _arrow = Boolean.valueOf(arrow);
    }

    public boolean isArrow()
    {
        if (_arrow != null) return _arrow.booleanValue();
        ValueBinding vb = getValueBinding("arrow");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : false;
    }

    //------------------ GENERATED CODE END ---------------------------------------
}
