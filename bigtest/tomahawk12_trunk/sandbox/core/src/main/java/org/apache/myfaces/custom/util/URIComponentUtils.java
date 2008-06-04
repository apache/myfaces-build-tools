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

package org.apache.myfaces.custom.util;

/**
 * This utility class provides equivalents to the
 * JavaScript encodeURIComponent(uri) and decodeURIComponent(encodedURI) functions.
 *
 * @author Gerald Müllan Date: 11.02.2007 Time: 23:30:08
 */
public abstract class URIComponentUtils
{

    public static String encodeURIComponent(String uri)
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

   /**
    * In case of incoming Strings - encoded with js encodeURIComponent()
    * e.g. ö -> %C3%B6 - it is not sufficient to set CharacterEncoding on the
    * ResponseWriter accordingly. Passing the uri to decodeURIComponent(String encodedURI)
    * decodes e.g. %C3%B6 back to ö.
    * @return decoded uri String
    * @param encoded uri String
    */
    public static String decodeURIComponent(String encodedURI)
    {
        char actualChar;

        StringBuffer buffer = new StringBuffer();

        int bytePattern, sumb = 0;

        for (int i = 0, more = -1; i < encodedURI.length(); i++)
        {
            actualChar = encodedURI.charAt(i);

            switch (actualChar)
            {
                case'%':
                {
                    actualChar = encodedURI.charAt(++i);
                    int hb = (Character.isDigit(actualChar) ? actualChar - '0'
                            : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    actualChar = encodedURI.charAt(++i);
                    int lb = (Character.isDigit(actualChar) ? actualChar - '0'
                            : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    bytePattern = (hb << 4) | lb;
                    break;
                }
                case'+':
                {
                    bytePattern = ' ';
                    break;
                }
                default:
                {
                    bytePattern = actualChar;
                }
            }

            //* Decode byte bytePattern as UTF-8, sumb collects incomplete chars *//*
            if ((bytePattern & 0xc0) == 0x80)
            {            // 10xxxxxx 
                sumb = (sumb << 6) | (bytePattern & 0x3f);
                if (--more == 0) buffer.append((char) sumb);
            }
            else if ((bytePattern & 0x80) == 0x00)
            {        // 0xxxxxxx
                buffer.append((char) bytePattern);
            }
            else if ((bytePattern & 0xe0) == 0xc0)
            {        // 110xxxxx
                sumb = bytePattern & 0x1f;
                more = 1;
            }
            else if ((bytePattern & 0xf0) == 0xe0)
            {        // 1110xxxx
                sumb = bytePattern & 0x0f;
                more = 2;
            }
            else if ((bytePattern & 0xf8) == 0xf0)
            {        // 11110xxx
                sumb = bytePattern & 0x07;
                more = 3;
            }
            else if ((bytePattern & 0xfc) == 0xf8)
            {        // 111110xx
                sumb = bytePattern & 0x03;
                more = 4;
            }
            else
            {    // 1111110x
                sumb = bytePattern & 0x01;
                more = 5;
            }
        }
        return buffer.toString();
    }
}
