/*
 * Copyright 2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.myfaces.taglib.core;

import org.apache.myfaces.shared_impl.renderkit.JSFAttr;
import org.apache.myfaces.shared_impl.taglib.UIComponentELTagBase;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class VerbatimTag
        extends UIComponentELTagBase
{
    //private static final Log log = LogFactory.getLog(VerbatimTag.class);

    public String getComponentType()
    {
        return "javax.faces.Output";
    }

    public String getRendererType()
    {
        return "javax.faces.Text";
    }

    // HtmlOutputText attributes
    private ValueExpression _escape;
    private ValueExpression _rendered;

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, JSFAttr.ESCAPE_ATTR, _escape, Boolean.FALSE);
        setBooleanProperty(component, JSFAttr.RENDERED, _rendered, Boolean.TRUE);

        //No need to save component state
        component.setTransient(true);
    }

    public void setEscape(ValueExpression escape)
    {
        _escape = escape;
    }

    public void setRendered(ValueExpression rendered)
    {
        _rendered = rendered;
    }
    
    public int doAfterBody() throws JspException
    {
        BodyContent bodyContent = getBodyContent();
        if (bodyContent != null)
        {
            UIOutput component = (UIOutput)getComponentInstance();
            component.setValue(bodyContent.getString());
            bodyContent.clearBody();
        }
        return super.getDoAfterBodyValue();
    }
}
