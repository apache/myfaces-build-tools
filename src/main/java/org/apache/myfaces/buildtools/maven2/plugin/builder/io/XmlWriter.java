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
package org.apache.myfaces.buildtools.maven2.plugin.builder.io;

import java.io.PrintWriter;
import java.util.Stack;

/**
 * Simple utility to help write out data structures in xml format, with pretty
 * indenting.
 */
public class XmlWriter
{
    private static final String XML_CHARS = "<>&";

    private PrintWriter out;
    private Stack contexts = new Stack();
    private int indent = 0;

    // Is the current xml element still "open" for adding attributes,
    // ie needs a ">" before adding nested text or child elements.
    private boolean openElement = false;

    private static class Context
    {
        String elementName;
        boolean hasContent;

        Context(String element)
        {
            elementName = element;
        }
    }

    public XmlWriter(PrintWriter out)
    {
        this.out = out;
    }

    private void indent()
    {
        out.write("\n");
        for (int i = 0; i < indent; ++i)
        {
            out.write("  ");
        }
    }

    private void indentInc()
    {
        indent();
        ++indent;
    }

    private void indentDec()
    {
        --indent;
        indent();
    }

    private boolean containsXmlChars(String text)
    {
        for (int i = 0; i < XML_CHARS.length(); ++i)
        {
            if (text.indexOf(XML_CHARS.charAt(i)) >= 0)
            {
                return true;
            }
        }
        return false;
    }

    public void writeElement(String name, String body)
    {
        if (body == null)
        {
            return;
        }

        if (openElement)
        {
            out.write(">");
            openElement = false;
        }

        indent();
        out.write("<");
        out.write(name);
        out.write(">");

        if (containsXmlChars(body))
        {
            // The text contains chars that need to be escaped. Rather than
            // escape them one-by-one, just write the body in a CDATA section.

            if (body.indexOf("\n") > 0)
            {
                // multi-line body, so it is most readable when the CDATA
                // markers are against the left-hand margin
                out.write("\n<![CDATA[\n");
                out.write(body);
                out.write("\n]]>");

                // Write the end tag on the next line, correctly indented
                indent();
            }
            else
            {
                // just a small body, so output it "inline"
                out.write("<![CDATA[");
                out.write(body);
                out.write("]]>");
            }
        }
        else
        {
            out.write(body);
        }
        out.write("</");
        out.write(name);
        out.write(">");

        ((Context) contexts.peek()).hasContent = true;
    }

    public void writeElement(String name, boolean value)
    {
        writeElement(name, String.valueOf(value));
    }

    public void writeElement(String name, Boolean value)
    {
        if (value != null)
        {
            writeElement(name, value.toString());
        }
    }

    public void beginElement(String name)
    {
        if (openElement)
        {
            out.write(">");
        }
        openElement = true;

        indentInc();
        out.write("<");
        out.write(name);

        if (!contexts.empty())
        {
            ((Context) contexts.peek()).hasContent = true;
        }
        contexts.push(new Context(name));

    }

    public void writeAttr(String name, String value)
    {
        if (value == null)
        {
            return;
        }

        if (!openElement)
        {
            throw new IllegalStateException("xml element not open");
        }
        out.write(" ");
        out.write(name);
        out.write("=");
        out.write("\"");
        out.write(value);
        out.write("\"");
    }

    public void endElement(String name)
    {
        Context c = (Context) contexts.pop();

        if (!c.elementName.equals(name))
        {
            throw new IllegalStateException("Unbalanced xml: expected to end ["
                    + c.elementName + "]" + " but [" + name
                    + "] was ended instead");
        }

        if (c.hasContent)
        {
            indentDec();
            out.write("</");
            out.write(c.elementName);
            out.write(">");
        }
        else
        {
            out.write("/>");
            --indent; // decrement the indent, but do not write the linefeed
        }
        openElement = false;
    }
}
