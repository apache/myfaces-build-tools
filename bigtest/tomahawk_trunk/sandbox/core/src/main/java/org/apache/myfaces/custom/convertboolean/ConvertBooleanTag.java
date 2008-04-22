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

package org.apache.myfaces.custom.convertboolean;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.ConverterTag;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * A tag that invokes the {@link BooleanConverter} and lets the developer
 * specify the desired values for a boolean true or false.
 * <p>
 * Example:
 * <code>
 * <h:outputText value="#{backingBean.customer.enjoysJazz}">
 *     <t:convertBoolean trueValue="Yes" falseValue="No"/>
 * </h:outputText>
 * </code>
 * <p>
 * @author Ken Weiner
 */
public class ConvertBooleanTag extends ConverterTag {
    
    
    
    /**
     * serial version id for correct serialisation versioning
     */
    private static final long serialVersionUID = 1L;
    private String trueValue;
    private String falseValue;

    public ConvertBooleanTag() {
        setConverterId(BooleanConverter.CONVERTER_ID);
    }

    public String getFalseValue() {
        return falseValue;
    }

    public void setFalseValue(String falseValue) {
        this.falseValue = falseValue;
    }

    public String getTrueValue() {
        return trueValue;
    }

    public void setTrueValue(String trueValue) {
        this.trueValue = trueValue;
    }

    public void setPageContext(PageContext context) {
        super.setPageContext(context);
        setConverterId(BooleanConverter.CONVERTER_ID);
    }

    protected Converter createConverter() throws JspException {
        BooleanConverter converter = (BooleanConverter) super.createConverter();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        setConverterTrueValue(facesContext, converter, this.trueValue);
        setConverterFalseValue(facesContext, converter, this.falseValue);

        return converter;
    }

    private static void setConverterTrueValue(FacesContext facesContext,
            BooleanConverter converter, String value) {
        if (value == null) {
            return;
        }
        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            converter.setTrueValue((String) vb.getValue(facesContext));
        } else {
            converter.setTrueValue(value);
        }
    }

    private static void setConverterFalseValue(FacesContext facesContext,
            BooleanConverter converter, String value) {
        if (value == null) {
            return;
        }
        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            converter.setFalseValue((String) vb.getValue(facesContext));
        } else {
            converter.setFalseValue(value);
        }
    }

}
