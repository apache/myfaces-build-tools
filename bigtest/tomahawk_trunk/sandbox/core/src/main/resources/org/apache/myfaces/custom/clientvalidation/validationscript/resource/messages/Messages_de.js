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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Fehler beim Model-Update.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Validierungsfehler";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Eingabe erforderlich.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Validierungsfehler";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": Wert ist keine gültige Auswahl.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Validierungsfehler";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": Wert ist keine gültige Auswahl.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Validierungsfehler";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": Eingegebener Wert liegt nicht im erwarteten Bereich von {0} bis {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Validierungsfehler";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "Eingegebener Wert kann nicht in den korrekten Typ umgewandelt werden.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Validierungsfehler";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": Wert ist größer als das erlaubte Maximum ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Validierungsfehler";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": Wert ist kleiner als das erlaubte Minimum ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Validierungsfehler";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": Eingegebener Wert hat falschen Datentyp.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Validierungsfehler";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "Eingegebener Wert kann nicht in den korrekten Typ umgewandelt werden.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Validierungsfehler";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": Eingegebener Wert ist länger als die maximal zulässige Anzahl von {0} Zeichen.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Validierungsfehler";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": Eingegebener Wert ist kürzer als die minimal zulässige Anzahl von {0} Zeichen.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Validierungsfehler";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "Eingegebener Wert kann nicht in den korrekten Typ umgewandelt werden.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Validierungsfehler";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": Wert ist größer als das erlaubte Maximum ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Validierungsfehler";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": Wert ist kleiner als das erlaubte Minimum ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Validierungsfehler";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": Eingegebener Wert hat falschen Datentyp.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\": {0} konnte nicht in einen boolschen Wert umgewandelt werden.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\": {0} konnte nicht in einen Byte-Wert umgewandelt werden.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "{0} konnte nicht in ein Zeichen umgewandelt werden.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": {0} ist keine gültige Datum/Zeit-Angabe.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Konvertierungsfehler";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": Der eingegebene Wert ist keine gültige Zahl.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Validierungsfehler";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "er eingegebene Wert ({0}) ist keine korrekte E-Mail-Adresse.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Validierungsfehler";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "er eingegebene Wert ({0}) stimmt nicht mit dem Wert \"{1}\" überein.";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Validierungsfehler";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "er eingegebene Wert ({0}) ist keine korrekte Kreditkarten Nummer.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "alidierungsfehler";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "er eingegebene Wert ({0}) ist nicht korrekt.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Validierungsfehler";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "er eingegebene Wert ({0}) ist kein korrektes Datum.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "Keine Verbindung:";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Sind Sie hinter einer Firewall?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Validierungsfehler";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "er eingegebene Wert ({0}) ist keine korrekte ISBN  Nummer.";
	this.messages["org.apache.myfaces.FileUpload.SIZE_LIMIT"] = "\"{0}\": Die Größe der hochgeladenen Datei darf nicht größer als {1} Bytes sein.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
