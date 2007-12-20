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
package org.apache.myfaces.trinidadbuild.plugin.xrts;

/**
 * The <code>UnicodeEscapes</code> class converts bytes and characters outside
 * the ASCII character set to Java usable '\\u' Unicode escapes characters.  This
 * is a runtime version of native2ascii more or less.
 *
 * @version $Name:  $ ($Revision: 1.5 $) $Date: 2002/02/27 17:18:16 $
 */
public final class UnicodeEscapes
{

  private UnicodeEscapes()
  {
  }

  static public String convert(String s)
  {
    StringBuffer buffer = new StringBuffer(s.length());
    for(int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      if (c < 128)
      {
        switch (c)
        {
        case '\b':
          buffer.append("\\b");
          break;
        case '\t':
          buffer.append("\\t");
          break;
        case '\n':
          buffer.append("\\n");
          break;
        case '\f':
          buffer.append("\\f");
          break;
        case '\r':
          buffer.append("\\r");
          break;
        case '\"':
          buffer.append("\\\"");
          break;
        case '\'':
          buffer.append("\\\'");
          break;
        case '\\':
          buffer.append("\\\\");
          break;
        default:
          buffer.append(c);
          break;
        }
      }
      else
      {
        buffer.append("\\u");
        buffer.append(_hex[(c>>12)& 0x0f]);
        buffer.append(_hex[(c>>8)& 0x0f]);
        buffer.append(_hex[(c>>4)& 0x0f]);
        buffer.append(_hex[c & 0x0f]);
      }
    }
    return buffer.toString();
  }

//  String byteToHex(byte b) {
//    // Returns hex String representation of byte b
//    char hexDigit[] = {
//       '0', '1', '2', '3', '4', '5', '6', '7',
//       '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
//    };
//    char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
//    return new String(array);
//  }

//  String charToHex(char c) {
//    // Returns hex String representation of char c
//    byte hi = (byte) (c >>> 8);
//    byte lo = (byte) (c & 0xff);
//    return byteToHex(hi) + byteToHex(lo);
//  }

  private static final String[] _hex =
    {"0", "1", "2", "3", "4", "5", "6", "7",
     "8", "9", "a", "b", "c", "d", "e", "f"};
}
