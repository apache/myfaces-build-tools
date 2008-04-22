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
package org.apache.myfaces.custom.tabbedpane;

import org.apache.myfaces.taglib.html.ext.HtmlPanelGroupTag;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlPanelTabTag
        extends HtmlPanelGroupTag
{
    //private static final Log log = LogFactory.getLog(HtmlPanelTabTag.class);

    public String getComponentType()
    {
        return HtmlPanelTab.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "javax.faces.Group";
    }

    private String _label;
    private String _disabled;

    // User Role support --> already handled by HtmlPanelGroupTag

    public void release() {
        super.release();
        _label=null;
        _disabled=null;
    }
    
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "label", _label);
        setBooleanProperty(component, "disabled", _disabled);
    }

    public void setLabel(String label)
    {
        _label = label;
    }
    
    public void setDisabled(String disabled)
    {
        _disabled = disabled;
    }
}
