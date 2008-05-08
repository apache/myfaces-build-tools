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
package org.apache.myfaces.component.html.ext;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;


/**
 * @JSFComponent
 *   name = "t:commandLink"
 *   class = "org.apache.myfaces.component.html.ext.HtmlCommandLink"
 *   superClass = "org.apache.myfaces.component.html.ext.AbstractHtmlCommandLink"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlCommandLinkTag"
 *   
 * @author Thomas Spiegl (latest modification by $Author$)
 * @author Manfred Geiler
 * @version $Revision$ $Date$
 */
abstract class AbstractHtmlCommandLink
        extends javax.faces.component.html.HtmlCommandLink
        implements UserRoleAware, ForceIdAware
{
        
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCommandLink";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Link";

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }        
    
    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

    /**
     * @JSFProperty
     */
    public abstract String getActionFor();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDisabled();

    /**
     * @JSFProperty
     */
    public abstract String getDisabledStyle();

    /**
     * @JSFProperty
     */
    public abstract String getDisabledStyleClass();

}
