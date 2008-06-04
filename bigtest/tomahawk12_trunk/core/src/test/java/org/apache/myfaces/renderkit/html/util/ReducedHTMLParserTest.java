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
package org.apache.myfaces.renderkit.html.util;

import junit.framework.TestCase;
import org.apache.myfaces.renderkit.html.util.CallbackListener;
import org.apache.myfaces.renderkit.html.util.ReducedHTMLParser;

/**
 * Unit test for the ReducedHTMLParser class which detects tags within an HTML document.
 */
public class ReducedHTMLParserTest extends TestCase
{
    public static class ParseCallbackListener implements CallbackListener
    {
        int beforeHeadStart = -1;
        int afterHeadStart = -1;
        int beforeHeadEnd = -1;
        int afterHeadEnd = -1;
        int beforeBodyStart = -1;
        int afterBodyStart = -1;
        int beforeBodyEnd = -1;
        int afterBodyEnd = -1;

        public void openedStartTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.HEAD_TAG)
            {
                beforeHeadStart = charIndex;
            }
            else if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                beforeBodyStart = charIndex;
            }
        }

        public void closedStartTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.HEAD_TAG)
            {
                afterHeadStart = charIndex;
            }
            else if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                afterBodyStart = charIndex;
            }
        }

        public void openedEndTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.HEAD_TAG)
            {
                beforeHeadEnd = charIndex;
            }
            else if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                beforeBodyEnd = charIndex;
            }
        }

        public void closedEndTag(int charIndex, int tagIdentifier)
        {
            if (tagIdentifier == ReducedHTMLParser.HEAD_TAG)
            {
                afterHeadEnd = charIndex;
            }
            else if (tagIdentifier == ReducedHTMLParser.BODY_TAG)
            {
                afterBodyEnd = charIndex;
            }
        }

        public void attribute(int charIndex, int tagIdentifier, String key, String value)
        {
        }
    }

    public void testIsFinished1()
    {
        CharSequence seq = "";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);
        assertTrue("Empty sequence is finished", parser.isFinished());
    }

    public void testIsFinished2()
    {
        CharSequence seq = "xx yy";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);

        assertFalse("Sequence is finished", parser.isFinished());
        parser.consumeNonWhitespace();
        assertFalse("Sequence is finished", parser.isFinished());
        parser.consumeWhitespace();
        assertFalse("Sequence is finished", parser.isFinished());
        parser.consumeNonWhitespace();
        assertTrue("Sequence is finished", parser.isFinished());
    }

    public void testConsumeWhitespace()
    {
        CharSequence seq = "  \t  \r\n   xx    yy  ";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);

        // test that one call consumes all available whitespace
        // and that all sorts of whitespace are consumed.
        assertFalse("Sequence is finished", parser.isFinished());
        parser.consumeWhitespace();
        String word1 = parser.consumeNonWhitespace();
        assertEquals("xx found", "xx", word1);

        // test that multiple calls don't consume anything but whitespace
        parser.consumeWhitespace();
        parser.consumeWhitespace();
        parser.consumeWhitespace();
        String word2 = parser.consumeNonWhitespace();
        assertEquals("yy found", "yy", word2);

        // test that no failure occurs from consuming whitespace at the
        // end of the sequence
        assertFalse("Sequence is finished", parser.isFinished());
        parser.consumeWhitespace();
        parser.consumeWhitespace();
        assertTrue("Sequence is finished", parser.isFinished());
    }

    public void testConsumeNonWhitespace()
    {
        CharSequence seq = "xx yy zz";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);

        String word1 = parser.consumeNonWhitespace();
        assertEquals("xx found", "xx", word1);

        // test that a call against whitespace returns null
        String noWord = parser.consumeNonWhitespace();
        assertNull("ConsumeNonWhitespace when whitespace is present", noWord);

        // test that no exception is generated for multiple calls
        parser.consumeNonWhitespace();
        parser.consumeNonWhitespace();

        parser.consumeWhitespace();
        String word2 = parser.consumeNonWhitespace();
        assertEquals("yy found", "yy", word2);

        // test word that is at end of sequence
        parser.consumeWhitespace();
        String word3 = parser.consumeNonWhitespace();
        assertEquals("zz found", "zz", word3);

        // test that isFinished is set
        assertTrue("Sequence is finished", parser.isFinished());

        // test that no failure occurs from consuming nonwhitespace at the
        // end of the sequence
        noWord = parser.consumeNonWhitespace();
        assertNull("ConsumeNonWhitespace at end of sequence", noWord);
    }

    public void testConsumeMatch()
    {
        CharSequence seq = "xx <!-- yy --> zz";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);

        // test non-match
        assertFalse("Match non-matching pattern", parser.consumeMatch("ffff"));

        // test valid match. Also verifies that previous match failure didn't
        // move the parsing offset.
        assertTrue("Match matching pattern", parser.consumeMatch("xx"));

        // this won't match until whitespace removed
        assertFalse("Match non-matching pattern", parser.consumeMatch("<!--"));
        parser.consumeWhitespace();
        assertTrue("Match matching pattern", parser.consumeMatch("<!--"));

        // repeat
        assertFalse("Match non-matching pattern", parser.consumeMatch("yy"));
        parser.consumeWhitespace();
        assertTrue("Match matching pattern", parser.consumeMatch("yy"));

        parser.consumeWhitespace();
        assertTrue("Match matching pattern", parser.consumeMatch("-->"));

        // match at end of sequence
        parser.consumeWhitespace();
        assertTrue("Match matching pattern", parser.consumeMatch("zz"));

        // check no exception on matching on finished sequence
        assertFalse("Match non-matching pattern", parser.consumeMatch("aa"));
    }

    public void testConsumeElementName()
    {
        CharSequence seq = "  foo  t:foo t:FooBar t:foo_bar element-name/>";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);

        // test that consumeElementName will automatically skip any leading whitespace
        String name1 = parser.consumeElementName();
        assertEquals("Element name matched", "foo", name1);

        String name2 = parser.consumeElementName();
        assertEquals("Element name matched", "t:foo", name2);

        String name3 = parser.consumeElementName();
        assertEquals("Element name matched", "t:FooBar", name3);

        String name4 = parser.consumeElementName();
        assertEquals("Element name matched", "t:foo_bar", name4);

        String name5 = parser.consumeElementName();
        assertEquals("Element name matched", "element-name", name5);
    }

    public void testConsumeStringBasic()
    {
        CharSequence s1 = "'string1' \"string2\"";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(s1, listener);

        // Note that the consumeString method always expects the leading quote to
        // have been consumed already..

        // test single-quote delimited
        parser.consumeMatch("'");
        String str1 = parser.consumeString('\'');
        assertEquals("String correctly parsed", "string1", str1);

        // test double-quote delimited
        parser.consumeWhitespace();
        parser.consumeMatch("\"");
        String str2 = parser.consumeString('\"');
        assertEquals("String correctly parsed", "string2", str2);
    }

    public void testConsumeStringEscapedQuote()
    {
        char quoteMark = '\'';

        // build literal sequence 'don\'t quote me' not-in-the-string
        StringBuffer buf = new StringBuffer();
        buf.append(quoteMark);
        buf.append("don\\'t quote me");
        buf.append(quoteMark);
        buf.append(" not-in-the-string");

        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(buf, listener);

        // Note that the consumeString method always expects the leading quote to
        // have been consumed already..

        parser.consumeMatch("'");
        String str1 = parser.consumeString('\'');
        assertEquals("String correctly parsed", "don't quote me", str1);
    }

    public void testConsumeStringEscapedNonQuote()
    {
        char quoteMark = '"';

        // build literal sequence 'don\'t quote me' not-in-the-string
        StringBuffer buf = new StringBuffer();
        buf.append(quoteMark);
        buf.append("don\\'t quote me");
        buf.append(quoteMark);
        buf.append(" not-in-the-string");

        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(buf, listener);

        // Note that the consumeString method always expects the leading quote to
        // have been consumed already..

        parser.consumeMatch("\"");
        String str1 = parser.consumeString('"');
        assertEquals("String correctly parsed", "don\\'t quote me", str1);
    }
    
    public void testConsumeStringEscapedEscape()
    {
        char quoteMark = '\'';
        char backSlash = '\\';

        // build literal sequence 'don\\'t escape me' not-in-the-string
        // The double-backslash should be treated as a single backslash
        // which does *not* escape the following quote.
        StringBuffer buf = new StringBuffer();
        buf.append(quoteMark);
        buf.append("don");
        buf.append(backSlash);
        buf.append(backSlash);
        buf.append(quoteMark);
        buf.append("t escape me");
        buf.append(quoteMark);

        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(buf, listener);

        // Note that the consumeString method always expects the leading quote to
        // have been consumed already..

        parser.consumeMatch("'");
        String str1 = parser.consumeString('\'');
        assertEquals("String correctly parsed", "don" + backSlash, str1);
    }

    public void testConsumeAttrValue()
    {
        CharSequence seq = "  bare 'quoted 1' \"quoted 2\" bare2 ";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);

        String val1 = parser.consumeAttrValue();
        assertEquals("Attr value matched", "bare", val1);

        String val2 = parser.consumeAttrValue();
        assertEquals("Attr value matched", "quoted 1", val2);

        String val3 = parser.consumeAttrValue();
        assertEquals("Attr value matched", "quoted 2", val3);

        String val4 = parser.consumeAttrValue();
        assertEquals("Attr value matched", "bare2", val4);
    }

    public void testConsumeExcept()
    {
        CharSequence seq = "abc$$#dd  ee#ff-gghh ii";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(seq, listener);

        parser.consumeExcept("#e");
        String val1 = parser.consumeNonWhitespace();
        assertEquals("ConsumeExcept skipped expected chars", "#dd", val1);

        parser.consumeExcept("z-");
        String val2 = parser.consumeNonWhitespace();
        assertEquals("ConsumeExcept skipped expected chars", "-gghh", val2);

        // check that consumeExcept will reach end of buffer ok if none of
        // the desired chars are found
        assertFalse(parser.isFinished());
        parser.consumeExcept("z");
        assertTrue(parser.isFinished());

        // check that calling consumeExcept is safe at end-of-buffer
        parser.consumeExcept("z");
    }

    // test parsing completes when a lessthan is not followed by an element name,
    // and there is just whitespace up to end of the input.
    public void testParseBadTagNoElementName1()
    {
        String s = "xxxx \n\n <# \n\n";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(s, listener);

        parser.parse();
        assertTrue(parser.isFinished());
    }

    // test parsing completes when a lessthan is not followed by an element name,
    public void testParseBadTagNoElementName2()
    {
        String s = "xxxx \n\n <# \n\n hi there";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(s, listener);

        parser.parse();
        assertTrue(parser.isFinished());
    }

    // test parsing completes when an invalid char is found where an attribute name
    // is expected.
    public void testParseBadTagInvalidAttributeName()
    {
        String s = "<foo )/>";
        CallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(s, listener);

        parser.parse();
        assertTrue(parser.isFinished());
    }

    // test CDATA sections are handled
    public void testParseCDATA()
    {
        String s = "xx<head> <![CDATA[ <head> ]]> <body>";
        ParseCallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(s, listener);

        parser.parse();
        assertTrue(parser.isFinished());
        assertEquals("CDATA works", 8, listener.afterHeadStart);
        assertEquals("CDATA works", 30, listener.beforeBodyStart);
    }

    // test PI sections are handled
    public void testParsePI()
    {
        String s = "<?xml version=\"1.0\"?> xx<head> ";
        ParseCallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(s, listener);

        parser.parse();
        assertTrue(parser.isFinished());
        assertEquals("PI works", 30, listener.afterHeadStart);
    }

    // Test script element support; the spec states that a <script> or
    // <style> tag can contain anything except "/>"
    public void testScript()
    {
        String s1 = "<head>";
        String s2 = "<script type='text/javascript'>"
                    + "if (1<2) alert('foo');\n"
                    + "if (1>2) alert('bar');\n"
                    + "</script>";
        String s3 = "</head>";
        String s4 = "<body>";
        String s5 = "</body>";

        StringBuffer buf = new StringBuffer();
        buf.append(s1);
        buf.append(s2);
        buf.append(s3);
        buf.append(s4);
        buf.append(s5);

        ParseCallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(buf.toString(), listener);

        parser.parse();
        assertTrue(parser.isFinished());
        assertEquals("Script works", s1.length(), listener.afterHeadStart);
        int beforeHeadEnd = s1.length() + s2.length();
        assertEquals("Script works", beforeHeadEnd, listener.beforeHeadEnd);
        int beforeBodyStart = beforeHeadEnd + s3.length();
        assertEquals("Script works", beforeBodyStart, listener.beforeBodyStart);
        int beforeBodyEnd = beforeBodyStart + s4.length();
        assertEquals("Script works", beforeBodyEnd, listener.beforeBodyEnd);
    }

    // test the full parse method
    public void testParse()
    {
        String s0 = "<!DOCTYPE PUBLIC \"sss\" \"http:foo\">\n";
        String s1 = "<html><head>";
        String s2 = "\n<!-- a comment --><title>foo</title>";
        String s3 = "</head>";
        String s4 = "< body onclick='zz'>";
        String s5 = "  bodytext ";
        // if comments aren't correctly parsed, then this will cause the
        // head/body start positions to get corrupted.
        String s6 = "  <!-- <head> <body> -->";
        // if xml attr strings aren't correctly parsed, then this will cause
        // the head/body start positions to get corrupted
        String s7 = "<t:foo a1='<head>' a2='<body>'/>";
        String s8 = "</body> </html>";

        StringBuffer buf = new StringBuffer();
        buf.append(s0);
        buf.append(s1);
        buf.append(s2);
        buf.append(s3);
        buf.append(s4);
        buf.append(s5);
        buf.append(s6);
        buf.append(s7);
        buf.append(s8);

        ParseCallbackListener listener = new ParseCallbackListener();
        ReducedHTMLParser parser = new ReducedHTMLParser(buf, listener);

        parser.parse();

        // check that listener has correctly computed the offset to the char just
        // before the </head> tag starts.
        int afterHeadStart = s0.length() + s1.length();
        assertEquals("Pos after <head> tag ", afterHeadStart, listener.afterHeadStart);

        int beforeBodyStart = afterHeadStart + s2.length() + s3.length();
        assertEquals("Pos before <body> tag", beforeBodyStart, listener.beforeBodyStart);

        int afterBodyStart = beforeBodyStart + s4.length();
        assertEquals("Pos after <body> tag", afterBodyStart, listener.afterBodyStart);
    }
}
