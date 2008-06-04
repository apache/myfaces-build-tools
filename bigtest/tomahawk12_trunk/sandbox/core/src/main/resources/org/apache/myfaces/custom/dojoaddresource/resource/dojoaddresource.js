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

function oamInsertCssFile(/* string */URI)
{
	//	summary
	// calls css by XmlHTTP and inserts it into DOM as <style [widgetType="widgetType"]> *downloaded cssText*</style>
	if (!URI)
	{
		return;
	}
	var cssStr = dojo.hostenv.getText(URI, false, true);
	if (cssStr === null)
	{
		return;
	}
	cssStr = dojo.html.fixPathsInCssText(cssStr, URI);

	return oamInsertCssText(cssStr);
}

function oamInsertCssText(/* string */cssStr)
{
	//	summary
	//	Attempt to insert CSS rules into the document through inserting a style element
	// DomNode Style  = insertCssText(String ".dojoMenu {color: green;}"[, DomDoc document, dojo.uri.Uri Url ])
	if (!cssStr)
	{
		return; //	HTMLStyleElement
	}
	var doc = document;

	var style = doc.createElement("style");
	style.setAttribute("type", "text/css");
	// IE is b0rken enough to require that we add the element to the doc
	// before changing it's properties
	var head = doc.getElementsByTagName("head")[0];
	if (!head)
	{ // must have a head tag
		dojo.debug("No head tag in document, aborting styles");
		return;	//	HTMLStyleElement
	}
	else
	{
		var links = head.getElementsByTagName("link");
		if (links && links.length > 0)
		{
			head.insertBefore(style, links[0]);
		}
		else
		{
			head.appendChild(style);
		}
	}

	if (style.styleSheet)
	{// IE
		var setFunc = function()
		{
			try
			{
				style.styleSheet.cssText = cssStr;
			}
			catch(e)
			{
				dojo.debug(e);
			}
		};
		if (style.styleSheet.disabled)
		{
			setTimeout(setFunc, 10);
		}
		else
		{
			setFunc();
		}
	}
	else
	{ // w3c
		var cssText = doc.createTextNode(cssStr);
		style.appendChild(cssText);
	}
	return style;	//	HTMLStyleElement
}
