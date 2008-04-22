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
package org.apache.myfaces.custom.dynaForm.guiBuilder.impl.jsf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.dynaForm.guiBuilder.impl.myfaces.MyFacesCheck;
import org.apache.myfaces.custom.dynaForm.guiBuilder.impl.myfaces.MyFacesGuiBuilder;

public class JsfGuiBuilderFactory
{
	public final static String CONTEXT_GUI_BUILDER = "org.apache.myfaces.custom.dynaForm.GUI_BUILDER";
	
	private JsfGuiBuilderFactory()
	{
	}
	
	public static JsfGuiBuilder create(final FacesContext facesContext)
	{
		JsfGuiBuilder guiBuilder = null;
		
		String guiBuilderName = facesContext.getExternalContext().getInitParameter(CONTEXT_GUI_BUILDER);
		if (guiBuilderName == null)
		{
			guiBuilder = createGuiBuilderInternal();
		}
		else
		{
			try
			{
				Class guiBuilderClass = Class.forName(guiBuilderName);
				try
				{
					// try to find a decorator constructor
					Constructor decoratorConst = guiBuilderClass.getConstructor(new Class[]{JsfGuiBuilder.class});
					return (JsfGuiBuilder) decoratorConst.newInstance(new Object[]{
							createGuiBuilderInternal()
					});
				}
				catch (NoSuchMethodException e)
				{
					// not - so letz create a plain instance
					return (JsfGuiBuilder) guiBuilderClass.newInstance();
				}
			}
			catch (IllegalArgumentException e)
			{
				throw new FacesException(e);
			}
			catch (InvocationTargetException e)
			{
				throw new FacesException(e);
			}
			catch (SecurityException e)
			{
				throw new FacesException(e);
			}
			catch (InstantiationException e)
			{
				throw new FacesException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new FacesException(e);
			}
			catch (ClassNotFoundException e)
			{
				throw new FacesException(e);
			}
		}
		return guiBuilder;
	}

	protected static JsfGuiBuilder createGuiBuilderInternal()
	{
		JsfGuiBuilder guiBuilder;
		if (MyFacesCheck.isMyFacesAvailable())
		{
			guiBuilder = new MyFacesGuiBuilder();			
		}
		else
		{
			guiBuilder = new JsfGuiBuilder();			
		}
		return guiBuilder;
	}
}
