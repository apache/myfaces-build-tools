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

import org.apache.myfaces.custom.ajax.api.AjaxComponent;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.render.Renderer;
import java.io.IOException;

/**
 * This component is to be used in conjunction with a regular combo box or list box. 
 * When the selected value of the latter changes, it executes an ajax call to the 
 * specified method to refresh its contents based on the new selected value. 
 * 
 * @JSFComponent
 *   name = "s:ajaxChildComboBox"
 *   tagClass = "org.apache.myfaces.custom.ajaxchildcombobox.AjaxChildComboBoxTag"
 *   
 * @author Sharath Reddy
 */
public class AjaxChildComboBox extends HtmlSelectOneMenu implements AjaxComponent
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.AjaxChildComboBox";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.AjaxChildComboBox";
        
    private MethodBinding _ajaxSelectItemsMethod;
    //This is not a 'Parent' in terms of the component heirarchy; This is the component 
    //whose 'onchange' event triggers a refresh.
    private String _parentComboBox;

    public AjaxChildComboBox()
    {
        super();
        setRendererType(AjaxChildComboBox.DEFAULT_RENDERER_TYPE);
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, _ajaxSelectItemsMethod);
        values[2] = _parentComboBox;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _ajaxSelectItemsMethod = (MethodBinding) restoreAttachedState(context, values[1]);
        _parentComboBox = (String) values[2];
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

    public void setAjaxSelectItemsMethod(MethodBinding ajaxSelectItemsMethod)
    {
       _ajaxSelectItemsMethod = ajaxSelectItemsMethod;
    }

    public MethodBinding getAjaxSelectItemsMethod()
    {
        return _ajaxSelectItemsMethod;
    }
    
    public void setParentComboBox(String parentComboBox) 
    {
        this._parentComboBox = parentComboBox;
    }
    
    public String getParentComboBox() 
    {
        return this._parentComboBox;
    }
    
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