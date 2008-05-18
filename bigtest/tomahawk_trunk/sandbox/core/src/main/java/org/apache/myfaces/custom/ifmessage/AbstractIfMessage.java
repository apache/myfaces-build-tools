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
package org.apache.myfaces.custom.ifmessage;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * Renders children if any of the component(s) specified in "for" has a message in the context.
 * 
 * @JSFComponent
 *   name = "s:ifMessage"
 *   class = "org.apache.myfaces.custom.ifmessage.IfMessage"
 *   superClass = "org.apache.myfaces.custom.ifmessage.AbstractIfMessage"
 *   tagClass = "org.apache.myfaces.custom.ifmessage.IfMessageTag"
 *   
 * @author Mike Youngstrom (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractIfMessage extends UIComponentBase {
	
    public static final String COMPONENT_TYPE = "org.apache.myfaces.IfMessage";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.IfMessageRenderer";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    	
    /**
     * @JSFProperty
     * @return
     */
    public abstract String getFor();
    
    private boolean isMessageForId(String id) {
    	UIComponent component = findComponent(id); 
    	if(component != null) {
        	String clientId = component.getClientId(FacesContext.getCurrentInstance());
        	return FacesContext.getCurrentInstance().getMessages(clientId).hasNext();
    	}
    	return false;
    }
    
	public boolean getRendersChildren() {
		return true;
	}
	
	public void encodeChildren(FacesContext context) throws IOException {
		if(getFor() != null) {
			StringTokenizer tokenizer = new StringTokenizer(getFor(), ",");
			while(tokenizer.hasMoreTokens()) {
			    if(isMessageForId(tokenizer.nextToken().trim())) {
					super.encodeChildren(context);
					break;
				}
			}
		} else {
			if(FacesContext.getCurrentInstance().getMessages().hasNext()) {
				super.encodeChildren(context);
			}
		}
	}
    
}


