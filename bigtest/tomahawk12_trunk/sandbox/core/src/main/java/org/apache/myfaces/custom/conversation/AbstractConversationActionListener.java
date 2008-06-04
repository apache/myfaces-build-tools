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

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * base class to handle actions events
 * @author imario@apache.org 
 */
public abstract class AbstractConversationActionListener implements ActionListener, StateHolder
{
	private String conversationName;

	private transient boolean isTransient;
	
	public AbstractConversationActionListener()
	{
	}

	/**
	 * @return the conversation name this listener is associated to
	 */
	public String getConversationName()
	{
		return conversationName;
	}

	/**
	 * set the conversation name this listener should be associated to
	 * 
	 * @param conversationName
	 */
	public void setConversationName(String conversationName)
	{
		this.conversationName = conversationName;
	}


	public Object saveState(FacesContext context)
	{
		return new Object[]
		{
			conversationName
		};
	}

	public void restoreState(FacesContext context, Object state)
	{
		Object[] states = (Object[]) state;
		conversationName = (String) states[0];
	}

	public boolean isTransient()
	{
		return isTransient;
	}

	public void setTransient(boolean newTransientValue)
	{
		isTransient = newTransientValue;
	}

	public void processAction(ActionEvent actionEvent) throws AbortProcessingException
	{
		AbstractConversationComponent startOrEndconversation = ConversationUtils.findStartOrEndConversationComponent(actionEvent.getComponent(), getConversationName());
		
		doConversationAction(startOrEndconversation);
	}
	
	/**
	 * override this to do your conversation action
	 */
	public abstract void doConversationAction(AbstractConversationComponent abstractConversationComponent);
}
