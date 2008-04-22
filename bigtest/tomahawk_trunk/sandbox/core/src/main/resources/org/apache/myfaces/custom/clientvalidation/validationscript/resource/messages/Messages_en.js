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
tomahawk.MessageBundle = new function() {
	this.messages = new Array();
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Error during model data update.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Validation Error";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Value is required.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Validation Error";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": Value is not a valid option.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Validation Error";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": Value is not a valid option.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Validation Error";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": Specified attribute is not between the expected values of {0} and {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Validation Error";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "Specified attribute cannot be converted to the proper type.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Validation Error";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": Value is greater than allowable maximum of ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Validation Error";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": Value is less than allowable minimum of ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Validation Error";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": Value is not of the correct type.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Validation Error";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "Specified attribute cannot be converted to proper type.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Validation Error";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": Value is longer than allowable maximum of {0} characters.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Validation Error";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": Value is shorter than allowable minimum of {0} characters.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Validation Error";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "Specified attribute cannot be converted to proper type.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Validation Error";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": Value is greater than allowable maximum of ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Validation Error";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": Value is less than allowable minimum of ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Validation Error";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": Value is not of the correct type.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "Specified value is not a valid number.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "Specified value is not a valid number.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "Could not convert '{0}' to Boolean.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "Could not convert '{0}' to Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "Could not convert '{0}' to Character.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": Specified value is not a valid date/time.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": Specified value is not a valid number.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": Specified value is not a valid number.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": Specified value is not a valid number.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": Specified value is not a valid number.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": Specified value is not a valid number.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Conversion Error";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": Specified value is not a valid number.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Validation Error";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "he given value ({0}) is not a correct email-address.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Validation Error";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "he given value ({0}) is not equal with value of \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Validation Error";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "he given value ({0}) is not a correct creditcard";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "alidation Error";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "he given value ({0}) is not valid.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Validation Error";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "he given value ({0}) is not a correct date";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "No Connection: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Maybe you are behind a firewall?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Validation Error";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "he given value ({0}) is not a correct isbn code.";
	this.messages["org.apache.myfaces.FileUpload.SIZE_LIMIT"] = "\"{0}\": The uploaded file exceeded the maximum size of {1} bytes.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
