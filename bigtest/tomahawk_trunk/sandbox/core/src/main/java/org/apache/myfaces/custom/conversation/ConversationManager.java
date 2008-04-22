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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectStreamException;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.requestParameterProvider.RequestParameterProviderManager;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * The manager will deal with the various contexts in the current session.
 * A new context will be created if the current window has none associated.
 *
 * @author imario@apache.org
 */
public class ConversationManager implements Serializable
{
	private final static Log log = LogFactory.getLog(ConversationManager.class);

	public final static String CONVERSATION_CONTEXT_PARAM = "conversationContext";

	private final static String INIT_PERSISTENCE_MANAGER_FACOTRY = "org.apache.myfaces.conversation.PERSISTENCE_MANAGER_FACTORY";
	private final static String INIT_MESSAGER = "org.apache.myfaces.conversation.MESSAGER";
	private final static String INIT_BEAN_ELEVATOR = "org.apache.myfaces.conversation.BEAN_ELEVATOR";

	private final static String CONVERSATION_MANAGER_KEY = "org.apache.myfaces.ConversationManager";
	private final static String CONVERSATION_CONTEXT_REQ = "org.apache.myfaces.ConversationManager.conversationContext";

	private static long NEXT_CONVERSATION_CONTEXT = 1;

	private PersistenceManagerFactory persistenceManagerFactory;
	private ConversationMessager conversationMessager;
	private ConversationBeanElevator conversationBeanElevator;

	private final Map conversationContexts = new HashMap();

	// private final List registeredEndConversations = new ArrayList(10);

	private class ContextWiperThread extends Thread
	{
		private final static long CHECK_TIME = 5 * 60 * 1000; // every 5 min
		public ContextWiperThread()
		{
			setDaemon(true);
			setName("ConversationManager.ContextWiperThread");
		}

		public void run()
		{
			while (!isInterrupted())
			{
				checkContextTimeout();

				try
				{
					Thread.sleep(CHECK_TIME);
				}
				catch (InterruptedException e)
				{
					log.warn(e.getLocalizedMessage(), e);
				}
			}
		}
	}
	private ContextWiperThread wiperThread = new ContextWiperThread();

	protected ConversationManager()
	{
		wiperThread.start();
	}

	/**
	 * Get the conversation manager
	 */
	public static ConversationManager getInstance()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null)
		{
			// check if we have a chance outside the FacesContext
			ConversationManager cm = ConversationServletFilter.getConversationManager();
			if (cm != null)
			{
				return cm;
			}

			throw new IllegalStateException("no faces context available");
		}
		return getInstance(context);
	}

	/**
	 * Get the conversation manager
	 */
	public static ConversationManager getInstance(FacesContext context)
	{
		ConversationManager conversationManager = (ConversationManager) context.getExternalContext().getSessionMap().get(CONVERSATION_MANAGER_KEY);
		if (conversationManager == null)
		{
			if (!Boolean.TRUE.equals(context.getExternalContext().getRequestMap().get(ConversationServletFilter.CONVERSATION_FILTER_CALLED)))
			{
				throw new IllegalStateException("ConversationServletFilter not called. Please configure the filter org.apache.myfaces.custom.conversation.ConversationServletFilter in your web.xml to cover your faces requests.");
			}

			conversationManager = createConversationManager();

			// initialize environmental systems
			RequestParameterProviderManager.getInstance(context).register(new ConversationRequestParameterProvider());

			// set mark
			context.getExternalContext().getSessionMap().put(CONVERSATION_MANAGER_KEY, conversationManager);
		}

		return conversationManager;
	}

	protected static ConversationManager createConversationManager()
	{
		ConversationManager conversationManager;

		// create manager
		conversationManager = new ConversationManager();

		// initialize the messager
		conversationManager.createMessager();

		// initialize the bean elevator
		conversationManager.createBeanElevator();

		return conversationManager;
	}

	/**
	 * Get the conversation manager from the http session. This will <b>not</b> create a conversation manager if none exists.
	 */
	public static ConversationManager getInstance(HttpSession session)
	{
		if (session == null)
		{
			return null;
		}
		return (ConversationManager) session.getAttribute(CONVERSATION_MANAGER_KEY);
	}

	/**
	 * Get the current, or create a new unique conversationContextId.<br />
	 * The current conversationContextId will retrieved from the request parameters, if we cant find it there
	 * a new one will be created. In either case the result will be stored within the request for faster lookup.
	 */
	protected Long getConversationContextId()
	{
		Map requestMap;
		Map requestParameterMap;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null)
		{
			requestMap = context.getExternalContext().getRequestMap();
			requestParameterMap = context.getExternalContext().getRequestParameterMap();
		}
		else
		{
			ConversationExternalContext ccontext = ConversationServletFilter.getConversationExternalContext();
			if (ccontext != null)
			{
				requestMap = ccontext.getRequestMap();
				requestParameterMap = ccontext.getRequestParameterMap();
			}
			else
			{
				throw new IllegalStateException("cant find a requestMap or requestParameterMap");
			}
		}

		Long conversationContextId = (Long) requestMap.get(CONVERSATION_CONTEXT_REQ);
		if (conversationContextId == null)
		{
			if (requestParameterMap.containsKey(CONVERSATION_CONTEXT_PARAM))
			{
				String urlConversationContextId = requestParameterMap.get(CONVERSATION_CONTEXT_PARAM).toString();
				conversationContextId = new Long(Long.parseLong(urlConversationContextId, Character.MAX_RADIX));
			}
			else
			{
				synchronized (ConversationManager.class)
				{
					conversationContextId = new Long(NEXT_CONVERSATION_CONTEXT);
					NEXT_CONVERSATION_CONTEXT++;
				}
			}

			requestMap.put(CONVERSATION_CONTEXT_REQ, conversationContextId);
		}

		return conversationContextId;
	}

	/**
	 * Get the conversation context for the given id
	 */
	protected ConversationContext getConversationContext(Long conversationContextId)
	{
		synchronized (conversationContexts)
		{
			return (ConversationContext) conversationContexts.get(conversationContextId);
		}
	}

	/**
	 * Get the conversation context for the given id. <br />
	 * If there is no conversation context a new one will be created
	 */
	protected ConversationContext getOrCreateConversationContext(Long conversationContextId)
	{
		synchronized (conversationContexts)
		{
			ConversationContext conversationContext = (ConversationContext) conversationContexts.get(conversationContextId);
			if (conversationContext == null)
			{
				conversationContext = new ConversationContext(conversationContextId.longValue());
				conversationContexts.put(conversationContextId, conversationContext);
			}

			return conversationContext;
		}
	}

	/**
	 * Destroy the given conversation context
	 */
	protected void destroyConversationContext(Long conversationContextId)
	{
		synchronized (conversationContexts)
		{
			conversationContexts.remove(conversationContextId);
		}
	}

	/**
	 * Start a conversation
	 * @see ConversationContext#startConversation(String, boolean)
	 */
	public void startConversation(String name, boolean persistence)
	{
		Long conversationContextId = getConversationContextId();
		ConversationContext conversationContext = getOrCreateConversationContext(conversationContextId);
		conversationContext.startConversation(name, persistence);
	}

	/**
	 * End a conversation
	 * @see ConversationContext#endConversation(String, boolean)
	 */
	public void endConversation(String name, boolean regularEnd)
	{
		Long conversationContextId = getConversationContextId();
		ConversationContext conversationContext = getConversationContext(conversationContextId);
		if (conversationContext != null)
		{
			conversationContext.endConversation(name, regularEnd);

			if (!conversationContext.hasConversations())
			{
				destroyConversationContext(conversationContextId);
			}
		}
	}

	/**
	 * Get the conversation with the given name
	 *
	 * @return null if no conversation context is active or if the conversation did not exist.
	 */
	public Conversation getConversation(String name)
	{
		ConversationContext conversationContext = getConversationContext();
		if (conversationContext == null)
		{
			return null;
		}
		return conversationContext.getConversation(name);
	}

	/**
	 * Find the conversation which holds the given instance
	 *
	 * @return null if no conversation context is active or if the conversation did not exist.
	 */
	public Conversation findConversation(Object instance)
	{
		ConversationContext conversationContext = getConversationContext();
		if (conversationContext == null)
		{
			return null;
		}
		return conversationContext.findConversation(instance);
	}

	/**
	 * check if the given conversation is active
	 */
	public boolean hasConversation(String name)
	{
		ConversationContext conversationContext = getConversationContext();
		if (conversationContext == null)
		{
			return false;
		}
		return conversationContext.hasConversation(name);
	}

	/**
	 * Get the current conversation context.
	 * @return null if there is no context active
	 */
	protected ConversationContext getConversationContext()
	{
		Long conversationContextId = getConversationContextId();
		ConversationContext conversationContext = getConversationContext(conversationContextId);
		return conversationContext;
	}

	/**
	 * Register the conversation to be ended after the cycle
	protected void registerEndConversation(String conversationName)
	{
		synchronized (registeredEndConversations)
		{
			registeredEndConversations.add(conversationName);
		}
	}
	 */

	/**
	 * Get all registered conversations
	protected List getRegisteredEndConversations()
	{
		return registeredEndConversations;
	}
	 */

	/**
	 * check if we have a conversation context
	 */
	public boolean hasConversationContext()
	{
		FacesContext context = FacesContext.getCurrentInstance();

		return
			(context.getExternalContext().getRequestMap().containsKey(CONVERSATION_CONTEXT_REQ) ||
			context.getExternalContext().getRequestParameterMap().containsKey(CONVERSATION_CONTEXT_PARAM)) &&
			getConversationContext() != null;
	}

	/**
	 * get the persistence manager responsible for the current conversation
	 */
	public PersistenceManager getPersistenceManager()
	{
		ConversationContext conversationContext = getConversationContext();
		if (conversationContext == null)
		{
			return null;
		}

		return conversationContext.getPersistenceManager();
	}

	/**
	 * create a persistence manager
	 */
	protected PersistenceManager createPersistenceManager()
	{
		return getPersistenceManagerFactory().create();
	}

	/**
	 * Get the Messager used to inform the user about anomalies.<br />
	 * The factory can be configured in your web.xml using the init parameter named
	 * <code>org.apache.myfaces.conversation.MESSAGER</code>
	 */
	protected ConversationMessager getMessager()
	{
		return conversationMessager;
	}

	/**
	 * Create the Messager used to inform the user about anomalies.<br />
	 * The factory can be configured in your web.xml using the init parameter named
	 * <code>org.apache.myfaces.conversation.MESSAGER</code>
	 */
	protected void createMessager()
	{
		String conversationMessagerName = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(INIT_MESSAGER);
		if (conversationMessagerName == null)
		{
			conversationMessager = new DefaultConversationMessager();
		}
		else
		{
			try
			{
				conversationMessager = (ConversationMessager) ClassUtils.classForName(conversationMessagerName).newInstance();
			}
			catch (InstantiationException e)
			{
				throw new FacesException("error creating messager: " + conversationMessagerName, e);
			}
			catch (IllegalAccessException e)
			{
				throw new FacesException("error creating messager: " + conversationMessagerName, e);
			}
			catch (ClassNotFoundException e)
			{
				throw new FacesException("error creating messager: " + conversationMessagerName, e);
			}
		}
	}

	/**
	 * Create the BeanElevator used to find a bean within the different contexts and elevate it
	 * into the conversation scope.<br />
	 * You can configure it in your web.xml using the init parameter named
	 * <code>org.apache.myfaces.conversation.BEAN_ELEVATOR</code>
	 */
	protected void createBeanElevator()
	{
		String conversationBeanElevatorName = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(INIT_BEAN_ELEVATOR);
		if (conversationBeanElevatorName == null)
		{
			conversationBeanElevator = new DefaultConversationBeanElevator();
		}
		else
		{
			try
			{
				conversationBeanElevator = (DefaultConversationBeanElevator) ClassUtils.classForName(conversationBeanElevatorName).newInstance();
			}
			catch (InstantiationException e)
			{
				throw new FacesException("error creating bean elevator: " + conversationBeanElevatorName, e);
			}
			catch (IllegalAccessException e)
			{
				throw new FacesException("error creating bean elevator: " + conversationBeanElevatorName, e);
			}
			catch (ClassNotFoundException e)
			{
				throw new FacesException("error creating bean elevator: " + conversationBeanElevatorName, e);
			}
		}
	}

	/**
	 * Get the persistenceManagerFactory.<br />
	 * The factory can be configured in your web.xml using the init parameter named
	 * <code>org.apache.myfaces.conversation.PERSISTENCE_MANAGER_FACTORY</code>
	 */
	protected PersistenceManagerFactory getPersistenceManagerFactory()
	{
		if (persistenceManagerFactory == null)
		{
			String persistenceManagerFactoryName = (String) ConversationServletFilter.getConversationExternalContext().getInitParameterMap().get(INIT_PERSISTENCE_MANAGER_FACOTRY);
			if (persistenceManagerFactoryName == null)
			{
				throw new IllegalArgumentException("please configure '" + INIT_PERSISTENCE_MANAGER_FACOTRY + "' in your web.xml");
			}
			try
			{
				persistenceManagerFactory =  (PersistenceManagerFactory) ClassUtils.classForName(persistenceManagerFactoryName).newInstance();
			}
			catch (InstantiationException e)
			{
				throw new FacesException("error creating persistenceManagerFactory named: " + persistenceManagerFactoryName, e);
			}
			catch (IllegalAccessException e)
			{
				throw new FacesException("error creating persistenceManagerFactory named: " + persistenceManagerFactoryName, e);
			}
			catch (ClassNotFoundException e)
			{
				throw new FacesException("error creating persistenceManagerFactory named: " + persistenceManagerFactoryName, e);
			}
		}

		return persistenceManagerFactory;
	}

	protected void attachPersistence()
	{
		ConversationContext conversationContext = getConversationContext();
		if (conversationContext != null)
		{
			conversationContext.attachPersistence();
		}
	}

	protected void detachPersistence()
	{
		ConversationContext conversationContext = getConversationContext();
		if (conversationContext != null)
		{
			conversationContext.detachPersistence();
		}
	}

	protected void purgePersistence()
	{
		ConversationContext conversationContext = getConversationContext();
		if (conversationContext != null)
		{
			conversationContext.purgePersistence();
		}
	}

	protected void checkContextTimeout()
	{
		synchronized (conversationContexts)
		{
			long timeToLive = 30 * 60 * 1000;
			long checkTime = System.currentTimeMillis();

			Iterator iterContexts = conversationContexts.values().iterator();
			while (iterContexts.hasNext())
			{
				ConversationContext conversationContext = (ConversationContext) iterContexts.next();
				if (conversationContext.getLastAccess() + timeToLive < checkTime)
				{
					if (log.isDebugEnabled())
					{
						log.debug("end conversation context due to timeout: " + conversationContext.getId());
					}
					conversationContext.shutdownContext();
					iterContexts.remove();
				}
			}
		}
	}


	/**
	 * the bean elevator used to get beans int the conversation scope
	 */
	public ConversationBeanElevator getConversationBeanElevator()
	{
		return conversationBeanElevator;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		// the conversation manager is not (yet) serializable, we just implement it
		// to make it work with distributed sessions
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		// nothing written, so nothing to read
	}

	private Object readResolve() throws ObjectStreamException
	{
		// do not return a real object, that way on first request a new conversation manager will be created
		return null;
	}
}