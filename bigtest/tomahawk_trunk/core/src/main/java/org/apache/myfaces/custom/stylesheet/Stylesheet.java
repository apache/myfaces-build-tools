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

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * @author mwessendorf (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public class Stylesheet extends UIOutput
{

	public static final String COMPONENT_TYPE = "org.apache.myfaces.Stylesheet";
	public static final String COMPONENT_FAMILY = "javax.faces.Output";
	private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Stylesheet";

	private String _path = null;
	private Boolean _filtered = null;
	private Boolean _inline = null;
	private String _media = null;
	private String _enabledOnUserRole = null;
	private String _visibleOnUserRole = null;


	// ------------------------------------------------------------ Constructors
	public Stylesheet()
	{

		setRendererType(DEFAULT_RENDERER_TYPE);

	}


	public String getFamily()
	{

		return COMPONENT_FAMILY;

	}


	public String getPath()
	{

		if (_path != null)
		{
			return _path;
		}
		ValueBinding vb = getValueBinding("path");
		return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
	}

	public void setPath(String path)
	{
		this._path = path;
	}

	/**
	 * @return true if the styles are inlined to the jsp file
	 */
	public boolean isInline()
	{
		if (this._inline != null)
		{
			return this._inline.booleanValue();
		}
		ValueBinding vb = getValueBinding("inline");
		return ((vb != null) ? (Boolean) vb.getValue(getFacesContext())
			: Boolean.FALSE).booleanValue();
	}

	/**
	 * @param inline if true, the css-file is inlined to the jsp file. Default is false.
	 */
	public void setInline(boolean inline)
	{
		this._inline = Boolean.valueOf(inline);
	}

	/**
	 * @return true if the stylesheet should be filtered before sending to the browser
	 */
	public boolean isFiltered()
	{
		if (this._filtered != null)
		{
			return this._filtered.booleanValue();
		}
		ValueBinding vb = getValueBinding("filtered");
		return ((vb != null) ? (Boolean) vb.getValue(getFacesContext())
			: Boolean.FALSE).booleanValue();
	}

	/**
	 * @param filtered filter stylesheet if true
	 */
	public void setFiltered(boolean filtered)
	{
		this._filtered = Boolean.valueOf(filtered);
	}

	public String getMedia()
	{
		if (this._media != null)
		{
			return this._media;
		}
		ValueBinding vb = getValueBinding("media");
		return ((vb != null) ? (String) vb.getValue(getFacesContext()) : null);
	}

	public void setMedia(String media)
	{
		this._media = media;
	}

	public void setEnabledOnUserRole(String enabledOnUserRole)
	{
		_enabledOnUserRole = enabledOnUserRole;
	}

	public String getEnabledOnUserRole()
	{
		if (_enabledOnUserRole != null)
		{
			return _enabledOnUserRole;
		}
		ValueBinding vb = getValueBinding("enabledOnUserRole");
		return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
	}

	public void setVisibleOnUserRole(String visibleOnUserRole)
	{
		_visibleOnUserRole = visibleOnUserRole;
	}

	public String getVisibleOnUserRole()
	{
		if (_visibleOnUserRole != null)
		{
			return _visibleOnUserRole;
		}
		ValueBinding vb = getValueBinding("visibleOnUserRole");
		return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
	}

	public boolean isRendered()
	{
		if (!UserRoleUtils.isVisibleOnUserRole(this))
		{
			return false;
		}
		return super.isRendered();
	}


	public void restoreState(FacesContext context, Object state)
	{

		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		_path = (String) values[1];
		_inline = (Boolean) values[2];
		_media = (String) values[3];
		_enabledOnUserRole = (String) values[4];
		_visibleOnUserRole = (String) values[5];
		_filtered = (Boolean) values[6];
	}

	public Object saveState(FacesContext context)
	{

		Object values[] = new Object[7];
		values[0] = super.saveState(context);
		values[1] = _path;
		values[2] = _inline;
		values[3] = _media;
		values[4] = _enabledOnUserRole;
		values[5] = _visibleOnUserRole;
		values[6] = _filtered;
		return values;

	}
}