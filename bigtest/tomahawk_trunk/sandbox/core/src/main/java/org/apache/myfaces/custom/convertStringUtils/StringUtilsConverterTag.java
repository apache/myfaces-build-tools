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

package org.apache.myfaces.custom.convertStringUtils;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.ConverterTag;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * A tag that invokes the {@link StringUtilsConverter} and lets the developer
 * specify how to convert a string.
 * <p>
 * Example:
 * <code>
 * <h:outputText value="#{backingBean.customer.name}">
 *     <t:caseConverter format="capitalize" trim="true" maxLength="50"/>
 * </h:outputText>
 * </code>
 * <p>
 * @author Julian ray
 */
public class StringUtilsConverterTag extends ConverterTag {
	
    private static final long serialVersionUID = 9006143486961806695L;

    private String format;
	private String maxLength;
	private String trim;
    private String appendEllipsesDuringInput;
    private String appendEllipsesDuringOutput;
    
	public StringUtilsConverterTag() {
		setConverterId(StringUtilsConverter.CONVERTER_ID);		
	}
	
	public void release() {
		format = null;
		maxLength = null;
        trim = null;
        appendEllipsesDuringInput = null;
        appendEllipsesDuringOutput = null;
	}
    public void setPageContext(PageContext context) {
        super.setPageContext(context);
        setConverterId(StringUtilsConverter.CONVERTER_ID);
    }

    protected Converter createConverter() throws JspException {
        StringUtilsConverter converter = (StringUtilsConverter) super.createConverter();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        setConverterFormatValue(facesContext, converter, this.format);
        setConverterMaxLengthValue(facesContext, converter, this.maxLength);
        setConverterTrimValue(facesContext, converter, this.trim);
        setConverterAppendEllipsesDuringInputValue(facesContext, converter, this.appendEllipsesDuringInput);
        setConverterAppendEllipsesDuringOutputValue(facesContext, converter, this.appendEllipsesDuringOutput);

        return converter;
    }
    private static void setConverterFormatValue(FacesContext facesContext, StringUtilsConverter converter, String value) {
        if (null == value) {
            return;
        }
        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            converter.setFormat((String) vb.getValue(facesContext));
        } else {
            converter.setFormat(value);
        }
    }

    private static void setConverterMaxLengthValue(FacesContext facesContext, StringUtilsConverter converter, String value) {
        if (null == value) {
            return;
        }
        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            converter.setMaxLength((Integer) vb.getValue(facesContext));
        } else {
            converter.setMaxLength(Integer.valueOf(value));
        }
    }
    private static void setConverterTrimValue(FacesContext facesContext, StringUtilsConverter converter, String value) {
        if (null == value) {
            return;
        }
        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            converter.setTrim((Boolean) vb.getValue(facesContext));
        } else {
            converter.setTrim(Boolean.valueOf(value));
        }
    }
    private static void setConverterAppendEllipsesDuringInputValue(FacesContext facesContext, StringUtilsConverter converter, String value) {
        if (null == value) {
            return;
        }
        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            converter.setAppendEllipsesDuringInput((Boolean) vb.getValue(facesContext));
        } else {
            converter.setAppendEllipsesDuringInput(Boolean.valueOf(value));
        }
    }
    private static void setConverterAppendEllipsesDuringOutputValue(FacesContext facesContext, StringUtilsConverter converter, String value) {
        if (null == value) {
            return;
        }
        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            converter.setAppendEllipsesDuringOutput((Boolean) vb.getValue(facesContext));
        } else {
            converter.setAppendEllipsesDuringOutput(Boolean.valueOf(value));
        }
    }
		
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getTrim() {
		return trim;
	}

	public void setTrim(String trim) {
		this.trim = trim;
	}

    public String getAppendEllipsesDuringInput() {
        return appendEllipsesDuringInput;
    }

    public void setAppendEllipsesDuringInput(String appendEllipsesDuringInput) {
        this.appendEllipsesDuringInput = appendEllipsesDuringInput;
    }

    public String getAppendEllipsesDuringOutput() {
        return appendEllipsesDuringOutput;
    }

    public void setAppendEllipsesDuringOutput(String appendEllipsesDuringOutput) {
        this.appendEllipsesDuringOutput = appendEllipsesDuringOutput;
    }

}
