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
package org.apache.myfaces.renderkit.html.util;

import junit.framework.TestCase;
import org.apache.myfaces.application.ApplicationFactoryImpl;
import org.apache.myfaces.context.servlet.ServletFacesContextImpl;
import org.apache.myfaces.renderkit.RenderKitFactoryImpl;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlResponseWriterImpl;
import org.easymock.MockControl;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Unit test for the AddResource class which can output script, style and inline
 * javascript into the header or body of an HTML response page.
 */
public class AddResourceTest extends TestCase
{
    public void testGetInstance()
    {

       Map cacheMap = new LinkedHashMap();

        AddResource instance1 = AddResourceFactory.getInstance(null,cacheMap, "/test1", null);
        assertNotNull(instance1);

        /* no longer true
        AddResource instance2 = AddResourceFactory.getInstance(null, "/test2", null);
        assertNotSame(instance1, instance2);
        */

        AddResourceFactory.getInstance(null,cacheMap, "/test1", null);
    }

    public void setUp()
    {
        // Make sure a FacesContext configured from some previous test case
        // doesn't interfere with the test case we are about to run...
        FacesContextHelper.setCurrentInstance(null);
    }

    public void tearDown()
    {
        // Make sure a FacesContext we may have configured in the test case
        // just completed doesn't interfere with test cases run later.
        FacesContextHelper.setCurrentInstance(null);
    }

    /**
     * Simple test helper class to allow unit tests to configure
     * mock FacesContext objects as the "current instance".
     * <p>
     * The method FacesContext.setCurrentInstance is protected, and
     * hence cannot be accessed by unit tests wanting to configure
     * a mock object as the value seen by code calling method
     * FacesContext.getCurrentInstance().
     */
    private static abstract class FacesContextHelper extends FacesContext
    {
        public static void setCurrentInstance(FacesContext other)
        {
            FacesContext.setCurrentInstance(other);
        }
    }

    /**
     * Configure a fake JSF environment for a test, consisting of a
     * FacesContext and dependent objects.
     * <p>
     * EasyMock control objects are used to emulate the necessary bits.
     */
    private static class MockState
    {
        Writer _writer;
        ResponseWriter _htmlResponseWriter;
        MockControl _servletRequestControl;
        HttpServletRequest _servletRequest;
        MockControl _servletResponseControl;
        HttpServletResponse _servletResponse;
        FacesContext _facesContext;

        public void setup() throws IOException
        {
            // set up a writer object to be the "response" output stream.
            _writer = new StringWriter();
            String contentType = "text/html";
            String charEncoding = "UTF-8";
            _htmlResponseWriter = new HtmlResponseWriterImpl(_writer, contentType, charEncoding);

            // Mock ServletRequest object that:
            // * returns "/test" for context path
            // * returns "/test/foo.jsp" for servlet path
            // * returns "null" for getPathInfo
            // * returns "null" for getHeader
            // * returns "null" for getAttribute
            // * returns null for getSession
            _servletRequestControl = MockControl.createControl(HttpServletRequest.class);
            _servletRequest = (HttpServletRequest) _servletRequestControl.getMock();
            _servletRequest.getContextPath();
            _servletRequestControl.setReturnValue("/test", MockControl.ZERO_OR_MORE);
            _servletRequest.getServletPath();
            _servletRequestControl.setReturnValue("/test/foo.jsp", MockControl.ZERO_OR_MORE);
            _servletRequest.getPathInfo();
            _servletRequestControl.setReturnValue("", MockControl.ZERO_OR_MORE);
            _servletRequest.getHeader("");
            _servletRequestControl.setMatcher(MockControl.ALWAYS_MATCHER);
            _servletRequestControl.setReturnValue(null, MockControl.ZERO_OR_MORE);
            _servletRequest.getAttribute("");
            _servletRequestControl.setMatcher(MockControl.ALWAYS_MATCHER);
            _servletRequestControl.setReturnValue(null, MockControl.ZERO_OR_MORE);
            _servletRequest.setAttribute("", "");
            _servletRequestControl.setMatcher(MockControl.ALWAYS_MATCHER);
            _servletRequestControl.setVoidCallable(MockControl.ZERO_OR_MORE);
            _servletRequest.getSession(false);
            _servletRequestControl.setReturnValue(null, MockControl.ZERO_OR_MORE);
            _servletRequestControl.replay();

            // Mock ServletResponse object that:
            // * returns appropriate encoded URLs
            // * returns a PrintWriter wrapping this object's writer member for
            //   calls to getWriter
            _servletResponseControl = MockControl.createControl(HttpServletResponse.class);
            _servletResponse = (HttpServletResponse) _servletResponseControl.getMock();
            _servletResponse.encodeURL("/test/scripts/script1");
            _servletResponseControl.setReturnValue("encoded(/test/scripts/script1)", MockControl.ZERO_OR_MORE);
            _servletResponse.getWriter();
            _servletResponseControl.setReturnValue(new PrintWriter(_writer), MockControl.ZERO_OR_MORE);
            _servletResponse.getCharacterEncoding();
            _servletResponseControl.setReturnValue("UTF-8", MockControl.ZERO_OR_MORE);
            _servletResponseControl.replay();

            // The FacesContext needs FactoryFinder configured.
            FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY, ApplicationFactoryImpl.class.getName());
            FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY, RenderKitFactoryImpl.class.getName());

            // Now create a FacesContext....
            _facesContext = new ServletFacesContextImpl(null,
                    _servletRequest, _servletResponse);
            _facesContext.setResponseWriter(_htmlResponseWriter);
        }

        public void verifyControls()
        {
            _servletRequestControl.verify();
            _servletResponseControl.verify();
        }
    }

    public void testAddJavaScriptHere() throws IOException
    {
        MockState mockState = new MockState();
        mockState.setup();

        // now start the test
        AddResource instance1 = AddResourceFactory.getInstance(null,null,"/test", null);
        instance1.addJavaScriptHere(mockState._facesContext, "/scripts/script1");

        // verify that our mock objects got the expected callbacks
        mockState.verifyControls();

        // verify that:
        // *script tag is output
        // * type attribute is right,
        // * URL has context path prepended to it
        // * URL has been encoded
        // * HTML comments have been used to hide script from non-script-aware
        //   browsers.
        //
        // NOTE: are comments required to hide this script from browsers when
        // there isn't any script body content (just a src attr)?
        assertEquals(
            "<script type=\"text/javascript\""
            + " src=\"encoded(/test/scripts/script1)\">"
            + "<!--\n\n//--></script>",
            mockState._writer.toString());
    }

    public void testWriteWithFullHeader() throws IOException
    {
        MockState mockState = new MockState();
        mockState.setup();

        String originalResponse =
            "<html><head></head><body></body></html>";

        AddResource ar = AddResourceFactory.getInstance(null,null,"/test", null);
        ar.parseResponse(mockState._servletRequest,originalResponse,mockState._servletResponse);
        ar.writeWithFullHeader(mockState._servletRequest,mockState._servletResponse);
        ar.writeResponse(mockState._servletRequest,mockState._servletResponse);

        mockState.verifyControls();

        System.out.println(mockState._writer.toString());
        assertEquals(originalResponse, mockState._writer.toString());
    }
}
