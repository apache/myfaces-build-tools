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
package org.apache.myfaces.custom.exporter;

import javax.faces.component.UIComponentBase;

/**
 * 
 * @JSFComponent
 *   name = "s:exporter"
 *   class = "org.apache.myfaces.custom.exporter.Exporter"
 *   superClass = "org.apache.myfaces.custom.exporter.AbstractExporter"
 *   tagClass = "org.apache.myfaces.custom.exporter.ExporterTag"
 *
 */
public abstract class AbstractExporter extends UIComponentBase {

	public static final String COMPONENT_TYPE = "org.apache.myfaces.Exporter";
	public static final String COMPONENT_FAMILY = "org.apache.myfaces.Export";
	private static final String DEFAULT_RENDERER = "org.apache.myfaces.ExporterRenderer";

	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * @JSFProperty
	 */
	public abstract String getFor();
	
    /**
     * @JSFProperty
     */
	public abstract String getFilename();
	
    /**
     * @JSFProperty
     */
	public abstract String getFileType();
	
}
