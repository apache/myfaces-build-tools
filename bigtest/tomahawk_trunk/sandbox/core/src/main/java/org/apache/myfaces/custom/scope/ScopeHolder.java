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

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * A central sessions holder, which stores
 * the triggered scopes and also does
 * the scope synching at the end
 *
 * @author Werner Punz werpu@gmx.at
 *
 */
public class ScopeHolder implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 2340601728913516991L;
    /*dunno of a log(n) map is suitable here, after all we have only a handful of scopes*/
    Map scopeMap = Collections.synchronizedMap(new TreeMap());
    Map oldScopes = Collections.synchronizedMap(new TreeMap());

    /**
     * fetches an old scope from the scope map
     *
     * @param key
     * @return Object old scope
     */
    public Object restoreScopeEntry(String key)
    {
        Object theentry = oldScopes.get(key);
        return theentry;
    }

    /**
     * note we use the global session internally not the portlet one, if you use
     * portlets please-keep that in mind with your scope names
     *
     * @param parent
     *            the parent component
     * @param context
     *            the current faces context
     * @param scopeBindingValue
     *            the scope value binding
     */
    public void saveScopeEntry(UIComponent parent, FacesContext context,
            ValueBinding scopeBindingValue)
    {

        scopeMap.put(scopeBindingValue.getExpressionString(), scopeBindingValue
                .getValue(context));
    }

    /**
     * the rendering is done we need to dump the unused scopes
     *
     */
    public void pageRefresh()
    {
        oldScopes = scopeMap;
        scopeMap = Collections.synchronizedMap(new TreeMap());
    }

    public void resetScopes()
    {
        oldScopes = Collections.synchronizedMap(new TreeMap());
        scopeMap = Collections.synchronizedMap(new TreeMap());
    }

    public void resetScope(String key)
    {
        oldScopes.remove(key);
        scopeMap.remove(key);
    }
}
