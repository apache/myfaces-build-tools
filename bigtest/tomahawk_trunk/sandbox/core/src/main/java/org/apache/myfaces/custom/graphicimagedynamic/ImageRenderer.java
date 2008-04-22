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
package org.apache.myfaces.custom.graphicimagedynamic;

import org.apache.myfaces.custom.dynamicResources.ResourceRenderer;

/**
 * The ImageRenderer is used to render the binary data for a html img tag
 * Implementions must have a default constructor
 * 
 * @author Mathias Broekelmann
 * @version $Revision$ $Date$
 */
public interface ImageRenderer extends ResourceRenderer
{
	/**
	 * This method will be called first, to set the contexts.
	 * 
     * @param facesContext the faces context
     * @param imageContext the image context width aditional image parameters
	 *
	void setContext(FacesContext facesContext, ImageContext imageContext) throws Exception;
	*/
    /**
     * Called to render the image to the given outputstream.
     * 
     * @param out the outputstream which is used to write the binary data of the image
     *  
     * @throws IOException
     *
    void renderImage(ResponseStream out) throws IOException;
    */
}
