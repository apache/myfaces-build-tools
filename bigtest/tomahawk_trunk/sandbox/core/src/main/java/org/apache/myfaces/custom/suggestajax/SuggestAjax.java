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

import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.custom.ajax.api.AjaxComponent;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import java.io.IOException;

/**
 * @author Gerald Muellan
 *         Date: 25.03.2006
 *         Time: 17:06:04
 */
public class SuggestAjax extends HtmlInputText implements AjaxComponent
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SuggestAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SuggestAjax";

    private MethodBinding _suggestedItemsMethod;

    private String _charset;

    private Integer _maxSuggestedItems;

    public SuggestAjax()
    {
        super();

        setRendererType(SuggestAjax.DEFAULT_RENDERER_TYPE);
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, _suggestedItemsMethod);
        values[2] = _maxSuggestedItems;
        values[3] = _charset;

        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _suggestedItemsMethod = (MethodBinding) restoreAttachedState(context, values[1]);
        _maxSuggestedItems = (Integer) values[2];
        _charset = (String) values[3];
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

     public void setSuggestedItemsMethod(MethodBinding suggestedItemsMethod)
    {
       _suggestedItemsMethod = suggestedItemsMethod;
    }

    public MethodBinding getSuggestedItemsMethod()
    {
        return _suggestedItemsMethod;
    }

    public Integer getMaxSuggestedItems() {
        if (_maxSuggestedItems != null)
            return _maxSuggestedItems;
        ValueBinding vb = getValueBinding("maxSuggestedItems");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setMaxSuggestedItems(Integer suggestedItems) {
        _maxSuggestedItems = suggestedItems;
    }
    
    public String getCharset() {
        return _charset;
    }
    
    public void setCharset(String charset) {
        _charset = charset;
    }

}
