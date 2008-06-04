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
package org.apache.myfaces.custom.ajaxchildcombobox;

import java.io.IOException;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.myfaces.component.html.ext.HtmlSelectOneMenu;
import org.apache.myfaces.custom.ajax.api.AjaxComponent;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;

/**
 * Refreshes contents through an ajax call when the parent combo box's value is changed.
 * 
 * This component is to be used in conjunction with a regular combo box or list box. 
 * When the selected value of the latter changes, it executes an ajax call to the 
 * specified method to refresh its contents based on the new selected value. 
 * 
 * @JSFComponent
 *   name = "s:ajaxChildComboBox"
 *   class = "org.apache.myfaces.custom.ajaxchildcombobox.AjaxChildComboBox"
 *   superClass = "org.apache.myfaces.custom.ajaxchildcombobox.AbstractAjaxChildComboBox"
 *   tagClass = "org.apache.myfaces.custom.ajaxchildcombobox.AjaxChildComboBoxTag"
 *   
 * @author Sharath Reddy
 */
public abstract class AbstractAjaxChildComboBox extends HtmlSelectOneMenu implements AjaxComponent
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.AjaxChildComboBox";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.AjaxChildComboBox";
        
    public AbstractAjaxChildComboBox()
    {
        super();
        setRendererType(AbstractAjaxChildComboBox.DEFAULT_RENDERER_TYPE);
    }
    
    public void encodeAjax(FacesContext context)
            throws IOException
    {
        
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;
        Renderer renderer = getRenderer(context);
        if (renderer != null && renderer instanceof AjaxRenderer)
        {
            ((AjaxRenderer) renderer).encodeAjax(context, this);
        }
    }

    public void decodeAjax(FacesContext context)
    {
        //Do Nothing
    }

    /**
     * Method to call via ajax to reload the combo box
     * 
     * @JSFProperty
     *   methodSignature = "java.lang.String"
     *   returnSignature = "javax.faces.model.SelectItem []"
     *   stateHolder = "true"    
     */
    public abstract MethodExpression getAjaxSelectItemsMethod();

    //
    
    /**
     * id of the parent combo box
     * 
     * This is not a 'Parent' in terms of the component heirarchy; 
     * This is the component whose 'onchange' event triggers a refresh.
     *  
     * @JSFProperty
     *   literalOnly="true"
     */
    public abstract String getParentComboBox();
    
    
    /**
     * We cannot verify that the result of converting the newly submitted value 
     * is <i>equal</i> to the value property of one of the child SelectItem
     * objects. This is because the contents of the child combo box could have 
     * been reloaded by a change in the parent combo box. 
     * 
     * @see javax.faces.component.UIInput#validateValue(javax.faces.context.FacesContext, java.lang.Object)
     */
    protected void validateValue(FacesContext context, Object value)
    {
        return;
      // selected value must match to one of the available options
      /*  if (!_SelectItemsUtil.matchValue(context, value, new _SelectItemsIterator(this), converter))
        {
            _MessageUtils.addErrorMessage(context, this, INVALID_MESSAGE_ID,
                            new Object[] {getId()});
            setValid(false);
      }*/
    }
}