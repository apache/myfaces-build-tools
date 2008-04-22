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
package org.apache.myfaces.custom.conversation;

import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.ObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;

/**
 * adapts the conversation scope to a new spring scope
 */
public class SpringConversationScope implements Scope
{
	private final static Log log = LogFactory.getLog(SpringConversationScope.class);

	public String getConversationId()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ConversationManager manager = ConversationManager.getInstance(facesContext);
		if (manager.hasConversationContext())
		{
			return Long.toString(manager.getConversationContextId().longValue(), 10);
		}

		return null;
	}

	/**
	 * get the conversation bean.<br />
	 * this will start a new conversation with the same name in case a conversation
	 * is not existent yet.
	 */
	public Object get(String name, ObjectFactory objectFactory)
	{
		name = buildBeanName(name);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		ConversationManager manager = ConversationManager.getInstance(facesContext);

		Object value = null;
		boolean created = false;

		// check if we have a conversation
		if (!manager.hasConversation(name))
		{
			// ... no ... create the object to ...
			value = objectFactory.getObject();
			created = true;

			// ... determine if it should deal with persistence.
			boolean isPersistent = (value instanceof PersistentConversation);

			// start the conversation
			manager.startConversation(name, isPersistent);
		}

		// get the conversation
		Conversation conversation = manager.getConversation(name);
		if (!conversation.hasBean(name))
		{
			// create the bean (if not already done)
			if (!created)
			{
				value = objectFactory.getObject();
			}

			conversation.putBean(facesContext, name, value);
		}

		// get the bean
		return conversation.getBean(name);
	}

	/**
	 * strip off any spring namespace.
	 * TODO: Is this valid?
	 */
	protected String buildBeanName(String name)
	{
		if (name == null)
		{
			return null;
		}

		int pos = name.lastIndexOf('.');
		if (pos < 0)
		{
			return name;
		}

		return name.substring(pos+1);
	}

	/**
	 * remove the bean from the conversation.
	 * TODO: when will this be called .. and should we close the conversation then?
	 */
	public Object remove(String name)
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();

		ConversationManager manager = ConversationManager.getInstance(facesContext);
		if (!manager.hasConversation(name))
		{
			return null;
		}

		return manager.getConversation(name).removeBean(name);
	}

	public void registerDestructionCallback(String name, Runnable runnable)
	{
		if (log.isWarnEnabled())
		{
			log.warn("SpringConversationScope: registerDestructionCallback not yet supported. Name=" + name);
		}
	}
}
