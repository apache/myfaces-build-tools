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
package org.apache.myfaces.custom.conversation;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * methods required to inform the user about some anomalies
 * 
 * @author imario@apache.org
 */
public class DefaultConversationMessager implements ConversationMessager
{
	public void setConversationException(FacesContext context, Throwable t)
	{
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, t.getLocalizedMessage(), getThrowableText(t)));
	}

	public void setConversationNotActive(FacesContext context, String name)
	{
		String message = "Conversation not active";
		String messageDtl = "Conversation not active. Please start over. (Conversation Name:" + name + ")";
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, messageDtl));
	}

	protected String getThrowableText(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.close();
		return sw.toString();
	}
}
