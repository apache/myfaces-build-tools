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

package org.apache.myfaces.custom.convertboolean;

import javax.faces.component.UIComponent;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Converter that translates between boolean values (true/false)
 * and alternate versions of those boolean values like
 * (yes/no), (1/0), and (way/no way).
 * <p/>
 * To customize the representation of a boolean true and false,
 * use {@link #setTrueValue(String)}
 * and {@link #setFalseValue(String)}
 * respectively.  If  not configured with these setter methods,
 * it defaults to <code>true</code> and <code>false</code>.
 * <p/>
 * The values are case sensitive.
 * <p/>
 *
 * @JSFConverter
 *   name = "s:convertBoolean"
 *   tagClass = "org.apache.myfaces.custom.convertboolean.ConvertBooleanTag" 
 *   
 * @author Ken Weiner
 */
public class BooleanConverter implements Converter, StateHolder
{

    private String trueValue = "true";
    private String falseValue = "false";

    private boolean isTransient;

    public static final String CONVERTER_ID = "org.apache.myfaces.custom.convertboolean.BooleanConverter";

    public BooleanConverter()
    {
        // Default constructor
    }

    public String getFalseValue()
    {
        return falseValue;
    }

    public void setFalseValue(String falseValue)
    {
        this.falseValue = falseValue;
    }

    public String getTrueValue()
    {
        return trueValue;
    }

    public void setTrueValue(String trueValue)
    {
        this.trueValue = trueValue;
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
            throws ConverterException
    {
        if (facesContext == null)
        {
            throw new NullPointerException("facesContext");
        }
        if (uiComponent == null)
        {
            throw new NullPointerException("uiComponent");
        }

        if (value != null)
        {
            value = value.trim();
            if (value.length() > 0)
            {
                try
                {
                    return Boolean.valueOf(value.equals(trueValue));
                }
                catch (Exception e)
                {
                    throw new ConverterException(e);
                }
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
            throws ConverterException
    {
        if (facesContext == null)
        {
            throw new NullPointerException("facesContext");
        }
        if (uiComponent == null)
        {
            throw new NullPointerException("uiComponent");
        }

        if (value == null)
        {
            return "";
        }
        if (value instanceof String)
        {
            return (String) value;
        }
        try
        {
            return ((Boolean) value).booleanValue() ? trueValue : falseValue;
        }
        catch (Exception e)
        {
            throw new ConverterException(e);
        }
    }

    // StateHolder methods ////////////////////////////////////////////////////

    public boolean isTransient()
    {
        return this.isTransient;
    }

    public void setTransient(boolean newTransientValue)
    {
        this.isTransient = newTransientValue;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        this.trueValue = (String) values[0];
        this.falseValue = (String) values[1];
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = this.trueValue;
        values[1] = this.falseValue;
        return values;
    }


}
