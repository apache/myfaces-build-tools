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
package org.apache.myfaces.custom.util;

import javax.faces.component.html.HtmlCommandButton;

import junit.framework.TestCase;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentUtilsTest extends TestCase{

	public void testDecorateEventAttribute() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId("testButton");
		
		ComponentUtils.decorateEventAttribute(button, "onclick", "alert('Whad up?');");
		assertEquals("alert('Whad up?');", (String) button.getAttributes().get("onclick"));
		
		//Second try to decorate with same value
		ComponentUtils.decorateEventAttribute(button, "onclick", "alert('Whad up?');");
		assertEquals("alert('Whad up?');", (String) button.getAttributes().get("onclick"));
		
		ComponentUtils.decorateEventAttribute(button, "onclick", "return false;");
		assertEquals("alert('Whad up?');return false;", (String) button.getAttributes().get("onclick"));
	}
}