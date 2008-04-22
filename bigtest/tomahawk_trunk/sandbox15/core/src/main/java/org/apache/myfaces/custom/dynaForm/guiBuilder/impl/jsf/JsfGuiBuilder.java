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

import org.apache.myfaces.custom.dynaForm.guiBuilder.GuiBuilder;
import org.apache.myfaces.custom.dynaForm.metadata.FieldInterface;
import org.apache.myfaces.custom.dynaForm.metadata.Selection;
import org.apache.myfaces.custom.dynaForm.metadata.utils.TypeInfos;
import org.apache.myfaces.custom.dynaForm.lib.ObjectSerializationConverter;

import javax.faces.FacesException;
import javax.faces.el.MethodBinding;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.BigDecimalConverter;
import javax.faces.convert.BigIntegerConverter;
import javax.faces.convert.BooleanConverter;
import javax.faces.convert.ByteConverter;
import javax.faces.convert.CharacterConverter;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FloatConverter;
import javax.faces.convert.IntegerConverter;
import javax.faces.convert.LongConverter;
import javax.faces.convert.NumberConverter;
import javax.faces.convert.ShortConverter;
import javax.faces.event.ActionEvent;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LengthValidator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * concrete gui builder which knows how to build JSF forms
 */
public class JsfGuiBuilder extends GuiBuilder
{
	public static final String SEARCH_ENTITY_TYPE = "_ff_searchEntity";
	public static final String SEARCH_ENTITY_BINDING = "_ff_searchEntityBinding";

	private FacesContext context;
	private NewComponentListener newComponentListener;
	private String backingEntityPrefix;
	private String backingBeanPrefix;

	private static final Map<String, JsfGuiElementBuilder> builderMap = new TreeMap<String, JsfGuiElementBuilder>();

	public JsfGuiBuilder()
	{
	}

	/**
	 * Add a specialized builder. This builder will be used instead of any default.
	 */
	public static void addElementBuilder(Class elementType, JsfGuiElementBuilder builder)
	{
		builderMap.put(elementType.getName(), builder);
	}

	public String getBackingBeanPrefix()
	{
		return backingBeanPrefix;
	}

	public void setBackingBeanPrefix(String backingBeanPrefix)
	{
		this.backingBeanPrefix = backingBeanPrefix;
	}

	public String getBackingEntityPrefix()
	{
		return backingEntityPrefix;
	}

	public void setBackingEntityPrefix(String backingEntityPrefix)
	{
		this.backingEntityPrefix = backingEntityPrefix;
	}

	public NewComponentListener getNewComponentListener()
	{
		return newComponentListener;
	}

	public void setNewComponentListener(NewComponentListener newComponentListener)
	{
		this.newComponentListener = newComponentListener;
	}

	public void setContext(FacesContext context)
	{
		this.context = context;
	}

	/* fulfill the interface */
	@Override
	public void createOutputText(FieldInterface field)
	{
		UIComponent cmp = doCreateOutputText(field);
		fireNewComponent(field, cmp);
	}

	@Override
	public void createInputDate(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateInputDate(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}

	@Override
	public void createInputText(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateInputText(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}

	@Override
	public void createInputNumber(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateInputNumber(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}

	@Override
	public void createInputBoolean(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateInputBoolean(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}

	@Override
	public void createSelectOneMenu(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateSelectOneMenu(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}

	@Override
	public void createSearchFor(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateSearchFor(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}

	@Override
	public void createSearchForSelectMenu(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateSearchForSelectMenu(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}

	/*
	@Override
	public void createSearchForSelectMenu(FieldInterface field)
	{
		UIComponent cmp;
		if (!isDisplayOnly())
		{
			cmp = doCreateSearchForSelectMenu(field);
		}
		else
		{
			cmp = doCreateOutputText(field);
		}
		fireNewComponent(field, cmp);
	}
	*/

	@Override
	public void createNative(FieldInterface field)
	{
		Object component = field.getWantedComponent();
		if (!(component instanceof UIComponent))
		{
			throw new IllegalArgumentException("wanted component for field " + field.getName() + " not a UIComponent");
		}

		UIComponent uinew = cloneComponent((UIComponent) component);

		doCreateNative(field, uinew);

		fireNewComponent(field, uinew);
	}

	public static UIComponent cloneComponent(UIComponent uicomponent)
	{
		// a naive try to clone a component
		UIComponent uinew;
		try
		{
			uinew = uicomponent.getClass().newInstance();
		}
		catch (InstantiationException e)
		{
			throw new FacesException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new FacesException(e);
		}
		uinew.restoreState(FacesContext.getCurrentInstance(), uicomponent.saveState(FacesContext.getCurrentInstance()));
		return uinew;
	}

	/* do the hard work */
	public HtmlOutputText doCreateOutputText(FieldInterface field)
	{
		HtmlOutputText cmp = doCreateOutputTextComponent();
		initOutputDefaults(cmp, field);
		return cmp;
	}

	public HtmlOutputText doCreateOutputTextComponent()
	{
		HtmlOutputText cmp = (HtmlOutputText) context.getApplication()
				.createComponent("javax.faces.HtmlOutputText");
		return cmp;
	}

	public HtmlOutputLabel doCreateOutputLabel(String text)
	{
		HtmlOutputLabel cmp = doCreateOutputLabelComponent();
		initDefaults(cmp, null);
		if (getLabelBundle() != null)
		{
			cmp.setValue(getLabelBundle().get(text));
		}
		else
		{
			cmp.setValue(text);
		}
		return cmp;
	}

	public HtmlOutputText doCreateOutputText(String text)
	{
		HtmlOutputText cmp = doCreateOutputTextComponent();
		initDefaults(cmp, null);
		cmp.setValue(text);
		return cmp;
	}

	public HtmlOutputLabel doCreateOutputLabelComponent()
	{
		HtmlOutputLabel cmp = (HtmlOutputLabel) context.getApplication()
				.createComponent("javax.faces.HtmlOutputLabel");
		return cmp;
	}

	public HtmlInputText doCreateInputDate(FieldInterface field)
	{
		HtmlInputText cmp = doCreateInputDateComponent(field);
		initInputDefaults(cmp, field);
		// DateTimeConverter cnv = doCreateDateConverter();
		// cmp.setConverter(cnv);
		return cmp;
	}

	public HtmlInputText doCreateInputDateComponent(FieldInterface field)
	{
		HtmlInputText cmp = doCreateInputText(field);
		return cmp;
	}

	public Converter doCreateConverter(FieldInterface field)
	{
		if (field.getConverterClass() != null)
		{
			return context.getApplication().createConverter(field.getConverterClass());
		}
		if (field.getConverterId() != null)
		{
			return context.getApplication().createConverter(field.getConverterId());
		}

		Class type = field.getType();
		if (type == null)
		{
			return null;
		}

		if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(BooleanConverter.CONVERTER_ID);
		}
		if (Character.class.isAssignableFrom(type) || char.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(CharacterConverter.CONVERTER_ID);
		}
		if (Byte.class.isAssignableFrom(type) || byte.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(ByteConverter.CONVERTER_ID);
		}
		if (Short.class.isAssignableFrom(type) || short.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(ShortConverter.CONVERTER_ID);
		}
		if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(IntegerConverter.CONVERTER_ID);
		}
		if (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(LongConverter.CONVERTER_ID);
		}
		if (Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(FloatConverter.CONVERTER_ID);
		}
		if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type))
		{
			// use the number converter to have locale sensitive input
			return context.getApplication().createConverter(NumberConverter.CONVERTER_ID);
		}
		if (BigInteger.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(BigIntegerConverter.CONVERTER_ID);
		}
		if (BigDecimal.class.isAssignableFrom(type))
		{
			return context.getApplication().createConverter(BigDecimalConverter.CONVERTER_ID);
		}
        if (Date.class.isAssignableFrom(type))
        {
            return doCreateDateConverter(field);
        }

		return null;
	}

	public DateTimeConverter doCreateDateConverter(FieldInterface field)
	{
		DateTimeConverter cnv = (DateTimeConverter) context.getApplication()
				.createConverter("javax.faces.DateTime");
		if (field.getTemporalType() != null)
		{
			switch (field.getTemporalType())
			{
			case DATE:
				cnv.setType("date");
				break;
			case TIME:
				cnv.setType("time");
				break;
			case TIMESTAMP:
				cnv.setType("both");
				break;
			}
		}
		else
		{
			cnv.setType("both");
		}
		cnv.setDateStyle("short");
		cnv.setTimeStyle("medium");
		return cnv;
	}

	public HtmlInputText doCreateInputText(FieldInterface field)
	{
		HtmlInputText cmp = doCreateInputTextComponent();
		initInputDefaults(cmp, field);
		if (Boolean.FALSE.equals(field.getCanWrite()))
		{
			cmp.setReadonly(true);
		}
		if (Boolean.TRUE.equals(field.getDisabled()))
		{
			cmp.setDisabled(true);
		}
		return cmp;
	}

	public HtmlInputText doCreateInputTextComponent()
	{
		HtmlInputText cmp = (HtmlInputText) context.getApplication()
				.createComponent("javax.faces.HtmlInputText");
		return cmp;
	}

	public UISelectBoolean doCreateInputBoolean(FieldInterface field)
	{
		HtmlSelectBooleanCheckbox cmp = doCreateInputBooleanComponent();
		initInputDefaults(cmp, field);
		if (Boolean.FALSE.equals(field.getCanWrite()))
		{
			cmp.setReadonly(true);
		}
		if (Boolean.TRUE.equals(field.getDisabled()))
		{
			cmp.setDisabled(true);
		}
		return cmp;
	}

	public HtmlSelectBooleanCheckbox doCreateInputBooleanComponent()
	{
		HtmlSelectBooleanCheckbox cmp = (HtmlSelectBooleanCheckbox) context
				.getApplication().createComponent("javax.faces.HtmlSelectBooleanCheckbox");
		return cmp;
	}

	public HtmlInputText doCreateInputNumber(FieldInterface field)
	{
		HtmlInputText cmp = doCreateInputText(field);
		cmp.setStyleClass("ff_inputNumber");
		return cmp;
	}

	public HtmlSelectOneMenu doCreateSelectOneMenu(FieldInterface field)
	{
		HtmlSelectOneMenu cmp = doCreateSelectOneMenuComponent();
		initInputDefaults(cmp, field);
		initSelections(field, cmp);

		if (Boolean.FALSE.equals(field.getCanWrite()))
		{
			cmp.setReadonly(true);
		}
		if (Boolean.TRUE.equals(field.getDisabled()))
		{
			cmp.setDisabled(true);
		}

		return cmp;
	}

	public HtmlSelectManyListbox doCreateSelectManyListbox(FieldInterface field)
	{
		HtmlSelectManyListbox cmp = doCreateSelectManyListboxComponent();
		initInputDefaults(cmp, field);
		initSelections(field, cmp);

		if (Boolean.FALSE.equals(field.getCanWrite()))
		{
			cmp.setReadonly(true);
		}
		if (Boolean.TRUE.equals(field.getDisabled()))
		{
			cmp.setDisabled(true);
		}

		return cmp;
	}

	public HtmlSelectOneMenu doCreateSelectOneMenuComponent()
	{
		HtmlSelectOneMenu cmp = (HtmlSelectOneMenu) context.getApplication()
				.createComponent("javax.faces.HtmlSelectOneMenu");
		return cmp;
	}

	public HtmlSelectManyListbox doCreateSelectManyListboxComponent()
	{
		HtmlSelectManyListbox cmp = (HtmlSelectManyListbox) context.getApplication()
				.createComponent("javax.faces.HtmlSelectManyListbox");
		return cmp;
	}

	@SuppressWarnings("unchecked")
	public UIComponent doCreateSearchFor(FieldInterface field)
	{
		throw new UnsupportedOperationException();
		/*
		HtmlPanelGroup panel = doCreatePanelGroupComponent();

		HtmlCommandLink command = doCreateCommandLink(field);
		// avoid duplicate id
		command.setId("cmd_" + command.getId());
		command.setValue("...");
		command.setStyleClass("ff_searchLink");
		command.setImmediate(true);

		command.getChildren().add(
				createParameter(SEARCH_ENTITY_TYPE, field.getType().getName()));
		command.getChildren().add(
				createParameter(SEARCH_ENTITY_BINDING,
						createValueBindingString(field)));

		Converter converter = context.getApplication().createConverter(field.getType());
		HtmlOutputText text = doCreateOutputText(field);
		if (converter != null)
		{
			text.setConverter(converter);
		}
		panel.getChildren().add(text);

		panel.getChildren().add(command);

		panel.setId("pnl_" + command.getId());

		return panel;
		*/
	}

	@SuppressWarnings("unchecked")
	public UIInput doCreateSearchForSelectMenu(FieldInterface field)
	{
		UIInput select;

		if (!field.getAllowMultipleSelections())
		{
			select = doCreateSelectOneMenu(field);
		}
		else
		{
			select = doCreateSelectManyListbox(field);
		}

		/*
		SelectionSourceEnum selectionSource;
		if (field.isEntityType())
		{
			selectionSource = SelectionSourceEnum.relation;
		}
		else
		{
			selectionSource = SelectionSourceEnum.distinct;
		}
		if (field.getSelectionSource() != null)
		{
			selectionSource = field.getSelectionSource();
		}
		if (!field.isEntityType() && SelectionSourceEnum.relation.equals(selectionSource))
		{
			throw new IllegalArgumentException("cant use selectionSource 'relation' for property " + field.getName());
		}
		*/

		/*
			String itemSource;
			String itemSourceName;
			switch (selectionSource)
			{
			case manual:
				itemSource=null;
				itemSourceName=null;
				break;
			case relation:
				itemSource="relationValues";
				itemSourceName=field.getType().getName();
				break;
			case distinct:
				itemSource="distinctValues";
				itemSourceName=field.getName();
				break;
			default:
				throw new IllegalArgumentException("dont know how to handle selectionSource: " + field.getSelectionSource());
			}
		*/

		if (!field.getAllowMultipleSelections() && !Boolean.TRUE.equals(field.getRequired()))
		{
			// not required - and a menu, so add the EMPTY entry
			UISelectItem  item = new UISelectItem();
			// item.setItemValue(ObjectIdentifierConverter.SELECT_NULL_OBJECT);
            item.setItemValue(ObjectSerializationConverter.SELECT_NULL_OBJECT);

			String labelKey = "SelectAll." + field.getName();

			if (getLabelBundle() != null && getLabelBundle().containsKey(labelKey))
			{
				item.setItemLabel((String) getLabelBundle().get(labelKey));
			}
			else
			{
				item.setItemLabel("");
			}
			select.getChildren().add(item);
		}

		MethodBinding mbValues = context.getApplication().createMethodBinding(
			field.getDataSource(),
			new Class[]{String.class});
		MethodBinding mbLabels = context.getApplication().createMethodBinding(
			field.getDataSourceDescription(),
			new Class[]{field.getType()});

		UISelectItems items = new UISelectItems();
		items.setValueBinding("value",
			new ValueBindingDataSourceAdapter(mbValues, mbLabels));
		select.getChildren().add(items);

/*
		if (itemSource != null)
		{
			UISelectItems items = new UISelectItems();
			items.setValueBinding("value",
					context.getApplication().createValueBinding(
						"#{" + backingBeanPrefix + "." + itemSource + "['" + itemSourceName + "']}"));
			select.getChildren().add(items);
		}
*/

		Converter converter;
        /*
        if (field.isEntityType() || SelectionSourceEnum.relation.equals(selectionSource))
		{
			// we know this must be an entity, so persist its id only
			converter = new ObjectIdentifierConverter();
		}
		else
		*/
		{
			// use the whole object as value - phu - any better idea?
			converter = new ObjectSerializationConverter();
		}
		select.setConverter(converter);

		return select;
	}

	public HtmlCommandLink doCreateCommandLink(FieldInterface field)
	{
		HtmlCommandLink command = doCreateCommandLinkComponent();
		iniCommandDefaults(command, field, "searchAction", null);
		return command;
	}

	public HtmlCommandLink doCreateCommandLinkComponent()
	{
		HtmlCommandLink command = (HtmlCommandLink) context.getApplication()
				.createComponent("javax.faces.HtmlCommandLink");
		return command;
	}

	public HtmlPanelGroup doCreatePanelGroupComponent()
	{
		HtmlPanelGroup panelGroup = (HtmlPanelGroup) context.getApplication()
				.createComponent("javax.faces.HtmlPanelGroup");
		return panelGroup;
	}

	public void doCreateNative(FieldInterface field, UIComponent uicomponent)
	{
		initDefaults(uicomponent, field);
		initValueBinding(uicomponent, field);
	}

	public UIParameter createParameter(String name, String value)
	{
		UIParameter parameter = (UIParameter) context.getApplication()
				.createComponent("javax.faces.Parameter");
		parameter.setName(name);
		parameter.setValue(value);
		return parameter;
	}

	/**
	 * jo, we made a component, create a possible label and fire its creation.
	 */
	public void fireNewComponent(FieldInterface field, UIComponent cmp)
	{
		UIOutput labelCmp = createLabelFor(field.getBaseName(), cmp);

		newComponentListener.newComponent(field.getName(), labelCmp, cmp);
	}

	/**
	 * create label for the given <code>labelText</code> and if possible
	 * attach it to the <code>cmp</code>. <br />
	 * If the component and none of its child is a UIInput then a simply
	 * outputText is generated.
	 */
	public UIOutput createLabelFor(String labelText, UIComponent cmp)
	{
		UIOutput labelCmp;
		UIInput inputCmp = findInputComponent(cmp);
		if (inputCmp != null)
		{
			HtmlOutputLabel label = doCreateOutputLabel(labelText);
			label.setFor(cmp.getId());
			labelCmp = label;
		}
		else
		{
			if (getLabelBundle() != null)
			{
				labelCmp = doCreateOutputText((String) getLabelBundle().get(labelText));
			}
			else
			{
				labelCmp = doCreateOutputText(labelText);
			}
		}
		return labelCmp;
	}

	/**
	 * searches the first input component. e.g the one we can the label attach
	 * to.
	 */
	public UIInput findInputComponent(UIComponent cmp)
	{
		if (cmp instanceof UIInput)
		{
			return (UIInput) cmp;
		}

		List children = cmp.getChildren();
		if (children != null)
		{
			for (Object child : children)
			{
				if (child instanceof UIComponent)
				{
					UIInput input = findInputComponent((UIComponent) child);
					if (input != null)
					{
						return input;
					}
				}
			}
		}

		return null;
	}

	/* inits */

	/**
	 * init global defaults like id
	 */
	public void initDefaults(UIComponent cmp, FieldInterface field)
	{
		if (field != null)
		{
			cmp.setId(getCleanedNameForId(field.getExternalName()));
		}
	}

	/**
	 * remove all not allowed characters for an jsf ID from the name
	 */
	protected String getCleanedNameForId(String name)
	{
		StringBuffer ret = new StringBuffer(name.length());
		for (int i = 0; i<name.length(); i++)
		{
			char c = name.charAt(i);
			if (Character.isDigit(c) || Character.isLetter(c) || c == '_' || c == '-')
			{
				ret.append(c);
			}
			else
			{
				ret.append("_");
			}
		}
		return ret.toString();
	}

	/**
	 * init global defaults for output fields
	 */
	public void initOutputDefaults(UIOutput cmp, FieldInterface field)
	{
		initValueBinding(cmp, field);
        initConverter(cmp, field);
	}

	/**
	 * init the default value binding
	 */
	public void initValueBinding(UIComponent cmp, FieldInterface field)
	{
		cmp.setValueBinding("value", context.getApplication()
				.createValueBinding(createValueBindingString(field)));
	}

	protected String createValueBindingString(FieldInterface field)
	{
		return "#{" + backingEntityPrefix + "." + field.getExternalName() + "}";
	}

	/**
	 * init defaults specifically for commands
	 */
	public void iniCommandDefaults(UICommand cmp, FieldInterface field,
			String action, String actionListener)
	{
		initDefaults(cmp, field);

		if (action == null)
		{
			action = "commandAction";
		}

        cmp.setAction(getContext().getApplication()
                        .createMethodBinding(
                                "#{" + backingBeanPrefix + "." + action
                                        + "}", null));

		if (actionListener != null)
		{
			cmp.setActionListener(getContext().getApplication()
					.createMethodBinding(
							"#{" + backingBeanPrefix + "." + actionListener
									+ "}", new Class[]
							{ ActionEvent.class }));
		}
	}

	/**
	 * setup all the validators, maxlength, size, ... for HtmlInputText fields
	 */
	public void initInputDefaults(HtmlInputText cmp, FieldInterface field)
	{
		initInputDefaults((UIInput) cmp, field);

		int size = -1;
		int maxlength = -1;
		Double minValue = null;
		Double maxValue = null;

		TypeInfos.Info typeInfo = TypeInfos.getInfo(field.getType());
		if (typeInfo != null)
		{
			// init from constants
			maxlength = typeInfo.getLength();
			size = maxlength;
			minValue = typeInfo.getMinValue();
			maxValue = typeInfo.getMaxValue();
		}

		if (field.getMaxSize() != null)
		{
			maxlength = field.getMaxSize();
			size = maxlength;
		}
		if (field.getMinSize() != null)
		{
			size = field.getMinSize();
		}
		if (field.getMinValue() != null)
		{
			minValue = field.getMinValue();
		}
		if (field.getMaxValue() != null)
		{
			maxValue = field.getMaxValue();
		}

		if (maxlength != -1)
		{
			cmp.setMaxlength(maxlength);
		}

		if (field.getDisplaySize() != null)
		{
			size = field.getDisplaySize();
		}
		else
		{
			if (field.getMinSize() == null)
			{
				// adjust automatically generated size info
				if (typeInfo != null && typeInfo.isNumber() && size > 10)
				{
					size = 10;
				}
				else if (size > 60) // max
				{
					size = 60;
				}
			}
		}

		if (size > -1)
		{
			cmp.setSize(size);
		}

		attachRangeValidator(cmp, minValue, maxValue);
		attachLengthValidator(cmp, field.getMinSize()!=null?field.getMinSize():0, maxlength);
	}

	protected void attachRangeValidator(HtmlInputText cmp, Double minValue, Double maxValue)
	{
		DoubleRangeValidator vld = null;
		if (minValue != null)
		{
			if (vld == null)
			{
				vld = new DoubleRangeValidator();
			}
			vld.setMinimum(minValue);
		}
		if (maxValue != null)
		{
			if (vld == null)
			{
				vld = new DoubleRangeValidator();
			}
			vld.setMaximum(maxValue);
		}

		if (vld != null)
		{
			cmp.addValidator(vld);
		}
	}

	protected void attachLengthValidator(HtmlInputText cmp, int minSize, int maxSize)
	{
		LengthValidator vld = null;
		if (minSize != -1)
		{
			if (vld == null)
			{
				vld = new LengthValidator();
			}
			vld.setMinimum(minSize);
		}
		if (maxSize != -1)
		{
			if (vld == null)
			{
				vld = new LengthValidator();
			}
			vld.setMaximum(maxSize);
		}

		if (vld != null)
		{
			cmp.addValidator(vld);
		}
	}

	/**
	 * setup defaults for input fields like required
	 */
	public void initInputDefaults(UIInput cmp, FieldInterface field)
	{
		initDefaults(cmp, field);
		initValueBinding(cmp, field);

		if (Boolean.TRUE.equals(field.getRequired()))
		{
			cmp.setRequired(true);
		}

        initConverter(cmp, field);
    }

    /**
     * setup a converter if required
     */
    public void initConverter(UIOutput cmp, FieldInterface field)
    {
        // if there is no converter setup one now.
        // we need this if the binding point to a map instead to a bean.
        // For a map JSF cant determine the wanted value type
        if (cmp.getConverter() == null)
        {
            Converter converter = doCreateConverter(field);
            if (converter != null)
            {
                cmp.setConverter(converter);
            }
        }
    }

    /**
	 * insert possible selection items
	 */
	@SuppressWarnings("unchecked")
	public void initSelections(FieldInterface field, UIComponent cmp)
	{
		if (field.getAllowedSelections() == null)
		{
			return;
		}

		Selection[] selections = field.getAllowedSelections();
		for (Selection selection : selections)
		{
			UISelectItem si = new UISelectItem();
            if (getLabelBundle() != null && selection.getLabel() != null)
            {
                si.setItemLabel((String) getLabelBundle().get(selection.getLabel()));
            }
            else
            {
                si.setItemLabel(selection.getLabel());
            }
            si.setItemValue(selection.getValue());
			cmp.getChildren().add(si);
		}
	}

	public FacesContext getContext()
	{
		return context;
	}

	@Override
	protected boolean buildField(FieldInterface field)
	{
		if (field.getType() != null)
		{
			JsfGuiElementBuilder builder = builderMap.get(field.getType().getName());
			if (builder != null)
			{
				if (builder.buildElement(this, field))
				{
					return true;
				}
			}
		}

		return super.buildField(field);
	}
}