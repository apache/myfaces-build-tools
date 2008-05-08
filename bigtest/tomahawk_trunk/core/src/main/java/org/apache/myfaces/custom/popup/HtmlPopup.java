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
package org.apache.myfaces.custom.popup;

import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @JSFComponent
 *   name = "t:popup"
 *   tagClass = "org.apache.myfaces.custom.popup.HtmlPopupTag"
 * 
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPopup
        extends UIComponentBase
{
    //private static final Log log = LogFactory.getLog(HtmlPopup.class);

    private static final String POPUP_FACET_NAME            = "popup";

    public void setPopup(UIComponent popup)
    {
        getFacets().put(POPUP_FACET_NAME, popup);
    }

    public UIComponent getPopup()
    {
        return (UIComponent)getFacets().get(POPUP_FACET_NAME);
    }

    public boolean getRendersChildren()
    {
        return true;
    }


    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPopup";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Popup";

    private String _style = null;
    private String _styleClass = null;
    private Integer _displayAtDistanceX = null;
    private Integer _displayAtDistanceY = null;
    private Boolean _closePopupOnExitingElement = null;
    private Boolean _closePopupOnExitingPopup = null;

    public HtmlPopup()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
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

    public void setDisplayAtDistanceX(Integer displayAtDistanceX)
    {
        _displayAtDistanceX = displayAtDistanceX;
    }

    public Integer getDisplayAtDistanceX()
    {
        if (_displayAtDistanceX != null) return _displayAtDistanceX;
        ValueBinding vb = getValueBinding("displayAtDistanceX");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }

    public void setDisplayAtDistanceY(Integer displayAtDistanceY)
    {
        _displayAtDistanceY = displayAtDistanceY;
    }

    public Integer getDisplayAtDistanceY()
    {
        if (_displayAtDistanceY != null) return _displayAtDistanceY;
        ValueBinding vb = getValueBinding("displayAtDistanceY");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }

    public void setClosePopupOnExitingElement(Boolean closePopupOnExitingElement)
    {
        _closePopupOnExitingElement = closePopupOnExitingElement;
    }

    public Boolean getClosePopupOnExitingElement()
    {
        if (_closePopupOnExitingElement != null) return _closePopupOnExitingElement;
        ValueBinding vb = getValueBinding("closePopupOnExitingElement");
        return vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
    }

    public void setClosePopupOnExitingPopup(Boolean closePopupOnExitingPopup)
    {
        _closePopupOnExitingPopup = closePopupOnExitingPopup;
    }

    public Boolean getClosePopupOnExitingPopup()
    {
        if (_closePopupOnExitingPopup != null) return _closePopupOnExitingPopup;
        ValueBinding vb = getValueBinding("closePopupOnExitingPopup");
        return vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
    }



    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[7];
        values[0] = super.saveState(context);
        values[1] = _style;
        values[2] = _styleClass;
        values[3] = _displayAtDistanceX;
        values[4] = _displayAtDistanceY;
        values[5] = _closePopupOnExitingElement;
        values[6] = _closePopupOnExitingPopup;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _style = (String)values[1];
        _styleClass = (String)values[2];
        _displayAtDistanceX = (Integer)values[3];
        _displayAtDistanceY = (Integer)values[4];
        _closePopupOnExitingElement = (Boolean)values[5];
        _closePopupOnExitingPopup = (Boolean)values[6];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
