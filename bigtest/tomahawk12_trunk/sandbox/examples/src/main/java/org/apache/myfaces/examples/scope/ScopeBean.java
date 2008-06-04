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

package org.apache.myfaces.examples.scope;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.myfaces.custom.scope.ScopeHolder;
import org.apache.myfaces.custom.scope.ScopeUtils;

/**
 * A simple bean for testing the scoping system it is filled over a scoped
 * wizard over several screens and later a report is printed to the result
 * screen
 *
 * @author Werner Punz werpu@gmx.at
 * @version $Revision$ $Date$
 */
public class ScopeBean implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = -7538089116831271626L;
    private String firstname = "";
    private String lastname = "";
    private String street = "";
    private String number = "";
    private String country = "";
    private String selectedproduct1 = "";
    private String selectedproduct2 = "";
    private String selectedproduct3 = "";
    private String selectedproduct4 = "";
    private String selectedproduct5 = "";
    private java.lang.String zipcode;
    private java.lang.String city;

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstName)
    {
        this.firstname = firstName;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastName)
    {
        this.lastname = lastName;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getSelectedproduct1()
    {
        return selectedproduct1;
    }

    public void setSelectedproduct1(String selectedProduct1)
    {
        this.selectedproduct1 = selectedProduct1;
    }

    public String getSelectedproduct2()
    {
        return selectedproduct2;
    }

    public void setSelectedproduct2(String selectedproduct2)
    {
        this.selectedproduct2 = selectedproduct2;
    }

    public String getSelectedproduct3()
    {
        return selectedproduct3;
    }

    public void setSelectedproduct3(String selectedproduct3)
    {
        this.selectedproduct3 = selectedproduct3;
    }

    public String getSelectedproduct4()
    {
        return selectedproduct4;
    }

    public void setSelectedproduct4(String selectedproduct4)
    {
        this.selectedproduct4 = selectedproduct4;
    }

    public String getSelectedproduct5()
    {
        return selectedproduct5;
    }

    public void setSelectedproduct5(String selectedproduct5)
    {
        this.selectedproduct5 = selectedproduct5;
    }

    public java.lang.String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(java.lang.String zipcode)
    {
        this.zipcode = zipcode;
    }

    public java.lang.String getCity()
    {
        return city;
    }

    public void setCity(java.lang.String city)
    {
        this.city = city;
    }

    public String resetOrder()
    {
        ScopeHolder holder = (ScopeHolder) ScopeUtils.getManagedBean("ScopeContainer");
        holder.resetScopes();
        return "go_first";
    }

    public String getItemsList()
    {
        Set items = new HashSet();
        if (selectedproduct1 != null && !selectedproduct1.equals(""))
            items.add(selectedproduct1);
        if (selectedproduct2 != null && !selectedproduct2.equals(""))
            items.add(selectedproduct2);
        if (selectedproduct3 != null && !selectedproduct3.equals(""))
            items.add(selectedproduct3);
        if (selectedproduct4 != null && !selectedproduct4.equals(""))
            items.add(selectedproduct4);
        if (selectedproduct5 != null && !selectedproduct5.equals(""))
            items.add(selectedproduct5);

        StringBuffer retBuf = new StringBuffer();
        retBuf.append("Number of bought products: ");
        retBuf.append(items.size());
        retBuf.append(" ");
        Iterator i = items.iterator();
        while (i.hasNext())
        {
            retBuf.append((String) i.next());
            if (i.hasNext())
                retBuf.append(",");
        }
        return retBuf.toString();
    }

    public void setItemsList()
    {

    }
}
