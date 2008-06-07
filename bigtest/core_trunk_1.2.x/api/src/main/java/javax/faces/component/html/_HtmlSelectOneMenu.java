// WARNING: This file was automatically generated. Do not edit it directly,
//          or you will lose your changes.

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

import javax.faces.component.UISelectOne;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 *
 * Allow the user to choose one option from a set of options.
 * <p>
 * Renders a drop-down menu (aka "combo-box") containing a set of
 * choices, of which only one can be chosen at a time. The available
 * choices are defined via child f:selectItem or f:selectItems
 * elements.
 * <p>
 * The value attribute of this component is read to determine
 * which of the available options is initially selected; its value
 * should match the "value" property of one of the child SelectItem
 * objects.
 * <p>
 * On submit of the enclosing form, the value attribute's bound property
 * is updated to contain the "value" property from the chosen SelectItem.
 * <p>
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 *
 * <h4>Events:</h4>
 * <table border="1" width="100%" cellpadding="3" summary="">
 * <tr bgcolor="#CCCCFF" class="TableHeadingColor">
 * <th align="left">Type</th>
 * <th align="left">Phases</th>
 * <th align="left">Description</th>
 * </tr>
 * <tr class="TableRowColor">
 * <td valign="top"><code>javax.faces.event.ValueChangeEvent</code></td>
 * <td valign="top" nowrap></td>
 * <td valign="top">The valueChange event is delivered when the value
                attribute is changed.</td>
 * </tr>
 * </table>
 */
@JSFComponent
(name = "h:selectOneMenu",
clazz = "javax.faces.component.html.HtmlSelectOneMenu",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlSelectOneMenuTag",
defaultRendererType = "javax.faces.Menu"
)
abstract class _HtmlSelectOneMenu extends UISelectOne implements
_AccesskeyProperty, _UniversalProperties, _Disabled_ReadonlyProperties,
_Focus_BlurProperties, _Change_SelectProperties, _EventProperties,
_StyleProperties, _TabindexProperty, _DisabledClass_EnabledClassProperties,
_LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectOne";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlSelectOneMenu";

}
