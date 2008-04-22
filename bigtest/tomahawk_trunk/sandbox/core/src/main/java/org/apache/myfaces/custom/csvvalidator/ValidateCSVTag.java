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
package org.apache.myfaces.custom.csvvalidator;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import org.apache.myfaces.validator.ValidatorBaseTag;

/**
*
* @author Lance Frohman
*
* @version $Revision: $ $Date: $
*/

public class ValidateCSVTag extends ValidatorBaseTag
{
    private static final long serialVersionUID = 1L;
    private String _subvalidatorId;
    private String _separator;

    public void setSubvalidatorId(String subvalidatorId)
    {
        this._subvalidatorId = subvalidatorId;
    }

    public void setSeparator(String separator)
    {
        this._separator = separator;
    }

    protected Validator createValidator( ) throws JspException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        setValidatorId(CSVValidator.VALIDATOR_ID);
        CSVValidator validator = (CSVValidator)super.createValidator();
        if (UIComponentTag.isValueReference(_subvalidatorId))
        {
            ValueBinding vb = facesContext.getApplication().createValueBinding(_subvalidatorId);
            validator.setSubvalidatorId(new String(vb.getValue(facesContext).toString()));
        }
        else
        {
            validator.setSubvalidatorId(_subvalidatorId);
        }
        if (_separator != null)
        {
            if (UIComponentTag.isValueReference(_separator))
            {
                ValueBinding vb = facesContext.getApplication().createValueBinding(_separator);
                validator.setSeparator(new String(vb.getValue(facesContext).toString()));
            }
            else
            {
                validator.setSeparator(_separator);
            }
        }
        return validator;
    }

    public void release()
    {
        super.release();
        _subvalidatorId= null;
        _separator= null;
    }

}
