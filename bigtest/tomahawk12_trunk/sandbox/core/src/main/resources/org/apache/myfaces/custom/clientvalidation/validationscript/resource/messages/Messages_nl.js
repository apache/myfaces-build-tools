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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Conversiefout";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": De gegeven waarde kon niet worden geïnterpreteerd.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Verplicht";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Een waarde is verplicht voor dit veld.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Ongeldig";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "De gekozen waarde is niet geldig.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Ongeldig";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "Een gekozen waarde is niet geldig.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Buiten bereik";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": De gegeven waarde valt niet tussen de verwachte waarden {0} en {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Validatiefout";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "De gegeven waarde is geen double.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Waarde te groot";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": De gegeven waarde is groter dan het toegelaten maximum ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Waarde te klein";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": De gegeven waarde is kleiner dan het toegelaten minimum ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Type fout";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": De invoer kan niet worden geïnterpreteerd als een decimaal getal.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Validatiefout";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "De gegeven waarde kon niet worden omgezet in een waarde van het benodigde type.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Invoer te groot";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": De invoer is langer dan het toegelaten maximum van {0} karakters.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Invoer te kort";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": De invoer is korter dan het toegelaten minimum van {0} karakters.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Validatiefout";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "De gegeven waarde kon niet worden omgezet in een waarde van het benodigde type.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Waarde te groot";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": De gegeven waarde is groter dan het toegelaten maximum ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Waarde te klein";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": De gegeven waarde is kleiner dan het toegelaten maximum ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Type fout";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": De invoer kan niet worden geïnterpreteerd als een geheeld getal.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Geen decimaal getal";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "De gegeven invoer kon niet worden geïnterpreteerd als een decimaal getal.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Geen geheel getal";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "De gegeven invoer kon niet worden geïnterpreteerd als een geheel getal.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Niet 'waar' of 'vals'";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "De gegeven invoer kon niet worden geïnterpreteerd '{0}' als 'waar' of 'vals'.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Geen Byte";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "De gegeven invoer kon niet worden geïnterpreteerd '{0}' als Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Geen karakter";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "De gegeven invoer kon niet worden geïnterpreteerd '{0}' als karakter.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Geen datum of tijd";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": De gegeven invoer kon niet worden geïnterpreteerd als datum of tijd.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Geen decimaal getal";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": De gegeven invoer kon niet worden geïnterpreteerd als een decimaal getal.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Geen decimaal getal";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": De gegeven invoer kon niet worden geïnterpreteerd als een decimaal getal.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Geen geheel getal";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": De gegeven invoer kon niet worden geïnterpreteerd als een geheel getal.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Geen geheel getal";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": De gegeven invoer kon niet worden geïnterpreteerd als een geheel getal";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Geen getal";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": De gegeven invoer kon niet worden geïnterpreteerd als een getal";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Geen geheel getal";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": De gegeven invoer kon niet worden geïnterpreteerd als een geheel getal";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Geen email adres";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "e gegeven invoer ({0}) is geen correct email adres.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Niet gelijk";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "e gegeven invoer ({0}) is niet gelijk aan \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Geen kredietkaartnummer";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "e gegeven invoer ({0}) is geen correct kredietkaartnummer.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "iet geldig";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "e gegeven invoer ({0}) is niet geldig.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Geen datum";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "e gegeven invoer ({0}) kon niet worden geïnterpreteerd als datum.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "Geen connectie:";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "U bevindt zich misschien achter een firewall?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Geen ISBN-nummer";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "e gegeven invoer ({0}) is geen correct ISBN-nummer.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
