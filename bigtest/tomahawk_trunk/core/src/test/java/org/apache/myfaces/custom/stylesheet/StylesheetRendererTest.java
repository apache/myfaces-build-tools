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

package org.apache.myfaces.custom.stylesheet;

import java.io.IOException;
import java.io.StringWriter;

import junit.framework.Test;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

public class StylesheetRendererTest extends AbstractTomahawkViewControllerTestCase
{

    private MockResponseWriter writer ;
    private Stylesheet stylesheet ;
    
    public StylesheetRendererTest(String name)
    {
        super(name);
    }
    
    protected void setUp() throws Exception
    {
        super.setUp();
        stylesheet = new Stylesheet();
        stylesheet.setPath("test.css");
        stylesheet.setMedia("printer");
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
//        facesContext.getRenderKit().addRenderer(
//                stylesheet.getFamily(), 
//                stylesheet.getRendererType(), 
//                new StylesheetRenderer());
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        writer = null;
        stylesheet = null;
    }

    public static Test suite()
    {
        return null; // keep this method or maven won't run it
    }

    /*
     * Test method for 'org.apache.myfaces.custom.stylesheet.StylesheetRenderer.encodeEnd(FacesContext, UIComponent)'
     */
    public void testInline() throws IOException
    {

        stylesheet.setInline(true);
        stylesheet.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        
        assertTrue("looking for a <style>", output.trim().startsWith("<style"));
        
    }

    public void testLink() throws IOException
    {

        stylesheet.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();
        
        assertTrue("looking for a <link>", output.trim().startsWith("<link"));
    }

}
