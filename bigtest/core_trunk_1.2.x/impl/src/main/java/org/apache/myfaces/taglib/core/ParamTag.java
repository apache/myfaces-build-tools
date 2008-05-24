/*
 * Copyright 2004-2006 The Apache Software Foundation.
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

import org.apache.myfaces.shared_impl.taglib.UIComponentELTagBase;

import javax.faces.component.UIComponent;
import javax.el.ValueExpression;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler (latest modification by $Author$)
 * @author Bruno Aranda (JSR-252)
 * @version $Revision$ $Date$
 */
public class ParamTag
    extends UIComponentELTagBase
{
    public String getComponentType()
    {
        return "javax.faces.Parameter";
    }

    public String getRendererType()
    {
        return null;
    }

    // UIComponent attributes --> already implemented in UIComponentELTagBase

    // UIParameter attributes
    private ValueExpression _name;

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        
        setStringProperty(component, "name", _name);
    }

    public void setName(ValueExpression name)
    {
        _name = name;
    }
}
