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

package org.apache.myfaces.custom.template;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockResponseWriter;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockHttpServletRequest;
import org.apache.shale.test.mock.MockValueBinding;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.*;
import java.net.URL;
import java.net.URI;

/**
 * Test case for XmlTemplate
 *
 * @author Sean Schofield
 */
public class XmlTemplateTest extends AbstractJsfTestCase
{
    private static final String PETS_STYLESHEET = "pets.xsl";
    private static final String PETS_CONTENT = "pets.xml";
    private static final String EXPECTED_TEXT = "Iguana";

    private XmlTemplate xmlTemplate;
    private ManagedFoo fooBean;
    private String stylesheet;
    private String stylesheetLocation = PETS_STYLESHEET;
    private InputStream styleStream;
    private String content;
    private String contentLocation = PETS_CONTENT;
    private InputStream contentStream;

    private StringWriter mockWriter = new StringWriter();

    /**
     * Constructor
     * @param name String
     */
    public XmlTemplateTest(String name)
    {
        super(name);
    }

    /**
     * See abstract class
     */
    public void setUp() throws Exception
    {
        super.setUp();

        xmlTemplate = new XmlTemplate();

        // additional setup not provided automatically by the shale mock stuff
        facesContext.setResponseWriter(new MockResponseWriter(mockWriter, null, null));

        // TODO remove these two lines once shale-test goes alpha, see MYFACES-1155
        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);

        fooBean = new ManagedFoo();
        fooBean.setContentLocation(PETS_CONTENT);
        fooBean.setStylesheetLocation(PETS_STYLESHEET);

        // put the foo bean in the request scope
        MockHttpServletRequest request = (MockHttpServletRequest)facesContext.getExternalContext().getRequest();
        request.setAttribute("foo", fooBean);

        // setup input stream to be used in some of the test cases
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null)
        {
            loader = XmlTemplate.class.getClassLoader();
        }

        URL url = loader.getResource(PETS_CONTENT);
        try
        {
            contentStream = new FileInputStream(new File(URI.create(url.toString())));

            // now also populate the String variable for certain test cases
            FileInputStream cs = new FileInputStream(new File(URI.create(url.toString())));
            int x= cs.available();
            byte b[]= new byte[x];
            //noinspection ResultOfMethodCallIgnored
            cs.read(b);
            content = new String(b);
        }
        catch (IOException io)
        {}  // do nothing - test will fail eventually

        // setup stylesheet text to be used in some of the test cases
        url = loader.getResource(PETS_STYLESHEET);
        try
        {
            styleStream = new FileInputStream(new File(URI.create(url.toString())));

            // now also populate the String variable for certain test cases
            FileInputStream ss = new FileInputStream(new File(URI.create(url.toString())));
            int x= ss.available();
            byte b[]= new byte[x];
            //noinspection ResultOfMethodCallIgnored
            ss.read(b);
            stylesheet = new String(b);
        }
        catch (IOException io)
        {}  // do nothing - test will fail eventually

        fooBean.setContent(content);
        fooBean.setStylesheet(stylesheet);
        fooBean.setContentStream(contentStream);
        fooBean.setStyleStream(styleStream);
    }

    /**
     * Test the save and restore state methods.
     */
    public void testSaveAndRestoreState()
    {
        /**
         * NOTE: Normally you would not set all of these properties together in the component, but for the
         * purposes of testing, we want to make sure all fields are saved and restored properly.
         */
        xmlTemplate.setContent(content);
        xmlTemplate.setContentLocation(contentLocation);
        xmlTemplate.setStylesheet(stylesheet);
        xmlTemplate.setStylesheetLocation(stylesheetLocation);
        xmlTemplate.setContentStream(contentStream);
        xmlTemplate.setStyleStream(styleStream);

        Object state = xmlTemplate.saveState(facesContext);
        xmlTemplate = new XmlTemplate();
        xmlTemplate.restoreState(facesContext, state);

        assertEquals(content, xmlTemplate.getContent());
        assertEquals(contentLocation, xmlTemplate.getContentLocation());
        assertEquals(stylesheet, xmlTemplate.getStylesheet());
        assertEquals(stylesheetLocation, xmlTemplate.getStylesheetLocation());
        assertNull("contentStream should be null since it cannot be serialized", xmlTemplate.getContentStream());
        assertNull("styleStream should be null since it cannot be serialized", xmlTemplate.getStyleStream());
    }

    /**
     * Test the save and restore state methods with all values as value binding expressions.  This also tests
     * the value bindings themselves to make sure they are working.
     */
    public void testSaveAndRestoreStateValueBinding()
    {
        /**
         * NOTE: Normally you would not set all of these properties together in the component, but for the
         * purposes of testing, we want to make sure all fields are saved and restored properly.
         */
        xmlTemplate.setValueBinding("content", new MockValueBinding(application, "#{foo.content}"));
        xmlTemplate.setValueBinding("contentLocation", new MockValueBinding(application, "#{foo.contentLocation}"));
        xmlTemplate.setValueBinding("stylesheet", new MockValueBinding(application, "#{foo.stylesheet}"));
        xmlTemplate.setValueBinding("stylesheetLocation", new MockValueBinding(application, "#{foo.stylesheetLocation}"));
        xmlTemplate.setValueBinding("contentStream", new MockValueBinding(application, "#{foo.contentStream}"));
        xmlTemplate.setValueBinding("styleStream", new MockValueBinding(application, "#{foo.styleStream}"));

        Object state = xmlTemplate.saveState(facesContext);
        xmlTemplate = new XmlTemplate();
        xmlTemplate.restoreState(facesContext, state);

        assertEquals(content, xmlTemplate.getContent());
        assertEquals(contentLocation, xmlTemplate.getContentLocation());
        assertEquals(stylesheet, xmlTemplate.getStylesheet());
        assertEquals(stylesheetLocation, xmlTemplate.getStylesheetLocation());
        assertEquals(contentStream, xmlTemplate.getContentStream());
    }

    public void testSetContent()
    {
        xmlTemplate.setContent("foo");
        assertEquals("foo", xmlTemplate.getContent());

        // reset property so we can test value binding
        xmlTemplate.setContent(null);
        xmlTemplate.setValueBinding("content", new MockValueBinding(application, "#{foo.content}"));
        assertEquals(fooBean.getContent(), xmlTemplate.getContent());
    }

    public void testSetContentStream()
    {
        xmlTemplate.setContentStream(fooBean.getContentStream());
        assertEquals(fooBean.getContentStream(), xmlTemplate.getContentStream());
    }

    public void testSetContentStreamValueBinding()
    {
        xmlTemplate.setValueBinding("contentStream", new MockValueBinding(application, "#{foo.contentStream}"));
        assertEquals(fooBean.getContentStream(), xmlTemplate.getContentStream());
    }

    public void testSetStylesheet()
    {
        xmlTemplate.setStylesheet("foo");
        assertEquals("foo", xmlTemplate.getStylesheet());

        // reset property so we can test value binding
        xmlTemplate.setStylesheet(null);
        xmlTemplate.setValueBinding("stylesheet", new MockValueBinding(application, "#{foo.stylesheet}"));
        assertEquals(fooBean.getStylesheet(), xmlTemplate.getStylesheet());
    }

    /**
     * Component should throw NPE if no stylesheet or template is provided
     */
    public void testNoTransformInfo() throws IOException
    {
        try
        {
            xmlTemplate.encodeBegin(facesContext);
        }
        catch (NullPointerException e)
        {
            return;
        }

        fail("Expected exception when no template or stylesheet provided");
    }

    /**
     * Component should throw NPE if no content is provided
     */
    public void testStylesheetNoContent() throws IOException
    {
        // stylesheet is not sufficient, content must be provided
        xmlTemplate.setStylesheet("blah");

        try
        {
            xmlTemplate.encodeBegin(facesContext);
        }
        catch (NullPointerException e)
        {
            return;
        }

        fail("Expected exception when stylesheet but no content provided");
    }

    public void testStyleSheet() throws IOException
    {
        xmlTemplate.setContentLocation(contentLocation);
        xmlTemplate.setStylesheet(stylesheet);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testStyleSheetValueBinding() throws IOException
    {
        xmlTemplate.setContent(content);
        xmlTemplate.setValueBinding("stylesheet", new MockValueBinding(application, "#{foo.stylesheet}"));
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testStylesheetLocation() throws IOException
    {
        xmlTemplate.setContent(content);
        xmlTemplate.setStylesheetLocation(stylesheetLocation);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testStylesheetLocationValueBinding() throws IOException
    {
        xmlTemplate.setContent(content);
        xmlTemplate.setValueBinding("stylesheetLocation", new MockValueBinding(application, "#{foo.stylesheetLocation}"));
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testStylesheetStream() throws IOException
    {
        xmlTemplate.setStyleStream(styleStream);
        xmlTemplate.setContent(content);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testStylesheetStreamValueBinding() throws IOException
    {
        xmlTemplate.setValueBinding("styleStream", new MockValueBinding(application, "#{foo.styleStream}"));
        xmlTemplate.setContent(content);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testContent() throws IOException
    {
        xmlTemplate.setContent(content);
        xmlTemplate.setStylesheet(stylesheet);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testContentValueBinding() throws IOException
    {
        xmlTemplate.setValueBinding("content", new MockValueBinding(application, "#{foo.content}"));
        xmlTemplate.setStylesheet(stylesheet);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testContentLocation() throws IOException
    {
        xmlTemplate.setContentLocation(contentLocation);
        xmlTemplate.setStylesheet(stylesheet);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public void testContentLocationValueBinding() throws IOException
    {
        xmlTemplate.setValueBinding("contentLocation", new MockValueBinding(application, "#{foo.contentLocation}"));
        xmlTemplate.setStylesheet(stylesheet);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    /**
     * Tests the actual transformation process using an InputStream as the source for content.
     * @throws IOException
     */
    public void testContentStream() throws IOException
    {
        xmlTemplate.setContentStream(contentStream);
        xmlTemplate.setStylesheet(stylesheet);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    /**
     * Tests the actual transformation process using an InputStream as the source for content.  This test
     * sets the property to a value binding (as you would through use of XmlTemplateTag) instead of setting
     * the value of the contentStream on the component directly.
     *
     * @throws IOException
     */
    public void testContentStreamValueBinding() throws IOException
    {
        xmlTemplate.setValueBinding("contentStream", new MockValueBinding(application, "#{foo.contentStream}"));
        xmlTemplate.setStylesheetLocation(stylesheetLocation);
        xmlTemplate.encodeBegin(facesContext);

        String responseText = mockWriter.toString();
        assertEquals("Unexpected response text from content transformation", EXPECTED_TEXT, responseText);
    }

    public static Test suite()
    {
        return new TestSuite(XmlTemplateTest.class);
    }

}
