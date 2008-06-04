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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": \u8f6c\u6362\u65f6\u53d1\u751f\u9519\u8bef.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": \u8be5\u503c\u5fc5\u987b\u586b\u5199.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": \u975e\u6cd5\u9009\u9879.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": \u975e\u6cd5\u9009\u9879.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": \u8be5\u503c\u4e0d\u5728\u5141\u8bb8\u7684 {0} \u81f3 {1} \u5305\u56f4\u5185.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "\u65e0\u6cd5\u5c06\u8be5\u5c5e\u6027\u8f6c\u6362\u6210\u5408\u9002\u7684\u7c7b\u578b.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": \u8be5\u503c\u5927\u4e8e\u5141\u8bb8\u7684\u6700\u5927\u503c ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": \u8be5\u503c\u5c0f\u4e8e\u5141\u8bb8\u7684\u6700\u5c0f\u503c ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5141\u8bb8\u7684\u7c7b\u578b.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "\u65e0\u6cd5\u5c06\u8be5\u5c5e\u6027\u8f6c\u6362\u6210\u5408\u9002\u7684\u7c7b\u578b.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": \u8be5\u503c\u957f\u5ea6\u5927\u4e8e\u5141\u8bb8\u6700\u5927\u503c {0} .";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": \u8be5\u503c\u957f\u5ea6\u5c0f\u4e8e\u5141\u8bb8\u6700\u5c0f\u503c {0} .";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "\u65e0\u6cd5\u5c06\u8be5\u5c5e\u6027\u8f6c\u6362\u6210\u5408\u9002\u7684\u7c7b\u578b.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": \u8be5\u503c\u5927\u4e8e\u5141\u8bb8\u7684\u6700\u5927\u503c ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": \u8be5\u503c\u5c0f\u4e8e\u5141\u8bb8\u7684\u6700\u5c0f\u503c ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": \u8be5\u503c\u7c7b\u578b\u4e0d\u6b63\u786e.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\": \u65e0\u6cd5\u5c06 '{0}' \u8f6c\u6362\u6210\u5e03\u5c14\u503c.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\": \u65e0\u6cd5\u5c06 '{0}' \u8f6c\u6362\u6210\u6574\u6570\u503c.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "\"{1}\": \u65e0\u6cd5\u5c06 '{0}' \u8f6c\u6362\u6210\u5b57\u7b26.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u65e5\u671f/\u65f6\u95f4\u503c.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "\u8f6c\u6362\u9519\u8bef";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": \u8be5\u503c\u4e0d\u662f\u5408\u6cd5\u7684\u6570\u503c.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = " \u5728\u57df {0} \u4e2d";
	this.messages["org.apache.myfaces.Email.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "\u6240\u7ed9\u51fa\u7684\u503c ({0}) \u4e0d\u662f\u5408\u6cd5\u7535\u5b50\u90ae\u4ef6\u5730\u5740.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "\u6240\u7ed9\u51fa\u7684\u503c ({0}) \u4e0e\u503c \"{1}\" \u4e0d\u76f8\u7b49.";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "\u6240\u7ed9\u51fa\u7684\u503c ({0}) \u4e0d\u662f\u5408\u6cd5\u4fe1\u7528\u5361\u5361\u53f7.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "\u6240\u7ed9\u51fa\u7684\u503c ({0}) \u4e0d\u5408\u6cd5.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "\u6240\u7ed9\u51fa\u7684\u503c ({0}) \u4e0d\u662f\u5408\u6cd5\u65e5\u671f.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "\u65e0\u6cd5\u8fde\u63a5: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "\u8bf7\u68c0\u67e5\u9632\u706b\u5899\u8bbe\u7f6e?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "\u9a8c\u8bc1\u9519\u8bef";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "\u6240\u7ed9\u51fa\u7684\u503c ({0}) \u4e0d\u662f\u5408\u6cd5\u7684ISBN\u4ee3\u7801.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
