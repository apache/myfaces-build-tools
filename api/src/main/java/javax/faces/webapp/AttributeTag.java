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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AttributeTag
        extends TagSupport
{
    private static final long serialVersionUID = 3147657100171678632L;
    private String _name;
    private String _value;

    public void setName(String name)
    {
        _name = name;
    }

    public void setValue(String value)
    {
        _value = value;
    }

    public int doStartTag() throws JspException
    {
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("no parent UIComponentTag found");
        }
        UIComponent component = componentTag.getComponentInstance();
        if (component == null)
        {
            throw new JspException("parent UIComponentTag has no UIComponent");
        }
        String name = getName();
        if (component.getAttributes().get(name) == null)
        {
        	if (UIComponentTag.isValueReference(_value))
        	{
            	FacesContext facesContext = FacesContext.getCurrentInstance();
            	ValueBinding vb = facesContext.getApplication().createValueBinding(_value);
            	component.setValueBinding(name, vb);
        	}
        	else
        	{
			if(_value != null) component.getAttributes().put(name, _value);
        	}
        }
        return Tag.SKIP_BODY;
    }

    public void release()
    {
        super.release();
        _name = null;
        _value = null;
    }


    private String getName()
    {
        if (UIComponentTag.isValueReference(_name))
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ValueBinding vb = facesContext.getApplication().createValueBinding(_name);
            return (String)vb.getValue(facesContext);
        }
        else
        {
            return _name;
        }
    }

}
