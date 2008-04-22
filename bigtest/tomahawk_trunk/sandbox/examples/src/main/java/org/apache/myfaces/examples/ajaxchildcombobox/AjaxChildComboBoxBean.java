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
package org.apache.myfaces.examples.ajaxchildcombobox;

import java.util.HashMap;
import java.util.Map;

import javax.faces.model.SelectItem;

/**
 * Backing Bean for AjaxChildComboBox component example 
 * @author Sharath
 */
public class AjaxChildComboBoxBean
{
    private String _selectedCountry;
    private String _selectedCity;
    
    private static Map _countryInfo = new HashMap();
    
    private static String USA = "U.S.A";
    private static String GER = "Germany";
    private static String IND = "India";
    
    static {
        _countryInfo.put(USA, new String [] { "Boston", "New York", "Chicago" } );
        _countryInfo.put(GER, new String [] { "Berlin", "Frankfurt", "Munich" } );
        _countryInfo.put(IND, new String [] { "Delhi", "Bombay", "Hyderabad" } );
    }
    
    public AjaxChildComboBoxBean()
    {
        _selectedCountry = USA;
        _selectedCity = "New York";
    }
        
    public SelectItem [] getCountries()
    {
        SelectItem [] countries = new SelectItem[3];
        
        countries[0] = new SelectItem(USA, USA);
        countries[1] = new SelectItem(GER, GER);
        countries[2] = new SelectItem(IND, IND);
        
        return countries;
    }
    
    public SelectItem [] getCities() 
    {
        System.out.println("getting cities");
        return getCitiesOfSelectedCountry(getSelectedCountry());
    }
    
    public String getSelectedCountry()
    {
        return _selectedCountry;
    }
    
    public void setSelectedCountry(String selectedCountry)
    {
        System.out.println("setting selected country " + selectedCountry);
        _selectedCountry = selectedCountry;
    }
    
    
    public String getSelectedCity()
    {
        return _selectedCity;
    }
    
    public void setSelectedCity(String selectedCity)
    {
        _selectedCity = selectedCity;
    }
            
    public SelectItem [] getCitiesOfSelectedCountry(String country)
    {
        String [] cities = (String[]) _countryInfo.get(country);
        
        SelectItem [] siCities = new SelectItem[cities.length];
        
        for (int i = 0; i < siCities.length; i++) {
            siCities[i] = new SelectItem(cities[i], cities[i]);
        }
        
        return siCities;
        
    }
}
