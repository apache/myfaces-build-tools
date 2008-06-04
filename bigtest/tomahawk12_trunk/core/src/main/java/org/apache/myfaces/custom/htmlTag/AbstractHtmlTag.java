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
package org.apache.myfaces.custom.htmlTag;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.StyleAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * Creates an arbitrary HTML tag which encloses any child components. 
 * The value attribute specifies the name of the generated tag.
 * 
 * If value is an empty string then no tag will be generated, but 
 * the child components will be rendered. This differs from setting 
 * rendered=false, which prevents child components from being 
 * rendered at all.
 * 
 * There is currently no facility for adding attributes to the 
 * generated html tag other than those explicitly supported by 
 * the attributes on this JSF component.
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:htmlTag"
 *   class = "org.apache.myfaces.custom.htmlTag.HtmlTag"
 *   superClass = "org.apache.myfaces.custom.htmlTag.AbstractHtmlTag"
 *   tagClass = "org.apache.myfaces.custom.htmlTag.HtmlTagTag"
 *   
 * @JSFJspProperty
 *   name = "converter"
 *   returnType = "javax.faces.convert.Converter"
 *   tagExcluded = "true"
 * 
 * @author bdudney (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlTag extends UIOutput 
    implements UserRoleAware, StyleAware, ForceIdAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTag";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTagRenderer";

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this,
                getRenderer(context), context);
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

}