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
package org.apache.myfaces.custom.datalist;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @JSFComponent
 *   name = "t:dataList"
 *   class = "org.apache.myfaces.custom.datalist.HtmlDataList"
 *   superClass = "org.apache.myfaces.custom.datalist.AbstractHtmlDataList"
 *   tagClass = "org.apache.myfaces.custom.datalist.HtmlDataListTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlDataList
        extends org.apache.myfaces.component.html.ext.HtmlDataTableHack
        implements UserRoleAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlDataList";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.List";
        
	private static Log log = LogFactory.getLog(AbstractHtmlDataList.class);
    private static final int PROCESS_DECODES = 1;
    private static final int PROCESS_VALIDATORS = 2; // not currently in use
    private static final int PROCESS_UPDATES = 3; // not currently in use

    /**
     * Throws NullPointerException if context is null.  Sets row index to -1, 
     * calls processChildren, sets row index to -1.
     */

    public void processDecodes(FacesContext context)
    {

        if (context == null)
            throw new NullPointerException("context");
        if (!isRendered())
            return;

        setRowIndex(-1);
        processChildren(context, PROCESS_DECODES);
        setRowIndex(-1);
    }

    public void processUpdates(FacesContext context)
    {
        if (context == null)
            throw new NullPointerException("context");
        if (!isRendered())
            return;

        setRowIndex(-1);
        processChildren(context, PROCESS_UPDATES);
        setRowIndex(-1);
    }

    public void processValidators(FacesContext context)
    {
        if (context == null)
            throw new NullPointerException("context");
        if (!isRendered())
            return;

        setRowIndex(-1);
        processChildren(context, PROCESS_VALIDATORS);
        setRowIndex(-1);    }

    /**
     * Iterates over all children, processes each according to the specified 
     * process action if the child is rendered.
     */

    public void processChildren(FacesContext context, int processAction)
    {
        // use this method for processing other than decode ?
        int first = getFirst();
        int rows = getRows();
        int last = rows == 0 ? getRowCount() : first + rows;

        if (log.isTraceEnabled())
        {
            log.trace("processing " + getChildCount()
                            + " children: starting at " + first
                            + ", ending at " + last);
        }

        for (int rowIndex = first; last == -1 || rowIndex < last; rowIndex++)
        {

            setRowIndex(rowIndex);

            if (!isRowAvailable())
            {
                if (log.isTraceEnabled())
                {
                    log.trace("scrolled past the last row, aborting");
                }
                break;
            }

            for (Iterator it = getChildren().iterator(); it.hasNext();)
            {
                UIComponent child = (UIComponent) it.next();
                if (child.isRendered())
                    process(context, child, processAction);
            }
        }
    }

    /**
     * Copy and pasted from UIData in order to maintain binary compatibility.
     */

    private void process(FacesContext context, UIComponent component,
            int processAction)
    {
        switch (processAction)
        {
        case PROCESS_DECODES:
            component.processDecodes(context);
            break;
        case PROCESS_VALIDATORS:
            component.processValidators(context);
            break;
        case PROCESS_UPDATES:
            component.processUpdates(context);
            break;
        }
    }
    
    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(
                this,getRenderer(context),context);

        if(clientId==null)
        {
            return super.getClientId(context);
        }
        else
        {
            int rowIndex = getRowIndex();
            if (rowIndex == -1)
            {
                return clientId;
            }
            else
            {
                return clientId + "_" + rowIndex;
            }
        }
    }

    public void setRowIndex(int rowIndex)
    {
        super.setRowIndex(rowIndex);
        String rowIndexVar = getRowIndexVar();
        String rowCountVar = getRowCountVar();
        if (rowIndexVar != null || rowCountVar != null)
        {
            Map requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
            if (rowIndex >= 0)
            {
                //regular row index, update request scope variables
                if (rowIndexVar != null)
                {
                    requestMap.put(getRowIndexVar(), new Integer(rowIndex));
                }
                if (rowCountVar != null)
                {
                    requestMap.put(getRowCountVar(), new Integer(getRowCount()));
                }
            }
            else
            {
                //rowIndex == -1 means end of loop --> remove request scope variables
                if (rowIndexVar != null)
                {
                    requestMap.remove(getRowIndexVar());
                }
                if (rowCountVar != null)
                {
                    requestMap.remove(getRowCountVar());
                }
            }
        }
    }
    
    /**
     * @JSFProperty
     */
    public abstract String getRowCountVar();
    
    /**
     * @JSFProperty
     */
    public abstract String getRowIndexVar();
    
    /**
     * @JSFProperty
     */
    public abstract String getLayout();
    
    /**
     * @JSFProperty
     */
    public abstract String getItemStyleClass();
    
}
