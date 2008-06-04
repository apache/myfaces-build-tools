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
package org.apache.myfaces.custom.focus;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @JSFComponent
 *   name = "s:focus"
 *   class = "org.apache.myfaces.custom.focus.HtmlFocus"
 *   superClass = "org.apache.myfaces.custom.focus.AbstractHtmlFocus"
 *   tagClass = "org.apache.myfaces.custom.focus.HtmlFocusTag"
 *   
 * @author Rogerio Pereira Araujo (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlFocus extends UIInput
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Focus";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Focus";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    private static final boolean DEFAULT_REMEMBER_CLIENT_FOCUS = false;

    private static Log log = LogFactory.getLog(AbstractHtmlFocus.class);

	/**
	 * The JSF id of the component to receive focus.
	 * 
	 * @JSFProperty
	 */
	public abstract String getFor();

    /**
     * @JSFProperty
     *   defaultValue="false"
     */
    public abstract boolean isRememberClientFocus();


    protected UIComponent findUIComponent()
	{
		String forStr = getFor();

		if (forStr == null)
		{
			throw new IllegalArgumentException("focus@for must be specified");
		}

		UIComponent forComp = findComponent(forStr);
		if (forComp == null)
		{
			log.warn("could not find UIComponent referenced by attribute focus@for = '"
					+ forStr + "'");
		}
		return forComp;
	}

}
