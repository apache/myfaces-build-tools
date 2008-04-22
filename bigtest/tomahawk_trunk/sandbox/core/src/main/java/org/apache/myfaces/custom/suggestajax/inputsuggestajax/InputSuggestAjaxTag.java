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
package org.apache.myfaces.custom.suggestajax.inputsuggestajax;


import org.apache.myfaces.custom.suggestajax.SuggestAjaxTag;

import javax.faces.component.UIComponent;
import javax.faces.el.MethodBinding;


/**
 * @author Gerald Muellan
 * @author Martin Marinschek
 * @version $Revision: $ $Date: $
 */

public class InputSuggestAjaxTag extends SuggestAjaxTag
{
    private String _itemLabelMethod;
    private String _autoComplete;

    public String getComponentType() {
        return InputSuggestAjax.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return InputSuggestAjax.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        _itemLabelMethod = null;
        _autoComplete = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        if (_itemLabelMethod != null)
        {
            if (isValueReference(_itemLabelMethod))
            {
                MethodBinding mb = getFacesContext().getApplication()
                                        .createMethodBinding(_itemLabelMethod, new Class[] {Object.class});

                ((InputSuggestAjax)component).setItemLabelMethod(mb);
            }
            else
            {
                throw new IllegalStateException("Invalid expression " + _itemLabelMethod);
            }
        }
        setBooleanProperty(component, "autoComplete", _autoComplete);
    }

    public void setItemLabelMethod(String itemLabelMethod)
    {
        _itemLabelMethod = itemLabelMethod;
    }

    public void setAutoComplete(String autoComplete)
    {
        _autoComplete = autoComplete;
    }
}
