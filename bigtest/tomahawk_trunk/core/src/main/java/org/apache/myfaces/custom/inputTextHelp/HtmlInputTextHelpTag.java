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
package org.apache.myfaces.custom.inputTextHelp;

import org.apache.myfaces.taglib.html.ext.HtmlInputTextTag;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Obereder
 * @version Date: 09.06.2005, 22:16:41
 */
public class HtmlInputTextHelpTag extends HtmlInputTextTag
{
    private String _helpText = null;
    private String _selectText = null;

    public String getComponentType()
    {
        return HtmlInputTextHelp.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlInputTextHelp.DEFAULT_RENDERER_TYPE;
    }

    public void release()
    {
        super.release();

        _helpText = null;
        _selectText = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        
        setStringProperty(component, "helpText", _helpText);
        setBooleanProperty(component, "selectText", _selectText);
    }

    public void setHelpText(String helpText)
    {
        _helpText = helpText;
    }
    public void setSelectText(String selectText)
    {
        _selectText = selectText;
    }
}
