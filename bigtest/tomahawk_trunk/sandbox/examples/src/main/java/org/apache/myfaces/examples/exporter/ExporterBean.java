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
package org.apache.myfaces.examples.exporter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExporterBean implements Serializable {

	private List carsList;

	public List getCarsList() {
		if( carsList == null)
			carsList = createCarsList();
		return carsList;
	}

	public void setCarsList(List carsList) {
		this.carsList = carsList;
	}
	
	private List createCarsList() {
		List list  = new ArrayList();
		list.add( new SimpleCar(0, "typeA", "blue"));
		list.add( new SimpleCar(1, "typeB", "red"));
		list.add( new SimpleCar(2, "typeC", "orange"));
		list.add( new SimpleCar(3, "typeD", "gray"));
		list.add( new SimpleCar(4, "typeE", "white"));
		list.add( new SimpleCar(5, "typeF", "black"));
		list.add( new SimpleCar(6, "typeG", "yellow"));
		list.add( new SimpleCar(6, "typeH", "purple"));
		return list;
	}
}
