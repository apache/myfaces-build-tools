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
package org.apache.myfaces.custom.security;

import javax.faces.el.EvaluationException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SecurityContextVariableResolverTest extends AbstractJsfTestCase{

	protected SecurityContextVariableResolver resolver;
	
	public SecurityContextVariableResolverTest(String testName) {
		super(testName);
	}
	
	public void setUp() throws Exception{
		super.setUp();
		resolver = new SecurityContextVariableResolver(null);
	}
	
	public void tearDown() throws Exception{
		super.tearDown();
		resolver = null;
	}
	
	public static Test suite() {
		return new TestSuite(SecurityContextVariableResolverTest.class);
	}
	
	public void testSecurityContextImplIsDefaultImplWhenNoCustomImplIsConfigured() {
		SecurityContext securityContext = (SecurityContext)resolver.resolveVariable(facesContext, "securityContext");
		assertTrue(securityContext instanceof SecurityContextImpl);
	}
	
	public void testSecurityContextImplIsCustomWhenCustomImplIsConfigured() {
		servletContext.addInitParameter("org.apache.myfaces.SECURITY_CONTEXT", "org.apache.myfaces.custom.security.TestSecurityContextImpl");
		SecurityContext securityContext = (SecurityContext)resolver.resolveVariable(facesContext, "securityContext");
		assertFalse(securityContext instanceof SecurityContextImpl);
		assertTrue(securityContext instanceof TestSecurityContextImpl);
	}
	
	public void testExceptionMustBeThrownWhenAnInvalidImplIsConfigured() {
		servletContext.addInitParameter("org.apache.myfaces.SECURITY_CONTEXT", "this.class.does.not.exist");
		try {
			resolver.resolveVariable(facesContext, "securityContext");
		}catch(EvaluationException evaluationException) {
			return;
		}
		fail();
		
	}
}
