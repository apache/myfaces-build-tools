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
package org.apache.myfaces.custom.valueChangeNotifier;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ValueChangeNotifier</p>
 * 
 * Like valueChangeListener, but will send valueChange events after the
 * UPDATE_MODEL phase. <br />
 * This simply means you CAN update your model values within such an event now.
 * <br />
 * <ul>
 * <li>It wont be overwritten by the model update</li>
 * <li>And wont trigger another valueChange if you update a value with an
 * valueChangeListener attached</li>
 * </ul>
 * 
 * @author Mario Ivankovits <imario - at - apache.org>  (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValueChangeNotifierTag extends TagSupport
{
	private String method = null;
	
	private static Log log = LogFactory.getLog(ValueChangeNotifierTag.class);

	public ValueChangeNotifierTag()
	{
	}

	/**
	 * The bean.method name of your valueChange method<br />
	 * Currently only methods listeners are supported.<br />
	 * e.g. myBean.myListenerMethod
	 */
	public void setMethod(String method)
	{
		this.method = method;
	}

	public int doStartTag() throws JspException
	{
		if (method == null)
		{
			throw new JspException("name attribute not set");
		}

		// Find parent UIComponentTag
		UIComponentTag componentTag = UIComponentTag
				.getParentUIComponentTag(pageContext);
		if (componentTag == null)
		{
			throw new JspException(
					"ValueChangeListenerTag has no UIComponentTag ancestor");
		}

		if (componentTag.getCreated())
		{
			// Component was just created, so we add the Listener
			UIComponent component = componentTag.getComponentInstance();
			if (component instanceof EditableValueHolder)
			{
				setupClassListener(component);
			}
			else
			{
				throw new JspException("Component " + component.getId()
						+ " is no EditableValueHolder");
			}
		}

		return Tag.SKIP_BODY;
	}

	protected void setupClassListener(UIComponent component)
	{
		if (UIComponentTag.isValueReference(method))
		{
				((EditableValueHolder) component)
				.addValueChangeListener(new ValueChangeCollector(method));
		} 
		else
		{
			if(log.isErrorEnabled()){
				log.error("Invalid expression " + method);
			}
		}
	}
}