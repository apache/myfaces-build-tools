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

import java.io.Serializable;

/**
 * @author Gerald Muellan
 *         Date: 12.02.2006
 *         Time: 23:30:40
 */
public class Address implements Serializable
{
    private int _streetNumber;
    private String _streetName;
    private String _city;
    private String _state;
    private long _zip;


    public Address(int streetNumber,
                   String streetName,
                   String city,
                   long zip,String state)
    {
        _streetNumber = streetNumber;
        _streetName = streetName;
        _city = city;
        _state = state;
        _zip = zip;
    }



    public int getStreetNumber()
    {
        return _streetNumber;
    }

    public void setStreetNumber(int streetNumber)
    {
        _streetNumber = streetNumber;
    }

    public String getStreetName()
    {
        return _streetName;
    }

    public void setStreetName(String streetName)
    {
        _streetName = streetName;
    }

    public String getCity()
    {
        return _city;
    }

    public void setCity(String city)
    {
        _city = city;
    }

    public String  getState()
    {
        return _state;
    }

    public void setState(String  state)
    {
        _state = state;
    }

    public long getZip()
    {
        return _zip;
    }

    public void setZip(long zip)
    {
        _zip = zip;
    }
}
