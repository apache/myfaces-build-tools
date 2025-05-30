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
package org.apache.myfaces.buildtools.maven2.plugin.javascript.jmt.compress;

import java.io.File;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:nicolas@apache.org">nicolas De Loof</a>
 */
public class YahooUICompressorTest
    extends TestCase
{
    private JSCompressor compressor = new YahooUICompressor();

    /**
     * Check the compressor is invoked as expected.
     * 
     * @throws Exception
     */
    public void testCompress()
        throws Exception
    {
        File input = new File( "src/test/resources/test.js" );
        File output = new File( "target/test-out.js" );

        compressor.compress( input, output, JSCompressor.MAX, JSCompressor.JAVASCRIPT_1_3 );

        assertTrue( "expected file not found", output.exists() );
        assertTrue( "no compression occurred", output.length() < input.length() );
    }
}
