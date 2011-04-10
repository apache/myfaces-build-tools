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
package org.myorganization.component.sayhello;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.myorganization.SayHelloRenderer"
 */
public class SayHelloRenderer extends Renderer
{

    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        super.decode(facesContext, uiComponent);
        // nothing to decode
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.encodeBegin(facesContext, uiComponent);
        // no need to use encodeBegin.
    }

    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.encodeChildren(facesContext, uiComponent);
        // this component does not have children
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.encodeEnd(facesContext, uiComponent);

        if (!uiComponent.isRendered())
        {
            return;
        }

        ResponseWriter writer = facesContext.getResponseWriter();

        SayHello sayHello = (SayHello) uiComponent;

        String firstName = sayHello.getFirstName();
        String lastName = sayHello.getLastName();

        writer.write("Hello ");

        if (firstName != null)
        {
            writer.write(firstName);
        }

        if (lastName != null)
        {
            writer.write(" "+lastName);
        }

        writer.write("!");
    }
}
