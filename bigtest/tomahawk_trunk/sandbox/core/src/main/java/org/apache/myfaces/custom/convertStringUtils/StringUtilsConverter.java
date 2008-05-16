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

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * Provides runtime modification of a string. Uses Apache Lang StringUtils and WordUtils
 * to peform operations.
 * <p>
 * Example:
 * <code>
 * <h:outputText value="#{backingBean.customer.name}">
 *     <t:convertStringUtils format="capitalize" trim="true" maxLength="50"/>
 * </h:outputText>
 * </code>
 * <p>
 * @JSFConverter
 *   name = "s:convertStringUtils"
 *   tagClass = "org.apache.myfaces.custom.convertStringUtils.StringUtilsConverterTag" 
 *   
 * @author Julian Ray
 */
public class StringUtilsConverter implements Converter, StateHolder {
    
    public static final String CONVERTER_ID = "org.apache.myfaces.custom.convertStringUtils.StringUtilsConverter";

    protected boolean _transient;
    
    protected String format = null;
    protected Boolean trim = null;
    protected Integer maxLength = null;
    protected Boolean appendEllipsesDuringOutput = null;
    protected Boolean appendEllipsesDuringInput = null;
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return null == value ? null : format(value.toString(), false);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		return value == null ? "" : format(value.toString(), true);
	}

	private String format(String val, boolean duringOutput) throws ConverterException {

		String str;
		if (BooleanUtils.isTrue(trim)) {
			str = val.trim();
		} else {
			str = val;
		}
		// Any decorations first
		if (StringUtils.isNotEmpty(format)) {
			if ("uppercase".equalsIgnoreCase(format)) {
				str = StringUtils.upperCase(str);
			} else if ("lowercase".equalsIgnoreCase(format)) {
				str = StringUtils.lowerCase(str);
			} else if ("capitalize".equalsIgnoreCase(format)) {
				str = WordUtils.capitalizeFully(str);
			} else {
				throw new ConverterException("Invalid format '" + format + "'");
			}
		}
        
        boolean appendEllipses = 
            ((duringOutput)
                && ((null != appendEllipsesDuringOutput)
                && (appendEllipsesDuringOutput.booleanValue())))
          || ((false == duringOutput)
                && ((null != appendEllipsesDuringInput)
                && (appendEllipsesDuringInput.booleanValue()))) ;
        
        if (appendEllipses)
        {
            // See if we need to abbreviate/truncate this string
            if (null != maxLength && maxLength.intValue() > 4) {
                str = StringUtils.abbreviate(str, maxLength.intValue());
            }
        }
        else
        {
            // See if we need to truncate this string
            if (null != maxLength) {
                str = str.substring(0, maxLength.intValue());
            }
        }
		return str;
	}
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        this.format = (String) values[0];
        this.trim = (Boolean) values[1];
        this.maxLength = (Integer) values[2];
        this.appendEllipsesDuringOutput = (Boolean) values[3];
        this.appendEllipsesDuringInput = (Boolean) values[4];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[5];
        values[0] = this.format;
        values[1] = this.trim;
        values[2] = this.maxLength;
        values[3] = this.appendEllipsesDuringOutput;
        values[4] = this.appendEllipsesDuringInput;
        return values;
    }
    
    public boolean isTransient() {
        return _transient;
    }

    public void setTransient(boolean _transient) {
        this._transient = _transient;
    }

    public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

    public Integer getMaxLength() {
        return maxLength;
    }

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

    public Boolean isAppendEllipsesDuringOutput() {
        return appendEllipsesDuringOutput;
    }

    public void setAppendEllipsesDuringOutput(Boolean appendEllipsesDuringOutput) {
        this.appendEllipsesDuringOutput = appendEllipsesDuringOutput;
    }

    public Boolean isAppendEllipsesDuringInput() {
        return appendEllipsesDuringInput;
    }

    public void setAppendEllipsesDuringInput(Boolean appendEllipsesDuringInput) {
        this.appendEllipsesDuringInput = appendEllipsesDuringInput;
    }

	public Boolean getTrim() {
		return trim;
	}

	public void setTrim(Boolean trim) {
		this.trim = trim;
	}
}
