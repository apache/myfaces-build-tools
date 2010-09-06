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
package org.myorganization.component.example;

import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myorganization.converter.PhoneNumber;

/**
 * Managed bean for the sayHello page example
 */
public class SayHelloBean
{

    private static final Log log = LogFactory.getLog(SayHelloBean.class);

    private String firstName;
    private String lastName;
    private boolean renderGreeting;
    private Integer oddNumber;
    private PhoneNumber phoneNumber;

    public SayHelloBean()
    {
        this.renderGreeting = false;
    }

    public void sayIt(ActionEvent evt)
    {
        renderGreeting = true;

        if (log.isInfoEnabled())
        {
            log.info("The name to say hello is '"+firstName+"' and the last name is '"+lastName+"'.");
        }
    }


    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    public boolean isRenderGreeting()
    {
        return renderGreeting;
    }

    public void setRenderGreeting(boolean renderGreeting)
    {
        this.renderGreeting = renderGreeting;
    }

    public Integer getOddNumber()
    {
        return oddNumber;
    }

    public void setOddNumber(Integer oddNumber)
    {
        this.oddNumber = oddNumber;
    }
    
    public String submitOddNumber() {
        return "display_number";
    }
    
    public String submitPhoneNumber() {
        return "display_phone";
    }

    public PhoneNumber getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}
