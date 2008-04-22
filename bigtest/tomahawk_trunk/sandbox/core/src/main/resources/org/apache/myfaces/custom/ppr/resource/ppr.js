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

//Declare the myfaces package in the JS Context

dojo.provide("org.apache.myfaces");

//Define the Partial Page Rendering Controller Class

org.apache.myfaces.PPRCtrl = function(formId, showDebugMessages, stateUpdate)
{
    this.blockPeriodicalUpdateDuringPost = false;
    this.showDebugMessages = showDebugMessages;
    this.stateUpdate = stateUpdate;
    this.linkIdRegexToExclude = '';
    this.waitBeforePeriodicalUpdate = null;
    this.periodicalRegexLinkFound = false;
    this.componentUpdateFunction = null;

    this.subFormId = new Array();

    if (!window.oamPartialTriggersToZoneIds)
    {
        window.oamPartialTriggersToZoneIds = new Array();
    }
    /* new reconnect
     if(!window.oamEventHandlerToInputIds)
     {
     window.oamEventHandlerToInputIds = new Array();
     }
     */
    if (!window.oamInlineLoadingMessage)
    {
        window.oamInlineLoadingMessage = new Array();
    }
    if (!window.oamRefreshTimeoutForZoneId)
    {
        window.oamRefreshTimeoutForZoneId = new Array();
    }
    if (!this.triggerPatternConfig)
    {
        this.triggerPatternConfig = new Array();
    }
    if (!this.triggerConfig)
    {
        this.triggerConfig = new Array();
    }

    this.replaceFormSubmitFunction(formId);

    this.reConnectEventHandlers();
}

// set the component update function to use instead of the default
// provide a function with the signature:
// function(formNode, destinationElement, pprResponseElement)
org.apache.myfaces.PPRCtrl.prototype.setComponentUpdateFunction = function(componentUpdateFunction)
{
    this.componentUpdateFunction = componentUpdateFunction;
};

// set the subform id this ppr belongs to
org.apache.myfaces.PPRCtrl.prototype.setSubFormId = function(subFormId, refreshZoneId)
{
    this.subFormId[refreshZoneId] = subFormId;
};

//Method to register individual HTML to be displayed instead of the component during loading

org.apache.myfaces.PPRCtrl.prototype.addInlineLoadingMessage = function(message, refreshZoneId)
{
    window.oamInlineLoadingMessage[refreshZoneId] = message;
};

//Method for ppr-panel-groups to register regular expressions for partial update triggering

org.apache.myfaces.PPRCtrl.prototype.addPartialTriggerPattern = function(pattern, refreshZoneId)
{
    var triggerPatternStruct = new Object();
    triggerPatternStruct["pattern"] = pattern;
    triggerPatternStruct["refreshZoneId"] = refreshZoneId;
    this.triggerPatternConfig.push(triggerPatternStruct);

    this._addPartialTriggerPattern(pattern, refreshZoneId);
}

org.apache.myfaces.PPRCtrl.prototype._addPartialTriggerPattern = function(pattern, refreshZoneId)
{
    //Register partial triggers for all matching buttons and links
    for (var f = 0; f < document.forms.length; f++)
    {
        var currentForm = document.forms[f];
        //search all buttons by iterating all inputs
        var buttons = currentForm.getElementsByTagName("input");
        for (var i = 0; i < buttons.length; i++)
        {
            var formElement = buttons[i];
            if (this.isMatchingPattern(pattern, formElement.id))
                this._addPartialTrigger(formElement.id, null, refreshZoneId);
        }
        //search all links
        var links = currentForm.getElementsByTagName("a");
        for (var i = 0; i < links.length; i++)
        {
            if (this.isMatchingPattern(pattern, links[i].id))
                this._addPartialTrigger(links[i].id, null, refreshZoneId);
        }
    }
};

//Method for ppr-panel-groups to register their partial triggers

org.apache.myfaces.PPRCtrl.prototype.addPartialTrigger = function(inputElementId, eventHookArr, refreshZoneId)
{
    var triggerStruct = new Object();
    triggerStruct["inputElementId"] = inputElementId;
    triggerStruct["eventHookArr"] = eventHookArr;
    triggerStruct["refreshZoneId"] = refreshZoneId;
    this.triggerConfig.push(triggerStruct);

    this._addPartialTrigger(inputElementId, eventHookArr, refreshZoneId);
}

org.apache.myfaces.PPRCtrl.prototype._addPartialTrigger = function(inputElementId, eventHookArr, refreshZoneId)
{

    this._cachingAddEventHandlerForId(inputElementId, eventHookArr, "elementOnEventHandler");

    this._addInputAndZone(window.oamPartialTriggersToZoneIds, inputElementId, refreshZoneId);
};

org.apache.myfaces.PPRCtrl.prototype._addInputAndZone = function(arr, inputElementId, refreshZoneId)
{

    if (arr["_oam_ppr_seen"] === undefined)
    {
        arr["_oam_ppr_seen"] = new Object();
    }

    if (arr[inputElementId] === undefined)
    {
        arr[inputElementId] = refreshZoneId;

        var seenArray = new Array();
        seenArray.push(refreshZoneId);
        arr["_oam_ppr_seen"][inputElementId] = seenArray;
    }
    else
    {
        var seenArray = arr["_oam_ppr_seen"][inputElementId];
        for (var i = 0; i < seenArray.length; i++)
        {
            if (seenArray[i] == refreshZoneId)
            {
                // already added
                return;
            }
        }
        seenArray.push(refreshZoneId);

        arr[inputElementId] =
        arr[inputElementId] +
        "," +
        refreshZoneId;
    }
}

//Method for JSF Components to register their Periodical Triggers

org.apache.myfaces.PPRCtrl.prototype.addPeriodicalTrigger = function(inputElementId, eventHookArr, refreshZoneId, refreshTimeout)
{

    this._cachingAddEventHandlerForId(inputElementId, eventHookArr, "elementOnPeriodicalEventHandler");

    this._addInputAndZone(window.oamPartialTriggersToZoneIds, inputElementId, refreshZoneId);

    if (window.oamRefreshTimeoutForZoneId[refreshZoneId] === undefined)
    {
        window.oamRefreshTimeoutForZoneId[refreshZoneId] = refreshTimeout;
    }
};

// registering an interceptor onsubmit function on each form to block
// periodical refresh and updating the dom if a page submit occurs.
// Blocking should only occur if onsubmit() returns true, otherwise not
// and not for links which are excluded via a regex pattern.

org.apache.myfaces.PPRCtrl.prototype.registerOnSubmitInterceptor = function()
{
    var ppr = this;

    for (var i = 0; i < document.forms.length; i++)
    {
        var form = document.forms[i];
        var origOnsubmit = form.onsubmit;
        form.onsubmit = function(event)
        {
            var returnValue = false;

            if (null != origOnsubmit && typeof origOnsubmit != "undefined")
            {
                var doSubmit = origOnsubmit();
                if (doSubmit || typeof doSubmit == "undefined")
                {
                    returnValue = true;
                }
            }
            else
            {
                returnValue = true;
            }

            if (ppr.linkIdRegexToExclude != '')
            {
                for (var i = 0; i < document.forms.length; i++)
                {
                    var form = document.forms[i];
                    var linkName = form.id + ':_idcl';
                    var clickedLink = form.elements[linkName];

                    if (clickedLink && clickedLink.value.match(ppr.linkIdRegexToExclude))
                    {
                        ppr.periodicalRegexLinkFound = true;
                        break;
                    }
                }
            }
            ppr.blockPeriodicalUpdateDuringPost = true;
            return returnValue;
        }
    }
};

// registering link-regex which pattern should not block the periodical update when being clicked

org.apache.myfaces.PPRCtrl.prototype.excludeFromStoppingPeriodicalUpdate = function(idRegex)
{
    this.linkIdRegexToExclude = idRegex;
};

// init function of automatically partial page refresh

org.apache.myfaces.PPRCtrl.prototype.startPeriodicalUpdate = function(refreshTimeout, refreshZoneId, waitBeforeUpdate)
{
    var ppr = this;
    ppr.waitBeforePeriodicalUpdate = waitBeforeUpdate;
    setTimeout(function()
    {
        ppr.doPeriodicalUpdate(refreshTimeout, refreshZoneId)
    }, refreshTimeout);
};

// periodically called when updating automatically

org.apache.myfaces.PPRCtrl.prototype.doPeriodicalUpdate = function(refreshTimeout, refreshZoneId)
{
    var content = new Array;
    content["org.apache.myfaces.PPRCtrl.triggeredComponents"] = refreshZoneId;

    if (!this.blockPeriodicalUpdateDuringPost)
    {
        this.doAjaxSubmit(content, refreshTimeout, refreshZoneId);
    }
    else if (this.periodicalRegexLinkFound)
    {
        var ppr = this;
        setTimeout(function()
        {
            ppr.blockPeriodicalUpdateDuringPost = false;
            ppr.periodicalRegexLinkFound = false;
            ppr.doAjaxSubmit(content, refreshTimeout, refreshZoneId);
        }, ppr.waitBeforePeriodicalUpdate)
    }
};

//Callback Method which handles the AJAX Response

org.apache.myfaces.PPRCtrl.prototype.handleCallback = function(type, data, evt)
{
    if (type == "load" && !this.formNode.myFacesPPRCtrl.blockPeriodicalUpdateDuringPost)
    {
        var componentUpdates = data.getElementsByTagName("component");

        //In case no componentUpdates are returned the response is considered
        //invalid - do a normal submit to get the corresponding error page
        if (componentUpdates == null || componentUpdates.length == 0)
        {
            this.formNode.myFacesPPRCtrl.callbackErrorHandler();
            return;
        }

        var componentUpdate = null;
        var domElement = null;

        for (var i = 0; i < componentUpdates.length; i++)
        {
            componentUpdate = componentUpdates[i];
            domElement = dojo.byId(componentUpdate.getAttribute("id"));
            if (this.formNode.myFacesPPRCtrl.componentUpdateFunction != null)
            {
                var componentUpdateDom = document.createElement("div");
                componentUpdateDom.innerHTML = componentUpdate.firstChild.data;

                eval(this.formNode.myFacesPPRCtrl.componentUpdateFunction)(this.formNode, domElement, componentUpdateDom);

                componentUpdateDom.innerHTML=""; // garbage collect
            }
            else
            {
                //todo - doesn't work with tables in IE (not used for tables at the moment)
                domElement.innerHTML = componentUpdate.firstChild.data;
            }
              //parse the new DOM element for script tags and execute them
            var regex = /<script([^>]*)>([\s\S]*?)<\/script>/i;
            var s = domElement.innerHTML;
            while (match = regex.exec(s))
            {
                var script = match[2];
                if (script.length > 5 && script.substring(0, 4) == "<!--")
                {
                    // strip html comment start to make ppr work with ie 5.5
                    script = script.substring(4);
                }
                eval(script);
                s = s.substr(0, match.index) + s.substr(match.index + match[0].length);
            }
        }

        var currentElement = null;
        var messageTexts = new Array();
        var appendMessagesToElements = new Array();
        var clearElements = new Array();
        var messages = data.getElementsByTagName("message");

        for (var i = 0; i < messages.length; i++)
        {
            messageTexts.push(messages[i].firstChild.data);
        }

        var appends = data.getElementsByTagName("append");

        for (var i = 0; i < appends.length; i++)
        {
            currentElement = dojo.byId(appends[i].getAttribute("id"));
            if (typeof currentElement != "undefined")
                appendMessagesToElements.push(currentElement);
        }

        var replaces = data.getElementsByTagName("replace");

        for (var i = 0; i < replaces.length; i++)
        {
            currentElement = dojo.byId(replaces[i].getAttribute("id"));
            if (typeof currentElement != "undefined")
            {
                appendMessagesToElements.push(currentElement);
                clearElements.push(currentElement);
            }
        }

        //clear all to replace messages components
        for (var i = 0; i < clearElements.length; i++)
        {
            this.formNode.myFacesPPRCtrl.clearElement(clearElements[i]);
        }
        //insert the messages into all messages-components which shall be updated
        for (var i = 0; i < appendMessagesToElements.length; i++)
        {
            for (var m = 0; m < messageTexts.length; m++)
            {
                this.formNode.myFacesPPRCtrl.displayMessage(messageTexts[m], appendMessagesToElements[i]);
            }
        }

        //ensure that new buttons in the PartialUpdate also have onclick-handlers
        this.formNode.myFacesPPRCtrl.reConnectEventHandlers();

        if (this.formNode.myFacesPPRCtrl.stateUpdate)
        {
            var stateElem = data.getElementsByTagName("state")[0];
            var stateUpdate = dojo.dom.firstElement(stateElem, 'INPUT');

            if (stateUpdate)
            {
                var stateUpdateId = stateUpdate.getAttribute('id');

                if (stateUpdateId == 'javax.faces.ViewState')
                {
                    var formArray = document.forms;

                    for (var i = 0; i < formArray.length; i++)
                    {
                        var form = formArray[i];
                        var domElement = form.elements['javax.faces.ViewState'];
                        if (domElement)
                            domElement.value = stateUpdate.getAttribute('value');
                    }
                }
                else if (this.formNode.myFacesPPRCtrl.showDebugMessages)
                    alert("server didn't return appropriate element for state-update. returned element-id: " +
                          stateUpdate.getAttribute('id') + ", value : " + stateUpdate.getAttribute('value'));
            }
        }
    }
    else if (!this.formNode.myFacesPPRCtrl.blockPeriodicalUpdateDuringPost)
    {
        // In case of an error during the AJAX Request do a normal form submit
        // to enable showing a proper error page
        this.formNode.myFacesPPRCtrl.callbackErrorHandler();
    }
}

org.apache.myfaces.PPRCtrl.prototype.clearElement = function (element)
{
    if (element.nodeName.toLowerCase() == "table")
    {
        this.removeAllChildNodes(element.firstChild);
    }
    else if (element.nodeName.toLowerCase() == "ul")
    {
        this.removeAllChildNodes(element);
    }
}

org.apache.myfaces.PPRCtrl.prototype.removeAllChildNodes = function (node)
{
    while (node.firstChild != null)
    {
        node.removeChild(node.firstChild);
    }
}

org.apache.myfaces.PPRCtrl.prototype.displayMessage = function (message, messageElement)
{
    if (typeof messageElement == "undefined")
        return;
    if (messageElement.nodeName.toLowerCase() == "table")
    {
        this.appendMessageToTable(messageElement, message);
    }
    else if (messageElement.nodeName.toLowerCase() == "ul")
    {
        this.appendMessageToList(messageElement, message);
    }
}

org.apache.myfaces.PPRCtrl.prototype.appendMessageToTable = function (table, message)
{
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    var textNode = document.createTextNode(message);
    td.appendChild(textNode);
    tr.appendChild(td);
    table.firstChild.appendChild(tr);
}

org.apache.myfaces.PPRCtrl.prototype.appendMessageToList = function (list, message)
{
    var li = document.createElement("li");
    var span = document.createElement("span");
    var textNode = document.createTextNode(message);
    span.appendChild(textNode);
    li.appendChild(span);
    list.appendChild(li);
}

org.apache.myfaces.PPRCtrl.prototype.callbackErrorHandler = function()
{

    if (!this.lastSubmittedElement)
    {
        if (this.showDebugMessages)
        {
            alert("An unexpected error occured during an ajax-request - page has been fully submitted!");
        }
        this.form.submit();
    }

    var formName = this.form.id;

    if (!formName)
    {
        formName = this.form.name;
    }

    var triggerElement = this.lastSubmittedElement;

    if (triggerElement.tagName.toLowerCase() == "input" &&
        (triggerElement.type.toLowerCase() == "submit" ||
         triggerElement.type.toLowerCase() == "image")
            )
    {
        var hiddenInputName = triggerElement.name;
                //Rename the button that we can insert a hidden input
        //that simulates a click of the submit-button
        triggerElement.name = "changed" + triggerElement.name;
        oamSetHiddenInput(formName, hiddenInputName, triggerElement.value);
    }
    else
    {
        oamSetHiddenInput(formName, formName + ':' + '_idcl', triggerElement.id);
    }
    this.form.submit();
}

//This Method checks if an AJAX Call is to be done instead of submitting the form
//as usual. If so it uses dojo.bind to submit the mainform via AJAX

org.apache.myfaces.PPRCtrl.prototype.ajaxSubmitFunction = function(triggerElement)
{
    var formName = this.form.id;

    if (!formName)
    {
        formName = this.form.name;
    }

    this.lastSubmittedElement = triggerElement;

    var refreshZoneId = window.oamPartialTriggersToZoneIds[triggerElement.id];

    var triggeredComponents = this.getTriggeredComponents(triggerElement.id);
    this.displayInlineLoadingMessages(triggeredComponents);

    var content = new Array();
    content["org.apache.myfaces.PPRCtrl.triggeredComponents"] = triggeredComponents;

    //todo: check why this is necessary - it shouldn't be necessary, a button should be submitted just the same as everything else
    if (this._isButton(triggerElement))
        content[triggerElement.id] = triggerElement.id;

    this.doAjaxSubmit(content, null, refreshZoneId)
}

org.apache.myfaces.PPRCtrl.prototype.doAjaxSubmit = function(content, refreshTimeout, refreshZoneId)
{
    var ppr = this;
    var requestUri = "";
    var formAction = this.form.attributes["action"];
    if (formAction == null)
    {
        requestUri = location.href;
    }
    else
    {
        requestUri = formAction.nodeValue;
    }

    if (this.subFormId[refreshZoneId])
    {
        content["org.apache.myfaces.custom.subform.submittedId"] = this.subFormId[refreshZoneId];
    }
    content["org.apache.myfaces.PPRCtrl.ajaxRequest"] = "true";

    dojo.io.bind({
        url        : requestUri,
        method    : "post",
        useCache: false,
        content    : content,
        handle    : this.handleCallback,
        mimetype: "text/xml",
        transport: "XMLHTTPTransport",
        formNode: this.form
    });

    if (refreshTimeout)
    {
        if (!this.blockPeriodicalUpdateDuringPost || !this.periodicalRegexLinkFound)
        {
            setTimeout(function()
            {
                ppr.doPeriodicalUpdate(refreshTimeout, refreshZoneId);
            }, refreshTimeout);
        }
    }
};

//This Method replaces the content of the PPRPanelGroups which have
//an inline-loading-message set with the loading message

org.apache.myfaces.PPRCtrl.prototype.displayInlineLoadingMessages = function(components)
{
    if (!components)
    {
        return;
    }
    var componentIds = components.split(',');
    var domElement = null;
    for (var i = 0,length = componentIds.length; i < length; i++)
    {
        if (window.oamInlineLoadingMessage[componentIds[i]])
        {
            domElement = dojo.byId(componentIds[i]);
            if (domElement != null)
            {
                domElement.innerHTML = window.oamInlineLoadingMessage[componentIds[i]];
            }
        }
    }
}

//this method replaces the mainform submit-function to call AJAX-submit
org.apache.myfaces.PPRCtrl.prototype.formSubmitReplacement = function(invocation)
{
    if (this.triggeredElement)
    {
        this.ajaxSubmitFunction(this.triggeredElement);
        this.triggeredElement = null;
        return false;
    }
    else
    {
        return invocation.proceed();
    }
}

//The submit function of the mainform is replaced with the AJAX submit method
//This method is called during the initialization of a PPR Controller

org.apache.myfaces.PPRCtrl.prototype.replaceFormSubmitFunction = function(formId)
{
    var form = dojo.byId(formId);
    if ((typeof form == "undefined" || form.tagName.toLowerCase() != "form")
            && this.showDebugMessages)
    {
        alert("MyFaces PPR Engine: Form with id:" + formId + " not found!");
        return;
    }

    this.form = form;
    form.myFacesPPRCtrl = this;

    dojo.event.kwConnect({
        adviceType: "around",
        srcObj: form,
        srcFunc: "onsubmit",
        targetObj: this,
        targetFunc: "formSubmitReplacement"}
            );
}

org.apache.myfaces.PPRCtrl.prototype._addEventHandlerForId = function (formElementId, connectToEventArr, eventHandler)
{
    var formElement = dojo.byId(formElementId);

    if (formElement)
        this._addEventHandler(formElement, connectToEventArr, eventHandler);
    /* where is the log?
     else
     log.error("Input element with id : "+formElementId +" not found.");
     */
}

//Really connect all deffered event handlers
//Also has to be done after a Response has been processed
org.apache.myfaces.PPRCtrl.prototype.reConnectEventHandlers = function()
{

    for (var i = 0; i < this.triggerPatternConfig.length; i++)
    {
        var struct = this.triggerPatternConfig[i];
        this._addPartialTriggerPattern(
                struct["pattern"],
                struct["refreshZoneId"]);
    }

    for (var i = 0; i < this.triggerConfig.length; i++)
    {
        var struct = this.triggerConfig[i];
        this._addPartialTrigger(
                struct["inputElementId"],
                struct["eventHookArr"],
                struct["refreshZoneId"]);
    }

    /* new reconnect
     for (var e = 0; e < window.oamEventHandlerToInputIds.length; e++) {
     var elem = window.oamEventHandlerToInputIds[e];
     this._addEventHandlerForId(elem['formElementId'],elem['connectToEventArr'],elem['eventHandler']);
     }
     */
}

//Store the information about to be connected event-handlers for connecting
//them initially and after each PPR Response
org.apache.myfaces.PPRCtrl.prototype._cachingAddEventHandlerForId = function (formElementId, connectToEventArr, eventHandler)
{
    /* new reconnect
     var element = new Array();
     element['formElementId'] = formElementId;
     element['eventHandler'] = eventHandler;
     element['connectToEventArr'] = connectToEventArr;
     window.oamEventHandlerToInputIds.push(element);
     */
    this._addEventHandlerForId(formElementId, connectToEventArr, eventHandler);
}
//combine 2 arrays ensuring that every element is only present once
org.apache.myfaces.PPRCtrl.prototype._combineArrays = function (array1, array2)
{
    var retval = new Array();
    for (var i = 0; i < array1.length; i++)
    {
        retval.push(array1[i]);
    }
    for (var i = 0; i < array2.length; i++)
    {
        if (!this._contains(retval, array2[i]))
            retval.push(array2[i]);
    }
}

org.apache.myfaces.PPRCtrl.prototype._contains = function (array, element)
{
    for (var i = 0; i < array.length; i++)
    {
        if (array[i] == element)
            return true;
    }
    return false;
}

org.apache.myfaces.PPRCtrl.prototype._addEventHandler = function (formElement, connectToEventArr, eventHandler)
{

    if (!connectToEventArr || connectToEventArr.length == 0)
    {
        connectToEventArr = new Array();
        if (this._isButton(formElement) || this._isCheckbox(formElement) || this._isRadio(formElement) || this._isLink(formElement))
        {
            //for these element-types, onclick is appropriate
            connectToEventArr.push("onclick");
        }
        else if (this._isText(formElement) || this._isDropdown(formElement))
        {
            //for these element-types, onblur will be working.
            //attention: onchange won't work properly in IE6 at least (field will never loose focus)
            connectToEventArr.push("onblur");
        }
    }

    for (var i = 0; i < connectToEventArr.length; i++)
    {

        dojo.event.kwConnect({
            adviceType: "before",
            srcObj:     formElement,
            srcFunc:    connectToEventArr[i],
            targetObj:  this,
            targetFunc: eventHandler,
            once:       true
        });
    }
}


org.apache.myfaces.PPRCtrl.prototype._isDropdown = function (formElement)
{
    return formElement.tagName.toLowerCase() == "select";
}

org.apache.myfaces.PPRCtrl.prototype._isButton = function (formElement)
{
    return formElement.tagName.toLowerCase() == "input" &&
           (formElement.type.toLowerCase() == "submit" ||
            formElement.type.toLowerCase() == "image");
}

org.apache.myfaces.PPRCtrl.prototype._isLink = function (formElement)
{
    return formElement.tagName.toLowerCase() == "a";
}

org.apache.myfaces.PPRCtrl.prototype._isText = function (formElement)
{
    return formElement.tagName.toLowerCase() == "input" &&
           (formElement.type.toLowerCase() == "text" );
}

org.apache.myfaces.PPRCtrl.prototype._isCheckbox = function (formElement)
{
    return formElement.tagName.toLowerCase() == "input" &&
           (formElement.type.toLowerCase() == "checkbox" );
}

org.apache.myfaces.PPRCtrl.prototype._isRadio = function (formElement)
{
    return formElement.tagName.toLowerCase() == "input" &&
           (formElement.type.toLowerCase() == "radio" );
}


//PointCutAdvisor which invokes the AJAX Submit Method of the PPR Controller after custom
//onclick-handlers for submit-buttons and submit-images

org.apache.myfaces.PPRCtrl.prototype.elementOnEventHandler = function (event)
{
    if (event.currentTarget === undefined)
        this.triggeredElement = event.srcElement;
    else
        this.triggeredElement = event.currentTarget;

    if (!(this._isButton(this.triggeredElement) || this._isLink(this.triggeredElement)))
    {
        this.formSubmitReplacement();
    }
}

org.apache.myfaces.PPRCtrl.prototype.elementOnPeriodicalEventHandler = function(event)
{
    if (event.target.oam_periodicalStarted)
    {
        return false;
    }
    else
    {
        var zoneIds = window.oamPartialTriggersToZoneIds[event.target.id];
        if (!zoneIds) return false;

        var zones = zoneIds.split(",");
        for (var i = 0; i < zones.length; i++)
        {
            var zoneId = zones[i];
            var timeout = window.oamRefreshTimeoutForZoneId[zoneId];
            if (!timeout) return false;
            this.doPeriodicalUpdate(timeout, zoneId);
            event.target.oam_periodicalStarted = true;
        }
    }
}

//Based on the Component which triggerd the submit this Method returns a comma-seperated
//list of component-ids which are to be updated via an AJAX call


org.apache.myfaces.PPRCtrl.prototype.getTriggeredComponents = function(triggerId)
{
    if (typeof triggerId != "undefined")
    {
        var retval = null;
        if (typeof window.oamPartialTriggersToZoneIds[triggerId] != "undefined")
        {
            retval = window.oamPartialTriggersToZoneIds[triggerId];
        }

        return retval;
    }
    return null;
};

org.apache.myfaces.PPRCtrl.prototype.isMatchingPattern = function(pattern, stringToMatch)
{
    if (typeof pattern != "string")
    {
        return false;
    }
    if (typeof stringToMatch != "string")
    {
        return false;
    }
    var expr = new RegExp(pattern);
    return expr.test(stringToMatch);
};
