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

package org.apache.myfaces.custom.requestParameterProvider;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @author Thomas Obereder
 * @version 30.04.2006 14:40:05
 */
public class RequestParameterResponseWrapper implements HttpServletResponse
{
    protected HttpServletResponse original;
    private String contentType;

    public RequestParameterResponseWrapper(HttpServletResponse httpServletResponse)
    {
        //super();
        this.original = httpServletResponse;
    }

    /**
     * @param url the url to encode
     * @return wrappedResponse.encodeUrl(url);
     */
    public String encodeURL(String url)
    {
        if(url == null)
            return null;

        url = RequestParameterProviderManager.getInstance().encodeAndAttachParameters(url);

        return this.original.encodeURL(url);
    }


    /**
     * @param url the url to encode
     * @return wrappedResponse.encodeUrl(url);
     */

    public String encodeRedirectURL(String url)
    {
        return this.original.encodeRedirectURL(url);
    }


    /**
     * @deprecated uses deprecated Method.
     * @param url the url to encode
     * @return wrappedResponse.encodeUrl(url);
     */
    public String encodeUrl(String url)
    {
        return this.original.encodeUrl(url);
    }


    /**
     * @deprecated uses deprecated Method.
     * @param url the url to encode
     * @return wrappedResponse.encodeUrl(url);
     */
    public String encodeRedirectUrl(String url)
    {
        return this.original.encodeRedirectUrl(url);
    }


    //simple delegation

    public void addCookie(Cookie cookie)
    {
        this.original.addCookie(cookie);
    }


    public boolean containsHeader(String header)
    {
        return this.original.containsHeader(header);
    }

    public void sendError(int i, String string) throws IOException
    {
        this.original.sendError(i, string);
    }

    public void sendError(int i) throws IOException
    {
        this.original.sendError(i);
    }

    public void sendRedirect(String string) throws IOException
    {
        this.original.sendRedirect(string);
    }

    public void setDateHeader(String string, long l)
    {
        this.original.setDateHeader(string, l);
    }

    public void addDateHeader(String string, long l)
    {
        this.original.addDateHeader(string, l);
    }

    public void setHeader(String string, String string1)
    {
        this.original.setHeader(string, string1);
    }

    public void addHeader(String string, String string1)
    {
        this.original.addHeader(string, string1);
    }

    public void setIntHeader(String string, int i)
    {
        this.original.setIntHeader(string, i);
    }

    public void addIntHeader(String string, int i)
    {
        this.original.addIntHeader(string, i);
    }

    public void setStatus(int i)
    {
        this.original.setStatus(i);
    }


    /**
     * @deprecated uses deprecated Method
     */

    public void setStatus(int i, String string)
    {
        this.original.setStatus(i, string);
    }

    public String getCharacterEncoding()
    {
        return this.original.getCharacterEncoding();
    }

    public ServletOutputStream getOutputStream() throws IOException
    {
        return this.original.getOutputStream();
    }

    public PrintWriter getWriter() throws IOException
    {
        return this.original.getWriter();
    }

    public void setContentLength(int i)
    {
        this.original.setContentLength(i);
    }

    public void setContentType(String string)
    {
        this.contentType = string;
        this.original.setContentType(string);
    }

    public void setBufferSize(int i)
    {
        this.original.setBufferSize(i);
    }

    public int getBufferSize()
    {
        return this.original.getBufferSize();
    }

    public void flushBuffer() throws IOException
    {
        this.original.flushBuffer();
    }

    public void resetBuffer()
    {
        this.original.resetBuffer();
    }

    public boolean isCommitted()
    {
        return this.original.isCommitted();
    }

    public void reset()
    {
        this.original.reset();
    }

    public void setLocale(Locale locale)
    {
        this.original.setLocale(locale);
    }

    public Locale getLocale()
    {
        return this.original.getLocale();
    }

    public String getContentType()
    {
        return contentType;
    }
}
