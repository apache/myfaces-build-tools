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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Hi ha hagut un error de conversió.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Error de validació";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Valor obligatori.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Error de validació";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": El valor no es una opció vàlida.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Error de validació";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": El valor no es una opció vàlida.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Error de validació";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": L'atribut especificat no es troba entre els valors esperats de {0} i {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Error de validació";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "L'atribut especificat no es pot convertir al tipus adequat.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Error de validació";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": Valor superior al màxim permès ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Error de validació";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": Valor inferior al mínim permès ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Error de validación";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": El valor no és del tipus correcte.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Error de validació";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "L'atribut especificat no es pot convertir al tipus adequat.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Error de validació";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": La longitud del valor és més gran que el màxim permès de {0} caràcters.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Error de validació";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": La longitud del valor és més petita que el mínim permès de {0} caràcters.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Error de validació";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "L'atribut especificat no es pot convertir al tipus adequat.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Error de validació";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": Valor superior al màxim permès de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Error de validació";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": Valor inferior al mínim permès ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Error de validació";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": El valor no és del tipus correcte.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\": No es pot convertir '{0}' a Boolean.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\": No es pot convertir '{0}' a Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "No es pot convertir '{0}' a Character.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": El valor especificat no és una data/hora vàlida.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Error de conversió";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no és un nombre vàlid.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Error de validació";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "l valor ({0}) no és una adreça de correu vàlida.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Error de validació";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "l valor ({0}) no és equivalent i té valor \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Error de validació";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "l valor ({0}) no és una tarjeta de crèdit vàlida.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "rror de validació";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "l valor ({0}) no és vàlid.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Error de validació";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "l valor ({0}) no és una data correcta.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "No hi ha connexió: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Potser es troba darrera d'un tallafocs?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Error de validació";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "l valor ({0}) no és un codi ISBN correcte.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
