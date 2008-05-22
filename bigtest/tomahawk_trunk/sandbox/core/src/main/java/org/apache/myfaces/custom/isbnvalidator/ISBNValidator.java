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
package org.apache.myfaces.custom.isbnvalidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.shared_tomahawk.util.MessageUtils;
import org.apache.myfaces.validator.ValidatorBase;

/**
 * A custom validator for isbn codes, based upons Jakarta Commons.
 * 
 * @JSFValidator
 *   name = "s:validateISBN"
 *   tagClass = "org.apache.myfaces.custom.isbnvalidator.ValidateISBNTag"
 *   
 * @JSFJspProperty name = "message" returnType = "java.lang.String" longDesc = "alternate validation error message format string"
 * @author <a href="mailto:matzew@apache.org">Matthias We&szlig;endorf</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public class ISBNValidator extends ValidatorBase {

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String 	VALIDATOR_ID 	   = "org.apache.myfaces.validator.ISBN";
	/**
	 * <p>The message identifier of the {@link FacesMessage} to be created if
	 * the maximum length check fails.</p>
	 */
	public static final String ISBN_MESSAGE_ID = "org.apache.myfaces.ISBN.INVALID";

	/**
	 * <p>isbnValidator</p>
	 */
	private org.apache.commons.validator.ISBNValidator isbnValidator;

	public ISBNValidator(){
	    isbnValidator = new org.apache.commons.validator.ISBNValidator();
	}

	/**
	 * methode that validates isbn codes.
	 * it uses the commons-validator
	 */
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
        
            if (!isbnValidator.isValid( value.toString())) {
				Object[] args = {value.toString()};
                String message = getMessage();
                if (null == message)  message = ISBN_MESSAGE_ID;

                throw new ValidatorException(MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, message, args));
			}
			

	}

}