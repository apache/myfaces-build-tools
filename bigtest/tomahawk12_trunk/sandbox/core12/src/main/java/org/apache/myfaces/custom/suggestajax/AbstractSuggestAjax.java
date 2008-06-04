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

import java.io.IOException;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.myfaces.component.LocationAware;
import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.custom.ajax.api.AjaxComponent;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;

/**
 * 
 * @JSFComponent
 *   configExcluded = "true"
 *   class = "org.apache.myfaces.custom.suggestajax.SuggestAjax"
 *   superClass = "org.apache.myfaces.custom.suggestajax.AbstractSuggestAjax"
 *   tagClass = "org.apache.myfaces.custom.suggestajax.SuggestAjaxTag"
 *   tagSuperclass = "org.apache.myfaces.custom.suggestajax.AbstractSuggestAjaxTag"
 * @author Gerald Muellan
 *         Date: 25.03.2006
 *         Time: 17:06:04
 */
public abstract class AbstractSuggestAjax extends HtmlInputText 
    implements AjaxComponent, LocationAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SuggestAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SuggestAjax";

    private MethodExpression _suggestedItemsMethod;

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, _suggestedItemsMethod);

        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _suggestedItemsMethod = (MethodExpression) restoreAttachedState(context, values[1]);
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

    }

    public void encodeChildren(FacesContext context) throws IOException
    {
        super.encodeChildren(context);
    }

     public void setSuggestedItemsMethod(MethodExpression suggestedItemsMethod)
    {
       _suggestedItemsMethod = suggestedItemsMethod;
    }

    /**
     * Reference to the method which returns the suggested items
     * 
     * @JSFProperty
     *   inheritedTag = "true"
     * @return
     */
    public MethodExpression getSuggestedItemsMethod()
    {
        return _suggestedItemsMethod;
    }

    /**
     * optional attribute to identify the max size of suggested Values. 
     * If specified in tableSuggestAjax, paginator functionality is used.
     * 
     * @JSFProperty
     *   inheritedTag = "true"
     * @return
     */
    public abstract Integer getMaxSuggestedItems();

    
    /**
     * Force the charset of the Response
     * 
     * @JSFProperty
     *   literalOnly = "true"
     * @return
     */
    public abstract String getCharset();

    /**
     *  An alternate location to find javascript resources. 
     *  If no values is specified, javascript will be loaded 
     *  from the resources directory using AddResource and 
     *  ExtensionsFilter.
     * 
     * @JSFProperty 
     */
    public abstract String getJavascriptLocation();
    
    /**
     * An alternate location to find image resources. If no 
     * values is specified, images will be loaded from the 
     * resources directory using AddResource and ExtensionsFilter.
     * 
     * @JSFProperty 
     */
    public abstract String getImageLocation();
    
    /**
     * An alternate location to find stylesheet resources. If no 
     * values is specified, stylesheets will be loaded from the 
     * resources directory using AddResource and ExtensionsFilter.
     * 
     * @JSFProperty 
     */
    public abstract String getStyleLocation();

    
}
