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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Errore durante l'' aggiornamento del data model";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Errore di convalida";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Il valore è obbligatorio";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Errore di convalida";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": Opzione non valida";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Errore di convalida";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": Opzione non valida";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Errore di convalida";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": il valore specificato non è compreso tra {0} e {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Errore di convalida";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "Il valore specificato non è convertibile in un tipo appropriato.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Errore di convalida";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": Il valore è superiore al massimo ammesso di ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Errore di convalida";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": Il valore è inferiore al minimo ammesso di ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Errore di convalida";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": Il valore non è di tipo corretto.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Errore di convalida";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "Il valore specificato non è convertibile in un tipo appropriato.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Errore di convalida";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": Il valore è più lungo del massimo ammesso di {0} caratteri.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Errore di convalida";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": Il valore è più corto del minimo ammessi di {0} caratteri.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Errore di convalida";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "Il valore specificato non è convertibile in un tipo appropriato.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Errore di convalida";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": Il valore è superiore al massimo ammesso di ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Errore di convalida";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": Il valore è inferiore al minimo ammesso di ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Errore di convalida";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": Il valore non è di tipo corretto.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "Il valore specificato non è un numero valido.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "Il valore specificato non è un numero valido.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "Impossibile convertire '{0}' in booleano.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "Impossibile convertire '{0}' in Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "Impossibile convertire '{0}' in carattere.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": Il valore specificato non è ua data/ora valida.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": Il valore specificato non è un numero (double) valido.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": Il valore specificato non è un numero (float) valido.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": Il valore specificato non è un numero intero valido.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": Il valore specificato non è un numero (long) valido.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": Il valore specificato non è un numero valido.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Errore di conversione";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": Il valore specificato non è un numero (short) valido.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Errore di convalida";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "l valore ({0}) non è un indirizzo e-mail corretto.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Errore di convalida";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "l valore ({0}) non è uguale al valore di \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Errore di convalida";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "l valore ({0}) non è un codice di carta di credito valido.";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "rrore di convalida";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "l valore ({0}) non è valido.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Errore di convalida";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "l valore ({0}) non è una data corretta.";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "Nessuna connessione: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Forse si è dietro ad un firewall ?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "rrore di convalida";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "l valore ({0}) non è un codice ISBN corretto.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
