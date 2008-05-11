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
 * Renders the path to a common CSS-file
 * 
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
	 * URL for CSS-file.
	 * 
	 * @JSFProperty
	 *   required="true"
	 */
	public abstract String getPath();

	/**
	 * Inline the stylesheet file content as in contrast to referencing it as .
	 * 
	 * @JSFProperty
	 *   defaultValue = "false"
	 * @return true if the styles are inlined to the jsp file
	 */
	public abstract boolean isInline();

	/**
	 * true|false. When true, any EL expression in the stylesheet will 
	 * be evaluated and replaced by its string representation on the 
	 * first access. The stylesheet will be processed only once. 
	 * Every subsequent request will get a cached view.
	 * 
	 * @JSFProperty
	 *   defaultValue = "false"
	 * @return true if the stylesheet should be filtered before sending to the browser
	 */
	public abstract boolean isFiltered();

    /**
     * Define the target media of the styles:
     *     <dl>
     *       <dt>screen</dt>
     *       <dd>Intended for non-paged computer screens.</dd>
     *       <dt>tty</dt>
     *       <dd>Intended for media using a fixed-pitch character grid, such
     *         as teletypes, terminals, or portable devices with limited
     *         display capabilities.</dd>
     *       <dt>tv</dt>
     *       <dd>Intended for television-type devices (low resolution,
     *         color, limited scrollability).</dd>
     *       <dt>projection</dt>
     *       <dd>Intended for projectors.</dd>
     *       <dt>handheld</dt>
     *       <dd>Intended for handheld devices (small screen, monochrome,
     *         bitmapped graphics, limited bandwidth).</dd>
     *       <dt>print</dt>
     *       <dd>Intended for paged, opaque material and for documents
     *         viewed on screen in print preview mode.</dd>
     *       <dt>braille</dt>
     *       <dd>Intended for braille tactile feedback devices.</dd>
     *       <dt>aural</dt>
     *       <dd>Intended for speech synthesizers.</dd>
     *       <dt>all</dt>
     *       <dd>Suitable for all devices.</dd>
     *     </dl>
     *     Could be a comma separated list.
     *     See also http://www.w3.org/TR/REC-html40/types.html#type-media-descriptors
     * 
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