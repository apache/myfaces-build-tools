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
	 * This flag {true | false} determines whether to show the details (left characters). 
	 * default is true
	 * 
	 * @JSFProperty
	 */
	public abstract String getShowDetails();
	
    /**
     * This flag determines the indicator type. It can be {text or bar}. Default is text
     * 
     * @JSFProperty
     */
	public abstract String getStrengthIndicatorType();		

    /**
     * The prefered length of the password
     * 
     * @JSFProperty
     */
	public abstract String getPreferredPasswordLength();
	
    /**
     * The prefix of the component message
     * 
     * @JSFProperty
     */
	public abstract String getPrefixText();
	
    /**
     * The text strength descriptions
     * 
     * @JSFProperty
     */
	public abstract String getTextStrengthDescriptions();
	
    /**
     * This string determines the expression of the custom security rule of the password
     * Note that the expression has the following format :
     * *******************************************************
     * S (Number)  N (Number) A (Number)
     * Where S stands for Symbols
     * Where N stands for Numbers
     * Where A stands for Alphabets
     * *******************************************************
     * For example) A4N2S3A2
     * Means that the password will be as following :
     * 4 or more Alphabets followed by
     * 2 or more Numbers followed by
     * 3 or more Symbols followed by
     * 2 or more Alphabets
     * *******************************************************
     * Note also that the useCustomSecurity should be set to true.
     * 
     * @JSFProperty
     */
	public abstract String getCustomSecurityExpression();

    /**
     * This flag determines whether to user custom security rules instead
     * of just depending on the password length. The default is false.
     * 
     * @JSFProperty
     */
	public abstract String getUseCustomSecurity();
	
    /**
     * This attribute determines the penalty ratio that will decrease the password 
     * Strength if the custom security expression is not met. Note also that the 
     * useCustomSecurity should be set to true to apply this flag. Possible values 
     * from 0 to 100. Default value is 50.
     * 
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
