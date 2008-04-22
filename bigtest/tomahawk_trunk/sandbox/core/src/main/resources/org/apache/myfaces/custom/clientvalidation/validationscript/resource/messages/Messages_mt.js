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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\"\: Inqalg\u0127et problema fil-konver\u017Cjoni.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\"\: Valur huwa me\u0127tie\u0121.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\"\: Il-valur mhux g\u0127a\u017Cla valida.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\"\: Il-valur mhux g\u0127a\u017Cla valida.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\"\: Il-valur spe\u010Bifikat mhux bejn il-valuri mistennija ta' {0} u {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "Il-valur spe\u010Bifikat ma jistax jinqaleb fit-tip mistenni.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\"\: Il-valur huwa ikbar mill-massimu ta' \"{0}\".";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\"\: Il-valur huwa i\u017Cg\u0127ar mill-minimu ta' \"{0}\".";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\"\: Il-valur mhux tat-tip korrett.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "Il-valur spe\u010Bifikat ma jistax jinqaleb g\u0127at-tip korrett.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\"\: Il-valur huwa itwal mill-massimu ta' {0} karattri.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\"\: Il-valur huwa iqsar mill-minimu ta' {0} karattri.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "Il-valur spe\u010Bifikat ma jistax jinqaleb g\u0127at-tip korrett.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\"\: Il-valur huwa ikbar mill-massimu ta' \"{0}\".";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\"\: Il-valur huwa i\u017Cg\u0127ar mill-minimu ta' \"{0}\".";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Problema fil-validazzjoni";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\"\: Il-valur mhux tat-tip korrett.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\"\: Il-valur \"{0}\" ma setax jinqaleb g\u0127al boolean.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\"\: Il-valur \"{0}\" ma setax jinqaleb g\u0127al byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "\"{1}\"\: Il-valur \"{0}\" ma setax jinqaleb g\u0127al karattru.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\"\: Il-valur \"{0}\" ma setax jinqaleb g\u0127al data/\u0127in.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\"\: Il-valur spe\u010Bifikat mhux numru validu.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = " f' {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Problema fil-validazzjoni";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "l-valur mog\u0127ti ({0}) mhux indirizz tal-email validu.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Problema fil-validazzjoni";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "l-valur mog\u0127ti ({0}) mhux ugwali g\u0127all-valur ta' \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Problema fil-validazzjoni";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "l-valur mog\u0127ti ({0}) mhux karta tal-kreditu korrett.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "roblema fil-validazzjoni";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "l-valur mog\u0127ti ({0}) mhux validu.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Problema fil-validazzjoni";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "l-valur mog\u0127ti ({0}) mhux data korretta.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "M'hemmx konnessjoni\: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Forsi qieg\u0127ed wara firewall?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Problema fil-validazzjoni";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "l-valur mog\u0127ti ({0}) mhux kodi\u010Bi isbn validu.";
	this.messages["org.apache.myfaces.tree2.MISSING_NODE"] = "Ferg\u0127a nieqsa";
	this.messages["org.apache.myfaces.tree2.MISSING_NODE_detail"] = "Il-ferg\u0127a mitluba \"{0}\" ma te\u017Cistix.";
	this.messages["org.apache.myfaces.calendar.CONVERSION"] = "Problema fil-konver\u017Cjoni";
	this.messages["org.apache.myfaces.calendar.CONVERSION_detail"] = "\"{0}\"\: Il-valur \"{1}\" ma setax jinqaleb f' data.";
	this.messages["org.apache.myfaces.FileUpload.SIZE_LIMIT"] = "\"{0}\"\: Il-fajl mibg\u0127ut qabe\u017C id-daqs massimu ta' {1} byte.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
