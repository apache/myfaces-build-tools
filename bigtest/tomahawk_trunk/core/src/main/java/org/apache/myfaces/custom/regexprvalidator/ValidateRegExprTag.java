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
package org.apache.myfaces.custom.regexprvalidator;

import org.apache.myfaces.validator.ValidatorBaseTag;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

/**
 * @author mwessendorf (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public class ValidateRegExprTag extends ValidatorBaseTag {
  private static final long serialVersionUID = -449945949876262076L;
  //the pattern, needed by Commons-Validator.
    private String _pattern = null;

	public ValidateRegExprTag(){
	}

    public void setPattern(String string) {
        _pattern = string;
    }

	protected Validator createValidator() throws JspException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		setValidatorId(RegExprValidator.VALIDATOR_ID);
		RegExprValidator validator = (RegExprValidator)super.createValidator();
        if (_pattern != null)
        {
            if (UIComponentTag.isValueReference(_pattern))
            {
                ValueBinding vb = facesContext.getApplication().createValueBinding(_pattern);
                validator.setPattern(new String(vb.getValue(facesContext).toString()));
            }
            else
            {
                validator.setPattern(_pattern);
            }
        }

		return validator;
	}
    
    public void release()
    {
        super.release();
        _pattern= null;
    }

}
