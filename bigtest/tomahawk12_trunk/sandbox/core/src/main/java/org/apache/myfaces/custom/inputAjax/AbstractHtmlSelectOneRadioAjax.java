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
import org.apache.myfaces.component.html.ext.HtmlSelectOneRadio;
import org.apache.myfaces.custom.ajax.AjaxCallbacks;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.ajax.api.DeprecatedAjaxComponent;

/**
 * Extends standard selectOneRadio allowing for dynamic ajax submitting.
 * 
 * @JSFComponent
 *   name = "s:selectOneRadioAjax"
 *   class = "org.apache.myfaces.custom.inputAjax.HtmlSelectOneRadioAjax"
 *   superClass = "org.apache.myfaces.custom.inputAjax.AbstractHtmlSelectOneRadioAjax"
 *   tagClass = "org.apache.myfaces.custom.inputAjax.HtmlSelectOneRadioAjaxTag"
 *   
 * User: treeder
 * Date: Nov 10, 2005
 * Time: 4:45:09 PM
 */
public abstract class AbstractHtmlSelectOneRadioAjax extends HtmlSelectOneRadio implements DeprecatedAjaxComponent, AjaxCallbacks
{
    private static final Log log = LogFactory.getLog(AbstractHtmlSelectOneRadioAjax.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectOneRadioAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SelectOneRadioAjax";

    public AbstractHtmlSelectOneRadioAjax()
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
        log.debug("entering HtmlSelectOneRadioAjax.decodeAjax");

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
        //log.debug("encodeAjax in HtmlSelectManyCheckbox");
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;
        Renderer renderer = getRenderer(context);

        if (renderer != null && renderer instanceof AjaxRenderer)
        {
            ((AjaxRenderer) renderer).encodeAjax(context, this);

        }
    }

    /**
     * Javascript method to call on successful ajax update
     * 
     * @JSFProperty
     */
    public abstract String getOnSuccess();

    /**
     * Javascript method to call on failed ajax update
     * 
     * @JSFProperty
     */
    public abstract String getOnFailure();

    /**
     * Javascript method to call on start of ajax update
     * 
     * @JSFProperty
     */
    public abstract String getOnStart();
    
}
