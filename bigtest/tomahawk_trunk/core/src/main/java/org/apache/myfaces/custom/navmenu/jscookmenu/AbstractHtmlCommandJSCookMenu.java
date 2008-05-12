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
package org.apache.myfaces.custom.navmenu.jscookmenu;

import javax.faces.component.UICommand;

import org.apache.myfaces.component.LocationAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;

/**
 * Renders a Javascript Menu. Nested NavigationMenuItem(s) are rendered 
 * as Javascript Menu. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:jscookMenu"
 *   class = "org.apache.myfaces.custom.navmenu.jscookmenu.HtmlCommandJSCookMenu"
 *   superClass = "org.apache.myfaces.custom.navmenu.jscookmenu.AbstractHtmlCommandJSCookMenu"
 *   tagClass = "org.apache.myfaces.custom.navmenu.jscookmenu.HtmlJSCookMenuTag"
 * 
 * @JSFJspProperty name = "value" tagExcluded = "true"
 * @JSFJspProperty name = "actionListener" tagExcluded = "true"
 * @JSFJspProperty name = "action" tagExcluded = "true"
 * 
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlCommandJSCookMenu
    extends UICommand
    implements UserRoleAware, LocationAware
{
    //private static final Log log = LogFactory.getLog(HtmlCommandJSCookMenu.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.JSCookMenu";
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.JSCookMenu";
    
    /**
     * @JSFProperty
     *   required = "true"
     */
    public abstract String getLayout();

    /**
     * @JSFProperty
     *   required = "true"
     */
    public abstract String getTheme();

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

}
