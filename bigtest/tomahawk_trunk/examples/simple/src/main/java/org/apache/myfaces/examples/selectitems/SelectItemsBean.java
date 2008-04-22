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

package org.apache.myfaces.examples.selectitems;

import java.util.ArrayList;
import java.util.List;

import org.apache.myfaces.examples.listexample.SimpleCar;

public class SelectItemsBean {

	private List carList;
	
	private String selectedCarColor;

	public List getCarList() {
		if(carList == null) {
			carList = createCarList();
		}		
		return carList;
	}

	public void setCarList(List list) {
		carList = list;
	}
	
	private List createCarList() {
		List list = new ArrayList();
		list.add(new SimpleCar(1, "Car 1", "blue"));
		list.add(new SimpleCar(2, "Car 2", "white"));
		list.add(new SimpleCar(3, "Car 3", "red"));
		list.add(new SimpleCar(4, "Car 4", "green"));
		return list;
	}

	public String getSelectedCarColor() {
		return selectedCarColor;
	}

	public void setSelectedCarColor(String selectedCarColor) {
		this.selectedCarColor = selectedCarColor;
	}
	
}
