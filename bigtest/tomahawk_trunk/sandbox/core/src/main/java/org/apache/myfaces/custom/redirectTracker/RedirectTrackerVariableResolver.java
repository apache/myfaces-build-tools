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
package org.apache.myfaces.custom.redirectTracker;

import javax.faces.el.VariableResolver;
import javax.faces.el.EvaluationException;
import javax.faces.context.FacesContext;

/**
 * try to capture all beans put into the request scope
 */
public class RedirectTrackerVariableResolver extends VariableResolver
{
	private final VariableResolver original;

	public RedirectTrackerVariableResolver(VariableResolver original)
	{
		this.original = original;
	}

	public Object resolveVariable(FacesContext facesContext, String variable) throws EvaluationException
	{
		boolean exists = facesContext.getExternalContext().getRequestMap().containsKey(variable);

		Object value = original.resolveVariable(facesContext, variable);

		if (!exists && facesContext.getExternalContext().getRequestMap().containsKey(variable))
		{
			// variable created and put into the request scope

			RedirectTrackerManager manager = RedirectTrackerManager.getInstance(FacesContext.getCurrentInstance());
			if (manager != null)
			{
				manager.addSaveStateBean(variable, value);
			}
		}

		return value;
	}
}
