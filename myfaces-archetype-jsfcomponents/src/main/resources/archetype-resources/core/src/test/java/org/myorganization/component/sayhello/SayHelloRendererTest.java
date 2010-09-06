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
package org.myorganization.component.sayhello;

import junit.framework.Test;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Class to test the behaviour of the SayHello renderer
 */
public class SayHelloRendererTest extends AbstractJsfTestCase
{

    public static Test suite()
    {
        return null;
    }

    private MockResponseWriter writer ;
    private SayHello sayHello;

    public SayHelloRendererTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        sayHello = new SayHello();
        sayHello.setFirstName("John");
        sayHello.setLastName("Smith");

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
        // TODO remove these two lines once shale-test goes alpha, see MYFACES-1155
        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                sayHello.getFamily(),
                sayHello.getRendererType(),
                new SayHelloRenderer());
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        sayHello = null;
        writer = null;
    }

    public void testEncodeEnd()  throws Exception
    {
        sayHello.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();

        assertEquals("Hello John Smith!", output);
        assertNotSame("Bye John Smith!", output);
    }

}
