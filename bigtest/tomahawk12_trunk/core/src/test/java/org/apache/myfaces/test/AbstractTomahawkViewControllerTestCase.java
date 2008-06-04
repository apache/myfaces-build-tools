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
package org.apache.myfaces.test;

import java.io.StringWriter;

import org.apache.myfaces.shared_tomahawk.config.MyfacesConfig;
import org.apache.myfaces.test.utils.TestUtils;
import org.apache.shale.test.base.AbstractViewControllerTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * Abstract Shale Test base class, which sets up the JSF environment.  If the test
 * overrides <code>setUp()</code> and/or <code>tearDown()</code>, then those
 * methods but call the overwitten method to insure a valid test environment.
 */
public class AbstractTomahawkViewControllerTestCase extends AbstractViewControllerTestCase
{
    /** Response Writer */
    private MockResponseWriter writer;

    /**
     * Construct a new instance of the test.
     * 
     * @param name Name of the test.
     */
    public AbstractTomahawkViewControllerTestCase(String name)
    {
        super(name);
    }

    /**
     *  Setup the test environment, including the following:
     *  <ul>
     *  <li>Set the Application Map.</li>
     *  <li>Set a response writer</li>
     *  <li>Add Tomahawk's renderers to the faces context.</li>
     *  </ul> 
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        // additional setup not provided automatically by the shale mock stuff
        facesContext.getExternalContext().getApplicationMap().put(
                MyfacesConfig.class.getName(), new MyfacesConfig());
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        TestUtils.addDefaultRenderers(facesContext);
    }

    /**
     * Tear down the test environment.
     */
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Verify the following:
     * <ul>
     * <li>id is not null</li>
     * <li>Response is complete</li>
     * <li>Responce contains the id</li>
     * </ul>
     * 
     * @param id ID to verify
     */
    protected void assertIdExists(String id)
    {
        assertNotNull("ID is not null", id);
        assertTrue("Response Complete", facesContext.getResponseComplete());
        String output = writer.getWriter().toString();
//        System.out.println("Output = '" + output + "'");
        assertNotNull("Has output", output);
        assertTrue("Contains id '" + id + "'", output.indexOf(id) != -1);
    }

}
