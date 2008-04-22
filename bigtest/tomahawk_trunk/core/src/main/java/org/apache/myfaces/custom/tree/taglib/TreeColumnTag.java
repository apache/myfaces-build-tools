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
package org.apache.myfaces.custom.tree.taglib;

import org.apache.myfaces.custom.tree.HtmlTreeColumn;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlComponentBodyTagBase;


/**
 * <p>
 * Tag used to render the column containing the tree.
 * </p>
 * 
 * @author <a href="mailto:dlestrat@apache.org">David Le Strat</a>
 */
public class TreeColumnTag extends HtmlComponentBodyTagBase
{

    /**
     * @see javax.faces.webapp.UIComponentTag#getComponentType()
     */
    public String getComponentType()
    {
        return HtmlTreeColumn.COMPONENT_TYPE;
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#getRendererType()
     */
    public String getRendererType()
    {
        return null;
    }

    // UIComponent attributes --> already implemented in UIComponentTagBase
}
