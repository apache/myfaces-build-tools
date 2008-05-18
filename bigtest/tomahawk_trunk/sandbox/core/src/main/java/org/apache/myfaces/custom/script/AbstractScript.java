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
package org.apache.myfaces.custom.script;

import javax.faces.component.UIOutput;

/**
 * @JSFComponent
 *   name = "s:script"
 *   class = "org.apache.myfaces.custom.script.Script"
 *   superClass = "org.apache.myfaces.custom.script.AbstractScript"
 *   tagClass = "org.apache.myfaces.custom.script.ScriptTag"
 *   
 * @author Matthias Wessendorf (changed by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractScript extends UIOutput {

    public static final String COMPONENT_TYPE = "org.apache.myfaces.Script";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Script";
    
    /**
     * @JSFProperty
     */
    public abstract String getSrc();

    /**
     * @JSFProperty
     *   defaultValue="text/javascript"
     */
    public abstract String getType();

    /**
     * @JSFProperty
     */
	public abstract String getLanguage();

}