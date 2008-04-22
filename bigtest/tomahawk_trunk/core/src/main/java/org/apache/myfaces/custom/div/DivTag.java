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
package org.apache.myfaces.custom.div;

import org.apache.myfaces.custom.htmlTag.HtmlTagTag;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;

/**
 * @author bdudney (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DivTag extends HtmlTagTag {

  public DivTag() {
    super();
  }

  public String getComponentType() {
    return Div.COMPONENT_TYPE;
  }

    protected UIComponent findComponent(FacesContext context) throws JspException
    {
        return super.findComponent(context);
    }

    public UIComponent getComponentInstance()
    {
        return super.getComponentInstance();
    }

    protected void setProperties(UIComponent component)
    {
        if(!(component instanceof Div))
            throw new IllegalArgumentException("component must be of type 'Div'");

        super.setProperties(component);
    }
}