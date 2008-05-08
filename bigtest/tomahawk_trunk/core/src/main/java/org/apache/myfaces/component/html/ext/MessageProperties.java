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
package org.apache.myfaces.component.html.ext;

public interface MessageProperties
{

    /**
     * @JSFProperty
     */
    public String getSummaryFormat();
    
    public void setSummaryFormat(String summaryFormat);
    
    /**
     * @JSFProperty
     */
    public String getDetailFormat();
    
    public void setDetailFormat(String detailFormat);
    
    /**
     * @JSFProperty
     *   defaultValue="false"
     */
    public boolean isReplaceIdWithLabel();
    
    public void setReplaceIdWithLabel(boolean replaceIdWithLabel);
    
    /**
     * @JSFProperty
     *   defaultValue="false"
     */    
    public boolean isForceSpan();
    
    public void setForceSpan(boolean forceSpan);
}
