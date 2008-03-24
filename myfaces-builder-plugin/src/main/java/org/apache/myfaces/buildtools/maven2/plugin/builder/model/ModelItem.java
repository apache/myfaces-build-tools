/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.buildtools.maven2.plugin.builder.model;

import java.util.ArrayList;
import java.util.List;

public class ModelItem {
	private String _className;
	private String _parentClassName;
	private List _interfaceClassNames = new ArrayList();

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getParentClassName() {
		return _parentClassName;
	}

	public void setParentClassName(String className) {
		_parentClassName = className;
	}

	public List getInterfaceClassNames() {
		return _interfaceClassNames;
	}

	public void setInterfaceClassNames(List classNames) {
		_interfaceClassNames = classNames;
	}
}
