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
package org.apache.myfaces.examples.captcha;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class CaptchaBean {
	
	public final static String SESSION_KEY_NAME = "mySessionKeyName";
	public final static String CORRECT = "Correct!";
	public final static String WRONG = "Wrong";	
	
	String status;
	String value;

	public String check() {

		// Compare the CAPTCHA value with the user entered value.
		String captchaValue = (String)((HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true))
				.getAttribute(SESSION_KEY_NAME);
		
		if(captchaValue.equalsIgnoreCase(value)) {
			status = CORRECT;
		} else {
			status = WRONG;
		}
		
		return "";
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSessionKeyName() {
		return SESSION_KEY_NAME;
	}
}
