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
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlDataList
        extends org.apache.myfaces.component.html.ext.HtmlDataTableHack
{
	private static Log log = LogFactory.getLog(HtmlDataList.class);
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


    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlDataList";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.List";

    private String _layout = null;
    private String _rowIndexVar = null;
    private String _rowCountVar = null;
    private String _onclick = null;
    private String _ondblclick = null;
    private String _onkeydown = null;
    private String _onkeypress = null;
    private String _onkeyup = null;
    private String _onmousedown = null;
    private String _onmousemove = null;
    private String _onmouseout = null;
    private String _onmouseover = null;
    private String _onmouseup = null;
    private String _style = null;
    private String _styleClass = null;
    private String _itemStyleClass = null;
    private String _title = null;

    public HtmlDataList()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }


    public void setLayout(String layout)
    {
        _layout = layout;
    }

    public String getLayout()
    {
        if (_layout != null) return _layout;
        ValueBinding vb = getValueBinding("layout");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setRowIndexVar(String rowIndexVar)
    {
        _rowIndexVar = rowIndexVar;
    }

    public String getRowIndexVar()
    {
        if (_rowIndexVar != null) return _rowIndexVar;
        ValueBinding vb = getValueBinding("rowIndexVar");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setRowCountVar(String rowCountVar)
    {
        _rowCountVar = rowCountVar;
    }

    public String getRowCountVar()
    {
        if (_rowCountVar != null) return _rowCountVar;
        ValueBinding vb = getValueBinding("rowCountVar");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    public String getOnclick()
    {
        if (_onclick != null) return _onclick;
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    public String getOndblclick()
    {
        if (_ondblclick != null) return _ondblclick;
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnkeydown(String onkeydown)
    {
        _onkeydown = onkeydown;
    }

    public String getOnkeydown()
    {
        if (_onkeydown != null) return _onkeydown;
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    public String getOnkeypress()
    {
        if (_onkeypress != null) return _onkeypress;
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnkeyup(String onkeyup)
    {
        _onkeyup = onkeyup;
    }

    public String getOnkeyup()
    {
        if (_onkeyup != null) return _onkeyup;
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmousedown(String onmousedown)
    {
        _onmousedown = onmousedown;
    }

    public String getOnmousedown()
    {
        if (_onmousedown != null) return _onmousedown;
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmousemove(String onmousemove)
    {
        _onmousemove = onmousemove;
    }

    public String getOnmousemove()
    {
        if (_onmousemove != null) return _onmousemove;
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmouseout(String onmouseout)
    {
        _onmouseout = onmouseout;
    }

    public String getOnmouseout()
    {
        if (_onmouseout != null) return _onmouseout;
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmouseover(String onmouseover)
    {
        _onmouseover = onmouseover;
    }

    public String getOnmouseover()
    {
        if (_onmouseover != null) return _onmouseover;
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setOnmouseup(String onmouseup)
    {
        _onmouseup = onmouseup;
    }

    public String getOnmouseup()
    {
        if (_onmouseup != null) return _onmouseup;
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setStyle(String style)
    {
        _style = style;
    }

    public String getStyle()
    {
        if (_style != null) return _style;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setStyleClass(String styleClass)
    {
        _styleClass = styleClass;
    }

    public String getStyleClass()
    {
        if (_styleClass != null) return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }
    
    public void setItemStyleClass(String itemStyleClass)
    {
        _itemStyleClass = itemStyleClass;
    }

    public String getItemStyleClass()
    {
        if (_itemStyleClass != null) return _itemStyleClass;
        ValueBinding vb = getValueBinding("itemStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }
    
    public void setTitle(String title)
    {
        _title = title;
    }

    public String getTitle()
    {
        if (_title != null) return _title;
        ValueBinding vb = getValueBinding("title");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }



    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[18];
        values[0] = super.saveState(context);
        values[1] = _layout;
        values[2] = _rowIndexVar;
        values[3] = _rowCountVar;
        values[4] = _onclick;
        values[5] = _ondblclick;
        values[6] = _onkeydown;
        values[7] = _onkeypress;
        values[8] = _onkeyup;
        values[9] = _onmousedown;
        values[10] = _onmousemove;
        values[11] = _onmouseout;
        values[12] = _onmouseover;
        values[13] = _onmouseup;
        values[14] = _style;
        values[15] = _styleClass;
        values[16] = _title;
        values[17] = _itemStyleClass;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _layout = (String)values[1];
        _rowIndexVar = (String)values[2];
        _rowCountVar = (String)values[3];
        _onclick = (String)values[4];
        _ondblclick = (String)values[5];
        _onkeydown = (String)values[6];
        _onkeypress = (String)values[7];
        _onkeyup = (String)values[8];
        _onmousedown = (String)values[9];
        _onmousemove = (String)values[10];
        _onmouseout = (String)values[11];
        _onmouseover = (String)values[12];
        _onmouseup = (String)values[13];
        _style = (String)values[14];
        _styleClass = (String)values[15];
        _title = (String)values[16];
        _itemStyleClass = (String)values[17];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
