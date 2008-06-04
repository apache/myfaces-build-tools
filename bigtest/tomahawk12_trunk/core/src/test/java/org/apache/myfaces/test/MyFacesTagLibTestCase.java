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

package org.apache.myfaces.test;

/**
 * @author Dennis C. Byrne
 */

public class MyFacesTagLibTestCase extends AbstractTagLibTestCase {

	protected static final String META_INF = "META-INF/";

	public MyFacesTagLibTestCase(){

		// TODO get the sandbox in here

		tldPaths = new String[3];
		tldPaths[0] = META_INF + "myfaces_html.tld";
		tldPaths[1] = META_INF + "myfaces_core.tld";
		tldPaths[2] = META_INF + "tomahawk.tld";
		// tldPaths[3] = META_INF + "myfaces_sandbox.tld";

	}

}
