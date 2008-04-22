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

package org.apache.myfaces.tomahawk.util;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * This is an alternative
 * implementation of the NavigationHandler,
 * directly using the outcome of an action
 * as the name of the page.
 */
public class DirectNavigationHandler extends NavigationHandler{

	/**
	 * Gives the handleNavigation() method an alternative behaviour. Linking
	 * is now processed directly to the given url (e.g. action="/pages/site.jsp").
     *
	 * There is no check if the outcome value really points to a valid page.
	 */
	public void handleNavigation(FacesContext context, String fromAction, String outcome) {

        if(outcome == null || outcome.length()==0)
            return;

        ViewHandler viewHandler=context.getApplication().getViewHandler();
		UIViewRoot viewRoot=viewHandler.createView(context,outcome);
		context.setViewRoot(viewRoot);
		context.renderResponse();
	}
}
