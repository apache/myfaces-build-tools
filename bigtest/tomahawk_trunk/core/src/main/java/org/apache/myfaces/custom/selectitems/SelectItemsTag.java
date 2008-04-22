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

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectItemsTag extends UIComponentTagBase {
	
	private String _var;
	
	private String _itemLabel;
	
	private String _itemValue;
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		
		if (_var != null) {
			if (isValueReference(_var)) {
				ValueBinding vb = getFacesContext().getApplication()
						.createValueBinding(_var);
				component.setValueBinding("var", vb);
			} else {
				component.getAttributes().put("var", _var);
			}
		}
		
		if (_itemLabel != null) {
			if (isValueReference(_itemLabel)) {
				ValueBinding vb = getFacesContext().getApplication()
						.createValueBinding(_itemLabel);
				component.setValueBinding("itemLabel", vb);
			} else {
				component.getAttributes().put("itemLabel", _itemLabel);
			}
		}
		
		if (_itemValue != null) {
			if (isValueReference(_itemValue)) {
				ValueBinding vb = getFacesContext().getApplication()
						.createValueBinding(_itemValue);
				component.setValueBinding("itemValue", vb);
			} else {
				component.getAttributes().put("itemValue", _itemValue);
			}
		}
		
	}
	
	public void release() {
		super.release();
		_var = null;
		_itemLabel = null;
		_itemValue = null;
	}
	
	public String getComponentType() {
		return UISelectItems.COMPONENT_TYPE;
	}
	
	public String getVar() {
		return _var;
	}

	public void setVar(String var) {
		this._var = var;
	}

	public String getItemLabel() {
		return _itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this._itemLabel = itemLabel;
	}

	public String getItemValue() {
		return _itemValue;
	}

	public void setItemValue(String itemValue) {
		this._itemValue = itemValue;
	}

	public String getRendererType() {
		return null;
	}	
}
