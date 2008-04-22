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
package org.apache.myfaces.custom.checkbox;

import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCheckbox
    extends UIComponentBase
{
    //private static final Log log = LogFactory.getLog(HtmlRadio.class);

    public static final String FOR_ATTR = "for".intern();
    public static final String INDEX_ATTR = "index".intern();


    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlCheckbox";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.Checkbox";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Checkbox";

    private String _for = null;
    private Integer _index = null;

    public HtmlCheckbox()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setFor(String forValue)
    {
        _for = forValue;
    }

    public String getFor()
    {
        if (_for != null) return _for;
        ValueBinding vb = getValueBinding("for");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setIndex(int index)
    {
        _index = new Integer(index);
    }

    public int getIndex()
    {
        if (_index != null) return _index.intValue();
        ValueBinding vb = getValueBinding("index");
        Number v = vb != null ? (Number)vb.getValue(getFacesContext()) : null;
        return v != null ? v.intValue() : Integer.MIN_VALUE;
    }



    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = _for;
        values[2] = _index;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _for = (String)values[1];
        _index = (Integer)values[2];
    }
    //------------------ GENERATED CODE END ---------------------------------------

}
