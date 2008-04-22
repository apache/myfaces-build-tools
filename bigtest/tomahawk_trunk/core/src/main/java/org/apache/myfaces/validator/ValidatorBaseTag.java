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
package org.apache.myfaces.validator;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.faces.webapp.ValidatorTag;
import javax.servlet.jsp.JspException;

/**
 * ValidatorBaseTag provides support for ValidatorBase subclasses.
 * ValidatorBaseTag subclass tld entries should include the following to pick up attribute defintions.
 *         &ext_validator_base_attributes;
 * 
 * @author mkienenb (latest modification by $Author$)
 * @version $Revision$
 */
public class ValidatorBaseTag extends ValidatorTag {
    private static final long serialVersionUID = 4416508071412794682L;
    private String _message = null;
    private String _detailMessage = null;
    private String _summaryMessage = null;

    public void setMessage(String string) {
        _message = string;
    }

    public void setDetailMessage(String detailMessage)
    {
        _detailMessage = detailMessage;
    }

    public void setSummaryMessage(String summaryMessage)
    {
        _summaryMessage = summaryMessage;
    }

    protected Validator createValidator() throws JspException {

        ValidatorBase validator = (ValidatorBase)super.createValidator();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if(_message != null && _detailMessage != null)
            throw new JspException("you may not set detailMessage and detailMessage together - they serve the same purpose.");

        String detailMessage = _message;

        if(_detailMessage != null)
            detailMessage = _detailMessage;

        if (detailMessage != null)
        {
            if (UIComponentTag.isValueReference(detailMessage))
            {
                ValueBinding vb = facesContext.getApplication().createValueBinding(detailMessage);
                validator.setValueBinding("detailMessage",vb);
            }
            else
            {
                validator.setDetailMessage(detailMessage);
            }
        }

        if (_summaryMessage != null)
        {
            if (UIComponentTag.isValueReference(_summaryMessage))
            {
                ValueBinding vb = facesContext.getApplication().createValueBinding(_summaryMessage);
                validator.setValueBinding("summaryMessage",vb);
            }
            else
            {
                validator.setSummaryMessage(_summaryMessage);
            }
        }

        return validator;
    }

    public void release()
    {
        super.release();
        _message= null;
        _detailMessage = null;
        _summaryMessage = null;
    }
}
