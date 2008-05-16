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
package org.apache.myfaces.custom.script;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

/**
 * @JSFComponent
 *   name = "s:script"
 *   tagClass = "org.apache.myfaces.custom.script.ScriptTag"
 *   
 * @author Matthias Wessendorf (changed by $Author$)
 * @version $Revision$ $Date$
 */
public class Script extends UIOutput {

    public static final String COMPONENT_TYPE = "org.apache.myfaces.Script";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Script";

    private String src = null;
    private String type = HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT;
	private String language = null;

	// ------------------------------------------------------------ Constructor
    public Script() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * @see <code>UIComponentBase</code>
     */
    public String getFamily() {

        return COMPONENT_FAMILY;

    }

    //  ------------------------------------------------------------ getter/setter
    public String getSrc() {
        if (src != null) return src;
        ValueBinding vb = getValueBinding("src");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }


    public void setSrc(String src) {
        this.src = src;
    }


    public String getType() {
        if (type != null) return type;
        ValueBinding vb = getValueBinding("type");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }


    public void setType(String type) {
        this.type = type;
    }

	public String getLanguage() {
		if (language != null) return language;
		ValueBinding vb = getValueBinding("language");
		return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
	}


	public void setLanguage(String language) {
		this.language = language;
	}

	//  ------------------------------------------------------------ StateHolder
    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        src = (String) values[1];
        type = (String) values[2];
		language = (String) values[3];
    }

    public Object saveState(FacesContext context) {

        return new Object[]
		{
			super.saveState(context),
        	src,
        	type,
			language
		};
    }
}