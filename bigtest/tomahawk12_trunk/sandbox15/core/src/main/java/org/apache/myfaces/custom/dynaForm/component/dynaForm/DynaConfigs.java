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
package org.apache.myfaces.custom.dynaForm.component.dynaForm;

import javax.faces.component.UIComponentBase;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * the dynaForm configuration component<br />
 * handles overruled field setup
 */
public class DynaConfigs extends UIComponentBase
{
	public static final String COMPONENT_TYPE = "org.apache.myfaces.dynaForm.DynaConfigs";
	public static final String COMPONENT_FAMILY = "org.apache.myfaces.dynaForm.DynaConfigs";

	private transient Map<String, DynaConfig> configMap = null;

	@Override
	public String getFamily()
	{
		return COMPONENT_FAMILY;
	}

	public DynaConfig getConfig(String name)
	{
		Map<String, DynaConfig> configMap = getConfigMap();
		return configMap.get(name);
	}

	protected Map<String, DynaConfig> getConfigMap()
	{
		if (configMap != null)
		{
			return configMap;
		}
		configMap = new LinkedHashMap<String, DynaConfig>();

		for (Object child : getChildren())
		{
			if (child instanceof DynaConfig)
			{
				DynaConfig dynaConfig = (DynaConfig) child;
				if (configMap.containsKey(dynaConfig.getFor()))
				{
					throw new IllegalStateException("duplicate config for property '" + dynaConfig.getFor() + "'");
				}
				configMap.put(dynaConfig.getFor(), dynaConfig);
			}
		}

		return configMap;
	}

	public Iterator<DynaConfig> iterator()
	{
		return getConfigMap().values().iterator();
	}
}