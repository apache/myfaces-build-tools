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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * handle conversation related stuff like beans
 * @author imario@apache.org
 */
public class Conversation
{
	private final static Log log = LogFactory.getLog(Conversation.class);

	private final String name;
	private final boolean persistence;

	private PersistenceManager persistenceManager;

	// private final Map beans = new TreeMap(new ValueBindingKey());
	private final Map beans = new TreeMap();

	protected Conversation(String name, boolean persistence)
	{
		this.name = name;
		this.persistence = persistence;

		if (log.isDebugEnabled())
		{
			log.debug("start conversation:" + name + "(persistence=" + persistence + ")");
		}
	}

	/**
	 * Add the given valueBinding to the context map. <br/>
	 * This will also resolve the value of the binding.
	 */
	public void putBean(FacesContext context, String name, Object value)
	{
		if (name.indexOf('.') > -1)
		{
			throw new IllegalArgumentException("you cant put a property under conversation control. name: " + name);
		}

		if (beans.containsKey(name))
		{
			// already there
			return;
		}
		if (log.isDebugEnabled())
		{
			log.debug("put bean to conversation:" + name + "(bean=" + name + ")");
		}
		beans.put(name, value);
	}

	/**
	 * the conversation name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * end this conversation <br />
	 * <ul>
	 * <li>inform all beans implementing the {@link ConversationListener} about the conversation end</li>
	 * <li>free all beans</li>
	 * </ul>
	 */
	public void endConversation(boolean regularEnd)
	{
		if (log.isDebugEnabled())
		{
			log.debug("end conversation:" + name);
		}

		Iterator iterBeans = beans.values().iterator();
		while (iterBeans.hasNext())
		{
			Object bean = iterBeans.next();
			if (bean instanceof ConversationListener)
			{
				((ConversationListener) bean).conversationEnded();
			}
		}
		beans.clear();

		if (isPersistence())
		{
			if (regularEnd)
			{
				getPersistenceManager().commit();
			}
			else
			{
				getPersistenceManager().rollback();
			}

			getPersistenceManager().purge();
		}
	}

	/**
	 * Iterate all beans associated to this context
	 *
	 * @return Iterator of {@link Map.Entry} elements
	public Iterator iterateBeanEntries()
	{
		return beans.entrySet().iterator();
	}
	 */

	public boolean hasBean(String name)
	{
		return beans.containsKey(name);
	}

	public Object getBean(String name)
	{
		return beans.get(name);
	}

	public Object removeBean(String name)
	{
		return beans.remove(name);
	}

	/**
	 * returns true if this conversation hold the persistence manager (aka EntityManager)
	 */
	public boolean isPersistence()
	{
		return persistence || persistenceManager != null;
	}

	public PersistenceManager getPersistenceManager()
	{
		if (persistenceManager == null)
		{
			persistenceManager = ConversationManager.getInstance().createPersistenceManager();
		}

		return persistenceManager;
	}

	/**
	 * returns true if we hold the given instance
	 */
	public boolean hasBean(Object instance)
	{
		return beans.containsValue(instance);
	}
}
