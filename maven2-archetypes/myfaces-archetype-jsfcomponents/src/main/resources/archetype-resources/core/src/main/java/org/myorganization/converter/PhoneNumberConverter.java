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
package org.myorganization.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * @JSFConverter
 *   name = "mycomponents:phoneNumberConverter"
 *   tagClass = "org.myorganization.converter.PhoneNumberConverterTag"
 */
public class PhoneNumberConverter
        implements Converter
{
    public static final String CONVERTER_ID = "javax.faces.PhoneNumber";
    
    public Object getAsObject(FacesContext context, UIComponent component, String value)
            throws ConverterException
    {
        String [] strPhone = value.split("-");
        PhoneNumber objPhone = null;
        if(strPhone.length == 2) 
        {
            objPhone = new PhoneNumber(strPhone[0], strPhone[1]);
        } 
        else if(strPhone.length == 1)
        {
            objPhone = new PhoneNumber(strPhone[0]);
        }
        
        return objPhone;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException
    {
        if(value != null)
        {
            PhoneNumber phone = (PhoneNumber)value;
            return phone.getAreaCode() + "-" + phone.getNumber();
        }
        
        return null;
    }

}
