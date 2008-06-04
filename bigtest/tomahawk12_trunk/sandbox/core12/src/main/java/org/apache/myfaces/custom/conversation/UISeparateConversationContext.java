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

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * Separates the current context from the children. e.g. commandLinks will start a 
 * new conversation context
 * 
 * separate the current context from the to be rendered children.
 * E.g. when you render commandLinks they will start a new conversationContext
 * 
 *   
 * @author imario@apache.org
 */
public class UISeparateConversationContext extends UIComponentBase
{
	public static final String COMPONENT_FAMILY = "javax.faces.Component";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SeparateConversationContext";

    private final static ThreadLocal inSeperationMode = new ThreadLocal();
    
	public static void setInSeparationMode(boolean seperationMode)
	{
		inSeperationMode.set(seperationMode?Boolean.TRUE:Boolean.FALSE);
	}

	public static boolean isInSeparationMode()
	{
		return Boolean.TRUE.equals(inSeperationMode.get());
	}
	
    public void encodeBegin(FacesContext context) throws IOException
	{
		super.encodeBegin(context);

		setInSeparationMode(true);
	}

	public void encodeChildren(FacesContext context) throws IOException
	{
		try
		{
			RendererUtils.renderChildren(context, this);
		}
		finally
		{
			setInSeparationMode(false);
		}
	}

    public boolean getRendersChildren()
    {
    	return true;
    }
    
	public String getFamily()
	{
		return COMPONENT_FAMILY;
	}
}