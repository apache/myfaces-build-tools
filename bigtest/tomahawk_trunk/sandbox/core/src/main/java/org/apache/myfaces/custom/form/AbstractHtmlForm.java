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
package org.apache.myfaces.custom.form;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;

/**
 * @JSFComponent
 *   name = "s:form"
 *   class = "org.apache.myfaces.custom.form.HtmlForm"
 *   superClass = "org.apache.myfaces.custom.form.AbstractHtmlForm"
 *   tagClass = "org.apache.myfaces.custom.form.HtmlFormTag"
 *   
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlForm extends javax.faces.component.html.HtmlForm
    implements ForceIdAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlForm";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Form";

    public AbstractHtmlForm()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }

    /**
     * @JSFProperty
     */
    public abstract Integer getPort();

    /**
     * @JSFProperty
     */
    public abstract String getScheme();

    /**
     * @JSFProperty
     */
    public abstract String getServerName();

    /**
     * @JSFProperty
     */
    public abstract String getAction();

    /**
     * @JSFProperty
     */
    public abstract String getMethod();
    
    /**
     * If true, this component will force the use of the specified id when rendering.
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "false"
     *   
     * @return
     */
    public abstract Boolean getForceId();
        
    /**
     *  If false, this component will not append a '[n]' suffix 
     *  (where 'n' is the row index) to components that are 
     *  contained within a "list." This value will be true by 
     *  default and the value will be ignored if the value of 
     *  forceId is false (or not specified.)
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "true"
     *   
     * @return
     */
    public abstract Boolean getForceIdIndex();
        
}
