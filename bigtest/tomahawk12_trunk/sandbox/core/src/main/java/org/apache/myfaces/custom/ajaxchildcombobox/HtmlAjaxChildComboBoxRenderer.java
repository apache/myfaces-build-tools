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
package org.apache.myfaces.custom.ajaxchildcombobox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.dojo.DojoConfig;
import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlMenuRenderer;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.model.SelectItem;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Renderer for component HtmlAjaxChildComboBox
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.SelectOne"
 *   type = "org.apache.myfaces.AjaxChildComboBox"
 *
 * @author Sharath Reddy
 */
public class HtmlAjaxChildComboBoxRenderer extends HtmlMenuRenderer implements AjaxRenderer
{
	private static final String BEGIN_OPTION = "<option>";
	private static final String END_OPTION = "</option>";
	private static final String BEGIN_OPTION_TEXT = "<optionText>";
	private static final String END_OPTION_TEXT = "</optionText>";
	private static final String BEGIN_OPTION_VALUE = "<optionValue>";
	private static final String END_OPTION_VALUE = "</optionValue>";

	public static final int DEFAULT_MAX_SUGGESTED_ITEMS = 200;

	private static Log log = LogFactory.getLog(HtmlAjaxChildComboBoxRenderer.class);

	// Adds the javascript files needed by Dojo and the custom javascript for
	// this
	// component
	private void encodeJavascript(FacesContext context, UIComponent component)
		throws IOException
	{
		String javascriptLocation = (String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
		DojoUtils.addMainInclude(context, component, javascriptLocation, new DojoConfig());
		DojoUtils.addRequire(context, component, "dojo.event.*");
		// not required - and results in an error
		// DojoUtils.addRequire(context, component, "dojo.io.bind");

		AddResource addResource = AddResourceFactory.getInstance(context);

		addResource.addJavaScriptAtPosition(context,
			AddResource.HEADER_BEGIN, AjaxChildComboBox.class, "javascript/ajaxChildComboBox.js");
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException
	{
		RendererUtils.checkParamValidity(context, component, AjaxChildComboBox.class);

		AjaxChildComboBox childComboBox = (AjaxChildComboBox) component;

		super.encodeEnd(context, component);

		String clientId = component.getClientId(context);

		UIComponent parentComboBox = this.getParentComboBox(childComboBox);
		if (parentComboBox == null)
		{
			log.error("Could not find parent combo box for AjaxChildComboBox " +
				childComboBox.getClientId(context));
			return;
		}

		encodeJavascript(context, component);

		ResponseWriter writer = context.getResponseWriter();

		// Begin: Write out the javascript that hooks up this component with the
		// parent combo-box
		writer.startElement(HTML.SCRIPT_ELEM, component);
		writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);

		writer.write("var parentCombo = document.getElementById('" +
			parentComboBox.getClientId(context) + "');");
		HtmlRendererUtils.writePrettyLineSeparator(context);
		writer.write("dojo.event.connect(parentCombo, 'onchange', function(evt) { ");
		HtmlRendererUtils.writePrettyLineSeparator(context);
		writer.write("var targetElement = evt.target;");
		writer.write("var targetValue = targetElement.options[targetElement.selectedIndex].value;");
		HtmlRendererUtils.writePrettyLineSeparator(context);
		writer.write("reloadChildComboBox('" + clientId + "', targetValue);");
		HtmlRendererUtils.writePrettyLineSeparator(context);
		writer.write("});");
		writer.endElement(HTML.SCRIPT_ELEM);
		// End: Javascript
	}


	// creates the XML response that is sent back to the browser
	public void encodeAjax(FacesContext context, UIComponent uiComponent)
		throws IOException
	{

		String parentValue = (String) context.getExternalContext().
			getRequestParameterMap().get("parentValue");

		ServletResponse response = (ServletResponse) context.getExternalContext().getResponse();
		PrintWriter writer = response.getWriter();

		StringBuffer xml = new StringBuffer();

		MethodBinding mb = ((AjaxChildComboBox) uiComponent).getAjaxSelectItemsMethod();
		SelectItem[] options = (SelectItem[])
			mb.invoke(context, new Object[]{parentValue});

        xml.append("<?xml version=\"1.0\"?>\n");
        xml.append("<response>\n");
        for (int i = 0; i < options.length; i++)
		{
			xml.append(BEGIN_OPTION);
			xml.append(BEGIN_OPTION_TEXT).append(options[i].getLabel()).append(END_OPTION_TEXT);
			xml.append(BEGIN_OPTION_VALUE).append(options[i].getValue()).append(END_OPTION_VALUE);
			xml.append(END_OPTION);
		}
        xml.append("</response>");

        writer.write(xml.toString());

	}

	private UIComponent getParentComboBox(AjaxChildComboBox comboBox)
	{
		String parentId = comboBox.getParentComboBox();

		UIComponent parentComboBox = comboBox.findComponent(parentId);
		if (parentComboBox != null)
		{
			return parentComboBox;
		}

		// try searching from the very root of the component tree
		return comboBox.findComponent(UINamingContainer.SEPARATOR_CHAR + parentId);
	}
}
