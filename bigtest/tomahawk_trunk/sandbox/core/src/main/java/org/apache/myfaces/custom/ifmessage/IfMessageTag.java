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
package org.apache.myfaces.custom.ifmessage;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentBodyTag;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagUtils;


/**
 * @author Mike Youngstrom (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class IfMessageTag extends UIComponentBodyTag
{
    private String _for;

    public String getComponentType()
    {
        return IfMessage.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.IfMessageRenderer";
    }


    /**
     * @see org.apache.myfaces.taglib.html.HtmlFormTagBase#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        UIComponentTagUtils.setStringProperty(getFacesContext(), component, "for", _for);
    }

    /**
     * @see org.apache.myfaces.taglib.html.HtmlFormTagBase#release()
     */
    public void release()
    {
        super.release();
        _for = null;
    }

    public void setFor(String forValue)
    {
        _for = forValue;
    }

}



