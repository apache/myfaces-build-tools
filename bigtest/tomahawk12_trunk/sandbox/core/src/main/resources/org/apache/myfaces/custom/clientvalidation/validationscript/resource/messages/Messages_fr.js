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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Erreur durant la mise \u00e0 jour des donn\u00e9es.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Erreur de validation";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Une donn\u00e9e est requise.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Erreur de Validation";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": La donn\u00e9e n''est pas une option valide.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Erreur de Validation";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": La donn\u00e9e n''est pas une option valide.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Erreur de Validation";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": La donn\u00e9e n''est pas comprise entre {0} et {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Erreur de Validation";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "La donn\u00e9e ne peut \u00e9tre convertie dans le bon type.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Erreur de Validation";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": La donn\u00e9e est sup\u00e9rieur au maximum authoris\u00e9 de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Erreur de Validation";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": La donn\u00e9e est inf\u00e9rieur au maximum authoris\u00e9 de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Erreur de Validation";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": La donn\u00e9e n''est pas du bon type.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Erreur de Validation";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "La donn\u00e9e ne peut \u00e9tre convertie dans le bon type.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Erreur de Validation";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": La donn\u00e9e a plus que les {0} caract\u00e9res maximum authoris\u00e9s.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Erreur de Validation";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": La donn\u00e9e a moins que les {0} caract\u00e9res maximum requis.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Erreur de Validation";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "La donn\u00e9e ne peut \u00e9tre convertie dans le bon type.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Erreur de Validation";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": La donn\u00e9e est sup\u00e9rieur au maximum authoris\u00e9 de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Erreur de Validation";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": La donn\u00e9e est inf\u00e9rieur au maximum authoris\u00e9 de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Erreur de Validation";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": La donn\u00e9e n''est pas du bon type.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "La donn\u00e9e n''est pas un nombre valide.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "La donn\u00e9e n''est pas un nombre valide.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "Conversion de '{0}' en Boolean impossible.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "Conversion de '{0}' en Octet impossible.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "Conversion de '{0}' en Caract\u00e8re impossible.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": Conversion de '{0}' en Date/Heure impossible.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": La donn\u00e9e n''est pas un nombre valide.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": La donn\u00e9e n''est pas un nombre valide.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": La donn\u00e9e n''est pas un nombre valide.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": La donn\u00e9e n''est pas un nombre valide.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": La donn\u00e9e n''est pas un nombre valide.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Erreur de conversion";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": La donn\u00e9e n''est pas un nombre valide.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Erreur de Validation";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "a valeur ({0}) n'est pas un email correct.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Erreur de Validation";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "a valeur ({0}) n'est pas \u00e9gale \u00e0 \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Erreur de Validation";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "a valeur ({0}) n'est pas un bon num\u00e9ro de carte de cr\u00e9dit.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "rreur de Validation";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "a valeur ({0}) n'est pas valide.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Erreur de Validation";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "a valeur ({0}) n'est pas une date correcte.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "Pas de connexion : ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Peut-\u00eatre \u00eates-vous derri\u00e8re un firewall ";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "Erreur de Validation";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "a valeur ({0}) n'est pas un code ISBN correct.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
