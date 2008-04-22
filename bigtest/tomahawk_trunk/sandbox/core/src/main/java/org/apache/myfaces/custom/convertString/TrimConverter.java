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

package org.apache.myfaces.custom.convertString;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Useful when a char(5) field ends up rendered as "abc  " .
 * 
 * @author Dennis Byrne
 */

public class TrimConverter implements Converter
{
    
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException
    {
        return value == null ? null : value;
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException
    {
        return value == null ? "" : value.toString().trim();
    }

}
