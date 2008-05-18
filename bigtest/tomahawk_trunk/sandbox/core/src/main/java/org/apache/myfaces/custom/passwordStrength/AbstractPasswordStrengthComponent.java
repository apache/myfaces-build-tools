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
package org.apache.myfaces.custom.passwordStrength;

import javax.faces.component.html.HtmlInputText;

import org.apache.myfaces.component.AlignProperty;

/**
 * 
 * @JSFComponent
 *   name = "s:passwordStrength"
 *   class = "org.apache.myfaces.custom.passwordStrength.PasswordStrengthComponent"
 *   superClass = "org.apache.myfaces.custom.passwordStrength.AbstractPasswordStrengthComponent"
 *   tagClass = "org.apache.myfaces.custom.passwordStrength.PasswordStrengthTag"
 *   
 */
public abstract class AbstractPasswordStrengthComponent extends HtmlInputText 
    implements AlignProperty{ 

	public static String COMPONENT_TYPE = "org.apache.myfaces.PasswordStrength";

	public static String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.PasswordStrength";
	
	public static String COMPONENT_FAMILY = "org.apache.myfaces.PasswordStrength";
		
	/**
	 * @JSFProperty
	 */
	public abstract String getShowDetails();
	
    /**
     * @JSFProperty
     */
	public abstract String getStrengthIndicatorType();		

    /**
     * @JSFProperty
     */
	public abstract String getPreferredPasswordLength();
	
    /**
     * @JSFProperty
     */
	public abstract String getPrefixText();
	
    /**
     * @JSFProperty
     */
	public abstract String getTextStrengthDescriptions();
	
	
    /**
     * @JSFProperty
     */
	public abstract String getCustomSecurityExpression();


    /**
     * @JSFProperty
     */
	public abstract String getUseCustomSecurity();
	
    /**
     * @JSFProperty
     */
	public abstract String getPenaltyRatio();
	
    /**
     * HTML: Specifies the horizontal alignment of this element. Deprecated in HTML 4.01.
     * 
     * @JSFProperty 
     */
    public abstract String getAlign();	
	
}
