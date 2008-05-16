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
import javax.faces.el.ValueBinding;

/**
 * Renders children if any of the component(s) specified in "for" has a message in the context.
 * 
 * @JSFComponent
 *   name = "s:ifMessage"
 *   tagClass = "org.apache.myfaces.custom.ifmessage.IfMessageTag"
 *   
 * @author Mike Youngstrom (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class IfMessage extends UIComponentBase {
	
    public static final String COMPONENT_TYPE = "org.apache.myfaces.IfMessage";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.IfMessageRenderer";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    
    private String _for;

	public IfMessage() {
        super.setRendererType(DEFAULT_RENDERER_TYPE);
	}

    public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
    public String getFor() {
        if (_for != null) return _for;
        ValueBinding vb = getValueBinding("for");
        return vb != null ? (String)vb.getValue(FacesContext.getCurrentInstance()) : null;
    }

    public void setFor(String forValue) {
        _for = forValue;
    }
    
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
    
    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = _for;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _for = (String)values[1];
    }
}


