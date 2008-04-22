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
package org.apache.myfaces.custom.suggest;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputTagBase;

/**
 * @author Sean Schofield
 * @author Matt Blum
 * @version $Revision: $ $Date: $
 */
public class InputSuggestTag extends HtmlInputTagBase
{

    public String getComponentType() {
        return "javax.faces.HtmlInputText";
    }

    public String getRendererType() {
        return "org.apache.myfaces.InputSuggest";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

//       FacesContext context = getFacesContext();
//       if (_value != null)
//       {
//           if (isValueReference(_value))
//           {
//               ValueBinding vb = context.getApplication().createValueBinding(_value);
//               component.setValueBinding("value", vb);
//           }
//           else
//           {
//               component.getAttributes().put("value", _value);
//           }
//       }
    }
}
