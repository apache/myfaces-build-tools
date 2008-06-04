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

import java.security.Principal;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SecurityContextPropertyResolverTest extends AbstractJsfTestCase{

	protected SecurityContextPropertyResolver resolver;
	
	public SecurityContextPropertyResolverTest(String testName) {
		super(testName);
	}
	
	public void setUp() throws Exception{
		super.setUp();
		resolver = new SecurityContextPropertyResolver(null);
	}
	
	public void tearDown() throws Exception{
		super.tearDown();
		resolver = null;
	}
	
	public static Test suite() {
		return new TestSuite(SecurityContextPropertyResolverTest.class);
	}
	
	//#{securityContext.remoteUser}
	public void testRemoteUser() {
		request.setUserPrincipal(new TestPrincipalImpl("Ronaldinho"));
		
		SecurityContext securityContext = new SecurityContextImpl();
		String user = (String) resolver.getValue(securityContext, "remoteUser");
		assertEquals("Ronaldinho", user);
	}
	
	public static class TestPrincipalImpl implements Principal {

		private String _name;

		public TestPrincipalImpl() {
			// NoOp
		}

		public TestPrincipalImpl(String name) {
			this._name = name;
		}

		public String getName() {
			return _name;
		}
	}
}