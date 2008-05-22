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

package org.apache.myfaces.custom.fisheye;

import javax.faces.component.UICommand;

/**
 * CommandLink component that can be used in nodeStamp facet
 * 
 * @JSFComponent
 *   name = "s:fishEyeCommandLink"
 *   class = "org.apache.myfaces.custom.fisheye.FishEyeCommandLink"
 *   superClass = "org.apache.myfaces.custom.fisheye.AbstractFishEyeCommandLink"
 *   tagClass = "org.apache.myfaces.custom.fisheye.FishEyeCommandLinkTag"
 *   
 * @author Thomas Spiegl
 */
public abstract class AbstractFishEyeCommandLink extends UICommand {

    public static final String COMPONENT_TYPE = "org.apache.myfaces.FishEyeCommandLink";
    public static final String DEFAULT_RENDERER_TYPE  = "org.apache.myfaces.FishEyeCommandLink";

    /**
     * @JSFProperty
     */
    public abstract String getCaption();

    /**
     * @JSFProperty
     */
    public abstract String getIconSrc();

    /**
     * @JSFProperty
     */
    public abstract String getTarget();
    
}
