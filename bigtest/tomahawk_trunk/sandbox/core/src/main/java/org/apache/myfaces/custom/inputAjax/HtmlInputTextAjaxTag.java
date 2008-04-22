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

package org.apache.myfaces.custom.inputAjax;

import org.apache.myfaces.taglib.html.ext.HtmlInputTextTag;
import org.apache.myfaces.custom.ajax.AjaxCallbacks;

import javax.faces.component.UIComponent;

/**
 * User: treeder
 * Date: Oct 28, 2005
 * Time: 7:42:08 PM
 */
public class HtmlInputTextAjaxTag extends HtmlInputTextTag implements AjaxCallbacks
{
    private String onSuccess;
    private String onFailure;
    private String onStart;
    private String showOkButton;
    private String okText;
    private String showCancelButton;
    private String cancelText;
    private String errorStyleClass;
    private String errorStyle;

    public String getComponentType() {
        return HtmlInputTextAjax.COMPONENT_TYPE;
    }

    /**
     * @return the RendererType String
     */
    public String getRendererType() {
        return HtmlInputTextAjax.DEFAULT_RENDERER_TYPE;
    }


    public void release()
    {
        super.release();
        onSuccess = null;
        onFailure = null;
        onStart = null;
        showOkButton = null;
        okText = null;
        showCancelButton = null;
        cancelText = null;
        errorStyle = null;
        errorStyleClass = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "onSuccess", onSuccess);
        setStringProperty(component, "onFailure", onFailure);
        setStringProperty(component, "onStart", onStart);
        setBooleanProperty(component, "showOkButton", showOkButton);
        setStringProperty(component, "okText", okText);
        setBooleanProperty(component, "showCancelButton", showCancelButton);
        setStringProperty(component, "cancelText", cancelText);
        setStringProperty(component, "errorStyle", errorStyle);
        setStringProperty(component, "errorStyleClass", errorStyleClass);
    }


    public String getOnSuccess()
    {
        return onSuccess;
    }

    public void setOnSuccess(String onSuccess)
    {
        this.onSuccess = onSuccess;
    }

    public String getOnFailure()
    {
        return onFailure;
    }

    public void setOnFailure(String onFailure)
    {
        this.onFailure = onFailure;
    }

    public String getOnStart()
    {
        return onStart;
    }

    public void setOnStart(String onStart)
    {
        this.onStart = onStart;
    }

    public String getShowOkButton()
    {
        return showOkButton;
    }

    public void setShowOkButton(String showOkButton)
    {
        this.showOkButton = showOkButton;
    }

    public String getOkText()
    {
        return okText;
    }

    public void setOkText(String okText)
    {
        this.okText = okText;
    }

    public String getShowCancelButton()
    {
        return showCancelButton;
    }

    public void setShowCancelButton(String showCancelButton)
    {
        this.showCancelButton = showCancelButton;
    }

    public String getCancelText()
    {
        return cancelText;
    }

    public void setCancelText(String cancelText)
    {
        this.cancelText = cancelText;
    }

    public void setErrorStyleClass(String errorStyleClass)
    {
        this.errorStyleClass = errorStyleClass;
    }

    public void setErrorStyle(String errorStyle)
    {
        this.errorStyle = errorStyle;
    }


}
