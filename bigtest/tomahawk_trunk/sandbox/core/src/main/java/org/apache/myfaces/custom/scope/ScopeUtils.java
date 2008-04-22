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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;

/**
 * Small helper to cope with the
 * managed beans within the scope tag
 * handler
 *
 * Thanks Derek Shen to allow me to relicense
 * the Faces Utils code under Apache2
 * this class is derived from it
 *
 * http://www.javaworld.com/javaworld/jw-07-2004/jw-0719-jsf.html
 *
 * @author Werner Punz werpu@gmx.at
 *
 */
public class ScopeUtils
{
    private static final String EL_END = "}";
    private static final String EL_BEGIN = "#{";
    private static Log log = LogFactory.getLog(ScopeUtils.class);

    /**
     * el checker
     *
     * @param beanName
     * @return true if the bean is a managed bean
     */
    public static final boolean isEl(String beanName)
    {
        beanName = beanName.trim();
        return beanName.startsWith(EL_BEGIN) && beanName.endsWith(EL_END);
    }

    /**
     * returns the managed bean from the given bean name
     * @param beanName
     * @return Object managed bean
     */
    public static Object getManagedBean(String beanName)
    {
        if (isEl(beanName))
        {
            if(FacesContext.getCurrentInstance() == null)
                return null;
        
            return FacesContext.getCurrentInstance().getApplication()
                    .createValueBinding(beanName).getValue(
                            FacesContext.getCurrentInstance());
        }
        else
        {
            try
            {
                if(FacesContext.getCurrentInstance() == null)
                    return null;
                
                return FacesContext.getCurrentInstance().getApplication()
                    .createValueBinding(EL_BEGIN + beanName + EL_END).getValue(
                            FacesContext.getCurrentInstance());
            }
            catch(Exception ex)
            {
                log.info("ScopeContainer not found - has not been initialized.",ex);
                return null;
            }
        }
    }

}
