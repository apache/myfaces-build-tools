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
var tomahawk = {};

tomahawk.CVUtils = new function() {
	
	this.isEmpty = function(value)  {
		return value == '';
	}
	
	this.isBlank = function(value)  {
		return value == null || value == '';
	}
	
	this.callValidators = function(facesContext,uiinput,convertedValue) {
		try {
			for(var i = 0; i < uiinput.validators.length; i++) {
				uiinput.validators[i].validateValue( facesContext , uiinput , convertedValue);
			}
		}
		catch(validatorException) {
			uiinput.isValid = false;
			facesMessage = validatorException.facesMessage;
			if( facesMessage != null ) {
				facesMessage.severity = tomahawk.FacesMessage.SEVERITY_ERROR;
				facesContext.addMessage( uiinput.clientId, facesMessage );
			}
		}
	}
	
}
		
tomahawk.RendererUtils = new function() {

	this.renderMessage = function(facesContext,clientId) {
		var messageComponent = 	document.getElementById(clientId + "_msgFor");
		if( messageComponent == undefined || messageComponent == null)
			return;
		
		this.clean(messageComponent); // clean first

		var message = tomahawk.MessageUtils.findMessage(facesContext,clientId);
		if(message != null) {
			messageComponent.appendChild(document.createTextNode(message.detail));
		}
	}
	
	this.renderMessages = function(facesContext,uimessages) {
		var messagesComponent = document.getElementById(uimessages.clientId);	
		this.clean(messagesComponent);
			
		if(uimessages.layout == "list")
			root = document.createElement("ul");		
		else {
			root = document.createElement("table");		
			root.appendChild(document.createElement("tbody"));		// tbody for IE
		}
		
		for(var i = 0 ; i < facesContext.messages.length ; i++ ) { 
			var message =  facesContext.messages[i];
			this.addRow(uimessages,root,message);
		}
		messagesComponent.appendChild(root);
	}
		
	this.clean = function(root) {
			if(root === undefined || root == null)
				return;
				
			while(root.firstChild) 
				root.removeChild(root.firstChild);
	}
	
	this.addRow = function(uimessages,root,message) {
		if(uimessages.layout == "list") {
			row = document.createElement("li");
			row.appendChild(document.createTextNode(this.getMessageText(uimessages,message)));
			root.appendChild(row);
		}
		else {			
			row = document.createElement("tr");
			column = document.createElement("td");
			column.appendChild(document.createTextNode(this.getMessageText(uimessages,message)));
			row.appendChild(column);
			root.firstChild.appendChild(row);
		}
	}
		
	this.getMessageText = function(uimessages,facesMessage) {
		var messageText = "";
		if(uimessages.showSummary == true)
			messageText = messageText + facesMessage.summary;
		if(uimessages.showDetail == true) {
			messageText = messageText + " " + facesMessage.detail;
		}
	
		return messageText;			
	}
}
		
tomahawk.MessageUtils = new function() {

	this.MESSAGE_DEFAULT_SUFFIX = "_detail";

	this.findMessage = function(facesContext,clientId){
		for(var j = 0; j < facesContext.messages_client_ids.length; j++) {
				if(facesContext.messages_client_ids[j] == clientId ) {
					return facesContext.messages[j];
				}
		}
		return null;
	}
	
	this.getMessage = function(severity,messageID,args) {
		var summary = tomahawk.MessageBundle.getString( messageID );
		var detail = tomahawk.MessageBundle.getString( messageID + this.MESSAGE_DEFAULT_SUFFIX );
		
		if(args != null && args.length > 0) {
			summary = this.formatMessage(summary,args);
			detail = this.formatMessage(detail,args);
		}
		return new tomahawk.FacesMessage(severity,summary,detail);
	}
	
	this.formatMessage = function(message,args) {
		str = message;
		for(var i=0 ; i < args.length ; i++) {
			var pattern = new RegExp("\\{" + i + "\\}", "g" );
			str = str.replace(pattern, args[i]);
		}
		return str;
	}
	
	this.addErrorMessage = function(context,uiinput,messageID, args) {
		var message = this.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR, messageID, args);
		context.addMessage(uiinput.clientId, message);
	}
	
}
	
tomahawk.FacesClientContext = function() {

	this.messages = new Array();
	this.messages_client_ids = new Array();
	
	this.addMessage = function(clientId, facesMessage) {
		this.messages_client_ids[this.messages_client_ids.length] = clientId;
		this.messages[this.messages.length] = facesMessage;
	}
	
	this.clearMessages = function() {
		this.messages_client_ids.length=0;
		this.messages.length = 0;
	}

}

tomahawk.UIViewRoot = function() {
	this.children = new Array();
	
	this.addChild = function(uiinput) {
		this.children[this.children.length] = uiinput;
	}
}

tomahawk.Severity = function(type,ordinal) {
	this.type = type;
	this.ordinal = ordinal;
}

tomahawk.FacesMessage = function(severity, summary, detail) {
	this.severity = severity;
	this.summary = summary;
	this.detail = detail;
}

tomahawk.FacesMessage.SEVERITY_INFO = new tomahawk.Severity("Info",1);
tomahawk.FacesMessage.SEVERITY_WARN = new tomahawk.Severity("Warn",2);
tomahawk.FacesMessage.SEVERITY_ERROR = new tomahawk.Severity("Error",3);
tomahawk.FacesMessage.SEVERITY_FATAL = new tomahawk.Severity("Fatal",4);

tomahawk.ConverterException = function(facesMessage) {
	this.facesMessage = facesMessage;
}

tomahawk.ValidatorException = function(facesMessage) {
	this.facesMessage = facesMessage;
}

/**
* UIMessages Component
**/
tomahawk.UIMessages = function(clientId,layout,showSummary,showDetail) {
	this.clientId = clientId;
	this.layout = layout;
	this.showSummary = showSummary;
	this.showDetail = showDetail;
}
	
/**
* UIInput
**/
tomahawk.UIInput = function(id,clientId, required,converter,validators) {
	this.id = id;
	this.clientId = clientId;
	this.submittedValue = document.getElementById(clientId).value;
	this.required = required;
	this.converter = converter;
	this.validators = validators;
	this.isValid = true;
	this.REQUIRED_MESSAGE_ID = "javax.faces.component.UIInput.REQUIRED";
	this.CONVERSION_MESSAGE_ID = "javax.faces.component.UIInput.CONVERSION";
	
	this.getConvertedValue = function(facesContext,submittedValue) {
		try {
			if( this.converter != null ) {
				return this.converter.getAsObject(facesContext, this, submittedValue);
			}
		}catch( converterException ) {
			facesMessage = converterException.facesMessage;
			if( facesMessage != null ) {
				facesContext.addMessage( this.clientId, facesMessage );
			}
			else {
				tomahawk.MessageUtils.addErrorMessage(facesContext, this, this.CONVERSION_MESSAGE_ID, new Array(this.id));
			}
			this.isValid = false;
		}			
		return submittedValue;
	}
	
	this.validateValue = function(facesContext,convertedValue) {
		var empty = tomahawk.CVUtils.isBlank( convertedValue );
		if( empty && this.required == true) {
			tomahawk.MessageUtils.addErrorMessage(facesContext, this, this.REQUIRED_MESSAGE_ID,  new Array(this.id));
			this.isValid = false;
			return;
		}
		
		if( !empty ) {
			tomahawk.CVUtils.callValidators( facesContext , this , convertedValue );
		}
	}
	
	this.validate = function(context) {
		if( this.submittedValue == null )
			return;
	
		convertedValue = this.getConvertedValue( context, this.submittedValue );
		if( !this.isValid )
			return;
		
		this.validateValue( context , convertedValue );
	}
}
	
	
tomahawk.processValidations = function(facesContext) {
	 viewRoot = facesContext.viewRoot;
	 for(var i = 0; i < viewRoot.children.length ; i ++) { 
		 viewRoot.children[i].validate(facesContext); 
	 }
}
	
tomahawk.executeClientLifeCycle = function() {
	var bypassClientValidationElement = document.getElementById('tomahawk.bypassClientValidation');
	if( bypassClientValidationElement != null && bypassClientValidationElement.value == "true")
		return true;	//skip client validation
	
	var facesContext = new tomahawk.FacesClientContext();
	tomahawk.createView(facesContext);
	tomahawk.processValidations(facesContext);
	tomahawk.renderResponse(facesContext);
	if( facesContext.messages.length > 0 ) 
		return false; 
 	else 
 		return true;
}
