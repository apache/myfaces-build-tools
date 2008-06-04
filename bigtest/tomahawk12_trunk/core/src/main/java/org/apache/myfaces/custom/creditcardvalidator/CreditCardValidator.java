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
package org.apache.myfaces.custom.creditcardvalidator;

import org.apache.myfaces.validator.ValidatorBase;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * A custom validator for creditCards, based upon Jakarta Commons. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions
 * 
 * @JSFValidator
 *   name = "t:validateCreditCard"
 *   bodyContent = "empty"
 *   tagClass = "org.apache.myfaces.custom.creditcardvalidator.ValidateCreditCardTag"
 *   tagSuperclass = "org.apache.myfaces.validator.ValidatorBaseTag"
 *   serialuidtag = "3810660506302799072L"
 * 
 * @JSFJspProperty name = "message" inheritedTag="true" returnType = "java.lang.String" longDesc = "alternate validation error detail message format string (use 'message' and 'detailMessage' alternatively)"
 * @JSFJspProperty name = "detailMessage" inheritedTag="true" returnType = "java.lang.String" longDesc = "alternate validation error detail message format string (use 'message' and 'detailMessage' alternatively)"
 * @JSFJspProperty name = "summaryMessage" inheritedTag="true" returnType = "java.lang.String" longDesc = "alternate validation error summary message format string"
 * @author mwessendorf (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CreditCardValidator extends ValidatorBase {

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String 	VALIDATOR_ID 	   = "org.apache.myfaces.validator.CreditCard";

	/**
	 * <p>The message identifier of the {@link FacesMessage} to be created if
	 * the creditcard check fails.</p>
	 */
	public static final String CREDITCARD_MESSAGE_ID = "org.apache.myfaces.Creditcard.INVALID";

	//private DEFAULT_VALUES
	private static final boolean DEFAULT_AMEX = true;
	private static final boolean DEFAULT_DISCOVER = true;
	private static final boolean DEFAULT_MASTERCARD = true;
	private static final boolean DEFAULT_VISA = true;
	private static final boolean DEFAULT_NONE = false;


	public CreditCardValidator(){
	}

	//Cardtypes, that are supported by Commons-Validator.
	private Boolean _amex = null;
	private Boolean _discover = null;
	private Boolean _mastercard = null;
	private Boolean _visa = null;
	private Boolean _none = null;

	//Field, to init the desired Validator
	private int _initSum = 0;

	private org.apache.commons.validator.CreditCardValidator creditCardValidator = null;

	/**
	 *
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
		initValidator();
		if (!this.creditCardValidator.isValid(value.toString())){
			Object[] args = {value.toString()};
            throw new ValidatorException(getFacesMessage(CREDITCARD_MESSAGE_ID, args));
		}
	}


	// -------------------------------------------------------- Private Methods

	/**
	 * <p>initializes the desired validator.</p>
	 */

	private void initValidator() {
		if(isNone()){
			//no cardtypes are allowed
			creditCardValidator = new org.apache.commons.validator.CreditCardValidator(org.apache.commons.validator.CreditCardValidator.NONE);
		}
		else{
			computeValidators();
			creditCardValidator = new org.apache.commons.validator.CreditCardValidator(_initSum);
		}
	}

	/**
	 * private methode, that counts the desired creditCards
	 */
	private void computeValidators(){
		if(isAmex()){
			this._initSum= org.apache.commons.validator.CreditCardValidator.AMEX + _initSum;
		}
		if(isVisa()){
			this._initSum= org.apache.commons.validator.CreditCardValidator.VISA+ _initSum;
		}
		if(isMastercard()){
			this._initSum= org.apache.commons.validator.CreditCardValidator.MASTERCARD+ _initSum;
		}
		if(isDiscover()){
			this._initSum= org.apache.commons.validator.CreditCardValidator.DISCOVER+ _initSum;
		}
	}

	//GETTER & SETTER
	
	/**
	 * american express cards
	 * 
	 * @JSFProperty
	 */
	public boolean isAmex() {
		if (_amex!= null) return _amex.booleanValue();
		return _amex != null ? _amex.booleanValue() : DEFAULT_AMEX;
	}

    /**
     * validation for discover
     * 
     * @JSFProperty
     */
	public boolean isDiscover() {
		if (_discover!= null) return _discover.booleanValue();
		return _discover != null ? _discover.booleanValue() : DEFAULT_DISCOVER;
	}

    /**
     * validation for mastercard
     * 
     * @JSFProperty
     */
	public boolean isMastercard() {
		if (_mastercard!= null) return _mastercard.booleanValue();
		return _mastercard != null ? _mastercard.booleanValue() : DEFAULT_MASTERCARD;
	}

    /**
     * none of the given cardtypes is allowed.
     * 
     * @JSFProperty
     */
	public boolean isNone() {
		if (_none!= null) return _none.booleanValue();
		return _none != null ? _none.booleanValue() : DEFAULT_NONE;
	}

    /**
     * validation for visa
     * 
     * @JSFProperty
     */
	public boolean isVisa() {
		if (_visa!= null) return _visa.booleanValue();
		return _visa != null ? _visa.booleanValue() : DEFAULT_VISA;
	}

	public void setAmex(boolean b) {
		_amex = Boolean.valueOf(b);
	}

	public void setDiscover(boolean b) {
		_discover = Boolean.valueOf(b);
	}

	public void setMastercard(boolean b) {
		_mastercard =  Boolean.valueOf(b);
	}

	public void setNone(boolean b) {
		_none =   Boolean.valueOf(b);
	}

	public void setVisa(boolean b) {
		_visa =   Boolean.valueOf(b);
	}


	// -------------------------------------------------------- StateholderIF

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		Object values[] = new Object[6];
        values[0] = super.saveState(context);
		values[1] = _amex;
		values[2] = _discover;
		values[3] = _mastercard;
		values[4] = _visa;
		values[5] = _none;
		return values;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
		_amex = ((Boolean) values[1]);
		_discover = ((Boolean) values[2]);
		_mastercard = ((Boolean) values[3]);
		_visa = ((Boolean) values[4]);
		_none = ((Boolean) values[5]);
	}
}
