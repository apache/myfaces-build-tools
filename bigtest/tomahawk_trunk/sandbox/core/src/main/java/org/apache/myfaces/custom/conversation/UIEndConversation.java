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

import org.apache.myfaces.shared_tomahawk.util.StringUtils;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * end a conversation
 *
 * @JSFComponent
 *   name = "s:endConversation"
 *   tagClass = "org.apache.myfaces.custom.conversation.EndConversationTag"
 *   
 * @author imario@apache.org
 */
public class UIEndConversation extends AbstractConversationComponent
{
	public static final String COMPONENT_TYPE = "org.apache.myfaces.EndConversation";

	private String onOutcome;
	private String errorOutcome;
	private Boolean restart;
	private MethodBinding restartAction;

	private boolean inited = false;

	/*
	public static class ConversationEndAction extends AbstractConversationActionListener
	{
		public void doConversationAction(AbstractConversationComponent abstractConversationComponent)
		{
			ConversationManager.getInstance().registerEndConversation(getConversationName());
		}
	}
	*/

	public void encodeBegin(FacesContext context) throws IOException
	{
		super.encodeBegin(context);

		UICommand command = ConversationUtils.findParentCommand(this);
		if (command != null)
		{
			if (!inited)
			{
				/*
				ConversationEndAction actionListener = new ConversationEndAction();
				actionListener.setConversationName(getName());
				command.addActionListener(actionListener);
				*/
				MethodBinding original = command.getAction();
				command.setAction(new EndConversationMethodBindingFacade(
					getName(),
					getOnOutcomes(),
					original,
					getErrorOutcome(),
					getRestart(),
					getRestartAction()));
				inited = true;
			}
		}
		else
		{
			ConversationUtils.endAndRestartConversation(context,
				getName(),
				getRestart(),
				getRestartAction());
		}
	}

	private Collection getOnOutcomes()
	{
		String onOutcome = getOnOutcome();
		if (onOutcome == null || onOutcome.trim().length() < 1)
		{
			return null;
		}

		return Arrays.asList(StringUtils.trim(StringUtils.splitShortString(onOutcome, ',')));
	}

	public void restoreState(FacesContext context, Object state)
	{
		Object[] states = (Object[]) state;
		super.restoreState(context, states[0]);
		inited = ((Boolean) states[1]).booleanValue();
		onOutcome = (String) states[2];
		errorOutcome = (String) states[3];
		restart = (Boolean) states[4];
		restartAction = (MethodBinding) restoreAttachedState(context, states[5]);
	}

	public Object saveState(FacesContext context)
	{
		return new Object[]
			{
				super.saveState(context),
				inited ? Boolean.TRUE : Boolean.FALSE,
				onOutcome,
				errorOutcome,
				restart,
				saveAttachedState(context, restartAction)
			};
	}

	public String getOnOutcome()
	{
		if (onOutcome != null)
		{
			return onOutcome;
		}
		ValueBinding vb = getValueBinding("onOutcome");
		if (vb == null)
		{
			return null;
		}
		return (String) vb.getValue(getFacesContext());
	}

	public void setOnOutcome(String onOutcome)
	{
		this.onOutcome = onOutcome;
	}

	public String getErrorOutcome()
	{
		if (errorOutcome != null)
		{
			return errorOutcome;
		}
		ValueBinding vb = getValueBinding("errorOutcome");
		if (vb == null)
		{
			return null;
		}
		return (String) vb.getValue(getFacesContext());
	}

	public void setErrorOutcome(String errorOutcome)
	{
		this.errorOutcome = errorOutcome;
	}

	public Boolean getRestart()
	{
		if (restart != null)
		{
			return restart;
		}
		ValueBinding vb = getValueBinding("restart");
		if (vb == null)
		{
			return null;
		}
		return (Boolean) vb.getValue(getFacesContext());
	}

	public void setRestart(Boolean restart)
	{
		this.restart = restart;
	}

	public MethodBinding getRestartAction()
	{
		return restartAction;
	}

	public void setRestartAction(MethodBinding restartAction)
	{
		this.restartAction = restartAction;
	}
}
