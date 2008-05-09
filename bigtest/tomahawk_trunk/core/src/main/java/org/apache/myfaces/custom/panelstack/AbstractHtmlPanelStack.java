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

import javax.faces.component.html.HtmlPanelGroup;


/**
 * Manage a stack of JSF components and allow for one child component to be choosen for rendering. The behaviour
 * is similar to the CardLayout of Java Swing. Property <code>selectedPanel</code> defines the id of the child
 * to be rendered. If no child panel is selected or if the selected panel can not be found the first child is rendered.
 *
 * @JSFComponent
 *   name = "t:panelStack"
 *   class = "org.apache.myfaces.custom.panelstack.HtmlPanelStack"
 *   superClass = "org.apache.myfaces.custom.panelstack.AbstractHtmlPanelStack"
 *   tagClass = "org.apache.myfaces.custom.panelstack.PanelStackTag"
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlPanelStack extends HtmlPanelGroup
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelStack";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.PanelStack";

    /**
     * @JSFProperty
     */
    public abstract String getSelectedPanel();
    
}
