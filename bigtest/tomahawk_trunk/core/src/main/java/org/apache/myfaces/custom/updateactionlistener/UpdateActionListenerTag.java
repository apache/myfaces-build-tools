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
package org.apache.myfaces.custom.updateactionlistener;

import javax.faces.application.Application;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * JSF 1.2 introduces a "setPropertyActionListener" with the same functionality like this. 
 *
 * @JSFJspTag
 *   name="t:updateActionListener"
 *   bodyContent="JSP"
 *   
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UpdateActionListenerTag
        extends TagSupport
{
    private static final long serialVersionUID = -6916153064327074092L;
    //private static final Log log = LogFactory.getLog(UpdateActionListenerTag.class);
    private String _property;
    private String _value;
    private String _converter;

    public UpdateActionListenerTag()
    {
    }

    /**
     * @JSFJspAttribute
     *   required="true"
     */
    public void setProperty(String property)
    {
        _property = property;
    }

    /**
     * @JSFJspAttribute
     *   required="true"
     */
    public void setValue(String value)
    {
        _value = value;
    }

    /**
     * @JSFJspAttribute
     */
    public void setConverter(String converter)
    {
        _converter = converter;
    }

    public int doStartTag() throws JspException
    {
        if (_property == null) throw new JspException("property attribute not set");
        if (_value == null) throw new JspException("value attribute not set");
        if (!UIComponentTag.isValueReference(_property)) throw new JspException("property attribute is no valid value reference: " + _property);

        //Find parent UIComponentTag
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("UpdateActionListenerTag has no UIComponentTag ancestor");
        }

        if (componentTag.getCreated())
        {
            //Component was just created, so we add the Listener
            UIComponent component = componentTag.getComponentInstance();
            if (component instanceof ActionSource)
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Application application = facesContext.getApplication();
                UpdateActionListener al = new UpdateActionListener();
                al.setPropertyBinding(application.createValueBinding(_property));
                if (UIComponentTag.isValueReference(_value))
                {
                    al.setValueBinding(application.createValueBinding(_value));
                }
                else
                {
                    al.setValue(_value);
                }
                if (_converter != null)
                {
                    Converter converter = application.createConverter(_converter);
                    al.setConverter(converter);
                }
                ((ActionSource)component).addActionListener(al);
            }
            else
            {
                throw new JspException("Component " + component.getId() + " is no ActionSource");
            }
        }

        return Tag.SKIP_BODY;
    }

    public void release()
    {
    	_property = null;
    	_converter = null;
    	_value = null;
    }

}