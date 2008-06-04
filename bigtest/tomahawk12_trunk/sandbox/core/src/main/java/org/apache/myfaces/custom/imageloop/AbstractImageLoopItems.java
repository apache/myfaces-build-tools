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
package org.apache.myfaces.custom.imageloop;

import javax.faces.component.UIComponentBase;

/**
 * Image loop items.
 * 
 * @JSFComponent
 *   name = "s:imageLoopItems"
 *   class = "org.apache.myfaces.custom.imageloop.ImageLoopItems"
 *   superClass = "org.apache.myfaces.custom.imageloop.AbstractImageLoopItems"
 *   tagClass = "org.apache.myfaces.custom.imageloop.ImageLoopItemsTag"
 *   
 * @author Felix Röthenbacher (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractImageLoopItems extends UIComponentBase {
    
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.ImageLoopItems";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.ImageLoopItems";
    
    // Value binding constants.
    private static final String VB_VALUE = "value";

    /**
     * An EL expression that specifies the elements of the image loop.
     * The expression can refer to one of the following:
     * <ol>
     *     <li>A single GraphicItem</li>
     *     <li>An array or Collection of GraphicItem instances</li>
     * </ol>
     * The value properties of each of the ImageLoopItems must be of the same
     * basic type as the parent component's value.
     * 
     * @JSFProperty
     */
    public abstract Object getValue();
}
