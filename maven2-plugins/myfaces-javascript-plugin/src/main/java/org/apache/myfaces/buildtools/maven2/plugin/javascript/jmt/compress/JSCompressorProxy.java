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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @author ndeloof
 */
public class JSCompressorProxy
    implements JSCompressor
{

    private Object compressor;

    private Method compress;

    public JSCompressorProxy( Object compressor )
        throws InitializationException
    {
        super();
        this.compressor = compressor;
        try
        {
            this.compress =
                compressor.getClass().getMethod( "compress",
                    new Class[] { File.class, File.class, int.class, int.class } );
        }
        catch ( Exception e )
        {
            throw new InitializationException(
                "proxied object has no method compress(File,File,int,int)" );
        }
    }

    public void compress( File input, File output, int level, int language )
        throws CompressionException
    {
        try
        {
            compress.invoke( compressor, new Object[] { input, output, Integer.valueOf( level ),
                Integer.valueOf( language ) } );
        }
        catch ( InvocationTargetException e )
        {
            if ( e.getTargetException() instanceof CompressionException )
            {
                throw (CompressionException) e.getTargetException();
            }
            throw new CompressionException( "Failed to compress JS file", e, input );
        }
        catch ( Exception e )
        {
            throw new CompressionException( "Failed to compress JS file", e, input );
        }
    }

}
