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

import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.component.EscapeCapable;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;
import org.apache.myfaces.component.DataProperties;
import org.apache.myfaces.component.DisplayValueOnlyAware;
import org.apache.myfaces.component.EscapeAware;
import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @JSFComponent
 *   name = "t:selectManyMenu"
 *   class = "org.apache.myfaces.component.html.ext.HtmlSelectManyMenu"
 *   superClass = "org.apache.myfaces.component.html.ext.AbstractHtmlSelectManyMenu"
 *   tagClass = "org.apache.myfaces.generated.taglib.html.ext.HtmlSelectManyMenuTag"
 *   
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class AbstractHtmlSelectManyMenu
        extends javax.faces.component.html.HtmlSelectManyMenu
        implements UserRoleAware, DisplayValueOnlyCapable,
        EscapeCapable, DisplayValueOnlyAware, EscapeAware,
        ForceIdAware, DataProperties
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectManyMenu";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Menu";
    
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

    public boolean isSetDisplayValueOnly(){
        return getDisplayValueOnly() != null ? true : false;  
    }
    
    public boolean isDisplayValueOnly(){
        return getDisplayValueOnly() != null ? getDisplayValueOnly().booleanValue() : false;
    }
    
    public void setDisplayValueOnly(boolean displayValueOnly){
        this.setDisplayValueOnly((Boolean) Boolean.valueOf(displayValueOnly));
    }

}
