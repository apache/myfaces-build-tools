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
package org.apache.myfaces.custom.dynaForm.guiBuilder.impl.jsf;

import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;

import org.apache.myfaces.custom.dynaForm.metadata.FieldInterface;

/**
 * a helper to be able to decorate the builder
 */
public class JsfGuiBuilderDecorator extends JsfGuiBuilder
{
	private final JsfGuiBuilder original;

	public JsfGuiBuilderDecorator(JsfGuiBuilder original)
	{
		this.original = original;
	}

	public JsfGuiBuilder getOriginal()
	{
		return original;
	}

	public void createInputBoolean(FieldInterface field)
	{
		original.createInputBoolean(field);
	}

	public void createInputDate(FieldInterface field)
	{
		original.createInputDate(field);
	}

	public void createInputNumber(FieldInterface field)
	{
		original.createInputNumber(field);
	}

	public void createInputText(FieldInterface field)
	{
		original.createInputText(field);
	}

	public UIOutput createLabelFor(String labelText, UIComponent cmp)
	{
		return original.createLabelFor(labelText, cmp);
	}

	public void createNative(FieldInterface field)
	{
		original.createNative(field);
	}

	public void createOutputText(FieldInterface field)
	{
		original.createOutputText(field);
	}

	public UIParameter createParameter(String name, String value)
	{
		return original.createParameter(name, value);
	}

	public void createSearchFor(FieldInterface field)
	{
		original.createSearchFor(field);
	}

	/*
	public void createSearchForSelectMenu(FieldInterface field)
	{
		original.createSearchForSelectMenu(field);
	}
	*/

	public void createSelectOneMenu(FieldInterface field)
	{
		original.createSelectOneMenu(field);
	}

	public HtmlCommandLink doCreateCommandLink(FieldInterface field)
	{
		return original.doCreateCommandLink(field);
	}

	public HtmlCommandLink doCreateCommandLinkComponent()
	{
		return original.doCreateCommandLinkComponent();
	}

	public DateTimeConverter doCreateDateConverter(FieldInterface field)
	{
		return original.doCreateDateConverter(field);
	}

	public UISelectBoolean doCreateInputBoolean(FieldInterface field)
	{
		return original.doCreateInputBoolean(field);
	}

	public HtmlSelectBooleanCheckbox doCreateInputBooleanComponent()
	{
		return original.doCreateInputBooleanComponent();
	}

	public HtmlInputText doCreateInputDate(FieldInterface field)
	{
		return original.doCreateInputDate(field);
	}

	public HtmlInputText doCreateInputDateComponent(FieldInterface field)
	{
		return original.doCreateInputDateComponent(field);
	}

	public HtmlInputText doCreateInputNumber(FieldInterface field)
	{
		return original.doCreateInputNumber(field);
	}

	public HtmlInputText doCreateInputText(FieldInterface field)
	{
		return original.doCreateInputText(field);
	}

	public HtmlInputText doCreateInputTextComponent()
	{
		return original.doCreateInputTextComponent();
	}

	public void doCreateNative(FieldInterface field, UIComponent uicomponent)
	{
		original.doCreateNative(field, uicomponent);
	}

	public HtmlOutputLabel doCreateOutputLabel(String text)
	{
		return original.doCreateOutputLabel(text);
	}

	public HtmlOutputLabel doCreateOutputLabelComponent()
	{
		return original.doCreateOutputLabelComponent();
	}

	public HtmlOutputText doCreateOutputText(FieldInterface field)
	{
		return original.doCreateOutputText(field);
	}

	public HtmlOutputText doCreateOutputText(String text)
	{
		return original.doCreateOutputText(text);
	}

	public HtmlOutputText doCreateOutputTextComponent()
	{
		return original.doCreateOutputTextComponent();
	}

	public HtmlPanelGroup doCreatePanelGroupComponent()
	{
		return original.doCreatePanelGroupComponent();
	}

	public UIComponent doCreateSearchFor(FieldInterface field)
	{
		return original.doCreateSearchFor(field);
	}

	public HtmlSelectOneMenu doCreateSelectOneMenu(FieldInterface field)
	{
		return original.doCreateSelectOneMenu(field);
	}

	public HtmlSelectOneMenu doCreateSelectOneMenuComponent()
	{
		return original.doCreateSelectOneMenuComponent();
	}

	public UIInput findInputComponent(UIComponent cmp)
	{
		return original.findInputComponent(cmp);
	}

	public void fireNewComponent(FieldInterface field, UIComponent cmp)
	{
		original.fireNewComponent(field, cmp);
	}

	public String getBackingBeanPrefix()
	{
		return original.getBackingBeanPrefix();
	}

	public String getBackingEntityPrefix()
	{
		return original.getBackingEntityPrefix();
	}

	public FacesContext getContext()
	{
		return original.getContext();
	}

	public NewComponentListener getNewComponentListener()
	{
		return original.getNewComponentListener();
	}

	public void iniCommandDefaults(UICommand cmp, FieldInterface field, String action, String actionListener)
	{
		original.iniCommandDefaults(cmp, field, action, actionListener);
	}

	public void initDefaults(UIComponent cmp, FieldInterface field)
	{
		original.initDefaults(cmp, field);
	}

	public void initInputDefaults(HtmlInputText cmp, FieldInterface field)
	{
		original.initInputDefaults(cmp, field);
	}

	public void initInputDefaults(UIInput cmp, FieldInterface field)
	{
		original.initInputDefaults(cmp, field);
	}

	public void initOutputDefaults(UIOutput cmp, FieldInterface field)
	{
		original.initOutputDefaults(cmp, field);
	}

	public void initSelections(FieldInterface field, UIComponent cmp)
	{
		original.initSelections(field, cmp);
	}

	public void initValueBinding(UIComponent cmp, FieldInterface field)
	{
		original.initValueBinding(cmp, field);
	}

	public boolean isDisplayOnly()
	{
		return original.isDisplayOnly();
	}

	public void setBackingBeanPrefix(String backingBeanPrefix)
	{
		original.setBackingBeanPrefix(backingBeanPrefix);
	}

	public void setBackingEntityPrefix(String backingEntityPrefix)
	{
		original.setBackingEntityPrefix(backingEntityPrefix);
	}

	public void setContext(FacesContext context)
	{
		original.setContext(context);
	}

	public void setDisplayOnly(boolean displayOnly)
	{
		original.setDisplayOnly(displayOnly);
	}

	public void setNewComponentListener(NewComponentListener newComponentListener)
	{
		original.setNewComponentListener(newComponentListener);
	}

	public Map getLabelBundle()
	{
		return original.getLabelBundle();
	}

	public void setLabelBundle(Map labelBundle)
	{
		original.setLabelBundle(labelBundle);
	}

	protected void attachLengthValidator(HtmlInputText cmp, int minSize, int maxSize)
	{
		original.attachLengthValidator(cmp, minSize, maxSize);
	}

	protected void attachRangeValidator(HtmlInputText cmp, double minValue, double maxValue)
	{
		original.attachRangeValidator(cmp, minValue, maxValue);
	}

	protected boolean buildField(FieldInterface field)
	{
		return super.buildField(field);
	}
}
