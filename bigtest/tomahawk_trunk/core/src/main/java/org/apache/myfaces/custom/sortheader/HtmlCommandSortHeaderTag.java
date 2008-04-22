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
package org.apache.myfaces.custom.sortheader;


import org.apache.myfaces.taglib.html.ext.HtmlCommandLinkTag;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCommandSortHeaderTag
        extends HtmlCommandLinkTag
{
    //private static final Log log = LogFactory.getLog(HtmlCommandSortHeaderTag.class);

    public String getComponentType()
    {
        return HtmlCommandSortHeader.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlCommandSortHeader.DEFAULT_RENDERER_TYPE;
    }

    private String _columnName;
    private String _arrow;
    private String _propertyName;
    private boolean _immediateSet;

    public void release() {
        super.release();

        _columnName=null;
        _arrow=null;
        _propertyName=null;
        _immediateSet=true;

    }

    // User Role support --> already handled by HtmlPanelGroupTag


    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "columnName", _columnName);
        setBooleanProperty(component, "arrow", _arrow);
        setStringProperty(component, "propertyName", _propertyName);

        if (!_immediateSet)
        {
            //Default of immediate is true (contrary to normal command links)
            setBooleanProperty(component, "immediate", "true");
        }
    }

    public void setColumnName(String columnName)
    {
        _columnName = columnName;
    }
    
    public void setPropertyName(String propertyName)
    {
        _propertyName = propertyName;
    }

    public void setArrow(String arrow)
    {
        _arrow = arrow;
    }

    public void setImmediate(String immediate)
    {
        super.setImmediate(immediate);
        _immediateSet = true;
    }
}
