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
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.FacesException;
import java.util.Collection;

/**
 * a facade for the original method binding to deal with end conversation conditions
 *
 * @author imario@apache.org
 */
public class EndConversationMethodBindingFacade extends MethodBinding implements StateHolder
{
	private MethodBinding original;
	private String conversationName;
	private Collection onOutcomes;
	private String errorOutcome;
	private Boolean restart;
	private MethodBinding restartAction;

	private boolean _transient = false;

	public EndConversationMethodBindingFacade()
	{
	}

	public EndConversationMethodBindingFacade(
		String conversation, Collection onOutcomes, MethodBinding original,
		String errorOutcome, Boolean restart, MethodBinding restartAction)
	{
		this.original = original;
		this.conversationName = conversation;
		this.onOutcomes = onOutcomes;
		this.errorOutcome = errorOutcome;
		this.restart = restart;
		this.restartAction = restartAction;
	}

	public String getConversationName()
	{
		return conversationName;
	}

	public String getExpressionString()
	{
		if (original == null)
		{
			return null;
		}
		return original.getExpressionString();
	}

	public Class getType(FacesContext context) throws MethodNotFoundException
	{
		if (original == null)
		{
			return null;
		}
		return original.getType(context);
	}

	public Object invoke(FacesContext context, Object[] values) throws EvaluationException, MethodNotFoundException
	{
		boolean ok = false;
		Object returnValue = null;
		try
		{
			if (original != null)
			{
				returnValue = original.invoke(context, values);
			}
			ok = true;
		}
		catch (Throwable t)
		{
			ConversationManager conversationManager = ConversationManager.getInstance(context);
			conversationManager.purgePersistence();

			if (errorOutcome != null)
			{
				conversationManager.getMessager().setConversationException(context, t);

				returnValue = errorOutcome;
			}
			else
			{
				throw new FacesException(t);
			}
		}
		finally
		{
			boolean end = true;
			if (ok)
			{
				if (onOutcomes != null && onOutcomes.size() > 0)
				{
					end = onOutcomes.contains(returnValue);
				}

				if (end)
				{
					ConversationUtils.endAndRestartConversation(context, conversationName, restart, restartAction);
				}
			}
		}
		return returnValue;
	}

	public void setTransient(boolean newTransientValue)
	{
		_transient = newTransientValue;
	}

	public boolean isTransient()
	{
		return _transient;
	}

	public void restoreState(FacesContext context, Object states)
	{
		Object[] state = (Object[]) states;

		original = (MethodBinding) UIComponentBase.restoreAttachedState(context, state[0]);
		conversationName = (String) state[1];
		onOutcomes = (Collection) state[2];
		errorOutcome = (String) state[3];
		restart = (Boolean) state[4];
		restartAction = (MethodBinding) UIComponentBase.restoreAttachedState(context, state[5]);
	}

	public Object saveState(FacesContext context)
	{
		return new Object[]
			{
				UIComponentBase.saveAttachedState(context, original),
				conversationName,
				onOutcomes,
				errorOutcome,
				restart,
				UIComponentBase.saveAttachedState(context, restartAction)
			};
	}
}
