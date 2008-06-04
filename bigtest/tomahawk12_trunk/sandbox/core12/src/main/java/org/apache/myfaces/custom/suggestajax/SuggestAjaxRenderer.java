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

import org.apache.myfaces.custom.ajax.api.AjaxDecodePhaseListener;
import org.apache.myfaces.custom.ajax.api.AjaxSuggestRenderer;
import org.apache.myfaces.renderkit.html.ext.HtmlTextRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.el.MethodExpression;
import javax.el.MethodNotFoundException;
import java.util.Collection;
import java.util.List;

/**
 * @author Gerald Muellan
 * @author Martin Marinschek
 * @version $Revision$ $Date$
 */
public class SuggestAjaxRenderer extends HtmlTextRenderer implements AjaxSuggestRenderer
{
    public static final int DEFAULT_MAX_SUGGESTED_ITEMS = 200;

    public Collection getSuggestedItems(FacesContext context, UIComponent uiComponent)
    {
        RendererUtils.checkParamValidity(context, uiComponent, SuggestAjax.class);

        SuggestAjax suggestAjax = (SuggestAjax) uiComponent;

        //getting the suggested items
        MethodExpression mb = suggestAjax.getSuggestedItemsMethod();
        Integer maxSuggestedCount = suggestAjax.getMaxSuggestedItems();

        Collection suggesteds;

        if (maxSuggestedCount != null
                && maxSuggestedCount.intValue() > 0)
        {
            try
            {
                suggesteds = (Collection) mb.invoke(context.getELContext(),new Object[]{
                        AjaxDecodePhaseListener.getValueForComponent(context, uiComponent),
                        maxSuggestedCount});
            }
            catch(MethodNotFoundException dummy)
            {
                suggesteds = (List) mb.invoke(context.getELContext(),new Object[]{
                        AjaxDecodePhaseListener.getValueForComponent(context, uiComponent)});
            }
        }
        else
        {
            try
            {
                suggesteds = (List) mb.invoke(context.getELContext(),new Object[]{
                        AjaxDecodePhaseListener.getValueForComponent(context, uiComponent)});
            }
            catch(MethodNotFoundException dummy)
            {
                suggesteds = (Collection) mb.invoke(context.getELContext(),new Object[]{
                        AjaxDecodePhaseListener.getValueForComponent(context, uiComponent),
                        new Integer( DEFAULT_MAX_SUGGESTED_ITEMS )});
            }
        }

        return suggesteds;
    }

    public void decode(FacesContext facesContext, UIComponent component)
    {
        super.decode(facesContext, component);
    }

     protected String addQueryString(String url, String queryString)
     {    	
   	    return url + (url.indexOf("?") > 0 ? "&" : "?") + queryString;
     }
}
