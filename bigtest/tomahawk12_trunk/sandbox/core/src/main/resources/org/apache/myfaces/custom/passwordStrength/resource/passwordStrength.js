function hide(id) { 
   if (document.getElementById){ 
      obj = document.getElementById(id); 
      obj.style.display = "none"; 
   } 
}
function show(id) { 
   if (document.getElementById){ 
      obj = document.getElementById(id); 
      obj.style.display = ""; 
   }
}

function startUpPasswordStrength(objID) {
	hide(objID);
}

function increaseProgressBar(progressBarID, degree){
	var currentValue = degree * 10;
	dojo.widget.byId(progressBarID).setMaxProgressValue(10, true);
	dojo.widget.byId(progressBarID).setProgressValue(currentValue, true);	
	dojo.widget.byId(progressBarID).render();
}

function getPasswordStrengthText(degree, tokens) {
	var index = Math.round( degree * tokens.length ) - 1;
	if(index <= 0) return tokens[0];
	return tokens[ index ];
}

function isAlpha(sText) {
   for ( c=0; c < sText.length; c ++ ) {
      alpha = ( sText.charCodeAt ( c ) >= 65 &&
         sText.charCodeAt ( c ) <=90 ) ||
         ( sText.charCodeAt ( c ) >= 97 &&
         sText.charCodeAt ( c ) <=122 )
      if ( !alpha ) {
         return false;
      }
   }
   return true;
}

function isNumeric(sText) {
   var ValidChars = "0123456789";
   var IsNumber=true;
   var Char;
   for (i = 0; i < sText.length && IsNumber == true; i++) {
      Char = sText.charAt(i);
      if (ValidChars.indexOf(Char) == -1) {
         IsNumber = false;
         }
      }
   return IsNumber;
}


function isSymbol(sText) {
	var iChars = "!@#$%^&*()+=-[]\';,./{}|\":<>? ";
	for (var i = 0; i < sText.length; i++) {
  		if (iChars.indexOf(sText.charAt(i)) != -1) {
  		return true;
  		}
	}
	return false;
}

function trimString(s) {
	var l=0; var r=s.length -1;
	while(l < s.length && s.charAt(l) == ' ')
	{	l++; }
	while(r > l && s.charAt(r) == ' ')
	{	r-=1;	}
	return s.substring(l, r+1);
}

/**
* This method checks whether the character is a valid password Strength
* character pattern ...
*/
function isValidCharacterPattern(character) {
		if(character != 'S' && character != 's' &&
		   character != 'A' && character != 'a' &&
		   character != 'N' && character != 'n' ) {
		   return false;
		}
		return true;
}

function isSymbolCharacterPattern(character) {
		if(character == 'S' || character == 's')
			return true;
		return false;
}


function isNumberCharacterPattern(character) {
		if(character == 'N' || character == 'n')
			return true;
		return false;
}


function isAlphaCharacterPattern(character) {
		if(character == 'A' || character == 'a')
			return true;
		return false;
}

/**
* This function checks whether the index is a valid index of
* the password string ...
*/
function isValidRange(passwordString, index) {
	if(index >= passwordString.length) {
		return false;
	}
	return true;
}


/**
* This function check whether the password string has a valid
* sequence of symbols ...
* count :- is the number of expected symbols.
* passwordString :- the src password string.
* currentCharacterIndex :- the current character index of the password string.
* Return the current password string character index if succeeded else
* return -1.
*/
function validSymbolSequence(count, passwordString, currentCharacterIndex) {
		//Consume symbol characters ...
		for(j = 0; j < count; ++j) {
			if(!isValidRange(passwordString, currentCharacterIndex)) {return -1;}
			if(!isSymbol( passwordString.charAt(currentCharacterIndex++) ))
				return -1;
		}
		//Check whether there are more character of this type ...
		while(currentCharacterIndex < passwordString.length) {
			if(isSymbol( passwordString.charAt(currentCharacterIndex) ) ) {
				currentCharacterIndex++;
			} else {
				break;
			}
		}
		return currentCharacterIndex;
}


/**
* This function check whether the password string has a valid
* sequence of numbers ...
* count :- is the number of expected numbers.
* passwordString :- the src password string.
* currentCharacterIndex :- the current character index of the password string.
* Return the current password string character index if succeeded else
* return -1.
*/
function validNumberSequence(count, passwordString, currentCharacterIndex) {
		for(j = 0; j < count; ++j) {
			if(!isValidRange(passwordString, currentCharacterIndex)) {return -1;}
			if(!isNumeric( passwordString.charAt(currentCharacterIndex++) ))
				return -1;
		}
		//Check whether there are more character of this type ...
		while(currentCharacterIndex < passwordString.length) {
			if(isNumeric( passwordString.charAt(currentCharacterIndex) ) ) {
				currentCharacterIndex++;
			} else {
				break;
			}
		}
		return currentCharacterIndex;
}


/**
* This function check whether the password string has a valid
* sequence of alphabets ...
* count :- is the number of expected alphabets.
* passwordString :- the src password string.
* currentCharacterIndex :- the current character index of the password string.
* Return the current password string character index if succeeded else
* return -1.
*/
function validAlphaSequence(count, passwordString, currentCharacterIndex) {
		for(j = 0; j < count; ++j) {
			if(!isValidRange(passwordString, currentCharacterIndex)) {return -1;}
			if(!isAlpha( passwordString.charAt(currentCharacterIndex++) ))
				return -1;
		}
		//Check whether there are more character of this type ...
		while(currentCharacterIndex < passwordString.length) {
			if(isAlpha( passwordString.charAt(currentCharacterIndex) ) ) {
				currentCharacterIndex++;
			} else {
				break;
			}
		}
		return currentCharacterIndex;
}


/**
* This method check whether the password string complies
* with the pattern specified ...
* Note that the pattern has the following format :
* S <<Number>>  N <<Number>> A<<Number>>
* Where S stands for Symbols
* Where N stands for Numbers
* Where A stands for Alphabets
****************************************************
* For example) A4N2S3A2
* Means that the password will be as following :
* 4 or more Alphabets followed by
* 2 or more Numbers followed by
* 3 or more Symbols followed by
* 2 or more Alphabets
****************************************************
*/
function checkPattern(passwordString, patternString) {

	var INVALID_PATTERN = "Invalid pattern";
	var INVALID_PATTERN_LENGTH = "Invalid pattern length";
	var currentCharacterIndex = 0;
	var patternLength = patternString.length;

	if(trimString( patternString ).length % 2 != 0) {
		alert(INVALID_PATTERN_LENGTH);
		return false;
	}

	for(var i = 0; i < patternLength; i += 2) {
		var character = patternString.charAt(i);
		var count = patternString.charAt(i+1);
		var retval;	

		//Check pattern format ...
		if(!isNumeric(count)) {
		 	alert(INVALID_PATTERN);
		 	return false;
		}
		if(!isValidCharacterPattern(character)) {
		 	alert(INVALID_PATTERN);
		 	return false;
		}

		//Validate the pattern ...
		if(isSymbolCharacterPattern( character )) {
			retval = validSymbolSequence(count, passwordString, currentCharacterIndex);
			if(retval == -1) {
				return false;
			}
		}
		else if(isNumberCharacterPattern( character )) {
			retval = validNumberSequence(count, passwordString, currentCharacterIndex);
			if(retval == -1) {
				return false;
			}
		}
		else if(isAlphaCharacterPattern( character )) {
			retval = validAlphaSequence(count, passwordString, currentCharacterIndex);
			if(retval == -1) {
				return false;
			}
		}

		currentCharacterIndex = retval;
	}

	//The pattern is valid ...
	return true;
}

function calculatePasswordStrengthDegree(passwordString, patternString,
								         preferredLength, useCustomSecurity, 
								         penaltyRatio){
	currentLength = passwordString.length;
	var strength = (currentLength / preferredLength);
	if(strength > 1) strength=1;
	if(useCustomSecurity == "true") {
		//If the pattern is not satisified decrease the power by the penalty ...
		if(!checkPattern(passwordString, patternString)){
			return strength * (penaltyRatio/100);
		}
	}
	return strength;
}


//This method is used for online updating the status of the textbox ...
function updateStatusValue(textID, preferredLength, 
						   prefixText, passwordDesc, 
						   indicatorMessageID, leftCharsMessageID, 
						   strengthIndicatorType, progressBarId, 
						   showDetails, leftCharactersString,
						   useCustomSecurity, patternString,
						   penaltyRatio) {				   
    //Get the current message content ...
    var content = document.getElementById(textID).value;
    var currentStatus = prefixText;	     
 	var degree = calculatePasswordStrengthDegree(content, patternString,
								  			     preferredLength, useCustomSecurity,
								  			     penaltyRatio);   	
   	   	
    /**
    * Check whether to display the strength indicator as a text or as bar ...
    */
    if(strengthIndicatorType == "text") {
    
	    var tokens = passwordDesc.split(";");
								  			     
	    currentStatus += getPasswordStrengthText(degree, tokens);
	    
	    if(document.all){ 
	           document.getElementById(indicatorMessageID).innerText = currentStatus;
	    } else {
	           document.getElementById(indicatorMessageID).textContent = currentStatus;
	    }
		
    }
    else { /*Here we are dealing with bar*/
    	
		increaseProgressBar(progressBarId, degree);   	    
    }
   
   
    /**
    * Here display the left characters message part ...
    */    
    if(content.length == 0) {
        hide(leftCharsMessageID);           
    } else if(showDetails == "true" && content.length < preferredLength) {
        show(leftCharsMessageID);
	diff = (preferredLength - content.length);
	var charLeft = diff + " " + leftCharactersString;
      if(document.all) { 
             document.getElementById(leftCharsMessageID).innerText = charLeft;
      } else {
             document.getElementById(leftCharsMessageID).textContent = charLeft;
      }
    } else {
        hide(leftCharsMessageID);
    }						   						   
}




