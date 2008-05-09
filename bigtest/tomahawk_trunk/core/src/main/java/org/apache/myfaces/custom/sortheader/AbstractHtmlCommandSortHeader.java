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

import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.component.html.ext.HtmlDataTable;

/**
 * @JSFComponent
 *   name = "t:commandSortHeader"
 *   class = "org.apache.myfaces.custom.sortheader.HtmlCommandSortHeader"
 *   superClass = "org.apache.myfaces.custom.sortheader.AbstractHtmlCommandSortHeader"
 *   tagClass = "org.apache.myfaces.custom.sortheader.HtmlCommandSortHeaderTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlCommandSortHeader
        extends HtmlCommandLink
{
    private static final Log log = LogFactory.getLog(AbstractHtmlCommandSortHeader.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandSortHeader";
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SortHeader";

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

    /**
     * @JSFProperty
     */
    public abstract String getColumnName();

    /**
     * @JSFProperty
     */
    public abstract String getPropertyName();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isArrow();
    
}
