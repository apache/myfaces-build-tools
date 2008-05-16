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

package org.apache.myfaces.custom.dialog;

import org.apache.myfaces.custom.dojo.DojoWidget;

import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * 
 * @JSFComponent
 *   name = "s:modalDialog"
 *   tagClass = "org.apache.myfaces.custom.dialog.ModalDialogTag"
 *   
 *
 */
public class ModalDialog extends UIPanel implements DojoWidget {

    public static final String COMPONENT_TYPE = "org.apache.myfaces.ModalDialog";

    private String _dialogAttr;
    private String _dialogId;
    private String _dialogVar;
    private String _hiderIds;

    private String _style;
    private String _styleClass;

    private String _widgetId = null;

	private String _viewId;

	private String _dialogTitle;
	private Boolean _closeButton;

	public String getDialogAttr() {
        if(_dialogAttr != null)
            return _dialogAttr;
        ValueBinding vb = getValueBinding("dialogAttr");
        return vb != null ? (String)vb.getValue(getFacesContext()) :null;
	}

	public String getDialogId() {
        if(_dialogId != null)
            return _dialogId;
        ValueBinding vb = getValueBinding("dialogId");
        return vb != null ? (String)vb.getValue(getFacesContext()) :null;
	}


    public String getDialogVar() {
        if(_dialogVar != null)
            return _dialogVar;
        ValueBinding vb = getValueBinding("dialogVar");
        return vb != null ? (String)vb.getValue(getFacesContext()) :null;
	}

	public String getHiderIds() {
        if(_hiderIds != null)
            return _hiderIds;
        ValueBinding vb = getValueBinding("hiderIds");
        return vb != null ? (String)vb.getValue(getFacesContext()) :null;
	}

	public String getViewId() {
        if(_viewId != null)
            return _viewId;
        ValueBinding vb = getValueBinding("viewId");
        return vb != null ? (String)vb.getValue(getFacesContext()) :null;
	}

	//@Override
	public boolean getRendersChildren() {
    	return true;
	}

	public String getStyle() {
        if(_style != null)
            return _style;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

	public String getStyleClass() {
        if(_styleClass != null)
            return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

	public String getWidgetId()
    {
        if (_widgetId != null) return _widgetId;
        ValueBinding vb = getValueBinding("widgetId");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

	public String getDialogTitle()
    {
        if (_dialogTitle != null) return _dialogTitle;
        ValueBinding vb = getValueBinding("dialogTitle");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

	public Boolean getCloseButton()
    {
        if (_closeButton != null) return _closeButton;

		ValueBinding vb = getValueBinding("closeButton");
        return (Boolean) (vb != null ? vb.getValue(getFacesContext()) : null);
    }

	public String getWidgetVar() {
        return getDialogVar();
    }

	public void restoreState(FacesContext facesContext, Object object) {
        Object[] values = (Object[]) object;
        super.restoreState(facesContext, values[0]);
        _dialogVar = (String) values[1];
        _dialogId = (String) values[2];
        _dialogAttr = (String) values[3];
        _hiderIds = (String) values[4];
        _style = (String) values[5];
        _styleClass = (String) values[6];
        ////restorestate widgetId begin
        _widgetId = (String)values[7];
        ////restorestate widgetId end
		_viewId = (String) values[8];
		_dialogTitle = (String) values[9];
		_closeButton = (Boolean) values[10];
	}

	public Object saveState(FacesContext facesContext) {
        return new Object[]
		{
			super.saveState(facesContext),
			_dialogVar,
			_dialogId,
			_dialogAttr,
			_hiderIds,
			_style,
			_styleClass,
			////savestate widgetId begin
			_widgetId,
			////savestate widgetId end
			_viewId,
			_dialogTitle,
			_closeButton
		};
    }

    public void setDialogAttr(String dialogAttr) {
		_dialogAttr = dialogAttr;
	}

    public void setDialogId(String dialogId) {
		_dialogId = dialogId;
	}

    public void setDialogVar(String dialogVar) {
		_dialogVar = dialogVar;
	}

    public void setHiderIds(String hiderIds) {
		_hiderIds = hiderIds;
	}

    public void setStyle(String style) {
        _style = style;
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    public void setWidgetId(String widgetId)
    {
        _widgetId = widgetId;
    }

	public void setViewId(String viewId)
	{
		this._viewId = viewId;
	}

	public void setDialogTitle(String dialogTitle)
	{
		this._dialogTitle = dialogTitle;
	}

	public void setCloseButton(Boolean closeButton)
	{
		this._closeButton = closeButton;
	}

	public void setWidgetVar(String widgetVar) {
       setDialogVar(widgetVar);
    }
}
