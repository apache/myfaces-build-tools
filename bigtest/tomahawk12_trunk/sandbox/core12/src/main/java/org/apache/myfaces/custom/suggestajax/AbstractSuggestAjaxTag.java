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

import java.util.Collection;
import java.util.List;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.generated.taglib.html.ext.HtmlInputTextTag;

/**
 * @author Gerald Muellan
 *         Date: 25.03.2006
 *         Time: 17:05:58
 */
public class AbstractSuggestAjaxTag extends HtmlInputTextTag
{
    private final static Class[] DEFAULT_SIGNATURE = new Class[]{String.class};
    private final static Class[] SUGGEST_ITEM_SIGNATURE = new Class[]{String.class, Integer.class};

    private static Log log = LogFactory.getLog(AbstractSuggestAjaxTag.class);

    private javax.el.ValueExpression _suggestedItemsMethod;
    
    private ValueExpression _maxSuggestedItems;
    
    public void setMaxSuggestedItems(ValueExpression maxSuggestedItems)
    {
        _maxSuggestedItems = maxSuggestedItems;
    }

    public void release() {

        super.release();
        _maxSuggestedItems = null;
       _suggestedItemsMethod = null;       
    }

    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        
        FacesContext context = getFacesContext();

        SuggestAjax comp = (SuggestAjax) component;
        
        //This part is a special hack for set the method for suggestedItemsMethod
        //First maxSuggestedItems should be resolved and put on component, for
        //make this value available when setSuggestedItemsMethodProperty is
        //called. Note on AbstractSuggestAjax the field maxSuggestedItems
        //the property inheritedTag = "true". In normal generation this should be defined
        //on SuggestAjaxTag.
        if (_maxSuggestedItems != null)
        {
            comp.setValueExpression("maxSuggestedItems", _maxSuggestedItems);
        }
           
        if (!_suggestedItemsMethod.isLiteralText())
        {
            AbstractSuggestAjaxTag.setSuggestedItemsMethodProperty(getFacesContext(),component,_suggestedItemsMethod.getExpressionString());
        }
        else
        {
            AbstractSuggestAjaxTag.log.error("Invalid expression " + _suggestedItemsMethod.getExpressionString());
        }
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
            if (((SuggestAjax) component).getMaxSuggestedItems() != null)
            {
                MethodExpression mb = context.getApplication()
                        .getExpressionFactory().createMethodExpression(
                                context.getELContext(), suggestedItemsMethod,
                                Collection.class,
                                SUGGEST_ITEM_SIGNATURE);
                ((SuggestAjax) component).setSuggestedItemsMethod(mb);
            }
            else
            {
                MethodExpression mb = context.getApplication()
                        .getExpressionFactory().createMethodExpression(
                                context.getELContext(), suggestedItemsMethod,
                                List.class, DEFAULT_SIGNATURE);
                ((SuggestAjax) component).setSuggestedItemsMethod(mb);
            }
        }
    }

    // setter methodes to populate the components properites

    public void setSuggestedItemsMethod(javax.el.ValueExpression suggestedItemsMethod)
    {
        _suggestedItemsMethod = suggestedItemsMethod;
    }
    
}
