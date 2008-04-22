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
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlGroupRendererBase;

public class TogglePanelRenderer extends HtmlGroupRendererBase {

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        RendererUtils.checkParamValidity(context, component, TogglePanel.class);
        TogglePanel togglePanel = (TogglePanel) component;
        boolean toggleMode = togglePanel.isToggled();
        toggleVisibility(togglePanel.getChildren(), toggleMode);
        
        // render the hidden input field
        ResponseWriter writer = context.getResponseWriter();

        String hiddenFieldId = getHiddenFieldId(context, togglePanel);

        writer.startElement(HTML.INPUT_ELEM, component);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);
        writer.writeAttribute(HTML.ID_ATTR, hiddenFieldId, null);
        writer.writeAttribute(HTML.NAME_ATTR, hiddenFieldId, null);

        writer.writeAttribute(HTML.VALUE_ATTR, toggleMode ? "1" : "", null);

        writer.endElement(HTML.INPUT_ELEM);

        if( ! toggleMode )
        	writeJavascriptToToggleVisibility(context, togglePanel);

        super.encodeEnd(context, togglePanel);
    }

    private void toggleVisibility(List children, boolean toggleMode) {
        for(Iterator it = children.iterator(); it.hasNext(); ) {
            UIComponent component = (UIComponent) it.next();
            setComponentVisibility( component, toggleMode );
        }
    }

    // checks if this component has getStyle/setStyle methods
    private boolean hasStyleAttribute(UIComponent component) {
        Method[] methods = component.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().equals("getStyle")) {
                return true;
            }
        }
        return false;
    }

    // hides component by appending 'display:none' to the 'style' attribute
    private void setComponentVisibility(UIComponent component, boolean toggleMode) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!hasStyleAttribute(component)) {
            getLog().info("style attribute expected, not found for component " + component.getClientId(context));
            return;
        }

        try {
            Class c = component.getClass();
            Method getStyle = c.getMethod("getStyle", new Class[] {});
            Method setStyle = c.getMethod("setStyle", new Class[] { String.class });

            String style = (String) getStyle.invoke(component, new Object[] {});
            
            boolean display = toggleMode != isHiddenWhenToggled(component);
            if( display ){
            	if (style == null || style.length() == 0) {
                    return;
                } else {
                    int index = style.indexOf(";display:none;");
                    if (index == -1)
                        return;

                    if (index == 0) {
                        style = null;
                    } else {
                        style = style.substring(0, index);
                    }
                }
            }else{ // hide
	            if (style == null) {
	                style = ";display:none;";
	            } else if (style.indexOf("display:none;") == -1) {
	                style = style.concat(";display:none;");
	            }
            }

            setStyle.invoke(component, new Object[] { style });
        } catch (Throwable e) {
            getLog().error("unable to set style attribute on component " + component.getClientId(context));
        }
    }

    private Log getLog() {
        return LogFactory.getLog(TogglePanelRenderer.class);
    }

    private boolean isHiddenWhenToggled(UIComponent component){
    	return component instanceof ToggleLink || component instanceof ToggleGroup;
    }

    private String getHiddenFieldId(FacesContext context, TogglePanel togglePanel){
    	return togglePanel.getClientId(context) + "_hidden";
    }
    // Generate the javascript function to hide the Link component
    // and display the components specified in the 'for' attribute
    private void writeJavascriptToToggleVisibility(FacesContext context, TogglePanel togglePanel) throws IOException {

        ResponseWriter out = context.getResponseWriter();

        out.startElement(HTML.SCRIPT_ELEM, null);
        out.writeAttribute(HTML.TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);

        String functionName = getToggleJavascriptFunctionName(context, togglePanel);

        out.write("function "+functionName + "(idsToShowS){\n");

        StringBuffer idsToHide = new StringBuffer();
        int idsToHideCount = 0;
        for(Iterator it = togglePanel.getChildren().iterator(); it.hasNext(); ) {
            UIComponent component = (UIComponent) it.next();
            if ( isHiddenWhenToggled( component ) ) {
            	if( idsToHideCount > 0 )
            		idsToHide.append( ',' );
            	idsToHide.append( component.getClientId( context ) );
            	idsToHideCount++;
            }
        }

        if( idsToHideCount == 1 ){
        	out.write( "document.getElementById('"+ idsToHide.toString() +"').style.display = 'none';\n" );
        }else if( idsToHideCount > 1 ){
        	out.write( "var idsToHide = '" + idsToHide.toString() + "'.split(',');\n" );
        	out.write( "for(var i=0;i<idsToHide.length;i++) document.getElementById(idsToHide[i]).style.display = 'none';\n" );
        }else{ // no idsToHide set
        	getLog().warn( "TogglePanel "+ togglePanel.getClientId(context) +" has no visible components when toggled." );
        }
        out.write( "var idsToShow = idsToShowS.split(',');\n" );
        out.write( "for(var j=0;j<idsToShow.length;j++) document.getElementById(idsToShow[j]).style.display = 'inline';\n");

        // toggle the value of the hidden field
        out.write("document.getElementById('" + getHiddenFieldId(context, togglePanel) + "').value = '1';\n");

        out.write("}");
        out.endElement(HTML.SCRIPT_ELEM);
    }

    static public String getToggleJavascriptFunctionName(FacesContext context, TogglePanel togglePanel) {
        String modifiedId = togglePanel.getClientId(context).replaceAll("\\:", "_").replaceAll("-", "_");
        return "toggle_" + modifiedId;
    }
}
