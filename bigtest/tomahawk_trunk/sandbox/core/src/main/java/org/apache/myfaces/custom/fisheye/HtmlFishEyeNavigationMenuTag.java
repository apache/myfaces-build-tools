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
import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

/**
 * JSP Tag for the FishEyeList component
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlFishEyeNavigationMenuTag extends UIComponentTagBase
{
    private String _attachEdge;
    private String _conservativeTrigger;
    private String _effectUnits;
    private String _itemHeight;
    private String _itemMaxHeight;
    private String _itemMaxWidth;
    private String _itemPadding;
    private String _itemWidth;
    private String _labelEdge;
    private String _orientation;
    private String _visibleWindow;
    private String _var;
    private String _immediate;

    public void setValue(String value) {
        super.setValue(value);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static final String TAG_PARAM_VisibleWindow = "visibleWindow";

    public String getComponentType()
    {
        return HtmlFishEyeNavigationMenu.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return HtmlFishEyeNavigationMenuRenderer.RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, "attachedEdge", _attachEdge);
        setIntegerProperty(component, "effectUnits", _effectUnits);
        setIntegerProperty(component, "itemHeight", _itemHeight);
        setIntegerProperty(component, "itemMaxHeight", _itemMaxHeight);
        setIntegerProperty(component, "itemMaxWidth", _itemMaxWidth);
        setIntegerProperty(component, "itemPadding", _itemPadding);
        setIntegerProperty(component, "itemWidth", _itemWidth);
        setStringProperty(component, "labelEdge", _labelEdge);
        setStringProperty(component, "orientation", _orientation);
        setBooleanProperty(component, "conservativeTrigger", _conservativeTrigger);
        setIntegerProperty(component, "visibleWindow", _visibleWindow);
        setStringProperty(component, "var", _var);
    }

    public void release()
    {
        super.release();
        _attachEdge = null;
        _effectUnits = null;
        _itemHeight = null;
        _itemMaxHeight = null;
        _itemMaxWidth = null;
        _itemPadding = null;
        _itemWidth = null;
        _labelEdge = null;
        _orientation = null;
        _visibleWindow = null;
        _var = null;
    }

    public void setVisibleWindow(String visibleWindow)
    {
        _visibleWindow = visibleWindow;
    }

    public String getConservativeTrigger()
    {
        return _conservativeTrigger;
    }

    public void setAttachEdge(String attachEdge)
    {
        this._attachEdge = attachEdge;
    }

    public void setConservativeTrigger(String conservativeTrigger)
    {
        this._conservativeTrigger = conservativeTrigger;
    }

    public void setEffectUnits(String effectUnits)
    {
        this._effectUnits = effectUnits;
    }

    public void setItemHeight(String itemHeight)
    {
        this._itemHeight = itemHeight;
    }

    public void setItemMaxHeight(String itemMaxHeight)
    {
        this._itemMaxHeight = itemMaxHeight;
    }

    public void setItemMaxWidth(String itemMaxWidth)
    {
        this._itemMaxWidth = itemMaxWidth;
    }

    public void setItemPadding(String itemPadding)
    {
        this._itemPadding = itemPadding;
    }

    public void setItemWidth(String itemWidth)
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

    public void setVar(String var)
    {
        _var = var;
    }
}
