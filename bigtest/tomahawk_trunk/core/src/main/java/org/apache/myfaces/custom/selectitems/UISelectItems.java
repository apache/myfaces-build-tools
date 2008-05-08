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
package org.apache.myfaces.custom.selectitems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 * @JSFComponent
 *   name = "t:selectItems"
 *   tagClass = "org.apache.myfaces.custom.selectitems.SelectItemsTag"
 * 
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UISelectItems extends javax.faces.component.UISelectItems {
	
    public static final String COMPONENT_TYPE = "org.apache.myfaces.UISelectItems";
    
    private String _var;
    
    private Object _itemLabel;
    
    private Object _itemValue;
    
    public UISelectItems()   {}
    
	public String getVar() {
		if(_var != null)
			return _var;
		
		ValueBinding vb = getValueBinding("var");
		String v = vb != null ? (String)vb.getValue(getFacesContext()) : null;
		return v;
	}

	public void setVar(String var) {
		this._var = var;
	}

	public Object getItemLabel() {
		if(_itemLabel != null)
			return _itemLabel;
		
		ValueBinding vb = getValueBinding("itemLabel");
		Object v = vb != null ? vb.getValue(getFacesContext()) : null;
		return v;
	}

	public void setItemLabel(Object itemLabel) {
		this._itemLabel = itemLabel;
	}

	public Object getItemValue() {
		if(_itemValue != null)
			return _itemValue;
		
		ValueBinding vb = getValueBinding("itemValue");
		Object v = vb != null ? vb.getValue(getFacesContext()) : null;
		return v;
	}

	public void setItemValue(Object itemValue) {
		this._itemValue = itemValue;
	}
    
    public Object getValue() {
    	Object value = super.getValue();
    	return createSelectItems(value);
    }

	private SelectItem[] createSelectItems(Object value) {
		List items = new ArrayList();
		
		if (value instanceof SelectItem[]) {
			return (SelectItem[]) value;
		}
		else if (value instanceof Collection) {
			Collection collection = (Collection) value;
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				Object currentItem = (Object) iter.next();
				if (currentItem instanceof SelectItemGroup) {
					SelectItemGroup itemGroup = (SelectItemGroup) currentItem;		
					SelectItem[] itemsFromGroup = itemGroup.getSelectItems();
					for (int i = 0; i < itemsFromGroup.length; i++) {
						items.add(itemsFromGroup[i]);
					}
				}
				else {
					putIteratorToRequestParam(currentItem);
					SelectItem selectItem = createSelectItem();
					removeIteratorFromRequestParam();
					items.add(selectItem);
				}
			}
		}
		else if (value instanceof Map) {
			Map map = (Map) value;
			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
				Entry currentItem = (Entry) iter.next();
				putIteratorToRequestParam(currentItem.getValue());
				SelectItem selectItem = createSelectItem();
				removeIteratorFromRequestParam();
				items.add(selectItem);
			}
		}
		
		return (SelectItem[]) items.toArray(new SelectItem[0]);
	}

	private SelectItem createSelectItem() {
		SelectItem item = null;
		Object value = getItemValue();
		String label = getItemLabel() != null ? getItemLabel().toString() : null;
		
		if(label != null)
			item = new SelectItem(value, label);
		else
			item = new SelectItem(value);
		
		return item;
	}
	
	private void putIteratorToRequestParam(Object object) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(getVar(), object);
	}
	
	private void removeIteratorFromRequestParam() {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(getVar());
	}
	
	public Object saveState(FacesContext context)  {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);
        values[1] = _var;
        values[2] = _itemLabel;
        values[3] = _itemValue;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)   {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _var = (String)values[1];
        _itemLabel = values[2];
        _itemValue = values[3];
    }
}

