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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Backing bean for the input-suggest-ajax component
 *
 * @author Gerald Müllan (latest modification by $Author$)
 * @version $Revision$
 */
public class InputSuggestAjaxBean
{
    private String suggestValue = null;
    private String suggestValueMaxItems = null;

    private static List cities = new ArrayList();

    private Address choosenAddress;

    public static List dummyDataBaseAddresses = new ArrayList();

    static
    {
        dummyDataBaseAddresses.add(new Address(11,"nonamestreet","detroit",15,"KL"));
        dummyDataBaseAddresses.add(new Address(12,"maxstreet","san diego",14,"SJ"));
        dummyDataBaseAddresses.add(new Address(13,"philstreet","philadelphia",13,"NW"));
        dummyDataBaseAddresses.add(new Address(14,"newstreet","new york",12,"IL"));
        dummyDataBaseAddresses.add(new Address(15,"sanstreet","san francisco",11,"NY"));
    }
    
    static {
        cities.add(new City("San Antonio", "Texas", "TX", "78821"));
        cities.add(new City("Sacramento", "California", "CA", "78880"));
        cities.add(new City("Salinas", "California", "CA", "78881"));
        cities.add(new City("San Bernardino", "California", "CA", "78882"));
        cities.add(new City("San Clemente", "California", "CA", "78883"));
        cities.add(new City("San Diego", "California", "CA", "78884"));
        cities.add(new City("San Dimas", "California", "CA", "78885"));
        cities.add(new City("San Fernando", "California", "CA", "78886"));
        cities.add(new City("San Francisco", "California", "CA", "78887"));
        cities.add(new City("San Gabriel", "California", "CA", "78888"));
        cities.add(new City("San Jose", "California", "CA", "78889"));
        cities.add(new City("San Marino", "California", "CA", "78890"));
        cities.add(new City("San Mateo", "California", "CA", "78895"));
        cities.add(new City("San Padre", "Texas", "TX", "78823"));
        cities.add(new City("San Rafael", "California", "CA", "78845"));
        cities.add(new City("Santa Ana", "California", "CA", "78811"));
        cities.add(new City("Santa Monica", "California", "CA", "78345"));
        cities.add(new City("Seal Beach", "California", "CA", "78526"));
    }
    
    public List getCityList(String cityFragment)
    {
        List filteredCities = new ArrayList();
        
        Iterator it = cities.iterator();
        while (it.hasNext()) 
        {
            City city = (City) it.next();
            if (city.getCity().startsWith(cityFragment))
            {
                filteredCities.add(city);
            }
        }
        System.out.println("returning cities: " + filteredCities.size());
        return filteredCities;
    }
        

    public List getAddressList(String cityFragment)
    {
        List addressList = new ArrayList();

        addressList.add(new Address(11,"noname",cityFragment+"nocity",15,"KL"));
        addressList.add(new Address(12,"max",cityFragment+"muster",14,"SJ"));
        addressList.add(new Address(13,"phil",cityFragment+"philadelphia",13,"NW"));
        addressList.add(new Address(14,"new",cityFragment+"new york",12,"IL"));
        addressList.add(new Address(15,"san",cityFragment+"san francisco",11,"NY"));
        addressList.add(new Address(16,"san",cityFragment+"san diego",16,"MH"));

        return addressList;
    }

    public List getAddressList(String cityFragment, Integer maxSize)
    {
        List addressList = new ArrayList();

        if (cityFragment.startsWith("as"))
        {
            addressList.add(new Address(11,"noname",cityFragment+"nocity",15,"KL"));
            addressList.add(new Address(12,"max",cityFragment+"muster",14,"SJ"));
            addressList.add(new Address(13,"phil",cityFragment+"philadelphia",13,"NW"));
            addressList.add(new Address(14,"new",cityFragment+"new york",12,"IL"));
            addressList.add(new Address(15,"san",cityFragment+"san francisco",11,"NY"));
            addressList.add(new Address(16,"san",cityFragment+"san diego",16,"MH"));
            addressList.add(new Address(11,"noname",cityFragment+"max",15,"KL"));
            addressList.add(new Address(12,"max",cityFragment+"nomax",14,"SJ"));
            addressList.add(new Address(13,"phil",cityFragment+"detroit",13,"NW"));
            addressList.add(new Address(11,"noname",cityFragment+"nocity",15,"KL"));
            addressList.add(new Address(12,"max",cityFragment+"muster",14,"SJ"));
            addressList.add(new Address(13,"phil",cityFragment+"philadelphia",13,"NW"));
            addressList.add(new Address(14,"new",cityFragment+"new york",12,"IL"));
            addressList.add(new Address(15,"san",cityFragment+"san francisco",11,"NY"));
            addressList.add(new Address(16,"san",cityFragment+"san diego",16,"MH"));
            addressList.add(new Address(11,"noname",cityFragment+"max",15,"KL"));
            addressList.add(new Address(12,"max",cityFragment+"nomax",14,"SJ"));
            addressList.add(new Address(13,"phil",cityFragment+"detroit",13,"NW"));
        }
        else
        {
            addressList.add(new Address(11,"noname",cityFragment+"nocity",15,"KL"));
            addressList.add(new Address(12,"max",cityFragment+"muster",14,"SJ"));
            addressList.add(new Address(13,"phil",cityFragment+"philadelphia",13,"NW"));
            addressList.add(new Address(14,"new",cityFragment+"new york",12,"IL"));
            addressList.add(new Address(15,"san",cityFragment+"san francisco",11,"NY"));
            addressList.add(new Address(16,"san",cityFragment+"san diego",16,"MH"));
            addressList.add(new Address(11,"noname",cityFragment+"max",15,"KL"));
            addressList.add(new Address(12,"max",cityFragment+"nomax",14,"SJ"));
        }

        return addressList;
    }

    public List getItems(String prefix)
    {
        List li = new ArrayList();
        li.add(prefix+1);
        li.add(prefix+2);
        li.add(prefix+3);
        li.add(prefix+4);
        li.add(prefix+5);
        li.add(prefix+6);
        return li;
    }

    public List getItems(String prefix, Integer maxSize) {

    	List li = new ArrayList();

    	for(int i = 0; i < maxSize.intValue(); i++) {
    		li.add(prefix+ " " +(i+1));
    	}

    	return li;
    }

    public List getAddresses(String prefix)
    {
        return dummyDataBaseAddresses;
    }

    public String getAddressLabel(Object adress)
    {
        if (adress instanceof Address)
        {
            Address a = (Address) adress;
            return a.getCity() + "," + a.getStreetName() + "," + a.getState();
        }
        else
        {
            return adress.toString();
        }
    }

    public String getSuggestValue()
    {
        return suggestValue;
    }

    public void setSuggestValue(String suggestValue)
    {
        this.suggestValue = suggestValue;
    }

    public String getSuggestValueMaxItems()
    {
        return suggestValueMaxItems;
    }

    public void setSuggestValueMaxItems(String suggestValueMaxItems)
    {
        this.suggestValueMaxItems = suggestValueMaxItems;
    }

    public Address getChoosenAddress()
    {
        if (choosenAddress == null)
        {
            return new Address(11,"nonamestreet","detroit",15,"KL");
        }
        return choosenAddress;
    }

    public void setChoosenAddress(Address choosenAddress)
    {
        this.choosenAddress = choosenAddress;
    }
}