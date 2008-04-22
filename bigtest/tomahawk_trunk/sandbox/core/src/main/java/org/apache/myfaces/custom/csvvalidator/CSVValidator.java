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
import java.util.regex.PatternSyntaxException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.myfaces.shared_tomahawk.util.MessageUtils;
import org.apache.myfaces.validator.ValidatorBase;

/**
*
* @author Lance Frohman
*
* @version $Revision: $ $Date: $
*/

public class CSVValidator extends ValidatorBase {
	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String VALIDATOR_ID = "org.apache.myfaces.validator.csv";
	/**
	 * <p>The message identifiers of the {@link FacesMessage} to be created if
	 * the check fails.</p>
	 */
	public static final String CSV_NOT_STRING_MESSAGE_ID = "org.apache.myfaces.csv.NOT_STRING";
	public static final String CSV_INVALID_SEPARATOR_MESSAGE_ID = "org.apache.myfaces.csv.INVALID_SEPARATOR";
	public static final String CSV_SUFFIX_MESSAGE_ID = "org.apache.myfaces.csv.SUFFIX";
	private static final String DEFAULT_SEPARATOR = ",";

	// the VALIDATOR_ID of the actual validator to be used
	protected String _subvalidatorId;

	// the separator character to separate values
	protected String _separator;

	/**
	 * @return the VALIDATOR_ID of the actual validator to be used
	 */
    public String getSubvalidatorId()
    {
    	return _subvalidatorId;
    }

	/**
	 * @param the VALIDATOR_ID of the actual validator to be used
	 */
	public void setSubvalidatorId(String subvalidatorId) {
		this._subvalidatorId = subvalidatorId;
	}

	/**
	 * @return the separator character to separate values
	 */
	public String getSeparator() {
		return _separator;
	}

	/**
	 * @param the separator character to separate values
	 */
	public void setSeparator(String separator) {
		this._separator = separator;
	}

	public Object saveState(FacesContext context) {
		Object value[] = new Object[2];
        value[0] = _subvalidatorId;
        value[1] = _separator;
		return value;
	}

	public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        _subvalidatorId = (String) values[0];
        _separator = (String) values[1];
	}

	private FacesMessage addMessage(FacesMessage oldMsg, FacesMessage newMsg, int index, String suffixMessageKey) {
		if (oldMsg != null && newMsg.getSeverity().getOrdinal() < oldMsg.getSeverity().getOrdinal())
			return oldMsg;
		String summaryMessageText = null;
		String detailMessageText = null;
		if (oldMsg == null || newMsg.getSeverity().getOrdinal() > oldMsg.getSeverity().getOrdinal()) {
			summaryMessageText = null;
			detailMessageText = null;
		}
		else {
			summaryMessageText = oldMsg.getSummary();
			detailMessageText = oldMsg.getDetail();
		}
		Object[] args = { new Integer(index + 1) };
		FacesMessage suffixMessage = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, suffixMessageKey, args);
		String summarySuffix = suffixMessage.getSummary();
		String detailSuffix = suffixMessage.getDetail();
		if (summarySuffix == null)
			summarySuffix = detailSuffix;
		else if (detailSuffix == null)
			detailSuffix = summarySuffix;
		String summary = newMsg.getSummary();
		if (summaryMessageText == null)
			summaryMessageText = summary + summarySuffix;
		else
			summaryMessageText += ", " + summary + summarySuffix;
		String detail = newMsg.getDetail();
		if (detailMessageText == null)
			detailMessageText = detail + detailSuffix;
		else
			detailMessageText += ", " + detail + detailSuffix;
		return new FacesMessage(newMsg.getSeverity(), summaryMessageText, detailMessageText);
	}

	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {

		if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

		if (value == null)
		{
		    return;
		}

		String suffixMessageKey = getMessage();
	    if (suffixMessageKey == null)
	    	suffixMessageKey = CSV_SUFFIX_MESSAGE_ID;
		FacesMessage facesMsg = null;
		// value must be a String
		if (!(value instanceof String)) {
			Object[] args = { value };
            throw new ValidatorException(MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, CSV_NOT_STRING_MESSAGE_ID, args));
		}
	    Validator validator = facesContext.getApplication().createValidator(_subvalidatorId);
	    if (_separator == null)
	    	_separator = DEFAULT_SEPARATOR;
	    String[] values = null;
	    try {
			values = ((String)value).split(_separator);
	    }
	    catch (PatternSyntaxException e) {
			Object[] args = { _separator };
            throw new ValidatorException(MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, CSV_INVALID_SEPARATOR_MESSAGE_ID, args));
	    }
		// loop through the separated values and validate each one
		for (int i = 0; i < values.length; i++) {
			if (values[i].trim().length() == 0) {
				continue;
			}
			else try {
				validator.validate(facesContext, uiComponent, values[i]);
			}
			catch (ValidatorException e) {
				facesMsg = addMessage(facesMsg, e.getFacesMessage(), i, suffixMessageKey);
			}
		}
		if (facesMsg != null)
			throw new ValidatorException(facesMsg);
	}
}
