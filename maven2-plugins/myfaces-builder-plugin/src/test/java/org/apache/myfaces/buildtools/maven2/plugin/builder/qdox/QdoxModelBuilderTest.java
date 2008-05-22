/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;

/**
 * Tests the QDoxModelBuilderTest generation mojo.
 */
public class QdoxModelBuilderTest extends TestCase
{
    public void testMethodToPropName() throws Exception
    {
        assertEquals("fooBar", QdoxModelBuilder.methodToPropName("getfooBar"));
        assertEquals("fooBar", QdoxModelBuilder.methodToPropName("getFooBar"));
        assertEquals("url", QdoxModelBuilder.methodToPropName("getUrl"));
        assertEquals("url", QdoxModelBuilder.methodToPropName("getURL"));
        assertEquals("urlLocation", QdoxModelBuilder
                .methodToPropName("getUrlLocation"));
        assertEquals("urlLocation", QdoxModelBuilder
                .methodToPropName("getURLLocation"));
    }

    public void testScan() throws Exception
    {
        QdoxModelBuilder builder = new QdoxModelBuilder();
        Model model = new Model();

        URL sourceUrl = this.getClass().getClassLoader().getResource(
                "builder/simple/Foo.java");
        String parentDirName = new File(sourceUrl.getFile()).getParent();
        File parentDir = new File(parentDirName);
        List sourceDirs = new ArrayList();
        sourceDirs.add(parentDir.getAbsolutePath());
        builder.buildModel(model, sourceDirs);

        assertEquals(1, model.getComponents().size());
        ComponentMeta cm = (ComponentMeta) model.getComponents().get(0);
        assertEquals(1, cm.propertiesSize());
        Iterator props = cm.properties();
        PropertyMeta prop1 = (PropertyMeta) props.next();
        assertEquals("prop1", prop1.getName());
        assertEquals("java.lang.String", prop1.getClassName());
    }

    /**
     * Scan a very simple source tree, and compare the result (line by line)
     * against a "known good" file.
     */
    public void testSimple() throws Exception
    {
        QdoxModelBuilder builder = new QdoxModelBuilder();

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL sourceUrl = classLoader.getResource("builder/simple/Foo.java");
        String parentDirName = new File(sourceUrl.getFile()).getParent();
        File parentDir = new File(parentDirName);
        List sourceDirs = new ArrayList();
        sourceDirs.add(parentDir.getAbsolutePath());

        Model model = new Model();
        builder.buildModel(model, sourceDirs);

        // basic sanity checks
        assertTrue(model.getComponents().size() > 0);

        // Now write it. Optionally, we could just write it to an in-memory
        // buffer.
        File outfile = new File("target/simple-out.xml");
        IOUtils.saveModel(model, outfile);

        StringWriter outbuf = new StringWriter();
        IOUtils.writeModel(model, outbuf);
        StringReader reader = new StringReader(outbuf.toString());

        InputStream is = classLoader
                .getResourceAsStream("builder/simple/goodfile.xml");
        compareData(reader, new InputStreamReader(is));
    }

    /**
     * Scan a very simple source tree that uses java15 annotations, and compare
     * the result (line by line) against a "known good" file.
     */
    public void testSimple15() throws Exception
    {
        QdoxModelBuilder builder = new QdoxModelBuilder();

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL sourceUrl = classLoader.getResource("builder/simple15/Foo.java");
        String parentDirName = new File(sourceUrl.getFile()).getParent();
        File parentDir = new File(parentDirName);
        List sourceDirs = new ArrayList();
        sourceDirs.add(parentDir.getAbsolutePath());

        Model model = new Model();
        builder.buildModel(model, sourceDirs);

        // basic sanity checks
        assertTrue(model.getComponents().size() > 0);

        // Now write it. Optionally, we could just write it to an in-memory
        // buffer.
        File outfile = new File("target/simple15-out.xml");
        IOUtils.saveModel(model, outfile);

        StringWriter outbuf = new StringWriter();
        IOUtils.writeModel(model, outbuf);
        StringReader reader = new StringReader(outbuf.toString());

        InputStream is = classLoader
                .getResourceAsStream("builder/simple15/goodfile.xml");
        compareData(reader, new InputStreamReader(is));
    }

    /**
     * Scan a very complex source tree, containing all the different features in
     * various combinations, then dump the result to a file and compare it (line
     * by line) against a "known good" file.
     */
    public void testComplex() throws Exception
    {
        QdoxModelBuilder builder = new QdoxModelBuilder();

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL sourceUrl = classLoader
                .getResource("builder/complex/ComponentBase.java");
        String parentDirName = new File(sourceUrl.getFile()).getParent();
        File parentDir = new File(parentDirName);
        List sourceDirs = new ArrayList();
        sourceDirs.add(parentDir.getAbsolutePath());

        Model model = new Model();
        builder.buildModel(model, sourceDirs);

        // basic sanity checks
        assertTrue(model.getComponents().size() > 0);

        // Now write it. Optionally, we could just write it to an in-memory
        // buffer.
        File outfile = new File("target/complex-out.xml");
        IOUtils.saveModel(model, outfile);

        StringWriter outbuf = new StringWriter();
        IOUtils.writeModel(model, outbuf);
        StringReader reader = new StringReader(outbuf.toString());

        InputStream is = classLoader
                .getResourceAsStream("builder/complex/goodfile.xml");
        compareData(reader, new InputStreamReader(is));
    }

    /**
     * Compare the contents of two Reader objects line-by-line.
     */
    private void compareData(Reader src1, Reader src2) throws IOException
    {
        BufferedReader in1 = new BufferedReader(src1);
        BufferedReader in2 = new BufferedReader(src2);

        int line = 0;
        for (;;)
        {
            ++line;
            String line1 = in1.readLine();
            String line2 = in2.readLine();

            if ((line1 == null) && (line2 == null))
            {
                // success
                return;
            }
            else if (line1 == null)
            {
                fail("input 2 has more lines than input 1");
            }
            else if (line2 == null)
            {
                fail("input 1 has more lines than input 2");
            }

            assertEquals("Inputs differ on line " + line, line1, line2);
        }
    }
}
