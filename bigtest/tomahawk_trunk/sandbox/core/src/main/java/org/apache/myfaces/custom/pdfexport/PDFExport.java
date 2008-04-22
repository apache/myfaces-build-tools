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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

public class PDFExport extends UIComponentBase {

	public static final String COMPONENT_TYPE = "org.apache.myfaces.PDFExport";
	public static final String COMPONENT_FAMILY = "org.apache.myfaces.Export";
	private static final String DEFAULT_RENDERER = "org.apache.myfaces.PDFExportRenderer";

	private String _for;
	private String _filename;

	public PDFExport() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	
	public boolean getRendersChildren() {
		return true;
	}

	public String getFor() {
		if (_for != null)
			return _for;

		ValueBinding vb = getValueBinding("for");
		return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb) : null;
	}

	public void setFor(String forValue) {
		_for = forValue;
	}
	
	public String getFilename() {
		if (_filename != null)
			return _filename;

		ValueBinding vb = getValueBinding("filename");
		return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb) : getFor();
	}

	public void setFilename(String filename) {
		this._filename = filename;
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[3];
		values[0] = super.saveState(context);
		values[1] = _for;
		values[2] = _filename;
		return ((Object) (values));
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		_for = (String) values[1];
		_filename = (String) values[2];
	}
}
