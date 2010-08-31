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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

/**
 * Use the Java version of the JSMin algorithm to compress a set of JS files.
 * For simplicity, the JSMin code (one class) is included in the plugin.
 * 
 * @see http://www.crockford.com/javascript/jsmin.html
 * @author <a href="mailto:nicolas@apache.org">nicolas De Loof</a>
 */
public class JSMinCompressor
    implements JSCompressor
{

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.mojo.javascript.compress.JSCompressor#compress(java.io.File,
     * java.io.File, int, int)
     */
    public void compress( File input, File output, int level, int language )
        throws CompressionException
    {
        try
        {
            new JSMin( new FileInputStream( input ), new FileOutputStream( output ) ).jsmin();
        }
        catch ( Exception e )
        {
            throw new CompressionException( "Failed to create compressed file", e, input );
        }
    }

    public static class JSMin {
        private static final int EOF = -1;

        private PushbackInputStream in;
        private OutputStream out;

        private int theA;
        private int theB;

        public JSMin(InputStream in, OutputStream out) {
            this.in = new PushbackInputStream(in);
            this.out = out;
        }

        /**
         * isAlphanum -- return true if the character is a letter, digit,
         * underscore, dollar sign, or non-ASCII character.
         */
        static boolean isAlphanum(int c) {
            return ( (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') ||
                     (c >= 'A' && c <= 'Z') || c == '_' || c == '$' || c == '\\' ||
                     c > 126);
        }

        /**
         * get -- return the next character from stdin. Watch out for lookahead. If
         * the character is a control character, translate it to a space or
         * linefeed.
         */
        int get() throws IOException {
            int c = in.read();

            if (c >= ' ' || c == '\n' || c == EOF) {
                return c;
            }

            if (c == '\r') {
                return '\n';
            }

            return ' ';
        }



        /**
         * Get the next character without getting it.
         */
        int peek() throws IOException {
            int lookaheadChar = in.read();
            in.unread(lookaheadChar);
            return lookaheadChar;
        }

        /**
         * next -- get the next character, excluding comments. peek() is used to see
         * if a '/' is followed by a '/' or '*'.
         */
        int next() throws IOException, UnterminatedCommentException {
            int c = get();
            if (c == '/') {
                switch (peek()) {
                case '/':
                    for (;;) {
                        c = get();
                        if (c <= '\n') {
                            return c;
                        }
                    }

                case '*':
                    get();
                    for (;;) {
                        switch (get()) {
                        case '*':
                            if (peek() == '/') {
                                get();
                                return ' ';
                            }
                            break;
                        case EOF:
                            throw new UnterminatedCommentException();
                        }
                    }

                default:
                    return c;
                }

            }
            return c;
        }

        /**
         * action -- do something! What you do is determined by the argument: 1
         * Output A. Copy B to A. Get the next B. 2 Copy B to A. Get the next B.
         * (Delete A). 3 Get the next B. (Delete B). action treats a string as a
         * single character. Wow! action recognizes a regular expression if it is
         * preceded by ( or , or =.
         */

        void action(int d) throws IOException, UnterminatedRegExpLiteralException,
                UnterminatedCommentException, UnterminatedStringLiteralException {
            switch (d) {
            case 1:
                out.write(theA);
            case 2:
                theA = theB;

                if (theA == '\'' || theA == '"') {
                    for (;;) {
                        out.write(theA);
                        theA = get();
                        if (theA == theB) {
                            break;
                        }
                        if (theA <= '\n') {
                            throw new UnterminatedStringLiteralException();
                        }
                        if (theA == '\\') {
                            out.write(theA);
                            theA = get();
                        }
                    }
                }

            case 3:
                theB = next();
                if (theB == '/' && (theA == '(' || theA == ',' || theA == '=' ||
                                    theA == ':' || theA == '[' || theA == '!' ||
                                    theA == '&' || theA == '|' || theA == '?' ||
                                    theA == '{' || theA == '}' || theA == ';' ||
                                    theA == '\n')) {
                    out.write(theA);
                    out.write(theB);
                    for (;;) {
                        theA = get();
                        if (theA == '/') {
                            break;
                        } else if (theA == '\\') {
                            out.write(theA);
                            theA = get();
                        } else if (theA <= '\n') {
                            throw new UnterminatedRegExpLiteralException();
                        }
                        out.write(theA);
                    }
                    theB = next();
                }
            }
        }

        /**
         * jsmin -- Copy the input to the output, deleting the characters which are
         * insignificant to JavaScript. Comments will be removed. Tabs will be
         * replaced with spaces. Carriage returns will be replaced with linefeeds.
         * Most spaces and linefeeds will be removed.
         */
        public void jsmin() throws IOException, UnterminatedRegExpLiteralException, UnterminatedCommentException, UnterminatedStringLiteralException{
            theA = '\n';
            action(3);
            while (theA != EOF) {
                switch (theA) {
                case ' ':
                    if (isAlphanum(theB)) {
                        action(1);
                    } else {
                        action(2);
                    }
                    break;
                case '\n':
                    switch (theB) {
                    case '{':
                    case '[':
                    case '(':
                    case '+':
                    case '-':
                        action(1);
                        break;
                    case ' ':
                        action(3);
                        break;
                    default:
                        if (isAlphanum(theB)) {
                            action(1);
                        } else {
                            action(2);
                        }
                    }
                    break;
                default:
                    switch (theB) {
                    case ' ':
                        if (isAlphanum(theA)) {
                            action(1);
                            break;
                        }
                        action(3);
                        break;
                    case '\n':
                        switch (theA) {
                        case '}':
                        case ']':
                        case ')':
                        case '+':
                        case '-':
                        case '"':
                        case '\'':
                            action(1);
                            break;
                        default:
                            if (isAlphanum(theA)) {
                                action(1);
                            } else {
                                action(3);
                            }
                        }
                        break;
                    default:
                        action(1);
                        break;
                    }
                }
            }
            out.flush();
        }

        class UnterminatedCommentException extends Exception {
        }

        class UnterminatedStringLiteralException extends Exception {
        }

        class UnterminatedRegExpLiteralException extends Exception {
        }

        public static void main(String arg[]) {
            try {
                JSMin jsmin = new JSMin(new FileInputStream(arg[0]), System.out);
                jsmin.jsmin();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnterminatedRegExpLiteralException e) {
                e.printStackTrace();
            } catch (UnterminatedCommentException e) {
                e.printStackTrace();
            } catch (UnterminatedStringLiteralException e) {
                e.printStackTrace();
            }
        }


    }
}
