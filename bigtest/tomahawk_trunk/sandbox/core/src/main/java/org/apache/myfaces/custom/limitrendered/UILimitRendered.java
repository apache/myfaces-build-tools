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
 * --
 * $Id$
 */
package org.apache.myfaces.custom.limitrendered;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * Component that only renders a subset of its children components. Provides
 * functionality that the JSTL choose tag has, but leverages the 
 * {@link javax.faces.component.UIComponent#isRendered()} method instead
 * of using when tags with test attributes.
 * <p>It can either use a filter type of "count" or "index". If count,
 * the given number of children will be rendered (so for example, a value of 2 will cause
 * the first two children that are have a true <tt>isRendered</tt> result
 * to be rendered. If index, it will render the given indexes.</p>
 * <p>See the taglib for more documentation</p>
 * 
 * @JSFComponent
 *   name = "s:limitRendered"
 *   tagClass = "org.apache.myfaces.custom.limitrendered.UILimitRenderedTag"
 *   
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UILimitRendered extends UIComponentBase
{
    public static final String COMPONENT_FAMILY = "javax.faces.Data";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.UILimitRendered";
    
    private String type;
    private Object value;
    
    public UILimitRendered()
    {
        setRendererType(null);
    }
    
    /**
     * @see javax.faces.component.UIComponent#getFamily()
     */
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        if (this.type != null) return this.type;
        ValueBinding vb = getValueBinding("type");
        return vb != null ? RendererUtils.getStringValue(getFacesContext(), vb) : null;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the value
     */
    public Object getValue()
    {
        if (this.value != null) return this.value;
        ValueBinding vb = getValueBinding("value");
        return vb != null ? vb.getValue(getFacesContext()) : null;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
    
    /**
     * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
        return new Object[] { 
            super.saveState(context),
            this.type,
            this.value
        };
    }
    
    /**
     * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object[] arr = (Object[])state;
        int index = -1;
        super.restoreState(context, arr[++index]);
        this.type = (String)arr[++index];
        this.value = arr[++index];
    }

    /**
     * @see javax.faces.component.UIComponentBase#getRendersChildren()
     */
    public boolean getRendersChildren()
    {
        return true;
    }
    
    /**
     * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
     */
    public void encodeChildren(FacesContext context) throws IOException
    {
        if (!isRendered()) {
            return;
        }
        
        for (Iterator iter = filterChildren().iterator(); iter.hasNext();) {
            RendererUtils.renderChild(context, (UIComponent)iter.next());
        }
    }
    
    protected List filterChildren()
    {
      Object value = getValue();
      String type = getType();
      if (type == null) {
          type = "count";
      }
      
      if ("index".equals(type)) {
          // default to all
          if (value == null) {
              return getChildren();
          }
          return getChildrenByIndex(value);
      } if ("count".equals(type)) {
          return getChildrenByCount(value);
      } else {
          throw new IllegalArgumentException("type");
      }
    }
    
    protected List getChildrenByCount(Object value)
    {
        if (value == null) {
            value = new Integer(1);
        }
        int count;
        if (value instanceof Number) {
          count = ((Number)value).intValue();
        } else {
          count = Integer.parseInt(value.toString());
        }
        
        List children = new ArrayList(count);
        int i = 0;
        for (Iterator iter = getChildren().iterator(); iter.hasNext() && i < count;)
        {
            UIComponent child = (UIComponent)iter.next();
            if (child.isRendered())
            {
                children.add(child);
                ++i;
            }
        }
        
        return children;
    }
        
    protected List getChildrenByIndex(Object value)
    {
        int[] indexes = parseIndexesValue(value);
        
        List retVal = new ArrayList(indexes.length);
        List children = getChildren();
        for (int i = 0; i < indexes.length; ++i)
        {
            int index = indexes[i] < 0 ? children.size() + indexes[i] :
                indexes[i];
            retVal.add(children.get(index));
        }
        return retVal;
    }
    
    protected int[] parseIndexesValue(Object value)
    {
        int[] indexes;
        if (value instanceof Collection)
        {
            Collection c = (Collection)value;
            indexes = new int[c.size()];
            int i = 0;
            for (Iterator iter = c.iterator(); iter.hasNext(); ++i)
            {
                Object obj = iter.next();
                if (obj instanceof Number) {
                    indexes[i] = ((Number)obj).intValue();
                } else {
                    indexes[i] = Integer.parseInt(obj.toString());
                }
            }
        }
        else if (value.getClass().isArray())
        {
            if (int.class.isAssignableFrom(value.getClass().getComponentType()))
            {
                indexes = (int[])value;
            }
            else
            {
                indexes = new int[Array.getLength(value)];
                for (int i = 0; i < indexes.length; ++i)
                {
                    Object obj = Array.get(value, i);
                    if (obj instanceof Number) {
                        indexes[i] = ((Number)obj).intValue();
                    } else {
                        indexes[i] = Integer.parseInt(obj.toString());
                    }
                }
            }
        }
        else
        {
            String[] values = value.toString().split("\\s*,\\s*");
            indexes = new int[values.length];
            for (int i = 0; i < indexes.length; ++i) {
                indexes[i] = Integer.parseInt(values[i]);
            }
        }
        
        return indexes;
    }
}
