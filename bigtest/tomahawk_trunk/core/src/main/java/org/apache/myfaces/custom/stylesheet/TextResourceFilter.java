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

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;

/**
 * filters a stylesheet
 * <p/>
 * This will replace any el expressing of the original stylesheet with its evaluated string form
 *
 * @author imario
 */
public class TextResourceFilter implements Serializable
{
	private static final Log log = LogFactory.getLog(TextResourceFilter.class);

	private final static String CONTEXT_KEY = TextResourceFilter.class.getName() + ".INSTANCE";

	private final Map filteredResources = Collections.synchronizedMap(new TreeMap());

	public static class ResourceInfo
	{
		private final long lastModified;
		private final String text;

		protected ResourceInfo(long lastModified, String text)
		{
			this.lastModified = lastModified;
			this.text = text;
		}

		public long getLastModified()
		{
			return lastModified;
		}

		public String getText()
		{
			return text;
		}

		public int getSize()
		{
			return text.length();
		}
	}

	protected TextResourceFilter()
	{
	}

	protected static TextResourceFilter create()
	{
		return new TextResourceFilter();
	}

	/**
	 * get the application stylesheet filter
	 */
	public static TextResourceFilter getInstance(ServletContext context)
	{
		TextResourceFilter filterText = (TextResourceFilter) context.getAttribute(CONTEXT_KEY);
		if (filterText == null)
		{
			filterText = create();
			context.setAttribute(CONTEXT_KEY, filterText);
		}

		return filterText;
	}

	/**
	 * get the application stylesheet filter
	 */
	public static TextResourceFilter getInstance(FacesContext context)
	{
		TextResourceFilter filterText = (TextResourceFilter) context.getExternalContext().getApplicationMap().get(CONTEXT_KEY);
		if (filterText == null)
		{
			filterText = create();
			context.getExternalContext().getApplicationMap().put(CONTEXT_KEY, filterText);
		}

		return filterText;
	}

	/**
	 * gets the filtered content of the resource pointing to with <code>path</code>
	 *
	 * This will <b>not</b> filter the resource if its not already done before.
	 */
	public ResourceInfo getFilteredResource(String path)
	{
		ResourceInfo filteredResource = (ResourceInfo) filteredResources.get(path);
		if (filteredResource == null)
		{
			return null;
		}

		return filteredResource;
	}

	/**
	 * <p>
	 * filteres the resource
	 * </p>
	 *
	 * Notice: This method is not synchronized for performance reasons (the map is)
	 * the worst case is that we filter a resource twice the first time wich is not
	 * a problem
	 */
	public ResourceInfo getOrCreateFilteredResource(FacesContext context, String path) throws IOException
	{
		if (path.startsWith("/"))
		{
			// the resource loader do not use the leading "/", so strip it here
			path = path.substring(1);
		}

		ResourceInfo filteredResource = getFilteredResource(path);
		if (filteredResource != null)
		{
			return filteredResource;
		}

		String text = RendererUtils.loadResourceFile(context, path);
		if (text == null)
		{
			// avoid loading the errorneous resource over and over again
			text = "";
		}

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new StringReader(text.toString()));

			String line;
			while ((line = reader.readLine()) != null)
			{
				int pos = line.indexOf("#{");
				if (pos > -1 && line.indexOf("}", pos) > -1)
				{
					line = RendererUtils.getStringValue(context, context.getApplication().createValueBinding(line));
				}

				if (line != null)
				{
					writer.println(line);
				}
			}
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					log.warn(e.getLocalizedMessage(), e);
				}
			}

			writer.close();
		}

		filteredResource = new ResourceInfo(System.currentTimeMillis(), stringWriter.toString());
		filteredResources.put(path, filteredResource);

		return filteredResource;
	}
}
