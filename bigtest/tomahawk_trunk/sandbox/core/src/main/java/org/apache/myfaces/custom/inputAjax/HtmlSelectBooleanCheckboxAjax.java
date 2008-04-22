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
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlSelectBooleanCheckbox;
import org.apache.myfaces.custom.ajax.AjaxCallbacks;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.ajax.api.DeprecatedAjaxComponent;

/**
 * User: treeder
 * Date: Nov 21, 2005
 * Time: 8:47:27 AM
 */
public class HtmlSelectBooleanCheckboxAjax extends HtmlSelectBooleanCheckbox
        implements DeprecatedAjaxComponent, AjaxCallbacks
{

    private static final Log log = LogFactory.getLog(HtmlSelectBooleanCheckboxAjax.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SelectBooleanCheckboxAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.CheckboxAjax";

    private String onSuccess;
    private String onFailure;
    private String onStart;
    private String onImage;
    private String offImage;

    public HtmlSelectBooleanCheckboxAjax()
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
        log.debug("entering HtmlSelectBooleanCheckboxAjax.decodeAjax");

        // Handled differently
        Map requestParams = context.getExternalContext().getRequestParameterMap();
        String checkedStr = (String) requestParams.get("elvalue");
        log.debug("elvalue: " + checkedStr);
        boolean checked = Boolean.valueOf(checkedStr).booleanValue();
        if (checked)
        {
            processDecodes(context);
        }
        else
        {
            // needs special handling
            decodeUISelectBoolean(context, this);
        }
        //System.out.println("SUBMITTED VALUE: " + ((EditableValueHolder) this).getSubmittedValue());
        processValidators(context);
        processUpdates(context);
        //context.getViewRoot().processApplication(context);

        //String elname = (String) requestParams.get("elname");
        //String elvalue = (String) requestParams.get("elvalue");

        if (log.isDebugEnabled())
        {
            Object valOb = this.getValue();
            log.debug("value object after decodeAjax: " + valOb);
        }

    }

    private void decodeUISelectBoolean(FacesContext facesContext, HtmlSelectBooleanCheckboxAjax component)
    {
        if (!(component instanceof EditableValueHolder))
        {
            throw new IllegalArgumentException("Component "
                    + component.getClientId(facesContext)
                    + " is not an EditableValueHolder");
        }
        ((EditableValueHolder) component).setSubmittedValue(Boolean.FALSE);

    }

    public void encodeAjax(FacesContext context) throws IOException
    {
        log.debug("entering HtmlSelectBooleanCheckboxAjax.encodeAjax");
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;
        Renderer renderer = getRenderer(context);

        if (renderer != null && renderer instanceof AjaxRenderer)
        {
            ((AjaxRenderer) renderer).encodeAjax(context, this);
        }
        else
        {
            log.warn("Renderer does not implement AjaxRenderer! " + renderer);
        }
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[6];
        values[0] = super.saveState(context);
        values[1] = onSuccess;
        values[2] = onFailure;
        values[3] = onStart;
        values[4] = onImage;
        values[5] = offImage;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        onSuccess = (String) values[1];
        onFailure = (String) values[2];
        onStart = (String) values[3];
        onImage = (String) values[4];
        offImage = (String) values[5];

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

    public String getOnImage()
    {
        return onImage;
    }

    public void setOnImage(String onImage)
    {
        this.onImage = onImage;
    }

    public String getOffImage()
    {
        return offImage;
    }

    public void setOffImage(String offImage)
    {
        this.offImage = offImage;
    }

}
