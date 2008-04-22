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
package org.apache.myfaces.custom.dynaForm.metadata.impl.jsf;


import java.util.Iterator;

import org.apache.myfaces.custom.dynaForm.component.dynaForm.DynaConfig;
import org.apache.myfaces.custom.dynaForm.component.dynaForm.DynaConfigs;
import org.apache.myfaces.custom.dynaForm.component.dynaForm.DynaForm;
import org.apache.myfaces.custom.dynaForm.metadata.Extractor;
import org.apache.myfaces.custom.dynaForm.metadata.MetaData;

/**
 * Extract metadata from jsf form.<br />
 * This will read all facets with "ff_" name prefix but nothing will be configured.
 * Its just there to collect which fields to show if in exclusiveFieldMode
 */
public class JsfRequestFieldExtractor implements Extractor
{
	public JsfRequestFieldExtractor()
	{
	}

	public void getMetaData(MetaData metaData, Object entity)
	{
		if (!(entity instanceof DynaForm))
		{
			throw new IllegalArgumentException("passed entity argument not a DynaForm: " + entity);
		}
		
		create(metaData, (DynaForm) entity);
	}

	/**
	 * create the metadata out of the dynaConfigs for the given component
	 */
	@SuppressWarnings("unchecked")
	protected void create(MetaData metaData, DynaForm dynaForm)
	{
		DynaConfigs formConfig = dynaForm.getFormConfigs();
		if (formConfig == null)
		{
			return;
		}
		
		Iterator<DynaConfig> entries = formConfig.iterator();
		while (entries.hasNext())
		{
			DynaConfig dynaConfig = entries.next();
			String name = dynaConfig.getFor();
			if (name == null)
			{
				throw new IllegalArgumentException("'for' in config tag required");
			}
			
			if (metaData.processField(name))
			{
				metaData.requestField(name);
			}
		}
	}
}
