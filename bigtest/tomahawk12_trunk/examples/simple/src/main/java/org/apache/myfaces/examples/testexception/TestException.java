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
package org.apache.myfaces.examples.testexception;

public class TestException
{
	public String npe()
	{
		throw new NullPointerException();
	}

	public String wrappedRuntimeNpe()
	{
		throw new RuntimeException(new NullPointerException());
	}

	public String wrappedServletNpe()
	{
		throw new FakesServletException(new NullPointerException());
	}

	public String npeTxt()
	{
		throw new NullPointerException("my null pointer exception");
	}

	public String wrappedRuntimeNpeTxt()
	{
		throw new RuntimeException("my text in wrapper exception", new NullPointerException());
	}

	public String wrappedServletNpeTxt()
	{
		throw new FakesServletException("my text in wrapper exception", new NullPointerException());
	}
}
