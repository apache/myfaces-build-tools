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
package org.apache.myfaces.component.html.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.util.MessageUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Iterator;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlSelectOneRadio
        extends javax.faces.component.html.HtmlSelectOneRadio
        implements UserRoleAware, DisplayValueOnlyCapable
{
    private static Log log = LogFactory.getLog(HtmlSelectOneRadio.class);

    public String getClientId(FacesContext context)
    {
        String clientId = HtmlComponentUtils.getClientId(this, getRenderer(context), context);
        if (clientId == null)
        {
            clientId = super.getClientId(context);
        }

        return clientId;
    }

    /**
     * Overridden method, as with extended seletOne, value doesn't necessarily
     * have to be contained within select list, for example, when forceId="true" and
     * forceIdIndex="false" then component may be used in datatable.
     */
    protected void validateValue(FacesContext context, Object value)
    {
        //Is this radio button used within a datatable (forceId=true and forceIdIndex=false)
        boolean forceId = getBooleanValue(JSFAttr.FORCE_ID_ATTR,
                this.getAttributes().get(JSFAttr.FORCE_ID_ATTR), false);

//      see if the originally supplied id should be used
        boolean forceIdIndex = getBooleanValue(JSFAttr.FORCE_ID_INDEX_ATTR,
                this.getAttributes().get(JSFAttr.FORCE_ID_INDEX_ATTR), true);

        boolean dataTable = forceId && !forceIdIndex;

        if (!dataTable)
        {
            super.validateValue(context, value);
        }
        else
        {
            //Specific behavior for data tables, or other scenarios where forceId is
            //true and forceIdIndex is false

            //Check if empty
            boolean empty = value == null
                    || (value instanceof String && ((String) value).length() == 0);

            //Check required and empty
            if (isRequired() && empty)
            {
              //Only add this message once, not for every radio button in set
            	String clientId = this.getClientId(context);
            	Iterator messages = context.getMessages(clientId);
            	boolean messageExists = messages.hasNext();

            	if(!messageExists)
            	{
            		//Add message
            		FacesMessage message = MessageUtils.getMessage(REQUIRED_MESSAGE_ID, new Object[]{clientId});
            		message.setSeverity(FacesMessage.SEVERITY_WARN);
            		context.addMessage(clientId, message);

                    setValid(false);
            	}
                return;
            }

            //Call validators
            if (!empty)
            {
                callValidators(context, this, value);
            }
        }
    }


    private static boolean getBooleanValue(String attribute, Object value, boolean defaultValue)
    {
        if(value instanceof Boolean)
        {
            return ((Boolean) value).booleanValue();
        }
        else if(value instanceof String)
        {
            return Boolean.valueOf((String) value).booleanValue();
        }
        else if(value != null)
        {
            log.error("value for attribute "+attribute+
                    " must be instanceof 'Boolean' or 'String', is of type : "+value.getClass());

            return defaultValue;
        }

        return defaultValue;
    }

    private static void callValidators(FacesContext context, UIComponent input, Object convertedValue)
    {
        if(!(input instanceof EditableValueHolder))
            throw new FacesException("param input not of type EditableValueHolder, but of : "+input.getClass().getName());

        EditableValueHolder holder = (EditableValueHolder) input;

        Validator[] validators = holder.getValidators();
        for (int i = 0; i < validators.length; i++)
        {
            Validator validator = validators[i];
            try
            {
                validator.validate(context, input, convertedValue);
            }
            catch (ValidatorException e)
            {
                holder.setValid(false);
                FacesMessage facesMessage = e.getFacesMessage();
                if (facesMessage != null)
                {
                    facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                    context.addMessage(input.getClientId(context), facesMessage);
                }
            }
        }

        MethodBinding validatorBinding = holder.getValidator();
        if (validatorBinding != null)
        {
            try
            {
                validatorBinding.invoke(context, new Object[] {context, input, convertedValue});
            }
            catch (EvaluationException e)
            {
                holder.setValid(false);
                Throwable cause = e.getCause();
                if (cause instanceof ValidatorException)
                {
                    FacesMessage facesMessage = ((ValidatorException) cause).getFacesMessage();
                    if (facesMessage != null)
                    {
                        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                        context.addMessage(input.getClientId(context), facesMessage);
                    }
                }
                else
                {
                    throw e;
                }
            }
        }
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectOneRadio";
    public static final String DEFAULT_RENDERER_TYPE ="org.apache.myfaces.Radio";

    private static final boolean DEFAULT_DISPLAYVALUEONLY = false;
    private static final boolean DEFAULT_ESCAPE = true;

    private String _enabledOnUserRole = null;
    private String _visibleOnUserRole = null;
    private Boolean _displayValueOnly = null;
    private String _displayValueOnlyStyle = null;
    private String _displayValueOnlyStyleClass = null;
    private Boolean _escape = null;

    public HtmlSelectOneRadio()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }


    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public String getEnabledOnUserRole()
    {
        if (_enabledOnUserRole != null) return _enabledOnUserRole;
        ValueBinding vb = getValueBinding("enabledOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public String getVisibleOnUserRole()
    {
        if (_visibleOnUserRole != null) return _visibleOnUserRole;
        ValueBinding vb = getValueBinding("visibleOnUserRole");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public boolean isSetDisplayValueOnly() {
        if (_displayValueOnly != null) return true;
        ValueBinding vb = getValueBinding("displayValueOnly");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null;
    }

    public boolean isDisplayValueOnly() {
        if (_displayValueOnly != null) return _displayValueOnly.booleanValue();
        ValueBinding vb = getValueBinding("displayValueOnly");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_DISPLAYVALUEONLY;
    }

    public void setDisplayValueOnly(boolean displayValueOnly) {
        _displayValueOnly = Boolean.valueOf(displayValueOnly);
    }

    public String getDisplayValueOnlyStyle() {
        if (_displayValueOnlyStyle != null) return _displayValueOnlyStyle;
        ValueBinding vb = getValueBinding("displayValueOnlyStyle");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setDisplayValueOnlyStyle(String displayValueOnlyStyle) {
        _displayValueOnlyStyle = displayValueOnlyStyle;
    }

    public String getDisplayValueOnlyStyleClass() {
        if (_displayValueOnlyStyleClass != null) return _displayValueOnlyStyleClass;
        ValueBinding vb = getValueBinding("displayValueOnlyStyleClass");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setDisplayValueOnlyStyleClass(String displayValueOnlyStyleClass) {
        _displayValueOnlyStyleClass = displayValueOnlyStyleClass;
    }

    public void setEscape(boolean escape)
    {
        _escape = Boolean.valueOf(escape);
    }

    public boolean isEscape()
    {
        if (_escape != null) return _escape.booleanValue();
        ValueBinding vb = getValueBinding("escape");
        Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_ESCAPE;
    }

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[7];
        values[0] = super.saveState(context);
        values[1] = _enabledOnUserRole;
        values[2] = _visibleOnUserRole;
        values[3] = _displayValueOnly;
        values[4] = _displayValueOnlyStyle;
        values[5] = _displayValueOnlyStyleClass;
        values[6] = _escape;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _enabledOnUserRole = (String)values[1];
        _visibleOnUserRole = (String)values[2];
        _displayValueOnly = (Boolean)values[3];
        _displayValueOnlyStyle = (String)values[4];
        _displayValueOnlyStyleClass = (String)values[5];
        _escape = (Boolean)values[6];
    }
    //------------------ GENERATED CODE END ---------------------------------------
}
