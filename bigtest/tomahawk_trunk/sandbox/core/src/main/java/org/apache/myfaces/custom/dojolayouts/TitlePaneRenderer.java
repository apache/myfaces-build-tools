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

package org.apache.myfaces.custom.dojolayouts;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.dojo.DojoConfig;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.TitlePaneRenderer"
 *
 * @author werpu
 *
 */
public class TitlePaneRenderer extends DojoContentPaneRenderer {

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);

    }

    protected void encodeJavascriptBegin(FacesContext context, UIComponent component) throws IOException {
        String javascriptLocation = (String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        try {
            if (javascriptLocation != null) {
                DojoUtils.addMainInclude(context, component, javascriptLocation, new DojoConfig());
            } else {
                DojoUtils.addMainInclude(context, component, null, new DojoConfig());
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        String [] requires = {"dojo.widget.myfaces.SavestatingTitlePane",
                              "dojo.widget.html.myfaces.SavestatingTitlePane"};
        DojoUtils.addRequire(context, component, requires);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
    }

    protected void encodeJavascriptEnd(FacesContext context, UIComponent component) throws IOException {

        String [] attributeNames = {"containerNodeClass", "label", "labelNodeClass", "widgetVar", "widgetId"};
        DojoUtils.renderWidgetInitializationCode(context, component, "SavestatingTitlePane", attributeNames);
    }

    public boolean getRendersChildren() {
        return true;
    }

}