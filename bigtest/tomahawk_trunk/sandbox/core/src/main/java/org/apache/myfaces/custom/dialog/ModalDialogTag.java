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

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Obereder
 * @version 02.09.2006 12:04:26
 */
public class ModalDialogTag extends HtmlComponentTagBase {

    private static final String TAG_PARAM_DIALOG_ATTR = "dialogAttr";
    private static final String TAG_PARAM_DIALOG_ID   = "dialogId";
    private static final String TAG_PARAM_DIALOG_VAR  = "dialogVar";
    private static final String TAG_PARAM_HIDER_IDS   = "hiderIds";
    private static final String TAG_PARAM_WIDGET_ID = "widgetId";
    private static final String TAG_PARAM_WIDGET_VAR = "widgetVar";
	private static final String TAG_PARAM_VIEW_ID = "viewId";
	private static final String TAG_PARAM_DIALOG_TITLE = "dialogTitle";
	private static final String TAG_PARAM_CLOSE_BUTTON = "closeButton";

    private String              _dialogAttr;
    private String              _dialogId;
    private String              _dialogVar;
    private String              _hiderIds;
    private String              _widgetId;
    private String              _widgetVar;
	private String				_viewId;
	private String				_dialogTitle;
	private String				_closeButton;

	public String getComponentType() {
        return ModalDialog.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return ModalDialogRenderer.RENDERER_TYPE;
    }

    public void release() {
        super.release();
        _dialogVar = null;
        _dialogId = null;
        _dialogAttr = null;
        _hiderIds = null;
        _widgetVar = null;
        _widgetId = null;
		_viewId = null;
		_dialogTitle = null;
		_closeButton = null;
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

    public void setWidgetId(String widgetId) {
        _widgetId = widgetId;
    }

    public void setWidgetVar(String widgetVar) {
        _widgetVar = widgetVar;
    }

	public String getViewId()
	{
		return _viewId;
	}

	public void setViewId(String viewId)
	{
		this._viewId = viewId;
	}

	public String getDialogTitle()
	{
		return _dialogTitle;
	}

	public void setDialogTitle(String dialogTitle)
	{
		this._dialogTitle = dialogTitle;
	}

	public String getCloseButton()
	{
		return _closeButton;
	}

	public void setCloseButton(String closeButton)
	{
		this._closeButton = closeButton;
	}

	protected void setProperties(UIComponent uiComponent) {
        super.setProperties(uiComponent);
        super.setStringProperty(uiComponent, TAG_PARAM_DIALOG_VAR, _dialogVar);
        super.setStringProperty(uiComponent, TAG_PARAM_DIALOG_ID, _dialogId);
        super.setStringProperty(uiComponent, TAG_PARAM_DIALOG_ATTR, _dialogAttr);
        super.setStringProperty(uiComponent, TAG_PARAM_HIDER_IDS, _hiderIds);
        super.setStringProperty(uiComponent, TAG_PARAM_WIDGET_VAR, _widgetVar);
        super.setStringProperty(uiComponent, TAG_PARAM_WIDGET_ID, _widgetId);
		super.setStringProperty(uiComponent, TAG_PARAM_VIEW_ID, _viewId);
		super.setStringProperty(uiComponent, TAG_PARAM_DIALOG_TITLE, _dialogTitle);
		super.setBooleanProperty(uiComponent, TAG_PARAM_CLOSE_BUTTON, _closeButton);
	}

}
