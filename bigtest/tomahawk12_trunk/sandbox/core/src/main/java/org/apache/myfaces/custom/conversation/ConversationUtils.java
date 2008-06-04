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

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.el.MethodBinding;
import javax.faces.context.FacesContext;

public class ConversationUtils
{
	private ConversationUtils()
	{
	}

	/**
	 * Find the first parent which is a command
	 */
	public static UICommand findParentCommand(UIComponent base)
	{
		UIComponent parent = base;
		do
		{
			parent = parent.getParent();
			if (parent instanceof UICommand)
			{
				return (UICommand) parent;
			}
		}
		while (parent != null);

		return null;
	}

	/**
	 * Find a child start or end conversation component for the given conversation name
	 */
	public static AbstractConversationComponent findStartOrEndConversationComponent(UIComponent component, String conversationName)
	{
		Iterator iterComponents = component.getFacetsAndChildren();
		while (iterComponents.hasNext())
		{
			Object child = iterComponents.next();
			AbstractConversationComponent conversation;

			if (child instanceof UIStartConversation || child instanceof UIEndConversation)
			{
				conversation = (AbstractConversationComponent) child;
				if (conversation.getName().equals(conversationName))
				{
					return conversation;
				}
			}
			else if (child instanceof UIComponent)
			{
				conversation = findStartOrEndConversationComponent((UIComponent) child, conversationName);
				if (conversation != null)
				{
					return conversation;
				}
			}
		}

		return null;
	}

	public static String extractBeanName(ValueBinding vb)
	{
		String valueBinding = vb.getExpressionString();
		return valueBinding.substring(2, valueBinding.length()-1);
	}

	/**
	 * end and restart a conversation
	 */
	static void endAndRestartConversation(FacesContext context, String conversationName, Boolean restart, MethodBinding restartAction)
	{
		ConversationManager conversationManager = ConversationManager.getInstance(context);
		Conversation conversation = conversationManager.getConversation(conversationName);

		conversationManager.endConversation(conversationName, true);

		if (restart != null && restart.booleanValue() && conversation != null)
		{
			conversationManager.startConversation(conversationName, conversation.isPersistence());

			if (restartAction != null)
			{
				restartAction.invoke(context, null);
			}
		}
	}
}
