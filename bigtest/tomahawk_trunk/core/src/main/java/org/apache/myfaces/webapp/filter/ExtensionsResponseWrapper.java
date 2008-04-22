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
package org.apache.myfaces.webapp.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.xml.sax.InputSource;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExtensionsResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream stream = null;
    private PrintWriter printWriter = null;
    private String contentType;

    public ExtensionsResponseWrapper(HttpServletResponse response){
        super( response );

        stream = new ByteArrayOutputStream();
    }


    public byte[] getBytes() {
        return stream.toByteArray();
    }

    public String toString(){
    	try{
            return stream.toString(getCharacterEncoding());
    	}catch(UnsupportedEncodingException e){
    		// an attempt to set an invalid character encoding would have caused this exception before
            throw new RuntimeException("Response accepted invalid character encoding " + getCharacterEncoding());
    	}
    }

    /** This method is used by Tomcat.
     */
    public PrintWriter getWriter(){
        if( printWriter == null ){
			OutputStreamWriter streamWriter = new OutputStreamWriter(stream, Charset.forName(getCharacterEncoding()));
			printWriter = new PrintWriter(streamWriter, true);
			//printWriter = new PrintWriter(stream, true); // autoFlush is true
        }
        return printWriter;
    }

	/** This method is used by Jetty.
	*/
	public ServletOutputStream getOutputStream(){
		return new MyServletOutputStream( stream );
	}

    public InputSource getInputSource(){
		ByteArrayInputStream bais = new ByteArrayInputStream( stream.toByteArray() );
		return new InputSource( bais );
    }

     /**
     *  Prevent content-length being set as the page might be modified.
     */
    public void setContentLength(int contentLength) {
        // noop
    }

    public void setContentType(String contentType) {
    	super.setContentType(contentType);
    	this.contentType = contentType;
    }
    
    public String getContentType() {
		return contentType;
	}
    
    public void flushBuffer() throws IOException{
    	stream.flush();
    }

    public void finishResponse() {
        try {
            if (printWriter != null) {
				printWriter.close();
            } else {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
        }
    }

    /** Used in the <code>getOutputStream()</code> method.
     */
    private class MyServletOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream outputStream;

		public MyServletOutputStream(ByteArrayOutputStream outputStream){
			this.outputStream = outputStream;
		}

		public void write(int b){
		    outputStream.write( b );
		}

		public void write(byte[] bytes) throws IOException{
		    outputStream.write( bytes );
		}

		public void write(byte[] bytes, int off, int len){
		    outputStream.write(bytes, off, len);
		}
    }
}