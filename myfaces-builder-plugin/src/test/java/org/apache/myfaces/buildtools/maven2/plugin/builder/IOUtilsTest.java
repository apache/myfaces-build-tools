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
package org.apache.myfaces.buildtools.maven2.plugin.builder;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import junit.framework.TestCase;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;

/**
 * Tests saving and loading models from xml.
 */
public class IOUtilsTest extends TestCase
{
    /**
     * Load the "goodfile.xml" from the complex test case and build a Model
     * object from it, then save it again and compare to the original.
     */
    public void testComplex() throws Exception
    {
        ClassLoader classLoader = this.getClass().getClassLoader();

        InputStream is = classLoader
                .getResourceAsStream("builder/complex/goodfile.xml");
        String src = readAll(is);
        is.close();

        StringReader srcReader = new StringReader(src);
        Model model = IOUtils.readModel(srcReader);

        StringWriter dstWriter = new StringWriter();
        IOUtils.writeModel(model, dstWriter);
        String dst = dstWriter.toString();
        writeAll("target/outfile-ioutils.xml", dst);

        srcReader = new StringReader(src);
        StringReader dstReader = new StringReader(dst);
        compareData(srcReader, dstReader);
    }

    /**
     * Read the contents of an input stream into a string.
     */
    private String readAll(InputStream is) throws IOException
    {
        InputStreamReader reader = new InputStreamReader(is);
        StringBuffer out = new StringBuffer(4096);
        char[] buf = new char[1024];
        while (true)
        {
            int nchars = reader.read(buf);
            if (nchars <= 0)
            {
                break;
            }
            out.append(buf, 0, nchars);
        }
        return out.toString();
    }

    private void writeAll(String dstFile, String src) throws Exception
    {
        FileWriter fw = new FileWriter(dstFile);
        fw.write(src);
        fw.close();
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
