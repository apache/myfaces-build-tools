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
package org.apache.myfaces.custom.focus;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

/**
 * @author Rogerio Pereira Araujo (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlFocusTag extends UIComponentTagBase
{
    private static final String FOR_ATTR                = "for";

    private String _for;

    public void release() {

        super.release();
        _for = null;

    }

    public String getComponentType() {

        return HtmlFocus.COMPONENT_TYPE;

    }

    public String getRendererType() {

        return HtmlFocus.DEFAULT_RENDERER_TYPE;

    }

    protected void setProperties(UIComponent component) {

        super.setProperties(component);

        setStringProperty(component, FOR_ATTR, _for);
    }

    public void setFor(String aFor)
    {
        _for = aFor;
    }
}
 