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
package org.myorganization.validator;

import java.io.StringWriter;

import javax.faces.component.html.HtmlInputText;
import javax.faces.validator.ValidatorException;

import junit.framework.Test;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

public class OddNumberValidatorTest extends AbstractJsfTestCase
{

    public static Test suite()
    {
        return null;
    }

    private MockResponseWriter writer ;
    private OddNumberValidator oddNumValidator;

    public OddNumberValidatorTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        oddNumValidator = new OddNumberValidator();
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        oddNumValidator = null;
        writer = null;
    }

    public void testOddNumInput() throws Exception
    {
        boolean noExceptionsThrown = true;
        try 
        {
            oddNumValidator.validate(facesContext, new HtmlInputText(), new Integer(3));
        } 
        catch(ValidatorException ve) 
        {
            noExceptionsThrown = false;
        }
        assertTrue(noExceptionsThrown);
    }
    
    public void testEvenNumInput() throws Exception
    {
        boolean noExceptionsThrown = true;
        try 
        {
            oddNumValidator.validate(facesContext, new HtmlInputText(), new Integer(8));
        } 
        catch(ValidatorException ve) 
        {
            noExceptionsThrown = false;
        }
        assertFalse(noExceptionsThrown);
    }
    
    
}
