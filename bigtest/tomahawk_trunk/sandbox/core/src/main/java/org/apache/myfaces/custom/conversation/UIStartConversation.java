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

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * start a conversation
 *
 * @author imario@apache.org
 */
public class UIStartConversation extends AbstractConversationComponent
{
    public final static String COMPONENT_TYPE = "org.apache.myfaces.StartConversation";
    private final static String CONVERSATION_SYSTEM_SETUP = "org.apache.myfaces.ConversationSystemSetup";

    private boolean inited;
    private Boolean persistence;

	public static class ConversationStartAction extends AbstractConversationActionListener
	{
		private boolean persistence;
		private List beanToElevate;

		public void doConversationAction(AbstractConversationComponent abstractConversationComponent)
		{
			ConversationManager conversationManager = ConversationManager.getInstance();
			conversationManager.startConversation(getConversationName(), isPersistence());

			if (beanToElevate != null)
			{
				FacesContext context = FacesContext.getCurrentInstance();

				Iterator iterBeans = beanToElevate.iterator();
				while (iterBeans.hasNext())
				{
					String vb = (String) iterBeans.next();
					UIConversation.elevateBean(
						context,
						getConversationName(),
						context.getApplication().createValueBinding(vb));
				}
			}
		}

		public boolean isPersistence()
		{
			return persistence;
		}

		public void setPersistence(boolean persistence)
		{
			this.persistence = persistence;
		}

		public void addBeanToElevate(String beanBinding)
		{
			if (beanToElevate == null)
			{
				beanToElevate = new ArrayList();
			}
			beanToElevate.add(beanBinding);
		}


		public Object saveState(FacesContext context)
		{
			return new Object[]
			{
				super.saveState(context),
				Boolean.valueOf(persistence),
				beanToElevate
			};
		}

		public void restoreState(FacesContext context, Object state)
		{
			Object[] states = (Object[]) state;
			super.restoreState(context, states[0]);
			persistence = ((Boolean) states[1]).booleanValue();
			beanToElevate = (List) states[2];
		}
	}

	public void encodeEnd(FacesContext context) throws IOException
	{
		super.encodeEnd(context);

		setupConversationSystem(context);

		UICommand command = ConversationUtils.findParentCommand(this);
		if (command != null)
		{
			if (!inited)
			{
				ConversationStartAction actionListener = new ConversationStartAction();
				actionListener.setConversationName(getName());
				actionListener.setPersistence(toBoolean(getPersistence()));
				command.addActionListener(actionListener);

				Iterator iterChildren = getChildren().iterator();
				while (iterChildren.hasNext())
				{
					Object child = iterChildren.next();
					if (child instanceof UIConversation)
					{
						UIConversation conversation = (UIConversation) child;
						actionListener.addBeanToElevate(conversation.getBeanBinding().getExpressionString());
					}
				}

				inited = true;
			}
		}
		else
		{
			ConversationManager conversationManager = ConversationManager.getInstance();
			conversationManager.startConversation(getName(), toBoolean(getPersistence()));
		}
	}

	protected boolean toBoolean(Boolean bool)
	{
		if (bool != null)
		{
			return bool.booleanValue();
		}
		return false;
	}

	protected void setupConversationSystem(FacesContext context)
	{
		if (Boolean.TRUE.equals(context.getExternalContext().getApplicationMap().get(CONVERSATION_SYSTEM_SETUP)))
		{
			return;
		}

		VariableResolver originalVR = context.getApplication().getVariableResolver();
		context.getApplication().setVariableResolver(new ConversationVariableResolver(originalVR));

		context.getExternalContext().getApplicationMap().put(CONVERSATION_SYSTEM_SETUP, Boolean.TRUE);
	}

	public void restoreState(FacesContext context, Object state)
	{
		Object[] states = (Object[]) state;
		super.restoreState(context, states[0]);
		inited = ((Boolean) states[1]).booleanValue();
		persistence = (Boolean) states[2];
	}

	public Object saveState(FacesContext context)
	{
		return new Object[]
		                  {
				super.saveState(context),
				inited?Boolean.TRUE:Boolean.FALSE,
				persistence
		                  };
	}

	public Boolean getPersistence()
	{
        if (persistence != null)
        {
        		return persistence;
        }
        ValueBinding vb = getValueBinding("persistence");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
	}

	public void setPersistence(Boolean persistence)
	{
		this.persistence = persistence;
	}
}