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

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;

/**
 * puts a bean into the conversation context
 * 
 * add a bean under context control
 *
 * @author imario@apache.org
 */
public class UIConversation extends AbstractConversationComponent
{
	private final static Log log = LogFactory.getLog(UIConversation.class);

	public static final String COMPONENT_TYPE = "org.apache.myfaces.Conversation";

	public void encodeBegin(FacesContext context) throws IOException
	{
		super.encodeBegin(context);

		UIComponent cmp = getParent();
		if (cmp instanceof UIStartConversation)
		{
			// start conversation should to the work
			return;
		}

		if (getName() == null)
		{
			throw new IllegalArgumentException("conversation name (attribute name=) required if used outside of startConversation tag");
		}

		elevateBean(context, getName(), getBeanBinding());
	}

	ValueBinding getBeanBinding()
	{
		return getValueBinding("value");
	}
	
	public static void elevateBean(FacesContext context, String conversationName, ValueBinding valueBinding)
	{
		Conversation conversation = ConversationManager.getInstance().getConversation(conversationName);
		if (conversation == null)
		{
			log.debug("no conversation named '" + conversationName + "' running - can't elevate bean '" + valueBinding.getExpressionString());
			return;
		}
		ConversationManager.getInstance().getConversationBeanElevator().elevateBean(context, conversation, valueBinding);
	}
}
