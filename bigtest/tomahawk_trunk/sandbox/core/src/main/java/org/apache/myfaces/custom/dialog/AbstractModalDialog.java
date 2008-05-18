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

package org.apache.myfaces.custom.dialog;

import javax.faces.component.UIPanel;

import org.apache.myfaces.custom.dojo.DojoWidget;

/**
 * 
 * @JSFComponent
 *   name = "s:modalDialog"
 *   class = "org.apache.myfaces.custom.dialog.ModalDialog"
 *   superClass = "org.apache.myfaces.custom.dialog.AbstractModalDialog"
 *   tagClass = "org.apache.myfaces.custom.dialog.ModalDialogTag"
 *   
 *
 */
public abstract class AbstractModalDialog extends UIPanel implements DojoWidget {

    public static final String COMPONENT_TYPE = "org.apache.myfaces.ModalDialog";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.ModalDialog";
    
	/**
	 * @JSFProperty
	 */
	public abstract String getDialogAttr();

    /**
     * @JSFProperty
     */
	public abstract String getDialogId();

    /**
     * @JSFProperty
     */
    public abstract String getDialogVar();
    
    public abstract void setDialogVar(String dialogVar);

    /**
     * @JSFProperty
     */
	public abstract String getHiderIds();

    /**
     * @JSFProperty
     */
	public abstract String getViewId();

	//@Override
	public boolean getRendersChildren() {
    	return true;
	}

    /**
     * @JSFProperty
     */
	public abstract String getStyle();

    /**
     * @JSFProperty
     */
	public abstract String getStyleClass();

    /**
     * @JSFProperty
     */
	public abstract String getWidgetId();

    /**
     * @JSFProperty
     */
	public abstract String getDialogTitle();

    /**
     * @JSFProperty
     */
	public abstract Boolean getCloseButton();
	/**
	 * @JSFProperty
	 */
	public String getWidgetVar() {
        return getDialogVar();
    }

	public void setWidgetVar(String widgetVar) {
       setDialogVar(widgetVar);
    }
}
