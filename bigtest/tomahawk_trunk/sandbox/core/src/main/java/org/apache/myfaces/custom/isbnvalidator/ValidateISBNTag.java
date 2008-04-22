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
package org.apache.myfaces.custom.isbnvalidator;

import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

import org.apache.myfaces.validator.ValidatorBaseTag;

/**
 * @author <a href="mailto:matzew@apache.org">Matthias We&szlig;endorf</a> (latest modification by $author$)
 * @version $Rev$ $Date$
 */
public class ValidateISBNTag extends ValidatorBaseTag {

    /**
     * serial version id for correct serialisation versioning
     */
    private static final long serialVersionUID = 1L;

    public ValidateISBNTag(){}

	protected Validator createValidator() throws JspException
    {
		setValidatorId(ISBNValidator.VALIDATOR_ID);
		ISBNValidator validator = (ISBNValidator)super.createValidator();
		return validator;
	}

    public void release()
    {
        super.release();
    }


}