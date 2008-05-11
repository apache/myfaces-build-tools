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
package org.apache.myfaces.custom.regexprvalidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.GenericValidator;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;
import org.apache.myfaces.validator.ValidatorBase;

/**
 * A custom validator for reg. expr., based upons Jakarta Commons. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFValidator
 *   name = "t:validateRegExpr"
 *   tagClass = "org.apache.myfaces.custom.regexprvalidator.ValidateRegExprTag"
 * 
 * @JSFJspProperty name = "message" returnType = "java.lang.String" longDesc = "alternate validation error detail message format string (use 'message' and 'detailMessage' alternatively)"
 * @JSFJspProperty name = "detailMessage" returnType = "java.lang.String" longDesc = "alternate validation error detail message format string (use 'message' and 'detailMessage' alternatively)"
 * @JSFJspProperty name = "summaryMessage" returnType = "java.lang.String" longDesc = "alternate validation error summary message format string"
 * @author mwessendorf (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public class RegExprValidator extends ValidatorBase {
	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String 	VALIDATOR_ID 	   = "org.apache.myfaces.validator.RegExpr";

	/**
	 * <p>The message identifier of the {@link FacesMessage} to be created if
	 * the regex check fails.</p>
	 */
	public static final String REGEXPR_MESSAGE_ID = "org.apache.myfaces.Regexpr.INVALID";

	public RegExprValidator(){
	}

	//the pattern on which the validation is based.
    protected String _pattern= null;

	public void validate(
		FacesContext facesContext,
		UIComponent uiComponent,
		Object value)
		throws ValidatorException {

		if (facesContext == null) throw new NullPointerException("facesContext");
		if (uiComponent == null) throw new NullPointerException("uiComponent");

		if (value == null)
			{
				return;
		}
		Object[] args = {value.toString()};
		if(!GenericValidator.matchRegexp(value.toString(),"^"+getPattern()+"$")){
			throw new ValidatorException(getFacesMessage(REGEXPR_MESSAGE_ID, args));
        }
	}



	// -------------------------------------------------------- StateholderIF

	public Object saveState(FacesContext context) {
		Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = _pattern;
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        _pattern = (String) values[1];
	}

	// -------------------------------------------------------- GETTER & SETTER

	/**
	 * the pattern, which is the base of the validation
	 * 
	 * @JSFProperty
	 * @return the pattern, on which a value should be validated
	 */
    public String getPattern()
    {
        if (_pattern != null) return _pattern;
        ValueBinding vb = getValueBinding("pattern");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

	/**
	 * @param string the pattern, on which a value should be validated
	 */
	public void setPattern(String string) {
		_pattern = string;
	}

}
