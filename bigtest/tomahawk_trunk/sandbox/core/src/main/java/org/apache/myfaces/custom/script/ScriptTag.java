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

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlOutputTextTagBase;

/**
 * @author Matthias Wessendorf (changed by $Author$)
 * @version $Revision$ $Date$
 */
public class ScriptTag extends HtmlOutputTextTagBase {

	private String src = null;
    private String type = null;
	private String language = null;

	//  ------------------------------------------------------------ UIComponentTags
    public String getComponentType() {
        return Script.COMPONENT_TYPE;

    }

    public String getRendererType() {
        return "org.apache.myfaces.Script";
    }

    public void release() {

        super.release();
        src = null;
        type = null;

    }

    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        setStringProperty(component, HTML.SRC_ATTR, src);
        setStringProperty(component, HTML.TYPE_ATTR, type);
		setStringProperty(component, HTML.SCRIPT_LANGUAGE_ATTR, language);
    }


    //  ------------------------------------------------------------ setter
	public void setSrc(String src) {
		this.src = src;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}