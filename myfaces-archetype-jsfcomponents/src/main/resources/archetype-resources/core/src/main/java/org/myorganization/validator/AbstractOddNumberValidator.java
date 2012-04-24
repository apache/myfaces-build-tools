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
package org.myorganization.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.commons.validator.ValidatorBase;

/**
 * @JSFValidator
 *   name = "mycomponents:validateOddNum"
 *   class = "org.myorganization.validator.OddNumberValidator"
 *   tagClass = "org.myorganization.validator.OddNumberValidatorTag"
 */
public abstract class AbstractOddNumberValidator extends ValidatorBase
{
    public static final String VALIDATOR_ID = "org.myorganization.validator.OddNumberValidator";
    
    public void validate(FacesContext arg0, UIComponent component, Object value)
            throws ValidatorException
    {
        if(!(value instanceof Integer)) {
            throw new ValidatorException(new FacesMessage("Please enter an integer."));
        }
        
        int intVal = ((Integer)value).intValue();
        if(intVal % 2 == 0) {
            throw new ValidatorException(new FacesMessage("Please enter an odd number."));
        }
    }    
}
