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

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import java.io.IOException;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Message"
 *   type = "org.apache.myfaces.MessageSandbox"
 *
 * User: treeder
 * Date: Nov 22, 2005
 * Time: 2:17:13 PM
 */
public class HtmlMessageRenderer extends org.apache.myfaces.renderkit.html.ext.HtmlMessageRenderer
{
    /**
     * This overrides the rendering to make sure it will output a span even if there is no message.
     *
     * @param facesContext
     * @param component
     * @throws IOException
     */
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
         //super.encodeEnd(facesContext, component);
        // Make sure a span is output
        renderMessage(facesContext, component, true);

    }
}
