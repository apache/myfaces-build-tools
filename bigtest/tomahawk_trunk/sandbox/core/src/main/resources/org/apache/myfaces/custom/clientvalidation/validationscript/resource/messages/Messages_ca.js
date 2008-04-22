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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Hi ha hagut un error de conversi�.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Error de validaci�";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Valor obligatori.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Error de validaci�";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": El valor no es una opci� v�lida.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Error de validaci�";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": El valor no es una opci� v�lida.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Error de validaci�";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": L'atribut especificat no es troba entre els valors esperats de {0} i {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Error de validaci�";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "L'atribut especificat no es pot convertir al tipus adequat.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Error de validaci�";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": Valor superior al m�xim perm�s ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Error de validaci�";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": Valor inferior al m�nim perm�s ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Error de validaci�n";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": El valor no �s del tipus correcte.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Error de validaci�";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "L'atribut especificat no es pot convertir al tipus adequat.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Error de validaci�";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": La longitud del valor �s m�s gran que el m�xim perm�s de {0} car�cters.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Error de validaci�";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": La longitud del valor �s m�s petita que el m�nim perm�s de {0} car�cters.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Error de validaci�";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "L'atribut especificat no es pot convertir al tipus adequat.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Error de validaci�";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": Valor superior al m�xim perm�s de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Error de validaci�";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": Valor inferior al m�nim perm�s ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Error de validaci�";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": El valor no �s del tipus correcte.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\": No es pot convertir '{0}' a Boolean.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\": No es pot convertir '{0}' a Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "No es pot convertir '{0}' a Character.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": El valor especificat no �s una data/hora v�lida.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Error de conversi�";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": El valor especificat no �s un nombre v�lid.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Error de validaci�";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "l valor ({0}) no �s una adre�a de correu v�lida.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Error de validaci�";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "l valor ({0}) no �s equivalent i t� valor \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Error de validaci�";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "l valor ({0}) no �s una tarjeta de cr�dit v�lida.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "rror de validaci�";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "l valor ({0}) no �s v�lid.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Error de validaci�";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "l valor ({0}) no �s una data correcta.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "No hi ha connexi�: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Potser es troba darrera d'un tallafocs?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Error de validaci�";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "l valor ({0}) no �s un codi ISBN correcte.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
