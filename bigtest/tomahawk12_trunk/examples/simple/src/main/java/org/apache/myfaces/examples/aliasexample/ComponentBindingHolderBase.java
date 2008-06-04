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
package org.apache.myfaces.examples.aliasexample;

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Martin Marinschek (latest modification by $Author: matzew $)
 * @version $Revision: 167718 $ $Date: 2005-03-24 17:47:11 +0100 (Do, 24 Mär 2005) $
 */
public abstract class ComponentBindingHolderBase
{
    protected HtmlPanelGroup _panelGroup;

    public HtmlPanelGroup getPanelGroup()
    {
        if(_panelGroup == null)
        {
            _panelGroup = (HtmlPanelGroup) createComponent(HtmlPanelGroup.COMPONENT_TYPE);
            _panelGroup.getChildren().add(createOutputText());
        }

        return _panelGroup;
    }

    public void setPanelGroup(HtmlPanelGroup panelGroup)
    {
        _panelGroup = panelGroup;
        _panelGroup.getChildren().add(createOutputText());
    }

    protected UIComponent createComponent(String type)
    {
        return FacesContext.getCurrentInstance().getApplication().createComponent(type);
    }

    private HtmlOutputText createOutputText()
    {
        HtmlOutputText text = (HtmlOutputText) createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue(getText());
        return text;
    }

    protected abstract String getText();

}
