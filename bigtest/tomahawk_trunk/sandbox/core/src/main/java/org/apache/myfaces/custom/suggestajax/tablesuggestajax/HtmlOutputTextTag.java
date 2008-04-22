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
package org.apache.myfaces.custom.suggestajax.tablesuggestajax;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;

/**
 * @author Gerald Muellan
 *         Date: 15.02.2006
 *         Time: 13:40:43
 */
public class HtmlOutputTextTag extends org.apache.myfaces.taglib.html.ext.HtmlOutputTextTag
{
    private String _for;
    private String _forValue;
    private String _label;

    public String getComponentType() {
        return HtmlOutputText.COMPONENT_TYPE;
    }

    public void release() {

        super.release();

        _for = null;
        _label = null;
        _forValue = null;
    }

    protected void setProperties(UIComponent component) {

        super.setProperties(component);

        setStringProperty(component, JSFAttr.FOR_ATTR, _for);
        setStringProperty(component, "label", _label);
        setStringProperty(component, "forValue", _forValue);
    }

    public void setFor(String aFor)
    {
        _for = aFor;
    }

    public void setLabel(String label)
    {
        _label = label;
    }


    public void setForValue(String forValue)
    {
        _forValue = forValue;
    }
}
