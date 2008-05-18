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
import org.apache.myfaces.component.html.ext.HtmlCommandButton;
import org.apache.myfaces.custom.ajax.AjaxCallbacks;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.ajax.api.DeprecatedAjaxComponent;

/**
 * @JSFComponent
 *   name = "s:commandButtonAjax"
 *   class = "org.apache.myfaces.custom.inputAjax.HtmlCommandButtonAjax"
 *   superClass = "org.apache.myfaces.custom.inputAjax.AbstractHtmlCommandButtonAjax"
 *   tagClass = "org.apache.myfaces.custom.inputAjax.HtmlCommandButtonAjaxTag"
 *   
 * User: Travis Reeder
 * Date: Mar 22, 2006
 * Time: 4:37:53 PM
 */
public abstract class AbstractHtmlCommandButtonAjax extends HtmlCommandButton implements DeprecatedAjaxComponent, AjaxCallbacks
{
    private static final Log log = LogFactory.getLog(HtmlInputTextAjax.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandButtonAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.CommandButtonAjax";

    public AbstractHtmlCommandButtonAjax()
    {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
        setType("button"); // we don't want this to submit
    }

    /**
     * Decode ajax request and apply value changes
     *
     * @param context
     */
    public void decodeAjax(FacesContext context)
    {
        log.debug("entering HtmlCommandButtonAjax.decodeAjax");

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
        log.debug("encodeAjax in HtmlCommandButtonAjax");
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
     */
    public abstract String getErrorStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getErrorStyle();
    
}
