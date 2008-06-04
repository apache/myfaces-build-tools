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

import java.util.List;
import java.util.ArrayList;

/**
 * @author Martin Marinschek (latest modification by $Author: matzew $)
 * @version $Revision: 167718 $ $Date: 2005-03-24 17:47:11 +0100 (Do, 24 Mär 2005) $
 */
public class AccessedBeans
{
    private List beanList;

    public List getBeanList()
    {
        if(beanList == null)
            beanList = new ArrayList();

        return beanList;
    }

    public void setBeanList(List beanList)
    {
        this.beanList = beanList;
    }

    public void addBean(String name, Object resolvedBean)
    {
        List li = getBeanList();

        for (int i = 0; i < li.size(); i++)
        {
            AccessedBean accessedBean = (AccessedBean) li.get(i);
            if(accessedBean.getName().equals(name))
                return;
        }

        AccessedBean bean = new AccessedBean();
        bean.setName(name);
        bean.setClazz(resolvedBean.getClass().getName());

        li.add(bean);
    }

    public static class AccessedBean
    {
        private String name;
        private String clazz;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getClazz()
        {
            return clazz;
        }

        public void setClazz(String clazz)
        {
            this.clazz = clazz;
        }
    }
}
