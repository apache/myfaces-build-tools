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
package org.apache.myfaces.custom.inputHtml;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class InputHtmlTag extends UIComponentTagBase {

    private String style;
    private String styleClass;

    private String fallback;
    private String type;

    private String allowEditSource;
    private String allowExternalLinks;
    private String addKupuLogo;

    private String showAllToolBoxes;
    private String showPropertiesToolBox;
    private String showLinksToolBox;
    private String showImagesToolBox;
    private String showTablesToolBox;
    private String showCleanupExpressionsToolBox;
    private String showDebugToolBox;

    private String enabledOnUserRole;
    private String visibleOnUserRole;

    private String displayValueOnly;
    private String displayValueOnlyStyle;
    private String displayValueOnlyStyleClass;

    private String immediate;
    private String required;
    private String validator;
    private String valueChangeListener;

    public void release() {
        super.release();
        style=null;
        styleClass=null;
        fallback=null;
        type=null;
        allowEditSource=null;
        allowExternalLinks=null;
        addKupuLogo=null;

        showAllToolBoxes=null;
        showPropertiesToolBox=null;
        showLinksToolBox=null;
        showImagesToolBox=null;
        showTablesToolBox=null;
        showCleanupExpressionsToolBox=null;
        showDebugToolBox=null;

        enabledOnUserRole=null;
        visibleOnUserRole=null;

        displayValueOnly=null;
        displayValueOnlyStyle=null;
        displayValueOnlyStyleClass=null;

        immediate=null;
        required=null;
        validator=null;
        valueChangeListener=null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setStringProperty(component, "style", style);
        setStringProperty(component, "styleClass", styleClass);

        setStringProperty(component, "fallback", fallback);
        setStringProperty(component, "type", type);

        setBooleanProperty(component, "allowEditSource", allowEditSource);
        setBooleanProperty(component, "allowExternalLinks", allowExternalLinks);
        setBooleanProperty(component, "addKupuLogo", addKupuLogo);

        setBooleanProperty(component, "showAllToolBoxes", showAllToolBoxes);
        setBooleanProperty(component, "showPropertiesToolBox", showPropertiesToolBox);
        setBooleanProperty(component, "showLinksToolBox", showLinksToolBox);
        setBooleanProperty(component, "showImagesToolBox", showImagesToolBox);
        setBooleanProperty(component, "showTablesToolBox", showTablesToolBox);
        setBooleanProperty(component, "showCleanupExpressionsToolBox", showCleanupExpressionsToolBox);
        setBooleanProperty(component, "showDebugToolBox", showDebugToolBox);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, visibleOnUserRole);

        setBooleanProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_ATTR, displayValueOnly);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_ATTR, displayValueOnlyStyle);
        setStringProperty(component, DisplayValueOnlyCapable.DISPLAY_VALUE_ONLY_STYLE_CLASS_ATTR, displayValueOnlyStyleClass);

        setBooleanProperty(component, JSFAttr.IMMEDIATE_ATTR, immediate);
        setBooleanProperty(component, JSFAttr.REQUIRED_ATTR, required);
        setValidatorProperty(component, validator);
        setValueChangedListenerProperty(component, valueChangeListener);
    }

    public String getComponentType() {
        return InputHtml.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return InputHtml.DEFAULT_RENDERER_TYPE;
    }

    public void setStyle(String style){
        this.style = style;
    }

    public void setStyleClass(String styleClass){
        this.styleClass = styleClass;
    }

    public void setFallback(String fallback){
        this.fallback = fallback;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setAllowEditSource(String allowEditSource){
        this.allowEditSource = allowEditSource;
    }

    public void setAllowExternalLinks(String allowExternalLinks){
        this.allowExternalLinks = allowExternalLinks;
    }

    public void setAddKupuLogo(String addKupuLogo){
        this.addKupuLogo = addKupuLogo;
    }

    public void setShowAllToolBoxes(String showAllToolBoxes){
        this.showAllToolBoxes = showAllToolBoxes;
    }

    public void setShowPropertiesToolBox(String showPropertiesToolBox){
        this.showPropertiesToolBox = showPropertiesToolBox;
    }

    public void setShowLinksToolBox(String showLinksToolBox){
        this.showLinksToolBox = showLinksToolBox;
    }

    public void setShowImagesToolBox(String showImagesToolBox){
        this.showImagesToolBox = showImagesToolBox;
    }

    public void setShowTablesToolBox(String showTablesToolBox){
        this.showTablesToolBox = showTablesToolBox;
    }

    public void setShowCleanupExpressionsToolBox(String showCleanupExpressionsToolBox){
        this.showCleanupExpressionsToolBox = showCleanupExpressionsToolBox;
    }

    public void setShowDebugToolBox(String showDebugToolBox){
        this.showDebugToolBox = showDebugToolBox;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole){
        this.enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole){
        this.visibleOnUserRole = visibleOnUserRole;
    }

    public void setDisplayValueOnly(String displayValueOnly){
        this.displayValueOnly = displayValueOnly;
    }

    public void setDisplayValueOnlyStyle(String displayValueOnlyStyle){
        this.displayValueOnlyStyle = displayValueOnlyStyle;
    }

    public void setDisplayValueOnlyStyleClass(String displayValueOnlyStyleClass){
        this.displayValueOnlyStyleClass = displayValueOnlyStyleClass;
    }

    public void setImmediate(String immediate){
        this.immediate = immediate;
    }

    public void setRequired(String required){
        this.required = required;
    }

    public void setValidator(String validator){
        this.validator = validator;
    }

    public void setValueChangeListener(String valueChangeListener){
        this.valueChangeListener = valueChangeListener;
    }
}