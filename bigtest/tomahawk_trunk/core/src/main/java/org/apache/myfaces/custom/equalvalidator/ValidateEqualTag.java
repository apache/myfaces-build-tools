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
package org.apache.myfaces.custom.equalvalidator;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import org.apache.myfaces.validator.ValidatorBaseTag;

/**
 * @author mwessendorf (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public class ValidateEqualTag extends ValidatorBaseTag {

  private static final long serialVersionUID = -3249115551944863108L;
  //the foreign component_id on which the validation is based.
	private String _for = null;

	public ValidateEqualTag(){
	}

	public void setFor(String string) {
		_for = string;
	}

	protected Validator createValidator() throws JspException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		setValidatorId(EqualValidator.VALIDATOR_ID);
		EqualValidator validator = (EqualValidator)super.createValidator();
		if (_for != null)
		{
			if (UIComponentTag.isValueReference(_for))
			{
				ValueBinding vb = facesContext.getApplication().createValueBinding(_for);
				validator.setFor(new String(vb.getValue(facesContext).toString()));
			}
			else
			{
				validator.setFor(_for);
			}
		}
		return validator;
	}
    public void release() {
        super.release();
       _for = null;
    }
}
