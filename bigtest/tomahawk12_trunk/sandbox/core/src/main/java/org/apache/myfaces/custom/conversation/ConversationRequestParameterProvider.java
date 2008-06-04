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

import org.apache.myfaces.custom.requestParameterProvider.RequestParameterProvider;

/**
 * adds the required fields (conversationContext) to the request parameters.
 * 
 * @author imario@apache.org
 */
public class ConversationRequestParameterProvider implements RequestParameterProvider
{
	private final static String[] REQUEST_PARAMETERS = new String[]
               {
		ConversationManager.CONVERSATION_CONTEXT_PARAM
               };
	
	public String getFieldValue(String field)
	{
		if (UISeparateConversationContext.isInSeparationMode())
		{
			return null;
		}
		
		ConversationManager conversationManager = ConversationManager.getInstance();
		if (conversationManager == null)
		{
			throw new IllegalStateException("can find the conversationManager");
		}
		if (!conversationManager.hasConversationContext())
		{
			return null;
		}
		
		return Long.toString(conversationManager.getConversationContextId().longValue(), Character.MAX_RADIX);
	}

	public String[] getFields()
	{
		if (UISeparateConversationContext.isInSeparationMode())
		{
			return null;
		}

		ConversationManager conversationManager = ConversationManager.getInstance();
		if (conversationManager == null)
		{
			throw new IllegalStateException("can find the conversationManager");
		}
		if (!conversationManager.hasConversationContext())
		{
			return null;
		}
		
		return REQUEST_PARAMETERS; 
	}
}
