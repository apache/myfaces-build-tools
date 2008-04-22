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

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.custom.ajax.AjaxCallbacks;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.ajax.api.DeprecatedAjaxComponent;

/**
 * User: treeder
 * Date: Oct 28, 2005
 * Time: 7:48:57 PM
 */
public class HtmlInputTextAjax extends HtmlInputText implements DeprecatedAjaxComponent, AjaxCallbacks
{
    private static final Log log = LogFactory.getLog(HtmlInputTextAjax.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.InputTextAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.InputTextAjax";

    private String onSuccess;
    private String onFailure;
    private String onStart;
    private Boolean showOkButton = new Boolean(false);
    private String okText;
    private Boolean showCancelButton = new Boolean(true); // might want to just remove this and always show cancel button
    private String cancelText;
    private String errorStyleClass;
    private String errorStyle;

    public HtmlInputTextAjax()
    {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * Decode ajax request and apply value changes
     *
     * @param context
     */
    public void decodeAjax(FacesContext context)
    {
        log.debug("entering HtmlInputTextAjax.decodeAjax");

        processDecodes(context);
        processValidators(context);
        processUpdates(context);
        //context.getViewRoot().processApplication(context);
        if (log.isDebugEnabled())
        {
            Object valOb = this.getValue();
            log.debug("value object after decodeAjax: " + valOb);
        }

    }

    public void encodeAjax(FacesContext context) throws IOException
    {
        log.debug("encodeAjax in HtmlInputTextAjax");
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;
        Renderer renderer = getRenderer(context);
        if (renderer != null && renderer instanceof AjaxRenderer)
        {
            ((AjaxRenderer) renderer).encodeAjax(context, this);

        }
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[10];
        values[0] = super.saveState(context);
        values[1] = onSuccess;
        values[2] = onFailure;
        values[3] = onStart;
        values[4] = showOkButton;
        values[5] = okText;
        values[6] = showCancelButton;
        values[7] = cancelText;
        values[8] = errorStyleClass;
        values[9] = errorStyle;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        onSuccess = (String) values[1];
        onFailure = (String) values[2];
        onStart = (String) values[3];
        showOkButton = (Boolean) values[4];
        okText = (String) values[5];
        showCancelButton = (Boolean) values[6];
        cancelText = (String) values[7];
        errorStyleClass = (String) values[8];
        errorStyle = (String) values[9];
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

    public Boolean getShowOkButton()
    {
        return showOkButton;
    }

    public void setShowOkButton(Boolean showOkButton)
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

    public Boolean getShowCancelButton()
    {
        return showCancelButton;
    }

    public void setShowCancelButton(Boolean showCancelButton)
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

    public String getErrorStyleClass()
    {
        return errorStyleClass;
    }

    public void setErrorStyleClass(String errorStyleClass)
    {
        this.errorStyleClass = errorStyleClass;
    }

    public String getErrorStyle()
    {
        return errorStyle;
    }

    public void setErrorStyle(String errorStyle)
    {
        this.errorStyle = errorStyle;
    }
}
