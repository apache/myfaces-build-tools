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
package org.apache.myfaces.trinidadbuild.plugin.faces.io;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class PrettyWriter extends PrintWriter
{
  public PrettyWriter(
    OutputStream out)
  {
    super(out);
  }

  public PrettyWriter(
    OutputStream out,
    boolean      autoFlush)
  {
    super(out, autoFlush);
  }

  public PrettyWriter(
    Writer out)
  {
    super(out);
  }

  public PrettyWriter(
    Writer  out,
    boolean autoFlush)
  {
    super(out, autoFlush);
  }

  public void indent()
  {
    _indentation ++;
  }

  public void unindent()
  {
    _indentation --;
  }

  public void write(
    String string,
    int    offset,
    int    length)
  {
    if (_unlatchPending())
    {
      for (int i=0; i < _indentation; i++)
      {
        super.write(_TAB_INDENT, 0, 2);
      }
    }

    super.write(string, offset, length);
  }

  public void write(
    char[] chars,
    int    offset,
    int    length)
  {
    if (_unlatchPending())
    {
      for (int i=0; i < _indentation; i++)
      {
        super.write(_TAB_INDENT_CHARS, 0, 2);
      }
    }

    super.write(chars, offset, length);
  }

  public void println()
  {
    super.println();
    _latchPending();
  }

  public void println(
    boolean x)
  {
    super.println(x);
    _latchPending();
  }

  public void println(
    char x)
  {
    super.println(x);
    _latchPending();
  }

  public void println(
    int x)
  {
    super.println(x);
    _latchPending();
  }

  public void println(
    long x)
  {
    super.println(x);
    _latchPending();
  }

  public void println(
    float x)
  {
    super.println(x);
    _latchPending();
  }

  public void println(
    double x)
  {
    super.println(x);
    _latchPending();
  }

  public void println(
    char[] x)
  {
    super.println(x);
    _latchPending();
  }

  public void println(
    String x)
  {
    int fromIndex = 0;
    for (int index = x.indexOf('\n');
         index != -1  && index + 1 < x.length();
         fromIndex = index + 1,
         index = x.indexOf('\n', fromIndex))
    {
      super.println(x.substring(fromIndex, index));
      _latchPending();
    }

    super.println(x.substring(fromIndex));
    _latchPending();
  }

  public void println(
    Object x)
  {
    super.println(x);
    _latchPending();
  }

  private boolean _unlatchPending()
  {
    boolean pending = _pending;
    if (pending)
      _pending = false;

    return pending;
  }

  private boolean _latchPending()
  {
    boolean pending = _pending;
    if (!pending)
      _pending = true;

    return !pending;
  }

  private int _indentation;
  private boolean _pending = true;

  private static final String _TAB_INDENT = "  ";
  private static final char[] _TAB_INDENT_CHARS = _TAB_INDENT.toCharArray();
}
