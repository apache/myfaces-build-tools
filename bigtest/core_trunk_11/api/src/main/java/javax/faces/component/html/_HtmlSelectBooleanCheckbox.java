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
package javax.faces.component.html;

import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Allow the user to choose a "true" or "false" value, presented as a
 * checkbox.
 * &lt;p&gt;
 * Renders as an HTML input tag with its type set to "checkbox", and
 * its name attribute set to the id. The "checked" attribute is rendered
 * if the value of this component is true.
 * &lt;p&gt;
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * 
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @JSFComponent
 *   name = "h:selectBooleanCheckbox"
 *   class = "javax.faces.component.html.HtmlSelectBooleanCheckbox"
 *   tagClass = "org.apache.myfaces.taglib.html.HtmlSelectBooleanCheckboxTag"
 *   desc = "h:selectBooleanCheckbox"
 *
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class _HtmlSelectBooleanCheckbox extends UISelectBoolean implements
    _AccesskeyProperty, _UniversalProperties, _Disabled_ReadonlyProperties,
    _Focus_BlurProperties, _Change_SelectProperties, _EventProperties,
    _StyleProperties, _TabindexProperty
{

    public static final String COMPONENT_TYPE = "javax.faces.HtmlSelectBooleanCheckbox";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Checkbox";

}
