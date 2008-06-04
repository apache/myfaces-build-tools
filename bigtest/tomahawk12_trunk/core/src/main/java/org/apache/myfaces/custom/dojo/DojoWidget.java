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


package org.apache.myfaces.custom.dojo;

/**
 * Basic dojo interface
 * which has to implement
 * certain methods regarding the dojo widgets
 * 
 * for now the interfaces are very limited and only 
 * have to implement the variable binding properties
 * 
 * 
 * @author werpu
 *
 */
public interface DojoWidget {
    /**
     * getter for an explizit widget
     * var
     * @return
     */
    public String getWidgetVar();
    
    /**
     * 
     * @param widgetVar
     */
    public void setWidgetVar(String widgetVar);
  
    
    /**
     * forces the internal widget id onto the given value
     * 
     * @param forceIt
     */
    public void setWidgetId(String widgetId);
    
    /**
     * 
     * @return the enforced widgetid
     */
    public String getWidgetId();
}
