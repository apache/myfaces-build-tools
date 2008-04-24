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
package javax.faces.webapp;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Creates a validator and associates it with the nearest parent
 * UIComponent.  During the validation phase (or the apply-request-values
 * phase for immediate components), if the associated component has any
 * submitted value and the conversion of that value to the required
 * type has succeeded then the specified validator type is
 * invoked to test the validity of the converted value.
 * &lt;p&gt;
 * Commonly associated with an h:inputText entity, but may be applied to
 * any input component.
 * &lt;p&gt;
 * Some validators may allow the component to use attributes to define
 * component-specific validation constraints; see the f:attribute tag.
 * See also the "validator" attribute of all input components, which
 * allows a component to specify an arbitrary validation &lt;i&gt;method&lt;/i&gt;
 * (rather than a registered validation type, as this tag does).
 * &lt;p&gt;
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * 
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @JSFJspTag
 *   name="f:validator"
 *   bodyContent="empty"
 *   
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValidatorTag
        extends TagSupport
{
    private static final long serialVersionUID = 8794036166323016663L;
    private String _validatorId;

    /**
     * The registered ID of the desired Validator.
     * 
     * @JSFJspAttribute
     *   required="true"
     */
    public void setValidatorId(String validatorId)
    {
        _validatorId = validatorId;
    }

    public int doStartTag()
            throws javax.servlet.jsp.JspException
    {
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("no parent UIComponentTag found");
        }
        if (!componentTag.getCreated())
        {
            return Tag.SKIP_BODY;
        }

        Validator validator = createValidator();

        UIComponent component = componentTag.getComponentInstance();
        if (component == null)
        {
            throw new JspException("parent UIComponentTag has no UIComponent");
        }
        if (!(component instanceof EditableValueHolder))
        {
            throw new JspException("UIComponent is no ValueHolder");
        }
        ((EditableValueHolder)component).addValidator(validator);

        return Tag.SKIP_BODY;
    }

    public void release()
    {
        super.release();
        _validatorId = null;
    }

    protected Validator createValidator()
            throws JspException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        if (UIComponentTag.isValueReference(_validatorId))
        {
            ValueBinding vb = facesContext.getApplication().createValueBinding(_validatorId);
            return application.createValidator((String)vb.getValue(facesContext));
        }
        else
        {
            return application.createValidator(_validatorId);
        }
    }
}
