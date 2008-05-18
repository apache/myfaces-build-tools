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
package org.apache.myfaces.custom.captcha;

import javax.faces.component.UIComponentBase;

/**
 * @JSFComponent
 *   name = "s:captcha"
 *   class = "org.apache.myfaces.custom.captcha.CAPTCHAComponent"
 *   superClass = "org.apache.myfaces.custom.captcha.AbstractCAPTCHAComponent"
 *   tagClass = "org.apache.myfaces.custom.captcha.CAPTCHATag"
 * 
 * @author Hazem Saleh
 *
 */
public abstract class AbstractCAPTCHAComponent extends UIComponentBase {

    public static String COMPONENT_TYPE = "org.apache.myfaces.CAPTCHA";
    public static String COMPONENT_FAMILY = "org.apache.myfaces.CAPTCHA";
    public static String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.CAPTCHA";
    
    // Value binding constants
    public static final String ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME = "captchaSessionKeyName";
    public static final String ATTRIBUTE_IMAGE_WIDTH = "imageWidth";
    public static final String ATTRIBUTE_IMAGE_HEIGHT = "imageHeight";
    
    /**
     * @JSFProperty
     * @return
     */
    public abstract String getCaptchaSessionKeyName();
    
    /**
     * @JSFProperty
     */
    public abstract String getImageWidth();

    /**
     * @JSFProperty
     */
    public abstract String getImageHeight();      
   
}
