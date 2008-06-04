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
package org.apache.myfaces.wap.renderkit;

import java.util.*;
import java.io.Writer;

import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.HTMLEncoder;

/**
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */ 

public class WmlResponseWriterImpl extends ResponseWriter {
    private static Log log = LogFactory.getLog(WmlResponseWriterImpl.class);

    private static final String DEFAUL_CONTENT_TYPE = "text/vnd.wap.wml";
    private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

    private Writer writer;
    private String contentType;
    private String characterEncoding;
    private String startElementName;
    private boolean startTagOpen;

    private static final Set s_emptyHtmlElements = new HashSet();
    static {
        s_emptyHtmlElements.add("img");
        s_emptyHtmlElements.add("postfield");
    }

    /** Creates a new instance of WmlResponseWriter */
    public WmlResponseWriterImpl(Writer writer, String contentType, String characterEncoding) {
        log.debug("created object " + this.getClass().getName());
        this.writer = writer;
        this.contentType = contentType;
        this.characterEncoding = characterEncoding;

        if (contentType == null) {
            log.info("Content type is null, using default value: " + DEFAUL_CONTENT_TYPE);
            this.contentType = DEFAUL_CONTENT_TYPE;
        }

        if (characterEncoding == null) {
            log.info("Character encoding is null, using default value: " + DEFAULT_CHARACTER_ENCODING);
            this.characterEncoding = DEFAULT_CHARACTER_ENCODING;
        }
    }

    public ResponseWriter cloneWithWriter(Writer writer) {
        WmlResponseWriterImpl newWriter = new WmlResponseWriterImpl(writer, getContentType(), getCharacterEncoding());
        return(newWriter);
    }

    public void startElement(String name, javax.faces.component.UIComponent uIComponent) throws java.io.IOException {
        if (name == null) {
            throw new NullPointerException("elementName name must not be null");
        }

        closeStartTagIfNecessary();
        writer.write('<');
        writer.write(name);
        startElementName = name;
        startTagOpen = true;
    }

    public void endElement(String name) throws java.io.IOException {
        if (name == null) {
            throw new NullPointerException("elementName name must not be null");
        }

        if (log.isWarnEnabled()) {
            if (startElementName != null && !name.equals(startElementName)) {
                log.warn("WML nesting warning on closing " + name + ": element " + startElementName + " not explicitly closed");
            }
        }

        if(startTagOpen) {
            // we will get here only if no text or attribute was written after the start element was opened
            writer.write(" />");
            startTagOpen = false;
        }
        else {
            writer.write("</");
            writer.write(name);
            writer.write('>');
        }

        startElementName = null;
    }

    public void writeText(Object value, String componentPropertyName) throws java.io.IOException {
        if (value == null) {
            throw new NullPointerException("text name must not be null");
        }

        closeStartTagIfNecessary();
        if(value == null)
            return;

        String strValue = value.toString(); //TODO: Use converter for value?
        writer.write(strValue);
    }

    public void writeText(char cbuf[], int off, int len) throws java.io.IOException {
        if (cbuf == null) {
            throw new NullPointerException("cbuf name must not be null");
        }
        if (cbuf.length < off + len) {
            throw new IndexOutOfBoundsException((off + len) + " > " + cbuf.length);
        }

        closeStartTagIfNecessary();

        writer.write(cbuf, off, len);
    }

    public void writeComment(Object value) throws java.io.IOException {
        if (value == null) {
            throw new NullPointerException("comment name must not be null");
        }

        closeStartTagIfNecessary();
        writer.write("<!--");
        writer.write(value.toString());    //TODO: Escaping: must not have "-->" inside!
        writer.write("-->");
    }

    public void writeAttribute(String name, Object value, String componentPropertyName) throws java.io.IOException {
        if (name == null) {
            throw new NullPointerException("attributeName name must not be null");
        }
        if (!startTagOpen) {
            throw new IllegalStateException("Must be called before the start element is closed (attribute '" + name + "')");
        }

        if (value instanceof Boolean) {
            if (((Boolean)value).booleanValue()) {
                // name as value for XHTML compatibility
                writer.write(' ');
                writer.write(name);
                writer.write("=\"");
                writer.write(name);
                writer.write('"');
            }
        }
        else {
            String strValue = value.toString(); //TODO: Use converter for value
            writer.write(' ');
            writer.write(name);
            writer.write("=\"");
            writer.write(HTMLEncoder.encode(strValue, false, false));
            writer.write('"');
        }
    }

    public String getContentType() {
        return contentType;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void startDocument() throws java.io.IOException {
        // do nothing
    }

    public void endDocument() throws java.io.IOException {
        flush();
        writer.flush();
    }

    public void writeURIAttribute(String name, Object value, String componentPropertyName) throws java.io.IOException {
        if (name == null) {
            throw new NullPointerException("attributeName name must not be null");
        }
        if (!startTagOpen) {
            throw new IllegalStateException("Must be called before the start element is closed (attribute '" + name + "')");
        }

        String strValue = value.toString(); //TODO: Use converter for value?
        writer.write(' ');
        writer.write(name);
        writer.write("=\"" + strValue + "\"");
    }

    private void closeStartTagIfNecessary() throws java.io.IOException {
        if (startTagOpen) {
            if (s_emptyHtmlElements.contains(startElementName.toLowerCase())) {
                writer.write(" />");
                // make null, this will cause NullPointer in some invalid element nestings
                // (better than doing nothing)
                startElementName = null;
            }
            else {
                writer.write('>');
            }
            startTagOpen = false;
        }
    }

    public void flush() throws java.io.IOException {
        // API doc says we should not flush the underlying writer
        //_writer.flush();
        // but rather clear any values buffered by this ResponseWriter:
        closeStartTagIfNecessary();
    }

    public void write(char cbuf[], int off, int len) throws java.io.IOException {
        closeStartTagIfNecessary();
        writer.write(cbuf, off, len);
    }

    public void write(int c) throws java.io.IOException {
        closeStartTagIfNecessary();
        writer.write(c);
    }

    public void write(char cbuf[]) throws java.io.IOException {
        closeStartTagIfNecessary();
        writer.write(cbuf);
    }

    public void close() throws java.io.IOException {
        if (startTagOpen) {
            // we will get here only if no text was written after the start element was opened
            writer.write(" />");
        }
        writer.close();
    }

}
