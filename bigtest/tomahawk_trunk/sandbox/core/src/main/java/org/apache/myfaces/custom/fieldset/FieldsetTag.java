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
package org.apache.myfaces.custom.fieldset;

import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.htmlTag.HtmlTagTag;
/**
 * @author svieujot (latest modification by $Author$)
 * @version $Revision$ $Date: 2005-06-09 02:27:56 -0400 (Thu, 09 Jun 2005) $
 */
public class FieldsetTag extends HtmlTagTag {

	private String legend = null;
	
	public FieldsetTag() {
		super();
	}

	public String getComponentType() {
		return Fieldset.COMPONENT_TYPE;
	}
	
	public String getRendererType() {
		return FieldsetRenderer.RENDERER_TYPE;
	}

	public void release() {
		super.release();
		this.legend = null;
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		setStringProperty(component, "legend", legend);
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}
}