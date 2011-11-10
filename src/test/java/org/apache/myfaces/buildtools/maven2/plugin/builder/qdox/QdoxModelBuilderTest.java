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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelParams;
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
        assertEquals("fooBar", QdoxHelper.methodToPropName("getfooBar"));
        assertEquals("fooBar", QdoxHelper.methodToPropName("getFooBar"));
        assertEquals("url", QdoxHelper.methodToPropName("getUrl"));
        assertEquals("url", QdoxHelper.methodToPropName("getURL"));
        assertEquals("urlLocation", QdoxHelper
                .methodToPropName("getUrlLocation"));
        assertEquals("urlLocation", QdoxHelper
                .methodToPropName("getURLLocation"));
    }

    public void testScan() throws Exception
    {
        QdoxModelBuilder builder = new QdoxModelBuilder();
        Model model = new Model();
        model.setModelId("test");

        URL sourceUrl = this.getClass().getClassLoader().getResource(
                "builder/simple/Foo.java");
        String parentDirName = new File(sourceUrl.getFile()).getParent();
        File parentDir = new File(parentDirName);
        List sourceDirs = new ArrayList();
        sourceDirs.add(parentDir.getAbsolutePath());
        
        ModelParams parameters = new ModelParams();
        parameters.setSourceDirs(sourceDirs);
        builder.buildModel(model, parameters);

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
        model.setModelId("test");
        ModelParams parameters = new ModelParams();
        parameters.setSourceDirs(sourceDirs);
        builder.buildModel(model, parameters);

        // basic sanity checks
        assertTrue(model.getComponents().size() > 0);

        // Now write it. Optionally, we could just write it to an in-memory
        // buffer.
        File outfile = new File("target/simple-out.xml");
        IOUtils.saveModel(model, outfile);

        StringWriter outbuf = new StringWriter();
        IOUtils.writeModel(model, outbuf);

        compareData(outfile, "builder/simple/goodfile.xml");
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
        model.setModelId("test");
        ModelParams parameters = new ModelParams();
        parameters.setSourceDirs(sourceDirs);
        builder.buildModel(model, parameters);

        // basic sanity checks
        assertTrue(model.getComponents().size() > 0);

        // Now write it. Optionally, we could just write it to an in-memory
        // buffer.
        File outfile = new File("target/simple15-out.xml");
        IOUtils.saveModel(model, outfile);

        StringWriter outbuf = new StringWriter();
        IOUtils.writeModel(model, outbuf);

        compareData(outfile, "builder/simple15/goodfile.xml");
    }

    /**
     * Scan annotated JSFComponent classes which use code-generation to 
     * actually create component classes.
     */
    public void testGeneration() throws Exception
    {
        QdoxModelBuilder builder = new QdoxModelBuilder();

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL sourceUrl = classLoader
                .getResource("builder/generation/testpkg/ComponentBase.java");
        String parentDirName = new File(sourceUrl.getFile()).getParent();
        File parentDir = new File(parentDirName);
        File baseDir = parentDir.getParentFile();
        List sourceDirs = new ArrayList();
        sourceDirs.add(baseDir.getAbsolutePath());

        Model model = new Model();
        model.setModelId("test");
        ModelParams parameters = new ModelParams();
        parameters.setSourceDirs(sourceDirs);
        builder.buildModel(model, parameters);

        // basic sanity checks
        assertTrue(model.getComponents().size() > 0);

        // Now write it. Optionally, we could just write it to an in-memory
        // buffer.
        File outfile = new File("target/generation-out.xml");
        IOUtils.saveModel(model, outfile);

        StringWriter outbuf = new StringWriter();
        IOUtils.writeModel(model, outbuf);

        compareData(outfile, "builder/generation/goodfile.xml");
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
        model.setModelId("test");
        ModelParams parameters = new ModelParams();
        parameters.setSourceDirs(sourceDirs);
        builder.buildModel(model, parameters);

        // basic sanity checks
        assertTrue(model.getComponents().size() > 0);

        // Now write it. Optionally, we could just write it to an in-memory
        // buffer.
        File outfile = new File("target/complex-out.xml");
        IOUtils.saveModel(model, outfile);

        StringWriter outbuf = new StringWriter();
        IOUtils.writeModel(model, outbuf);

        compareData(outfile, "builder/complex/goodfile.xml");
    }

    /**
     * Compare the contents of two Reader objects line-by-line.
     */
    private void compareData(File testDatafileName, String goodResourceName) throws IOException
    {
        Reader testDataReaderRaw = new FileReader(testDatafileName);

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream goodDataStream = classLoader.getResourceAsStream(goodResourceName);
        assertNotNull("good resource file not found: " + goodResourceName, goodDataStream);
        Reader goodDataReaderRaw = new InputStreamReader(goodDataStream);

        BufferedReader testDataReader = new BufferedReader(testDataReaderRaw);
        BufferedReader goodDataReader = new BufferedReader(goodDataReaderRaw);

        try
        {
            int line = 0;
            for (;;)
            {
                ++line;
                String testLine = testDataReader.readLine();
                String goodLine = goodDataReader.readLine();
    
                if ((testLine == null) && (goodLine == null))
                {
                    // success
                    break;
                }
                else if (testLine == null)
                {
                    fail("Test input " + testDatafileName + " has fewer lines than good file " + goodResourceName);
                }
                else if (goodLine == null)
                {
                    fail("Test input " + testDatafileName + " has more lines than good file " + goodResourceName);
                }
    
                assertEquals(
                        "Test input " + testDatafileName + " and good file " + goodResourceName +
                        " differ on line " + line, 
                        goodLine, testLine);
            }
        }
        finally
        {
            testDataReaderRaw.close();
            goodDataStream.close();
        }
    }
}
