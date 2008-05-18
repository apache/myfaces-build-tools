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
package org.apache.myfaces.custom.media;

import javax.faces.component.UIComponentBase;

/**
 * 
 * @JSFComponent
 *   name = "s:media"
 *   class = "org.apache.myfaces.custom.media.MediaComponent"
 *   superClass = "org.apache.myfaces.custom.media.AbstractMediaComponent"
 *   tagClass = "org.apache.myfaces.custom.media.MediaTag"
 */
public abstract class AbstractMediaComponent extends UIComponentBase {

    public static String COMPONENT_TYPE = "org.apache.myfaces.media";
    public static String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.media";
    public static String COMPONENT_FAMILY = "org.apache.myfaces.media";
    
    // Value binding constants
    public static final String ATTRIBUTE_WIDTH = "width";
    public static final String ATTRIBUTE_HEIGHT = "height";
    public static final String ATTRIBUTE_SOURCE = "source";
    public static final String ATTRIBUTE_CONTENT_TYPE = "contentType";    
    
    /**
     * @JSFProperty
     */
    public abstract String getWidth();

    /**
     * @JSFProperty
     */
    public abstract String getHeight();
    
    /**
     * @JSFProperty
     */
    public abstract String getContentType();
    
    /**
     * @JSFProperty
     */
    public abstract String getSource();
    
}
