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

import javax.faces.component.UIComponent;
import javax.faces.el.MethodBinding;

/**
 * Ends a conversation
 *
 * @author imario@apache.org
 */
public class EndConversationTag extends AbstractConversationTag
{
	private String onOutcome;
	private String errorOutcome;
	private String restart;
	private String restartAction;

	public String getComponentType()
	{
		return UIEndConversation.COMPONENT_TYPE;
	}

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

		UIEndConversation endConversation = (UIEndConversation) component;

		setStringProperty(component, "onOutcome", getOnOutcome());
		setStringProperty(component, "errorOutcome", getErrorOutcome());
		setBooleanProperty(component, "restart", getRestart());

		if (getRestartAction() != null)
		{
			if (isValueReference(getRestartAction()))
			{
				MethodBinding mb = getFacesContext().getApplication().createMethodBinding(
					getRestartAction(), null);
				endConversation.setRestartAction(mb);
			}
			else
			{
				throw new IllegalArgumentException("argument 'restartAction' must be a method binding");
			}
		}
		else
		{
				endConversation.setRestartAction(null);
		}
	}

	public String getOnOutcome()
	{
		return onOutcome;
	}

	public void setOnOutcome(String onOutcome)
	{
		this.onOutcome = onOutcome;
	}

	public String getErrorOutcome()
	{
		return errorOutcome;
	}

	public void setErrorOutcome(String errorOutcome)
	{
		this.errorOutcome = errorOutcome;
	}

	public String getRestart()
	{
		return restart;
	}

	public void setRestart(String restart)
	{
		this.restart = restart;
	}

	public String getRestartAction()
	{
		return restartAction;
	}

	public void setRestartAction(String restartAction)
	{
		this.restartAction = restartAction;
	}
}
