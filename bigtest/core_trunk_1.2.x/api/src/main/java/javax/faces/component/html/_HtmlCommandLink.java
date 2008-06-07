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

import javax.faces.component.UICommand;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 *
 * This tag renders as an HTML a element.
 * 
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
 * <td valign="top"><code>javax.faces.event.ActionEvent</code></td>
 * <td valign="top" nowrap>Invoke Application<br>Apply Request Values</td>
 * <td valign="top">Event delivered when the "action" of the component has been
invoked;  for example, by clicking on a button.  The action may result
in page navigation.</td>
 * </tr>
 * </table>
 */
@JSFComponent
(name = "h:commandLink",
clazz = "javax.faces.component.html.HtmlCommandLink",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlCommandLinkTag",
defaultRendererType = "javax.faces.Link"
)
abstract class _HtmlCommandLink extends UICommand
    implements _EventProperties, _UniversalProperties, _StyleProperties,
    _Focus_BlurProperties, _AccesskeyProperty, _TabindexProperty,
    _LinkProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Command";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlCommandLink";

  /**
   * When true, this element cannot receive focus.
   *
   * @return  the new disabled value
   */
  @JSFProperty
  (defaultValue = "false")
  public abstract boolean isDisabled();

}
