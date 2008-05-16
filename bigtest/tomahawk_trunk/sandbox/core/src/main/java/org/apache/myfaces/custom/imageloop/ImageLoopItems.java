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
package org.apache.myfaces.custom.imageloop;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Image loop items.
 * 
 * @JSFComponent
 *   name = "s:imageLoopItems"
 *   tagClass = "org.apache.myfaces.custom.imageloop.ImageLoopItemsTag"
 *   
 * @author Felix Röthenbacher (latest modification by $Author:$)
 * @version $Revision:$ $Date:$
 */
public class ImageLoopItems extends UIComponentBase {
    
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.ImageLoopItems";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.ImageLoopItems";
    
    // Value binding constants.
    private static final String VB_VALUE = "value";

    private Object _value;

    public String getFamily() {
        return COMPONENT_FAMILY;
    }
    
    public void setValue(Object value)
    {
        _value = value;
    }

    public Object getValue()
    {
        if (_value != null) return _value;
        ValueBinding vb = getValueBinding(VB_VALUE);
        return vb != null ? vb.getValue(getFacesContext()) : null;
    }


    public Object saveState(FacesContext facesContext)
    {
        Object values[] = new Object[2];
        values[0] = super.saveState(facesContext);
        values[1] = _value;
        return values;
    }

    public void restoreState(FacesContext facesContext, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(facesContext, values[0]);
        _value = values[1];
    }
}
