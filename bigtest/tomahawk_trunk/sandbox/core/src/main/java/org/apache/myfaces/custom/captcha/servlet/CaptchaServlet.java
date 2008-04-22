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
package org.apache.myfaces.custom.captcha.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.captcha.CAPTCHAComponent;
import org.apache.myfaces.custom.captcha.util.*;


/**
 * The (CaptchaServlet) is responsible for generating the CAPTCHA image.
 */
public class CaptchaServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Initialize world.
		String captchaText = null;
		CAPTCHAImageGenerator captchaImageGenerator = new CAPTCHAImageGenerator();
		String captchaSessionKeyName = request
				.getParameter(CAPTCHAComponent.ATTRIBUTE_CAPTCHASESSIONKEYNAME);

		// Generate random CAPTCHA text.
		captchaText = CAPTCHATextGenerator.generateRandomText();

		// Generate the image.
		captchaImageGenerator.generateImage(response, captchaText);

		// Set the generated text in the user session.
		request.getSession().setAttribute(captchaSessionKeyName, captchaText);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
