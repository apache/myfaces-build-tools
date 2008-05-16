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

package org.apache.myfaces.custom.scope;

import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Scope control
 * which does basically the same as
 * savestate but bypasses the serialization
 * and utilizes the session directly
 *
 * @JSFComponent
 *   name = "s:scope"
 *   tagClass = "org.apache.myfaces.custom.scope.ScopeTag"
 *   
 * @author Werner Punz werpu@gmx.at
 * @version $Revision$ $Date$
 */

public class UIScope extends UIParameter
{

    public static final String SCOPE_CONTAINER_KEY = "ScopeContainer";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Scope";
    public static final String COMPONENT_FAMILY = "javax.faces.Parameter";

    ScopeHolder holder = (ScopeHolder) ScopeUtils
            .getManagedBean(UIScope.SCOPE_CONTAINER_KEY);

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    /**
     * save state saves the scope value binding into the holder map
     */
    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[1];
        values[0] = super.saveState(context);

        ValueBinding vb = getValueBinding("value");
        holder.saveScopeEntry(this, context, vb);
        return ((Object) (values));
    }

    /**
     * in the restores state phase we get the binding and replace the one from
     * the system with the one from the scope map
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);

        /*
         * fetch the old scoped object and bind it to the value binding if it
         * exists, otherwise there will be no binding
         */
        ValueBinding vb = getValueBinding("value");
        Object oldVal = holder.restoreScopeEntry(vb.getExpressionString());
        if (oldVal == null)
            return;
        vb.setValue(context, oldVal);
        setValueBinding("value", vb);

    }

    /**
     * reset scope helper which allows to remove the scope from the system
     * within the backend context
     *
     * @param context
     */
    public void resetScope(FacesContext context)
    {
        ValueBinding vb = getValueBinding("value");
        vb.setValue(context, "");
        setValueBinding("value", vb);
        holder.resetScope(vb.getExpressionString());
    }
}
