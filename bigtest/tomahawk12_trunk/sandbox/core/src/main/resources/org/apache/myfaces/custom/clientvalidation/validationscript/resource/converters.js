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
tomahawk.IntegerConverter = function() {
		
	this.CONVERSION_MESSAGE_ID = "javax.faces.convert.IntegerConverter.CONVERSION";

	this.getAsObject = function(context,uiinput,value) {
		
		if( value != null ) {
			//TODO trim
			if( value.length > 0)  {
				var integerRegExp = /^(\+|-)?\d+$/;
				var isInteger = integerRegExp.test(value);						
				if( !isInteger ) {
					var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.CONVERSION_MESSAGE_ID,new Array(uiinput.id,value))
					throw new tomahawk.ConverterException( facesMessage );
				}
				else {
					var convertedValue = parseInt( value );
					return convertedValue;
				}
			}
		}
		return null;
	}
}

tomahawk.DoubleConverter = function() {
		
	this.CONVERSION_MESSAGE_ID = "javax.faces.convert.DoubleConverter.CONVERSION";

	this.getAsObject = function(context,uiinput,value) {
		
		if( value != null ) {
			//TODO trim
			if( value.length > 0)  {
				var doubleRegExp = /^(\+|-)?\d*\.?\d*([eE]\d+)?[dD]?$/;
				var isDouble = doubleRegExp.test(value);						
				if( !isDouble ) {
					var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.CONVERSION_MESSAGE_ID,new Array(uiinput.id,value))
					throw new tomahawk.ConverterException( facesMessage );
				}
				else {
					var convertedValue = parseFloat( value );
					return convertedValue;
				}
			}
		}
		return null;
	}
}

tomahawk.FloatConverter = function() {
		
	this.CONVERSION_MESSAGE_ID = "javax.faces.convert.FloatConverter.CONVERSION";

	this.getAsObject = function(context,uiinput,value) {
		
		if( value != null ) {
			//TODO trim
			if( value.length > 0)  {
				var floatRegExp = /^(\+|-)?\d*\.?\d*([eE]\d+)?[fF]?$/;
				var isFloat = floatRegExp.test(value);						
				if( !isFloat ) {
					var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.CONVERSION_MESSAGE_ID,new Array(uiinput.id,value))
					throw new tomahawk.ConverterException( facesMessage );
				}
				else {
					var convertedValue = parseFloat( value );
					return convertedValue;
				}
			}
		}
		return null;
	}
}

tomahawk.LongConverter = function() {
		
	this.CONVERSION_MESSAGE_ID = "javax.faces.convert.LongConverter.CONVERSION";

	this.getAsObject = function(context,uiinput,value) {
		
		if( value != null ) {
			//TODO trim
			if( value.length > 0)  {
				var integerRegExp = /^(\+|-)?\d+$/;
				var isInteger = integerRegExp.test(value);						
				if( !isInteger ) {
					var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.CONVERSION_MESSAGE_ID,new Array(uiinput.id,value))
					throw new tomahawk.ConverterException( facesMessage );
				}
				else {
					var convertedValue = parseInt( value );
					return convertedValue;
				}
			}
		}
		return null;
	}
}

tomahawk.ShortConverter = function() {
		
	this.CONVERSION_MESSAGE_ID = "javax.faces.convert.ShortConverter.CONVERSION";

	this.getAsObject = function(context,uiinput,value) {
		
		if( value != null ) {
			//TODO trim
			if( value.length > 0)  {
				var integerRegExp = /^(\+|-)?\d+$/;
				var isInteger = integerRegExp.test(value);						
				if( !isInteger ) {
					var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.CONVERSION_MESSAGE_ID,new Array(uiinput.id,value))
					throw new tomahawk.ConverterException( facesMessage );
				}
				else {
					var convertedValue = parseInt( value );
					return convertedValue;
				}
			}
		}
		return null;
	}
}