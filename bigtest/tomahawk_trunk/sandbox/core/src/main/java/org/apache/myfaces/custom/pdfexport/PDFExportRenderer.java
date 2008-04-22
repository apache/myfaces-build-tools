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
package org.apache.myfaces.custom.pdfexport;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.pdfexport.util.PDFExportConstants;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

public class PDFExportRenderer extends HtmlRenderer {

	public void encodeChildren(FacesContext facesContext, UIComponent component)
			throws IOException {
		if (component.getChildCount() == 0)
			return;

		PDFExport pdfExport = (PDFExport) component;
		UIComponent child = (UIComponent) component.getChildren().get(0);

		if (!isDecorated(facesContext, child, pdfExport))
			decorateOnClick(facesContext, child, pdfExport);

		RendererUtils.renderChildren(facesContext, component);
	}

	private boolean isDecorated(FacesContext facesContext, UIComponent child,
			PDFExport pdfExport) {
		String onClick = (String) child.getAttributes().get("onclick");
		String jsCall = getJSCall(facesContext, pdfExport);

		if (onClick == null || onClick.indexOf(jsCall) == -1)
			return false;
		else
			return true;
	}

	private void decorateOnClick(FacesContext facesContext, UIComponent child,
			PDFExport pdfExport) {
		String jsCall = getJSCall(facesContext, pdfExport);
		String onclickEvent = (String) child.getAttributes().get("onclick");
		if (onclickEvent == null)
			child.getAttributes().put("onclick", jsCall);
		else
			child.getAttributes().put("onclick", onclickEvent + ";" + jsCall);
	}

	private String getJSCall(FacesContext facesContext, PDFExport pdfExport) {
		String viewId = StringUtils.split(facesContext.getViewRoot()
				.getViewId(), "\\.")[0];
		return "window.open('"
				+ facesContext.getApplication().getViewHandler().getActionURL(
						facesContext, viewId) + "?"
				+ PDFExportConstants.PDF_EXPORT_TABLE_ID + "="
				+ pdfExport.getFor() + "&"
				+ PDFExportConstants.PDF_EXPORT_FILE_NAME + "="
				+ pdfExport.getFilename() + "');return false;";
	}
}
