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

import org.apache.myfaces.custom.suggestajax.SuggestAjax;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import java.io.IOException;

/**
 * @author Gerald Muellan (latest modification by $Author: svieujot $)
 * @author Martin Marinschek
 *
 * @version $Revision: 169662 $ $Date: 2005-05-11 19:57:24 +0200 (Wed, 11 May 2005) $
 */

public class InputSuggestAjax extends SuggestAjax
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.InputSuggestAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.InputSuggestAjax";

    private MethodBinding _itemLabelMethod;

    private Boolean _autoComplete = new Boolean(true);

    public InputSuggestAjax()
    {
        super();

        setRendererType(DEFAULT_RENDERER_TYPE);

		// it makes absolutely no sense to have two autocompletes active at the same time
		// ensure to disable the browser one - this has nothing to do with the
		// autocomplete attribute this component provides
		setAutocomplete("off"); // NON-NLS
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, _itemLabelMethod);
        values[2] = _autoComplete;

        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _itemLabelMethod = (MethodBinding) restoreAttachedState(context, values[1]);
        _autoComplete = (Boolean) values[2];
    }

    public void encodeChildren(FacesContext context) throws IOException
    {
        super.encodeChildren(context);
    }

    public Boolean getAutoComplete()
    {
         if (_autoComplete != null)
        {
            return _autoComplete;
        }
        ValueBinding vb = getValueBinding("autoComplete");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public void setAutoComplete(Boolean autoComplete)
    {
        _autoComplete = autoComplete;
    }

    public MethodBinding getItemLabelMethod()
    {
        return _itemLabelMethod;
    }

    public void setItemLabelMethod(MethodBinding itemLabelMethod)
    {
        _itemLabelMethod = itemLabelMethod;
    }
}
