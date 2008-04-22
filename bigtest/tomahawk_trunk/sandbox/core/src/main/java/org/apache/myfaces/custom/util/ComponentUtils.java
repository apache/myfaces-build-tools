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

package org.apache.myfaces.custom.util;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlMessages;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: treeder
 * Date: Nov 21, 2005
 * Time: 9:20:14 PM
 */
public final class ComponentUtils
{

	private ComponentUtils() {

	}

    /**
     * TR- This was moved from AjaxPhaseListenere on checkin 344383
     *
     * @param context
     * @param root
     * @param clientId
     * @return component referenced by clientId or null if not found
     */
    public static UIComponent findComponentByClientId(FacesContext context,
			UIComponent root, String clientId) {
		UIComponent component = null;
		for (int i = 0; i < root.getChildCount() && component == null; i++) {
			UIComponent child = (UIComponent) root.getChildren().get(i);
			component = findComponentByClientId(context, child, clientId);
		}
		if (root.getId() != null) {
			if (component == null && root.getClientId(context).equals(clientId)) {
				component = root;
			}
		}
		return component;
	}

    /**
	 * Useful if you don't know the clientId <p/> TR- This was moved from
	 * AjaxPhaseListenere on checkin 344383 Seems like this could be made more
	 * efficient
	 * 
	 * @param context
	 * @param root
	 * @param id
	 * @return component referenced by id or null if not found
	 */
    public static UIComponent findComponentById(FacesContext context,
			UIComponent root, String id) {
		UIComponent component = null;
		for (int i = 0; i < root.getChildCount() && component == null; i++) {
			UIComponent child = (UIComponent) root.getChildren().get(i);
			component = findComponentById(context, child, id);
		}
		// System.out.println("component looking for: " + id + " - rootid: " +
		// root.getId() + " " + root);
		if (root.getId() != null) {
			if (component == null && root.getId().equals(id)) {
				component = root;
			}
		}
		return component;
	}

	public static UIComponent findFirstMessagesComponent(FacesContext context,
			UIComponent base) {
		if (base == null) {
			return null;
		}

		if (base instanceof HtmlMessages) {
			return base;
		}

		Iterator iterChildren = base.getFacetsAndChildren();
		while (iterChildren.hasNext()) {
			UIComponent child = (UIComponent) iterChildren.next();

			UIComponent found = findFirstMessagesComponent(context, child);
			if (found != null) {
				return found;
			}
		}

		return null;
	}

	private static boolean isDecorated(UIComponent component, String attribute,
			String value) {
		String attributeValue = (String) component.getAttributes().get(
				attribute);

		if (attributeValue == null || attributeValue.indexOf(value) == -1)
			return false;
		else
			return true;
	}
	
	/**
	 * Changes the event attributes like onclick by appending the given value
	 * 
	 * @param component
	 *            UIComponent instance that the attribute belongs to
	 * @param attribute
	 *            Attribute to be changed
	 * @param value
	 *            Value to be appended
	 */
	public static void decorateEventAttribute(UIComponent component,
			String attribute, String value) {
		if (isDecorated(component, attribute, value))
			return;

		String attributeValue = (String) component.getAttributes().get(
				attribute);

		if (attributeValue == null)
			component.getAttributes().put(attribute, value);
		else if (attributeValue.endsWith(";"))
			component.getAttributes().put(attribute, attributeValue + value);
		else
			component.getAttributes().put(attribute,
					attributeValue + ";" + value);
	}
	
	/**
	 * The getParameterMap() is used for getting the parameters
	 * of a specific component.
	 * @param component
	 * @return the Map of the component.
	 */
	public static Map getParameterMap(UIComponent component) {
		Map result = new HashMap();
		for (Iterator iter = component.getChildren().iterator(); iter.hasNext();) {
			UIComponent child = (UIComponent) iter.next();
			if (child instanceof UIParameter) {
				UIParameter uiparam = (UIParameter) child;
				Object value = uiparam.getValue();
				if (value != null) {
					result.put(uiparam.getName(), value);
				}
			}
		}
		return result;
	}	
	
	/**
	 * The getLifecycleId() is used for getting the id of 
	 * the Lifecycle from the ServletContext.
	 * @param context
	 * @return the id of the life cycle.
	 */	
	public static String getLifecycleId(ServletContext context) {
		String lifecycleId = context
				.getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);
		return lifecycleId != null ? lifecycleId
				: LifecycleFactory.DEFAULT_LIFECYCLE;
	}	
	
	/**
	 * This method is used for getting the columns of 
	 * a specific HTMLDataTable.
	 * @param table
	 * @return the List of UIColumns
	 */
	public static List getHTMLDataTableColumns(HtmlDataTable table) {
		List columns = new ArrayList();
		
		for (int i = 0; i < table.getChildCount(); i++) {
			UIComponent child = (UIComponent) table.getChildren().get( i );
			if (child instanceof UIColumn) {				
				columns.add( child );
			}
		}
		return columns;
	}	
}
