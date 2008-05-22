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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.myfaces.shared_tomahawk.util.MessageUtils;

/**
 * Converter which uses either the manually set <code>destType</code> or the value binding to determine the 
 * correct destination type to convert the number to
 * 
 * This tag creates a number formatting converter and associates it with the nearest 
 * parent UIComponent. It uses either the manually set destType or the value 
 * binding to determine the correct destination type to convert the number to. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFConverter
 *   name = "s:convertNumber"
 *   tagClass = "org.apache.myfaces.custom.convertNumber.TypedNumberConverterTag" 
 *   
 * @author imario@apache.org
 */
public class TypedNumberConverter implements javax.faces.convert.Converter, StateHolder
{
	public static final String CONVERTER_ID = "org.apache.myfaces.custom.convertNumber.TypedNumberConverter";

	private Class destType;

	public TypedNumberConverter()
	{
	}
	
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
	{
		Object convertedValue = _getAsObject(facesContext, uiComponent, value);
		if (convertedValue == null)
		{
			return null;
		}

		Class destType = getDestType(); 
		if (destType == null)
		{
			ValueBinding valueBinding = uiComponent.getValueBinding("value");
			if (valueBinding != null)
			{
				destType = valueBinding.getType(facesContext);
			}
		}
		
		if (destType != null)
		{
			Converter converter = ConvertUtils.lookup(destType);
			if (converter == null)
			{
				throw new UnsupportedOperationException("cant deal with " + destType);
			}

			// setting type to null, in fact the documentation is wrong here and this type is never used
			convertedValue = converter.convert(null, convertedValue);
		}
		
		
		return convertedValue;
	}

	public void restoreState(FacesContext facesContext, Object state)
	{
		Object[] states = (Object[]) state;
		_restoreState(facesContext, states[0]);
		destType = (Class) states[1];
	}

	public Object saveState(FacesContext facesContext)
	{
		return new Object[]
		                  {
				_saveState(facesContext),
				destType
		                  };
	}

    /**
     * The java class name the value should be converted to. 
     * 
     * Default: automatically determined through valueBinding
     * 
     * @JSFProperty
     */
	public Class getDestType()
	{
		return destType;
	}

	public void setDestType(Class destType)
	{
		this.destType = destType;
	}

	/* ORIGINAL STUFF COPIED FROM javax.faces.convert.NumberConverter */
	
    // internal constants
    private static final String CONVERSION_MESSAGE_ID = "javax.faces.convert.NumberConverter.CONVERSION";


    private static final boolean JAVA_VERSION_14;

    static
    {
        JAVA_VERSION_14 = checkJavaVersion14();
    }

    private String _currencyCode;
    private String _currencySymbol;
    private Locale _locale;
    private int _maxFractionDigits;
    private int _maxIntegerDigits;
    private int _minFractionDigits;
    private int _minIntegerDigits;
    private String _pattern;
    private String _type = "number";
    private boolean _groupingUsed = true;
    private boolean _integerOnly = false;
    private boolean _transient;

    private boolean _maxFractionDigitsSet;
    private boolean _maxIntegerDigitsSet;
    private boolean _minFractionDigitsSet;
    private boolean _minIntegerDigitsSet;


    // METHODS
    public Object _getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
    {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value != null)
        {
            value = value.trim();
            if (value.length() > 0)
            {
                NumberFormat format = getNumberFormat(facesContext);
                format.setParseIntegerOnly(_integerOnly);
                try
                {
                    return format.parse(value);
                }
                catch (ParseException e)
                {
                	FacesMessage message = MessageUtils.getMessage(facesContext, CONVERSION_MESSAGE_ID, new Object[]{uiComponent.getId(),value});
                	message.setSeverity(FacesMessage.SEVERITY_ERROR);
                	
                    throw new ConverterException(message, e);
                }
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
    {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value == null)
        {
            return "";
        }
        if (value instanceof String)
        {
            return (String)value;
        }

        NumberFormat format = getNumberFormat(facesContext);
        format.setGroupingUsed(_groupingUsed);
        if (_maxFractionDigitsSet) format.setMaximumFractionDigits(_maxFractionDigits);
        if (_maxIntegerDigitsSet) format.setMaximumIntegerDigits(_maxIntegerDigits);
        if (_minFractionDigitsSet) format.setMinimumFractionDigits(_minFractionDigits);
        if (_minIntegerDigitsSet) format.setMinimumIntegerDigits(_minIntegerDigits);
        formatCurrency(format);
        try
        {
            return format.format(value);
        }
        catch (Exception e)
        {
            throw new ConverterException("Cannot convert value '" + value + "'");
        }
    }

    private NumberFormat getNumberFormat(FacesContext facesContext)
    {
        Locale lokale = _locale != null ? _locale : facesContext.getViewRoot().getLocale();

        if (_pattern == null && _type == null)
        {
            throw new ConverterException("Cannot get NumberFormat, either type or pattern needed.");
        }

        // pattern
        if (_pattern != null)
        {
            return new DecimalFormat(_pattern, new DecimalFormatSymbols(lokale));
        }

        // type
        if (_type.equals("number"))
        {
            return NumberFormat.getNumberInstance(lokale);
        }
        else if (_type.equals("currency"))
        {
            return NumberFormat.getCurrencyInstance(lokale);
        }
        else if (_type.equals("percent"))
        {
            return NumberFormat.getPercentInstance(lokale);
        }
        throw new ConverterException("Cannot get NumberFormat, illegal type " + _type);
    }

    private void formatCurrency(NumberFormat format)
    {
        if (_currencyCode == null && _currencySymbol == null)
        {
            return;
        }

        boolean useCurrencyCode;
        if (JAVA_VERSION_14)
        {
            useCurrencyCode = _currencyCode != null;
        }
        else
        {
            useCurrencyCode = _currencySymbol == null;
        }

        if (useCurrencyCode)
        {
            // set Currency
            try
            {
                format.setCurrency(Currency.getInstance(_currencyCode));
            }
            catch (Exception e)
            {
                throw new ConverterException("Unable to get Currency instance for currencyCode " +
                                             _currencyCode);
            }
        }
        else if (format instanceof DecimalFormat)

        {
            DecimalFormat dFormat = (DecimalFormat)format;
            DecimalFormatSymbols symbols = dFormat.getDecimalFormatSymbols();
            symbols.setCurrencySymbol(_currencySymbol);
            dFormat.setDecimalFormatSymbols(symbols);
        }
    }

    // STATE SAVE/RESTORE
    public void _restoreState(FacesContext facesContext, Object state)
    {
        Object values[] = (Object[])state;
        _currencyCode = (String)values[0];
        _currencySymbol = (String)values[1];
        _locale = (Locale)values[2];
        Integer value = (Integer)values[3];
        _maxFractionDigits = value != null ? value.intValue() : 0;
        value = (Integer)values[4];
        _maxIntegerDigits = value != null ? value.intValue() : 0;
        value = (Integer)values[5];
        _minFractionDigits = value != null ? value.intValue() : 0;
        value = (Integer)values[6];
        _minIntegerDigits = value != null ? value.intValue() : 0;
        _pattern = (String)values[7];
        _type = (String)values[8];
        _groupingUsed = ((Boolean)values[9]).booleanValue();
        _integerOnly = ((Boolean)values[10]).booleanValue();
        _maxFractionDigitsSet = ((Boolean)values[11]).booleanValue();
        _maxIntegerDigitsSet = ((Boolean)values[12]).booleanValue();
        _minFractionDigitsSet = ((Boolean)values[13]).booleanValue();
        _minIntegerDigitsSet = ((Boolean)values[14]).booleanValue();
    }

    public Object _saveState(FacesContext facesContext)
    {
        Object values[] = new Object[15];
        values[0] = _currencyCode;
        values[1] = _currencySymbol;
        values[2] = _locale;
        values[3] = _maxFractionDigitsSet ? new Integer(_maxFractionDigits) : null;
        values[4] = _maxIntegerDigitsSet ? new Integer(_maxIntegerDigits) : null;
        values[5] = _minFractionDigitsSet ? new Integer(_minFractionDigits) : null;
        values[6] = _minIntegerDigitsSet ? new Integer(_minIntegerDigits) : null;
        values[7] = _pattern;
        values[8] = _type;
        values[9] = _groupingUsed ? Boolean.TRUE : Boolean.FALSE;
        values[10] = _integerOnly ? Boolean.TRUE : Boolean.FALSE;
        values[11] = _maxFractionDigitsSet ? Boolean.TRUE : Boolean.FALSE;
        values[12] = _maxIntegerDigitsSet ? Boolean.TRUE : Boolean.FALSE;
        values[13] = _minFractionDigitsSet ? Boolean.TRUE : Boolean.FALSE;
        values[14] = _minIntegerDigitsSet ? Boolean.TRUE : Boolean.FALSE;
        return values;
    }

    // GETTER & SETTER
    
    /**
     * ISO 4217 currency code
     * 
     * @JSFProperty
     */
    public String getCurrencyCode()
    {
        return _currencyCode != null ?
               _currencyCode :
               getDecimalFormatSymbols().getInternationalCurrencySymbol();
    }

    public void setCurrencyCode(String currencyCode)
    {
        _currencyCode = currencyCode;
    }

    /**
     * The currency symbol used to format a currency value. 
     * 
     * Defaults to the currency symbol for locale.
     * 
     * @JSFProperty
     */
    public String getCurrencySymbol()
    {
        return _currencySymbol != null ?
               _currencySymbol :
               getDecimalFormatSymbols().getCurrencySymbol();
    }

    public void setCurrencySymbol(String currencySymbol)
    {
        _currencySymbol = currencySymbol;
    }

    /**
     * Specifies whether output will contain grouping separators. 
     * 
     * Default: true.
     * 
     * @JSFProperty
     */
    public boolean isGroupingUsed()
    {
        return _groupingUsed;
    }

    public void setGroupingUsed(boolean groupingUsed)
    {
        _groupingUsed = groupingUsed;
    }

    /**
     * Specifies whether only the integer part of the input will be parsed. 
     * 
     * Default: false.
     * 
     * @JSFProperty
     */
    public boolean isIntegerOnly()
    {
        return _integerOnly;
    }

    public void setIntegerOnly(boolean integerOnly)
    {
        _integerOnly = integerOnly;
    }

    /**
     * The name of the locale to be used, instead of the default as specified 
     * in the faces configuration file.
     * 
     * @JSFProperty
     */
    public Locale getLocale()
    {
        if (_locale != null) return _locale;
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getViewRoot().getLocale();
    }

    public void setLocale(Locale locale)
    {
        _locale = locale;
    }

    /**
     * The maximum number of digits in the fractional portion of the number.
     * 
     * @JSFProperty
     */
    public int getMaxFractionDigits()
    {
        return _maxFractionDigits;
    }

    public void setMaxFractionDigits(int maxFractionDigits)
    {
        _maxFractionDigitsSet = true;
        _maxFractionDigits = maxFractionDigits;
    }

    /**
     * The maximum number of digits in the integer portion of the number.
     * 
     * @JSFProperty
     */
    public int getMaxIntegerDigits()
    {
        return _maxIntegerDigits;
    }

    public void setMaxIntegerDigits(int maxIntegerDigits)
    {
        _maxIntegerDigitsSet = true;
        _maxIntegerDigits = maxIntegerDigits;
    }

    /**
     * The minimum number of digits in the fractional portion of the number.
     * 
     * @JSFProperty
     */
    public int getMinFractionDigits()
    {
        return _minFractionDigits;
    }

    public void setMinFractionDigits(int minFractionDigits)
    {
        _minFractionDigitsSet = true;
        _minFractionDigits = minFractionDigits;
    }

    /**
     * The minimum number of digits in the integer portion of the number.
     * 
     * @JSFProperty
     */
    public int getMinIntegerDigits()
    {
        return _minIntegerDigits;
    }

    public void setMinIntegerDigits(int minIntegerDigits)
    {
        _minIntegerDigitsSet = true;
        _minIntegerDigits = minIntegerDigits;
    }

    /**
     * A custom Date formatting pattern, in the format used by java.text.SimpleDateFormat.
     * 
     * @JSFProperty
     */
    public String getPattern()
    {
        return _pattern;
    }

    public void setPattern(String pattern)
    {
        _pattern = pattern;
    }

    public boolean isTransient()
    {
        return _transient;
    }

    public void setTransient(boolean aTransient)
    {
        _transient = aTransient;
    }

    /**
     * The type of formatting/parsing to be performed. 
     * 
     * Values include: number, currency, and percentage. Default: number.
     * 
     * @JSFProperty
     */
    public String getType()
    {
        return _type;
    }

    public void setType(String type)
    {
        //TODO: validate type
        _type = type;
    }

    private static boolean checkJavaVersion14()
    {
        String version = System.getProperty("java.version");
        if (version == null)
        {
            return false;
        }
        byte java14 = 0;
        for (int idx = version.indexOf('.'), i = 0; idx > 0 || version != null; i++)
        {
            if (idx > 0)
            {
                byte value = Byte.parseByte(version.substring(0, 1));
                version = version.substring(idx + 1, version.length());
                idx = version.indexOf('.');
                switch (i)
                {
                    case 0:
                        if (value == 1)
                        {
                            java14 = 1;
                            break;
                        }
                        else if (value > 1)
                        {
                            java14 = 2;
                        }
                    case 1:
                        if (java14 > 0 && value >= 4)
                        {
                            java14 = 2;
                        }
                        ;
                    default:
                        idx = 0;
                        version = null;
                        break;
                }
            }
            else
            {
                byte value = Byte.parseByte(version.substring(0, 1));
                if (java14 > 0 && value >= 4)
                {
                    java14 = 2;
                }
                break;
            }
        }
        return java14 == 2;
    }


    private DecimalFormatSymbols getDecimalFormatSymbols()
    {
        return new DecimalFormatSymbols(getLocale());
    }
}
