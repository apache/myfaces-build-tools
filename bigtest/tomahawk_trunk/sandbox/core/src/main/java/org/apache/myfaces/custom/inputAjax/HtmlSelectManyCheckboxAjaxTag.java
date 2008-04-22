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

import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.ajax.AjaxCallbacks;
import org.apache.myfaces.taglib.html.ext.HtmlSelectManyCheckboxTag;

/**
* @author Travis Reeder (latest modification by $Author: svieujot $)
* @version $Revision: 169739 $ $Date: 2005-05-12 02:45:14 +0200 (Thu, 12 May 2005) $
*/
public class HtmlSelectManyCheckboxAjaxTag extends HtmlSelectManyCheckboxTag implements AjaxCallbacks
{
    private String onSuccess;
    private String onFailure;
    private String onStart;

    public void release()
    {
        super.release();
        onSuccess = null;
        onFailure = null;
        onStart = null;
    }
     protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setStringProperty(component, "onSuccess", onSuccess);
        setStringProperty(component, "onFailure", onFailure);
        setStringProperty(component, "onStart", onStart);
    }

     public String getComponentType() {
        return HtmlSelectManyCheckboxAjax.COMPONENT_TYPE;
    }

    /**
     * @return the RendererType String
     */
    public String getRendererType() {
        return HtmlSelectManyCheckboxAjax.DEFAULT_RENDERER_TYPE;
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



}
