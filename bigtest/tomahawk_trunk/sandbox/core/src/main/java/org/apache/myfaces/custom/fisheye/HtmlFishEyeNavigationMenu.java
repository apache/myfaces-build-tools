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
package org.apache.myfaces.custom.fisheye;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import java.util.Iterator;

/**
 * A Mac OSX-style toolbar, using the DOJO toolkit.
 * 
 * @see <a href="http://dojotoolkit.org/">http://dojotoolkit.org/</a>
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlFishEyeNavigationMenu extends UIData
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.FishEyeList";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.FishEyeList";

    public static final String EDGE_BOTTOM = "bottom";
    public static final String EDGE_CENTER = "center";
    public static final String EDGE_LEFT = "left";
    public static final String EDGE_RIGHT = "right";
    public static final String EDGE_TOP = "top";
    public static final String HORIZONTAL_ORIENTATION = "horizontal";
    public static final String VERTICAL_ORIENTATION = "vertical";
    private static final String NODE_STAMP_FACET_NAME = "nodeStamp";

    private String _attachEdge;
    private Boolean _conservativeTrigger;
    private Integer _effectUnits;
    private Integer _itemHeight;
    private Integer _itemMaxHeight;
    private Integer _itemMaxWidth;
    private Integer _itemPadding;
    private Integer _itemWidth;
    private String _labelEdge;
    private String _orientation;

    private Integer _visibleWindow = null;

    public HtmlFishEyeNavigationMenu()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void processDecodes(FacesContext context) {
        super.processDecodes(context);
        int first = getFirst();
        int rows = getRows();
        int last;
        if (rows == 0)
        {
            last = getRowCount();
        }
        else
        {
            last = first + rows;
        }
        for (int rowIndex = first; last==-1 || rowIndex < last; rowIndex++)
        {
            setRowIndex(rowIndex);

            //scrolled past the last row
            if (!isRowAvailable())
                break;

            for (Iterator it = getChildren().iterator(); it.hasNext();)
            {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof FishEyeCommandLink)
                {
                    if (!child.isRendered())
                    {
                        //Column is not visible
                        continue;
                    }
                    child.processDecodes(context);
                }
            }
        }
    }

    public void setVisibleWindow(Integer visibleWindow)
    {
        _visibleWindow = visibleWindow;
    }

    public void setValueBinding(String string, ValueBinding valueBinding) {
        super.setValueBinding(string, valueBinding);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Integer getVisibleWindow()
    {
        if (_visibleWindow != null) return _visibleWindow;
        ValueBinding vb = getValueBinding("visibleWindow");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }

    public UIComponent getNodeStamp()
    {
        return (UIComponent) getFacets().get(NODE_STAMP_FACET_NAME);
    }

    public String getAttachEdge()
    {
        if (_attachEdge != null)
        {
            return _attachEdge;
        }
        ValueBinding vb = getValueBinding("attachEdge");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;

    }

    public Boolean getConservativeTrigger()
    {
        if (_conservativeTrigger != null)
        {
            return _conservativeTrigger;
        }
        ValueBinding vb = getValueBinding("conservativeTrigger");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public Integer getEffectUnits()
    {
        if (_effectUnits != null)
        {
            return _effectUnits;
        }
        ValueBinding vb = getValueBinding("effectUnits");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public Integer getItemHeight()
    {
        if (_itemHeight != null)
        {
            return _itemHeight;
        }
        ValueBinding vb = getValueBinding("itemHeight");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public Integer getItemMaxHeight()
    {
        if (_itemMaxHeight != null)
        {
            return _itemMaxHeight;
        }
        ValueBinding vb = getValueBinding("itemMaxHeight");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public Integer getItemMaxWidth()
    {
        if (_itemMaxWidth != null)
        {
            return _itemMaxWidth;
        }
        ValueBinding vb = getValueBinding("itemMaxWidth");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public Integer getItemPadding()
    {
        if (_itemPadding != null)
        {
            return _itemPadding;
        }
        ValueBinding vb = getValueBinding("itemPadding");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public Integer getItemWidth()
    {
        if (_itemWidth != null)
        {
            return _itemWidth;
        }
        ValueBinding vb = getValueBinding("itemWidth");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public String getLabelEdge()
    {
        if (_labelEdge != null)
        {
            return _labelEdge;
        }
        ValueBinding vb = getValueBinding("labelEdge");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOrientation()
    {
        if (_orientation != null)
        {
            return _orientation;
        }
        ValueBinding vb = getValueBinding("orientation");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public boolean getRendersChildren()
    {
        return true;
    }

    /**
     * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _itemWidth = (Integer) values[1];
        _itemHeight = (Integer) values[2];
        _itemMaxWidth = (Integer) values[3];
        _itemMaxHeight = (Integer) values[4];
        _orientation = (String) values[5];
        _effectUnits = (Integer) values[6];
        _itemPadding = (Integer) values[7];
        _attachEdge = (String) values[8];
        _labelEdge = (String) values[9];
        _conservativeTrigger = (Boolean) values[10];
        _visibleWindow = (Integer)values[11];

    }

    /**
     * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[12];
        values[0] = super.saveState(context);
        values[1] = _itemWidth;
        values[2] = _itemHeight;
        values[3] = _itemMaxWidth;
        values[4] = _itemMaxHeight;
        values[5] = _orientation;
        values[6] = _effectUnits;
        values[7] = _itemPadding;
        values[8] = _attachEdge;
        values[9] = _labelEdge;
        values[10] = _conservativeTrigger;
        values[11] = _visibleWindow;

        return values;
    }

    public void setAttachEdge(String attachEdge)
    {
        this._attachEdge = attachEdge;
    }

    public void setConservativeTrigger(Boolean conservativeTrigger)
    {
        this._conservativeTrigger = conservativeTrigger;
    }

    public void setEffectUnits(Integer effectUnits)
    {
        this._effectUnits = effectUnits;
    }

    public void setItemHeight(Integer itemHeight)
    {
        this._itemHeight = itemHeight;
    }

    public void setItemMaxHeight(Integer itemMaxHeight)
    {
        this._itemMaxHeight = itemMaxHeight;
    }

    public void setItemMaxWidth(Integer itemMaxWidth)
    {
        this._itemMaxWidth = itemMaxWidth;
    }

    public void setItemPadding(Integer itemPadding)
    {
        this._itemPadding = itemPadding;
    }

    public void setItemWidth(Integer itemWidth)
    {
        this._itemWidth = itemWidth;
    }

    public void setLabelEdge(String labelEdge)
    {
        this._labelEdge = labelEdge;
    }

    public void setOrientation(String orientation)
    {
        this._orientation = orientation;
    }
}
