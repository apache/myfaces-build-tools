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
	this.messages["javax.faces.component.UIInput.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.component.UIInput.CONVERSION_detail"] = "\"{0}\": Ocorreu um erro de conversão.";
	this.messages["javax.faces.component.UIInput.REQUIRED"] = "Erro de validação";
	this.messages["javax.faces.component.UIInput.REQUIRED_detail"] = "\"{0}\": Um valor é requerido.";
	this.messages["javax.faces.component.UISelectOne.INVALID"] = "Erro de validação";
	this.messages["javax.faces.component.UISelectOne.INVALID_detail"] = "\"{0}\": O valor não é uma opção válida.";
	this.messages["javax.faces.component.UISelectMany.INVALID"] = "Erro de validação";
	this.messages["javax.faces.component.UISelectMany.INVALID_detail"] = "\"{0}\": O valor não é uma opção válida.";
	this.messages["javax.faces.validator.NOT_IN_RANGE"] = "Erro de validação";
	this.messages["javax.faces.validator.NOT_IN_RANGE_detail"] = "\"{2}\": O atributo especificador não está entre os valores esperados {0} e {1}.";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT"] = "Erro de validação";
	this.messages["javax.faces.validator.DoubleRangeValidator.LIMIT_detail"] = "O atributo especificado não pode ser convertido para o tipo apropriado.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM"] = "Erro de validação";
	this.messages["javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail"] = "\"{1}\": O valor é maior que o máximo permitido de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM"] = "Erro de validação";
	this.messages["javax.faces.validator.DoubleRangeValidator.MINIMUM_detail"] = "\"{1}\": O valor é menor que o mínimo permitido de ''{0}''.";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE"] = "Erro de validação";
	this.messages["javax.faces.validator.DoubleRangeValidator.TYPE_detail"] = "\"{0}\": O valor não é do tipo correto.";
	this.messages["javax.faces.validator.LengthValidator.LIMIT"] = "Erro de validação";
	this.messages["javax.faces.validator.LengthValidator.LIMIT_detail"] = "O atributo especificado não pode ser convertido para o tipo apropriado.";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM"] = "Erro de validação";
	this.messages["javax.faces.validator.LengthValidator.MAXIMUM_detail"] = "\"{1}\": O valor é mais longo do que o máximo permitido de {0} caracteres.";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM"] = "Erro de validação";
	this.messages["javax.faces.validator.LengthValidator.MINIMUM_detail"] = "\"{1}\": O valor é mais curto do que o mínimo permitido de {0} caracteres.";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT"] = "Erro de validação";
	this.messages["javax.faces.validator.LongRangeValidator.LIMIT_detail"] = "O atributo especificado não pode ser convertido para o tipo apropriado.";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM"] = "Erro de validação";
	this.messages["javax.faces.validator.LongRangeValidator.MAXIMUM_detail"] = "\"{1}\": O valor é maior que o máximo permitido de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM"] = "Erro de validação";
	this.messages["javax.faces.validator.LongRangeValidator.MINIMUM_detail"] = "\"{1}\": O valor é menor que o mínimo permitido de ''{0}''.";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE"] = "Erro de validação";
	this.messages["javax.faces.validator.LongRangeValidator.TYPE_detail"] = "\"{0}\": O valor não é do tipo correto.";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.BigDecimalConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.BigIntegerConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.BooleanConverter.CONVERSION_detail"] = "\"{1}\": Impossível converter '{0}' para Boolean.";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.ByteConverter.CONVERSION_detail"] = "\"{1}\": Impossível converter '{0}' para Byte.";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.CharacterConverter.CONVERSION_detail"] = "Não foi possível converter '{0}' para caracter.";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.DateTimeConverter.CONVERSION_detail"] = "\"{1}\": O valor especificado não é uma data/hora válida.";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.DoubleConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.FloatConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.IntegerConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["javax.faces.convert.LongConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.LongConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.NumberConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION"] = "Erro de conversão";
	this.messages["javax.faces.convert.ShortConverter.CONVERSION_detail"] = "\"{0}\": O valor especificado não é um número válido.";
	this.messages["org.apache.myfaces.renderkit.html.HtmlMessagesRenderer.IN_FIELD"] = "\u0020in {0}";
	this.messages["org.apache.myfaces.Email.INVALID"] = "Erro de validação";
	this.messages["org.apache.myfaces.Email.INVALID_detail"] = "O valor informado ({0}) não é um endereço de e-mail válido.";
	this.messages["org.apache.myfaces.Equal.INVALID"] = "Erro de validação";
	this.messages["org.apache.myfaces.Equal.INVALID_detail"] = "O valor informado ({0}) não é igual ao valor de \"{1}\".";
	this.messages["org.apache.myfaces.Creditcard.INVALID"] = "Erro de validação";
	this.messages["org.apache.myfaces.Creditcard.INVALID_detail"] = "O valor informado ({0}) está incorreto para cartão de crédito";
	this.messages["org.apache.myfaces.Regexpr.INVALID"] = "rro de validação";
	this.messages["org.apache.myfaces.Regexpr.INVALID_detail"] = "O valor informado ({0}) é inválido.";
	this.messages["org.apache.myfaces.Date.INVALID"] = "Erro de validação";
	this.messages["org.apache.myfaces.Date.INVALID_detail"] = "O valor informado ({0}) não é uma data válida";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION"] = "Sem conexão: ";
	this.messages["org.apache.myfaces.ticker.NOCONNECTION_detail"] = "Talvez você esteja atrás de um firewall?";
	this.messages["org.apache.myfaces.ISBN.INVALID"] = "rro de validação";
	this.messages["org.apache.myfaces.ISBN.INVALID_detail"] = "O valor informado ({0}) não é um código isbn válido.";

	this.getString = function(key) {
		return this.messages[key];
	}
}
