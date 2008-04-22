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

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

/**
 * A class very similar to savestate it allows to defined scopes for variables
 * between session and request, so that you can traverse parameters over forms
 * within sessions but you do not have the full session lifecycle the main
 * advantage over SaveState for this one is, that you do not have to make the
 * objects serializable, the main disadvantage is, scopes need session ram for the
 * time of existence
 *
 * @author Werner Punz werpu@gmx.at
 *
 * @version $Revision$ $Date$
 */

public class ScopeTag extends UIComponentTagBase
{

    /**
     * @return the component type
     */
    public String getComponentType()
    {
        return UIScope.COMPONENT_TYPE;
    }

    /**
     * standard setProperties
     */
    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
    }

    /**
     * standard release
     */
    public void release()
    {
        super.release();
    }

    /**
     * we do not render anything, hence the renderer type results in a plain
     * null
     */
    public String getRendererType()
    {
        return null;
    }

}
