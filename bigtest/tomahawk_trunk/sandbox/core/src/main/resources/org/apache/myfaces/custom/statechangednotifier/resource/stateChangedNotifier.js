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
function traceTime(message) {
	var now = new Date();
	dojo.debug(message + " " + now.getTime());
} 
var myfaces_stateChange_globalExclusionList = new Array();
var myfaces_stateChange_notificationFields = new Array();
/**
* central function definition for the state change notifier
*/
function org_apache_myfaces_StateChangedNotifier(paramnotifierName, paramformId, paramhiddenFieldId, parammessage, paramexcludeCommandIdList) {
    traceTime("begin init");
    this.notifierName = paramnotifierName;
    /*affected notifier controls*/
    this.notifierDialogName = paramnotifierName + "_Dialog";
    this.notifierDialogContentName = paramnotifierName + "_Dialog_Content";
    this.notifierYesButtonName = paramnotifierName + "_Dialog_Yes";
    this.notifierNoButtonName = paramnotifierName + "_Dialog_No";
    this.formId = paramformId;
    this.hiddenFieldId = paramhiddenFieldId;
    this.message = parammessage;
    this.excludeCommandIdList = paramexcludeCommandIdList;
    this.arrCommandIds = new Array();
    this.objectsToConfirmList = new Array();
    this.objectsToConfirmBeforeExclusion = new Array();
    
    /*we store the array of notifier hidden fields in the documents for components
    which want to reference it themselves for state change notification
    every form there is should have the list of notifier components
    stored
    */
    //TODO only mark the parent form of the hidden field 
    var formArr = document.getElementsByTagName("form");
	var found = false;
	for (var notifyId in myfaces_stateChange_notificationFields) {
		if(myfaces_stateChange_notificationFields[notifyId] == paramhiddenFieldId)  {
			found = true; 
			break;
		}
	}
	if(!found)  {
		myfaces_stateChange_notificationFields.push(paramhiddenFieldId);
	}    
    
    /*global exclusion list singleton which keeps track of all exclusions over all entire forms
		we cannot allow that some predefined exclusions
		are rendered invalid by another tag
	*/
    /**
	* initialize the dialog subsystem
	*/
    dojo.debug("form1__idJsp1Notifier_Dialog_Yes");
    dojo.debug("." + this.notifierYesButtonName + ".");
    var yesButton = dojo.byId(this.notifierYesButtonName);
    var noButton = dojo.byId(paramnotifierName + "_Dialog_No");
    dojo.debug("system initialized" + yesButton);
    var dlg = dojo.widget.createWidget("Dialog", {id:(paramnotifierName + "_Dialog_dojo"), bgColor:"white", bgOpacity:0.5, toggle:"fade", toggleDuration:250}, dojo.byId(this.notifierDialogName));
	traceTime("end init");
};
org_apache_myfaces_StateChangedNotifier.prototype.getDialog = function () {
    return dojo.widget.manager.getWidgetById(this.notifierDialogName + "_dojo");
};
org_apache_myfaces_StateChangedNotifier.prototype.getYesButton = function () {
    return dojo.byId(this.notifierYesButtonName);
};
org_apache_myfaces_StateChangedNotifier.prototype.getNoButton = function () {
    return dojo.byId(this.notifierNoButtonName);
};
org_apache_myfaces_StateChangedNotifier.prototype.getContent = function () {
    return dojo.byId(this.notifierDialogContentName);
};
org_apache_myfaces_StateChangedNotifier.prototype.showDialog = function (text, yesButtonText, noButtonText) {
    var dialog = this.getDialog();
    var noButton = this.getNoButton();
    var yesButton = this.getYesButton();
    var content = this.getContent();
    yesButton.value = yesButtonText;
    noButton.value = noButtonText;
    content.innerHTML = text;
    dialog.show();
};
/**
* prepares the notifier entry fields
* and trigger filters
* for the entire mechanism
*/
org_apache_myfaces_StateChangedNotifier.prototype.prepareNotifier = function () {
    traceTime("prepareNotifier onchangeadd");
    this.addOnChangeListener("input");
    this.addOnChangeListener("textarea");
    this.addOnChangeListener("select");
    traceTime("prepareNotifier onchangeadd end");
    var globalExclLen = myfaces_stateChange_globalExclusionList.length;
    for (var cnt = 0; cnt < globalExclLen; cnt += 1) {
        this.arrCommandIds.push(myfaces_stateChange_globalExclusionList[cnt]);
    }
    traceTime("prepareNotifier onchangeadd push done");
    if (this.excludeCommandIdList !== null) {
        var newIds = this.excludeCommandIdList.split(",");

		//we do not filter double entries for now
		//since the number of exclusion tags will be kept small
		//anyway
        traceTime("prepareNotifier command id push");
        for (var cnt = globalExclLen; cnt < (globalExclLen + newIds.length); cnt += 1) {
            myfaces_stateChange_globalExclusionList.push(newIds[cnt - globalExclLen]);
            this.arrCommandIds.push(newIds[cnt - globalExclLen]);
        }
        traceTime("prepareNotifier onchangeadd push done");
        this.addObjectsToConfirmList("a", null);
        this.addObjectsToConfirmList("input", "button");
        this.addObjectsToConfirmList("input", "submit");
        this.addObjectsToConfirmList("button", null);
        traceTime("prepareNotifier confirm list push done");
        this.putConfirmExcludingElements();
    }
};
org_apache_myfaces_StateChangedNotifier.prototype.changeHiddenValue = function (evt) {
    var hiddenField = dojo.byId(this.hiddenFieldId);
    hiddenField.value = "true";
};
org_apache_myfaces_StateChangedNotifier.prototype.addOnChangeListener = function (tagName) {
    var arrElements = document.getElementsByTagName(tagName);
    for (var i = 0; i < arrElements.length; i += 1) {
        dojo.event.connect(arrElements[i], "onchange", this, "changeHiddenValue");
    }
};
org_apache_myfaces_StateChangedNotifier.prototype.addObjectsToConfirmList = function (tagName, tagType) {
    var arrElements = document.getElementsByTagName(tagName);
    for (var i = 0; i < arrElements.length; i += 1) {
        var elementId = arrElements[i].id;
        var onclick = arrElements[i].onclick;
        if (tagType === null || (tagType !== null && arrElements[i].type === tagType)) {
            if (elementId !== null && onclick !== null && elementId !== "") {
                this.objectsToConfirmBeforeExclusion.push(elementId);
            }
        }
    }
};
org_apache_myfaces_StateChangedNotifier.prototype.putConfirmExcludingElements = function () {
    for (var cnt = 0; cnt < this.objectsToConfirmBeforeExclusion.length; cnt += 1) {
        var elementId = this.objectsToConfirmBeforeExclusion[cnt];
        if (!this.isElementExcluded(elementId)) {
            this.objectsToConfirmList.push(elementId);
        } else {
            this.removeConfirmInElement(elementId); //remove old includes from the list if we get one
        	//putNoConfirmInElement(elementId); // we need a special resetter list which reset the control if triggered
        }
    }
    for (var cnt2 = 0; cnt2 < this.objectsToConfirmList.length; cnt2 += 1) {
        var objectToConfirm = this.objectsToConfirmList[cnt2];
        this.putConfirmInElement(objectToConfirm);
    }
};
org_apache_myfaces_StateChangedNotifier.prototype.isElementExcluded = function (elementId) {
    if (this.arrCommandIds === null || (this.arrCommandIds.length == 1 && (this.arrCommandIds[0] === null || this.arrCommandIds[0] === ""))) {
        return false;
    }
    for (var i = 0; i < this.arrCommandIds.length; i += 1) {
        var excludedId = this.arrCommandIds[i];
        var idRegex = null;
        if (elementId.indexOf(":") > -1) {
            idRegex = new RegExp(".*" + excludedId + "([\\d+])?");
        } else {
            idRegex = new RegExp(excludedId + "([\\d+])?");
        }
        if (elementId.match(idRegex) !== null) {
            return true;
        }
    }
};
org_apache_myfaces_StateChangedNotifier.prototype.resetHiddenField = function () {
    var hiddenField = dojo.byId(this.hiddenFieldId);
    if (hiddenField !== null) {
        hiddenField.value = "false";
    }
};
/**
* builds up a show message function
* dependend on the correct browser
*/
org_apache_myfaces_StateChangedNotifier.prototype.showMessage = function () {
    var hiddenField = dojo.byId(this.hiddenFieldId);
    if (hiddenField.value == "true") {
            //if (!confirm(message)) return false;
        var confirmit = confirm(this.message);
        if (confirmit) {
            hiddenField.value = "false";
        }//TODO possible error
        return confirmit;
    }
    return true;
};
org_apache_myfaces_StateChangedNotifier.prototype.showDojoDlgMsg = function () {
    dojodebug("show dlg");
    alert("pause");
    /*  var hiddenField = dojo.byId(this.hiddenFieldId);
    var oldonclickstr = dojo.byId(commandid);
    if (dojo.render.html.ie) {
            oldonclickstr = oldonclickstr.replace(/function anonymous()/, "");
    }
    if (hiddenField.value == "true") {

    	var dlg = this.getDialog();
    	var content = this.getContent();
    	var yesButton = this.getYesButton();


        if (dojo.render.html.ie) {
            oldonclickstr = "" + this.notifierName + ".resetHiddenField(); " + oldonclickstr + " ;";
            yesButton("onclick", new Function("", onclickstr));

        } else {
            yesButton("onclick", "" + this.notifierName + ".resetHiddenField();" + oldonclickstr + " ;");
        }
        dlg.show();
    } else {
    	eval(oldonclickstr);
    }   */
};
org_apache_myfaces_StateChangedNotifier.prototype.removeConfirmInElement = function (commandId) {
    var command = dojo.byId(commandId);
    var oldOnClick = command.getAttribute("old_onclick");
    if (oldOnClick !== null) {
        command.setAttribute("onclick", oldOnClick);
    }
};
org_apache_myfaces_StateChangedNotifier.prototype.putNoConfirmInElement = function (commandId) {
    var command = dojo.byId(commandId);
    if (command !== null) {
        var onclick = command.getAttribute("onclick");
        var onclickstr = onclick + "";
        if (onclickstr.indexOf("/*myfacesnotifyinjected*/;") == -1) {
            command.setAttribute("old_onclick", onclickstr);
        }
        if (dojo.render.html.ie) {
            onclickstr = onclickstr.replace(/function anonymous\(\)/, "");
            onclickstr = "" + this.notifierName + ".resetHiddenField(); /*myfacesnotifyinjected*/;" + onclickstr + " ;";
            command.setAttribute("onclick", new Function("", onclickstr));
        } else {
            command.setAttribute("onclick", "" + this.notifierName + ".resetHiddenField();/*myfacesnotifyinjected*/;" + onclick + " ;");
        }
    }
};

org_apache_myfaces_StateChangedNotifier.prototype.showconfirm = function (commandid) {
    var hiddenField = dojo.byId(this.hiddenFieldId);
    var oldonclickstr = dojo.byId(commandid).getAttribute("old_onclick");
    dojo.debug("dialog triggered" + hiddenField.value);
    if (dojo.render.html.ie) {
        oldonclickstr = oldonclickstr.replace(/function anonymous\(\)/, "");
    }
    if (hiddenField.value == "true") {
        dojo.debug("dialog triggered" + this.getDialog());
        var content 	= this.getContent();
        var yesButton 	= this.getYesButton();
        var noButton 	= this.getNoButton();

		content.innerHTML = this.message;

        if (dojo.render.html.ie) {
            oldonclickstr = this.notifierName + ".getDialog().hide();" + this.notifierName + ".resetHiddenField(); /*myfacesnotifyinjected*/;" + oldonclickstr + " ;";
            yesButton.setAttribute("onclick", new Function("", oldonclickstr));
        	noButton.setAttribute("onclick", new Function("",this.notifierName + ".getDialog().hide();"));
        } else {
            yesButton.setAttribute("onclick", this.notifierName + ".getDialog().hide();" + this.notifierName + ".resetHiddenField();/*myfacesnotifyinjected*/;" + oldonclickstr + " ;");
        	noButton.setAttribute("onclick", this.notifierName + ".getDialog().hide();");
        }

        var dlg = this.getDialog();
        dlg.show();
    } else {
   		var oldClick = new Function("oldClick",oldonclickstr);
   		oldClick();
    }
};
org_apache_myfaces_StateChangedNotifier.prototype.putConfirmInElement = function (commandId) {
    var command = dojo.byId(commandId);
    if (command !== null) {
        var onclick = command.getAttribute("onclick");
        var onclickstr = onclick + "";
        if (onclickstr.indexOf("/*myfacesnotifyinjected*/;") == -1) {
            command.setAttribute("old_onclick", onclickstr);
        }
        if (dojo.render.html.ie) {
            onclickstr = onclickstr.replace(/function anonymous\(\)/, "");
            /*onclickstr = "if (" + this.notifierName + ".showMessage()) { " + onclickstr + " }";
            command.setAttribute("onclick", new Function("", onclickstr));
            */
            command.setAttribute("onclick", new Function("", "/*myfacesnotifyinjected*/;" + this.notifierName + ".showconfirm('" + commandId + "')"));

           // dojo.io.bind(command, "onclick",this,"showDojoDlgMsg");
        } else {
            command.setAttribute("onclick", "/*myfacesnotifyinjected*/;" + this.notifierName + ".showconfirm('" + commandId + "')");
        }
    }
};

