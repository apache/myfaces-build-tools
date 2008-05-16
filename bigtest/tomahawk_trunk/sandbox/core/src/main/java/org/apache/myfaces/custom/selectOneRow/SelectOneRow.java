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
package org.apache.myfaces.custom.selectOneRow;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 * 
 * @JSFComponent
 *   name = "s:selectOneRow"
 *   tagClass = "org.apache.myfaces.custom.selectOneRow.SelectOneRowTag"
 *   
 *
 */
public class SelectOneRow extends UIInput
{
    private String groupName;

    public static final String COMPONENT_TYPE = "org.apache.myfaces.SelectOneRow";

    public static final String COMPONENT_FAMILY = "org.apache.myfaces.SelectOneRow";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SelectOneRow";

    public SelectOneRow()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public void restoreState(FacesContext context, Object state)
    {

        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        groupName = (String) values[1];

    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = groupName;
        return values;
    }

}
