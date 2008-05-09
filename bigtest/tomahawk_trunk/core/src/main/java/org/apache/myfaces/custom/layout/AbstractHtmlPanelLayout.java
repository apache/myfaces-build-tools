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
package org.apache.myfaces.custom.layout;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.EventAware;
import org.apache.myfaces.component.PanelProperties;
import org.apache.myfaces.component.UniversalProperties;

/**
 * @JSFComponent
 *   name = "t:panelLayout"
 *   class = "org.apache.myfaces.custom.layout.HtmlPanelLayout"
 *   superClass = "org.apache.myfaces.custom.layout.AbstractHtmlPanelLayout"
 *   tagClass = "org.apache.myfaces.custom.layout.HtmlPanelLayoutTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlPanelLayout
        extends HtmlPanelGroup implements
        AlignProperty, UniversalProperties, EventAware, DataProperties,
        PanelProperties
{
    //private static final Log log = LogFactory.getLog(HtmlPanelLayout.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlPanelLayout";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Layout";

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;
    private static final String DEFAULT_LAYOUT = "classic";
    
    // typesafe facet getters

    /**
     * @JSFFacet
     */
    public UIComponent getHeader()
    {
        return (UIComponent)getFacet("header");
    }

    /**
     * @JSFFacet
     */
    public UIComponent getNavigation()
    {
        return (UIComponent)getFacet("navigation");
    }

    /**
     * @JSFFacet
     */
    public UIComponent getBody()
    {
        return (UIComponent)getFacet("body");
    }

    /**
     * @JSFFacet
     */
    public UIComponent getFooter()
    {
        return (UIComponent)getFacet("footer");
    }

    /**
     * The CSS class to be applied to footer cells.
     * 
     * @JSFProperty
     */
    public abstract String getFooterClass();
    
    /**
     * The CSS class to be applied to header cells.
     * 
     * @JSFProperty
     */
    public abstract String getHeaderClass();

    /**
     * @JSFProperty
     *   defaultValue="classic" 
     */
    public abstract String getLayout();
    
    /**
     * @JSFProperty
     */
    public abstract String getNavigationClass();

    /**
     * @JSFProperty
     */
    public abstract String getBodyClass();

    /**
     * @JSFProperty
     */
    public abstract String getHeaderStyle();

    /**
     * @JSFProperty
     */
    public abstract String getNavigationStyle();

    /**
     * @JSFProperty
     */
    public abstract String getBodyStyle();

    /**
     * @JSFProperty
     */
    public abstract String getFooterStyle();

}
