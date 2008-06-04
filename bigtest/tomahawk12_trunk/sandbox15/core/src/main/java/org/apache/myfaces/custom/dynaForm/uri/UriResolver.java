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
package org.apache.myfaces.custom.dynaForm.uri;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.myfaces.custom.dynaForm.lib.DynaFormException;
import org.apache.myfaces.custom.dynaForm.metadata.Extractor;

/**
 * Resolves the URI to a configuration 
 */
public abstract class UriResolver
{
	/**
	 * The configuration
	 */
	public static class Configuration
	{
		private final Extractor extractor;
		private final String entity;
		
		protected Configuration(Extractor extractor, String entity)
		{
			this.extractor = extractor;
			this.entity = entity;
		}

		/**
		 * metadata for the given entity
		 */
		public Extractor getExtractor()
		{
			return extractor;
		}

		/**
		 * the entity identification 
		 */
		public String getEntity()
		{
			return entity;
		}
	}
	
	protected Configuration createConfiguration(Extractor extractor, String entity)
	{
		return new Configuration(extractor, entity);
	}

	/**
	 * resolve the given uri 
	 */
	public Configuration resolveUri(String uri)
	{
		int pos = uri.indexOf(":");
        if (pos < 0)
        {
            return resolve("default", uri);
        }
        if (uri.length() < pos+1)
		{
			throw new IllegalArgumentException("Invalid uri: " + uri);
		}
		
		return resolve(uri.substring(0, pos), uri.substring(pos+1));
	}

	/**
	 * do the hard work
	 */
	protected Configuration resolve(String scheme, String path)
	{
		String config = "dynaForm-" + scheme + ".xml";
		Properties props = new Properties();
		InputStream resource = null;
		try
		{
			resource = findConfig(config);
			if (resource == null)
			{
				throw new DynaFormException("configuration '" + config + "' not found.");
			}
			
			props.loadFromXML(resource);
		}
		catch (InvalidPropertiesFormatException e)
		{
			throw new DynaFormException(e);
		}
		catch (IOException e)
		{
			throw new DynaFormException(e);
		}
		finally
		{
			if (resource != null)
			{
				try
				{
					resource.close();
				}
				catch (IOException e)
				{
					// do not shadow the real exception
				}
			}
		}
		
		String extractor = getRequiredProperty(config, props, "Extractor");

		try
		{
			Extractor extractorClass = (Extractor) Class.forName(extractor).newInstance();
			return createConfiguration(
					extractorClass,
					path);
		}
		catch (InstantiationException e)
		{
			throw new DynaFormException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new DynaFormException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new DynaFormException(e);
		}
		catch (SecurityException e)
		{
			throw new DynaFormException(e);
		}
		catch (IllegalArgumentException e)
		{
			throw new DynaFormException(e);
		}
	}

	/**
	 * load the configuration
	 */
	protected InputStream findConfig(String config)
	{
		return getResourceAsStream("META-INF/" + config);
	}

	protected InputStream getResourceAsStream(String resource)
	{
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
		if (stream == null)
		{
			stream = UriResolver.class.getClassLoader().getResourceAsStream(resource);
		}
		return stream;
	}

	protected String getRequiredProperty(String config, Properties props, String key)
	{
		String value = props.getProperty(key);
		if (value == null)
		{
			throw new IllegalStateException("Configuration '" + key + "' missing in config " + config);
		}
		return value;
	}
}
