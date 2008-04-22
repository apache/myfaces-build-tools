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

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

/**
 * 
 * @author cagatay
 */
public class SecurityContextVariableResolver extends VariableResolver{

	private static final String SECURITY_CONTEXT = "securityContext";
	private static final String INIT_PARAM_SECURITY_CONTEXT = "org.apache.myfaces.SECURITY_CONTEXT";

	private VariableResolver originalResolver;

	public SecurityContextVariableResolver(VariableResolver variableresolver) {
		originalResolver = variableresolver;
	}
	
	public Object resolveVariable(FacesContext facesContext, String name) throws EvaluationException {
		if(SECURITY_CONTEXT.equals(name)) {
			return getSecurityContextImpl(facesContext);
		}
		else {
			return originalResolver.resolveVariable(facesContext, name);
		}
	}
	
	private Object getSecurityContextImpl(FacesContext facesContext) {
		String className = (String) facesContext.getExternalContext().getInitParameter(INIT_PARAM_SECURITY_CONTEXT);
		
		if(className == null)
			return new SecurityContextImpl();		//return default implementation
		
		try {
			Class clazz = Class.forName(className);
			return clazz.newInstance();
		}catch(Exception e) {
			throw new EvaluationException(e);
		}
	}

}
