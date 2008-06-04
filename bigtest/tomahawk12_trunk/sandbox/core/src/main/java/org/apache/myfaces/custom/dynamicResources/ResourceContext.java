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
package org.apache.myfaces.custom.dynamicResources;

import java.util.Map;


/**
 * The ResourceContext class holds additional objects and values which can be used 
 * to determine which or how a resource should be rendered. 
 * 
 * @author Sylvain Vieujot
 * @version $Revision$ $Date$
 *
 */
public interface ResourceContext
{
    /**
     * Returns a map with the values of the used parameters
     * 
     * @return not null, Map contains instances of String for keys and values
     */
    Map getParamters();
}
