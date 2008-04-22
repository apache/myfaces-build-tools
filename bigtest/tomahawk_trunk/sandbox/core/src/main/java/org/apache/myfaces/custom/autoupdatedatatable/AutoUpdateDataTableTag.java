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
package org.apache.myfaces.custom.autoupdatedatatable;

import javax.faces.component.UIComponent;

import org.apache.myfaces.taglib.html.ext.HtmlDataTableTag;

/**
 * @author J&ouml;rg Artaker
 * @author Thomas Huber
 * @version $Revision: $ $Date: $
 *          <p/>
 *          $Log: $
 */
public class AutoUpdateDataTableTag extends HtmlDataTableTag{

    private String _frequency;
    private String _onSuccess;


    /**
     * @param frequency String
     */
    public void setFrequency(String frequency) {
        _frequency = frequency;
    }

    public String getOnSuccess() {
        return _onSuccess;
    }

    public void setOnSuccess(String _onSuccess) {
        this._onSuccess = _onSuccess;
    }

    /**
     * @return the ComponentType String
     */
    public String getComponentType() {
        return AutoUpdateDataTable.COMPONENT_TYPE;
    }

    /**
     * @return the RendererType String
     */
    public String getRendererType() {
        return AutoUpdateDataTable.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        _frequency = null;
        _onSuccess = null;
    }

    /**
     * @param component UIComponent
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setStringProperty(component, "frequency", _frequency);
        setStringProperty(component, "onSuccess", _onSuccess);
    }
}
