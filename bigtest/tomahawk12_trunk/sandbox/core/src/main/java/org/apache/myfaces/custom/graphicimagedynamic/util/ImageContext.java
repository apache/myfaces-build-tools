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

package org.apache.myfaces.custom.graphicimagedynamic.util;

import org.apache.myfaces.custom.dynamicResources.ResourceContext;


/**
 * The ImageContext class holds additional objects and values which can be used 
 * to determine which or how an image should be rendered. 
 * 
 * @author Mathias Broekelmann
 * @version $Revision: 472727 $ $Date: 2006-11-09 03:08:46 +0200 (Thu, 09 Nov 2006) $
 *
 */
public interface ImageContext extends ResourceContext
{
    
    /**
     * Returns the desired width of the image
     * 
     * @return null if no width is defined
     */
    Integer getWidth();

    /**
     * Returns the desired height of the image
     * 
     * @return null if no height is defined
     */
    Integer getHeight();
}
