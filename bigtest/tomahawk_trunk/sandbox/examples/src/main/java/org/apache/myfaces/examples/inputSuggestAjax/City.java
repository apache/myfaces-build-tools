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

package org.apache.myfaces.examples.inputSuggestAjax;

public class City
{
    private String _city;
    private String _stateName;
    private String _stateCode;
    private String _zip;
    
    public City(String city, String stateName, String stateCode, String zip)
    {
        _city = city;
        _stateName = stateName;
        _stateCode = stateCode;
        _zip = zip;
    }
    
    public String getCity()
    {
        return _city;
    }

    public String getStateName()
    {
        return _stateName;
    }

    public String getStateCode()
    {
        return _stateCode;
    }
    
    
    public String getZip()
    {
        return _zip;
    }
}