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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Ocorreu um erro de convers�o.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Erro de valida��o";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Um valor � requerido.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Erro de valida��o";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": O valor n�o � uma op��o v�lida.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Erro de valida��o";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": O valor n�o � uma op��o v�lida.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Erro de valida��o";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": O atributo especificador n�o est� entre os valores esperados {0} e {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Erro de valida��o";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "O atributo especificado n�o pode ser convertido para o tipo apropriado.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Erro de valida��o";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": O valor � maior que o m�ximo permitido de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Erro de valida��o";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": O valor � menor que o m�nimo permitido de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Erro de valida��o";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": O valor n�o � do tipo correto.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Erro de valida��o";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "O atributo especificado n�o pode ser convertido para o tipo apropriado.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Erro de valida��o";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": O valor � mais longo do que o m�ximo permitido de {0} caracteres.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Erro de valida��o";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": O valor � mais curto do que o m�nimo permitido de {0} caracteres.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Erro de valida��o";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "O atributo especificado n�o pode ser convertido para o tipo apropriado.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Erro de valida��o";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": O valor � maior que o m�ximo permitido de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Erro de valida��o";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": O valor � menor que o m�nimo permitido de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Erro de valida��o";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": O valor n�o � do tipo correto.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\": Imposs�vel converter '{0}' para Boolean.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\": Imposs�vel converter '{0}' para Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "N�o foi poss�vel converter '{0}' para caracter.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": O valor especificado n�o � uma data/hora v�lida.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Erro de convers�o";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado n�o � um n�mero v�lido.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Erro de valida��o";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "O valor informado ({0}) n�o � um endere�o de e-mail v�lido.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Erro de valida��o";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "O valor informado ({0}) n�o � igual ao valor de \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Erro de valida��o";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "O valor informado ({0}) est� incorreto para cart�o de cr�dito";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "rro de valida��o";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "O valor informado ({0}) � inv�lido.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Erro de valida��o";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "O valor informado ({0}) n�o � uma data v�lida";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "Sem conex�o: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Talvez voc� esteja atr�s de um firewall?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "rro de valida��o";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "O valor informado ({0}) n�o � um c�digo isbn v�lido.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
