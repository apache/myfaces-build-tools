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
tomahawk.LengthValidator = function(min,max) {
		
	this.MAXIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MAXIMUM";
	this.MINIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MINIMUM";
		
	this.validateValue = function(facesContext,uiinput,value) {

		if( value == null )
			return;
		
			var length = value.length;
				
			if(min != null) {
				if(length < min) {
					var args = new Array(min, uiinput.id);
					var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.MINIMUM_MESSAGE_ID,args);
					throw new tomahawk.ValidatorException( facesMessage );
				}
			}
			
			if(max != null) {
				if(length> max) {
					var args = new Array(max, uiinput.id);
					var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.MAXIMUM_MESSAGE_ID,args);
					throw new tomahawk.ValidatorException( facesMessage );
				}
			}
	}
}

tomahawk.LongRangeValidator = function(min,max) {

	this.MAXIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MAXIMUM";
	this.MINIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MINIMUM";
	this.NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.NOT_IN_RANGE";
	this.TYPE_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.TYPE";
		
	this.validateValue = function(facesContext,uiinput,value) {
		if(value == null)
			return;
			
		if (min != null && max != null)
        {
            if (value < min || value > max)
            {
            	var args = new Array(min,max,uiinput.id);
	            var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.NOT_IN_RANGE_MESSAGE_ID,args);
	            throw new tomahawk.ValidatorException(facesMessage);
            }
        }
        else if (min != null)
        {
            if (value < min)
            {
				var args = new Array(min,uiinput.id);
	            var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.MINIMUM_MESSAGE_ID,args);
	            throw new tomahawk.ValidatorException(facesMessage);
            }
        }
        else if (max != null)
        {
            if (value > max)
            {
                var args = new Array(max,uiinput.id);
	            var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.MAXIMUM_MESSAGE_ID,args);
	            throw new tomahawk.ValidatorException(facesMessage);
            }
        }
	}
}

tomahawk.DoubleRangeValidator = function(min,max) {

	this.MAXIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MAXIMUM";
	this.MINIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MINIMUM";
	this.NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.NOT_IN_RANGE";
	this.TYPE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.TYPE";
		
	this.validateValue = function(facesContext,uiinput,value) {
		if(value == null)
			return;
			
		if (min != null && max != null)
        {
            if (value < min || value > max)
            {
            	var args = new Array(min,max,uiinput.id);
	            var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.NOT_IN_RANGE_MESSAGE_ID,args);
	            throw new tomahawk.ValidatorException( facesMessage );
            }
        }
        else if (min != null)
        {
            if (value < min)
            {
				var args = new Array(min,uiinput.id);
	            var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.MINIMUM_MESSAGE_ID,args);
	            throw new tomahawk.ValidatorException( facesMessage );
            }
        }
        else if (max != null)
        {
            if (value > max)
            {
                var args = new Array(max,uiinput.id);
	            var facesMessage = tomahawk.MessageUtils.getMessage(tomahawk.FacesMessage.SEVERITY_ERROR,this.MAXIMUM_MESSAGE_ID,args);
	            throw new tomahawk.ValidatorException( facesMessage );
            }
        }
	}
}