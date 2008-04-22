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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExcelExportPhaseListener implements PhaseListener {

	public void afterPhase(PhaseEvent phaseEvent) {
		FacesContext facesContext = phaseEvent.getFacesContext(); 
		Map map = facesContext.getExternalContext().getRequestParameterMap();

		if(map.containsKey("excelExportTableId")) {
			String tableId = (String) map.get("excelExportTableId");
			String filename = (String) map.get("excelExportFileName");
			
			HtmlDataTable dataTable = (HtmlDataTable) ComponentUtils.findComponentById( facesContext, facesContext.getViewRoot(), tableId );
			HSSFWorkbook generatedExcel = generateExcel( facesContext, dataTable );
			try {
				Object contextResponse = facesContext.getExternalContext().getResponse();
				if(contextResponse instanceof HttpServletResponse)
					writeExcelOutput(generatedExcel,(HttpServletResponse)contextResponse, filename);
				else if(contextResponse instanceof RenderResponse)
					writeExcelOutput(generatedExcel,(RenderResponse)contextResponse, filename);
				
				facesContext.getApplication().getStateManager().saveSerializedView(facesContext);
				facesContext.responseComplete();
			} catch (Exception e) {
				throw new RuntimeException( e );
			}
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		//do nothing
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	private void writeExcelOutput(HSSFWorkbook workBook, HttpServletResponse response, String filename) throws IOException{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Expires", "0");
	    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	    response.setHeader("Pragma", "public");
	    response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");

		workBook.write(response.getOutputStream());
	}
	
	private void writeExcelOutput(HSSFWorkbook workBook, RenderResponse response, String filename) throws IOException{
		response.setContentType("application/vnd.ms-excel");
		response.setProperty(RenderResponse.EXPIRATION_CACHE, "0");
	    response.setProperty("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	    response.setProperty("Pragma", "public");
	    response.setProperty("Content-disposition", "attachment;filename=" + filename + ".xls");
		
		workBook.write(response.getPortletOutputStream());
	}
	
	private List getColumns(HtmlDataTable table) {
		List columns = new ArrayList();
		for(int i=0; i < table.getChildCount(); i++) {
			UIComponent child = (UIComponent) table.getChildren().get( i );
			if(child instanceof UIColumn) {				
				columns.add( child );
			}
		}
		return columns;
	}
	
	private HSSFWorkbook generateExcel(FacesContext facesContext, HtmlDataTable table) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(table.getId());
		List columns = getColumns(table);
		int currentRowIndex = table.getRowIndex();
				
		addColumnHeaders(sheet, columns);
		addColumnValues(sheet, columns, table);

		table.setRowIndex( currentRowIndex );
		return workbook;
	}
	
	private void addColumnValue(HSSFRow rowHeader, UIComponent component, int index) {
		HSSFCell cell = rowHeader.createCell((short)index);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		if(component instanceof ValueHolder) {
			String stringValue = RendererUtils.getStringValue(FacesContext.getCurrentInstance(), component);
			cell.setCellValue(stringValue);
		}
	}
	
	private void addColumnHeaders(HSSFSheet sheet, List columns) {
		HSSFRow rowHeader = sheet.createRow(0);
		
		for(int i=0; i < columns.size(); i++) {
			UIColumn column = (UIColumn) columns.get(i);
			addColumnValue(rowHeader, column.getHeader(), i);
		}
	}
	
	private void addColumnValues(HSSFSheet sheet, List columns, HtmlDataTable dataTable) {
		for(int i=0 ; i < dataTable.getRowCount(); i++)  {
			dataTable.setRowIndex(i);
			HSSFRow row = sheet.createRow( i + 1 );
			for (int j=0; j < columns.size(); j++) {
				UIColumn column = (UIColumn) columns.get(j);
				addColumnValue(row, (UIComponent) column.getChildren().get(0), j);
			}
		}
	}
}