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
package org.apache.myfaces.custom.convertNumber;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.ConverterTag;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;
import org.apache.myfaces.shared_tomahawk.util.LocaleUtils;

/**
 * converter which uses either the manually set <code>destType</code> or the
 * value binding to determine the correct destination type to convert the number
 * to
 * 
 * @author imario@apache.org
 */
public class TypedNumberConverterTag extends ConverterTag
{
	private String _destType;
	private String _currencyCode = null;
	private String _currencySymbol = null;
	private String _groupingUsed = "true"; // default value as required by the
											// spec
	private String _integerOnly = "false"; // default value as required by the
											// spec
	private String _locale = null;
	private String _maxFractionDigits = null;
	private String _maxIntegerDigits = null;
	private String _minFractionDigits = null;
	private String _minIntegerDigits = null;
	private String _pattern = null;
	private String _type = "number"; // default value as required by the spec

	public TypedNumberConverterTag()
	{
		setConverterId(TypedNumberConverter.CONVERTER_ID);
	}

	public void setDestType(String destType)
	{
		_destType = destType;
	}

	public void setCurrencyCode(String currencyCode)
	{
		_currencyCode = currencyCode;
	}

	public void setCurrencySymbol(String currencySymbol)
	{
		_currencySymbol = currencySymbol;
	}

	public void setGroupingUsed(String groupingUsed)
	{
		_groupingUsed = groupingUsed;
	}

	public void setIntegerOnly(String integerOnly)
	{
		_integerOnly = integerOnly;
	}

	public void setLocale(String locale)
	{
		_locale = locale;
	}

	public void setMaxFractionDigits(String maxFractionDigits)
	{
		_maxFractionDigits = maxFractionDigits;
	}

	public void setMaxIntegerDigits(String maxIntegerDigits)
	{
		_maxIntegerDigits = maxIntegerDigits;
	}

	public void setMinFractionDigits(String minFractionDigits)
	{
		_minFractionDigits = minFractionDigits;
	}

	public void setMinIntegerDigits(String minIntegerDigits)
	{
		_minIntegerDigits = minIntegerDigits;
	}

	public void setPattern(String pattern)
	{
		_pattern = pattern;
	}

	public void setType(String type)
	{
		_type = type;
	}

	public void setPageContext(PageContext context)
	{
		super.setPageContext(context);
		setConverterId(TypedNumberConverter.CONVERTER_ID);
	}

	protected Converter createConverter() throws JspException
	{
		TypedNumberConverter converter = (TypedNumberConverter) super.createConverter();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		setConverterCurrencyCode(facesContext, converter, _currencyCode);
		setConverterCurrencySymbol(facesContext, converter, _currencySymbol);
		setConverterGroupingUsed(facesContext, converter, _groupingUsed);
		setConverterIntegerOnly(facesContext, converter, _integerOnly);
		setConverterLocale(facesContext, converter, _locale);
		setConverterMaxFractionDigits(
			facesContext, converter, _maxFractionDigits);
		setConverterMaxIntegerDigits(facesContext, converter, _maxIntegerDigits);
		setConverterMinFractionDigits(
			facesContext, converter, _minFractionDigits);
		setConverterMinIntegerDigits(facesContext, converter, _minIntegerDigits);
		setConverterPattern(facesContext, converter, _pattern);
		setConverterType(facesContext, converter, _type);
		setDestType(facesContext, converter, _destType);

		return converter;
	}

	protected static void setConverterLocale(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			converter.setLocale((Locale) vb.getValue(facesContext));
		}
		else
		{
			Locale locale = LocaleUtils.converterTagLocaleFromString(value);
			converter.setLocale(locale);
		}
	}

	private static void setConverterCurrencyCode(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			converter.setCurrencyCode((String) vb.getValue(facesContext));
		}
		else
		{
			converter.setCurrencyCode(value);
		}
	}

	private static void setConverterCurrencySymbol(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			converter.setCurrencySymbol((String) vb.getValue(facesContext));
		}
		else
		{
			converter.setCurrencySymbol(value);
		}
	}

	private static void setConverterGroupingUsed(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			Boolean b = (Boolean) vb.getValue(facesContext);
			if (b != null)
			{
				converter.setGroupingUsed(b.booleanValue());
			}
		}
		else
		{
			converter.setGroupingUsed(Boolean.valueOf(value).booleanValue());
		}
	}

	private static void setConverterIntegerOnly(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			Boolean b = (Boolean) vb.getValue(facesContext);
			if (b != null)
			{
				converter.setIntegerOnly(b.booleanValue());
			}
		}
		else
		{
			converter.setIntegerOnly(Boolean.valueOf(value).booleanValue());
		}
	}

	private static void setConverterMaxFractionDigits(
			FacesContext facesContext, TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			Integer i = (Integer) vb.getValue(facesContext);
			if (i != null)
			{
				converter.setMaxFractionDigits(i.intValue());
			}
		}
		else
		{
			converter.setMaxFractionDigits(Integer.parseInt(value));
		}
	}

	private static void setConverterMaxIntegerDigits(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			Integer i = (Integer) vb.getValue(facesContext);
			if (i != null)
			{
				converter.setMaxIntegerDigits(i.intValue());
			}
		}
		else
		{
			converter.setMaxIntegerDigits(Integer.parseInt(value));
		}
	}

	private static void setConverterMinFractionDigits(
			FacesContext facesContext, TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			Integer i = (Integer) vb.getValue(facesContext);
			if (i != null)
			{
				converter.setMinFractionDigits(i.intValue());
			}
		}
		else
		{
			converter.setMinFractionDigits(Integer.parseInt(value));
		}
	}

	private static void setConverterMinIntegerDigits(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			Integer i = (Integer) vb.getValue(facesContext);
			if (i != null)
			{
				converter.setMinIntegerDigits(i.intValue());
			}
		}
		else
		{
			converter.setMinIntegerDigits(Integer.parseInt(value));
		}
	}

	private static void setConverterPattern(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			converter.setPattern((String) vb.getValue(facesContext));
		}
		else
		{
			converter.setPattern(value);
		}
	}

	private static void setConverterType(FacesContext facesContext,
			TypedNumberConverter converter, String value)
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			converter.setType((String) vb.getValue(facesContext));
		}
		else
		{
			converter.setType(value);
		}
	}

	private static void setDestType(FacesContext facesContext,
			TypedNumberConverter converter, String value) throws JspException
	{
		if (value == null)
			return;
		if (UIComponentTag.isValueReference(value))
		{
			ValueBinding vb = facesContext.getApplication().createValueBinding(
				value);
			converter.setDestType((Class) vb.getValue(facesContext));
		}
		else
		{
			try
			{
				converter.setDestType(ClassUtils.classForName(value));
			}
			catch (ClassNotFoundException e)
			{
				throw new JspException(e);
			}
		}
	}
}
