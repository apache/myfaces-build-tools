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
 * @JSFComponent
 *   name = "s:inputTextAjax"
 *   class = "org.apache.myfaces.custom.inputAjax.HtmlInputTextAjax"
 *   superClass = "org.apache.myfaces.custom.inputAjax.AbstractHtmlInputTextAjax"
 *   tagClass = "org.apache.myfaces.custom.inputAjax.HtmlInputTextAjaxTag"
 *   
 * User: treeder
 * Date: Oct 28, 2005
 * Time: 7:48:57 PM
 */
public abstract class AbstractHtmlInputTextAjax extends HtmlInputText implements DeprecatedAjaxComponent, AjaxCallbacks
{
    private static final Log log = LogFactory.getLog(AbstractHtmlInputTextAjax.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.InputTextAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.InputTextAjax";

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

    /**
     * @JSFProperty
     */
    public abstract String getOnSuccess();

    /**
     * @JSFProperty
     */
    public abstract String getOnFailure();

    /**
     * @JSFProperty
     */
    public abstract String getOnStart();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract Boolean getShowOkButton();

    /**
     * @JSFProperty
     */
    public abstract String getOkText();

    /**
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract Boolean getShowCancelButton();

    /**
     * @JSFProperty
     */
    public abstract String getCancelText();

    /**
     * @JSFProperty
     */
    public abstract String getErrorStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getErrorStyle();
    
}
