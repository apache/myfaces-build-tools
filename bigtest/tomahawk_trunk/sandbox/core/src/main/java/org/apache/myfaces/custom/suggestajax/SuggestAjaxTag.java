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
package org.apache.myfaces.custom.suggestajax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.suggestajax.inputsuggestajax.InputSuggestAjax;
import org.apache.myfaces.generated.taglib.html.ext.HtmlInputTextTag;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

/**
 * @author Gerald Muellan
 *         Date: 25.03.2006
 *         Time: 17:05:58
 */
public class SuggestAjaxTag extends HtmlInputTextTag
{
    private final static Class[] DEFAULT_SIGNATURE = new Class[]{String.class};
    private final static Class[] SUGGEST_ITEM_SIGNATURE = new Class[]{String.class, Integer.class};

    private static Log log = LogFactory.getLog(SuggestAjaxTag.class);

    private String _suggestedItemsMethod;
    private String _maxSuggestedItems;

    private String _charset;

    public String getComponentType() {
        return InputSuggestAjax.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return InputSuggestAjax.DEFAULT_RENDERER_TYPE;
    }

    public void release() {

        super.release();

       _suggestedItemsMethod = null;
       _maxSuggestedItems = null;
       _charset = null;
       
    }

    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        
        FacesContext context = getFacesContext();

        SuggestAjax comp = (SuggestAjax) component;
        
        if (_maxSuggestedItems != null)
        {
            if (isValueReference(_maxSuggestedItems))
            {
                ValueBinding vb = context.getApplication().createValueBinding(_maxSuggestedItems);
                comp.setValueBinding("maxSuggestedItems", vb);
            }
            else
            {
                comp.getAttributes().put("maxSuggestedItems", Integer.valueOf(_maxSuggestedItems));
            }
        } 

        if (_charset != null)
        {
            if (isValueReference(_charset))
            {
                ValueBinding vb = context.getApplication().createValueBinding(_charset);
                comp.setValueBinding("charset", vb);
            }
            else
            {
                comp.getAttributes().put("charset", _charset);
            }
        } 
        if (_javascriptLocation != null)
        {
            if (isValueReference(_javascriptLocation))
            {
                ValueBinding vb = context.getApplication().createValueBinding(_javascriptLocation);
                comp.setValueBinding("javascriptLocation", vb);
            }
            else
            {
                comp.getAttributes().put("javascriptLocation", _javascriptLocation);
            }
        } 
        if (_imageLocation != null)
        {
            if (isValueReference(_imageLocation))
            {
                ValueBinding vb = context.getApplication().createValueBinding(_imageLocation);
                comp.setValueBinding("imageLocation", vb);
            }
            else
            {
                comp.getAttributes().put("imageLocation", _imageLocation);
            }
        } 
        if (_styleLocation != null)
        {
            if (isValueReference(_styleLocation))
            {
                ValueBinding vb = context.getApplication().createValueBinding(_styleLocation);
                comp.setValueBinding("styleLocation", vb);
            }
            else
            {
                comp.getAttributes().put("styleLocation", _styleLocation);
            }
        } 

        SuggestAjaxTag.setSuggestedItemsMethodProperty(getFacesContext(),component,_suggestedItemsMethod);
    }

    public static void setSuggestedItemsMethodProperty(FacesContext context,
                                                       UIComponent component,
                                                       String suggestedItemsMethod)
    {
        if (suggestedItemsMethod != null)
        {
            if (!(component instanceof SuggestAjax))
            {
                throw new IllegalArgumentException("Component " + component.getClientId(context) + " is no InputSuggestAjax");
            }
            if (isValueReference(suggestedItemsMethod))
            {
                if (((SuggestAjax)component).getMaxSuggestedItems()!=null) {
                    MethodBinding mb = context.getApplication().createMethodBinding(suggestedItemsMethod, SuggestAjaxTag.SUGGEST_ITEM_SIGNATURE);
                    ((SuggestAjax)component).setSuggestedItemsMethod(mb);
                } else {
                    MethodBinding mb = context.getApplication().createMethodBinding(suggestedItemsMethod, SuggestAjaxTag.DEFAULT_SIGNATURE);
                    ((SuggestAjax)component).setSuggestedItemsMethod(mb);
                }
            }
            else
            {
                SuggestAjaxTag.log.error("Invalid expression " + suggestedItemsMethod);
            }
        }
    }

    // setter methodes to populate the components properites

    public void setSuggestedItemsMethod(String suggestedItemsMethod)
    {
        _suggestedItemsMethod = suggestedItemsMethod;
    }

    public void setMaxSuggestedItems(String maxSuggestedItems) {
        _maxSuggestedItems = (maxSuggestedItems);
    }
    
    public void setCharset(String charset) {
        _charset = charset;
    }

    private String _javascriptLocation;
    
    public void setJavascriptLocation(String javascriptLocation)
    {
        _javascriptLocation = javascriptLocation;
    }
 
    private String _imageLocation;
    
    public void setImageLocation(String imageLocation)
    {
        _imageLocation = imageLocation;
    }
 
    private String _styleLocation;
    
    public void setStyleLocation(String styleLocation)
    {
        _styleLocation = styleLocation;
    }
    
}
