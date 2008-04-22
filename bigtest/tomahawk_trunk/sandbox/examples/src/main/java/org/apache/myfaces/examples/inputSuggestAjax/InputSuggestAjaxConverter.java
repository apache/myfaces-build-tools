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

package org.apache.myfaces.examples.inputSuggestAjax;

import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import java.util.List;

/**
 * @author Gerald Müllan
 *         Date: 24.10.2006
 *         Time: 01:18:42
 */
public class InputSuggestAjaxConverter 
        implements Converter
{
    public Object getAsObject(FacesContext context,
                              UIComponent component,
                              String value) throws ConverterException
    {
        List addresses = InputSuggestAjaxBean.dummyDataBaseAddresses;

        if (value != null)
        {
            Integer newValue = new Integer(value);

            for (int i = 0; i < addresses.size(); i++)
            {
                Address o = (Address) addresses.get(i);
                if (o.getStreetNumber() == newValue.intValue())
                {
                    return o;
                }
            }
        }
        return null;
    }

    public String getAsString(FacesContext context,
                              UIComponent component,
                              Object value) throws ConverterException
    {
        if (value instanceof Address)
        {
            Address address = (Address) value;

            return Integer.toString(address.getStreetNumber());
        }
        else if (value instanceof String)
        {
            return (String) value;
        }
        else return null;
    }
}
