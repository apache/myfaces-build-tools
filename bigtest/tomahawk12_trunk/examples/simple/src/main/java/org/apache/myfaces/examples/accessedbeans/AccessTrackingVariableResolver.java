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
package org.apache.myfaces.examples.accessedbeans;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;
import java.util.Map;

/**
 * @author Martin Marinschek (latest modification by $Author: matzew $)
 * @version $Revision: 167718 $ $Date: 2005-03-24 17:47:11 +0100 (Do, 24 Mär 2005) $
 */
public class AccessTrackingVariableResolver extends VariableResolver
{
    private VariableResolver delegate;

    /**Implementing delegate-pattern
     *
     * @param delegate Delegating to this instance.
     */
    public AccessTrackingVariableResolver(VariableResolver delegate)
    {
        this.delegate = delegate;
    }

    // METHODS
    public Object resolveVariable(FacesContext facesContext, String name) throws EvaluationException
    {
        Object resolvedBean = delegate.resolveVariable(facesContext, name);

        if(!(resolvedBean instanceof AccessedBeans) && resolvedBean!=null)
        {
            if(!resolvedBean.getClass().getName().startsWith("java.lang.") && !(resolvedBean instanceof Map))
            {
                ((AccessedBeans) facesContext.getApplication().
                        getVariableResolver().resolveVariable(facesContext,"accessedBeans")).addBean(name, resolvedBean);
            }
        }

        return resolvedBean;
    }
}
