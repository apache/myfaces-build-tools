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

import org.apache.myfaces.taglib.html.ext.HtmlSelectBooleanCheckboxTag;

import javax.faces.component.UIComponent;

/**
 * User: treeder
 * Date: Nov 21, 2005
 * Time: 8:47:50 AM
 */
public class HtmlSelectBooleanCheckboxAjaxTag extends HtmlSelectBooleanCheckboxTag
{
    private String onSuccess;
    private String onFailure;
    private String onStart;
    private String onImage;
    private String offImage;

    public String getComponentType() {
        return HtmlSelectBooleanCheckboxAjax.COMPONENT_TYPE;
    }

    /**
     * @return the RendererType String
     */
    public String getRendererType() {
        return HtmlSelectBooleanCheckboxAjax.DEFAULT_RENDERER_TYPE;
    }


    public void release()
    {
        super.release();
        onSuccess = null;
        onFailure = null;
        onStart = null;
        onImage = null;
        offImage = null;

    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "onSuccess", onSuccess);
        setStringProperty(component, "onFailure", onFailure);
        setStringProperty(component, "onStart", onStart);
        setStringProperty(component, "onImage", onImage);
        setStringProperty(component, "offImage", offImage);

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
