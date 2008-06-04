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
package org.apache.myfaces.custom.inputAjax;

/**
 * MyFaces extension to the standard messages tag: see summaryDetailSeparator attribute
 * 
 * @JSFComponent
 *   name = "s:message"
 *   tagClass = "org.apache.myfaces.custom.inputAjax.HtmlMessageTag"
 *
 */
public class HtmlMessage extends org.apache.myfaces.component.html.ext.HtmlMessage
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.MessageSandbox";
    static public final String DEFAULT_RENDERER_TYPE = 
        "org.apache.myfaces.MessageSandbox";
    

}
