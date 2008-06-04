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

package org.apache.myfaces.custom.dojo;

import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 * Dojointializerrenderer
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.DojoInitializerRenderer"
 *
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DojoInitializerRenderer extends HtmlRenderer {

    public static final String RENDERER_TYPE = "org.apache.myfaces.DojoInitializerRenderer";

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);

    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        Boolean rendered = (Boolean) component.getAttributes().get("rendered");

        if ((rendered != null) && (!rendered.booleanValue()))
            return;

        super.encodeBegin(context, component);

    }

    /**
     * Standard encode end
     *
     */
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
        super.encodeEnd(facesContext, component);

        encodeJavascript(facesContext, component);

        if ((((DojoInitializer) component).getDebugConsole() != null) && ((DojoInitializer) component).getDebugConsole().booleanValue()) {
            DojoUtils.addDebugConsole(facesContext, component);
        }
    }

    public boolean getRendersChildren() {
        return false;
    }

    /**
     * Encodes any stand-alone javascript functions that are needed. Uses either
     * the extension filter, or a user-supplied location for the javascript
     * files.
     *
     * @param context
     *            FacesContext
     * @param component
     *            UIComponent
     */
    private void encodeJavascript(FacesContext context, UIComponent component) throws IOException {
        String javascriptLocation = (String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        DojoUtils.addMainInclude(context, component, javascriptLocation, DojoUtils.getDjConfigInstance(context));

        String require = (String) component.getAttributes().get("require");
        String provide = (String) component.getAttributes().get("provide");

        if (provide != null)
            DojoUtils.addProvide(context, component, provide);

        if (require != null)
            DojoUtils.addRequire(context, component, require);

    }

}
