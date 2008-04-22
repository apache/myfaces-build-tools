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
package org.apache.myfaces.custom.stylesheet;

import org.apache.myfaces.renderkit.html.util.ResourceProvider;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * provdes a simple string as utf-8 encoded resource
 */
public class TextResourceFilterProvider implements ResourceProvider
{
	public boolean exists(ServletContext context, String resource)
	{
		return TextResourceFilter.getInstance(context).getFilteredResource(resource) != null;
	}

	public int getContentLength(ServletContext context, String resource) throws IOException
	{
		return TextResourceFilter.getInstance(context).getFilteredResource(resource).getSize();
	}

	public long getLastModified(ServletContext context, String resource) throws IOException
	{
		return TextResourceFilter.getInstance(context).getFilteredResource(resource).getLastModified();
	}

	public InputStream getInputStream(ServletContext context, String resource) throws IOException
	{
		return new ByteArrayInputStream(
			TextResourceFilter.getInstance(context).getFilteredResource(resource).getText().getBytes(
				getEncoding(context, resource)
			));
	}

	public String getEncoding(ServletContext context, String resource) throws IOException
	{
		return "UTF-8";
	}
}
