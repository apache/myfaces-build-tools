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

import org.apache.myfaces.taglib.html.ext.HtmlCommandButtonTag;

import javax.faces.component.UIComponent;

/**
 * User: Travis Reeder
 * Date: Mar 22, 2006
 * Time: 4:37:07 PM
 */
public class HtmlCommandButtonAjaxTag extends HtmlCommandButtonTag
{
    public String getComponentType()
    {
        return HtmlCommandButtonAjax.COMPONENT_TYPE;
    }

    /**
     * @return the RendererType String
     */
    public String getRendererType()
    {
        return HtmlCommandButtonAjax.DEFAULT_RENDERER_TYPE;
    }

    public void release()
    {
        super.release();
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
    }

    public void setOnSuccess(String onSuccess) {
        //TODO: please do something here, because it is referenced by the tld
    }
    public void setOnFailure(String onSuccess) {
        //TODO: please do something here, because it is referenced by the tld
    }
    public void setOnStart(String onSuccess) {
        //TODO: please do something here, because it is referenced by the tld
    }

}
