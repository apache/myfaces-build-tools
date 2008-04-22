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
package org.apache.myfaces.custom.excelexport;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExcelExportRenderer extends HtmlRenderer {

	public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException{
		if(component.getChildCount() == 0)
			return ;
		
		ExcelExport excelexport = (ExcelExport) component;
		UIComponent child = (UIComponent) component.getChildren().get( 0 );
		
		if( !isDecorated(facesContext, child, excelexport) )
			decorateOnClick(facesContext, child, excelexport);
		
		RendererUtils.renderChildren(facesContext, component);
	}
	
	private boolean isDecorated(FacesContext facesContext, UIComponent child, ExcelExport excelExport) {
		String onClick = (String) child.getAttributes().get("onclick");
		String jsCall = getJSCall(facesContext, excelExport);
		
		if(onClick == null || onClick.indexOf(jsCall) == -1)
			return false;
		else
			return true;
	}
	
	private void decorateOnClick(FacesContext facesContext, UIComponent child, ExcelExport excelExport) {
		String jsCall = getJSCall(facesContext, excelExport);
		String onclickEvent = (String) child.getAttributes().get("onclick");
		if(onclickEvent == null)
			child.getAttributes().put("onclick", jsCall);
		else
			child.getAttributes().put("onclick", onclickEvent + ";" + jsCall);
	}
	
	private String getJSCall(FacesContext facesContext, ExcelExport excelExport) {
		String viewId = StringUtils.split( facesContext.getViewRoot().getViewId() , "\\.")[0];
		return "window.open('"
				+ facesContext.getApplication().getViewHandler().getActionURL(
						facesContext, viewId) + "?excelExportTableId="
				+ excelExport.getFor() + "&excelExportFileName=" + excelExport.getFilename() + "');return false;";
	}
}