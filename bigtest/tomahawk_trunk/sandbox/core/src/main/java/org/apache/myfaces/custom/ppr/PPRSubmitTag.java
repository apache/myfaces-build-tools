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
package org.apache.myfaces.custom.ppr;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentBodyTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Thomas Spiegl
 */
public class PPRSubmitTag extends UIComponentBodyTagBase
{
    private String processComponentIds;

    public String getComponentType()
    {
        return PPRSubmit.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return PPRSubmit.DEFAULT_RENDERER_TYPE;
    }

    public void release()
    {
        super.release();

        processComponentIds=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "processComponentIds", processComponentIds);
    }

    public String getProcessComponentIds()
    {
        return processComponentIds;
    }

    public void setProcessComponentIds(String processComponentIds)
    {
        this.processComponentIds = processComponentIds;
    }
}