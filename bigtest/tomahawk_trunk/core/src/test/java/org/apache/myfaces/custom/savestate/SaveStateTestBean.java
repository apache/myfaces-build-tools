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
package org.apache.myfaces.custom.savestate;

import java.io.Serializable;
import java.util.LinkedList;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * @author cagatay
 * Test bean for UISaveStateTest
 */
public class SaveStateTestBean implements Serializable, StateHolder {

	private LinkedList linkedList;

	private String name;

	public LinkedList getLinkedList() {
		if(linkedList == null)
			linkedList = new LinkedList();
		return linkedList;
	}

	public void setLinkedList(LinkedList linkedList) {
		this.linkedList = linkedList;
	}

	public boolean isTransient() {
		return false;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[])state;
        name = (String)values[0];
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[1];
        values[0] = name;
        return values;
	}

	public void setTransient(boolean newTransientValue) {
		
	}

	public String name() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
