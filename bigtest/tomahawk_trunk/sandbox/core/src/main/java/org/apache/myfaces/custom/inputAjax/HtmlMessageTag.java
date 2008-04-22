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

package org.apache.myfaces.custom.inputAjax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * User: treeder
 * Date: Nov 22, 2005
 * Time: 12:10:58 PM
 */
public class HtmlMessageTag extends org.apache.myfaces.taglib.html.ext.HtmlMessageTag
{
    private static final Log log = LogFactory.getLog(HtmlMessageTag.class);


    protected void encodeEnd() throws IOException
    {
        super.encodeEnd();
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.MessageSandbox";
    }


    public void setFor(String aFor)
    {
        super.setFor(aFor);
        String id = getId();
        //UIComponent comp = getComponentInstance();
        log.debug("setFor ID WAS: " + id); // Are these tags reused??  The id at this point is set to the last s:message on the page?

        /*if (id == null)
        {*/
            // default id so client side scripts can use this (ie: ajax), this will obviously break things if someone specifies an id, so please don't specify an id if using Ajax components!

        id = "msgFor_" + getFor();
        log.debug("Setting id on MessageTag: " + id);
        setId(id);
        setForceId("true");
        /*if(comp != null){
            log.debug("SETTING COMP ID");
            comp.setId(id);
        }*/
    //}
        
    }

    protected UIComponent findComponent(FacesContext context) throws JspException
    {
        return super.findComponent(context);
    }
}
