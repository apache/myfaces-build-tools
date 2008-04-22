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
package org.apache.myfaces.custom.panelstack;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

import javax.faces.component.UIComponent;
import javax.servlet.jsp.JspException;


/**
 * PanelStack tag.
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 */
public class PanelStackTag
        extends UIComponentTagBase
{

    private String selectedPanel;

    public void release() {
        super.release();
        selectedPanel=null;
    }

    public String getComponentType()
    {
        return "org.apache.myfaces.HtmlPanelStack";
    }


    public String getRendererType()
    {
        return "org.apache.myfaces.PanelStack";
    }


    public String getSelectedPanel()
    {
        return selectedPanel;
    }


    public void setSelectedPanel(String selectedPanel)
    {
        this.selectedPanel = selectedPanel;
    }


    public int doStartTag() throws JspException
    {
        return super.doStartTag();
    }


    /**
     * Applies attributes to the tree component
     */
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setStringProperty(component, "selectedPanel", selectedPanel);
    }
}
