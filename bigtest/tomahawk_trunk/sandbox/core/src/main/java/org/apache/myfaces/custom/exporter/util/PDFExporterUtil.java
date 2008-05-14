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
package org.apache.myfaces.custom.exporter.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.util.ComponentUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * This class is a utility class for serving PDF exporting.
 */
public class PDFExporterUtil {
	
	/*
	 * This method is used for setting the response headers of the pdf.
	 */
	private static void setPDFResponseHeaders(HttpServletResponse response,
			ByteArrayOutputStream byteArrayStream, String fileName)
			throws IOException {

		// setting response headers.
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName + ".pdf");

		// setting the content type.
		response.setContentType("application/pdf");

		// the contentlength is needed for MSIE.
		response.setContentLength(byteArrayStream.size());

		// write ByteArrayOutputStream to the ServletOutputStream.
		ServletOutputStream outputStream = response.getOutputStream();

		byteArrayStream.writeTo(outputStream);
		outputStream.flush();
	}		
	
	/*
	 * This method is used for adding the columns headers to the pdfTable.
	 */
	private static void addColumnHeaders(PdfPTable pdfTable, List columns) {

		for (int i = 0; i < columns.size(); i++) {
			UIColumn column = (UIColumn) columns.get(i);
			UIComponent columnHeaderCell = column.getHeader();
			if (columnHeaderCell instanceof ValueHolder) {
				String cellValue = RendererUtils.getStringValue(FacesContext
						.getCurrentInstance(), columnHeaderCell);
				pdfTable.addCell(cellValue);
			}
		}
	}	
	
	/*
	 * This method is used for adding the columns values to the pdfTable.
	 */
	private static void addColumnValues(PdfPTable pdfTable, List columns,
			HtmlDataTable dataTable) {
		
		int numberOfColumns = columns.size();
		int numberOfRows = dataTable.getRowCount();
		
		for (int i = 0; i < numberOfRows; ++i) {
			dataTable.setRowIndex(i);
			for (int j = 0; j < numberOfColumns; ++j) {
				UIComponent valueHolder = (UIComponent) ((UIColumn) columns
						.get(j)).getChildren().get(0);
				if (valueHolder instanceof ValueHolder) {
					String cellValue = RendererUtils.getStringValue(
							FacesContext.getCurrentInstance(), valueHolder);
					pdfTable.addCell(cellValue);
				}
			}
		}
	}	
	
	/*
	 * This method is used for creating the PDFTable model.
	 */	
	public static PdfPTable generatePDFTableModel(FacesContext facesContext,
			HtmlDataTable dataTable) {

		int numberOfColumns;
		List columns = null;
		PdfPTable pdfTable = null;

		// getting the HTMLDataTable Columns.
		columns = ComponentUtils.getHTMLDataTableColumns(dataTable);

		if (columns.size() == 0) {
			return null;
		} else {
			numberOfColumns = columns.size();
		}

		// creating the PDF Table.
		pdfTable = new PdfPTable(numberOfColumns);

		addColumnHeaders(pdfTable, columns);

		addColumnValues(pdfTable, columns, dataTable);

		return pdfTable;
	}	
	
	/*
	 * This method is responsible for writing the PDF to the response stream.
	 */
	public static void generatePDF(FacesContext facesContext,
			HttpServletResponse response, String fileName,
			HtmlDataTable dataTable) throws Exception {

		int currentRowIndex;
		Document document = new Document();		
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, byteArrayStream);		
		PdfPTable pdfTable = null;

		currentRowIndex = dataTable.getRowIndex();

		// generate the PDF table model.
		pdfTable = generatePDFTableModel(facesContext, dataTable);
		
		// open the document and write the generated PDF.
		document.open();
		document.add(pdfTable);
		document.close();

		// write the response headers.
		setPDFResponseHeaders(response, byteArrayStream, fileName);

		dataTable.setRowIndex(currentRowIndex);

	}		
}
