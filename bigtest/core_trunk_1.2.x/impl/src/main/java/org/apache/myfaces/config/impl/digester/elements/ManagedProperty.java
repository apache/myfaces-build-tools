/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.myfaces.config.impl.digester.elements;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.config.impl.digester.elements.ListEntries;
import org.apache.myfaces.util.ContainerUtils;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a> (latest modification by $Author$)
 * @author Anton Koinov
 *
 * @version $Revision$ $Date$
 */
public class ManagedProperty implements org.apache.myfaces.config.element.ManagedProperty
{
    private static final ValueBinding DUMMY_VB = new DummyValueBinding();

    private int                       _type    = TYPE_UNKNOWN;
    private String                    _propertyName;
    private String                    _propertyClass;
    private ValueBinding              _valueBinding;
    private String                    _value;
    private MapEntries                _mapEntries;
    private ListEntries               _listEntries;

    public int getType()
    {
        return _type;
    }


    public org.apache.myfaces.config.element.MapEntries getMapEntries()
    {
        return _mapEntries;
    }


    public void setMapEntries(MapEntries mapEntries)
    {
        _mapEntries = mapEntries;
        _type = TYPE_MAP;
    }


    public org.apache.myfaces.config.element.ListEntries getListEntries()
    {
        return _listEntries;
    }


    public void setListEntries(ListEntries listEntries)
    {
        _listEntries = listEntries;
        _type = TYPE_LIST;
    }


    public String getPropertyName()
    {
        return _propertyName;
    }


    public void setPropertyName(String propertyName)
    {
        _propertyName = propertyName;
    }


    public String getPropertyClass()
    {
        return _propertyClass;
    }


    public void setPropertyClass(String propertyClass)
    {
        _propertyClass = propertyClass;
    }


    public boolean isNullValue()
    {
        return _type == TYPE_NULL;
    }


    public void setNullValue()
    {
        _type = TYPE_NULL;
    }


    public void setValue(String value)
    {
        _value = value;
        _type = TYPE_VALUE;
    }

    public String getValue()
    {
        return _value;
    }


    public Object getRuntimeValue(FacesContext facesContext)
    {
        getValueBinding(facesContext);

        return (_valueBinding == DUMMY_VB)
            ? _value : _valueBinding.getValue(facesContext);
    }


    public ValueBinding getValueBinding(FacesContext facesContext)
    {
        if (_valueBinding == null)
        {
            _valueBinding =
                isValueReference()
                ? facesContext.getApplication().createValueBinding(_value)
                : DUMMY_VB;
        }
        return _valueBinding;
    }


    public boolean isValueReference()
    {
        return ContainerUtils.isValueReference(_value);
    }


    private static class DummyValueBinding extends ValueBinding
    {
        public String getExpressionString()
        {
            throw new UnsupportedOperationException();
        }

        public Class getType(FacesContext facesContext)
        {
            throw new UnsupportedOperationException();
        }

        public Object getValue(FacesContext facesContext)
        {
            throw new UnsupportedOperationException();
        }

        public boolean isReadOnly(FacesContext facesContext)
        {
            throw new UnsupportedOperationException();
        }

        public void setValue(FacesContext facesContext, Object value)
        {
            throw new UnsupportedOperationException();
        }
    }
}
