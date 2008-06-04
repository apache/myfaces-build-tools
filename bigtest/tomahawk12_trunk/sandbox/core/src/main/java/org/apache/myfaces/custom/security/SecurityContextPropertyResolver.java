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
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author cagatay
 */
public class SecurityContextPropertyResolver extends PropertyResolver{
	
	private static final Log log = LogFactory.getLog(SecurityContextPropertyResolver.class);

	private final static String AUTH_TYPE = "authType";
	private final static String USER = "remoteUser";
	private final static String IF_GRANTED = "ifGranted";
	private final static String IF_ALL_GRANTED = "ifAllGranted";
	private final static String IF_ANY_GRANTED = "ifAnyGranted";
	private final static String IF_NOT_GRANTED = "ifNotGranted";
	
	private PropertyResolver originalResolver;

	public SecurityContextPropertyResolver(PropertyResolver propertyresolver) {
		originalResolver = propertyresolver;
	}
	
	public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		if(base instanceof SecurityContext) {
			SecurityContext securityContext = (SecurityContext) base;
			
			if(property.equals(AUTH_TYPE)) {
				return securityContext.getAuthType();				
			}
			else if(property.equals(USER)) 
			{
				return securityContext.getRemoteUser();
			}
			else if(property.equals(IF_GRANTED)) 
			{
				securityContext.setAuthMode(SecurityContext.AUTH_MODE_SINGLE);
				return securityContext;
			}
			else if(property.equals(IF_ALL_GRANTED)) 
			{
				securityContext.setAuthMode(SecurityContext.AUTH_MODE_ALL);
				return securityContext;
			}
			else if(property.equals(IF_ANY_GRANTED)) 
			{
				securityContext.setAuthMode(SecurityContext.AUTH_MODE_ANY);
				return securityContext;
			}
			else if(property.equals(IF_NOT_GRANTED)) 
			{
				securityContext.setAuthMode(SecurityContext.AUTH_MODE_NOT);
				return securityContext;
			}
			else if(securityContext.inAuthMode()) {
				securityContext.setRoles(getRolesFromProperty(property));
				int authMode = securityContext.getAuthMode();
				
				if(authMode == SecurityContext.AUTH_MODE_SINGLE)
					return Boolean.valueOf(securityContext.ifSingleGranted());
				else if(authMode == SecurityContext.AUTH_MODE_ALL)
					return Boolean.valueOf(securityContext.ifAllGranted());
				else if(authMode == SecurityContext.AUTH_MODE_ANY)
					return Boolean.valueOf(securityContext.ifAnyGranted());
				else
					return Boolean.valueOf(securityContext.ifNotGranted());
			}
			else {
				 if(log.isDebugEnabled())
		              log.debug("Exception while retrieving property; base : "+base.getClass().getName()+", property : "+property);
				 
				throw new PropertyNotFoundException(getMessage(base, (String)property));
			}
		}
		else 
			return originalResolver.getValue(base, property);
		
	}

	public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		return originalResolver.getType(base, index);
	}

	public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		if(base instanceof SecurityContext)
			return SecurityContext.class;
		else
			return originalResolver.getType(base, property);
	}

	public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		return originalResolver.getValue(base, index);
	}

	public boolean isReadOnly(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		return originalResolver.isReadOnly(base, index);
	}

	public boolean isReadOnly(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		if(base instanceof SecurityContext)
			return true;
		else
			return originalResolver.isReadOnly(base, property);
	}

	public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException {
		originalResolver.setValue(base, index, value);
	}

	public void setValue(Object base, Object property, Object value) throws EvaluationException, PropertyNotFoundException {
		originalResolver.setValue(base, property, value);
	}
	
	private String[] getRolesFromProperty(Object property) {
		String[] roles = ((String)property).split(",");
		for (int i = 0; i < roles.length; i++) {
			roles[i] = roles[i].trim();
		}
		return roles;
	}
	
	private static String getMessage(Object base, String name) {
		return "Bean: " + base.getClass().getName() + ", property: " + name;
	}

}
