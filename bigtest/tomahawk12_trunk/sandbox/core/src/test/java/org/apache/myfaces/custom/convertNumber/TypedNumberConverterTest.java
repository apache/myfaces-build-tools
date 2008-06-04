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
package org.apache.myfaces.custom.convertNumber;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.convert.ConverterException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * @author cagatay (latest modification by $Author:$)
 * @version $Revision$ $Date$
 */
public class TypedNumberConverterTest extends AbstractJsfTestCase{
	
	private TypedNumberConverter converter;
	
	public TypedNumberConverterTest(String testName) {
		super(testName);
	}
	
	public void setUp() throws Exception{
		super.setUp();
		converter = new TypedNumberConverter();
	}
	
	public void tearDown() throws Exception{
		super.tearDown();
		converter = null; 
	}
	
	public static Test suite() {
		return new TestSuite(TypedNumberConverterTest.class);
	}
	
	public void testSeverityLevelOfMessageShouldBeErrorInCaseConversionFails() {
		UIInput input = new UIInput();
		input.setId("txt_test");
		
		converter.setIntegerOnly(true);
		
		try {
			converter.getAsObject(facesContext, input, "test_invalid_input");
			
			fail();
		}catch (ConverterException exception) {
			FacesMessage facesMessage = exception.getFacesMessage();
			assertEquals(FacesMessage.SEVERITY_ERROR, facesMessage.getSeverity());
		}
		
	}
}
