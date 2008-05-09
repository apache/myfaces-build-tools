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
package org.apache.myfaces.custom.stylesheet;

import javax.faces.component.UIOutput;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;


/**
 * @JSFComponent
 *   name = "t:stylesheet"
 *   class = "org.apache.myfaces.custom.stylesheet.Stylesheet"
 *   superClass = "org.apache.myfaces.custom.stylesheet.AbstractStylesheet"
 *   tagClass = "org.apache.myfaces.custom.stylesheet.StylesheetTag"
 * 
 * @author mwessendorf (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractStylesheet extends UIOutput
    implements UserRoleAware    
{
	public static final String COMPONENT_TYPE = "org.apache.myfaces.Stylesheet";
	public static final String COMPONENT_FAMILY = "javax.faces.Output";
	private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Stylesheet";

	/**
	 * @JSFProperty
	 */
	public abstract String getPath();

	/**
	 * @JSFProperty
	 *   defaultValue = "false"
	 * @return true if the styles are inlined to the jsp file
	 */
	public abstract boolean isInline();

	/**
	 * @JSFProperty
	 *   defaultValue = "false"
	 * @return true if the stylesheet should be filtered before sending to the browser
	 */
	public abstract boolean isFiltered();

    /**
     * @JSFProperty
     */
	public abstract String getMedia();

	public boolean isRendered()
	{
		if (!UserRoleUtils.isVisibleOnUserRole(this))
		{
			return false;
		}
		return super.isRendered();
	}

}