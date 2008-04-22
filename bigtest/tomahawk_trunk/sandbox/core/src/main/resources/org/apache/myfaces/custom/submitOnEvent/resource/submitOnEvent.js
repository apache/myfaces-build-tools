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

function orgApacheMyfacesSubmitOnEventRegister(eventType, callbackFunction, inputComponentId, clickComponentId)
{
	// alert("eventType:" + eventType + " callback:" + callbackFunction + " input:" + inputComponentId + " click:" + clickComponentId);

	var forceEventAttach = false;

	var inputComponents = [];
	if (eventType == "dialogok")
	{
		inputComponents = [ eval(inputComponentId) ];
		forceEventAttach = true;
	}
	else if (inputComponentId != null && inputComponentId != '')
    {
        inputComponents = document.getElementsByName(inputComponentId);
	}
    else
    {
		forceEventAttach = true;
        inputComponents = [ document ];
    }

    var clickComponent = document.getElementById(clickComponentId);
    if (!clickComponent)
    {
		alert("SubmitOnEvent: can't find button or link '" + clickComponentId + "'");
		return;
	}

    var handler;
	var userHandler = false;

	if (callbackFunction != null && callbackFunction != '')
    {
		userHandler = true;
		handler=function(event)
        {
            if (!event)
            {
                event = window.event;
            }

            var callbackRef = eval(callbackFunction);
            if (!callbackRef)
            {
                alert("submitOnEnter: can't find callback Function '" + callbackFunction + "'");
                return true;
            }

            var ret = callbackRef(event, inputComponentId, clickComponentId);
            orgApacheMyfacesSubmitOnEventSetEvent(event, !ret);
            if (ret)
            {
                orgApacheMyfacesSubmitOnEventGeneral(clickComponentId);
            }
            return !ret;
        };
    }
    else if (eventType == "keypress" || eventType == "keydown" || eventType == "keyup")
    {
		handler=function(event)
        {
            return orgApacheMyfacesSubmitOnEventSetEvent(event, orgApacheMyfacesSubmitOnEventKeypress(event, clickComponentId));
        };
    }
	else if (eventType == "dialogok")
	{
		var dialog = eval(inputComponentId);
		if (dialog)
		{
			handler=function()
			{
				if (dialog._myfaces_ok)
				{
					orgApacheMyfacesSubmitOnEventGeneral(clickComponentId);
				}
			}
		}
	}
	else
    {
        handler=function()
        {
            orgApacheMyfacesSubmitOnEventGeneral(clickComponentId);
        };
    }

	for (var cmpNum = 0; cmpNum < inputComponents.length; cmpNum++)
	{
		var inputComponent = inputComponents[cmpNum];
		if (!forceEventAttach && !orgApacheMyfacesSubmitOnEventIsFormElement(inputComponent))
		{
			continue;
		}
		orgApacheMyfacesSubmitOnEventAttachEvent(inputComponent, inputComponents, eventType, handler, userHandler);
	}
}

function orgApacheMyfacesSubmitOnEventGetNodeName(component)
{
	if (component.nodeName)
	{
		return component.nodeName.toLowerCase();
	}
	if (component.tagName)
	{
		return component.tagName.toLowerCase();
	}

	return null;
}

function orgApacheMyfacesSubmitOnEventIsFormElement(component)
{
	var nodeName = orgApacheMyfacesSubmitOnEventGetNodeName(component);
	if (!nodeName)
	{
		return false;
	}

	return nodeName == "input" || nodeName == "select" || nodeName == "textarea";
}

function orgApacheMyfacesSubmitOnEventAttachEvent(component, components, eventType, handler, userHandler)
{
    if (!component.oamSOEAttachedHandlers)
    {
        component.oamSOEAttachedHandlers=new Object;
    }
    if (component.oamSOEAttachedHandlers[eventType])
    {
        // handler already attached
        // do not attach again (which might happen during ppr) as then the event fires twice,
        // or the browser is crazy afterwards at all
        return;
    }
    component.oamSOEAttachedHandlers[eventType] = true;

    var setupHandler;

	if (document.all && eventType == "change" && !userHandler && component.type && component.type.toLowerCase() == "radio")
	{
		// install IE fix for onchange bug with radio buttons
		// the idea is to reroute the event to an onclick instead of onchange and check
		// the change of the value manually. Fire event only on value change.

		component.orgApacheMyfacesSubmitOnEventChecked = component.checked;

		eventType="click";
		setupHandler = function(event)
		{
			if (component.orgApacheMyfacesSubmitOnEventChecked == component.checked)
			{
				return true;
			}

			for (var cmpNum = 0; cmpNum < components.length; cmpNum++)
			{
				var partComponent = components[cmpNum];
				if (!orgApacheMyfacesSubmitOnEventIsFormElement(partComponent))
				{
					continue;
				}
				partComponent.orgApacheMyfacesSubmitOnEventChecked = component.checked;
			}

			return handler(event);
		}
	}
	else
	{
		setupHandler = handler;
	}

	if (eventType == "dialogok")
	{
		if (!component._myfaces_submitOnEvent_onHide)
		{
			component._myfaces_submitOnEvent_onHide=component.onHide;
			component.onHide=function()
			{
				this._myfaces_submitOnEvent_onHide();
				setupHandler();
			}
		}
	}
	else if (component.addEventListener)
	{
		component.addEventListener(eventType, setupHandler, false);
	}
	else if (component.attachEvent)
	{
		component.attachEvent("on" + eventType, setupHandler);
	}
	else
	{
		alert("SubmitOnEvent: your browser do support event attaching");
	}
}

function orgApacheMyfacesSubmitOnEventSetEvent(event, ret)
{
    if (!ret)
    {
        if (!event)
        {
            event = window.event;
        }

        event.cancelBubble = true;
        if (event.stopPropagation)
        {
            event.stopPropagation();
        }
        if (event.preventDefault)
        {
            event.preventDefault();
        }
    }

    return ret;
}

function orgApacheMyfacesSubmitOnEventGeneral(componentId)
{
    var clickComponent = document.getElementById(componentId);
    if ((clickComponent.nodeName && clickComponent.nodeName.toLowerCase() == "a")
        || (clickComponent.tagName && clickComponent.tagName.toLowerCase() == "a"))
    {
        orgApacheMyfacesSubmitOnEventClickLink(clickComponent);
        return false;
    }
    else if (clickComponent.type
        && (clickComponent.type.toLowerCase() == "submit" || clickComponent.type.toLowerCase() == "image"))
    {
        orgApacheMyfacesSubmitOnEventClickButton(clickComponent);
        return false;
    }
    else
    {
        alert("SubmitOnEvent: don't know how to fire component '" + componentId + "'");
    }

    return true;
}

function orgApacheMyfacesSubmitOnEventKeypress(event, componentId)
{
    var keycode;
    if (window.event)
    {
        keycode = window.event.keyCode;
    }
    else if (event)
    {
        keycode = event.which;
    }
    else
    {
        return true;
    }

    if (keycode == 13)
    {
        orgApacheMyfacesSubmitOnEventGeneral(componentId);
        return false;
    }

    return true;
}

function orgApacheMyfacesSubmitOnEventClickLink(fireOnThis)
{
    if (document.createEvent)
    {
        var evObj = document.createEvent('MouseEvents')
        evObj.initEvent('click', true, false)
        fireOnThis.dispatchEvent(evObj)
    }
    else if (fireOnThis.fireEvent)
    {
        fireOnThis.fireEvent('onclick')
    }
    else
    {
        alert("SubmitOnEvent: your browser do not support fireing an event on a link");
    }
}

function orgApacheMyfacesSubmitOnEventClickButton(fireOnThis)
{
    fireOnThis.click();
}
