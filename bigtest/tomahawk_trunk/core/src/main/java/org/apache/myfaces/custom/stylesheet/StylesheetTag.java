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

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlOutputTextTagBase;

import javax.faces.component.UIComponent;

/**
 * @author mwessendorf (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StylesheetTag extends HtmlOutputTextTagBase
{


	private String _path = null;
	private String _media = null;
	private boolean _inline = false;
	private boolean _filtered = false;

	// User Role support
	private String _enabledOnUserRole;
	private String _visibleOnUserRole;

	public String getComponentType()
	{
		return Stylesheet.COMPONENT_TYPE;
	}

	public String getRendererType()
	{
		return "org.apache.myfaces.Stylesheet";
	}


	public void release()
	{
		super.release();
		_path = null;
		_media = null;
		_enabledOnUserRole = null;
		_visibleOnUserRole = null;
		_inline = false;
		_filtered = false;
	}

	/**
	 * overrides setProperties() form UIComponentTag.
	 */
	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);
		setStringProperty(component, "path", _path);
		setStringProperty(component, "media", _media);
		setBooleanProperty(component, "inline", Boolean.toString(_inline));
		setBooleanProperty(component, "filtered", Boolean.toString(_filtered));
		setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
		setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);

	}

	//---------------------------------------------only the Setters

	public void setPath(String path)
	{
		this._path = path;
	}

	public void setMedia(String media)
	{
		this._media = media;
	}

	public void setEnabledOnUserRole(String string)
	{
		_enabledOnUserRole = string;
	}

	public void setVisibleOnUserRole(String string)
	{
		_visibleOnUserRole = string;
	}

	public boolean isInline()
	{
		return _inline;
	}

	public void setInline(boolean inline)
	{
		this._inline = inline;
	}

	public boolean isFiltered()
	{
		return _filtered;
	}

	public void setFiltered(boolean filtered)
	{
		this._filtered = filtered;
	}
}