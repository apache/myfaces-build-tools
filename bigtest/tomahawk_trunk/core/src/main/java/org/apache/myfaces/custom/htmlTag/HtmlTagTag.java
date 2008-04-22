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
package org.apache.myfaces.custom.htmlTag;

import javax.faces.component.UIComponent;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlOutputTextTagBase;
/**
 * @author bdudney (latest modification by $Author$)
 * @version $Revision$ $Date: 2005-05-11 11:47:12 -0400 (Wed, 11 May 2005) $
 */
public class HtmlTagTag extends HtmlOutputTextTagBase {
  private String _style = null;
  private String _styleClass = null;
  private String _enabledOnUserRole;
  private String _visibleOnUserRole;

  public String getComponentType() {
    return HtmlTag.COMPONENT_TYPE;
  }

  public String getRendererType() {
    return HtmlTagRenderer.RENDERER_TYPE;
  }

  public void release() {
    super.release();
    this._style = null;
    this._styleClass = null;
    this._enabledOnUserRole=null;
    this._visibleOnUserRole=null;
  }

  /**
   * overrides setProperties() form UIComponentTag.
   */
  protected void setProperties(UIComponent component) {
    super.setProperties(component);
    setStringProperty(component, "style", _style);
    setStringProperty(component, "styleClass", _styleClass);
    setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
    setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
  }

  public void setStyle(String style) {
    this._style = style;
  }

  public void setStyleClass(String styleClass) {
    this._styleClass = styleClass;
  }
  
  public void setEnabledOnUserRole(String enabledOnUserRole)
  {
      _enabledOnUserRole = enabledOnUserRole;
  }

  public void setVisibleOnUserRole(String visibleOnUserRole)
  {
      _visibleOnUserRole = visibleOnUserRole;
  }
}