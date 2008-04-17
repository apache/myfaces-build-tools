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


package org.apache.myfaces.config;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.config.impl.digester.DigesterFacesConfigDispenserImpl;
import org.apache.myfaces.config.impl.digester.DigesterFacesConfigUnmarshallerImpl;
import org.apache.shale.test.base.AbstractJsfTestCase;

public class FacesConfigValidatorTestCase extends AbstractJsfTestCase
{

    private FacesConfigDispenser dispenser;
    private FacesConfigUnmarshaller unmarshaller;
    
    public FacesConfigValidatorTestCase(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(FacesConfigValidatorTestCase.class); // keep this method or maven won't run it
    }
    
    public void setUp()
    {

        super.setUp();
        
        dispenser = new DigesterFacesConfigDispenserImpl();
        unmarshaller = new DigesterFacesConfigUnmarshallerImpl(externalContext);
        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(testFacesConfig.getBytes());
            dispenser.feed(unmarshaller.getFacesConfig(bais, null));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void testVerifyExistence(){
        
        Iterator managedBeans = dispenser.getManagedBeans();
        Iterator navRules = dispenser.getNavigationRules();
        
        List list = FacesConfigValidator.validate(managedBeans, navRules, "C:/somePath/");
        
        int expected = 3;
        
        assertTrue(list.size() + " should equal " + expected, list.size() == expected);
        
    }
    
    private static final String testFacesConfig =
        "<?xml version='1.0' encoding='UTF-8'?>" +
        "<!DOCTYPE faces-config PUBLIC " +
            "\"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\" " +
            "\"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">" +
            "<faces-config>" +
            "<navigation-rule>" +
            "    <from-view-id>/doesNotExist.jsp</from-view-id>" +
            "    <navigation-case>" +
            "        <from-outcome>doesNotMatter</from-outcome>" +
            "        <to-view-id>/doesNotExist2.jsp</to-view-id>" +
            "    </navigation-case>" +
            "</navigation-rule>" +
            "<managed-bean>" +
            "    <managed-bean-name>exist</managed-bean-name>" +
            "    <managed-bean-class>org.apache.myfaces.config.FacesConfigValidatorTestCase</managed-bean-class>" +
            "    <managed-bean-scope>request</managed-bean-scope>" +
            "</managed-bean>" +
            "<managed-bean>" +
            "    <managed-bean-name>nonExist</managed-bean-name>" +
            "    <managed-bean-class>org.apache.myfaces.config.NonExist</managed-bean-class>" +
            "    <managed-bean-scope>request</managed-bean-scope>" +
            "</managed-bean>" +
       "</faces-config>";
}
