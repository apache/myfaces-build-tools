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
package org.apache.myfaces.custom.toggle;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.ext.HtmlLinkRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * Renderer for component HtmlAjaxChildComboBox
 * 
 * @author Sharath Reddy
 */
public class ToggleLinkRenderer extends HtmlLinkRenderer {
    public static final int DEFAULT_MAX_SUGGESTED_ITEMS = 200;

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.checkParamValidity(context, component, ToggleLink.class);

        if(((ToggleLink) component).isDisabled())
        	return;

        super.encodeEnd(context, component);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.checkParamValidity(context, component, ToggleLink.class);

        ToggleLink toggleLink = (ToggleLink) component;
        if(toggleLink.isDisabled())
        	return;

        String[] componentsToToggle = toggleLink.getFor().split(",");
        StringBuffer idsToShow = new StringBuffer();
        for (int i = 0; i < componentsToToggle.length; i++) {
            String componentId = componentsToToggle[i].trim();
            UIComponent componentToShow = toggleLink.findComponent(componentId);
            if (component == null) {
                Log log = LogFactory.getLog(ToggleLinkRenderer.class);
                log.error("Unable to find component with id " + componentId);
                continue;
            }
            if( idsToShow.length() > 0 )
            	idsToShow.append( ',' );
            idsToShow.append( componentToShow.getClientId(context) );
        }

        toggleLink.setOnclick(getToggleJavascriptFunctionName(context, toggleLink) + "('"+idsToShow+"')");

        super.encodeBegin(context, component);
    }
    
    private String getToggleJavascriptFunctionName(FacesContext context,ToggleLink toggleLink){
    	for(UIComponent component = toggleLink.getParent(); component != null; component = component.getParent())
    		if( component instanceof TogglePanel )
    			return TogglePanelRenderer.getToggleJavascriptFunctionName( context, (TogglePanel)component );

    	Log log = LogFactory.getLog(ToggleLinkRenderer.class);
        log.error("The ToggleLink component with id " + toggleLink.getClientId( context )+" isn't enclosed in a togglePanel.");
    	return null;
    }
}
