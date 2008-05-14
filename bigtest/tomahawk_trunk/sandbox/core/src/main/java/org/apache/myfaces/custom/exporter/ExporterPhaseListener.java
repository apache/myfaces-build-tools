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
package org.apache.myfaces.custom.exporter;

import java.util.Map;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.exporter.util.ExcelExporterUtil;
import org.apache.myfaces.custom.exporter.util.ExporterConstants;
import org.apache.myfaces.custom.exporter.util.PDFExporterUtil;
import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExporterPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 8027141771159351243L;

	public void afterPhase(PhaseEvent phaseEvent) {
		FacesContext facesContext = phaseEvent.getFacesContext();
		Map map = facesContext.getExternalContext().getRequestParameterMap();

		if (map.containsKey(ExporterConstants.EXPORTER_TABLE_ID)) {

			String tableId = (String) map
					.get(ExporterConstants.EXPORTER_TABLE_ID);
			String filename = (String) map
					.get(ExporterConstants.EXPORTER_FILE_NAME);
			String fileType = (String) map
					.get(ExporterConstants.EXPORTER_FILE_TYPE);

			try {

				if (ExporterConstants.EXCEL_FILE_TYPE
						.equalsIgnoreCase(fileType)) { /* excel case. */

					HtmlDataTable dataTable = (HtmlDataTable) ComponentUtils
							.findComponentById(facesContext, facesContext
									.getViewRoot(), tableId);
					HSSFWorkbook generatedExcel = ExcelExporterUtil
							.generateExcelTableModel(facesContext, dataTable);
					Object contextResponse = facesContext.getExternalContext()
							.getResponse();

					// generate the excel to the response stream.
					if (contextResponse instanceof HttpServletResponse)
						ExcelExporterUtil
								.generateEXCEL(generatedExcel,
										(HttpServletResponse) contextResponse,
										filename);
					else if (contextResponse instanceof RenderResponse)
						ExcelExporterUtil.generateEXCEL(generatedExcel,
								(RenderResponse) contextResponse, filename);

				} else { /* pdf case. */

					HtmlDataTable dataTable = (HtmlDataTable) ComponentUtils
							.findComponentById(facesContext, facesContext
									.getViewRoot(), tableId);

					// generate the PDF to the response stream.
					HttpServletResponse response = (HttpServletResponse) facesContext
							.getExternalContext().getResponse();

					PDFExporterUtil.generatePDF(facesContext, response,
							filename, dataTable);
				}

				// save the seralized view and complete the response.
				facesContext.getApplication().getStateManager()
						.saveSerializedView(facesContext);
				facesContext.responseComplete();

			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		// do nothing
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
