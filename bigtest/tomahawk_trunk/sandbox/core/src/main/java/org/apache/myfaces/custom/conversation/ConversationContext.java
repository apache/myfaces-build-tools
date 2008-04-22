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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * The ConversationContext handles all conversations within the current context
 *
 * @author imario@apache.org
 */
public class ConversationContext
{
	private final long id;

	private final Object mutex = new Object();
	private final Map conversations = new TreeMap();
	// private final List conversationStack = new ArrayList(10);
	private Conversation currentConversation;

	private long lastAccess;

	protected ConversationContext(long id)
	{
		this.id = id;
		touch();
	}

	protected void touch()
	{
		lastAccess = System.currentTimeMillis();
	}

	public long getLastAccess()
	{
		return lastAccess;
	}

	public void shutdownContext()
	{
		synchronized (mutex)
		{
			Iterator iterConversation = conversations.values().iterator();
			while (iterConversation.hasNext())
			{
				Conversation conversation = (Conversation) iterConversation.next();
				conversation.endConversation(false);
			}

			conversations.clear();
			// conversationStack.clear();
		}
	}

	/**
	 * Start a conversation if not already started.<br />
	 * All nested conversations (if any) are closed if the conversation already existed.
	 * @param name
	 */
	public void startConversation(String name, boolean persistence)
	{
		synchronized (mutex)
		{
			touch();
			Conversation conversation = (Conversation) conversations.get(name);
			if (conversation == null)
			{
				conversation = new Conversation(name, persistence);
				conversations.put(name, conversation);
				// conversationStack.add(conversation);
			}
			/*
			else
			{
				endNestedConversations(conversation, false);
			}
			*/
			currentConversation = conversation;
		}
	}

	/*
	 * Ends all conversations nested under conversation
	 *
	protected void endNestedConversations(Conversation conversation, boolean regularEnd)
	{
		synchronized (mutex)
		{
			touch();
			while (conversationStack.size()>0)
			{
				int index = conversationStack.size()-1;
				Conversation dependingConversation = (Conversation) conversationStack.get(index);
				if (conversation == dependingConversation)
				{
					return;
				}

				endConversation((Conversation) conversationStack.remove(index), regularEnd);
			}
		}
	}
	*/

	/**
	 * End the given conversation
	 */
	protected void endConversation(Conversation conversation, boolean regularEnd)
	{
		synchronized (mutex)
		{
			touch();
			conversation.endConversation(regularEnd);
			conversations.remove(conversation.getName());
		}
	}

	/**
	 * End the conversation with given name.<br />
	 * This also automatically closes all nested conversations.
	 */
	public void endConversation(String name, boolean regularEnd)
	{
		synchronized (mutex)
		{
			touch();
			Conversation conversation = (Conversation) conversations.get(name);
			if (conversation != null)
			{
				/*
				while (conversationStack.size()>1)
				{
					Conversation dependingConversation = (Conversation) conversationStack.get(conversationStack.size()-1);
					endConversation(dependingConversation, false);
					if (dependingConversation == conversation)
					{
						if (conversationStack.size() > 0)
						{
							currentConversation = (Conversation) conversationStack.get(conversationStack.size()-1);
						}
						return;
					}
				}
				*/
				endConversation(conversation, regularEnd);
			}
		}
	}

	/**
	 * Get the current conversation. The current conversation is the one last seen by the startConversation tag.
	 */
	public Conversation getCurrentConversation()
	{
		touch();
		return currentConversation;
	}

	/**
	 * see if there is a conversation
	 */
	public boolean hasConversations()
	{
		synchronized (mutex)
		{
			touch();
			return conversations.size() > 0;
		}
	}

	/**
	 * check if the given conversation exists
	 */
	public boolean hasConversation(String name)
	{
		synchronized (mutex)
		{
			touch();
			return conversations.get(name) != null;
		}
	}

	/**
	 * get a conversation by name
	 */
	public Conversation getConversation(String name)
	{
		synchronized (mutex)
		{
			touch();
			return (Conversation) conversations.get(name);
		}
	}

	/**
	 * find the conversation which holds the given instance
	 */
	public Conversation findConversation(Object instance)
	{
		synchronized (mutex)
		{
			touch();
			Iterator iterConversations = conversations.values().iterator();
			while (iterConversations.hasNext())
			{
				Conversation conversation = (Conversation) iterConversations.next();
				if (conversation.hasBean(instance))
				{
					return conversation;
				}
			}
		}
		return null;
	}

	/**
	 * inject all beans from the conversation context to their configured scope
	protected void injectConversationBeans(FacesContext context)
	{
		Set alreadyAdded = new TreeSet(new ValueBindingKey());

		for (int i = conversationStack.size(); i>0; i--)
		{
			Conversation conversation = (Conversation) conversationStack.get(i-1);
			Iterator iterBeans = conversation.iterateBeanEntries();
			while (iterBeans.hasNext())
			{
				Map.Entry bean = (Map.Entry) iterBeans.next();
				if (!alreadyAdded.contains(bean.getKey()))
				{
					((ValueBinding) bean.getKey()).setValue(context, bean.getValue());
				}
			}
		}
	}
	 */

	/**
	 * find the bean named <code>name</code> in the conversation stack
	 */
	public Object findBean(String name)
	{
		synchronized (mutex)
		{
			touch();
			/*
			for (int i = conversationStack.size(); i>0; i--)
			{
				Conversation conversation = (Conversation) conversationStack.get(i-1);
				if (conversation.hasBean(name))
				{
					return conversation.getBean(name);
				}
			}
			*/
			Iterator iterConversations = conversations.values().iterator();
			while (iterConversations.hasNext())
			{
				Conversation conversation = (Conversation) iterConversations.next();
				if (conversation.hasBean(name))
				{
					return conversation.getBean(name);
				}
			}

			return null;
		}
	}

	/**
	 * find the persistence manager in the conversation stack
	 */
	public PersistenceManager getPersistenceManager()
	{
		synchronized (mutex)
		{
			touch();
			/*
			for (int i = conversationStack.size(); i>0; i--)
			{
				Conversation conversation = (Conversation) conversationStack.get(i-1);
				if (conversation.isPersistence())
				{
					return conversation.getPersistenceManager();
				}
			}
			*/
			Iterator iterConversations = conversations.values().iterator();
			while (iterConversations.hasNext())
			{
				Conversation conversation = (Conversation) iterConversations.next();
				if (conversation.isPersistence())
				{
					return conversation.getPersistenceManager();
				}
			}

			return null;
		}
	}

	/**
	 * detach all conversations from their underlaying persistence
	 */
	public void detachPersistence()
	{
		synchronized (mutex)
		{
			touch();
			/*
			for (int i = conversationStack.size(); i>0; i--)
			{
				Conversation conversation = (Conversation) conversationStack.get(i-1);
				if (conversation.isPersistence())
				{
					conversation.getPersistenceManager().detach();
				}
			}
			*/
			Iterator iterConversations = conversations.values().iterator();
			while (iterConversations.hasNext())
			{
				Conversation conversation = (Conversation) iterConversations.next();
				if (conversation.isPersistence())
				{
					conversation.getPersistenceManager().detach();
				}
			}
		}
	}

	/**
	 * detach all conversations from their underlaying persistence
	 */
	public void purgePersistence()
	{
		synchronized (mutex)
		{
			touch();
			/*
			for (int i = conversationStack.size(); i>0; i--)
			{
				Conversation conversation = (Conversation) conversationStack.get(i-1);
				if (conversation.isPersistence())
				{
					conversation.getPersistenceManager().purge();
				}
			}
			*/
			Iterator iterConversations = conversations.values().iterator();
			while (iterConversations.hasNext())
			{
				Conversation conversation = (Conversation) iterConversations.next();
				if (conversation.isPersistence())
				{
					conversation.getPersistenceManager().purge();
				}
			}
		}
	}

	/**
	 * attach all conversations to their underlaying persistence
	 */
	public void attachPersistence()
	{
		synchronized (mutex)
		{
			touch();
			/*
			for (int i = conversationStack.size(); i>0; i--)
			{
				Conversation conversation = (Conversation) conversationStack.get(i-1);
				if (conversation.isPersistence())
				{
					conversation.getPersistenceManager().attach();
				}
			}
			*/
			Iterator iterConversations = conversations.values().iterator();
			while (iterConversations.hasNext())
			{
				Conversation conversation = (Conversation) iterConversations.next();
				if (conversation.isPersistence())
				{
					conversation.getPersistenceManager().attach();
				}
			}
		}
	}

	public long getId()
	{
		return id;
	}
}