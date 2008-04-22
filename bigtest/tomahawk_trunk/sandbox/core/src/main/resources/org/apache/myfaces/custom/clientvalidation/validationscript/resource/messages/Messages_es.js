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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Ha ocurrido un error de conversi�n.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Error de validaci�n";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Valor requerido.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Error de validaci�n";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": El valor no es una opci�n v�lida.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Error de validaci�n";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": El valor no es una opci�n v�lida.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Error de validaci�n";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": El atributo especificado no se encuentra entre los valores esperados {0} y {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Error de validaci�n";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "El atributo especificado no se puede convertir al tipo adecuado.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Error de validaci�n";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": Valor superior al m�ximo permitido de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Error de validaci�n";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": Valor inferior al m�nimo permitido de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Error de validaci�n";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": El valor no es del tipo correcto.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Error de validaci�n";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "El atributo especificado no se puede convertir al tipo adecuado.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Error de validaci�n";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": La longitud del valor es mayor al m�ximo permitido de {0} caracteres.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Error de validaci�n";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": La longitud del valor es inferior al m�nimo permitido de {0} caracteres.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Error de validaci�n";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "El atributo especificado no se puede convertir al tipo adecuado.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Error de validaci�n";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": Valor superior al m�ximo permitido de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Error de validaci�n";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": Valor inferior al m�nimo permitido de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Error de validaci�n";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": El valor no es del tipo correcto.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\": No se puede convertir '{0}' a Boolean.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\": No se puede convertir '{0}' a Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "No se puede convertir '{0}' a Character.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": El valor espefificado no es una fecha/hora v�lida.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Error de conversi�n";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": El valor especificado no es un n�mero v�lido.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Error de validaci�n";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "l valor ({0}) no es una direcci�n de correo v�lida.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Error de validaci�n";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "l valor ({0}) no es equivalente y tiene valor \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Error de validaci�n";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "l valor ({0}) no es una tarjeta de cr�dito correcta.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "rror de validaci�n";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "l valor ({0}) no es v�lido.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Error de validaci�n";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "l valor ({0}) no es una fecha correcta.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "No hay conexi�n: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "�Quiz� est� detr�s de un cortafuegos?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Error de validaci�n";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "l valor ({0}) no es un c�digo ISBN correcto.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
