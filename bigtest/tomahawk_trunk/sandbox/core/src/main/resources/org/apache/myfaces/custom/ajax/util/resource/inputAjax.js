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


/**
 * Extra javascript functions to handle Ajax components.
 */
var _MyFaces_inputAjax_ajaxResponseMap = new Object();
var _MyFaces_inputAjax_listenersMap = new Object();

function _MyFaces_inputAjax_clearById(elname)
{
    var el = document.getElementById(elname);
    el.value = "";
}

function _MyFaces_inputAjax_notifyElement(originalRequest, successful)
{
    var clientId = originalRequest.clientId;

    var triggerComponent =
            originalRequest.responseXML.getElementsByTagName("response")[0].
                    getElementsByTagName("triggerComponent")[0];
    var triggerComponentId;

    if(triggerComponent)
    {
        triggerComponentId = triggerComponent.getAttribute("id");
    }

    //_MyFaces_log('req: ' + originalRequest.responseText);
    _MyFaces_inputAjax_clearGlobalMessages();

    var validationSuccessful = true;

    var errorArray = originalRequest.responseXML.getElementsByTagName("response")[0].getElementsByTagName("error");
    if (errorArray && errorArray.length > 0)
    {
        validationSuccessful = false;

        for (ierr = 0; ierr < errorArray.length; ierr++)
        {
            var myObError = errorArray[ierr];
            var errorClientId = myObError.getAttribute("elname");
            var errorSeverity = myObError.getAttribute("severity");
            var errorSummary = myObError.getAttribute("summary");
            _MyFaces_stopLoader(errorClientId);
            var errorDetail = null;
            var errorDetailElements = myObError.getElementsByTagName("detail");
            if (errorDetailElements) errorDetail = errorDetailElements[0].text;
            var style = myObError.getAttribute("style");
            var styleClass = myObError.getAttribute("styleClass");
            _MyFaces_inputAjax_displayError(errorClientId, errorSeverity, errorSummary, errorDetail, styleClass, style);
        }
    }

    if(validationSuccessful)
    {
        var method = _MyFaces_inputAjax_ajaxResponseMap[triggerComponentId]['onSuccessFunction'];
        if(method)
        {
            method();
        }
    }
    else
    {
        var method = _MyFaces_inputAjax_ajaxResponseMap[triggerComponentId]['onFailureFunction'];
        if(method)
        {
            method();
        }
    }

    var myObElementArray = originalRequest.responseXML.getElementsByTagName("response")[0].getElementsByTagName("elementUpdated");
    if (myObElementArray && myObElementArray.length > 0)
    {
        for (iob = 0; iob < myObElementArray.length; iob++)
        {
            var myObElement = myObElementArray[iob];
            var elname = myObElement.getAttribute("elname");
            var elvalue = myObElement.getAttribute("elvalue");
            var eltype = myObElement.getAttribute("eltype");
            _MyFaces_log('elname: ' + elname + ' - value: ' + elvalue);
            _MyFaces_stopLoader(elname);
            if (elvalue)
            {
                if (successful)
                {
                    _MyFaces_inputAjax_clearError(elname);
                    var elToUpdate = document.getElementById(elname);
                    //_MyFaces_log('elToUpdate: ' + elToUpdate + ' - type: ' + elToUpdate.type);
                    if (elToUpdate.type == 'text'
                            || elToUpdate.type == 'textarea'
                            || elToUpdate.type == 'password')
                    {
                        elToUpdate.value = elvalue;
                    }
                    else
                    {
                        if(eltype){
                            if(eltype == 'output'){
                                elToUpdate.innerHTML = elvalue;
                            } // checkbox, radio, etc
                        } else {
                            // maybe shouldn't handle this case
                            elToUpdate.innerHTML = elvalue;
                        }
                    }
                    if (!(typeof _MyFaces_inputAjax_listenersMap == 'undefined'))
                    {
                        var _MyFaces_listenerArray = _MyFaces_inputAjax_listenersMap[elname];
                        if (_MyFaces_listenerArray)
                        {
                            for (ilaindex = 0; ilaindex < _MyFaces_listenerArray.length; ilaindex++)
                            {
                                var _MyFaces_listener = _MyFaces_listenerArray[ilaindex];
                                //_MyFaces_log('listener on ' + elname + ' -- ' + _MyFaces_listener['id']);
                                var _MyFaces_jsString = 'javascript:';
                                if (_MyFaces_listener['action'].indexOf(_MyFaces_jsString) != -1)
                                {
                                    var _func = _MyFaces_listener['action'].substr(_MyFaces_jsString.length);
                                    _func = _func.replace(/this/, "document.getElementById(\'" + _MyFaces_listener['id'] + "\')");
                                    eval(_func);
                                }
                                else
                                {
                                    _MyFaces_inputAjax_updateComponent(_MyFaces_listener['id']);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
function _MyFaces_inputAjax_displayError(elname, severity, summary, detail, styleClass, style)
{
    var summaryAndDetail = summary;
    if (detail) summaryAndDetail += ": " + detail;
    var ajaxMessagesSpan = document.getElementById("_id1:_id23");
    if (ajaxMessagesSpan)
    {
        ajaxMessagesSpan.innerHTML += summaryAndDetail + '<br/>';
    }
    var msgSpan = document.getElementById(elname + "_msgFor");
    if (msgSpan) msgSpan.innerHTML = summaryAndDetail;
    if (styleClass) msgSpan.className = styleClass;
    var errorStyleElem = document.getElementById(elname);
    if (errorStyleElem)
    {
        Element.addClassName(errorStyleElem, "myFaces_error");
    }
}
function _MyFaces_inputAjax_clearError(elname)
{
    var msgSpan = document.getElementById(elname + "_msgFor");
    if (msgSpan)
    {
        msgSpan.innerHTML = "";
    }
    var errorField = document.getElementById(elname);
    if (errorField)
    {
        // can't do this because clears all styles errorField.style.cssText = "";
        Element.removeClassName(errorField, "myFaces_error");
    }
}
function _MyFaces_inputAjax_clearGlobalMessages()
{
    var errorMessageSpan = document.getElementById(_MyFaces_inputAjax_globalErrorsId);
    if(errorMessageSpan) errorMessageSpan.innerHTML = "";
}
function _MyFaces_startLoader(elname)
{
    var myFaces_loaderImg = document.getElementById(elname + '_loaderImg');
    if (myFaces_loaderImg) Element.addClassName(myFaces_loaderImg, "myFacesInputSuggestAjaxThrobbing");
    else
    {
        var elToUpdate = document.getElementById(elname);
        if (elToUpdate)Element.addClassName(elToUpdate, "myFacesInputSuggestAjaxThrobbing");
    }

}
function _MyFaces_stopLoader(elname)
{
    // sleep for eyes sake
    setTimeout("_MyFaces_stopLoader2('" + elname + "')", 500);
}
function _MyFaces_stopLoader2(elname)
{
    var myFaces_loaderImg = document.getElementById(elname + '_loaderImg');
    if (myFaces_loaderImg) Element.removeClassName(myFaces_loaderImg, "myFacesInputSuggestAjaxThrobbing");
    else
    {
        var elToUpdate = document.getElementById(elname);
        if (elToUpdate)Element.removeClassName(elToUpdate, "myFacesInputSuggestAjaxThrobbing");
    }
}
function _MyFaces_inputAjax_notifyElementFailure(originalRequest)
{
    _MyFaces_inputAjax_notifyElement(originalRequest, false);
}
function _MyFaces_inputAjax_notifyElementSuccess(originalRequest)
{
    _MyFaces_inputAjax_notifyElement(originalRequest, true);
}
function _MyFaces_inputAjax_complete(originalRequest)
{
}
function _MyFaces_inputAjax_ajaxSubmit1(elname)
{
    var el = document.getElementById(elname);
    var elvalue = el.value;
    _MyFaces_inputAjax_ajaxSubmit(elname, elvalue, el);
}
function _MyFaces_inputAjax_ajaxSubmit2(el, elname)
{
    var checked = el.checked;
    var elvalue = el.value;
    var extraParams = '&checked=' + checked;
    _MyFaces_inputAjax_ajaxSubmit(elname, elvalue, el, extraParams);
}
function _MyFaces_inputAjax_ajaxSubmit3(elname)
{
    var el = document.getElementById(elname);
    var formEl = el.form;
    var elvalue = 'submit';
    _MyFaces_inputAjax_ajaxSubmit(elname, elvalue, formEl);
}
function _MyFaces_inputAjax_ajaxSubmit(elname, elvalue, el, extraParams, updateOnly)
{
    _MyFaces_log('SUBMITTING el: ' + el);
    _MyFaces_startLoader(elname);
    var pars = "affectedAjaxComponent=" + elname + "&elname=" + elname + "&elvalue=" + elvalue + "&" + elname + "=" + elvalue + "";
    if (extraParams) pars += extraParams;
    if (updateOnly) pars += '&updateOnly=true';
    if (el) pars += '&' + Form.serialize(el);
    //_MyFaces_log('Parameters: ' + pars);
    var _ajaxRequest = new Ajax.Request(
            _MyFaces_inputAjax_ajaxUrl,
    {clientId: elname, method: 'post', parameters: pars, onComplete: _MyFaces_inputAjax_complete, onSuccess: _MyFaces_inputAjax_notifyElementSuccess, onFailure: _MyFaces_inputAjax_notifyElementFailure}
            );
}
function _MyFaces_inputAjax_updateComponent(elname)
{
    _MyFaces_inputAjax_ajaxUpdate(elname);
}
function _MyFaces_inputAjax_ajaxUpdate(elname)
{
    _MyFaces_inputAjax_ajaxSubmit(elname, null, null, null, true);
}
function _MyFaces_log(msg)
{
    var _myFaces_logDiv = document.getElementById("logDiv");
    if (_myFaces_logDiv)
    {
        _myFaces_logDiv.innerHTML = _myFaces_logDiv.innerHTML + '<br/>' + _MyFaces_escapeHtml(msg);
    }
}
function _MyFaces_escapeHtml(msg)
{
    encodedHtml = msg.replace(/</g, "&lt;");
    encodedHtml = encodedHtml.replace(/>/g, "&gt;");
    encodedHtml = encodedHtml.replace(/\n/g, "<br/>");
    //alert('enc: ' + encodedHtml);
    return encodedHtml;

}
